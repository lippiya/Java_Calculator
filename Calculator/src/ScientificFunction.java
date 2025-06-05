
public class ScientificFunction extends ScientificAbstract implements BasicArithmetic, CalculatorMemory,
        ExponentialFunctions, HyperbolicFunctions, TrigonometricFunctions {
    private ResultHandler resultHandler;
    private MemoryHandler memoryHandler;

    public ScientificFunction(double num, String sign, ResultHandler resultHandler, MemoryHandler memoryHandler) {
        super(num, sign);
        this.resultHandler = resultHandler;
        this.memoryHandler = memoryHandler;
    }

    @Override
    public double square() {
        return num * num;
    }

    @Override
    public double cube() {
        return num * num * num;
    }

    @Override
    public double customPower(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    @Override
    public double tenthPower() {
        return Math.pow(10, num);
    }

    @Override
    public double fraction() {
        return 1 / num;
    }

    @Override
    public double exponential() {
        return 2.718281828459045;
    }

    @Override
    public double exponentialPower() {
        return Math.pow(2.718281828459045, num);
    }

    @Override
    public double sqroot() {
        return Math.sqrt(num);
    }

    @Override
    public double cubeRoot() {
        return Math.cbrt(num);
    }

    @Override
    public double customRoot(double base, double exponent) {
        return Math.pow(base, 1.0 / exponent);
    }

    @Override
    public double ln() {
        return Math.log(num);
    }

    @Override
    public double log() {
        return Math.log10(num);
    }

    @Override
    public double factorial() {
        if (num < 0) {
            resultHandler.showError("Factorial is not defined for negative number");
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        double result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    @Override
    public double sin() {
        double resultInRadians = convertDegreeToRadian(num);
        return Math.sin(resultInRadians);
    }

    @Override
    public double cos() {
        double resultInRadians = convertDegreeToRadian(num);
        return Math.cos(resultInRadians);
    }

    @Override
    public double tan() {
        if (num == 90) {
            return Double.NaN;
        }
        double resultInRadians = convertDegreeToRadian(num);
        return Math.tan(resultInRadians);
    }

    @Override
    public double arcSin() {
        double resultInRadians = convertDegreeToRadian(num);
        return Math.asin(resultInRadians);
    }

    @Override
    public double arcCos() {

        double resultInRadians = convertDegreeToRadian(num);
        return Math.acos(resultInRadians);
    }

    @Override
    public double arcTan() {
        double resultInRadians = convertDegreeToRadian(num);
        return Math.atan(resultInRadians);
    }

    @Override
    public double sinH() {
        double resultInDegrees = Math.toDegrees(Math.sinh(num));
        return resultInDegrees;
    }

    @Override
    public double cosH() {
        double resultInDegrees = Math.toDegrees(Math.cosh(num));
        return resultInDegrees;
    }

    @Override
    public double tanH() {
        double resultInDegrees = Math.toDegrees(Math.tanh(num));
        return resultInDegrees;
    }

    @Override
    public double arcTanH() {
        double resultInRadians = convertDegreeToRadian(num);
        return inverse(resultInRadians, "atanh");
    }

    @Override
    public double arcSinH() {
        double resultInRadians = convertDegreeToRadian(num);
        return inverse(resultInRadians, "asinh");
    }

    @Override
    public double arcCosH() {
        double resultInRadians = convertDegreeToRadian(num);
        return inverse(resultInRadians, "atanh ");
    }

    @Override
    public double singleE() {
        return 2.718281828459045;
    }

    @Override
    public double rad() {
        return Math.toRadians(num);
    }

    @Override
    public double mc() {
        return memoryHandler.resetMemory();
    }

    @Override
    public double mread() {
        return memoryHandler.getMemoryValue();
    }

    @Override
    public double mplus() {
        return memoryHandler.setMemoryValue(num);
    }

    @Override
    public double mminus() {
        return memoryHandler.subtractFromMemory(num);
    }

    public double convertDegreeToRadian(double num) {
        double result = (num * Math.PI) / 180;
        return result;
    }

    public double inverse(double num, String type) {
        double result = 0;
        if (type.equals("asinh")) {
            result = Math.log(num + Math.sqrt((num * num) + 1));
        } else if (type.equals("acosh")) {
            result = Math.log(num + Math.sqrt((num * num) - 1));
        } else if (type.equals("atanh")) {
            result = 0.5 * Math.log((1 + num) / (1 - num));
        }
        return result;
    }

}
