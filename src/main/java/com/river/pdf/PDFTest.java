package com.river.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.itextpdf.text.Font.BOLDITALIC;

/**
 * @author: he.feng
 * @date: 10:40 2018/2/5
 * @desc:
 **/
public class PDFTest {

    @Test
    public void AddContentToPDF() throws Exception{

        PdfReader reader = new PdfReader("d:/666.pdf");
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream("d:/777.pdf"));
        BaseFont bf =  BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        String json = "[{\"x\":\"0.4712\",\"y\":\"0.85\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.5442\",\"y\":\"0.85\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.5923\",\"y\":\"0.85\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.2385\",\"y\":\"0.19\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.5923\",\"y\":\"0.77\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.2385\",\"y\":\"0.73\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.2692\",\"y\":\"0.69\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"1\",\"value\":\"小菜鸡\"},{\"x\":\"0.3558\",\"y\":\"0.4\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.70\",\"y\":\"0.398\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.3558\",\"y\":\"0.37\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.692\",\"y\":\"0.37\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.3663\",\"y\":\"0.34\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.465\",\"y\":\"0.34\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.37\",\"y\":\"0.32\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.35\",\"y\":\"0.295\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.452\",\"y\":\"0.295\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.35\",\"y\":\"0.27\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.705\",\"y\":\"0.3034\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"},{\"x\":\"0.3558\",\"y\":\"0.25\",\"fontSize\":\"14\",\"type\":\"text\",\"pageNum\":\"2\",\"value\":\"小菜鸡\"}]";
        JSONArray jsonArray = JSONArray.parseArray(json);
        int size = jsonArray.size();
        if(size > 0) {
            for (int i = 0; i< size; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                float percentX = jsonObject.getFloat("x");
                float percentY = jsonObject.getFloat("y");
                int fontSize = jsonObject.getInteger("fontSize");
                int pageNum = jsonObject.getInteger("pageNum");
                String value = jsonObject.getString("value");
                Document document = new Document(reader.getPageSize(pageNum));
                Rectangle pageSize = document.getPageSize();
                float width = pageSize.getWidth();
                float height = pageSize.getHeight();
                System.out.println("width=" + width);
                System.out.println("height" + height);
                float x = (float) (width * percentX);
                float y = (float) (height * percentY);
                System.out.println("x=" + x);
                System.out.println("y=" + y);
                // get object for writing over the existing content;
                // you can also use getUnderContent for writing in the bottom layer
                PdfContentByte over = stamper.getUnderContent(pageNum);
                // write text
                over.beginText();
                // set font and size
                over.setFontAndSize(bf, fontSize);
                // set x,y position (0,0 is at the bottom left)
                over.setTextMatrix(x, y);
                // set text
                over.showText(value);
                System.out.println(value);
                over.endText();
            }
            stamper.close();
        }
    }

    @Test
    public void createPDF() throws Exception{
        Document document = new Document(PageSize.A4);
        OutputStream outputStream = new FileOutputStream("d:/888.pdf");
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        BaseFont baseFont =  BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font chinese = new Font(baseFont, 20, Font.BOLDITALIC);
        document.open();
        document.add(new Paragraph("如果要设定第一页的页面属性，这些方法必须在文档打开之前调用。\n" +
                "　　对于PDF文档，iText还提供了文档的显示属性，通过调用书写器的setViewerPreferences方法可以控制文档打开时Acrobat Reader的显示属性，如是否单页显示、是否全屏显示、是否隐藏状态条等属性。\n" +
                "　　另外，iText也提供了对PDF文件的安全保护，通过书写器(Writer)的setEncryption方法，可以设定文档的用户口令、只读、可打印等属性。\n" +
                "　　添加文档内容\n" +
                "　　所有向文档添加的内容都是以对象为单位的，如Phrase、Paragraph、Table、Graphic对象等。比较常用的是段落(Paragraph)对象，用于向文档中添加一段文字。\n" +
                "　　三、文本处理\n" +
                "　　iText中用文本块(Chunk)、短语(Phrase)和段落(paragraph)处理文本。\n" +
                "　　文本块(Chunk)是处理文本的最小单位，有一串带格式(包括字体、颜色、大小)的字符串组成。如以下代码就是产生一个字体为HELVETICA、大小为10、带下划线的字符串：\n" +
                "　　Chunk chunk1 = new Chunk(\"This text is underlined\", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE));\n" +
                "　　短语(Phrase)由一个或多个文本块(Chunk)组成，短语(Phrase)也可以设定字体，但对于其中以设定过字体的文本块 (Chunk)无效。通过短语(Phrase)成员函数add可以将一个文本块(Chunk)加到短语(Phrase)中，如：phrase6.add(chunk);\n" +
                "　　段落(paragraph)由一个或多个文本块(Chunk)或短语(Phrase)组成，相当于WORD文档中的段落概念，同样可以设定段落的字体大小、颜色等属性。另外也可以设定段落的首行缩进、对齐方式(左对齐、右对齐、居中对齐)。通过函数setAlignment可以设定段落的对齐方式， setAlignment的参数1为居中对齐、2为右对齐、3为左对齐，默认为左对齐。\n" +
                "　　四、表格处理\n" +
                "　　iText中处理表格的类为：com.lowagie.text.Table和com.lowagie.text.PDF.PDFPTable，对于比较简单的表格处理可以用com.lowagie.text.Table，但是如果要处理复杂的表格，这就需要 com.lowagie.text.PDF.PDFPTable进行处理。这里就类com.lowagie.text.Table进行说明。\n" +
                "　　类com.lowagie.text.Table的构造函数有三个：\n" +
                "　　①Table (int columns)\n" +
                "　　②Table(int columns, int rows)\n" +
                "　　③Table(Properties attributes)\n" +
                "　　参数columns、rows、attributes分别为表格的列数、行数、表格属性。创建表格时必须指定表格的列数，而对于行数可以不用指定。\n" +
                "　　建立表格之后，可以设定表格的属性，如：边框宽度、边框颜色、衬距(padding space 即单元格之间的间距)大小等属性。下面通过一个简单的例子说明如何使用表格，代码如下：",chinese));
        document.close();
        pdfWriter.close();
        outputStream.close();

    }


}
