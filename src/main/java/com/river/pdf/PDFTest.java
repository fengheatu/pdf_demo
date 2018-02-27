package com.river.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.junit.Test;

import java.io.FileOutputStream;

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



}
