FROM registry.access.redhat.com/ubi8/openjdk-11:latest
# RUN apk --no-cache add curl
COPY target/*.jar spring-boot-datagrid-challenge-0.0.2.jar
CMD java ${JAVA_OPTS} -jar spring-boot-datagrid-challenge-0.0.2.jar