package com.example.camilo.myapprx;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView textView;
    private final int count=5;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnDo);
        textView = findViewById(R.id.text_view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    observe();
            }
        });
    }


    public void observe() {
        disposable.add(Observable.interval(0, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));

    }
    @Override
    public  void onDestroy(){
        super.onDestroy();
        disposable.clear();
    }


   private DisposableObserver<Long> getObserver(){
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                textView.append("OnNext: "+ aLong*count);
                textView.append("\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
   }

}
