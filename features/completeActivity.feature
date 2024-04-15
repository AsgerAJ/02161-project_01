Feature: Complete Activity, Author: Asger
  Scenario: complete activity
    Given a user with id "Huba" exists
    And a project exists with title "Free time"
    And the user is part of the project
    And the project has an activity with name "Yoga"
    When user marks activity "Yoga" as complete
    Then the activity with name "Yoga" is marked as completed