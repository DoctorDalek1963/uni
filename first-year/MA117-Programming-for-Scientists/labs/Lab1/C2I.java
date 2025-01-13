import java.util.Scanner;

class C2I {
  public static void main(String args[]) {
    System.out.print("Enter measurement in centimetres: ");
    Scanner scanner = new Scanner(System.in);
    double centimetres = scanner.nextDouble();
    scanner.close();
    double inches = centimetres / 2.5;
    System.out.println("The measurement in inches is: " + inches);
  }
}