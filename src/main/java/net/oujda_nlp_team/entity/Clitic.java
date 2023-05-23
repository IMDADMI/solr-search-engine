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
public class Clitic implements java.io.Serializable {
/*============================================================================*/  
    private static final long serialVersionUID = 12421L;
/*============================================================================*/
    private String unvoweledform;
    private String voweledform;
    private String desc;
    private String classe;
/*============================================================================*/
    public String getUnvoweledform() {return unvoweledform;}
    public String getVoweledform() {return voweledform;}
    public String getDesc() {return desc;}
    public String getClasse() {return classe;}
/*============================================================================*/
    public void setUnvoweledform(String _unvoweledform) {this.unvoweledform = _unvoweledform;}
    public void setVoweledform(String _voweledform) {this.voweledform = _voweledform;}
    public void setDesc(String _desc) {this.desc = _desc;}
    public void setClasse(String _classe) {this.classe = _classe;}
/*============================================================================*/
}
