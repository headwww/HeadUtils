package com.head.bluetooth.ble.queue.retry;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.head.bluetooth.ble.Ble;
import com.head.bluetooth.ble.BleLog;
import com.head.bluetooth.ble.BleStates;
import com.head.bluetooth.ble.callback.BleConnectCallback;
import com.head.bluetooth.ble.model.BleDevice;
import com.head.bluetooth.ble.queue.reconnect.DefaultReConnectHandler;
import com.head.bluetooth.ble.request.ConnectRequest;
import com.head.bluetooth.ble.request.Rproxy;

/**
 * author: jerry
 * date: 21-1-8
 * email: superliu0911@gmail.com
 * des:
 */
public class RetryDispatcher<T extends BleDevice> extends BleConnectCallback<T> implements RetryCallback<T> {
    private static final String TAG = "RetryDispatcher";
    private static RetryDispatcher retryDispatcher;
    private final Map<String, Integer> deviceRetryMap = new HashMap<>();

    public static <T extends BleDevice>RetryDispatcher<T> getInstance() {
        if (retryDispatcher == null){
            retryDispatcher = new RetryDispatcher();
        }
        return retryDispatcher;
    }

    @Override
    public void retry(T device) {
        BleLog.i(TAG, "正在尝试重试连接第"+deviceRetryMap.get(device.getBleAddress())+"次重连: "+device.getBleName());
        if (!device.isAutoConnect()){
            ConnectRequest<T> connectRequest = Rproxy.getRequest(ConnectRequest.class);
            connectRequest.connect(device);
        }
    }

    @Override
    public void onConnectionChanged(BleDevice device) {
        BleLog.i(TAG, "onConnectionChanged:"+device.getBleName()+"---连接状态:"+device.isConnected());
        if (device.isConnected()){
            String key = device.getBleAddress();
            deviceRetryMap.remove(key);
        }
    }

    @Override
    public void onConnectFailed(T device, int errorCode) {
        super.onConnectFailed(device, errorCode);
        if (errorCode == BleStates.ConnectError || errorCode == BleStates.ConnectFailed){
            String key = device.getBleAddress();
            int lastRetryCount = Ble.options().connectFailedRetryCount;
            if (lastRetryCount <= 0)return;
            if (deviceRetryMap.containsKey(key)){
                lastRetryCount = deviceRetryMap.get(key);
            }
            if (lastRetryCount <= 0){
                deviceRetryMap.remove(key);
                return;
            }
            deviceRetryMap.put(key, lastRetryCount-1);
            retry(device);
        }
    }
}
