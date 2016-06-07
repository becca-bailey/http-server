Feature: POST /echo
  Scenario: Server echoes "hello"
    Given the server is running on port 5000
    When I POST "parameter=hello" to "/echo"
    Then the response status should be 200
    And the response body should be "hello"

  Scenario: Server echoes "goodbye"
    Given the server is running on port 5000
    When I POST "parameter=goodbye" to "/echo"
    Then the response status should be 200
    And the response body should be "goodbye"
