Feature: Passenger service

  Scenario: Create Passenger by not existing username and not existing phone
    When Create passenger with username "tserashkevich" and phone "+375484848484"
    Then Create PassengerResponse should contains passenger with username "tserashkevich" and phone "+375484848484"

  Scenario: Create Passenger by existing phone
    When Create passenger with username "John Doe" and phone "+375297435874"
    Then ValidationErrorResponse should contains phone

  Scenario: Find Passenger by existing ID
    When Find existing passenger with ID "0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5"
    Then PassengerResponse should contains passenger with ID "0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5"

  Scenario: Find Passenger by not existing ID
    When Find non existing passenger with ID "99999999-9999-9999-9999-999999999999"
    Then Find should be thrown PassengerNotFoundException

  Scenario: Update Passenger by existing ID and not existing phone
    When Update passenger method is called with ID "11111111-1111-1111-1111-111111111111" and PassengerRequest of username "USERNAME2" and phone "+375222222222"
    Then PassengerResponse should contains passenger with ID "11111111-1111-1111-1111-111111111111" and username "USERNAME2" and phone "+375222222222"

  Scenario: Edit Passenger by existing phone
    When Update passenger method is called with ID "11111111-1111-1111-1111-111111111111" and PassengerRequest of username "USERNAME3" and phone "+375331675879"
    Then Update ValidationErrorResponse should contains phone

  Scenario: Delete Passenger by existing ID
    When Delete passenger method is called with ID "11111111-1111-1111-1111-111111111111"
    Then Should return No Content

  Scenario: Delete Passenger by not existing ID
    When Delete passenger method is called with ID "99999999-9999-9999-9999-999999999999"
    Then Delete should be thrown PassengerNotFoundException