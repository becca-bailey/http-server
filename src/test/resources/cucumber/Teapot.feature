Feature: GET /coffee and /tea
  Scenario: Server returns 418 I'm a teapot
    Given the server is running
    When I request "GET" "/coffee"
    Then the response status should be 418
    And the response body should be "I'm a teapot"

  Scenario: Server returns 200 for /tea
    Given the server is running
    When I request "GET" "/tea"
    Then the response status should be 200