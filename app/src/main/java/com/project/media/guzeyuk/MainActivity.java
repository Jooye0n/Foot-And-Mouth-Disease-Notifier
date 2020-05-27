package com.project.media.guzeyuk;


import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.location.Geocoder;
import android.view.View;
import java.util.ArrayList;
import java.lang.Object;
import java.io.IOException;
import android.location.Address;
import java.util.List;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText meatcode = (EditText) findViewById(R.id.barcode);
        final EditText add1 = (EditText) findViewById(R.id.t1);
        final EditText add2 = (EditText) findViewById(R.id.t2);
        final TextView distance = (TextView) findViewById(R.id.t3);
        final TextView res = (TextView) findViewById(R.id.result);
        Button b1 = (Button) findViewById(R.id.change); //이력번호 버튼
        Button b2 = (Button) findViewById(R.id.button); //위도 경도 변환 버튼

        final Geocoder geocoder = new Geocoder(this);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list_add = null;
                try {
                    double d1 = Double.parseDouble(add1.getText().toString());
                    double d2 = Double.parseDouble(add2.getText().toString());

                    list_add = geocoder.getFromLocation(
                            d1, // 위도
                            d2, // 경도
                            10); // 얻어올 값의 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list_add != null) {
                    if (list_add.size()==0) {
                        res.setText("해당되는 주소 정보는 없습니다");
                    } else {
                        res.setText(list_add.get(0).toString());
                    }
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> resulttext;
                List<Address> list_dist = null;
                try{
                    System.out.println(meatcode.getText().toString());
                    String meatcodetext = meatcode.getText().toString();
                    resulttext = new Crawling().execute(meatcodetext).get();
                    res.setText(resulttext.get(1));
                    distance.setText(resulttext.get(0));

                    String str = res.getText().toString();
                    list_dist = geocoder.getFromLocationName(str,str.length()); //지역이름, 읽을개수
                    double mLat = list_dist.get(0).getLatitude();
                    double mLng = list_dist.get(0).getLongitude();

                    Location loc1 = new Location("p1");
                    loc1.setLatitude(mLat);
                    loc1.setLongitude(mLng);
                    Location loc2 = new Location("p2");
                    loc2.setLatitude(Double.parseDouble(add1.getText().toString()));
                    loc2.setLongitude(Double.parseDouble(add2.getText().toString()));

                    if(list_dist != null){
                        if(list_dist.size() == 0) distance.setText("없는 주소입니다.");
                        else distance.setText(String.valueOf(loc1.distanceTo(loc2) + 'm'));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
