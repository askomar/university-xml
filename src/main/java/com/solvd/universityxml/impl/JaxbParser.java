package com.solvd.universityxml.impl;

import com.solvd.universityxml.EntrantForm;
import com.solvd.universityxml.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import java.util.Optional;

import static com.solvd.universityxml.utils.ParserUtils.loadSchema;
import static com.solvd.universityxml.utils.ParserUtils.readXmlFileIntoInputStream;

public class JaxbParser implements Parser {

    private static final Logger logger = LogManager.getLogger(JaxbParser.class);

    @Override
    public EntrantForm parse(String xmlFile, String xslFile) {
        Optional<Schema> optSchema = loadSchema(xslFile);
        Unmarshaller unmarshaller = null;
        EntrantForm entrantForm = null;
        try {
            JAXBContext context = JAXBContext.newInstance(EntrantForm.class);
            unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(optSchema.get());
        } catch (JAXBException e) {
            logger.error("Error when try to initialise unmarshaller " + e);
        }
        try {
            assert unmarshaller != null;
            entrantForm = (EntrantForm) unmarshaller.unmarshal(readXmlFileIntoInputStream(xmlFile));
        } catch (JAXBException e) {
            logger.error("Error when try to unmarshall using JaxbParser " + e);
        }
        return entrantForm;
    }
}
