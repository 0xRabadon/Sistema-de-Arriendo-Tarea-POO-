package cl.uach.info090.OvalleLuis;

import java.time.format.DateTimeFormatter;

/**
 * Construye la boleta y genera la estructura de la boleta
 * @author Luis Ovalle 
 */

public class BoletaCL extends Boleta {
	private String fechaAct;
	private String cliente;
	private String detCobro;
	private double valorNeto;
	private double impuesto;
	private double valorTotal;
	
	public BoletaCL(String fechaAct, String cliente, String detCobro, double valorNeto, double impuesto, double valorTotal) {
		this.fechaAct = fechaAct;
		this.cliente = cliente;
		this.detCobro = detCobro;
		this.valorNeto = valorNeto;
		this.impuesto = impuesto;
		this.valorTotal = valorTotal;
	}
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public String toString() {
		return String.format("%s    $ %.0f", fechaAct.formatted(FORMATTER), valorTotal);
	}
	/**
	 * Genera la boleta intentando seguir el estandar de las boletas
	 */
	@Override
	public String detalle() {
		StringBuilder boletaSB = new StringBuilder();
		String separador = "----------------------------------------- \n";
		
		boletaSB.append("Fecha: ");
		boletaSB.append(fechaAct);
		boletaSB.append("\nCliente: ");
		boletaSB.append(cliente);
		boletaSB.append("\n");
		boletaSB.append(separador);
		boletaSB.append("Detalle del cobro: ");
		boletaSB.append(detCobro);
		boletaSB.append("\n Valor Neto: ");
		boletaSB.append(valorNeto);
		boletaSB.append("\n Impuesto: ");
		boletaSB.append(impuesto);
		boletaSB.append("\n Valor total: ");
		boletaSB.append(valorTotal);
		
		return boletaSB.toString();
	}
	
	public String getFechaAct() {
		return fechaAct;
	}

	public String getCliente() {
		return cliente;
	}

	public String getFecha() {
		return fechaAct;
	}
 	
}
