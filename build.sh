#!/bin/bash

JAVAAGENTS_JAR="../javaagents/target/javaagents-2.1.jar"
MARSMEN="com/cse431/marsmen/*.java"
STRATEGIES="com/cse431/marsmen/strategy/*.java"
SOURCES="${MARSMEN} ${STRATEGIES}"

OUT_JAR="marsmen.jar"

if javac -d build -cp ${JAVAAGENTS_JAR} ${SOURCES}; then
    echo "Compile succeeded"
else
    echo "Compile failed"
    exit
fi

mkdir -p build
cd build/
if jar cvf ../marsmen/${OUT_JAR} *; then
    echo "Jar created"
else
    echo "Jar failed!"
    exit
fi

cd ../marsmen/
if java -ea -classpath "../${JAVAAGENTS_JAR}:${OUT_JAR}" massim.javaagents.App; then
    echo "Ran succesfully"
else
    echo "Run failed!"
    exit
fi
