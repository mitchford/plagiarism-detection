/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism;

import java.util.Scanner;


/**
 *
 * @author mitchford
 */
public class Plagiarism {
    
//    public void db(){
//    
//    try {
//        Driver myDriver = new com.mysql.jdbc.Driver();
//        DriverManager.registerDriver( myDriver );
//    }
//    catch(ClassNotFoundException ex) {
//        System.out.println("Error: unable to load driver class!");
//        System.exit(1);
//    }
//        
//    } 

    /* Function taken from 
    https://github.com/mission-peace/interview/blob/master/src/com/interview/string/SubstringSearch.java */ 
    private int[] computeTemporaryArray(char pattern[]){
        int [] lps = new int[pattern.length];
        int index =0;
        for(int i=1; i < pattern.length;){
            if(pattern[i] == pattern[index]){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = lps[index-1];
                }else{
                    lps[i] =0;
                    i++;
                }
            }
        }
        return lps;
    }
    
    /* Function has been taken from 
    https://github.com/mission-peace/interview/blob/master/src/com/interview/string/SubstringSearch.java */
    public boolean KMP(char []text, char []pattern){
        
        int lps[] = computeTemporaryArray(pattern);
        int i=0;
        int j=0;
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
        }
        if(j == pattern.length){
            return true;
        }
        return false;
    }

    public int wordCount(String s){
        
        int words = 0;
        
        for (int i = 0; i < s.length()-1; i++){
            // if double space, assume word has finished.
            if (s.charAt(i) == ' ' && s.charAt(i+1) != ' ') {
                words++;
            }
        }
        /* Plus 1 as this accounts for the end of the string not
        counting with the extra space */
        return words + 1;
    }
    
    public int levd(char[] str1, char[] str2){

        int d[][] = new int [str1.length+1][str2.length+1];

        for(int i = 0; i < d[0].length; i++){
          d[0][i] = i;
        }

        for(int i = 0; i < d.length; i++){
          d[i][0] = i;
        }

        for(int i = 1; i <= str1.length; i++){
            for(int j = 1; j <= str2.length; j++){
                if(str1[i-1] == str2[j-1]){
                    d[i][j] = d[i-1][j-1];
                } else {
                    d[i][j] = 1 + getMin(d[i-1][j-1], d[i-1][j], d[i][j-1]);
                }
            }
        }

        return d[str1.length][str2.length];
    }

    public int getMin(int a, int b, int c){
      int lowest = a;
      if(b<a){
        lowest = b;
      }
      if(c<lowest){
        lowest = c;
      }
      return lowest;
    }

    
    
    public static void main(String[] args) {
        
        Plagiarism search = new Plagiarism();
        Scanner scan = new Scanner(System.in);
        System.out.print("enter string: ");
        String user_string = scan.nextLine();
        String test_string = "Test file. This will hopefully pick up matches. three sentences.";
        String levtest = "test fils";
        int matchCount = 0, similarities = 0;
        int result = search.wordCount(user_string);
        float percent, percent2;
        int dist;
        int allowance = 10;
        
    
        //Separate the text file into sentences
        String[] submission = user_string.split("\\.");
        for (int i = 0; i < submission.length; i++) {
            dist = search.levd(submission[i].trim().toLowerCase().toCharArray(), levtest.trim().toLowerCase().toCharArray());
            if (dist < allowance) {
                similarities++;
            }
            // need to replace user_string variable with text from db
            boolean matched = search.KMP(test_string.trim().toLowerCase().toCharArray(), submission[i].trim().toLowerCase().toCharArray());
            if (matched) {
                // keep a count of matched strings
                matchCount++;
            }
        }
        // work out % of matched material for judgement
        percent = ((float) matchCount/(float) submission.length) * 100;
        percent2 = ((float) similarities/(float) submission.length) * 100;
        System.out.println(percent + "% matched exactly." + percent2 + "% similar (based on allowance). Word count: " + result);
        
    }
    
    
}
