This software aims at supporting Software Traceability principles.

https://www.sealights.io/software-quality/software-traceability-keeping-track-of-dev-and-test-productivity/#:~:text=Software%20traceability%20measures%20how%20easily,of%20engineering%20and%20testing%20teams. 

https://www.3ds.com/products/catia/reqtify 

https://www.perforce.com/blog/alm/what-traceability

## maven commands

mvn clean

## build jar with dependencies
mvn clean compile assembly:single

this command should create a jar file : target/JavaMestra-0.0.1-SNAPSHOT-jar-with-dependencies.jar

see plugin configuration in pom.xml

       <plugin>
	      <artifactId>maven-assembly-plugin</artifactId>
	      <configuration>
	        <archive>
	          <manifest>
	            <mainClass>com.issyhome.JavaMestra.Main.MainMain</mainClass>
	          </manifest>
	        </archive>
	        <descriptorRefs>
	          <descriptorRef>jar-with-dependencies</descriptorRef>
	        </descriptorRefs>
	      </configuration>
	    </plugin>
	    


## launch the application

. ./Launch.bat

java -cp target/JavaMestra-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.issyhome.JavaMestra.Main.MainMain 