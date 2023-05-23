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
import net.oujda_nlp_team.factory.DerivedAnalyzerFactory;
import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.util.Static;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.util.Validator;
import net.oujda_nlp_team.interfaces.IDerivedAnalyzer;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.CreatHamza;
import net.oujda_nlp_team.util.Settings;
/*============================================================================*/
public class VerbalAnalyzerImpl extends DerivedAnalyzerFactory implements IDerivedAnalyzer {
/*============================================================================*/
    private static final IDerivedAnalyzer instance = new VerbalAnalyzerImpl();
/*============================================================================*/
    public static IDerivedAnalyzer getInstance() {return instance;}
/*============================================================================*/
    public VerbalAnalyzerImpl() {
//        this.derived = VerbalDerivedImpl.getInstance();
        this.possiblePattern = new java.util.HashMap();
    }
//    //+---------------------------------+
//    public VerbalAnalyzerImpl(boolean rapide) {
//        this.derived = VerbalDerivedImpl.getInstance();
//        this.possiblePattern = new java.util.HashMap();
//    }
    //+---------------------------------+
    @Override
    public synchronized java.util.List analyzedSegment(String word, Segment segment) {
        int Verbs_Pattern_Min  = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Verbs.Patterns.Min"));
        int Verbs_Pattern_Max  = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Verbs.Patterns.Max"));
        String stem = segment.getStem();
        String procClass = segment.getProclitic().getClasse();
        String encClass = segment.getEnclitic().getClasse();
        return ( stem.length() >= Verbs_Pattern_Min && stem.length()<= Verbs_Pattern_Max 
                && !procClass.contains("N") 
                && !encClass.contains("N") ) 
                ? getPossiblesSolutions(word, segment) 
                : new java.util.ArrayList();
    }
    //+---------------------------------+
    @Override
    public synchronized java.util.List getPossiblesSolutions( String normalizedWord, Segment segment) {
        
        //+---------------------------+
        java.util.List result = new java.util.ArrayList();        
        java.util.Map<String, java.util.Map> possibleSolutions;
        //+---------------------------+
        String Stem = segment.getStem();
        possibleSolutions = (possiblePattern.containsKey(Stem))?possiblePattern.get(Stem):possibleSolutions(normalizedWord, segment, VerbalDerivedImpl.getInstance());
        //+---------------------------+        
        java.util.Map<String, java.util.Set> iMapRes = getAllRoot_Formulas(possibleSolutions);
        for(java.util.Map.Entry<String, java.util.Set> entry : iMapRes.entrySet()){
            String s_root = entry.getKey();
            
                
            java.util.Iterator<Formulas> it_solPatterns = entry.getValue().iterator();
            while(it_solPatterns.hasNext()){
                Formulas sol = it_solPatterns.next();
                int lenStem = segment.getStem().length();
                String Solutions[] = getInfoResults(sol, lenStem, VerbalDerivedImpl.getInstance());
                int i=0;
                String diac = Solutions[i];
                i++;
                double diacFreq = Double.parseDouble(Solutions[i]);
                i++;
                String canonicPattern = "";
                double canonicPatternFreq = 0.0;
                if(Settings.choicePatternStem){
                    canonicPattern = Solutions[i];
                    i++;
                    canonicPatternFreq = Double.parseDouble(Solutions[i]);
                    i++;
                }
                String lemma = "";
                double lemmaFreq = 0.0;
                if(Settings.choiceLemma){
                    lemma = ArabicStringUtil.getInstance().getWordFromRootAndPattern(s_root, Solutions[i]);
                    i++;
                    lemmaFreq = Double.parseDouble(Solutions[i]);
                    i++;
                }
                String lemmapattern = "";
                double lemmapatternFreq = 0.0;
                if(Settings.choicePatternLemma){
                    lemmapattern = Solutions[i];
                    i++;
                    lemmapatternFreq = Double.parseDouble(Solutions[i]);
                    i++;
                }    
                int idpartofspeech = Integer.parseInt(Solutions[i]);
                double partOfSpeechFreq = Double.parseDouble(VerbalDerivedImpl.getInstance().getPartOfSpeech(idpartofspeech).getFreq());
                //+---------------------------+
                VEntity rPartOfSpeech[] = getPartOfSpeech(idpartofspeech);                    
                VEntity stMain = rPartOfSpeech[0];                    
                VEntity stType = rPartOfSpeech[1];                    
                VEntity stAugmented = rPartOfSpeech[2];                    
                VEntity stEmphasized = rPartOfSpeech[3];                    
                VEntity stNbRoot = rPartOfSpeech[4];                    
                VEntity stPerson = rPartOfSpeech[5];                    
                VEntity stPerson2 = rPartOfSpeech[6];                    
                VEntity stTransitivity = rPartOfSpeech[7];                    
                VEntity stVoice = rPartOfSpeech[8];
                //+---------------------------+
                String stPartOfSpeech = stMain.getValAR()
                            + "|" + stType.getValAR()
                            + "|" + stEmphasized.getValAR()
                            + "|" + stVoice.getValAR()
                            + "|" + stNbRoot.getValAR()
                            + "|" + stAugmented.getValAR()
                            + "|" + stPerson.getValAR()
                            + "|" + stPerson2.getValAR()
                            + "|" + stTransitivity.getValAR();
                i++;
                //+---------------------------+    
                int idCaseOrMood = Integer.parseInt(Solutions[i]);
                VEntity stCaseOrMood = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getCaseOrMood(idCaseOrMood);
                //+---------------------------+
                String[] vowledWords = ArabicStringUtil.getInstance().Vowelize(segment, diac);
                String vowledWord = vowledWords[0];
                if ( isValidVerbalSolution( segment, vowledWords, normalizedWord, stType.getValAR(), stVoice.getValAR(), stPerson2.getValAR(), stCaseOrMood.getValAR(), stTransitivity.getValAR()) ) {
                    //+---------------------------+
                    String[] Sol = Interpret( segment, s_root, stType.getValAR(), stPerson.getValAR(), stPerson2.getValAR(), stEmphasized.getValAR(), stCaseOrMood.getValAR());//interpretation of the coded informations
                    String stem = CreatHamza.correctStemHamza(segment.getStem());  
                    
                    
                    
                    //+---------------------------+
                    double freq = (diacFreq + canonicPatternFreq + lemmaFreq + lemmapatternFreq + partOfSpeechFreq)/5;
                    java.text.DecimalFormat df = new java.text.DecimalFormat() ; 
                    df.setMaximumFractionDigits ( 10 );
                    df.setMinimumFractionDigits ( 10 );
                    df.setDecimalSeparatorAlwaysShown ( true ) ; 
                    Sol[4] = "" + (df.format(freq)).replaceAll(",", ".");
                    //+---------------------------+
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
            }
        }
        return result;
    }

    //+-------------------------------------------+
    /**
     * 
     * @param segment
     * @param vowledWords
     * @param normalizedWord
     * @param type
     * @param voice
     * @param person2
     * @param caseormood
     * @param transitivity
     * @return 
     */
    private boolean isValidVerbalSolution(
            Segment segment,
            String[] vowledWords,
            String normalizedWord,
            String type,
            String voice,
            String person2,
            String caseormood,
            String transitivity
    ){
        //+-------------------------------------------+
        boolean valid = true;
        //+-------------------------------------------+
        String prefcalss = segment.getProclitic().getClasse();
        String suffcalss = segment.getEnclitic().getClasse();
        //+-------------------------------------------+
        String vowledWord = vowledWords[0];
        //+-------------------------------------------+
        // intransitive verbs are not compatible with non empty suffixes
        if (transitivity.equals("لازم") && (!segment.getEnclitic().getUnvoweledform().equals(""))) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        //some prefixes (V1 class) are not compatible with the  non imperfect indicative verbs ( mouDarie ghayr marfoue)
        if (prefcalss.equals("V1") && !type.equals("مضارع") && !caseormood.equals("مرفوع")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        //some prefixes (V2 class )are not compatible with the non imperfect subjunctive verbs ( mouDarie ghayr mansoub)
        if (prefcalss.equals("V2") && !type.equals("مضارع") && !caseormood.equals("منصوب")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        //some prefixes (V3 class )are not compatible with the non imperfect jussive verbs ( mouDarie ghayr majzoum)
        if (prefcalss.equals("V3")  && !type.equals("مضارع") && !caseormood.equals("مجزوم")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        //some prefixes (C2 class )are not compatible with the imperative verbs ( amr)
        if (prefcalss.equals("C2") && type.equals("أمر")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        //some suffixes (except C1 suffixes )are not compatible with the  imperfect jussive verbs ( mouDarie majzoum)
        if ( !suffcalss.equals("C1") && type.equals("مضارع") && caseormood.equals("مجزوم")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        //some suffixes (V2 and V3 class )are not compatible with the imperative verbs ( amr)
        if((suffcalss.equals("V2") || suffcalss.equals("V3")) && type.equals("أمر")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        if(suffcalss.equals("V4") && !person2.equals("أنتم")) {
            valid = false;
            return valid;
        }
        //+-------------------------------------------+
        // Compatibility of the voweled word with the normalized word according
        //to the possibly existing short vowels in the normalized word
        if(Validator.getInstance().notCompatible(normalizedWord, vowledWord, segment, false)) {
            valid = false;
            return valid;
        }

        // misspelled hamza writings
        if ((vowledWord.contains("إَ")) || (vowledWord.contains("أِ")) || (vowledWord.contains("ِؤ")) || (vowledWord.contains("إُ")) || (vowledWord.contains("ؤِ")) || (vowledWord.contains("ئَ")) || (vowledWord.contains("ئُ"))) {
            valid = false;
            return valid;
        }
  
 
       
        
       
//        // Rules for writing Hamza
//        if(!normalizedWord.equals(normalizedWord.replaceAll("[ًٌٍَُِّْ]", ""))){
//            valid = isCorrectWritingHamza(vowledWords[1]);
//            if(!valid){
//                return false;
//            }
//
//        }
     
//
//         if ((vowledWord.indexOf("إَ") != -1) || (vowledWord.indexOf("أِ") != -1) || (vowledWord.indexOf("ِؤ") != -1) || (vowledWord.indexOf("إُ") != -1) || (vowledWord.indexOf("ؤِ") != -1) || (vowledWord.indexOf("ئَ") != -1)) {
//            valid = false;
//            return valid;
//        }
   
        //+-------------------------------------------+
        return valid;
        //+-------------------------------------------+
    }      

    //+---------------------------------+
    public java.util.Map<String, java.util.Set> getAllRoot_Formulas(java.util.Map<String, java.util.Map> solutions){
        //+---------------------------------+
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
        //+---------------------------------+
    }
    //+---------------------------------+
    public VEntity[] getPartOfSpeech( int idpartofspeech){
        VEntity result[] = new VEntity[9];
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechType();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechAugmented();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechEmphasized();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechMain();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechNbRoot();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechPerson();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechPerson2();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechTransitivity();
        ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).addDescPartOfSpeechVoice();
        PartOfSpeech pos = VerbalDerivedImpl.getInstance().getPartOfSpeech(idpartofspeech);
        result[0] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechMain(Integer.parseInt(pos.getMain()));
        result[1] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechType(Integer.parseInt(pos.getType()));        
        result[2] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechAugmented(Integer.parseInt(pos.getAugmented()));
        result[3] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechEmphasized(Integer.parseInt(pos.getEmphasized()));
        result[4] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechNbRoot(Integer.parseInt(pos.getNbroot()));
        result[5] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechPerson(Integer.parseInt(pos.getPerson()));
        result[6] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechPerson2(Integer.parseInt(pos.getPerson2()));
        result[7] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechTransitivity(Integer.parseInt(pos.getTransitivity()));
        result[8] = ((VerbalDerivedImpl) VerbalDerivedImpl.getInstance()).getDescPartOfSpeechVoice(Integer.parseInt(pos.getVoice()));
        return result;
    }
/*============================================================================*/
    /**
     * 
     * @param seg
     * @param root
     * @param type
     * @param person
     * @param person2
     * @param emphasized
     * @param caseormood
     * @return 
     */
    public String[] Interpret(
            Segment seg, 
            String root, 
            String type,
            String person,
            String person2, 
            String emphasized, 
            String caseormood
    ) {
        
        /*
            String type,
            String voice,
            String person2,
            String caseormood,
            String transitivity
*/
        String result[] = new String[7];

        String priority = "2";
        String prefix1;
        String suffix1;
        String prefix;
        String suffix;

        if (seg.getProclitic().getVoweledform().equals("")) {
            prefix1 = "#";
            result[5] = "#";
        } else {
            prefix1 = seg.getProclitic().getVoweledform() 
                    + "|" + seg.getProclitic().getDesc();
            result[5] = seg.getProclitic().getVoweledform();
        }

        if (seg.getEnclitic().getVoweledform().equals("")) {
            suffix1 = "#";
            result[6] = "#";
        } else {
            suffix1 = seg.getEnclitic().getVoweledform() 
                    + "|" + seg.getEnclitic().getDesc();
            result[6] = seg.getEnclitic().getVoweledform();
        }
        //+-------------------------------------------+
        prefix = getProcliticValue(type, person, person2);
        suffix = getEncliticValue(type, person, person2, emphasized, caseormood);
        //+-------------------------------------------+
        result[4] = priority;
        //+-------------------------------------------+
        if( !prefix.equals("") ){
            if( prefix1.equals("#") ){
                result[0] = prefix;
            }
            else{
                result[0] = prefix1 + "+" + prefix;
            }
        }
        else{
            result[0] = prefix1;
        }
        //+-------------------------------------------+
        if(!suffix.equals("")){
            
            if(suffix1.equals("#")){
                result[3] = suffix;
                
            }
            else{
                result[3] = suffix + "+" + suffix1;
            }
        }
        else{
            result[3] = suffix1;
        }        
        return result;
        //+---------------------------------+
    }
/*============================================================================*/
    public String getProcliticValue(String type, String person, String person2){
        
        if( type.equals("مضارع") ){
            if(person.equals("مخاطب")){
                return ""+ArabicCharacter.TEH + '|' + "حرف المضارعة";
            }
            else{
                if(person2.equals("أنا")){
                    return "أ" + '|' + "حرف المضارعة";
                }
                else{
                    if(person2.equals("نحن")){
                        return ""+ArabicCharacter.NOON + '|' + "حرف المضارعة";
                    }
                    else{
                        if(person2.equals("هي")){
                            return ""+ArabicCharacter.TEH + '|' + "تاء الغائبة";
                        }
                        else{
                            if(person2.equals("هما(ة)")){
                                return ""+ArabicCharacter.TEH + '|' + "حرف المضارعة";                            
                            }
                            else{
                                return ""+ArabicCharacter.YEH + '|' + "حرف المضارعة";                                
                            }
                        }
                    }
                }
            }
        }
        else{return "";}
    }
/*============================================================================*/
    public String getEncliticValue(String type, String person, String person2, String emphasized,String caseormood){
        
        if(emphasized.equals("مؤكد")){
            if(person.equals("مخاطب")){
                return ""+ArabicCharacter.NOON + "|" + "نون التوكيد";
            }
            else{
                return "";
            }
        }
        else{
            if ( type.equals("أمر") ) {
                if(person.equals("مخاطب") && !person2.equals("أنتَ")){
                    if(person2.equals("أنتِ")){
                        return "ي" + "|" + "ياء المخاطبة";
                    }
                    else{
                        if(person2.equals("أنتما")){
                            return "ا" + "|" + "ألف المثنى";
                        }
                        else{
                            if(person2.equals("أنتم")){
                                return "وا" + "|" + "واو الجماعة";
                            }
                            else{
                                if(person2.equals("أنتن")){
                                    return ""+ArabicCharacter.NOON + "|" + "نون النسوة";
                                }
                                else{
                                    return "";
                                }
                            }
                        }
                    }
                }
                else{
                    return "";
                }
            }
            if ( type.equals("ماض") ) {
                if(person2.equals("أنا")){
                    return "ت" + "|" + "تاء المتكلم";
                }
                if(person2.equals("نحن")){
                    return "نا" + "|" + "نون المتكلمين";
                }
                if(person2.equals("أنتَ")){
                    return "ت" + "|" + "تاء المخاطب";
                }
                if(person2.equals("أنتِ")){
                    return "ت" + "|" + "تاء المخاطبة";
                }
                if(person2.equals("أنتما")){
                    return "تما" + "|" + "تاء المخاطبين";
                }
                if(person2.equals("أنتم")){
                    return "تم" + "|" + "تاء المخاطبين";
                }
                if(person2.equals("أنتن")){
                    return "تن" + "|" + "تاء المخاطبات";
                }
                if(person2.equals("هي")){
                    return "ت" + "|" + "تاء التأنيث الساكنة";
                }
                if(person2.equals("هما")){
                    return "ا" + "|" + "ألف الاثنين";
                }
                if(person2.equals("هما(ة)")){
                    return "تا" + "|" + "تاء التأنيت وألف الاثنين";
                }
                if(person2.equals("هم")){
                    return "وا" + "|" + "واو الجماعة";
                }
                if(person2.equals("هن")){
                    return ""+ArabicCharacter.NOON + "|" + "نون النسوة";
                }
                return "";
            }
            if ( type.equals("مضارع") ) {
                if(person2.equals("هن") || person2.equals("أنتن")){
                    return ""+ArabicCharacter.NOON + "|" + "نون النسوة";
                }
                if(person2.equals("أنتِ")){
                    if(caseormood.equals("مرفوع")){
                        return "ين" + "|" + "ياء المخاطبة" 
                                + " " + "والنون علامة الرفع";
                    }
                    else{
                        return "ي" + "|" + "ياء المخاطبة";
                    }
                }
                if(person2.equals("أنتما") 
                        || person2.equals("هما(ة)") 
                        || person2.equals("هما")){
                    
                    if(caseormood.equals("مرفوع")){
                        return "ان" + "|" + "ألف المثنى" 
                                + " " + "والنون علامة الرفع";
                    }
                    else{
                        return "ا" + "|" + "ألف المثنى";
                    }
                }
                if(person2.equals("أنتم") || person2.equals("هم")){
                    if(caseormood.equals("مرفوع")){
                        return "ون" + "|" + "واو الجماعة" 
                                + " " + "والنون علامة الرفع";
                    }
                    else{
                        return "وا" + "|" + "واو الجماعة";
                    }
                }
                return "";
            }
            return "";
        }
    }
/*============================================================================*/
    @Override
    public void clear(){
        VerbalDerivedImpl.getInstance().clear();
        this.possiblePattern = new java.util.HashMap();//s .clear();
    }
/*============================================================================*/
}
