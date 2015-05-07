package Settings;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeaponClassCreator {

	public static void main(String[] args) {
		createXMLDocument("Weapons");

	}

	public static void readXMLDocument() {
		String path = "";
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			System.out.println(doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("staff");// the tags
			System.out.println("-----------------------");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				System.out.println("\ncurrent element" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					System.out.println("something" + eElement.getAttribute("id"));
					System.out.println("something" + eElement.getElementsByTagName("name"));
					System.out.println("something" + eElement.getElementsByTagName("stuff"));
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block -- newdocbuilder
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block -- parse
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block -- parse
			e.printStackTrace();
		}
	}

	public static void createXMLDocument(String fileName) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element mainRootElement = doc.createElementNS("My website or something", fileName);
			doc.appendChild(mainRootElement);

			version00(fileName, mainRootElement, doc);

			// output DOM XML to console
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			// StreamResult console = new StreamResult(System.out);
			StreamResult saveTheFile = new StreamResult(new File("C:/Users/Preston/Desktop/game/" + fileName + ".xml"));
			transformer.transform(source, saveTheFile);

			System.out.println("\nXML DOM Created Successfully..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void version00(String fileName, Element mre, Document doc) {
		int id = 0;
		switch (fileName) {
		case "Weapons":
			// append child elements to root element
			mre.appendChild(getWeapon(doc, id++, "Axe", 0.1, 0.5, 1.0));
			mre.appendChild(getWeapon(doc, id++, "Sword", 0.2, 1.0, 0.1));
			mre.appendChild(getWeapon(doc, id++, "Knife", 0.8, 0.8, 0.0));
			mre.appendChild(getWeapon(doc, id++, "Bow", 1.0, 0.0, 0.0));
			mre.appendChild(getWeapon(doc, id++, "CrossBow", 1.0, 0.0, 0.0));
		}
	}

	private static Node getWeapon(Document doc, int id, String name, double valP, double valS, double valB) {
		// might want this to be 'weapons' not sure though TODO
		Element weapon = doc.createElement(name);
		weapon.setAttribute("id", Integer.toString(id));
		weapon.appendChild(getWeaponStats(doc, weapon, Key.Epiercing, Double.toString(valP)));
		weapon.appendChild(getWeaponStats(doc, weapon, Key.Eslashing, Double.toString(valS)));
		weapon.appendChild(getWeaponStats(doc, weapon, Key.Eblugeoning, Double.toString(valB)));
		return weapon;
	}

	private static Node getWeaponStats(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}
