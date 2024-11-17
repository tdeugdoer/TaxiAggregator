Feature: End to End Tests

  Scenario: Create passenger
    Given PassengerRequest with name "test1" and phone "+375292078118"
    When Send create request to passenger-service with passengerRequest
    Then PassengerResponse should not be null
    And PassengerResponse should contains name "test1"
    And PassengerResponse should contains phone "+375292078118"

  Scenario: Create driver and car
    Given DriverRequest with name "test1", phone "+375292078103" and car number "test1995"
    When Send create request to driver-service with driverRequest
    Then DriverResponse should not be null
    And DriverResponse should contains name "test1"
    And DriverResponse should contains phone "+375292078103"
    And DriverResponse should contains car number "test1995"

  Scenario: Create ride
    Given CreateRideRequest with startGeoPoint "53.928990,27.587178" and endGeoPoint "53.891457,27.549987"
    When Send create request to ride-service with createRideRequest
    Then CreateRideResponse should not be null
    And CreateRideResponse should contains startGeoPoint "53.92918125|27.587633952568417"
    And CreateRideResponse should contains endGeoPoint "53.89113|27.549815"

  Scenario: Added driver to ride
    When Send add driver request to ride-service with rideId and driverId
    Then RideResponse should not be null
    And RideResponse should contains the sent driverId

  Scenario: Passenger try rate not finished ride
    Given CreateRatingRequest with comment "Good" and rating "9"
    When Send bad create comment request to ride-service with createRatingRequest
    Then ExceptionResponse should contains RideNotFinishedException

  Scenario: Finished ride
    When Send change status request to ride-service with rideId and status "FINISHED"
    Then RideResponse should not be null
    And RideResponse should contains status "FINISHED"

  Scenario: Passenger rate ride
    Given CreateRatingRequest with comment "Good" and rating "9"
    When Send create comment request to ride-service with createRatingRequest

  Scenario: Find rating
    When Send find all request to rating-service with sourceId and rideId
    Then RatingResponse should not be null
    And RatingResponse should contains rating "9"