package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.Student;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsyncActivity extends BaseActivity {

    private static final String LOG_TAG = "AsyncActivity";
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private Student[] students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        ButterKnife.bind(this);
//        initData();
    }

//    private void initData() {
//        students = new Student[3];
//        List<String> courses1 = new ArrayList<>();
//        courses1.add("语文");
//        courses1.add("数学");
//        courses1.add("英语");
//        students[0] = new Student("张三", courses1);
//
//        List<String> courses2 = new ArrayList<>();
//        courses2.add("数学");
//        courses2.add("物理");
//        students[1] = new Student("李四", courses2);
//
//        List<String> courses3 = new ArrayList<>();
//        courses3.add("政治");
//        courses3.add("历史");
//        students[2] = new Student("王五", courses3);
//        Subscriber subscriber = new Subscriber() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//
//            }
//        };
//        Observer observer = new Observer() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//
//            }
//        };
//        Observable<Object> observable = Observable.create(new Observable.OnSubscribe<Object>() {
//            @Override
//            public void call(Subscriber<? super Object> subscriber) {
//                subscriber.onNext("hello");
//                subscriber.onNext("java");
//                subscriber.onNext("android");
//                subscriber.onCompleted();
//                subscriber.onError(new Throwable());
//            }
//        });
//        observable.subscribe(subscriber);
//    }

//    public void flatmapTest(View view) {
//        Observable.from(students)
//                .flatMap(new Func1<Student, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(Student student) {
//                        return Observable.from(student.getCourses());
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String courseName) {
//                        Logger.e(LOG_TAG, "courseName:" + courseName);
//                    }
//                });
//
//    }

//    public void doOnSubscribeTest(View view) {
//        Observable.just("hello", "world", "android")
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        progressBar.setVisibility(View.VISIBLE);
//                    }
//                })
//                .observeOn(Schedulers.computation())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Logger.e(LOG_TAG, "msg:" + s);
//                    }
//                });
//
//    }

//    public void multiChangeThread(View view) {
//        Observable.just("hello", "java", "android")
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return "新线程" + s;
//                    }
//                })
//                .observeOn(Schedulers.computation())
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return "计算线程:" + s;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Logger.e(LOG_TAG, "msg:" + s);
//                    }
//                });
//    }
}
