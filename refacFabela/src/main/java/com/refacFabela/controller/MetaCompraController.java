package com.refacFabela.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

import com.refacFabela.dto.MetaCompraDto;
import com.refacFabela.model.VwProductoMetaCompra;
import com.refacFabela.service.VwProductoMetaCompraService;

@RestController
@RequestMapping("/meta-compra")

public class MetaCompraController {
	private static final Logger log = LoggerFactory.getLogger(MetaCompraController.class);

	
	@Autowired
    private  VwProductoMetaCompraService productoMetaCompraService;

    @PreAuthorize("hasRole('META_COMPRA_READ')")
    @GetMapping("/por-no-parte")
    public ResponseEntity<MetaCompraDto> porNoParte(@RequestParam String sNoParte) {
    	 log.debug(">> sNoParte={}", sNoParte);

         Optional<VwProductoMetaCompra> opt = productoMetaCompraService.buscarPorNoParte(sNoParte);
         if (!opt.isPresent()) {
             return ResponseEntity.notFound().build();
         }

         VwProductoMetaCompra v = opt.get();
         MetaCompraDto dto = new MetaCompraDto(
                 v.getsNoParte(),
                 v.getsProducto(),
                 v.getsMarca(),
                 v.getnTotalUnitarioCalculado()
         );

         log.debug("<< resp sNoParte={} total={}", dto.getsNoParte(), dto.getnTotalUnitarioCalculado());

         // Evita que algún proxy/cliente te “pegue” la respuesta anterior
         return ResponseEntity.ok()
                 .header("Cache-Control", "no-store, no-cache, max-age=0, must-revalidate")
                 .header("Pragma", "no-cache")
                 .header("Expires", "0")
                 .body(dto);
     }
}
