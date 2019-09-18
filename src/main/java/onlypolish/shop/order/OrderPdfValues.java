package onlypolish.shop.order;

public class OrderPdfValues {
    private OrderPdfValues() throws Exception{
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    private static final String ORDER_NUMBER = "Nr zamówienia: %s\n";
    private static final String ORDER_STATUS = "Status: %s\n";
    private static final String ORDER_USER = "Zamawiający: %s %s\n";
    private static final String ORDER_USER_LOGIN = "Login: %s\n";
    private static final String ORDER_USER_EMAIL = "Email: %s\n";
    private static final String ORDER_USER_PHONE = "Nr telefonu: %s\n";
    private static final String ORDER_USER_ADDRESS = "Adres: %s, %s %s\n";
    private static final String ORDER_INVOICE_ADDRESS = "Adres do faktury: %s, %s %s\n";
    private static final String ORDER_PAYMENT_WAY = "Sposób płatności: %s\n";
    private static final String ORDER_DELIVERY_WAY = "Sposób dostawy: %s\n";
    private static final String ORDER_DELIVERY_ADDRESS = "Adres dostawy: %s, %s %s\n";
    private static final String ORDER_TOTAL_PAYMENT = "Cena całkowita: %s\n\n";

    private static final String WAITING_FOR_PAYING = "Oczekuje na wpłatę";
    private static final String PAID = "Opłacone";
    private static final String PAYU = "Przelew internetowy (PayU)";
    private static final String CARD = "Płatność kartą";
    private static final String ONDELIVERY = "Płatność za pobraniem";
    private static final String INPOST = "Paczkomaty InPost";
    private static final String POCZTA_POLSKA = "Poczta Polska";
    private static final String DPD = "Kurier DPD";

    public static final String TITLE = "Twoje zamówienia oczekujące na sfinalizowanie\n\n";

    public static final String TABLE_HEADER_PRODUCT_ID = "Id";
    public static final String TABLE_HEADER_PRODUCT_NAME = "Nazwa produktu";
    public static final String TABLE_HEADER_PRODUCT_DESCRIPTION = "Opis produktu";
    public static final String TABLE_HEADER_PRODUCT_NUMBER = "Ilość";
    public static final String TABLE_HEADER_PRODUCT_PRICE_PER_ITEM = "Cena";
    public static final String TABLE_HEADER_PRODUCT_PRICE = "Cena całkowita";

    public static String getOrderNumber(Order order){
        return String.format(ORDER_NUMBER, order.getOrderNumber());
    }

    public static String getOrderStatus(Order order){
        if(order.isPaid()) return String.format(ORDER_STATUS, PAID);
        if(order.isPlaced()) return String.format(ORDER_STATUS, WAITING_FOR_PAYING);
        else return "";
    }

    public static String getOrderUser(Order order){
        return String.format(ORDER_USER, order.getUserName(), order.getUserSurname());
    }

    public static String getOrderUserLogin(Order order){
        return String.format(ORDER_USER_LOGIN, order.getUserLogin());
    }

    public static String getOrderUserEmail(Order order){
        return String.format(ORDER_USER_EMAIL, order.getUserEmail());
    }

    public static String getOrderUserPhone(Order order){
        return String.format(ORDER_USER_PHONE, order.getUserPhone());
    }

    public static String getOrderUserAddress(Order order){
        return String.format(ORDER_USER_ADDRESS, order.getUserAddress(), order.getUserPostalCode(), order.getUserCity());
    }

    public static String getOrderInvoiceAddress(Order order){
        return String.format(ORDER_INVOICE_ADDRESS, order.getInvoiceAddress(), order.getInvoicePostalCode(), order.getInvoiceCity());
    }

    public static String getOrderPaymentWay(Order order){
        if(order.isCardPayment()) return String.format(ORDER_PAYMENT_WAY, CARD);
        if(order.isPayUPayment()) return String.format(ORDER_PAYMENT_WAY, PAYU);
        if(order.isOnDeliveryPayment()) return  String.format(ORDER_PAYMENT_WAY, ONDELIVERY);
        else return "";
    }

    public static String getOrderDeliveryWay(Order order){
        if(order.getDeliveryWay().isDpd()) return String.format(ORDER_DELIVERY_WAY, DPD);
        if(order.getDeliveryWay().isInpost()) return String.format(ORDER_DELIVERY_WAY, INPOST);
        if(order.getDeliveryWay().isPocztaPolska()) return String.format(ORDER_DELIVERY_WAY, POCZTA_POLSKA);
        else return  "";
    }

    public static String getOrderDeliveryAddress(Order order){
        return String.format(ORDER_DELIVERY_ADDRESS, order.getReceiptAddress(), order.getInvoicePostalCode(), order.getReceiptCity());
    }

    public static String getOrderTotalPrice(Order order){
        return String.format(ORDER_TOTAL_PAYMENT, order.getTotalPrice());
    }
}
