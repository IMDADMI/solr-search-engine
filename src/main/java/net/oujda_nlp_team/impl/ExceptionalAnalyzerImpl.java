package net.oujda_nlp_team.impl;
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
import net.oujda_nlp_team.entity.Exceptional;
import net.oujda_nlp_team.entity.Result;
/*============================================================================*/
public class ExceptionalAnalyzerImpl {
/*============================================================================*/
    private static final ExceptionalAnalyzerImpl instance = new ExceptionalAnalyzerImpl();
/*============================================================================*/
    public static ExceptionalAnalyzerImpl getInstance() {return instance;}
/*============================================================================*/    
    private ExceptionalAnalyzerImpl() {}
/*============================================================================*/        
    public synchronized java.util.List analyzedSegment(String normalizedWord, String unvoweledWord){        
        java.util.List result = new java.util.ArrayList();
        if(ExceptionalBuildImpl.getInstance().containsValue(unvoweledWord)){
            Exceptional ew = ExceptionalBuildImpl.getInstance().getExceptionalFormValue(unvoweledWord);
            String lemma = "الله";
            result.add(new Result(
                    ew.getVoweledform(), 
                    ew.getProclitic(), 
                    ew.getProclitic(), 
                    ew.getStem(), 
                    ew.getPartOfSpeech(),
                    "-",
                    "-",
                    lemma,
                    "-",
                    "-",
                    "-",
                    ew.getEnclitic(),
                    ew.getEnclitic(),
                    "1"));
        }
        
        return result; 
    
    }
/*============================================================================*/    
    public void clear(){ExceptionalBuildImpl.getInstance().clear();}
/*============================================================================*/    
}
