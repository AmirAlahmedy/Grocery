package com.grocery.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestClient restClient;

    public WeatherService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.weather.gov")
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
                .build();
    }

    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public String getWeatherForecastByLocation(
            double latitude,   // Latitude coordinate
            double longitude   // Longitude coordinate
    ) {
        // Returns detailed forecast including:
        // - Temperature and unit
        // - Wind speed and direction
        // - Detailed forecast description

        try {
            // Step 1: Get grid point data for the coordinates
            String pointsEndpoint = String.format("/points/%.4f,%.4f", latitude, longitude);
            var pointsResponse = restClient.get()
                    .uri(pointsEndpoint)
                    .retrieve()
                    .body(Map.class);

            if (pointsResponse == null || !pointsResponse.containsKey("properties")) {
                return "Unable to retrieve grid point data for the specified coordinates.";
            }

            // Step 2: Extract the forecast URL from the points response
            Map<String, Object> properties = (Map<String, Object>) pointsResponse.get("properties");
            String forecastUrl = (String) properties.get("forecast");

            if (forecastUrl == null || forecastUrl.isEmpty()) {
                return "Forecast data not available for the specified location.";
            }

            // Step 3: Get the forecast data
            var forecastResponse = restClient.get()
                    .uri(forecastUrl.replace("https://api.weather.gov", ""))
                    .retrieve()
                    .body(Map.class);

            if (forecastResponse == null || !forecastResponse.containsKey("properties")) {
                return "Unable to retrieve forecast data.";
            }

            // Step 4: Extract forecast periods
            Map<String, Object> forecastProperties = (Map<String, Object>) forecastResponse.get("properties");
            List<Map<String, Object>> periods = (List<Map<String, Object>>) forecastProperties.get("periods");

            if (periods == null || periods.isEmpty()) {
                return "No forecast periods available.";
            }

            // Step 5: Format the response with the first period (current/next forecast)
            Map<String, Object> currentPeriod = periods.get(0);

            StringBuilder result = new StringBuilder();
            result.append("Weather Forecast for ")
                    .append(latitude)
                    .append(", ")
                    .append(longitude)
                    .append("\n\n");

            result.append("Period: ").append(currentPeriod.get("name")).append("\n");
            result.append("Temperature: ").append(currentPeriod.get("temperature")).append("°").append(currentPeriod.get("temperatureUnit")).append("\n");

            // Wind information
            String windSpeed = (String) currentPeriod.get("windSpeed");
            String windDirection = (String) currentPeriod.get("windDirection");
            result.append("Wind: ").append(windSpeed).append(" ").append(windDirection).append("\n");

            // Forecast description
            result.append("\nForecast: ").append(currentPeriod.get("detailedForecast")).append("\n");

            return result.toString();

        } catch (Exception e) {
            return "Error retrieving weather forecast: " + e.getMessage();
        }
    }

    @Tool(description = "Get weather alerts for a US state")
    public String getAlerts(
            @ToolParam(description = "Two-letter US state code (e.g. CA, NY)") String state
    ) {
        // Returns active alerts including:
        // - Event type
        // - Affected area
        // - Severity
        // - Description
        // - Safety instructions

        try {
            // Validate state code format
            if (state == null || state.length() != 2) {
                return "Invalid state code. Please provide a two-letter US state code (e.g. CA, NY).";
            }

            // Convert to uppercase for consistency
            String stateCode = state.toUpperCase();

            // Get alerts for the specified state
            String alertsEndpoint = "/alerts/active/area/" + stateCode;
            var alertsResponse = restClient.get()
                    .uri(alertsEndpoint)
                    .retrieve()
                    .body(Map.class);

            if (alertsResponse == null || !alertsResponse.containsKey("features")) {
                return "Unable to retrieve alerts data.";
            }

            List<Map<String, Object>> features = (List<Map<String, Object>>) alertsResponse.get("features");

            if (features == null || features.isEmpty()) {
                return "No active weather alerts for " + stateCode + ".";
            }

            StringBuilder result = new StringBuilder();
            result.append("Active Weather Alerts for ").append(stateCode).append("\n\n");

            int alertCounter = 1;
            for (Map<String, Object> feature : features) {
                Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
                if (properties == null) continue;

                result.append("ALERT #").append(alertCounter++).append("\n");
                result.append("Event: ").append(properties.get("event")).append("\n");

                // Affected area
                String areaDesc = (String) properties.get("areaDesc");
                result.append("Area: ").append(areaDesc).append("\n");

                // Severity
                String severity = (String) properties.get("severity");
                result.append("Severity: ").append(severity).append("\n");

                // Description
                String description = (String) properties.get("description");
                if (description != null && !description.isEmpty()) {
                    result.append("\nDescription: ").append(description).append("\n");
                }

                // Safety Instructions (sometimes called instruction)
                String instruction = (String) properties.get("instruction");
                if (instruction != null && !instruction.isEmpty()) {
                    result.append("\nSafety Instructions: ").append(instruction).append("\n");
                }

                result.append("\n----------------------------\n\n");
            }

            return result.toString();

        } catch (Exception e) {
            return "Error retrieving weather alerts: " + e.getMessage();
        }
    }

}