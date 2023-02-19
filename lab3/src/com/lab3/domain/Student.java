package com.lab3.domain;

public class Student extends Employee {
    private final String studentIdCardNumber;
    private float workingRate;

    public Student(int empId, String name, String ssn, double salary, String studentIdCardNumber, float workingRate) {
        super(empId, name, ssn, salary);
        this.studentIdCardNumber = studentIdCardNumber;
        this.workingRate = workingRate;
    }

    public String getStudentIdCardNumber() {
        return studentIdCardNumber;
    }

    public float getWorkingRate() {
        return workingRate;
    }

    public void setWorkingRate(float workingRate) {
        this.workingRate = workingRate;
    }

    @Override
    public double getSalary() {
        return super.getSalary() * workingRate;
    }
}
