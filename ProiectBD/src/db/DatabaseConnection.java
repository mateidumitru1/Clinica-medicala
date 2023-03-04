package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import date.Medicamente;
import date.Medici;
import date.Pacienti;
import date.RapoarteMedicale;
import date.Tratamente;
import date.TratamenteMedicamente;

public class DatabaseConnection {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public DatabaseConnection() throws ClassNotFoundException {
		String url = "jdbc:sqlserver://localhost\\SQLEXPRESS01;IntegratedSecurity=true;Database=ProiectDB;encrypt=false;";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		try {
			con = DriverManager.getConnection(url);
		}
		catch (SQLException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Pacienti> selectPacienti() throws SQLException {
		
		String select = "SELECT * FROM Pacienti";
		stmt = con.createStatement();
		rs = stmt.executeQuery(select);
		ArrayList<Pacienti> p = new ArrayList<Pacienti>();
		
		while(rs.next()) {
			int ID = rs.getInt(1);
			String nume = rs.getString(2);
			String prenume = rs.getString(3);
			String cnp = rs.getString(4);
			String sex = rs.getString(5);
			int varsta = rs.getInt(6);
			int greutate = rs.getInt(7);
			String oras = rs.getString(8);
			String strada = rs.getString(9);
			int numar = rs.getInt(10);
			p.add(new Pacienti(ID, nume, prenume, cnp, sex, varsta, greutate, oras, strada, numar));
		}
		return p;
	}
	
	public ArrayList<Medici> selectMedici() throws SQLException {
		String select = "SELECT * FROM Medici";
		stmt = con.createStatement();
		rs = stmt.executeQuery(select);
		ArrayList<Medici> m = new ArrayList<Medici>();
		while(rs.next()) {
			int ID = rs.getInt(1);
			String nume = rs.getString(2);
			String prenume = rs.getString(3);
			String cnp = rs.getString(4);
			String specializare = rs.getString(5);
			m.add(new Medici(ID, nume, prenume, cnp, specializare));
		}
		return m;
	}

	public ArrayList<Medicamente> selectMedicamente() throws SQLException {
		String select = "SELECT * FROM Medicamente";
		stmt = con.createStatement();
		rs = stmt.executeQuery(select);
		ArrayList<Medicamente> m = new ArrayList<Medicamente>();
		while(rs.next()) {
			int ID = rs.getInt(1);
			String denumire = rs.getString(2);
			String substantaActiva = rs.getString(3);
			m.add(new Medicamente(ID, denumire, substantaActiva));
		}
		return m;
	}

	public ArrayList<RapoarteMedicale> selectRapoarteMedicale() throws SQLException {
		String select = "SELECT * FROM RapoarteMedicale";
		stmt = con.createStatement();
		rs = stmt.executeQuery(select);
		ArrayList<RapoarteMedicale> rm = new ArrayList<RapoarteMedicale>();
		
		while(rs.next()) {
			int ID = rs.getInt(1);
			int IDPacient = rs.getInt(2);
			int IDMedic = rs.getInt(3);
			int IDTratament = rs.getInt(4);
			String data = rs.getString(5);
			String obs = rs.getString(6);
			rm.add(new RapoarteMedicale(ID, IDPacient, IDMedic, IDTratament, data, obs));
		}
		return rm;
	}

	public ArrayList<Tratamente> selectTratamente() throws SQLException {
		
		String select = "SELECT * FROM Tratamente";
		stmt = con.createStatement();
		rs = stmt.executeQuery(select);
		ArrayList<Tratamente> t = new ArrayList<Tratamente>();
		
		while(rs.next()) {
				int ID = rs.getInt(1);
				String afectiune = rs.getString(2);
				int IDCategorie = rs.getInt(3);
				t.add(new Tratamente(ID, afectiune, IDCategorie));
		}
		
		return t;
	}
	
	public String getDenumireCategorie(int id) throws SQLException {
		
		String query = "SELECT Denumire FROM Categorie WHERE IDCategorie = " + id;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		rs.next();
		return rs.getString(1);
	}
	
	public int insertPacient(Pacienti p) throws SQLException, ClassNotFoundException {
		
		String s;
		if(p.getNumar() == -1) s = "INSERT INTO Pacienti(Nume, Prenume, CNP, Sex, Varsta, Greautate, Oras, Strada) VALUES('" + p.getNume() + "','" + p.getPrenume() + "','" + 
				p.getCnp() + "','" + p.getSex() + "','" + p.getVarsta() + "','" + p.getGreutate() + "','" + p.getOras() + "','" + p.getStrada() + "')";
		else s = "INSERT INTO Pacienti(Nume, Prenume, CNP, Sex, Varsta, Greautate, Oras, Strada, Numar) VALUES('" + p.getNume() + "','" + p.getPrenume() + "','" + 
				p.getCnp() + "','" + p.getSex() + "','" + p.getVarsta() + "','" + p.getGreutate() + "','" + p.getOras() + "','" + p.getStrada() + "','" + p.getNumar() + "')";
		stmt = con.createStatement();
		return stmt.executeUpdate(s);
	}
	

	public void deletePacient(Object id) throws SQLException {
		
		String query = "DELETE FROM Pacienti WHERE IDPacient = " + id;
		stmt = con.createStatement();
		stmt.execute(query);
	}

	public ArrayList<Pacienti> searchPacient(String search) throws SQLException {
			
		String query = "SELECT * FROM Pacienti WHERE Nume LIKE '" + search + "'";
		stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(query);
		ArrayList<Pacienti> p = new ArrayList<Pacienti>();
		
		while(r.next()) {
			int ID = r.getInt(1);
			String nume = r.getString(2);
			String prenume = r.getString(3);
			String cnp = r.getString(4);
			String sex = r.getString(5);
			int varsta = r.getInt(6);
			int greutate = r.getInt(7);
			String oras = r.getString(8);
			String strada = r.getString(9);
			int numar = r.getInt(10);
			p.add(new Pacienti(ID, nume, prenume, cnp, sex, varsta, greutate, oras, strada, numar));
		}
		return p;
	}

	public void editPacient(Pacienti pacient) throws SQLException {
		
		String query = "UPDATE Pacienti SET Nume = '" + pacient.getNume() + "', Prenume = '" + pacient.getPrenume() + "', CNP = '" + pacient.getCnp() + "', Sex = '" +
				pacient.getSex() + "', Varsta = " + pacient.getVarsta() + ", Greautate = " + pacient.getGreutate() + ", Oras = '" + pacient.getOras() + "', Strada = '" + 
				pacient.getStrada() + "', Numar = " + pacient.getNumar() + " WHERE IDPacient = " + pacient.getId();
		stmt = con.createStatement();
		stmt.execute(query);
	}

	public int insertMedic(Medici medic) throws SQLException {
		
		String s = "INSERT INTO Medici(Nume, Prenume, CNP, Specializare) VALUES('" + medic.getNume() + "', '" + medic.getPrenume() + "', '" + medic.getCnp() + "', '" + 
				medic.getSpecializare() + "')";
		stmt = con.createStatement();
		return stmt.executeUpdate(s);
	}

	public void deleteMedic(Object id) throws SQLException {
		
		String query = "DELETE FROM Medici WHERE IDMedic = " + id;
		stmt = con.createStatement();
		stmt.execute(query);
	}

	public ArrayList<Medici> searchMedic(String search) throws SQLException {
		
		String query = "SELECT * FROM Medici WHERE Nume LIKE '" + search + "'";
		stmt = con.createStatement();
		ResultSet r = stmt.executeQuery(query);
		ArrayList<Medici> m = new ArrayList<Medici>();
		
		while(r.next()) {
			int ID = r.getInt(1);
			String nume = r.getString(2);
			String prenume = r.getString(3);
			String cnp = r.getString(4);
			String specializare = r.getString(5);
			m.add(new Medici(ID, nume, prenume, cnp, specializare));
		}
		return m;
	}

	public void editMedic(Medici medic) throws SQLException {
		
		String query = "UPDATE Medici SET Nume = '" + medic.getNume() + "', Prenume = '" + medic.getPrenume() + "', CNP = '" + medic.getCnp() + "', Specializare = '" +
				medic.getSpecializare() + "' WHERE IDMedic = " + medic.getId();
		stmt = con.createStatement();
		stmt.execute(query);
	}

	public ArrayList<Object> getMedicamente(int id) throws SQLException {
		
		ArrayList<Object> m = new ArrayList<Object>();
		String query = "SELECT m.IDMedicament, m.Denumire, m.SubstantaActiva, tm.Perioada, tm.Cantitate, tm.IntervalOrar FROM Medicamente m join TratamenteMedicamente tm"
				+ " on m.IDMedicament = tm.IDMedicament WHERE tm.IDTratament = " + id;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			m.add(new Medicamente(rs.getInt(1), rs.getString(2), rs.getString(3)));
			m.add(new TratamenteMedicamente(rs.getInt(4), rs.getInt(5), rs.getInt(6)));
		}
		return m;
	}

	public ArrayList<Medici> getMedici(int idPacient) throws SQLException {
		
		ArrayList<Medici> m = new ArrayList<Medici>();
		String query = "SELECT m.IDMedic, m.Nume, m.Prenume, m.Specializare FROM RapoarteMedicale rm join Medici m on rm.IDMedic = m.IDMedic WHERE rm.IDPacient = " + idPacient;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			m.add(new Medici(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		
		return m;
	}

	public ArrayList<String> getTratamentebyPacient(int idPacient) throws SQLException {
		
		ArrayList<String> s = new ArrayList<String>();
		String query = "select t.Afectiune, c.Denumire, rm.Data, rm.Observatii, t.IDTratament from RapoarteMedicale rm join Tratamente t on rm.IDTratament = t.IDTratament"
				+ " join Categorie c on t.IDCategorie = c.IDCategorie where rm.IDPacient = " + idPacient;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			s.add(rs.getString(1));
			s.add(rs.getString(2));
			s.add(rs.getString(3));
			s.add(rs.getString(4));
			s.add(String.valueOf(rs.getInt(5)));
		}
		return s;
	}
	
	public ArrayList<Object> getMedicamente(int idPacient, int idMedic, int idTratament) throws SQLException {
		
		ArrayList<Object> m = new ArrayList<Object>();
		String query = "select m.IDMedicament, m.Denumire, m.SubstantaActiva, tm.Perioada, tm.Cantitate, tm.IntervalOrar\r\n"
				+ "from Medicamente m join TratamenteMedicamente tm on m.IDMedicament = tm.IDMedicament join\r\n"
				+ "Tratamente t on tm.IDTratament = t.IDTratament join RapoarteMedicale rm on t.IDTratament = rm.IDTratament\r\n"
				+ "WHERE rm.IDPacient = " + idPacient + " and rm.IDMedic = " + idMedic + " and t.IDTratament = " + idTratament;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			m.add(new Medicamente(rs.getInt(1), rs.getString(2), rs.getString(3)));
			m.add(new TratamenteMedicamente(rs.getInt(4), rs.getInt(5), rs.getInt(6)));
		}
		return m;
	}

	public ArrayList<Pacienti> getPacienti(int idMedic) throws SQLException {
		ArrayList<Pacienti> p = new ArrayList<Pacienti>();
		String query = "SELECT p.IDPacient, p.Nume, p.Prenume FROM RapoarteMedicale rm join Pacienti p on rm.IDPacient = p.IDPacient WHERE rm.IDMedic = " + idMedic;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			p.add(new Pacienti(rs.getInt(1), rs.getString(2), rs.getString(3)));
		}
		
		return p;
	}

	public ArrayList<String> getTratamentebyMedic(int idMedic) throws SQLException {
		ArrayList<String> s = new ArrayList<String>();
		String query = "select t.Afectiune, c.Denumire, rm.Data, rm.Observatii, t.IDTratament"
				+ " from RapoarteMedicale rm join Tratamente t on rm.IDTratament = t.IDTratament"
				+ " join Categorie c on t.IDCategorie = c.IDCategorie where rm.IDMedic = " + idMedic;
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			s.add(rs.getString(1));
			s.add(rs.getString(2));
			s.add(rs.getString(3));
			s.add(rs.getString(4));
			s.add(String.valueOf(rs.getInt(5)));
		}
		return s;
	}

	public ArrayList<Medici> getTopMediciBySpecializare(String specializare) throws SQLException {

		ArrayList<Medici> m = new ArrayList<Medici>();
		
		String query = "SELECT c, m.Nume, m.Prenume\r\n"
				+ "FROM Medici m\r\n"
				+ "JOIN (SELECT r.IDMedic, COUNT(*) as c\r\n"
				+ "      FROM RapoarteMedicale r\r\n"
				+ "	  WHERE r.IDMedic in (SELECT m2.IDMedic FROM Medici m2 WHERE m2.Specializare = '" + specializare + "')\r\n"
				+ "      GROUP BY r.IDMedic\r\n"
				+ "      HAVING COUNT(*) = (SELECT MAX(c) FROM (SELECT COUNT(*) as c FROM RapoarteMedicale r\r\n"
				+ "	  WHERE r.IDMedic in (SELECT m2.IDMedic FROM Medici m2 WHERE m2.Specializare = '" + specializare + "')\r\n"
				+ "	  GROUP BY r.IDMedic) as subquery)) r\r\n"
				+ "ON m.IDMedic = r.IDMedic";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			m.add(new Medici(rs.getInt(1), rs.getString(2), rs.getString(3)));
		}
		return m;
	}

	public ArrayList<String> getSpecializari() throws SQLException {
		
		ArrayList<String> s = new ArrayList<String>();
		stmt = con.createStatement();
		rs = stmt.executeQuery("select distinct Specializare from Medici");
		while(rs.next()) {
			s.add(rs.getString(1));
		}
		return s;
	}

	public ArrayList<String> getOrase() throws SQLException {
		
		ArrayList<String> s = new ArrayList<String>();
		stmt = con.createStatement();
		rs = stmt.executeQuery("select distinct Oras from Pacienti");
		while(rs.next()) {
			s.add(rs.getString(1));
		}
		return s;
	}

	public ArrayList<Pacienti> getTopPacientiByOras(String oras) throws SQLException {
		
		ArrayList<Pacienti> p = new ArrayList<Pacienti>();
		
		String query = "SELECT c, p.Nume, p.Prenume\r\n"
				+ "FROM Pacienti p\r\n"
				+ "JOIN (SELECT r.IDPacient, COUNT(*) as c\r\n"
				+ "      FROM RapoarteMedicale r\r\n"
				+ "	  WHERE r.IDPacient in (SELECT p2.IDPacient FROM Pacienti p2 WHERE p2.Oras = '" + oras + "')\r\n"
				+ "      GROUP BY r.IDPacient\r\n"
				+ "      HAVING COUNT(*) = (SELECT MAX(c) FROM (SELECT COUNT(*) as c FROM RapoarteMedicale r\r\n"
				+ "	  WHERE r.IDPacient in (SELECT p2.IDPacient FROM Pacienti p2 WHERE p2.Oras = '" + oras + "')\r\n"
				+ "	  GROUP BY r.IDPacient) as subquery)) r\r\n"
				+ "ON p.IDPacient = r.IDPacient";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			p.add(new Pacienti(rs.getInt(1), rs.getString(2), rs.getString(3)));
		}
		
		return p;
	}

	public ArrayList<Pacienti> getTopPacienti() throws SQLException {
		
		
		ArrayList<Pacienti> p = new ArrayList<Pacienti>();
		
		String query = "SELECT c, p.Nume, p.Prenume, p.Oras\r\n"
				+ "FROM Pacienti p\r\n"
				+ "JOIN (SELECT r.IDPacient, COUNT(*) as c\r\n"
				+ "      FROM RapoarteMedicale r\r\n"
				+ "      GROUP BY r.IDPacient\r\n"
				+ "      HAVING COUNT(*) = (SELECT MAX(c) FROM (SELECT COUNT(*) as c FROM RapoarteMedicale r\r\n"
				+ "	  GROUP BY r.IDPacient) as subquery)) r\r\n"
				+ "ON p.IDPacient = r.IDPacient";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			p.add(new Pacienti(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		
		return p;
	}

	public ArrayList<Medici> getTopMedici() throws SQLException {
		
		ArrayList<Medici> m = new ArrayList<Medici>();
		
		String query = "SELECT c, m.Nume, m.Prenume, m.Specializare\r\n"
				+ "FROM Medici m\r\n"
				+ "JOIN (SELECT r.IDMedic, COUNT(*) as c\r\n"
				+ "      FROM RapoarteMedicale r\r\n"
				+ "      GROUP BY r.IDMedic\r\n"
				+ "      HAVING COUNT(*) = (SELECT MAX(c) FROM (SELECT COUNT(*) as c FROM RapoarteMedicale r\r\n"
				+ "	  GROUP BY r.IDMedic) as subquery)) r\r\n"
				+ "ON m.IDMedic = r.IDMedic";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			m.add(new Medici(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		return m;
	}

	public ArrayList<Medicamente> getTop3Medicamente() throws SQLException {
		
		ArrayList<Medicamente> m = new ArrayList<Medicamente>();
		
		String query = "SELECT TOP 3 m.Denumire, count(tm.IDMedicament) as Numar FROM Medicamente m join TratamenteMedicamente tm\r\n"
				+ "on m.IDMedicament = tm.IDMedicament\r\n"
				+ "GROUP BY m.Denumire\r\n"
				+ "ORDER BY count(tm.IDMedicament) desc";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			m.add(new Medicamente(rs.getInt(2), rs.getString(1)));
		}
		return m;	
	}

	public ArrayList<Medici> get0Medici() throws SQLException {
		
		ArrayList<Medici> m = new ArrayList<Medici>();
		
		String query = "SELECT DISTINCT m.Nume, m.Prenume, m.Specializare FROM Medici m join RapoarteMedicale rm\r\n"
				+ "on m.IDMedic not in (select distinct m1.IDMedic from Medici m1 join RapoarteMedicale rm1\r\n"
				+ "on m1.IDMedic = rm1.IDMedic)";
		
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			m.add(new Medici(rs.getString(1), rs.getString(2), rs.getString(3)));
		}
		return m;
	}
	
}
