Feature: Log in, Log out
  Description: log in and log out of app
  Actor: User

  Scenario: Log user in successfully
    Given no user is logged in
    And a user with id "HUBA" exists
    When logging in with id "HUBA"
    Then the current user has id "HUBA"

    Scenario: Log in user unsuccessfully
      Given no user is logged in
      And no user with id "HUBA" exists
      When logging in with id "HUBA"
      Then the errormessage "No user with UserId exists" is given
      
    Scenario: Log in attempt with already logged in user
        Given a user with id "HUBA" is logged in
        And a user with id "TEST" exists
        When logging in with id "Test"
        Then the errormessage "A user is already logged in" is given

    Scenario: Log out user
      Given a user with id "Huba" is logged in
      When logging out
      Then the user has logged out