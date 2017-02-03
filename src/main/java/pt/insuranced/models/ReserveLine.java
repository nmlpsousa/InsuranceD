package pt.insuranced.models;

public class ReserveLine {
	private int id;

	private String description;

	private Double limit;

	private Double usedFunds;

	public ReserveLine() {
	}

	public ReserveLine(int id, String description, Double limit, Double usedFunds) {
		this.id = id;
		this.description = description;
		this.limit = limit;
		this.usedFunds = usedFunds;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getLimit() {
		return this.limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public Double getUsedFunds() {
		return this.usedFunds;
	}

	public void setUsedFunds(Double usedFunds) {
		this.usedFunds = usedFunds;
	}

	public int getId() {
		return this.id;
	}

	public static class Builder {
		private int id;
		
		private String description;
		
		private Double limit;
		
		private Double usedFunds;

		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
        public static Builder newBuilder() {
            return new Builder();
        }

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setLimit(Double limit) {
			this.limit = limit;
			return this;
		}

		public Builder setUsedFunds(Double usedFunds) {
			this.usedFunds = usedFunds;
			return this;
		}

		public ReserveLine build() {
			return new ReserveLine(this.id,
			this.description,
			this.limit,
			this.usedFunds);
		}
	}
}
