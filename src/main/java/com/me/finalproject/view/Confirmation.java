/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.view;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.me.finalproject.pojo.Booking;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

/**
 *
 * @author karnvadaliya
 */
public class Confirmation extends AbstractPdfView {
    
    public Confirmation() {
        setContentType("application/pdf");
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document doc, PdfWriter writer, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        Booking b = (Booking) map.get("booking");
        Font boldfont = FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD);
        Paragraph p = new Paragraph("Reservation Confirmation", boldfont);
        p.setAlignment(Element.ALIGN_CENTER);
        
        
        doc.add(p);
        doc.add(new Paragraph(" "));
        Font font = FontFactory.getFont(FontFactory.HELVETICA,12, Font.BOLD);
       
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPhrase(new Phrase("Booking ID", font));
        table.addCell(cell);
        table.addCell(String.valueOf(b.getBookId()));
        
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        cell.setPhrase(new Phrase("Booking Date", font));
        table.addCell(cell);
        table.addCell(formatter.format(b.getBookingDate()));
        
        int t=b.getTimeSlot();
        String tod="AM";
        
        if(t>=12)
        {
            tod="PM";
            if(t>12)
                t=t-12;
        }
            
        cell.setPhrase(new Phrase("Booking Time", font));
        table.addCell(cell);
        table.addCell(String.valueOf(t)+" "+tod);
        
        cell.setPhrase(new Phrase("Customer Name", font));
        table.addCell(cell);
        table.addCell(b.getCustomers().getName());
        
        cell.setPhrase(new Phrase("Customer Email", font));
        table.addCell(cell);
        table.addCell(b.getCustomers().getUemail());
        
        cell.setPhrase(new Phrase("Restaurant Name", font));
        table.addCell(cell);
        table.addCell(b.getRestaurants().getName());
        
        cell.setPhrase(new Phrase("Restaurant Phone", font));
        table.addCell(cell);
        table.addCell(String.valueOf(b.getRestaurants().getNumber()));
        
        cell.setPhrase(new Phrase("Restaurant Address", font));
        table.addCell(cell);
        table.addCell(b.getRestaurants().getStreet()+"\n"+b.getRestaurants().getCity()+"\n"+ String.valueOf(b.getRestaurants().getZipcode()));
       
        doc.add(table);
    }
    
}
