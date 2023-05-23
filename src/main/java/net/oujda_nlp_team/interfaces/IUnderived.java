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
public interface IUnderived {
/*============================================================================*/
    public Object getPartOfSpeech(int idType);
    public String getUnvoweledFormValue(String unvoweled);
    public Object getVoweledFormValue(int idVoweled);
    public java.util.Set getUnvoweledForm();
/*============================================================================*/
    public void addCaseOrMood();
    public void addPartOfSpeech();
    public void addUnvoweledForm();
    public void addVoweledForm();    
/*============================================================================*/
    public boolean containsVal(String unvoweled);
    public int size();
    public void clear();
/*============================================================================*/
}
