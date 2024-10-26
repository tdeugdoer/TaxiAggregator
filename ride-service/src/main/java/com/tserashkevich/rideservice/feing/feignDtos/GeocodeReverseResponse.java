package com.tserashkevich.rideservice.feing.feignDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeocodeReverseResponse {
    @JsonProperty("results")
    private List<Result> results;
    private Query query;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Datasource datasource;
        private String name;
        private String country;
        @JsonProperty("country_code")
        private String countryCode;
        private String city;
        private String postcode;
        private String district;
        private double lon;
        private double lat;
        private int distance;
        @JsonProperty("result_type")
        private String resultType;
        private String formatted;
        @JsonProperty("address_line1")
        private String addressLine1;
        @JsonProperty("address_line2")
        private String addressLine2;
        private String category;
        private Timezone timezone;
        @JsonProperty("plus_code")
        private String plusCode;
        @JsonProperty("plus_code_short")
        private String plusCodeShort;
        private Rank rank;
        @JsonProperty("place_id")
        private String placeId;
        private Bbox bbox;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Datasource {
            private String sourcename;
            private String attribution;
            private String license;
            private String url;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Timezone {
            private String name;
            @JsonProperty("offset_STD")
            private String offsetSTD;
            @JsonProperty("offset_STD_seconds")
            private int offsetSTDSeconds;
            @JsonProperty("offset_DST")
            private String offsetDST;
            @JsonProperty("offset_DST_seconds")
            private int offsetDSTSeconds;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Rank {
            private double importance;
            private double popularity;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Bbox {
            private double lon1;
            private double lat1;
            private double lon2;
            private double lat2;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Query {
        private double lat;
        private double lon;
        @JsonProperty("plus_code")
        private String plusCode;
    }
}
