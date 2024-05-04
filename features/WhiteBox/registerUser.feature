Feature: White box test for registerUser method

  Scenario: Input set A
    Given a user with id "ANDE" exists
    And a user with id "GUST" exists
    And a user with id "NIKH" exists
    When the registerUser method is called with id "JONA"
    Then user with id "JONA" is added to the userList
    And the user with id "JONA" is returned

  Scenario: Input set B
    Given a user with id "ANDE" exists
    And a user with id "JONA" exists
    And a user with id "NIKH" exists
    When the registerUser method is called with id "JONA"
    Then the errormessage "UserId already in use" is given
