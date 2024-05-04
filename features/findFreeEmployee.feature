Feature: Find free employee
  Description: User can find free employees for activity
  Actor: project leader

  Scenario: All project members available
      Given a user with id "HUBA" exists
      And a user with id "HUBA" is logged in
      And a project exists with title "Test"
      And 10 users are part of the project
      And the project has 10 activities with budget time 40
      And all the activities have start date 12,04,2023
      And all the activities have deadline 30,05,2025
      And the users have different amounts of activities
      And user creates an activity with name "Yoga" with time budget 1
      And the activity has start date 20,04,2024
      And the activity has deadline 30,04,2024
      When the user searches for free employee
      Then all users are returned
      And the returned users are sorted by amount of activities overlapping

  Scenario: Employee on leave
      Given a user with id "HUBA" exists
      And a user with id "HUBA" is logged in
      And a project exists with title "Test"
      And user "HUBA" is part of the project
      And user "HUBA" is on "sick" leave from 21,04,2024 to 22,04,2024
      And 10 users are part of the project
      And the project has 10 activities with budget time 40
      And all the activities have start date 12,04,2023
      And all the activities have deadline 30,05,2023
      And the users have different amounts of activities
      And user creates an activity with name "Yoga" with time budget 1
      And the activity has start date 20,04,2024
      And the activity has deadline 30,04,2024
      When the user searches for free employee
      Then "HUBA" is not found
      And the returned users are sorted by amount of activities overlapping

  Scenario: Activity with no start- or end-date
      Given a user with id "HUBA" exists
      And a user with id "HUBA" is logged in
      And a project exists with title "Test"
      And 10 users are part of the project
      And the project has 10 activities with budget time 40
      And all the activities have start date 12,04,2023
      And all the activities have deadline 30,05,2023
      And the users have different amounts of activities
      And user creates an activity with name "Yoga" with time budget 1
      When the user searches for free employee
      Then no users are found


