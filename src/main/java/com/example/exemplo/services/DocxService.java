package com.example.exemplo.services;

import com.example.exemplo.controller.JourneysController;
import com.example.exemplo.controller.dto.AccessPointsDTO.AccessPointsDTO;
import com.example.exemplo.controller.dto.AccessPointsDTO.Tool;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class DocxService {
    public final MapsService mapsService;
    public final PointsService pointsServices;
    private final ResponsesQuestionService responsesQuestionService;

    private final SummaryService summaryService;
    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);

    public DocxService(MapsService mapsService, PointsService pointsService, ResponsesQuestionService responsesQuestionService, SummaryService summaryService){
        this.mapsService = mapsService;
        this.pointsServices = pointsService;
        this.responsesQuestionService = responsesQuestionService;
        this.summaryService = summaryService;
    }


    public void generateDocx(HttpServletResponse response, String journeyId, String mapId, int pointDivergence) throws IOException {

        String tituloJornada = mapsService.getMaps(journeyId).getBody().getTitle();

        int posicaoPointDivergence = pointDivergence;

        List<AccessPointsDTO> content =  pointsServices.getPoints(mapId).getBody().getContent();


        Tool toll = content.get(0).getTool();
        String tituloPoints = toll.getTitle();

        String divergenceId= content.get(0).getId();
        String questionID;
        String commentsReplies;
        int quantityAnswers;
        int quantityCommentsAnswers;

        XWPFDocument document = new XWPFDocument();
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=document.docx");

        // PÁGINA DA CAPA DO DOCUMENTO
        XWPFParagraph emptyParagraph2 = document.createParagraph();
        emptyParagraph2.setSpacingBeforeLines(2100);

        XWPFParagraph imageCapa = document.createParagraph();
        imageCapa.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageCapaRun = imageCapa.createRun();
        imageCapaRun.setTextPosition(20);

        try {
            Path imagePath = Paths.get(ClassLoader.getSystemResource("logo-tds.png").toURI());
            imageCapaRun.addPicture(Files.newInputStream(imagePath),
                    XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
                    Units.toEMU(300), Units.toEMU(90));
            imageCapaRun.addBreak();
        }catch (Exception e){
            log.info(e.getMessage());
        }

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText((" Residência Tecnológica do Porto Digital ").toUpperCase());
        titleRun.addBreak();
        titleRun.setText(("Squad 23").toUpperCase());
        titleRun.addBreak();

        titleRun.setFontFamily("Arial");
        titleRun.setFontSize(16);
        titleRun.addBreak();


        titleRun.addBreak(org.apache.poi.xwpf.usermodel.BreakType.PAGE);

        // PÁGINA DO RELATÓRIO
        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        try {
            Path imagePath = Paths.get(ClassLoader.getSystemResource("strateegia-destaque.png").toURI());
            imageRun.addPicture(Files.newInputStream(imagePath),
            XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
            Units.toEMU(50), Units.toEMU(50));
            imageRun.addBreak();
        }catch (Exception e){
            log.info(e.getMessage());
        }

        // O título do relatório tem o título da jornada e dos pontos de divergência
        XWPFParagraph titleReport = document.createParagraph();
        titleReport.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleReportRun = titleReport.createRun();

        titleReportRun.setText(tituloJornada.toUpperCase());
        titleReportRun.addBreak();
        titleReportRun.setText(tituloPoints.toUpperCase());
        titleReportRun.setFontFamily("Arial");
        titleReportRun.setFontSize(12);
        titleReportRun.addBreak();


        // Loop mostrar todas as questões e seus respectivos resumos do endpoint escolhido
        for (int loopEachQuestionEndpoint = 0; loopEachQuestionEndpoint < toll.getQuestions().size(); loopEachQuestionEndpoint++) {
            String question = toll.getQuestions().get(loopEachQuestionEndpoint).getQuestion();
            questionID= content.get(0).getTool().getQuestions().get(loopEachQuestionEndpoint).getId();
            quantityAnswers = responsesQuestionService.getResponsesQuestion(divergenceId, questionID).getBody().getComments().size();

            // Gerar o resumo das questões
            String summary = summaryService.getSummary(divergenceId, questionID).getBody().getContent().get(0).getSummary();

            XWPFParagraph paragraphSummary = document.createParagraph();
            XWPFRun paragraphSummaryRun = paragraphSummary.createRun();
            String prefix = "RESUMO: ";
            paragraphSummaryRun.setText(prefix + summary);

            paragraphSummary.setIndentationLeft(410);
            paragraphSummaryRun.setFontFamily("Arial");
            paragraphSummaryRun.setFontSize(12);
            paragraphSummary.setAlignment(ParagraphAlignment.BOTH);
            paragraphSummaryRun.addBreak();

            // Gerar as questões do endpoint
            XWPFParagraph paragraphQuestion = document.createParagraph();
            XWPFRun paragraphQuestionRun = paragraphQuestion.createRun();
            paragraphQuestionRun.setText(question);

            paragraphQuestion.setIndentationLeft(410);
            paragraphQuestionRun.setBold(true);
            paragraphQuestionRun.setFontFamily("Arial");
            paragraphQuestionRun.setFontSize(12);
            paragraphQuestionRun.addBreak();


            // Loop para pegar todas as respostas de cada pergunta do endpoint
            for (int loopEachQuestionAnswer = 0 ; loopEachQuestionAnswer < quantityAnswers; loopEachQuestionAnswer++){

                XWPFParagraph responses = document.createParagraph();
                XWPFRun responsesRun = responses.createRun();
                String responseQuestion = responsesQuestionService.getResponsesQuestion(divergenceId, questionID).getBody().getComments().get(loopEachQuestionAnswer).getText();
                responsesRun.setText(responseQuestion);

                responses.setIndentationLeft(900);
                responsesRun.setFontFamily("Arial");
                responsesRun.setFontSize(12);
                responsesRun.addBreak();

                quantityCommentsAnswers = responsesQuestionService.getResponsesQuestion(divergenceId, questionID).getBody().getComments().get(loopEachQuestionAnswer).getReplies().size();

                // Loop para gerar todos os comentários presentes nas respostas das pessoas
                for(int loopEachCommentReply= 0; loopEachCommentReply < quantityCommentsAnswers; loopEachCommentReply++){

                    commentsReplies = responsesQuestionService.getResponsesQuestion(divergenceId, questionID).getBody().getComments().get(loopEachQuestionAnswer).getReplies().get(loopEachCommentReply).getText();

                    XWPFParagraph comments = document.createParagraph();
                    XWPFRun commentsRun = comments.createRun();
                    commentsRun.setText(commentsReplies);

                    comments.setIndentationLeft(1800);
                    commentsRun.setFontFamily("Arial");
                    commentsRun.setFontSize(12);
                    commentsRun.addBreak();

                }

            }
        }

        document.write(response.getOutputStream());
        document.close();
    }


}
