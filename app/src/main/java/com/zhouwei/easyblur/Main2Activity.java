package com.zhouwei.easyblur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouwei.blurlibrary.EasyBlur;

public class Main2Activity extends Activity {
    private ImageView iv_mg;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = (TextView) findViewById(R.id.tv);
        iv_mg = (ImageView) findViewById(R.id.iv_mg);
        iv_mg.setImageResource(R.mipmap.cover);

        iv_mg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                iv_mg.getViewTreeObserver().removeOnPreDrawListener(this);
                iv_mg.buildDrawingCache();
                Bitmap bmp = iv_mg.getDrawingCache();
                Bitmap overlay = Bitmap.createBitmap((int) (tv.getMeasuredWidth()),
                        (int) (tv.getMeasuredHeight()), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(overlay);

                canvas.translate(-tv.getLeft(), -tv.getTop());
                canvas.drawBitmap(bmp, 0, 0, null);

                Bitmap finalBitmap = EasyBlur.with(Main2Activity.this)
                        .bitmap(overlay) //要模糊的图片
                        .radius(10)//模糊半径
//                        .scale(4)//指定模糊前缩小的倍数
                        .policy(EasyBlur.BlurPolicy.FAST_BLUR)//使用fastBlur
                        .blur();

                tv.setBackground(new BitmapDrawable(
                        getResources(), finalBitmap));
                return true;
            }
        });

    }
}
