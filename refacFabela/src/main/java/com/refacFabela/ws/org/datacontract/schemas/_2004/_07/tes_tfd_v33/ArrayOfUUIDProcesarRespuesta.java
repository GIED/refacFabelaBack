
package com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfUUIDProcesarRespuesta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfUUIDProcesarRespuesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UUIDProcesarRespuesta" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}UUIDProcesarRespuesta" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUUIDProcesarRespuesta", propOrder = {
    "uuidProcesarRespuesta"
})
public class ArrayOfUUIDProcesarRespuesta {

    @XmlElement(name = "UUIDProcesarRespuesta", nillable = true)
    protected List<UUIDProcesarRespuesta> uuidProcesarRespuesta;

    /**
     * Gets the value of the uuidProcesarRespuesta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uuidProcesarRespuesta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUUIDProcesarRespuesta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UUIDProcesarRespuesta }
     * 
     * 
     */
    public List<UUIDProcesarRespuesta> getUUIDProcesarRespuesta() {
        if (uuidProcesarRespuesta == null) {
            uuidProcesarRespuesta = new ArrayList<UUIDProcesarRespuesta>();
        }
        return this.uuidProcesarRespuesta;
    }

}
