package com.wzh.io;

import java.io.InputStream;

/**
 * 将指定路径下的文件加载成字节输入流，存储到内存当中
 */
public class Resources {

    /**
     * 根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中
     *
     * @param path
     * @return
     */
    public static InputStream getResourceAsSteam(String path){
        //借助类加载器加载配置文件
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;

    }
}
