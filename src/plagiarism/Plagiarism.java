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
    
    
    public static void main(String[] args) {
        
        Plagiarism search = new Plagiarism();
        Scanner scan = new Scanner(System.in);
        System.out.print("enter string: ");
        String user_string = scan.nextLine();
        String test_string = "Test file. This will hopefully pick up matches. three sentences.";
        int matchCount = 0;
        int result = search.wordCount(user_string);
        float percent = 0;
    
        //Separate the text file into sentences
        String[] submission = user_string.split("\\.");
        for (int i = 0; i < submission.length; i++) {
            // need to replace user_string variable with text from db
            boolean matched = search.KMP(test_string.toCharArray(), submission[i].toCharArray());
            if (matched) {
                // keep a count of matched strings
                matchCount++;
            }
        }
        // work out % of matched material for judgement
        percent = ((float) matchCount/(float) submission.length) * 100;
        System.out.println(percent + "% matched. word count: " + result);
        
    }
    
    
}
