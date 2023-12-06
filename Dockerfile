FROM registry.vmgmedia.vn/library/java:openjdk-8u111-alpine

ENV HOST=0.0.0.0
WORKDIR /app
COPY ./deploy/lib /app/lib

EXPOSE 8002