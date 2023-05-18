import 'dart:developer';
import 'dart:ui';
import 'package:flutter/widgets.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter/services.dart';

void bgExecuteAsync() {
  log("inside isolate");
  print("inside isolate");

  WidgetsFlutterBinding.ensureInitialized();

  FlutterLocalNotificationsPlugin plugin = FlutterLocalNotificationsPlugin();

  plugin.initialize(
    InitializationSettings(
        android: const AndroidInitializationSettings("test")),
  );

  plugin
      .show(
          1,
          "title",
          "body",
          const NotificationDetails(
              android: AndroidNotificationDetails("1", "test")))
      .then((value) => print("done"), onError: (err) => print(err));
}

class NotificationFacade {
  static const _pipe = MethodChannel('communication.pipe');

  static Future<void> initialize() async {
    final callback = PluginUtilities.getCallbackHandle(bgExecuteAsync);

    var handle = callback?.toRawHandle();

    await _pipe.invokeMethod('saveBgIsolateHandle', {"handle": handle});
  }
}
