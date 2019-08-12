insert into users values (1, 'Admińska 1', 'Adminowo', 'admin@admin.com', 'Admin', 'Admin', 'Admin','ADMIN', '123456789', '34-666', 'Admin');
insert into users values (2, 'Jana Pawła II 21/37', 'Wadowice', 'tester.testorowski@onet.pl', 'goracakotka', 'Karyna', '1234567890ABCDEF','CLIENT', '700666997', '34-666', 'Nosacz');
insert into users values (3, 'Modna 10', 'Kraków', 'tester.testorowski@onet.pl', 'bolce_banana', 'Jan', '1234567890ABCDEF','SELLER', '111222333', '34-666', 'Nowak');
insert into users values (4, 'Krwawa 69/2', 'Warszawa', 'tester.testorowski@onet.pl', 'BloodMarry666', 'Alicja', '1234567890ABCDEF','CLIENT', '222333444', '34-666', 'Kowalska');
insert into users values (5, 'Pudzianowskiego 17', 'Kielce', 'tester.testorowski@onet.pl', 'koko_menel', 'Janusz', '1234567890ABCDEF','SELLER', '333444555', '34-666', 'Tracz');

insert into application_forms values (1, 'Odzież, Obuwie i galanteria, Biżuteria i dodatki', 'Myszkikiszki', 'Jasik SA', 'Joanna Zdun, Krzysztof Kubicki', '2014-10-10', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes. Powder chocolate chocolate croissant I love topping gingerbread.','tester.testorowski@onet.pl', 'WAITING', '0000009754', 'Spółka akcyjna', '1239589754', '111222333', '177604806', 'ul. Tościkowa 10', 'Myszkikiszki', 'Ejszendem', '25-207', 'ul. Serduszkowa 21/37', 'Grzegorz','25-666','Jasiński', 'https://ejszendem.com', '2014');
insert into application_forms values (2, 'Odzież, Obuwie i galanteria, Biżuteria i dodatki', 'Myszkikiszki', 'Jasik SA', 'Joanna Zdun, Krzysztof Kubicki', '2014-10-10', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes. Powder chocolate chocolate croissant I love topping gingerbread.','tester.testorowski@onet.pl', 'CONSIDERED', '0000009754', 'Spółka akcyjna', '1239589754', '111222333', '177604806', 'ul. Tościkowa 10', 'Myszkikiszki', 'Ejszendem', '25-207', 'ul. Serduszkowa 21/37', 'Grzegorz','25-666','Jasiński', 'https://ejszendem.com', '2014');
insert into application_forms values (3, 'Odzież, Obuwie i galanteria, Biżuteria i dodatki', 'Myszkikiszki', 'Jasik SA', 'Joanna Zdun, Krzysztof Kubicki', '2014-10-10', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes. Powder chocolate chocolate croissant I love topping gingerbread.','tester.testorowski@onet.pl', 'WAITING_FOR_MONEY', '0000009754', 'Spółka akcyjna', '1239589754', '111222333', '177604806', 'ul. Tościkowa 10', 'Myszkikiszki', 'Raz Dwa TRZY', '25-207', 'ul. Serduszkowa 21/37', 'Grzegorz','25-666','Jasiński', 'https://ejszendem.com', '2014');
insert into application_forms values (4, 'Odzież, Obuwie i galanteria, Biżuteria i dodatki', 'Myszkikiszki', 'Jasik SA', 'Joanna Zdun, Krzysztof Kubicki', '2014-10-10', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes. Powder chocolate chocolate croissant I love topping gingerbread.','tester.testorowski@onet.pl', 'ACCEPTED', '0000009754', 'Spółka akcyjna', '1239589754', '111222333', '177604806', 'ul. Tościkowa 10', 'Myszkikiszki', 'Ejszendem', '25-207', 'ul. Serduszkowa 21/37', 'Grzegorz', 'Jasiński','25-666','https://ejszendem.com', '2014');
insert into application_forms values (5, 'Odzież, Obuwie i galanteria, Biżuteria i dodatki', 'Myszkikiszki', 'Jasik SA', 'Joanna Zdun, Krzysztof Kubicki', '2014-10-10', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes. Powder chocolate chocolate croissant I love topping gingerbread.','tester.testorowski@onet.pl', 'REJECTED', '0000009754', 'Spółka akcyjna', '1239589754', '111222333', '177604806', 'ul. Tościkowa 10', 'Myszkikiszki', 'Ejszendem', '25-207', 'ul. Serduszkowa 21/37', 'Grzegorz', 'Jasiński','25-666','https://ejszendem.com', '2014');

insert into security_alerts values (1, 'WAITING', 'NOT_MADE_IN_POLAND_SECURITY_ALERT', '2019-08-03', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes.', 'https://onlypolish.pl/shops/sampleShop/products/sampleProduct/', 3, 4);
insert into security_alerts values (2, 'WAITING', 'OFFENSIVE_OPINION_SECURITY_ALERT', '2019-08-03', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes.', 'https://onlypolish.pl/shops/sampleShop/products/sampleProduct/', 3, 4);
insert into security_alerts values (3, 'WAITING', 'CHEAT_SECURITY_ALERT', '2019-08-03', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes.', 'https://onlypolish.pl/shops/sampleShop/products/sampleProduct/', 3, 4);

insert into bug_raports values (1, '2019-08-04', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes.', 'WAITING', 3);
insert into bug_raports values (2, '2019-08-04', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes.', 'TO_REPAIR', 3);
insert into bug_raports values (3, '2019-08-04', 'Cupcake ipsum dolor sit amet cupcake carrot cake candy canes.', 'REPAIRED', 3);

insert into news values(1, 'news/news_20190301221034.txt', '2019-03-01 22:10:34', 'main.jpg', 'Okazje na Dzień Kobiet!');
insert into news values(2, 'news/news_20181202174416.txt', '2018-12-02 17:44:16', 'main2.jpg', 'Świąteczny zawrót głowy!');