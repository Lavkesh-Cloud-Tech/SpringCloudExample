#!/bin/bash
FROM rabbitmq:3.6-management
RUN apt-get update && apt-get install -y dos2unix

COPY conf/rabbitmq.config /etc/rabbitmq/rabbitmq.config
COPY conf/custom_definitions.json /etc/rabbitmq/custom_definitions.json

COPY conf/entrypoint.sh /
RUN dos2unix /entrypoint.sh && chmod +x /entrypoint.sh

EXPOSE 4369 5671 5672 25672 15671 15672
ENTRYPOINT ["/entrypoint.sh"]
CMD ["rabbitmq-server"]