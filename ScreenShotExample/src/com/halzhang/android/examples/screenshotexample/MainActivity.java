
package com.halzhang.android.examples.screenshotexample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.MonthDisplayHelper;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        final View rootView = findViewById(R.id.root_view);
        rootView.setDrawingCacheEnabled(true);
        findViewById(R.id.shot).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bitmap b = rootView.getDrawingCache();//loadBitmapFromView(rootView, rootView.getWidth(), rootView.getHeight());
                try {
                    String filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separatorChar + "demo.png";
                    FileOutputStream out = new FileOutputStream(filename);
                    b.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
