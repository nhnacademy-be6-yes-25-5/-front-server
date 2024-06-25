#!/bin/bash

# Build Docker images
docker build -f Dockerfile-8040 -t front1-app-8040 .
docker build -f Dockerfile-8041 -t front1-app-8041 .

# Stop and remove existing container for port 8040
docker stop front1-app-8040 || true
docker rm front1-app-8040 || true

# Run container for port 8040
docker run -d -p 8040:8040 --name front1-app-8040 front1-app-8040

# Wait for 30 seconds
sleep 30

# Stop and remove existing container for port 8041
docker stop front1-app-8041 || true
docker rm front1-app-8041 || true

# Run container for port 8041
docker run -d -p 8041:8041 --name front1-app-8041 front1-app-8041