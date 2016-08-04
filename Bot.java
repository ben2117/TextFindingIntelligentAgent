
/**
 * Write a description of class Bot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Bot implements java.io.Serializable
{
    // instance variables - replace the example below with your own
    public String name;
    private ArrayList<Perceptron> perceptrons;
    private Decider decider;
    private Learner learner;
    private FileHandler fileHandler;

    /**
     * Constructor for objects of class Bot
     */
    public Bot(String name)
    {
        this.name = name;
        perceptrons = new ArrayList<Perceptron>();
        decider = new Decider();
        learner = new Learner();
        fileHandler = new FileHandler();
        
    }
    
    public String dump(){
        for(int i = 0; i < perceptrons.size(); i++){
            System.out.println("Perceptron " + i + " weight " + perceptrons.get(i).weight);
            for(int j = 0; j < perceptrons.get(i).learnedWords.size(); j++){
                System.out.print("    ");
                perceptrons.get(i).learnedWords.get(j).dumpword();
                
            }
        }
        return "fin";
    }
    
    public String readAndLearn(String fileName){
        boolean goodText = decider.makeADecision(fileHandler.getWordsFromTextFile(fileName, name),perceptrons );
        if(goodText){
            learner.learnNewWords(fileHandler.getWordsFromTextFile(fileName, name), perceptrons);
        }
        return "fin";
    }
        
        
    
    public String readText(String fileName){
        decider.makeADecision(fileHandler.getWordsFromTextFile(fileName, name),perceptrons );
        return "fin";
    }
    
    public String learnFromLastDecision(boolean good){
        learner.learnFromLastDecision(good, perceptrons);
        return "fin";
    }
    
    public String teachText(String fileName){
        learner.learnNewWords(fileHandler.getWordsFromTextFile(fileName, name), perceptrons);
        return "fin";
    }
    
    public String teachBadLastWords(){
        ArrayList<String> lmw = decider.getLastMatchedWords();
        fileHandler.saveGUW(lmw, name);
        decider.resetHighScore();
        return "fin";
    }
    
    public String compareAndRemove(String f1, String f2, String f3){
        ArrayList<String> guw = learner.compareAndRemove(fileHandler.getWordsFromTextFile(f1, name), 
                                                            fileHandler.getWordsFromTextFile(f2, name),
                                                            fileHandler.getWordsFromTextFile(f3, name));
        fileHandler.saveGUW(guw, name);
        return "fin";
    }
    /*
    //public String saveBot(){
    //    fileHandler.saveABot(this);
    //    return "fin";
    //}
    */
    

}
