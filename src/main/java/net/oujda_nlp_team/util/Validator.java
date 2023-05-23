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
import net.oujda_nlp_team.entity.Segment;
/*============================================================================*/
public class Validator {
/*============================================================================*/
    private static Validator instance = new Validator();
/*============================================================================*/
    public static Validator getInstance() {return instance;}
/*============================================================================*/
    private Validator(){}
/*============================================================================*/
    public boolean isHamza(char c) {
        switch (c) {
            case ArabicCharacter.HAMZA:
                return true;
            case ArabicCharacter.ALEF_HAMZA_ABOVE:
                return true;
            case ArabicCharacter.ALEF_HAMZA_BELOW:
                return true;
            case ArabicCharacter.WAW_HAMZA:
                return true;
            case ArabicCharacter.YEH_HAMZA:
                return true;
            default:
                return false;
        }
    }
/*============================================================================*/
    public boolean isSeparator(char car) {
        switch (car) {
            case '.':
                return true;
            case '!':
                return true;
            case ':':
                return true;
        // case '،' :
        //     return true;
            case ArabicCharacter.QUESTION_MARK :
                return true;
            case ArabicCharacter.SEMICOLON :
                return true;
            default:
                return false;
        }
    }
/*============================================================================*/
    public boolean isDefinit(String proClass){
        return (proClass.equals("N1") || proClass.equals("N2") || proClass.equals("N3") || proClass.equals("N5"));
    }
/*============================================================================*/
    public boolean isSolar(char c) {
        switch (c) {
            case ArabicCharacter.TEH:
                return true;
            case ArabicCharacter.THEH:
                return true;
            case ArabicCharacter.DAL:
                return true;
            case ArabicCharacter.THAL:
                return true;
            case ArabicCharacter.REH:
                return true;
            case ArabicCharacter.ZAIN:
                return true;
            case ArabicCharacter.SEEN:
                return true;
            case ArabicCharacter.SHEEN:
                return true;
            case ArabicCharacter.SAD:
                return true;
            case ArabicCharacter.DAD:
                return true;
            case ArabicCharacter.TAH:
                return true;
            case ArabicCharacter.ZAH:
                return true;
            case ArabicCharacter.LAM:
                return true;
            case ArabicCharacter.NOON:
                return true;
            default:
                return false;
        }
    }
/*============================================================================*/
    public boolean isNumeric(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }
/*============================================================================*/
    public boolean isDiacritic(char c) {
        switch (c) {
            case ArabicCharacter.FATHA:
            case ArabicCharacter.FATHATAN:
            case ArabicCharacter.DAMMA:
            case ArabicCharacter.DAMMATAN:
            case ArabicCharacter.KASRA:
            case ArabicCharacter.KASRATAN:
            case ArabicCharacter.SUKUN:
            case ArabicCharacter.SHADDA:
                return true;
            default:
                return false;
        }
    }
/*============================================================================*/
    public boolean isCompletVoyeled(String word) {
        String ch_1 = "\u0636\u0635\u062b\u0642\u0641\u063a\u0639\u0647\u062e\u062d\u062c\u062f\u0630\u0637\u0643\u0645\u0646\u062a\u0644\u064a\u0633\u0634\u0628\u0621\u0624\u0631\u0629\u0632\u0638\u0625\u0623\u0622";
        String ch_2 = "\u0648\u064a";
        String ch_3 = "\u0627\u0649";
        String ch_4 = "\u0651";
        String ch_5 = "\u064e\u064b\u064f\u064c\u0650\u064d\u0652";
        String exp = "(([" + ch_1 + ch_2 + "][" + ch_4 + "]*[" + ch_5 + "])|([" + ch_3 + ch_2 + "]))+";
        try {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(exp);
            java.util.regex.Matcher m = p.matcher(word);
            String str = "";
            while (m.find()) {
                str = m.group(0);
            }
            return word.equals(str);
        } catch (java.util.regex.PatternSyntaxException pse) {
        }
        return false;
    }
/*============================================================================*/
    public boolean isSeparator(String pat) {
        java.util.Set<String> sep = new java.util.HashSet();
        sep.add(".");
        sep.add(":");
        sep.add("\u060c");
        sep.add("\u061f");
        sep.add("\u061b");
        sep.add("!");
        return sep.contains(pat);
    }
/*============================================================================*/
    public boolean notCompatible(String normalizedWord, String voweledWord ) {
        //+----------------------------------------------------+
        String norWord = normalizedWord;
        String vowWord = voweledWord;
        String unorWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(norWord);
        String uvowWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(vowWord);
        //+----------------------------------------------------+
        String str1 = "" + ArabicCharacter.FATHA + ArabicCharacter.ALEF;
        //+----------------------------------------------------+
        String str2 = "" + ArabicCharacter.ALEF + ArabicCharacter.FATHATAN;
        //+----------------------------------------------------+
        if (vowWord.endsWith(str1) && norWord.endsWith(str2)) {return true;}
        int in = 0;
        int iv = 0;
        if (unorWord.length() != uvowWord.length()) {return true;}
        //+----------------------------------------------------+
        while (in < norWord.length() && iv < vowWord.length()) {
            if (
                    (norWord.charAt(in) == vowWord.charAt(iv)) 
                    || ( norWord.charAt(in) == ArabicCharacter.ALEF
                    && vowWord.charAt(iv) == ArabicCharacter.ALEF_MAKSURA )) {
                //+----------------------------------------------------+
                if (isDiacritic(norWord.charAt(in))) {
                    in++;iv++;
                } 
                else {
                    in++;iv++;
                }
                //+----------------------------------------------------+
            } 
            else {
                if (!isDiacritic(norWord.charAt(in)) && !isDiacritic(vowWord.charAt(iv))) {
                    return true;
                } 
                else {
                    if (!isDiacritic(norWord.charAt(in)) && isDiacritic(vowWord.charAt(iv))) {
                        //+----------------------------------------------------+
                        iv++;
                        //+----------------------------------------------------+
                    } 
                    else {
                        if (isDiacritic(norWord.charAt(in))) {return true;}
                    }
                }
            }
        }
        //+----------------------------------------------------+
        return false;
        //+----------------------------------------------------+
    }
/*============================================================================*/
    public synchronized boolean notCompatible(String normalizedWord, String vowledWord, Segment segment, boolean isTool ){
        //+----------------------------------------------------+
        String norWord = normalizedWord;
        String vowWord = vowledWord;
        String unorWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(norWord);
        String uvowWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(vowWord);
        //+----------------------------------------------------+
        if ((!segment.getEnclitic().getUnvoweledform().equals("") 
                || isTool 
                || norWord.charAt(norWord.length() - 1) == ArabicCharacter.KASRA
                ) 
                && vowWord.charAt(vowWord.length() - 1) == ArabicCharacter.SUKUN 
                && isDiacritic(norWord.charAt(norWord.length() - 1)) 
                ) {
            //+----------------------------------------------------+
            norWord = norWord.substring(0, norWord.length() - 1);
            vowWord = vowWord.substring(0, vowWord.length() - 1);
            //+----------------------------------------------------+
        }
        //+----------------------------------------------------+
        String str1 = "" + ArabicCharacter.FATHA 
                + ArabicCharacter.ALEF;
        //+----------------------------------------------------+
        String str2 = "" + ArabicCharacter.ALEF 
                + ArabicCharacter.FATHATAN;
        //+----------------------------------------------------+
        if (vowWord.endsWith(str1) && norWord.endsWith(str2)) {
            return true;
        }
        //+----------------------------------------------------+
        if (unorWord.length() != uvowWord.length()) {
            return true;
        }
        //+----------------------------------------------------+
        int in = 0;
        int iv = 0;
        while (in < norWord.length() && iv < vowWord.length()) {            
            //String norval = ArabicStringUtil.getInstance().getIsHamza(norWord.charAt(in));           
            //String vowval = ArabicStringUtil.getInstance().getIsHamza(vowWord.charAt(iv));|| (norval.equals(vowval))
            if ((norWord.charAt(in) == vowWord.charAt(iv)) 
                    || (norWord.charAt(in) == ArabicCharacter.ALEF && vowWord.charAt(iv) == ArabicCharacter.ALEF_MAKSURA )
            ) {
                //+----------------------------------------------------+
                if (isDiacritic(norWord.charAt(in))) {
                    in++;
                    iv++;
                } else {
                    in++;
                    iv++;
                }
                //+----------------------------------------------------+
            } else {
                if (!isDiacritic(norWord.charAt(in)) && !isDiacritic(vowWord.charAt(iv))) {
                    //+----------------------------------------------------+
                    return true;
                    //+----------------------------------------------------+
                } else {
                    if (!isDiacritic(norWord.charAt(in)) && isDiacritic(vowWord.charAt(iv))) {
                        //+----------------------------------------------------+
                        iv++;
                        //+----------------------------------------------------+
                    } else {
                        if (isDiacritic(norWord.charAt(in))) {
                            //+----------------------------------------------------+
                            return true;
                            //+----------------------------------------------------+
                        }
                    }
                }
            }
        }        
        //+----------------------------------------------------+
        return false;
        //+----------------------------------------------------+
    }
/*============================================================================*/
    public boolean isDiacPattern(String stem, String diac) {
        int i = 0;
        int j = 0;
        while (i < diac.length() && j < stem.length()) {
            if (diac.charAt(i)!=ArabicCharacter.FEH && diac.charAt(i)!=ArabicCharacter.AIN && diac.charAt(i)!=ArabicCharacter.LAM ) {
                if (diac.charAt(i) != stem.charAt(j)) {return false;}
            }
            i++;j++;
        }            
        return true;
    }
/*============================================================================*/
}
