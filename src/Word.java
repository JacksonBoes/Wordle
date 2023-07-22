import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Word {
	private String word; //the problem as a string
	
	
	/**
	 * The constructor method to read the file and create the array of characters from the problem.
	 * @param wordList
	 * 		A list of all the words that are possible options
	 */
	public Word(List<String> wordList) {
		Random rand = new Random();
		int randNum = rand.nextInt(3321); //create a random number to be used to obtain the word to guess
		
		this.word = wordList.get(randNum); //set word as the randomly chosen word
	}
	
	/**
	 * This method takes in a word that the user has guessed for the problem and returns an array where each char matches up 
	 * to the guess with an "o" if their guess was in the right spot, a "|" if their guess is in the problem but in the wrong 
	 * spot, and a "x" if their guess is not in the problem at all.
	 * @param guess
	 * 		The current guess being checked against the array
	 * @return
	 * 		Return an array of chars that is representative of the accuracy of the guess
	 */
	public char[] checkGuess(String guess) {
		char[] result = new char[5]; //this variable will eventually hold the return value
		
		Map<Character, Integer> counts = new HashMap<>(); //get a map of each unique char in this.word mapped to its occurence count
		for (int i = 0; i < 5; i++) {
			char current = this.word.charAt(i);
			if (counts.containsKey(current)) {
				counts.replace(current, counts.get(current) + 1);
			} else {
				counts.put(current, 1);
			}
		}
		
		//add all of the indicators for the correctly placed letters decrementing their count in counts as it goes
		for (int j = 0; j < 5; j++) {
			char current = guess.charAt(j);
			char currentWord = this.word.charAt(j);
			if (current == currentWord) {
				result[j] = 'o';
				counts.replace(current, counts.get(current) - 1);
			}
		}
		
		/*
		 * add in the | characters only if the char's count is greater than zero implying that there is an instance of that character
		 * in this.word whose location has not yet been found, and add an x for each character of guess which is not in this.word
		 */
		for (int j = 0; j < 5; j++) {
			char current = guess.charAt(j);
			int index = this.word.indexOf(current);
			if (index != j && index >= 0 && counts.get(current) > 0) {
				result[j] = '|';
				counts.replace(current, counts.get(current) - 1);
			} else if (result[j] != 'o') {
				result[j] = 'x';
			}
		}
		
		return result; //return the result of the guess
	}
	
	/** 
	 * This method will return true if the user's guess is totally correct.
	 * @param userGuess
	 * 		The guess that the user has just entered
	 * @return
	 * 		Return true if the user has one and return false otherwise
	 */
	public boolean isWin(String userGuess) {
		return this.word.equals(userGuess);
	}
	
	/**
	 * This method returns the current word that the user is guessing.
	 * @return
	 * 		Returns the current word.
	 */
	public String getAnswer() {
		return this.word;
	}
	
}
