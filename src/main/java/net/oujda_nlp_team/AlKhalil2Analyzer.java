package net.oujda_nlp_team;
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
import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.impl.NominalAnalyzerImpl;
import net.oujda_nlp_team.impl.VerbalAnalyzerImpl;
import net.oujda_nlp_team.impl.PropernounAnalyzerImpl;
import net.oujda_nlp_team.impl.ToolwordsAnalyzerImpl;
import net.oujda_nlp_team.impl.ExceptionalAnalyzerImpl;
import net.oujda_nlp_team.impl.CliticAnalyzerImpl;
import net.oujda_nlp_team.impl.PropernounUnderivedImpl;
import net.oujda_nlp_team.impl.ToolwordsUnderivedImpl;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.Stemming;
/*============================================================================*/
public class AlKhalil2Analyzer {
/*============================================================================*/
    public java.util.Map allResults = new java.util.HashMap();
    public java.util.Map allResultsBis = new java.util.HashMap();
    public java.util.Map<String, Double> map;
/*============================================================================*/
    private static final AlKhalil2Analyzer instance = new AlKhalil2Analyzer();
/*============================================================================*/
    public static AlKhalil2Analyzer getInstance() {return instance;}
/*============================================================================*/
    private AlKhalil2Analyzer() {}
/*============================================================================*/
    public void addRootStatistic() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.root");
        map = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
/**
 * <p>  
 *      Analyzes a given word and suggests all possible analyses of the word.
 *      If input word is fully or partially diacritized, all analyses that don’t 
 *      match the input diacritics will be filtered out.
 * </p>
 * @param normalizedWord
 * @return 
 */
    public synchronized java.util.List analyzerToken(String normalizedWord){     
        String unvoweledWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(normalizedWord);        
        normalizedWord = ArabicStringUtil.getInstance().correctErreur(normalizedWord);  
        //+-------------------------------------------+        
        java.util.List result = ExceptionalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord);
        //+-------------------------------------------+
        if(!result.isEmpty()){ return result;}
        else{
            //+----------------------------------------------------+
            java.util.List Segments = Stemming.getInstance().getListsSegment(unvoweledWord);
            java.util.Iterator<Segment> it_Seg = Segments.iterator();
            while(it_Seg.hasNext()){                
                Segment segment = it_Seg.next();
                //+----------------------------------------------------+
                //------------> ProperNouns <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Propernouns").equals("true")){
                    result.addAll(PropernounAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                }                
                //+----------------------------------------------------+
                //------------> ToolWord <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Toolwords").equals("true")){
                    result.addAll(ToolwordsAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                }                    
                //+----------------------------------------------------+
                //---------------> Noun <---------------//
                if( Database.getInstance().getResources().getProperty("Data.Output.Nouns").equals("true")) {
                    result.addAll(NominalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment));
                }                
                //+----------------------------------------------------+
                //---------------> Verb <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Verbs").equals("true")){
                    result.addAll(VerbalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment));
                }
                //+----------------------------------------------------+
                //---------------> Affixe <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Clitics").equals("true")){
                    result.add(CliticAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                }
            }
            return result; 
        }
    }
/*============================================================================*/
/**
 * <p>  
 *      Analyzes a given word and suggests all possible analyses of the word.
 *      If input word is fully or partially diacritized, all analyses that don’t 
 *      match the input diacritics will be filtered out.
 * </p>
 * @param normalizedWord
 * @return 
 */
    public synchronized ResultList processToken(String normalizedWord){
        ResultList result = new ResultList();        
        String unvoweledWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(normalizedWord);        
        normalizedWord = ArabicStringUtil.getInstance().correctErreur(normalizedWord);  
        result.addAll(ExceptionalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord));
        if(result.isAnalyzed()){return result;}
        else{
            java.util.List Segments = Stemming.getInstance().getListsSegment(unvoweledWord);
            java.util.Iterator<Segment> it_Seg = Segments.iterator();
            while(it_Seg.hasNext()){                
                Segment segment = it_Seg.next();        
                //------------> ProperNouns <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Propernouns").equals("true")){
                    result.addAll(PropernounAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                }
                //------------> ToolWord <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Toolwords").equals("true")){
                    result.addAll(ToolwordsAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                }                    
                //+----------------------------------------------------+
                //---------------> Noun <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Nouns").equals("true")) {
                    result.addAll(NominalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment));
                }                
                //+----------------------------------------------------+
                //---------------> Verb <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Verbs").equals("true")){
                    result.addAll(VerbalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment));
                }
                //+----------------------------------------------------+
                //---------------> Affixe <---------------//
                if(Database.getInstance().getResources().getProperty("Data.Output.Clitics").equals("true")){
                    result.add(CliticAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                }
            }
            if(!result.isAnalyzed() && normalizedWord.endsWith("ًا") && !normalizedWord.endsWith("ًّا") ){
                normalizedWord = normalizedWord.replaceAll("ًا$", "ًّا");
                it_Seg = Segments.iterator();
                while(it_Seg.hasNext()){                
                    Segment segment = it_Seg.next();        
                    //------------> ProperNouns <---------------//
                    if(Database.getInstance().getResources().getProperty("Data.Output.Propernouns").equals("true")){
                        result.addAll(PropernounAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment));
                    }
                    //---------------> Noun <---------------//
                    if(Database.getInstance().getResources().getProperty("Data.Output.Nouns").equals("true")) {
                        result.addAll(NominalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment));
                    }
                }
            }
            //result.sort(); 
            return result;
        }
    }
/*============================================================================*/
    public void clear(){
        NominalAnalyzerImpl.getInstance().clear();
        VerbalAnalyzerImpl.getInstance().clear();
        Stemming.getInstance().clear();
        ExceptionalAnalyzerImpl.getInstance().clear();        
        PropernounAnalyzerImpl.getInstance().clear(PropernounUnderivedImpl.getInstance());
        ToolwordsAnalyzerImpl.getInstance().clear(ToolwordsUnderivedImpl.getInstance());
    }
/*============================================================================*/
}