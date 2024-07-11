# Crypto Folder

This folder is dedicated to my learning and understanding of cryptographic hasing, specifically with the famous Secure Hash Algorithm family (SHA).

## `Bitwise.java`

This file is dedicated to my learning of bitwise operators within programming (learning the syntax an how values are manipulated). Specifically, using interger type data I.E. (int, long, short, char, and/or byte).

It is very interesting once you realize sifting bits to the right by a distance of 1 is equivalent to diviting the original number by 2, shifting to the left is the same as multiplying by 2!

## `BitwiseString.java`

I have used bitwise operations before in ACSL (American Computer Science League) under the bit-string flicking section of the competitions, however I have never had the chance to use these operations in code until now.

For this file in particular, all methods intake binary strings, not primitive data types such as integers or longs.
For primitive data usage with bitwise operators see `Bitwise.java`

## `SHA256.java`

This file is dedicated to the SHA256 cryptographic hashing algorithm.

### Manual calculation

The beginning ~200-250 lines of code in this file are a manual method with (hopefully) detailed comments as to how the SHA256 algorithm works with some supporting methods to aid with calculations and data complience.

This section of the file was made so I could learn and better understand how cryptographic hashing works on a deeper level.

### API calculation

The bottom section of the file contains methods to calculate the SHA256 hash of string input using the Java API.

There is also a main method for running each manner of calculating the SHA256 hash of a String, both ways should produce the same result.

## `SHA512.java`

This file uses the Java API to calculate the SHA512 hash (128 character hex string) of any string input

No manual implementation, yet... ;)
