public class Main {
    public static int celsiusToFahrenheit(int celsius) {
        // FIXME: algorithm is broken for integers below 0.
        int t1 = celsius << 3;
        int t2 = t1 + celsius;
        int t3 = 0;
        for (int t4 = 8; t4 > 0; t4 = t4 - 1) {
            int t5 = 5 << t4;
            t2 = t2 - t5;
            if (t2 >= 0) {
                int t6 = (1 << t4);
                t3 = t3 + t6;
            }
            else {
                t2 = t2 + t5;
            }
        }
        int t7 = t3 + 32;
        return t7;
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