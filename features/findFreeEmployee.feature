Feature: Find free employee
  Description: User can find free employees for activity
  Actor: project leader

  Scenario: All project members available
      Given 10 users exist
      And a project exists with title "Test"
      And all users are part of the project
      And the project has 10 activities
      And all the activies have start date 