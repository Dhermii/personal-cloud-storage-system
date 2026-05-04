FROM tomcat:9.0

COPY dist/Personal_Cloud_Storage_System.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
