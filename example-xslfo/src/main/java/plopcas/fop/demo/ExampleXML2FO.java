package plopcas.fop.demo;

import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

/**
 * This class demonstrates the conversion of an XML file to FO
 */
public class ExampleXML2FO {

    private static Document document;

    /**
     * Main method.
     * 
     * @param args
     *            command-line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	// Setup directories
	File baseDir = new File(".");
	File outDir = new File(baseDir, "out");
	outDir.mkdirs();

	// Setup input and output files
	File xmlfile = new File(baseDir, "in/in.xml");
	File xsltfile = new File(baseDir, "template/xml2fo.xsl");
	File fofile = new File(outDir, "result.fo");

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	DocumentBuilder builder = factory.newDocumentBuilder();
	document = builder.parse(xmlfile);

	// Use a Transformer for output
	TransformerFactory tFactory = TransformerFactory.newInstance();
	StreamSource stylesource = new StreamSource(xsltfile);
	Transformer transformer = tFactory.newTransformer(stylesource);

	FileWriter writer = new FileWriter(fofile);

	DOMSource source = new DOMSource(document);
	StreamResult result = new StreamResult(writer);
	transformer.transform(source, result);

	writer.close();
    }
}
