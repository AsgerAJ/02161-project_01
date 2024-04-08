Feature: Register a user
  Description: Register a user to application
  Actor: User

  Scenario: Successfully register a user
    Given no user with id "Huba" exists
    When a user registers with user id "Huba"
    Then a user is registered with id "Huba"
