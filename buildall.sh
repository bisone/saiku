#!/bin/bash
cd saiku-core
mvn clean install   
cd ..
cd saiku-webapp
mvn clean install
cd ..
git submodule init
git submodule update
cd saiku-ui
git checkout qm 
git pull origin qm
mvn clean package install:install-file -Dfile=target/saiku-ui-3.0-SNAPSHOT.war  -DgroupId=org.saiku -DartifactId=saiku-ui -Dversion=3.0-SNAPSHOT -Dpackaging=war
cd ../saiku-server
mvn clean package
cd ../saiku-bi-platform-plugin-util
mvn clean install
cd ../saiku-bi-platform-plugin
mvn clean package
