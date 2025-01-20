import java.util.Scanner;

public class QuadSolver {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter a: ");
		double a = s.nextDouble();
		System.out.print("Enter b: ");
		double b = s.nextDouble();
		System.out.print("Enter c: ");
		double c = s.nextDouble();
		s.close();

		double discriminant = b * b - 4 * a * c;
		if (discriminant < 0) {
			System.out.println("No real solutions");
		} else if (discriminant == 0) {
			double sol = -b / (2 * a);
			System.out.format("Solution is x = %.4f%n", sol);
		} else {
			double sol1 = (-b + Math.sqrt(discriminant)) / (2 * a);
			double sol2 = (-b - Math.sqrt(discriminant)) / (2 * a);
			System.out.format("Solutions are x = %.4f or x = %.4f%n", sol1, sol2);
		}
	}
}
