package io.student.common.utils;

import java.math.BigInteger;

public abstract class HexUtil {
	 public static final String byteArrayToHexString(byte[] array)
	  {
	    BigInteger bi = new BigInteger(1, array);
	    String hex = bi.toString(16);
	    int paddingLength = array.length * 2 - hex.length();
	    if (paddingLength > 0) {
	      return String.format(new StringBuilder().append("%0").append(paddingLength).append("d").toString(), new Object[] { Integer.valueOf(0) }) + hex;
	    }
	    return hex;
	  }
	  
	  public static final byte[] hexStringToByteArray(String hex)
	  {
	    byte[] binary = new byte[hex.length() / 2];
	    for (int i = 0; i < binary.length; i++) {
	      binary[i] = ((byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16));
	    }
	    return binary;
	  }
}
