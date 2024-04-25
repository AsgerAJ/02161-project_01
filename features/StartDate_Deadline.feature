Feature: Startdate & deadling
  Description: a project can be given a start date and a deadline


  Scenario: give project deadline successfully
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    And sets the deadline of the project to 22,11,2026
    Then the project has the deadline 22,11,2026


  Scenario: give project startdate
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    And sets the startdate of the project to 22,11,2026
    Then the project has the startdate 22,11,2026


    Scenario: give deadline to project with startdate success
      Given a user with id "HUBA" exists
      And a user with id "HUBA" is logged in
      And the date is 10,04,2024
      When the user creates a project with title "TestProject"
      And sets the startdate of the project to 22,11,2024
      And sets the deadline of the project to 22,11,2026
      Then the project has the deadline 22,11,2026
      And the project has the startdate 22,11,2024

  Scenario: give startdate to project with deadline success
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    And sets the deadline of the project to 22,11,2026
    And sets the startdate of the project to 22,11,2024
    Then the project has the deadline 22,11,2026
    And the project has the startdate 22,11,2024

    Scenario: give deadline to project with startdate fail
      Given a user with id "HUBA" exists
      And a user with id "HUBA" is logged in
      And the date is 10,04,2024
      When the user creates a project with title "TestProject"
      And sets the startdate of the project to 22,11,2024
      And sets the deadline of the project to 22,11,2023
      Then the errormessage "deadline before start date" is given

  Scenario: give startdate to project with deadline fail
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the date is 10,04,2024
    When the user creates a project with title "TestProject"
    And sets the deadline of the project to 22,11,2023
    And sets the startdate of the project to 22,11,2024
    Then the errormessage "Startdate after deadline" is given
