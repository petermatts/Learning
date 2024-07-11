# Crypto Folder

A folder dedicated to leaning cyrptographic hash functions in/and the C++ programming language

## SHA256

### `SHA256.h`

A C header file to contain some of the rounding constants, initial hashes, and aid methods (all four sigma functions as well as binary majority and chice methods). 

### `SHA256.cpp`

A manual implementation of the SHA256 hash function.
Works for common strings, hash not been tested against more complex strings (yet), but should work for all strings that contain ASCII characters only.

## Other Files

### `BaseConversion.cpp`

This file was created to aid with conversion between the common number bases in programming, binary, octal, decimal, and hexadecimal. (Currently only supports binary to hexadecimal, but more comming soon)

This file was created because I am new to the C++ language and have had a hard time finding API means to certain conversions that I understand, I'm sure with more time with this language those methods will become clearer to me. But in the mean time, coding this myself does no harm and only helps me practive ;)