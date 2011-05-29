#!/bin/sh
#自动更新为最新版本，并部署到tomcat

SRC_HOME="/home/sam/www/artrai/src/"
WWW_HOME="/home/sam/www/artrai/"

cd $SRC_HOME
#从SVN更新代码
svn update
#用Maven打包程序
mvn clean
mvn compile
cp -R $SRC_HOME/src/main/java/com $SRC_HOME/target/classes/
find $SRC_HOME/target/classes/com -name *.java |xargs rm -rf
mvn package

for tomcat in `ls $WWW_HOME |grep "tomcat"`
    do
        $WWW_HOME$tomcat/bin/shutdown.sh
        rm -rf $WWW_HOME$tomcat/webapps/ROOT*
done

for j in `lsof -F "c" -i :8080,8090 |grep -o "[0-9]\+"`
    do
        echo "shutdown java process "$j
        kill -9 $j
done

for tomcat in `ls $WWW_HOME |grep "tomcat-"`
    do
	cp $SRC_HOME"target/artrai-1.0.0.war" $WWW_HOME$tomcat/webapps/ROOT.war
        $WWW_HOME$tomcat/bin/startup.sh
done


