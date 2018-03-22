package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.goldencarp.lingqianbao.R;

import org.reactivestreams.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxjavaActivity extends BaseActivity {

    private static final String LOG_TAG = "RxjavaActivity";
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.button)
    Button button;
    private Subscriber<String> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);

        //控制事件产生和消费的线程
//        Observable.just(1, 2, 3, 4)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        Logger.e(LOG_TAG, "test==" + integer);
//                    }
//                }
//                );
    }

//    private void simpleUse() {
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Logger.e(LOG_TAG, "--------onCompleted-------");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.e(LOG_TAG, "--------onError-------");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Logger.e(LOG_TAG, "--------onNext-------" + s);
//            }
//        };
//        subscriber = new Subscriber<String>() {
//
//
//            @Override
//            public void onCompleted() {
//                Logger.e(LOG_TAG, "-----------subonCompleted-----------");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.e(LOG_TAG, "-----------subonError-----------");
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Logger.e(LOG_TAG, "-----------subonNext-----------" + s);
//
//            }
//        };
//
//        //Observable第一种构造方法create
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("hello");
//                subscriber.onNext("rxjava");
//                subscriber.onNext("android");
//                subscriber.onCompleted();
//            }
//        });
//        //Observable第二种构造方法just
//        Observable<String> observable1 = Observable.just("hello1", "rxjava1", "android1");
//        //Observable第三种构造方法from
//        String[] words = new String[]{"hello2", "rxjava2", "android2"};
//        Observable<String> observable2 = Observable.from(words);
//
//        Action0 onCompletedAction = new Action0() {
//
//            @Override
//            public void call() {
//                Logger.e(LOG_TAG, "----------onCompletedAction---------");
//            }
//        };
//
//        Action1 onNextAction = new Action1<String>() {
//            @Override
//            public void call(String o) {
//                Logger.e(LOG_TAG, "---------onNextAction---------" + o);
//            }
//        };
//
//        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                Logger.e(LOG_TAG, "-----------onErrorAction-----------" + throwable.getMessage());
//            }
//        };
//
//        observable1.subscribe(onNextAction);
//        observable1.subscribe(onNextAction, onErrorAction);
//        observable1.subscribe(onNextAction, onErrorAction, onCompletedAction);
//    }

    @Override
    protected void onStop() {
        super.onStop();
//        subscriber.unsubscribe();
    }

    @OnClick(R.id.button)
    public void onClick() {
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = LQBApp.getApp().getResources().getDrawable(R.mipmap.ic_launcher);
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io())//事件在io线程产生
//                .observeOn(AndroidSchedulers.mainThread())//事件在主线程消费
//                .subscribe(new Observer<Drawable>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.e(LOG_TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.e(LOG_TAG, "onError,msg:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Drawable drawable) {
//                        imageView2.setImageDrawable(drawable);
//                    }
//                });
    }
}
