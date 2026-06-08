package cl.uach.info090.OvalleLuis;

import javax.swing.JButton;

public abstract class Item extends JButton implements Arrendable{
	private String itemId;
	private String itemDescription;
	private double valorHora;
	private double valorBase;
	private CreadorBoleta creadorBoleta;
	
	public Item(String itemId, String itemDescripcion, double valorHora, double valorBase, CreadorBoleta creadorBoleta) {
		this.itemId = itemId;
		this.itemDescription = itemDescripcion;
		this.valorHora = valorHora;
		this.valorBase = valorBase;
		this.creadorBoleta = creadorBoleta;
	}
	@Override
	public void arrendar(String cliente) {
		
		
	}
	@Override
	public boolean enArriendo() {
		return true;
	}
	@Override
	public Boleta devolver() {
		return null;
	}
}
