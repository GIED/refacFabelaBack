package com.refacFabela.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.dto.PedidoProductoDto;
import com.refacFabela.dto.ProductoBodegaDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.dto.TwSaldoUtilizadoDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TwPedido;
import com.refacFabela.service.ReporteService;
import com.refacFabela.utils.envioMail;
import com.refacFabela.utils.utils;
import com.refacFabela.utils.factura.ConstantesFactura;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@Service
public class ReporteServiceImpl implements ReporteService {
	
	 private static final Logger logger = LogManager.getLogger("errorLogger");

	 private BufferedImage imagenHeader;
	 
	
	
	public ReporteServiceImpl() {
		try {
	         this.imagenHeader = ImageIO.read(new ClassPathResource("/reports/images/LOGO.jpg").getInputStream());
	      } catch (IOException e) {
	         logger.error("Error al obtener imagen: " + e.getMessage());
	      }
	}
	
	@Override
	public byte[] generaCotizacionPDF(ReporteCotizacionDto reporteCotizacion,  List<ReporteCotizacionDto> listaProducto) {
		 try {
	         Resource resource = new ClassPathResource("/reports/plantillas/cotizacion.jrxml");
	         final Map<String, Object> params = new HashMap<>();
	         File pdfFile = null;
	         String nombreArchivo = "cotizacion_"+reporteCotizacion.getFolioCotizacion();
	         String ruta="/opt/webserver/backEnd/refacFabela/";
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
	         
	         //aqui van los parametros
	         params.put("logo", this.imagenHeader);
	         params.put("nombreEmpresa", reporteCotizacion.getNombreEmpresa());
	         params.put("rfcEmpresa", reporteCotizacion.getRfcEmpresa());
	         params.put("nombreCliente", reporteCotizacion.getNombreCliente());
	         params.put("rfcCliente", reporteCotizacion.getRfcCliente());
	         params.put("folioCotizacion", reporteCotizacion.getFolioCotizacion());
	         params.put("fecha", reporteCotizacion.getFecha());
	         params.put("subTotal", reporteCotizacion.getSubTotal());
	         params.put("ivaTotal", reporteCotizacion.getIvaTotal());
	         params.put("total", reporteCotizacion.getTotal());
	         params.put("listaProductos", listaProducto);
	         params.put("nombreVendedor", reporteCotizacion.getNombreVendedor());

	         
	         params.put("qr", getQR(("Folio de cotización: C-"+reporteCotizacion.getFolioCotizacion()+"\nRFC cliente: "+reporteCotizacion.getRfcCliente()+"\nRazón Social: "+reporteCotizacion.getNombreCliente()+"\nTotal: "+reporteCotizacion.getTotal().toString()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	         
	         
	         
	         
	         
	         // InputStream ligado al reporte
	         InputStream inputStreamReport = resource.getInputStream();
	         FileOutputStream pos = new FileOutputStream(pdfFile);
	         JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
	         JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteCotizacion)), pos);

	         // Cierre de output stream
	         pos.flush();
	         pos.close();
	         
	         envioMail enviar=new envioMail();
				enviar.enviarCorreo(reporteCotizacion.getCorreo(), 
						"Cotización_"+reporteCotizacion.getFolioCotizacion(),
						"<p>Adjunto al presente cotizaci&oacute;n No. "+reporteCotizacion.getFolioCotizacion()+"</p><p>No omito mencionar que estar&aacute; vigente durante 3 d&iacute;as h&aacute;biles. </p><p> Sin m&aacute;s por el momento envi&oacute; un cordial saludo.</p>",
						ruta,
						nombreArchivo,
						1
						);			
	         // Se recuperan los bytes correspondientes al reporte
	         byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));

	         
	        
	         
	         
	         
	         //Eliminar el archivo generado 	         
	        
	        	 pdfFile.delete();
	         
	         

	         return bytesReporte;
	      } catch (Exception e) {
	         logger.error("Error en metodo generaCotizacionPDF(). Error al generar la cotizacion con id: " + reporteCotizacion.getFolioCotizacion(), e);
	      }

	      return null;
	}
	
	
	@Override
	public byte[] generaVentaPDF(ReporteVentaDto reporteVenta, List<ReporteVentaDto> listaProducto, double totalAbono) {
		try {
			String ruta="";
			if(reporteVenta.getTipoPago()==1) {
				ruta="/reports/plantillas/ventaCredito.jrxml";
			}
			if(reporteVenta.getTipoPago()==0) {
				ruta="/reports/plantillas/venta.jrxml";
			}
			
			utils util= new utils();
			
			
			
	         Resource resource = new ClassPathResource(ruta);
	         final Map<String, Object> params = new HashMap<>();
	         File pdfFile = null;
	         String nombreArchivo = "venta_"+reporteVenta.getFolioVenta();
	         pdfFile = new File("/opt/webserver/backEnd/refacFabela/" + nombreArchivo + ".pdf");
	        
	         String fechaVencimiento=util.sumarRestarDiasFecha(reporteVenta.getFecha(), 30);

	         
	         //aqui van los parametros
	         params.put("logo", this.imagenHeader);
	         params.put("nombreEmpresa", reporteVenta.getNombreEmpresa());
	         params.put("rfcEmpresa", reporteVenta.getRfcEmpresa());
	         params.put("nombreCliente", reporteVenta.getNombreCliente());
	         params.put("rfcCliente", reporteVenta.getRfcCliente());
	         params.put("folioVenta", reporteVenta.getFolioVenta());
	         params.put("fecha", reporteVenta.getFecha());
	         params.put("subTotal", reporteVenta.getSubTotal());
	         params.put("ivaTotal", reporteVenta.getIvaTotal());
	         params.put("total", reporteVenta.getTotal() - reporteVenta.getDescuento()-totalAbono);
	         params.put("listaProductos", listaProducto);
	         params.put("descuento", reporteVenta.getDescuento());
	         params.put("qr", getQR(("Folio de venta: V-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+reporteVenta.getTotal()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	         params.put("fechaVencimiento", fechaVencimiento);
	     	if(reporteVenta.getTipoPago()==1) {
	         params.put("totalAbono", totalAbono);
	     	}
	     	 params.put("nombreVendedor", reporteVenta.getNombreVendedor());
	         
	         
	         
	         
	         
	         // InputStream ligado al reporte
	         InputStream inputStreamReport = resource.getInputStream();
	         FileOutputStream pos = new FileOutputStream(pdfFile);
	         JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
	         JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteVenta)), pos);

	         // Cierre de output stream
	         pos.flush();
	         pos.close();

	         // Se recuperan los bytes correspondientes al reporte
	         byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
	         
	         //envió de correo
	        

	         //Eliminar el archivo generado
	        
	        	 pdfFile.delete();
	         
	         

	         return bytesReporte;
	      } catch (Exception e) {
	         logger.error("Error en metodo generaVentaPDF(). Error al generar la venta con id: " + reporteVenta.getFolioVenta(), e);
	      }

	      return null;
	}
	
	@Override
	public byte[] generaSaldoFavorPDF(ReporteVentaDto reporteVenta, List<ReporteVentaDto> listaProducto, double totalAbono,  double saldoFinalSaldo, double totalSaldoUsado, List<TwSaldoUtilizadoDto> listaTwSaldoUtilizadoDto ) {
		try {
			String ruta="";
			
				ruta="/reports/plantillas/saldoFavor.jrxml";
			
			
			utils util= new utils();
			
			
			
	         Resource resource = new ClassPathResource(ruta);
	         final Map<String, Object> params = new HashMap<>();
	         File pdfFile = null;
	         String nombreArchivo = "SALDO_FAVOR_"+reporteVenta.getFolioVenta();
	         pdfFile = new File("/opt/webserver/backEnd/refacFabela/" + nombreArchivo + ".pdf");
	        
	         String fechaVencimiento=util.sumarRestarDiasFecha(reporteVenta.getFecha(), 30);

	         
	         //aqui van los parametros
	         params.put("logo", this.imagenHeader);
	         params.put("nombreEmpresa", reporteVenta.getNombreEmpresa());
	         params.put("rfcEmpresa", reporteVenta.getRfcEmpresa());
	         params.put("nombreCliente", reporteVenta.getNombreCliente());
	         params.put("rfcCliente", reporteVenta.getRfcCliente());
	         params.put("folioVenta", reporteVenta.getFolioVenta());
	         params.put("fecha", reporteVenta.getFecha());
	         params.put("subTotal", reporteVenta.getSubTotal());
	         params.put("ivaTotal", reporteVenta.getIvaTotal());
	         params.put("total", reporteVenta.getTotal() - reporteVenta.getDescuento()-totalAbono);
	         params.put("listaProductos", listaProducto);
	         params.put("descuento", reporteVenta.getDescuento());
	         params.put("qr", getQR(("Folio de venta: V-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+reporteVenta.getTotal()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	         params.put("fechaVencimiento", fechaVencimiento);
	     	if(reporteVenta.getTipoPago()==1) {
	         params.put("totalAbono", totalAbono);
	     	}
	     	 params.put("nombreVendedor", reporteVenta.getNombreVendedor());
	     	 
	         params.put("listaSaldosUtilizados", listaTwSaldoUtilizadoDto);
	         params.put("saldoFinalSaldo", saldoFinalSaldo);
	         params.put("totalSaldoUsado", totalSaldoUsado);

	         
	         
	         
	         
	         
	         // InputStream ligado al reporte
	         InputStream inputStreamReport = resource.getInputStream();
	         FileOutputStream pos = new FileOutputStream(pdfFile);
	         JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
	         JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteVenta)), pos);

	         // Cierre de output stream
	         pos.flush();
	         pos.close();

	         // Se recuperan los bytes correspondientes al reporte
	         byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
	         
	         //envió de correo
	        

	         //Eliminar el archivo generado
	        
	        	 pdfFile.delete();
	         
	         

	         return bytesReporte;
	      } catch (Exception e) {
	         logger.error("Error en metodo generarSaldoFacorPDF(). Error al generar el salado favor " + reporteVenta.getFolioVenta(), e);
	      }

	      return null;
	}
	
	@Override
	public byte[] generaVentaAlmacenPDF(ReporteVentaDto reporteVenta, List<ReporteVentaDto> listaProducto, double totalAbono) {
		try {
			String ruta="";
			System.err.println("LLEGUE A GENERAR EL ARCHIVO ");
			
				ruta="/reports/plantillas/ventaAlmacen.jrxml";
		
			
			utils util= new utils();
			
			
			
	         Resource resource = new ClassPathResource(ruta);
	         final Map<String, Object> params = new HashMap<>();
	         File pdfFile = null;
	         String nombreArchivo = "venta_"+reporteVenta.getFolioVenta();
	         pdfFile = new File("/opt/webserver/backEnd/refacFabela/" + nombreArchivo + "_almacen.pdf");
	        
	         String fechaVencimiento=util.sumarRestarDiasFecha(reporteVenta.getFecha(), 30);

	         
	         //aqui van los parametros
	         params.put("logo", this.imagenHeader);
	         params.put("nombreEmpresa", reporteVenta.getNombreEmpresa());
	         params.put("rfcEmpresa", reporteVenta.getRfcEmpresa());
	         params.put("nombreCliente", reporteVenta.getNombreCliente());
	         params.put("rfcCliente", reporteVenta.getRfcCliente());
	         params.put("folioVenta", reporteVenta.getFolioVenta());
	         params.put("fecha", reporteVenta.getFecha());
	         params.put("subTotal", reporteVenta.getSubTotal());
	         params.put("ivaTotal", reporteVenta.getIvaTotal());
	         params.put("total", reporteVenta.getTotal() - reporteVenta.getDescuento()-totalAbono);
	         params.put("listaProductos", listaProducto);
	         params.put("descuento", reporteVenta.getDescuento());
	         params.put("qr", getQR(("Folio de venta: V-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+reporteVenta.getTotal()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	         params.put("fechaVencimiento", fechaVencimiento);
	         params.put("nombreVendedor", reporteVenta.getNombreVendedor());
	     	
	         
	         
	         
	         
	         
	         // InputStream ligado al reporte
	         InputStream inputStreamReport = resource.getInputStream();
	         FileOutputStream pos = new FileOutputStream(pdfFile);
	         JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
	         JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteVenta)), pos);

	         // Cierre de output stream
	         pos.flush();
	         pos.close();

	         // Se recuperan los bytes correspondientes al reporte
	         byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
	         
	         //envió de correo
	        

	         //Eliminar el archivo generado
	        
	        	 pdfFile.delete();
	         
	         

	         return bytesReporte;
	      } catch (Exception e) {
	         logger.error("Error en metodo generaVentaPDF(). Error al generar la venta con id: " + reporteVenta.getFolioVenta(), e);
	      }

	      return null;
	}
	
	public byte[] generaAbonoVentaPDF(ReporteVentaDto reporteVenta, List<AbonosDto> listaAbono, double abonos) {
		try {
			String ruta="";
			
				ruta="/reports/plantillas/abonoVenta.jrxml";
			
		
			
			utils util= new utils();
			
			
			
	         Resource resource = new ClassPathResource(ruta);
	         final Map<String, Object> params = new HashMap<>();
	         File pdfFile = null;
	         String nombreArchivo = "abono_"+reporteVenta.getFolioVenta();
	          ruta=ConstantesFactura.rutaRaiz;
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
	        
	         String fechaVencimiento=util.sumarRestarDiasFecha(reporteVenta.getFecha(), 30);

	         
	         //aqui van los parametros
	         params.put("logo", this.imagenHeader);
	         params.put("nombreEmpresa", reporteVenta.getNombreEmpresa());
	         params.put("rfcEmpresa", reporteVenta.getRfcEmpresa());
	         params.put("nombreCliente", reporteVenta.getNombreCliente());
	         params.put("rfcCliente", reporteVenta.getRfcCliente());
	         params.put("folioVenta", reporteVenta.getFolioVenta());
	         params.put("fecha", reporteVenta.getFecha());
	         params.put("subTotal", reporteVenta.getSubTotal());
	         params.put("ivaTotal", reporteVenta.getIvaTotal());
	         params.put("total", reporteVenta.getTotal());
	         params.put("listaAbonos", listaAbono);	    
	         params.put("qr", getQR(("Folio de venta: V-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+(reporteVenta.getTotal()-reporteVenta.getDescuento())+"\nTotal de Abonos: "+ listaAbono.size()).toString()));
	         params.put("fechaVencimiento", fechaVencimiento);
	         params.put("totalAbonos", abonos);
	         params.put("descuento", reporteVenta.getDescuento());
	         params.put("saldoFinal", reporteVenta.getTotal()-abonos - reporteVenta.getDescuento());
	         
	         
	         
	         
	         
	         // InputStream ligado al reporte
	         InputStream inputStreamReport = resource.getInputStream();
	         FileOutputStream pos = new FileOutputStream(pdfFile);
	         JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
	         JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteVenta)), pos);

	         // Cierre de output stream
	         pos.flush();
	         pos.close();

	         // Se recuperan los bytes correspondientes al reporte
	         byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));

	         //Eliminar el archivo generado
	         
	         /*
	         
	         envioMail enviar=new envioMail();
				enviar.enviarCorreo(reporteVenta.getCorreo(), 
						"Venta a crédito "+reporteVenta.getFolioVenta(),
						"<p>Adjunto al presente el historial de abonos del la venta a cr&eacute;dito "+reporteVenta.getFolioVenta()+"</p><p>No omito mencionar que la fecha limite para cubrir la totalidad de la nota es:"+fechaVencimiento+"  </p><p> Sin m&aacute;s por el momento envi&oacute; un cordial saludo.</p>",
						ruta,
						nombreArchivo,
						1
						);		*/
	         
	         
	        
	        	 pdfFile.delete();
	         
	         

	         return bytesReporte;
	      } catch (Exception e) {
	         logger.error("Error en metodo generaAbonoVentaPDF(). Error al generar la venta con id: " + reporteVenta.getFolioVenta(), e);
	      }

	      return null;
	}
	
	@Override
	public byte[] generaVentaPedidoPDF(ReporteVentaDto reporteVenta, List<ReporteVentaDto> listaProducto) {
		try {
			Resource resource = new ClassPathResource("/reports/plantillas/ventaPedido.jrxml");
			final Map<String, Object> params = new HashMap<>();
			File pdfFile = null;
			String ruta="";			
			  String nombreArchivo = "venta_pedido_"+reporteVenta.getFolioVenta();
	          ruta="/opt/webserver/backEnd/refacFabela/";
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
			
			//aqui van los parametros
			params.put("logo", this.imagenHeader);
			params.put("nombreEmpresa", reporteVenta.getNombreEmpresa());
			params.put("rfcEmpresa", reporteVenta.getRfcEmpresa());
			params.put("nombreCliente", reporteVenta.getNombreCliente());
			params.put("rfcCliente", reporteVenta.getRfcCliente());
			params.put("folioVenta", reporteVenta.getFolioVenta());
			params.put("fecha", reporteVenta.getFecha());
			params.put("subTotal", reporteVenta.getSubTotal());
			params.put("ivaTotal", reporteVenta.getIvaTotal());
			params.put("total", reporteVenta.getTotal());
			params.put("listaProductos", listaProducto);
			params.put("anticipo", reporteVenta.getAnticipo());	
			params.put("descuento", reporteVenta.getDescuento());
	        params.put("qr", getQR(("Folio de venta por pedido: VP-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+(reporteVenta.getTotal()-reporteVenta.getAnticipo()-reporteVenta.getDescuento())+"\nTotal de productos: "+ listaProducto.size()).toString()));
	        params.put("saldoFinal", reporteVenta.getTotal()-reporteVenta.getAnticipo());
	        params.put("nombreVendedor", reporteVenta.getNombreVendedor());
	        
			
			
			
			
			// InputStream ligado al reporte
			InputStream inputStreamReport = resource.getInputStream();
			FileOutputStream pos = new FileOutputStream(pdfFile);
			JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
			JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteVenta)), pos);
			
			// Cierre de output stream
			pos.flush();
			pos.close();
			
			// Se recuperan los bytes correspondientes al reporte
			byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
			
			//Eliminar el archivo generado
			
			pdfFile.delete();
			
			
			
			return bytesReporte;
		} catch (Exception e) {
			logger.error("Error en metodo generaVentaPDF(). Error al generar la venta con id: " + reporteVenta.getFolioVenta(), e);
		}
		
		return null;
	}
	
	
	
	
	private Image getQR(String total) throws IOException, WriterException {
	      
	         int size = 100;


	         // Configuracion del QR
	         Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
	         hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	         hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	         hintMap.put(EncodeHintType.MARGIN, 0);

	         // Generación del QR
	         QRCodeWriter qrCodeWriter = new QRCodeWriter();
	         BitMatrix byteMatrix = qrCodeWriter.encode(total, BarcodeFormat.QR_CODE, size, size, hintMap);
	         return MatrixToImageWriter.toBufferedImage(byteMatrix);
	      
	      
	   }

	@Override
	public byte[] generaAbonoVentaClientePDF(TcCliente cliente, List<ReporteAbonoVentaCreditoDto> listaAbomoVenta, ReporteVentaDto reporteVenta) {
		
		try {
			String ruta="";
			Resource resource = new ClassPathResource("/reports/plantillas/historialAbonosVentas.jrxml");
			final Map<String, Object> params = new HashMap<>();
			File pdfFile = null;
			String nombreArchivo = "historal_clinete_"+cliente.getsRfc();
			
			 ruta="/opt/webserver/backEnd/refacFabela/";
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
			utils util= new utils();
			
			//aqui van los parametros
			params.put("logo", this.imagenHeader);
			params.put("nombreEmpresa", reporteVenta.getNombreEmpresa());
			params.put("rfcEmpresa", reporteVenta.getRfcEmpresa());
			params.put("nombreCliente", cliente.getsRazonSocial());
			params.put("rfcCliente", cliente.getsRfc());		
			params.put("fecha", util.formatoFecha(new Date()));
			//params.put("subTotal", reporteVenta.getSubTotal());
			//params.put("ivaTotal", reporteVenta.getIvaTotal());
			params.put("total", reporteVenta.getTotal());
			params.put("descuento", reporteVenta.getDescuento());
			params.put("listaVenta", listaAbomoVenta);
			params.put("abonos", reporteVenta.getAbonos());			
	        params.put("qr", getQR(("\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+cliente.getsRazonSocial()+"\nTotal: "+reporteVenta.getTotal().toString()+"\nTotal de Ventas a crédito: "+ listaAbomoVenta.size()).toString()));
	        params.put("saldoFinal", (reporteVenta.getTotal()-reporteVenta.getAbonos()-reporteVenta.getDescuento()));
	        		
			
			
			// InputStream ligado al reporte
			InputStream inputStreamReport = resource.getInputStream();
			FileOutputStream pos = new FileOutputStream(pdfFile);
			JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
			JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteVenta)), pos);
			
			// Cierre de output stream
			pos.flush();
			pos.close();
			
			// Se recuperan los bytes correspondientes al reporte
			byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
			
			//Eliminar el archivo generado
			
			//pdfFile.delete();
			
			
			
			return bytesReporte;
		} catch (Exception e) {
			logger.error("Error en metodo generaVentaPDF(). Error al generar la venta con id: ", e);
		}
		
		return null;
	}

	@Override
	public byte[] generaPedidoPDF(TwPedido twPedido, List<PedidoProductoDto> listaPedidoProducto) {
	
		try {
			String ruta="";
			Resource resource = new ClassPathResource("/reports/plantillas/pedido.jrxml");
			final Map<String, Object> params = new HashMap<>();
			File pdfFile = null;
			String nombreArchivo = "pedido_"+twPedido.getsCvePedido();
			
			 ruta="/opt/webserver/backEnd/refacFabela/";
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
			utils util= new utils();
			
			//aqui van los parametros
			params.put("logo", this.imagenHeader);
			params.put("nombreEmpresa", "REFACCIONES FABELLA");
			params.put("rfcEmpresa", "TES030201001");
			params.put("id", twPedido.getnId());
			params.put("clave", twPedido.getsCvePedido());		
			params.put("fecha", util.formatoFecha(twPedido.getdFechaPedido()));			
			params.put("listaPedidoProducto", listaPedidoProducto);		
			params.put("usuario", twPedido.getTcUsuario().getsNombreUsuario());
	        params.put("qr", getQR(("\nUsuario: "+twPedido.getTcUsuario().getsNombreUsuario())));
	       
	        System.err.println("El tamaño de la lista es:"+listaPedidoProducto.size());
			
			
			// InputStream ligado al reporte
			InputStream inputStreamReport = resource.getInputStream();
			FileOutputStream pos = new FileOutputStream(pdfFile);
			JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
			JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(twPedido)), pos);
			
			// Cierre de output stream
			pos.flush();
			pos.close();
			
			// Se recuperan los bytes correspondientes al reporte
			byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
			
			//Eliminar el archivo generado
			
			//pdfFile.delete();
			
			
			
			return bytesReporte;
		} catch (Exception e) {
			logger.error("Error en metodo generaPDF(). Error al generar el pedido ", e);
		}
		
		return null;
	}
	
	
	public byte[] generarReporteCajaPDF(BalanceCajaDto balanceCajaDto) {
	
		try {
			
			System.err.println(balanceCajaDto);
			
			String ruta="";
			Resource resource = new ClassPathResource("/reports/plantillas/corteCaja.jrxml");
			final Map<String, Object> params = new HashMap<>();
			File pdfFile = null;
			String nombreArchivo = "caja_"+balanceCajaDto.getCaja();
			
			 ruta="/opt/webserver/backEnd/refacFabela/";
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
			utils util= new utils();
			
			//aqui van los parametros
			params.put("logo", this.imagenHeader);
			params.put("nombreEmpresa", "REFACCIONES FABELLA");
			params.put("rfcEmpresa", "FAMJ810312FY6");
			params.put("listaFormaPago", balanceCajaDto.getTvReporteCajaFormaPago());
			params.put("caja",  balanceCajaDto.getCaja());
			params.put("fechaEmicion",  balanceCajaDto.getFechaGeneraReporte());
			params.put("totalIngresoVentas", util.truncarDecimales(balanceCajaDto.getTotalIngresoVenta()) );
			params.put("totalIngresoAbonos",  util.truncarDecimales(balanceCajaDto.getTotalIngresoAbonos()) );
			params.put("totalIngresoGeneral",  util.truncarDecimales(balanceCajaDto.getTotalGeneralIngresos()) );
			params.put("noVentas",  balanceCajaDto.getNoVentas());
			params.put("noAbonos",  balanceCajaDto.getNoAbonos());
			params.put("totalVentas",  util.truncarDecimales(balanceCajaDto.getTotalVentas()));
			params.put("entregadas",  balanceCajaDto.getTotalEntregadas());
			params.put("noEntregadas",  balanceCajaDto.getTotalNoEntregadas());
			params.put("entregasParciales",  balanceCajaDto.getTotalEntregasParciales());
			params.put("listaVentaDetalle", balanceCajaDto.getTvReporteDetalleVenta());
			params.put("totalReintegro", balanceCajaDto.getTotalReintegro());
			params.put("totalGastos", balanceCajaDto.getTotalGastos());
			params.put("listaGastos", balanceCajaDto.getListaGastos());
			params.put("listaAbonos", balanceCajaDto.getListaAbonos());
			params.put("listaCancelados", balanceCajaDto.getListaCancelados());
			params.put("totalCredito", balanceCajaDto.getTotalCredito());
			params.put("totalVenta", balanceCajaDto.getTotalVenta());
			
			
			params.put("efectivoContado", balanceCajaDto.getEfectivoContado());
			params.put("chequeContado", balanceCajaDto.getChequeContado());
			params.put("transferenciaContado", balanceCajaDto.getTransferenciaContado());
			params.put("tarjetaCreditoContado", balanceCajaDto.getTarjetaCreditoContado());
			params.put("tarjetaDebitoContado", balanceCajaDto.getTarjetaDebitoContad());
			params.put("condonacionContado", balanceCajaDto.getCondonacionContado());
			
			params.put("efectivoAbono", balanceCajaDto.getEfectivoAbono());
			params.put("chequeAbono", balanceCajaDto.getChequeAbono());
			params.put("transferenciaAbono", balanceCajaDto.getTransferenciaAbono());
			params.put("tarjetaCreditoAbono", balanceCajaDto.getTarjetaCreditoAbono());
			params.put("tarjetaDebitoAbono", balanceCajaDto.getTarjetaDebitoAbon());
			params.put("condonacionAbono", balanceCajaDto.getCondonacionAbono());
			params.put("saldoFavor", balanceCajaDto.getSaldosFavor());
			params.put("totalCancela", balanceCajaDto.getTotalCancelado());
			params.put("totalDescuento", balanceCajaDto.getTotalDescuento());

			
			System.err.println(balanceCajaDto.listaGastos);
			
			
		
			
		
			
	        params.put("qr", getQR(("\nTotalventas: "+balanceCajaDto.getTotalVentas()+"\nTotal ventas: "+balanceCajaDto.getTotalIngresoVenta()+"\nTotal abonos: "+balanceCajaDto.getTotalIngresoAbonos()+"\nTotal ingreso: "+balanceCajaDto.getTotalGeneralIngresos())));
	       
	      
			
			
			// InputStream ligado al reporte
			InputStream inputStreamReport = resource.getInputStream();
			FileOutputStream pos = new FileOutputStream(pdfFile);
			JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
			JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(balanceCajaDto)), pos);
			
			// Cierre de output stream
			pos.flush();
			pos.close();
			
			// Se recuperan los bytes correspondientes al reporte
			byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
			
			//Eliminar el archivo generado
			
			//pdfFile.delete();
			
			
			
			return bytesReporte;
			//return null;

		} catch (Exception e) {
			logger.error("Error en metodo generaPDF(). Error al generar el pdf de reporte de caja ", e);
		}
		
		return null;
	}

	public byte[] generarReporteInventarioPDF(List<ProductoBodegaDto> listaProductoBodega) {
		
		try {
			
		
			
			String ruta="";
			Resource resource = new ClassPathResource("/reports/plantillas/inventario.jrxml");
			final Map<String, Object> params = new HashMap<>();
			File pdfFile = null;
			String nombreArchivo = "inventario";
			
			 ruta="/opt/webserver/backEnd/refacFabela/";
	         pdfFile = new File(ruta + nombreArchivo + ".pdf");
			utils util= new utils();
			
			//aqui van los parametros
			
			params.put("listaProductos", listaProductoBodega);
		

			
			// InputStream ligado al reporte
			InputStream inputStreamReport = resource.getInputStream();
			FileOutputStream pos = new FileOutputStream(pdfFile);
			JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
			JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(listaProductoBodega)), pos);
			
			// Cierre de output stream
			pos.flush();
			pos.close();
			
			// Se recuperan los bytes correspondientes al reporte
			byte[] bytesReporte = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
			
			//Eliminar el archivo generado
			
			//pdfFile.delete();
			
			
			
			return bytesReporte;
			//return null;

		} catch (Exception e) {
			logger.error("Error en metodo generaPDF(). Error al generar el pdf de reporte de caja ", e);
		}
		
		return null;
	}


	

}
