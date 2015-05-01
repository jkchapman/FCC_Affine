import java.io.*;
import java.util.*;

public class AffineCipher
{

	//The size of the alphabet, in our case, 26 to represent 
	//all possible letters of the standard english alphabet.
	private static final int M = 26;

	public static void main( String[] args) {

		//only checks if too few arguments. If too few, presents usage. 
		//Too many, presumes format is correct and ignores any extras.
		if( args.length < 3) {

			System.out.println("USAGE: java AffineCipher [E/D] [INPUT] [OUTPUT]");

		} else {

			String mode = args[0];
			String inputFile = args[1];
			String outputFile = args[2];

			Scanner in = new Scanner(System.in);

			try {

				int a;		//a and b form the key, with a
				int b;		//required to be coprime with M.

				System.out.print("Key a:");
				a = in.nextInt();

				//Check if a and M are coprime (GCD of 1)
				if( greatestCommonDivisor( a, M) == 1) {

					System.out.print("Key b:");
					b = in.nextInt();

					affineFile( inputFile, outputFile, mode, a, b);

				} else {

					System.out.println("Try again! Key a and m are NOT coprime.");

				}
			//ensuring integers are inputted
			} catch( InputMismatchException e) {

				System.out.println( "Must enter an integer for keys!");

			}

		}

		System.exit(0);

	}

	//Function to open the input and output file and, depending on the mode, 
	//encrypt/decrypt a character on at a time.
	public static void affineFile( String inputFile, String outputFile, String mode, int a, int b) {

		FileReader inputStream = null;
		FileWriter outputStream = null;

		try {

			inputStream = new FileReader( inputFile);
			outputStream = new FileWriter( outputFile);

			int currentChar;

			//Loop till the final character of the input file.
			while( (currentChar = inputStream.read()) != -1) {

				//to remember if a character was upper or lower when operating.
				boolean lower = false;
				boolean upper = false;

				switch( mode.charAt(0)) {

					//falls through 'e' and 'E', so the first argument can infact be any
					//word that begins with 'e' or 'E' to encrypt (or 'd' 'D' to decrypt).
					case 'e':
					case 'E':
						//ascii arithmetic to make between 0 and 26 for the enc/dec function.
						//works for both upper and lower case only, ignoring all other chars.
						if( currentChar >= 'A' && currentChar <= 'Z') {

							currentChar -= 65;
							upper = true;
							currentChar = affineEncrypt( currentChar, a, b);

						} else if( currentChar >= 'a' && currentChar <= 'z') {

							currentChar -= 97;
							lower = true;
							currentChar = affineEncrypt( currentChar, a, b);

						}
						if( upper) {

							currentChar += 65;

						} else if( lower) {

							currentChar += 97;

						}							
						break;
					case 'd':
					case 'D':
						if( currentChar >= 'A' && currentChar <= 'Z') {

							currentChar -= 65;
							upper = true;
							currentChar = affineDecrypt( currentChar, a, b);

						} else if( currentChar >= 'a' && currentChar <= 'z') {

							currentChar -= 97;
							lower = true;
							currentChar = affineDecrypt( currentChar, a, b);

						}
						if( upper) {

							currentChar += 65;

						} else if( lower) {

							currentChar += 97;

						}
						break;
					default:
						break;

				}
				outputStream.write(currentChar);
			}

			inputStream.close();
			outputStream.close();

		} catch( IOException e) {

			System.out.println( "Error in processing file: " + e.getMessage());

			try {

				if( inputStream != null)
				inputStream.close();

				if( outputStream != null)
				outputStream.close();

			} catch( IOException e2){};

		} 

	}

	//calculates the GCD for ensuring a and M are coprime.
	public static int greatestCommonDivisor( int a, int b) {

		int x = a % b;

		if( x != 0) 
			x = greatestCommonDivisor( b, x);
		else if( x == 0)
			x = b;
		
		return x;

	}

	//calculates the inverse modular (extended euclidean) for decryption.
	public static int inverseModular( int a, int n) {

		int quot, temp;
		int x = 0, newX = 1, r = n, newR = a;

		if( greatestCommonDivisor( a, n) != 1)
			System.out.println("Error: GDC Must be 1 for a inverse modular.");
		else {

			while( newR != 0) {

			quot = r / newR;

			temp = newX;
			newX = x - quot * newX;
			x = temp;

			temp = newR;
			newR = r - quot * newR;
			r = temp;

			}

			if( x < 0)
				x += n;
		}

		return x;

	}

	//Affine encryption.
	public static int affineEncrypt( int c, int a, int b) {

		return ((a * c) + b) % M;

	}

	//Affine decryption.
	public static int affineDecrypt( int c, int a, int b) {
		
		return ((inverseModular(a, M)) * (c + M - b)) % M;

	}

}