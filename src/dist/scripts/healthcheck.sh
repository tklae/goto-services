#!/bin/bash

status="fail"
  max_checks=10

  set +e
  for i in `seq 1 $max_checks`; do
    echo "${i}. try"

    out=`curl -fsS --max-time 5 "http://localhost:8093/healthcheck"`

    if [ $? -eq 0 ]; then
      status="success"
      echo $status
      break
    else
      sleep 1
    fi
  done
  set -e

  if [ "${status}" != "success" ]; then
    fatal "healthcheck failed"
  fi