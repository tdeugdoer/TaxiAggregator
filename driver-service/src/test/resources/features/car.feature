Feature: Car service

  Scenario: Create car by not existing number and existing driver
    When Create car with number "number2024" and driver "661f45e3-a203-4de2-bd11-c77ba0bd7052"
    Then Create CarResponse should contains passenger with number "number2024" and driver "661f45e3-a203-4de2-bd11-c77ba0bd7052"

  Scenario: Create car by existing number and not existing driver
    When Create car with number "D101EE" and driver "99999999-9999-9999-9999-999999999999"
    Then ValidationErrorResponse should contains number and driver

  Scenario: Find car by existing ID
    When Find existing car with ID "1"
    Then CarResponse should contains car with ID "1"

  Scenario: Find car by not existing ID
    When Find non existing car with ID "1000"
    Then Find should be thrown CarNotFoundException

  Scenario: Update car by existing ID and not existing number and existing driver
    When Update car method is called with ID "3" and CarRequest of number "number2025" and driver "661f45e3-a203-4de2-bd11-c77ba0bd7052"
    Then CarResponse should contains car with ID "3" and number "number2025" and driver "661f45e3-a203-4de2-bd11-c77ba0bd7052"

  Scenario: Edit Passenger by existing number and non existing driver
    When Update car method is called with ID "1" and CarRequest of number "B456CC" and driver "11111111-1111-1111-1111-111111111111"
    Then Update ValidationErrorResponse should contains number and driver

  Scenario: Delete Passenger by existing ID
    When Delete car method is called with ID "4"
    Then Car should return No Content

  Scenario: Delete Passenger by not existing ID
    When Delete car method is called with ID "1000"
    Then Delete should be thrown CarNotFoundException