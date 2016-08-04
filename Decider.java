
/**
 * Write a description of class Decider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class Decider implements java.io.Serializable
{
    private double highScore;
    private double ERROR = 0.3;
    private ArrayList<String> lastMatchedWords;
    
    public Decider()
    {
        highScore = 0;
    }

    public ArrayList<String> getLastMatchedWords(){
        
        return lastMatchedWords;
    }
    
    public void resetHighScore(){
        this.highScore = 0;
    }
    
    public boolean makeADecision(Map<String, Integer> newWords, ArrayList<Perceptron> perceptrons){
        boolean goodText = false;
        lastMatchedWords = new ArrayList<String>();
        double fedPerceptrons = 0;
        int size = newWords.size();
        for(int i = 0; i < perceptrons.size(); i++){
            double fedWords = 0;
            int wordCount = 0;
            for(int j = 0; j < perceptrons.get(i).learnedWords.size(); j++){
                for(Map.Entry<String, Integer> newWord : newWords.entrySet()){
                    String readWord = (String)newWord.getKey();
                    double readFreq = ((double)((int)newWord.getValue()))/(size);
                    double wordValue = perceptrons.get(i).learnedWords.get(j).feed(readWord, readFreq);
                    if(wordValue!=0 && wordValue != Double.NaN){
                        lastMatchedWords.add(readWord);
                        fedWords += wordValue;
                        wordCount++;
                    }
                }
            }
            double perceptronValue;
            if(wordCount != 0){
            
                perceptronValue = perceptrons.get(i).feed(fedWords, wordCount);
            }else{
                perceptronValue = 0;
            }
            fedPerceptrons += perceptronValue;
            CLI.debugLog += "\nDecider.perceptronValue " + i + " is " + perceptronValue;
            
        }
        CLI.debugLog += "\nDecider.fedPerceptron is " + fedPerceptrons;
        if(highScore < fedPerceptrons){ 
            highScore = fedPerceptrons;
        }
        CLI.debugLog += "\nDecider.highscore " + highScore;
        
        if(highScore < (fedPerceptrons + ( highScore * ERROR ) )){
            System.out.println("I liked this text!");
            goodText = true;
        } else {
            System.out.println("I did not like this text.");
        }
        return goodText;
    }
} 
