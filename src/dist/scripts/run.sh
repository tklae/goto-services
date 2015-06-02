#!/bin/bash

SERVICE_NAME=${SERVICE_NAME-goto-services}
STAGE=${STAGE-dev}
STAGE=$(echo $STAGE | tr '[A-Z]' '[a-z]')
DEPLOY_DIR=/$STAGE/$SERVICE_NAME

echo "run $SERVICE_NAME service on stage $STAGE"
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12 "unzip -o /$STAGE/dist-$SERVICE_NAME.zip -d $DEPLOY_DIR"
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12 "/$STAGE/scripts/stop_$STAGE.sh"
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12 "/$STAGE/scripts/start_$STAGE.sh"