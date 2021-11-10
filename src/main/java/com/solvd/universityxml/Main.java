package com.solvd.universityxml;

import com.solvd.universityxml.impl.DomParser;
import com.solvd.universityxml.impl.JaxbParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(DomParser.class);

    public static void main(String[] args) {
        String schemaName = "entrantForm.xsd";
        String xmlName = "entrantForm.xml";

        logger.info("########## Using DomParse to parse EntrantForm ##########");
        Parser domParser = new DomParser();
        EntrantForm domEntrantForm = domParser.parse(xmlName, schemaName);
        logger.debug(domEntrantForm);

        logger.info("########## Using JAXB to parse EntrantForm ##########");
        Parser jaxbParser = new JaxbParser();
        EntrantForm jaxbEntrantForm = jaxbParser.parse(xmlName, schemaName);
        logger.debug(jaxbEntrantForm);
    }
}
