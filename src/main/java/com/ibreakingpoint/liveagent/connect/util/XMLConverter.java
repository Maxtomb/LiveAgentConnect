package com.ibreakingpoint.liveagent.connect.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class XMLConverter {
	public static Object convertXmlStringToModel(Class modelClass,String xmlString) throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(modelClass);
		Unmarshaller u = jc.createUnmarshaller();
		StringBuffer xmlStr = new StringBuffer(xmlString);
		return u.unmarshal(new StreamSource(new StringReader(xmlStr.toString())));
	}
}
