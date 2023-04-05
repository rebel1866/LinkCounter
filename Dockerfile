FROM ubuntu:latest
WORKDIR /opt
RUN apt-get update && apt-get upgrade && apt install -y openjdk-17-jdk openjdk-17-jre 
RUN apt install -y wget
RUN wget -c https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.73/bin/apache-tomcat-9.0.73.tar.gz
RUN tar -xvzf apache-tomcat-9.0.73.tar.gz 
RUN rm apache-tomcat-9.0.73.tar.gz 
RUN apt install -y maven
RUN cd ./apache-tomcat-9.0.73/bin/ && touch setenv.sh && echo "JRE_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64" >> setenv.sh && echo "CATALINA_PID="$CATALINA_BASE/tomcat.pid"" >> setenv.sh && chmod u+x setenv.sh
RUN mkdir app
COPY pom.xml ./app
COPY src ./app/src
RUN cd /opt/app && mvn package
RUN  mv /opt/app/target/LinkCounter.war /opt/apache-tomcat-9.0.73/webapps/links.war
EXPOSE 8080

CMD cd apache-tomcat-9.0.73/bin/ && ./startup.sh && /bin/bash
