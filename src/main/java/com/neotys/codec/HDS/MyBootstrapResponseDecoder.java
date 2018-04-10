
package com.neotys.codec.HDS;

import com.neotys.extensions.codec.functions.Decoder;

public class MyBootstrapResponseDecoder implements Decoder{
	public Object apply(final byte[] input) {
		return new HDSHttpResponse (input);
	}
}

