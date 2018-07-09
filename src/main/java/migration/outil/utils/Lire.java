package migration.outil.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import migration.outil.entite.Ligne;

public class Lire {
	ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
	String shere = bundle.getString("shere");

	ArrayList<Ligne> lignes = new ArrayList<Ligne>();

	public ArrayList<Ligne> listerLigne(String fichierLire) {

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(fichierLire));
			Sheet sheet = workbook.getSheet(0);
			int colLength = sheet.getRows();
			for (int i = 1; i < colLength - 1; i++) {
				if (sheet.getCell(3, i).getContents().equals("???")) {
					Ligne e = new Ligne(sheet.getCell(0, i).getContents(),
							fichierLire.substring(shere.length(), fichierLire.length() - 4));
					lignes.add(e);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return lignes;

	}

}
