import java.util.Scanner;

public class PopDensity {
    public static void main(String[] args) {
        System.out.print("Enter the radius of the planet in kilometres: ");
        Scanner scanner = new Scanner(System.in);
        double radius = scanner.nextDouble();
        System.out.print("Enter the total population of the planet: ");
        long population = scanner.nextLong();
        scanner.close();

        double surfaceArea = 4.0 * Math.PI * radius * radius;
        double density = (double)population / surfaceArea;
        System.out.println("The naive population density (ignoring oceans) is " + density + " people per square kilometre");
    }
}