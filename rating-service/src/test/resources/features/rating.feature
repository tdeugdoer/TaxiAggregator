Feature: Rating service

  Scenario: Create Rating by not existing sourceId and not existing rideId
    When Create rating with sourceId "0a252c55-e2b9-472e-a218-fb9b006546d1" and rideId "42170c5af3d27e919f30b100"
    Then Create RatingResponse should contains rating with sourceId "0a252c55-e2b9-472e-a218-fb9b006546d1" and rideId "42170c5af3d27e919f30b100"

  Scenario: Create Rating by existing sourceId and rideId
    When Create rating with sourceId "bfabfb09-3b94-47c0-a201-b717cf9adcc1" and rideId "507f1f77bcf86cd799439011"
    Then ExceptionResponse should be thrown

  Scenario: Find Rating by existing ID
    When Find existing rating with ID "6b51fbb1-2a9f-4978-8455-c9a555223946"
    Then RatingResponse should contains rating with ID "6b51fbb1-2a9f-4978-8455-c9a555223946"

  Scenario: Find Rating by not existing ID
    When Find non existing rating with ID "99999999-9999-9999-9999-999999999999"
    Then Find should be thrown RatingNotFoundException

  Scenario: Update Rating by existing rideId and not existing sourceId
    When Update rating method is called with ID "d5cd9e9b-9fe6-471d-ae10-c5f3c8c965f1" and RatingRequest of sourceId "la276f41-8f73-4107-be9e-05779a1fe00e" and rideId "74480108d38569054192869f"
    Then RatingResponse should contains rating with ID "d5cd9e9b-9fe6-471d-ae10-c5f3c8c965f1" and sourceId "la276f41-8f73-4107-be9e-05779a1fe00e" and rideId "74480108d38569054192869f"

  Scenario: Update Rating by existing rideId and sourceId
    When Update rating method is called with ID "c9cd4246-035b-46f5-b792-1b0f32c9223e" and RatingRequest of sourceId "bfabfb09-3b94-47c0-a201-b717cf9adcc1" and rideId "507f1f77bcf86cd799439011"
    Then Update ExceptionResponse should be thrown

  Scenario: Delete Rating by existing ID
    When Delete rating method is called with ID "6788a6fe-ccf6-4243-973d-28719fe00541"
    Then Should return No Content

  Scenario: Delete Rating by not existing ID
    When Delete rating method is called with ID "99999999-9999-9999-9999-999999999999"
    Then Delete should be thrown RatingNotFoundException