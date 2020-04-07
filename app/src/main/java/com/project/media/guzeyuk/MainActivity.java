package com.project.media.guzeyuk;


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
        final TextView res = (TextView) findViewById(R.id.result);
        Button b1 = (Button) findViewById(R.id.change);

        Geocoder geocoder = new Geocoder(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> resulttext;
                try{
                    System.out.println(meatcode.getText().toString());
                    String meatcodetext = meatcode.getText().toString();
                    resulttext = new Crawling().execute(meatcodetext).get();
                    res.setText(resulttext.get(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
