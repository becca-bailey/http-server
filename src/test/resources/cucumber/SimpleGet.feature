Feature: GET /
  Scenario: Server responds to simple GET with 200
    Given the server is running
    When I request "GET" "/"
    Then the response status should be 200