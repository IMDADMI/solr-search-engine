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
import java.util.Collections;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Transliteration;
/*============================================================================*/
public class ResultList {
/*============================================================================*/
    private java.util.List<Result> allResult;
/*============================================================================*/
    public ResultList(){this.allResult = new java.util.ArrayList();}
/*============================================================================*/
    public void add(Result res){this.allResult.add(res);}
    public void addAll(java.util.List<Result> res){this.allResult.addAll(res);}
    private boolean isEmpty(){return this.allResult.isEmpty();}
    public boolean isAnalyzed(){return !isEmpty();}
    public int size(){return this.allResult.size();}
    public java.util.List<Result> getAllResults(){return this.allResult;}
/*============================================================================*/
    public void print(java.util.List<String> result){
        java.util.Iterator<String> it_result = result.iterator();
        while(it_result.hasNext()){
            String st_result = it_result.next();
            String bw = Transliteration.getInstance().getArabicToBuckWalter(st_result);
            System.out.println(st_result + "\t" + bw);
        }
    }
/*============================================================================*/
    public String listToString(java.util.List<String> result){
        Collections.sort(result);
        String res = "";
        java.util.Iterator<String> it_result = result.iterator();
        while(it_result.hasNext()){
            String st_result = it_result.next();
            res += st_result + " ";
        }
        return res.trim();
    }
/*============================================================================*/
    public String listToString(java.util.List<String> result, String separator){
        Collections.sort(result);
        String res = "";
        java.util.Iterator<String> it_result = result.iterator();
        while(it_result.hasNext()){
            String st_result = it_result.next();
            res += st_result + separator;
        }
        return res.trim();
    }
/*============================================================================*/
    public java.util.List<String> getAllRoots(){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                String st_result = it_result.next().getRoot();
                if(!lSet.contains(st_result)){
                    lSet.add(st_result);
                    _res.add(st_result);
                }                
            }
        }
        return _res;
    }
/*============================================================================*/
    public java.util.List<String> getAllLemmas(){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                String st_result = it_result.next().getLemma();
                if(!lSet.contains(st_result)){
                    lSet.add(st_result);
                    _res.add(st_result);
                }                
            }
        }
        return _res;
    }
/*============================================================================*/
    public java.util.List<String> getAllDiac(){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                String st_result = it_result.next().getVoweledWord();
                if(!lSet.contains(st_result)){
                    lSet.add(st_result);
                    _res.add(st_result);
                }                
            }
        }
        return _res;
    }
/*============================================================================*/
    public java.util.Set<String> getAllDiacMajrour(){
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                Result re = it_result.next();
                String st_result = re.getVoweledWord();
                String st_Mood = re.getCaseOrMood();
                if(!lSet.contains(st_result) && st_Mood.equals("مجرور")){
                    lSet.add(st_result);
                }
            }
        }
        return lSet;
    }
/*============================================================================*/
    public java.util.List<String> getAllStems(){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                String st_result = it_result.next().getStem();
                if(!lSet.contains(st_result)){
                    lSet.add(st_result);
                    _res.add(st_result);
                }                
            }
        }
        return _res;
    }
/*============================================================================*/
    public java.util.List<String> getAllVoweledWord(){
        java.util.List<String> _res = new java.util.ArrayList();
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                String st_result = it_result.next().getVoweledWord();
                st_result = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(st_result);
                if(!lSet.contains(st_result)){
                    lSet.add(st_result);
                    _res.add(st_result);
                }                
            }
        }
        return _res;
    }
/*============================================================================*/
    public String getAllVoweledWordString(){
        String str = "";
        java.util.Set<String> lSet = new java.util.HashSet();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                String st_result = it_result.next().getVoweledWord();
                st_result = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(st_result);
                if(!lSet.contains(st_result)){
                    lSet.add(st_result);
                    str += "<a class='dropdown-item' style='font-size: 1.5em;'>" + st_result + "</a>";
                }                
            }
        }
        return str;
    }
/*============================================================================*/
    public java.util.Map<String, java.util.Set<String>> getAllLemmaStems(){
        java.util.Map<String, java.util.Set<String>> map = new java.util.HashMap();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                Result res = it_result.next();
                String lemma = res.getLemma();
                String stem = res.getStem();
                if(map.containsKey(lemma)){ map.get(lemma).add(stem); }
                else{
                    java.util.Set<String> lStem = new java.util.HashSet();
                    lStem.add(stem);
                    map.put(lemma, lStem);
                }               
            }
        }
        return map;
    }
/*============================================================================*/
    public java.util.Map<String, java.util.Set<String>> getAllLemmaRoots(){
        java.util.Map<String, java.util.Set<String>> map = new java.util.HashMap();
        if(!allResult.isEmpty()){
            java.util.Iterator<Result> it_result = allResult.iterator();
            while(it_result.hasNext()){
                Result res = it_result.next();
                String lemma = res.getLemma();
                String root = res.getRoot();
                if(map.containsKey(lemma)){ map.get(lemma).add(root); }
                else{
                    java.util.Set<String> lRoot = new java.util.HashSet();
                    lRoot.add(root);
                    map.put(lemma, lRoot);
                }               
            }
        }
        return map;
    }
/*============================================================================*/
    public void sort() {        
        java.util.Map<String, Result> iMap = new java.util.HashMap();
        java.util.Iterator<Result> it = allResult.iterator();
        while(it.hasNext()){
            Result rs = it.next();
            if(!iMap.containsKey(rs.toString())){iMap.put(rs.toString(), rs);}
        }
        allResult = new java.util.ArrayList();
        java.util.Iterator<String> itt = iMap.keySet().iterator();
        while(itt.hasNext()){add(iMap.get(itt.next()));}
        java.util.Collections.sort(allResult);
    }   
/*============================================================================*/
    @Override
    public String toString(){return allResult.toString();}
/*============================================================================*/
    public void printResult() {        
        java.util.Iterator<Result> it = allResult.iterator();
        while(it.hasNext()){
            Result rs = it.next();
            String word = rs.getVoweledWord();
            String root = rs.getRoot();
            String lemma = rs.getLemma();
            String stem = rs.getStem();
            String pLemma = rs.getPatternLemma();
            String pStem = rs.getPatternStem();
            String mood = rs.getCaseOrMood();
            String pos = rs.getPartOfSpeech();
            String enc = rs.getEnclitic();
            String pro = rs.getProclitic();
            String p = "";
            if(root.length()>0){
                p = ""+root.charAt(0);
                for(int i=1; i<root.length(); i++){
                    p += "." + root.charAt(i);
                }
            }
            
            String res = word + "\t" + "[" + p + "]" + " " + mood + "\n";
            res += "الجذع: " + stem + "/" + pStem + "/" + "\t" + "الفرع: " + lemma + "/" + pLemma + "/" + "\n";
            res += pos + "\n";
            res += pro + "\t" + enc;
            System.out.println(res);
            System.out.println("------------------------");
        }
    }   
/*============================================================================*/
    public void printAllStems() {        
        java.util.Iterator<String> it = getAllStems().iterator();
        while(it.hasNext()){
            String word = it.next();
            String bwWord = Transliteration.getInstance().getArabicToBuckWalter(word);
            System.out.println(word + "\t" + "/"+bwWord+"/");
        }
    }   
/*============================================================================*/
    public boolean containsType(String type) {        
        java.util.Iterator<Result> it = allResult.iterator();
        while(it.hasNext()){
            Result rs = it.next();
            String word = rs.getVoweledWord();
            String root = rs.getRoot();
            String lemma = rs.getLemma();
            String stem = rs.getStem();
            String pLemma = rs.getPatternLemma();
            String pStem = rs.getPatternStem();
            String mood = rs.getCaseOrMood();
            String pos = rs.getPartOfSpeech();
            String enc = rs.getEnclitic();
            String pro = rs.getProclitic();
            if(pos.startsWith("اسم" + "|" + type)){return true;}
        }
        return false;
    }   
/*============================================================================*/
    public java.util.List<String> getAllResultInLine() {
        java.util.List<String> result = new java.util.ArrayList();
        java.util.Iterator<Result> it = allResult.iterator();
        while(it.hasNext()){
            Result rs = it.next();
            String word = rs.getVoweledWord();
            String root = rs.getRoot();
            String lemma = rs.getLemma();
            String stem = rs.getStem();
            String pLemma = rs.getPatternLemma();
            String pStem = rs.getPatternStem();
            String mood = rs.getCaseOrMood();
            String pos = rs.getPartOfSpeech();
            String enc = rs.getEnclitic();
            String pro = rs.getProclitic();
            String priority = rs.getPriority();
            
            result.add(priority + rs);
        }
        java.util.Collections.sort(result, java.util.Collections.reverseOrder());
        return result;
    }   
/*============================================================================*/
    public String getAllLemmasString(){
        java.util.List<String> list = getAllLemmas();
        StringBuilder str = new StringBuilder();
        java.util.Iterator<String> it = list.iterator();
        while(it.hasNext()){str.append(it.next()).append(':');}
        return str.toString().trim();
    } 
/*============================================================================*/
    public String getAllLemmasString(char diliminateur){
        java.util.List<String> list = getAllLemmas();
        StringBuilder str = new StringBuilder();
        java.util.Iterator<String> it = list.iterator();
        while(it.hasNext()){str.append(it.next()).append(diliminateur);}
        return str.toString().trim();
    }
/*============================================================================*/
    public String getAllRootString(){
        java.util.List<String> list = getAllRoots();
        StringBuilder str = new StringBuilder();
        java.util.Iterator<String> it = list.iterator();
        while(it.hasNext()){str.append(it.next()).append(':');}
        return str.toString().trim();
    }
/*============================================================================*/
    public String getAllStemString(){
        java.util.List<String> list = getAllStems();
        StringBuilder str = new StringBuilder();
        java.util.Iterator<String> it = list.iterator();
        while(it.hasNext()){str.append(it.next()).append(':');}
        return str.toString().trim();
    }
/*============================================================================*/
}
