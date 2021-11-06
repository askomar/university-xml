package com.solvd.universityxml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Parseable<T> {

    public void parse(String xmlFile, String xslFile) throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException;
}
