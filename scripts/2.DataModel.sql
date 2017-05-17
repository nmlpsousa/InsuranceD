-- Table: public.password

-- DROP TABLE public.password;

CREATE TABLE public.password
(
  password character varying,
  userid bigint,
  isactive boolean,
  id bigint NOT NULL DEFAULT nextval('password_id_seq'::regclass),
  CONSTRAINT pk_password PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.password
  OWNER TO postgres;

-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
  personalid bigint,
  typeid bigint,
  username character varying,
  lastpasswordchangedate date,
  statusid bigint
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users
  OWNER TO postgres;
  
-- Table: public.client

-- DROP TABLE public.client;

CREATE TABLE public.client
(
-- Inherited from table users:  personalid bigint,
-- Inherited from table users:  typeid bigint,
-- Inherited from table users:  username character varying,
-- Inherited from table users:  lastpasswordchangedate date,
-- Inherited from table users:  statusid bigint,
  id bigint NOT NULL DEFAULT nextval('client_id_seq'::regclass),
  CONSTRAINT pk_client PRIMARY KEY (id)
)
INHERITS (public.users)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.client
  OWNER TO postgres;

-- Table: public.accountmanager

-- DROP TABLE public.accountmanager;

CREATE TABLE public.accountmanager
(
-- Inherited from table users:  personalid bigint,
-- Inherited from table users:  typeid bigint,
-- Inherited from table users:  username character varying,
-- Inherited from table users:  lastpasswordchangedate date,
-- Inherited from table users:  statusid bigint,
  employeeno integer,
  id bigint NOT NULL DEFAULT nextval('accountmanager_id_seq'::regclass),
  CONSTRAINT pk_accountmanager PRIMARY KEY (id)
)
INHERITS (public.users)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.accountmanager
  OWNER TO postgres;

-- Table: public.personalidentification

-- DROP TABLE public.personalidentification;

CREATE TABLE public.personalidentification
(
  firstname character varying,
  lastname character varying,
  dateofbirth date,
  addressid bigint,
  identificationno character varying,
  fiscalnumber character varying,
  id bigint NOT NULL DEFAULT nextval('personalidentification_id_seq'::regclass),
  phonenumberid bigint,
  CONSTRAINT pk_personalidentification PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.personalidentification
  OWNER TO postgres;

-- Table: public.address

-- DROP TABLE public.address;

CREATE TABLE public.address
(
  addressline1 character varying,
  addressline2 character varying,
  city character varying,
  postalcode character varying,
  countryid integer,
  id bigint NOT NULL DEFAULT nextval('address_id_seq'::regclass)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.address
  OWNER TO postgres;

-- Table: public.phonenumber

-- DROP TABLE public.phonenumber;

CREATE TABLE public.phonenumber
(
  id bigint NOT NULL DEFAULT nextval('phonenumber_id_seq'::regclass),
  pref character varying,
  num integer,
  CONSTRAINT pk_phonenumber PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.phonenumber
  OWNER TO postgres;

-- Table: public.claim

-- DROP TABLE public.claim;

CREATE TABLE public.claim
(
  id bigint NOT NULL DEFAULT nextval('claim_id_seq'::regclass),
  coverableid bigint,
  claimno integer,
  description character varying,
  incidentdate date,
  claimstatusid bigint,
  reservelineid bigint,
  CONSTRAINT pk_claim PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.claim
  OWNER TO postgres;

-- Table: public.coverable

-- DROP TABLE public.coverable;

CREATE TABLE public.coverable
(
  id bigint NOT NULL DEFAULT nextval('coverable_id_seq'::regclass),
  coverableno integer,
  description character varying,
  policyid bigint,
  CONSTRAINT pk_coverable PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.coverable
  OWNER TO postgres;

-- Table: public.coverage

-- DROP TABLE public.coverage;

CREATE TABLE public.coverage
(
  id bigint NOT NULL DEFAULT nextval('coverage_id_seq'::regclass),
  coverableid bigint,
  lim double precision,
  premium double precision,
  description character varying,
  CONSTRAINT pk_coverage PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.coverage
  OWNER TO postgres;

-- Table: public.payment

-- DROP TABLE public.payment;

CREATE TABLE public.payment
(
  id bigint NOT NULL DEFAULT nextval('payment_id_seq'::regclass),
  paymentno integer,
  coverageid bigint,
  payee character varying,
  amount double precision,
  paymentstatusid bigint,
  CONSTRAINT pk_payment PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.payment
  OWNER TO postgres;

-- Table: public.reserveline

-- DROP TABLE public.reserveline;

CREATE TABLE public.reserveline
(
  id bigint NOT NULL DEFAULT nextval('reserveline_id_seq'::regclass),
  description character varying,
  lim double precision,
  usedfunds double precision,
  CONSTRAINT pk_reserveline PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.reserveline
  OWNER TO postgres;

-- Table: public.policy

-- DROP TABLE public.policy;

CREATE TABLE public.policy
(
  id bigint NOT NULL DEFAULT nextval('policy_id_seq'::regclass),
  startdate date,
  enddate date,
  premium double precision,
  userid bigint,
  CONSTRAINT pk_policy PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.policy
  OWNER TO postgres;

-- Table: public.usertype

-- DROP TABLE public.usertype;

CREATE TABLE public.usertype
(
  id bigint NOT NULL DEFAULT nextval('usertype_id_seq'::regclass),
  usertype character varying,
  CONSTRAINT pk_usertype PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.usertype
  OWNER TO postgres;

-- Table: public.userstatus

-- DROP TABLE public.userstatus;

CREATE TABLE public.userstatus
(
  id bigint NOT NULL DEFAULT nextval('userstatus_id_seq'::regclass),
  userstatus character varying,
  CONSTRAINT pk_userstatus PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.userstatus
  OWNER TO postgres;

-- Table: public.paymentstatus

-- DROP TABLE public.paymentstatus;

CREATE TABLE public.paymentstatus
(
  id bigint NOT NULL DEFAULT nextval('paymentstatus_id_seq'::regclass),
  paymentstatus character varying,
  CONSTRAINT pk_paymentstatus PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.paymentstatus
  OWNER TO postgres;

-- Table: public.countries

-- DROP TABLE public.countries;

CREATE TABLE public.countries
(
  id bigint NOT NULL DEFAULT nextval('countries_id_seq'::regclass),
  description character varying,
  abbreviation character varying,
  CONSTRAINT pk_countries PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.countries
  OWNER TO postgres;

-- Table: public.claimstatus

-- DROP TABLE public.claimstatus;

CREATE TABLE public.claimstatus
(
  id bigint NOT NULL DEFAULT nextval('claimstatus_id_seq'::regclass),
  description character varying,
  CONSTRAINT pk_claimstatus PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.claimstatus
  OWNER TO postgres;