@echo off
set SVN_HOME=E:\jtiger\svn\server\bin
set CATALINA_HOME=E:\jtiger\apache-tomcat-6.0.29
set SRC_HOME=E:\jtiger\source

call %SVN_HOME%\svn update %SRC_HOME%
call %CATALINA_HOME%\bin\shutdown
call rmdir /Q /S %CATALINA_HOME%\webapps\ROOT
call del /Q %CATALINA_HOME%\webapps\ROOT.war
call mvn clean -f %SRC_HOME%\pom.xml
#call mvn compile -f %SRC_HOME%\pom.xml
#call robocopy /E %SRC_HOME%\src\main\java\com %SRC_HOME%\target\classes\com /xd .svn /xf *.java
call mvn package -f %SRC_HOME%\pom.xml
call copy %SRC_HOME%\target\artrai-1.0.0.war %CATALINA_HOME%\webapps\ROOT.war
call %CATALINA_HOME%\bin\startup