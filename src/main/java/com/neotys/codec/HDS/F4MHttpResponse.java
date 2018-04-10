package com.neotys.codec.HDS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.codec.binary.Base64;
import com.neotys.hds.HDSBoostrapParser;
import com.neotys.hds.HDSBootstrap;
import com.google.common.io.BaseEncoding;




public class F4MHttpResponse {
	private Document F4MDocument;
	//private ConvertedContent F4m
	
	
	
	public F4MHttpResponse(final byte[] input)
	{
		super();
		String toParse = null;

		try {
			toParse = new String(input, "UTF-8");
			if(isXMLLike(toParse))
			{

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        F4MDocument = builder.parse(new ByteArrayInputStream(input));
		        F4MDocument.getDocumentElement().normalize();
		        
		        
		        XPathFactory xpathfactory = XPathFactory.newInstance();
		        XPath xpath = xpathfactory.newXPath();
			  	
		        decode64XmlContenFomXpath(F4MDocument, xpath,"/manifest//media/metadata",false);
		        
		        decode64XmlContenFomXpath(F4MDocument, xpath,"/manifest//bootstrapInfo",true);
		        
			}
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
 
	private void decode64XmlContenFomXpath(Document doc, XPath xpath, String strquery,Boolean BoostrapDecodingRequired)
			throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		
		
		XPathExpression expr = xpath.compile(strquery);
		NodeList  nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		String convertedBytes;
		
		for (int i = 0; i < nodes.getLength(); i++) {
			String nodeValue = nodes.item(i).getTextContent();
			
			if( nodeValue != null)
			{
				
					
				if(BoostrapDecodingRequired)
				{
					try {
							byte[] decoded = BaseEncoding.base64().decode(nodeValue.trim());
							if(Base64.isArrayByteBase64(decoded))
								decoded=BaseEncoding.base64().decode(new String(decoded).trim());
							convertedBytes=AMFConverter.ConvertBootstraptoXMl(decoded);
					}catch (final Throwable t) {
						System.out.println(t.getMessage()+" the cause :"+t.toString());
													

			
						byte[] decoded = BaseEncoding.base64().decode(nodeValue.trim());
						
						decoded=BaseEncoding.base64().decode(new String(decoded).trim());
						
						convertedBytes=AMFConverter.convertAmfMessageToXml(decoded,false);
					}

				}
				else
				{	
					
					//byte[] decoded = Base64.getDecoder().decode(nodeValue);
					byte[] decoded =BaseEncoding.base64().decode(nodeValue.trim());
					if(Base64.isArrayByteBase64(decoded))
						decoded=BaseEncoding.base64().decode(new String(decoded).trim());
					
					convertedBytes=AMFConverter.convertAmfMessageToXml(decoded,false);
					
				}
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
				Document tmp=builder.parse(new ByteArrayInputStream(convertedBytes.getBytes()));
				Node Ele = tmp.getFirstChild();
				Node imported=doc.adoptNode(Ele);
				nodes.item(i).appendChild(imported);
				//nodes.item(i).setTextContent(convertedBytes);
			}
		}
		
	}
	
	public static boolean isXMLLike(String inXMLStr) {

        boolean retBool = false;
        
        // IF WE HAVE A STRING
        if (inXMLStr != null && inXMLStr.trim().length() > 0) {

            // IF WE EVEN RESEMBLE XML
            	 retBool =inXMLStr.trim().startsWith("<?xml");
             
            }
        // ELSE WE ARE FALSE
          return retBool;
    }

	public Document getF4MDocument()
	{
		return F4MDocument;
	}
	
}
