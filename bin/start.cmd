set JREBELLICENSE_DIR=%~dp0%
set CLASSPATH=.;%JREBELLICENSE_DIR%lib\*
echo %CLASSPATH%
java -cp %classpath%; Main 8888
