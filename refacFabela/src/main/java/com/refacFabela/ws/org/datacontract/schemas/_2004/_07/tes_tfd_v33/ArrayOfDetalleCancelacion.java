
package com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfDetalleCancelacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDetalleCancelacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetalleCancelacion" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}DetalleCancelacion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetalleCancelacion", propOrder = {
    "detalleCancelacion"
})
public class ArrayOfDetalleCancelacion {

    @XmlElement(name = "DetalleCancelacion", nillable = true)
    protected List<DetalleCancelacion> detalleCancelacion;

    /**
     * Gets the value of the detalleCancelacion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detalleCancelacion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetalleCancelacion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetalleCancelacion }
     * 
     * 
     */
    public List<DetalleCancelacion> getDetalleCancelacion() {
        if (detalleCancelacion == null) {
            detalleCancelacion = new ArrayList<DetalleCancelacion>();
        }
        return this.detalleCancelacion;
    }

}
