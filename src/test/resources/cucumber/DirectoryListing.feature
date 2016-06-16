Feature: Directory Listing
  Scenario: Server responds to GET / with directory listing
    Given the server is running
    When I request "GET" "/"
    Then the response status should be 200
    And the response body has directory contents "public"

  Scenario Outline: Directory contains file listings
    When I request "GET" "/"
    Then the response status should be 200
    And the response body has directory link "<file>"
    Examples:
      | file           |
      | /file1         |
      | /file2         |
      | /image.gif     |
      | /image.png     |
      | /text-file.txt |
