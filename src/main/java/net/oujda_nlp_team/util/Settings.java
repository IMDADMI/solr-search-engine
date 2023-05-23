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
public final class Settings {
/*============================================================================*/
    /** used to choose which database the user wants (the extended or the small one) */
    public static boolean choiceDataBase = false;
    /** true if the user chooses to display the vowelization of the word */
    public static boolean choiceVoweledword = true;
    /** true if the user chooses to display the prefix of the word */
    public static boolean prefchoice = true;
    /** true if the user chooses to display the stem of the word */
    public static boolean choiceStem = true;
    /** true if the user chooses to display the type of the word */
    public static boolean choicePartOfSpeech = true;
    /** true if the user chooses to display the canonic pattern of the word */
    public static boolean choicePatternStem = true;
    /** true if the user chooses to display the diacpattern of the word */
    public static boolean choiceDiacPattern = true;
    /** true if the user chooses to display the lemme of the word */
    public static boolean choiceLemma = true;
    /** true if the user chooses to display the pattern of the lemme */
    public static boolean choicePatternLemma = true;
    /** true if the user chooses to display the root of the word */
    public static boolean choiceRoot = true;
    /** true if the user chooses to display the part of speech of the word */
    public static boolean choiceCaseOrMood = true;
    /** true if the user chooses to display the suffix of the word */
    public static boolean suffixchoice = true;
    public static boolean Tchoice = true;
    public static boolean isText = true;
    /** indicates how many cloumns are used to display the results*/
    private static int ncoloumns;
/*============================================================================*/
    public Settings() {}
/*============================================================================*/
    public void upDateSettings() {
        String fileIn = "/AlKhalil2/db/settings";
        java.io.File settings = new java.io.File(fileIn);
        try {
            java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(settings));
            out.write( "dbchoice=" + ((choiceDataBase)?"true":"false") +  "\n");
            out.write( "vowchoice=" + ((choiceVoweledword)?"true":"false") +  "\n");
            out.write( "prefchoice=" + ((prefchoice)?"true":"false") +  "\n");
            out.write( "stemchoice=" + ((choiceStem)?"true":"false") +  "\n");
            out.write( "typechoice=" + ((choicePartOfSpeech)?"true":"false") +  "\n");
            out.write( "diacpatternchoice=" + ((choiceDiacPattern)?"true":"false") +  "\n");
            out.write( "canpatternchoice=" + ((choicePatternStem)?"true":"false") +  "\n");
            out.write( "lemmechoice=" + ((choiceLemma)?"true":"false") +  "\n");
            out.write( "lemmepatternchoice=" + ((choicePatternLemma)?"true":"false") +  "\n");
            out.write( "rootchoice=" + ((choiceRoot)?"true":"false") +  "\n");
            out.write( "poschoice=" + ((choiceCaseOrMood)?"true":"false") +  "\n");
            out.write( "suffixchoice=" + ((suffixchoice)?"true":"false") +  "\n");
            out.close();
        } catch (java.io.IOException ex) {System.out.println("Erreur : "+ex);}
    }
/*============================================================================*/
    public void readSettings() throws java.io.IOException {
        String fileIn = "/AlKhalil2/db/settings";
        java.io.InputStream settings = IOFile.getInstance().openFileXml(fileIn);
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(settings));
            choiceDataBase = (in.readLine().endsWith("true"));
            choiceVoweledword = (in.readLine().endsWith("true"));
            prefchoice = (in.readLine().endsWith("true"));
            choiceStem = (in.readLine().endsWith("true"));
            choicePartOfSpeech = (in.readLine().endsWith("true"));
            choiceDiacPattern = (in.readLine().endsWith("true"));
            choicePatternStem = (in.readLine().endsWith("true"));
            choiceLemma = (in.readLine().endsWith("true"));
            choicePatternLemma = (in.readLine().endsWith("true"));
            choiceRoot = (in.readLine().endsWith("true"));
            choiceCaseOrMood = (in.readLine().endsWith("true"));
            suffixchoice = (in.readLine().endsWith("true"));
            in.close();
        } catch (java.io.IOException ex) {System.out.println("Erreur : "+ex);}
    }
/*============================================================================*/
    public static void changeSettings(boolean _vowchoice, boolean _prefchoice, boolean _stemchoice, boolean _typechoice, boolean _canpatternchoice, boolean _diacpatternchoice, boolean _lemmechoice, boolean _lemmepatternchoice, boolean _rootchoice, boolean _poschoice, boolean _suffixchoice){
        choiceVoweledword = _vowchoice;
        prefchoice = _prefchoice;
        choiceStem = _stemchoice;
        choicePartOfSpeech = _typechoice;
        choicePatternStem = _canpatternchoice;
        choiceDiacPattern = _diacpatternchoice;
        choiceLemma = _lemmechoice;
        choicePatternLemma = _lemmepatternchoice;
        choiceRoot = _rootchoice;
        choiceCaseOrMood = _poschoice;
        suffixchoice = _suffixchoice;
    }
/*============================================================================*/
    public static void changeSettings(boolean _dbchoice, boolean _vowchoice, boolean _prefchoice, boolean _stemchoice, boolean _typechoice, boolean _canpatternchoice, boolean _diacpatternchoice, boolean _lemmechoice, boolean _lemmepatternchoice, boolean _rootchoice, boolean _poschoice, boolean _suffixchoice){
        choiceDataBase = _dbchoice;
        choiceVoweledword = _vowchoice;
        prefchoice = _prefchoice;
        choiceStem = _stemchoice;
        choicePartOfSpeech = _typechoice;
        choicePatternStem = _canpatternchoice;
        choiceDiacPattern = _diacpatternchoice;
        choiceLemma = _lemmechoice;
        choicePatternLemma = _lemmepatternchoice;
        choiceRoot = _rootchoice;
        choiceCaseOrMood = _poschoice;
        suffixchoice = _suffixchoice;
    }
/*============================================================================*/
    public static int getNumberOfColumn(){
        ncoloumns = 0;
        ncoloumns += ( (Settings.choiceVoweledword) ? 1 : 0 );
        ncoloumns += ( (Settings.prefchoice) ? 1 : 0 );
        ncoloumns += ( (Settings.choiceStem) ? 1 : 0 );
        ncoloumns += ( (Settings.choicePartOfSpeech) ? 1 : 0 );
        ncoloumns += ( (Settings.choicePatternStem) ? 1 : 0 );
        ncoloumns += ( (Settings.choiceLemma) ? 1 : 0 );
        ncoloumns += ( (Settings.choicePatternLemma) ? 1 : 0 );
        ncoloumns += ( (Settings.choiceRoot) ? 1 : 0 );
        ncoloumns += ( (Settings.choiceCaseOrMood) ? 1 : 0 );
        ncoloumns += ( (Settings.suffixchoice) ? 1 : 0 );
        return ncoloumns;
    }
/*============================================================================*/
}
