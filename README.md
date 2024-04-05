# PDFbox Threshold Converter
Inspired by - https://tonyjunkes.com/blog/formatting-a-color-pdf-to-grayscale/

## Introduction

Will change a colour PDF into binary black and white by changing each page into a 
buffered grayscale image and then changing each pixel to blackk or white depending on if they are higher
or lower than a given threshold. 

## Instructions 
In the Main.class, change the `Converter.pdfToImage` method parameter to the pathway 
of the colour PDF you want to change to binary black and white. Perhaps
the location of the PDF could be the project's resources folder.

Then run the main method. The Black and White PDF will be located in the resources folder
