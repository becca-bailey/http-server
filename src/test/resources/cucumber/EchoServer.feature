Feature: Echo Server
  Scenario: Server returns echo response
    Given the server is running on port 5000
    When I "GET /echo"
    Then the response status should be "200 OK"
    And the response body should be empty