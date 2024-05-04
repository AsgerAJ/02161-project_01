Feature: White box test for getUserFromID method

  Scenario: Input set A
    Given a user with id "ASGE" exists
    And a user with id "HUBA" exists
    And a user with id "NIKL" exists
    When the getUserFromId method is called with id "ASGE"
    Then the user with id "ASGE" is returned

  Scenario: Input set B
    Given a user with id "HUBA" exists
    And a user with id "AXXX" exists
    And a user with id "NIKL" exists
    When the getUserFromId method is called with id "ASGE"
    Then the exeption UserIdDoesNotExistException is thrown

  Scenario: Input set C
    Given no users exist
    When the getUserFromId method is called with id "ASGER"
    Then the exeption UserIdDoesNotExistException is thrown
