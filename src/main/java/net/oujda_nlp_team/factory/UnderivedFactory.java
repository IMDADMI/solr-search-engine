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
public abstract class UnderivedFactory {    
/*============================================================================*/
    protected java.util.List iCaseOrMood;
    protected java.util.List partOfspeech;
    protected java.util.Map unvoweledForm;
    protected java.util.List allVoweled;
/*============================================================================*/
    public net.oujda_nlp_team.entity.VEntity getCaseOrMood(int index){return (!isEmptyCaseOrMood())?(net.oujda_nlp_team.entity.VEntity) iCaseOrMood.get(index-1):null;}
    public Object getPartOfSpeech(int idType){return (Object) partOfspeech.get(idType-1);}
    public String getUnvoweledFormValue(String unvoweled){return (String) unvoweledForm.get(unvoweled);}
    public java.util.Set getUnvoweledForm(){return unvoweledForm.keySet();}  
/*============================================================================*/
    public boolean isEmptyCaseOrMood(){return iCaseOrMood.isEmpty();}
    public boolean isEmptyPartOfSpeech(){return partOfspeech.isEmpty();}
    public boolean containsVal(String unvoweled){return unvoweledForm.containsKey(unvoweled);}
    public boolean isEmptyUnvoweled(){return unvoweledForm.isEmpty();}
    public boolean isEmptyVoweled(){return allVoweled.isEmpty();}
    public int size(){return this.allVoweled.size();}
/*============================================================================*/    
    public void clear(){
        iCaseOrMood.clear();
        partOfspeech.clear();
        unvoweledForm.clear();
        allVoweled.clear();
    }
/*============================================================================*/
}
