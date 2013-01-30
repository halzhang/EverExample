
package com.example.wifidirectexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Iterator;

public class WiFiDirectActivity extends Activity implements OnClickListener {
    
    private static final String LOG_TAG = WiFiDirectActivity.class.getSimpleName();
    
    private WifiP2pManager mWifiP2pManager;
    private Channel mChannel;

    private WiFiDirectBroadcastReceiver mWiFiDirectBroadcastReceiver;

    private IntentFilter mIntentFilter;
    
    private Button mDiscoverPeersBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mWifiP2pManager = (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(this, getMainLooper(), new ChannelListener() {
            
            @Override
            public void onChannelDisconnected() {
                Log.i(LOG_TAG, "Channel disconnected!");
            }
        });
        mWiFiDirectBroadcastReceiver = new WiFiDirectBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
        mDiscoverPeersBtn = (Button)findViewById(R.id.discoverPeers);
        mDiscoverPeersBtn.setEnabled(false);
        mDiscoverPeersBtn.setOnClickListener(this);
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mWiFiDirectBroadcastReceiver, mIntentFilter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mWiFiDirectBroadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discoverPeers:
                mWifiP2pManager.discoverPeers(mChannel, new ActionListener() {
                    
                    @Override
                    public void onSuccess() {
                        Log.i(LOG_TAG, "DiscoverPeers Success!");
                        mWifiP2pManager.requestPeers(mChannel, mPeerListListener);
                    }
                    
                    @Override
                    public void onFailure(int reason) {
                        Log.w(LOG_TAG, "DiscoverPeers failure reason: "+reason);
                    }
                });
                break;

            default:
                break;
        }
        
    }
    
    private PeerListListener mPeerListListener = new PeerListListener() {

        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            Iterator<WifiP2pDevice> iterator = peers.getDeviceList().iterator();
            WifiP2pDevice device;
            while (iterator.hasNext()) {
                device = iterator.next();
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                mWifiP2pManager.connect(mChannel, config, new ActionListener() {
                    
                    @Override
                    public void onSuccess() {
                        Log.w(LOG_TAG, "Connect success!");
                    }
                    
                    @Override
                    public void onFailure(int reason) {
                        Log.w(LOG_TAG, "Connect failure!");                        
                    }
                });
                break;
            }
        }
    };
    
    private class WiFiDirectBroadcastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                // Check to see if Wi-Fi is enabled and notify appropriate activity
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    // Wifi Direct is enabled
                    Log.w(LOG_TAG, "Wifi Direct is enabled!");
                    mDiscoverPeersBtn.setEnabled(true);
                } else {
                    Log.w(LOG_TAG, " Wi-Fi Direct is not enabled!");
                    // Wi-Fi Direct is not enabled
                }
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                // Call WifiP2pManager.requestPeers() to get a list of current peers

                if (mWifiP2pManager != null) {
                    Log.i(LOG_TAG, "WifiP2pManager requestPeers!");
                    mWifiP2pManager.requestPeers(mChannel, mPeerListListener);
                }

            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
                    .equals(action)) {
                // Respond to new connection or disconnections
            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
                    .equals(action)) {
                // Respond to this device's wifi state changing
            }
        }

    }

}
