package io.student.common.utils;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacAddress {
	private static final Logger logger = LoggerFactory.getLogger(MacAddress.class);
	  private static final String[] IGNORE_MACS = { "00-00-00-00-00-00-00-E0" };
	  
	  private static String hexByte(byte b)
	  {
	    String content = "000000" + Integer.toHexString(b).toUpperCase();
	    return content.substring(content.length() - 2);
	  }
	  
	  public static List<String> getMacAddressList()
	  {
	    List<String> macAddressList = new ArrayList();
	    try
	    {
	      Enumeration<NetworkInterface> networkInterfaceList = NetworkInterface.getNetworkInterfaces();
	      while (networkInterfaceList.hasMoreElements())
	      {
	        NetworkInterface network = (NetworkInterface)networkInterfaceList.nextElement();
	        byte[] macBytes = network.getHardwareAddress();
	        if ((macBytes != null) && (macBytes.length != 0))
	        {
	          StringBuilder builder = new StringBuilder();
	          for (byte macByte : macBytes)
	          {
	            builder.append(hexByte(macByte));
	            builder.append('-');
	          }
	          String macAddress = builder.deleteCharAt(builder.length() - 1).toString();
	          if ((!ArrayUtil.contains(IGNORE_MACS, macAddress)) && (!macAddressList.contains(macAddress))) {
	            macAddressList.add(macAddress);
	          }
	        }
	      }
	    }
	    catch (SocketException e)
	    {
	      logger.error("getMacAddressList", e);
	    }
	    Collections.sort(macAddressList);
	    return macAddressList;
	  }
}
