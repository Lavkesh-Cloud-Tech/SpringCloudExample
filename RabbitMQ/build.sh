#!/bin/bash
set -e

echo "======================== Start build image ======================="
docker build --rm -t lavkesh/rabbitmq-server:$1 .
echo "Image build => lavkesh/rabbitmq:$1"