[![Automatic Generation](https://github.com/secugrow/java-archetype/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/secugrow/java-archetype/actions/workflows/generate_archetype_output.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.secugrow/secugrow-java-archetype)](https://secugrow.io)


![SeCuGrow Logo](/docs/pics/SeCuGrow_Logo_300x150.png)
# Let your Selenium Cucumber Project grow
### Java Edition

### Looking for the documentation how to use it after generating an project from this archetype?
[follow this link](src/main/resources/archetype-resources/README.md)


## How to generate a ready to start project with this archetype

This archetype will generate you a Selenium Cucumber Skeleton for your projects with your choosen packagenames
A ready to use showcase ca be cloned/downloaded/forked from https://github.com/secugrow/generated-project.

### Installation
Before being able to make use of the `archetype:generate` command, the project has to be built from its root directory
via `mvn install`. This will result in a jar being copied to your local maven-repo.

OR

copy a jar from releases to your local maven repository in the correct path:

    <user dir, depens on your OS>/.m2/repository/io/secugrow/secugrow-java-archetype/<version>/secugrow-java-archetype-<version>.jar

OR

using a release archetype from a maven repository

You're all set - feel free to use the archetype.


## Use this archetype to generate a project

     mvn archetype:generate \  
        -DarchetypeArtifactId=secugrow-java-archetype \
        -DarchetypeGroupId=io.secugrow \
        -DarchetypeVersion=1.9.0 \
        -DgroupId=<your-group-id> \
        -DartifactId=<your artifactid> \
        -DinteractiveMode=false


example

     mvn archetype:generate \  
        -DarchetypeArtifactId=secugrow-java-archetype \
        -DarchetypeGroupId=io.secugrow \
        -DarchetypeVersion=1.9.0 \
        -DgroupId=io.secugrow.demo \
        -DartifactId=fromArchetype \
        -DinteractiveMode=false
