package cl.uach.info090.OvalleLuis;

import java.time.LocalDate;

public class CreadorBoletaCL implements CreadorBoleta{
	@Override
	public Boleta generarBoleta(String cliente, String detalle, double neto) {
		LocalDate fecha = LocalDate.now(); 		// da la fecha año mes dia
		String fecha2 = fecha.toString();
		
		double iva = neto*0.19;
		
		BoletaCL generado = new BoletaCL(fecha2, cliente, detalle, neto, iva, (neto+iva));
		return generado;
	}

}
