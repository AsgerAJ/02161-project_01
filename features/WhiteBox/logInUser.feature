Feature: White box test for logInUser method

  Scenario: Input set A
    Given a user with id "NVT" exists
    And a user with id "NEL" exists
    And a user with id "NEL" is logged in
    When logging in with id "NVT"
    Then the exeption AUserIsAlreadyLoggedInException is thrown

  Scenario: Input set B
    Given a user with id "NVT" exists
    And a user with id "NEL" exists
    And a user with id "AAJ" exists
    And a user with id "LOAN" exists
    And no user is logged in
    When logging in with id "NVT"
    Then a user with id "NVT" is logged in

Scenario: Input set C
    Given a user with id "NEL" exists
    And a user with id "AAJ" exists
    And a user with id "LOAN" exists
    And no user is logged in
    When logging in with id "NVT"
    Then the exeption UserIdDoesNotExistException is thrown

