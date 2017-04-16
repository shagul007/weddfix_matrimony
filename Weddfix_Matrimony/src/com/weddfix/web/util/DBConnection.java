package com.weddfix.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection 
{
		private static Connection connection;
	    public static Connection getConnection()
	    {
	    	try
	    	{
	    		Properties props = new Properties();
	    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    		props.load(classLoader.getResourceAsStream("/jdbc.properties"));
	           
	            System.out.println(props.getProperty("jdbc.username"));
	            System.out.println(props.getProperty("jdbc.password"));
	            System.out.println(props.getProperty("jdbc.ip"));
	            System.out.println(props.getProperty("jdbc.port"));
	            

	    		Class.forName("org.postgresql.Driver");
				//connection = DriverManager.getConnection("jdbc:postgresql://"+props.getProperty("jdbc.ip")+":"+props.getProperty("jdbc.port")+"/"+props.getProperty("jdbc.dbname")+","+props.getProperty("jdbc.username")+","+props.getProperty("jdbc.password"));

				connection = DriverManager.getConnection("jdbc:postgresql://"+props.getProperty("jdbc.ip")+":"+props.getProperty("jdbc.port")+"/"+props.getProperty("jdbc.dbname"),"postgres","postgres");
				return connection;
		    }
                catch(SQLException se)
                {
                    se.printStackTrace();
                }catch(ClassNotFoundException cnfe)
                {
                		cnfe.printStackTrace();
                }catch(Exception e)
                {
                		e.printStackTrace();
                }
		return connection;
	}
}
