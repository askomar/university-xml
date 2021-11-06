package com.solvd.universityxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.solvd.universityxml.XmlDomParserUtils.*;

public class EntrantForm implements Parseable<EntrantForm> {

    private int id;
    private List<Entrant> entrants;
    private SpecialisationPlan specializationPlan;
    private boolean paid;
    private LocalDate acceptedDate;

    public EntrantForm() {
    }

    public EntrantForm(int id, List<Entrant> entrants, SpecialisationPlan specializationPlan, boolean paid, LocalDate acceptedDate) {
        this.id = id;
        this.entrants = entrants;
        this.specializationPlan = specializationPlan;
        this.paid = paid;
        this.acceptedDate = acceptedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Entrant> getEntrants() {
        return entrants;
    }

    public void setEntrants(List<Entrant> entrants) {
        this.entrants = entrants;
    }

    public SpecialisationPlan getSpecializationPlan() {
        return specializationPlan;
    }

    public void setSpecializationPlan(SpecialisationPlan specializationPlan) {
        this.specializationPlan = specializationPlan;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }


    public LocalDate getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(LocalDate acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    @Override
    public void parse(String xmlFile, String xslFile) throws ParserConfigurationException, IOException, SAXException {
        Schema schema = loadSchema(xslFile);
        Document document = parseXmlDom(xmlFile);
        validateXml(schema, document);

        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("entrantForm");
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
            NodeList entrantList = document.getElementsByTagName("entrant");
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
            Element specialisationPlanElenent = (Element) document.getElementsByTagName("specialisationPlan").item(0);

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
                    document.getElementsByTagName("paid").item(0).getTextContent()
            );

            /**
             * Parse acceptedDate
             */
            LocalDate acceptedDate = LocalDate.parse(
                    document.getElementsByTagName("acceptedDate").item(0).getTextContent()
            );

            this.id = id;
            this.entrants = entrants;
            this.specializationPlan = specialisationPlan;
            this.paid = paid;
            this.acceptedDate = acceptedDate;
        }
    }

    @Override
    public String toString() {
        return String.format("########EntrantForm#########\nid: %d \nentrants: %s \nspecPlan: %s \npaid: %b \nacceptedDate: %s",
                this.id, entrants, this.specializationPlan, this.paid, this.acceptedDate.toString());
    }
}
