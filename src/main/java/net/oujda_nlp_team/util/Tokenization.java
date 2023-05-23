package net.oujda_nlp_team.util;
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
public class Tokenization {
/*============================================================================*/
    private java.util.Map<Integer, java.util.List<String>> iMapAllText = new java.util.HashMap();
    private java.util.List<String> AllText = new java.util.LinkedList();
    private java.util.List<String> AllTokensUnvoweled = new java.util.LinkedList();
    public static java.util.Map<String, Integer> TokensRepeat = new java.util.HashMap();
    private java.util.Map<String, Integer> TokensUnvoweledRepeat = new java.util.HashMap();
    private java.util.List<String> allSentences = new java.util.LinkedList();
    private java.util.List<String> AllTokens    = new java.util.LinkedList();
/*============================================================================*/
    private static final Tokenization instance = new Tokenization();
/*============================================================================*/
    public static Tokenization getInstance() {return instance;}
/*============================================================================*/
    private Tokenization() {}
/*============================================================================*/
    public java.util.Map<Integer, java.util.List<String>> getMapAllText(){return iMapAllText;}
/*============================================================================*/
    public java.util.List<String> getAllTokens() {return this.AllTokens;}
/*============================================================================*/
    public java.util.List<String> getAllSentences() {return this.allSentences;}
/*============================================================================*/
    public java.util.Set<String> getTokens() {
        java.util.Set<String> Tokens = new java.util.HashSet();
        Tokens.addAll(this.AllTokens);
        return Tokens;
    }
/*============================================================================*/
    public java.util.Map<String, Integer> getTokensRepeat() {return Tokenization.TokensRepeat;} 
/*============================================================================*/
    public java.util.Map<String, Integer> getTokensUnvoweledRepeat() {return this.TokensUnvoweledRepeat;}
/*============================================================================*/
    public java.util.List<String> getAllTokensUnvoweled() {return this.AllTokensUnvoweled;}
/*============================================================================*/
    public java.util.List<String> getAllText() {return this.AllText;}
/*============================================================================*/
    public int getNbAllTokens(){return getAllTokens().size();}
/*============================================================================*/
    public int getNbAllTokensUnvoweled(){return getAllTokensUnvoweled().size();}
/*============================================================================*/
    public int getNbTokens(){return getTokens().size();}
/*============================================================================*/
    public int getNbAllText(){return getAllText().size();}
/*============================================================================*/
    public void setTokenizationFile(String nameFile, String nameCharset) {
        //+-----------------------------------------------+
        Tokenization.TokensRepeat   = new java.util.HashMap();
        this.TokensUnvoweledRepeat  = new java.util.HashMap();
        this.AllTokensUnvoweled     = new java.util.ArrayList();
        this.AllTokens              = new java.util.ArrayList();
        this.allSentences           = new java.util.ArrayList();
        this.AllText                = new java.util.ArrayList();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
        //+-----------------------------------------------+
        java.io.BufferedReader in;
        try{
            //+-----------------------------------------------+
            in = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(new java.io.File(nameFile)), nameCharset));
            String line;
            String wSentence = "";
            //+-----------------------------------------------+
            while((line=in.readLine())!=null){
                int start = 0;
                int end = 0;
                line = line.replaceAll("" + ArabicCharacter.TATWEEL, "");
                java.util.regex.Matcher matcher = pattern.matcher(line);
                while(matcher.find()){
                    String word = matcher.group();
                    char c1 = word.charAt(0);
                    if(!Validator.getInstance().isSeparator(c1)){
                        word = ArabicStringUtil.getInstance().correctErreur(word);
                        this.AllTokens.add(word);
                        Collections.addStringToMap(Tokenization.TokensRepeat, word);
                        Collections.addStringToMap(TokensUnvoweledRepeat, ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
                        this.AllTokensUnvoweled.add(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
                        wSentence += " " + word;
                    }
                    else{
                        if(!"".equals(wSentence)){this.allSentences.add(wSentence.trim()); wSentence = "";}
                    }
                    if(matcher.start() > 0){
                        String wrd = line.substring(start, matcher.start());
                        this.AllText.add(wrd);
                    }
                    this.AllText.add(word);
                    start = matcher.end();
                    end = matcher.end();
                }
                if( line.length() > end ){
                    String wrd = line.substring(start, line.length());
                    this.AllText.add(wrd);
                }
                this.AllText.add("\n");
                //this.AllText.add("<end>");
                if(!"".equals(wSentence)){this.allSentences.add(wSentence.trim()); wSentence = "";}
            }
            in.close();
        }
        catch(java.io.IOException e){System.out.println("Erreur : " + e);}
    }

/*============================================================================*/
    public void setTokenizationString(String Text) {        
        java.util.List<String> allTokens = new java.util.LinkedList();
        this.AllTokens = new java.util.LinkedList();
        this.AllTokensUnvoweled = new java.util.LinkedList();
        this.AllText = new java.util.LinkedList();
        this.TokensUnvoweledRepeat = new java.util.HashMap();
        this.iMapAllText = new java.util.HashMap();
        Tokenization.TokensRepeat = new java.util.HashMap();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
        java.util.List<String> Lines = new java.util.LinkedList();
        Lines.addAll( java.util.Arrays.asList(Text.split("\n")) );
        java.util.Iterator<String> it_line = Lines.iterator();
        int i=1;
        while(it_line.hasNext()){
            String line = it_line.next();
            int start = 0;
            int end = 0;
            line = line.replaceAll("" + ArabicCharacter.TATWEEL, "");
            java.util.regex.Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                String word = matcher.group();
                char c1 = word.charAt(0);
                if(!Validator.getInstance().isSeparator(c1)){
                    word = ArabicStringUtil.getInstance().correctErreur(word);
                    this.AllTokens.add(word);
                    Collections.addStringToMap( Tokenization.TokensRepeat, word);
                    Collections.addStringToMap( this.TokensUnvoweledRepeat, ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
                    this.AllTokensUnvoweled.add(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
                    allTokens.add(word);
                }
                else{
                    if(!allTokens.isEmpty()){
                        this.iMapAllText.put(i, allTokens);
                        i++;
                        allTokens = new java.util.LinkedList(); 
                    }                                       
                }
                if(matcher.start() > 0){
                    this.AllText.add(line.substring(start, matcher.start()));
                }
                this.AllText.add(word);
                start = matcher.end();
                end = matcher.end();
            }
            if( line.length() > end ){
                this.AllText.add(line.substring(start, line.length()));
            }
            this.AllText.add("");
        }
    }    
/*============================================================================*/
}
