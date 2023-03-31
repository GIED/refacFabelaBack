package com.refacFabela.utils.factura;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class DefaultNamespacePrefixMapper extends NamespacePrefixMapper{
	
private Map<String, String> namespaceMap= new HashMap<>();
    
    public  DefaultNamespacePrefixMapper(){
        namespaceMap.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        namespaceMap.put("http://www.sat.gob.mx/cfd/4", "cfdi");
        
    }
    
    @Override
    public  String getPreferredPrefix(String nameespaceUri, String suggestion, boolean requiredPrefix) {
       return namespaceMap.getOrDefault(nameespaceUri, suggestion);
    }

}
