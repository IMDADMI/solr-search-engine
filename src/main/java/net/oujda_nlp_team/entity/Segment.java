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
public class Segment implements java.io.Serializable {
/*============================================================================*/
    private static final long serialVersionUID = 121214L;
/*============================================================================*/
    /** the  prefix  */
    private Clitic proclitic;
    /** the  stem  */
    private String stem;
    /** the  suffix  */
    private Clitic enclitic;
/*============================================================================*/
    public Segment(){}
/*============================================================================*/
    public Segment(Clitic _proclitic, String _stem, Clitic _enclitic){
        setProclitic(_proclitic);
        setStem(_stem);
        setEnclitic(_enclitic);
    }
/*============================================================================*/
    public Clitic getProclitic() {return proclitic;}
    public String getStem() {return stem;}
    public Clitic getEnclitic() {return enclitic;}
/*============================================================================*/    
    public void setProclitic(Clitic _proclitic) {this.proclitic = _proclitic;}
    public void setStem(String _stem) {this.stem = _stem;}
    public void setEnclitic(Clitic _enclitic) {this.enclitic = _enclitic;}
/*============================================================================*/
    @Override
    public String toString(){return (this.getProclitic().getUnvoweledform() + "|" + this.getProclitic().getVoweledform() + "|" + this.getStem() + "|" + this.getEnclitic().getVoweledform() + "|" + this.getEnclitic().getUnvoweledform());}
/*============================================================================*/
}
