Feature: Leave
    Description: Register and remove a period to be on leave from work
    Actor: User

    Scenario: Register leave
        Given a user with id "HUBA" exists
        And a user with id "HUBA" is logged in
        When user "HUBA" goes on "sick" leave from 21,04,2024 to 22,04,2024
        Then the user has the "sick" event assigned to them


    Scenario: Remove leave
        Given a user with id "HUBA" exists
        And a user with id "HUBA" is logged in
        And user "HUBA" goes on "sick" leave from 21,04,2024 to 22,04,2024
        When user "HUBA" removes "sick" leave from 21,04,2024 to 22,04,2024
        Then the user does not have the "sick" event assigned to them