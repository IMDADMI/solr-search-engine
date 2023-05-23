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
public class ArabicStringUtil {
/*============================================================================*/
    private static final ArabicStringUtil instance = new ArabicStringUtil();
/*============================================================================*/
    public static ArabicStringUtil getInstance() {return instance;}
    private ArabicStringUtil() {}
/*============================================================================*/
    public String getIsHamza(char car){
        switch(car){
            case ArabicCharacter.ALEF_HAMZA_ABOVE: 
            case ArabicCharacter.ALEF_HAMZA_BELOW:
            case ArabicCharacter.WAW_HAMZA:
            case ArabicCharacter.YEH_HAMZA:
                return "" + ArabicCharacter.HAMZA;
            default : return "" + car;
        }
    }
/*============================================================================*/
    public String replaceAllHamza(String _word){
        return ArabicString.ALL_HAMZA.matcher(_word).replaceAll("");
    }
/*============================================================================*/
    public String removeAllDiacriticsOfWord(String _word){
        try{
            return ArabicString.ALL_DIACRITICS.matcher(_word).replaceAll("");
        }
        catch(java.lang.NullPointerException e){
            System.out.println(_word);
        }
        return _word;
        //System.out.println(ArabicString.ALL_DIACRITICS.matcher(_word));
        
    }
/*============================================================================*/
    public String removeLastDiacriticsOfWord(String _word){
        return ArabicString.DIACRITICS_EXCEPT_SHADDA.matcher(_word).replaceAll("");
    }
/*============================================================================*/
    public static String getListToString(java.util.List<String> list){
        String str = "";
        java.util.Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String st = it.next();
            str += st + '\t';
        }
        return str.replaceAll("\t$", "");
    }
/*============================================================================*/
    public String[] Vowelize(Segment segment, String pattern) {
        //+-------------------------------------------+
        String[] resultat = {"", ""};
        String proClass = segment.getProclitic().getClasse();
        String encClass = segment.getEnclitic().getClasse();
        String stem = segment.getStem();
        if(encClass.equals("V4")){
            String diacritics = "[" + ArabicString.allDiacriticsExceptSHADDA + "]$";
            pattern = pattern.replaceAll( diacritics, "" + ArabicCharacter.DAMMA);
        }
        String result = getDiactirizationStem(pattern, stem);
        //+-------------------------------------------+
        // if the prefix contains the definit article ال we have to discuss whether the stem begins with a solar character or not
        // insert the shadda
        result = result.charAt(0) + ((Validator.getInstance().isDefinit(proClass) && Validator.getInstance().isSolar(stem.charAt(0)))? "" + ArabicCharacter.SHADDA : "") + result.substring(1);
        // insert the soukoun
        String soukoun = (Validator.getInstance().isDefinit(proClass) && !Validator.getInstance().isSolar(stem.charAt(0))) ? "" + ArabicCharacter.SUKUN : "";
        //+-------------------------------------------+
        // replace ة by ت in the stem if the suffix is not empty
        if ((!segment.getEnclitic().getVoweledform().equals(""))) {
            result = result.replace(ArabicCharacter.TEH_MARBUTA, ArabicCharacter.TEH);
        }
        //+-------------------------------------------+
        // delete ا in some verbs if the suffix is not empty
        if ( (result.endsWith("ُوا") || result.endsWith("وْا")) && (!segment.getEnclitic().getUnvoweledform().equals(""))) {
            result = result.substring(0,result.length()-1);
        }
        //+-------------------------------------------+
        // the vowelization of some suffix should be discussed according to the last character of the stem
        if (segment.getEnclitic().getUnvoweledform().equals("ه")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getEnclitic().setVoweledform("هِ");
            } else {
                segment.getEnclitic().setVoweledform("هُ");
            }
        }
        //+-------------------------------------------+
        if (segment.getEnclitic().getUnvoweledform().equals("هما")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getEnclitic().setVoweledform("هِمَا");
            } else {
                segment.getEnclitic().setVoweledform("هُمَا");
            }
        }
        //+-------------------------------------------+
        if (segment.getEnclitic().getUnvoweledform().equals("هم")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getEnclitic().setVoweledform("هِمْ");
            } else {
                segment.getEnclitic().setVoweledform("هُمْ");
            }
        }
        //+-------------------------------------------+
        if (segment.getEnclitic().getUnvoweledform().equals("هن")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getEnclitic().setVoweledform("هِنَّ");
            } else {
                segment.getEnclitic().setVoweledform("هُنَّ");
            }
        }
        //+-------------------------------------------+
        if( (result.charAt(result.length()-1) == ArabicCharacter.DAMMA || result.charAt(result.length()-1)==ArabicCharacter.FATHA) && (segment.getEnclitic().getUnvoweledform().equals("ي"))) {
            result = result.substring(0, result.length()-1) + ArabicCharacter.KASRA;
        }
        //+-------------------------------------------+
        String res;
        String encVoweled = segment.getEnclitic().getVoweledform();
        if(encVoweled.length()>0){
            result = result.replaceAll("ى", "ا");
            //+-------------------------------------------+
            res = ( (!(result.endsWith(ArabicCharacter.YEH + "" + ArabicCharacter.SUKUN) 
                        && segment.getEnclitic().getClasse().equals("N1"))                        
                    ) 
                    && (result.endsWith("" + ArabicCharacter.SUKUN)
                        || encVoweled.length() == 1 
                        || !Validator.getInstance().isDiacritic(encVoweled.charAt(1)) ) 
                    )
                    ? addDiacBeforeString(encVoweled, result)
                    : result + encVoweled;
            //+-------------------------------------------+    
        }
        else{
            res = result;
        }
        //+-------------------------------------------+
        // the vowelization of the entire word
        if(!res.equals("")){res = CreatHamza.correctHamza(res);}
        //+-------------------------------------------+
        resultat[0] = segment.getProclitic().getVoweledform() + soukoun
                + (( segment.getProclitic().getUnvoweledform().equals(""+ArabicCharacter.ALEF_HAMZA_ABOVE) 
                    && res.length()>3 && res.charAt(0) == ArabicCharacter.ALEF 
                    && res.charAt(2) == ArabicCharacter.SUKUN 
                    )
                    ?
                    res.substring(1)
                    :
                    res
                );
        
        //+-------------------------------------------+
        if(resultat[0].endsWith(ArabicCharacter.YEH + "" + ArabicCharacter.SUKUN + "" + ArabicCharacter.YEH) 
                && segment.getEnclitic().getClasse().equals("N1")){
            //+-------------------------------------------+
            resultat[0] = resultat[0].replaceAll(ArabicCharacter.YEH 
                    + "" + ArabicCharacter.SUKUN 
                    + "" + ArabicCharacter.YEH + "$", 
                    ArabicCharacter.YEH + "" 
                            + ArabicCharacter.SHADDA 
                            + "" + ArabicCharacter.FATHA);
            //+-------------------------------------------+
        }
        //+-------------------------------------------+
        // the vowelization of the stem only
        resultat[1] = result;
        //+-------------------------------------------+
        return resultat;
        //+-------------------------------------------+
    }
/*============================================================================*/
    public String getWordFromRootAndPattern( String root, String diacLemma){
        String str = "";
        boolean passe=false;
        boolean passe2=false;
        for(int i=0;i<diacLemma.length();i++){
            if( diacLemma.charAt(i) == ArabicCharacter.FEH ) str += root.charAt(0);
            else{
                if( diacLemma.charAt(i) == ArabicCharacter.AIN ) str += root.charAt(1);
                else{
                    if( diacLemma.charAt(i) == ArabicCharacter.LAM ){
                        if(!passe) str += root.charAt(2);
                        else {
                            if(!passe2){
                                str += root.charAt(3);    
                                if(root.length()>4) passe2=true;
                            }
                            else str += root.charAt(4);    
                        }
                        if(root.length()!=3) passe=true;
                    }
                    else str += diacLemma.charAt(i);
                }
            }
        }
        return CreatHamza.correctHamza(str);
    }
/*============================================================================*/
    public static int Max(int a, int b){return ( a > b) ? a : b;}
/*============================================================================*/
    public static int Min(int a, int b){return ( a < b ) ? a : b;}
/*============================================================================*/
    public String getPatternCompile(){
        String str = "";
        str += "[" + ArabicString.allArabicCharacter + "][" + ArabicString.allArabicCharacter + new ArabicString().allDiacritics + "]*";
        String chainePattern =  "(" + "(" + str + ")";
        chainePattern += "|(" + "\\." +")";
        chainePattern += "|(" + ":" +")";
        //chainePattern += "|(" + "،" +")";
        chainePattern += "|(" + ArabicCharacter.QUESTION_MARK +")";
        chainePattern += "|(" + ArabicCharacter.SEMICOLON +")";
        chainePattern += "|(" + "!" +")";
        chainePattern += "|(" + "\n" +")";
        chainePattern += ")";
        return chainePattern;
    }
/*============================================================================*/
    private String getDiactirizationStem(String pattern, String stem){
        String result = "";
        int j = 0;
        for (int i = 0; i < pattern.length(); i++) {
            result += (Validator.getInstance().isDiacritic(pattern.charAt(i))) ? pattern.charAt(i) : stem.charAt(j++);
        }
        return result;
    }
/*============================================================================*/
    private String addDiacBeforeString(String encVoweled, String result){
        switch(encVoweled.charAt(0)){
            case ArabicCharacter.WAW:
                return result.substring(0, result.length()-1) + ArabicCharacter.DAMMA + encVoweled;
            case ArabicCharacter.YEH:
                return result.substring(0, result.length()-1) + ArabicCharacter.KASRA + encVoweled;
            case ArabicCharacter.ALEF:
                return result.substring(0, result.length()-1) + ArabicCharacter.FATHA + encVoweled;
            default:
                return result + encVoweled;
        }
    }
/*============================================================================*/
    public String correctErreur(String normalizedWord){
        normalizedWord = 
                (normalizedWord.length()>=3 && normalizedWord.charAt(1) == ArabicCharacter.SHADDA)
                ? normalizedWord.charAt(0) + normalizedWord.substring(2)
                :normalizedWord;
        normalizedWord = normalizedWord.replaceAll("ٱ","ا");
        normalizedWord = normalizedWord.replaceAll("اُ","ا");
        normalizedWord = normalizedWord.replaceAll("اَّ","َّا");
        normalizedWord = normalizedWord.replaceAll("ىً$", "ًى");
        normalizedWord = normalizedWord.replaceAll("اً$", "ًا");
        normalizedWord = normalizedWord.replaceAll("اَ", "ا");
        normalizedWord = normalizedWord.replaceAll("اِ", "ا");
        normalizedWord = normalizedWord.replaceAll("ِا", "ا");
        normalizedWord = normalizedWord.replaceAll("ِّ","ِّ");
        normalizedWord = normalizedWord.replaceAll("َّ","َّ");
        normalizedWord = normalizedWord.replaceAll("ُّ","ُّ");
        normalizedWord = normalizedWord.replaceAll("ٌّ","ٌّ");
        normalizedWord = normalizedWord.replaceAll("ًّ","ًّ");
        normalizedWord = normalizedWord.replaceAll("ٍّ","ٍّ");
        normalizedWord = normalizedWord.replaceAll("آَ","آ");
        return normalizedWord;
    }
/*============================================================================*/
    public String getNormalizeHamza(String word){
        String in = "[" + ArabicCharacter.WAW_HAMZA 
                + ArabicCharacter.ALEF_HAMZA_ABOVE 
                + ArabicCharacter.ALEF_HAMZA_BELOW
                + ArabicCharacter.YEH_HAMZA + "]";
        String out = "" + ArabicCharacter.HAMZA;
        return word.replaceAll( in, out);
    }
/*============================================================================*/
}
