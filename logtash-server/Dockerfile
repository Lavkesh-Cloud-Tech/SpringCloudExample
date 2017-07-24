#!/bin/bash
FROM logstash:5.5

RUN mkdir logtash_conf && apt-get update && apt-get install -y dos2unix

COPY conf/entrypoint.sh /
RUN dos2unix /entrypoint.sh && chmod +x /entrypoint.sh

COPY conf/logstash.conf /logtash_conf/

ENTRYPOINT ["/entrypoint.sh"]
CMD ["logstash", "-f", "/logtash_conf/logstash.conf"]