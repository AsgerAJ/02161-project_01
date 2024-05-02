Feature: Log worked time
    Description: Log time worked on an activity
    Actor: User

    Scenario: Log time to assigned activity
        Given a user with id "HUBA" exists
        And a user with id "HUBA" is logged in
        And a project exists with title "testProject"
        And the project has an activity with name "testActivity"
        And the user is assigned the activity
        When the user logs 3.5 hours of work to the activity
        Then the user has logged 3.5 hours of work to the activity

    Scenario: Log time to unassigned activity
        Given a user with id "HUBA" exists
        And a user with id "HUBA" is logged in
        And a project exists with title "testProject"
        And the project has an activity with name "testActivity"
        When the user logs 3.5 hours of work to the activity
        Then the user has loggged 3.5 hours of work to the activity
        #spelling error to avoid multiple step definitions


    Scenario: Log additional worked time
        Given a user with id "HUBA" exists
        And a user with id "HUBA" is logged in
        And a project exists with title "testProject"
        And the project has an activity with name "testActivity"
        And the user is assigned the activity
        And the user has loggged 3.5 hours of work to the activity
        When the user logs 3.5 hours of work to the activity
        Then the user has logged 7.0 hours of work to the activity
        #typo in "loggged" to avoid identical step notations