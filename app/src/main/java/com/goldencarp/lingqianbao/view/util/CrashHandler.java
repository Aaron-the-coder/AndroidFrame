package com.goldencarp.lingqianbao.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.goldencarp.lingqianbao.model.Constants;
import com.goldencarp.lingqianbao.model.net.HttpUtil;
import com.goldencarp.lingqianbao.view.LQBApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeSet;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类 来接管程序,并记录 发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * Debug Log tag
     */
    public static final String TAG = "LQBCrashHandler";

    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     * */
//    public static final boolean DEBUG = !Constants.RTM_ENV;

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;

    /**
     * 程序的Context对象
     */
    private static Context mContext;

    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    StringBuilder stackBuilder = new StringBuilder();

    private Properties mDeviceCrashInfo = new Properties();

    private static final String USERID = "userID";

    private static final String USERKEY = "userKEY";

    private static final String VERSION_NAME = "versionName";

    private static final String VERSION_CODE = "versionCode";

    private static final String STACK_TRACE = "{UploadLog}";

    public static final String CRASH_FILE_NAME = "CRASH_FILE_NAME";

    public static final String CRASH_FILE_COMMIT_TIME = "CRASH_FILE_COMMIT_TIME";

    public static final String CRASH_FILE_COMMIT_COUT = "CRASH_FILE_COMMIT_COUT";

    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    private static boolean sendOK = false;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        sendOK = false;
        try {
//            if (DEBUG && mDefaultHandler != null)
//            {
//                mDefaultHandler.uncaughtException(thread, ex);
//                return;
//            }
            if (!handleException(ex) && mDefaultHandler != null) {
                // 如果用户没有处理则让系统默认的异常处理器来处理
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                // Sleep一会后结束程序
                Thread.sleep(500);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        } catch (Exception newEx) {
            newEx.printStackTrace();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信息
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
////                Toast.makeText(mContext, "ERROR in LQB!" + msg,
////                        Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, "抱歉,零钱包发生错误即将退出", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//
//        }.start();

        mDeviceCrashInfo.clear();
        // 收集设备信息
        collectCrashDeviceInfo(mContext);
        // 保存错误报告文件
        String crashFileName = saveCrashInfoToFile(mContext, ex);
        // 发送错误报告到服务器
        if (crashFileName != null) {
            // 将文件名存到本地
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            preferences.edit().putString(CRASH_FILE_NAME, crashFileName).commit();
            sendCrashReportsToServer(mContext, crashFileName);
        }
        return true;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Context ctx, Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = info.toString();
        printWriter.close();
//        mDeviceCrashInfo.put(STACK_TRACE, result);
        //将崩溃log日志打印到请求日志中（因为崩溃日志在服务器后台不能生成.log文件。后台人员太懒了，不愿改。）
        Logger.e(STACK_TRACE, result == null ? "" : result);
        String errorClass = "";
        if (result != null) {
            int n = result.indexOf(":");
            if (n >= 0)
                errorClass = result.substring(0, n);
        }
        //不再保存相同类型的日志
        if (errorClass.length() > 0) {
            String[] crFiles = getCrashReportFiles(ctx);
            if (crFiles != null && crFiles.length > 0) {
                for (String fileName : crFiles) {
                    File cr = new File(ctx.getFilesDir(), fileName);
                    String line = readFirstLineOfFile(cr);
                    if (line.contains(errorClass))
                        return null;
                }
            }
        }

        try {
            long timestamp = System.currentTimeMillis();
            String fileName = "crash-" + timestamp + CRASH_REPORTER_EXTENSION;
            FileOutputStream trace = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(trace);

            writer.write(STACK_TRACE);
            writer.write(" : ");
            writer.write(result == null ? "" : result);
            writer.write("\n");

            //保存手机设备信息
            Enumeration en = mDeviceCrashInfo.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                if (TextUtils.isEmpty(key))
                    continue;
                String Property = mDeviceCrashInfo.getProperty(key);
                if (TextUtils.isEmpty(Property))
                    continue;
                writer.write(key);
                writer.write(" : ");
                writer.write(Property);
                writer.write("\n");
                if (Constants.isDebug()) {
                    Logger.e(key + ":" + Property);
                }
            }
            writer.flush();
            writer.close();
//            mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing report file...", e);
        }
        return null;
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
                mDeviceCrashInfo.put("CurrentTime", StringUtil.getSimpleFormatDate(System.currentTimeMillis()));
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }

        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }

        }

    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx
     */
    public void sendCrashReportsToServer(Context ctx, String curfileName) {
        if (!HttpUtil.isNetworkConnected(LQBApp.getApp())) { // 无可用网络
            return;
        }
        File cr = new File(ctx.getFilesDir(), curfileName);
        if (cr != null) {
            boolean ret = postReport(mContext, cr);
        }
    }

    public static boolean postReport(final Context context, final File file) {
        StringBuilder str = new StringBuilder();
        try {
            FileInputStream ffr = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(ffr);

            BufferedReader br = new BufferedReader(read);
            String s;
            int n = 0;
            while ((s = br.readLine()) != null) {
                str.append(s).append("\n");
            }

            ffr.close();
        } catch (Exception e) {
            return false;
        }

        if (str.length() == 0)
            return true;

        String idc = "_crash";
        try {

            HashMap<String, String> binParams1 = new HashMap<String, String>();
            String url = "";
            FileUploader binVersion = new FileUploader(url, file.getAbsolutePath(), binParams1,
                    new FileUploader.FileUploadCallBack() {

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(int proess) {

                        }

                        @Override
                        public void onDownSuccess(String filePath) {
                            if (file != null && file.exists()) {
                                file.delete();
                            }
                            preCrashFile();
                        }

                        @Override
                        public void onDownError() {
                        }
                    });
            binVersion.execute((Void) null);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    // public void sendPreviousReportsToServer()
    // {
    // sendCrashReportsToServer(mContext);
    // }
    public static void preCrashFile() {
        String[] crFiles = getCrashReportFiles(LQBApp.getApp());
        if (crFiles != null && crFiles.length > 0) {
            //删除7天之前的日志,保存当天的日志文件
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LQBApp.getApp());
            String fileName = getTodayCrashReportFiles(LQBApp.getApp(), crFiles, preferences);
            preferences.edit().putString(CRASH_FILE_NAME, fileName).apply();
        } else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LQBApp.getApp());
            preferences.edit().putString(CRASH_FILE_NAME, "").apply();
        }
    }

    public static String getTodayCrashReportFiles(Context ctx, String[] crFiles, SharedPreferences preferences) {
        String todayFileName = Constants.EMPTY_STRING;
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            // 只保留7天的文件， 且一天内最多发送10封邮件
            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                long date = cr.lastModified();
                if (date == 0)
                    continue;
                // 避免产生过多的文件
                long rest = 7 * 24 * 3600;
                if (Math.abs(System.currentTimeMillis() - date) / 1000 > rest) {
                    cr.delete();
                    continue;
                }
                //一天最多发送10封邮件
                if (isToday(date)) {
                    if (isToday(preferences.getLong(CRASH_FILE_COMMIT_TIME, 0))) {
                        int count = preferences.getInt(CRASH_FILE_COMMIT_COUT, 0);
                        if (count < 10) {
                            count++;
                            preferences.edit().putLong(CRASH_FILE_COMMIT_TIME, System.currentTimeMillis()).putInt(CRASH_FILE_COMMIT_COUT, count);
                            todayFileName = fileName;
                        }
                    } else {
                        preferences.edit().putLong(CRASH_FILE_COMMIT_TIME, System.currentTimeMillis()).putInt(CRASH_FILE_COMMIT_COUT, 0);
                        todayFileName = fileName;
                    }
                    break;
                }
            }
        }
        return todayFileName;
    }

    public static boolean isToday(long dateMi) {
        Calendar old = Calendar.getInstance();
        old.setTimeInMillis(dateMi);
        Calendar now = Calendar.getInstance();

        Calendar old1 = Calendar.getInstance();
        old1.set(old.get(Calendar.YEAR), old.get(Calendar.MONTH), old.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        Calendar now1 = Calendar.getInstance();
        now1.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        int intervalDay = (int) ((now1.getTimeInMillis() - old1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
        return intervalDay == 0;
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    public static String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    public static String readFirstLineOfFile(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String info = br.readLine();
            fr.close();

            return info;

        } catch (Exception e) {
        }
        return "";
    }

}