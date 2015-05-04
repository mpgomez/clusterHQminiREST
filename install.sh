#!/bin/bash
#This script will install restx and compile the 
#clusterHQMiniREST
echo "Downloading and installing restx...\n\n"
curl -Ls http://restx.io/install.sh | sh
echo "Installing restx server...\n\n"
restx shell install 3
echo "Compiling clusterHQMiniREST...\n\n"
mvn install
