#!/bin/bash
LOG=$1

cat $1 | grep -o "CHOSEN STRATEGY: [A-z]*" | sort | uniq -c | sort -nr
