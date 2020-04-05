package com.currency.converter.controller;

import com.currency.converter.domain.dto.ValuteDto;
import com.currency.converter.service.ValuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public String main (Model model) {
        List<ValuteDto> list = valuteService.getAllByDate(valuteService.getValidDate().getDate());
        model.addAttribute("currency", list);
        return "index";
    }

    @PostMapping()
    public String calculate(@RequestParam("valute1") String valute1,
                            @RequestParam("valute2") String valute2,
                            @RequestParam(value = "have", defaultValue = "0") BigDecimal have,
                            @RequestParam(value = "get", defaultValue = "0") BigDecimal get,
                            Model model) {

        if (have.compareTo(new BigDecimal(0)) > 0) {
            ValuteDto currencyFrom = valuteService.getValuteByName(valute1);
            ValuteDto currencyTo = valuteService.getValuteByName(valute2);

            BigDecimal from = currencyFrom.getValue();
            BigDecimal to = currencyTo.getValue();

            String result = have.divide(to, 2, RoundingMode.HALF_DOWN).toString();

            model.addAttribute("get" , result);
            model.addAttribute("have" , have);
            model.addAttribute("currencyFrom", currencyFrom.getName());
            model.addAttribute("currencyTo", currencyTo.getName());
        } else {
            model.addAttribute("message", "Введите положительное число!");
        }
        model.addAttribute("currency", valuteService.getAllByDate(valuteService.getValidDate().getDate()));
        return "index";
    }

}
