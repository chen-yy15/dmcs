#!/usr/bin/env bash
git pull
mvn clean package
java -jar dmcs.jar