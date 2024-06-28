#!/bin/bash

# Build Docker images
docker build -f Dockerfile-8040 -t front1-app-8040 .
docker build -f Dockerfile-8041 -t front1-app-8041 .

# Stop and remove existing container for port 8040
docker stop front1-app-8040 || true
docker rm front1-app-8040 || true

# Run container for port 8040
docker run -d -p 8040:8040 --name front1-app-8040 \
  -e NAVER_ID = ${NAVER_ID} \
  -e NAVER_SECRET = ${NAVER_SECRET} \
  -e NHN_IMAGE_SECRET = ${NHN_IMAGE_SECRET} \
  -e NHN_IMAGE_APPKEY = ${NHN_IMAGE_APPKEY} \
  front1-app-8040

# Wait for 30 seconds
sleep 30

# Stop and remove existing container for port 8041
docker stop front1-app-8041 || true
docker rm front1-app-8041 || true

# Run container for port 8041
docker run -d -p 8041:8041 --name front1-app-8041 \
  -e NAVER_ID = ${NAVER_ID} \
  -e NAVER_SECRET = ${NAVER_SECRET} \
  -e NHN_IMAGE_SECRET = ${NHN_IMAGE_SECRET} \
  -e NHN_IMAGE_APPKEY = ${NHN_IMAGE_APPKEY} \
  front1-app-8041