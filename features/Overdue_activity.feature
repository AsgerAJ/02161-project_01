Feature: Overdue activity
  Description: a activity can be overdue



  Scenario: activity not complete but  overdue
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the user creates a project with title "TestProject"
    And user creates an activity with name "Yoga" with time budget 1
    And user sets activity deadline to 20,10,2023
    And the activity is not set to complete
    When the date is 21,10,2026
    Then the activity is overdue

  Scenario: activity complete and not overdue
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the user creates a project with title "TestProject"
    And user creates an activity with name "Yoga" with time budget 1
    And user sets activity deadline to 20,10,2023
    And the user marks the activity as complete
    When the date is 21,10,2022
    Then the activity is not overdue


  Scenario: activity complete and past deadline (not overdue)
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the user creates a project with title "TestProject"
    And user creates an activity with name "Yoga" with time budget 1
    And user sets activity deadline to 20,10,2023
    And the user marks the activity as complete
    When the date is 21,10,2026
    Then the activity is not overdue

  Scenario: activity not complete  and not past deadline
    Given a user with id "HUBA" exists
    And a user with id "HUBA" is logged in
    And the user creates a project with title "TestProject"
    And user creates an activity with name "Yoga" with time budget 1
    And user sets activity deadline to 20,10,2023
    And the activity is not set to complete
    When the date is 21,10,2022
    Then the activity is not overdue