#!/bin/bash

LIB_DIR=`pwd`
SERVICE_ROOT=$LIB_DIR/../../../
source $LIB_DIR/common.sh

echo "create fatJar"
cd $SERVICE_ROOT
./gradlew clean fatJar

echo "copy jar and config file to deploy folder"
cd $SERVICE_ROOT
cp ./src/dist/config/dev.yml $DEPLOY_DIR/$SERVICE_NAME-config.yml
cp ./build/libs/goto-services-fat.jar $DEPLOY_DIR/$SERVICE_NAME-service.jar

fatal() {
  echo "FEHLER: $*"
  exit 1
}