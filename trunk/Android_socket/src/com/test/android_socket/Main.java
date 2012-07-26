package com.test.android_socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class Main extends Activity {
	public static EditText mSendText;
	public static EditText mChatList;
	public MySocket mySocket;
	public Thread threadSocket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSendText = (EditText)findViewById(R.id.SendText);
        mChatList = (EditText)findViewById(R.id.ChatList);
        mySocket = new MySocket("192.168.1.9", 1000);
        threadSocket = new Thread(mySocket);
        threadSocket.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
