package com.neotys.codec.HDS;



import com.neotys.extensions.codec.functions.Encoder;

public class HTTPRequestEncoder implements Encoder {

	@Override
	public byte[] apply(Object arg0) {
		// TODO Auto-generated method stub
		final HTTPRequest httprequest=( HTTPRequest)arg0;
		
		return httprequest.getBytes();
	}

}
