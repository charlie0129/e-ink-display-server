#!/bin/bash
JARPATH=$(ls target | grep ".jar$")
JARPATH="target/"$JARPATH
# echo $JARPATH
java -jar $JARPATH & echo $! > ./pid.file &
