package com.qbt.entity;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Mr.Xu
 * @Date: Created in 15:40 2018/12/9
 * @Description:
 */
public class Constants {

    public static final String DBDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String DBURL;
    public static final String WsdlURL;
    public static final String DBUser;
    public static final String IP;
    public static final String PORT;
    public static final String DBpwd ;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new BufferedInputStream(new FileInputStream("conf/config")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WsdlURL = properties.getProperty("WsdlURL");
        DBURL = properties.getProperty("DBURL");
        DBUser = properties.getProperty("DBUser");
        DBpwd = properties.getProperty("DBPwd");
        IP = properties.getProperty("IP");
        PORT = properties.getProperty("PORT");
    }

}
