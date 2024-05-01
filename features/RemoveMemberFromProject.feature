Feature: Remove a member from project
  Description: users can be removed from project participant lists
  Actor: User

  Scenario: Remove user from project
    Given a user with id "HUBA" exists
    And a project exists with title "testProject"
    And the user is part of the project
    When the user is removed from the project
    Then the user is no longer part of the project