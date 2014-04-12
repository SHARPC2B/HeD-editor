#!/bin/bash

TOOL_HOME=/home/davide/Software/Develop/

FUSEKI_HOME=$TOOL_HOME/jena-fuseki-1.0.0
TOMCAT_HOME=$TOOL_HOME/apache-tomcat-7.0.40
PLAYFW_HOME=$TOOL_HOME/play-2.2.0

#Start the triple store used by CTS2
$FUSEKI_HOME/fuseki start &

#Start tomcat, with CTS2 & Web UI
$TOMCAT_HOME/bin/startup.sh &

#Start play with the core editor
#$PLAYFW_HOME/play

