package com.solvd.universityxml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class XmlDomParserUtils {

    private static InputStream readXmlFileIntoInputStream(final String fileName) {
        return XmlDomParserUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    static void validateXml(Schema schema, Document document) throws IOException, SAXException {
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
        System.out.println("Validation passed");
    }

    static Schema loadSchema(String schemaFileName) throws SAXException {
        Schema schema;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        schema = factory.newSchema(
                new File(Objects.requireNonNull(
                        XmlDomParserUtils.class.getClassLoader().getResource(schemaFileName)
                ).getFile())
        );
        return schema;
    }

    public static Document parseXmlDom(String xmlName) throws
            ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(readXmlFileIntoInputStream(xmlName));
    }
}
