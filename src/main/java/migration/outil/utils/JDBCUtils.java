package migration.outil.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import migration.outil.entite.LigneResultat;
import oracle.jdbc.driver.OracleConnection;

public class JDBCUtils {
	private Connection conn = null;
	private ResultSet rs = null;
	private Statement stmt = null;

	private final static String driverSQLServer = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final static String driverOracle = "oracle.jdbc.driver.OracleDriver";

	private String editeur;
	private String host;
	private String database;
	private String user;
	private String password;

	private boolean verbose = false;

	/**
	 * Fonction qui initie les parametre de connexion
	 * 
	 * @param editeur
	 * @param host
	 * @param database
	 * @param user
	 * @param password
	 */

	public void init(String editeur, String host, String database, String user, String password) {
		this.editeur = editeur;
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	private void logger(String log) {
		if (verbose) {
			System.out.println(log);
		}
	}

	/**
	 * Fonction qui ouvre une connexion
	 * 
	 * @return
	 */
	public boolean ouvrirConnexion() {
		boolean ret = true;
		String connectionString = "";

		try {
			if ("SqlServer".equals(editeur)) {

				Class.forName(driverSQLServer);
				connectionString = "jdbc:sqlserver://" + host + ";database=" + database;

			} else if ("Oracle".equals(editeur)) {
				Class.forName(driverOracle);
				connectionString = "jdbc:oracle:thin:@" + host + ":1521:" + database;
			}

			System.out.println("Connexion : '" + connectionString + "'");
			conn = DriverManager.getConnection(connectionString, user, password);

			// permet l'affichage des commentaires

			((OracleConnection) conn).setRemarksReporting(true);

		} catch (Exception e) {
			ret = false;
			System.out.println("Ouverture connexion impossible");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return ret;
	}

	// Permet de recupérer la liste des nom des table contenue dans une base
	/**
	 * Fonction qui retourne une liste de nom de table contenue dans la bdd
	 * 
	 * @param schema
	 *            schema de la bdd
	 * @return liste de nom de table
	 */
	public ArrayList<LigneResultat> resultatRequete(String requete) {

		int i = 0;

		ArrayList<LigneResultat> resultat = new ArrayList<LigneResultat>();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(requete);
			ResultSetMetaData metadata = rs.getMetaData();

			String nomColonne = (metadata.getColumnName(1));

			while (rs.next()) {
				i++;
				LigneResultat e = new LigneResultat(rs.getString(nomColonne), rs.getInt(2));
				resultat.add(e);
				if (i > 10000) {

					break;
				}

			}

			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultat;

	}

	/**
	 * fonction qui ferme une connexion
	 * 
	 */
	public void fermerConnexion() {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println("probleme rs.close");
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("probleme Connexion close");
				e.printStackTrace();
			}
		}
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
}
