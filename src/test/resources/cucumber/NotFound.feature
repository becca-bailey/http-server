Feature: HEAD /foobar
  Scenario: Server responds to GET /foobar with 404
    Given the server is running on port 5000
    When I request "GET" "/foobar"
    Then the response status should be 404
    And the response body should be empty