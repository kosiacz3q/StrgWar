package StrgWar.map.loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
	public List<RawNode> GetNodes()
	{
		ArrayList<RawNode> list = new ArrayList<RawNode>();
		
		Node _actNode = _xmlDoc.getDocumentElement().getFirstChild();
		
		do
		{
			_actNode = _actNode.getNextSibling();
		}while(_actNode != null && !(_actNode instanceof Element));
		
		while(_actNode != null)
		{
			RawNode rn = new RawNode();
			rn.name = _actNode.getAttributes().getNamedItem("name").getNodeValue();
			rn.occupant = _actNode.getAttributes().getNamedItem("occupant").getNodeValue();
			rn.startSize = Integer.parseInt(_actNode.getAttributes().getNamedItem("startSize").getNodeValue());
			rn.unitsPerSecond = Integer.parseInt(_actNode.getAttributes().getNamedItem("unitsPerSecond").getNodeValue());
			rn.x = Integer.parseInt(_actNode.getAttributes().getNamedItem("x").getNodeValue());
			rn.y = Integer.parseInt(_actNode.getAttributes().getNamedItem("y").getNodeValue());
			rn.r = Integer.parseInt(_actNode.getAttributes().getNamedItem("r").getNodeValue());
			list.add(rn);
			
			do
			{
				_actNode = _actNode.getNextSibling();
			}while(_actNode != null && !(_actNode instanceof Element));
		}
		
		return list;
	}
	
	Document _xmlDoc;
	
}
