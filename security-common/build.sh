#!/bin/bash
set -e

VERSION=$1

echo "======================== Start build application ====================="
./gradlew -Pversion=$VERSION clean build publishToMavenLocal