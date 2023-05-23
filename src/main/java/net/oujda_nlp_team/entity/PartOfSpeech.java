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
public class PartOfSpeech implements java.io.Serializable {
/*============================================================================*/
    private static final long serialVersionUID = 1240L;
/*============================================================================*/
    private String id;
    private String main; 
    private String type;
    private String gender;
    private String number;
    private String definit;
    private String nbroot;
    private String person;
    private String person2;
    private String voice;
    private String emphasized;
    private String transitivity;
    private String augmented;
    private String freq;
/*============================================================================*/
    public void setId(String _id) { this.id = _id;}
    public void setMain(String _main) { this.main = _main;}
    public void setType(String _type) { this.type = _type;}
    public void setGender(String _gender) {this.gender = _gender;}
    public void setNumber(String _number) {this.number = _number;}
    public void setDefinit(String _definit) {this.definit = _definit;}
    public void setNbroot(String _nbroot) {this.nbroot = _nbroot;}
    public void setPerson(String _person) {this.person = _person;}
    public void setPerson2(String _person2) {this.person2 = _person2;}
    public void setVoice(String _voice) {this.voice = _voice;}
    public void setEmphasized(String _emphasized) {this.emphasized = _emphasized;}
    public void setTransitivity(String _transitivity) {this.transitivity = _transitivity;}
    public void setAugmented(String _augmented) {this.augmented = _augmented;}
    public void setFreq(String _freq) {this.freq = _freq;}
/*============================================================================*/
    public String getId() { return id;}
    public String getMain() { return main;}
    public String getType() { return type;}
    public String getGender() {return gender;}
    public String getNumber() {return number;}
    public String getDefinit() {return definit;}
    public String getNbroot() {return nbroot;}
    public String getPerson() {return person;}
    public String getPerson2() {return person2;}
    public String getEmphasized() {return emphasized;}
    public String getTransitivity() {return transitivity;}
    public String getAugmented() {return augmented;}
    public String getVoice() {return voice;}
    public String getFreq() {return freq;}
/*============================================================================*/
}
