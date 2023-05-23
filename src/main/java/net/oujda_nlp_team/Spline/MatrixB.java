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
public class MatrixB implements java.io.Serializable {
    
    private long wordFrequency;
    
    public java.util.Map<String, Long> allTagsFrequency = new java.util.HashMap();
    
    private static final long serialVersionUID = 01234L;
    
    public MatrixB(){
        this.allTagsFrequency = new java.util.HashMap();
    }
    
    public Long getTagsFrequency(String _tag){
        return (this.allTagsFrequency.containsKey(_tag)? this.allTagsFrequency.get(_tag) : 0);
    }

    public long getWordFrequency(){
        return this.wordFrequency;
    }
    
    public void setWordFrequency(long _wordFrequency){
        this.wordFrequency = _wordFrequency;
    }
        
    public void setAllTagsFrequency(String _tag, long _wordtagFrequency){
        this.allTagsFrequency.put(_tag, _wordtagFrequency + (this.allTagsFrequency.containsKey(_tag)? this.allTagsFrequency.get(_tag) : 0));     
    }
}
