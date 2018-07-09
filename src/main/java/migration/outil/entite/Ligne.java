package migration.outil.entite;

public class Ligne {
	private String aMigree;
	private String fichierProvenance;

	public Ligne(String aMigree, String fichierProvenance) {
		super();
		this.aMigree = aMigree;
		this.fichierProvenance = fichierProvenance;

	}

	@Override
	public String toString() {
		return "Ligne [aMigree=" + aMigree + ", fichierProvenance=" + fichierProvenance + "]";
	}

	public String getFichierProvenance() {
		return fichierProvenance;
	}

	public void setFichierProvenance(String fichierProvenance) {
		this.fichierProvenance = fichierProvenance;
	}

	public String getaMigree() {
		return aMigree;
	}

	public void setaMigree(String aMigree) {
		this.aMigree = aMigree;
	}

}