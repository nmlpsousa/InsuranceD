package pt.insuranced.models;

public class Coverage {
    private long id;

    private long coverableId;

    private Double limit;

    private Double premium;

    private String description;

    public Coverage() {
    }

    public Coverage(long id, long coverableId, Double limit, Double premium, String description) {
        this.id = id;
        this.coverableId = coverableId;
        this.limit = limit;
        this.premium = premium;
        this.description = description;
    }

    public long getCoverableId() {
        return this.coverableId;
    }

    public void setCoverableId(long coverableId) {
        this.coverableId = coverableId;
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

    public long getId() {
        return this.id;
    }

    public static final class Builder {
        private long id;

        private long coverableId;

        private Double limit;

        private Double premium;

        private String description;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setCoverableId(long coverableId) {
            this.coverableId = coverableId;
            return this;
        }

        public Builder setLimit(Double limit) {
            this.limit = limit;
            return this;
        }

        public Builder setPremium(Double premium) {
            this.premium = premium;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Coverage build() {
            return new Coverage(this.id, this.coverableId, this.limit, this.premium, this.description);
        }
    }
}
