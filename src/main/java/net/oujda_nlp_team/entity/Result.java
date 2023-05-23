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
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Settings;
/*============================================================================*/
public final class Result implements Comparable<Result>, java.io.Serializable {
/*============================================================================*/
    private static final long serialVersionUID = 124211L;
/*============================================================================*/
    /** the voweled form of the word  */
    private String voweledWord;
    /**the stem of the word  */
    private String stem;
    /** the type of the word  */
    private String partofspeech;
    /** the diac pattern of the word */
    private String diacPatternStem;
    /** the canonic pattern of the word */
    private String patternStem;
    /** the lemma of the word */
    private String lemma;
    /** the pattern of the lemma */
    private String patternLemma;
    /** the root of the word */
    private String root;
    /** the part of speech of the word  */
    private String caseormood;
    /** the proclitic of the word  */
    private String proclitic;
    /** the enclitic of the word  */
    private String enclitic;
    /** the proclitic of the word  */
    private String procliticNoDec;
    /** the descrption of the enclitic  */
    private String encliticNoDec;
    /** the priority of the result in displaying  */
    private String priority;
/*============================================================================*/
    public void setVoweledWord(String _voweledWord) {this.voweledWord = (Settings.choiceVoweledword) ? _voweledWord : "-";}
    public void setStem(String _stem) {this.stem = (Settings.choiceStem) ? _stem : "-";}
    public void setPartOfSpeech(String _partofspeech) {this.partofspeech = (Settings.choicePartOfSpeech) ? _partofspeech : "-";}
    public void setDiacPatternStem(String _diacPatternStem) {this.diacPatternStem = (Settings.choiceDiacPattern) ? _diacPatternStem : "-";}
    public void setPatternStem(String _patternStem) {this.patternStem = (Settings.choicePatternStem) ? _patternStem : "-";}
    public void setLemma(String _lemma) {this.lemma = (Settings.choiceLemma) ? _lemma : "-";}
    public void setPatternLemma(String _patternLemma) {this.patternLemma = (Settings.choicePatternLemma) ? _patternLemma : "-";}
    public void setRoot(String _root) {this.root = (Settings.choiceRoot) ? _root : "-";}
    public void setCaseOrMood(String _caseOrMood) {this.caseormood = (Settings.choiceCaseOrMood) ? _caseOrMood : "-";}
    public void setProclitic(String _proclitic) {this.proclitic = (Settings.prefchoice) ? _proclitic : "-";}
    public void setEnclitic(String _enclitic) {this.enclitic = (Settings.suffixchoice) ? _enclitic : "-";}
    public void setProcliticNoDec(String _procliticNoDec) {this.procliticNoDec = (Settings.prefchoice) ? _procliticNoDec : "-";}
    public void setEncliticNoDec(String _encliticNoDec) {this.encliticNoDec = (Settings.suffixchoice) ? _encliticNoDec : "-";}
    public void setPriority(String _priority) {this.priority = _priority;}
/*============================================================================*/
    public String getVoweledWord() {return voweledWord;}
    public String getStem() {return stem;}
    public String getPartOfSpeech() {return partofspeech;}
    public String getDiacPatternStem() {return diacPatternStem;}
    public String getPatternStem() {return patternStem;}
    public String getLemma() {return lemma;}
    public String getPatternLemma() {return patternLemma;}    
    public String getRoot() {return root;}
    public String getCaseOrMood() {return caseormood;}
    public String getProclitic() {return proclitic;}
    public String getEnclitic() {return enclitic;}
    public String getProcliticNoDec() {return procliticNoDec;}
    public String getEncliticNoDec() {return encliticNoDec;}
    public String getPriority() {return priority;}
/*============================================================================*/
    public Result() {}
/*============================================================================*/
    public Result(java.util.List<String> result, String priority){        
        java.util.Iterator<String> ires = result.iterator();
        if(Settings.choiceVoweledword){this.voweledWord = ires.next();}
        if(Settings.prefchoice){this.proclitic = ires.next();}
        if(Settings.choiceStem){this.stem = ires.next();}
        if(Settings.choicePartOfSpeech){this.partofspeech = ires.next();}
        if(Settings.choiceDiacPattern){this.diacPatternStem = ires.next();}
        if(Settings.choicePatternStem){this.patternStem = ires.next();}
        if(Settings.choiceLemma){this.lemma = ires.next();}
        if(Settings.choicePatternLemma){this.patternLemma = ires.next();}
        if(Settings.choiceRoot){this.root = ires.next();}
        if(Settings.choiceCaseOrMood){this.caseormood = ires.next();}
        if(Settings.suffixchoice){this.enclitic = ires.next();}
        this.priority = priority;
    }
/*============================================================================*/
    /**
     * 
     * @param _voweledWord
     * @param _proclitic
     * @param _stem
     * @param _partofspeech
     * @param wordDiacPattern
     * @param _patternStem
     * @param _lemma
     * @param _patternLemma
     * @param _root
     * @param _cas
     * @param _enclitic 
     */
    public Result(String _voweledWord,
            String _proclitic, 
            String _stem, 
            String _partofspeech, 
            String wordDiacPattern, 
            String _patternStem, 
            String _lemma, 
            String _patternLemma, 
            String _root, 
            String _cas, 
            String _enclitic) {
        //+-------------------------------------------+
        setVoweledWord(_voweledWord);
        setStem(_stem);
        setLemma(_lemma);
        setRoot(_root);
        setPatternStem(_patternStem);
        setPatternLemma(_patternLemma);
        setPartOfSpeech(_partofspeech);
        setCaseOrMood(_cas);
        setProclitic(_proclitic);
        setEnclitic(_enclitic);
        //+-------------------------------------------+
    }
/*============================================================================*/
    /**
     * 
     * @param _voweledWord
     * @param _proclitic
     * @param _stem
     * @param _partofspeech
     * @param _diacPatternStem
     * @param _patternStem
     * @param _lemma
     * @param _patternLemma
     * @param _root
     * @param _caseormood
     * @param _enclitic
     * @param _priority 
     */
    public Result(
            String _voweledWord,
            String _proclitic,
            String _stem,
            String _partofspeech,
            String _diacPatternStem,
            String _patternStem,
            String _lemma,
            String _patternLemma,
            String _root,
            String _caseormood,
            String _enclitic,
            String _priority) {
        //+-------------------------------------------+
        setVoweledWord(_voweledWord);
        setProclitic(_proclitic);
        setEnclitic(_enclitic);
        //+-------------------------------------------+
        _diacPatternStem = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(_diacPatternStem);
        if(!_root.equals("#")){
            _stem = ArabicStringUtil.getInstance().getWordFromRootAndPattern(_root, _diacPatternStem);
        }
        //+-------------------------------------------+
        setStem(_stem);
        setDiacPatternStem(_diacPatternStem);
        setLemma(_lemma);
        setRoot(_root);
        setPatternStem(_patternStem);
        setPatternLemma(_patternLemma);
        setPartOfSpeech(_partofspeech);
        setCaseOrMood(_caseormood);
        setPriority(_priority);
        //+-------------------------------------------+
    }
/*============================================================================*/
    /**
     * <p> </p>
     * @param _voweledWord the voweled form of the word
     * @param _proclitic the proclitic of the word
     * @param _procliticNoDec
     * @param _stem the stem of the word
     * @param _partofspeech the type of the word
     * @param _diacPatternStem the pattern of the word
     * @param _patternStem the canonic pattern of the word
     * @param _lemma the lemma of the word
     * @param _patternLemma the pattern lemma of the word
     * @param _root the root of the word
     * @param _caseormood the part of speech of the word
     * @param _enclitic the enclitic of the word
     * @param _encliticNoDec
     * @param _priority the priority of the resultin displaying
     */
    public Result(String _voweledWord, String _proclitic, String _procliticNoDec, String _stem, String _partofspeech, String _diacPatternStem, String _patternStem, String _lemma, String _patternLemma, String _root, String _caseormood, String _enclitic, String _encliticNoDec, String _priority ) {
        setVoweledWord(_voweledWord);
        setProclitic(_proclitic);
        setProcliticNoDec(_procliticNoDec);
        setEnclitic(_enclitic);
        setEncliticNoDec(_encliticNoDec);
        _diacPatternStem = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(_diacPatternStem);
        if(!_root.equals("#") && !_root.equals("-") && !_diacPatternStem.equals("-") ){
            _stem = ArabicStringUtil.getInstance().getWordFromRootAndPattern(_root, _diacPatternStem);
        }
        setStem(_stem);
        setDiacPatternStem(_diacPatternStem);
        setLemma(_lemma);
        setRoot(_root);
        setPatternStem(_patternStem);
        setPatternLemma(_patternLemma);
        setPartOfSpeech(_partofspeech);
        setCaseOrMood(_caseormood);
        setPriority(_priority);
    }
/*============================================================================*/
    @Override
    public String toString() {
        return (
                (Settings.choiceVoweledword)? "\t"+voweledWord:"") 
                + ((Settings.prefchoice)? "\t"+proclitic:"") 
                + ((Settings.choiceStem)? "\t"+stem:"") 
                + ((Settings.choicePartOfSpeech)? "\t"+partofspeech:"") 
                + ((Settings.choicePatternStem)? "\t"+patternStem:"")
                + ((Settings.choiceLemma)? "\t"+lemma:"")  
                + ((Settings.choicePatternLemma)? "\t"+patternLemma:"") 
                + ((Settings.choiceRoot)? "\t"+root:"") 
                + ((Settings.choiceCaseOrMood)? "\t"+caseormood:"") 
                + ((Settings.suffixchoice)? "\t"+enclitic:"");
    }
/*============================================================================*/
    public String print(){return toString() + "|" + this.priority + "|";}
/*============================================================================*/
    @Override
    public boolean equals(Object obj) {return (obj.hashCode() == this.hashCode());}
/*============================================================================*/
    @Override
    public int hashCode() {
        int hash;
        hash = this.voweledWord.hashCode();
        hash += this.stem.hashCode();
        hash += this.lemma.hashCode();
        hash += this.root.hashCode();
        hash += this.patternLemma.hashCode();
        hash += this.patternStem.hashCode();
        hash += this.proclitic.hashCode();
        hash += this.enclitic.hashCode();
        hash += this.partofspeech.hashCode();
        hash += this.caseormood.hashCode();
        return hash;
    }
/*============================================================================*/
    @Override
    public int compareTo(Result o) {return o.getPriority().compareTo(this.getPriority());}    
/*============================================================================*/
}
