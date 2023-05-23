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
public class Collections {
/*============================================================================*/
    public java.util.List regroupList(java.util.List list){
        java.util.Collections.sort(list);
        java.util.List l_word = new java.util.LinkedList();
        java.util.ListIterator it = list.listIterator();
        String word = "";
        String ind;
        String chaine = "";
        if(it.hasNext()){
            String str[] = ((String) it.next()).split("_");
            word = str[0];
            ind = str[1];
            chaine = word + "_" + ind;
        }
        while(it.hasNext()){
            String str[] = ((String) it.next()).split("_");
            if(word.equals(str[0])){
                chaine += " " + str[1];
            }
            else{
                l_word.add(chaine);
                word = str[0];
                ind = str[1];
                chaine = word + "_" + ind;
            }
        }
        l_word.add(chaine);
        return l_word;
    
    }
/*============================================================================*/
    public java.util.List regroupListInteger(java.util.List list){
        
        java.util.Collections.sort(list);
        
        java.util.List l_word = new java.util.LinkedList();
        java.util.ListIterator it = list.listIterator();
        
        String word = "";
        int ind = 0;
        String chaine = "";
        
        if(it.hasNext()){
            String str[] = ((String) it.next()).split("_");
            word = str[0];
            ind = Integer.parseInt(str[1]);
            chaine = word + "_" + ind;
        }
        
        while(it.hasNext()){
            String str[] = ((String) it.next()).split("_");
            
            if(word.equals(str[0])){
                chaine = word + "_" + (ind + Integer.parseInt(str[1]));
            }
            else{
                l_word.add(chaine);
                word = str[0];
                ind = Integer.parseInt(str[1]);
                chaine = word + "_" + ind;
            }
        }
        l_word.add(chaine);
        return l_word;
    
    }
/*============================================================================*/
    public static java.util.Map<String, Integer> addStringToMap(java.util.Map<String, Integer> iMap, String str){
        if(iMap.containsKey(str)){iMap.put(str, iMap.get(str) + 1);}
        else{iMap.put(str, 1);}
        return iMap;
    }    
/*============================================================================*/
    public java.util.Set Intersection( java.util.Set L1, java.util.List L2){
        java.util.Set result = new java.util.HashSet(); 
        for(Object str_L2 : L2)
            if(L1.contains(str_L2)) result.add(str_L2);
        return result;
    }
/*============================================================================*/
    public static String getResultToString(
            Result result,
            boolean choiceAll,
            boolean choiceVoweled,
            boolean choiceProclitic,
            boolean choiceEnclitic,
            boolean choiceStem,
            boolean choiceLemma,
            boolean choiceRoot,
            boolean choicePatternStem,
            boolean choicePatternLemma,
            boolean choicePOS,
            boolean choiceCase
    ){        
        //+---------------------------------------------------------+
        StringBuilder str = new StringBuilder();
        //+---------------------------------------------------------+
        if(choiceAll || choiceVoweled){
            str.append(result.getVoweledWord()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choiceProclitic){
            str.append(result.getProclitic()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choiceEnclitic){
            str.append(result.getEnclitic()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choiceStem){
            str.append(result.getStem()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choiceLemma){
            str.append(result.getLemma()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choiceRoot){
            str.append(result.getRoot()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choicePatternStem){
            str.append(result.getPatternStem()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choicePatternLemma){
            str.append(result.getPatternLemma()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choicePOS){
            str.append(result.getPartOfSpeech()).append('\t');
        }
        //+---------------------------------------------------------+
        if(choiceAll || choiceCase){
            str.append(result.getCaseOrMood()).append('\t');
        }
        //+---------------------------------------------------------+
        return str.toString();
        //+---------------------------------------------------------+
    }
/*============================================================================*/
    public static Result getStringToResult(
            String str,
            boolean choiceAll,
            boolean choiceVoweled,
            boolean choiceProclitic,
            boolean choiceEnclitic,
            boolean choiceStem,
            boolean choiceLemma,
            boolean choiceRoot,
            boolean choicePatternStem,
            boolean choicePatternLemma,
            boolean choicePOS,
            boolean choiceCase
    ){        
        //+---------------------------------------------------------+
        Result result = new Result();
        //+---------------------------------------------------------+
        String strs[] = str.split("\t");
        int i=0;
        if(choiceAll || choiceVoweled){result.setVoweledWord(strs[i++]);}
        if(choiceAll || choiceProclitic){result.setProclitic(strs[i++]);}
        if(choiceAll || choiceEnclitic){result.setEnclitic(strs[i++]);}
        if(choiceAll || choiceStem){result.setStem(strs[i++]);}
        if(choiceAll || choiceLemma){result.setLemma(strs[i++]);}
        if(choiceAll || choiceRoot){result.setRoot(strs[i++]);}
        if(choiceAll || choicePatternStem){result.setPatternStem(strs[i++]);}
        if(choiceAll || choicePatternLemma){result.setPatternLemma(strs[i++]);}
        if(choiceAll || choicePOS){result.setPartOfSpeech(strs[i++]);}
        if(choiceAll || choiceCase){result.setCaseOrMood(strs[i++]);}
        return result;
        //+---------------------------------------------------------+
    }
/*============================================================================*/
    public static java.util.List sort(java.util.List result, boolean choiceAll, boolean choiceVoweled, boolean choiceProclitic, boolean choiceEnclitic, boolean choiceStem, boolean choiceLemma, boolean choiceRoot, boolean choicePatternStem, boolean choicePatternLemma, boolean choicePOS, boolean choiceCase) {            
        java.util.Map<String, String> iMap = new java.util.HashMap();
        java.util.Iterator<Result> it = result.iterator();
        while(it.hasNext()){
            Result rs = it.next();
            String stRes = getResultToString(rs, choiceAll, choiceVoweled, choiceProclitic, choiceEnclitic, choiceStem, choiceLemma, choiceRoot, choicePatternStem, choicePatternLemma, choicePOS, choiceCase);
            iMap.put(stRes, rs.getPriority());
        }
        java.util.List<String> lKey = new java.util.ArrayList();
        java.util.Iterator<String> itt = iMap.keySet().iterator();
        while(itt.hasNext()){
            String rs = itt.next();
            String val = iMap.get(rs);
            lKey.add(val + "x" + rs);
        }
        java.util.Collections.sort(lKey, java.util.Collections.reverseOrder());
        result = new java.util.ArrayList();
        itt = lKey.iterator();
        while(itt.hasNext()){
            String rs = itt.next();
            String val[] = rs.split("x");
            Result Res = getStringToResult(
                    val[1],
                    choiceAll,
                    choiceVoweled,
                    choiceProclitic,
                    choiceEnclitic,
                    choiceStem,
                    choiceLemma,
                    choiceRoot,
                    choicePatternStem,
                    choicePatternLemma,
                    choicePOS,
                    choiceCase
            );
            result.add(Res);
        }
        return result;
    }
/*============================================================================*/
    public String getListToString(java.util.List<String> list){
        StringBuilder str = new StringBuilder();
        java.util.Iterator<String> it = list.iterator();
        while(it.hasNext()){str.append(it.next()).append('\n');}
        return str.toString();
    }
/*============================================================================*/
}
