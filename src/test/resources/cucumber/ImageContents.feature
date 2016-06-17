Feature: Image Contents
  Scenario Outline: Server outputs image
    Given the server is running
    When I request "GET" "<imageFile>"
    Then the response body has file contents "<imageFile>"
    Examples:
      | imageFile    |
      | /image.jpeg |
      | /image.png  |
      | /image.gif  |