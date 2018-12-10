package org.xiao.message.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {
	public static String getLoacalHost(){
		String host="";
		try {
			  host=InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host;
	}
}
