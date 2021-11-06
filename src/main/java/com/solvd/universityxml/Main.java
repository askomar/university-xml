package com.solvd.universityxml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String schemaName = "entrantForm.xsd";
        String xmlName = "entrantForm.xml";

        EntrantForm entrantForm = new EntrantForm();
        entrantForm.parse(xmlName, schemaName);
        System.out.println(entrantForm);
    }
}
