-- Table: public."Password"

-- DROP TABLE public."Password";

CREATE TABLE public."Password"
(
  password character varying,
  "userId" bigint,
  "isActive" boolean,
  id bigint NOT NULL DEFAULT nextval('"Password_id_seq"'::regclass)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Password"
  OWNER TO postgres;

-- Table: public."Users"

-- DROP TABLE public."Users";

CREATE TABLE public."Users"
(
  "personalId" bigint,
  "typeId" bigint,
  username character varying,
  "lastPasswordChangeDate" date,
  "statusId" bigint
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Users"
  OWNER TO postgres;
  
-- Table: public."Client"

-- DROP TABLE public."Client";

CREATE TABLE public."Client"
(
-- Inherited from table "Users":  "personalId" integer,
-- Inherited from table "Users":  "typeId" integer,
-- Inherited from table "Users":  username character varying,
-- Inherited from table "Users":  "lastPasswordChangeDate" date,
-- Inherited from table "Users":  "statusId" integer,
  id bigint NOT NULL DEFAULT nextval('"Client_id_seq"'::regclass)
)
INHERITS (public."Users")
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Client"
  OWNER TO postgres;

-- Table: public."AccountManager"

-- DROP TABLE public."AccountManager";

CREATE TABLE public."AccountManager"
(
-- Inherited from table "Users":  "personalId" integer,
-- Inherited from table "Users":  "typeId" integer,
-- Inherited from table "Users":  username character varying,
-- Inherited from table "Users":  "lastPasswordChangeDate" date,
-- Inherited from table "Users":  "statusId" integer,
  "employeeNo" integer,
  id bigint NOT NULL DEFAULT nextval('"AccountManager_id_seq"'::regclass)
)
INHERITS (public."Users")
WITH (
  OIDS=FALSE
);
ALTER TABLE public."AccountManager"
  OWNER TO postgres;

-- Table: public."PersonalIdentification"

-- DROP TABLE public."PersonalIdentification";

CREATE TABLE public."PersonalIdentification"
(
  "firstName" character varying,
  "lastName" character varying,
  "dateOfBirth" date,
  "addressId" bigint,
  "identificationNo" character varying,
  "fiscalNumber" character varying,
  id bigint NOT NULL DEFAULT nextval('"PersonalIdentification_id_seq"'::regclass),
  "phoneNumberId" bigint
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."PersonalIdentification"
  OWNER TO postgres;

-- Table: public."Address"

-- DROP TABLE public."Address";

CREATE TABLE public."Address"
(
  "addressLine1" character varying,
  "addressLine2" character varying,
  city character varying,
  "postalCode" character varying,
  "countryId" integer,
  id bigint NOT NULL DEFAULT nextval('"Address_id_seq"'::regclass)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Address"
  OWNER TO postgres;

-- Table: public."PhoneNumber"

-- DROP TABLE public."PhoneNumber";

CREATE TABLE public."PhoneNumber"
(
  id bigint NOT NULL DEFAULT nextval('"PhoneNumber_id_seq"'::regclass),
  prefix character varying,
  "number" integer,
  CONSTRAINT "pk_phoneNumber" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."PhoneNumber"
  OWNER TO postgres;

-- Table: public."Claim"

-- DROP TABLE public."Claim";

CREATE TABLE public."Claim"
(
  id bigint NOT NULL DEFAULT nextval('"Claim_id_seq"'::regclass),
  "coverableId" bigint,
  "claimNo" integer,
  description character varying,
  "incidentDate" date,
  "claimStatusId" bigint,
  "reserveLineId" bigint,
  CONSTRAINT pk_claim PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Claim"
  OWNER TO postgres;

-- Table: public."Coverable"

-- DROP TABLE public."Coverable";

CREATE TABLE public."Coverable"
(
  id bigint NOT NULL DEFAULT nextval('"Coverable_id_seq"'::regclass),
  "coverableNo" integer,
  description character varying,
  "policyId" bigint,
  CONSTRAINT pk_coverable PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Coverable"
  OWNER TO postgres;

-- Table: public."Coverage"

-- DROP TABLE public."Coverage";

CREATE TABLE public."Coverage"
(
  id bigint NOT NULL DEFAULT nextval('"Coverage_id_seq"'::regclass),
  "coverableId" bigint,
  "limit" double precision,
  premium double precision,
  description character varying,
  CONSTRAINT pk_coverage PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Coverage"
  OWNER TO postgres;

-- Table: public."Payment"

-- DROP TABLE public."Payment";

CREATE TABLE public."Payment"
(
  id bigint NOT NULL DEFAULT nextval('"Payment_id_seq"'::regclass),
  "paymentNo" integer,
  "coverageId" bigint,
  payee character varying,
  amount double precision,
  "paymentStatusId" bigint,
  CONSTRAINT pk_payment PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Payment"
  OWNER TO postgres;

-- Table: public."ReserveLine"

-- DROP TABLE public."ReserveLine";

CREATE TABLE public."ReserveLine"
(
  id bigint NOT NULL DEFAULT nextval('"ReserveLine_id_seq"'::regclass),
  description character varying,
  "limit" double precision,
  "usedFunds" double precision,
  CONSTRAINT "pk_reserveLine" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."ReserveLine"
  OWNER TO postgres;

-- Table: public."Policy"

-- DROP TABLE public."Policy";

CREATE TABLE public."Policy"
(
  id bigint NOT NULL DEFAULT nextval('"Policy_id_seq"'::regclass),
  "policyNo" integer,
  "startDate" date,
  "endDate" date,
  premium double precision,
  "userId" bigint,
  CONSTRAINT pk_policy PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Policy"
  OWNER TO postgres;

-- Table: public."UserType"

-- DROP TABLE public."UserType";

CREATE TABLE public."UserType"
(
  id bigint NOT NULL DEFAULT nextval('"UserType_id_seq"'::regclass),
  "userType" character varying,
  CONSTRAINT "pk_userType" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."UserType"
  OWNER TO postgres;

-- Table: public."UserStatus"

-- DROP TABLE public."UserStatus";

CREATE TABLE public."UserStatus"
(
  id bigint NOT NULL DEFAULT nextval('"UserStatus_id_seq"'::regclass),
  "userStatus" character varying,
  CONSTRAINT "pk_userStatus" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."UserStatus"
  OWNER TO postgres;

-- Table: public."PaymentStatus"

-- DROP TABLE public."PaymentStatus";

CREATE TABLE public."PaymentStatus"
(
  id bigint NOT NULL DEFAULT nextval('"PaymentStatus_id_seq"'::regclass),
  "paymentStatus" character varying
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."PaymentStatus"
  OWNER TO postgres;

-- Table: public."Countries"

-- DROP TABLE public."Countries";

CREATE TABLE public."Countries"
(
  id bigint NOT NULL DEFAULT nextval('"Countries_id_seq"'::regclass),
  description character varying,
  abbreviation character varying,
  CONSTRAINT pk_countries PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Countries"
  OWNER TO postgres;

-- Table: public."ClaimStatus"

-- DROP TABLE public."ClaimStatus";

CREATE TABLE public."ClaimStatus"
(
  id bigint NOT NULL DEFAULT nextval('"ClaimStatus_id_seq"'::regclass),
  description character varying,
  CONSTRAINT "pk_claimStatus" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."ClaimStatus"
  OWNER TO postgres;
