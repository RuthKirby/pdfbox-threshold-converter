package org.box.pdfconverter;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Converter {

    public static void pdfToImage(String pathname) throws IOException {
        File pdfFile = new File(pathname);
        int threshold = 170;
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            System.out.printf("Success. Document has %d pages.", document.getNumberOfPages());
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int page = 0;
            int[] white = {255};
            int[] black = {0};

            for(PDPage pdPage: document.getPages()) {
                BufferedImage greyImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.GRAY);
                BufferedImage blackWhiteImage = new BufferedImage(greyImage.getWidth(),
                        greyImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
                for(int y = 0; y < greyImage.getHeight(); y++) {
                    for(int x = 0; x < greyImage.getWidth(); x++) {
                        int[] greyScaleValue = greyImage.getRaster().getPixel(x, y, new int[1]);
                        blackWhiteImage.getRaster().setPixel(x, y, greyScaleValue[0] > threshold ? white : black);
                    }
                }
                page++;
                ImageIOUtil.writeImage(blackWhiteImage, "src/main/resources/image" + "-" + page + ".jpg", 300);
            }

        }
    }

    public static void imageToPdf() throws IOException {
        String[] formats = {"png", "jpg", "jpeg", "gif", "bmp"};
        // Create blank PDDocument as the new PDF shell
        PDDocument document = new PDDocument();
        // Read in the directory of images
        Collection<File> files = FileUtils.listFiles(new File("src/main/resources/"), formats, false);
        // Iterate over each image and create a PDF page from it
        for(File file : files) {
            PDImageXObject imgObj = PDImageXObject.createFromFileByContent(file, document);
            int width = imgObj.getWidth();
            int height = imgObj.getHeight();
            PDPage page = new PDPage(new PDRectangle( width, height));
            document.addPage(page);
            var contentStream = new PDPageContentStream( document, page );
            contentStream.drawImage( imgObj, 0, 0 );
            contentStream.close();
        }
        document.save("src/main/resources/testgray.pdf");
        document.close();
    }

}
