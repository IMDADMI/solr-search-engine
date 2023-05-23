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
import net.oujda_nlp_team.factory.DerivedFactory;
import net.oujda_nlp_team.interfaces.IDerived;
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.util.IOFile;
/*============================================================================*/
public class NominalDerivedImpl extends DerivedFactory implements IDerived{
/*============================================================================*/
    private java.util.List descPartOfSpeechType;    
    private java.util.List descPartOfSpeechDefinit;    
    private java.util.List descPartOfSpeechGender;    
    private java.util.List descPartOfSpeechMain;    
    private java.util.List descPartOfSpeechNbRoot;    
    private java.util.List descPartOfSpeechNumber;
/*============================================================================*/
    private static final IDerived instance = new NominalDerivedImpl();
/*============================================================================*/
    public static IDerived getInstance() {return instance;}
/*============================================================================*/
    private NominalDerivedImpl() {
        super();
        iCaseOrMood = new java.util.ArrayList();
        vector = new java.util.HashMap();
        partOfSpeech = new java.util.ArrayList();
        descPartOfSpeechType = new java.util.ArrayList();
        descPartOfSpeechDefinit = new java.util.ArrayList();
        descPartOfSpeechGender = new java.util.ArrayList();
        descPartOfSpeechMain = new java.util.ArrayList();
        descPartOfSpeechNbRoot = new java.util.ArrayList();
        descPartOfSpeechNumber = new java.util.ArrayList();
        unvoweledPatternStem = new java.util.HashMap();
        voweledDiacPatternLemmaMap = new java.util.HashMap();
        voweledCanonicPatternLemmaMap = new java.util.HashMap();
        voweledDiacPatternStemMap = new java.util.HashMap();
        voweledCanonicPatternStemMap = new java.util.HashMap();
        addNominalDerivedImpl();
    }
/*============================================================================*/
    private void addNominalDerivedImpl() {
        addCaseOrMood();
        addPartOfSpeech();
        addDescPartOfSpeechType();
        addDescPartOfSpeechDefinit();
        addDescPartOfSpeechGender();
        addDescPartOfSpeechMain();
        addDescPartOfSpeechNbRoot();
        addDescPartOfSpeechNumber(); 
        addAllVectorList();
        addQuadriliteralRootList();
        addTrilateralRootList();
        addAllTrilateralRootMap();
        addAllQuadriliteralRootMap();    
        addAllUnvoweledPatternMap();
        addAllVoweledDiacPatternLemmaMap();
        addAllVoweledCanonicPatternLemmaMap();
        addAllVoweledDiacPatternStemMap();
        addAllVoweledCanonicPatternStemMap();
    }
/*============================================================================*/
    public void addDescPartOfSpeechType() {
        if(this.descPartOfSpeechType.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Type");
            descPartOfSpeechType = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    public void addDescPartOfSpeechDefinit() {
        if(this.descPartOfSpeechDefinit.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Definit");
            descPartOfSpeechDefinit = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    public void addDescPartOfSpeechGender() {
        if(this.descPartOfSpeechGender.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Gender");
            descPartOfSpeechGender = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    public void addDescPartOfSpeechMain() {
        if(this.descPartOfSpeechMain.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Main");
            descPartOfSpeechMain = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    public void addDescPartOfSpeechNbRoot() {
        if(this.descPartOfSpeechNbRoot.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.NbRoot");
            descPartOfSpeechNbRoot = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    public void addDescPartOfSpeechNumber() {
        if(this.descPartOfSpeechNumber.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Number");
            descPartOfSpeechNumber = IOFile.getInstance().deserializeList(data);
        }
    }
    @Override
    public void addCaseOrMood(){
        if(isEmptyCaseOrMood()){
            String data = Database.getInstance().getPath() 
                    + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.CaseOrMood");
            iCaseOrMood = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    @Override
    public void addAllVectorList(){
        if(isEmptyVector()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Formulas");
            vector = IOFile.getInstance().deserializeMap(data);
        }
    }
    //+---------------------------------+
    @Override
    public void addPartOfSpeech() {
        if(isEmptyPartOfSpeech()){           
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech");
            partOfSpeech = IOFile.getInstance().deserializeList(data);
        }
    }
    //+---------------------------------+
    @Override
    public void addAllTrilateralRootMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.Trilateral");
        trilateralRootMap = IOFile.getInstance().deserializeMap(data);
    }
    //+---------------------------------+
    @Override
    public void addAllQuadriliteralRootMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.Quadriliteral");
        quadriliteralRootMap = IOFile.getInstance().deserializeMap(data);
    }
    //+---------------------------------+
    @Override
    public void addQuadriliteralRootList() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.id.Quadriliteral");
        quadriliteralRootList = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addTrilateralRootList() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.id.Trilateral");
        trilateralRootList = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllUnvoweledPatternMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Stems.Unvoweled");
        unvoweledPatternStem = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledDiacPatternLemmaMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Lemmas.Diac");
        voweledDiacPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledCanonicPatternLemmaMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Lemmas.Canonic");
        voweledCanonicPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledDiacPatternStemMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Stems.Diac");
        voweledDiacPatternStemMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledCanonicPatternStemMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Stems.Canonic");
        voweledCanonicPatternStemMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    public VEntity getDescPartOfSpeechType (int index){return (VEntity) this.descPartOfSpeechType.get(index-1);}
    public VEntity getDescPartOfSpeechDefinit (int index){return (VEntity) this.descPartOfSpeechDefinit.get(index-1);}
    public VEntity getDescPartOfSpeechGender (int index){return (VEntity) this.descPartOfSpeechGender.get(index-1);}
    public VEntity getDescPartOfSpeechMain (int index){return (VEntity) this.descPartOfSpeechMain.get(index-1);}
    public VEntity getDescPartOfSpeechNbRoot (int index){return (VEntity) this.descPartOfSpeechNbRoot.get(index-1);}
    public VEntity getDescPartOfSpeechNumber (int index){return (VEntity) this.descPartOfSpeechNumber.get(index-1);}
/*============================================================================*/
}
