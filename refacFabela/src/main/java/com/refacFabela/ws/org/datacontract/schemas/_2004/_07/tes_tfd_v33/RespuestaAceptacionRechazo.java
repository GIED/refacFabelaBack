
package com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para RespuestaAceptacionRechazo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RespuestaAceptacionRechazo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaBase">
 *       &lt;sequence>
 *         &lt;element name="Fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Folios" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}ArrayOfFolioRespuesta" minOccurs="0"/>
 *         &lt;element name="RFCReceptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XMLAcuse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaAceptacionRechazo", propOrder = {
    "fecha",
    "folios",
    "rfcReceptor",
    "xmlAcuse"
})
public class RespuestaAceptacionRechazo
    extends RespuestaBase
{

    @XmlElement(name = "Fecha")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElementRef(name = "Folios", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfFolioRespuesta> folios;
    @XmlElementRef(name = "RFCReceptor", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> rfcReceptor;
    @XmlElementRef(name = "XMLAcuse", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlAcuse;

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad folios.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfFolioRespuesta }{@code >}
     *     
     */
    public JAXBElement<ArrayOfFolioRespuesta> getFolios() {
        return folios;
    }

    /**
     * Define el valor de la propiedad folios.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfFolioRespuesta }{@code >}
     *     
     */
    public void setFolios(JAXBElement<ArrayOfFolioRespuesta> value) {
        this.folios = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcReceptor.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRFCReceptor() {
        return rfcReceptor;
    }

    /**
     * Define el valor de la propiedad rfcReceptor.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRFCReceptor(JAXBElement<String> value) {
        this.rfcReceptor = value;
    }

    /**
     * Obtiene el valor de la propiedad xmlAcuse.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getXMLAcuse() {
        return xmlAcuse;
    }

    /**
     * Define el valor de la propiedad xmlAcuse.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setXMLAcuse(JAXBElement<String> value) {
        this.xmlAcuse = value;
    }

}
