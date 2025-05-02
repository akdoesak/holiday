package com.akdoes.holiday.service;

import com.akdoes.holiday.model.Holiday;
import com.akdoes.holiday.model.HolidayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class HolidayService {

    private static final String API_URL ="https://date.nager.at/api/v3/PublicHolidays/{year}/{country}";

    @Value("${holiday.years-lookahead}")
    private int yearsLookahead;

    private final RestTemplate restTemplate = new RestTemplate();


    public HolidayResponse findNextCommonHoliday(String country1, String country2, String fromDate) {
        Map<LocalDate, List<String>> holidayMap = new TreeMap<>();

        LocalDate from = LocalDate.parse(fromDate);
        for (int offset = 0; offset <= yearsLookahead; offset++) {
            int year = from.getYear() + offset;
            addToHolidayMap(holidayMap, fetchHolidays(country1, year));
            addToHolidayMap(holidayMap, fetchHolidays(country2, year));
        }
        return holidayMap.entrySet().stream()
                .filter(e -> e.getKey().isAfter(from) && e.getValue().size() > 1)
                .map(e -> new HolidayResponse(
                        e.getKey().toString(),
                        e.getValue().getFirst(),
                        e.getValue().get(1)
                ))
                .findFirst()
                .orElse(null);
    }

    private void addToHolidayMap(Map<LocalDate, List<String>> map, List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            map.computeIfAbsent(holiday.date(), k -> new ArrayList<>()).add(holiday.localName());
        }
    }

    private List<Holiday> fetchHolidays(String countryCode, int year) {
        Holiday[] holidays = restTemplate.getForObject(API_URL, Holiday[].class, year, countryCode);
        return holidays != null ? List.of(holidays) : List.of();
    }
}