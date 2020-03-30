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

    ValuteService valuteService;

    @Autowired
    public void setValuteService(ValuteService valuteService) {
        this.valuteService = valuteService;
    }

    @GetMapping()
    public String main () {
        List<ValuteDto> list = valuteService.getAllByDate(valuteService.getValidDate().getDate());
        return "index";
    }

}
