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
public abstract class CliticFactory {
    //+-------------------------------------------+
    protected java.util.Map<String, java.util.List> clitic;
    //+-------------------------------------------+
    public java.util.List possibleClitics(String _affix) { 
        return this.clitic.containsKey(_affix)?this.clitic.get(_affix):new java.util.ArrayList(); 
    }         
    public void clear(){ this.clitic.clear();}
    //+-------------------------------------------+
}
