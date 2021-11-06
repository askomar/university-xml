package com.solvd.universityxml;

import java.time.LocalDate;
import java.util.Objects;

public class Entrant {
    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate dateOfBirth;

    public Entrant(Integer id, String surname, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Entrant(Integer id, String surname, String name, String patronymic, LocalDate dateOfBirth) {
        this(id, surname, name, dateOfBirth);
        this.patronymic = patronymic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s %s %s %s",
                this.id, this.surname, this.name, !Objects.isNull(this.patronymic) ? this.patronymic : "", this.dateOfBirth
        );
    }
}
