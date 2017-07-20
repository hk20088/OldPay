package com.newspace.common.utils;

import java.io.File;
import java.net.URI;

public class PathUtils
{
    /**
     * 获取文件的真实路径
     * @param path
     * @return
     * @throws Exception
     */
    public static String getRealPath(String path)throws Exception
    {
        path = path.replaceFirst("\\$\\{user\\.dir\\}", System.getProperty("user.dir"));
        URI uri = new URI(path);
        if(!uri.isAbsolute())
        {
            path = System.getProperty("user.dir")+File.separator+path;
            path = parseFilePath(path);
        }
        return path;
    }
    
    /**
     * 转换文件路径
     * @param path
     * @return
     */
    public static String parseFilePath(String path)
    {
        path = path.replace('\\', File.separatorChar);
        path = path.replace('/',File.separatorChar);
        path = path.replaceAll("((\\\\)|/)+","\\"+File.separator);
        path = new File(path).getPath();
        return path;
    }
    /**
     * 获取类路径
     * @return
     */
    public static String getClassPath()
    {
        String path = PathUtils.class.getResource("/").getPath();
        return parseFilePath(path);
    }
    /**
     * 获取类所在包的路径
     * @param clazz
     * @return
     */
    public static String getClassPackagePath(Class<?>clazz)
    {
        String clzpath = getClassPath();
        String clzname = clazz.getPackage().getName();
        clzname = clzname.replace('.', File.separatorChar);
        clzpath += File.separator+clzname;
        return parseFilePath(clzpath);
    }
}
