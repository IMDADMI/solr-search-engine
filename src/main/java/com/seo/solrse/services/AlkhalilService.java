package com.seo.solrse.services;

import net.oujda_nlp_team.ADATAnalyzer;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Arrays;

@Service
public class AlkhalilService {


    /***
     * this method take the query of the user and lemmitize it using al-khalil lemmitizer
     * @param query the query of the user
     * @return return a string that contain the lemma separated by a space
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public String convertToLemmas(String query) throws IOException, ParserConfigurationException, SAXException {
        query.replaceAll(",","");
        query.replaceAll("\\.","");
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/query.txt"));
        writer.write(query);
        writer.close();

            ADATAnalyzer
                    .getInstance().processLemmatization(
                            "src/query.txt",
                            "utf-8",
                            "src/query.xml",
                            "utf-8");

        return getContentFromXML("src/query.xml");
    }

    /***
     * this method get all the nodes in the xml file
     * @param path take the path of the xml file
     * @return the list of nodes
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private NodeList getItems(String path) throws IOException,ParserConfigurationException, SAXException {
        File inputFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        return  doc.getElementsByTagName("res");
    }

    /***
     * here in this method we get the content from the xml file which is the lemmas and the words
     * @param path the path of the xml file
     * @return lemmas separated by a space
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private String getContentFromXML(String path) throws ParserConfigurationException, IOException, SAXException {
        StringBuilder builder = new StringBuilder();
        NodeList nList = getItems(path);
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            Element element = (Element) nNode;
            String lemma = element.getElementsByTagName("lemma").item(0).getTextContent();
            lemma = removeSpecialCharacters(lemma);
            builder.append(lemma).append(" ");
        }
        return builder.toString();
    }

    /***
     * this method fix and return the lemma without any special character
     * @param lemma
     * @return return the fixed lemma
     */
    private static String removeSpecialCharacters(String lemma) {
        return (lemma.replaceAll("[،؟.\\[\\]{}()’`]", " ")).replaceAll("EOL","");
    }


}
