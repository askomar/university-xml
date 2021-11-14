package com.solvd.universityxml.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.universityxml.EntrantForm;
import com.solvd.universityxml.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.solvd.universityxml.utils.ParserUtils.readXmlFileIntoInputStream;

public class JacksonParser implements Parser {

    private static final Logger logger = LogManager.getLogger(JacksonParser.class);

    @Override
    public EntrantForm parse(String xmlFile, String xslFile) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        EntrantForm entrantForm = null;
        try {
            entrantForm = mapper.readValue(readXmlFileIntoInputStream(xmlFile), EntrantForm.class);
        } catch (IOException e) {
            logger.error("Error when try to deserialize entrantForm " + e);
        }
        return entrantForm;
    }
}
