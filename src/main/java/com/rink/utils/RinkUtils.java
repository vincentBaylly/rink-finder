package com.rink.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RinkUtils {
    
	public static boolean isInternetReachable(String hostnameOrIP) throws UnknownHostException, IOException{
		
		Socket socket = null;
		boolean reachable = false;
		try {
		    socket = new Socket(hostnameOrIP, 80);
		    reachable = true;
		} finally {            
		    if (socket != null) try { socket.close(); } catch(IOException e) {}
		}
		
		return reachable;
	}
    
    /**
     * 
     * 
     * @param inputDate
     * @return the formated date
     */
	public static Date parseDate(String inputDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date parsedDate = null;

		try {
			parsedDate = sdf.parse(inputDate);
			System.out.println(parsedDate);
		} catch (ParseException e) {
			System.out.println("Unparseable using " + sdf);
		}
		
		return parsedDate;
	}

}
