package com.solvd.universityxml.utils;

import com.solvd.universityxml.impl.DomParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.Optional;

public class ParserUtils {

    private static final Logger logger = LogManager.getLogger(DomParser.class);

    public static Optional<Schema> loadSchema(String schemaFileName) {
        Schema schema = null;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        try {
            schema = factory.newSchema(
                    new File(
                            Objects.requireNonNull(DomParser.class.getClassLoader().getResource(schemaFileName)).getFile()
                    )
            );
        } catch (SAXException e) {
            logger.error("Error when try to create new Schema " + e);
        }
        return Optional.of(schema);
    }

    public static Optional<Document> parseXmlDom(String xmlName) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(readXmlFileIntoInputStream(xmlName));
        } catch (ParserConfigurationException e) {
            logger.error("Error when try to config parser" + e);
        } catch (SAXException e) {
            logger.error("Error when try to parse" + e);
        } catch (IOException e) {
            logger.error("Error when try to read xml input stream" + e);
        }
        return Optional.of(document);
    }

    public static boolean validateXml(Schema schema, Document document) {
        boolean valid = true;
        Validator validator = schema.newValidator();
        try {
            validator.validate(new DOMSource(document));
        } catch (SAXException e) {
            valid = false;
            logger.error("Error when try to validate xml" + e);
        } catch (IOException e) {
            valid = false;
            logger.error("Error when try to open xml file " + e);
        }
        return valid;
    }

    public static InputStream readXmlFileIntoInputStream(final String fileName) {
        return DomParser.class.getClassLoader().getResourceAsStream(fileName);
    }
}
