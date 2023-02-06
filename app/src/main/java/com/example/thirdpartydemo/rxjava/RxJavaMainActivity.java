package com.example.thirdpartydemo.rxjava;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thirdpartydemo.R;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.POST;

public class RxJavaMainActivity extends AppCompatActivity {

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            Bitmap bitmap = (Bitmap) msg.obj;
//            imageView.setImageBitmap(bitmap);
//        }
//    };
    private ImageView imageView;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rxjava);

        imageView = findViewById(R.id.imageView);

        resources = RxJavaMainActivity.this.getResources();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.two);
//                bitmap = createWaterMark(bitmap, "RxJava2.0");
//                Message message = Message.obtain();
//                message.obj = bitmap;
//                handler.sendMessage(message);
//            }
//        }).start();

        Observable.just("file_path")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.two);
                        return bitmap;
                    }
                })
                .map(o -> {
                    Bitmap bitmap = createWaterMark(o, "Rxjava map");

                    return bitmap;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        imageView.setImageBitmap(bitmap);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}