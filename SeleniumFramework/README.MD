![A simple Test Automation Framework  with the essential dependencies](test-automation-framework-skeleton-header.png)




### About the repo
Using Maven, it includes four main dependencies with their latest versions:
- TestNG: for a simple yet powerful test running experience
- Selenium: for browser-based front-end UI-focused tests
- RestAssured: for backend API-focused tests
- SLF4J with Log4j2: for logging and log management

### Using it

Prerequisites:
- Java JDK 17 installed
- Apache Maven installed
- Google Chrome installed. If you use other browsers, you will need to modify 
- eclipse need to be installed and testNG should download from marketplace 

Running the tests:
In project run .xml files separately for both API and  UI and u can see the results
using maven u can give the goal commands
for UI mvn test -DsuiteXmlFile=test/ui-tests.xml

for API mvn test -DsuiteXmlFile=test/api-tests.xml