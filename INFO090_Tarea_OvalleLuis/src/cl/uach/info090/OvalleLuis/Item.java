package cl.uach.info090.OvalleLuis;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JButton;

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
