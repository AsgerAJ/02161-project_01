Feature: Create activity
  Description: Create a feature
  Actor: User

  Scenario: create activity
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And a project exists with title "free time"
    And the user is part of the project
    When user creates an activity with name "Yoga" with time budget 1
    Then the activity with name "Yoga" is added to the project