call mvn clean
call mvn install
call mvn test
call mvn package
cd target
copy *.war C:\m2gl\archives
cd C:\Users\ibrah\OneDrive\Bureau\@Ibrahima-private\maven-demo\target
ren *.war ROOT.war

