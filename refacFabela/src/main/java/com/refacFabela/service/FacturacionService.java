package com.refacFabela.service;

import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.dto.CancelacionFacturaDto;
import com.refacFabela.dto.SolicitudCancelacionAccionDto;
import com.refacFabela.dto.SubirFacturaDto;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.dto.CfdiRelacionadosResponse;
import com.refacFabela.dto.SolicitudCancelacionDto;
import com.refacFabela.dto.StatusCfdiResponse;
import com.refacFabela.model.TwFacturacion;

import java.util.List;

public interface FacturacionService {
	
	public String venta(Long idVenta, String cveCfdi) throws Exception;
	public String cancelaFactura(Long idVenta, String cveCfdi) throws Exception;
	public String cancelaFactura(CancelacionFacturaDto cancelacionFacturaDto) throws Exception;
	
	public String complemento(Long idVenta, String cveCfdi) throws Exception;
	
	public TwFacturacion guardar(TwFacturacion twFacturacion);
	
	public int consultaCreditos(Long nDatoFactura);

	public StatusCfdiResponse consultarEstatusCfdi(Long nIdVenta) throws Exception;

	public CfdiRelacionadosResponse consultarCfdiRelacionados(Long nIdVenta) throws Exception;

	public List<SolicitudCancelacionDto> consultarSolicitudesPendientesCancelacion(Long nIdDatoFactura) throws Exception;

	public CancelacionResponse aceptarSolicitudCancelacion(SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) throws Exception;

	public CancelacionResponse rechazarSolicitudCancelacion(SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) throws Exception;
	
	public SubirFacturaDto subirArchivo(MultipartFile file, MultipartFile fileXml, String venta, String uuid) throws Exception;


}

