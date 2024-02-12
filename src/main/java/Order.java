import java.util.List;

public class Order {
    protected static String firstName;
    protected static String lastName;
    protected static String address;
    protected static int metroStation;
    protected static String phone;
    protected static int rentTime;
    protected static String deliveryDate;
    protected static String comment;
    protected static List<String> color;

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        Order.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        Order.lastName = lastName;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Order.address = address;
    }

    public static int getMetroStation() {
        return metroStation;
    }

    public static void setMetroStation(int metroStation) {
        Order.metroStation = metroStation;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Order.phone = phone;
    }

    public static int getRentTime() {
        return rentTime;
    }

    public static void setRentTime(int rentTime) {
        Order.rentTime = rentTime;
    }

    public static String getDeliveryDate() {
        return deliveryDate;
    }

    public static void setDeliveryDate(String deliveryDate) {
        Order.deliveryDate = deliveryDate;
    }

    public static String getComment() {
        return comment;
    }

    public static void setComment(String comment) {
        Order.comment = comment;
    }

    public static List<String> getColor() {
        return color;
    }

    public static void setColor(List<String> color) {
        Order.color = color;
    }

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
