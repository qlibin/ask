#!/bin/bash

apt-get purge -y maven maven2 maven3
add-apt-repository -y ppa:andrei-pozolotin/maven3
apt-get update -y
apt-get install -y maven3
