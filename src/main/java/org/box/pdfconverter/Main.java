package org.box.pdfconverter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello pdfs!");
        try {
            Converter.pdfToImage("src/main/resources/testcolours.pdf");
            Converter.imageToPdf();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}