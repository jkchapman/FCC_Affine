# FCC_Affine
A simple, extremely quickly thrown together implementation of the Affine Cipher in Java.  
Required for Fundamental Concepts of Cryptography, a second year CyberSecurity unit at Curtin.  

## Usage
Tested with Java version "1.7.0_75"

Compile with: 

javac AffineCipher.java  

Run as:  

java AffineCipher [E/D] [INPUT] [OUTPUT]

where E/D = encrypt or decrypt, INPUT = the path to the input file, and OUTPUT = the path to the new output file.

## Notes
This implementation is only operating on the English alphabet so alphabet size M = 26, with case being preserved (in a rather quick and messy way).  Only using M of 26 instead of the whole of ascii (so M = 256) because that is what the assingment spec wished for. 
