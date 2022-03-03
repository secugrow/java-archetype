# SecuGrow Java

Selenium Cucumber Java

Sample Project for Selenium with Cucumber in Java (there is also a Kotlin Project on github from us)

Running your tests just start with maven or your IDE (a sample RunConfig is in ressource Folder)

Add more tests:

* Write scenarios and features
* Create new steps (group your steps and use lambda notation)
* create PageObjects with page functions

Usage Tips:

If you have actions which change the page your last stagement in your function should instantiate the new page like
``` new PageObject(session);```

If doing like this, the currentPage is set correctly, and you can access the page in your step files with getPage(PageObject.class)

## Building the archetype

- make sure you're in the root of the Project(where the pom.xml is located) and do:
  ```shell
  mvn archetype:create-from-project
  ```
- cd into
  ```
  target/generated-sources/archetype
  ``` 
- execute:
  ```shell
  mvn clean install
  ```  
  If build succeeds, we have a new archetype in our repository,
  e.g.: `<path/to/your/maven/repository>/at/co/boris/SecuGrow-archetype/1.0-SNAPSHOT/SecuGrow-archetype-1.0-SNAPSHOT.jar`
- cd into directory you want to start a project from the archetype and execute following command:
  ```shell
  mvn archetype:generate -DarchetypeArtifactId=SecuGrow-archetype -DarchetypeGroupId=at.co.boris -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=at.some.test -DartifactId=secuavaTestArchetype -DinteractiveMode=false
  ```

