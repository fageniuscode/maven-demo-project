call mvn clean
call mvn install
call mvn test
call mvn javadoc:javadoc
call mvn package
cd target
del /Q C:\m2gl\archives\*.war
copy maven-demo-project-0.0.1-SNAPSHOT-*.war C:\m2gl\archives
cd C:\Users\ibrah\OneDrive\Bureau\@Ibrahima-private\maven-demo\target
if exist ROOT.war (
    del ROOT.war
)
ren maven-demo-project-0.0.1-SNAPSHOT-*.war ROOT.war

