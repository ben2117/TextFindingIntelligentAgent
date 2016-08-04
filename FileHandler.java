
/**
 *This is crazy amounts of hack and slash, I want a database to handle the data properly
 *and an entirely new class should be written in order to handle input output
 *however for times sake data is stored as a basterdised csv file
 */

import java.util.*;
import java.io.*;

public class FileHandler implements java.io.Serializable
{
    String PATH ="Files/";
    
    public FileHandler(){
        
    }

     public  Map<String, Integer> getWordsFromTextFile(String textFileName, String botName){
        try{
            File file = new File(PATH+botName+"sbadWords.txt");
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(Exception e) {System.out.println("THIS DIDN'T WORK");}
        
        ArrayList<String> rtf = readTextFile(PATH+textFileName);
        Map<String, Integer> mptf = mapTextFile(rtf);
        mptf = removeUselessWords(mptf);
        mptf = removeBotsWords(mptf, botName);
        mptf = sortByValue(mptf);
        return mptf;
    
    }
    
    public void saveABot(Bot bot){       
       try{
           FileOutputStream fo = new FileOutputStream(PATH+bot.name);
           ObjectOutputStream oo = new ObjectOutputStream(fo);
           oo.writeObject(bot);
           oo.close();
           fo.close();
           System.out.println("Bot Saved");
           
        }catch(Exception e){
            System.out.println("Problem Saving");
            e.printStackTrace();
        }
        
    }
    
    public Bot loadABot(String botFileName){
        //oh god what a disaster I should have just made a database
        Bot bot = null;
        try{
            FileInputStream fin = new FileInputStream(PATH+botFileName);
            ObjectInputStream in = new ObjectInputStream(fin);
            bot = (Bot) in.readObject();
            in.close();
            fin.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        System.out.println(bot.name + " has been loaded ");    
        return bot;
    }
    
    public void saveGUW(ArrayList<String> guw, String botName){
        try{
            File file = new File(PATH+botName+"sbadWords.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(PATH+botName+"sbadWords.txt", true);
            
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < guw.size(); i++){
                bw.write(guw.get(i) + " ");
                System.out.println(guw.get(i));
            }
            bw.close();
            System.out.println("guw saved");

            
        } catch (Exception e){e.printStackTrace();};
    }
    
   private ArrayList<String> readTextFile(String FILEPATH){
        
       // String FILEPATH  = "text.txt";
        
        try {
            FileReader fr = new FileReader(FILEPATH);   
            BufferedReader br = new BufferedReader(fr);
            
            
            ArrayList<String> fullText = new ArrayList<String>();
            String readingLine;
            readingLine = br.readLine();
            while( readingLine != null){
              
               fullText.add(readingLine);
               readingLine = br.readLine();
            }
            br.close();
        
            return fullText;
        } catch (Exception e) {
            System.out.println("COULD NOT READ THIS FILE " + FILEPATH);
            e.printStackTrace();
        };
        
        return null;
    }
    
    
    private Map<String, Integer> mapTextFile(ArrayList<String> fullText){
         String text = "";
        
        for(int i = 0; i < fullText.size(); i++){
            text += fullText.get(i);
        }
        
        text = text.toLowerCase();
        text = text.replaceAll("[\",!.:;?()']", "");
        String[] splitText = text.split("\\s+");

        Map<String, Integer> map = new HashMap<>();
        for (String w : splitText) {
            Integer n = map.get(w);
            if( n == null) {
                ///remove this when master word list is working
                n = 1;
                map.put(w, n);
                //end remove
            }
            else {
                n = n+1;
                map.put(w, n);
            }
           
        }
        
        return map;
    }
    
    
    //Map sorting algorythem taken from
    //http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    
    }  
    
    
    
    
    private Map<String, Integer> removeUselessWords(Map<String, Integer> map){
        
        //
        // only nounes verbs and adjectivies i have much use for.
        //
        
        
        ArrayList<String> uselessWords = readTextFile(PATH+"uselessWords.txt");
        ArrayList<String> fixedWords = new ArrayList <String>();
        for(String ad : uselessWords){
            String text = ad.toLowerCase();
            text = text.replaceAll("[\",!.:;?()']", "");
            String[] splitText = text.split("\\s+");
            for(String x : splitText){
                fixedWords.add(x);
            }
        }
            
        for (String w : fixedWords) {
            Integer n = map.get(w);
           
                if( n != null) {
                    map.remove(w);
                }
            
           
        }
        
        return map;
    }
    
    private Map<String, Integer> removeBotsWords(Map<String, Integer> map, String botName){
        ArrayList<String> uselessWords = readTextFile(PATH+botName+"sbadWords.txt");
        ArrayList<String> fixedWords = new ArrayList <String>();
        
        
        for(String ad : uselessWords){
            String text = ad.toLowerCase();
            text = text.replaceAll("[\",!.:;?()']", "");
            String[] splitText = text.split("\\s+");
            for(String x : splitText){
                fixedWords.add(x);
            }
        }
            
        for (String w : fixedWords) {
            Integer n = map.get(w);
           
                if( n != null) {
                    map.remove(w);
                }
            
           
        }
        return map;
    }
    
}
