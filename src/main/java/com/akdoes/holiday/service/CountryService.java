package com.akdoes.holiday.service;

import com.akdoes.holiday.exception.CountryNotAvailableException;
import com.akdoes.holiday.model.CountryInfo;
import com.akdoes.holiday.model.Holiday;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private static final String API_URL ="https://date.nager.at/api/v3/PublicHolidays/{year}/{country}";
    private static final String API_COUNTRIES ="https://date.nager.at/api/v3/AvailableCountries";

    
    @Cacheable("availableCountries")
    public Set<CountryInfo> getAvailableCountries() throws IOException, InterruptedException {
        HttpResponse<String> response = getResponse(API_COUNTRIES);

        CountryInfo[] countries = objectMapper.readValue(response.body(), CountryInfo[].class);
        return Set.of(countries);
    }

    @Cacheable(value = "holidays", key = "#countryCode + '-' + #year")
    public List<Holiday> fetchHolidays(String countryCode, int year) throws Exception {
        if(isValidCountry(countryCode)){
        String url = API_URL.replace("{year}", String.valueOf(year))
                .replace("{country}", countryCode);

        HttpResponse<String> response = getResponse(url);

        Holiday[] countries = objectMapper.readValue(response.body(), Holiday[].class);
        return List.of(countries);}
        else{
            throw new CountryNotAvailableException("Invalid country code");
        }
    }

    public boolean isValidCountry(String countryCode) throws Exception {
        return getAvailableCountries().stream()
                .anyMatch(country -> country.countryCode().equals(countryCode));

    }

    private HttpResponse<String> getResponse(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    }
}

