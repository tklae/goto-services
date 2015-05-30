#!/bin/bash

SERVICE_NAME=${GO_PIPELINE_NAME-local}
DEPLOY_DIR=/prod/$SERVICE_NAME

echo "run $SERVICE_NAME service"
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12 "unzip /prod/dist-$SERVICE_NAME.zip -d $DEPLOY_DIR"
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12 "nohup java -jar $DEPLOY_DIR/app-$SERVICE_NAME.jar server $DEPLOY_DIR/config-$SERVICE_NAME.yml &> /dev/null &"