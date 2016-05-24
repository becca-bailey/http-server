Feature: Echo Server
  Scenario: Server returns echo response
    Given the server is running on port 5000
    And the client connects on port 5000
    When the user inputs "hello"
    Then the response is "Echo: hello"
