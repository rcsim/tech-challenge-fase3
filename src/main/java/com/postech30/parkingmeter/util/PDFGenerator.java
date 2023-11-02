package com.postech30.parkingmeter.util;



import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

public class PDFGenerator {

    public static void generatePDFFromHTML(String vehiclePlate, Instant checkIn, Instant checkOut, double value) throws IOException {
        File pdfFile = new File("src/main/resources/output/recibo.pdf");
        pdfFile.getParentFile().mkdirs();

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(pdfFile)));
        ConverterProperties converterProperties = new ConverterProperties();
        String html = transformHtml(generateHtmlForPdf(),vehiclePlate,checkIn,checkOut,value);
        HtmlConverter.convertToPdf(html, pdfDocument, converterProperties);
    }

    private static String  generateHtmlForPdf(){
        return "<!DOCTYPE html> <html> <head> <meta charset=\"UTF-8\"> <title>Recibo de Pagamento</title> <style> body { font-family: Arial, sans-serif; } .container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; } .header { background-color: #0078d4; color: #fff; padding: 10px; text-align: center; } .content { background-color: #fff; padding: 20px; } .footer { background-color: #0078d4; color: #fff; padding: 10px; text-align: center; } </style> </head> <body> <div class=\"container\"> <div class=\"header\"> <h1>Recibo de Pagamento</h1> </div> <div class=\"content\"> <p> Placa do Veículo: {vehicle}</p> <p>Horário de Entrada: {checkIn}</p> <p>Horário de Saída: {checkOut}</p> <p>Valor Pago: R$ {valorPago}</p> </div> <div class=\"footer\"> <p>&copy; 2023 Estacionamento XYZ</p> </div> </div> </body> </html>";
    }
    private static  String transformHtml(String html,String vehicleName, Instant CheckIn,Instant CheckOut, double valorPago){
        String htmlFormatted = html;
        htmlFormatted = htmlFormatted.replace("{vehicle}", vehicleName);
        htmlFormatted = htmlFormatted.replace("{checkIn}", CheckIn.toString());
        htmlFormatted = htmlFormatted.replace("{checkOut}", CheckOut.toString());
        htmlFormatted = htmlFormatted.replace("{valorPago}",""+valorPago);

        return htmlFormatted;



    }
}
