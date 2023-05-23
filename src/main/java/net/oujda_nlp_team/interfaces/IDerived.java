package net.oujda_nlp_team.interfaces;
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
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Root;
import net.oujda_nlp_team.entity.Unvoweled;
import net.oujda_nlp_team.entity.Voweled;
/*============================================================================*/
public interface IDerived {
    //+---------------------------------+
    public void addCaseOrMood();    
    public void addPartOfSpeech();
    public void addAllVectorList();    
    public boolean containsValVector(int len);    
    public java.util.List getListVectors(int len);
    public java.util.List getInfoResult(String idRoot, String idPatternStem, int lenStem);
    public PartOfSpeech getPartOfSpeech(int index);   
    public boolean isEmptyQuadriliteralRootMap();    
    public boolean isEmptyQuadriliteralRootList();    
    public boolean isEmptyTrilateralRootList();    
    public boolean isEmptyTrilateralRootMap();    
    public void addAllQuadriliteralRootMap();    
    public void addAllTrilateralRootMap();    
    public void addQuadriliteralRootList();    
    public void addTrilateralRootList(); 
    public boolean containsQuadriliteralRoot(String root);       
    public boolean containsTrilateralRoot(String root);     
    public boolean containsQuadriliteralCharRoot(char rootC1);      
    public boolean containsTrilateralCharRoot(char rootC1);      
    public int getQuadriliteralRootList(String root);
    public int getTrilateralRootList(String root);    
    public java.util.List<Root> getQuadriliteralRootMap(char rootC1);    
    public java.util.List<Root> getTrilateralRootMap(char rootC1);    
    public Root getQuadriliteralRoot(String root);    
    public Root getTrilateralRoot(String root);    
    public String getLenTrilateralRoot(String root, int len);    
    public String getLenQuadriliteralRoot(String root, int len);    
    public void addAllUnvoweledPatternMap();    
    public boolean containsIdUnvoweled(int len);    
    public java.util.List getUnvoweledList(int len);    
    public Unvoweled getUnvoweledForm(int len, int index);
    public void addAllVoweledDiacPatternLemmaMap();
    public void addAllVoweledCanonicPatternLemmaMap();
    public void addAllVoweledDiacPatternStemMap();
    public void addAllVoweledCanonicPatternStemMap();
    public boolean containsIdVoweledDiacPatternLemma(int len);
    public boolean containsIdVoweledCanonicPatternLemma(int len);
    public boolean containsIdVoweledDiacPatternStem(int len);
    public boolean containsIdVoweledCanonicPatternStem(int len);
    public java.util.List getVoweledDiacPatternLemmaList(int len);
    public java.util.List getVoweledCanonicPatternLemmaList(int len);
    public java.util.List getVoweledDiacPatternStemList(int len);
    public java.util.List getVoweledCanonicPatternStemList(int len);
    public Voweled getVoweledDiacPatternLemma(int len, int index);
    public Voweled getVoweledCanonicPatternLemma(int len, int index);
    public Voweled getVoweledDiacPatternStem(int len, int index);    
    public Voweled getVoweledCanonicPatternStem(int len, int index);
    //+---------------------------------+
    
    public void clear();
    //+---------------------------------+
}
