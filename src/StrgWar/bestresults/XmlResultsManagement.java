package StrgWar.bestresults;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlResultsManagement {
	public XmlResultsManagement() throws SAXException, IOException,
			ParserConfigurationException {
		_filePath = GetResultsFilePath();

		File fXmlFile = new File(_filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		_xmlDoc = dBuilder.parse(fXmlFile);
	}

	public List<Result> GetResults() {
		ArrayList<Result> list = new ArrayList<Result>();

		Node _actNode = _xmlDoc.getDocumentElement().getFirstChild();

		do {
			_actNode = _actNode.getNextSibling();
		} while (_actNode != null && !(_actNode instanceof Element));

		while (_actNode != null) {
			Result r = new Result();
			r.name = _actNode.getAttributes().getNamedItem("name")
					.getNodeValue();
			r.gamesPlayed = _actNode.getAttributes()
					.getNamedItem("gamesPlayed").getNodeValue();
			r.gamesWon = _actNode.getAttributes().getNamedItem("gamesWon")
					.getNodeValue();
			list.add(r);

			do {
				_actNode = _actNode.getNextSibling();
			} while (_actNode != null && !(_actNode instanceof Element));
		}

		return list;
	}

	public void ResetResults() throws SAXException, IOException,
			ParserConfigurationException, TransformerException {
		Node _actNode = _xmlDoc.getDocumentElement().getFirstChild();

		do {
			_actNode = _actNode.getNextSibling();
		} while (_actNode != null && !(_actNode instanceof Element));

		while (_actNode != null) {
			Node nodeAttr = _actNode.getAttributes()
					.getNamedItem("gamesPlayed");
			nodeAttr.setTextContent("0");

			nodeAttr = _actNode.getAttributes().getNamedItem("gamesWon");
			nodeAttr.setTextContent("0");

			do {
				_actNode = _actNode.getNextSibling();
			} while (_actNode != null && !(_actNode instanceof Element));
		}

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(_xmlDoc);
		StreamResult result = new StreamResult(new File(_filePath));
		transformer.transform(source, result);
	}

	public void SetGameResults(String winner, String loser)
			throws SAXException, IOException, ParserConfigurationException,
			TransformerException {
		Node _actNode = _xmlDoc.getDocumentElement().getFirstChild();

		do {
			_actNode = _actNode.getNextSibling();
		} while (_actNode != null && !(_actNode instanceof Element));

		while (_actNode != null) {
			if (_actNode.getAttributes().getNamedItem("name").getNodeValue()
					.compareTo(winner) == 0) {
				Node nodeAttr = _actNode.getAttributes().getNamedItem(
						"gamesPlayed");
				int gamesPlayed = Integer.parseInt(nodeAttr.getNodeValue());
				gamesPlayed += 1;
				nodeAttr.setTextContent(Integer.toString(gamesPlayed));

				nodeAttr = _actNode.getAttributes().getNamedItem("gamesWon");
				int gamesWon = Integer.parseInt(nodeAttr.getNodeValue());
				gamesWon += 1;
				nodeAttr.setTextContent(Integer.toString(gamesWon));
			} else if (_actNode.getAttributes().getNamedItem("name").getNodeValue()
					.compareTo(loser) == 0) {
				Node nodeAttr = _actNode.getAttributes().getNamedItem(
						"gamesPlayed");
				int gamesPlayed = Integer.parseInt(nodeAttr.getNodeValue());
				gamesPlayed += 1;
				nodeAttr.setTextContent(Integer.toString(gamesPlayed));
			}

			do {
				_actNode = _actNode.getNextSibling();
			} while (_actNode != null && !(_actNode instanceof Element));
		}

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(_xmlDoc);
		StreamResult result = new StreamResult(new File(_filePath));
		transformer.transform(source, result);
	}

	private String GetResultsFilePath() {
		File fXmlFile = new File("ERROR");

		try {
			String rootDir = new File(".").getCanonicalPath();
			File subDir = new File(rootDir, "/resources/results");
			fXmlFile = new File(subDir, "bestresults.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fXmlFile.getAbsolutePath();
	}

	Document _xmlDoc;
	String _filePath;
}
