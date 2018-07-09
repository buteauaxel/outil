package migration.outil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import migration.outil.entite.Ligne;
import migration.outil.entite.LigneResultat;
import migration.outil.utils.Lire;
import migration.outil.utils.JDBCUtils;


public class App {
	public static void main(String[] args) throws SQLException, RowsExceededException, WriteException {
		int i = 0;
		int j = 0;
		//int z = 0;

		WritableWorkbook myWbookSource = null;
		ArrayList<LigneResultat> rs = null;

		ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
		String shere = bundle.getString("shere");
		String sourceEditeur = bundle.getString("sgbdsource.editeur");
		String sourceHost = bundle.getString("sgbdsource.host");
		String sourceDatabase = bundle.getString("sgbdsource.database");
		String sourceUser = bundle.getString("sgbdsouce.user");
		String sourcePassword = bundle.getString("sgbdsource.password");

		File here = new File(shere);
		Lire test = new Lire();

		ArrayList<Ligne> t = new ArrayList<Ligne>();
		for (File content : here.listFiles()) {
			if (content.isFile() && content.getName().endsWith(".xls")) {

				t = test.listerLigne(shere + content.getName());
				if (t.size() != 0) {

					try {
						myWbookSource = Workbook
								.createWorkbook(new File(shere + "resultat\\" + "resultat_" + content.getName()));

						for (Ligne s : t) {

							WritableSheet eSheet = myWbookSource.createSheet(s.getaMigree(), i++);
							System.out.println(i);

							Label label = new Label(0, 0, "Valeur");
							eSheet.addCell(label);

							label = new Label(1, 0, "Nombre");
							eSheet.addCell(label);
							JDBCUtils connBase = new JDBCUtils();
							connBase.init(sourceEditeur, sourceHost, sourceDatabase, sourceUser, sourcePassword);
							connBase.ouvrirConnexion();

							String requete = "Select " + s.getaMigree() + " , count(1) as NOMBRE From "
									+ content.getName().substring(0, content.getName().length() - 4) + " Group by "
									+ s.getaMigree();
							System.out.println(requete);
							rs = connBase.resultatRequete(requete);
							connBase.fermerConnexion();

							for (LigneResultat r : rs) {

								j++;

								label = new Label(0, j, r.getValeur());
								eSheet.addCell(label);

								Number number = new Number(1, j, r.getNombres());
								eSheet.addCell(number);

							}

							j = 0;

						}
						myWbookSource.write();
						myWbookSource.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			t.clear();

		}

		for (Ligne c : t) {

			System.out.println(c.toString());

		}

	}
}
