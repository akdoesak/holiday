# Holiday API

A Spring Boot application that provides an API for finding the next common holiday between two countries.

## Overview

This API leverages the [Nager.Date API](https://date.nager.at/Api) to fetch public holiday information for different countries and provides functionality to find the next common holiday between two specified countries after a given date.

## API Endpoints

### Get Next Common Holiday

Finds the next common holiday between two countries after a specified date.

```
GET /api/holidays
```

#### Query Parameters

| Parameter | Type   | Description                                                |
|-----------|--------|------------------------------------------------------------|
| country1  | String | ISO country code for the first country (e.g., "US", "DE")  |
| country2  | String | ISO country code for the second country (e.g., "FR", "IT") |
| date      | String | Start date in ISO format (YYYY-MM-DD)                      |

**Note**: The API validates country codes against the list of available countries from the Nager.Date API. If an invalid country code is provided, a 404 Not Found response will be returned.

#### Response

```json
{
  "date": "2023-12-25",
  "name1": "Christmas Day",
  "name2": "Natale"
}
```

- `date`: The date of the common holiday in ISO format (YYYY-MM-DD)
- `name1`: The name of the holiday in the first country
- `name2`: The name of the holiday in the second country

#### Error Responses

| Status Code | Description                                                |
|-------------|------------------------------------------------------------|
| 404         | Returned when an invalid country code is provided          |
| 500         | Returned when there is an internal server error            |

#### Example Request

```
GET /api/holidays?country1=US&country2=DE&date=2023-01-01
```

## Configuration

The application can be configured using the following properties in `application.properties`:

| Property                | Default | Description                                                |
|-------------------------|--------|------------------------------------------------------------|
| holiday.years-lookahead | 5      | Number of years to look ahead for common holidays          |

## Caching

The application implements caching for improved performance:

- Available countries are cached to reduce API calls to the Nager.Date API
- Holiday information for each country and year combination is cached

## Setup and Running

### Prerequisites

- Java 24 or higher
- Maven 3.6 or higher

### Building the Application

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on the default port 8080.

## Dependencies

- Spring Boot 3.4.5
- Spring Web
- Java 24 (with preview features)

## External APIs

This application uses the Nager.Date API to fetch holiday information and available countries:
- Holiday API URL: `https://date.nager.at/api/v3/PublicHolidays/{year}/{country}`
- Available Countries API URL: `https://date.nager.at/api/v3/AvailableCountries`
- Documentation: [Nager.Date API Documentation](https://date.nager.at/Api)

## Examples

### Finding the next common holiday between the US and Germany

```
GET /api/holidays?country1=US&country2=DE&date=2023-01-01
```

This will return the next holiday that is celebrated in both the United States and Germany after January 1, 2023.
