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
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
/*============================================================================*/
public class IOFile {
    private static final IOFile instance = new IOFile();
    public static IOFile getInstance() {return instance;}
    public IOFile(){}
    public java.io.InputStream openStream(String nameFile) {
        try {
            System.out.println("suuu");
            InputStream inputStream = Objects.requireNonNull(getClass().getResource(nameFile)).openStream();
            return inputStream;

        }
//        getClass().getResourceAsStream
        catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
/*============================================================================*/
    public java.io.InputStream openFileXml(String nameFile) {
        try { return getClass().getResourceAsStream(nameFile);}
        catch (Exception ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
/*============================================================================*/
    public void openFileChooser(javax.swing.JTextPane inputText, String nameCharset, int seuil){
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        int resultat = jfc.showOpenDialog(null);
        if (resultat == javax.swing.JFileChooser.APPROVE_OPTION){
            readFile(jfc.getSelectedFile(), nameCharset, inputText, seuil);
        } 
        else {
            System.out.println("Error : Fichier n'existe pas");
        }
    }
/*============================================================================*/
    public String getArabicURL(String str, java.util.Map<String, String> iMap){
        java.util.Iterator<String> it = iMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            if(str.endsWith(key)){ 
                return str.substring(0, str.length()-10) + iMap.get(key);                             
            }
        }
        return str;
    }
/*============================================================================*/
    public void serializeMap(String BinDirMap, java.util.Map input) {
        java.io.FileOutputStream fos;
        java.io.ObjectOutputStream oos;
        try {
            fos = new java.io.FileOutputStream(BinDirMap);
            oos = new java.io.ObjectOutputStream(fos);
            oos.writeObject(input);
            oos.close(); fos.close();
        } 
        catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
    }
/*============================================================================*/
    public void serializeList(String BinDirMap, java.util.List input) {
        java.io.FileOutputStream fos;
        java.io.ObjectOutputStream oos;
        try {
            fos = new java.io.FileOutputStream(BinDirMap);
            oos = new java.io.ObjectOutputStream(fos);
            oos.writeObject(input);
            oos.close(); fos.close();
        }
        catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
    }
/*============================================================================*/
    public void serializeObject(String BinDirMap, Object input) {        
        java.io.FileOutputStream fos;        
        java.io.ObjectOutputStream oos;        
        try {            
            fos = new java.io.FileOutputStream(BinDirMap);
            oos = new java.io.ObjectOutputStream(fos);
            oos.writeObject(input);
            oos.close();
            fos.close();            
        }
        catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
    }
/*============================================================================*/
    public java.util.Map deserializeMap(String BinDirMap){
        try {
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(openFileXml(BinDirMap)); 
            java.util.Map map = (java.util.Map) ois.readObject();
            ois.close();
            return map;
        }
        catch (java.io.IOException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return new java.util.HashMap();
    }
/*============================================================================*/
    public java.util.List deserializeList(String BinDirMap){
        try {
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(openFileXml(BinDirMap));            
            java.util.List map = (java.util.List) ois.readObject();
            ois.close();
            return map;
        }
        catch (java.io.IOException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return new java.util.ArrayList();
    }
/*============================================================================*/
    public Object deserializeObject(String BinDirMap){
        try {
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(resolveName1(BinDirMap));            
            Object map = ois.readObject();
            ois.close();
            return map;
        }
        catch (java.io.IOException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return new java.util.HashMap();
    }
/*============================================================================*/
    private java.io.InputStream resolveName(String name) {
        if (name == null) {return null;}
        if (!name.startsWith("/")) {
            String baseName = this.getClass().getName();
            int index = baseName.lastIndexOf('.');
            if (index != -1) {
                name = baseName.substring(0, index).replace('.', '/') + "/" + name;
            }
        } else {
            name = name.substring(1);
        }
	return this.getClass().getClassLoader().getResourceAsStream(name);
    }
/*============================================================================*/
    private java.io.InputStream resolveName1(String name) {
        try {return new java.io.FileInputStream(name);}
        catch (IOException ex) {
            Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
/*============================================================================*/
    public java.util.Set readFileToSet( String nameFileIn, String nameCharset){
        java.util.Set lset = new java.util.HashSet();
        java.util.List list = readFileToList(nameFileIn, nameCharset);
        lset.addAll(list);
        return lset;
    }
/*============================================================================*/
    public java.util.List readFileToList(String nameFileIn, String nameCharset){
        java.util.List list = new java.util.ArrayList();
        java.io.BufferedReader in;
        try {
            in = new java.io.BufferedReader(new java.io.InputStreamReader( new java.io.FileInputStream(new java.io.File(nameFileIn)), nameCharset));
            String line;
            while ((line = in.readLine()) != null)
                list.add(line);
            in.close();
        } catch (java.io.IOException ex) {
            System.out.println("Erreur : " + ex);
        }
        return list;
    }
/*============================================================================*/
    public java.util.List readFileToList(java.io.InputStream nameFileIn, String nameCharset){
        java.util.List list = new java.util.ArrayList();
        java.io.BufferedReader in;
        try {
            in = new java.io.BufferedReader(new java.io.InputStreamReader( nameFileIn, nameCharset));
            String line;
            while ((line = in.readLine()) != null)
                list.add(line);
            in.close();
        } catch (java.io.IOException ex) {
            System.out.println("Erreur : " + ex);
        }
        return list;
    }
/*============================================================================*/
    public java.util.List readFileStreamToList(String nameFileIn, String nameCharset){
        java.util.List list = new java.util.ArrayList();
        java.io.BufferedReader in;
        try {
            in = new java.io.BufferedReader(new java.io.InputStreamReader( openStream(nameFileIn), nameCharset));
            String line;
            while ((line = in.readLine()) != null)
                list.add(line);
            in.close();
        } catch (java.io.IOException ex) {
            System.out.println("Erreur : " + ex);
        }
        return list;
    }
/*============================================================================*/
    public java.util.List<String> readSubFileToList(String nameFileIn, String nameCharset, int start){
        java.util.List<String> iResult = new java.util.ArrayList();
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader( new java.io.FileInputStream( new java.io.File(nameFileIn)), nameCharset));
            String line;
            int i=0; 
            while ((line = in.readLine()) != null) {
                if( ++i >= start){
                    iResult.add(line);
                }
            }
            in.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Erreur ...." + e);
        } catch (java.io.IOException e) {
            System.out.println("Erreur ...." + e);
        }   
        return iResult;
    }
/*============================================================================*/
    public void readFile(java.io.File fichier, String nameCharset, javax.swing.JTextPane inputText,int seuil){
        String ligne;
        StringBuilder buf = new StringBuilder();
        try {
            inputText.setText("");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(fichier), nameCharset));
            int i = 0;
            while (i < seuil && (ligne = in.readLine()) != null) {
                buf.append(ligne).append((char) '\n'); i++;
            }
            inputText.setText(buf.toString());
        }
        catch (java.io.IOException e) {
            System.out.println("Error : " + e);
        }
    }
/*============================================================================*/
    public java.util.List<String> readFileToList(
            String namePathIN, 
            String charsetCode, 
            int start, 
            int fin
    ){
        //+-------------------------------------------+        
        java.util.List<String> iResult = new java.util.ArrayList();
        //+-------------------------------------------+        
        try {
            //+-------------------------------------------+
            java.io.BufferedReader in = new java.io.BufferedReader(
                new java.io.InputStreamReader(
                    new java.io.FileInputStream(
                        new java.io.File(namePathIN)), 
                        charsetCode
                    )
                );
            //+-------------------------------------------+
            String line;
            //+-------------------------------------------+
            int i=0; 
            while ((line = in.readLine()) != null  && i<fin) {
                //+-------------------------------------------+
                if(i>=start){
                    iResult.add(line);
                }
                //+-------------------------------------------+
                i++;
                //+-------------------------------------------+
            }
            //+-------------------------------------------+
            in.close();
            //+-------------------------------------------+
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Erreur ...." + e);
        } catch (java.io.IOException e) {
            System.out.println("Erreur ...." + e);
        }   
        //+-------------------------------------------+
        return iResult;
        //+-------------------------------------------+
    }
/*============================================================================*/
    public java.util.Properties loadProperties(java.io.InputStream filename){

        try {
            //java.io.InputStream input = new java.io.FileInputStream(filename);
            //+-------------------------------------------+
            java.util.Properties properties = new java.util.Properties();
            properties.load(filename);
            //+-------------------------------------------+
            filename.close();
            return properties;
            //+-------------------------------------------+
        } catch (java.io.IOException ex) {
            System.out.println("Erreur : " + ex);
        }
        return null;
    }
/*============================================================================*/
    public void writeSetToFile(
            String nameFile, 
            String nameCharset, 
            java.util.Set l
    ){
        //+-------------------------------------------+
        java.util.List list = new java.util.LinkedList();
        //+-------------------------------------------+
        list.addAll(l);
        //+-------------------------------------------+
        writeListToFile(nameFile, nameCharset, list);
        //+-------------------------------------------+
    }
/*============================================================================*/
    public void writeListToFile(
            String nameFile, 
            String nameCharset, 
            java.util.Set l
    ){
        //+-------------------------------------------+
        java.util.List list = new java.util.LinkedList();
        //+-------------------------------------------+
        list.addAll(l);
        java.util.Collections.sort(list);
        //+-------------------------------------------+
        writeListToFile(nameFile, nameCharset, list);
        //+-------------------------------------------+
    }
/*============================================================================*/
    public void writeListToFile(
            String nameFile, 
            String nameCharset, 
            java.util.List l
    ){
        //+-------------------------------------------+
        try {
            //+-------------------------------------------+
            java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(new java.io.File(nameFile)), nameCharset);
            //+-------------------------------------------+
            java.util.Iterator<String> it = l.iterator();
            while (it.hasNext()) {
                String normalizedWord = it.next();
                out.write(normalizedWord + "\n");
            }
            //+-------------------------------------------+
            out.close();
            //+-------------------------------------------+
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Erreur ...." + e);
        } catch (java.io.IOException e) {
            System.out.println("Erreur ...." + e);
        }
        //+-------------------------------------------+
    }
/*============================================================================*/
    public void writeListToFile(
            String nameFile, 
            String nameCharset, 
            java.util.List l,
            boolean ecraser
    ){
        
        try {
            //+-------------------------------------------+
            java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(
                        new java.io.File(nameFile), ecraser), 
                    nameCharset);
            //+-------------------------------------------+
            java.util.Iterator<String> it = l.iterator();
            while (it.hasNext()) {
                String normalizedWord = it.next();
                out.write(normalizedWord + "\n");
            }            
            out.close();            
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Erreur ...." + e);
        } catch (java.io.IOException e) {
            System.out.println("Erreur ...." + e);
        }        
    }
/*============================================================================*/
    public void writeMapToFile( String nameFile, String nameCharset, java.util.Map<String, Integer> map, boolean ecraser){
        try {
            java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(new java.io.File(nameFile), ecraser),nameCharset);
            for (java.util.Map.Entry<String, Integer> entry : map.entrySet()){
                out.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
            out.close();
        }
        catch (java.io.FileNotFoundException e) { System.out.println("Erreur ...." + e); }
        catch (java.io.IOException e) { System.out.println("Erreur ...." + e); }
    }
/*============================================================================*/
    public void writeStringToFile(String nameFile, String nameCharset,String text,boolean ecraser){
        try {
            java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(new java.io.File(nameFile), ecraser), nameCharset);
            out.write(text + "\n");
            out.close();
        } 
        catch (java.io.FileNotFoundException e) {System.out.println("Erreur ...." + e);}
        catch (java.io.IOException e) {System.out.println("Erreur ...." + e);}
    }
/*============================================================================*/
}
