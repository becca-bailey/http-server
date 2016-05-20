Feature: Echo Server

  Scenario Outline: Server returns echo response
    Given the server is running on port <port>
    And the client connects to the server on port <port>
    When the user inputs "<text>"
    Then the server echos "<text>"
    Examples:
      | text              | port |
      | hello             | 5000 |
      | this is some text | 6000 |