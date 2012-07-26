package net.learn2develop.bluetooth;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.StreamConnection;

public class ProcessConectionThread implements Runnable{

	private StreamConnection mConnection;

	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;
	
	byte[] temp = new byte[1000];
	//OutputStream out = null;
	InputStream in = null;
	public static Robot robot ;
	static float  MouseX;
	static float MouseY;
	static float MouseX1;
	static float MouseY1;
	float kScale;
	static int StateDown = 0;
	private final static int PC_Height = 768;
	private final static int And_Height = 180;
	float timeTick;
	//byte[] temp = null;
	public ProcessConectionThread(StreamConnection connection) throws IOException
	{
		mConnection = connection;
	    in = mConnection.openInputStream();
		
		
	}

	@Override
	public void run() {
		try {
			// prepare to receive data
			System.out.println("waiting for input");
			
			while (true) {
				RemoteBluetoothServer.mode = 0;
				int command = in.read(temp);
				String sReceive = new String(temp);
				sReceive= sReceive.substring(0,command);
				RemoteBluetoothServer.chatText.setText(sReceive);
				mouseClass(sReceive);
				if (command == EXIT_CMD)
				{
					System.out.println("finish process");
					break;
				}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static void mouseClass(String receive) {
		String s[] = receive.split(";");
		int CurrentX,CurrentY;
		CurrentX = MouseInfo.getPointerInfo().getLocation().x;
		CurrentY = MouseInfo.getPointerInfo().getLocation().y;
		/**********************PRESENTATION************************/
		if(receive.equals("mouseleft")){
			robot.mousePress(InputEvent.BUTTON1_MASK);
	        robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}else if(receive.equals("mouseright")){
			robot.mousePress(InputEvent.BUTTON3_MASK);
	        robot.mouseRelease(InputEvent.BUTTON3_MASK);
		}else if(receive.equals("presentationNext")){
			//robot.mousePress(InputEvent.BUTTON3_MASK);
	        //robot.mouseRelease(InputEvent.BUTTON3_MASK);
			robot.keyPress(KeyEvent.VK_LEFT);
			robot.keyRelease(KeyEvent.VK_LEFT);
		}else if(receive.equals("presentationPrevious")){
			robot.keyPress(KeyEvent.VK_RIGHT);
			robot.keyRelease(KeyEvent.VK_RIGHT);
		}else if(receive.equals("presentationStart")){
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
		}else if(receive.equals("presentationExit")){
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		}else if(receive.equals("presentationStartCurrent")){
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}else if(receive.equals("presentationExitAll")){
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
		}
		/**********************CONTROL************************/
		if(!s[0].equals("mouse")) return;
		System.out.println(s[1]);
		if(s[1].equals("0")){
			MouseX = new Float(s[2])*PC_Height/And_Height;
			MouseY = new Float(s[3])*PC_Height/And_Height;
			MouseX1 = new Float(s[2])*PC_Height/And_Height;
			MouseY1 = new Float(s[3])*PC_Height/And_Height;
			StateDown = 1;
			
		}
		if(s[1].equals("1") && (StateDown ==1)){
			robot.mousePress(InputEvent.BUTTON1_MASK);
	        robot.mouseRelease(InputEvent.BUTTON1_MASK);
			StateDown = 0;
		}
		if(s[1].equals("2")){
			//kScale = kScale ;
			
			MouseX = new Float(s[2])*PC_Height/And_Height;
			MouseY = new Float(s[3])*PC_Height/And_Height;
			if(MouseX >= 1366) MouseX = 1366;
			if(MouseY >= 768) MouseY = 768;
		
			robot.mouseMove(CurrentX + (int)(-MouseX1 + MouseX)
						,CurrentY + (int)(-MouseY1 + MouseY));
			MouseX1 = MouseX;
			MouseY1 = MouseY;
		
		}
		
	
	}
	/**
	 * Process the command from client
	 * @param command the command code
	 */
}
