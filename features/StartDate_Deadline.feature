Feature: Startdate & deadling
  Description: a project can be given a start date and a deadline


  Scenario: give project deadline successfully
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    And sets the deadline of the project to 22,11,2026
    Then the project has the deadline 22,11,2026


  Scenario: give project startdate
    Given a user with id "Huba" exists
    And a user with id "Huba" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    And sets the startdate of the project to 22,11,2026
    Then the project has the startdate 22,11,2026