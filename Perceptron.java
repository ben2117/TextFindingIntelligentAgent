

import java.util.*;
 
public class Perceptron implements java.io.Serializable
{
   
    public double weight; // Weight of the overall group of words
    public ArrayList<Word> learnedWords;
    public double memory; //I remember my score of the last thing i read

    /**
     * Constructor for objects of class Perceptron
     */
    public Perceptron()
    {
        weight = 1;
        learnedWords = new ArrayList<Word>();
    }
    
    //Called by decider for calculating final score
    public double feed(double fedWords, int numberOfFedWords){
        memory = (fedWords/numberOfFedWords)*weight;
        return memory;
    }
    //Change how important this group of words are
    public void influenceWeight(double influence){
        weight = weight+influence;
    }
    
    public double getWeight(){
        return weight;
    }
    public ArrayList<Word> getLearnedWords(){
        return learnedWords;
    }
    public void addWord(String word, double freq, double weight){
        Word newWord = new Word(word, freq, weight);
        learnedWords.add(newWord);
    }
        
}
