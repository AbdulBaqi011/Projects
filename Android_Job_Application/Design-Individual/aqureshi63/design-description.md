# **Design-Description Document**

## **Name: AbdulBaqi Qureshi**

**Email:** Aqureshi63@gatech.edu

---

# **Job Comparison**

## **System Overview**

The system consists of the following classes:

### **Main\_Menu**

This is the entry point into the system and allows the user to navigate between different functionalities of the app.

#### **Attributes:**

* `jobs`: List of job offers  
* `comparisonSetting`: AdjustComparisonSettings  
* `compareOffer`: CompareJobOffers

#### **Methods:**

* `setJobsList()`: Allows the user to input job offers.  
* `setCurrentJob()`: Allows the user to input their current job details.  
* `jobExistChecker()`: Verifies if jobs exist before comparison.

---

### **Job**

This class represents either current jobs or various job offers. It stores attributes related to job benefits and contains methods for calculating adjusted values.

#### **Attributes:**

* `title`: String  
* `company`: String  
* `location`: String  
* `costOfLiving`: String  
* `yearlySalary`: Float  
* `yearlyBonus`: Float  
* `tuitionReimbursement`: Float  
* `healthInsurance`: Float  
* `employeeDiscount`: Float  
* `childAdoptionAssistance`: Float

#### **Methods:**

* `getAdjustedYearlySalary()`: Adjusts user salary based on the cost of living.  
* `getAdjustedYearlyBonus()`: Adjusts user bonus based on the cost of living.  
* `getAdjustedYearlyHealth()`: Adjusts user health insurance benefits.  
* `calculateWeightedAverage()`: Computes job score based on assigned weights.

---

### **Adjust Comparison Settings**

This class allows the user to assign weights to different job factors for comparison.

#### **Attributes:**

* `yearlySalaryWeight`: Int  
* `yearlyBonusWeight`: Int  
* `tuitionReimbursementWeight`: Int  
* `healthInsuranceWeight`: Int  
* `employeeDiscountWeight`: Int  
* `childAdoptionAssistanceWeight`: Int

#### **Methods:**

* `computeWeightTotal()`: Computes the total weight assigned.

---

### **CompareJobOffers**

Handles job offer comparisons using weighted calculations.

#### **Attributes:**

* `jobs`: CurrentJob  
* `jobList`: List of Job objects

#### **Methods:**

* `calculateWeightedAvg()`: Computes the weighted job score.

---

# **Requirements Implementation**

## **1\.** 

When the app is started, the user is presented with the main menu, which allows them to:

1. Enter or edit current job details  
2. Enter job offers  
3. Adjust the comparison settings  
4. Compare job offers (disabled if no job offers were entered yet)  
* Implemented using the `Main_Menu` class.  
* The `jobExistChecker` method ensures the comparison is disabled if no jobs exist.

---

## **2\.**

When choosing to enter current job details, a user will:

* Be shown a UI to enter or edit details of their current job, including:

  * Title  
  * Company  
  * Location (city and state)  
  * Cost of living index  
  * Yearly salary  
  * Yearly bonus  
  * Tuition reimbursement ($0 \- $15,000 annually)  
  * Health insurance ($0 \- $1,000 \+ 2% of AYS annually)  
  * Employee discount (up to 18% of yearly salary)  
  * Child adoption assistance ($0 \- $30,000 over 5 years)  
* Be able to save job details or cancel and exit, returning to the main menu.

  * Implemented via `setCurrentJob()` in `Main_Menu`.

  * Job attributes stored in the `Job` class.

  * Users can save or cancel job entries using `confirmJobDetails()`.

---

## **3\.** 

When choosing to enter job offers, a user will:

* Be shown a UI to enter all job offer details (same as those listed above for the current job).

* Be able to either:

  1. Save the job offer details  
  2. Cancel  
  3. Enter another offer  
  4. Return to the main menu  
  5. Compare the offer (if saved and current job details are present)  
* Implemented via `setJobList()` in `Main_Menu`.

* Job attributes stored in the `Job` class.

* Users can save, cancel, or proceed to comparison.

---

## **4\.** 

Users can assign integer weights to the following factors:

* Yearly salary

* Yearly bonus

* Tuition reimbursement

* Health insurance

* Employee discount

* Child adoption assistance

* Implemented via `adjustComparisonSetting` class.

* `computeWeightTotal()` ensures proper weight calculations.

---

## **5\.** 

When choosing to compare job offers, a user will:

* See a ranked list of job offers displayed as `Title` and `Company`, including the current job (if present).

* Select two jobs to compare.

* View a table comparing the two jobs, showing:

  * Title  
  * Company  
  * Location  
  * Adjusted yearly salary  
  * Adjusted yearly bonus  
  * Tuition reimbursement  
  * Health insurance  
  * Employee discount  
  * Child adoption assistance  
  * Job score (JS)  
* Choose to perform another comparison or return to the main menu.

* Implemented via `CompareJobOffers` class.

* Users select two jobs for comparison.

* Adjusted salary, bonus, and other factors are computed.

---

## **6\.** 

Jobs are ranked based on the weighted average job score (JS):

JS=AYS+AYB+TR+HI+EPSD+(CAA/5)JS \= AYS \+ AYB \+ TR \+ HI \+ EPSD \+ (CAA/5)

Where:

* `AYS` \= Adjusted yearly salary  
* `AYB` \= Adjusted yearly bonus  
* `TR` \= Tuition reimbursement ($0 \- $15,000 annually)  
* `HI` \= Health insurance ($0 \- $1,000 \+ 2% of AYS annually)  
* `EPSD` \= Employee discount (up to 18% of yearly salary)  
* `CAA` \= Child adoption assistance (available over 5 years)

For example, if the user assigns weights as follows:

* `Yearly salary`: 2  
* `Yearly bonus`: 2  
* `Tuition reimbursement`: 2  
* `All other factors`: 1

Then, the formula is: JS=29AYS+29AYB+29TR+19HI+19EPSD+19(CAA/5)JS \= \\frac{2}{9} AYS \+ \\frac{2}{9} AYB \+ \\frac{2}{9} TR \+ \\frac{1}{9} HI \+ \\frac{1}{9} EPSD \+ \\frac{1}{9} (CAA/5)

* Implemented via `Job.calculateWeightedAverage()`.  
* Weights are applied based on user preferences.

---

## **7\.** 

The UI must be intuitive and responsive.

* A structured UI will be used for data input and display.  
* No specific GUI class is required in the design.

---

## **8\.** 

The system operates as a standalone application.

* No communication or saving between multiple devices is necessary.  
* Data is stored and processed locally within the app.

---

