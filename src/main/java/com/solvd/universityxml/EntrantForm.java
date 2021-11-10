package com.solvd.universityxml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement
public class EntrantForm {

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

    @XmlAttribute(name = "id")
    public void setId(int id) {
        this.id = id;
    }

    public List<Entrant> getEntrants() {
        return entrants;
    }

    @XmlElementWrapper(name = "entrants")
    @XmlElement(name = "entrant")
    public void setEntrants(List<Entrant> entrants) {
        this.entrants = entrants;
    }

    public SpecialisationPlan getSpecializationPlan() {
        return specializationPlan;
    }

    @XmlElement(name = "specialisationPlan")
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

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setAcceptedDate(LocalDate acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    @Override
    public String toString() {
        return String.format("########EntrantForm#########\nid: %d \nentrants: %s \nspecPlan: %s \npaid: %b \nacceptedDate: %s",
                this.id, entrants, this.specializationPlan, this.paid, this.acceptedDate.toString());
    }
}
