package com.mycompany.salsabil.alburani;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SalsabilAlburani {

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new FullTimeEmployee("E101", "Salsabil", 4000, LocalDate.of(2022, 5, 10), 800));
        employees.add(new ContractorEmployee("E102", "Sara", LocalDate.of(2020, 3, 15), 35, 160));
        employees.add(new FullTimeEmployee("E103", "Nasieb", 3000, LocalDate.of(2023, 1, 20), 500));
        employees.add(new ContractorEmployee("E104", "Saja", LocalDate.of(2021, 8, 1), 25, 150));

        Collections.sort(employees);
        System.out.println("Employees Sorted by Net Salary");
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        Comparator<Employee> joinDateComparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getJoinDate().compareTo(e2.getJoinDate());
            }
        };

        Collections.sort(employees, joinDateComparator);
        System.out.println("\n Employees Sorted by Seniority ");
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        double totalTaxes = 0;
        double totalBonuses = 0;
        double defaultScore = 4.5; 

        System.out.println("\nTaxes and Bonuses Details " + defaultScore );
        for (Employee emp : employees) {
            double tax = emp.calculateTax();
            double bonus = emp.calculateBonus(defaultScore);

            totalTaxes += tax;
            totalBonuses += bonus;

            System.out.println("Name: " + emp.getName() + " | Tax: " + tax + " | Bonus: " + bonus);
        }

        System.out.println("\nTotal Taxes: " + totalTaxes);
        System.out.println("Total Bonuses: " + totalBonuses);
    }
}

abstract class Employee implements Taxable, BonusCalculable, Comparable<Employee> {
    private String id;
    private String name;
    protected double baseSalary;
    private LocalDate joinDate;

    public Employee(String id, String name, double baseSalary, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
        this.joinDate = joinDate;
    }

    public abstract double calculateNetSalary();

    @Override
    public double calculateTax() {
        return calculateNetSalary() * TAX_RATE;
    }

    @Override
    public int compareTo(Employee other) {
        if (this.calculateNetSalary() < other.calculateNetSalary()) {
            return 1;
        } else if (this.calculateNetSalary() > other.calculateNetSalary()) {
            return -1;
        } else {
            return 0;
        }
    }

    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Join Date: " + joinDate + " | Net Salary: " + calculateNetSalary();
    }
}

class FullTimeEmployee extends Employee {
    private double allowance; 

    public FullTimeEmployee(String id, String name, double baseSalary, LocalDate joinDate, double allowance) {
        super(id, name, baseSalary, joinDate);
        this.allowance = allowance;
    }

    @Override
    public double calculateNetSalary() {
        return baseSalary + allowance;
    }

    @Override
    public double calculateBonus(double performanceScore) {
        return (baseSalary * 0.10) * performanceScore;
    }
}

class ContractorEmployee extends Employee {
    private int hourlyRate;  
    private int hoursWorked; 

    public ContractorEmployee(String id, String name, LocalDate joinDate, int hourlyRate, int hoursWorked) {
        super(id, name, 0, joinDate);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateNetSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public double calculateBonus(double performanceScore) {
        if (performanceScore > 4) {
            return 500;
        } else {
            return 0;
        }
    }
}