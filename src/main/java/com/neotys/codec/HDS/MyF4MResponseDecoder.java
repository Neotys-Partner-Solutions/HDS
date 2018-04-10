package com.neotys.codec.HDS;
import com.neotys.extensions.codec.functions.Decoder;


public class MyF4MResponseDecoder implements Decoder {
	public Object apply(final byte[] input) {
		return new F4MHttpResponse (input);
	}
}
