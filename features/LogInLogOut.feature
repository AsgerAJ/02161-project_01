Feature: Log in, Log out
  Description: log in and log out of app
  Actor: User

  Scenario: Log user in successfully
    Given no user is logged in
    And a user with id "Huba" exists
    When logging in with id "Huba"
    Then the current user has id "Huba"

    Scenario: Log in user unsuccessfully
      Given no user is logged in
      And no user with id "Huba" exists
      When logging in with id "Huba"
      Then the errormessage "No user with UserId exists" is given

    Scenario: Log out user
      Given a user with id "Huba" is logged in
      When logging out
      Then the user has logged out