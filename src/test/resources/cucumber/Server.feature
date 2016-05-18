Feature: Echo Server
  Scenario: Server returns echo response
    Given the server is running on port 5000
    And the client connects to the server on port 5000
    When the client inputs a string of text
    Then the server returns an echo
