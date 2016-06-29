Feature: Basic Auth
  Scenario: Server returns 401 without authorization
    Given the server is running
    When I request "GET" "/logs"
    Then the response status should be 401
    And the response header should include "WWW-Authenticate" "Basic"
    And the response body should be empty

  Scenario: Server returns 200 with authorization
    Given the server is running
    And I have made additional requests
    When I request "GET" "/logs" with authorization
    Then the response status should be 200
    And the response body has log contents
