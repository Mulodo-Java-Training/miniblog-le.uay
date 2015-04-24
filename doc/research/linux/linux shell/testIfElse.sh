#!/bin/sh

printf "Is this morning?Please answer with yes or no: "
read timeOfDay

if [ "$timeOfDay" = "yes"  ]; then
	echo "Good morning"
elif [ "$timeOfDay" = "no"  ]; then
	echo "Good afternoon"
else
	echo "Sorry, $timeOfDay is not recognied. Enter yes or no."
	exit 1
fi
exit 0
