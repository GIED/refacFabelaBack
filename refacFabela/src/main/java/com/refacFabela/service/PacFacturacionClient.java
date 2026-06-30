package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.ArchivoCfdiResponse;
import com.refacFabela.dto.CancelacionRequest;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.dto.CfdiRelacionadosResponse;
import com.refacFabela.dto.CfdiTimbradoRequest;
import com.refacFabela.dto.CodigoPostalResponse;
import com.refacFabela.dto.CatalogoSatResponse;
import com.refacFabela.dto.ComplementoPagoRequest;
import com.refacFabela.dto.DescargaMasivaXmlResponse;
import com.refacFabela.dto.SolicitudCancelacionDto;
import com.refacFabela.dto.StatusCfdiRequest;
import com.refacFabela.dto.StatusCfdiResponse;
import com.refacFabela.dto.TimbradoResponse;
import com.refacFabela.dto.ValidacionCertificadoResponse;
import com.refacFabela.dto.ValidacionEfosEdosResponse;
import com.refacFabela.dto.ValidacionRfcResponse;

public interface PacFacturacionClient {

	TimbradoResponse timbrarCfdi(CfdiTimbradoRequest request);

	TimbradoResponse timbrarComplementoPago(ComplementoPagoRequest request);

	CancelacionResponse cancelarCfdi(CancelacionRequest request);

	StatusCfdiResponse consultarEstatusCfdi(StatusCfdiRequest request);

	List<SolicitudCancelacionDto> consultarSolicitudesPendientesCancelacion(String rfcEmisor);

	CancelacionResponse aceptarCancelacion(CancelacionRequest request);

	CancelacionResponse rechazarCancelacion(CancelacionRequest request);

	CfdiRelacionadosResponse consultarCfdiRelacionados(StatusCfdiRequest request);

	ArchivoCfdiResponse descargarXml(String uuid);

	ArchivoCfdiResponse descargarPdf(String uuid);

	int consultarCreditosDisponibles(String rfcEmisor);

	void enviarCorreoCfdi(String uuid, CfdiTimbradoRequest.DatosCorreoDto correo);

	ValidacionRfcResponse validarRfc(String rfc);

	ValidacionCertificadoResponse validarCertificado(String certificadoBase64);

	ValidacionEfosEdosResponse validarEfosEdos(String rfc);

	CatalogoSatResponse consultarCatalogoSat(String catalogo, String filtro);

	CodigoPostalResponse consultarCodigoPostal(String codigoPostal);

	DescargaMasivaXmlResponse solicitarDescargaMasivaXml(String rfcEmisor);
}
