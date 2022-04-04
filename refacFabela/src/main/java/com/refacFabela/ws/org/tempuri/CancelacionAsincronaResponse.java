
package com.refacFabela.ws.org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCancelacionAsincrona;


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
 *         &lt;element name="CancelacionAsincronaResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaCancelacionAsincrona" minOccurs="0"/>
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
    "cancelacionAsincronaResult"
})
@XmlRootElement(name = "CancelacionAsincronaResponse")
public class CancelacionAsincronaResponse {

    @XmlElementRef(name = "CancelacionAsincronaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaCancelacionAsincrona> cancelacionAsincronaResult;

    /**
     * Obtiene el valor de la propiedad cancelacionAsincronaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacionAsincrona }{@code >}
     *     
     */
    public JAXBElement<RespuestaCancelacionAsincrona> getCancelacionAsincronaResult() {
        return cancelacionAsincronaResult;
    }

    /**
     * Define el valor de la propiedad cancelacionAsincronaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacionAsincrona }{@code >}
     *     
     */
    public void setCancelacionAsincronaResult(JAXBElement<RespuestaCancelacionAsincrona> value) {
        this.cancelacionAsincronaResult = value;
    }

}
