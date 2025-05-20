# Job Comparison App Design Description
1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

>The JobComparisonApp class is where we allow (1) setJobs() with the help of UI for entering the current job details + setIsCurrentJob() to flag the currentJob as a boolean. (2) setJobs() allows the user to enter multiple job offers (3) setComparisonSettings() will adjust comparison settings and (4) will be more of a GUI representation between a displayed table and a combination of ComparisonSettings class and getJobScore() functionality from the Job class.

2. When choosing to _enter current job details,_ a user will:
    1.  Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:
        1.  Title
        2.  Company
        3.  Location (entered as city and state)
        4.  Cost of living in the location (expressed as an [index](https://www.expatistan.com/cost-of-living/index/north-america))
        5.  Yearly salary
        6.  Yearly bonus
        7.  Tuition Reimbursement ($0 to $15000 inclusive annually)
        8.  Health Insurance ($0-$1000 inclusive + 2% of AYS annually)
        9.  Employee Product/Service Discount (dollar amount up to 18% of Yearly Salary)
        10.  Child Adoption Assistance (expressed as a lump sum $0 to $30000 inclusive available over 5 years)
        > We have getters and setters for each of the represented fields under the Job class.
    2.  Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.
   > This will be implemented in the UI.

3.  When choosing to _enter job offers,_ a user will:
    1.  Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
    > In the JobComparisonApp class the setJobs() method will handle this requirement.
    2.  Be able to either save the job offer details or cancel.
    > This will be implemented in the UI.
    3.  Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).
    > This will be implemented in the UI.

4.  When _adjusting the comparison settings,_ the user can assign integer _weights_ to:
    1.  Yearly salary
    2.  Yearly bonus
    3.  Tuition Reimbursement
    4.  Health Insurance
    5.  Employee Product/Service Discount
    6.  Child Adoption Assistance
    > All of the above values are assigned values inside of the ComparisonSettings Class which will handle receiving and assigning the values

5.  When choosing to _compare job offers,_ a user will:
    1.  Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
    > The JobComparisonApp maintains a list of Job objects, which includes attributes such as title, company, and location. Since getJobs() is a public method, it allows retrieving and displaying the job list. The inclusion of the currentJob attribute ensures that the user's existing job can be highlighted in the list.
    2.  Select two jobs to compare and trigger the comparison.
    > The user can select two jobs from the retrieved list using getJobs(). The setJobs(List<Job>) method ensures that selected jobs can be managed dynamically.
    3.  Be shown a table comparing the two jobs, displaying, for each job:
        1.  Title
        2.  Company
        3.  Location
        4.  Yearly salary adjusted for cost of living
        5.  Yearly bonus adjusted for cost of living
        6.  Tuition Reimbursement (TR)
        7.  Health Insurance (HI)
        8.  Employee Product/Service Discount (EPSD)
        9.  Child Adoption Assistance (CAA)
        10.  Job Score - (JS) Calculation shown in Requirement #6
        > The user can select two jobs from the retrieved list using getJobs(). The setJobs(List<Job>) method ensures that selected jobs can be managed dynamically.
    4.  Be offered to perform another comparison or go back to the main menu.
    > The design does not explicitly define UI navigation, but since JobComparisonApp only handles core logic, UI interactions can be managed externally without modifying the core structure.

6.  When ranking jobs, a job’s score is computed as the **weighted** average of:  
    

7. JS = AYS + AYB + TR + HI + EPSD + (CAA/5)  
where:  
AYS = Yearly Salary Adjusted for cost of living  
AYB = Yearly Bonus Adjusted for cost of living  
TR = Tuition Reimbursement ($0 to $15000 inclusive annually)

    HI = Health Insurance ($0-$1000 inclusive + 2% of AYS annually)

    EPSD = Employee Product/Service Discount (dollar amount up to 18% of Yearly Salary)

    CAA = Child Adoption Assistance (expressed as a lump sum available over 5 years)

    For example, if the weights are 2 for the yearly salary, 2 for the yearly bonus, 2 for Tuition Reimbursement, and 1 for all other factors, the score would be computed as:  

    JS = 2/9 \* AYS + 2/9 \* AYB + 2/9 \* TR + 1/9 \* HI + 1/9 \* EPSD + 1/9 \* (CAA/5)
> The `Job.calculateJobScore(ComparisonSettings)`, method requires a `ComparisonSettings` object to compute the job score, which is set onto the object attribute `Job.jobScore`. This can then be retrieved by the client with `Job.getJobScore()`.

> The ComparisonSettings class stores weight factors for each criterion.
The getComparisonSettings() method allows retrieving these weights.
The weighted average formula is computed using the attributes available in Job.
The JobComparisonApp can retrieve the necessary values (AYS, AYB, TR, HI, EPSD, and CAA) from Job.

7. The user interface must be intuitive and responsive.
> 	This will be implemented in the UI.

8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
> The system operates as a standalone application. No communication or saving between multiple devices is necessary.  Data is stored and processed locally within the app.
