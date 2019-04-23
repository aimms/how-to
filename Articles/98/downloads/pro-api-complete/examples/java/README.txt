1. Install maven (https://maven.apache.org/install.html)

2. Execute:
 2.a Run with maven:
   mvn compile
   mvn exec:java -Dexec.mainClass="com.aimms.proapiexample.Program" -Dexec.args="-l 'ws://my.pro.com' -e 'root' -u 'admin' -p 'admin' -a 'API Example' -v '1'"

 2.b Run with java: (Allows to execute example jar file from different computer)
   mvn package
   java -jar target/example-jar-with-dependencies.jar -l "ws://my.pro.com" -e "root" -u "admin" -p "admin" -a "API Example" -v "1"