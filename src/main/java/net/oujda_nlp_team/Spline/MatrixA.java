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
public class MatrixA implements java.io.Serializable {
    
    private String tag;
    
    private long frequency;
    
    private java.util.Map<String, Long> allNextTag = new java.util.HashMap();
    
    private static final long serialVersionUID = 4021L;
    
    public MatrixA(){
        this.tag = "";
        this.frequency = 0;
        this.allNextTag = new java.util.HashMap();
    }
    
    public java.util.Map<String, Long> getAllNextTag(){
        return this.allNextTag;
    }

    public String getTag(){
        return this.tag;
    }
    
    public long getFrequency(){
        return this.frequency;
    }
    
    public void setTag(String _tag){
        this.tag = _tag;
    }
    
    public void setFrequency(long _frequency){
        this.frequency = _frequency;
    }
    
    public void setAllNexTag(String _nextTag, long _frequency){        
        this.allNextTag.put(_nextTag, _frequency + (this.allNextTag.containsKey(_nextTag)? this.allNextTag.get(_nextTag) : 0));
    }
}
