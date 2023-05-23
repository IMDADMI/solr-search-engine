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
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Voweled;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Validator;
/*============================================================================*/
public class PropernounAnalyzerImpl extends UnderivedAnalyzerFactory {
/*============================================================================*/
    private static final UnderivedAnalyzerFactory instance = new PropernounAnalyzerImpl();
/*============================================================================*/
    public static UnderivedAnalyzerFactory getInstance() {return instance;}
/*============================================================================*/
    private PropernounAnalyzerImpl() {}
/*============================================================================*/    
    @Override
    public synchronized java.util.List analyzedSegment(String normalizedWord, String unvoweledWord, Segment segment){
        String stem = segment.getStem();
        String procClass = segment.getProclitic().getClasse();
        String encClass = segment.getEnclitic().getClasse();
        //+-------------------------------------------+
        return ( !procClass.contains("V") 
                && !encClass.contains("V") ) 
                ? getPossiblesSolutions(normalizedWord, unvoweledWord, segment) 
                : new java.util.ArrayList();
        //+-------------------------------------------+
        
    }
/*============================================================================*/
    public java.util.List getPossiblesSolutions(String normalizedWord, String unvoweledWord, Segment segment){        
        /***    ***/
        java.util.List result = new java.util.ArrayList();
        /***    ***/
        String stem = segment.getStem();
        if( PropernounUnderivedImpl.getInstance().containsVal(stem) ){
            String[] lpn = PropernounUnderivedImpl.getInstance().getUnvoweledFormValue(segment.getStem()).split(" ");
            
            java.util.List listPN = new java.util.ArrayList();
            listPN.addAll(java.util.Arrays.asList(lpn));
            java.util.Iterator it_pn = listPN.iterator();
            while(it_pn.hasNext()){
                int Ipn = Integer.parseInt((String) it_pn.next());
                
                Voweled pn = new Voweled();
                
                boolean valid = false;
                String vowledWord = "";
               
       
                 
                String pf       = segment.getProclitic().getVoweledform();
                String sf       = segment.getEnclitic().getVoweledform();
                String pfNoDec  = segment.getProclitic().getVoweledform();
                String sfNoDec  = segment.getEnclitic().getVoweledform();
                
                
                
                if(Ipn <= PropernounUnderivedImpl.getInstance().size()){
                    pn = (Voweled) PropernounUnderivedImpl.getInstance().getVoweledFormValue(Ipn);
                    // the vowelization of some suffix should be discussed according to the last character of the stem
                    if (segment.getEnclitic().getUnvoweledform().equals("ه")) {
                        if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                            segment.getEnclitic().setVoweledform("هِ");
                        } else {
                            segment.getEnclitic().setVoweledform("هُ");
                        }
                    }
                    if (segment.getEnclitic().getUnvoweledform().equals("هما")) {
                        if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                            segment.getEnclitic().setVoweledform("هِمَا");
                        } else {
                            segment.getEnclitic().setVoweledform("هُمَا");
                        }
                    }
                    if (segment.getEnclitic().getUnvoweledform().equals("هم")) {
                        if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                            segment.getEnclitic().setVoweledform("هِمْ");
                        } else {
                            segment.getEnclitic().setVoweledform("هُمْ");
                        }
                    }
                    if (segment.getEnclitic().getUnvoweledform().equals("هن")) {
                        if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                            segment.getEnclitic().setVoweledform("هِنَّ");
                        } else {
                            segment.getEnclitic().setVoweledform("هُنَّ");
                        }
                    }
                    
                    
                    
                    pf = segment.getProclitic().getVoweledform();
                    sf = segment.getEnclitic().getVoweledform();
                    pfNoDec = segment.getProclitic().getVoweledform();
                    sfNoDec = segment.getEnclitic().getVoweledform();
                
                    String pfClass = segment.getProclitic().getClasse();
                    /***    ***/
                    if( segment.getProclitic().getClasse().equals("N4") && segment.getProclitic().getUnvoweledform().endsWith("ل") && pn.getVoweledform().startsWith("ا")){
                        String val = pn.getVoweledform();
                        if ((!segment.getEnclitic().getUnvoweledform().equals(""))) {
                            val = val.replace(ArabicCharacter.TEH_MARBUTA, ArabicCharacter.TEH);
                        }
                        vowledWord = pf + val.substring(1) + sf;
                    }
                    else{
                        if(Validator.getInstance().isDefinit(pfClass)){
                            String res = pn.getVoweledform();
                            if ((!segment.getEnclitic().getUnvoweledform().equals(""))) {
                                res = res.replace(ArabicCharacter.TEH_MARBUTA, ArabicCharacter.TEH);
                            }
                            
                            if (Validator.getInstance().isSolar(res.charAt(0))) {
                                vowledWord = pf 
                                        + ArabicCharacter.SUKUN 
                                        + res.substring(0, 1) 
                                        + ArabicCharacter.SHADDA 
                                        + res.substring(1) 
                                        + sf;
                                
                                
                            }
                            else{
                                vowledWord = pf 
                                        + ArabicCharacter.SUKUN 
                                        + res 
                                        + sf;
                            }
                            
                        
                        }
                        else{
                            String res = pn.getVoweledform();
                            if ((!segment.getEnclitic().getUnvoweledform().equals(""))) {
                                res = res.replace(ArabicCharacter.TEH_MARBUTA, ArabicCharacter.TEH);
                            }
                            vowledWord = pf + res + sf;
                        }
                        
                    
                       
                    }
                    
                    
                    valid = true;
                }
                
        
                if( valid && !(Validator.getInstance().notCompatible(normalizedWord, vowledWord, segment, false)) ){
                    String lemma = pn.getLemma();
                    String Root = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(pn.getRoot()); 
                    
                    String root = pn.getRoot().equals(Root)?pn.getRoot():"-" ; 
                    
                    if(!net.oujda_nlp_team.util.Settings.prefchoice || pf.equals("")){
                        pf = "#";
                        pfNoDec = "#";
                    }
                    else{
                        pf += " : " + segment.getProclitic().getDesc();  
                    }
                    
                    if(!net.oujda_nlp_team.util.Settings.suffixchoice || sf.equals("")){
                        sf = "#";
                        sfNoDec = "#";
                    }
                    else{
                        sf += " : " + segment.getEnclitic().getDesc();  
                    }
                    
                    String type;
                    if(net.oujda_nlp_team.util.Settings.choicePartOfSpeech){
                        PartOfSpeech pos = (PartOfSpeech) PropernounUnderivedImpl.getInstance().getPartOfSpeech(Integer.parseInt(pn.getPartOfSpeech()));
                        type = pos.getMain() 
                                + "|" + pos.getType()
                                + "|" + pos.getGender()
                                + "|" + pos.getNumber()
                                //+ "|" + pos.getDefinit()
                                ; 
                    }
                    else{
                        type="#";
                    }
                    
                    String cas;
                    if(net.oujda_nlp_team.util.Settings.choiceCaseOrMood){
                        cas = ((PropernounUnderivedImpl)PropernounUnderivedImpl.getInstance()).getCaseOrMood(Integer.parseInt(pn.getCase())).getValAR();
                    }
                    else{
                        cas="#";
                    }
                    //String[] vowledWords = new AlKhalil2.util.ArabMethod().Vowelizes(segment, pn.getVoweledform());
                    
                            
                    if(isValidNominalSolution(segment, cas, vowledWord, normalizedWord)){ 
                        result.add(
                                new Result(
                                        vowledWord, 
                                        pf, 
                                        pfNoDec, 
                                        ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(pn.getVoweledform()), 
                                        type, 
                                        "-", 
                                        "-", 
                                        lemma, 
                                        "-", 
                                        root, 
                                        cas, 
                                        sf, 
                                        sfNoDec, 
                                        "0.01100101"
                                )
                        );
                    }
                
                }
            }
        }
        else{
            //System.out.println(stem + ":" + unvoweled.containsVal(stem) + ":" + (segment.getProclitic().getClasse().indexOf("C1") != -1) + ":" + (segment.getEnclitic().getClasse().indexOf("C1") != -1));
        
        }
        return result;  
        
    }
/*============================================================================*/
    /**
     * 
     * @param segment
     * @param caseormood
     * @param vowledWords
     * @param normalizedWord
     * @return 
     */
    private boolean isValidNominalSolution(
            net.oujda_nlp_team.entity.Segment segment, 
            String caseormood, 
            String vowledWord, 
            String normalizedWord) {
        
        //+-------------------------------------------+
        String prefcalss = segment.getProclitic().getClasse();
        
        //+-------------------------------------------+    
        // Nunnation (tanwin) is not compatible with suffixes except the empty suffix
        // it is not compatible also with the definit article prefixes (ie N1 N2 N3 N5)
        if ( ((vowledWord.indexOf(ArabicCharacter.FATHATAN) != -1) 
                || (vowledWord.indexOf(ArabicCharacter.KASRATAN) != -1) 
                || (vowledWord.indexOf(ArabicCharacter.DAMMATAN) != -1) )
             && (
                (!segment.getEnclitic().getUnvoweledform().equals("")) || Validator.getInstance().isDefinit(prefcalss))) {
            return false;
        }
        //+-------------------------------------------+
        // Some prefixes (C2 C3 N2) are not compatible with the genitive case (majrour)
        if( ( prefcalss.equals("N2") || prefcalss.equals("C2") || prefcalss.equals("C3") ) 
                && caseormood.equals("مجرور")) {
            return false;
        }
        //+-------------------------------------------+
        // Some prefixes (N4 N5) are not compatible with the  non genitive cases
        if( (prefcalss.equals("N4") || prefcalss.equals("N5") ) 
            && !caseormood.equals("مجرور")) {
            return false;
        }
        //+-------------------------------------------+
        // Compatibility of the voweled word with the normalized word according 
        // to the possibly existing short vowels in the normalized word
        return true;//!Validator.notCompatible(normalizedWord, vowledWord, segment, false);
        
    }
/*============================================================================*/
}
