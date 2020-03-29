package com.currency.converter.domain.dto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "currency")
public class ValuteDto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "idvalute")
    private String idValute;

    @Column(name = "numcode")
    private int numCode;

    @Column(name = "charcode")
    private String charCode;

    @Column(name = "nominal")
    private int nominal;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "date")
    private Timestamp date;

    public ValuteDto() {
    }

    public ValuteDto(String idValute, int numCode, String charCode, int nominal, String name, BigDecimal value, Timestamp date) {
        this.idValute = idValute;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdValute() {
        return idValute;
    }

    public void setIdValute(String idValute) {
        this.idValute = idValute;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
