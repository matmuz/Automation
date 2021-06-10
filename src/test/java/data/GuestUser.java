package data;

/**
 * Class responsible for guest user creation
 */

public class GuestUser extends User {

    private static final GuestUser guestUser = new GuestUser();
    private final String address;
    private final String city;
    private final String postalCode;
    public final String country;

    private GuestUser(){
        super();
        address = faker.address()
                .streetName();
        city = faker.address()
                .city();
        postalCode = "" + (random.nextInt(89999) + 10000);
        country = "Poland";
    }

    public static GuestUser getUser() {
        return guestUser;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        StringBuilder stringBuilder = new StringBuilder(postalCode);
        stringBuilder.insert(2, "-");
        return stringBuilder.toString();
    }

    public String getCountry() {
        return country;
    }
}