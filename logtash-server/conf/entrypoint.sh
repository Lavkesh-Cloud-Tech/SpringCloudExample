#!/bin/bash
set -e

if [ -z "$RABBITMQ_USERNAME" ]; then
    #Fail
    echo "No RABBITMQ_USERNAME specified!"
    exit 1
fi

if [ -z "$RABBITMQ_PASSWORD" ]; then
    #Fail
    echo "No RABBITMQ_PASSWORD specified!"
    exit 1
fi

sed -i.bak "s|RABBITMQ_USERNAME|$RABBITMQ_USERNAME|g" /logtash_conf/logstash.conf
sed -i.bak "s|RABBITMQ_PASSWORD|$RABBITMQ_PASSWORD|g" /logtash_conf/logstash.conf

exec "$@"
