package com.head.toolkit;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 类名称：FileUtil.java <br/>
 * 类描述：文件工具<br/>
 * 创建人：shuwen <br/>
 * 创建时间：2020-05-18 13:24 <br/>
 */
public class FileUtil
{
    
    public static File createCacheFile(Context context, String uniqueName)
    {
        String parentPath = null;
        if (SDCardUtil.isSDCardEnable())
        {
            parentPath = context.getExternalCacheDir().getAbsolutePath();
        }
        else
        {
            parentPath = context.getCacheDir().getAbsolutePath();
        }
        return new File(parentPath + File.separator + uniqueName);
    }
    
    public static void clearAllCacheFile(Context context)
    {
        deleteFile(context.getCacheDir());
        if (SDCardUtil.isSDCardEnable())
        {
            deleteFile(context.getExternalCacheDir());
        }
    }
    
    public static void deleteFile(File file)
    {
        if (file.isFile())
        {
            file.delete();
            return;
        }
        if (file.isDirectory())
        {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0)
            {
                file.delete();
                return;
            }
            for (File f : childFile)
            {
                deleteFile(f);
            }
            file.delete();
        }
    }
    
    public static long getFileSize(File file)
    {
        long size = 0;
        try
        {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                // 如果下面还有文件
                if (fileList[i].isDirectory())
                {
                    size = size + getFileSize(fileList[i]);
                }
                else
                {
                    size = size + fileList[i].length();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return size;
    }
    
    public static boolean isSDCardValue()
    {
        if (Environment.getExternalStorageState()
            .equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static File getDiskCacheDir(Context context, String uniqueName)
    {
        return new File(
            context.getExternalCacheDir() + File.separator + uniqueName);
    }
    
    public static File getExternalCacheDir()
    {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }
}
