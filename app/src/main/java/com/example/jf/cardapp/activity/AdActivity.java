package com.example.jf.cardapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jf.cardapp.R;

public class AdActivity extends AppCompatActivity {
    private Button bt;
    private boolean f=true;
    private ImageView background;
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    int i= (int) message.obj;
                    bt.setText(i+"");
                    if(i==0){
                        Animation animation = AnimationUtils.loadAnimation(AdActivity.this, R.anim.splash);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Intent intent=new Intent(AdActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        background.startAnimation(animation);

                    }
                    break;
            }
            return false;

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        bt= (Button) findViewById(R.id.button);
        background= (ImageView) findViewById(R.id.iv_welcome_img);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=false;
                Message msg=new Message();
                msg.what=0;
                msg.obj=0;
                handler.sendMessage(msg);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=5;i>=0;i--){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(f==true){
                        Message msg=new Message();
                        msg.what=0;
                        msg.obj=i;
                        handler.sendMessage(msg);
                    }

                }
            }
        }).start();
    }
}
