# HeD-editor
Please see LICENSE and Disclaimer.txt for additional information

A User's guide is available at
https://github.com/SHARPC2B/HeD-editor/blob/master/design-docs/src/main/resources/docs/User%20Guide.pdf

A Developer's guide is available at
https://github.com/SHARPC2B/HeD-editor/blob/master/design-docs/src/main/resources/docs/Developer%20Guide.pdf

A Templating Guide is available at
https://github.com/SHARPC2B/HeD-editor/blob/master/design-docs/src/main/resources/docs/Templating%20Guide.pdf

A recorded Demo/Presentation is available at
http://youtu.be/8l994wFD6yo

# Requirements
* JDK 1.7
	* [http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html Oracle Java]
* Git
	* [http://git-scm.com/ git-scm.com]
* sbt
	* Install sbt from the [http://www.scala-sbt.org/download.html scala-sbt.org] site.
* apache-stanbol (or existing one to connect to)
	* Install Stanbol from the [https://stanbol.apache.org Apache Stanbol] site. [https://stanbol.apache.org/docs/trunk/tutorial.html Build instructions].
* CTS2 code system '''and''' value set service (or one to connect to)
	* Exiting read-only service: http://lexevscts2.nci.nih.gov/lexevscts2 or http://tlamp.org
	* Roll your own with the [http://informatics.mayo.edu/cts2/framework/ CTS2 Development Framework]

# Build & Deploy
1. Make sure you have all the above dependencies.
1. Checkout Source: <code>$ git clone https://github.com/suesse/HeD-editor</code>
1. Go to base dir: <code>$ cd Hed-editor</code>
1. Build: <code>$ mvn clean install -DskipTests</code> (takes about 15 minutes the first time)
1. Build hed-services
	1. Go to hed-services dir: <code>$ cd hed-services</code>
	1. Run SBT: <code>$ sbt</code>
	1. Build war (from within the sbt console): <code>$ war</code> (takes about 15 minutes the first time)
1. Setup Environment Vars
	1. HED_SERVICE_URL="http://localhost:8080/hed-services"
	1. CTS2_URL="http://lexevscts2.nci.nih.gov/lexevscts2"
	1. STANBOL_URL="http://localhost:8080/stanbol"
1. Deploy to Tomcat
	1. Deploy HeD-editor/editor-ui/target/editor-ui.war
	1. Deploy HeD-editor/hed-services/target/hed-services-0.1-SNAPSHOT.war