package com.goldencarp.lingqianbao.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.dao.Meizi;
import com.goldencarp.lingqianbao.model.dao.gen.DaoSession;
import com.goldencarp.lingqianbao.model.dao.gen.MeiziDao;
import com.goldencarp.lingqianbao.view.LQBApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CacheCleanActivity extends BaseActivity {

    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.btn_write_files)
    Button btnWriteFiles;
    @BindView(R.id.btn_clear_files)
    Button btnClearFiles;
    @BindView(R.id.btn_write_sp)
    Button btnWriteSp;
    @BindView(R.id.btn_clear_sp)
    Button btnClearSp;
    @BindView(R.id.btn_write_db)
    Button btnWriteDb;
    @BindView(R.id.btn_clear_db)
    Button btnClearDb;
    @BindView(R.id.et_data)
    EditText etData;
    @BindView(R.id.btn_read_files)
    Button btnReadFiles;
    @BindView(R.id.btn_read_sp)
    Button btnReadSp;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_write_multi_db)
    Button btnWriteMultiDb;
    @BindView(R.id.btn_update_db)
    Button btnUpdateDb;
    @BindView(R.id.btn_update_multi_db)
    Button btnUpdateMultiDb;
    @BindView(R.id.btn_query_db)
    Button btnQueryDb;
    @BindView(R.id.btn_query_all_db)
    Button btnQueryAllDb;
    private BufferedWriter bw;
    private BufferedReader bis;
    private DaoSession daoSession;
    private MeiziDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clean);
        ButterKnife.bind(this);
        daoSession = LQBApp.getDaoInstant();
        dao = daoSession.getMeiziDao();
    }

    @OnClick({R.id.btn_write_files, R.id.btn_clear_files, R.id.btn_write_sp, R.id.btn_clear_sp, R.id.btn_write_db, R.id.btn_clear_db, R.id.btn_read_files, R.id.btn_read_sp, R.id.btn_write_multi_db, R.id.btn_update_db, R.id.btn_update_multi_db, R.id.btn_query_db, R.id.btn_query_all_db})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_write_files:
                try {
                    FileOutputStream fos = openFileOutput("cacheCleanTest", Context.MODE_APPEND);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    Editable data = etData.getText();
                    String dataStr = data.toString().trim();
                    if (TextUtils.isEmpty(dataStr)) {
                        Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
                    } else {
                        bw.write(dataStr);
                        Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();
                        etData.getText().clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.btn_read_files:

                try {
                    StringBuilder sb = new StringBuilder();
                    FileInputStream fis = openFileInput("cacheCleanTest");
                    bis = new BufferedReader(new InputStreamReader(fis));
                    String line = null;
                    while (!TextUtils.isEmpty((line = bis.readLine()))) {
                        sb.append(line);
                    }
                    tvResult.setText(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.btn_clear_files:
                try {
                    FileOutputStream fos = openFileOutput("cacheCleanTest", Context.MODE_PRIVATE);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write("");
                    Toast.makeText(this, "数据清除成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.btn_write_sp:
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(LQBApp.getApp()).edit();
                edit.putString("name", "zhangsan").apply();
                edit.putInt("age", 26).apply();
                edit.putBoolean("isMarried", true).apply();
                break;
            case R.id.btn_read_sp:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LQBApp.getApp());
                String name = sp.getString("name", null);
                int age = sp.getInt("age", 0);
                boolean isMarried = sp.getBoolean("isMarried", false);
                tvResult.setText(null);
                tvResult.setText(name + "--" + age + "--" + isMarried);
                break;
            case R.id.btn_clear_sp:
                PreferenceManager.getDefaultSharedPreferences(LQBApp.getApp()).edit().clear().apply();
                break;
            case R.id.btn_write_db:
                long index = dao.insert(new Meizi(1l, "中国", "www.baidu.com"));
                Toast.makeText(this, "数据插入在第"+index+"位置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_clear_db:
                dao.deleteAll();
                break;
            case R.id.btn_write_multi_db:
                ArrayList<Meizi> list = new ArrayList<>();
                //写入多条数据
                for (int i = 0; i < 10; i++) {
                    list.add(new Meizi(new Long(i+2), "国家" + i, "www.hello" + i + ".com"));
                }
                dao.insertInTx(list);
                break;
            case R.id.btn_update_db:
                Meizi meizi1 = new Meizi(1l, "美国", "www.baidu.com");
                dao.update(meizi1);
                break;
            case R.id.btn_update_multi_db:
                ArrayList<Meizi> list1 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list1.add(new Meizi(new Long(i), "星球" + i, "www.world" + i + ".com"));
                }
                dao.updateInTx(list1);
                break;
            case R.id.btn_query_db:
                //查询一条
                List<Meizi> list2 = dao.queryBuilder().where(MeiziDao.Properties._id.eq(3)).list();
                StringBuilder sb = new StringBuilder();
                for (Meizi meizi : list2) {
                    sb.append(meizi.toString());
                }
                tvResult.setText(sb.toString());
                break;
            case R.id.btn_query_all_db:
                //查询一条
                List<Meizi> list3 = dao.loadAll();
                StringBuilder sb1 = new StringBuilder();
                for (Meizi meizi : list3) {
                    sb1.append(meizi.toString());
                }
                tvResult.setText(sb1.toString());
                break;

        }
    }

}
