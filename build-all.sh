#!/bin/bash

function buildPoject() {
  echo ""
  echo ""
  local arg=$1
  echo "Building $arg"
  buildArray=(`echo $arg | sed 's/:/\n/g'`)

  cd ${buildArray[0]}
  echo "In ${buildArray[0]} Folder"
  sh ./build.sh ${buildArray[1]}

  cd ..
}

version=$1
buildPoject rabbitmq-server:$version
buildPoject security-common:$version
buildPoject eureka-server:$version
buildPoject config-server:$version
buildPoject test-service:$version
buildPoject security-service:$version
buildPoject api-gateway-service:$version

