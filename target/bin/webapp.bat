@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup
set REPO=


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\springframework\spring-core\4.1.6.RELEASE\spring-core-4.1.6.RELEASE.jar;"%REPO%"\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;"%REPO%"\org\openimaj\core\1.3.1\core-1.3.1.jar;"%REPO%"\org\openimaj\core-citation\1.3.1\core-citation-1.3.1.jar;"%REPO%"\org\openimaj\core-aop-support\1.3.1\core-aop-support-1.3.1.jar;"%REPO%"\javassist\javassist\3.12.1.GA\javassist-3.12.1.GA.jar;"%REPO%"\org\jbibtex\jbibtex\1.0.2\jbibtex-1.0.2.jar;"%REPO%"\org\jsoup\jsoup\1.6.3\jsoup-1.6.3.jar;"%REPO%"\net\sf\trove4j\trove4j\3.0.2\trove4j-3.0.2.jar;"%REPO%"\com\google\guava\guava\14.0.1\guava-14.0.1.jar;"%REPO%"\colt\colt\1.2.0\colt-1.2.0.jar;"%REPO%"\concurrent\concurrent\1.3.4\concurrent-1.3.4.jar;"%REPO%"\log4j\log4j\1.2.14\log4j-1.2.14.jar;"%REPO%"\commons-lang\commons-lang\2.6\commons-lang-2.6.jar;"%REPO%"\org\apache\commons\commons-vfs2\2.0\commons-vfs2-2.0.jar;"%REPO%"\org\apache\maven\scm\maven-scm-api\1.4\maven-scm-api-1.4.jar;"%REPO%"\org\codehaus\plexus\plexus-utils\1.5.6\plexus-utils-1.5.6.jar;"%REPO%"\org\apache\maven\scm\maven-scm-provider-svnexe\1.4\maven-scm-provider-svnexe-1.4.jar;"%REPO%"\org\apache\maven\scm\maven-scm-provider-svn-commons\1.4\maven-scm-provider-svn-commons-1.4.jar;"%REPO%"\regexp\regexp\1.3\regexp-1.3.jar;"%REPO%"\commons-httpclient\commons-httpclient\3.1\commons-httpclient-3.1.jar;"%REPO%"\commons-codec\commons-codec\1.2\commons-codec-1.2.jar;"%REPO%"\com\esotericsoftware\kryo\kryo\2.21\kryo-2.21.jar;"%REPO%"\com\esotericsoftware\reflectasm\reflectasm\1.07\reflectasm-1.07-shaded.jar;"%REPO%"\org\ow2\asm\asm\4.0\asm-4.0.jar;"%REPO%"\com\esotericsoftware\minlog\minlog\1.2\minlog-1.2.jar;"%REPO%"\org\objenesis\objenesis\1.2\objenesis-1.2.jar;"%REPO%"\vigna\dsi\unimi\it\jal\20031117\jal-20031117.jar;"%REPO%"\org\apache\ant\ant\1.8.4\ant-1.8.4.jar;"%REPO%"\org\apache\ant\ant-launcher\1.8.4\ant-launcher-1.8.4.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.2.5\httpclient-4.2.5.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.2.4\httpcore-4.2.4.jar;"%REPO%"\org\openimaj\faces\1.3.1\faces-1.3.1.jar;"%REPO%"\org\openimaj\image-processing\1.3.1\image-processing-1.3.1.jar;"%REPO%"\org\openimaj\core-experiment\1.3.1\core-experiment-1.3.1.jar;"%REPO%"\org\openimaj\IREval\1.3.1\IREval-1.3.1.jar;"%REPO%"\org\apache\commons\commons-math\2.2\commons-math-2.2.jar;"%REPO%"\com\googlecode\jatl\jatl\0.2.1\jatl-0.2.1.jar;"%REPO%"\jfree\jfreechart\1.0.13\jfreechart-1.0.13.jar;"%REPO%"\jfree\jcommon\1.0.16\jcommon-1.0.16.jar;"%REPO%"\net\sf\jasperreports\jasperreports\4.6.0\jasperreports-4.6.0.jar;"%REPO%"\commons-beanutils\commons-beanutils\1.8.0\commons-beanutils-1.8.0.jar;"%REPO%"\commons-collections\commons-collections\2.1\commons-collections-2.1.jar;"%REPO%"\commons-digester\commons-digester\2.1\commons-digester-2.1.jar;"%REPO%"\com\lowagie\itext\2.1.7\itext-2.1.7.jar;"%REPO%"\bouncycastle\bcmail-jdk14\138\bcmail-jdk14-138.jar;"%REPO%"\bouncycastle\bcprov-jdk14\138\bcprov-jdk14-138.jar;"%REPO%"\org\bouncycastle\bctsp-jdk14\1.38\bctsp-jdk14-1.38.jar;"%REPO%"\org\bouncycastle\bcprov-jdk14\1.38\bcprov-jdk14-1.38.jar;"%REPO%"\org\bouncycastle\bcmail-jdk14\1.38\bcmail-jdk14-1.38.jar;"%REPO%"\xml-apis\xml-apis\1.3.02\xml-apis-1.3.02.jar;"%REPO%"\eclipse\jdtcore\3.1.0\jdtcore-3.1.0.jar;"%REPO%"\org\codehaus\castor\castor\1.2\castor-1.2.jar;"%REPO%"\org\apache\poi\poi-ooxml\3.7\poi-ooxml-3.7.jar;"%REPO%"\org\apache\poi\poi\3.7\poi-3.7.jar;"%REPO%"\org\apache\poi\poi-ooxml-schemas\3.7\poi-ooxml-schemas-3.7.jar;"%REPO%"\org\apache\xmlbeans\xmlbeans\2.3.0\xmlbeans-2.3.0.jar;"%REPO%"\stax\stax-api\1.0.1\stax-api-1.0.1.jar;"%REPO%"\org\apache\geronimo\specs\geronimo-stax-api_1.0_spec\1.0\geronimo-stax-api_1.0_spec-1.0.jar;"%REPO%"\dom4j\dom4j\1.6.1\dom4j-1.6.1.jar;"%REPO%"\org\codehaus\jackson\jackson-core-asl\1.9.4\jackson-core-asl-1.9.4.jar;"%REPO%"\org\codehaus\jackson\jackson-mapper-asl\1.9.4\jackson-mapper-asl-1.9.4.jar;"%REPO%"\org\bethecoder\ascii-table\1.0\ascii-table-1.0.jar;"%REPO%"\org\openimaj\core-feature\1.3.1\core-feature-1.3.1.jar;"%REPO%"\org\openimaj\core-image\1.3.1\core-image-1.3.1.jar;"%REPO%"\net\billylieurance\azuresearch\azure-bing-search-java\0.12.0\azure-bing-search-java-0.12.0.jar;"%REPO%"\net\sourceforge\jeuclid\jeuclid-core\3.1.9\jeuclid-core-3.1.9.jar;"%REPO%"\org\apache\xmlgraphics\batik-svg-dom\1.7\batik-svg-dom-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-anim\1.7\batik-anim-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-awt-util\1.7\batik-awt-util-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-css\1.7\batik-css-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-dom\1.7\batik-dom-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-xml\1.7\batik-xml-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-ext\1.7\batik-ext-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-parser\1.7\batik-parser-1.7.jar;"%REPO%"\org\apache\xmlgraphics\batik-util\1.7\batik-util-1.7.jar;"%REPO%"\xml-apis\xml-apis-ext\1.3.04\xml-apis-ext-1.3.04.jar;"%REPO%"\org\apache\xmlgraphics\xmlgraphics-commons\1.3.1\xmlgraphics-commons-1.3.1.jar;"%REPO%"\uk\ac\ed\ph\snuggletex\snuggletex-core\1.2.2\snuggletex-core-1.2.2.jar;"%REPO%"\uk\ac\ed\ph\snuggletex\snuggletex-upconversion\1.2.2\snuggletex-upconversion-1.2.2.jar;"%REPO%"\net\sf\saxon\saxon9\9.1.0.8\saxon9-9.1.0.8.jar;"%REPO%"\net\sf\saxon\saxon9-dom\9.1.0.8\saxon9-dom-9.1.0.8.jar;"%REPO%"\uk\ac\ed\ph\snuggletex\snuggletex-jeuclid\1.2.2\snuggletex-jeuclid-1.2.2.jar;"%REPO%"\com\aetrion\flickr\flickrapi\1.2ss.1\flickrapi-1.2ss.1.jar;"%REPO%"\org\apache\sanselan\sanselan\0.97-incubator\sanselan-0.97-incubator.jar;"%REPO%"\com\caffeineowl\graphics\BezierUtils\1.0.0\BezierUtils-1.0.0.jar;"%REPO%"\javax\media\jai-core\1.1.3\jai-core-1.1.3.jar;"%REPO%"\com\sun\media\jai-codec\1.1.3\jai-codec-1.1.3.jar;"%REPO%"\com\twelvemonkeys\imageio\imageio-jpeg\3.0-rc5\imageio-jpeg-3.0-rc5.jar;"%REPO%"\com\twelvemonkeys\imageio\imageio-core\3.0-rc5\imageio-core-3.0-rc5.jar;"%REPO%"\com\twelvemonkeys\imageio\imageio-metadata\3.0-rc5\imageio-metadata-3.0-rc5.jar;"%REPO%"\com\twelvemonkeys\common\common-io\3.0-rc5\common-io-3.0-rc5.jar;"%REPO%"\com\twelvemonkeys\common\common-lang\3.0-rc5\common-lang-3.0-rc5.jar;"%REPO%"\com\twelvemonkeys\common\common-image\3.0-rc5\common-image-3.0-rc5.jar;"%REPO%"\org\openimaj\core-video\1.3.1\core-video-1.3.1.jar;"%REPO%"\org\openimaj\core-audio\1.3.1\core-audio-1.3.1.jar;"%REPO%"\org\openimaj\content\animation\1.3.1\animation-1.3.1.jar;"%REPO%"\org\openimaj\JTransforms\1.3.1\JTransforms-1.3.1.jar;"%REPO%"\org\openimaj\image-local-features\1.3.1\image-local-features-1.3.1.jar;"%REPO%"\org\openimaj\clustering\1.3.1\clustering-1.3.1.jar;"%REPO%"\org\openimaj\image-feature-extraction\1.3.1\image-feature-extraction-1.3.1.jar;"%REPO%"\org\openimaj\klt-tracker\1.3.1\klt-tracker-1.3.1.jar;"%REPO%"\org\openimaj\core-math\1.3.1\core-math-1.3.1.jar;"%REPO%"\jama\jama\1.0.2\jama-1.0.2.jar;"%REPO%"\org\la4j\la4j\0.4.0\la4j-0.4.0.jar;"%REPO%"\com\googlecode\matrix-toolkits-java\mtj\0.9.14\mtj-0.9.14.jar;"%REPO%"\com\googlecode\netlib-java\netlib-java\0.9.3\netlib-java-0.9.3.jar;"%REPO%"\net\sourceforge\f2j\arpack_combined_all\0.1\arpack_combined_all-0.1.jar;"%REPO%"\net\sf\jafama\JaFaMa\1.2\JaFaMa-1.2.jar;"%REPO%"\jgrapht\jgrapht\0.8.2\jgrapht-0.8.2.jar;"%REPO%"\org\openimaj\MatrixLib\1.3.1\MatrixLib-1.3.1.jar;"%REPO%"\gov\sandia\foundry\gov-sandia-cognition-common-core\3.3.0\gov-sandia-cognition-common-core-3.3.0.jar;"%REPO%"\com\thoughtworks\xstream\xstream\1.4.3\xstream-1.4.3.jar;"%REPO%"\gov\sandia\foundry\gov-sandia-cognition-learning-core\3.3.0\gov-sandia-cognition-learning-core-3.3.0.jar;"%REPO%"\net\sourceforge\jmatio\jmatio\1.2\jmatio-1.2.jar;"%REPO%"\org\apache\commons\commons-math3\3.3\commons-math3-3.3.jar;"%REPO%"\org\openimaj\video-processing\1.3.1\video-processing-1.3.1.jar;"%REPO%"\org\openimaj\object-detection\1.3.1\object-detection-1.3.1.jar;"%REPO%"\xpp3\xpp3_min\1.1.4c\xpp3_min-1.1.4c.jar;"%REPO%"\xmlpull\xmlpull\1.1.3.1\xmlpull-1.1.3.1.jar;"%REPO%"\org\openimaj\FaceTracker\1.3.1\FaceTracker-1.3.1.jar;"%REPO%"\org\openimaj\core-video-capture\1.3.1\core-video-capture-1.3.1.jar;"%REPO%"\com\nativelibs4java\bridj\0.7-20140918\bridj-0.7-20140918.jar;"%REPO%"\com\google\android\tools\dx\1.7\dx-1.7.jar;"%REPO%"\org\openimaj\xuggle-video\1.3.1\xuggle-video-1.3.1.jar;"%REPO%"\xuggle\xuggle-xuggler-lgpl\5.5-20140728\xuggle-xuggler-lgpl-5.5-20140728.jar;"%REPO%"\commons-cli\commons-cli\1.1\commons-cli-1.1.jar;"%REPO%"\ch\qos\logback\logback-core\1.0.0\logback-core-1.0.0.jar;"%REPO%"\ch\qos\logback\logback-classic\1.0.0\logback-classic-1.0.0.jar;"%REPO%"\org\openimaj\machine-learning\1.3.1\machine-learning-1.3.1.jar;"%REPO%"\joda-time\joda-time\2.1\joda-time-2.1.jar;"%REPO%"\org\openimaj\nearest-neighbour\1.3.1\nearest-neighbour-1.3.1.jar;"%REPO%"\de\bwaldvogel\liblinear-dense\1.92\liblinear-dense-1.92.jar;"%REPO%"\tw\edu\ntu\csie\libsvm\3.1\libsvm-3.1.jar;"%REPO%"\commons-fileupload\commons-fileupload\1.3.1\commons-fileupload-1.3.1.jar;"%REPO%"\commons-io\commons-io\2.4\commons-io-2.4.jar;"%REPO%"\org\apache\logging\log4j\log4j-api\2.2\log4j-api-2.2.jar;"%REPO%"\org\apache\logging\log4j\log4j-core\2.2\log4j-core-2.2.jar;"%REPO%"\org\twitter4j\twitter4j-httpclient-support\2.2.6\twitter4j-httpclient-support-2.2.6.jar;"%REPO%"\org\twitter4j\twitter4j-core\4.0.2\twitter4j-core-4.0.2.jar;"%REPO%"\org\glassfish\jersey\containers\jersey-container-servlet\2.14\jersey-container-servlet-2.14.jar;"%REPO%"\org\glassfish\jersey\containers\jersey-container-servlet-core\2.14\jersey-container-servlet-core-2.14.jar;"%REPO%"\org\glassfish\jersey\core\jersey-common\2.14\jersey-common-2.14.jar;"%REPO%"\javax\annotation\javax.annotation-api\1.2\javax.annotation-api-1.2.jar;"%REPO%"\org\glassfish\jersey\bundles\repackaged\jersey-guava\2.14\jersey-guava-2.14.jar;"%REPO%"\org\glassfish\hk2\osgi-resource-locator\1.0.1\osgi-resource-locator-1.0.1.jar;"%REPO%"\org\glassfish\jersey\core\jersey-server\2.14\jersey-server-2.14.jar;"%REPO%"\javax\validation\validation-api\1.1.0.Final\validation-api-1.1.0.Final.jar;"%REPO%"\javax\ws\rs\javax.ws.rs-api\2.0.1\javax.ws.rs-api-2.0.1.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-core\2.5.0\jackson-core-2.5.0.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-databind\2.5.0\jackson-databind-2.5.0.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-annotations\2.5.0\jackson-annotations-2.5.0.jar;"%REPO%"\org\python\jython-standalone\2.5.2\jython-standalone-2.5.2.jar;"%REPO%"\org\glassfish\jersey\core\jersey-client\2.14\jersey-client-2.14.jar;"%REPO%"\org\glassfish\hk2\hk2-api\2.4.0-b06\hk2-api-2.4.0-b06.jar;"%REPO%"\org\glassfish\hk2\hk2-utils\2.4.0-b06\hk2-utils-2.4.0-b06.jar;"%REPO%"\org\glassfish\hk2\external\aopalliance-repackaged\2.4.0-b06\aopalliance-repackaged-2.4.0-b06.jar;"%REPO%"\org\glassfish\hk2\external\javax.inject\2.4.0-b06\javax.inject-2.4.0-b06.jar;"%REPO%"\org\glassfish\hk2\hk2-locator\2.4.0-b06\hk2-locator-2.4.0-b06.jar;"%REPO%"\org\javassist\javassist\3.18.1-GA\javassist-3.18.1-GA.jar;"%REPO%"\org\glassfish\jersey\connectors\jersey-apache-connector\2.14\jersey-apache-connector-2.14.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-multipart\2.14\jersey-media-multipart-2.14.jar;"%REPO%"\org\jvnet\mimepull\mimepull\1.9.3\mimepull-1.9.3.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-core\7.0.57\tomcat-embed-core-7.0.57.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-logging-juli\7.0.57\tomcat-embed-logging-juli-7.0.57.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-jasper\7.0.57\tomcat-embed-jasper-7.0.57.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-el\7.0.57\tomcat-embed-el-7.0.57.jar;"%REPO%"\org\eclipse\jdt\core\compiler\ecj\4.4\ecj-4.4.jar;"%REPO%"\org\apache\tomcat\tomcat-jasper\7.0.57\tomcat-jasper-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-servlet-api\7.0.57\tomcat-servlet-api-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-juli\7.0.57\tomcat-juli-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-el-api\7.0.57\tomcat-el-api-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-api\7.0.57\tomcat-api-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-util\7.0.57\tomcat-util-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-jasper-el\7.0.57\tomcat-jasper-el-7.0.57.jar;"%REPO%"\org\apache\tomcat\tomcat-jsp-api\7.0.57\tomcat-jsp-api-7.0.57.jar;"%REPO%"\org\postgresql\postgresql\9.4-1200-jdbc41\postgresql-9.4-1200-jdbc41.jar;"%REPO%"\com\github\dblock\waffle\waffle-jna\1.7\waffle-jna-1.7.jar;"%REPO%"\net\java\dev\jna\jna\4.1.0\jna-4.1.0.jar;"%REPO%"\net\java\dev\jna\jna-platform\4.1.0\jna-platform-4.1.0.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.7\slf4j-api-1.7.7.jar;"%REPO%"\org\slf4j\slf4j-simple\1.7.7\slf4j-simple-1.7.7.jar;"%REPO%"\com\luckypants\LuckyPants\1.0-SNAPSHOT\LuckyPants-1.0-SNAPSHOT.jar

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASEDIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH% -Dapp.name="webapp" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" launch.Main %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
