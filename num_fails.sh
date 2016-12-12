#!/bin/bash
LOG=$1

echo "Total failures: "
cat $1 | grep "(failed" | wc -l 

cat $1 | grep -o "(failed[A-z-]*)" | sort | uniq -c
