public class Main {
    public static int celsiusToFahrenheit(int celsius) {
        return celsius * 9 / 5 + 32;
    }

    public static void main(String[] args) {
        int[] degrees = {-10, 0, 25, 30, 50, 100};
        for (int degree: degrees) {
            System.out.printf(
                    "%d graden celsius is %d graden fahrenheit\n",
                    degree,
                    celsiusToFahrenheit(degree)
            );
        }
    }
}