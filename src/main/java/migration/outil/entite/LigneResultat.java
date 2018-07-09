package migration.outil.entite;

public class LigneResultat {
	private String valeur;
	private int nombres;

	public LigneResultat(String valeur, int nombres) {
		super();
		this.valeur = valeur;
		this.nombres = nombres;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public int getNombres() {
		return nombres;
	}

	public void setNombres(int nombres) {
		this.nombres = nombres;
	}

}
