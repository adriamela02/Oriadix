package crear.llegir.xml;

//CREEM LA CLASSE DE LA QUAL VOLEM CREAR EL XML AMB TOTES LES DADES QUE VOLEM AFEGIR I AMB ELS GETTERS I SETTERS

public class Client {
	private int id_client;
	private String dni;
	private String nom;
	private String cognom;
	private String correu;
	private int telefon;
	
	public Client(int id_client, String dni, String nom, String cognom, String correu, int telefon) {
		this.id_client = id_client;
		this.dni = dni;
		this.nom = nom;
		this.cognom = cognom;
		this.correu = correu;
		this.telefon = telefon;
	}
	
	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCognom() {
		return cognom;
	}
	public void setCognom(String cognom) {
		this.cognom = cognom;
	}
	public String getCorreu() {
		return correu;
	}
	public void setCorreu(String correu) {
		this.correu = correu;
	}
	public int getTelefon() {
		return telefon;
	}
	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}
}
