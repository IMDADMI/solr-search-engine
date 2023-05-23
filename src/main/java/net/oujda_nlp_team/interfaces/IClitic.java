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
public interface IClitic{
    public void addClitics();
    public java.util.List getListsClitics(String unvoweledWord);
    public void clear();
}
