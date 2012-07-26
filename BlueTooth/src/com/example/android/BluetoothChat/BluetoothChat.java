/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.BluetoothChat;

import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothChat extends Activity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_READ_WIFI = 6;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private static final int REQUEST_TOUCH_EVENT = 4;

    // Layout Views
    public static TextView mTitle;
    public static  ListView mConversationView;
    public static EditText mOutEditText;
    public static Button mSendButton;
    public static EditText mTouchPad;
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    public static ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private static StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private static BluetoothChatService mChatService = null;
    ProgressDialog dialogProgress ;
    /******************Ngoc An***********************/
    public static String bufferReceive = new String();
    public String receiveSms = new String();
    IntentFilter intentFilter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			receiveSms = intent.getExtras().getString("sms");
			sendMessage(receiveSms);
		}
	};
	/*****************TAb********************************/
	private MainTab mMainTab = null;
	public static Button but;
	public static Button but1;
	public static ListView m_lvSearch;
	public Context mContext;
    /***********************MODE*********************/
    public static int mode;
    public static socketThread socket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");
       
        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        mContext = this;
        
        mMainTab = new MainTab(this);
        mOutEditText = new EditText(this);
        setContentView(mMainTab.createMainTABHost());
        dialogProgress = ProgressDialog.show(mContext, "","Scanning. Please wait...", true);
        dialogProgress.dismiss();
        
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mOutStringBuffer = new StringBuffer("");
        but.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mBluetoothAdapter.isEnabled()) {
		            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		           
		        }else{
		        	if(MainTab.m_StateBlutooth ==0){
			        	mChatService = new BluetoothChatService(mContext, mHandler);
			            getPariedDevice();
			            MainTab.m_StateBlutooth = 1;
			            but.setText("BlueTooth(Scan)");
			        }else if(MainTab.m_StateBlutooth == 1){
			        	 but.setText("BlueTooth");
			        	 // Indicate scanning in the title
			             setProgressBarIndeterminateVisibility(true);
			             setTitle(R.string.scanning);
			             // Register for broadcasts when a device is discovered
			             IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			             mContext.registerReceiver(mReceiver, filter);

			             // Register for broadcasts when discovery has finished
			             filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			             mContext.registerReceiver(mReceiver, filter);
			             if (mBluetoothAdapter.isDiscovering()) {
			                 mBluetoothAdapter.cancelDiscovery();
			             }
			            // dialogProgress = ProgressDialog.show(mContext, "","Scanning. Please wait...", true);	
			             mPairedDevicesArrayAdapter.clear();
			             dialogProgress.show();
			             // Request discover from BluetoothAdapter
			             mBluetoothAdapter.startDiscovery();
			        	 MainTab.m_StateBlutooth = 0;
			        }
		        }
				
			}
       	});
        but1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				LayoutInflater factory = LayoutInflater.from(mContext);
	            final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
	            alert.setView(textEntryView);
	                
				alert.setTitle("Address");
				alert.setMessage("Message");
				final EditText ip =(EditText)textEntryView.findViewById(R.id.ip);
				final EditText port =(EditText)textEntryView.findViewById(R.id.port);
				ip.setText("192.168.1.72");
				port.setText("5000");
							
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					  String m_ip = ip.getText().toString();
					   dialogProgress.show();
					  // Do something with value!
					  	BluetoothChat.socket = new socketThread(m_ip,Integer.parseInt(port.getText().toString()),mHandler);
						if(socketThread.stateSocket == 1){
							Toast.makeText(mContext, "Connected", Toast.LENGTH_SHORT).show();
							but1.setBackgroundColor(Color.BLUE);
							BluetoothChat.mode = 1;
						}else if(socketThread.stateSocket == 0){
							Toast.makeText(mContext, "Not Connect", Toast.LENGTH_SHORT).show();
							but1.setBackgroundColor(Color.RED);
						}
						dialogProgress.dismiss();
					  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				
			}
		});
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        m_lvSearch.setAdapter(mPairedDevicesArrayAdapter);
        m_lvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mBluetoothAdapter.cancelDiscovery();
	            // Get the device MAC address, which is the last 17 chars in the View
	            String info = ((TextView) arg1).getText().toString();
	            String address = info.substring(info.length() - 17);
	            connectDevice(address, true);
			}
        	
		});
         
       
        //**************************************************************/
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        //BuferString
        bufferReceive = new String();
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        registerReceiver(intentReceiver,intentFilter);
          
        	
        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
      /*  if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) {
            //	setupChat();
         }
        }*/
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
            	
              mChatService.start();
            }
        }
        //registerReceiver(intentReceiver,intentFilter);
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
    	
    	super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
        
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(intentReceiver);
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
        if(socket !=null) socket.socket = null;
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    public static void sendMessage(String message) {
        // Check that we're actually connected before trying anything
       if(mode ==0){
	    	if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
	          //  Toast.makeText(get, R.string.not_connected, Toast.LENGTH_SHORT).show();
	            return;
	        }
	
	        // Check that there's actually something to send
	        if (message.length() > 0) {
	            // Get the message bytes and tell the BluetoothChatService to write
	            byte[] send = message.getBytes();
	            mChatService.write(send);
	
	            // Reset out string buffer to zero and clear the edit text field
	            mOutStringBuffer.setLength(0);
	            mOutEditText.setText(mOutStringBuffer);
	        }
       }else if(mode ==1){
    	   if(socketThread.stateSocket !=1){
    		   return;
    	   }
    	   socket.sendMessage(message);
    	   mOutStringBuffer.setLength(0);
           mOutEditText.setText(mOutStringBuffer);
           mConversationArrayAdapter.add("Me:  " + message);
        //   mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, message).sendToTarget();
       }
    }


    // The Handler that gets information back from the BluetoothChatService
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                   // mTitle.setText(R.string.title_connected_to);
                   // mTitle.append(mConnectedDeviceName);
                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    //mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    //mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                bufferReceive = readMessage;
                String s[] = bufferReceive.split(";");
                if(s[2].equals("send")) sendMessafe(s[0],s[1]);
                else if (s[2].equals("open")) {
					openMessage(s[0], s[1]);
				}
                mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                
                break;
            case MESSAGE_READ_WIFI:
            	
            	 String s1[] = bufferReceive.split(";");
            	 if(s1.length > 2){
	                 if(s1[2].equals("send")) sendMessafe(s1[0],s1[1]);
	                 else if (s1[2].equals("open")) {
	 					openMessage(s1[0], s1[1]);
	 				 }
            	 }
                 mConversationArrayAdapter.add(mConnectedDeviceName+":  " + bufferReceive);
            	break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
               // connectDevice(data, true);
            }
            break;
        case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
               // connectDevice(data, false);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
            	mChatService = new BluetoothChatService(mContext, mHandler);
	            getPariedDevice();
               // setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
             //   finish();
            }
            dialogProgress.dismiss();
        case REQUEST_TOUCH_EVENT:
        	 Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        	
        	 break;
        }
    }

    private void connectDevice(String data, boolean secure) {
        // Get the device MAC address
        String address = data;
        //    .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BLuetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
        case R.id.secure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        case R.id.insecure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        case R.id.bluetoothMouse:
        	  EditText mainview =(EditText)findViewById(R.id.edit_text_out);
        	  
              mainview.setHeight(400);
              mainview.setWidth(320);
              
              mainview.setOnTouchListener(new OnTouchListener() {
      			
      			@Override
      			public boolean onTouch(View v, MotionEvent event) {
      				// TODO Auto-generated method stub
      			//	mainview.onInterceptTouchEvent(event);
      				//Toast.makeText(getBaseContext(), "aaa", Toast.LENGTH_SHORT).show();
      				Log.d("test","test");
      				sendMessage(event.getAction()+ ";mouse;"+event.getX()+";"+event.getY());
      				return true;
      			}
      		});
          
        	return true;
        }
        return false;
    }
    /***********************Send Message********************/
    public void sendMessafe(String address,String message){
    	 String SENT = "SMS_SENT";
         String DELIVERED = "SMS_DELIVERED";
  
         @SuppressWarnings("unused")
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
             new Intent(SENT), 0);
  
         @SuppressWarnings("unused")
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
             new Intent(DELIVERED), 0);
  
         //---when the SMS has been sent---
         registerReceiver(new BroadcastReceiver(){
             @Override
             public void onReceive(Context arg0, Intent arg1) {
                 switch (getResultCode())
                 {
                     case Activity.RESULT_OK:
                         Toast.makeText(getBaseContext(), "SMS sent", 
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                         Toast.makeText(getBaseContext(), "Generic failure", 
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case SmsManager.RESULT_ERROR_NO_SERVICE:
                         Toast.makeText(getBaseContext(), "No service", 
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case SmsManager.RESULT_ERROR_NULL_PDU:
                         Toast.makeText(getBaseContext(), "Null PDU", 
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case SmsManager.RESULT_ERROR_RADIO_OFF:
                         Toast.makeText(getBaseContext(), "Radio off", 
                                 Toast.LENGTH_SHORT).show();
                         break;
                 }
             }
         }, new IntentFilter(SENT));
  
         //---when the SMS has been delivered---
         registerReceiver(new BroadcastReceiver(){
             @Override
             public void onReceive(Context arg0, Intent arg1) {
                 switch (getResultCode())
                 {
                     case Activity.RESULT_OK:
                         Toast.makeText(getBaseContext(), "SMS delivered", 
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case Activity.RESULT_CANCELED:
                         Toast.makeText(getBaseContext(), "SMS not delivered", 
                                 Toast.LENGTH_SHORT).show();
                         break;                        
                 }
             }
         }, new IntentFilter(DELIVERED));     
    	
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(address, null, message, null, null);
    }
    public void openMessage(String address,String message){
    	  Intent i =new Intent(android.content.Intent.ACTION_VIEW);
         
         i.putExtra("address", address);
         i.putExtra("sms_body", message);
         i.setType("vnd.android-dir/mms-sms");
         startActivity(i);
    }
    public void getPariedDevice(){
    	 // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
//            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mPairedDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mPairedDevicesArrayAdapter.add(noDevices);
                }
                dialogProgress.dismiss();
            }
        }
    };
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
    		sendMessage("arrow;1");
    	}else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
    		sendMessage("arrow;2");
    	}
    	// TODO Auto-generated method stub
    	return super.onKeyDown(keyCode, event);
    }*/
    
}
