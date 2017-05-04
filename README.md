# CipherBox
Encode and decode simple ciphers

Ciphers have been used for thousands of years in order to protect secrets and sensitive information.  This program is used to encode and decode text for some simple ciphers.

1 – A1Z26

This is perhaps the simplest and most straightforward cipher in this program.  Each letter of the alphabet is simply assigned a number in succession from 1 to 26, where a=1, b=2, c=3, … , z=26.  

Example:

	Plaintext:	hello world
	Encoded:	8-5-12-12-15 23-15-18-12-4


2 – Affine
	
This cipher uses an equation to convert one letter to another.  First, two number keys a and b are chosen, where a and b are between 1 and 25, and a is not co-prime to 26.  Each letter in the alphabet is assigned a number in succession from 0 to 25, where a=0, b=1, c=2, … , z=25.  The plaintext is first converted to integers, then each integer is altered using the following equation: c = ap + b % 26, where p is each letter-integer.  Then the new integer c is converted back to a letter.

Example:

	Key a = 3, Key b = 10
	Plaintext:	hello world
	Integers:	7 4 11 11 14 22 14 17 11 3
	Equation:	5 22 17 17 0 24 0 9 17 19
	Encoded:	fwrra yajrt


3 – Atbash

This is another very simple cipher.  The alphabet is simply reversed in order and each letter is assigned to its reverse, like so:

	ABCDEFGHIJKLMNOPQRSTUVWXYZ
	ZYXWVUTSRQPONMLKJIHGFEDCBA
	
Example:

	Plaintext:	hello world
	Encoded:	svool dliow


4 – Autokey


5 – Caesar

The Caesar cipher “shifts” the alphabet down a number of places based on a number key, and reassigns each letter to the shifted letter. For example if the key was 2, then a would become c, b would become d, and so forth like this:

	ABCDEFGHIJKLMNOPQRSTUVWXYZ
	CDEFGHIJKLMNOPQRSTUVWXYZAB

Example:

	Shift = 5
	Plaintext:	hello world
	Encoded:	mjqqt btwqi


6 – Columnar Transposition


7 – Railfence

This is another transposition cipher.  This one uses a number key and rearranges the letters in a “zig-zag” pattern across a number of rows based on the key.

Example:

	Rail = 3
	Plaintext:	hello world

	1-h---o---r--
	2--e-l- -o-l-
	3---l---w---d

	Encoded: 	horel ollwd


8 – Rot13

This cipher is essentially just the Caesar cipher, but it always uses the number 13 as the shift number.  The letter a becomes n, b becomes, o, and so forth like this:

	ABCDEFGHIJKLMNOPQRSTUVWXYZ
	NOPQRSTUVWXYZABCDEFGHIJKLM

Example:

	Plaintext:	hello world
	Encoded:	uryyb jbeyq
