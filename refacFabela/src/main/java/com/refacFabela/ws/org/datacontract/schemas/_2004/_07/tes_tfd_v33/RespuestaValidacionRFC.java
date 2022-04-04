
package com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RespuestaValidacionRFC complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RespuestaValidacionRFC">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cancelado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="MensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RFC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RFCLocalizado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Subcontratacion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnidadSNCF" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaValidacionRFC", propOrder = {
    "cancelado",
    "mensajeError",
    "rfc",
    "rfcLocalizado",
    "subcontratacion",
    "unidadSNCF"
})
public class RespuestaValidacionRFC {

    @XmlElement(name = "Cancelado")
    protected Boolean cancelado;
    @XmlElementRef(name = "MensajeError", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeError;
    @XmlElementRef(name = "RFC", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> rfc;
    @XmlElement(name = "RFCLocalizado")
    protected Boolean rfcLocalizado;
    @XmlElement(name = "Subcontratacion")
    protected Boolean subcontratacion;
    @XmlElement(name = "UnidadSNCF")
    protected Boolean unidadSNCF;

    /**
     * Obtiene el valor de la propiedad cancelado.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCancelado() {
        return cancelado;
    }

    /**
     * Define el valor de la propiedad cancelado.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCancelado(Boolean value) {
        this.cancelado = value;
    }

    /**
     * Obtiene el valor de la propiedad mensajeError.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensajeError() {
        return mensajeError;
    }

    /**
     * Define el valor de la propiedad mensajeError.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensajeError(JAXBElement<String> value) {
        this.mensajeError = value;
    }

    /**
     * Obtiene el valor de la propiedad rfc.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRFC() {
        return rfc;
    }

    /**
     * Define el valor de la propiedad rfc.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRFC(JAXBElement<String> value) {
        this.rfc = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcLocalizado.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRFCLocalizado() {
        return rfcLocalizado;
    }

    /**
     * Define el valor de la propiedad rfcLocalizado.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRFCLocalizado(Boolean value) {
        this.rfcLocalizado = value;
    }

    /**
     * Obtiene el valor de la propiedad subcontratacion.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSubcontratacion() {
        return subcontratacion;
    }

    /**
     * Define el valor de la propiedad subcontratacion.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSubcontratacion(Boolean value) {
        this.subcontratacion = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadSNCF.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnidadSNCF() {
        return unidadSNCF;
    }

    /**
     * Define el valor de la propiedad unidadSNCF.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnidadSNCF(Boolean value) {
        this.unidadSNCF = value;
    }

}
