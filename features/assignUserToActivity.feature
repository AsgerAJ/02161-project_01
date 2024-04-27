Feature: add user to acitvity participant list
  Description: employees can be added to acitvities
  Actor: User



  Scenario: Successfully add employee to activity
      Given a user with id "HUBA" exists
      And a project exists with title "test"
      And the user is part of the project
      And the project has an activity with name "act1"
      When the user is added to the activity
      Then the user is part of the activity
