package com.weddfix.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBRestore {
	
	/*public static void main(String[] args) throws IOException {
		
		Runtime r = Runtime.getRuntime();
		Process p;
		ProcessBuilder pb;
		r = Runtime.getRuntime();
		pb = new ProcessBuilder( 
		    "C:\\Program Files (x86)\\PostgreSQL\\9.4\\bin\\psql.exe",
		    "--host", "localhost",
		    "--port", "5432",
		    "--username", "postgres",
		    "--dbname", "MyPhysio",
		    "--role", "postgres",
		    "--no-password",
		    "--verbose",
		   "E:\\myphysio\\myphysio_Nov112015.backup");
		pb.redirectErrorStream(true);
		p = pb.start();
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String ll;
		while ((ll = br.readLine()) != null) {
		 System.out.println(ll);
	}
		}                  */  

	public static void main(String[] args) {
		String path = "E:/myphysio_Nov112015.backup";
		final String [] cmd = { "C:/Program Files (x86)/PostgreSQL/9.4/bin/psql.exe",
		       "-U", "postgres",
		       "-d", "MyPhysio",
		       "-f", path 
		     };

		try {
		    Process reloadProcess = Runtime.getRuntime().exec(cmd);
		    System.out.println("Reloadeddddddddd    "+reloadProcess);
		    if(null!=reloadProcess){
		        if(reloadProcess.waitFor()==0){
		            System.out.println("Reloaded");
		        }
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}

}
