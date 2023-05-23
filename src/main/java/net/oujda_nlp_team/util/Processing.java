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
import net.oujda_nlp_team.entity.Result;
/*============================================================================*/
public class Processing {
/*============================================================================*/
    private Result result = new Result();
/*============================================================================*/
    public Processing() {}
/*============================================================================*/
    public Result getResult() {return result;}
/*============================================================================*/
    public void setResult(Result result) {this.result = result;}
/*============================================================================*/
    /**
     * 
     * @param voweledWord
     * @param prefix
     * @param stem
     * @param wordType
     * @param wordDiacPattern
     * @param wordCanPattern
     * @param lemme
     * @param lemmePattern
     * @param wordRoot
     * @param pos
     * @param suffix
     * @return 
     */
    public java.util.List listResultSS(String voweledWord, String prefix, String stem, String wordType, String wordDiacPattern, String wordCanPattern, String lemme, String lemmePattern, String wordRoot, String pos, String suffix){
        
        java.util.List _result = new java.util.ArrayList();
        
        if(Settings.choiceVoweledword){
            _result.add(voweledWord);
        }
        if(Settings.prefchoice){
            _result.add(prefix);
        }
        if(Settings.choiceStem){
            _result.add(stem);
        }
        if(net.oujda_nlp_team.util.Settings.choicePartOfSpeech){
            _result.add(wordType);
        }
        if(net.oujda_nlp_team.util.Settings.choiceDiacPattern){
            _result.add(wordDiacPattern);
        }
        if(net.oujda_nlp_team.util.Settings.choicePatternStem){
            _result.add(wordCanPattern);
        }
        if(net.oujda_nlp_team.util.Settings.choiceLemma){
            _result.add(lemme);
        }
        if(net.oujda_nlp_team.util.Settings.choicePatternLemma){
            _result.add(lemmePattern);
        }
        if(net.oujda_nlp_team.util.Settings.choiceRoot){
            _result.add(wordRoot);
        }
        if(net.oujda_nlp_team.util.Settings.choiceCaseOrMood){
            _result.add(pos);
        }
        if(net.oujda_nlp_team.util.Settings.suffixchoice){
            _result.add(suffix);
        }
        return _result;
    }
/*============================================================================*/
    /**
     * 
     * @param result
     * @return 
     */    
    public java.util.List nOcurrenceResult(java.util.List result){
        
        java.util.Set hashTemp = new java.util.HashSet();
        java.util.List resultat = new java.util.ArrayList();
        java.util.Iterator it_result = result.iterator();
        while(it_result.hasNext()){
            setResult((net.oujda_nlp_team.entity.Result) 
                    it_result.next());
            if(!hashTemp.contains(toString())){
                hashTemp.add(toString());
                resultat.add(this.result);
            }   
        }
        return resultat;
    }
/*============================================================================*/
    /**
     * 
     * @param r
     */
    public void print(Result r){setResult(r);}
/*============================================================================*/
     public static java.util.List sortResult_(java.util.List result) {
         if (!result.isEmpty()) {
             for (int i = 0; i < result.size() - 1; i++) {
                net.oujda_nlp_team.entity.Result 
                ri = (net.oujda_nlp_team.entity.Result) result.get(i);
                int ind = i;
                net.oujda_nlp_team.entity.Result temp = ri;
                for (int j = i; j < result.size(); j++) {
                    net.oujda_nlp_team.entity.Result 
                    rj = (net.oujda_nlp_team.entity.Result) result.get(j);
                    if (rj.getPriority().compareTo(temp.getPriority()) < 0) {
                        ind = j;
                        temp = rj;
                    }
                }
                result.add(i, temp);
                result.remove(i + 1);
                result.add(ind, ri);
                result.remove(ind + 1);
             }
         }
         return result;
    }
/*============================================================================*/
     public java.util.List sortResultssSSS(java.util.List result) {
        
         java.util.List lRes = new java.util.ArrayList();
         
         int i=0;
         java.util.Iterator<net.oujda_nlp_team.entity.Result> it = result.iterator();
         while(it.hasNext()){
             net.oujda_nlp_team.entity.Result st = it.next();
             lRes.add( st.getPriority() + "_" + i);
             i++;
         }
         java.util.Collections.sort(lRes);
         
         java.util.List resul = new java.util.ArrayList();
         java.util.Iterator<String> iit = lRes.iterator();
         while(it.hasNext()){
             String st = iit.next();
             String str[] = st.split("_");
             int ind = Integer.parseInt(str[1]);
             resul.add((net.oujda_nlp_team.entity.Result) result.get(ind));
         }
         
         return nOcurrenceResult(resul);
    }
/*============================================================================*/
     @Override
     public String toString(){
        String str = ""; 
        str += (net.oujda_nlp_team.util.Settings.choiceVoweledword)? this.result.getVoweledWord() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.prefchoice)? this.result.getProclitic() + ";" + this.result.getProcliticNoDec() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choiceStem)? this.result.getStem() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choicePatternStem)? this.result.getPatternStem() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choiceLemma)? this.result.getLemma() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choicePatternLemma)? this.result.getPatternLemma() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choiceRoot)? this.result.getRoot() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choicePartOfSpeech)? this.result.getPartOfSpeech() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.choiceCaseOrMood)? this.result.getCaseOrMood() + ";" : "" ;
        str += (net.oujda_nlp_team.util.Settings.suffixchoice)? this.result.getEnclitic() + ";" + this.result.getEncliticNoDec() + ";" : "" ;
        return str;
     }
/*============================================================================*/
     public Result toResult(String str){
        
         net.oujda_nlp_team.entity.Result result1 = new net.oujda_nlp_team.entity.Result(); 
         String Str[] = str.split(";"); 
         int i = 0;
         
         result1.setPriority("");
         
         if(net.oujda_nlp_team.util.Settings.choicePartOfSpeech){
             result1.setPartOfSpeech(Str[i]);
             i++;
         }
         
         
         if(net.oujda_nlp_team.util.Settings.choiceVoweledword){
             result1.setVoweledWord(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.prefchoice){
             result1.setProclitic(Str[i]);
             i++;
             result1.setProcliticNoDec(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choiceStem){
             result1.setStem(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choicePatternStem){
             result1.setPatternStem(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choiceLemma){
             result1.setLemma(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choicePatternLemma){
             result1.setPatternLemma(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choiceRoot){
             result1.setRoot(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choicePartOfSpeech){
             result1.setPartOfSpeech(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.choiceCaseOrMood){
             result1.setCaseOrMood(Str[i]);
             i++;
         }
         
         if(net.oujda_nlp_team.util.Settings.suffixchoice){
             result1.setEnclitic(Str[i]);
             i++;
             result1.setEnclitic(Str[i]);
             i++;
         }
         return result1;
    }
/*============================================================================*/
    /**
    * 
    * @param result
    * @return 
    */
    public java.util.List sorts(java.util.List result) {
        java.util.Set<String> strRes = new java.util.HashSet();         
        java.util.Iterator<Result> it = result.iterator();
        while(it.hasNext()){
            this.result = it.next();
            strRes.add(toString());
        }
        java.util.List lres = new java.util.ArrayList();
        lres.addAll(strRes);
        java.util.Collections.sort(lres);
        java.util.List Res = new java.util.ArrayList();       
        java.util.Iterator<String> itt = lres.iterator();
        while(itt.hasNext()){
            String st = itt.next();
            Result res = toResult(st);
            Res.add(res);
        }         
        return Res;
    }
/*============================================================================*/
    /**
    * 
    * @param _result
    * @return 
    */
    public static java.util.List<String> getAllLemma(java.util.List _result){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!_result.isEmpty()){
            java.util.Iterator<Result> it_result = _result.iterator();
            while(it_result.hasNext()){
                Result st_result = it_result.next();
                lSet.add(st_result.getLemma());
            }      
        }
        else{lSet.add("##");}
        _res.addAll(lSet);
        return _res;
        
    } 
/*============================================================================*/
    /**
     * 
     * @param _result
     * @param root
     * @return 
     */
    public static java.util.List<String> getAllLemma(java.util.List _result, String root){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!_result.isEmpty()){
            java.util.Iterator<Result> it_result = _result.iterator();
            while(it_result.hasNext()){
                Result st_result = it_result.next();
                if(st_result.getRoot().equals(root)){
                    lSet.add(st_result.getLemma());
                }                
            }
        }
        _res.addAll(lSet);
        return _res;
    }
/*============================================================================*/
    /**
     * 
     * @param _result
     * @return 
     */
    public java.util.List<String> getAllDiacritizerWords(java.util.List _result){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!_result.isEmpty()){
            java.util.Iterator<Result> it_result = _result.iterator();
            while(it_result.hasNext()){
                Result st_result = it_result.next();
                String word = st_result.getVoweledWord();
                word = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(word);
                lSet.add(word);
            }
        }
        _res.addAll(lSet);
        return _res;
    }
/*============================================================================*/
    /**
     * 
     * @param _result
     * @return 
     */
    public static java.util.List<String> getAllStem(java.util.List _result){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!_result.isEmpty()){
            java.util.Iterator<Result> it_result = _result.iterator();
            while(it_result.hasNext()){
                Result st_result = it_result.next();
                lSet.add(st_result.getStem());
            }     
        }
        _res.addAll(lSet);
        return _res;
    }
/*============================================================================*/
    /**
     * 
     * @param _result
     * @return 
     */
    public static java.util.List<String> getAllRoot(java.util.List _result){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!_result.isEmpty()){
            java.util.Iterator<Result> it_result = _result.iterator();
            while(it_result.hasNext()){
                Result st_result = it_result.next();
                lSet.add(st_result.getRoot());
            }       
        }
        _res.addAll(lSet);
        return _res;
    }
/*============================================================================*/
    /**
     * 
     * @return 
     */
    public int getNbrHeaderResult(){
        int cmp = 1;
        cmp += (Settings.choiceVoweledword) ? 1 : 0;
        cmp += (Settings.prefchoice) ? 1 : 0;
        cmp += (Settings.choiceStem) ? 1 : 0;
        cmp += (Settings.choicePartOfSpeech) ? 1 : 0;
        cmp += (Settings.choicePatternStem) ? 1 : 0;
        cmp += (Settings.choiceLemma) ? 1 : 0;
        cmp += (Settings.choicePatternLemma) ? 1 : 0;
        cmp += (Settings.choiceRoot) ? 1 : 0;
        cmp += (Settings.choiceCaseOrMood) ? 1 : 0;
        cmp += (Settings.suffixchoice) ? 1 : 0;
        return cmp;
    }
/*============================================================================*/
}
