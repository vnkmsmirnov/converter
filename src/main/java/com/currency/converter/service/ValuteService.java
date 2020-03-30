package com.currency.converter.service;

import com.currency.converter.domain.dto.ValuteDto;
import com.currency.converter.repos.ValuteRepo;
import com.currency.converter.soapclient.ValCurs;
import com.currency.converter.soapclient.Valute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ValuteService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

    private SoapService soapService;
    private ValuteRepo valuteRepo;

    @Autowired
    public void setSoapService(SoapService soapService) {
        this.soapService = soapService;
    }

    @Autowired
    public void setValuteRepo(ValuteRepo valuteRepo) {
        this.valuteRepo = valuteRepo;
    }

    public ValuteDto getValidDate() {
        return valuteRepo.findTopByOrderByIdDesc();
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


    @Scheduled(cron="0 0 1 1/1 * ? *")
    private void scheduledUpdateOfCurrencyData() {
        ValCurs valCurs = getCurrentDataFromCb();
        Timestamp date = getDateFromValCurs(valCurs);
        List<ValuteDto> list = getAllByDate(date);
        if (list.size() == 0) {
            for (Valute valute : valCurs.getValutes()) {
                save(toValuteDto(valute, date));
            }
        } else {
            for (Valute valute : valCurs.getValutes()) {
                saveOrUpdate(toValuteDto(valute, date));
            }
        }
    }

    private ValCurs getCurrentDataFromCb () {
        String body = getBody();
        LocalDate localDate = LocalDate.now();
        String currentDate = localDate.format(DATE_TIME_FORMATTER);

        byte[] buffer = new byte[body.length()];

        try {
            buffer = body.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ValCurs valCurs = new ValCurs();

        try {
            URL u = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" +  currentDate);
            URLConnection uc = u.openConnection();
            HttpURLConnection connection = (HttpURLConnection) uc;
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/soap+xml;charset=UTF-8");

            try (OutputStream out = connection.getOutputStream()) {
                out.write(buffer);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader is = new InputStreamReader(connection.getInputStream(), "windows-1251")) {

                    try (BufferedReader rd = new BufferedReader(is)) {
                        String line;
                        String result = null;
                        while ((line = rd.readLine()) != null) {
                            result = new String(line.getBytes(StandardCharsets.UTF_8));
                        }
                        connection.disconnect();

                        valCurs = soapService.unmarshallXml(result);
                    }
                }
            } else {
                try (InputStreamReader eis = new InputStreamReader(connection.getErrorStream())) {
                    System.out.println(eis);
                } catch(IOException el) {
                    System.err.println(el);
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return valCurs;
    }

    private String getBody() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        sb.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
        sb.append("xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">").append("\n");
        sb.append("<soap:Body>").append("\n");
        sb.append("</soap:Body>").append("\n");
        sb.append("</soap:Envelope>").append("\n");
        return sb.toString();
    }

    private ValuteDto toValuteDto(Valute valute, Timestamp date) {
        ValuteDto valuteDto = new ValuteDto();
        valuteDto.setIdValute(valute.getId());
        valuteDto.setNumCode(valute.getNumCode());
        valuteDto.setCharCode(valute.getCharCode());
        valuteDto.setNominal(valute.getNominal());
        valuteDto.setName(valute.getName());
        valuteDto.setValue(new BigDecimal(valute.getValue().replace(",", ".")));
        valuteDto.setDate(date);
        return valuteDto;
    }

    private Timestamp getDateFromValCurs(ValCurs valCurs) {
        Date dateFormat = null;
        Timestamp date = null;
        try {
            dateFormat = DATE_FORMATTER.parse(valCurs.getDate());
            date = new Timestamp(dateFormat.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
