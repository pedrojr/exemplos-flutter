import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(ExampleApp());

class ExampleApp extends StatefulWidget {
  const ExampleApp({super.key});

  @override
  _ExampleAppState createState() => _ExampleAppState();
}

class _ExampleAppState extends State<ExampleApp> {
  static const channel = MethodChannel('flutter.native/serial');
  String strItems = "";

  @override
  void initState() {
    super.initState();
  }

  void _listPorts() async {
    String? result = await channel.invokeMethod<String>('listPorts');
    if (result != null) {
      setState(() => strItems = result);
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Ports'),
        ),
        body: Scrollbar(
          child: ListView(
            children: [
              for (final item in strItems.split(';'))
                Builder(builder: (context) {
                  return ExpansionTile(
                    title: Text(item),
                  );
                }),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.refresh),
          onPressed: _listPorts,
        ),
      ),
    );
  }
}
