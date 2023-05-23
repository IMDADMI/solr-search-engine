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
public class Exceptional implements java.io.Serializable {
/*============================================================================*/ 
    private static final long serialVersionUID = 12422L;
/*============================================================================*/
    private String unvoweledform;
    private String voweledform;
    private String proclitic;
    private String enclitic;
    private String stem;
    private String pos;
/*============================================================================*/  
    public void setUnvoweledform(String _unvow) {this.unvoweledform = _unvow;}
    public void setVoweledform(String _vow) {this.voweledform = _vow;}
    public void setProclitic(String _proc) {this.proclitic = _proc;}
    public void setEnclitic(String _enc) {this.enclitic = _enc;}
    public void setStem(String _stem) {this.stem = _stem;}
    public void setPartOfSpeech(String _pos) {this.pos = _pos;}
/*==========================================================*==================*/
    public String getUnvoweledform() {return unvoweledform;}
    public String getVoweledform() {return voweledform;}  
    public String getProclitic() {return proclitic;} 
    public String getEnclitic() {return enclitic;}
    public String getStem() {return stem;}
    public String getPartOfSpeech() {return pos;}
/*============================================================================*/  
}
