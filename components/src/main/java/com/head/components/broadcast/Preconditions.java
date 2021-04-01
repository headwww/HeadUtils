package com.head.components.broadcast;

import android.os.Looper;

import io.reactivex.rxjava3.core.Emitter;


public class Preconditions {
    private Preconditions() {
        throw new AssertionError("No util class instances for you!");
    }

    static boolean checkLooperThread(final Emitter emitter) {
        if (Looper.myLooper() == null) {
            emitter.onError(new IllegalStateException("Calling thread is not associated with Looper"));
            return false;
        } else {
            return true;
        }
    }

}