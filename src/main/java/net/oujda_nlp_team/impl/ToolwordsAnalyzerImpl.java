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
import net.oujda_nlp_team.util.ArabicCharacter;
import net.oujda_nlp_team.factory.UnderivedAnalyzerFactory;
import net.oujda_nlp_team.entity.Clitic;
import net.oujda_nlp_team.entity.Toolwords;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Validator;
/*============================================================================*/
public class ToolwordsAnalyzerImpl extends UnderivedAnalyzerFactory {
/*============================================================================*/
    private static final UnderivedAnalyzerFactory instance = new ToolwordsAnalyzerImpl();
/*============================================================================*/
    public static UnderivedAnalyzerFactory getInstance() {return instance;}
/*============================================================================*/
    private ToolwordsAnalyzerImpl() {}   
/*============================================================================*/
    @Override
    public synchronized java.util.List analyzedSegment(String normalizedWord, String unvoweledWord, Segment segment){
        String stem = segment.getStem();
        String procClass = segment.getProclitic().getClasse();
        String encClass = segment.getEnclitic().getClasse();
        //+-------------------------------------------+
        return getPossiblesSolutions(normalizedWord, unvoweledWord, segment);
        /*
        return ( !procClass.contains("V") 
                && !encClass.contains("V") ) 
                ? getPossiblesSolutions(normalizedWord, unvoweledWord, segment) 
                : new java.util.ArrayList();
        *///+-------------------------------------------+
        
    }
/*============================================================================*/
    /**
     * 
     * @param normalizedWord
     * @param unvoweledWord
     * @param segment
     * @return 
     */
    public java.util.List getPossiblesSolutions(String normalizedWord, String unvoweledWord, Segment segment){
        //+----------------------------------------------------+
        java.util.List result = new java.util.ArrayList();
        //+----------------------------------------------------+
        Clitic enC = segment.getEnclitic();
        Clitic proC = segment.getProclitic();
        //+----------------------------------------------------+
        java.util.List toolwordlist = possibleToolWords(segment);
        java.util.Iterator<Toolwords> it_TW = toolwordlist.iterator();
        while(it_TW.hasNext()){
            //+-------------------------------------------+
            Toolwords tw = it_TW.next();
            //+-------------------------------------------+
            if (enC.getUnvoweledform().equals(""+ArabicCharacter.HEH)) {
                if (tw.getVoweledform().endsWith(""+ArabicCharacter.YEH) || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                    enC.setVoweledform("هِ");
                } 
                else{
                    enC.setVoweledform("هُ");
                }
            }
            else{    
                if (enC.getUnvoweledform().equals(""+ArabicCharacter.HEH+ArabicCharacter.MEEM+ArabicCharacter.ALEF)) {
                    if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")){
                        enC.setVoweledform("هِمَا");
                    } 
                    else {
                        enC.setVoweledform("هُمَا");
                    }
                }
                else{
                    if (enC.getUnvoweledform().equals("هم")) {
                        if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                            enC.setVoweledform("هِمْ");
                        } 
                        else {
                            enC.setVoweledform("هُمْ");
                        }
                    }
                    else{
                        if (enC.getUnvoweledform().equals("هن")) {
                            if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                                enC.setVoweledform("هِنَّ");
                            } 
                            else {
                                enC.setVoweledform("هُنَّ");
                            }
                        }
                    }
                }
            }
            //+-------------------------------------------+
            String lemma = tw.getLemma();
            String Root = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(tw.getRoot()); 
            String root = tw.getRoot().equals(Root)?tw.getRoot():"-" ;         
                    
                    
            String vowledWord;
            //+-------------------------------------------+
            if( tw.getClassProclitic().contains("N1")
                    || tw.getClassProclitic().contains("N2") 
                    || tw.getClassProclitic().contains("N3") 
                    || tw.getClassProclitic().contains("N5") 
            ){
                //+-------------------------------------------+
                String res = tw.getVoweledform();
                if(Validator.getInstance().isSolar(res.charAt(0))) {
                    vowledWord = proC.getVoweledform() 
                            + ArabicCharacter.SUKUN 
                            + res.substring(0, 1) 
                            + ArabicCharacter.SHADDA
                            + res.substring(1)
                            + enC.getVoweledform();
                }
                else{
                    vowledWord = proC.getVoweledform() 
                            + ArabicCharacter.SUKUN 
                            + res
                            + enC.getVoweledform();
                }
            }
            else{
                vowledWord = proC.getVoweledform()  + tw.getVoweledform() + enC.getVoweledform();
            }
            //+-------------------------------------------+
            String pf = proC.getVoweledform();
            String sf = enC.getVoweledform();
            String pfNoDec = proC.getVoweledform();
            String sfNoDec = enC.getVoweledform();
            //+-------------------------------------------+
            if (!net.oujda_nlp_team.util.Settings.prefchoice || proC.getVoweledform().equals("")) {
                pf = "#"; // replace the empty prefix by # for display purpose
                pfNoDec = "#"; // replace the empty prefix by # for display purpose
            }
            else{
                pf += " : " + proC.getDesc();
            }
            //+-------------------------------------------+
            if (!net.oujda_nlp_team.util.Settings.suffixchoice || enC.getVoweledform().equals("")) {
                sf = "#"; //replace the empty suffix by #
                sfNoDec = "#"; //replace the empty suffix by #
            }
            else{
                sf += " : " + enC.getDesc();
            }
            //+-------------------------------------------+
            String type;
            if( net.oujda_nlp_team.util.Settings.choicePartOfSpeech ){
                int id = Integer.parseInt(tw.getPartOfSpeech());
                type = (String) ToolwordsUnderivedImpl.getInstance().getPartOfSpeech(id);
            }
            else{
                type = "#";
            }
            //+----------------------------------------------------+
            vowledWord = vowledWord.replaceAll("لَلْلّ", "لَلّ").replaceAll("لِلْلّ", "لِلّ");
            vowledWord = vowledWord.replaceAll("لِالّ", "لِلّ").replaceAll("لَالّ", "لَلّ");
            //+----------------------------------------------------+
            
            if (!(Validator.getInstance().notCompatible(normalizedWord, vowledWord ))) {
                //check the compatibility  between
                //the word voweled form produced by the analyzer and the short vowels possibly
                //existing  in the normlized form of the input word
                Result r = new Result(
                        vowledWord, 
                        pf, 
                        pfNoDec, 
                        ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(tw.getVoweledform()), 
                        type, 
                        "-", 
                        "-", 
                        lemma, 
                        "-", 
                        root, 
                        "-",
                        sf, 
                        sfNoDec, 
                        "0.0001100101"
                );
                // add the tool word found to the result list to be returned
                result.add(r); 
            }
        }
        //+----------------------------------------------------+
        return result;  
        //+----------------------------------------------------+
    }
/*============================================================================*/ 
    /**
     * 
     * @param segment
     * @return 
     */
    public java.util.List possibleToolWords(Segment segment){
        java.util.List result = new java.util.ArrayList();        
        String stem = segment.getStem();
        
        if(ToolwordsUnderivedImpl.getInstance().containsVal(stem)){
            
            java.util.List TW = java.util.Arrays.asList(ToolwordsUnderivedImpl.getInstance().getUnvoweledFormValue(stem).split(" "));
            java.util.Iterator<String> it_TW = TW.iterator();
            while(it_TW.hasNext()){
                
                int id = Integer.parseInt(it_TW.next()); 
                Toolwords tw = (Toolwords) ToolwordsUnderivedImpl.getInstance().getVoweledFormValue(id);
                if ( tw.getClassProclitic().contains(segment.getProclitic().getClasse()) && tw.getClassEnclitic().contains(segment.getEnclitic().getClasse())) {
                    result.add(tw);
                }
            }
        }
        //+----------------------------------------------------+
        return result;
        //+----------------------------------------------------+
    }
/*============================================================================*/
}
