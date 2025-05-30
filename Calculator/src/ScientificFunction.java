

public class ScientificFunction extends ScientificAbstract implements BasicArithmetic, CalculatorMemory, ExponentialFunctions, HyperbolicFunctions, SpecialFunctions, TrigonometricFunctions {
    InputSection inputSection;
    // Constructor
    public ScientificFunction(double num, String sign, InputSection inputSection) {
        super(num, sign);
        this.inputSection=inputSection;
    }

    // Method to calculate square of a number
    @Override
	public double square() {
        return num * num;
    }

    // Method to calculate cube of a number
    @Override
    public double cube() {
        return num * num * num;
    }

    // Method to calculate custom power of a number
    @Override
    public double customPower(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // Method to calculate tenth power of a number
    @Override
    public double tenthPower() {
        return Math.pow(10, num);
    }

    // Method to calculate reciprocal of a number
    @Override
    public double fraction() {
        return 1 / num;
    }

    // Method to return Euler's number (e)
    @Override
    public double exponential() {
        return 2.718281828459045;
    }

    // Method to calculate exponential power
    @Override
    public double exponentialPower() {
        return Math.pow(2.718281828459045, num);
    }

    // Method to calculate square root of a number
    @Override
    public double sqroot() {
        return Math.sqrt(num);
    }

    // Method to calculate cube root of a number
    @Override
    public double cubeRoot() {
        return Math.cbrt(num);
    }

    // Method to calculate custom root of a number
    @Override
    public double customRoot(double base, double exponent) {
        return Math.pow(base, 1.0 / exponent);
    }

    // Method to calculate natural logarithm of a number
    @Override
    public double ln() {
        return Math.log(num);
    }

    // Method to calculate base-10 logarithm of a number
    @Override
    public double log() {
        return Math.log10(num);
    }

    // Method to calculate factorial of a number
    @Override
    public double factorial() {
        if (num < 0) {
            inputSection.setInputField("NaN");
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        double result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    // Method to calculate sine of an angle in degrees
    @Override
    public double sin() {
        double resultInRadians = degreeToRadian(num);
        return Math.sin(resultInRadians);
    }

    // Method to calculate cosine of an angle in degrees
    @Override
    public double cos() {
        double resultInRadians = degreeToRadian(num);
        return Math.cos(resultInRadians);
    }

    // Method to calculate tangent of an angle in degrees
    @Override
    public double tan() {
        if(num==90){
            return Double.NaN;
        }
        double resultInRadians = degreeToRadian(num);
        return Math.tan(resultInRadians);
    }

    @Override
    public double arcSin(){
        double resultInRadians = degreeToRadian(num);
        return Math.asin(resultInRadians);
    }
    @Override
    public double arcCos() {
        
        double resultInRadians=degreeToRadian(num);
        return Math.acos(resultInRadians);
    }
    @Override
    public double arcTan() {
        double resultInRadians=degreeToRadian(num);
        return Math.atan(resultInRadians);
    }



    // Method to calculate hyperbolic sine of an angle in degrees
    @Override
    public double sinH() {
        double resultInDegrees = Math.toDegrees(Math.sinh(num));
        return resultInDegrees;
    }

    // Method to calculate hyperbolic cosine of an angle in degrees
    @Override
    public double cosH() {
        double resultInDegrees = Math.toDegrees(Math.cosh(num));
        return resultInDegrees;
    }

    // Method to calculate hyperbolic tangent of an angle in degrees
    @Override
    public double tanH() {
        double resultInDegrees = Math.toDegrees(Math.tanh(num));
        return resultInDegrees;
    }

    @Override
    public double arcTanH() {
        double resultInRadians=degreeToRadian(num);
        return inverse(resultInRadians, "atanh");
    }

    @Override
    public double arcSinH() {
        double resultInRadians=degreeToRadian(num);
        return inverse(resultInRadians, "asinh");
    }
    @Override
    public double arcCosH() {
        double resultInRadians=degreeToRadian(num);
        return inverse(resultInRadians, "atanh ");
    }

    // Method to return Euler's number (e)
    @Override
    public double singleE() {
        return 2.718281828459045;
    }



    // Method to convert degrees to radians
    @Override
    public double rad() {
        return Math.toRadians(num);
    }

    @Override
    public double mc(){
        return inputSection.resetMemory();
    }

    @Override
    public double mread(){
        return inputSection.getMemoryValue();
    }

    @Override
    public double mplus(){
        return inputSection.setMemoryValue(num);
    }

    @Override
    public double mminus(){ 
        return inputSection.subtractFromMemory(num);
    }


    // Method to convert degrees to radians
    public double degreeToRadian(double num) {
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
