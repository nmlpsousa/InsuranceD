SELECT 
  usertype.id AS usertypeid, 
  userstatus.id AS userstatusid, 
  client.username, 
  client.lastpasswordchangedate, 
  client.id AS clientid, 
  password.id AS passwordid, 
  password.isactive, 
  password.password, 
  personalidentification.id AS personalid, 
  personalidentification.fiscalnumber, 
  personalidentification.identificationno, 
  personalidentification.dateofbirth, 
  personalidentification.lastname, 
  personalidentification.firstname, 
  address.addressline1, 
  address.addressline2, 
  address.city, 
  address.postalcode, 
  address.id AS addressid, 
  phonenumber.id AS phonenumberid, 
  phonenumber.pref, 
  phonenumber.num, 
  countries.id AS countryid
FROM 
  public.client, 
  public.usertype, 
  public.userstatus, 
  public.personalidentification, 
  public.address, 
  public.phonenumber, 
  public.countries, 
  public.password
WHERE 
  client.personalid = personalidentification.id AND
  usertype.id = client.typeid AND
  userstatus.id = client.statusid AND
  personalidentification.addressid = address.id AND
  phonenumber.id = personalidentification.phonenumberid AND
  countries.id = address.countryid AND
  password.userid = client.id AND
  password.isactive = TRUE AND
  client.id = ?;
