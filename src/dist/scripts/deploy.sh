#!/bin/bash

SERVICE_NAME=${SERVICE_NAME-goto-services}

STAGE=${STAGE-dev}
STAGE=$(echo $STAGE | tr '[A-Z]' '[a-z]')
DEPLOY_DIR="vagrant@12.12.12.12:/$STAGE"

echo "create dist-$SERVICE_NAME.zip"
chmod +x ./dist/scripts/*.sh
zip dist-$SERVICE_NAME.zip ./dist

echo "deploy dist-$SERVICE_NAME.zip on stage $STAGE"
scp -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" dist-$SERVICE_NAME.zip $DEPLOY_DIR