package date;

public class TratamenteMedicamente {
	private int idTratament, idMedicament, perioada, cantitate, intervalOrar;
	
	public TratamenteMedicamente(int idTratament, int idMedicament, int perioada, int cantitate, int intervalOrar) {
		setIdTratament(idTratament);
		setIdMedicament(idMedicament);
		setPerioada(perioada);
		setCantitate(cantitate);
		setIntervalOrar(intervalOrar);
	}

	public TratamenteMedicamente(int perioada, int cantitate, int intervalOrar) {
		setPerioada(perioada);
		setCantitate(cantitate);
		setIntervalOrar(intervalOrar);
	}

	public int getIdTratament() {
		return idTratament;
	}

	public void setIdTratament(int idTratament) {
		this.idTratament = idTratament;
	}

	public int getIdMedicament() {
		return idMedicament;
	}

	public void setIdMedicament(int idMedicament) {
		this.idMedicament = idMedicament;
	}

	public int getPerioada() {
		return perioada;
	}

	public void setPerioada(int perioada) {
		this.perioada = perioada;
	}

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	public int getIntervalOrar() {
		return intervalOrar;
	}

	public void setIntervalOrar(int intervalOrar) {
		this.intervalOrar = intervalOrar;
	}
	
}
