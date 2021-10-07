#!/bin/bash

set -ex
USERNAME=marcorodnav
IMAGE=books-app

mvn clean package

docker build -t $USERNAME/$IMAGE:latest .
