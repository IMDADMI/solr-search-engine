package net.oujda_nlp_team.config;
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
public class Database {
/*============================================================================*/
    private static final Database instance = new Database();
/*============================================================================*/
    public static Database getInstance() {return instance;}
/*============================================================================*/
    private final java.util.Properties resources;
    private final String path;
/*============================================================================*/
    public Database(){
        String filename = "/net/oujda_nlp_team/config/AlKhalil2.properties";

        this.resources = net.oujda_nlp_team.util.IOFile.getInstance().loadProperties(net.oujda_nlp_team.util.IOFile.getInstance().openFileXml(filename));
        this.path = this.resources.getProperty("ALKHALIL2.database.path");
    }
/*============================================================================*/
    public Database(String filename){
        this.resources = net.oujda_nlp_team.util.IOFile.getInstance().loadProperties(net.oujda_nlp_team.util.IOFile.getInstance().openFileXml(filename));
        this.path = this.resources.getProperty("ALKHALIL2.database.path");
    }
/*============================================================================*/
    public java.util.Properties getResources(){return this.resources;}
    public String getPath(){return this.path;}
/*============================================================================*/
}
