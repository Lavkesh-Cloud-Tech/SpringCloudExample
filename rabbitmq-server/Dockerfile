#!/bin/bash
FROM rabbitmq:3.6.9-management

COPY conf/rabbitmq.config /etc/rabbitmq/rabbitmq.config
COPY conf/custom_definitions.json /etc/rabbitmq/custom_definitions.json

COPY conf/entrypoint.sh /
RUN chmod +x /entrypoint.sh

EXPOSE 4369 5671 5672 25672 15671 15672
ENTRYPOINT ["/entrypoint.sh"]
CMD ["rabbitmq-server"]