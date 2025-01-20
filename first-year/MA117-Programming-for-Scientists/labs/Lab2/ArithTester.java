public class ArithTester {
	public static void main(String[] args) {
		partA();
		partB();
	}

	private static void partA() {
		double x = 4.0, y = 3.0;
		x = x / y;
		System.out.format("x = %.4f, y = %.4f%n", x, y);

		x = 1.0 + 2.0 + 3.0 * 4.0;
		y = (1.0 + 2.0 + 3.0) * 4.0;
		System.out.format("x = %.4f, y = %.4f%n", x, y);
	}

	private static void partB() {
		int x, y = 12, z = 13;
		x = y / z;
		y = z / y;
		System.out.format("x = %d, y = %d, z = %d%n", x, y, z);

		x = 5;
		y = 2;
		x = x % y;
		System.out.format("x = %d, y = %d%n", x, y);

		x = 0;
		y = 0;
		x = (x++) + x;
		y = (++y) + y;
		System.out.format("x = %d, y = %d%n", x, y);

		x = 1;
		y = 0;
		x = x / ++y;
		System.out.format("x = %d, y = %d%n", x, y);

		x = 12;
		x += 12;
		System.out.format("x = %d, y = %d%n", x, y);

		x = 0;
		y = 0;
		x = x *= 2 + 3;
		y = (y *= 2) + 3;
		System.out.format("x = %d, y = %d%n", x, y);
	}
}
