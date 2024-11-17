Feature: Rating service

  Scenario: Create Rating by not existing sourceId and not existing rideId
    When Create rating with sourceId "0a252c55-e2b9-472e-a218-fb9b006546d1" and rideId "42170c5af3d27e919f30b100"
    Then Create RatingResponse should contains rating with sourceId "0a252c55-e2b9-472e-a218-fb9b006546d1" and rideId "42170c5af3d27e919f30b100"

  Scenario: Create Rating by existing sourceId and rideId
    When Create rating with sourceId "0a252c55-e2b9-472e-a218-fb9b006546d1" and rideId "42170c5af3d27e919f30b100"
    Then ExceptionResponse should be thrown

  Scenario: Find Rating by existing ID
    When Find existing rating with ID "6b51fbb1-2a9f-4978-8455-c9a555223946"
    Then RatingResponse should contains rating with ID "6b51fbb1-2a9f-4978-8455-c9a555223946"

  Scenario: Find Rating by not existing ID
    When Find non existing rating with ID "99999999-9999-9999-9999-999999999999"
    Then Find should be thrown RatingNotFoundException

  Scenario: Update Rating by existing rideId and not existing sourceId
    When Update rating method is called with ID "6b51fbb1-2a9f-4978-8455-c9a555223946" and RatingRequest of sourceId "85d3d192-bf92-43b6-ac12-141c85fc30e6" and rideId "74480108d38569054192869f"
    Then RatingResponse should contains rating with ID "6b51fbb1-2a9f-4978-8455-c9a555223946" and sourceId "85d3d192-bf92-43b6-ac12-141c85fc30e6" and rideId "74480108d38569054192869f"

  Scenario: Update Rating by existing rideId and sourceId
    When Update rating method is called with ID "6b51fbb1-2a9f-4978-8455-c9a555223946" and RatingRequest of sourceId "85d3d192-bf92-43b6-ac12-141c85fc30e6" and rideId "74480108d38569054192869f"
    Then Update ExceptionResponse should be thrown

  Scenario: Delete Rating by existing ID
    When Delete rating method is called with ID "6b51fbb1-2a9f-4978-8455-c9a555223946"
    Then Should return No Content

  Scenario: Delete Rating by not existing ID
    When Delete rating method is called with ID "99999999-9999-9999-9999-999999999999"
    Then Delete should be thrown RatingNotFoundException