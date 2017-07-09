#!/bin/bash
set -e

if [ -z "$RABBITMQ_USERNAME" ]; then
    #Fail
    echo "No RABBITMQ_USERNAME specified!"
    exit 1
fi

if [ -z "$RABBITMQ_PASSWORD_HASH" ]; then
    #Fail
    echo "No RABBITMQ_PASSWORD_HASH specified!"
    exit 1
fi

sed -i.bak "s|RABBITMQ_USERNAME|$RABBITMQ_USERNAME|g" /etc/rabbitmq/custom_definitions.json
sed -i.bak "s|RABBITMQ_PASSWORD_HASH|$RABBITMQ_PASSWORD_HASH|g" /etc/rabbitmq/custom_definitions.json

exec "$@"
