package net.test.javasocket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Java_socket {
	public static JFrame mainFrame;
	public static JPanel SetupPanel;
	public static JPanel statusBar;
	public static JLabel statusField;
	public static JTextField statusColor;
	public static JTextArea chatText;
	public static JTextField chatLine;
	public static String bufferSend = null;
	public static String bufferReceive = null;
	public static JTextField phoneNum;
	public  static JTextArea bodySms;
	public static KeyAdapter mKeyAdapter;
	
	public static Thread waitThread;
	public static Thread socketThread;
	public static Thread sendThread;
	//public static Robot robot;
	public static MySocket mySocket;
	public static int mode;
	public static void main(String[] args) throws IOException {
		bufferSend = new String();
		bufferReceive = new String();
		initGui();
			
}	
	public static void initGui(){
		//Set up Status Bar pane
		statusField = new JLabel();
		statusField.setText("Disconnect     ");
		statusColor = new JTextField(1);
		statusColor.setBackground(Color.red);
		statusColor.setEditable(false);
		statusBar = new JPanel(new BorderLayout());
		
		//JButton option = new JButton();
		
		JPanel panel = new JPanel();
		final JButton start = new JButton("Connect");
		final JComboBox<String> com = new JComboBox<String>();
		com.addItem("Wifi");
		com.addItem("Bluetooth");
		com.setSelectedIndex(0);
		final JComboBox<String> comSide = new JComboBox<String>();
		comSide.addItem("Server");
		comSide.addItem("Client");
		comSide.setSelectedIndex(0);
		final JTextField m_tcpip = new JTextField();
		final JTextField m_port = new JTextField();
		m_tcpip.setText("192.168.1.56");
		m_port.setText("1000");
		m_tcpip.setEnabled(false);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(com.getSelectedIndex() == 0){//wifi
					if(comSide.getSelectedIndex() == 0){
						m_tcpip.setEnabled(false);
						mySocket = new MySocket(Integer.parseInt(m_port.getText()));
						socketThread = new Thread(mySocket);
						socketThread.start();

					}else{
						m_tcpip.setEnabled(true);
						mySocket = new MySocket(m_tcpip.getText(),Integer.parseInt(m_port.getText()));
						socketThread = new Thread(mySocket);
						socketThread.start();

					}
				}else if(com.getSelectedIndex() == 1){//bluetooth
					System.out.println("bluetooth,"+String.valueOf(comSide.getSelectedItem()));
				}
			}
		});
		/***********************GUI************************************/
		panel.add(start,BorderLayout.WEST);
		panel.add(com,BorderLayout.CENTER);
		panel.add(comSide,BorderLayout.EAST);

		SetupPanel = new JPanel(new BorderLayout());
		SetupPanel.add(panel,BorderLayout.CENTER);
		
		//statusBar.add(statusColor,BorderLayout.WEST);
		statusBar.add(statusField,BorderLayout.WEST);
		statusBar.add(m_tcpip,BorderLayout.CENTER);
		statusBar.add(m_port,BorderLayout.EAST);
	
		
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
		phoneNum.addKeyListener(new KeyListener() {
	
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if( e.getKeyChar() == 10){
					bufferSend = phoneNum.getText();
					mySocket.send(bufferSend);
					phoneNum.setText("");
				}
			}
		});

		JPanel sendPane = new JPanel();
		sendPane.setLayout(new BorderLayout());
		sendPane.add(phoneNum, BorderLayout.NORTH);
		chatPane.add(chatTextPane,BorderLayout.SOUTH);
		chatPane.add(sendPane,BorderLayout.NORTH);

		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.add(chatPane,BorderLayout.NORTH);
		mainPane.add(SetupPanel,BorderLayout.CENTER);
		mainPane.add(statusBar,BorderLayout.SOUTH);
		
		//Set up the main frame
	
		mainFrame = new JFrame("TestSocket");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(mainFrame.getPreferredSize());
		mainFrame.setContentPane(mainPane);
		mainFrame.setLocation(200, 200);
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				mySocket.close();
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
