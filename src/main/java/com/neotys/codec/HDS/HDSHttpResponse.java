package com.neotys.codec.HDS;


import java.io.IOException;

import com.neotys.hds.HDSBoostrapParser ;
import com.neotys.hds.HDSBootstrap;

public class HDSHttpResponse {

	
	String  Strbootstrap;
	
	public HDSHttpResponse(final byte[] input)
	{
		try {
			
			HDSBootstrap bootstrap = HDSBoostrapParser.parse(input);
			Strbootstrap=bootstrap.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
