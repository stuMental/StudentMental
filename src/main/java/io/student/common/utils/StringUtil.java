package io.student.common.utils;

public class StringUtil {
	public static String merge(Object[] array)
	  {
	    return merge(array, ",");
	  }
	public static String merge(Object[] array, String delimiter)
	  {
	    if (array == null) {
	      return null;
	    }
	    if (array.length == 0) {
	      return "";
	    }
	    StringBuilder sb = new StringBuilder(2 * array.length - 1);
	    for (int i = 0; i < array.length; i++)
	    {
	      sb.append(String.valueOf(array[i]).trim());
	      if (i + 1 != array.length) {
	        sb.append(delimiter);
	      }
	    }
	    return sb.toString();
	  }
}
