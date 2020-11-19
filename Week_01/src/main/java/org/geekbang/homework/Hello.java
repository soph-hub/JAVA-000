package org.geekbang.homework;

public class Hello {

    public static void main(String[] args) {
        double x = 10;
        double y = 2;

        double sum = add(x, y);
        double diff = subtract(x, y);
        double product = multiply(x, y);
        double quotient = divide(x, y);

        if(sum == 12){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }

        for (int i = 0; i < 5; i++){
            System.out.println(i);
        }

    }

    private static double add(double x, double y){
        return x + y;
    }

    private static double subtract(double x, double y){
        return x - y;
    }

    private static double multiply(double x, double y){
        return x * y;
    }

    private static double divide(double x, double y){
        return x / y;
    }
}
