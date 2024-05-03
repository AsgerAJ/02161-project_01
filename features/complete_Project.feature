Feature: complete project
  Description: a project can be marked as completed


  Scenario: complete project
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And a project exists with title "testProject"
    And the user is part of the project
    When the user marks the project as complete
    Then the project is marked as complete

  Scenario: uncomplete project
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And a project exists with title "testProject"
    And the user is part of the project
    And the user marks the project as complete
    When the user marks the project as incomplete
    Then the project is not complete
