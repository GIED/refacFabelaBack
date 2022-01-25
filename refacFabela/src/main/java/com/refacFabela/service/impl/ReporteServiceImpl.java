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
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.service.ReporteService;
import com.refacFabela.utils.utils;

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
	        
	         pdfFile = new File("/opt/webserver/backEnd/refacFabela/" + nombreArchivo + ".pdf");
	         
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
	         params.put("qr", getQR(("Folio de cotización: C-"+reporteCotizacion.getFolioCotizacion()+"\nRFC cliente: "+reporteCotizacion.getRfcCliente()+"\nRazón Social: "+reporteCotizacion.getNombreCliente()+"\nTotal: "+reporteCotizacion.getTotal().toString()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	         
	         
	         
	         
	         
	         // InputStream ligado al reporte
	         InputStream inputStreamReport = resource.getInputStream();
	         FileOutputStream pos = new FileOutputStream(pdfFile);
	         JasperReport report = JasperCompileManager.compileReport(inputStreamReport);
	         JasperReportsUtils.renderAsPdf(report, params, new JRBeanCollectionDataSource(Collections.singleton(reporteCotizacion)), pos);

	         // Cierre de output stream
	         pos.flush();
	         pos.close();

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
	public byte[] generaVentaPDF(ReporteVentaDto reporteVenta, List<ReporteVentaDto> listaProducto) {
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
	         params.put("total", reporteVenta.getTotal());
	         params.put("listaProductos", listaProducto);	    
	         params.put("qr", getQR(("Folio de venta: V-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+reporteVenta.getTotal().toString()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	         params.put("fechaVencimiento", fechaVencimiento);
	         
	         
	         
	         
	         
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
	
	@Override
	public byte[] generaVentaPedidoPDF(ReporteVentaDto reporteVenta, List<ReporteVentaDto> listaProducto) {
		try {
			Resource resource = new ClassPathResource("/reports/plantillas/ventaPedido.jrxml");
			final Map<String, Object> params = new HashMap<>();
			File pdfFile = null;
			String nombreArchivo = "venta_pedido_"+reporteVenta.getFolioVenta();
			
			pdfFile = new File("/opt/webserver/backEnd/refacFabela/" + nombreArchivo + ".pdf");
			
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
	        params.put("qr", getQR(("Folio de venta por pedido: VP-"+reporteVenta.getFolioVenta()+"\nRFC cliente: "+reporteVenta.getRfcCliente()+"\nRazón Social: "+reporteVenta.getNombreCliente()+"\nTotal: "+reporteVenta.getTotal().toString()+"\nTotal de productos: "+ listaProducto.size()).toString()));
	        params.put("saldoFinal", reporteVenta.getTotal()-reporteVenta.getAnticipo());
	        
			System.err.println("El saldo final es de:"+(reporteVenta.getTotal()-reporteVenta.getAnticipo()));
			
			
			
			
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


	

}
