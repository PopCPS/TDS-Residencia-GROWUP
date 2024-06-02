package com.example.exemplo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class DocxController {

    @GetMapping(value = "/export/{journeyId}/{mapId}", produces = "application/docx")
    public @ResponseBody void exportFile(HttpServletResponse response) throws IOException {
        XWPFDocument document = new XWPFDocument();
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=document.docx");

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);


        XWPFRun titleRun = title.createRun();
        titleRun.setText("Funcionou!!!");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        document.write(response.getOutputStream());
        document.close();
    }

}
