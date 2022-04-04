
package com.refacFabela.ws.org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaValidacionRFC;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidarRFCResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaValidacionRFC" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "validarRFCResult"
})
@XmlRootElement(name = "ValidarRFCResponse")
public class ValidarRFCResponse {

    @XmlElementRef(name = "ValidarRFCResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaValidacionRFC> validarRFCResult;

    /**
     * Obtiene el valor de la propiedad validarRFCResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaValidacionRFC }{@code >}
     *     
     */
    public JAXBElement<RespuestaValidacionRFC> getValidarRFCResult() {
        return validarRFCResult;
    }

    /**
     * Define el valor de la propiedad validarRFCResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaValidacionRFC }{@code >}
     *     
     */
    public void setValidarRFCResult(JAXBElement<RespuestaValidacionRFC> value) {
        this.validarRFCResult = value;
    }

}
