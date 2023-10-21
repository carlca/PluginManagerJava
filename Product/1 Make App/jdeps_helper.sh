#!/bin/zsh
jdeps Plugin\ Manager.jar | awk '/->/ {print $3}' | sort -u