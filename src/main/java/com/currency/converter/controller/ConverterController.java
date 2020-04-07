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
        model.addAttribute("currency", valuteService.getAllByDate(valuteService.getValidDate()));
        return "index";
    }

    @PostMapping()
    public String calculate(@RequestParam("valuteFrom") String valuteFrom,
                            @RequestParam("valuteTo") String valuteTo,
                            @RequestParam(value = "have", defaultValue = "0") BigDecimal have,
                            Model model) {
        model.addAttribute("currencyFrom", valuteFrom);
        model.addAttribute("currencyTo", valuteTo);
        model.addAttribute("message", "Данные за " + valuteService.getFormattedValidDate());
        currencyCalculation(model, valuteFrom, valuteTo, have);
        model.addAttribute("currency", valuteService.getAllByDate(valuteService.getValidDate()));
        return "index";
    }

    private void currencyCalculation(final Model model, String valuteFrom, String valuteTo, BigDecimal have) {
        if (have.compareTo(BigDecimal.ZERO) > 0) {
            ValuteDto currencyFrom = valuteService.getValuteByName(valuteFrom);
            ValuteDto currencyTo = valuteService.getValuteByName(valuteTo);
            BigDecimal from = currencyFrom.getValue().divide(new BigDecimal(currencyFrom.getNominal()), 4, RoundingMode.HALF_UP);
            BigDecimal multiply = have.multiply(from);
            BigDecimal to = currencyTo.getValue().divide(new BigDecimal(currencyTo.getNominal()), 4, RoundingMode.HALF_UP);
            BigDecimal result = multiply.divide(to, 4, RoundingMode.HALF_UP).stripTrailingZeros();
            model.addAttribute("get" , result.toPlainString());
            model.addAttribute("have" , have.stripTrailingZeros().toPlainString());
        } else {
            model.addAttribute("message", "Введите положительное число!");
        }
    }

}
