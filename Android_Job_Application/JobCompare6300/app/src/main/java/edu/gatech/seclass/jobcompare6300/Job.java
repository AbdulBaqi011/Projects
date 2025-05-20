package edu.gatech.seclass.jobcompare6300;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class Job implements Serializable{
    private String jobId;
    private String title;
    private String company;
    private String locationKey;
    private float yearlySalary;
    private float yearlyBonus;
    private float tuitionReimbursement;
    private float healthInsurance;
    private float EmployeeDiscount;
    private float childAdoptionAssistance;

    private float adjustedHealthInsurance;

    private float jobScore;

    public Job() {}

    public Job(String jobId,
               String title,
               String company,
               String locationKey,
               float yearlySalary,
               float yearlyBonus,
               float tuitionReimbursement,
               float healthInsurance,
               float EmployeeDiscount,
               float childAdoptionAssistance) {

        this.title = title ;
        this.company = company ;
        this.locationKey = locationKey ;
        this.yearlySalary =yearlySalary ;
        this.yearlyBonus = yearlyBonus;
        this.tuitionReimbursement =tuitionReimbursement ;
        this.healthInsurance =healthInsurance ;
        this.EmployeeDiscount = EmployeeDiscount;
        this.childAdoptionAssistance = childAdoptionAssistance ;
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public float getYearlySalary(){
        return yearlySalary;
    }
    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }
    public float getYearlyBonus(){
        return yearlyBonus;
    }
    public void setYearlyBonus(float yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }
    public float getTuitionReimbursement(){
        return tuitionReimbursement;
    }
    public void setTuitionReimbursement(float tuitionReimbursement) {
        this.tuitionReimbursement = tuitionReimbursement;
    }
    public float getHealthInsurance(){
        return healthInsurance;
    }
    public void setHealthInsurance(float healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public float getAdjustedHealthInsurance(){
        return adjustedHealthInsurance;
    }
    public void setAdjustedHealthInsurance() {
        this.adjustedHealthInsurance = healthInsurance + (0.02f * yearlySalary);
    }

    public float getEmployeeDiscount(){
        return EmployeeDiscount;
    }
    public void setEmployeeDiscount(float employeeDiscount) {
        this.EmployeeDiscount = employeeDiscount;
    }

    public float getChildAdoptionAssistance(){
        return childAdoptionAssistance;
    }
    public void setChildAdoptionAssistance (float childAdoptionAssistance) {
        this.childAdoptionAssistance = childAdoptionAssistance;
    }

    @JsonIgnore
    public void calculateJobScore(ComparisonSettings settings, Float costOfLiving) {
        float totalWeight = settings.getYearlySalaryWeight() +
                settings.getYearlyBonusWeight() +
                settings.getTuitionReimbursementWeight() +
                settings.getHealthInsuranceWeight() +
                settings.getEmployeeDiscountWeight() +
                settings.getChildAdoptionWeight();

        if (totalWeight == 0) {
            jobScore = 0; // Avoid division by zero
            return;
        }

        float ays = getYearlySalary() / costOfLiving * 100;
        float ayb = getYearlyBonus() / costOfLiving * 100;
        float tr = getTuitionReimbursement();
        float hi = getHealthInsurance() + (0.02f * ays);
        float epsd = getEmployeeDiscount();
        float caa = getChildAdoptionAssistance() / 5.0f;

        jobScore = (settings.getYearlySalaryWeight() / totalWeight) * ays +
                (settings.getYearlyBonusWeight() / totalWeight) * ayb +
                (settings.getTuitionReimbursementWeight() / totalWeight) * tr +
                (settings.getHealthInsuranceWeight() / totalWeight) * hi +
                (settings.getEmployeeDiscountWeight() / totalWeight) * epsd +
                (settings.getChildAdoptionWeight() / totalWeight) * caa;
    }

    @JsonIgnore
    public float getJobScore() {
        return jobScore;
    }

    // Log output for Jobs
    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location=" + locationKey + // Assuming Location also has a toString()
                ", yearlySalary=" + yearlySalary +
                ", yearlyBonus=" + yearlyBonus +
                ", tuitionReimbursement=" + tuitionReimbursement +
                ", healthInsurance=" + healthInsurance +
                ", EmployeeDiscount=" + EmployeeDiscount +
                ", childAdoptionAssistance=" + childAdoptionAssistance +
                ", adjustedHealthInsurance=" + adjustedHealthInsurance +
                '}';
    }

}
