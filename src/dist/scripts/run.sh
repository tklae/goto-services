#!/bin/bash

GO_PIPELINE_NAME=${1-local}

echo "fetch dist.zip (from wherever)"
ssh vagrant@12.12.12.12
cd /prod

echo "unzip dist"
unzip dist-$GO_PIPELINE_NAME.zip -d $GO_PIPELINE_NAME

echo "run service"
java -jar $GO_PIPELINE_NAME/app-$GO_PIPELINE_NAME.jar server $GO_PIPELINE_NAME/config-$GO_PIPELINE_NAME.yml