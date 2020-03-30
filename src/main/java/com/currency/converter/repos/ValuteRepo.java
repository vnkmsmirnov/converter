package com.currency.converter.repos;

import com.currency.converter.domain.dto.ValuteDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ValuteRepo extends CrudRepository<ValuteDto, Long>, PagingAndSortingRepository<ValuteDto, Long> {
    ValuteDto findByName(String username);
    List<ValuteDto> findAllByDate(Timestamp date);
    ValuteDto findTopByOrderByIdDesc();
}
