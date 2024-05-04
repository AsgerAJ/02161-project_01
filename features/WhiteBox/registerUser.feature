Feature: White box test for registerUser method

  Scenario: Input set A
    Given a user with id "ANDE" exists
    And a user with id "GUST" exists
    And a user with id "NIKH" exists
    When the registerUser method is called with id "JON"
    Then user with id "JONX" is added to the userList
    And the user with id "JONX" is returned

  Scenario: Input set B
    Given a user with id "ANDE" exists
    And a user with id "GUST" exists
    And a user with id "NIKH" exists
    When the registerUser method is called with id "JONATAN"
    Then user with id "JONA" is added to the userList
    And the user with id "JONA" is returned

  Scenario: Input set C
    Given a user with id "ANDE" exists
    And a user with id "JONA" exists
    And a user with id "NIKH" exists
    When the registerUser method is called with id "JONATAN"
    Then the errormessage "UserId already in use" is given

  Scenario: Input set D
    Given a user with id "ANDE" exists
    And a user with id "JONX" exists
    And a user with id "NIKH" exists
    When the registerUser method is called with id "JON"
    Then the errormessage "UserId already in use" is given