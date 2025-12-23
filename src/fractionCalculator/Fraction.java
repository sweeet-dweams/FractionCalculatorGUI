package src.fractionCalculator;

class Fraction {

    private final long numerator;
    private final long denominator;

    /**
     * This method find the greatest common divisor of 2 numbers using the Euclidean Algorithm
     * @param a - first number
     * @param b - second number
     * @return The GCD of a and b
     */
    private static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    /**
     * Finds the least common multiple (LCM) of longs "a" and "b"
     * @param a - first long
     * @param b - second long
     * @return least common multiple
     */
    private static long lcm(long a, long b) {
        long g = gcd(a, b);
        long div = Math.divideExact(b, g);
        return Math.multiplyExact(a, div);
    }


    /**
     * Adds frac2 to frac1 (frac1 + frac2) by finding a common denominator,
     * adjusting the numerators, adding them, and creating a new Fraction object.
     * @param frac1 - First Fraction to Add.
     * @param frac2 - Second Fraction to Add.
     * @return - The new fraction.
     */
    public static Fraction add(Fraction frac1, Fraction frac2) {

        long newDenominator = Fraction.lcm(frac1.getDenominator(), frac2.getDenominator());

        long newNumerator = Math.addExact(
            Math.multiplyExact(frac1.getNumerator(), Math.divideExact(newDenominator, frac1.getDenominator())), 
            Math.multiplyExact(frac2.getNumerator(), Math.divideExact(newDenominator, frac2.getDenominator()))
        );

        return new Fraction(newNumerator, newDenominator);
    }

    /**
     * Subtracts frac2 from frac1 (frac1 - frac2) by finding a common denominator, 
     * adjusting the numerators, subtracting the numerators, and creating a new Fraction object.
     * @param frac1 - First Fraction to Subtract.
     * @param frac2 - Second Fraction to Subtract.
     * @return - The new fraction.
     */
    public static Fraction subtract(Fraction frac1, Fraction frac2) {

        long newDenominator = Fraction.lcm(frac1.getDenominator(), frac2.getDenominator());
        
        long newNumerator = Math.subtractExact (
            Math.multiplyExact(frac1.getNumerator(), Math.divideExact(newDenominator, frac1.getDenominator())),
            Math.multiplyExact(frac2.getNumerator(), Math.divideExact(newDenominator, frac2.getDenominator()))
        );
        return new Fraction(newNumerator, newDenominator);
    }

    /**
     * Multiplies frac2 with frac1 by multiplying the numerators and denominators.
     * @param frac1 - First Fraction to Multiply.
     * @param frac2 - Second Fraction to Multiply.
     * @return - The new fraction.
     */
    public static Fraction multiply(Fraction frac1, Fraction frac2) {
        
        long n = Math.multiplyExact(frac1.getNumerator(), frac2.getNumerator());
        long d = Math.multiplyExact(frac1.getDenominator(), frac2.getDenominator());
        return new Fraction(n, d);
    }

    /**
     * Divides frac2 from frac1 (frac1 / frac2) by multiplying the reciprocal of frac2 to frac1.
     * @param frac1 - First Fraction to Divide.
     * @param frac2 - Second Fraction to Divide.
     * @return - The new fraction.
     */
    public static Fraction divide(Fraction frac1, Fraction frac2) {

        return Fraction.multiply(frac1, frac2.reciprocal());
    }

    /**
     * Raises a fraction to a power
     * @param frac - Fraction to be exponentiated
     * @param exponent - Exponent (can be positive, negative, or zero)
     * @return New Fraction object of the solution
     */
    public static Fraction pow(Fraction frac, long exponent) {
        if (exponent == 0) {
            return new Fraction(1); // anything^0 = 1
        }

        boolean negativeExp = exponent < 0;
        if (negativeExp) {
            frac = frac.reciprocal();
            exponent = -exponent;
        }

        long baseNum = frac.getNumerator();
        long baseDen = frac.getDenominator();

        long resultNum = 1;
        long resultDen = 1;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                resultNum = Math.multiplyExact(resultNum, baseNum);
                resultDen = Math.multiplyExact(resultDen, baseDen);
            }
            exponent >>= 1; //bitshift right for division by 2
            if (exponent > 0) {
                baseNum = Math.multiplyExact(baseNum, baseNum);
                baseDen = Math.multiplyExact(baseDen, baseDen);
            }
        }

        return new Fraction(resultNum, resultDen);
    }


    /**
     * Takes the absolute value of the fraction.
     * @param frac The fraction to which the absolute value is applied.
     * @return New Fraction object with the result
     */
    public static Fraction abs(Fraction frac) {
        return new Fraction(Math.abs(frac.getNumerator()), frac.getDenominator());
    }

    /**
     * @param numerator - Numerator of the fraction expressed as an integer.
     * @param denominator - Denominator of the fraction expressed as an integer.
     */
    public Fraction(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator Cannot be zero (0).");
        }

        long gcd = Fraction.gcd(numerator, denominator);
        numerator = Math.divideExact(numerator, gcd);
        denominator = Math.divideExact(denominator, gcd);
        if (denominator < 0) {
            denominator *= -1;
            numerator *= -1;
        }

        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Second constructor that takes a long.
     * @param num - Long to be converted to a fraction.
     */
    public Fraction(long num) {
        this.numerator = num;
        this.denominator = 1;
    }

    /**
     * @return Numerator of the fraction
     */
    public long getNumerator() {
        return numerator;
    }

    /**
     * @return Denominator of the fraction
     */
    public long getDenominator() {
        return denominator;
    }

    // METHODS FOR FRACTIONS -----------------------------------------------------------------------------------------------------

    /**
     * Adds an Integer, Long, or Fraction to the Fraction.
     * @param other Fraction to be added.
     * @return New Fraction object with the result.
     */
    public Fraction add(Fraction other) {
        
        return Fraction.add(this, other);
    }

    /**
     * Subtracts an Integer, Long, or Fraction to the Fraction.
     * @param other Fraction to be subtracted.
     * @return New Fraction object with the result.
     */
    public Fraction subtract(Fraction other) {
        
        return Fraction.subtract(this, other);
    }

    /**
     * Multiplies an Integer, Long, or Fraction to the Fraction.
     * @param other Fraction to be multiplied.
     * @return New Fraction object with the result.
     */
    public Fraction multiply(Fraction other) {
        
        return Fraction.multiply(this, other);
    }

    /**
     * Divides an Integer, Long, or Fraction to the Fraction.
     * @param other Fraction to be Divided.
     * @return New Fraction object with the result.
     */
    public Fraction divide(Fraction other) {
        
        return Fraction.divide(this, other);
    }

    /**
     * Applies an exponent to the fraction.
     * @param power - Exponent to be applied
     * @return New Fraction object with the answer.
     */
    public Fraction pow(long power) {
        return Fraction.pow(this, power);
    }

    /**
     * Takes the absolute value of the fraction.
     * @return Result
     */
    public Fraction abs() {
        return Fraction.abs(this);
    }

    /**
     * Returns whether the fraction is less than `other`.
     * @param other The fraction to compare
     * @return A boolean of the result.
     */
    public boolean lessThan(Fraction other) {
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 < c2) return true;
        else return false;
    }

    /**
     * Returns whether the fraction is less than or equal to `other`.
     * @param other The fraction to compare
     * @return A boolean of the result.
     */
    public boolean lessOrEqual(Fraction other) {
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 <= c2) return true;
        else return false;

    }

    /**
     * Returns whether the fraction is greater than `other`.
     * @param other The fraction to compare
     * @return A boolean of the result.
     */
    public boolean greaterThan(Fraction other) {
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 > c2) return true;
        else return false;
    }

    /**
     * Returns whether the fraction is greater than or equal to `other`.
     * @param other The fraction to compare
     * @return A boolean of the result.
     */
    public boolean greaterOrEqual(Fraction other) {
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 >= c2) return true;
        else return false;
    }



    // METHODS FOR LONGS -----------------------------------------------------------------------------------------------------------

    /**
     * Adds an long to the Fraction.
     * @param other long to be added.
     * @return New Fraction object with the result.
     */
    public Fraction add(long o) {
        Fraction other = new Fraction(o);
        return Fraction.add(this, other);
    }

    /**
     * Subtracts an long to the Fraction.
     * @param other long to be subtracted.
     * @return New Fraction object with the result.
     */
    public Fraction subtract(long o) {
        Fraction other = new Fraction(o);
        return Fraction.subtract(this, other);
    }

    /**
     * Multiplies an long to the Fraction.
     * @param other long to be multiplied.
     * @return New Fraction object with the result.
     */
    public Fraction multiply(long o) {
        Fraction other = new Fraction(o);
        return Fraction.multiply(this, other);
    }

    /**
     * Divides an long to the Fraction.
     * @param other long to be Divided.
     * @return New Fraction object with the result.
     */
    public Fraction divide(long o) {
        Fraction other = new Fraction(o);
        return Fraction.divide(this, other);
    }

    /**
     * Returns whether the fraction is less than `other`.
     * @param other The long to compare
     * @return A boolean of the result.
     */
    public boolean lessThan(long o) {
        Fraction other = new Fraction(o);
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 < c2) return true;
        else return false;
    }

    /**
     * Returns whether the fraction is less than or equal to `other`.
     * @param other The long to compare
     * @return A boolean of the result.
     */
    public boolean lessOrEqual(long o) {
        Fraction other = new Fraction(o);
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 <= c2) return true;
        else return false;

    }

    /**
     * Returns whether the fraction is greater than `other`.
     * @param other The long to compare
     * @return A boolean of the result.
     */
    public boolean greaterThan(long o) {
        Fraction other = new Fraction(o);
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 > c2) return true;
        else return false;
    }

    /**
     * Returns whether the fraction is greater than or equal to `other`.
     * @param other The long to compare
     * @return A boolean of the result.
     */
    public boolean greaterOrEqual(long o) {
        Fraction other = new Fraction(o);
        long c1 = Math.multiplyExact(this.getNumerator(), other.getDenominator());
        long c2 = Math.multiplyExact(other.getNumerator(), this.getDenominator());

        if (c1 >= c2) return true;
        else return false;
    }

    /**
     * Takes the reciprocal of a fraction.
     * Throws an Airthmetic Exception if the numerator is 0.
     */
    public Fraction reciprocal() {
        if (this.numerator == 0) {
            throw new ArithmeticException("Cannot Divide by 0");
        }

        return new Fraction(this.denominator, this.numerator);
    }

    /**
     * @return The fraction in text form.
     */
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        return numerator + "/" + denominator;
    }

    public String toLatexString() {
        if (this.denominator != 1) return "\\frac{" + this.numerator + "}{" + this.denominator + "}";
        else return "" + this.numerator;
    }

    /**
     * Prints the value of the fraction in decimal form at a given preceision.
     * @param precision - Precision of the decimal.
     * @return Decimal representation of the fraction.
     */
    public String toDecimalString(int precision) {
        String result = "";
        
        long integer = this.numerator / this.denominator;
        result += Long.toString(integer) + ".";
        
        long remainder = Math.abs(this.numerator % this.denominator);
        
        for (int i = 0; i < precision; i++) {
            if (remainder == 0) {
                result += "0";
                continue;
            }
            
            remainder *= 10;
            long digit = remainder / this.denominator;
            result += Long.toString(digit);
            remainder = remainder % this.denominator;
        }
        
        return result;
    }
}
