INSERT INTO public."ClaimStatus"(description) VALUES ('Draft');
INSERT INTO public."ClaimStatus"(description) VALUES ('Validated');
INSERT INTO public."ClaimStatus"(description) VALUES ('Payment');
INSERT INTO public."ClaimStatus"(description) VALUES ('Processed');

INSERT INTO public."Countries"(description, abbreviation) VALUES ('Portugal','PT');
INSERT INTO public."Countries"(description, abbreviation) VALUES ('Spain','ES');
INSERT INTO public."Countries"(description, abbreviation) VALUES ('United Status','US');
INSERT INTO public."Countries"(description, abbreviation) VALUES ('France','FR');

INSERT INTO public."PaymentStatus"("paymentStatus") VALUES ('Open');
INSERT INTO public."PaymentStatus"("paymentStatus") VALUES ('Closed');

INSERT INTO public."UserStatus"("userStatus") VALUES ('Active');
INSERT INTO public."UserStatus"("userStatus") VALUES ('Inactive');
INSERT INTO public."UserStatus"("userStatus") VALUES ('Pending');

INSERT INTO public."UserType"("userType") VALUES ('Client');
INSERT INTO public."UserType"("userType") VALUES ('Account Manager');