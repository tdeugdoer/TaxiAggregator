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
public class RoutingResponse {
    private List<Feature> features;
    private Properties properties;
    private String type;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Feature {
        private String type;
        private Properties properties;
        private Geometry geometry;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Properties {
            private String mode;
            private List<Waypoint> waypoints;
            private String units;
            private int distance;
            @JsonProperty("distance_units")
            private String distanceUnits;
            private double time;
            private List<Leg> legs;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Waypoint {
                private List<Double> location;
                @JsonProperty("original_index")
                private int originalIndex;
            }

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Leg {
                private int distance;
                private double time;
                private List<Step> steps;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Step {
                    @JsonProperty("from_index")
                    private int fromIndex;
                    @JsonProperty("to_index")
                    private int toIndex;
                    private int distance;
                    private double time;
                    private Instruction instruction;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    public static class Instruction {
                        private String text;
                    }
                }
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Geometry {
            private String type;
            private List<List<List<Double>>> coordinates;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String mode;
        private List<Waypoint> waypoints;
        private String units;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Waypoint {
            private double lat;
            private double lon;
        }
    }
}
