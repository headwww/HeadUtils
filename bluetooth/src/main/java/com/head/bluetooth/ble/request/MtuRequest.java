package com.head.bluetooth.ble.request;

import com.head.bluetooth.ble.Ble;
import com.head.bluetooth.ble.callback.wrapper.BleWrapperCallback;
import com.head.bluetooth.ble.callback.wrapper.MtuWrapperCallback;
import com.head.bluetooth.ble.model.BleDevice;
import com.head.bluetooth.ble.BleRequestImpl;
import com.head.bluetooth.ble.annotation.Implement;
import com.head.bluetooth.ble.callback.BleMtuCallback;

/**
 *
 * Created by LiuLei on 2017/10/23.
 */
@Implement(MtuRequest.class)
public class MtuRequest<T extends BleDevice> implements MtuWrapperCallback<T> {

    private BleMtuCallback<T> bleMtuCallback;
    private final BleWrapperCallback<T> bleWrapperCallback = Ble.options().getBleWrapperCallback();
    private final BleRequestImpl<T> bleRequest = BleRequestImpl.getBleRequest();

    public boolean setMtu(String address, int mtu, BleMtuCallback<T> callback){
        this.bleMtuCallback = callback;
        return bleRequest.setMtu(address, mtu);
    }

    @Override
    public void onMtuChanged(T device, int mtu, int status) {
        if(null != bleMtuCallback){
            bleMtuCallback.onMtuChanged(device, mtu, status);
        }

        if (bleWrapperCallback != null){
            bleWrapperCallback.onMtuChanged(device, mtu, status);
        }
    }
}
