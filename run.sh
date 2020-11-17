#!/bin/env bash

echo Starting main instance...
locust --config=main.conf &>>logs/main.log &

sleep 2

echo Starting nodes...
for i in {1..5}; do (locust --worker &>>logs/nodes.log &) ; done

echo Done.