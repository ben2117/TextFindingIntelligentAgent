
/**
 */
public class Word implements java.io.Serializable
{
    // data structure of a word it has been taught
    private String word;
    private double freq;
    private double weight;
    private double ERROR = 0.3;
    
    public Word(String word, double freq, double weight)
    {
        this.word = word;
        this.freq = freq;
        this.weight = weight;
    }

    
    
    
        /// if the frequency of the learned words is less then the frequency in the text 
        /// minus 20% then this word is accepted as being frequent enough in the text
        /// for the bot to consider it. 
        /// if the bot likes the word 1 will be fed forward, if it doesnt -1 will be fed forward
        /// if it doesnt consider the word 0 will be fed forward
    public void dumpword(){
        System.out.println(word + " freg: " + freq + " weight: " + weight); 
    }
    
    public double feed(String fedWord, double fedFreq)
    {
        if(this.word.equals(fedWord)){
            if(freq < (fedFreq + ( freq * ERROR ) ) ) {
                CLI.debugLog += "\n Word Match " + word;
                return 1*weight;
            }
        }
        return 0;
    }
}
