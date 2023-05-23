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
import net.oujda_nlp_team.factory.UnderivedFactory;
import net.oujda_nlp_team.interfaces.IUnderived;
import net.oujda_nlp_team.entity.Voweled;
import net.oujda_nlp_team.util.IOFile;
/*============================================================================*/
public class PropernounUnderivedImpl extends UnderivedFactory implements IUnderived {
/*============================================================================*/
    private static final PropernounUnderivedImpl instance = new PropernounUnderivedImpl();
/*============================================================================*/
    public static PropernounUnderivedImpl getInstance() {return instance;}
/*============================================================================*/
    private PropernounUnderivedImpl() {
        super();
        iCaseOrMood = new java.util.ArrayList();
        partOfspeech = new java.util.ArrayList();
        unvoweledForm = new java.util.HashMap();
        allVoweled = new java.util.ArrayList();
        addCaseOrMood();
        addPartOfSpeech();
        addUnvoweledForm();
        addVoweledForm();
    }
/*============================================================================*/
    @Override
    public void addCaseOrMood() {
        if(isEmptyCaseOrMood()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.CaseOrMood");
            iCaseOrMood = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public void addPartOfSpeech(){
        if(isEmptyPartOfSpeech()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.PartOfSpeech");
            partOfspeech = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public void addUnvoweledForm() {
        if(isEmptyUnvoweled()){            
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.Unvoweled");
            unvoweledForm = IOFile.getInstance().deserializeMap(data);
        }
    }
/*============================================================================*/
    @Override
    public void addVoweledForm() {
        if(isEmptyVoweled()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Propernoun.Voweled");
            allVoweled = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public Voweled getVoweledFormValue(int idVoweled){return (Voweled) allVoweled.get(idVoweled-1);}
/*============================================================================*/
    @Override
    public int size(){return allVoweled.size();}
/*============================================================================*/
}
