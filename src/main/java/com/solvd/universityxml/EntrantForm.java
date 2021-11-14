package com.solvd.universityxml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "entrantForm")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntrantForm {

    @XmlAttribute(name = "id")
    private int id;

    @JsonProperty("entrants")
    @XmlElementWrapper(name = "entrants")
    @XmlElement(name = "entrant")
    private List<Entrant> entrants;

    @JsonProperty("specialisationPlan")
    @XmlElement(name = "specialisationPlan")
    private SpecialisationPlan specializationPlan;
    private boolean paid;

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
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
    public String toString() {
        return String.format("########EntrantForm#########\nid: %d \nentrants: %s \nspecPlan: %s \npaid: %b \nacceptedDate: %s",
                this.id, entrants, this.specializationPlan, this.paid, this.acceptedDate.toString());
    }
}
