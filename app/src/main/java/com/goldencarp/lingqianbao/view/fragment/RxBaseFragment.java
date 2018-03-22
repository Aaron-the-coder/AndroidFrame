package com.goldencarp.lingqianbao.view.fragment;


import android.support.v4.app.Fragment;

import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * hello
 */
public class RxBaseFragment extends Fragment {

    protected Disposable disposable;

    public RxBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsuscribe();
    }

    protected void unsuscribe() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
