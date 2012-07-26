package com.example.android.BluetoothChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class socketThread {
	public Socket socket;
	public static int stateSocket;
	Thread myCommsThread = null;
	private final Handler mHandler;
	public socketThread(String ip,int port,Handler handler){
		mHandler = handler;
		stateSocket = 0;
		try{
			
			InetAddress serverAddr = InetAddress.getByName(ip);
			socket = new Socket(serverAddr, port);
			if(socket.isConnected()){
	    	 stateSocket = 1;
			 myCommsThread = new Thread(new CommsThread());
	    	 myCommsThread.start();
	    	 
			}
	  	} catch (IOException e1) {
		   // e1.printStackTrace();
	  		socket = null;
		 // tvStateConnect.setText("Cannot Connect Server");
	  	}
	}
	class CommsThread implements Runnable {
		public void run() {
            //Socket s = null;
            while (!Thread.currentThread().isInterrupted()) {
               Message m = new Message();
               m.what = BluetoothChat.MESSAGE_READ;
               if(socket != null){
                  try {
                   BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                   String st = null;
                   st = input.readLine();
                   //mHandler.obtainMessage(BluetoothChat.MESSAGE_READ, st.getBytes(), -1, buffer).sendToTarget();
                   BluetoothChat.bufferReceive = st;
                   mHandler.obtainMessage(BluetoothChat.MESSAGE_READ_WIFI).sendToTarget();
                   //  mClientMsg = st;
                   //    myUpdateHandler.sendMessage(m);
                } catch (IOException e) {
                        e.printStackTrace();
                }
            }
            }
		}
	}
	public void sendMessage(String str){
	    PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),true);
			out.println(str);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
}
