package com.head.bluetooth.ble.proxy;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.head.bluetooth.ble.BleLog;
import com.head.bluetooth.ble.request.ConnectRequest;
import com.head.bluetooth.ble.request.DescriptorRequest;
import com.head.bluetooth.ble.request.MtuRequest;
import com.head.bluetooth.ble.request.NotifyRequest;
import com.head.bluetooth.ble.request.ReadRequest;
import com.head.bluetooth.ble.request.ReadRssiRequest;
import com.head.bluetooth.ble.request.Rproxy;
import com.head.bluetooth.ble.request.ScanRequest;
import com.head.bluetooth.ble.request.WriteRequest;

/**
 *
 * Created by LiuLei on 2017/9/1.
 */

public class RequestProxy implements InvocationHandler{
    private static final String TAG = "RequestProxy";

    private RequestProxy(){}

    private Object receiver;

    public static RequestProxy newProxy(){
        return new RequestProxy();
    }

    //Bind the delegate object and return the proxy class
    public Object bindProxy(Context context, Object tar){
        this.receiver = tar;
        //绑定委托对象，并返回代理类
        BleLog.d(TAG, "bindProxy: "+"Binding agent successfully");
        Rproxy.init(ConnectRequest.class, MtuRequest.class,
                NotifyRequest.class, ReadRequest.class,
                ReadRssiRequest.class, ScanRequest.class,
                WriteRequest.class, DescriptorRequest.class);
        return Proxy.newProxyInstance(
                tar.getClass().getClassLoader(),
                tar.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(receiver, args);
    }
}
