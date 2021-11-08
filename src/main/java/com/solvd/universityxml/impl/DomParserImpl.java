package com.solvd.universityxml.impl;

import com.solvd.universityxml.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DomParserImpl implements Parser {

    @Override
    public EntrantForm parse(String xmlFile, String xslFile) {
        Optional<Schema> optSchema = loadSchema(xslFile);
        Optional<Document> optDocument = parseXmlDom(xmlFile);

        if (!optSchema.isPresent() && optDocument.isPresent()) {
            throw new RuntimeException("Error when try to initialise parsing");
        }
        if (validateXml(optSchema.get(), optDocument.get())) {
            optDocument.get().getDocumentElement().normalize();
            NodeList nList = optDocument.get().getElementsByTagName("entrantForm");
            Node node = nList.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                /**
                 * Parse id
                 */
                int id = Integer.parseInt(eElement.getAttribute("id"));

                /**
                 * Parse entrants
                 */
                List<Entrant> entrants = new ArrayList<>();
                NodeList entrantList = optDocument.get().getElementsByTagName("entrant");
                for (int i = 0; i < entrantList.getLength(); i++) {
                    Node entrantNode = entrantList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element entrantElement = (Element) entrantNode;
                        Entrant entrant = null;
                        int entrantId = Integer.parseInt(entrantElement.getAttribute("id"));
                        String surname = entrantElement.getElementsByTagName("surname").item(0).getTextContent();
                        String name = entrantElement.getElementsByTagName("name").item(0).getTextContent();
                        NodeList patronymicNodeList = entrantElement.getElementsByTagName("patronymic");
                        String patronymic = patronymicNodeList.getLength() > 0 ? patronymicNodeList.item(0).getTextContent() : null;
                        LocalDate dateOfBirth = LocalDate.parse(entrantElement.getElementsByTagName("dateOfBirth").item(0).getTextContent());

                        if (!Objects.isNull(patronymic)) {
                            entrant = new Entrant(entrantId, surname, name, patronymic, dateOfBirth);
                        } else {
                            entrant = new Entrant(entrantId, surname, name, dateOfBirth);
                        }
                        entrants.add(entrant);
                    }
                }

                /**
                 * Parse specialisationPlan
                 */
                SpecialisationPlan specialisationPlan = null;
                Element specialisationPlanElenent = (Element) optDocument.get().getElementsByTagName("specialisationPlan").item(0);

                /**
                 * Parse specialisation for SpecialisationPlan
                 */
                Specialisation specialisation = null;
                Node specialisationNode = specialisationPlanElenent.getElementsByTagName("specialisation").item(0);
                Element specialisationElement = (Element) specialisationNode;
                String speciasisationName = specialisationElement.getElementsByTagName("name").item(0).getTextContent();
                specialisation = new Specialisation(speciasisationName);

                /**
                 * Parse specialisationPlanType for SpecialisationPlan
                 */
                SpecialisationPlanType specialisationPlanType = null;
                NodeList specialisationPlanTypeNode = specialisationPlanElenent.getElementsByTagName("specialisationPlanType");
                String specialisationPlanTypeName = ((Element) specialisationPlanElenent).getElementsByTagName("name").item(0).getTextContent();
                specialisationPlanType = new SpecialisationPlanType(specialisationPlanTypeName);

                /**
                 * Parse freePlacesAmount for SpecialisationPlan
                 */
                Integer freePlacesAmount = Integer.parseInt(
                        specialisationPlanElenent.getElementsByTagName("freePlacesAmount").item(0).getTextContent()
                );

                /**
                 * Parse paidPlacesAmount for SpecialisationPlan
                 */
                Integer paidPlacesAmount = Integer.parseInt(
                        specialisationPlanElenent.getElementsByTagName("paidPlacesAmount").item(0).getTextContent()
                );

                /**
                 * Parse cost for SpecialisationPlan
                 */
                Double cost = Double.parseDouble(
                        specialisationPlanElenent.getElementsByTagName("cost").item(0).getTextContent()
                );

                /**
                 * Parse lastUpdate for SpecialisationPlan
                 */
                LocalDate lastUpdate = LocalDate.parse(
                        specialisationPlanElenent.getElementsByTagName("lastUpdate").item(0).getTextContent()
                );

                specialisationPlan = new SpecialisationPlan(
                        specialisation,
                        specialisationPlanType,
                        freePlacesAmount,
                        paidPlacesAmount,
                        cost,
                        lastUpdate
                );

                /**
                 * Parse paid
                 */
                boolean paid = Boolean.parseBoolean(
                        optDocument.get().getElementsByTagName("paid").item(0).getTextContent()
                );

                /**
                 * Parse acceptedDate
                 */
                LocalDate acceptedDate = LocalDate.parse(
                        optDocument.get().getElementsByTagName("acceptedDate").item(0).getTextContent()
                );
                EntrantForm entrantForm = new EntrantForm();
                entrantForm.setId(id);
                entrantForm.setEntrants(entrants);
                entrantForm.setSpecializationPlan(specialisationPlan);
                entrantForm.setPaid(paid);
                entrantForm.setAcceptedDate(acceptedDate);
                return entrantForm;
            }
        } else {
            throw new RuntimeException("Validation is falsed");
        }
        return null;
    }

    private static InputStream readXmlFileIntoInputStream(final String fileName) {
        return DomParserImpl.class.getClassLoader().getResourceAsStream(fileName);
    }

    static boolean validateXml(Schema schema, Document document) {
        boolean valid = true;
        Validator validator = schema.newValidator();
        try {
            validator.validate(new DOMSource(document));
        } catch (SAXException e) {
            valid = false;
            System.err.println("Error when try to validate xml" + e);
        } catch (IOException e) {
            valid = false;
            System.err.println("Error when try to open xml file" + e);
        }
        return valid;
    }

    static Optional<Schema> loadSchema(String schemaFileName) {
        Schema schema = null;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        try {
            schema = factory.newSchema(
                    new File(
                            Objects.requireNonNull(DomParserImpl.class.getClassLoader().getResource(schemaFileName)).getFile()
                    )
            );
        } catch (SAXException e) {
            System.err.println("Error when try to create new Schema " + e);
        }
        assert schema != null;
        return Optional.of(schema);
    }

    public static Optional<Document> parseXmlDom(String xmlName) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(readXmlFileIntoInputStream(xmlName));
        } catch (ParserConfigurationException e) {
            System.err.println("Error when try to config parser" + e);
        } catch (SAXException e) {
            System.err.println("Error when try to parse" + e);
        } catch (IOException e) {
            System.err.println("Error when try to read xml input stream" + e);
        }
        assert document != null;
        return Optional.of(document);
    }
}
