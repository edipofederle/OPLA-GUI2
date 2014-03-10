#GUI for OPLA Tool

## Notes to Developers

    Dependencies
        OPLA-Tool - http://github.com/edipofederle/architecture-representation
        JUnit     - http://junit.org/
        Yml       - http://jyaml.sourceforge.net/

This project use Maven for dependency manager. The OPLA-Tool project must be installed manually, to do that, follow below steps:

Download OPLA-Tool JAR file at: <link_para_o_jar_aqui>

In order to install the JAR on your local repository you should run this command:

>> mvn install:install-file -DFile=/paht/to/jar -DgroupId=ufpr.br -DartifactId=opla-tool -Dversion=0.1 -Dpackaging=jar

Then add the dependency to pom.xml

    <dependency>
        <groupId>ufpr.br</groupId>
        <artifactId>opla-tool</artifactId>
        <version>0.1</version>
    </dependency>

You can chance values for groupId, artifactId and version, if you wish/need.

