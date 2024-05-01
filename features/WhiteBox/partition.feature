Feature: WhiteBox test of partition method of quicksorting UserCount


  Scenario: Set name A
        Given the beginning index is 2
        And the end index is 1
        And the arraylist contains a dataPackage with a null user, and a count of 4
        And the arraylist contains a dataPackage with a null user, and a count of 2
        And the arraylist contains a dataPackage with a null user, and a count of 7
        When partition is called
        Then 1 is returned
        #And the dataPackage at index 0 has count 4
        #And the dataPackage at index 1 has count 7
        #And the dataPackage at index 2 has count 2


  Scenario: Set name B

    Given the beginning index is 0
    And the end index is 4
    And the arraylist contains a dataPackage with a null user, and a count of 5
    And the arraylist contains a dataPackage with a null user, and a count of 4
    And the arraylist contains a dataPackage with a null user, and a count of 3
    And the arraylist contains a dataPackage with a null user, and a count of 2
    And the arraylist contains a dataPackage with a null user, and a count of 1
    When partition is called
    Then 0 is returned
    And the dataPackage at index 0 has count 1
    And the dataPackage at index 1 has count 4
    And the dataPackage at index 2 has count 3
    And the dataPackage at index 3 has count 2
    And the dataPackage at index 4 has count 5

  Scenario: Set name C
    Given the beginning index is 0
    And the end index is 4
    And the arraylist contains a dataPackage with a null user, and a count of 4
    And the arraylist contains a dataPackage with a null user, and a count of 4
    And the arraylist contains a dataPackage with a null user, and a count of 3
    And the arraylist contains a dataPackage with a null user, and a count of 2
    And the arraylist contains a dataPackage with a null user, and a count of 7
    When partition is called
    Then 4 is returned
    And the dataPackage at index 0 has count 4
    And the dataPackage at index 1 has count 4
    And the dataPackage at index 2 has count 3
    And the dataPackage at index 3 has count 2
    And the dataPackage at index 4 has count 7


