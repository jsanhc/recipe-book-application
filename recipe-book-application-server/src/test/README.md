## To remember

This package must be the container of Integration Tests.

The docker application image container is build using the maven plugin `docker-maven-plugin`
from [`io.fabric`](https://dmp.fabric8.io/)

**From official documentation**

With this plugin it is possible to run completely isolated integration tests so you don’t need to take care of shared
resources. Ports can be mapped dynamically and made available as Maven properties to your integration test code.

Multiple containers can be managed at once, which can be linked together or share data via volumes. Containers are
created and started with the docker:start goal and stopped and destroyed with the docker:stop goal. For integration
tests both goals are typically bound to the the pre-integration-test and post-integration-test phase, respectively. It
is recommended to use the maven-failsafe-plugin for integration testing in order to stop the docker container even when
the tests fail.

For proper isolation, container exposed ports can be dynamically and flexibly mapped to local host ports. It is easy to
specify a Maven property which will be filled in with a dynamically assigned port after a container has been started.
This can then be used as parameter for integration tests to connect to the application.

```xml

<plugin>
    <!-- https://dmp.fabric8.io/ -->
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <configuration>
        <images>
            <image>
                <name>${project.artifactId}</name>
                <build>
                    <dockerFile>${project.basedir}/docker/Dockerfile</dockerFile>
                    <contextDir>${project.basedir}</contextDir>
                    <args>
                        <targetFile>target/${project.build.finalName}-${descriptorRef.value}.jar
                        </targetFile>
                    </args>
                </build>
                <run>
                    <ports>
                        <port>8080:8080</port>
                    </ports>
                    <wait>
                        <!-- Check for this URL to return a 200 return code .... -->
                        <url>http://localhost:8080/health</url>
                        <time>120000</time>
                    </wait>
                </run>
            </image>
        </images>
    </configuration>
    <!--
        Connect start/stop to pre- and
        post-integration-test phase, respectively if you want to start
        your docker containers during integration tests
     -->
    <executions>
        <execution>
            <id>start</id>
            <phase>pre-integration-test</phase>
            <goals>
                <!-- "build" should be used to create the images with the artifact -->
                <goal>build</goal>
                <goal>start</goal>
            </goals>
        </execution>
        <execution>
            <id>stop</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>stop</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```