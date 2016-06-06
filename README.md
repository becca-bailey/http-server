# Becca's HTTP Server ![travis](https://travis-ci.org/beccanelson/http-server.svg?branch=fix-null-pointer)

This is a simple HTTP server written in Java and tested with [Cucumber](http://cucumber.io). 

## Dependencies:

+ [Maven](https://maven.apache.org/)
+ [Java](http://www.oracle.com/technetwork/systems/index-jsp-138363.html)

## To run the server:

With dependencies installed:
```
git clone git@github.com:beccanelson/http-server.git
cd http-server
mvn package
java -jar target/http-server-0.0.1.jar -p <port number> (default is 5000)
```

## To run tests:

In the `http-server` directory:
```
mvn test
```
