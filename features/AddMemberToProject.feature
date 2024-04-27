Feature: Add a member to project
  Description: Register a user to application
  Actor: User: Huba, User: Hobo

  Scenario: Successfully add a user to a project
    Given a user with id "HUBA" exists
    And a user with id "HOBO" exists
    And a user with id "HUBA" is logged in
    And a project exists with title "free time"
    And user "HUBA" is part of the project
    And user "HOBO" is not part of the project
    When user "HUBA" add's user "HOBO" to the project
    Then the user "HOBO" is added to the project participant list

  Scenario: User being added to the project can't be found
    Given a user with id "HUBA" exists
    And no user with id "HOBO" exists
    And a user with id "HUBA" is logged in
    And a project exists with title "free time"
    And user "HUBA" is part of the project
    When user "HUBA" add's user "HOBO" to the project
    Then the errormessage "No user with UserId exists" is given

  Scenario: User added twice
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And a project exists with title "test"
    And user "HUBA" is part of the project
    When user "HUBA" add's user "HUBA" to the project
    Then the participant list contains 1 user with userID "HUBA"