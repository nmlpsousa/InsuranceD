package pt.insuranced.models;

import pt.insuranced.sdk.enums.ClaimStatusEnum;

import java.time.LocalDate;
import java.util.Comparator;

public class Claim {
    private long id;

    private String description;

    private LocalDate incidentDate;

    private ClaimStatusEnum status;

    private ReserveLine reserveLine;

    private long coverableId;

    public Claim() {
    }

    public Claim(long id, String description, LocalDate incidentDate, ClaimStatusEnum status, ReserveLine reserveLine, long coverableId) {
        this.id = id;
        this.description = description;
        this.incidentDate = incidentDate;
        this.status = status;
        this.reserveLine = reserveLine;
        this.coverableId = coverableId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getIncidentDate() {
        return this.incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public ClaimStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(ClaimStatusEnum status) {
        this.status = status;
    }

    public ReserveLine getReserveLine() {
        return this.reserveLine;
    }

    public void setReserveLine(ReserveLine reserveLine) {
        this.reserveLine = reserveLine;
    }

    public long getId() {
        return this.id;
    }

    public long getCoverableId() {
        return coverableId;
    }

    public void setCoverableId(long coverableId) {
        this.coverableId = coverableId;
    }

    public static final class Builder {
        private long id;

        private String description;

        private LocalDate incidentDate;

        private ClaimStatusEnum status;

        private ReserveLine reserveLine;

        private long coverableId;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setIncidentDate(LocalDate incidentDate) {
            this.incidentDate = incidentDate;
            return this;
        }

        public Builder setStatus(ClaimStatusEnum status) {
            this.status = status;
            return this;
        }

        public Builder setReserveLine(ReserveLine reserveLine) {
            this.reserveLine = reserveLine;
            return this;
        }

        public Builder setCoverableId(long coverableId) {
            this.coverableId = coverableId;
            return this;
        }

        public Claim build() {
            return new Claim(this.id, this.description, this.incidentDate,
                    this.status, this.reserveLine, this.coverableId);
        }
    }

    public static Comparator<Claim> getCompByDate() {
        Comparator<Claim> comp = new Comparator<Claim>() {
            @Override
            public int compare(Claim c1, Claim c2) {
                return c2.getIncidentDate().compareTo(c1.getIncidentDate());
            }
        };
        return comp;
    }

}
