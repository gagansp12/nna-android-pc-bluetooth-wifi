package com.example.android.BluetoothChat;

import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.TabSpec;

public class  MainTab {
	public TabHost tabHost;
	public TabWidget tabWidget;
	public FrameLayout frameLayout;
	public Context mContext;
	public static TabSpec mSearch;
	public static int m_StateBlutooth;
	public MainTab(Context context){
		m_StateBlutooth = 0;
		this.tabHost = new TabHost(context);
		this.tabWidget = new TabWidget(context);
		this.frameLayout = new FrameLayout(context);
		this.mContext = context;
	}
	public View createMainTABHost() {
		// construct the TAB Host
    	//TabHost tabHost = new TabHost(this);
    	tabHost.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
 
        // the tabhost needs a tabwidget, that is a container for the visible tabs
//        TabWidget tabWidget = new TabWidget(this);
        tabWidget.setId(android.R.id.tabs);
        tabHost.addView(tabWidget, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)); 
 
        // the tabhost needs a frame layout for the views associated with each visible tab
     //   FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(android.R.id.tabcontent);
        frameLayout.setPadding(0, 65, 0, 0);
        tabHost.addView(frameLayout, new LinearLayout.LayoutParams(
        		LayoutParams.MATCH_PARENT, 
        		LayoutParams.MATCH_PARENT)); 
 
        // setup must be called if you are not initialising the tabhost from XML
        tabHost.setup(); 
 
        // create the tabs
        TabSpec ts;
      //  ImageView iv;
        
        mSearch = tabHost.newTabSpec("TAB_TAG_1");
        mSearch.setIndicator("Search");
        mSearch.setContent(new TabHost.TabContentFactory()
        {
            public View createTabContent(String tag)
            {
            	return createTabContent1(mContext);
             } //TAB 1 done
        });
        tabHost.addTab(mSearch);
        // -- set the image for this tab
      //  iv = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
      //  if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.bt));

 
        ts = tabHost.newTabSpec("TAB_TAG_2");
        ts.setIndicator("Control");
        ts.setContent(new TabHost.TabContentFactory(){
             public View createTabContent(String tag)
             {
            	 return createTabContent2(mContext);
             }
        });
        
        tabHost.addTab(ts);
        // -- set the image for this tab
     //   iv = (ImageView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
     //   if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.perseus));
        
        ts = tabHost.newTabSpec("TAB_TAG_3");
        ts.setIndicator("Send SMS");
        ts.setContent(new TabHost.TabContentFactory(){
             public View createTabContent(String tag)
             {
            	 return createTabContent3(mContext);
             }
        });
        tabHost.addTab(ts);
      
        // -- set the image for this tab
     //   iv = (ImageView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
     //   if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.perseus));
        ts = tabHost.newTabSpec("TAB_TAG_4");
        ts.setIndicator("Presentation");
        ts.setContent(new TabHost.TabContentFactory(){
             public View createTabContent(String tag)
             {
            	 return createTabContent4(mContext);
             }
        });
        
        tabHost.addTab(ts);
        // -- set the image for this tab
     //   iv = (ImageView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
     //   if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.perseus));
        return tabHost;
	}
	public View createTabContent1(Context context)
	{
		// Intent serverIntent = new Intent(context, DeviceListActivity.class);
        // startActivityForResult(serverIntent, 1);
		//final Context context = PerseusAndroid.this;
		// Tab container
    	LinearLayout panel = new LinearLayout(context);
  		panel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
  		panel.setOrientation(LinearLayout.VERTICAL);
  		
  		LinearLayout panelH = new LinearLayout(context);
     	panelH.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
  		panelH.setOrientation(LinearLayout.HORIZONTAL);
  		panelH.setGravity(Gravity.LEFT);
  		panelH.setGravity(Gravity.CENTER_VERTICAL);

  		BluetoothChat.but = new Button(context);
  		BluetoothChat.but.setText("BlueTooth(Paired)");
  		BluetoothChat.but.setId(Menu.FIRST + 1);
  		BluetoothChat.but.setWidth(160);
  		//but.setOnClickListener(this);
  		BluetoothChat.but.setLayoutParams(new LayoutParams(
  				LayoutParams.WRAP_CONTENT, 
  				LayoutParams.WRAP_CONTENT));
  		panelH.addView(BluetoothChat.but);
         	
  		BluetoothChat.but1 = new Button(context);
  		BluetoothChat.but1.setText("Wifi");
  		BluetoothChat.but1.setWidth(160);
  		//BluetoothChat.but.setId(Menu.FIRST + 1);
  		//but.setOnClickListener(this);
  		BluetoothChat.but1.setLayoutParams(new LayoutParams(
  				LayoutParams.WRAP_CONTENT, 
  				LayoutParams.WRAP_CONTENT));
  		panelH.addView(BluetoothChat.but1);
     	
     	panel.addView(panelH);


		BluetoothChat.m_lvSearch = new ListView( context );
	 	// clear previous results in the LV
		BluetoothChat.m_lvSearch.setAdapter(null);      
		//m_lvSearch.setOnItemClickListener((OnItemClickListener) this);
		panel.addView(BluetoothChat.m_lvSearch);
	    
		TextView lbBottom = new TextView(context);
    	lbBottom.setText("Press the button to discover Bluetooth devices");
    	lbBottom.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
     	panel.addView(lbBottom);

	    
	    return panel;
	}
	
	public View createTabContent2(Context context)
	{
	//	final Context context = PerseusAndroid.this;
		// Tab container
		LinearLayout panel = new LinearLayout(context);
  		panel.setLayoutParams(new LayoutParams(
  				LayoutParams.FILL_PARENT,
  				LayoutParams.WRAP_CONTENT,
  				1f));
  		panel.setOrientation(LinearLayout.VERTICAL);
  		//EditText
  		BluetoothChat.mTouchPad = new EditText(context);
   		BluetoothChat.mTouchPad.setWidth(320);
  		BluetoothChat.mTouchPad.setHeight(180);
  		BluetoothChat.mTouchPad.setFocusable(false);
  		BluetoothChat.mTouchPad.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//Log.d("aa","aaa");
				BluetoothChat.sendMessage("mouse;"+event.getAction()+";"+event.getX()+";"+event.getY()+";");
				return true;
			}
		});
  		panel.addView(BluetoothChat.mTouchPad);
  		
  		/*********************Button**************************/
  		LinearLayout panel1 = new LinearLayout(context);
		panel1.setOrientation(LinearLayout.HORIZONTAL);
		panel1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
  		Button bt1 = new Button(context);
  		bt1.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
  		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("mouseleft");
			}
		});
  		panel1.addView(bt1);
  		Button bt2 = new Button(context);
  		bt2.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
		bt2.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("mouseright");
			}
		});
  		panel1.addView(bt2);
  		panel.addView(panel1);
  		return panel;
	}
	public View createTabContent3(Context context){
		LinearLayout panel = new LinearLayout(context);
		
		panel.setOrientation(LinearLayout.VERTICAL);
		panel.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			
		/***********************Conversation***************************/
		BluetoothChat.mConversationView = new ListView( context );
		BluetoothChat.mConversationView.setAdapter(BluetoothChat.mConversationArrayAdapter);    
		BluetoothChat.mConversationView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
		BluetoothChat.mConversationView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		BluetoothChat.mConversationView.setStackFromBottom(true);
		panel.addView(BluetoothChat.mConversationView);
		/***********************Conversation***************************/
		/***********************Send***************************/
		/***********************Edit***************************/
		LinearLayout panel1 = new LinearLayout(context);
		panel1.setOrientation(LinearLayout.HORIZONTAL);
		panel1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		BluetoothChat.mOutEditText = new EditText(context);
		BluetoothChat.mOutEditText.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT,
								1f));
		BluetoothChat.mOutEditText.setWidth(240);
		BluetoothChat.mOutEditText.setGravity(Gravity.BOTTOM);
		panel1.addView(BluetoothChat.mOutEditText);
		/***********************Button***************************/
		BluetoothChat.mSendButton = new Button(context);
		BluetoothChat.mSendButton.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT,
								1f));
		BluetoothChat.mSendButton.setText("Send");
		
		BluetoothChat.mSendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage(BluetoothChat.mOutEditText.getText().toString());
			}
		});
		panel1.addView(BluetoothChat.mSendButton);
		
		
		panel.addView(panel1);
		/***********************Conversation***************************/
		return panel;
	}
	public View createTabContent4(Context context)
	{
	//	final Context context = PerseusAndroid.this;
		// Tab container
		LinearLayout panel = new LinearLayout(context);
  		panel.setLayoutParams(new LayoutParams(
  				LayoutParams.FILL_PARENT,
  				LayoutParams.WRAP_CONTENT,
  				1f));
  		panel.setOrientation(LinearLayout.HORIZONTAL);
  		  		
  		/*********************Button**************************/
  	
  		Button bt1 = new Button(context);
  		bt1.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
  		bt1.setText("Next");
  		bt1.setHeight(200);
  		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("presentationNext");
			}
		});
  		panel.addView(bt1);
  		Button bt2 = new Button(context);
  		bt2.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
  		bt2.setText("Previous");
  		bt2.setHeight(200);
		bt2.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("presentationPrevious");
			}
		});
  		panel.addView(bt2);
  		
  		LinearLayout panel1 = new LinearLayout(context);
  		panel1.setLayoutParams(new LayoutParams(
  				LayoutParams.FILL_PARENT,
  				LayoutParams.WRAP_CONTENT,
  				1f));
  		panel1.setOrientation(LinearLayout.HORIZONTAL);
  		
  		Button bt3 = new Button(context);
  		bt3.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
  		bt3.setText("Start");
  		bt3.setHeight(100);
  		bt3.setLongClickable(true);
  		bt3.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("presentationStartCurrent");
				return false;
			}
		});
  		bt3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("presentationStart");
			}
		});
  		panel1.addView(bt3);
  		
  		Button bt4 = new Button(context);
  		bt4.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1f));
  		bt4.setText("Exit");
  		bt4.setHeight(100);
  		bt4.setLongClickable(true);
  		bt4.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("presentationExitAll");
				return false;
			}
		});
  		bt4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BluetoothChat.sendMessage("presentationExit");
			}
		});
  		panel1.addView(bt4);
  		
  		LinearLayout panel2 = new LinearLayout(context);
  		panel2.setLayoutParams(new LayoutParams(
  				LayoutParams.FILL_PARENT,
  				LayoutParams.WRAP_CONTENT,
  				1f));
  		panel2.setOrientation(LinearLayout.VERTICAL);
  		
  		panel2.addView(panel);
  		panel2.addView(panel1);
  		
  		return panel2;
	}
}
