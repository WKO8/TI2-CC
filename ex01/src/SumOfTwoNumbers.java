import java.util.*;

class SumOfTwoNumbers {
	public static Scanner sc = new Scanner(System.in);
	public static void main(String args[]) {
		// Declaration of variables
		int num1, num2, sum;
		
		// Readings
		System.out.println("Type a number: ");
		num1 = sc.nextInt();
		System.out.println("Type another number: ");
		num2 = sc.nextInt();
		
		// Sum
		sum = num1 + num2;
		
		// Show on screen
		System.out.println("Result: " + sum);
	}
}
