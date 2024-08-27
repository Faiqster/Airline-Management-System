public class Passenger extends Person {
    String passengerID;
    String cnic;
    String phone;
    String email;       
    String vaccinated;
    String flightID;
    String origin;
    String destination;
    String price;
    String departureTime;
    String arrivalTime;
    String profile;

    public Passenger(String passengerID, String name, String cnic,
                     String phone, String email, String vaccinated,
                     String flightID, String origin, String destination,
                     String price, String departureTime, String arrivalTime,
                     String profile) {
        super(name, "Unknown", "Unknown");
        this.passengerID = passengerID;
        this.cnic = cnic;
        this.phone = phone;
        this.email = email;
        this.vaccinated = vaccinated;
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "<html>Passenger ID: " + passengerID + "<br>Origin: " + origin + "<br>CNIC: " + cnic +
                "<br>Phone: " + phone + "<br>Email: " + email + "<br>Vaccinated: " + vaccinated +
                 "<br>Name: " + name + "<br>Destination: " + destination +
                "<br>Price: " + price + "<br>Departure Time: " + departureTime +
                "<br>Arrival Time: " + arrivalTime + "</html>";
    }
}
