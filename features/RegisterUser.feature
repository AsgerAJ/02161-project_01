Feature: Register a user
  Description: Register a user to application
  Actor: User

  Scenario: Successfully register a user
    Given no user with id "HUBA" exists
    When a user registers with user id "Huba"
    Then a user is registered with id "HUBA"

  Scenario: UserId already in use
    Given a user with id "HUBA" exists
    When a user registers with user id "Huba"
    Then the errormessage "UserId already in use" is given

  Scenario: UserId is too short
    Given no user with id "HUBX" exists
    When a user registers with user id "Hub"
    Then a user is registered with id "HUBX"
