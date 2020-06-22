package io.student.common.utils;
public class ArrayUtil {
	public static boolean contains(String[] values, String value)
	  {
	    boolean ignoreCase = false;
	    return contains(values, value, ignoreCase);
	  }
	  
	  public static boolean contains(String[] values, String value, boolean ignoreCase)
	  {
	    boolean result = false;
	    for (String oneValue : values) {
	      if (ignoreCase)
	      {
	        if (oneValue.equalsIgnoreCase(value))
	        {
	          result = true;
	          break;
	        }
	      }
	      else if (oneValue.equals(value))
	      {
	        result = true;
	        break;
	      }
	    }
	    return result;
	  }
	  
}
