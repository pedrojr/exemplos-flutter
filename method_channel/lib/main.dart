import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(ExampleApp());

class ExampleApp extends StatefulWidget {
  const ExampleApp({super.key});

  @override
  _ExampleAppState createState() => _ExampleAppState();
}

class _ExampleAppState extends State<ExampleApp> {
  static const platform = MethodChannel('flutter.native/usb');
  String devices = "";

  @override
  void initState() {
    super.initState();
  }

  void _listDevices() async {
    String? result = await platform.invokeMethod<String>('listDevices');
    if (result != null) {
      setState(() => devices = result);
    }
  }

  Future<String> _requestPermission(String deviceName) async {
    String? result = await platform.invokeMethod<String>('requestPermission', {'deviceName':deviceName});
    return result!;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Devices'),
        ),
        body: Scrollbar(
          child: ListView(
            children: [
              for (final device in devices.split(';'))
                Builder(builder: (context) {
                  return ExpansionTile(
                    title: Text(device),
                  );
                }),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.refresh),
          onPressed: _listDevices,
        ),
      ),
    );
  }
}
