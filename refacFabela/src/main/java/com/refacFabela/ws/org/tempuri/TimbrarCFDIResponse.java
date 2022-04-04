
package com.refacFabela.ws.org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;


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
 *         &lt;element name="TimbrarCFDIResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaTFD33" minOccurs="0"/>
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
    "timbrarCFDIResult"
})
@XmlRootElement(name = "TimbrarCFDIResponse")
public class TimbrarCFDIResponse {

    @XmlElementRef(name = "TimbrarCFDIResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTFD33> timbrarCFDIResult;

    /**
     * Obtiene el valor de la propiedad timbrarCFDIResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     *     
     */
    public JAXBElement<RespuestaTFD33> getTimbrarCFDIResult() {
        return timbrarCFDIResult;
    }

    /**
     * Define el valor de la propiedad timbrarCFDIResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     *     
     */
    public void setTimbrarCFDIResult(JAXBElement<RespuestaTFD33> value) {
        this.timbrarCFDIResult = value;
    }

}
