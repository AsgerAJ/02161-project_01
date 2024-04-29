Feature: WhiteBox test of partition method of quicksorting UserCount


  Scenario: Set name A
        Given the beginning index is 2
        And the end index i 1
        And the arraylist contains a dataPackage with a null user, and a count of 4
        And the arraylist contains a dataPackage with a null user, and a count of 2
        And the arraylist contains a dataPackage with a null user, and a count of 7
        When partition is called
        Then 2 is returned

  Scenario: Set name B

    Given the beginning index is 0
    And the end index i 4
    And the arraylist contains a dataPackage with a null user, and a count of 5
    And the arraylist contains a dataPackage with a null user, and a count of 4
    And the arraylist contains a dataPackage with a null user, and a count of 3
    And the arraylist contains a dataPackage with a null user, and a count of 2
    And the arraylist contains a dataPackage with a null user, and a count of 1
    When partition is called
    Then 2 is returned

  Scenario: Set name C
    Given the beginning index is 0
    And the end index i 4
    And the arraylist contains a dataPackage with a null user, and a count of 4
    And the arraylist contains a dataPackage with a null user, and a count of 4
    And the arraylist contains a dataPackage with a null user, and a count of 3
    And the arraylist contains a dataPackage with a null user, and a count of 2
    And the arraylist contains a dataPackage with a null user, and a count of 7
    When partition is called
    Then 4 is returned
