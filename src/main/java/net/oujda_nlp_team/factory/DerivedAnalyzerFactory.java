package net.oujda_nlp_team.factory;
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
import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.entity.Unvoweled;
import net.oujda_nlp_team.entity.Voweled;
import net.oujda_nlp_team.interfaces.IDerived;
import net.oujda_nlp_team.util.ArabicCharacter;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Settings;
import net.oujda_nlp_team.util.Validator;
/*============================================================================*/
public abstract class DerivedAnalyzerFactory {    
    //+---------------------------------+
    //protected IDerived derived;
    protected java.util.Map<String, java.util.Map> possiblePattern;    
    //+---------------------------------+
    public synchronized java.util.Set getPossibleRoots(String st, String rules, IDerived derived){                    
        java.util.List<String> listIterator = new java.util.ArrayList();
        listIterator.addAll(java.util.Arrays.asList(rules.split(" ")));
        java.util.Set Root = new java.util.HashSet();
        java.util.Iterator<String> it_rules = listIterator.iterator();
        while(it_rules.hasNext()){
            String rule = it_rules.next();
            String root = "";
            char [] Rul = rule.toCharArray();            
            for(char r : Rul){
                if (Validator.getInstance().isNumeric(r) && r != '0') {
                    char c = st.charAt(Integer.parseInt(r + "") - 1);
                    root += (Validator.getInstance().isHamza(c)) ? ArabicCharacter.HAMZA : c;
                } 
                else {
                    root += (Validator.getInstance().isHamza(r)) ? ArabicCharacter.HAMZA : r;
                }
            }
            if(isContains(root, derived)){Root.add(root);}
        }
        return Root;
    }
    //+---------------------------------+
    public boolean isContains(String root, IDerived derived){
        return (root.length()==3) ? derived.containsTrilateralRoot(root) : derived.containsQuadriliteralRoot(root);
    }    
    //+---------------------------------+
    public String idRoots(String root, int lenRoot, int lenStem, IDerived derived){
        return (lenRoot==3) ? derived.getLenTrilateralRoot(root, lenStem) : derived.getLenQuadriliteralRoot(root, lenStem);
    }
    //+---------------------------------+
    public String [] getInfoResults( Formulas formulas, int lenStem, IDerived derived){
        String [] result = new String[10];
        int id = Integer.parseInt(formulas.getIdDiacPatternStem());
        Voweled vd = derived.getVoweledDiacPatternStem(lenStem, id);
        result[0] = vd.getVal();
        result[1] = vd.getFreq();
        if(Settings.choicePatternStem){
            String strCanonic[] = formulas.getIdCanonicPatternStem().split("\\.");
            id = Integer.parseInt(strCanonic[1]);
            int len = Integer.parseInt(strCanonic[0]);
            vd = derived.getVoweledCanonicPatternStem(len, id);
            result[2] = vd.getVal();
            result[3] = vd.getFreq();
        }
        if(Settings.choiceLemma){
            String strCanonic[] = formulas.getIdDiacPatternLemma().split("\\.");
            id = Integer.parseInt(strCanonic[1]);
            int len = Integer.parseInt(strCanonic[0]);
            vd = derived.getVoweledDiacPatternLemma(len, id);
            result[4] = vd.getVal();
            result[5] = vd.getFreq();
        }
        if(Settings.choicePatternLemma){
            String strCanonic[] = formulas.getIdCanonicPatternLemma().split("\\.");
            id = Integer.parseInt(strCanonic[1]);
            int len = Integer.parseInt(strCanonic[0]);
            vd = derived.getVoweledCanonicPatternLemma(len, id);
            result[6] = vd.getVal();
            result[7] = vd.getFreq();
        }
        result[8] = formulas.getIdPartOfSpeech();
        result[9] = formulas.getIdCaseOrMood();
        return result;
    }
    //+---------------------------------+
    public synchronized java.util.Map possibleSolutions( String normalizedWord, Segment segment, IDerived derived){
        java.util.Map result = new java.util.HashMap();
        boolean addResult;
        String stem1 = segment.getStem();
        String stem = stem1.charAt(0) + ArabicStringUtil.getInstance().getNormalizeHamza(stem1.substring(1));
        int lenDiac = stem.length();
        java.util.Iterator<Unvoweled> it_unvoweled = derived.getUnvoweledList(lenDiac).iterator();
        while(it_unvoweled.hasNext()){
            Unvoweled unvoweledForm = it_unvoweled.next();
            String s_unvoweled = unvoweledForm.getVal();
            s_unvoweled = s_unvoweled.charAt(0) + ArabicStringUtil.getInstance().getNormalizeHamza(s_unvoweled.substring(1));
            if(Validator.getInstance().isDiacPattern(stem, s_unvoweled)){
                java.util.Set iRoots = getPossibleRoots(stem, unvoweledForm.getRules(), derived);
                if(!iRoots.isEmpty()) {
                    java.util.Map ROOT = new java.util.HashMap();
                    addResult = false;
                    java.util.Iterator<String> it_roots = iRoots.iterator();
                    while(it_roots.hasNext()){
                        String root = it_roots.next();
                        String val = ArabicStringUtil.getInstance().getWordFromRootAndPattern(root, s_unvoweled);
                        int lenRoot = root.length();
                        int lenStem = segment.getStem().length();
                        String idRoot = idRoots(root, lenRoot, lenStem, derived);                         
                        if(idRoot!=null && !"".equals(idRoot) && ArabicStringUtil.getInstance().getNormalizeHamza(val).equals(ArabicStringUtil.getInstance().getNormalizeHamza(stem))){
                            java.util.List res = derived.getInfoResult( idRoot, unvoweledForm.getIds(), lenStem);
                            if(!res.isEmpty()){
                                ROOT.put(root, res);
                                addResult = true;
                            }
                        }
                    }
                    if(addResult){result.put(unvoweledForm.getVal(), ROOT);}
                }
            }
        }
        
        possiblePattern.put(stem1, result);
        
        return result;
    }
    
}
