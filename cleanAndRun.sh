#!/bin/bash
#This script will install restx and compile the 
#clusterHQMiniREST
echo "Compiling clusterHQMiniREST...\n\n"
mvn clean 
mvn install
echo "Running clusterHQMiniREST...\n\n"
restx app run
