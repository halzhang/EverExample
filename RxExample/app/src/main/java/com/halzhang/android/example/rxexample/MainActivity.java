package com.halzhang.android.example.rxexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String[] CITIES = {"Budapest,hu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 多个 city 请求
         * map，flatMap 对 Observable进行变换
         */
        Observable.from(CITIES).flatMap(new Func1<String, Observable<WeatherData>>() {
            @Override
            public Observable<WeatherData> call(String s) {
                return ApiManager.getWeatherData(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherData>() {
                    @Override
                    public void call(WeatherData weatherData) {
                        Log.d(LOG_TAG, weatherData.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        /**
         * 单个 city 请求
         */
        ApiManager.getWeatherData(CITIES[0]).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherData>() {
                    @Override
                    public void call(WeatherData weatherData) {
                        Log.d(LOG_TAG, weatherData.toString());
                        ((TextView) findViewById(R.id.text)).setText(weatherData.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(LOG_TAG, throwable.getMessage(), throwable);
                    }
                });

        /**
         * view事件处理
         */
        ViewObservable.clicks(findViewById(R.id.text), false).subscribe(new Action1<OnClickEvent>() {
            @Override
            public void call(OnClickEvent onClickEvent) {

            }
        });
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
