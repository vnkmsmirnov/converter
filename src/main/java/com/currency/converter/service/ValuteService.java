package com.currency.converter.service;

import com.currency.converter.domain.dto.ValuteDto;
import com.currency.converter.repos.ValuteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ValuteService {

    private ValuteRepo valuteRepo;

    @Autowired
    public void setValuteRepo(ValuteRepo valuteRepo) {
        this.valuteRepo = valuteRepo;
    }

    public ValuteDto getValuteByName(String name) {
        return valuteRepo.findByName(name);
    }

    public List<ValuteDto> getAllByDate(Timestamp date) {
        return valuteRepo.findAllByDate(date);
    }

    public List<ValuteDto> getAll() {
        return (List<ValuteDto>)valuteRepo.findAll();
    }

    public ValuteDto saveOrUpdate(ValuteDto valute) {
        ValuteDto valuteDto = valuteRepo.findByName(valute.getName());
        valuteDto.setIdValute(valute.getIdValute());
        valuteDto.setNumCode(valute.getNumCode());
        valuteDto.setCharCode(valute.getCharCode());
        valuteDto.setNominal(valute.getNominal());
        valuteDto.setValue(valute.getValue());
        return valuteRepo.save(valuteDto);
    }

    public ValuteDto save(ValuteDto valute) {
        return valuteRepo.save(valute);
    }

}
