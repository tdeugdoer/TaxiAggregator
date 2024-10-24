Feature: Ride service

  Scenario: Create Ride by existing passengerId
    When Create ride with passengerId "c69056ca-03a5-48e9-aa37-abd5656064f7"
    Then Create RideResponse should contains ride with passengerId "c69056ca-03a5-48e9-aa37-abd5656064f7"

  Scenario: Create Ride by not existing passengerId
    When Create ride with passengerId "d08835d7-bcb7-4c16-b50f-0c5d0973576c"
    Then ValidationErrorResponse should contains passengerId

  Scenario: Find Ride by existing ID
    When Find existing ride with ID "66e777326df3cf7ede379779"
    Then RideResponse should contains ride with ID "66e777326df3cf7ede379779"

  Scenario: Find Ride by not existing ID
    When Find non existing ride with ID "99999999-9999-9999-9999-999999999999"
    Then Find should be thrown RideNotFoundException

  Scenario: Delete Ride by existing ID
    When Delete ride method is called with ID "6713e512a276eb22b6b0cae6"
    Then Should return No Content

  Scenario: Delete Ride by not existing ID
    When Delete ride method is called with ID "9713e556876db16c0d63d8b1"
    Then Delete should be thrown RideNotFoundException