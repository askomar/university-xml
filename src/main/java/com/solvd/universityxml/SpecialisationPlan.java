package com.solvd.universityxml;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class SpecialisationPlan {

    private Specialisation specialisation;
    private SpecialisationPlanType specialisationPlanType;
    private Integer freePlacesAmount;
    private Integer paidPlacesAmount;
    private Double cost;
    private LocalDate lastUpdate;

    public SpecialisationPlan() {
    }

    public SpecialisationPlan(Specialisation specialisation, SpecialisationPlanType specialisationPlanType, Integer freePlacesAmount, Integer paidPlacesAmount, Double cost, LocalDate lastUpdate) {
        this.specialisation = specialisation;
        this.specialisationPlanType = specialisationPlanType;
        this.freePlacesAmount = freePlacesAmount;
        this.paidPlacesAmount = paidPlacesAmount;
        this.cost = cost;
        this.lastUpdate = lastUpdate;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public SpecialisationPlanType getSpecialisationPlanType() {
        return specialisationPlanType;
    }

    public void setSpecialisationPlanType(SpecialisationPlanType specialisationPlanType) {
        this.specialisationPlanType = specialisationPlanType;
    }

    public Integer getFreePlacesAmount() {
        return freePlacesAmount;
    }

    public void setFreePlacesAmount(Integer freePlacesAmount) {
        this.freePlacesAmount = freePlacesAmount;
    }

    public Integer getPaidPlacesAmount() {
        return paidPlacesAmount;
    }

    public void setPaidPlacesAmount(Integer paidPlacesAmount) {
        this.paidPlacesAmount = paidPlacesAmount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String toString() {
        return String.format(
                "Specialisation plan:\n\tspecialisation - %s\n\tspecialisationPlanType - %s\n\tfreePlacesAmount - %s\n\tpaid places amount - %d\n\tcost - %f\n\tlast update - %s",
                this.specialisation, this.specialisationPlanType.getName(), this.freePlacesAmount, this.paidPlacesAmount, this.cost, this.lastUpdate);
    }
}
