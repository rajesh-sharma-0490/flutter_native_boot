package com.example.auto_start

import android.os.Bundle
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {
    private var pipe: CommunicationPipe? = null;

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        GeneratedPluginRegistrant.registerWith(flutterEngine);

        Log.i("info", "in main activity(kotlin)")

        if(this.pipe == null){
            this.pipe = CommunicationPipe(this)
            this.pipe!!.setup(flutterEngine)
        }
    }
}
