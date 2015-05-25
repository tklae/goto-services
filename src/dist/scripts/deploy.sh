#!/bin/bash

LIB_DIR=`pwd`
source $LIB_DIR/common.sh

echo "copy files for dist.zip"
cp ./src/dist/config/dev.yml config-$GO_PIPELINE_NAME.yml
cp ./build/libs/*.jar app-$GO_PIPELINE_NAME.jar

echo "create dist.zip"
zip dist-$GO_PIPELINE_NAME.zip config-$GO_PIPELINE_NAME.yml app-$GO_PIPELINE_NAME.jar

echo "deploy dist.zip (wherever)"
scp dist-$GO_PIPELINE_NAME.zip vagrant@12.12.12.12:/prod
