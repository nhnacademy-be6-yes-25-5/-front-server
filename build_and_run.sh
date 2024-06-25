#!/bin/bash

# Build Docker images
docker build -f DockerFile-8040 -t front1-app-8040 .
docker build -f DockerFile-8041 -t front1-app-8041 .

# Stop and remove existing containers
docker stop front1-app-8040 front1-app-8041
docker rm front1-app-8040 front1-app-8041

# Run containers
docker run -d -p 8040:8040 --name front1-app-8040 front1-app-8040
docker run -d -p 8041:8041 --name front1-app-8041 front1-app-8041