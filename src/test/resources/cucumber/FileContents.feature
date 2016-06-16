Feature: File Contents
  Scenario: Server outputs file contents for file path
    Given the server is running
    When I request "GET" "/file1"
    Then the response body has file contents "/file1"