
package com.refacFabela.ws.org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaEstatusCancelacionAsincrona;


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
 *         &lt;element name="EstatusCancelacionAsincronaResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaEstatusCancelacionAsincrona" minOccurs="0"/>
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
    "estatusCancelacionAsincronaResult"
})
@XmlRootElement(name = "EstatusCancelacionAsincronaResponse")
public class EstatusCancelacionAsincronaResponse {

    @XmlElementRef(name = "EstatusCancelacionAsincronaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaEstatusCancelacionAsincrona> estatusCancelacionAsincronaResult;

    /**
     * Obtiene el valor de la propiedad estatusCancelacionAsincronaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}
     *     
     */
    public JAXBElement<RespuestaEstatusCancelacionAsincrona> getEstatusCancelacionAsincronaResult() {
        return estatusCancelacionAsincronaResult;
    }

    /**
     * Define el valor de la propiedad estatusCancelacionAsincronaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaEstatusCancelacionAsincrona }{@code >}
     *     
     */
    public void setEstatusCancelacionAsincronaResult(JAXBElement<RespuestaEstatusCancelacionAsincrona> value) {
        this.estatusCancelacionAsincronaResult = value;
    }

}
