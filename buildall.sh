#!/bin/bash
cd saiku-core
mvn clean install 
cd ..
cd saiku-webapp
mvn clean scm:checkout install
cd ..
cd saiku-ui
mvn clean package install:install-file -Dfile=target/saiku-ui-2.0-SNAPSHOT.war  -DgroupId=org.saiku -DartifactId=saiku-ui -Dversion=2.0-SNAPSHOT -Dpackaging=war
cd ../saiku-server
mvn clean package

