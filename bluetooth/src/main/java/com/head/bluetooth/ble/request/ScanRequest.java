package com.head.bluetooth.ble.request;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Handler;

import androidx.core.os.HandlerCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.head.bluetooth.ble.Ble;
import com.head.bluetooth.ble.BleHandler;
import com.head.bluetooth.ble.BleStates;
import com.head.bluetooth.ble.annotation.Implement;
import com.head.bluetooth.ble.callback.BleScanCallback;
import com.head.bluetooth.ble.callback.wrapper.BleWrapperCallback;
import com.head.bluetooth.ble.callback.wrapper.ScanWrapperCallback;
import com.head.bluetooth.ble.model.BleDevice;
import com.head.bluetooth.ble.model.ScanRecord;
import com.head.bluetooth.ble.scan.BleScannerCompat;
import com.head.bluetooth.ble.utils.Utils;

/**
 * Created by LiuLei on 2017/10/21.
 */
@Implement(ScanRequest.class)
public class ScanRequest<T extends BleDevice> implements ScanWrapperCallback {

    private static final String TAG = "ScanRequest";
    private static final String HANDLER_TOKEN = "stop_token";
    private boolean scanning;
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BleScanCallback<T> bleScanCallback;
    private final Map<String, T> scanDevices = new HashMap<>();
    private final Handler handler = BleHandler.of();
    private final BleWrapperCallback<T> bleWrapperCallback = Ble.options().getBleWrapperCallback();

    public void startScan(BleScanCallback<T> callback, long scanPeriod) {
        if (callback == null) throw new IllegalArgumentException("BleScanCallback can not be null!");
        bleScanCallback = callback;
        //TODO
        if (!Utils.isPermission(Ble.getInstance().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            if (bleScanCallback != null){
                bleScanCallback.onScanFailed(BleStates.BlePermissionError);
            }
            return;
        }
        if (!isEnableInternal()) {
            return;
        }
        if (scanning) {
            if (bleScanCallback != null){
                bleScanCallback.onScanFailed(BleStates.ScanAlready);
            }
            return;
        }
        // Stops scanning after a pre-defined scan period.
        if (scanPeriod >= 0){
            HandlerCompat.postDelayed(handler, new Runnable() {
                @Override
                public void run() {
                    if (scanning) {
                        stopScan();
                    }
                }
            }, HANDLER_TOKEN, scanPeriod);
        }
        BleScannerCompat.getScanner().startScan(this);
    }

    private boolean isEnableInternal() {
        if (!bluetoothAdapter.isEnabled()) {
            if (bleScanCallback != null) {
                bleScanCallback.onScanFailed(BleStates.BluetoothNotOpen);
                return false;
            }
        }
        return true;
    }

    public void stopScan() {
        if (!isEnableInternal()) {
            return;
        }
        if (!scanning) {
            if (bleScanCallback != null) {
                bleScanCallback.onScanFailed(BleStates.ScanStopAlready);
            }
            return;
        }
        handler.removeCallbacksAndMessages(HANDLER_TOKEN);
        BleScannerCompat.getScanner().stopScan();
    }

    @Override
    public void onStart() {
        scanning = true;
        if (bleScanCallback != null) {
            bleScanCallback.onStart();
        }

        if(bleWrapperCallback != null){
            bleWrapperCallback.onStart();
        }
    }

    @Override
    public void onStop() {
        scanning = false;
        if (bleScanCallback != null) {
            bleScanCallback.onStop();
            bleScanCallback = null;
        }
        if(bleWrapperCallback != null){
            bleWrapperCallback.onStop();
        }
        scanDevices.clear();
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (device == null) return;
        String address = device.getAddress();
        T bleDevice = getDevice(address);
        if (bleDevice == null) {
            bleDevice = (T) Ble.options().getFactory().create(address, device.getName());
            bleDevice.setDeviceType(device.getType());
            if (bleScanCallback != null) {
                bleScanCallback.onLeScan(bleDevice, rssi, scanRecord);
            }
            if (bleWrapperCallback != null){
                bleWrapperCallback.onLeScan(bleDevice, rssi, scanRecord);
            }
            scanDevices.put(device.getAddress(), bleDevice);
        } else {
            if (!Ble.options().isIgnoreRepeat) {//????????????
                if (bleScanCallback != null) {
                    bleScanCallback.onLeScan(bleDevice, rssi, scanRecord);
                }
                if (bleWrapperCallback != null){
                    bleWrapperCallback.onLeScan(bleDevice, rssi, scanRecord);
                }
            }
        }
    }

    @Override
    public void onScanFailed(int errorCode) {
        if (bleScanCallback != null) {
            bleScanCallback.onScanFailed(errorCode);
        }
    }

    @Override
    public void onParsedData(BluetoothDevice device, ScanRecord scanRecord) {
        if (bleScanCallback != null) {
            T bleDevice = getDevice(device.getAddress());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bleScanCallback.onParsedData(bleDevice, scanRecord);
            }
        }
    }

    public boolean isScanning() {
        return scanning;
    }

    //?????????????????????????????????????????????
    private T getDevice(String address) {
        return scanDevices.get(address);
    }

    public void cancelScanCallback(){
        bleScanCallback = null;
    }

}
