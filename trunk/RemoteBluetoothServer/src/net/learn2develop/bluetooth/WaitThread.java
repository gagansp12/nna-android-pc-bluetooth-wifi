package net.learn2develop.bluetooth;

import java.awt.Color;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WaitThread implements Runnable {
	/** Constructor */
	public static StreamConnection connection = null;
	public WaitThread() {
	}

	@Override
	public void run() {
		waitForConnection();
	}

	/** Waiting for connection from devices */
	private void waitForConnection() {
		// retrieve the local Bluetooth device object
		LocalDevice local = null;

		StreamConnectionNotifier notifier;
		

		
		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			System.out.println(local.getBluetoothAddress());
			
			local.setDiscoverable(DiscoveryAgent.GIAC);

			UUID uuid = new UUID(8844); // "04c6093b-0000-1000-8000-00805f9b34fb"
			String url = "btspp://localhost:" + uuid + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
       	       	// waiting for connection
		Thread thisThread = Thread.currentThread();
		while(true) {
			try {
				System.out.println("waiting for connection...");
				
	            connection = notifier.acceptAndOpen();
	            RemoteBluetoothServer.statusField.setText("Connected");	
	            RemoteBluetoothServer.statusColor.setBackground(Color.blue);
				Thread processThread = new Thread(new ProcessConectionThread(connection));
				processThread.start();
				
			//	Thread sendThread = new Thread(new SendThread());
			  //  sendThread.start();
			 
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

}
