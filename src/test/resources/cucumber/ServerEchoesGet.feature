Feature: GET /echo
  Scenario: Server returns echo response
    Given the server is running
    When I request "GET" "/echo"
    Then the response status should be 200
    And the response body should be empty