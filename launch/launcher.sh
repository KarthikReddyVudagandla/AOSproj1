#!/bin/bash

# Change this to your netid
netid=nxm160630

# Root directory of your project
PROJDIR=/home/010/n/nx/nxm160630/AOS/AOSproj1

# Directory where the config file is located on your local system
CONFIGLOCAL=$HOME/Desktop/AOS_proj_1/AOSproj1/launch/config.txt

# Directory your java classes are in
BINDIR=$PROJDIR/bin

# Your main project class
PROG=Main

n=0

cat $CONFIGLOCAL | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while [[ $n -lt $i ]]
    do
    	read line
    	p=$( echo $line | awk '{ print $1 }' )
        host=$( echo $line | awk '{ print $2 }' )
	
	    osascript -e 'tell app "Terminal"
        do script "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no '$netid@$host' java -cp '$BINDIR' '$PROG' '$n'; '$SHELL'"
        end tell'

        n=$(( n + 1 ))
    done
)
