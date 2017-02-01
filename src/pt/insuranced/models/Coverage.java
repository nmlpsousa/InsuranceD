package pt.insuranced.models;

public class Coverage {
    private int id;

    private int coverageCode;

    private Double limit;

    private Double premium;

    private String description;

    public Coverage() {
    }

    public Coverage(int id, int coverageCode, Double limit, Double premium, String description) {
        this.id = id;
        this.coverageCode = coverageCode;
        this.limit = limit;
        this.premium = premium;
        this.description = description;
    }

    public int getCoverageCode() {
        return this.coverageCode;
    }

    public void setCoverageCode(int coverageCode) {
        this.coverageCode = coverageCode;
    }

    public Double getLimit() {
        return this.limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getPremium() {
        return this.premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }
}
