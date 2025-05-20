package edu.gatech.seclass.jobcompare6300;

public class ComparisonSettings {

    private int yearlySalaryWeight = 1;
    private int yearlyBonusWeight = 1;
    private int tuitionReimbursementWeight = 1;
    private int healthInsuranceWeight = 1;
    private int employeeDiscountWeight = 1;
    private int childAdoptionWeight = 1;

    public ComparisonSettings() {}
    /*
    public ComparisonSettings(int yearlySalaryWeight, int yearlyBonusWeight, int tuitionReimbursementWeight,
                              int healthInsuranceWeight, int employeeDiscountWeight, int childAdoptionWeight) {

        this.yearlySalaryWeight = yearlySalaryWeight;
        this.yearlyBonusWeight = yearlyBonusWeight;
        this.tuitionReimbursementWeight = tuitionReimbursementWeight;
        this.healthInsuranceWeight = healthInsuranceWeight;
        this.employeeDiscountWeight = employeeDiscountWeight;
        this.childAdoptionWeight = childAdoptionWeight;
    }
    */


    public int getYearlySalaryWeight() {
        return yearlySalaryWeight;
    }

    public void setYearlySalaryWeight(int yearlySalaryWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
    }

    public int getYearlyBonusWeight() {
        return yearlyBonusWeight;
    }

    public void setYearlyBonusWeight(int yearlyBonusWeight) {
        this.yearlyBonusWeight = yearlyBonusWeight;
    }

    public int getTuitionReimbursementWeight() {
        return tuitionReimbursementWeight;
    }

    public void setTuitionReimbursementWeight(int tuitionReimbursementWeight) {
        this.tuitionReimbursementWeight = tuitionReimbursementWeight;
    }

    public int getHealthInsuranceWeight() {
        return healthInsuranceWeight;
    }

    public void setHealthInsuranceWeight(int healthInsuranceWeight) {
        this.healthInsuranceWeight = healthInsuranceWeight;
    }

    public int getEmployeeDiscountWeight() {
        return employeeDiscountWeight;
    }

    public void setEmployeeDiscountWeight(int employeeDiscountWeight) {
        this.employeeDiscountWeight = employeeDiscountWeight;
    }

    public int getChildAdoptionWeight() {
        return childAdoptionWeight;
    }

    public void setChildAdoptionWeight(int childAdoptionWeight) {
        this.childAdoptionWeight = childAdoptionWeight;
    }
}
