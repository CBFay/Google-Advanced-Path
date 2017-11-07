/**
 * Problem 1
 * from https://techdevguide.withgoogle.com/paths/advanced/compress-decompression/#code-challenge
 * created 11.05.2017 by CB Fay
 */

/**
 * Your input is a compressed string of the format: 
 * number[string] and the decompressed output form 
 * should be the string written number times. For example:
 *
 * The input:
 * 3[abc]4[ab]c
 *
 * Would be output as:
 * abcabcabcababababc
 */

class Decompression {
	
	private String compressed;
	
	// test case
	public static void main(String[] args) {
		Decompression test = new Decompression();
		test.getMessage("5[3[i]yb]big5[3[c]a]jkl2[erf]");
		System.out.println(test.decompress());
	}
	
	private void getMessage(String decompressMe) {
		compressed = decompressMe;
		return;
	}
	
	// Solution method
	private String decompress() {
		String result = "";
		int units = 0;
		int i = 0;
		
		while (i < compressed.length()) {
			result += readUnit(i);
			i += unitSize(i);
		}
		return result;
	}
	
	private String readUnit(int index) {
		String unit = "";
		
		// if the unit is non-repeating
		if (Character.isLetter(compressed.charAt(index))) {
			int i = index;
			
			while (i<compressed.length() && Character.isLetter(compressed.charAt(i))) {
				unit+=compressed.charAt(i);
				i++;
			}
			return unit;
		}
		
		// if the unit repeats
		String bracket = readBrackets(index);
		for (int j = 0; j < readMultiplier(index);) {
			unit += bracket;
			j++;
		}
		return unit;
	} // <- readUnit method
	
	// returns the sequence of chars held between a pair of square brackets
	private String readBrackets(int index) {
		String message = "";
		int i = index+1;
		
		while (compressed.charAt(i-1) != '[')
			i++;
		while (compressed.charAt(i) != ']') {
			if (Character.isDigit(compressed.charAt(i))) {
				message += readUnit(i);
				i++;
				while (compressed.charAt(i-1) != ']') i++;
			}
			else {
				message += compressed.charAt(i);
				i++;
			}
		}
		return message;
	} // <- readBrackets method
	
	// returns an int representing the multiplier preceding square brackets
	private int readMultiplier(int index) {
		String multiplier = "";
		
		int i = index;
		while (compressed.charAt(i) != '[') {
			multiplier += compressed.charAt(i);
			i++;
		}
		
		return Integer.parseInt(multiplier);
	} // <- readMultiplier method
	
	// Determines the size of units within the compressed message
	private int unitSize(int index) {
		int size = 0;
		int i = index;
		
		// if the unit is non-repeating
		if (Character.isLetter(compressed.charAt(index))) {
			while (i<compressed.length() && Character.isLetter(compressed.charAt(i))) {
				size++;	
				i++;
			}
			return size;
		}
		
		// if the unit repeats
		size++; 
		i++;
		int blocks = 1;
		
		// move past the first '['
		while (compressed.charAt(i-1) != '[')
			i++;
			size++;
			
		while (blocks != 0) {
			if (compressed.charAt(i) == '[')
				blocks++;
			if (compressed.charAt(i) == ']')
				blocks--;
			i++;
			size++;
		}
		return size;
	} // <- unitSize method
	
} // <- Decompression Class