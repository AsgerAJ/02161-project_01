Feature: Create project
Description: Create a Project
  Actor: User

  Scenario: Create project successfully
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And the date is 10,04,2024
    And 1 year(s) pass(es)
    When the user creates a project with title "TestProject"
    Then the project is created in app
    And the project is given the id 250001
    And the project is added to user projects.
    And the user is added to the project participant list