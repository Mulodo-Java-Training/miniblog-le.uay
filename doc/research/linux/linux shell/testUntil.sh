#!/bin/sh
echo "Locate for user ..."
attempt=1
until who | grep "$1" > /dev/null
do
	echo "attempt $attempt"
        attempt=$(($attempt+1))
	sleep 5
done

echo -e \\a

echo "***** $1 has just logged in *****"

exit 0
