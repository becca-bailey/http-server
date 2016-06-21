Feature: POST GET PUT GET DELETE GET
  Scenario: /form responds with 200
    Given the page content of "/form" is empty
    When I request "GET" "/form"
    Then the response status should be 200
    And the response body should be empty

  Scenario: POST to /form
    When I "POST" "data=fatcat" to "/form"
    Then the response status should be 200

  Scenario: GET form after POST
    When I request "GET" "/form"
    Then the response status should be 200
    And the response body should be "data=fatcat"

  Scenario: PUT form data
    When I "PUT" "data=heathcliff" to "/form"
    Then the response status should be 200

  Scenario: DELETE form data
    When I request "DELETE" "/form"
    Then the response status should be 200

  Scenario: GET form after DELETE
    When I request "GET" "/form"
    Then the response status should be 200
    And the response body should be empty
