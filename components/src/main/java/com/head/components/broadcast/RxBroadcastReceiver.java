package com.head.components.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Cancellable;


public class RxBroadcastReceiver implements ObservableOnSubscribe<Intent> {

    @NonNull
    private final Context context;
    @NonNull
    private final IntentFilter intentFilter;

    RxBroadcastReceiver(@NonNull final Context context, @NonNull final IntentFilter intentFilter) {
        this.context = context.getApplicationContext();
        this.intentFilter = intentFilter;
    }

    @Override
    public void subscribe(final ObservableEmitter<Intent> emitter) {
        if (!Preconditions.checkLooperThread(emitter)) {
            return;
        }
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                emitter.onNext(intent);
            }
        };

        if (Looper.myLooper() == Looper.getMainLooper()) {
            context.registerReceiver(receiver, intentFilter);
        } else {
            context.registerReceiver(receiver, intentFilter, null, new Handler(Looper.myLooper()));
        }
        emitter.setCancellable(new Cancellable() {
            @Override
            public void cancel() {
                context.unregisterReceiver(receiver);
            }
        });
    }
}