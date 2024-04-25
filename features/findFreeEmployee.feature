Feature: Find free employee
  Description: User can find free employees for activity
  Actor: project leader

  Scenario: All project members available
      Given a project exists with title "Test"
      And 10 users are part of the project
      And the project has 10 activities with budget time 40
      And all the activities the project have start date 12,04,2023
      And the users have