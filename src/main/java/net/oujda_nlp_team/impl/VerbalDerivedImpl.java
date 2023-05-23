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
public class VerbalDerivedImpl extends DerivedFactory implements IDerived{
/*============================================================================*/
    private static final VerbalDerivedImpl instance = new VerbalDerivedImpl();
/*============================================================================*/
    public static VerbalDerivedImpl getInstance() {return instance;}
/*============================================================================*/
    private java.util.List descPartOfSpeechMain;
    private java.util.List descPartOfSpeechType;
    private java.util.List descPartOfSpeechPerson;
    private java.util.List descPartOfSpeechPerson2;
    private java.util.List descPartOfSpeechVoice;
    private java.util.List descPartOfSpeechEmphasized;
    private java.util.List descPartOfSpeechTransitivity;
    private java.util.List descPartOfSpeechAugmented;
    private java.util.List descPartOfSpeechNbRoot;
/*============================================================================*/
    private VerbalDerivedImpl() {
        super();
        iCaseOrMood = new java.util.ArrayList();
        vector = new java.util.HashMap();
        partOfSpeech = new java.util.ArrayList();
        descPartOfSpeechMain = new java.util.ArrayList();
        descPartOfSpeechType = new java.util.ArrayList();
        descPartOfSpeechPerson = new java.util.ArrayList();
        descPartOfSpeechPerson2 = new java.util.ArrayList();
        descPartOfSpeechVoice = new java.util.ArrayList();
        descPartOfSpeechEmphasized = new java.util.ArrayList();
        descPartOfSpeechTransitivity = new java.util.ArrayList();
        descPartOfSpeechAugmented = new java.util.ArrayList();
        descPartOfSpeechNbRoot = new java.util.ArrayList();
        quadriliteralRootMap = new java.util.HashMap();
        trilateralRootMap = new java.util.HashMap();
        quadriliteralRootList = new java.util.HashMap();
        trilateralRootList = new java.util.HashMap();
        unvoweledPatternStem = new java.util.HashMap();
        voweledDiacPatternLemmaMap = new java.util.HashMap();
        voweledCanonicPatternLemmaMap = new java.util.HashMap();
        voweledDiacPatternStemMap = new java.util.HashMap();
        voweledCanonicPatternStemMap = new java.util.HashMap();
        addVerbalDerivedImpl();
    }
/*============================================================================*/
    private void addVerbalDerivedImpl() {
        addCaseOrMood();
        addPartOfSpeech();
        addDescPartOfSpeechMain();
        addDescPartOfSpeechType();
        addDescPartOfSpeechPerson();
        addDescPartOfSpeechPerson2();
        addDescPartOfSpeechVoice();
        addDescPartOfSpeechEmphasized();
        addDescPartOfSpeechTransitivity();
        addDescPartOfSpeechNbRoot();
        addDescPartOfSpeechAugmented();
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
    @Override
    public void addCaseOrMood(){
        if(isEmptyCaseOrMood()){
            String data = Database.getInstance().getPath() 
                    + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.CaseOrMood");
            iCaseOrMood = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public void addAllVectorList(){
        if(isEmptyVector()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Formulas");
            vector = IOFile.getInstance().deserializeMap(data);
        }
    }
/*============================================================================*/
    @Override
    public void addPartOfSpeech() {
        if(isEmptyPartOfSpeech()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech");
            partOfSpeech = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public void addQuadriliteralRootList() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.id.Quadriliteral");
        quadriliteralRootList = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addTrilateralRootList() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.id.Trilateral");
        trilateralRootList = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllTrilateralRootMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.Trilateral");
        trilateralRootMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllQuadriliteralRootMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.Quadriliteral");
        quadriliteralRootMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllUnvoweledPatternMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Stems.Unvoweled");
        unvoweledPatternStem = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledDiacPatternLemmaMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Lemmas.Diac");
        voweledDiacPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledCanonicPatternLemmaMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Lemmas.Canonic");
        voweledCanonicPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledDiacPatternStemMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Stems.Diac");
        voweledDiacPatternStemMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    @Override
    public void addAllVoweledCanonicPatternStemMap() {
        String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Stems.Canonic");
        voweledCanonicPatternStemMap = IOFile.getInstance().deserializeMap(data);
    }
/*============================================================================*/
    public void addDescPartOfSpeechMain() {
        if(this.descPartOfSpeechMain.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Main");
            this.descPartOfSpeechMain = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechType() {
        if(this.descPartOfSpeechType.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Type");
            this.descPartOfSpeechType = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechPerson() {
        if(this.descPartOfSpeechPerson.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Person");
            this.descPartOfSpeechPerson = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechPerson2() {
        if(this.descPartOfSpeechPerson2.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Person2");
            this.descPartOfSpeechPerson2 = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechVoice() {
        if(this.descPartOfSpeechVoice.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Voice");
            this.descPartOfSpeechVoice = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechEmphasized() {
        if(this.descPartOfSpeechEmphasized.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Emphasized");
            this.descPartOfSpeechEmphasized = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechTransitivity() {
        if(this.descPartOfSpeechTransitivity.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Transitivity");
            this.descPartOfSpeechTransitivity = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechNbRoot() {
        if(this.descPartOfSpeechNbRoot.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.NbRoot");
            this.descPartOfSpeechNbRoot = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public void addDescPartOfSpeechAugmented() {
        if(this.descPartOfSpeechAugmented.isEmpty()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Augmented");
            this.descPartOfSpeechAugmented = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    public VEntity getDescPartOfSpeechMain (int index){return (VEntity) this.descPartOfSpeechMain.get(index-1);}
    public VEntity getDescPartOfSpeechType (int index){return (VEntity) this.descPartOfSpeechType.get(index-1);}
    public VEntity getDescPartOfSpeechPerson (int index){return (VEntity) this.descPartOfSpeechPerson.get(index-1);}
    public VEntity getDescPartOfSpeechPerson2 (int index){return (VEntity) this.descPartOfSpeechPerson2.get(index-1);}
    public VEntity getDescPartOfSpeechVoice (int index){return (VEntity) this.descPartOfSpeechVoice.get(index-1);}
    public VEntity getDescPartOfSpeechEmphasized (int index){return (VEntity) this.descPartOfSpeechEmphasized.get(index-1);}
    public VEntity getDescPartOfSpeechTransitivity (int index){return (VEntity) this.descPartOfSpeechTransitivity.get(index-1);}
    public VEntity getDescPartOfSpeechAugmented (int index){return (VEntity) this.descPartOfSpeechAugmented.get(index-1);}
    public VEntity getDescPartOfSpeechNbRoot (int index){return (VEntity) this.descPartOfSpeechNbRoot.get(index-1);}
/*============================================================================*/
}