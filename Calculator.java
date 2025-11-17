package preweek3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    static ArrayList<Double> results = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("=== 계산기 메뉴 ===");
            System.out.println("1. 계산하기");
            System.out.println("2. 계산 이력 보기");
            System.out.println("3. 이력 지우기");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            int option;

            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }
            if (option == 1) {
                calculator(sc);
            } else if (option == 2) {
                System.out.println(results);
            } else if (option == 3) {
                results.clear();
            } else if (option == 0) {
                break;
            } else {
                System.out.println("0~3까지의 숫자를 입력하세요.");
            }
        }
        System.out.println("계산기를 종료합니다.");
        sc.close();
    }

    private static void calculator(Scanner sc) {
        System.out.println("=== Java 계산기 ===");
        double num1;
        double num2=0;
        String op;
        double result=0.0;
        boolean flag = false;
        double previousValue = 0;

        while (true) {
            if (!results.isEmpty()) {
                previousValue = results.get(results.size() - 1);
                System.out.print("이전 결과(" + previousValue + ")를 사용하시겠습니까? (y/n): ");
                String option = sc.nextLine();
                if (option.equals("y")) {
                    flag = true;
                }
            }
            if (!flag) {
                System.out.print("첫 번째 숫자를 입력하세요: ");
                num1 = inputNumber(sc);
                op = inputOperator(sc);
                if (!op.equals("sqrt")) {
                    System.out.print("두 번째 숫자를 입력하세요: ");
                    num2 = inputNumber(sc);
                }
                try {
                    result = calculate(op, num1, num2);
                } catch (ArithmeticException e) {
                    System.out.println("0으로 나눌수 없습니다.");
                    continue;
                }
                printResult(num1, num2, op, result);
                boolean again = calculateAgain(sc);
                if (!again) {
                    break;
                }
            } else {
                num1 = previousValue;
                op = inputOperator(sc);
                if (!op.equals("sqrt")) {
                    System.out.print("숫자를 입력하세요: ");
                    num2 = inputNumber(sc);
                }
                try {
                    result = calculate(op, num1, num2);
                } catch (ArithmeticException e) {
                    System.out.println("0으로 나눌수 없습니다.");
                    continue;
                }
                printResult(num1, num2, op, result);
                boolean again = calculateAgain(sc);
                if (!again) {
                    break;
                }
            }
        }
    }

    private static boolean calculateAgain(Scanner sc) {
        System.out.print("\n계속 계산하시겠습니까? (y/n): ");
        String line = sc.nextLine();
        if (line.charAt(0) == 'y') {
            return true;
        }
        return false;
    }

    private static void printResult(double num1, double num2, String op, double result) {
        if (op.equals("sqrt")) {
            System.out.println("결과: " + num1 + " " + op + " = " + result);
        } else {
            System.out.println("결과: " + num1 + " " + op + " " + num2 + " = " + result);
        }
    }

    private static double calculate(String op, double num1, double num2) {
        double result = Double.MAX_VALUE;

        if (op.equals("+")) {
            result = num1 + num2;
        } else if (op.equals("-")) {
            result = num1 - num2;
        } else if (op.equals("*")) {
            result = num1 * num2;
        } else if (op.equals("/")) {
            if (num2 == 0) {
                throw new ArithmeticException();
            }
            result = (double) num1 / num2;
        } else if (op.equals("%")) {
            if (num2 == 0) {
                throw new ArithmeticException();
            }
            result = num1 % num2;
        } else if (op.equals("^")) {
            result = Math.pow(num1, num2);
        } else if (op.equals("sqrt")) {
            result = Math.sqrt(num1);
        }
        results.add(result);
        return result;
    }

    static int inputNumber(Scanner sc) {
        int num;
        while (true) {
            try {
                num = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요.");
                sc.nextLine();
                continue;
            }
            return num;
        }
    }

    static String inputOperator(Scanner sc) {
        String op;
        while (true) {
            System.out.print("연산자를 입력하세요 (+, -, *, /, %, ^, sqrt): ");
            try {
                op = sc.nextLine();
                if (!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%") || op.equals("^") || op.equals("sqrt"))) {
                    throw new RuntimeException();
                }
            } catch (RuntimeException e) {
                System.out.println("가능한 연산자는 (+, -, *, /, %, ^, sqrt) 입니다.");
                continue;
            }
            return op;
        }
    }
}
