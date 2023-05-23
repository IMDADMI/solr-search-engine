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
import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.util.ArabicCharacter;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Validator;
import net.oujda_nlp_team.util.Static;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.interfaces.IDerivedAnalyzer;
import net.oujda_nlp_team.factory.DerivedAnalyzerFactory;
/*============================================================================*/
public class NominalAnalyzerImpl extends DerivedAnalyzerFactory implements IDerivedAnalyzer {
/*============================================================================*/
    private static final IDerivedAnalyzer instance = new NominalAnalyzerImpl();
/*============================================================================*/
    public static IDerivedAnalyzer getInstance() {return instance;}
/*============================================================================*/
    private boolean possibleSolution;       
/*============================================================================*/
    public NominalAnalyzerImpl() {
        this.possiblePattern    = new java.util.HashMap();        
    }
/*============================================================================*/
    /**
     * This method return all possible nominal solutions of the input word
     * if the prefix and suffix classes are not verbal (V)
     * @param word
     * @param segment
     * @return 
     */
    @Override
    public synchronized java.util.List analyzedSegment(String word, Segment segment){
        //+-------------------------------------------+
        int Nouns_Pattern_Min  = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Nouns.Patterns.Min"));
        int Nouns_Pattern_Max  = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Nouns.Patterns.Max"));        
        String stem = segment.getStem();
        String procClass = segment.getProclitic().getClasse();
        String encClass = segment.getEnclitic().getClasse();
        return ( (stem.length() >= Nouns_Pattern_Min && stem.length()<= Nouns_Pattern_Max)
                && !procClass.contains("V") 
                && !encClass.contains("V") ) 
                ? getPossiblesSolutions(word, segment) 
                : new java.util.ArrayList();
    }    
/*============================================================================*/
    @Override
    public synchronized java.util.List getPossiblesSolutions(String normalizedWord, Segment segment){
        //+-------------------------------------------+
        java.util.List result = new java.util.ArrayList();
        //+-------------------------------------------+
        java.util.Map<String, java.util.Map> possibleSolutions;// = new java.util.HashMap();
        //+-------------------------------------------+
        
        String Stem = segment.getStem();
        
            
        possibleSolutions = (possiblePattern.containsKey(Stem))?possiblePattern.get(Stem):possibleSolutions(normalizedWord, segment, NominalDerivedImpl.getInstance());
        //+---------------------------+  
        java.util.Map<String, java.util.Set> iMapRes = getAllRoot_Formulas(possibleSolutions);
        for(java.util.Map.Entry<String, java.util.Set> entry : iMapRes.entrySet()){
            String s_root = entry.getKey();
            
                        
           
            java.util.Iterator<Formulas> it_solPatterns = entry.getValue().iterator();
            while(it_solPatterns.hasNext()) {
                //+-------------------------------------------+
                Formulas sol = it_solPatterns.next();
                //+-------------------------------------------+
                int lStem = segment.getStem().length();
                String Solutions[] = getInfoResults(sol, lStem, NominalDerivedImpl.getInstance()); 
                int i=0;
                String diac = Solutions[i];
                i++;
                double diacFreq = Double.parseDouble(Solutions[i]);
                i++;
                //+-------------------------------------------+
                String canonicPattern = "";
                double canonicPatternFreq = 0.0;
                if(net.oujda_nlp_team.util.Settings.choicePatternStem){
                    canonicPattern = Solutions[i];
                    i++;
                    canonicPatternFreq = Double.parseDouble(Solutions[i]);
                    i++;
                }
                //+-------------------------------------------+
                String lemma = "";
                double lemmaFreq = 0.0;
                if(net.oujda_nlp_team.util.Settings.choiceLemma){
                    lemma = ArabicStringUtil.getInstance().getWordFromRootAndPattern(s_root, Solutions[i]);
                    i++;
                    lemmaFreq = Double.parseDouble(Solutions[i]);
                    i++;
                }
                //+-------------------------------------------+
                String lemmapattern = "";
                double lemmapatternFreq = 0.0;
                if(net.oujda_nlp_team.util.Settings.choicePatternLemma){
                    lemmapattern = Solutions[i];
                    i++;
                    lemmapatternFreq = Double.parseDouble(Solutions[i]);
                    i++;
                }
                //+-------------------------------------------+
                int idpartofspeech = (Solutions[i]!=null)? Integer.parseInt(Solutions[i]):0;
                double partOfSpeechFreq = Double.parseDouble(NominalDerivedImpl.getInstance().getPartOfSpeech(idpartofspeech).getFreq());
                //+---------------------------+
                VEntity rPartOfSpeech[] = getPartOfSpeech(idpartofspeech);                
                VEntity stMain = rPartOfSpeech[0];                
                VEntity stType = rPartOfSpeech[1];                
                VEntity stDefinit = rPartOfSpeech[2];
                VEntity stGender = rPartOfSpeech[3];                
                VEntity stNbRoot = rPartOfSpeech[4];                
                VEntity stNumber = rPartOfSpeech[5];
                //+---------------------------+
                i++;
                //+----------------------------------------------------+
                int idCaseOrMood = Integer.parseInt(Solutions[i]);
                VEntity stCaseOrMood = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getCaseOrMood(idCaseOrMood);
                //+----------------------------------------------------+  
                String[] vowledWords = ArabicStringUtil.getInstance().Vowelize(segment, diac);
                //+----------------------------------------------------+    
                String vowledWord = vowledWords[0];
                if( !stNumber.getValAR().equals("مثنى") && vowledWord.endsWith("ءَانِ")){
                    vowledWord = vowledWord.replaceAll("ءَانِ", "آنِ");
                }
                //+----------------------------------------------------+   
                vowledWord = vowledWord.replaceAll("لَلْلّ", "لَلّ").replaceAll("لِلْلّ", "لِلّ");
                //+----------------------------------------------------+
                boolean procDef = false;
                if (segment.getProclitic().getClasse().equals("N1") || segment.getProclitic().getClasse().equals("N2") || segment.getProclitic().getClasse().equals("N3") || segment.getProclitic().getClasse().equals("N5")) {
                    procDef = true;
                }
                boolean pDefinit = true;
                if(stDefinit.getValAR().equals("معرف بأل") && !procDef){
                    pDefinit = false;
                }
                if(procDef && (stDefinit.getValAR().equals("مضاف إلى معرفة") || stDefinit.getValAR().equals("مضاف إلى نكرة") || stDefinit.getValAR().equals("غير مضاف"))){
                    pDefinit = false;
                }
                
                    
                
                
                if(pDefinit && isValidNominalSolution(segment, stCaseOrMood.getValAR(), vowledWord, normalizedWord) ) {
                     //+----------------------------------------------------+                     
                    String[] Sol = Interpret(segment, stNumber.getValAR(), stGender.getValAR(), stDefinit.getValAR(), canonicPattern);//interpretation of the coded informations
                    //+----------------------------------------------------+
                    String stPartOfSpeech = stMain.getValAR()
                                + "|" + stType.getValAR()
                                + "|" + stNumber.getValAR()
                                + "|" + stGender.getValAR()
                                + "|" + Sol[2];
                    //+----------------------------------------------------+
                    if(this.possibleSolution){
                        //+----------------------------------------------------+
                        String stem = net.oujda_nlp_team.util.CreatHamza.correctStemHamza(segment.getStem());
                        //+----------------------------------------------------+
                        double freq = (diacFreq + canonicPatternFreq + lemmaFreq + lemmapatternFreq + partOfSpeechFreq)/5;
                        //+----------------------------------------------------+
                        java.text.DecimalFormat df = new java.text.DecimalFormat(); 
                        df.setMaximumFractionDigits ( 10 );
                        df.setMinimumFractionDigits ( 10 );
                        df.setDecimalSeparatorAlwaysShown ( true ) ; 
                        //+----------------------------------------------------+
                        Sol[4] = "" + (df.format(freq)).replaceAll(",", ".");
                        //---------------
                        if(Static.StaticUnvoweled){
                            if(vowledWord.equals(normalizedWord)){
                                Result r = new Result( 
                                        vowledWord, 
                                        Sol[0], 
                                        Sol[5], 
                                        stem, 
                                        stPartOfSpeech, 
                                        diac, 
                                        canonicPattern, 
                                        lemma, 
                                        lemmapattern, 
                                        s_root, 
                                        stCaseOrMood.getValAR(), 
                                        Sol[3], 
                                        Sol[6], 
                                        Sol[4]
                                );
                                result.add(r);
                            } 
                        }
                        else{
                            String t = "";
                            if( ( segment.getProclitic().getClasse().equals("N4") || segment.getProclitic().getClasse().equals("N5") ) && stCaseOrMood.getValAR().equals("منصوب") && vowledWord.equals(normalizedWord) ) {
                                t = ("مجرور");
                            }
                            Result r = new Result( 
                                    vowledWord, 
                                    Sol[0], 
                                    Sol[5], 
                                    stem, 
                                    stPartOfSpeech, 
                                    diac, 
                                    canonicPattern, 
                                    lemma, 
                                    lemmapattern, 
                                    s_root, 
                                    (!t.equals("")?t:stCaseOrMood.getValAR()), 
                                    Sol[3],
                                    Sol[6], 
                                    Sol[4]
                            );
                            result.add(r);
                        }
                    }   
                }
            }
        } 
        return result;
    }
/*============================================================================*/
    private boolean isValidNominalSolution( Segment segment, String caseormood, String vowledWord, String normalizedWord) {
        //+-------------------------------------------+
        String procCalss = segment.getProclitic().getClasse();
        String encUnv = segment.getEnclitic().getUnvoweledform();
        //+-------------------------------------------+   
        // Nunnation (tanwin) is not compatible with suffixes except the empty suffix
        // it is not compatible also with the definit article prefixes (ie N1 N2 N3 N5)
        if (    ( 
                    vowledWord.contains(""+ArabicCharacter.FATHATAN) 
                    || vowledWord.contains(""+ArabicCharacter.KASRATAN) 
                    || vowledWord.contains(""+ArabicCharacter.DAMMATAN) 
                ) 
                && 
                (   !encUnv.equals("") || Validator.getInstance().isDefinit(procCalss)
                )) {
            return false;
        }
        //+-------------------------------------------+
        //Some prefixes (C2 C3 N2) are not compatible with the genitive case (majrour)
        if( ( procCalss.equals("N2") || procCalss.equals("C2") || procCalss.equals("C3") ) 
                && caseormood.equals("مجرور")) {
            return false;
        }
        //+-------------------------------------------+
        // Some prefixes (N4 N5) are not compatible with the  non genitive cases
        if( ( procCalss.equals("N4") || procCalss.equals("N5") ) && !caseormood.equals("مجرور") 
                && !vowledWord.equals(normalizedWord) ) {
            //System.out.println(vowledWord + ':' + normalizedWord);
            return false;
        }      
        //+-------------------------------------------+
        // Compatibility of the voweled word with the normalized word according 
        // to the possibly existing short vowels in the normalized word
        return !Validator.getInstance().notCompatible(normalizedWord, vowledWord, segment, false);
        //+-------------------------------------------+
    }
/*============================================================================*/
    /**
     * 
     * @param segment
     * @param number
     * @param gender
     * @param definit
     * @param canonicPattern
     * @return 
     */
    public String[] Interpret( Segment segment, String number, String gender, String definit, String canonicPattern) {
        //+---------------------------+
        String unDiac = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(canonicPattern);
        String result[] = new String[7];
        //+---------------------------+
        String procCalss    = segment.getProclitic().getClasse();
        String encCalss     = segment.getEnclitic().getClasse();
        //+---------------------------+
        StringBuilder prefix = new StringBuilder();
        StringBuilder suffix = new StringBuilder();
        StringBuilder priority = new StringBuilder();
        //+---------------------------+        
        boolean pass = true;
        this.possibleSolution = true;
        //+-------------------------------------------+
        prefix.append("");
        suffix.append("");
        priority.append("1");
        //+-------------------------------------------+
        if (segment.getProclitic().getVoweledform().equals("")) {
            result[0] = "#";
            result[5] = "#";
        } 
        else {
            result[0] = segment.getProclitic().getVoweledform() + ": " + segment.getProclitic().getDesc();
            result[5] = segment.getProclitic().getVoweledform();
        }

        if (segment.getEnclitic().getVoweledform().equals("")) {
            result[3] = "#";
            result[6] = "#";
        } 
        else {
            result[3] = segment.getEnclitic().getVoweledform() + ": " + segment.getEnclitic().getDesc();
            result[6] = segment.getEnclitic().getVoweledform();
        }
        //+-------------------------------------------+  
        //+-------------------------------------------+
        if( gender.equals("مؤنث") && number.equals("مفرد") && (segment.getStem().endsWith("ة")) ){
            //+----------------------------------------------------------+
            suffix.append("ة: تاء التأنيث");
            pass = true;
            //+----------------------------------------------------------+
        }
        else {
            if( gender.equals("مؤنث") && number.equals("مثنى") ){
                //+----------------------------------------------------------+
                if (segment.getStem().endsWith("تان")) {
                    suffix.append("ة: تاء التأنيث + ان: علامة الإعراب");
                    pass = true;
                }
                else{
                    if (segment.getStem().endsWith("تين")) {
                        suffix.append("ة: تاء التأنيث + ين: علامة الإعراب");
                        pass = true;
                    }
                    else{
                        if (segment.getStem().endsWith("تا")) {
                            suffix.append("ة: تاء التأنيث + ا: علامة الإعراب");
                            pass = false;
                        }
                        else{
                            if (segment.getStem().endsWith("تي")) {
                                suffix.append("ة: تاء التأنيث + ي: علامة الإعراب");
                                pass = false;
                            }
                        }
                    }
                }
            }
            else{
                if( gender.equals("مؤنث") && number.equals("جمع") ){
                    
                    if (segment.getStem().endsWith("ات")) {
                        suffix.append("ات: تاء التأنيث");
                        pass = true;
                    }
                    
                }
                else{
                    if( gender.equals("مذكر") && number.equals("مثنى") ){
                        
                        if (segment.getStem().endsWith("ان")) {
                            suffix.append("ان: علامة الإعراب");
                            pass = true;
                        }
                        else{
                            if (segment.getStem().endsWith("ين")) {
                                suffix.append("ين: علامة الإعراب");
                                pass = true;
                            }
                            else{
                                if (segment.getStem().endsWith("ا")) {
                                    suffix.append("ا: علامة الإعراب");
                                    pass = false;
                                }
                                else{
                                    if (segment.getStem().endsWith("ي")) {
                                        suffix.append("ي: علامة الإعراب");
                                        pass = false;
                                    }
                                }
                            }
                        }
                    }
                    else{//*****
                        if(!unDiac.equals("مفاعل") && !canonicPattern.equals("فَعَالِي") && gender.equals("مذكر") && number.equals("جمع") ){
                            if (segment.getStem().endsWith("ون")) {
                                suffix.append("ون: علامة الإعراب");
                                pass = true;
                            }
                            else{
                                if (segment.getStem().endsWith("ين")) {
                                    suffix.append("ين: علامة الإعراب");
                                    pass = true;
                                }
                                else{
                                    if (segment.getStem().endsWith("و")) {
                                        suffix.append("و: علامة الإعراب");
                                        pass = false;
                                    }
                                    else{
                                        if (segment.getStem().endsWith("ي")) {
                                            suffix.append("ي: علامة الإعراب");
                                            pass = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //+-------------------------------------------+
        //+-------------------------------------------+
        if (procCalss.equals("N1") || procCalss.equals("N2") || procCalss.equals("N3") || procCalss.equals("N5")) {
            //+-------------------------------------------+
            if(pass){result[2] = "معرف";}
            else{
                result[2] = "";
                this.possibleSolution = false;
            }
            //+-------------------------------------------+
        }
        else{result[2] = definit;}
        //+-------------------------------------------+
        result[4] = priority.toString();
        
        if (result[0].equals("#") && (!prefix.toString().equals(""))) {
            result[0] = prefix.toString();
        } 
        else {
            if ((!result[0].equals("#")) && (!prefix.toString().equals(""))) {
                result[0] = result[0] + " + " + prefix.toString();
            }
        }
        if (result[3].equals("#") && (!suffix.toString().equals(""))) {
            result[3] = suffix.toString();
        } 
        else {
            if ((!result[3].equals("#")) && (!suffix.toString().equals(""))) {
                result[3] = suffix.toString() + " + " + result[3];
            }
        }
        //+-------------------------------------------+
        return result;
        //+-------------------------------------------+        
    }    
/*============================================================================*/
    public java.util.Map<String, java.util.Set> getAllRoot_Formulas(java.util.Map<String, java.util.Map> solutions){
        java.util.Map<String, java.util.Set> iMapRes = new java.util.HashMap();
        for(java.util.Map.Entry<String, java.util.Map> unvoweled : solutions.entrySet()){            
            String s_unvoweled = unvoweled.getKey();
            
            java.util.Map<String, java.util.List> Roots = unvoweled.getValue();
            for(java.util.Map.Entry<String, java.util.List> roots : Roots.entrySet() ){
                String s_root = roots.getKey();
                java.util.List solutionPatterns = roots.getValue();
                if(iMapRes.containsKey(s_root)){
                    iMapRes.get(s_root).addAll(solutionPatterns);
                }
                else{
                    java.util.Set set = new java.util.HashSet();
                    set.addAll(solutionPatterns);
                    iMapRes.put(s_root, set);
                }
            }        
        }        
        return iMapRes;
    }
/*============================================================================*/
    public VEntity[] getPartOfSpeech( int idpartofspeech){
        VEntity result[] = new VEntity[6];
        ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).addDescPartOfSpeechType();
        ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).addDescPartOfSpeechDefinit();
        ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).addDescPartOfSpeechGender();
        ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).addDescPartOfSpeechMain();
        ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).addDescPartOfSpeechNbRoot() ;
        ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).addDescPartOfSpeechNumber();
        PartOfSpeech pos = NominalDerivedImpl.getInstance().getPartOfSpeech(idpartofspeech);
        result[0] = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getDescPartOfSpeechMain(Integer.parseInt(pos.getMain()));
        result[1] = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getDescPartOfSpeechType(Integer.parseInt(pos.getType()));
        result[2] = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getDescPartOfSpeechDefinit(Integer.parseInt(pos.getDefinit()));
        result[3] = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getDescPartOfSpeechGender(Integer.parseInt(pos.getGender()));
        result[4] = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getDescPartOfSpeechNbRoot(Integer.parseInt(pos.getNbroot()));
        result[5] = ((NominalDerivedImpl) NominalDerivedImpl.getInstance()).getDescPartOfSpeechNumber(Integer.parseInt(pos.getNumber()));
        return result;
    }
/*============================================================================*/
    @Override
    public void clear(){
        NominalDerivedImpl.getInstance().clear();
        this.possiblePattern = new java.util.HashMap();
    }
/*============================================================================*/
}
