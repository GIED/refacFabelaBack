
package com.refacFabela.ws.org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCancelacion;


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
 *         &lt;element name="CancelarCFDIConValidacionResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaCancelacion" minOccurs="0"/>
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
    "cancelarCFDIConValidacionResult"
})
@XmlRootElement(name = "CancelarCFDIConValidacionResponse")
public class CancelarCFDIConValidacionResponse {

    @XmlElementRef(name = "CancelarCFDIConValidacionResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaCancelacion> cancelarCFDIConValidacionResult;

    /**
     * Obtiene el valor de la propiedad cancelarCFDIConValidacionResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     *     
     */
    public JAXBElement<RespuestaCancelacion> getCancelarCFDIConValidacionResult() {
        return cancelarCFDIConValidacionResult;
    }

    /**
     * Define el valor de la propiedad cancelarCFDIConValidacionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     *     
     */
    public void setCancelarCFDIConValidacionResult(JAXBElement<RespuestaCancelacion> value) {
        this.cancelarCFDIConValidacionResult = value;
    }

}
