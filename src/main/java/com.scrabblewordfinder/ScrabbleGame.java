package com.scrabblewordfinder;

import com.google.gson.Gson;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ScrabbleGame {


    /**
     * @param reader
     * @return
     * @throws IOException
     * Parse the JSON file and convert it to a MAP of String,String
     * NOTE: this parsing code allows me to run my program faster.
     *
     */
    private static Map<?, ?> parseJsonFile(BufferedReader reader) throws IOException {

        /* using Gson Library to parse the JSON file */
        Gson gson = new Gson();

        /* convert JSON file to map */
        Map<?, ?> dictionaryMap = gson.fromJson(reader, Map.class);

        /* close reader */
        reader.close();

        return dictionaryMap;
    }

    /**
     * @param lettersOfPlayer
     * @param dictionaryWord
     * @return
     * Main algorithm : found the first word of the dictionary which contains some/all the letters given of the player
     * If we found a word : the method returns TRUE
     */
    public static boolean findWordWithGivenLetters(String lettersOfPlayer, String dictionaryWord) {

        boolean isWordMatches = false;

        char givenLetterArray[] = lettersOfPlayer.toCharArray();
        char wordArray[] = dictionaryWord.toCharArray();

        Arrays.sort(givenLetterArray);

        List<Character> lettersOfGivenWord = new ArrayList<Character>();

        /* fill lettersOfGivenWord list of chars given word */
        for (char c : wordArray) {
            lettersOfGivenWord.add(c);
        }

        int nbOfCharFound = 0;

        /* check if the word is not longer than the number of given letters */
        if(givenLetterArray.length >= wordArray.length) {
            for (int i = 0; i < givenLetterArray.length; i++) {
                Boolean isFound = searchLetter(givenLetterArray[i], lettersOfGivenWord);
                if(isFound) {
                    nbOfCharFound++;
                }

            }
        }

        /* if the list of letters founded are equal to the length of the dictionary word :
           so we can play this word! */
        if(nbOfCharFound == wordArray.length){
            isWordMatches = true;
        }

        return isWordMatches;
    }

    /**
     * @param letter
     * @param lettersOfGivenWord
     * @return
     * Searching if every letter of the player is founded in the dictionary word
     * if YES => we remove that letter founded in the list of chars of the dictionary word
     *
     */
    private static Boolean searchLetter(char letter, List<Character> lettersOfGivenWord) {

        Boolean isFound = false;

        Iterator<Character> iter = lettersOfGivenWord.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(letter)) {
                isFound = true;
                iter.remove();
                break;
            }

        }
        return isFound;
    }

    /** Main function **/
    public static void main(String[] args) throws JSONException, FileNotFoundException {

        // given letters to Scrabble player
        String givenLetters = "FOEMPAS";

        try {

            /* load JSON file in Resources/json */
            String fileName = "src/main/resources/json/words.json";
            BufferedReader reader = new BufferedReader(new FileReader(fileName));


            Map<?, ?> dictionaryMap = parseJsonFile(reader);

            // MAIN SCRABBLE GAME ALGORITHM
            for (Map.Entry<?,?> entry : dictionaryMap.entrySet()) {
                boolean isDone = findWordWithGivenLetters(givenLetters, (String) entry.getKey());

                if(isDone) {
                    System.out.println("WORD FOUND!");
                    System.out.println("INPUT => " + givenLetters);
                    System.out.println("OUTPUT => " + entry.getKey());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}