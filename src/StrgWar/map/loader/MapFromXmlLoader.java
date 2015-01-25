package StrgWar.map.loader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RawNode GetNext()
	{
		// TODO Auto-generated method stub
		return null;	
		
	}
	
	Document _xmlDoc;
	
}
