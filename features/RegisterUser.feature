Feature: Register a user
  Description: Register a user to application
  Actor: User

  Scenario: Successfully register a user
    When a user registers with user id "Huba"
    And no user with id "Huba" exists
    Then a user is registered with id "Huba"
