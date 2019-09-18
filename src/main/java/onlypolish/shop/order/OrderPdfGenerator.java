package onlypolish.shop.order;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import onlypolish.dashboard.StringFromDateGenerator;
import onlypolish.shop.product.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public enum OrderPdfGenerator {
    INSTANCE;

    private String generateFileName(Date date) {
        String fileName = "orders/orderlist_";
        fileName += StringFromDateGenerator.INSTANCE.stringFromDateGenerator(date);
        fileName += ".pdf";
        return fileName;
    }

    private Map<Product, Integer> getNumberOfProductsFromProductsList(List<Product> products){
        Map<Product, Integer> numberOfProducts = new HashMap<>();
        for(Product product : products){
            if(numberOfProducts.containsKey(product)){
                numberOfProducts.replace(product, numberOfProducts.get(product)+1);
            }else {
                numberOfProducts.put(product, 1);
            }
        }
        return numberOfProducts;
    }

    private Font titleFont() throws IOException, DocumentException {
        BaseFont nationalBase = BaseFont.createFont("c:\\\\windows\\\\fonts\\\\Times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(nationalBase, 16f, Font.NORMAL, BaseColor.BLACK);
    }

    private Font regularFont() throws IOException, DocumentException {
        BaseFont nationalBase = BaseFont.createFont("c:\\\\windows\\\\fonts\\\\Times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(nationalBase, 12f, Font.NORMAL, BaseColor.BLACK);
    }

    private Font boldFont() throws IOException, DocumentException {
        BaseFont nationalBase = BaseFont.createFont("c:\\\\windows\\\\fonts\\\\Times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(nationalBase, 12f, Font.BOLD, BaseColor.BLACK);
    }

    private void addTableHeader(PdfPTable table) throws IOException, DocumentException {
        Stream.of(OrderPdfValues.TABLE_HEADER_PRODUCT_ID,
                OrderPdfValues.TABLE_HEADER_PRODUCT_NAME,
                OrderPdfValues.TABLE_HEADER_PRODUCT_DESCRIPTION,
                OrderPdfValues.TABLE_HEADER_PRODUCT_NUMBER,
                OrderPdfValues.TABLE_HEADER_PRODUCT_PRICE_PER_ITEM,
                OrderPdfValues.TABLE_HEADER_PRODUCT_PRICE)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    try {
                        header.setPhrase(new Phrase(columnTitle, boldFont()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    table.addCell(header);
                });
    }

    private void addTableRow(PdfPTable table, Product product, int number) throws IOException, DocumentException {
        table.addCell(new Phrase(String.valueOf(product.getId()), regularFont()));
        table.addCell(new Phrase(product.getName(), regularFont()));
        table.addCell(new Phrase(product.getDescription(), regularFont()));
        table.addCell(new Phrase(String.valueOf(number), regularFont()));
        table.addCell(new Phrase(String.valueOf(product.getPrice()), regularFont()));
        table.addCell(new Phrase(String.valueOf(product.getPrice()*number), regularFont()));
    }

    public String generateOrderListPdf(List<Order> orders) throws IOException, DocumentException {
        String fileName = generateFileName(new Date());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        document.add(new Phrase(OrderPdfValues.TITLE, titleFont()));
        for(Order order : orders){
            document.add(new LineSeparator());
            document.add(new Phrase(OrderPdfValues.getOrderNumber(order), boldFont()));
            document.add(new Phrase(OrderPdfValues.getOrderStatus(order), boldFont()));
            document.add(new Phrase(OrderPdfValues.getOrderUser(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderUserLogin(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderUserEmail(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderUserPhone(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderUserAddress(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderInvoiceAddress(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderPaymentWay(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderDeliveryWay(order), regularFont()));
            document.add(new Phrase(OrderPdfValues.getOrderDeliveryAddress(order), regularFont()));
            Map <Product, Integer> productsNumber = getNumberOfProductsFromProductsList(order.getProducts());
            PdfPTable table = new PdfPTable(6);
            addTableHeader(table);
            for(Product product : productsNumber.keySet()){
                addTableRow(table, product, productsNumber.get(product));
            }
            document.add(table);
            document.add(new Phrase(OrderPdfValues.getOrderTotalPrice(order), boldFont()));
        }
        document.close();
        return fileName;
    }
}
