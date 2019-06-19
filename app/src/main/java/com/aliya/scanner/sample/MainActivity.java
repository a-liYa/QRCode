package com.aliya.scanner.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aliya.scanner.QRCode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mIvQRCode;
    ImageView mIvQRCode_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvQRCode = findViewById(R.id.iv_qrcode);
        mIvQRCode_ = findViewById(R.id.iv_qrcode_);
        mIvQRCode.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qrcode:
                Bitmap qrCode = new QRCode("http://www.baidu.com", 800, 800)
                        .setMargin(0)
                        .createQRCode();
                mIvQRCode.setImageBitmap(qrCode);

                Bitmap logo  = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_a_liya);
                Log.e("TAG", "onClick: " + logo);
                mIvQRCode_.setImageBitmap(new QRCode("http://www.baidu.com", 800, 800)
                        .setMargin(1)
                        .setLogoBitmap(logo)
                        .createQRCode());
                break;
        }
    }
}
