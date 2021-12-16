//AQUEST ES EL NOSTRE PROGRAMA PER A GENERAR ELS XML A PARTIR DE LES DADES DE LA BD DEL NOSTRE SERVIDOR
package crear.llegir.xml;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//AQUESTA SERÀ LA CLASSE QUE SEXECUTARÀ

public class CrearLlegirXML{
    public static void main(String[] args) throws TransformerConfigurationException, ParserConfigurationException, TransformerException, SQLException {

    	//LI DEFINIM COM ES DIRA EL NOM DE L'ARXIU
    	
    	String nomArxiuClients = "clients";
    	String nomArxiuTreballadors = "treballadors";
    	String nomArxiuPaquets = "paquets";
    	String nomArxiuEntrega = "entrega";
    	
    	
    	//CRIDEM ELS MÉTODES QUE ENS CREARAN ELS XML
    	crearXMLClients(nomArxiuClients, null);
    	crearXMLTreballadors(nomArxiuTreballadors, null);
    	crearXMLPaquets(nomArxiuPaquets, null);
    	crearXMLEntrega(nomArxiuEntrega, null);
    }
    
    //AQUI CREAREM EL DE CLIENTS
    public static Document crearXMLClients(String nomArxiuClients, List<Client> llistClients) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SQLException {
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DOMSource domSource = null;

		
		//DECLAREM LES VARIABLES QUE UTILITZAREM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element results = doc.createElement("table");
		doc.appendChild(results);
        
        try {
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArxiuClients, null);
            document.setXmlVersion("1.0");
            
            //CREEM LA CONNEXIO A LA BD DEL SERVIDOR
            try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://192.168.1.49:5432/odoo", "oriadix", "Fat/3232");
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
            
            //FEM UN SELECT DE LES DADES QUE VOLEM OBTENIR
            pstmt = con.prepareStatement("SELECT * FROM client");
            
            rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			int l = 0;
			
			//FORMATEM EL NOSTRE XML
			while (rs.next()) {
				Element row = doc.createElement("client" + (++l));
				results.appendChild(row);
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(i); //AIXO
					Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode((value != null) ? value.toString() : ""));
					row.appendChild(node);
				}
			}

			
			//L'OMPLIM AMB LES DADES
			domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
	

		} catch (SQLException sqlExp) {

			System.out.println("SQLExcp:" + sqlExp.toString());

		} finally {
			try {

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
				
			} catch (SQLException expSQL) {
				System.out.println("CourtroomDAO::loadCourtList:SQLExcp:CLOSING:" + expSQL.toString());
			}
		}
        
        //GENEREM EL XML
        Source source = new DOMSource(doc);
        
        //GUARDEM EL XML AL SERVIDOR 
        //Result result = new StreamResult(new java.io.File("\\\\192.168.1.49\\samba-compartir\\xml\\" + nomArxiuClients + ".xml"));
        Result result = new StreamResult(new java.io.File("\\\\192.168.1.49\\xml\\" + nomArxiuClients + ".xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
        System.out.println("XML de clients creat correctament!");
		return doc;            
    }
    
    //AQUI CREAREM EL DE TREBALLADORS
    public static Document crearXMLTreballadors(String nomArxiuTreballadors, List<Treballadors> llistTreballador) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SQLException {
        
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DOMSource domSource = null;

		//DECLAREM LES VARIABLES QUE UTILITZAREM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element results = doc.createElement("table");
		doc.appendChild(results);
        
        try {
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArxiuTreballadors, null);
            document.setXmlVersion("1.0");
            
            //CREEM LA CONNEXIO A LA BD DEL SERVIDOR
            try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://192.168.1.49:5432/odoo", "oriadix", "Fat/3232");
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
            
            //FEM UN SELECT DE LES DADES QUE VOLEM OBTENIR
            pstmt = con.prepareStatement("SELECT * FROM treballadors");
            
            rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			
			//FORMATEM EL NOSTRE XML
			while (rs.next()) {
				Element row = doc.createElement("treballador");
				results.appendChild(row);
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(i); //AIXO
					Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode((value != null) ? value.toString() : ""));
					row.appendChild(node);
				}
			}

			//L'OMPLIM AMB LES DADES
			domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
	

		} catch (SQLException sqlExp) {

			System.out.println("SQLExcp:" + sqlExp.toString());

		} finally {
			try {

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
				
				
			} catch (SQLException expSQL) {
				System.out.println("CourtroomDAO::loadCourtList:SQLExcp:CLOSING:" + expSQL.toString());
			}
		}
        
        //GENEREM EL XML
        Source source = new DOMSource(doc);
        
        //GUARDEM EL XML AL SERVIDOR 
        Result result = new StreamResult(new java.io.File("\\\\192.168.1.49\\xml\\" + nomArxiuTreballadors + ".xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
		System.out.println("XML de Treballadors creat correctament!");
		return doc;
    }
    
    //AQUI CREAREM EL DE PAQUETS
    public static Document crearXMLPaquets(String nomArxiuPaquets, List<Paquet> llistPaquets) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SQLException {
        
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DOMSource domSource = null;

		//DECLAREM LES VARIABLES QUE UTILITZAREM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element results = doc.createElement("table");
		doc.appendChild(results);
        
        try {
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArxiuPaquets, null);
            document.setXmlVersion("1.0");
            
            //CREEM LA CONNEXIO A LA BD DEL SERVIDOR
            try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://192.168.1.49:5432/odoo", "oriadix", "Fat/3232");
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
            
            //FEM UN SELECT DE LES DADES QUE VOLEM OBTENIR
            pstmt = con.prepareStatement("SELECT * FROM paquet");
            
            rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			//FORMATEM EL NOSTRE XML
			while (rs.next()) {
				Element row = doc.createElement("paquet");
				results.appendChild(row);
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(i); //AIXO
					Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode((value != null) ? value.toString() : ""));
					row.appendChild(node);
				}
			}

			//L'OMPLIM AMB LES DADES
			domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
	

		} catch (SQLException sqlExp) {

			System.out.println("SQLExcp:" + sqlExp.toString());

		} finally {
			try {

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
				
			} catch (SQLException expSQL) {
				System.out.println("CourtroomDAO::loadCourtList:SQLExcp:CLOSING:" + expSQL.toString());
			}
		}
        
        //GENEREM EL XML
        Source source = new DOMSource(doc);
        
        //GUARDEM EL XML AL SERVIDOR 
        Result result = new StreamResult(new java.io.File("\\\\192.168.1.49\\xml\\" + nomArxiuPaquets + ".xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
		System.out.println("XML de Paquet creat correctament!");
		return doc;
    }
    
    //AQUI CREAREM EL DE ENTREGA
    public static Document crearXMLEntrega(String nomArxiuEntrega, List<Entrega> llistEntrega) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SQLException {
        
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DOMSource domSource = null;

		//DECLAREM LES VARIABLES QUE UTILITZAREM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element results = doc.createElement("table");
		doc.appendChild(results);
        
        try {
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArxiuEntrega, null);
            document.setXmlVersion("1.0");
            
            //CREEM LA CONNEXIO A LA BD DEL SERVIDOR
            try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://192.168.1.49:5432/odoo", "oriadix", "Fat/3232");
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
            
            //FEM UN SELECT DE LES DADES QUE VOLEM OBTENIR
            pstmt = con.prepareStatement("SELECT * FROM entrega");
            
            rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			//FORMATEM EL NOSTRE XML
			while (rs.next()) {
				Element row = doc.createElement("entrega");
				results.appendChild(row);
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(i); //AIXO
					Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode((value != null) ? value.toString() : ""));
					row.appendChild(node);
				}
			}

			//L'OMPLIM AMB LES DADES
			domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
	

		} catch (SQLException sqlExp) {

			System.out.println("SQLExcp:" + sqlExp.toString());

		} finally {
			try {

				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
				
				
			} catch (SQLException expSQL) {
				System.out.println("CourtroomDAO::loadCourtList:SQLExcp:CLOSING:" + expSQL.toString());
			}
		}

		// return sw.toString();
        
        //GENEREM EL XML
        Source source = new DOMSource(doc);
        
        //GUARDEM EL XML AL SERVIDOR 
        Result result = new StreamResult(new java.io.File("\\\\192.168.1.49\\xml\\" + nomArxiuEntrega + ".xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
		System.out.println("XML d'Entrega creat correctament!");
		return doc;
    }
}






