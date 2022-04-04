
package com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import com.refacFabela.ws.com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;


/**
 * <p>Clase Java para RespuestaPeticionesPendientes complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RespuestaPeticionesPendientes">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaBase">
 *       &lt;sequence>
 *         &lt;element name="UUIDs" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaPeticionesPendientes", propOrder = {
    "uuiDs"
})
public class RespuestaPeticionesPendientes
    extends RespuestaBase
{

    @XmlElementRef(name = "UUIDs", namespace = "http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfstring> uuiDs;

    /**
     * Obtiene el valor de la propiedad uuiDs.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getUUIDs() {
        return uuiDs;
    }

    /**
     * Define el valor de la propiedad uuiDs.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setUUIDs(JAXBElement<ArrayOfstring> value) {
        this.uuiDs = value;
    }

}
