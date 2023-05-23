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
public class GenerateDiac {
/*============================================================================*/    
    public int regexOccur(String text, String regex) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(text);
        int occur = 0;
        while(matcher.find()) {occur ++;}
        return occur;
    }   
/*============================================================================*/
    public String deleteVoyel(String word){
        String mot =word;
        for (int i = 0; i < mot.length(); i++){
            String s=String.valueOf(mot.charAt(i));
            int c=(int)mot.charAt(i);
            if(!((c>=1569&&c<=1594)||(c>=1601&&c<=1610))){
                mot=mot.replace(s,"");
            }
        }
        return mot;
    }
/*============================================================================*/
    public String deleteHamza(String mot){
        for (int i = 0; i < mot.length(); i++) {
            if(mot.charAt(i)=='ؤ' ||mot.charAt(i)=='ئ' ||mot.charAt(i)=='أ'||mot.charAt(i)=='إ') {
                char c[]=mot.toCharArray();
                c[i]='ء';
                mot=new String(c);
            }
        }
       return mot;
    }
/*============================================================================*/
    int nbr_car(char c,String mot){
        int cp=0;
        for(int i=0;i<mot.length();i++){
            if(mot.charAt(i)==c) cp++;
        }
        return cp;
    }
/*============================================================================*/
  public int  getSuffix(String canonic)
  {
      for(int i=canonic.length()-1;i>=0;i--)
          if(canonic.charAt(i)=='ل') return i;
      return -1;
  }
/*============================================================================*/
  
  public String  deleteSuffix1(String vow,String suffix)
  {
      String voweled=vow;
      int j=suffix.length()-1;
      if(j>=0)
      {
      for(int i=voweled.length()-1;i>=voweled.length()-j-1;i--)
          if(voweled.charAt(i)==suffix.charAt(j)) 
          {
              voweled=voweled.substring(0,i);
              --j;
          }
      }
      return voweled;
  }
 /*============================================================================*/
  public String  deleteSuffix(String vow,String suffix)
  {
      String voweled=vow;
      String suff=vow.substring(vow.length()-suffix.length(),vow.length());
      if(suff.compareTo(suffix)==0)
          voweled=vow.substring(0,vow.length()-suffix.length());
      return voweled;
  }
/*============================================================================*/
    public String deletePreffix(String vow ,String pref){
        char c[]=vow.toCharArray();
        for (int i = 0; i < pref.length(); i++) {
            if(pref.charAt(i)==c[i]) c[i]=' ';
        }
        vow=new String(c);
        vow=vow.replace(" ", "");
        return vow;
    }

/*============================================================================*/
    public String Replace(CharSequence inputStr,String patternStr,String replacementStr){
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternStr);
        java.util.regex.Matcher matcher = pattern.matcher(inputStr);
        String output = matcher.replaceAll(replacementStr);
        return output;
    }
/*============================================================================*/ 
    public String diac(String root,String vow,String can){

        String Chakl = "[ًٌٍَُِّْ]";
        String Vow = vow.replaceAll("[ًٌٍَُِّْ]", "");
        String T = vow;
        String Can = can.replaceAll("[ًٌٍَُِّْ]", "");
            
        boolean passe=false;
        int p1 = Can.indexOf(""+ArabicCharacter.FEH);
        if(p1>0){
            if(Can.charAt(p1-1)==root.charAt(0)){passe=true;}
        }            
        String Pref="";
        String Suff="";
        boolean trouve=false;
        trouve=false;
        for(int i=Vow.length()-1; i>=0 && !trouve; i--){
            if(Can.charAt( ((Can.length()-Vow.length())+i))!=ArabicCharacter.LAM){
                if(Can.substring((Can.length()-Vow.length())+i, Can.length()).equals(Vow.substring(i, Vow.length()))){
                    Suff=Vow.substring(i, Vow.length());
                }
                else{trouve = true;}
            }
            else{trouve = true;}
        }
            trouve=false;
            for(int i=1;i<Vow.length() && !trouve;i++)
            {
                if(Can.charAt(i-1)!=ArabicCharacter.FEH){
                    if(Can.substring(0, i).equals(Vow.substring(0, i)))
                        Pref=Can.substring(0,i);
                    else
                        trouve = true;
                }
                else
                    trouve = true;   
                
            }
            
            // System.out.println("\""+root+"\",\""+vow+"\",\""+can+"\"");
            Vow = Vow.substring(Pref.length(),Vow.length()-Suff.length());
            Can = Can.substring(Pref.length(),Can.length()-Suff.length());
            String VowCan ="";
            String VowA = Vow.replaceAll("[أإؤئ]", "ء");
            
            String Pref1="^";
            String Suff1="";
            for(int s=0;s<Pref.length();s++)
            {
                Pref1+="("+Pref.charAt(s)+")"+"("+Chakl+")*";
            }
            for(int s=0;s<Suff.length();s++)
            {
                Suff1+="("+Suff.charAt(s)+")"+"("+Chakl+")*";
            }
            Suff1+="$";
            
            T = Replace((CharSequence)T, Pref1, "");
            T = Replace((CharSequence)T, Suff1, "");
            
            int indR=0;
            int indV=0;
            int indC=0;
            int i=0;
             //System.out.println(vow+":"+can);
           //  System.out.println(Vow+":"+Can);
            
            int position=0;
            int x=0;
            while(i<Vow.length() && indC<Can.length()){
                //System.out.println(i+":"+Vow.charAt(i));
                //System.out.println(indC+":"+Can.charAt(i));
                if(Vow.charAt(i)=='آ'){
                    
                    VowCan+='آ';
                    T=T.replace(""+Vow.charAt(i), "");
                    
                    //System.out.println(Can.charAt(indC));
                    if(Can.charAt(indC)!='آ') {
                        indC+=1;
                    }
                    indC+=1;
                    indR = root.indexOf("ء");
                    i++;
                }
                else
                {
                    if(indC==0 && ( Can.charAt(indC)=='ا' || Can.charAt(indC)=='أ') ){
                        indC++;
                    }
                    else{
                       
                        if(Vow.charAt(i)==Can.charAt(indC) && ( Can.charAt(indC)!=ArabicCharacter.FEH && Can.charAt(indC)!='ع' && Can.charAt(indC)!='ل'  )){
                            
                            if(position==1 && (root.charAt(1)==ArabicCharacter.YEH) || (ArabicCharacter.WAW==root.charAt(1)))
                            {
                                position=2;
                            }
                            else
                            {
                                if(position==2 && (root.charAt(2)==ArabicCharacter.YEH) || (ArabicCharacter.WAW==root.charAt(2)))
                                {
                                    position=3;
                                }
                                else
                                {
                                    if(root.length()>3)
                                    {
                                        if(position==3 && (root.charAt(3)==ArabicCharacter.YEH) || (ArabicCharacter.WAW==root.charAt(3)))
                                        {
                                            position=4;
                                        }
                                    }
                                        
                                }
                            }
                            VowCan+=""+Can.charAt(indC);
                            T=T.replace(""+Vow.charAt(i), "");
                            indC++;
                            i++;
                            
                        }
                        else
                        {
                            if(Vow.charAt(i)=='ى' || Vow.charAt(i)==ArabicCharacter.ALEF)
                            {
                                if(position==1 && (root.charAt(1)==ArabicCharacter.YEH) || (ArabicCharacter.WAW==root.charAt(1)))
                            {
                                position=2;
                            }
                            else
                            {
                                if(position==2 && (root.charAt(2)==ArabicCharacter.YEH) || (ArabicCharacter.WAW==root.charAt(2)))
                                {
                                    position=3;
                                }
                                else
                                {
                                    if(root.length()>3)
                                    {
                                        if(position==3 && (root.charAt(3)==ArabicCharacter.YEH) || (ArabicCharacter.WAW==root.charAt(3)))
                                        {
                                            position=4;
                                        }
                                    }
                                        
                                }
                            }
                                VowCan+=Vow.charAt(i);
                                T=T.replace(""+Vow.charAt(i), "");
                                indC++;
                                i++;
                            }
                            else
                            {
                                if(Vow.charAt(i)==ArabicCharacter.YEH || Vow.charAt(i)==ArabicCharacter.WAW )
                                {
                                    
                                    if(Can.charAt(indC)==ArabicCharacter.FEH)
                                    {
                                        position=1;
                                        if(root.charAt(0)==Vow.charAt(i))
                                        {
                                            
                                            int T1= T.indexOf(Vow.charAt(i));
                                            if(T1<T.length()-1)
                                            {
                                                if(Validator.getInstance().isDiacritic(T.charAt(T1+1)))
                                                {
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    VowCan+=Can.charAt(indC);
                                                    indC++;i++;
                                                }
                                                else
                                                {
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    VowCan+=Vow.charAt(i);
                                                    indC++;i++;
                                                }
                                             }
                                            else
                                            {
                                                T=T.replace(""+Vow.charAt(i), "");
                                                VowCan+=Vow.charAt(i);
                                                indC++;i++;                                    
                                            }
                                        }
                                        else
                                        {
                                            T=T.replace(""+Vow.charAt(i), "");
                                            VowCan+=Vow.charAt(i);
                                            indC++;i++; 
                                        }
                                    }
                                    else
                                    {
                                        if(Can.charAt(indC)==ArabicCharacter.AIN )
                                        {
                                            position=2;
                                            if(root.charAt(1)==Vow.charAt(i))
                                            {
                                                int T1= T.indexOf(Vow.charAt(i));
                                                if(T1<T.length()-1)
                                                {
                                                    if(Validator.getInstance().isDiacritic(T.charAt(T1+1)))
                                                    {
                                                        VowCan+=Can.charAt(indC);
                                                        T=T.replace(""+Vow.charAt(i), "");
                                                        indC++;i++;
                                                    }
                                                    else
                                                    {
                                                        VowCan+=Vow.charAt(i);
                                                        T=T.replace(""+Vow.charAt(i), "");
                                                        indC++;i++;
                                                    }
                                                }
                                                else
                                                {
                                                    VowCan+=Vow.charAt(i);
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    indC++;i++;                                    
                                                }
                                            }
                                            else
                                            {
                                                VowCan+=Vow.charAt(i);
                                                T=T.replace(""+Vow.charAt(i), "");
                                                indC++;i++; 
                                            }
                                        }
                                        else
                                        {
                                            if(Can.charAt(indC)==ArabicCharacter.LAM)
                                            {
                                                if(x==0)position=3;
                                                else position=4;
                                                x++;
                                                if((root.length()==3)?(root.charAt(2)==Vow.charAt(i)):((root.charAt(2)==Vow.charAt(i)) || (root.charAt(3)==Vow.charAt(i)) ))
                                                {
                                                    int T1= T.indexOf(Vow.charAt(i));
                                                    if(T1<T.length()-1)
                                                    {
                                                        if(Validator.getInstance().isDiacritic(T.charAt(T1+1))){
                                                            T=T.replace(""+Vow.charAt(i), "");
                                                            VowCan+=Can.charAt(indC);
                                                            indC++;i++;
                                                        }
                                                        else
                                                        {
                                                            T=T.replace(""+Vow.charAt(i), "");
                                                            VowCan+=Vow.charAt(i);indC++;i++;
                                                        }
                                                    }
                                                    else
                                                    {
                                                        T=T.replace(""+Vow.charAt(i), "");
                                                        VowCan+=Vow.charAt(i);
                                                        indC++;i++;                                    
                                                    }
                                                }
                                                else
                                                {
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    VowCan+=Vow.charAt(i);
                                                    indC++;i++; 
                                                }
                                            }
                                            else
                                            {
                                                indC++;
                                            }
                                        }
                                    } 
                                }
                                else
                                {
                                     
                                    if(Can.charAt(indC)==ArabicCharacter.FEH)
                                    {   
                                        position=1;
                                       /* if(root.charAt(0)=='ي' || root.charAt(0)=='و')
                                        {
                                            indC++;
                                        }
                                        else
                                        {*/
                                            if((root.charAt(0)==Vow.charAt(i)) || (VowA.charAt(i)==root.charAt(0)) )
                                            {
                                                VowCan+=Can.charAt(indC);
                                                T=T.replace(""+Vow.charAt(i), "");
                                                indC++;i++;
                                            }
                                            else
                                            {
                                                if(root.charAt(0)=='ء' || passe)
                                                {
                                                    indC++;
                                                }
                                                else{
                                                // cette cas n'existe pas ??...
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    VowCan+=Vow.charAt(i);
                                                    i++;  indC++;
                                                }
                                           // }
                                        }
                                    }
                                    else
                                    {
                                        if(Can.charAt(indC)==ArabicCharacter.AIN )
                                        {
                                            position=2;
                                          /*  if(root.charAt(1)=='ي' || root.charAt(1)=='و')
                                            {
                                                indC++;
                                            }
                                            else
                                            {*/
                                                if((root.charAt(1)==Vow.charAt(i))  || (VowA.charAt(i)==root.charAt(1)) )
                                                {
                                                    VowCan+=Can.charAt(indC);
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    indC++;i++;
                                                }
                                                else
                                                {
                                                    // cette cas n'existe pas ??...
                                                    T=T.replace(""+Vow.charAt(i), "");
                                                    VowCan+=Vow.charAt(i);
                                                    i++;indC++;
                                                }
                                           // }
                                        }
                                        else
                                        { 
                                            if(Can.charAt(indC)==ArabicCharacter.LAM)
                                            {
                                                if(x==0)position=3;
                                                else position=4;
                                                x++;
                                              /**/
                                                    if((root.length()==3)?((root.charAt(2)==Vow.charAt(i)) || (VowA.charAt(i)==root.charAt(2))):((root.charAt(2)==Vow.charAt(i)) || (root.charAt(3)==Vow.charAt(i))  || (VowA.charAt(i)==root.charAt(2)) || (VowA.charAt(i)==root.charAt(3))))
                                                    {
                                                        VowCan+=Can.charAt(indC);
                                                        T=T.replace(""+Vow.charAt(i), "");
                                                        indC++;i++;
                                                    }
                                                    else
                                                    {
                                                       /* if((root.length()==3)?((root.charAt(2)=='ي') || ('و'==root.charAt(2))):((root.charAt(2)=='ي') || (root.charAt(3)=='و')  || ('و'==root.charAt(2)) || ('ي'==root.charAt(3))))
                                                        {
                                                            indC++;  
                                                        }
                                                        else
                                                        {*/
                                                            VowCan+=Vow.charAt(i);  
                                                            T=T.replace(""+Vow.charAt(i), "");
                                                            indC++; i++; 
                                                       // }
                                                 }
                                            }
                                            else{
                                                
                                                //System.out.println(Can.charAt(indC-1));
                                                //System.out.println(root.charAt(1)+":"+Vow.charAt(i));
                                                
                                                //System.out.println((Can.charAt(indC-1)=='ف' && (root.charAt(1)==Vow.charAt(i)) || (VowA.charAt(i)==root.charAt(1))));
                                                //System.out.println((Can.charAt(indC-1)=='ع' && (root.charAt(2)==Vow.charAt(i)) || (VowA.charAt(i)==root.charAt(2))) );
                                                
                                            if((position==1  && ((root.charAt(1)==Vow.charAt(i)) || (VowA.charAt(i)==root.charAt(1))) )|| (position==2 && ((root.charAt(2)==Vow.charAt(i)) || (VowA.charAt(i)==root.charAt(2)))) ) {
                                                indC++;
                                            }
                                            else{
                                                T=T.replace(""+Vow.charAt(i), "");
                                                VowCan+=Vow.charAt(i);
                                                i++;indC++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int k=i;k<Vow.length();k++){ VowCan+=Vow.charAt(k);}
        String Result= Pref+VowCan+Suff;
        String res="";
        int ind=0;
        for(int t=0;t<vow.length();t++){
            if(Validator.getInstance().isDiacritic(vow.charAt(t))){ res+=vow.charAt(t); }
            else{res+=Result.charAt(ind); ind++;}
        }
        // System.out.println(vow +" : "+can +" : "+res);
        return res;
    }
/*============================================================================*/
    public String getRules(String root, String diac, String wr){
        //+-------------------------------------------+
        String Result="";
        if(root.length()==3)
        {
            int pos3=0;
            diac=diac.replaceAll("[ًٌٍَُِّْ]", "");
            Result+=(!diac.contains(""+ArabicCharacter.FEH))?root.charAt(0):String.valueOf((diac.indexOf(""+ArabicCharacter.FEH)+1));
            
            if(!diac.contains(""+ArabicCharacter.AIN))
            {
                Result+=root.charAt(1);
            }
            else
            {
                Result+=String.valueOf(diac.indexOf(""+ArabicCharacter.AIN)+1);
            }
            
            if(!diac.contains("ل"))
            {
                if(diac.contains("ع"))
                {
                    if(root.charAt(1)==root.charAt(2))
                    {
                        Result+=String.valueOf(diac.indexOf("ع")+1);
                    }
                    else
                    {
                        Result+=root.charAt(2);
                    }
                }
                else {
                    Result+=root.charAt(2);
                }
            }
            else
            {
                Result+=String.valueOf(diac.indexOf("ل")+1);
            }
            
        }
        else
        {
            int posL=0;
            diac=diac.replaceAll("[ًٌٍَُِّْ]", "");
            wr=wr.replaceAll("[ًٌٍَُِّْ]", "");
            String Wr = wr.replaceAll("[أإؤئ]", "ء");
            if(!diac.contains("ف"))
            {
                Result+=root.charAt(0);
            }
            else
            {
                Result+=String.valueOf(diac.indexOf("ف")+1);
            }
            
            if(!diac.contains("ع"))
            {
                Result+=root.charAt(1);
            }
            else
            {
                Result+=String.valueOf(diac.indexOf("ع")+1);
            }
                
            
            if(!diac.contains("ل"))
            {
                Result+=root.charAt(2)+root.charAt(3);
            }
            else
            {
                posL=diac.indexOf("ل");
                if((root.charAt(2)==wr.charAt(posL)) || (root.charAt(2)==Wr.charAt(posL))) 
                {
                    Result+=String.valueOf(posL+1);
                    if(!diac.substring(posL+1).contains("ل"))
                    {
                        Result+=root.charAt(3);
                    }
                    else
                    {
                        posL=diac.substring(0, posL+1).length()+diac.substring(posL+1).indexOf("ل")+1;
                        Result+=String.valueOf(posL);
                    }
                }
                else
                {
                    Result+=root.charAt(2)+String.valueOf(posL+1);
                }
            }
            
        }
        
        return Result;
    }
}
