Feature: Echo Server

  Scenario Outline: Server returns echo response
    Given the server is running on port 5000
    And the client connects to the server on port 5000
    When the user inputs "<text>"
    Then the server echos "<text>"
    Examples:
      | text              |
      | hello             |
      | this is some text |