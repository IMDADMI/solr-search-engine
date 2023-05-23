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
public class Formulas implements java.io.Serializable{
/*============================================================================*/
    private static final long serialVersionUID = 123L;    
/*============================================================================*/
    private String idRoot;
    private String idDiacPatternStem;
    private String idCanonicPatternStem;
    private String idDiacPatternLemma;
    private String idCanonicPatternLemma;
    private String idPartOfSpeech;
    private String idCaseOrMood;
/*============================================================================*/ 
    public void setIdRoot(String _idRoot) {this.idRoot = _idRoot;}
    public void setIdDiacPatternStem(String _idDPS) {this.idDiacPatternStem = _idDPS;}
    public void setIdCanonicPatternStem(String _idCPS) {this.idCanonicPatternStem = _idCPS;}
    public void setIdDiacPatternLemma(String _idDPL) {this.idDiacPatternLemma = _idDPL;}
    public void setIdCanonicPatternLemma(String _idCPL) {this.idCanonicPatternLemma = _idCPL;}
    public void setIdPartOfSpeech(String _idPOS) {this.idPartOfSpeech = _idPOS;}
    public void setIdCaseOrMood(String _idCOM) {this.idCaseOrMood = _idCOM;}
/*============================================================================*/ 
    public String getIdRoot() {return idRoot;}
    public String getIdDiacPatternStem() {return idDiacPatternStem;}
    public String getIdCanonicPatternStem() {return idCanonicPatternStem;}
    public String getIdDiacPatternLemma() {return idDiacPatternLemma;}
    public String getIdCanonicPatternLemma() {return idCanonicPatternLemma;}
    public String getIdPartOfSpeech() {return idPartOfSpeech;}
    public String getIdCaseOrMood() {return idCaseOrMood;}
/*============================================================================*/ 
}
