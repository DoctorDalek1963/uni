import java.util.Scanner;

class I2C {
  public static void main(String args[]) {
    System.out.print("Enter measurement in inches: ");
    Scanner scanner = new Scanner(System.in);
    double inches = scanner.nextDouble();
    scanner.close();
    double centimetres = inches * 2.5;
    System.out.println("The measurement in centimetres is: " + centimetres);
  }
}
