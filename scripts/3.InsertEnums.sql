INSERT INTO public.claimstatus(description) VALUES ('Draft');
INSERT INTO public.claimstatus(description) VALUES ('Validated');
INSERT INTO public.claimstatus(description) VALUES ('Payment');
INSERT INTO public.claimstatus(description) VALUES ('Processed');

INSERT INTO public.countries(description, abbreviation) VALUES ('Portugal','PT');
INSERT INTO public.countries(description, abbreviation) VALUES ('Spain','ES');
INSERT INTO public.countries(description, abbreviation) VALUES ('United Status','US');
INSERT INTO public.countries(description, abbreviation) VALUES ('France','FR');

INSERT INTO public.paymentstatus(paymentstatus) VALUES ('Open');
INSERT INTO public.paymentstatus(paymentstatus) VALUES ('Closed');

INSERT INTO public.userstatus(userstatus) VALUES ('Active');
INSERT INTO public.userstatus(userstatus) VALUES ('Inactive');
INSERT INTO public.userstatus(userstatus) VALUES ('Pending');

INSERT INTO public.usertype(usertype) VALUES ('Client');
INSERT INTO public.usertype(usertype) VALUES ('Account Manager');