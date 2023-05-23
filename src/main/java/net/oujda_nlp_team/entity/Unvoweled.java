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
public class Unvoweled implements java.io.Serializable{
/*============================================================================*/
    private static final long serialVersionUID = 1236L;
/*============================================================================*/
    private String val;
    private String rules;
    private String ids;
    private String id;
    private String vowform;
    private String pos;
    private String cas;    
/*============================================================================*/
    public void setVal(String _val) {this.val = _val;}
    public void setRules(String _rules) {this.rules = _rules;}
    public void setIds(String _ids) {this.ids = _ids;}
    public void setId(String _id) {this.id = _id;}
    public void setVoweledform(String _vowform) {this.vowform = _vowform;}
    public void setPartOfSpeech(String _pos) {this.pos = _pos;}
    public void setCase(String _cas) {this.cas = _cas;}
/*============================================================================*/
    public String getVal() {return val;}
    public String getRules() {return rules;}
    public String getIds() {return ids;}
    public String getId() {return id;}
    public String getVoweledform() {return vowform;}
    public String getPartOfSpeech() {return pos;}
    public String getCase() {return cas;}
/*============================================================================*/
    @Override
    public String toString() {return (val + ";" + rules + ";" + ids);}
/*============================================================================*/
}
