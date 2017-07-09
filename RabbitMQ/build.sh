#!/bin/bash
set -e

echo "======================== Start build image ======================="
docker build --rm -t lavkesh/rabbitmq:$1 .
echo "Image build => lavkesh/rabbitmq:$1"