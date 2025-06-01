import java.util.*;

class Accessories extends Model{
    String colour;
    String location;
    Accessories(String manufacturer , Scanner sc){
        super(manufacturer , sc);
        System.out.println("Please select your desired color from \n1)Silver\n2)Blue\n3)Yellow:");
        this.colour = sc.nextLine();

        System.out.println("Please select location \n1)Delhi\n2)Bangalore\n3)Hyderabad\n4)Chennai:");
        this.location = sc.nextLine();
    }
    public void printSelection() {
        System.out.println("\nYour Customized Car Details:");
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Model: " + model);
        System.out.println("Transmission: " + transmission);
        System.out.println("Fuel Type: " + fuelType);
        System.out.println("Colour: " + colour);
        System.out.println("Location: " + location);
    }

}

class Model extends Car{
    String model;
    String transmission;
    String fuelType;

    Model(String manufacturer , Scanner sc){
        super(manufacturer);



        if (manufacturer.equalsIgnoreCase("Mahindra")) {
            System.out.println("Please choose your desired model\n1) Scorpio\n2)Thar\n3) Scorpio N\n4)XUV 700");
        } else if (manufacturer.equalsIgnoreCase("Tata")) {
            System.out.println("Please choose your desired model\nPlease choose your desired model\n1) Nexon\n2) Harrier\n3) Safari\n4) Tiago");
        } else if (manufacturer.equalsIgnoreCase("Maruti")) {
            System.out.println("Please choose your desired model\n1) Swift\n2) Baleno\n3) Brezza\n4) Alto");
        } else {
            System.out.println("Unknown manufacturer. Default model selected.");
        }

        this.model = sc.nextLine();
        System.out.println("Please ! \n select transmission variant from either Manual or Automatic:");
        this.transmission = sc.nextLine();

        System.out.println("Please select fuel type from \n1)Diesel\n2)Petrol\n3)CNG:");
        this.fuelType = sc.nextLine();

    }





}

class Car {
    String manufacturer;

    public Car(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}

public class Main

{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter which Manufacturer would you like to explore:\n1) Mahindra\n2) Tata\n3) Maruti");
        String manufacturer = sc.nextLine();

        Accessories customCar = new Accessories(manufacturer, sc);
        customCar.printSelection();

        sc.close();


    }
}