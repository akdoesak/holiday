package com.akdoes.holiday.service;

import com.akdoes.holiday.model.HolidayResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HolidayServiceTest {

    private final HolidayService holidayService = new HolidayService();

    @Test
    void shouldReturnNextCommonHolidayForPolandAndNorway() {
        // given
        String country1 = "PL";
        String country2 = "NO";
        String fromDate = "2016-01-01";

        // when
        HolidayResponse response = holidayService.findNextCommonHoliday(country1, country2, fromDate);

        // then
        assertNotNull(response, "Response should not be null");
        assertEquals("2016-03-27", response.date());
        assertEquals("Wielkanoc", response.name1());
        assertEquals("Første påskedag", response.name2());
    }
}
