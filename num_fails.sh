#!/bin/bash
LOG=$1
FAILURES="_resources _attacked _parried _unreachable _out_of_range _in_range _wrong_param _role _status _limit"

echo "Total failures: "
cat $1 | grep "(failed" | wc -l 

for FAILURE in $FAILURES; do
    echo "failed$FAILURE:"
    cat $1 | grep "(failed$FAILURE" | wc -l 
done
    
