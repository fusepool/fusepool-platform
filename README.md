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

Current issue: You first need to compile stanbol/parent manually. To do this:

   cd stanbol/parent
   mvn install
   cd ../..

now you can compile the real thing:

   mvn install -Dmaven.test.skip=true

Of course, omitt -Dmaven.test.skip=true if you want to run the tests as well

To run it change to launcher/target and run it with

   java -Xmx1024M -XX:MaxPermSize=400M -Xss512k -jar launcher-0.1-SNAPSHOT.jar

To start it in debug mode so that you can connect a debuger on port 8888

   java -Xmx1024M -XX:MaxPermSize=400M -Xss512k -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8888,server=y,suspend=n -jar launcher-0.1-SNAPSHOT.jar 

Windows specifics
=================

Note these are specifics about how to install a development environment on windows suitable to build this project. The git and putty remarks are not specific for this project.

GIT for windows: http://code.google.com/p/msysgit/downloads/detail?name=Git-1.7.11-preview20120710.exe&can=2&q=
Putty: http://the.earth.li/~sgtatham/putty/latest/x86/putty-0.62-installer.exe
JDK + netbeans: http://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-142931.html

You need git to get the source and commit back the changes.

Putty is used to generate a key pair, which is used to authenticate yourself at gibhub.

The JDK and netbeans are needed to build the sources.

1. First install Putty.
2. Install Git for windows.
3. Setup an environment variable to let Git now which ssh agent to use.
   If you are unsure how to edit environment variables see: http://support.microsoft.com/kb/310519
   add this variable GIT_SSH=C:\Program Files\PuTTY\plink.exe
4. At this point you can open a git shell or git bash (should be installed in step 2) and follow the same instructions as for unix.

