package net.learn2develop.bluetooth;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ItemSelectable;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RemoteBluetoothServer {
	public static JFrame mainFrame;
	public static JPanel statusBar;
	public static JLabel statusField;
	public static JTextField statusColor;
	public static JTextArea chatText;
	public static JTextField chatLine;
	public static String bufferSend = null;
	public static String bufferReceive = null;
	public static JTextField phoneNum;
	private static JTextArea bodySms;
	private static JButton sendButton;
	private static JButton sendButton2;
	public static Thread waitThread;
	public static Thread socketThread;
	//public static Robot robot;
	public static SocketServer m_SocketServer;
	public static int mode;
	public static void main(String[] args) throws IOException {
		/*waitThread = new Thread(new WaitThread());
		waitThread.start();*/
		try {
			ProcessConectionThread.robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_SocketServer = new SocketServer();
	//	socketThread = new Thread(new SocketServer());
	//	socketThread.start();
	
		Thread sendThread = new Thread(new SendThread());
	    sendThread.start();
	    
		bufferSend = new String();
		bufferReceive = new String();
		initGui();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!sendButton.isEnabled()){
					sendButton.setEnabled(true);
				}
				if(!sendButton2.isEnabled()){
					sendButton2.setEnabled(true);
				}
				//System.out.println("test");
			}
		}, 0, 10000 );
		
			
	}
	public static void initGui(){
		//Set up Status Bar pane
		statusField = new JLabel();
		statusField.setText("Disconnect");
		statusColor = new JTextField(1);
		statusColor.setBackground(Color.red);
		statusColor.setEditable(false);
		statusBar = new JPanel(new BorderLayout());
		//JButton option = new JButton();
		JPanel panel = new JPanel();
		final JCheckBox wifi  = new JCheckBox("Wifi");
		final JCheckBox bluetooth = new JCheckBox("Bluetooth");
		/***************************BLUETOOH BUTTON*********************/
		bluetooth.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				wifi.setSelected(false);
				
				waitThread = new Thread(new WaitThread());
				waitThread.start();
				mode = 0;
				
			
			}
		});
		/***************************WIFI BUTTON*********************/
		wifi.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				bluetooth.setSelected(false);
				//waitThread = null;
				socketThread = new Thread(m_SocketServer);
				socketThread.start();
			}
		});
		/***********************GUI************************************/
		panel.add(bluetooth,BorderLayout.WEST);
		panel.add(wifi,BorderLayout.EAST);
		statusBar.add(statusColor,BorderLayout.WEST);
		statusBar.add(statusField,BorderLayout.CENTER);
		statusBar.add(panel,BorderLayout.EAST);
		
		
		//Set the Chat Pane
		JPanel chatPane = new JPanel(new BorderLayout());
		chatText = new JTextArea(10, 20);
		chatText.setLineWrap(true);
		chatText.setEditable(false);
		chatText.setForeground(Color.blue);
		JScrollPane chatTextPane = new JScrollPane(chatText,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		phoneNum = new JTextField(10);
		phoneNum.setEnabled(true);
			
		bodySms = new JTextArea(10,20);
		bodySms.setEditable(true);
		bodySms.setLineWrap(true);
		
		JPanel button = new JPanel(new BorderLayout());
		sendButton = new JButton();
		sendButton.setText("Send");
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				bufferSend = phoneNum.getText()+";"+bodySms.getText()+";send";
				if(!bufferSend.equals("")){
					System.out.println(bufferSend);
					sendButton.setEnabled(false);
					
					/*robot.mousePress(InputEvent.BUTTON3_MASK);
			        robot.mouseRelease(InputEvent.BUTTON3_MASK);*/
				}
			}
		});
		
		sendButton2 = new JButton();
		sendButton2.setText("OpenSms");
		sendButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				bufferSend = phoneNum.getText()+";"+bodySms.getText()+";open";
				if(!bufferSend.equals("")){
					System.out.println(bufferSend);
					sendButton2.setEnabled(false);
				}
			}
		});
		button.add(sendButton, BorderLayout.WEST);
		button.add(sendButton2,BorderLayout.EAST);
		
		JPanel sendPane = new JPanel();
		sendPane.setLayout(new BorderLayout());
		sendPane.add(phoneNum, BorderLayout.NORTH);
		sendPane.add(bodySms,BorderLayout.CENTER);
		sendPane.add(button,BorderLayout.SOUTH);
		chatPane.add(chatTextPane,BorderLayout.EAST);
		chatPane.add(sendPane,BorderLayout.WEST);
		
		
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.add(statusBar,BorderLayout.SOUTH);
		mainPane.add(chatPane,BorderLayout.CENTER);
		//Set up the main frame
		mainFrame = new JFrame("Bluetooth");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(mainFrame.getPreferredSize());
		mainFrame.setContentPane(mainPane);
		mainFrame.setLocation(200, 200);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		
	}
}
