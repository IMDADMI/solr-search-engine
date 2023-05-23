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
public class Root implements java.io.Serializable{
/*============================================================================*/
    private static final long serialVersionUID = 1234L;
/*============================================================================*/
    private String val;
    private String len1;
    private String len2;
    private String len3;
    private String len4;
    private String len5;
    private String len6;
    private String len7;
    private String len8;
    private String len9;
    private String len10;
    private String len11;
    private String len12;
    private String freq;
/*============================================================================*/
    public void setVal(String _val) {this.val = _val;}
    public void setLen1(String _len1) {this.len1 = _len1;}
    public void setLen2(String _len2) {this.len2 = _len2;}
    public void setLen3(String _len3) {this.len3 = _len3;}
    public void setLen4(String _len4) {this.len4 = _len4;}
    public void setLen5(String _len5) {this.len5 = _len5;}
    public void setLen6(String _len6) {this.len6 = _len6;}
    public void setLen7(String _len7) {this.len7 = _len7;}
    public void setLen8(String _len8) {this.len8 = _len8;}
    public void setLen9(String _len9) {this.len9 = _len9;}
    public void setLen10(String _len10) {this.len10 = _len10;}
    public void setLen11(String _len11) {this.len11 = _len11;}
    public void setLen12(String _len12) {this.len12 = _len12;}
    public void setFreq(String _freq) {this.freq = _freq;}
/*============================================================================*/
    public String getVal() {return val;}
    public String getLen1() {return len1;}
    public String getLen2() {return len2;}
    public String getLen3() {return len3;}
    public String getLen4() {return len4;}
    public String getLen5() {return len5;}
    public String getLen6() {return len6;}
    public String getLen7() {return len7;}
    public String getLen8() {return len8;}
    public String getLen9() {return len9;}
    public String getLen10() {return len10;}
    public String getLen11() {return len11;}
    public String getLen12() {return len12;}
    public String getFreq() {return freq;}
/*============================================================================*/
    @Override
    public String toString() {return val + ";" + len1 + ";" + len2 + ";" + len3 + ";" + len4 + ";" + len5 + ";" + len6 + ";" + len7 + ";" + len8 + ";" + len9 + ";" + len10 + ";" + len11 + ";" + len12 + ";" + freq;}
/*============================================================================*/
}
