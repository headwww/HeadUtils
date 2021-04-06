package com.head.toolkit;

/**
 *
 * 类名称：ByteTransform.java <br/>
 * 类描述：byte转化为B,KB,M,G<br/>
 * 创建人：shuwen <br/>
 * 创建时间：2020-03-09 11:02 <br/>
 * 
 * @version
 */
public class ByteTransform
{
    public static String formatSize(float size)
    {
        long kb = 1024;
        
        long mb = (kb * 1024);
        
        long gb = (mb * 1024);
        
        if (size < kb)
        {
            
            return String.format("%d B", (int)size);
            
        }
        
        else if (size < mb)
        {
            
            return String.format("%.2f KB", size / kb); // 保留两位小数
            
        }
        
        else if (size < gb)
        {
            
            return String.format("%.2f MB", size / mb);
            
        }
        
        else
        {
            
            return String.format("%.2f GB", size / gb);
            
        }
        
    }
    
}
