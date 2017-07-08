#!/bin/bash


function buildPoject() {
  local arg=$1
  echo "Building $arg"
  buildArray=(`echo $arg | sed 's/:/\n/g'`)

  cd ${buildArray[0]}
  echo "In ${buildArray[0]} Folder"
  sh ./build.sh ${buildArray[1]}

  cd ..
}

for arg in $@
do
  buildPoject $arg
done

