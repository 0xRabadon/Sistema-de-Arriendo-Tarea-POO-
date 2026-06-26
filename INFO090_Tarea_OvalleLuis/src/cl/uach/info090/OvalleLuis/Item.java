package cl.uach.info090.OvalleLuis;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JButton;

/**
 * Clase abstracta que plantea el como funcionara el sistema de arriendo para cada vehiculo
 * @author Luis Ovalle
 */
public abstract class Item extends JButton implements Arrendable{
	private String itemId;
	private String itemDescription;
	private String cliente;
	private double valorHora;
	private double valorBase;
	private boolean arrendado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFinal;
	private CreadorBoleta creadorBoleta;
	
	/**
	 * Constructor de los Item
	 * @param itemId Identificador del item
	 * @param itemDescripcion Descripcion del item
	 * @param valorHora Costo de arrendarlo por hora
	 * @param valorBase Costo base del arriendo
	 * @param arrendado Booleano que corrobora si esta arrendado o no
	 * @param creadorBoleta Crea la boleta cuando se devuelve el item
	 * @param cliente Nombre del cliente que arrendo el item
	 * @param fechaInicio Dia donde comenzo el arriendo
	 */
	
	public Item(String itemId, String itemDescripcion, double valorHora, double valorBase, boolean arrendado, CreadorBoleta creadorBoleta) {
		super(itemId);
		this.itemId = itemId;
		this.itemDescription = itemDescripcion;
		this.valorHora = valorHora;
		this.valorBase = valorBase;
		this.arrendado = false;
		this.creadorBoleta = creadorBoleta;
		this.cliente = null;
		this.fechaInicio = null;
	}
	
	/**
	 * Funcion para arrendar el vehiculo
	 */
	
	@Override
	public void arrendar(String cliente) {
		if (!enArriendo()) {
			this.cliente = cliente;
			fechaInicio = LocalDateTime.now();
			arrendado = true;
		}
		
	}
	@Override
	public boolean enArriendo() {
		return arrendado;
	}
	@Override
	
	/**
	 * Funcion para devolver el vehiculo y genera boleta
	 */
	public Boleta devolver() {
		if (enArriendo()) {
			arrendado = false;
			fechaFinal = LocalDateTime.now();
			long horas = Duration.between(fechaInicio, fechaFinal).toHours();
			double neto =  valorBase + (valorHora*horas);
			return creadorBoleta.generarBoleta(cliente, itemDescription,  neto);
		}
		return null;
	}
	
	public String getItemId() {return itemId;}
	public String getItemDescription() {return itemDescription;}
	public double getValorHora() {return valorHora;}
	public double getValorBase() {return valorBase;}
	public String getCliente() {return cliente;}
	public LocalDateTime getFechaInicio() {return fechaInicio;}
	
}
