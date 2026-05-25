package FYP.CREDITRISK;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please fill in the company's name.")
    private String companyName;

    @NotEmpty(message = "Please enter the company's industry.")
    private String industry;

    @NotNull(message = "Cash must not be null.")
    private double cash;

    @NotNull(message = "EBIT/Revenue must not be null.")
    private double ebit_Rev;

    @NotNull(message = "EBIT/Total Asset must not be null.")
    private double ebit_totalAsset;

    @NotNull(message = "Employees must not be null.")
    private double employees;

    @NotNull(message = "Gross Profit/Revenue must not be null.")
    private double grossProfit_Rev;

    @NotNull(message = "Net Cash Flow must not be null.")
    private double netCashFlow;

    @NotNull(message = "Retained Earnings must not be null.")
    private double retainedEarnings;

    @NotNull(message = "Total Asset/Total Liabilities must not be null.")
    private double totalAsset_TotalLiabilities;

    @NotNull(message = "Total Debt/Total Asset must not be null.")
    private double totalDebt_TotalAsset;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getEbit_Rev() {
        return ebit_Rev;
    }

    public void setEbit_Rev(double ebit_Rev) {
        this.ebit_Rev = ebit_Rev;
    }

    public double getEbit_totalAsset() {
        return ebit_totalAsset;
    }

    public void setEbit_totalAsset(double ebit_totalAsset) {
        this.ebit_totalAsset = ebit_totalAsset;
    }

    public double getEmployees() {
        return employees;
    }

    public void setEmployees(double employees) {
        this.employees = employees;
    }

    public double getGrossProfit_Rev() {
        return grossProfit_Rev;
    }

    public void setGrossProfit_Rev(double grossProfit_Rev) {
        this.grossProfit_Rev = grossProfit_Rev;
    }

    public double getNetCashFlow() {
        return netCashFlow;
    }

    public void setNetCashFlow(double netCashFlow) {
        this.netCashFlow = netCashFlow;
    }

    public double getRetainedEarnings() {
        return retainedEarnings;
    }

    public void setRetainedEarnings(double retainedEarnings) {
        this.retainedEarnings = retainedEarnings;
    }

    public double getTotalAsset_TotalLiabilities() {
        return totalAsset_TotalLiabilities;
    }

    public void setTotalAsset_TotalLiabilities(double totalAsset_TotalLiabilities) {
        this.totalAsset_TotalLiabilities = totalAsset_TotalLiabilities;
    }

    public double getTotalDebt_TotalAsset() {
        return totalDebt_TotalAsset;
    }

    public void setTotalDebt_TotalAsset(double totalDebt_TotalAsset) {
        this.totalDebt_TotalAsset = totalDebt_TotalAsset;
    }
}
