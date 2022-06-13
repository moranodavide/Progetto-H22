package model.booking.ticket;

import com.google.zxing.WriterException;
import model.persistence.entity.Booking;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe che si occupa della creazione e della gestione di un biglietto.
 *
 * @author GruppoNoSuchMethod
 */
public class Ticket {
    private final String bookingId;
    private final String name;
    private final String surname;
    private final String iataDep;
    private final String iataArr;
    private final String cityDep;
    private final String cityArr;
    private final String flightNumber;
    private final String date;
    private final String depTime;
    private final String arrTime;
    private final String path =
            "src/it.unipv.po.nsm.aerospin/resources/GeneratedDoc/BoardingPass.pdf";

    //TODO RIVEDERE DOPO MODIFICHE
//    /**
//     * Costruttore dell'oggetto biglietto.
//     *
//     * @param name Nome
//     * @param surname Cognome
//     * @param iataDep Partenza
//     * @param iataArr Arrivo
//     * @param flightNumber Numero del Volo
//     * @param date Data
//     * @param depTime Ora Partenza
//     * @param arrTime Ora Arrivo
//     */
    public Ticket(Booking booking) throws IOException {
        this.bookingId = booking.getId();
        this.name = booking.getPassengerById().getName();
        this.surname = booking.getPassengerById().getSurname();
        this.iataDep = booking.getFlightById().getRouteById().getAirportDep().getIata();
        this.iataArr = booking.getFlightById().getRouteById().getAirportArr().getIata();
        this.cityDep = booking.getFlightById().getRouteById().getAirportDep().getCity();
        this.cityArr = booking.getFlightById().getRouteById().getAirportArr().getCity();
        this.flightNumber = booking.getFlightById().getFlightNumber();
        this.date = booking.getFlightById().getScheduledDate().toString();
        this.depTime = booking.getFlightById().getScheduledTime().toString();
        this.arrTime = booking.getFlightById().getArrivalTime().toString();
        generateTicket();
    }

    private void generateTicket() throws IOException {
        File file = new File(
                "src/it.unipv.po.nsm.aerospin/resources/GeneratedDoc/BoardingPassTemplate.pdf");
        PDDocument document =  PDDocument.load(file);
        PDPage page = document.getPage(0);
        PDFont font = PDType0Font.load(document,
                new File("src/it.unipv.po.nsm.aerospin/resources/fonts/Kollektif.ttf"));
        PDPageContentStream contentStream = new PDPageContentStream(
                document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.beginText();
        contentStream.setFont(font,20);
        float rgb = 0;
        contentStream.setStrokingColor(Color.blue);

        contentStream.newLineAtOffset(360, 720);
            contentStream.showText(name);
        contentStream.newLineAtOffset(250,0);
            contentStream.showText(surname);
        contentStream.setFont(font,70);
        contentStream.newLineAtOffset(-275,275);
        contentStream.showText(iataDep);
        contentStream.newLineAtOffset(300,0);
        contentStream.showText(iataArr);
        contentStream.setFont(font,25);
        contentStream.newLineAtOffset(-275,-190);
        contentStream.showText(flightNumber);
        contentStream.newLineAtOffset(125,0);
        contentStream.showText(date);
        contentStream.newLineAtOffset(175,0);
        contentStream.drawString(depTime);
        contentStream.newLineAtOffset(200,0);
        contentStream.drawString(arrTime);
        contentStream.endText();
        //TODO VEDERE STYLE TICKET

        try {
            QRCode.generate(bookingId + " " + flightNumber);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        PDImageXObject pdImage = PDImageXObject.createFromFile(
                "src/it.unipv.po.nsm.aerospin/resources/GeneratedDoc/qr.png", document);
        contentStream.drawImage(pdImage, 50, 50);
        contentStream.close();
        document.save(path);
        document.close();
    }

    public String getPath(){
        return path;
    }
}
