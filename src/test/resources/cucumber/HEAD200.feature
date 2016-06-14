Feature: HEAD /
  Scenario: Server responds to HEAD request with 200
    Given the server is running
    When I request "HEAD" "/"
    Then the response status should be 200
    And the response body should be empty