#Feature: Server
#  Scenario: The server will start with no parameters
#    When the server is called with no parameters
#    Then the server will run on port 5000
#
#  Scenario: The server accepts a port parameter
#    When the server is called with arguments "-p" "8000"
#    Then the server will run on port 8000
#
#  Scenario Outline: The server prints an error message given incorrect arguments
#    When the server is called with arguments "<parameter>" "<port>"
#    Then the server will display message "Usage: java -jar package/http-server-0.0.1.jar -p <port number>"
#    Examples:
#      | parameter | port |
#      | -p        | null |
#      | -a        | 8000 |
