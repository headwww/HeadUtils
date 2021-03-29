package com.head.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 类名称：CrashHandler.java <br/>
 * 类描述：全局异常捕获<br/>
 * 创建人：shuwen <br/>
 * 创建时间：2020-01-14 10:56 <br/>
 *
 * @version
 */
public final class CrashHandler implements Thread.UncaughtExceptionHandler
{
    /**
     * TAG
     */
    public static final String TAG = "CrashHandler";

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * CrashHandler实例
     */
    private static CrashHandler instance = null;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 用来存储设备信息和异常信息
     */
    private Map<String, String> infos = new HashMap<String, String>();

    private CrashHandler()
    {
    }

    /**
     * 单例模式
     *
     * @return CrashHandler
     */
    public static CrashHandler getInstance()
    {

        synchronized (CrashHandler.class)
        {
            if (instance == null)
            {
                instance = new CrashHandler();
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context)
    {
        mContext = context;

        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        // TODO Auto-generated method stub
        if (!handleException(ex) && mDefaultHandler != null)
        {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
        else
        {
            try
            {
                Thread.sleep(3000);// 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
            }
            catch (InterruptedException e)
            {
                Log.e(TAG, "");
            }
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex)
    {
        if (ex == null)
        {
            return false;
        }
        // 使用Toast来显示异常信息

        new Thread()
        {
            @Override
            public void run()
            {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_LONG)
                    .show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
//        collectDeviceInfo(mContext);
        // 保存日志文件
//        saveCrashInfo2File(ex);
        // 发送到后台
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx 上线文
     */
    public void collectDeviceInfo(Context ctx)
    {
        try
        {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                PackageManager.GET_ACTIVITIES);
            if (pi != null)
            {
                String versionName =
                    pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            }
            catch (Exception e)
            {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex 异常
     */
    private void saveCrashInfo2File(Throwable ex)
    {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();

        // 循环着把所有的异常信息写入writer中
        while (cause != null)
        {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        Log.e("=", sb.toString());
    }
}