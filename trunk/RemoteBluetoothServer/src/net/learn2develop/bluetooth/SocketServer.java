package net.learn2develop.bluetooth;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {
	
   final static int MINUTES = 1;
   public final static int PORT = 5000;
   static ServerSocket ss;
   public static Socket s ;
   BufferedReader is;
   public SocketServer(){
		try {
			ss = new ServerSocket(PORT);
			//ss.setSoTimeout(3000);
			s = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("SocketServer waiting for connection");
        RemoteBluetoothServer.mode = 1;
   }
	@Override
	public void run() {
		
		 System.out.println("waiting client"); 
		// TODO Auto-generated method stub
		 try {
			 s = ss.accept();
			 RemoteBluetoothServer.statusColor.setBackground(Color.blue);
			 RemoteBluetoothServer.statusField.setText("Connect Wifi");
		 } catch (IOException e1) { 
				// TODO Auto-generated catch block
				e1.printStackTrace();
		 }
		
		 while ( s != null) {
			 try {
				 System.out.println("waiting input event"); 
				 is = new BufferedReader(
		            new InputStreamReader(s.getInputStream()));
		         String name = is.readLine();
		         RemoteBluetoothServer.chatText.setText(name);
				 ProcessConectionThread.mouseClass(name);
	         } catch (IOException e) {
	            System.err.println("Oh, dear me! " + e);
	         }
	      } 
		 System.out.println("Exit Thread");
	}
	public static void sendWifi(String st){
		 PrintWriter pout;
		try {
			pout = new PrintWriter(s.getOutputStream(), true);
			pout.println(st);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public static void closeSocket(){
		s = null;
		
		/*try {
			ss.close();
			
			//ss.setSoTimeout(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
