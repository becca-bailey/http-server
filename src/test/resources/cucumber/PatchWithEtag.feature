#Feature: PATCH with etag
#  Scenario: Server returns original content of text file
#    Given the server is running
#    And "/patch-content.txt" has original contents "default content"
#    When I request "GET" "/patch-content.txt"
#    Then the response status should be 200
#    And the response body should include "default content"
#
#  Scenario: PATCH to server
#    Given the server is running
#    When I "PATCH" "patched content" to "/patch-content.txt"
#    And I set the etag to "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec"
#    Then the response status should be 204
#
#  Scenario: Server returns patched content
#    When I request "GET" "/patch-content.txt"
#    Then the response status should be 200
#    And the response body should include "patched content"
#
#  Scenario: Content can be set back to default
#    When I "PATCH" "default content" to "/patch-content.txt"
#    And I set the etag to "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0"
#    Then the response status should be 204
#
#  Scenario: Default content
#    When I request "GET" "patch-content.txt"
#    Then the response status should be 200
#    And the file content is set back to "default content"