Feature: Driver service

  Scenario: Create Driver by not existing username and not existing phone
    When Create driver with username "tserashkevich" and phone "+375484848484"
    Then Create DriverResponse should contains driver with username "tserashkevich" and phone "+375484848484"

  Scenario: Create Driver by existing phone
    When Create driver with username "Peter Jones" and phone "+375447436112"
    Then ValidationErrorResponse should contains phone

  Scenario: Find Driver by existing ID
    When Find existing driver with ID "714de0de-3e65-42a7-b874-676c99ba6855"
    Then DriverResponse should contains driver with ID "714de0de-3e65-42a7-b874-676c99ba6855"

  Scenario: Find Driver by not existing ID
    When Find non existing driver with ID "99999999-9999-9999-9999-999999999999"
    Then Find should be thrown DriverNotFoundException

  Scenario: Update Driver by existing ID and not existing phone
    When Update driver method is called with ID "714de0de-3e65-42a7-b874-676c99ba6855" and DriverRequest of username "USERNAME2" and phone "+375222222222"
    Then DriverResponse should contains driver with ID "714de0de-3e65-42a7-b874-676c99ba6855" and username "USERNAME2" and phone "+375222222222"

  Scenario: Edit Driver by existing phone
    When Update driver method is called with ID "11111111-1111-1111-1111-111111111111" and DriverRequest of username "USERNAME3" and phone "+375337436112"
    Then Update ValidationErrorResponse should contains phone

  Scenario: Delete Driver by existing ID
    When Delete driver method is called with ID "ced8b8a3-d691-4e57-adec-2c615d7ee3c1"
    Then Driver should return No Content

  Scenario: Delete Driver by not existing ID
    When Delete driver method is called with ID "99999999-9999-9999-9999-999999999999"
    Then Delete should be thrown DriverNotFoundException