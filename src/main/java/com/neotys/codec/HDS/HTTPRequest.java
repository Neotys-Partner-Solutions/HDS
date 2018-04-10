package com.neotys.codec.HDS;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class HTTPRequest {
	HashMap<String, String> HttpParam;
	public HTTPRequest(byte[] arg0) {
		// TODO Auto-generated constructor stub
		String toParse = null;
			try {
			toParse = new String(arg0, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// no op
		}
		if(toParse != null)
		{
			HttpParam=new HashMap<String,String>();
			
			//---- parse POst parameters ( param=vamue&param....Etc)
			StringTokenizer tokens = new StringTokenizer(toParse, " &=");
			while(tokens.hasMoreTokens())
			{
			   String Key=tokens.nextToken();
			   String param=null;
			   if(tokens.hasMoreElements())
				   param=tokens.nextToken();
			   
	    	   HttpParam.put(Key, param);
			}
		}
	}
	public byte[] getBytes() {
		String ParamString= null;
		if(HttpParam!= null)
		{
			for (Entry<String, String> e : HttpParam.entrySet()) 
			{
			    String key    = e.getKey();
			    String value  = e.getValue();
			    if(value ==null)
			    	value="";
			    
			    if(ParamString!=null)
			    	ParamString=ParamString+"&";
			    
			    ParamString=key+"="+value;
			}
		}
		if(ParamString != null)
			return ParamString.getBytes();
		else
			return null;
	}
}
