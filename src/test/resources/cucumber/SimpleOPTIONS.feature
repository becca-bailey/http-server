Feature: OPTIONS
  Scenario Outline: Server returns options for /method_options
    Given the server is running
    When I request "OPTIONS" "/method_options"
    Then the response status should be 200
    And the response header should include "Allow" "<option>"
    Examples:
      | option  |
      | GET     |
      | HEAD    |
      | POST    |
      | OPTIONS |
      | PUT     |

  Scenario Outline: Server returns options for /method_options2
    Given the server is running
    When I request "OPTIONS" "/method_options2"
    Then the response status should be 200
    And the response header should include "Allow" "<option>"
    Examples:
      | option  |
      | GET     |
      | OPTIONS |
