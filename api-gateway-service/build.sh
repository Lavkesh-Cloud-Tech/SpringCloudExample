#!/bin/bash
set -e

IMAGE_NAME=lavkesh/api-gateway-service
IMAGE_VERSION=$1

echo "======================== Start build application ====================="
./gradlew clean build -x test

echo "======================== Start build image ======================="
docker build --rm -t $IMAGE_NAME:$IMAGE_VERSION .
echo "Image build => $IMAGE_NAME:$IMAGE_VERSION"