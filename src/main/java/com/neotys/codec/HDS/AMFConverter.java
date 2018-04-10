package com.neotys.codec.HDS;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.neotys.hds.HDSBoostrapParser;
import com.neotys.hds.HDSBootstrap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.Mapper;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.ActionMessage;
import flex.messaging.io.amf.Amf0Input;
import flex.messaging.io.amf.AmfMessageDeserializer;
import flex.messaging.io.amf.AmfMessageSerializer;
import flex.messaging.io.amf.MessageBody;
import flex.messaging.io.amf.MessageHeader;
import flex.messaging.messages.AcknowledgeMessage;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.CommandMessage;
import flex.messaging.messages.CommandMessageExt;
import flex.messaging.messages.ErrorMessage;
import flex.messaging.messages.RemotingMessage;

public class AMFConverter {
	private static XStream xstream; 

	public static XStream getXStream() {
		 
		if (xstream == null) { 
		   xstream = new XStream(new DomDriver()); 
		 
		   xstream.alias("ActionMessage", ActionMessage.class); 
		   xstream.alias("MessageHeader", MessageHeader.class); 
		   xstream.alias("MessageBody", MessageBody.class); 
		   xstream.alias("RemotingMessage", RemotingMessage.class); 
		   xstream.alias("CommandMessage", CommandMessage.class); 
		   xstream.alias("AcknowledgeMessage", AcknowledgeMessage.class); 
		   xstream.alias("ErrorMessage", ErrorMessage.class); 
		   xstream.alias("ASObject", ASObject.class); 
		   xstream.alias("AsyncMessage", AsyncMessage.class); 
		   xstream.alias("DSC", CommandMessageExt.class); 
		//   xstream.alias("DSK", AcknowledgeMessageExt.class); 
		 
		   // Better ASObject Converter 
		   Mapper mapper = xstream.getMapper(); 
		  xstream.registerConverter(new ASObjectConverter(mapper)); 
		 }
		return xstream; 
	}
	/*private void configureTypeMarshaller(final AmfProtocolContext protocolCtxt) {
        // DISABLE enum management if [AMF]context.legacyEnum=true
        // At deserialize time, try to create value using enum dedicated class instead of String
        if (!protocolCtxt.getAmfPrefsManager().isLegacyEnum()) {
            TypeMarshallingContext.setTypeMarshaller(new Java15TypeMarshaller());
        }
        TypeMarshallingContext.getTypeMarshallingContext().setClassLoader(getDynamicClassLoader());
    }*/

	public static String ConvertBootstraptoXMl(byte[] amf) {
	
		XStream xs = getXStream(); 
		
		HDSBootstrap bootstrap = null;
		try {
			bootstrap = HDSBoostrapParser.parse(amf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String xml = xs.toXML(bootstrap);
		return xml;

		} catch (Exception ex) {
		 ex.printStackTrace();
		return null;
		}
	}
	public static String convertAmfMessageToXml(byte[] amf, boolean useAliasRegistry) {
		XStream xs = getXStream(); 
		 
		InputStream myInputStream = new ByteArrayInputStream(amf); 
		DataInputStream dataInStream = new DataInputStream(myInputStream);
		SerializationContext serializationContext = new SerializationContext();
		//serializationContext.setCharset(protocolCtxt.getCharset());
        serializationContext.instantiateTypes = false;
        serializationContext.setSerializerClass(AmfMessageSerializer.class);
        serializationContext.setDeserializerClass(AmfMessageDeserializer.class);
     //   TypeMarshallingContext.setTypeMarshaller(new Java15TypeMarshaller());
        //configureTypeMarshaller(protocolCtxt);

		Amf0Input deserializer = new Amf0Input(serializationContext );
		deserializer.setInputStream( dataInStream);
		final ArrayList<Object> o = new ArrayList<>();
                try {
                    while(myInputStream.available() > 0) {
                        try {
                            o.add(deserializer.readObject());
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

		try {
				String xml = xs.toXML(o);
			return xml;

		} catch (Exception ex) {
			 ex.printStackTrace();
			return null;
		}
	}
}
