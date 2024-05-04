Feature: White box test for getUserFromID method

  Scenario: Input set A
    Given a user with id "ASGE" exists
    And a user with id "HUBA" exists
    And a user with id "NIKL" exists
    When the getUserFromId method is called with id "ASGER"
    Then the user with id "ASGE" is returned

  Scenario: Input set B
    Given a user with id "HUBA" exists
    And a user with id "AXXX" exists
    And a user with id "NIKL" exists
    When the getUserFromId method is called with id "A"
    Then the user with id "AXXX" is returned

  Scenario: Input set C
    Given a user with id "HUBA" exists
    And a user with id "NIKL" exists
    And a user with id "AXXX" exists
    When the getUserFromId method is called with id "ASGER"
    Then the exeption UserIdDoesNotExistException is thrown

  Scenario: Input set D
    Given a user with id "NIKL" exists
    And a user with id "ASGE" exists
    And a user with id "HUBA" exists
    When the getUserFromId method is called with id "A"
    Then the exeption UserIdDoesNotExistException is thrown

  Scenario: Input set E
    Given no users exist
    When the getUserFromId method is called with id "ASGER"
    Then the exeption UserIdDoesNotExistException is thrown