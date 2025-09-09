// package com.sca.service.impl;

// import com.sca.service.CsvService;
// import com.sca.service.InventarioService;

// import org.springframework.stereotype.Service;
// import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// import java.nio.charset.StandardCharsets;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// @Service
// public class InventarioServiceImpl extends ResponseEntityExceptionHandler implements InventarioService{
//     @Override
//     public byte[] invetarioCsv() {
        
//         CsvService csvService;
//         String csv = "";
        
//         Object data = csvService.generateCsv();

//         String restored = new String(bytes, StandardCharsets.UTF_8);

//         return csv.getBytes(StandardCharsets.UTF_8);
//     }
// }
