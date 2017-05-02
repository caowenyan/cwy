package cn.cindy.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

 /** 
 * <p> Description:  获取服务器ip</p>
 */
public class UGetAddress {
	
	public static String getAddress() throws SocketException, UnknownHostException {
		Collection<InetAddress> colInetAddress =getAllHostAddress();
		  for (InetAddress address : colInetAddress) {   
		    if (!address.isLoopbackAddress()&&(address instanceof Inet4Address)) 
		     return address.getHostAddress();
		   }   
        return "";
    }
		 
	public static Collection<InetAddress> getAllHostAddress() {
		try {   
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();   
		    Collection<InetAddress> addresses = new ArrayList<InetAddress>();   
		    while (networkInterfaces.hasMoreElements()) {   
		    	NetworkInterface networkInterface = networkInterfaces.nextElement();   
		        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();   
		        while (inetAddresses.hasMoreElements()) {   
		        	InetAddress inetAddress = inetAddresses.nextElement();   
		            addresses.add(inetAddress);   
		        }   
		    }
		    return addresses;   
		   } catch (SocketException e) {   
		     throw new RuntimeException(e.getMessage(), e);   
		   }   
	}
}
