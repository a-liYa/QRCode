package com.aliya.scanner.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aliya.scanner.client.CaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_qrcode).setOnClickListener(this);
        findViewById(R.id.tv_qrcode_create).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qrcode:
                startActivity(new Intent(this, CaptureActivity.class));
                break;
            case R.id.tv_qrcode_create:
//                Intent intent = new Intent(this, BookmarkPickerActivity.class);
//                intent.setAction(Intent.ACTION_SEND);
//                startActivity(intent);
                break;
        }
    }
}
