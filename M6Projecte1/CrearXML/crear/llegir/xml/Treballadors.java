package crear.llegir.xml;

//CREEM LA CLASSE DE LA QUAL VOLEM CREAR EL XML AMB TOTES LES DADES QUE VOLEM AFEGIR I AMB ELS GETTERS I SETTERS

public class Treballadors {
	private int id_treballador;
	private String dni;
	private String nom;
	private String cognom;
	private int telefon;
	private String correu;
	private String contrasenya;
	
	public Treballadors(int id_treballador, String dni, String nom, String cognom, int telefon, String correu, String contrasenya) {
		this.id_treballador = id_treballador;
		this.dni = dni;
		this.nom = nom;
		this.cognom = cognom;
		this.telefon = telefon;
		this.correu = correu;
		this.contrasenya = contrasenya;
	}

	public int getId_treballador() {
		return id_treballador;
	}

	public void setId_treballador(int id_treballador) {
		this.id_treballador = id_treballador;
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

	public int getTelefon() {
		return telefon;
	}

	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}

	public String getCorreu() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}	
}
