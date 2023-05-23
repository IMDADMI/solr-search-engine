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
import net.oujda_nlp_team.entity.Toolwords;
import net.oujda_nlp_team.util.IOFile;
/*============================================================================*/
public class ToolwordsUnderivedImpl extends UnderivedFactory implements IUnderived {
/*============================================================================*/
    private static final ToolwordsUnderivedImpl instance = new ToolwordsUnderivedImpl();
/*============================================================================*/
    public static ToolwordsUnderivedImpl getInstance() {return instance;}
/*============================================================================*/
    private ToolwordsUnderivedImpl() {
        super();
        partOfspeech = new java.util.ArrayList();
        unvoweledForm = new java.util.HashMap();
        allVoweled = new java.util.ArrayList();
        addToolwordsUnderivedImpl();
    }
/*============================================================================*/
    private void addToolwordsUnderivedImpl() {
        addPartOfSpeech();
        addUnvoweledForm();
        addVoweledForm();
    }
/*============================================================================*/
    @Override
    public void addPartOfSpeech(){
        if(isEmptyPartOfSpeech()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Toolwords.PartOfSpeech");
            partOfspeech = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public void addUnvoweledForm(){
        if(isEmptyUnvoweled()){            
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Toolwords.Unvoweled");
            unvoweledForm = IOFile.getInstance().deserializeMap(data);
        }
    }
/*============================================================================*/
    @Override
    public void addVoweledForm(){
        if(isEmptyVoweled()){
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Toolwords.Voweled");
            allVoweled = IOFile.getInstance().deserializeList(data);
        }
    }
/*============================================================================*/
    @Override
    public Toolwords getVoweledFormValue(int idVoweled ){return (Toolwords) allVoweled.get(idVoweled-1);}
/*============================================================================*/
    @Override
    public int size(){return allVoweled.size();}
/*============================================================================*/
    @Override
    public void addCaseOrMood(){}
/*============================================================================*/
}
