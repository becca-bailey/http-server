Feature: PUT to /form
  Scenario: PUT returns 200 status
    Given the server is running
    When I "PUT" "my=data" to "/form"
    Then the response status should be 200
    And the response body should be empty