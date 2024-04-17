Feature: overdue projects
  Description: a project can be overdue


  Scenario: Non-overdue project
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And a project exists with title "test Project"
    And the user is part of the project
    And the project has been given the deadline 15,4,2024
    When the date is 25,12,2026
    Then the project is overdue