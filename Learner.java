 import java.util.*;
 
public class Learner implements java.io.Serializable
{

    public Learner()
    {
    }
    
    //Read in words and assign weight depending on how frequent the word is
    public void learnNewWords( Map<String, Integer> newWords, ArrayList<Perceptron> perceptrons){
        Perceptron newPerceptron = new Perceptron();
        Iterator iter = newWords.entrySet().iterator();
        double size = newWords.size();
        int counter = 0;
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry)iter.next();
            String theWord = (String)pair.getKey();
            double theFreq = (((double)((int)pair.getValue()))/(size));
            double theWeight = 0;
            boolean relevant = true;
            counter++;
            if(counter > (size*0.99)){ // top 1% of words
               theWeight = 1;
                //System.out.println(size*0.99+"   " +word.word);
            } else if(counter > (size*0.95)){ // top 5% of words
               theWeight = 0.95;
                //System.out.println(size*0.95+"   " +word.word);
            }
            else if(counter > (size*0.9)){ // top 10% of words
                theWeight = 0.9;
                //System.out.println(size*0.9+"   " +word.word);
            }
            else if(counter > (size*0.8)){ // top 20% of words
                theWeight = 0.8;
                //System.out.println(size*0.8+"   " +word.word);
            }
            else if(counter > (size*0.7)){ // top 30% of words
                theWeight = 0.7;
                //System.out.println(size*0.7+"   " +word.word);
            }
            else if(counter > (size*0.5)){ // top 50% of words
                theWeight = 0.5;
               // System.out.println(size*0.5+"   " +word.word);
            } else {
                //word is too low
                relevant = false;
            }
            if(relevant){
                newPerceptron.addWord(theWord, theFreq, theWeight);
            }
            iter.remove(); // avoids a ConcurrentModificationException, thanks stack overflow
        }
        perceptrons.add(newPerceptron);
        System.out.println("I have learned this text");
    }
    
    //modify the values of the weights
    public void learnFromLastDecision(boolean gutOderSchlect, ArrayList<Perceptron> perceptrons){
        double INFLUENCE = 0.3;
        double value = 0;
        for(int i = 0; i < perceptrons.size(); i++){
            value = value + perceptrons.get(i).memory;
            CLI.debugLog += "\n perceptron.mem" + perceptrons.get(i).memory;
        }
        int size = perceptrons.size();
        CLI.debugLog += "\nValue and size " + value + "  " + size;
        double overAverageValue = value/size;
        CLI.debugLog += "\nlearner.overAverageValue= " + overAverageValue;
        for(int i = 0; i < perceptrons.size(); i++){
            if(perceptrons.get(i).memory > overAverageValue){
                CLI.debugLog += i + " perceptron's memory " + perceptrons.get(i).memory;
                if(gutOderSchlect){
                    perceptrons.get(i).weight = perceptrons.get(i).weight + INFLUENCE;
                } else {
                    perceptrons.get(i).weight = perceptrons.get(i).weight - INFLUENCE;
                }
            }
        }
    }
    
    public ArrayList<String> compareAndRemove(Map<String, Integer> w1, Map<String, Integer> w2,  Map<String, Integer> w3){
        ArrayList<String> words = new ArrayList<String>();
        
        Iterator<Map.Entry<String, Integer>> i1 = w1.entrySet().iterator() ;
        while(i1.hasNext()){
            
            Map.Entry<String, Integer> w1Entry = i1.next();
            String wordCheck = w1Entry.getKey();
            boolean removeWord = false;
            
            Iterator<Map.Entry<String, Integer>> i2 = w2.entrySet().iterator() ;
            while(i2.hasNext()){
                Map.Entry<String, Integer> w2Entry = i2.next();
                String checkAgainst1 = w2Entry.getKey();
                if(wordCheck.equals(checkAgainst1)){
                    
                    Iterator<Map.Entry<String, Integer>> i3 = w3.entrySet().iterator() ;
                    while(i3.hasNext()){
                        Map.Entry<String, Integer> w3Entry = i3.next();
                        String checkAgainst2 = w3Entry.getKey();
                        if(wordCheck.equals(checkAgainst2)){
                            removeWord = true;
                        }
                    }
                }
            }
            if(removeWord){
                System.out.println(wordCheck);
                words.add(wordCheck);
                i1.remove();
            }
        }
        
        return words;
        
    }

}
