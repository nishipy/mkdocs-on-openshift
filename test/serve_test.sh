#!/bin/bash

res=$(curl -X GET -s http://localhost:8000/)
test="<title>Test Docs</title>"

#get the response with cURL and judge if it is as we expected
if [[ $res =~ $test ]] ;
then
  echo "[SUCCESS]Serving Test has been completed."
  exit 0
else
  echo "[ERROR]Some Errors have occurred."
  exit 1
fi