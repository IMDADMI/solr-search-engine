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
public class Voweled implements java.io.Serializable {
/*============================================================================*/ 
    private static final long serialVersionUID = 12444L;
/*============================================================================*/  
    private String id;
    private String val;
    private String freq;
    private String root;
    private String lemma;
    private String vowform;
    private String pos;
    private String cas;
/*============================================================================*/    
    public void setId(String _id) {this.id = _id;}
    public void setVal(String _val) {this.val = _val;}    
    public void setFreq(String _freq) {this.freq = _freq;}
    public void setRoot(String _root) {  this.root = _root; }
    public void setLemma(String _lemma) {  this.lemma = _lemma;}
    public void setVoweledform(String _vowform) {  this.vowform = _vowform;}
    public void setPartOfSpeech(String _pos) {this.pos = _pos;}
    public void setCase(String _cas) {this.cas = _cas;}
/*============================================================================*/    
    public String getId() {return this.id;}
    public String getVal() {return val;}
    public String getRoot() { return this.root; }
    public String getLemma() { return this.lemma; }
    public String getVoweledform() { return this.vowform;}
    public String getPartOfSpeech() { return this.pos;}
    public String getCase() {return this.cas;}
    public String getFreq() {return freq;}  
/*============================================================================*/  
}
