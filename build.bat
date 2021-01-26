echo off
echo NUL>_.class&&echo NUL>_.log&&del /s /f /q *.class&&del /s /f /q *.log
cls
javac -cp com/krzem/mario/modules/purejavahidapi.jar;com/krzem/mario/modules/jna-4.0.0.jar; com/krzem/mario/*.java&&java -cp com/krzem/mario/modules/purejavahidapi.jar;com/krzem/mario/modules/jna-4.0.0.jar; com/krzem/mario/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"