Feature: Create project
Description: Create a Project
  Actor: User



  Scenario: Create project successfully (present)
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    Then the project is created in app
    And the project is given the id 240001
    And the project is added to user projects.
    And the user is added to the project participant list

  Scenario: Create project successfully (future)
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And the date is 10,04,2024
    And 1 year(s) pass(es)
    When the user creates a project with title "TestProject"
    Then the project is created in app
    And the project is given the id 250001
    And the project is added to user projects.
    And the user is added to the project participant list


    Scenario: Create project with deadline
      Given a user with id "Huba" exists
      And a user with id "Huba" is logged in
      And the date is 10,04,2024
      When the user creates a project with title "TestProject"
      And sets the deadline of the project to 22,11,2026
      Then the project has the deadline 22,11,2026