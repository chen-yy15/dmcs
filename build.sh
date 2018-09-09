#!/usr/bin/env bash
git pull
mvn clean package
nohup java -jar ./target/dmcs.jar &
echo $! > /var/run/Test.pid