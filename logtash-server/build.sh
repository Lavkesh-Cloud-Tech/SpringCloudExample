#!/bin/bash
set -e

echo "======================== Start build image ======================="
docker build --rm -t lavkesh/logtash-server:$1 .
echo "Image build => lavkesh/logtash-server:$1"