package com.goldencarp.lingqianbao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;

import org.apache.commons.codec1.digest.DigestUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {


    private static final String LOG_TAG = "OtherFragment";
    @BindView(R.id.btn_test_doonnext)
    Button btnTestDoonnext;
    Unbinder unbinder;
    @BindView(R.id.et_raw_str)
    EditText etRawStr;
    @BindView(R.id.tv_encode_result)
    TextView tvEncodeResult;

    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_test_doonnext)
    public void onClick() {
        encodeTest();
//        unsuscribe();
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("hello");
//                e.onNext("java");
//                e.onNext("android");
//                e.onComplete();
//                Logger.e(LOG_TAG, "--------subscribe--------");
//                Logger.e(LOG_TAG, "当前线程:" + Thread.currentThread().getName());
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .doOnNext(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Logger.e(LOG_TAG, "--------doOnNext--------");
//                        Logger.e(LOG_TAG, "当前线程:" + Thread.currentThread().getName());
//                    }
//                })
//                .doAfterNext(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Logger.e(LOG_TAG, "--------doAfterNext--------");
//                        Logger.e(LOG_TAG, "当前线程:" + Thread.currentThread().getName());
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Logger.e(LOG_TAG, "--------onNext--------");
//                        Logger.e(LOG_TAG, "当前线程:" + Thread.currentThread().getName());
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    /**
     * 测试sha1加密
     */
    private void encodeTest() {
        String rawStr = etRawStr.getText().toString().trim();
        if (!TextUtils.isEmpty(rawStr)){
            byte[] sha1Bytes = DigestUtils.getSha1Digest().digest(rawStr.getBytes());
            tvEncodeResult.setText(new String(sha1Bytes));
        }
    }

}
