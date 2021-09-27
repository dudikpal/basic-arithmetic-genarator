package basicarithmeticgenarator.example;



public enum Operator {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVIDE("/");

    String sign;

    private Operator(String sign) {
        this.sign = sign;
    }
}
