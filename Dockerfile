#
##build jdk
#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} Joopging-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Joopging-0.0.1-SNAPSHOT.jar"]
#
#
##Nginx
#FROM nginx
#EXPOSE 80
#EXPOSE 443