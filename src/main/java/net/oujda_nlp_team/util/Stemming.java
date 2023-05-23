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
import net.oujda_nlp_team.entity.Clitic;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.impl.EncliticImpl;
import net.oujda_nlp_team.impl.ProcliticImpl;
/*============================================================================*/
public class Stemming {
/*============================================================================*/
    private static final Stemming instance = new Stemming();
/*============================================================================*/
    public static Stemming getInstance() {return instance;}
/*============================================================================*/
    //+-------------------------------------------+
    //private final IClitic enclitic;
//    private final IClitic proclitic;
    //+-------------------------------------------+
    private Stemming() {
//        this.enclitic = EncliticImpl.getInstance(); this.enclitic.addClitics();
//        this.proclitic = ProcliticImpl.getInstance(); this.proclitic.addClitics();
    }
    //+-------------------------------------------+
    public java.util.List getListsSegment(String unvoweledWord){
        java.util.List result = new java.util.ArrayList(); 
        java.util.Set setResult = new java.util.HashSet();
        java.util.List listsProclitic = ProcliticImpl.getInstance().getListsClitics(unvoweledWord);
        java.util.List listsEnclitic = EncliticImpl.getInstance().getListsClitics(unvoweledWord);
        boolean valid = true;
        java.util.Iterator<Clitic> it_p = listsProclitic.iterator();
        while(it_p.hasNext()){
            Clitic p = it_p.next();
            java.util.Iterator<Clitic> it_s = listsEnclitic.iterator();
            while(valid && it_s.hasNext()){
                net.oujda_nlp_team.entity.Clitic s = it_s.next();
                if( (unvoweledWord.length() - s.getUnvoweledform().length() - p.getUnvoweledform().length()) >=0 ){
                    java.util.Set Alternatives = new java.util.HashSet();
                    String stem = unvoweledWord.substring(p.getUnvoweledform().length(), unvoweledWord.length() - s.getUnvoweledform().length());
                    Alternatives.add(stem);
                    if( (p.getClasse().equals("N1") || p.getClasse().equals("N2") || p.getClasse().equals("N3") || p.getClasse().equals("N5")) && p.getUnvoweledform().endsWith(""+ArabicCharacter.LAM) && !stem.startsWith(""+ArabicCharacter.LAM)){
                        Alternatives.add(ArabicCharacter.LAM + stem);
                    }
                    if( (s.getUnvoweledform().equals(""+ArabicCharacter.YEH)) && !stem.startsWith(""+ArabicCharacter.YEH)){
                        Alternatives.add(stem + ArabicCharacter.YEH);
                    }
                    if( (p.getClasse().equals("N4") || p.getClasse().equals("C3"))&& p.getUnvoweledform().endsWith(""+ArabicCharacter.LAM) && stem.startsWith(""+ArabicCharacter.LAM)){
                        Alternatives.add(getAlternatives(stem, stem.charAt(stem.length()-1), (s.getUnvoweledform().length()!=0), (s.getUnvoweledform().length()>0)));
                        stem = ""+ArabicCharacter.ALEF + stem;
                    }
                    Alternatives.add(stem);
                    if(p.getUnvoweledform().equals("أ") || p.getUnvoweledform().equals("ب")){
                        Alternatives.add(ArabicCharacter.ALEF + stem);
                    }
                    String stem1 ="";
                    if(stem.length()>1){
                        stem1 = getAlternatives(stem, stem.charAt(stem.length()-1), (s.getUnvoweledform().length()!=0), (s.getUnvoweledform().length()>0)); 
                    }
                    if(!"".equals(stem1)){Alternatives.add(stem1);}
                    String stem3 = stem1.replaceAll("آ", "ءا");
                    if(!stem3.equals(stem1)){Alternatives.add(stem3);}
                    String stem2 = stem.replaceAll("آ", "ءا");
                    if(!stem2.equals(stem)){Alternatives.add(stem2);}
                    java.util.Iterator<String> it = Alternatives.iterator();
                    while( it.hasNext() ) {
                        String sch = it.next();                                               
                        Segment segment = new Segment(p, sch, s);
                        // Segment validation: compatibility between the elements, stem length etc..
                        if (isValidSegment(segment)) {setResult.add(segment);}
                    }
                }
                else{valid=false;}
            }
        }
        result.addAll(setResult);
        return result;
    }
    //+-------------------------------------------+
    private boolean isValidSegment(Segment segment){
        String proClass = segment.getProclitic().getClasse();
        String encClass = segment.getEnclitic().getClasse();
        return !(   ( encClass.startsWith("V") && proClass.startsWith("N") ) 
                ||  ( proClass.startsWith("V") && encClass.startsWith("N")) 
                ||  ( Validator.getInstance().isDefinit(proClass) && !segment.getEnclitic().getUnvoweledform().equals("") )
                );
    }
    //+-------------------------------------------+
    public String getAlternatives(String stem, char st, boolean valid, boolean validSuf){
    
        switch(st){
            case ArabicCharacter.TEH : return (valid) ? stem.substring(0,stem.length()-1) + ArabicCharacter.TEH_MARBUTA : ""; 
            case ArabicCharacter.ALEF_MADDA : return (valid) ? stem.substring(0,stem.length()-1) + "أى" : "";    
            case ArabicCharacter.ALEF : return (valid) ? stem.substring(0,stem.length()-1) + ArabicCharacter.ALEF_MAKSURA : ""; 
            case ArabicCharacter.WAW : return (valid) ? stem + ArabicCharacter.ALEF : ""; 
            case ArabicCharacter.YEH_HAMZA : return (validSuf) ? stem.substring(0,stem.length()-1) + ArabicCharacter.HAMZA : ""; 
            case ArabicCharacter.WAW_HAMZA : return (validSuf) ? stem.substring(0,stem.length()-1) + ArabicCharacter.HAMZA : ""; 
            
            /*    
            case 'أ' : 
                if(validSuf){
                    return stem.substring(0,stem.length()-1) + 'ء';
                }
                else{ 
                    return ""; 
                }
            case 'إ' : 
                if(validSuf){
                    return stem.substring(0,stem.length()-1) + 'ء';
                }
                else{ 
                    return ""; 
                }
             */
            
            default : return "";    
        }
    }    
    //+-------------------------------------------+
    public static void print(Segment segment){System.out.println(segment);}
    //+-------------------------------------------+
    public void clear(){
        EncliticImpl.getInstance().clear();
        ProcliticImpl.getInstance().clear();
    }
    //+-------------------------------------------+
}
