package com.neotys.codec.HDS;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.instanceOf;
import static com.neotys.extensions.codec.predicates.MorePredicates.isResponseEntity;
import static com.neotys.extensions.codec.predicates.MorePredicates.isRequestEntity;
import com.neotys.extensions.codec.AbstractBinder;
import com.neotys.extensions.codec.predicates.MorePredicates;


public class MyBinder extends AbstractBinder {
	
	@Override
	
	protected void configure() {
	
		whenEntity(and(MorePredicates.urlContains(".bootstrap"),isResponseEntity())).decodeWith(MyBootstrapResponseDecoder.class);
		whenEntity(and(MorePredicates.urlContains(".bootstrap"),isRequestEntity())).decodeWith(HttpRequestDecoder.class);
		whenEntity(and(MorePredicates.urlContains(".f4m"),isRequestEntity())).decodeWith(HttpRequestDecoder.class);
		whenEntity(and(MorePredicates.urlContains(".f4m"),isResponseEntity())).decodeWith(MyF4MResponseDecoder.class);
		whenObject(instanceOf(HTTPRequest.class)).encodeWith(HTTPRequestEncoder.class);
	}
	
	

}
