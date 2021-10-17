#!/bin/bash

mvn clean package

java -jar target/books-app-0.0.1-SNAPSHOT.jar
