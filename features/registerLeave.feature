Feature: Register leave
    Description: Register a period to be on leave from work
    Actor: User

    Scenario: Create leave
        Given a user with id "HUBA" exists
        And a user with id "HUBA" is logged in
        And a project exists with title "TestProject"
        And the user is part of the project
        When user "HUBA" goes on "sick" leave from 21,04,2024 to 22,04,2024
        Then the user has the "sick" even assigned to them
