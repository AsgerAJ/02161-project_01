Feature: Complete Activity, Author: Asger
  Scenario: complete activity
    Given a user with id "HUBA" exists
    And a project exists with title "Free time"
    And the user is part of the project
    And the project has an activity with name "Yoga"
    When user marks activity "Yoga" as complete
    Then the activity with name "Yoga" is marked as completed


    Scenario: Uncomplete activity
      Given a user with id "HUBA" exists
      And a user with id "HUBA" is logged in
      And a project exists with title "testProject"
      And the project has an activity with name "testActivity"
      And the user is assigned the activity
      And the user marks the activity as complete
      When the user uncompletes the activity
      Then the activity is not complete
