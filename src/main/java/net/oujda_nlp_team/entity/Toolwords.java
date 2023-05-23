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
public class Toolwords implements java.io.Serializable {
/*============================================================================*/  
    private static final long serialVersionUID = 12445L;
/*============================================================================*/
    private String id;
    private String voweledform;
    private String pos;
    private String procClass;
    private String encClass;
    private String priority;
    private String root;
    private String lemma;
    private String desc;
    private String mood;
/*============================================================================*/
    public void setClassProclitic(String _procClass){this.procClass = _procClass;}
    public void setClassEnclitic(String _encClass){this.encClass = _encClass;}
    public void setRoot(String _root){this.root = _root;}  
    public void setLemma(String _lemma){this.lemma = _lemma;} 
    public void setDesc(String _desc){this.desc = _desc;} 
    public void setMood(String _mood){this.mood = _mood;}
    public void setId(String _id) {this.id = _id;}
    public void setVoweledform(String _voweledform){this.voweledform = _voweledform;}
    public void setPartOfSpeech(String _pos){this.pos = _pos;}
    public void setPriority(String _priority){this.priority = _priority;}  
/*============================================================================*/
    public String getId() {return this.id;}
    public String getVoweledform(){return this.voweledform;}
    public String getClassProclitic(){return this.procClass;}
    public String getClassEnclitic(){return this.encClass;}
    public String getRoot(){return this.root;}  
    public String getLemma(){return this.lemma;} 
    public String getPartOfSpeech(){return this.pos;}
    public String getDesc(){return this.desc;} 
    public String getMood(){return this.mood;}
    public String getPriority(){return this.priority;}  
/*============================================================================*/
}
