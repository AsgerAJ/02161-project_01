Feature: Create activity
  Description: Create a feature
  Actor: User

  Scenario: create activity as projectleader
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And a project exists with title "free time"
    And the user with id "Huba" is project leader
    When user creates an activity with name "Yoga" with start date 10,04,2024 and end date 11,04,2024 and with time budget 1
    Then the activity with name "Yoga" is added to the project