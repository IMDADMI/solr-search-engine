package net.oujda_nlp_team;

/*============================================================================*/
/**
 *
 * ADAT : AlKhalil for Disambiguation of Arabic Texts © 2018
 *
 * @author Mohamed BOUDCHICHE
 * @email moha.boudchiche@gmail.com
 *
 */
/*============================================================================*/
import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.Tokenization;
import net.oujda_nlp_team.util.Transliteration;

/*============================================================================*/
public class ADATAnalyzer {

    /*============================================================================*/
    public static java.util.List<String> listLemma;
    public static java.util.List<String> listRoot;
    public static java.util.List<String> listStem;
    /*============================================================================*/
 /*============================================================================*/
    private final String nameFile;
    private final String encoding;
    private java.util.Set<String> setMatrixB;
    private java.util.Set<String> setMatrixA;
    private java.util.Set<String> setMatrixB_1;
    private java.util.Set<String> setMatrixA_1;
    private java.util.Set<String> setMatrixS;
    private java.util.Map<String, String> matrixB;
    private java.util.Map<String, String> matrixA;
    private java.util.Map<String, String> matrixS;
    /*============================================================================*/
    private java.util.List<String> listLM;
    private final java.util.Set<String> notAnaTokens;
    private final java.util.Map<String, Long> mapRoot;
    private final java.util.Map<String, Double> mapLemma;
    private final java.util.Map<String, Long> mapStem;
    private final java.util.Map<String, java.util.List<String>> morph;
    //Provisoire
/*============================================================================*/
    private static final ADATAnalyzer instance = new ADATAnalyzer();

    /*============================================================================*/
    public static ADATAnalyzer getInstance() {
        return instance;
    }

    /*============================================================================*/
    private ADATAnalyzer() {
        nameFile = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.nameFile");
        encoding = "Cp1252";
        this.listLM = IOFile.getInstance().readFileToList(IOFile.getInstance().openFileXml(nameFile), encoding);
        //+---------------------------+
        this.mapRoot = IOFile.getInstance().deserializeMap(Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.fileRoot"));
        this.mapLemma = IOFile.getInstance().deserializeMap(Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.fileLemma2"));
        this.mapStem = IOFile.getInstance().deserializeMap(Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.fileStem"));
        this.setAllMatrixS(listLM);
        this.morph = new java.util.HashMap();
        this.notAnaTokens = new java.util.HashSet();
        //+---------------------------+
    }

    /*============================================================================*/
    private java.util.List<String> analyzed(java.util.List<String> _allTokens, java.util.Map<String, java.util.List<String>> _morph, java.util.Map<String, String> _matrixS, java.util.Map<String, String> _matrixA, java.util.Map<String, String> _matrixB) {
        java.util.Map<Integer, java.util.List<Integer>> iMapRes = new java.util.HashMap();
        java.util.Map<String, Double> startMatrix = new java.util.HashMap();
        java.util.Map<String, Double> IMapResW1;
        java.util.Map<String, Double> IMapResW2;
        //+------------------------------+
        String sword = _allTokens.get(0);
        int size = _morph.get(sword).size();
        double som = 0;
        double D = 0.5;
        double maxStart = 1;
        double maxN = 13336;
        java.util.Iterator<String> it = _morph.get(sword).iterator();
        if (it.hasNext()) {
            String tag = it.next();
            double x = 0;
            if (_matrixS.containsKey(tag)) {
                String[] st = _matrixS.get(tag).split(":");
                maxStart = Double.parseDouble(st[1]) - D;
                maxN = Double.parseDouble(st[0]);
                x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
            }
            som = x;
            startMatrix.put(tag, x);
        }
        while (it.hasNext()) {
            String tag = it.next();
            double start = 0;
            double x = 0;
            if (_matrixS.containsKey(tag)) {
                String[] st = _matrixS.get(tag).split(":");
                start = Double.parseDouble(st[1]) - D;
                maxN = Double.parseDouble(st[0]);
                x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
            }
            if (maxStart < start) {
                maxStart = start;
            }
            som += x;
            startMatrix.put(tag, x);
        }
        size = (size == 0) ? 1 : size;
        it = startMatrix.keySet().iterator();
        while (it.hasNext()) {
            String tag = it.next();
            double val = (maxStart / maxN + startMatrix.get(tag)) / (som + ((maxStart / maxN) * size));
            startMatrix.put(tag, val);
        }
        //+------------------------------+
        int j = 0;
        int i;
        IMapResW1 = new java.util.HashMap();
        for (i = 1; i < _allTokens.size(); i++) {
            IMapResW2 = new java.util.HashMap();
            java.util.List<Integer> il = new java.util.ArrayList();
            String word1 = _allTokens.get(i - 1);
            String word2 = _allTokens.get(i);
            java.util.Iterator<String> it_2 = _morph.get(word2).iterator();
            while (it_2.hasNext()) {
                //+------------------------------+
                String tag2 = it_2.next();
                //+------------------------------+
                double max = 0;
                int imax = 0;
                String maxTag = "";
                j = 0;
                java.util.Iterator<String> it_1 = _morph.get(word1).iterator();
                if (it_1.hasNext()) {
                    String tag1 = it_1.next();
                    j = 0;
                    String word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1) + ":" + tag1;
                    String couple = tag1 + ":" + tag2;
                    if (i == 1) {
                        double start = startMatrix.get(tag1);
                        double resB = (!_matrixB.containsKey(word)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : Double.parseDouble(_matrixB.get(word).split(":")[1]) / Double.parseDouble(_matrixB.get(word).split(":")[0]);
                        double resA = (!_matrixA.containsKey(couple)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : Double.parseDouble(_matrixA.get(couple).split(":")[1]) / Double.parseDouble(_matrixA.get(couple).split(":")[0]);
                        max = resA + resB + start;
                    } else {
                        double resB = (!IMapResW1.containsKey(word)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : IMapResW1.get(word);
                        double resA = (!_matrixA.containsKey(couple)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : Double.parseDouble(_matrixA.get(couple).split(":")[1]) / Double.parseDouble(_matrixA.get(couple).split(":")[0]);
                        max = resA + resB;
                    }
                    maxTag = tag1;
                    imax = j;
                }
                while (it_1.hasNext()) {
                    String tag1 = it_1.next();
                    double val;
                    j++;
                    String word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1) + ":" + tag1;
                    String couple = tag1 + ":" + tag2;
                    if (i == 1) {
                        double start = startMatrix.get(tag1);
                        double resB = (!_matrixB.containsKey(word)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : Double.parseDouble(_matrixB.get(word).split(":")[1]) / Double.parseDouble(_matrixB.get(word).split(":")[0]);
                        double resA = (!_matrixA.containsKey(couple)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : Double.parseDouble(_matrixA.get(couple).split(":")[1]) / Double.parseDouble(_matrixA.get(couple).split(":")[0]);
                        val = resA + resB + start;
                    } else {
                        double resB = (!IMapResW1.containsKey(word)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : IMapResW1.get(word);
                        double resA = (!_matrixA.containsKey(couple)) ? ((this.mapLemma.containsKey(tag1)) ? this.mapLemma.get(tag1) : 0.00001) : Double.parseDouble(_matrixA.get(couple).split(":")[1]) / Double.parseDouble(_matrixA.get(couple).split(":")[0]);
                        val = resA + resB;
                    }
                    if (val > max) {
                        max = val;
                        maxTag = tag1;
                        imax = j;
                    }
                }
                String word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2) + ":" + tag2;
                double resB = (!_matrixB.containsKey(word)) ? ((this.mapLemma.containsKey(tag2)) ? this.mapLemma.get(tag2) : 0.00001) : Double.parseDouble(_matrixB.get(word).split(":")[1]) / Double.parseDouble(_matrixB.get(word).split(":")[0]);
                max = resB + max;
                IMapResW2.put(word, max);
                il.add(imax);
            }
            IMapResW1 = IMapResW2;
            iMapRes.put(i, il);
        }
        //+------------------------------+
        double max = 0;
        int imax = 0;
        String maxTag = "";
        String word = _allTokens.get(i - 1);
        java.util.Iterator<String> itt = _morph.get(word).iterator();
        if (itt.hasNext()) {
            String tag = itt.next();
            String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word) + ":" + tag;
            j = 0;
            max = IMapResW1.get(wrd);
            maxTag = tag;
            imax = j;
        }
        while (itt.hasNext()) {
            String tag = itt.next();
            String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word) + ":" + tag;
            j++;
            double val = IMapResW1.get(wrd);
            if (val > max) {
                max = val;
                maxTag = tag;
                imax = j;
            }
        }
        java.util.List<String> ilp = new java.util.ArrayList();
        ilp.add(maxTag);
        for (i = _allTokens.size() - 1; i >= 1; i--) {
            word = _allTokens.get(i - 1);
            String tag = _morph.get(word).get(iMapRes.get(i).get(imax));
            imax = iMapRes.get(i).get(imax);
            ilp.add(tag);
        }
        java.util.Collections.reverse(ilp);
        return ilp;
    }

    /*============================================================================*/
    private java.util.List<String> analyzedToken(java.util.List<String> _allTokens, java.util.Map<String, java.util.List<String>> _morph,
            java.util.Map<String, String> _matrixS,
            java.util.Map<String, String> _matrixA,
            java.util.Map<String, String> _matrixB
    ) {
        java.util.Map<Integer, java.util.List<Integer>> iMapRes = new java.util.HashMap();
        java.util.Map<String, Double> startMatrix = new java.util.HashMap();
        java.util.Map<String, Double> IMapResW1 = new java.util.HashMap();
        java.util.Map<String, Double> IMapResW2 = new java.util.HashMap();
        //+------------------------------+
        String sword = _allTokens.get(0);
        int size = _morph.get(sword).size();
        double som = 0;
        double D = 0.5;
        double maxStart = 1;
        double maxN = 13336;
        java.util.Iterator<String> it = _morph.get(sword).iterator();
        if (it.hasNext()) {
            String tag = it.next();
            double x = 0;
            if (_matrixS.containsKey(tag)) {
                String[] st = _matrixS.get(tag).split(":");
                maxStart = Double.parseDouble(st[1]) - D;
                maxN = Double.parseDouble(st[0]);
                x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
            }
            som = x;
            startMatrix.put(tag, x);
        }
        while (it.hasNext()) {
            String tag = it.next();
            double start = 0;
            double x = 0;
            if (_matrixS.containsKey(tag)) {
                String[] st = _matrixS.get(tag).split(":");
                start = Double.parseDouble(st[1]) - D;
                maxN = Double.parseDouble(st[0]);
                x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
            }
            if (maxStart < start) {
                maxStart = start;
            }
            som += x;
            startMatrix.put(tag, x);
        }
        size = (size == 0) ? 1 : size;
        it = startMatrix.keySet().iterator();
        while (it.hasNext()) {
            String tag = it.next();
            double resB = (!_matrixB.containsKey(sword)) ? 0.000001 : 0;
            if (_matrixB.containsKey(sword)) {
                String[] st = _matrixB.get(sword).split(":");
                resB = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
            }
            double val = (maxStart / maxN + startMatrix.get(tag)) / (som + ((maxStart / maxN) * size)) + resB;
            startMatrix.put(tag, val);
        }
        double max = 0;
        String maxTag = "";

        it = startMatrix.keySet().iterator();
        if (it.hasNext()) {
            String tag = it.next();
            max = startMatrix.get(tag);
            maxTag = tag;
        }
        while (it.hasNext()) {
            String tag = it.next();
            double val = startMatrix.get(tag);
            if (max < val) {
                max = val;
                maxTag = tag;
            }
        }
        //+------------------------------+        
        java.util.List<String> ilp = new java.util.ArrayList();
        ilp.add(maxTag);
        return ilp;
        //+------------------------------+    
    }

    /*============================================================================*/
    private void addAllMorphoResult(java.util.Set<String> listWords) {
        java.util.Iterator<String> it_result = listWords.iterator();
        while (it_result.hasNext()) {
            String word = it_result.next();
            if (!this.morph.containsKey(word)) {
                this.morph.put(word, getAllLemmas(word, AlKhalil2Analyzer.getInstance().processToken(word)));
            }
        }
    }

    /*============================================================================*/
    private java.util.List<String> getAllLemmas(String word, ResultList _result) {

        if (_result.isAnalyzed()) {
            return _result.getAllLemmas();
        } else {
            this.notAnaTokens.add(word);
            java.util.List<String> _res = new java.util.ArrayList();
            _res.add(word);
            return _res;
        }
    }

    /*============================================================================*/
    private void addMatrixS(java.util.List<String> _allTokens, java.util.Map<String, java.util.List<String>> _morph) {
        this.setMatrixS = new java.util.HashSet();
        if (!_allTokens.isEmpty()) {
            String word = _allTokens.get(0);
            java.util.Iterator<String> iKey = _morph.get(word).iterator();
            while (iKey.hasNext()) {
                String tag = iKey.next();
                String tagBW = Transliteration.getInstance().getArabicToBuckWalter(tag);
                this.setMatrixS.add(tagBW);
            }
        }
    }

    /*============================================================================*/
    private void addMatrixA(java.util.List<String> _allTokens, java.util.Map<String, java.util.List<String>> _morph) {
        this.setMatrixA_1 = new java.util.HashSet();
        this.setMatrixA = new java.util.HashSet();
        for (int i = 1; i < _allTokens.size(); i++) {
            String word1 = _allTokens.get(i - 1);
            String word2 = _allTokens.get(i);
            java.util.Iterator<String> iKey1 = _morph.get(word1).iterator();
            while (iKey1.hasNext()) {
                String tag1 = iKey1.next();
                String tag1BW = Transliteration.getInstance().getArabicToBuckWalter(tag1);
                java.util.Iterator<String> iKey2 = _morph.get(word2).iterator();
                while (iKey2.hasNext()) {
                    String tag2 = iKey2.next();
                    String tag2BW = Transliteration.getInstance().getArabicToBuckWalter(tag2);
                    this.setMatrixA_1.add(tag1BW);
                    this.setMatrixA.add(tag1BW + ":" + tag2BW);
                }
            }
        }
    }

    /*============================================================================*/
    private void addMatrixB(java.util.Map<String, java.util.List<String>> _morph) {
        this.setMatrixB_1 = new java.util.HashSet();
        this.setMatrixB = new java.util.HashSet();
        java.util.Iterator<String> iKey = _morph.keySet().iterator();
        while (iKey.hasNext()) {
            String word = iKey.next();
            String wordBW = Transliteration.getInstance().getArabicToBuckWalter(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
            java.util.Iterator<String> iKey1 = _morph.get(word).iterator();
            while (iKey1.hasNext()) {
                String tag = iKey1.next();
                String tagBW = Transliteration.getInstance().getArabicToBuckWalter(tag);
                this.setMatrixB.add(wordBW + ":" + tagBW);
                this.setMatrixB_1.add(wordBW);
            }
        }
    }

    /*============================================================================*/
    private void setAllMatrixS(java.util.List<String> listLM) {
        this.matrixS = new java.util.HashMap();
        this.matrixB = new java.util.HashMap();
        this.matrixA = new java.util.HashMap();
        String SFreq = "";
        java.util.Iterator<String> it = listLM.iterator();
        while (it.hasNext()) {
            String line = it.next();
            if (line.charAt(0) == 'S') {
                if (line.charAt(1) == '1') {
                    int size = Integer.parseInt(line.substring(3, 5));
                    String Stag = line.substring(6, 6 + size);
                    String StagFreq = line.substring(7 + size);
                    this.matrixS.put(Transliteration.getInstance().getBuckWalterToArabic(Stag), SFreq + ":" + StagFreq);
                } else {
                    SFreq = line.substring(3);
                }
            } else {
                if (line.charAt(0) == 'B') {
                    int sizeT = Integer.parseInt(line.substring(2, 4));
                    int sizeF = Integer.parseInt(line.substring(4, 5));
                    String word = line.substring(6, 6 + sizeT);
                    String wordFreq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
                    //if(setMatrixB_1.contains(word)){                            
                    int taille = 8 + sizeT + sizeF;
                    while (taille < line.length()) {
                        int size2T = Integer.parseInt(line.substring(taille, taille + 2));
                        int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
                        String tag = line.substring(taille + 4, taille + 4 + size2T);
                        String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
                        //if(setMatrixB.contains(word + ":" + tag)){ 
                        String wordArb = Transliteration.getInstance().getBuckWalterToArabic(word);
                        String tagArb = Transliteration.getInstance().getBuckWalterToArabic(tag);
                        this.matrixB.put(wordArb + ":" + tagArb, wordFreq + ":" + tagFreq);
                        //}
                        taille += 6 + size2T + size2F;
                    }
                    //}
                } else {
                    if (line.charAt(0) == 'A') {
                        int sizeT = Integer.parseInt(line.substring(2, 4));
                        int sizeF = Integer.parseInt(line.substring(4, 5));
                        String tag1 = line.substring(6, 6 + sizeT);
                        String tag1Freq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
                        //if(setMatrixA_1.contains(tag1)){                            
                        int taille = 8 + sizeT + sizeF;
                        while (taille < line.length()) {
                            int size2T = Integer.parseInt(line.substring(taille, taille + 2));
                            int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
                            String tag2 = line.substring(taille + 4, taille + 4 + size2T);
                            String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
                            //if(setMatrixA.contains(tag1 + ":" + tag2)){
                            String tag1Arb = Transliteration.getInstance().getBuckWalterToArabic(tag1);
                            String tag2Arb = Transliteration.getInstance().getBuckWalterToArabic(tag2);
                            this.matrixA.put(tag1Arb + ":" + tag2Arb, tag1Freq + ":" + tagFreq);
                            //}
                            taille += 6 + size2T + size2F;
                        }
                        //}
                    }
                }
            }
        }
    }

    /*============================================================================*/
    private void setAllMatrix(java.io.InputStream nameFileIn, String nameCharset, java.util.Set<String> setMatrixS, java.util.Set<String> setMatrixB, java.util.Set<String> setMatrixB_1, java.util.Set<String> setMatrixA, java.util.Set<String> setMatrixA_1) {
        this.matrixS = new java.util.HashMap();
        this.matrixB = new java.util.HashMap();
        this.matrixA = new java.util.HashMap();
        //+-------------------------------------------+
        java.io.BufferedReader in;
        try {
            in = new java.io.BufferedReader(new java.io.InputStreamReader(nameFileIn, nameCharset));
            String SFreq = "";
            String line;
            while ((line = in.readLine()) != null) {
                if (line.charAt(0) == 'S') {
                    if (line.charAt(1) == '1') {
                        int size = Integer.parseInt(line.substring(3, 5));
                        String Stag = line.substring(6, 6 + size);
                        String StagFreq = line.substring(7 + size);
                        if (setMatrixS.contains(Stag)) {
                            String tagArb = Transliteration.getInstance().getBuckWalterToArabic(Stag);
                            this.matrixS.put(tagArb, SFreq + ":" + StagFreq);
                        }
                    } else {
                        SFreq = line.substring(3);
                    }
                } else {
                    if (line.charAt(0) == 'B') {
                        int sizeT = Integer.parseInt(line.substring(2, 4));
                        int sizeF = Integer.parseInt(line.substring(4, 5));
                        String word = line.substring(6, 6 + sizeT);
                        String wordFreq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
                        if (setMatrixB_1.contains(word)) {
                            int taille = 8 + sizeT + sizeF;
                            while (taille < line.length()) {
                                int size2T = Integer.parseInt(line.substring(taille, taille + 2));
                                int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
                                String tag = line.substring(taille + 4, taille + 4 + size2T);
                                String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
                                if (setMatrixB.contains(word + ":" + tag)) {
                                    String wordArb = Transliteration.getInstance().getBuckWalterToArabic(word);
                                    String tagArb = Transliteration.getInstance().getBuckWalterToArabic(tag);
                                    this.matrixB.put(wordArb + ":" + tagArb, wordFreq + ":" + tagFreq);
                                }
                                taille += 6 + size2T + size2F;
                            }
                        }
                    } else {
                        if (line.charAt(0) == 'A') {
                            int sizeT = Integer.parseInt(line.substring(2, 4));
                            int sizeF = Integer.parseInt(line.substring(4, 5));
                            String tag1 = line.substring(6, 6 + sizeT);
                            String tag1Freq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
                            if (setMatrixA_1.contains(tag1)) {
                                int taille = 8 + sizeT + sizeF;
                                while (taille < line.length()) {
                                    int size2T = Integer.parseInt(line.substring(taille, taille + 2));
                                    int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
                                    String tag2 = line.substring(taille + 4, taille + 4 + size2T);
                                    String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
                                    if (setMatrixA.contains(tag1 + ":" + tag2)) {
                                        String tag1Arb = Transliteration.getInstance().getBuckWalterToArabic(tag1);
                                        String tag2Arb = Transliteration.getInstance().getBuckWalterToArabic(tag2);
                                        this.matrixA.put(tag1Arb + ":" + tag2Arb, tag1Freq + ":" + tagFreq);
                                    }
                                    taille += 6 + size2T + size2F;
                                }
                            }
                        }
                    }
                }
            }
            in.close();
        } catch (java.io.IOException ex) {
            System.out.println("Erreur : " + ex);
        }
        //+-------------------------------------------+
    }

    /*============================================================================*/
    public void processLemmatization(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
        Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
        addAllMorphoResult(Tokenization.getInstance().getTokens());
        this.addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
        this.addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
        this.addMatrixB(this.morph);
        this.setAllMatrix(IOFile.getInstance().openFileXml(nameFile), encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
        java.util.List<String> result = new java.util.ArrayList();
        //+-------------------------------------------+
        java.util.Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
        while (it.hasNext()) {
            String sentence = it.next();
            if (!sentence.equals("")) {
                java.util.List<String> iTokens = new java.util.ArrayList();
                iTokens.addAll(java.util.Arrays.asList(sentence.split(" ")));
                if (iTokens.size() > 1) {
                    java.util.List<String> res = this.analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
                    result.addAll(res);
                } else {
                    if (iTokens.size() == 1) {
                        java.util.List<String> res = this.analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
                        result.addAll(res);
                    }
                }
            }
        }
        IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getADDResults(Tokenization.getInstance().getAllText(), this.morph, result));
    }

    /*============================================================================*/
    private java.util.List<String> getADDResults(java.util.List<String> _allText, java.util.Map<String, java.util.List<String>> _morph, java.util.List<String> res) {
        java.util.List<String> results = new java.util.ArrayList();
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <Lemmas>\n";
        int j = 0;
        for (String token : _allText) {
            if (token.equals("\n")) {
                results.add(result);
                result = "";
            } else {
                if (_morph.containsKey(token)) {
                    String _res = (!this.notAnaTokens.contains(token)) ? res.get(j) : token;
                    result += "<res>\n<word>" + token + "</word> \n <lemma>" + _res + "</lemma>\n</res>\n";
                    j++;
                } else {
                    if(!token.trim().equals("")) result += "<res>\n<word>" + token + "</word> \n <lemma>" + token + "</lemma>\n</res>\n";
                }
            }
        }
        result += "</Lemmas>";
        
        if (!result.equals("")) {
            results.add(result);
        }
        
        return results;
    }
    /*============================================================================*/
}
