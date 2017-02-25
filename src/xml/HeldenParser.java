/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import easykampf.Person;
import easykampf.TextLogger;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.text.Text;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 *
 * @author cje
 */
public class HeldenParser {
    
    public static Person parseDef(String fileName) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        
        File file = new File(fileName);
        org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                file);
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        
                NodeList nodes = (NodeList) xpath.evaluate("//processing-instruction()[name() = \"xml-stylesheet\"]", doc,
                XPathConstants.NODESET);
        if (nodes.getLength() == 0) {
             TextLogger.getInstance().add("Datei <b>" + fileName + "</b> ist keine gültige Helden-Datei", "red");
            return null;
        }
        
        if(!nodes.item(0).getNodeValue().contains("helden.xsl")){
            TextLogger.getInstance().add("Datei <b>" + fileName + "</b> ist keine gültige Helden-Datei", "red");
            return null;
        }
            
        
        
        //System.out.println(nodes.item(0).getNodeValue());
        
        
        Person p = new Person();
        
        nodes = (NodeList) xpath.evaluate("//helden/held/@name", doc,
                XPathConstants.NODESET);
        if (nodes.getLength() == 0) {
            TextLogger.getInstance().add("Datei <b>" + fileName + "</b> enthält keine gültigen Helden-Daten. Ist die Datei korrupt?", "red");
            return null;
        }
        String name = nodes.item(0).getNodeValue();
        p.setNAME(name);
        
        nodes = (NodeList) xpath.evaluate("//helden/held/eigenschaften/eigenschaft", doc,
                XPathConstants.NODESET);
        
        HashMap<String, String[]> aMap = new HashMap<>();
        
        if (nodes != null) {
            int length = nodes.getLength();
            for (int i = 0; i < length; i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nodes.item(i);
                    //if (el.getNodeName().contains("staff")) {
                    //String name = el.getElementsByTagName("name").item(0).getTextContent();

                    String n = getAttributeValue(e, "name");
                    String m = getAttributeValue(e, "mod");
                    String v = getAttributeValue(e, "value");
                    aMap.put(n, new String[]{m, v});
                    
                }
            }
            
        }
        
        calcAttr(p, aMap);
        
        nodes = (NodeList) xpath.evaluate("//helden/held/vt/vorteil[@name='Eisern']", doc,
                XPathConstants.NODESET);
        boolean eisern = false;
        if (nodes.getLength() == 1) {
            eisern = true;
        }
        calcWs(p, aMap, eisern);
        
        return p;
    }
    
    private static String getAttributeValue(Element e, String att) {
        
        NamedNodeMap ma = e.getAttributes();
        
        try {
            String a = ma.getNamedItem(att).getNodeValue();
            return a;
            //System.out.println(tVal);
        } catch (NullPointerException ex) {
            return null;
        }
        
    }

    private static void calcAttr(Person p, HashMap<String, String[]> aMap) {
        int ko = num(aMap.get("Konstitution")[1]);
        int kk = num(aMap.get("Körperkraft")[1]);
        int mu = num(aMap.get("Mut")[1]);
        int kl = num(aMap.get("Klugheit")[1]);
        int ge = num(aMap.get("Gewandtheit")[1]);
        int in = num(aMap.get("Intuition")[1]);
        int ch = num(aMap.get("Charisma")[1]);
        
        int at = num(aMap.get("at")[1]);
        int pa = num(aMap.get("pa")[1]);
        int ini = num(aMap.get("ini")[1]);
        p.setAT(at);
        p.setPA(pa);
        p.setINI(ini);
        p.setBASEINI(ini);
        
        String[] mrArray = aMap.get("Magieresistenz");
        String[] lpArray = aMap.get("Lebensenergie");
        String[] auArray = aMap.get("Ausdauer");
        String[] aspArray = aMap.get("Astralenergie");

        // ws
        //TODO FK-wert, gs
        int lp = Math.round((float) (ko * 2 + kk) / 2) + num(lpArray[0]) + num(lpArray[1]);
        p.setLP(lp);
        p.setMAXLP(lp);
        int mr = num(mrArray[0]) + num(mrArray[1]) + Math.round((float) (mu + kl + ko) / 5);
        p.setMR(mr);
        int au = num(auArray[0]) + num(auArray[1]) + Math.round((float) (mu + ge + ko) / 2);
        p.setAU(au);
        
        int asp = num(aspArray[0]) + num(aspArray[1]) + Math.round((float) (mu + in + 2 * ch) / 2);
        p.setASP(asp);
        
        p.setGS(7);
    }
    
    private static int num(String s) {
        return Integer.parseInt(s);
    }
    
    private static void calcWs(Person p, HashMap<String, String[]> aMap, boolean eisern) {
        int ko = num(aMap.get("Konstitution")[1]);
        int mod = eisern ? 2 : 0;
        int ws = Math.round((float) ko / 2) + mod;
        p.setWS(ws);
    }
    
}
