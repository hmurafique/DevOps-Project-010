FROM tomcat:10.1-jdk21
# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy war file
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
