Feature: Remove member from activity
  Description: users can be removed from activity participant lists
  Actor: User

  Scenario: Remove user from activity
    Given a user with id "HUBA" exists
    And a project exists with title "testProject"
    And the project has an activity with name "testActivity"
    And the user is added to the activity
    When the user is removed from the activity
    Then the user is no longer part of the activity