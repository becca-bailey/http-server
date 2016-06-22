#Feature: Partial Content
#  Scenario Outline: Server returns partial file contents
#    Given the server is running
#    When I request "GET" "/partial_content.txt"
#    And I specify a range from <range_start> to <range_end>
#    Then the response status should be 206
#    And the body should include partial contents from <range_start> to <range_end>
#    Examples:
#      | range_start | range_end |
#      | 0           | 4         |
#      | 71          | 76        |
#      | 4           | 76        |