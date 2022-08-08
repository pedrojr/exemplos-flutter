package com.example.method_channel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;

import java.util.Collection;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private static final String USB_CHANNEL = "flutter.native/usb";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {

        GeneratedPluginRegistrant.registerWith(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor(), USB_CHANNEL).setMethodCallHandler((call, result) -> {
            UsbManager usbManager = (UsbManager) getApplicationContext().getSystemService(Context.USB_SERVICE);
            Collection<UsbDevice> devices = usbManager.getDeviceList().values();
            if (call.method.equals("listDevices")) {
                String devicesNames = "<";
                for (UsbDevice device : devices)
                    devicesNames += device.getDeviceName() + ";";
                result.success(devicesNames + ">");
            }
            else if (call.method.equals("requestPermission"))
            {
                String deviceName = (String) call.argument("deviceName");
                PendingIntent mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        new Intent(getApplicationContext().getPackageName() + ".USB_PERMISSION"), 0);
                UsbDevice device = devices.stream().filter(d -> d.getDeviceName().equals(deviceName)).findFirst().get();
                if ((device != null) && !usbManager.hasPermission(device))
                    usbManager.requestPermission(device, mPendingIntent);
            }
        });
    }
}
