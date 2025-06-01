import java.util.*;

class Salary {
    float salary;
    int age;

    Salary(float salary, int age) {
        this.salary = salary;
        this.age = age;
    }
}

class Investment extends Salary {
    float saveInstrument = 0;
    float health;
    float homeLoan;

    Investment(float salary, int age, Scanner sc) {
        super(salary, age);
        System.out.println("Enter the number of tax saving instruments you are investing in:");
        int count = sc.nextInt();
        System.out.println("Enter amount invested in tax-saving instruments like PPF, ELSS, etc. (one per line):");
        while (count-- > 0) {
            float addAmount = sc.nextFloat();
            saveInstrument += addAmount;
        }

        System.out.println("Enter Health insurance premium:");
        health = sc.nextFloat();
        System.out.println("Enter Home Loan Interest:");
        homeLoan = sc.nextFloat();
    }
}

class TaxCalculate extends Salary {
    float tax;

    TaxCalculate(float salary, int age, Investment inv) {
        super(salary, age);
        
        //deduction details{

        float deduction80C = Math.min(inv.saveInstrument, 150000);
        float deduction80D = Math.min(inv.health, (age >= 60 ? 50000 : 25000));
        float deduction24 = Math.min(inv.homeLoan, 200000);
        float totalDeductions = deduction80C + deduction80D + deduction24;
        
    //}

        float taxable = salary - totalDeductions;

        if (age < 60) {
            if (taxable <= 250000) {
                tax = 0;
            } else if (taxable <= 500000) {
                tax = (taxable - 250000) * 0.05f;
            } else if (taxable <= 1000000) {
                tax = (250000 * 0.05f) + (taxable - 500000) * 0.20f;
            } else {
                tax = (250000 * 0.05f) + (500000 * 0.20f) + (taxable - 1000000) * 0.30f;
            }
        } else if (age >= 60 && age < 80) {
            if (taxable <= 300000) {
                tax = 0;
            } else if (taxable <= 500000) {
                tax = (taxable - 300000) * 0.05f;
            } else if (taxable <= 1000000) {
                tax = (200000 * 0.05f) + (taxable - 500000) * 0.20f;
            } else {
                tax = (200000 * 0.05f) + (500000 * 0.20f) + (taxable - 1000000) * 0.30f;
            }
        } else {
            if (taxable <= 500000) {
                tax = 0;
            } else if (taxable <= 1000000) {
                tax = (taxable - 500000) * 0.20f;
            } else {
                tax = (500000 * 0.20f) + (taxable - 1000000) * 0.30f;
            }
        }
        

        System.out.println("\n--- TAX DETAILS ---");
        System.out.println("Gross Salary: ₹" + salary);
        System.out.println("Total Deductions: ₹" + totalDeductions);
        System.out.println("Taxable Income: ₹" + taxable);
        System.out.println("Tax Payable: ₹" + tax);
        System.out.println("Net Salary After Tax: ₹" + (salary - tax));
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your annual salary:");
        float salary = sc.nextFloat();
        System.out.println("Enter your age:");
        int age = sc.nextInt();

        Investment inv = new Investment(salary, age, sc);
        TaxCalculate taxObj = new TaxCalculate(salary, age, inv);
    }
}
