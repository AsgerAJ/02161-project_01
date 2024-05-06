Feature: Create a user id
  Description: Create a user id for the user
  Actor: user

  Scenario: Successfully create a user id
    Given no user is logged in
    When the user enters "John Doe" as the user name
    Then the user id "JODO" is created


  Scenario: User inputs invalid name
    Given no user is logged in
    When the user enters "" as the user name
    Then the errormessage "Invalid user name" is given