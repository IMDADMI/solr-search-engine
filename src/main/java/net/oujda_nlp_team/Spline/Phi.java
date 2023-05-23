package net.oujda_nlp_team.Spline;
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
public class Phi implements java.io.Serializable {
    
    private int indice;
    
    private String tag;
    
    private String lastTag;
    
    private double frequency;
    
    private java.util.List<String> previousTag = new java.util.ArrayList();
    
    private static final long serialVersionUID = 12390L;
    
    public Phi(){
        this.tag = "";
        this.frequency = 0.;
        this.previousTag = new java.util.ArrayList();
    }
    
    public Phi(String _tag, double _frequency, java.util.List<String> _list){
        this.tag            = _tag;
        this.frequency      = _frequency;
        this.previousTag    = _list;
    }
    
    
    public String getPreviousTag(int indice){
        return this.previousTag.get(indice);
    }

    public java.util.List<String> getAllTags(){
        return this.previousTag;
    }

    public int getIndice(){
        return this.indice;
    }
    
    public String getTag(){
        return this.tag;
    }
    
    public String getLastTag(){
        return this.lastTag;
    }
    
    public double getFrequency(){
        return this.frequency;
    }
    
    public void setIndice(int _indice){
        this.indice = _indice;
    }
    
    public void setTag(String _tag){
        this.tag = _tag;
    }
    
    public void setLastTag(String _lastTag){
        this.lastTag = _lastTag;
    }
    
    public void setFrequency(double _frequency){
        this.frequency = _frequency;
    }
    
    public void setPreviousTag(String _previousTag){
        this.previousTag.add(_previousTag);
    }
    
}
