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
import net.oujda_nlp_team.factory.CliticFactory;
import net.oujda_nlp_team.interfaces.IClitic;
import net.oujda_nlp_team.util.IOFile;
/*============================================================================*/
public class ProcliticImpl extends CliticFactory implements IClitic {
/*============================================================================*/
    private static final IClitic instance = new ProcliticImpl();
/*============================================================================*/
    public static IClitic getInstance() {return instance;}
/*============================================================================*/
    private ProcliticImpl() {
        super();
        this.clitic = new java.util.HashMap();
        addClitics();
    }
/*============================================================================*/
    @Override
    public void addClitics(){
        if(this.clitic.isEmpty()){            
            String data = Database.getInstance().getPath() 
                    + "/" + Database.getInstance().getResources().getProperty("Data.Proclitics");
            this.clitic = IOFile.getInstance().deserializeMap(data);
        }
    }
/*============================================================================*/
    @Override
    public java.util.List getListsClitics(String token) {
        java.util.List listClitics = new java.util.LinkedList();
        int size = token.length(); 
        int ip=0;
        int Max_Proclitic = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Proclitics.Max")); 
        while( ip<size && ip <= Max_Proclitic ){
            listClitics.addAll( possibleClitics(token.substring(0,ip)));
            ip++;
        }
        return listClitics;
    }
/*============================================================================*/    
}
