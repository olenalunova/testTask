
## How to run tests
1.  Download and install latest [Maven](https://maven.apache.org/download.cgi) version. Change PATH variable to include path to maven.
2.  Make sure that you've latest [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed and JAVA_HOME environment variable points to correct location. 
3.  Download latest version of [Selenium](http://selenium-release.storage.googleapis.com/index.html) standalone server, currently it's 2.53.1
4.  Start selenium server and node
    
    ```<folder where selenium-server-standalone-<version>.jar is located>
    java -jar selenium-server-standalone-2.53.1.jar -browserTimeout=60000 -role hub
    java -jar selenium-server-standalone-2.53.1.jar -role node  -hub http://localhost:4444/grid/register
	```    


5. Download [ChromeDriver](http://chromedriver.storage.googleapis.com/index.html?path=2.22/) and change PATH to include path to chromedriver.
6. Run tests by 
   
   ```
   cd <folder where pom.xml is located>
   mvn clean test -Dsurefire.rerunFailingTestsCount=3
 