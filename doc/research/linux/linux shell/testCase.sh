#!/bin/sh

flag=false
while [ "$flag" == false ]; do
	printf "Is it morning? Please answer yer or no: "
	read timeOfDay
	echo "flag = $flag"
	case "$timeOfDay" in
		"yes" | "y") echo "Good Morning"
		flag=true;;
		"no" | "n") echo "Good Afternoon"
		flag=true;;
		*) echo "Sorry, answer is not recognized";;
	esac
done
exit 0
