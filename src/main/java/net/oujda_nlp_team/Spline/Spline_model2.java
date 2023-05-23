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
import net.oujda_nlp_team.util.ArabicStringUtil;
//+----------------------------------------------------+
public class Spline_model2 {
/*============================================================================*/    
    //public static java.util.List<String> listNOANALYZED = new java.util.ArrayList();
    //public static java.util.List<String> listP = new java.util.ArrayList();
    //public static java.util.List<String> listT = new java.util.ArrayList();
/*============================================================================*/
    private static final Spline_model2 instance = new Spline_model2();
/*============================================================================*/
    public static Spline_model2 getInstance() {return instance;}
/*============================================================================*/
    private Spline_model2(){}
/*============================================================================*/
    /**
     * 
     * @param word
     * @param _iMapMorph
     * @param _matrixB
     * @return 
     */
    public java.util.Map<String, Double> getProba_Words_To_Tags(String word, java.util.Map<String, java.util.List<String>> _iMapMorph, java.util.Map<String, String> _matrixB){
        java.util.Map<String, Double> _iMapResult = new java.util.HashMap();
        String unvWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
        java.util.Iterator<String> it = _iMapMorph.get(word).iterator();
        while(it.hasNext()){
            String tag      = it.next();
            String Tag    = ""+unvWord + ":" + tag;//, wordFreq + ":" + tagFreq;
            double ival     = 0.0;
            if(_matrixB.containsKey(Tag)){
                String[] vall = _matrixB.get(Tag).split(":");
                ival = Double.parseDouble(vall[1])/Double.parseDouble(vall[0]);
                _iMapResult.put(tag, ival);
            }
            else{
                _iMapResult.put(tag, ival);
            }
        }
        return _iMapResult;
    }
/*============================================================================*/ 
    /**
     * 
     * @param word_j
     * @param word_j_1
     * @param _iMapMorph
     * @param _matrixA
     * @return 
     */
    public java.util.Map<String, Double> getProba_Tags_To_Tags(String word_j, String word_j_1, java.util.Map<String, java.util.List<String>> _iMapMorph, java.util.Map<String, String> _matrixA){
        java.util.Map<String, Double> _iMapResult = new java.util.HashMap();
        double Som  =   0.0; 
        java.util.Iterator<String> it_j_1 = _iMapMorph.get(word_j_1).iterator();
        while(it_j_1.hasNext()){
            String tag_j_1 = it_j_1.next();
            java.util.Iterator<String> it_j = _iMapMorph.get(word_j).iterator();
            while(it_j.hasNext()){
                String tag_j    = it_j.next();
                String E_Tj_Tj1 = tag_j + ":" + tag_j_1;
                double ival     =   0.0;
                if( _matrixA.containsKey(E_Tj_Tj1)){
                    String[] vall = _matrixA.get(E_Tj_Tj1).split(":");
                    ival = Double.parseDouble(vall[1])/Double.parseDouble(vall[0]);
                }
                Som += ival;
                _iMapResult.put(E_Tj_Tj1, ival);
            }
        }
        java.util.Iterator<String> it = _iMapResult.keySet().iterator();
        while(it.hasNext()){
            String E_Tj_Tj1 = it.next();            
            _iMapResult.put(E_Tj_Tj1, ((Som!=0)?_iMapResult.get(E_Tj_Tj1)/Som:0.) ); 
        }
        return _iMapResult;
    }
/*============================================================================*/
    /**
     * 
     * @param _word_j
     * @param _word_j1
     * @param _iMapMorph
     * @param _matrixA
     * @param _matrixB
     * @param _iMapPsy
     * @param indice
     * @return 
     */
    public java.util.Map<String, Phi> getArgMaxI(String _word_j, String _word_j1, java.util.Map<String, java.util.List<String>> _iMapMorph, java.util.Map<String, String> _matrixA, java.util.Map<String, String> _matrixB, java.util.Map<String, Phi> _iMapPsy, int indice){
        //+----------------------------------------------------+
        java.util.Map<String, Phi> _iMapPsyResult = new java.util.HashMap();
        //+----------------------------------------------------+
        java.util.Map<String, Double> iMap_Words_j   = getProba_Words_To_Tags( _word_j, _iMapMorph, _matrixB);
        java.util.Map<String, Double> iMap_Words_j_1 = getProba_Words_To_Tags( _word_j1, _iMapMorph, _matrixB);
        java.util.Map<String, Double> iMap_Trans_j_j1 = getProba_Tags_To_Tags(_word_j, _word_j1, _iMapMorph, _matrixA);
        //+----------------------------------------------------+
        java.util.Iterator<String> it_j_1 = _iMapMorph.get(_word_j1).iterator();
        double somTrans = 0.;
        double somProb = 0.;
        boolean notAnalyzed = false;
        while(it_j_1.hasNext()){
            String tag_j_1  = it_j_1.next();
            double Prob_j_1 = (iMap_Words_j_1.containsKey(tag_j_1)) ? iMap_Words_j_1.get(tag_j_1) : 0.;
            double Max      =   0.0;
            String argMax   =   "#";
            somProb = 0.;
            java.util.Iterator<String> it_j = _iMapMorph.get(_word_j).iterator();
            while(it_j.hasNext()){
                String tag_j = it_j.next();
                double Prob_j   = (iMap_Words_j.containsKey(tag_j)) ? iMap_Words_j.get(tag_j) : 0.;
                String E_Tj_Tj1 = tag_j + ":" + tag_j_1;
                double Trans_j_j1 = (Prob_j!=0) ? iMap_Trans_j_j1.get(E_Tj_Tj1) : 0.;
                double I = ((2*Prob_j)/3) + (Prob_j_1/3) + (Trans_j_j1/6);
                I += (_iMapPsy.containsKey(tag_j)) ? _iMapPsy.get(tag_j).getFrequency() : 0.;
                if( Max<=I ){
                    Max = I;
                    argMax = tag_j;
                }
                somProb     += Prob_j;
                somTrans    += Trans_j_j1;
                if(tag_j.equals("##")){
                    notAnalyzed = true;
                }
            }
            java.util.List<String> list = (_iMapPsy.containsKey(argMax)) ? _iMapPsy.get(argMax).getAllTags() : new java.util.ArrayList();
            if(indice != list.size()){
                list.add(argMax);
            }
            _iMapPsyResult.put(tag_j_1, new Phi(tag_j_1, Max, list));
        }
        /*
        if(somProb == 0){
            listP.add(_word_j);
        }
        else{
            if(somTrans == 0){
                listT.add(_word_j + "\t" + _word_j1);
            }
        }
        if(notAnalyzed){
            listNOANALYZED.add(_word_j);
        }
        */
        //+----------------------------------------------------+
        return _iMapPsyResult;
        //+----------------------------------------------------+
    }
}
