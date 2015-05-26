#!/bin/bash

GO_PIPELINE_NAME=${GO_PIPELINE_NAME-local}

echo "copy files for dist.zip"
cp ./src/dist/config/dev.yml config-$GO_PIPELINE_NAME.yml
cp ./build/libs/$GO_PIPELINE_NAME-fat.jar app-$GO_PIPELINE_NAME.jar

echo "create dist.zip"
zip dist-$GO_PIPELINE_NAME.zip config-$GO_PIPELINE_NAME.yml app-$GO_PIPELINE_NAME.jar

echo "deploy dist.zip (wherever)"
# need an ssh key
scp dist-$GO_PIPELINE_NAME.zip vagrant@12.12.12.12:/prod
