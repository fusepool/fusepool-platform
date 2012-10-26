fusepool-platform
=================

The fusepool platform containing all fusepool deveoped components as submodules.

To get the source do the following:

    git clone git@github.com:fusepool/fusepool-platform.git
    cd fusepool-platform
    git submodule init
    git submodule update

To compile the fusepool platform and its modules you need to have [Maven](http://maven.apache.org/) installed.

On many systems to should set an environment variable for maven to be executed with enough memory:

    export MAVEN_OPTS="-Xmx1024M -XX:MaxPermSize=128M"

Compile with

    mvn install -Dmaven.test.skip=true

Of course, omitt -Dmaven.test.skip=true if you want to run the tests as well

To run it change to launcher/target and run it with

    java -Xmx1024M -XX:MaxPermSize=400M -Xss512k -jar launcher-0.1-SNAPSHOT.jar

To start it in debug mode so that you can connect a debuger on port 8888

    java -Xmx1024M -XX:MaxPermSize=400M -Xss512k -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8888,server=y,suspend=n -jar launcher-0.1-SNAPSHOT.jar 

Windows specifics
=================

See https://github.com/fusepool/fusepool-platform/wiki/Windows-Installation-Guide-(beginners)