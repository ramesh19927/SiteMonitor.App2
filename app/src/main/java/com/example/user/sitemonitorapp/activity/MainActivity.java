package com.example.user.sitemonitorapp.activity;
import android.app.AlertDialog;
import android.app.Dialog;
import  android.support.v7.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.database.CursorIndexOutOfBoundsException;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import android.widget.EditText;

import android.widget.LinearLayout;

import android.widget.Toast;

/*
import com.bosch.cbs.device.BvipDeviceUser;
import com.bosch.cbs.device.IDeviceInfo;
import com.bosch.cbs.device.protocol.http.IHttpConnectionProviderFactory;
import com.bosch.cbs.device.protocol.tcp.ITcpConnectionProvider;
import com.bosch.cbs.tool.bvipconfigurator.model.DetectedDevice;
import com.bosch.cbs.tool.bvipconfigurator.model.DetectedDeviceCollection;
import com.bosch.cbs.tool.bvipconfigurator.rcp.AutodetectReply;
import com.bosch.cbs.tool.bvipconfigurator.rcp.IRcpDeviceDetector;
import com.bosch.cbs.tool.bvipconfigurator.rcp.RcpMultiDeviceDetector;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;


*/
import com.example.user.sitemonitorapp.R;




import android.content.Intent;

import android.net.Uri;

import android.text.Editable;
import android.text.TextWatcher;

import android.widget.AdapterView;

import android.widget.GridView;

import android.widget.AdapterView.OnItemClickListener;

import com.example.user.sitemonitorapp.adapter.CustomListViewAdapter;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {
   static CustomListViewAdapter adapter;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private GridView lvItem;
    static ArrayList<String> itemArrey= new ArrayList<String>();
    static EditText inputSearch;
    static String[] descriptions;
    static RowItem item;
    static Integer[] images;
    static List<RowItem> rowItems;
    static   DataBaseHandler db;
    static  EditText input;
    static Data[] datas;
    static String[] ramesh;
    static String[] itemsopen= new String[8];
    static String[] itemsopensettings= new String[8];
/*    static BvipDeviceUser user;
    static Timer timer=new Timer() {

        public Set<Timeout> stop() {
            // TODO Auto-generated method stub
            return null;
        }

        public Timeout newTimeout(TimerTask arg0, long arg1, TimeUnit arg2) {
            // TODO Auto-generated method stub
            return null;
        }
    };
    static String password;
    static int timeoutInSeconds ;
    static int retryCount;
    static Set<String> asda;
    static ITcpConnectionProvider asdasd;
    static IHttpConnectionProviderFactory dasdasd;
    static AutodetectReply reply;
    static IDeviceInfo info;
    static DetectedDevice device;
*/
private String path;
    //private HashMap<String, String> options;


    static List<Data> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
         getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(!OpenCVLoader.initDebug()){
            Log.i("chudutammudu","nothing");
        }else{
            Log.i("chudutammudu","yes!!");

        }
        rowItems = new ArrayList<RowItem>();
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);



       MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();


/*
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_main);

        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
        path = "rtsp://192.168.1.6";
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });


*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.menu_main, menu);
       Toast.makeText(getApplicationContext(), "WELCOME TO THE STORE......NO ADS FOR NOW!!!", Toast.LENGTH_LONG).show();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            opensettings();
        }
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
             getSupportActionBar().setTitle(title);
        }
    }
   public String ipnew(){
        String ip=null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
       return ip;
    }
    private static final int NB_THREADS = 10;
    public void doScan() {
String str;
        Log.i("venkat", "Start scanning");
        Log.i("venkat",ipnew());
        String ipn=ipnew();
        if(ipn.endsWith(".0")||ipn.endsWith(".1")||ipn.endsWith(".2")||ipn.endsWith(".3")||ipn.endsWith(".4")||ipn.endsWith(".5")||ipn.endsWith(".6")||ipn.endsWith(".7")||ipn.endsWith(".8")||ipn.endsWith(".9")){
 str=ipn.substring(0,ipn.length()-1);
        }else{
str=ipn.substring(0,ipn.length()-2);
        }
        ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);
        for(int dest=0; dest<25; dest++) {
            String host = str + dest;
            executor.execute(pingRunnable(host));
            Log.i("venkat-ramesh-paina", host);

        }
        Log.i("venkat", "Waiting for executor to terminate...");
       executor.shutdown();
        try { executor.awaitTermination(6 * 1000, TimeUnit.MILLISECONDS); } catch (InterruptedException ignored) { }
        Log.i("venkat", "Scan finished");
      }
    private Runnable pingRunnable(final String host) {
        return new Runnable() {
            public void run() {
                try {
                    if (InetAddress.getByName(host).isReachable(1000)){
                        InetAddress.getByName(host);
                       Log.i("venkat-ramesh",host);
                        itemArrey.add(host);
                    }
                } catch (UnknownHostException e) {
                } catch (IOException e) {
                }
            }
        };
    }

public void open(){
    Dialog dialog;
    final ArrayList<Integer> itemsSelected = new ArrayList<Integer>();

    for(int i=0;i<itemsopen.length;i++){
        itemsopen[i]=itemArrey.get(i);
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Select the Entrance Camera : ");
    builder.setMultiChoiceItems(itemsopen, null,
            new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int selectedItemId,
                                    boolean isSelected) {
                    if (isSelected) {
                        itemsSelected.add(selectedItemId);
                    } else if (itemsSelected.contains(selectedItemId)) {
                        itemsSelected.remove(Integer.valueOf(selectedItemId));
                    }
                }
            })
            .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                    db.updateContact(new Data(1, "first", itemArrey.get(itemsSelected.get(0))));
                    Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("rtsp://" + itemArrey.get(Integer.valueOf(itemsSelected.get(0).toString()))));
                    Log.i("edasdasd","rtsp://"+itemsSelected.get(0));
                        startActivity(viewIntent);


                 }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
    dialog = builder.create();
    dialog.show();
}
    public void opensettings(){
        Dialog dialog;
       for(int i=0;i<itemsopensettings.length;i++){
           itemsopensettings[i]=itemArrey.get(i);
       }
        final ArrayList<Integer> itemsSelected1 = new ArrayList<Integer>();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);


        builder.setTitle("Please Enter the Camera name for an appropriate Ip ");
        builder.setMultiChoiceItems(itemsopensettings, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected1.add(selectedItemId);

                        } else if (itemsSelected1.contains(selectedItemId)) {
                            itemsSelected1.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                   db.updateContact(new Data(1,input.getText().toString(),itemArrey.get(itemsSelected1.get(0))));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        dialog = builder.create();
        dialog.show();
    }




    class MyAsyncTask extends AsyncTask<Void, Integer, ArrayList<String>> {

        boolean running;
        ProgressDialog progressDialog;

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            int i = 5;
            while(running){
                try {
doScan();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i-- == 0){
                    running = false;
                }
                publishProgress(i);
            }

            return itemArrey;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setMessage(String.valueOf(values[0]));

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Scanning for Cameras",
                    "Please Wait!");

            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    running = false;
                }
            });
   }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            /////////////////////////////////////////////////////////////////////////////////////////////////////////



            try {

                for (int i = 0; i < itemArrey.size(); i++) {

                       item = new RowItem(R.drawable.news,itemArrey.get(i),"");
                       rowItems.add(item);


                     }

/*
              db = new DataBaseHandler(getApplicationContext());
                     data = db.getAllContacts();

                if (!db.getContact(1).getPhoneNumber().equals("")) {
                //    db.getContact(1).getPhoneNumber()
                    Intent myIntent = new Intent(MainActivity.this, Video.class);
                    myIntent.putExtra("key","192.168.1.22");
                            MainActivity.this.startActivity(myIntent);

                } else {
                    Intent myIntent = new Intent(MainActivity.this, Video.class);
                    myIntent.putExtra("key","192.168.1.22");
                    MainActivity.this.startActivity(myIntent);
                }
               
                for (int i = 0; i < itemArrey.size(); i++) {
                    item = new RowItem(R.drawable.ic_profile,itemArrey.get(i),"");
                    rowItems.add(item);
                }
*/
/*      data= db.getAllContacts();
       for(int i=0;i<itemArrey.size();i++) {
           db.addContact(new Data(i,"detected "+i,itemArrey.get(i) ));
       }*/
                inputSearch = (EditText) findViewById(R.id.inputSearch);
                lvItem = (GridView) findViewById(R.id.gridview);
                inputSearch.addTextChangedListener(new TextWatcher() {
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        MainActivity.this.adapter.getFilter().filter(cs);
                    }

                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub
                    }

                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                lvItem.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        RowItem value = (adapter.getItem(position));
                            String de = value.getTitle();

                            /*Uri myuri = Uri.parse(rtsp);
                            Log.i("venkat", rtsp);
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            myuri);
                            startActivity(viewIntent);
                            */

                             Intent myIntent = new Intent(MainActivity.this, Video.class);
                             myIntent.putExtra("key",de); //Optional parameters
                             MainActivity.this.startActivity(myIntent);





                    }
                });


                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) lvItem.getLayoutParams();
                linearParams.width = 400 * itemArrey.size();
                lvItem.setLayoutParams(linearParams);
                lvItem.setColumnWidth(300);
                adapter = new CustomListViewAdapter(getApplicationContext(),
                        R.layout.list_item, rowItems);
                lvItem.setAdapter(adapter);
            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }

    }


}