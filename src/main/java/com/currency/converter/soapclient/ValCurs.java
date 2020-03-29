package com.currency.converter.soapclient;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name="ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCurs implements Serializable {

    @XmlAttribute(name = "Date")
    private String date;

    @XmlElement(name = "Valute")
    private List<Valute> valutes;

    public List<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ValCurs() {}

    public ValCurs(String date, List<Valute> valutes) {
        this.date = date;
        this.valutes = valutes;
    }

    @Override
    public String toString() {
        return "ValCurs{" +
                "date='" + date + '\'' +
                ", valutes=" + valutes +
                '}';
    }
}
