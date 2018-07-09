package migration.outil.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

public class CreationFichierResultat {
	WritableWorkbook myWbookSource = null;

	ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
	String shere = bundle.getString("shere");

	public void creerFichier(ArrayList<String> l) {

		for (String s : l) {

			File outFile = new File(s + ".xls");
			try {
				myWbookSource = Workbook.createWorkbook(new File(shere + "resultat\\" + s + ".xls"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
