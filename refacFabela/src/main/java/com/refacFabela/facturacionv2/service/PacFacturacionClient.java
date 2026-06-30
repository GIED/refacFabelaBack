package com.refacFabela.facturacionv2.service;

import java.util.List;

import com.refacFabela.facturacionv2.dto.internal.ArchivoCfdiResponse;
import com.refacFabela.facturacionv2.dto.internal.CancelacionRequest;
import com.refacFabela.facturacionv2.dto.internal.CancelacionResponse;
import com.refacFabela.facturacionv2.dto.internal.CfdiRelacionadosResponse;
import com.refacFabela.facturacionv2.dto.internal.CfdiTimbradoRequest;
import com.refacFabela.facturacionv2.dto.internal.CodigoPostalResponse;
import com.refacFabela.facturacionv2.dto.internal.CatalogoSatResponse;
import com.refacFabela.facturacionv2.dto.internal.ComplementoPagoRequest;
import com.refacFabela.facturacionv2.dto.internal.DescargaMasivaXmlResponse;
import com.refacFabela.facturacionv2.dto.internal.SolicitudCancelacionDto;
import com.refacFabela.facturacionv2.dto.internal.StatusCfdiRequest;
import com.refacFabela.facturacionv2.dto.internal.StatusCfdiResponse;
import com.refacFabela.facturacionv2.dto.internal.TimbradoResponse;
import com.refacFabela.facturacionv2.dto.internal.ValidacionCertificadoResponse;
import com.refacFabela.facturacionv2.dto.internal.ValidacionEfosEdosResponse;
import com.refacFabela.facturacionv2.dto.internal.ValidacionRfcResponse;

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