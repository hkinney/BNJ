cls
echo off

REM JMETRA_HOME may be changed to the absolute location of jmetra, but we will
REM assume that the examples are placed directly below it, and use a relative path
set JMETRA_HOME=C:\jmetra

REM MUST SPECIFY THE JAVA HOME
REM  FILL IN JAVA_HOME HERE !!!!!!!!!!!!!!!!
set JAVA_HOME=C:\jdk

if "%JAVA_HOME%" == "" goto noJavaHome


REM ** TOOLS FOR BUILDING JAVA APPLICATION
set TPARTY_CLASSPATH=
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\jmetra.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\xml-apis.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\xercesImpl.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\jasper-compiler.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\jasper-runtime.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\servlet.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\log4j.jar
set TPARTY_CLASSPATH=%TPARTY_CLASSPATH%;%JMETRA_HOME%\lib\freemarker.jar


set CLASSPATH=%TPARTY_CLASSPATH%

REM **** The following are variables are for the command line arguments.
REM **** They should be set for each project that JMetra is used for.
set SOURCE_KEYWORD=java
set PROJECT=.
set METRICS_DIR=%PROJECT%\metrics
set AGGRMETRICS_DIR=%METRICS_DIR%\aggrbuild
set DOCOUTPUT_DIR=%METRICS_DIR%\docs
set CONFIG_FILE=jmetraconfig.xml

REM  making a metrics file given the current snapshot of the code
REM explicitly identify the release dates since these are archived pieces of the source code

REM set LABEL=v1 don't use label.... defaults to timestamp
REM set RELEASE_DATE=2002.01.05.000000 don't specified timestamp, defaults to current time.
set SOURCEROOT_DIR=%PROJECT%\src edu
%JAVA_HOME%\bin\java com.jmetra.JMetraMaker -verbose -rootsource %SOURCEROOT_DIR% %SOURCE_KEYWORD% %METRICS_DIR%
REM %JAVA_HOME%\bin\java com.jmetra.JMetraMaker -verbose -rootsource %SOURCEROOT_DIR% %SOURCE_KEYWORD% %METRICS_DIR%
REM for aggregating a list of metrics files from various snapshots through time
REM note, it looks like it doesn't event use the second argument, it jsut appends
REM aggrbuild on to it.
%JAVA_HOME%\bin\java com.jmetra.JMetraMaker -recent 7 -verbose -aggr %METRICS_DIR% %AGGRMETRICS_DIR%
REM for generating metricdoc based on the current aggregate builds
%JAVA_HOME%\bin\java com.jmetra.JMetraDoc  -verbose  %AGGRMETRICS_DIR%  %DOCOUTPUT_DIR%


goto end


:noJavaHome
if "%_JAVACMD%" == "" set _JAVACMD=java
echo.
echo Warning: JAVA_HOME environment variable is not set.
echo   Please specify the JAVA_HOME variable in this file.
echo
echo
echo.

:end