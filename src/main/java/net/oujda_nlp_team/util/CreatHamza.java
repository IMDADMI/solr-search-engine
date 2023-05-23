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
public class CreatHamza {
/*============================================================================*/    
    public static String correctHamza(String word){
//        String wrdA = "";
//        for(char c : word.toCharArray()){
//            switch(c){
//                case 'ئ':
//                case 'أ':
//                case 'إ':
//                case 'ؤ':    
//                    wrdA += 'ء';
//                default :
//                    wrdA += c;
//            }
//        }
//        word = wrdA;
        
        word = word.replaceAll("[أإئؤ]", "ء");
        String wrd = "";
        word = startHamza(word);
        String wrdD = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
        int len = word.length();
        int ip = 0;
        
        if(word.startsWith("اءْ")){
            word = "ائْ" + word.substring(3);
        }
        
        for(int i=0; i<len; i++){
            
            if( word.charAt(i) == net.oujda_nlp_team.util.ArabicCharacter.HAMZA ){
                if( (ip+1)==wrdD.length() ) {
                    wrd += endHamza(word, i, len);
                    
                }
                else{
                    if(isNibraHamza(word, i, len)){
                        wrd += "ئ";
                    }
                    else{
                        if(isWawHamza(word, i, len)){
                            wrd += "ؤ";
                        }
                        else{
                            if(isAlifHamza(word, i, len)){
                                if( i+2<len && word.charAt(i+1) == net.oujda_nlp_team.util.ArabicCharacter.FATHA && word.charAt(i+2) == 'ا' ){
                                    wrd+="آ";
                                    i+=2;
                                }
                                else{
                                    if(i+3<len && word.charAt(i+1) == 'ّ' && word.charAt(i+2) == net.oujda_nlp_team.util.ArabicCharacter.FATHA && word.charAt(i+3) == 'ا' ){
                                        wrd += "آَّ";
                                        i+=3;
                                    }
                                    else{
                                        if(i+3<len && word.charAt(i+1) == 'َ' && word.charAt(i+2) == net.oujda_nlp_team.util.ArabicCharacter.HAMZA && word.charAt(i+3) == ArabicCharacter.SUKUN ){
                                            wrd += "آ";
                                            i+=3;
                                        }
                                        else{
                                            wrd += "أ";
                                        }
                                    }
                                }
                            }
                            else{
                                wrd+=word.charAt(i);
                            }
                        }
                    }
                }
            }
            else{
                wrd+=word.charAt(i);
            }
            if(!Validator.getInstance().isDiacritic(word.charAt(i))){ip++;}
        }
        //wrd = wrd.replaceAll("^اءْ","ائْ");
        //wrd = wrd.replaceAll("أَأْ","آ");
        
        return wrd;
    }
/*============================================================================*/  
    public static String startHamza(String word){
        
        String wordF = "";
        if(word.length() > 1 && word.charAt(0) == net.oujda_nlp_team.util.ArabicCharacter.HAMZA){
            wordF += ( word.charAt(1) == net.oujda_nlp_team.util.ArabicCharacter.KASRA ) ? 'إ' : 'أ';
        }
        else{
            wordF += word.charAt(0);
        }
        wordF+=word.substring(1);
        if(wordF.startsWith("أَا")){
            wordF = "آ" + wordF.substring(3);
        }
        return wordF;
    }
/*============================================================================*/
    public static String endHamza(String word, int pos, int len){
        if(pos>1 && ( word.charAt(pos-1) == ArabicCharacter.KASRA )){
            return "ئ";
        }
        if(pos>1 && ( word.charAt(pos-1) == net.oujda_nlp_team.util.ArabicCharacter.FATHA)){
            return "أ";
        }
        if(pos>3 && ( word.charAt(pos-1)== net.oujda_nlp_team.util.ArabicCharacter.DAMMA ) && ( word.charAt(pos-2)=='ّ') && ( word.charAt(pos-3) == net.oujda_nlp_team.util.ArabicCharacter.WAW ) ){
            return "" + net.oujda_nlp_team.util.ArabicCharacter.HAMZA;
        }
        if(pos>1 && ( word.charAt(pos-1)== net.oujda_nlp_team.util.ArabicCharacter.DAMMA ) ){
            return "ؤ";
        }
        return "" + net.oujda_nlp_team.util.ArabicCharacter.HAMZA;
        
        
    }
/*============================================================================*/
    //----> Yae
    private static boolean isNibraHamza(String word, int pos, int len){
        
        if( pos>0 && ( word.charAt(pos-1)== ArabicCharacter.KASRA)){
            return true;
        }
        if( (pos>0 && ( word.charAt(pos-1)=='ي')) || (pos>1 && ( word.charAt(pos-1) == ArabicCharacter.SUKUN)  && ( word.charAt(pos-2)=='ي') )){
            return true;
        }
        if((pos>1 && ( word.charAt(pos-1)== ArabicCharacter.SUKUN)  && isNoRelatifCaracter(word.charAt(pos-2))) && pos+2<len && ( ( word.charAt(pos+1)=='َ') || ( word.charAt(pos+1)=='ً')) && ( word.charAt(pos+2)=='ا')){
            return true;
        }
        if( pos>0 && ( word.charAt(pos-1)=='ا' || word.charAt(pos-1)=='ي' || word.charAt(pos-1)== net.oujda_nlp_team.util.ArabicCharacter.WAW ) && ( ((pos+1)<len && word.charAt(pos+1)=='ِ') || ((pos+2)<len && word.charAt(pos+2)=='ِ' && word.charAt(pos+1)=='ّ')) ){
            return true;
        }
        return( pos>0 && ( word.charAt(pos-1)== ArabicCharacter.SUKUN || word.charAt(pos-1)=='َ' || word.charAt(pos-1)== net.oujda_nlp_team.util.ArabicCharacter.DAMMA ) && ( ((pos+1)<len && word.charAt(pos+1)=='ِ') || ((pos+2)<len && word.charAt(pos+2)=='ِ' && word.charAt(pos+1)=='ّ')) );
        
    }
/*============================================================================*/
    //----> Waw
    private static boolean isWawHamza(String word, int pos, int len){
        
        if( pos>0 && ( word.charAt(pos-1)=='ا' || word.charAt(pos-1)=='ْ' ) && word.charAt(pos-1)!='ي' && word.charAt(pos-1)!='و'){
            if((pos+1)<len && word.charAt(pos+1) == net.oujda_nlp_team.util.ArabicCharacter.DAMMA ){
                return ((pos+2)<len); //(word.charAt(pos+2)!='و');
            }
            else{
                if(((pos+2)<len && word.charAt(pos+2) == net.oujda_nlp_team.util.ArabicCharacter.DAMMA && word.charAt(pos+1)=='ّ')){
                    return ((pos+3)<len) ? (word.charAt(pos+3)!='و') : false;
                }
            }
        }
        if( pos>0 && ( word.charAt(pos-1)=='َ') && ( ((pos+1)<len && word.charAt(pos+1) == net.oujda_nlp_team.util.ArabicCharacter.DAMMA) || ((pos+2)<len && word.charAt(pos+2)== net.oujda_nlp_team.util.ArabicCharacter.DAMMA  && word.charAt(pos+1)=='ّ')) ){
            return true;
        }
        return (pos>0 && word.charAt(pos-1) == net.oujda_nlp_team.util.ArabicCharacter.DAMMA && ( ((pos+1)<len && word.charAt(pos+1)!='ِ') || ((pos+2)<len && word.charAt(pos+2)!='ِ' && word.charAt(pos+1)=='ّ')) ) ? !(pos>2 && word.charAt(pos-2)=='ّ' && word.charAt(pos-3) == net.oujda_nlp_team.util.ArabicCharacter.WAW ) : false;
        
    }
/*============================================================================*/
    //-----> Alif
    private static boolean isAlifHamza(String word, int pos, int len){
    
        if( pos>0 && ( word.charAt(pos-1)=='َ') && ( ((pos+1)<len && word.charAt(pos+1)=='ْ') || ((pos+1)<len && word.charAt(pos+1)=='َ') || ((pos+2)<len && word.charAt(pos+2)=='َ' && word.charAt(pos+1)=='ّ')) ){
            return true;
        }
        if( pos>1 && ( word.charAt(pos-1)== ArabicCharacter.SUKUN)&& ( word.charAt(pos-2)== net.oujda_nlp_team.util.ArabicCharacter.WAW ) && pos+1<len && ( word.charAt(pos+1)=='َ') ){
            return false;       
        }
        
        if( pos>0 && ( word.charAt(pos-1)== ArabicCharacter.SUKUN)){
            if(pos+2<len && ( word.charAt(pos+1)=='ً') && ( word.charAt(pos+2)=='ا')){
                return false;
            }
            if(pos+4<len && ( word.charAt(pos+1)=='َ') && ( word.charAt(pos+2)=='ا') && ( word.charAt(pos+3)=='ن') && ( word.charAt(pos+4)=='ِ')){
                return false;
            }
            if( (pos+1<len && ( word.charAt(pos+1)=='َ')) || (pos+2<len && ( word.charAt(pos+1)=='ّ') && ( word.charAt(pos+2)=='َ'))){
                return true;
            }
            
        }
        return false;
    }
/*============================================================================*/
    private static boolean isNoRelatifCaracter(char caracter){
        switch(caracter){
            case 'د' :
            case 'ذ' :
            case 'ز' :
            case 'ر' :
            case ArabicCharacter.WAW : return false;
            default : return true;            
        }
    }
/*============================================================================*/    
    public static String correctStemHamza(String str){
        
        int len = str.length();
        String res = "";
        for(int i=0; i<len; i++){
            if( (str.charAt(i) == ArabicCharacter.HAMZA) || (str.charAt(i) == 'أ') ){
                if( i+1<len && str.charAt(i+1) == 'ا' ){
                    if(i-1>=0 && str.charAt(i-1) == 'ا' ){
                        res += str.charAt(i);
                    }
                    else{
                        res += "آ";
                        i++;
                    }
                }
                else{
                    res += str.charAt(i);
                }
            }
            else{
                res += str.charAt(i);
            }
            
        }
        
        return res;
    }
/*============================================================================*/
}
