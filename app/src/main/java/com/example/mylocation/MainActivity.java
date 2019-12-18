package com.example.mylocation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationManager locationManager1;
    Button btn_one;
    Button btn_two;
    TextView tv_tst_one;
    TextView tv_tst_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        tv_tst_one = findViewById(R.id.tv_tst_one);
        tv_tst_two = findViewById(R.id.tv_tst_two);
        findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOne = true;
                mGPS();
            }
        });
        findViewById(R.id.btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTwo = true;
                mNetWork();
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void mGPS() {
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isOpen) {//GPS开启
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,
                    0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        } else {
            Toast.makeText(this, "请打开GPS", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isOne = false;
    boolean isTwo = false;
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //位置信息变化时触发
            Log.d("abcd", "定位方式：" + location.getProvider());
            Log.d("abcd", "纬度：" + location.getLatitude());
            Log.d("abcd", "经度：" + location.getLongitude());
            Log.d("abcd", "海拔：" + location.getAltitude());
            Log.d("abcd", "时间：" + location.getTime());
            if (isOne) {
                tv_tst_one.setText("");
                tv_tst_one.setText(location.getLatitude() + "---" + location.getLongitude());
                Toast.makeText(MainActivity.this, "GPS" + location.getLatitude() + "---" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
            if (isTwo) {
                tv_tst_two.setText("");
                tv_tst_two.setText(location.getLatitude() + "---" + location.getLongitude());
                Toast.makeText(MainActivity.this, "NT" + location.getLatitude() + "---" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //GPS状态变化时触发
            switch (i) {
                case LocationProvider.AVAILABLE:

                    Log.d("abcd", "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("abcd", "当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("abcd", "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String s) {
            //GPS开启时触发
            Log.d("abcd", "onProviderEnabled: ");
        }

        @Override
        public void onProviderDisabled(String s) {
            //GPS禁用时触发
            Log.d("abcd", "onProviderDisabled: ");
        }
    };

    @SuppressLint("MissingPermission")
    public void mNetWork() {
        boolean isOpen = locationManager1.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isOpen) {//GPS开启
            locationManager1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000,
                    0, locationListener);
        } else {
            Toast.makeText(this, "请开启网络连接", Toast.LENGTH_SHORT).show();
        }
    }
}
