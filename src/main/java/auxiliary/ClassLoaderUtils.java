package main.java.auxiliary;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 左边牙齿疼
 * @Description: 类加载攻击
 * @date 2019/3/24
 */
public abstract class ClassLoaderUtils {

    /**
     * 根据包名加载类集合
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> loadClassByPackage(String packageName) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        String dirName = packageName.replace('.', '/');
        try {
            Enumeration<URL> urls = ClassLoaderUtils.class.getClassLoader().getResources(dirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    loadClassByFile(packageName, filePath, classes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载[" + packageName + "]包下的类异常");
        }
        return classes;
    }

    /**
     * 根据文件加载类
     *
     * @param packageName
     * @param packageNamePath
     * @param classes
     */
    private static void loadClassByFile(String packageName, String packageNamePath, Set<Class<?>> classes) {
        File dir = new File(packageNamePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 获取目录与class文件
        File[] listFiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        for (File f : listFiles) {
            if (f.isDirectory()) {//加载子包calss
                loadClassByFile(packageName + "." + f.getName(),
                        packageNamePath + "/" + f.getName(), classes);
                continue;
            }
            // 获取类名去掉.class 后缀
            String className = f.getName();
            className = className.substring(0, className.length() - 6);
            // 加载类
            Class<?> classType = loadClass(packageName + "." + className);
            if (classType != null) {
                classes.add(classType);
            }
        }
    }

    /**
     * 根据完整类名加载类
     *
     * @param fullClassName
     * @return
     */
    private static Class<?> loadClass(String fullClassName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
