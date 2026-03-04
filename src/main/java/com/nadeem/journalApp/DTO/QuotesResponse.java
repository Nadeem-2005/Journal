package com.nadeem.journalApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * What this annotation does is that it tells spring to ignore unmentioned properties
 * For ex :- the api response consists of author and quote but since we only need quote, the below class
 * only has quote thus author is ignored
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class QuotesResponse {
    private String quote;
}

/***
 * For complex json responses , the below form of entity can be used
 * import com.fasterxml.jackson.annotation.JsonProperty;
 * import lombok.Data;
 *
 * import java.util.List;
 *
 * @Data
 * public class WeatherResponse {
 *
 *     private Request request;
 *     private Location location;
 *     private Current current;
 *
 *     @Data
 *     public static class Request {
 *
 *         private String type;
 *         private String query;
 *         private String language;
 *         private String unit;
 *     }
 *
 *     @Data
 *     public static class Location {
 *
 *         private String name;
 *         private String country;
 *         private String region;
 *         private String lat;
 *         private String lon;
 *
 *         @JsonProperty("timezone_id")
 *         private String timezoneId;
 *
 *         private String localtime;
 *
 *         @JsonProperty("localtime_epoch")
 *         private long localtimeEpoch;
 *
 *         @JsonProperty("utc_offset")
 *         private String utcOffset;
 *     }
 *
 *     @Data
 *     public static class Current {
 *
 *         @JsonProperty("observation_time")
 *         private String observationTime;
 *
 *         private int temperature;
 *
 *         @JsonProperty("weather_code")
 *         private int weatherCode;
 *
 *         @JsonProperty("weather_icons")
 *         private List<String> weatherIcons;
 *
 *         @JsonProperty("weather_descriptions")
 *         private List<String> weatherDescriptions;
 *
 *         @JsonProperty("wind_speed")
 *         private int windSpeed;
 *
 *         @JsonProperty("wind_degree")
 *         private int windDegree;
 *
 *         @JsonProperty("wind_dir")
 *         private String windDir;
 *
 *         private int pressure;
 *         private int precip;
 *         private int humidity;
 *         private int cloudcover;
 *         private int feelslike;
 *
 *         @JsonProperty("uv_index")
 *         private int uvIndex;
 *
 *         private int visibility;
 *
 *         @JsonProperty("is_day")
 *         private String isDay;
 *     }
 * }
 */