#!/bin/bash
#
# Takes an argument for the city
#
java -cp target/uber-findnearby-1.0-SNAPSHOT.jar com.vish.findnearby.main.FindNearby $1
