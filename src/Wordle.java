import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Wordle {
	
	
	/**
	 * This method will fill a given array with a chosen char
	 * @param c
	 * 		The char we want to fill the array with
	 * @param guessArray
	 * 		THe array we want to fill
	 */
	public static void fillArray(char c, char[][] guessArray) {
		for (int i = 0; i < guessArray.length; i++) { //loop to iterate through the array and fill it with -
			for (int j = 0; j < guessArray[i].length; j++) {
				guessArray[i][j] = c;
			}
		}//end loop
	}
	
	 /**
	  * This method takes in the users guesses and checks them against the Problem object and fills every other line in the guess array with
	  * each of the user's guesses with each subsequent line holding a series of chars that represent the accuracy of each char in the guess.
	  * The method also checks if each guess is correct and if a correct guess is entered then it will stop the loop and congratulate the user.
	  * If the end is reached without the correct answer being entered then it will inform the user that they have lost.
	  * @param wordList
	  * 		The list of all acceptable words.
	  * @param thisProb
	  * 		The current problem object.
	  * @param guessArray
	  * 		The array that holds all of the guesses and their feedback.
	  * @param input
	  * 		The scanner
	  */
	public static void makeGuesses(List<String> wordList, Word thisWord, char[][] guessArray, Scanner input) {
		String userGuess = "";
		for (int i = 0; i < guessArray.length - 1; i+=2) { //for loop to take in user guesses
			userGuess = getGuess(wordList, input); //get the user's guess
			
			char[] result = thisWord.checkGuess(userGuess); //check the guess and get the array of characters to inform the user how they did
			for (int j = 0; j < guessArray[i].length; j++) {
				guessArray[i][j] = userGuess.charAt(j); //fill the appropriate row in the array with the user's last guess
				guessArray[i + 1][j] = result[j]; //fill the line of the array with the characters to indicate the accuracy of the guess
			}
			
			for (int k = 0; k < guessArray.length; k++) { //print the array of guesses and results as it currently stands
				for (int h = 0; h < guessArray[i].length; h++) {
					System.out.print(guessArray[k][h] + " ");
				}
				System.out.println();
			}
			
			if (thisWord.isWin(userGuess)) { //end the loop and tell the user they have won if their guess is correct
				i = (guessArray.length);
				System.out.println("Congratulations you have won the game and gotten the correct answer.");
			}
		}//end loop
		
		if (!thisWord.isWin(userGuess)) { //if the user runs out of guesses without getting the problem correct
			System.out.println("Better luck next time you did not figure out the problem.");
			System.out.println("The word you were guessing was: " + thisWord.getAnswer());
		}
	}
	
	/**
	 * A method to take in a word as a guess from the user and ensure that the guess is five letters long and is in the 
	 * acceptable word list.
	 * @param wordList
	 * 		The list of acceptable words.
	 * @param input
	 * 		The scannr
	 */
	public static String getGuess(List<String> wordList, Scanner input) {
		String userGuess = "";
		System.out.print("Enter your guess for the problem: ");
		userGuess = input.nextLine(); //take in the user guess
		
		//ensure that the user's guess is five letters long
		while (userGuess.length() != 5) {
			System.out.println("Oops! That's the wrong number of letters.");
			System.out.println("Guesses should always be five letters long.");
			System.out.print("Enter your guess for the problem: ");
			userGuess = input.nextLine(); //take in the user guess
		}
		
		//ensure that the user's guess is in the acceptable word list
		while (!wordList.contains(userGuess)) {
			System.out.println("Not a valid word from the word list.");
			System.out.print("Enter your guess for the problem: ");
			userGuess = input.nextLine(); //take in the user guess
		}
		
		return userGuess;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in); //open scanner for user input
		File wordFile = new File("wordleProblem.txt"); //open the file that contains the current problem
		//get a list of all of the acceptable words
		Scanner fileScanner = new Scanner(wordFile);
		List<String> wordList = new ArrayList<>();
		while (fileScanner.hasNext()) {
			wordList.add(fileScanner.nextLine());
		}
		
		Word thisWord = new Word(wordList); //create a problem object using the wordList
		
		char[][] guessArray = new char[12][5];
		
		fillArray('-', guessArray); //fill the array of guesses with '-' as place holders
		
		System.out.println("Welcome a WORDLE ripoff."); //print out instructions for the game
		System.out.println("In this game you will make several guesses to attempt to guess a random word.");
		System.out.println("After each guess you will receive feedback beneath your guess with several markers");
		System.out.println("A \"x\" means that the character you guessed is not in the problem at all");
		System.out.println("A \"|\" means that the character you guessed is in the word but in another spot");
		System.out.println("A \"o\" means that your guess is correct and in the correct spot");

		makeGuesses(wordList, thisWord, guessArray, input); //take in user guesses and give results
		
		
		input.close();
		fileScanner.close(); //close the file scanner
	}

}
