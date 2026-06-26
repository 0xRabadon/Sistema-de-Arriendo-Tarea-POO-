package cl.uach.info090.OvalleLuis;

/**
 * Interfaz con los metodos para administrar los items (arrendarlo, devolver o saber si esta arrendado)
 * @author Luis Ovalle
 */

public interface Arrendable {
	public void arrendar(String cliente);
	public Boleta devolver();
	public boolean enArriendo();
}
