package net.oujda_nlp_team.util;
/*============================================================================*/
/**
 * 
 * ADAT : AlKhalil for Disambiguation of Arabic Texts
 * © 2018
 * @author Mohamed BOUDCHICHE
 * @email moha.boudchiche@gmail.com
 * 
 */
/*============================================================================*/
public class Transliteration {
/*============================================================================*/
    private static final Transliteration instance = new Transliteration();
/*============================================================================*/
    public static Transliteration getInstance() {return instance;}
/*============================================================================*/
    private Transliteration(){}
/*============================================================================*/
/**
 * <p>Returns a word in the Buckwalter transliteration system from a word in arabic.</p>
 * @param word The word in arabic
 * @return The romanized word
 */    
    public String getBuckWalterToArabic(String word){
        String st = "";
        for(int i=0; i<word.length(); i++){
            st += "" + ArabicCharacterUtil.getArabicCharacter(word.charAt(i));
        }
        return st;
    }
/*============================================================================*/
/**
 * <p>Returns a word in the Buckwalter transliteration system from a word in arabic.</p>
 * @param word The word in arabic
 * @return The romanized word
 */    
    public String getArabicToBuckWalter(String word){
        String st = "";
        for(int i=0; i<word.length(); i++){
            st += "" + ArabicCharacterUtil.getBuckWalterCharacter(word.charAt(i));
        }
        return st;
    }
/*============================================================================*/
}
