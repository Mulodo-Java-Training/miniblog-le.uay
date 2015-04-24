#!/bin/sh

yes_or_no() {
	echo "In function parameters are S*"
	echo "Param 1 $1 and Param2 $2"
	while true
	do
		echo -n "Enter yes or no: "
		read x
		case "$x" in
			y | yes) return 0;;
			n | no)	return 1;;
			*) echo "Plz answer yes or no"
		esac
	done
}

echo "Original parameters are $*"

yes_or_no "Is your name", "$1?"

resultReturn=$?

echo "result = $resultReturn"

if [ "$resultReturn" -eq 1 ]; then
	echo "Hi $1"
elif [ "$resultReturn" -eq 0 ]; then
	echo "Never mind"
else 
	echo "Some thing error"
fi
exit 0
