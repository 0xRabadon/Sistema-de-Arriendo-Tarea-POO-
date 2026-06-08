package cl.uach.info090.OvalleLuis;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BoletaCL extends Boleta {
	private String fechaAct;
	private String cliente;
	private String detCobro;
	private int valorNeto;
	private int impuesto;
	private int valorTotal;
	
	public BoletaCL(String fechaAct, String cliente, String detCobro, int valorNeto, int impuesto, int valorTotal) {
		this.fechaAct = fechaAct;
		this.cliente = cliente;
		this.detCobro = detCobro;
		this.valorNeto = valorNeto;
		this.impuesto = impuesto;
		this.valorTotal = valorTotal;
	}
	
	public String toString() {
		// obtener hora actual para la boleta
		LocalTime hora = LocalTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
		
		String valor = String.valueOf(valorTotal);
		
		String corta = fechaAct + " " + hora.format(formato) + " " + "$ " + valor;
		return corta;
	}
	
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
		
		String boleta = boletaSB.toString();
		return boleta;
	}
	
	// GETTERS
	public String getFechaAct() {
		return fechaAct;
	}

	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDetCobro() {
		return detCobro;
	}

	public void setDetCobro(String detCobro) {
		this.detCobro = detCobro;
	}

	public int getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(int valorNeto) {
		this.valorNeto = valorNeto;
	}

	public int getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(int impuesto) {
		this.impuesto = impuesto;
	}

	public int getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
