package com.example.auto_start;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.view.FlutterCallbackInformation;
import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterNativeView;

public class BootReceiver extends BroadcastReceiver{
    private FlutterEngine flutterEngine;

    @Override public void onReceive(final Context context, final Intent intent) {
        Log.i("info", "Boot_Receiver");

        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);

        setupFlutterCounterpart(context);

        Log.i("info", "Boot_Receiver exiting");
    }

    private void setupFlutterCounterpart(Context context){
        long isolateHandle = context.getSharedPreferences(CommunicationPipe.CHANNEL, Context.MODE_PRIVATE).getLong(CommunicationPipe.isolateHandleName, -1);

        Log.i("info", "Invoking Isolate: " + isolateHandle);

        flutterEngine = new FlutterEngine(context);

        flutterEngine.getDartExecutor().executeDartCallback(new DartExecutor.DartCallback(
            context.getAssets(),
            FlutterMain.findAppBundlePath(context),
            FlutterCallbackInformation.lookupCallbackInformation(isolateHandle)
        ));

//        FlutterMain.ensureInitializationComplete(context, null);
//        String appBundlePath = FlutterMain.findAppBundlePath();
//        FlutterCallbackInformation flutterCallback = FlutterCallbackInformation.lookupCallbackInformation(isolateHandle);
//
//        FlutterNativeView backgroundFlutterView = new FlutterNativeView(context, true);
//
//        val args = FlutterRunArguments()
//        args.bundlePath = appBundlePath
//        args.entrypoint = flutterCallback.callbackName
//        args.libraryPath = flutterCallback.callbackLibraryPath
//
//        backgroundFlutterView?.runFromBundle(args)
//
//// Initialize your registrant in the app class
//        pluginRegistrantCallback?.registerWith(backgroundFlutterView?.pluginRegistry)
    }
}
