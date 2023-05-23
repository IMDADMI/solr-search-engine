package net.oujda_nlp_team.impl;
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
import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.Exceptional;
import net.oujda_nlp_team.util.IOFile;
/*============================================================================*/
public class ExceptionalBuildImpl {
/*============================================================================*/
    private java.util.Map<String, Exceptional> exceptional;
    private static final ExceptionalBuildImpl instance = new ExceptionalBuildImpl();
/*============================================================================*/
    public static ExceptionalBuildImpl getInstance() {return instance;}    
/*============================================================================*/    
    private ExceptionalBuildImpl() {
        exceptional = new java.util.HashMap();
        addExceptionalForm();
    }
/*============================================================================*/    
    public boolean isEmpty(){return this.exceptional.isEmpty();}
/*============================================================================*/    
    public void addExceptionalForm(){
        if(isEmpty()){           
            String data = Database.getInstance().getPath() 
                + "/" + Database.getInstance().getResources().getProperty("Data.Exceptional");
            exceptional = IOFile.getInstance().deserializeMap(data);
        }
    }
/*============================================================================*/
    public Exceptional getExceptionalFormValue(String unvoweled){return exceptional.get(unvoweled);}
/*============================================================================*/    
    public boolean containsValue(String unvoweled){return exceptional.containsKey(unvoweled);}
/*============================================================================*/
    public void clear(){exceptional.clear();}
/*============================================================================*/
}
