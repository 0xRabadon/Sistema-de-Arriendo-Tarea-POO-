package cl.uach.info090.OvalleLuis;

public interface Arrendable {
	public void arrendar(String cliente);
	public Boleta devolver();
	public boolean enArriendo();
}
