package net.learn2develop.bluetooth;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

public  class SendThread implements Runnable{
	private StreamConnection mConnection;
	OutputStream out = null;
	public SendThread() throws IOException
	{
		if(RemoteBluetoothServer.mode == 0){
			mConnection = WaitThread.connection;
			out = mConnection.openOutputStream();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(RemoteBluetoothServer.mode ==0){
				if(!RemoteBluetoothServer.bufferSend.isEmpty()){
					try {
						out.write(RemoteBluetoothServer.bufferSend.getBytes());
						RemoteBluetoothServer.bufferSend="";
						//out.write("aaa".getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(RemoteBluetoothServer.bufferSend);
						
				}
			}else if(RemoteBluetoothServer.mode ==1){
				if(!RemoteBluetoothServer.bufferSend.isEmpty()){
					SocketServer.sendWifi(RemoteBluetoothServer.bufferSend);
					RemoteBluetoothServer.bufferSend="";
				}
			}
			
		}
	}

}
