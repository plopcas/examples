package plopcas.fop.demo;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 * This class demonstrates the conversion of an XML file to PDF using JAXP (XSLT) and FOP (XSL-FO).
 */
public class ExampleXML2PDF {

    /**
     * Main method.
     * 
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args) {
	try {
	    System.out.println("FOP ExampleXML2PDF\n");
	    System.out.println("Preparing...");

	    // Setup directories
	    File baseDir = new File(".");
	    File outDir = new File(baseDir, "out");
	    outDir.mkdirs();

	    // Setup input and output files
	    File xmlfile = new File(baseDir, "in/in.xml");
	    File xsltfile = new File(baseDir, "template/xml2fo.xsl");
	    File pdffile = new File(outDir, "result.pdf");

	    System.out.println("Input: XML (" + xmlfile + ")");
	    System.out.println("Stylesheet: " + xsltfile);
	    System.out.println("Output: PDF (" + pdffile + ")");
	    System.out.println();
	    System.out.println("Transforming...");

	    // configure fopFactory as desired
	    final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

	    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
	    // configure foUserAgent as desired

	    // Setup output
	    OutputStream out = new java.io.FileOutputStream(pdffile);
	    out = new java.io.BufferedOutputStream(out);

	    try {
		// Construct fop with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

		// Setup XSLT
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));

		// Set the value of a <param> in the stylesheet
		transformer.setParameter("versionParam", "2.0");

		// Setup input for XSLT transformation
		Source src = new StreamSource(xmlfile);

		// Resulting SAX events (the generated FO) must be piped through to FOP
		Result res = new SAXResult(fop.getDefaultHandler());

		// Start XSLT transformation and FOP processing
		transformer.transform(src, res);
	    } finally {
		out.close();
	    }

	    System.out.println("Success!");
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	    System.exit(-1);
	}
    }
}
