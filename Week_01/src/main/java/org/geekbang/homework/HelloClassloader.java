package org.geekbang.homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Yaphet
 * @date 2020/10/18
 */
public class HelloClassloader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassloader().findClass("Hello");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getMethod("hello");
            method.invoke(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(this.getClass().getResource(name + ".xlass").getPath());
        try {
            byte[] bytes = getBytes(file);
            for (int i = 0; i < bytes.length; ++i) {
                bytes[i] = (byte)(255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //读取一个文件的内容
    private byte[] getBytes(File file) throws IOException{
        long len = file.length();
        byte[] raw = new byte[(int)len];
        FileInputStream fis = new FileInputStream(file);
        //一次读取class文件的全部二进制数据
        int r = fis.read(raw);
        if(r != len){
            throw new IOException("无法读取全部文件：" + r + " != " + len);
        }
        fis.close();
        return raw;
    }
}