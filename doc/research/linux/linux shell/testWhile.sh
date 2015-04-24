#!/bin/sh

echo "Enter password"
read trythis

while [ "$trythis" != "secret" ]; do
	echo "Sorry, try agian"
	read trythis
done
echo "Password was corrected."

foo=1
while [ "$foo" -le 16 ]; do
	echo "Here $foo"
	foo=$(($foo+1))
done
echo "foo = $foo"
exit 0  

