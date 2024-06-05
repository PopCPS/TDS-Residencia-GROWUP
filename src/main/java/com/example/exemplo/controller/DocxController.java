package com.example.exemplo.controller;

import com.example.exemplo.services.DocxService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DocxController {

    public final DocxService docxService;

    public DocxController(DocxService docxService){
        this.docxService = docxService;
    }

    // O pointDivergence serve para você indicar qual ponto você quer gerar o relatório.
    @GetMapping(value = "/export/{journeyId}/{mapId}/{pointDivergence}", produces = "application/docx")
    public @ResponseBody void exportFile(HttpServletResponse response, @PathVariable String journeyId, @PathVariable String mapId, @PathVariable int pointDivergence) throws IOException {
        docxService.generateDocx(response, journeyId, mapId, pointDivergence);
    }
}
