fusepool-platform
=================

The fusepool platform containing all fusepool deveoped components as submodules.

To get the source do the following:

    git clone git@github.com:fusepool/fusepool-platform.git
    git submodule init
    git submodule update

To compile the fusepool platform and its modules you need to have [Maven](http://maven.apache.org/) installed.

On many systems to should set an environment variable for maven to be executed with enough memory:

    export MAVEN_OPTS="-Xmx1024M -XX:MaxPermSize=128M"

Compile with

   mvn install -Dmaven.test.skip=true

Of course, omitt -Dmaven.test.skip=true if you want to run the tests as well

