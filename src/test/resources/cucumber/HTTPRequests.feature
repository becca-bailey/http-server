Feature: HTTP Requests

  Scenario: Server returns echo response
    Given the server is running on port 5000
    When I request "GET" "/echo"
    Then the response status should be 200
    And the response body should be empty

  Scenario: Server responds to simple GET with 200
    Given the server is running on port 5000
    When I request "GET" "/"
    Then the response status should be 200

  Scenario: Server responds to HEAD request with 200
    Given the server is running on port 5000
    When I request "HEAD" "/"
    Then the response status should be 200
    And the response body should be empty

  Scenario: Server responds to HEAD /foobar with 404
    Given the server is running on port 5000
    When I request "HEAD" "/foobar"
    Then the response status should be 404
    And the response body should be empty