Task: Transfer Money API

1.       APIs

Use Spring Boot or Mule to implement 2 APIs

API #1: Retrieve balance

******Just transactional api to read an amount 

API #2: Create transaction

******Transactional api to change an amount, use pending credit to prevent handling of table locking in real application, for now we just change the value in the table directly.

o   Perform transaction and return its result

o   The transaction result should include the balance after transaction, by calling API #1 via HTTP in API #2

****** just reuse code of API#1??

APIs should follow REST style and use JSON for the request and response format, and

have input and transaction validation, and

****** do this in both controller, service level
UUID??

have error handling

****** error code file for error code

§  returning appropriate HTTP status code

****** 403 for access error, 404 for not found, 400 for bad request, 500 should never return(i.e. no such thing or handled in error handling too), 200 for normal results/ error results

§  with documented error code and information in the JSON body

2.       API specifications

o   Write API specifications using RAML or Swagger

****** need to pick up RAML or Swagger

https://app.swaggerhub.com/apis/Gcommand/testAPI/0.1

3.       Tests

o   Create test cases on your APIs

 ****** JUnit, skip for now
 
 ****** write some test cases

In your project include a README.md, with

1.       Basic information about the project, e.g. how to run the API / tests

Steps to run the API,



README.md content


******
Swagger link for test project APIs: https://app.swaggerhub.com/apis/Gcommand/testAPI/0.1
token: testTokenTEST

This test project is build on Spring boot, so you will need the followings to run and test the project smoothly,
A. tools needed

1. Java 1.8 SDK
2. Mysql 8.0.3 (latest as of Jan 2019), run mysql server on port 3306, create a db of name res and restore the db from res_test.sql
3. REDIS 3.2 or above, config to default port 6379

B. Steps
1. run spring boot project either by using maven run or package the project and run the executable jar, you will being running on port 8080
2. by default, spring boot will run the test cases of Junit, but to save time, Junit is skipped, but I will provide some test cases
2.1. testing retrieveBalance API, API #1
 2.1.1. Success case, refer to the swagger documentation, success case would be a JSON with the correct token and UserID as parameter, they are both string for now
    {
    	"token": "testTokenTEST",
    	"UserID": "2"
    }
 -->
    {
        "result": true,
        "amount": 60.1,
        "userId": 2
    }
 2.1.2. Fail cases:
 missing 1 of those parameters
    {
    	"token": "testTokenTEST"
    }
 --> 
    {
       "result": false,
       "errorCode": "ER_0001:UserID"
    }
 wrong data format
{
	"token": "testTokenTEST",
	"UserID": "2dd"
}
    --> 
       {
           "result": false,
           "errorCode": "ER_0001"
       }
 wrong token
 {
 	"token": "xxx",
 	"UserID": "2"
 }
     --> 
        {
            "result": false,
            "errorCode": "ER_0001:token"
        }
2.2. testing retrieveBalance API, API #2
2.2.1. Success case would be a JSON with the correct token, changeAmount and UserID as parameter, they are all string for now
{
	"token": "testTokenTEST",
	"UserID": "2",
	"changeAmount": "10.1"
}
-->
{
    "result": true,
    "newAmount": 50.1,
    "userId": 2
}
2.2.2. Failed case are similar to API #1

2.3. Not found will return http 404.
2.4. Other internal server error not handled will return http 500.
******


3rd party library are all listed out in pom.xml, other external tools needed REDIS 3.2 or above, mysql 8 with db named: res