Feature: Add member to activity
  Description: users can be added to activity participant lists
  Actor: User


  Scenario: Add user to activity
      Given a user with id "HUBA" exists
      And a project exists with title "testProject"
      And the project has an activity with name "testActivity"
      When the user is added to the activity
      Then the user is part of the activity