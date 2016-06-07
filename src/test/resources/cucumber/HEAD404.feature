Feature: HEAD /foobar
  Scenario: Server responds to HEAD /foobar with 404
    Given the server is running on port 5000
    When I request "HEAD" "/foobar"
    Then the response status should be 404
    And the response body should be ""