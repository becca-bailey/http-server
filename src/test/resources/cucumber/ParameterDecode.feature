#Feature: Parameter Decode
#  Scenario Outline: Server returns parameters in body
#    Given the server is running
#    When I request "GET" "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff"
#    Then the response body should include "<decodedParameters>"
#    Examples:
#      | decodedParameters |
#      | variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: |
#      | is that all                                                    |
#      | variable_2 = stuff |