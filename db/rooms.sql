DROP TABLE IF EXISTS CRIPTA_ROOMS;
CREATE TABLE CRIPTA_ROOMS(
    ID INT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    DESCRIPTION VARCHAR(2000),
    SETLOOK VARCHAR(2000),
    VISIBLE BOOLEAN,
    NORD INT,
    SUD INT,
    EST INT,
    OVEST INT

);
INSERT INTO CRIPTA_ROOMS (ID, NAME, DESCRIPTION, SETLOOK, VISIBLE, NORD, SUD, EST, OVEST) VALUES (0,'Cripta antica', 'Ti trovi nella cripta l''aria e'' fredda e umida, e la tua mente e'' avvolta nella nebbia:
non ricordi nulla del tuo passato.
Riuscirai a trovare una via d''uscita da questo luogo dimenticato dal tempo?', 
'Sei nella cripta. Le pareti di pietra sono ricoperte di muschio, e un silenzio pesante ti circonda.
E'' tutto buio, non distingui alcuna uscita...
ma per terra, un piccolo oggetto luccica nella penombra.
Forse potrebbe esserti utile.',
TRUE,1,NULL,NULL,NULL);
INSERT INTO CRIPTA_ROOMS (ID, NAME, DESCRIPTION, SETLOOK, VISIBLE, NORD, SUD, EST, OVEST) VALUES (1,'Corridoio oscuro', 'Ti trovi in un corridoio stretto e silenzioso.
Le pareti del corridoio sono incrinate e coperte di muffa e le ombre proiettate dalla torcia sono inquietanti.',
'Il corridoio e'' vuoto. Davanti a te ci sono due porte chiuse... quale sara'' quella giusta?',
FALSE,NULL,0,2,NULL);
INSERT INTO CRIPTA_ROOMS (ID, NAME, DESCRIPTION, SETLOOK, VISIBLE, NORD, SUD, EST, OVEST) VALUES(2,'Sala del rituale','Ti trovi nella sala del rituale.
Una sala circolare illuminata da una luce tremolante.
Al centro, un antico altare di pietra e'' coperto di incisioni e simboli sconosciuti.',
'Ti avvicini all''altare. Noti una coppa d''argento adagiata sulla pietra.',
TRUE,3,NULL,2,NULL);
INSERT INTO CRIPTA_ROOMS (ID, NAME, DESCRIPTION, SETLOOK, VISIBLE, NORD, SUD, EST, OVEST) VALUES (3,'Biblioteca Perduta','Ti trovi nella biblioteca perduta.
Scaffali altissimi ti circondano, colmi di libri antichi coperti di polvere.',
'In mezzo ai libri noti qualcosa. E'' una chiave dorata!!',
TRUE,NULL,2,NULL,4);
INSERT INTO CRIPTA_ROOMS (ID, NAME, DESCRIPTION, SETLOOK, VISIBLE, NORD, SUD, EST, OVEST) VALUES (4,'La camera del custode','Ti trovi nella Camera del Custode.
La stanza e'' immersa in un silenzio solenne.
Al centro, un grande sarcofago di pietra domina la sala.',
'Ti avvicini al sarcofago: Noti una serratura, ma e'' completamente ostruita da una sostanza misteriosa.
Senza forze,noti uno spiraglio nel muro da cui filtra una lieve luce azzurra.',
TRUE,NULL,NULL,3,5);
INSERT INTO CRIPTA_ROOMS (ID, NAME, DESCRIPTION, SETLOOK, VISIBLE, NORD, SUD, EST, OVEST) VALUES (5,'La sala delle lacrime','Ti infili nello stretto spiraglio e ti ritrovi nella Sala delle Lacrime.
In fondo alla stanza noti una vasca... da li'' proviene la luce azzurra!!',
'Ti avvicini lentamente alla vasca.
La coppa d''argento vibra tra le tue mani, e il liquido nella vasca sembra rispondere al suo richiamo.',
TRUE,NULL,NULL,4,NULL);








