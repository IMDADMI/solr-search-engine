package net.oujda_nlp_team.entity;
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
public class VEntity implements java.io.Serializable {
/*============================================================================*/   
    private static final long serialVersionUID = 1239L;
/*============================================================================*/ 
    private String id;
    private String valAR; 
    private String valEN;
/*============================================================================*/ 
    public String getId(){return id;}
    public String getValAR() { return valAR;}
    public String getValEN(){return valEN;}    
/*============================================================================*/ 
    public void setId(String _id){this.id = _id;}
    public void setValAR(String _valAR) {this.valAR = _valAR;}
    public void setValEN(String _valEN) { this.valEN = _valEN;}
/*============================================================================*/ 
}
