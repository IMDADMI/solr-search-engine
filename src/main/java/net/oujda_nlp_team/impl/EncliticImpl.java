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
public class EncliticImpl extends CliticFactory implements IClitic {
/*============================================================================*/
    private static final IClitic instance = new EncliticImpl();
/*============================================================================*/
    public static IClitic getInstance() {return instance;}
/*============================================================================*/
    private EncliticImpl() {
        super();
        this.clitic = new java.util.HashMap();
        addClitics();
    }
/*============================================================================*/
    @Override
    public void addClitics(){
        if(this.clitic.isEmpty()){
            String data = Database.getInstance().getPath() 
                    + "/" + Database.getInstance().getResources().getProperty("Data.Enclitics");
            this.clitic = IOFile.getInstance().deserializeMap(data);
        }
    }
/*============================================================================*/
    @Override
    public java.util.List getListsClitics(String token) {
        java.util.List listClitics = new java.util.LinkedList();
        int size = token.length(); 
        int ip=0; 
        int Max_Enclitic = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Enclitics.Max"));
        while( ip<size && ip <= Max_Enclitic ){
            listClitics.addAll(possibleClitics(token.substring(size-ip, size)));
            ip++;
        }
        return listClitics;
    }
/*============================================================================*/
}
