package com.example.serial_ports_jssc;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

import jssc.SerialPortList;

public class MainActivity extends FlutterActivity {

    private static final String USB_CHANNEL = "flutter.native/serial";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {

        GeneratedPluginRegistrant.registerWith(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor(), USB_CHANNEL).setMethodCallHandler((call, result) -> {
            if (call.method.equals("listPorts")) {
                String[] ports = SerialPortList.getPortNames();
                String portNames = "<";
                for (String port : ports)
                    portNames += port + ";";
                result.success(portNames + ">");
            }
        });
    }
}