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
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Root;
import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.entity.Unvoweled;
import net.oujda_nlp_team.entity.Voweled;
/*============================================================================*/
public abstract class DerivedFactory {    
    //+----------------------------------------------------+
    protected java.util.List iCaseOrMood;
    public boolean isEmptyCaseOrMood(){return iCaseOrMood.isEmpty();}
    public VEntity getCaseOrMood(int index){return (!iCaseOrMood.isEmpty())?(VEntity) iCaseOrMood.get(index-1):null;}
    //+----------------------------------------------------+
    protected java.util.List partOfSpeech;
    public boolean isEmptyPartOfSpeech(){return partOfSpeech.isEmpty();}
    public PartOfSpeech getPartOfSpeech (int index){return (PartOfSpeech) this.partOfSpeech.get(index-1);}
    //+----------------------------------------------------+
    protected java.util.Map<Integer, java.util.List<Formulas>> vector;
    public boolean isEmptyVector(){return vector.isEmpty();}
    public boolean containsValVector(int len) {return (vector != null)?vector.containsKey(len):false;}    
    public java.util.List<Formulas> getListVectors(int len) {return this.vector.get(len);}  
    //+----------------------------------------------------+
    protected java.util.Map<Character, java.util.List<Root>> trilateralRootMap;
    public boolean isEmptyTrilateralRootMap(){return trilateralRootMap.isEmpty();}
    public boolean containsTrilateralCharRoot(char rootC1) {return (trilateralRootMap!=null)?trilateralRootMap.containsKey(rootC1):false;}
    public java.util.List<Root> getTrilateralRootMap(char rootC1) {return this.trilateralRootMap.get(rootC1);}
    public Root getTrilateralRoot(String root) {return getTrilateralRootMap(root.charAt(0)).get(getTrilateralRootList(root));}
    //+----------------------------------------------------+
    protected java.util.Map<Character, java.util.List<Root>> quadriliteralRootMap;
    public boolean isEmptyQuadriliteralRootMap(){return quadriliteralRootMap.isEmpty();}
    public boolean containsQuadriliteralCharRoot(char rootC1) {return (quadriliteralRootMap!=null)?quadriliteralRootMap.containsKey(rootC1):false;}
    public java.util.List<Root> getQuadriliteralRootMap(char rootC1) {return this.quadriliteralRootMap.get(rootC1);}
    public Root getQuadriliteralRoot(String root) {return getQuadriliteralRootMap(root.charAt(0)).get(getQuadriliteralRootList(root));}
    //+----------------------------------------------------+
    protected java.util.Map<String, Integer> trilateralRootList;   
    public boolean isEmptyTrilateralRootList(){return trilateralRootList.isEmpty();}
    public boolean containsTrilateralRoot(String root) {return (trilateralRootList!=null)?trilateralRootList.containsKey(root):false;}
    public int getTrilateralRootList(String root) {return this.trilateralRootList.get(root);}
    //+----------------------------------------------------+
    protected java.util.Map<String, Integer> quadriliteralRootList;
    public boolean isEmptyQuadriliteralRootList(){return quadriliteralRootList.isEmpty();}
    public boolean containsQuadriliteralRoot(String root) {return (quadriliteralRootList!=null)?quadriliteralRootList.containsKey(root):false;}
    public int getQuadriliteralRootList(String root) {return this.quadriliteralRootList.get(root);}
    //+----------------------------------------------------+
    protected java.util.Map unvoweledPatternStem;    
    public boolean containsIdUnvoweled(int len) {return (unvoweledPatternStem!=null)?unvoweledPatternStem.containsKey(len):false;}
    public java.util.List getUnvoweledList(int len) {return (java.util.List) this.unvoweledPatternStem.get(len);}
    public Unvoweled getUnvoweledForm(int len, int index) {return (Unvoweled) getUnvoweledList(len).get(index-1); }
    //+----------------------------------------------------+
    protected java.util.Map voweledDiacPatternLemmaMap;
    public boolean containsIdVoweledDiacPatternLemma(int len) {return (voweledDiacPatternLemmaMap!=null)?voweledDiacPatternLemmaMap.containsKey(len):false;}
    public java.util.List getVoweledDiacPatternLemmaList(int len) {return (java.util.List) this.voweledDiacPatternLemmaMap.get(len);}
    public Voweled getVoweledDiacPatternLemma(int len, int index) {return (Voweled) getVoweledDiacPatternLemmaList(len).get(index-1);}
    //+----------------------------------------------------+
    protected java.util.Map voweledCanonicPatternLemmaMap;    
    public boolean containsIdVoweledCanonicPatternLemma(int len) {return (voweledCanonicPatternLemmaMap!=null)?voweledCanonicPatternLemmaMap.containsKey(len):false;}
    public java.util.List getVoweledCanonicPatternLemmaList(int len) {return (java.util.List) this.voweledCanonicPatternLemmaMap.get(len);}
    public Voweled getVoweledCanonicPatternLemma(int len, int index) {return (Voweled) getVoweledCanonicPatternLemmaList(len).get(index-1);}
    //+----------------------------------------------------+
    protected java.util.Map voweledDiacPatternStemMap;
    public boolean containsIdVoweledDiacPatternStem(int len) {return (voweledDiacPatternStemMap!=null)?voweledDiacPatternStemMap.containsKey(len):false;}
    public java.util.List getVoweledDiacPatternStemList(int len) {return (java.util.List) this.voweledDiacPatternStemMap.get(len);}
    public Voweled getVoweledDiacPatternStem(int len, int index) {return (Voweled) getVoweledDiacPatternStemList(len).get(index-1);}
    //+----------------------------------------------------+
    protected java.util.Map voweledCanonicPatternStemMap;
    public boolean containsIdVoweledCanonicPatternStem(int len) {return (voweledCanonicPatternStemMap!=null)?voweledCanonicPatternStemMap.containsKey(len):false;}
    public java.util.List getVoweledCanonicPatternStemList(int len) {return (java.util.List) this.voweledCanonicPatternStemMap.get(len);}
    public Voweled getVoweledCanonicPatternStem(int len, int index) {return (Voweled) getVoweledCanonicPatternStemList(len).get(index-1);}
    //+----------------------------------------------------+
    public java.util.List getInfoResult( String idRoot, String idPatternStem, int len) {
        java.util.List result = new java.util.ArrayList();
        java.util.Set<String> listPatternStem = new java.util.HashSet();
        listPatternStem.addAll(java.util.Arrays.asList(idPatternStem.split(" ")));
        for(String idR : idRoot.split(" ")){            
            int idForm = Integer.parseInt( idR );    
            Formulas ids = getListVectors(len).get(idForm-1);
            if(listPatternStem.contains(ids.getIdDiacPatternStem())){result.add(ids);}
        }
        return result;
    }
    //+----------------------------------------------------+
    public String getLenTrilateralRoot(String root, int len) {        
        switch(len){
            case 1 : return getTrilateralRoot(root).getLen1(); 
            case 2 : return getTrilateralRoot(root).getLen2(); 
            case 3 : return getTrilateralRoot(root).getLen3(); 
            case 4 : return getTrilateralRoot(root).getLen4(); 
            case 5 : return getTrilateralRoot(root).getLen5(); 
            case 6 : return getTrilateralRoot(root).getLen6(); 
            case 7 : return getTrilateralRoot(root).getLen7(); 
            case 8 : return getTrilateralRoot(root).getLen8(); 
            case 9 : return getTrilateralRoot(root).getLen9(); 
            case 10 : return getTrilateralRoot(root).getLen10(); 
            case 11 : return getTrilateralRoot(root).getLen11(); 
            case 12 : return getTrilateralRoot(root).getLen12(); 
            default : return "";
        } 
    }
    //+----------------------------------------------------+
    public String getLenQuadriliteralRoot(String root, int len) {        
        switch(len){
            case 1 : return getQuadriliteralRoot(root).getLen1(); 
            case 2 : return getQuadriliteralRoot(root).getLen2(); 
            case 3 : return getQuadriliteralRoot(root).getLen3(); 
            case 4 : return getQuadriliteralRoot(root).getLen4(); 
            case 5 : return getQuadriliteralRoot(root).getLen5(); 
            case 6 : return getQuadriliteralRoot(root).getLen6(); 
            case 7 : return getQuadriliteralRoot(root).getLen7(); 
            case 8 : return getQuadriliteralRoot(root).getLen8(); 
            case 9 : return getQuadriliteralRoot(root).getLen9(); 
            case 10 : return getQuadriliteralRoot(root).getLen10(); 
            case 11 : return getQuadriliteralRoot(root).getLen11(); 
            case 12 : return getQuadriliteralRoot(root).getLen12(); 
            default : return "";
        } 
    }
    //+----------------------------------------------------+
    public void clear(){
        iCaseOrMood.clear();
        vector.clear();
        partOfSpeech.clear();
        quadriliteralRootMap.clear();
        trilateralRootMap.clear();
        quadriliteralRootList.clear();
        trilateralRootList.clear();
        unvoweledPatternStem.clear();        
        voweledDiacPatternLemmaMap.clear();
        voweledCanonicPatternLemmaMap.clear();
        voweledDiacPatternStemMap.clear();
        voweledCanonicPatternStemMap.clear(); 
    }
    //+----------------------------------------------------+
}
