package com.solvd.universityxml;

import com.solvd.universityxml.impl.DomParserImpl;

public class Main {

    public static void main(String[] args) {
        String schemaName = "entrantForm.xsd";
        String xmlName = "entrantForm.xml";

        Parser domParser = new DomParserImpl();
        EntrantForm entrantForm = domParser.parse(xmlName, schemaName);
        System.out.println(entrantForm);
    }
}
