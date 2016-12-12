#!/bin/bash
LOG=$1
FAILURES="failed_resources 
failed_attacked 
failed_parried 
failed_unreachable 
failed_out_of_range 
failed_in_range 
failed_wrong_param 
failed_role 
failed_status 
failed_limit
failed
"

echo "Total failures: "
cat $1 | grep "(failed" | wc -l 

for FAILURE in $FAILURES; do
    echo "$FAILURE:"
    cat $1 | grep "($FAILURE)" | wc -l 
done
