package com.lab3;

import com.lab3.domain.*;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class EmployeeTest {
    public static void main(String[] args) {
        Engineer eng = new Engineer(101, "Jane Smith", "012-34-5678", 120_345.27);
        Manager mgr = new Manager(207, "Barbara Johnson", "054- 12- 2367", 109_501.36, "US Marketing");
        Admin adm = new Admin(304, "Bill Munroe", "108-23- 2367", 75_002.34);
        Director dir = new Director(12, "Susan Wheeler", "099- 45- 2340", 120_567.36, "Global Marketing", 1_000_000.00);
        Student student = new Student(316, "Thomas Anderson", "031-45-2666", 100_000, "43888", 0.8f);
        printEmployee(eng);
        printEmployee(mgr);
        printEmployee(adm);
        printEmployee(dir);
        printEmployee(student);
    }

    public static void printEmployee(@NotNull Employee emp) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        System.out.println("Employee ID: " + emp.getEmpId());
        System.out.println("Employee Name: " + emp.getName());
        System.out.println("Employee Soc Sec # " + emp.getSsn());
        System.out.println("Employee salary: " + formatter.format(emp.getSalary()));
    }
}
