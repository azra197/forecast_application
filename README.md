# Forecast Application  

## Overview  
This application provides weather forecasts using data retrieved from [WeatherAPI.com](https://www.weatherapi.com/). The data is updated every hour, and users can access weather information through two different endpoints.  

## Features  
- Fetches and updates weather data hourly.  
- Provides a 3-day forecast with max, min, and average temperatures, along with current weather conditions.  
- Allows users to request weather data for a specific day, displaying max, min, and average temperatures, as well as current conditions.   

## How It Works  
1. The application fetches weather data every hour.  
2. The data is stored and made available via the exposed REST API endpoints.  
3. Users can retrieve general forecasts or forecasts for a specific day within the next three days.  

## API Access
1. http://localhost:8080/forecast - 3-day forecast.
2. http://localhost:8080/weather/{day} – Forecast for a specific day (e.g., Monday, Tuesday, Wednesday).

## Technologies Used
- Java, Spring Boot – Backend framework
- WeatherAPI.com – External weather data provider
- REST API – Structured data retrieval

## Setup & Running the Project
1. Clone the repository:
```
git clone https://github.com/azra197/forecast_application.git
```
2. Navigate to the project directory:
```
cd forecast_application
```
3. Build and run the application:
```
mvn spring-boot:run
```
4. Access the API at
```
http://localhost:8080/forecast
```
or
```
http://localhost:8080/weather/{day}
```

## Example Response
GET /weather/Monday
```
Forecast for monday: Max temperature: 5.0, Min temperature: -1.8, Average temperature: 2.0, Condition: Patchy rain nearby
```
