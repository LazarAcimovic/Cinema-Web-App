insert into bioskop(id, naziv, adresa) 
values
(nextval('bioskop_seq'), 'Galaksija', 'Prote Smiljanica 4'),
(nextval('bioskop_seq'), 'Dugina Dvorana', 'Kralja Petra prvog 10'),
(nextval('bioskop_seq'), 'Daleki Horizont', 'Dositejeva 50'),
(nextval('bioskop_seq'), 'Diablo', 'Cara Dusana 100');

insert into film(id, recenzija, trajanje, naziv, zanr)
values
(nextval('film_seq'),50, 240, 'Inception', 'naucna fantastika'),
(nextval('film_seq'),60, 250, 'The godfather', 'drama'),
(nextval('film_seq'),70, 240, 'The Dark Knight', 'kriminalistika'),
(nextval('film_seq'),80, 240, 'Matrix', 'naucna fantastika');


insert into sala(id, bioskop, broj_redova, kapacitet)
values
(nextval('sala_seq'),1, 100, 210),
(nextval('sala_seq'),2, 110, 220),
(nextval('sala_seq'),3, 120, 230),
(nextval('sala_seq'),4, 130, 240);


insert into rezervacija(id, broj_osoba, cena_karte, film, placeno, sala, datum)
values
(nextval('rezervacija_seq'),50, 240, 1, true, 1, to_date('12.12.2024', 'dd.mm.yyyy')),
(nextval('rezervacija_seq'),60, 240, 2, true, 2, to_date('13.12.2024', 'dd.mm.yyyy')),
(nextval('rezervacija_seq'),70, 240, 3, false, 3, to_date('20.12.2024', 'dd.mm.yyyy')),
(nextval('rezervacija_seq'),80, 240, 4, false, 4, to_date('25.12.2024', 'dd.mm.yyyy'));






