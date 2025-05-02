package com.akdoes.holiday.controller;

import com.akdoes.holiday.model.HolidayResponse;
import com.akdoes.holiday.service.HolidayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping
    public HolidayResponse getCommonHoliday(@RequestParam String country1,
                                            @RequestParam String country2,
                                            @RequestParam String date) {
        return holidayService.findNextCommonHoliday(country1, country2, date);
    }
}