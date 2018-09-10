#!/usr/bin/env bash
rm -f nohup.out
git pull
mvn clean package
nohup java -jar ./target/dmcs.jar &
echo $! > /var/run/Test.pid