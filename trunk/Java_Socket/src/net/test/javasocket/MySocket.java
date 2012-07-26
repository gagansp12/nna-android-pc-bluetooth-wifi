package net.test.javasocket;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket implements Runnable {
	public static final int SERVER = 0;
	public static final int CLIENT = 1;
	public int m_Connect;
	public int m_Port;
    private ServerSocket ss;
    public static Socket s ;
    BufferedReader is;
	public MySocket(int m_sPort){
		m_Connect = SERVER;
		m_Port = m_sPort;
		try {
			ss = new ServerSocket(m_Port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MySocket(String m_sTcpip, int m_sPort){
		m_Connect = CLIENT;
		try {
			InetAddress serverAddr = InetAddress.getByName(m_sTcpip);
			s = new Socket(serverAddr, m_sPort);
			Java_socket.statusColor.setBackground(Color.blue);
			Java_socket.statusField.setText("Connected Server");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(s == null){
				if(m_Connect == SERVER){
					try {
						Java_socket.statusField.setText("Waiting.... ");
						s = ss.accept();
						Java_socket.statusField.setText("Connected Client ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}else {
				 try {
					 is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			         String name = is.readLine();
			         Java_socket.chatText.insert("=> "+name+"\n", 0);
	     
				 } catch (IOException e) {
		            System.err.println("Oh, dear me! " + e);
		            s = null;
		            Java_socket.statusField.setText("Waiting.....");
           
		         }
		     } 
		}
	}
	public void send(String st){
		PrintWriter pout;
		try {
			pout = new PrintWriter(s.getOutputStream(), true);
			Java_socket.chatText.insert("<= "+st+"\n", 0);
			pout.println(st);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	public void close(){
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
