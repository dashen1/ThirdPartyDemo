package com.example.thirdpartydemo.rxjava.sample1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdpartydemo.R;
import android.content.res.Resources;
import android.widget.ImageView;

public class ActivitySample1 extends AppCompatActivity {

    private static String TAG = "ActivitySample1";
    Resources resources;
    private ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        resources = ActivitySample1.this.getResources();
        imageView = findViewById(R.id.name_iv);

//        Observable.just("url")
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e(TAG,"onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e(TAG,"onNext");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG,"onError");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG,"onComplete");
//                    }
//                });
        Observable.just("xxxurl")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.e(TAG,"custom onSubscribe");
                    }

                    @Override
                    public void onNext(String item) {
                        Log.e(TAG,"custom onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"custom onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG,"custom onComplete");
                    }
                });


             Observable.just("xxxurl")
                     .map(new Function<String, Bitmap>() {
                         @Override
                         public Bitmap apply(String s) {
                             Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.two);
                             Log.e(TAG,"apply bitmap1 ");
                             return bitmap;
                         }
                     })
                     .map(new Function<Bitmap, Bitmap>() {
                         @Override
                         public Bitmap apply(Bitmap bitmap) {
                             bitmap = createWaterMark(bitmap, "Custom RxJava");
                             Log.e(TAG,"apply bitmap2 ");
                             return bitmap;
                         }
                     })
                     .subscribe(new Consumer<Bitmap>() {
                         @Override
                         public void onNext(Bitmap item) {
                             Log.e(TAG,"item = "+item);
                         }
                     });
    }


    private Bitmap createWaterMark(Bitmap bitmap, String mark) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#c5576370"));
        paint.setTextSize(150);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.drawText(mark,0,h/2,paint);
        canvas.save();
        canvas.restore();
        return bmp;
    }

}
