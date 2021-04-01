package com.head.components.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.CheckResult;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;


public class HeadBroadcastReceivers {
    private HeadBroadcastReceivers() {
        throw new AssertionError("No util class instances for you!");
    }

    /**
     * Creates Observable that will register {@link android.content.BroadcastReceiver} for provided
     * {@link IntentFilter} when subscribed to. Observable will emit received broadcast as data {@link Intent}
     *
     * @param context used to register broadcast receiver to.
     * @param filter  {@link IntentFilter} used to select Intent broadcast to be received.
     */
    @NonNull
    @CheckResult
    public static Observable<Intent> fromIntentFilter(@NonNull final Context context,
                                                      @NonNull final IntentFilter filter) {
        return Observable.create(new RxBroadcastReceiver(context, filter));
    }
}
