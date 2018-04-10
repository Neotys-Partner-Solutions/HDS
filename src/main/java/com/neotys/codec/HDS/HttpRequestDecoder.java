package com.neotys.codec.HDS;

import com.neotys.extensions.codec.functions.Decoder;

public class HttpRequestDecoder implements Decoder {

	@Override
	public Object apply(byte[] arg0) {
		// TODO Auto-generated method stub
		return new HTTPRequest(arg0);
	}

}
