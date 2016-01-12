package com.halzhang.android.examples.permissionexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE = 1;
    private static final String[] STORAGE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    private Button mStorageBtn;
    private Button mPhoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mStorageBtn = (Button) findViewById(R.id.btn_storage);
        mStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeFile();
            }
        });
        mPhoneBtn = (Button) findViewById(R.id.btn_phone);

    }

    private void writeFile() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权，请求权限
            requestStoragePermission();
        } else {
            File file = new File(Environment.getExternalStorageDirectory(), "temp.txt");
            try {
                FileUtils.writeStringToFile(file, "Hello Runtime Permission");
                Log.i("PermissionExample", "Write file success!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(mStorageBtn, "请求存储权限", Snackbar.LENGTH_LONG).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this, STORAGE_PERMISSION, REQUEST_STORAGE);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            if (Utils.verifyPermission(grantResults)) {
                Snackbar.make(mStorageBtn, "授权成功", Snackbar.LENGTH_LONG).show();
                writeFile();
            } else {
                Snackbar.make(mStorageBtn, "授权失败", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
