package com.currency.converter.service;

import com.currency.converter.soapclient.ValCurs;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Component
public class SoapService {

    @SuppressWarnings("unchecked")
    public ValCurs unmarshallXml(final String xml) throws JAXBException {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StreamSource streamSource = new StreamSource(new StringReader(xml));
            JAXBElement<ValCurs> je = jaxbUnmarshaller.unmarshal(streamSource, ValCurs.class);

            return  je.getValue();
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
