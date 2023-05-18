package com.example.auto_start;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class CommunicationPipe {
    public static final String CHANNEL = "communication.pipe";
    private MethodChannel pipe;
    private SharedPreferences sharedPreferences;
    public static final String isolateHandleName = "isolateHandle";

    CommunicationPipe(Context context){
        this.sharedPreferences = context.getSharedPreferences(CHANNEL, Context.MODE_PRIVATE);
    }

    public void setup(FlutterEngine flutterEngine){
        this.pipe = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);

        Log.i("info", "setting up method handler");
        this.pipe.setMethodCallHandler((call, result) -> {
            Log.i("info", "method call handler");
            if(call.method.equals("saveBgIsolateHandle")) {
                Log.i("info", "saveBgIsolateHandle");
                long handle = call.argument("handle");
                Log.i("info", "isolate handle: " + handle);
                sharedPreferences.edit().putLong(isolateHandleName, handle).apply();
                result.success(null);

                return;
            }

            result.notImplemented();
        });
    }
}
