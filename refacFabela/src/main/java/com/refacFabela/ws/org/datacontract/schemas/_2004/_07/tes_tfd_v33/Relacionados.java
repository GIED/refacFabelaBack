
package com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Relacionados complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Relacionados">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UuidConsultado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UuidsRelacionadosHijos" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}ArrayOfUUID" minOccurs="0"/>
 *         &lt;element name="UuidsRelacionadosPadres" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}ArrayOfUUID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relacionados", propOrder = {
    "resultado",
    "uuidConsultado",
    "uuidsRelacionadosHijos",
    "uuidsRelacionadosPadres"
})
public class Relacionados {

    @XmlElementRef(name = "Resultado", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resultado;
    @XmlElementRef(name = "UuidConsultado", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> uuidConsultado;
    @XmlElementRef(name = "UuidsRelacionadosHijos", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfUUID> uuidsRelacionadosHijos;
    @XmlElementRef(name = "UuidsRelacionadosPadres", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfUUID> uuidsRelacionadosPadres;

    /**
     * Obtiene el valor de la propiedad resultado.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResultado() {
        return resultado;
    }

    /**
     * Define el valor de la propiedad resultado.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResultado(JAXBElement<String> value) {
        this.resultado = value;
    }

    /**
     * Obtiene el valor de la propiedad uuidConsultado.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUuidConsultado() {
        return uuidConsultado;
    }

    /**
     * Define el valor de la propiedad uuidConsultado.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUuidConsultado(JAXBElement<String> value) {
        this.uuidConsultado = value;
    }

    /**
     * Obtiene el valor de la propiedad uuidsRelacionadosHijos.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfUUID }{@code >}
     *     
     */
    public JAXBElement<ArrayOfUUID> getUuidsRelacionadosHijos() {
        return uuidsRelacionadosHijos;
    }

    /**
     * Define el valor de la propiedad uuidsRelacionadosHijos.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfUUID }{@code >}
     *     
     */
    public void setUuidsRelacionadosHijos(JAXBElement<ArrayOfUUID> value) {
        this.uuidsRelacionadosHijos = value;
    }

    /**
     * Obtiene el valor de la propiedad uuidsRelacionadosPadres.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfUUID }{@code >}
     *     
     */
    public JAXBElement<ArrayOfUUID> getUuidsRelacionadosPadres() {
        return uuidsRelacionadosPadres;
    }

    /**
     * Define el valor de la propiedad uuidsRelacionadosPadres.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfUUID }{@code >}
     *     
     */
    public void setUuidsRelacionadosPadres(JAXBElement<ArrayOfUUID> value) {
        this.uuidsRelacionadosPadres = value;
    }

}
