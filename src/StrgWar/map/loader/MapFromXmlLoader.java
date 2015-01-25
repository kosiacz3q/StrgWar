package StrgWar.map.loader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapFromXmlLoader implements IMapLoader
{

	public MapFromXmlLoader(String fileName) throws SAXException, IOException, ParserConfigurationException
	{
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		_xmlDoc = dBuilder.parse(fXmlFile);
	}
	
	@Override
	public boolean HasNext()
	{
		NodeList elements = _xmlDoc.getElementsByTagName("node");

		if(elements.getLength() > currentNode)
			return true;
		else
			return false;
	}

	@Override
	public RawNode GetNext()
	{
		NodeList elements = _xmlDoc.getElementsByTagName("node");
		
		for (int temp = 0; temp < elements.getLength(); temp++) {
			Node element = elements.item(temp);

			if (element.getNodeType() == Node.ELEMENT_NODE && currentNode == temp) {
				Element e = (Element) element;
				String name = e.getAttribute("name");
				String occupant = e.getAttribute("occupant");
				int startSize = Integer.parseInt(e.getAttribute("startSize"));
				int unitsPerSecond = Integer.parseInt(e.getAttribute("unitsPerSecond"));
				int x = Integer.parseInt(e.getAttribute("x"));
				int y = Integer.parseInt(e.getAttribute("y"));
				int r = Integer.parseInt(e.getAttribute("r"));
				
				currentNode++;

				return new RawNode(name, occupant, startSize, unitsPerSecond, x, y, r);
			}
		}
		
		return null;
	}
	
	Document _xmlDoc;
	static int currentNode = 0;
}
