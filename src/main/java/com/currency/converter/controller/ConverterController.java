package com.currency.converter.controller;

import com.currency.converter.domain.dto.ValuteDto;
import com.currency.converter.service.ValuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class ConverterController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

    ValuteService valuteService;

    @Autowired
    public void setValuteService(ValuteService valuteService) {
        this.valuteService = valuteService;
    }

    @GetMapping()
    public String main () {
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DATE_TIME_FORMATTER);
        List<ValuteDto> list = valuteService.getAllByDate(getDate(date));
        return "index";
    }

    private Timestamp getDate(String date) {
        Date dateFormat = null;
        Timestamp result = null;
        try {
            dateFormat = DATE_FORMATTER.parse(date);
            result = new Timestamp(dateFormat.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}
