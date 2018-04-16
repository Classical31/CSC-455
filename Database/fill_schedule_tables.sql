use schedule;

/*Clear tables*/
delete from employee;
delete from venue;
delete from work_history;
delete from salary;
delete from venue_emp_rating;
delete from request_off;
delete from swap_request;



/*Insert data into employee table*/
insert into employee values("13881", "Malin", "Kraft", "password01", "pho-num-0001", "email01@domain.com", "1");
insert into employee values("17000", "Christian", "Thorstensson", "password02", "pho-num-0002", "email02@domain.com", "0");
insert into employee values("17005", "Marcus", "Eriksson", "password03", "pho-num-0003", "email03@doamin.com", "0");
insert into employee values("17014", "Jennie", "Hansson", "password04", "pho-num-0004", "email04@domain.com", "0");
insert into employee values("17022", "Emelie", "Ojersson", "password05", "pho-num-0005", "email05@domain.com", "0");
insert into employee values("17035", "Maria", "Persson", "password06", "pho-num-0006", "email06@domain.com", "0");
insert into employee values("17037", "Linda", "Lindstrom", "password07", "pho-num-0007", "email07@domain.com", "0");
insert into employee values("17038", "Ninwa", "Jusef", "password08", "pho-num-0008", "email08@domain.com", "0");
insert into employee values("17041", "Natalie", "Leis", "password09", "pho-num-0009", "email09@domain.com", "0");
insert into employee values("17057", "Petra", "Eriksson", "password10", "pho-num-0010", "email10@domain.com", "0");
insert into employee values("17064", "Sofia", "Kalabic", "password11", "pho-num-0011", "email11@domain.com", "0");
insert into employee values("17084", "Sara", "Olson", "password12", "pho-num-0012", "email12@domain.com", "0");
insert into employee values("17088", "Gabriella", "Suren", "password13", "pho-num-0013", "email13@domain.com", "0");
insert into employee values("17089", "Maximilian", "Salen", "password14", "pho-num-0014", "email14@domain.com", "0");
insert into employee values("17094", "Daniella", "Yilmaz", "password15", "pho-num-0015", "email15@domain.com", "0");
insert into employee values("17100", "Disa", "Nilsson", "password16", "pho-num-0016", "email16@domain.com", "0");
insert into employee values("170104", "Emma", "Blomqvist", "password17", "pho-num-0017", "email17@domain.com", "0");
insert into employee values("17108", "Jonna", "Grevle", "password18", "pho-num-0018", "email18@domain.com", "0");
/*insert into employee values("17114", "Aurora", "", "password19", "pho-num-0019", "email19@domain.com", "0");*/

insert into employee values("17121", "Viktoria", "Carshaw", "password20", "pho-num-0020", "email20@domain.com", "0");
insert into employee values("17126", "Olivia", "Schultzberg", "password21", "pho-num-0021", "email21@domain.com", "0");
insert into employee values("17128", "Amanda", "Hessfelt", "password22", "pho-num-0022", "email22@domain.com", "0");
insert into employee values("17129", "Johanna", "Almqvist", "password23", "pho-num-0023", "email23@domain.com", "0");
insert into employee values("17130", "Vikorija", "Skorupskyte", "password24", "pho-num-0024", "email24@domain.com", "0");
insert into employee values("17131", "Andrea-Monica", "Riscu", "password25", "pho-num-0025", "email25@domain.com", "0");
insert into employee values("17132", "Alicia", "Enstedt", "password26", "pho-num-0026", "email26@domain.com", "0");
insert into employee values("17134", "Olivia", "Niklasson", "password27", "pho-num-0027", "email27@domain.com", "0");
insert into employee values("17136", "Moa", "Larsson", "password28", "pho-num-0028", "email28@domain.com", "0");
insert into employee values("17137", "Tanya", "Singh", "password29", "pho-num-0029", "email29@domain.com", "0");
insert into employee values("17139", "Vanja", "Faxell", "password30", "pho-num-0030", "email30@domain.com", "0");
insert into employee values("17140", "Elsa", "Ott", "password31", "pho-num-0031", "email31@domain.com", "0");
insert into employee values("17142", "Madelene", "Hjelman", "password32", "pho-num-0032", "email32@domain.com", "0");
insert into employee values("17143", "Diana", "Jaghobi", "password33", "pho-num-0033", "email33@domain.com", "0");
insert into employee values("17144", "Emelie", "Heale", "password34", "pho-num-0034", "email34@domain.com", "0");
insert into employee values("17145", "Elin", "Gustafsson", "password35", "pho-num-0035", "email35@domain.com", "0");
insert into employee values("17146", "Lina", "Stromberg", "password36", "pho-num-0036", "email36@domain.com", "0");
insert into employee values("817003", "Andrea", "Thorin", "password37", "pho-num-0037", "email37@domain.com", "0");
insert into employee values("817042", "Emma", "Degerstam", "password38", "pho-num-0038", "email38@domain.com", "0");
insert into employee values("817086", "Elly", "Yarali", "password39", "pho-num-0039", "email39@domain.com", "0");
insert into employee values("917061", "Josefina", "Wiklund", "password40", "pho-num-0040", "email40@domain.com", "0");
insert into employee values("917063", "Emmie", "Hansson", "password41", "pho-num-0041", "email41@domain.com", "0");

/*Insert data into venue table*/
insert into venue values("#", "Day Off", 0, "");
insert into venue values("BD", "El Barco", 2, "address01");
insert into venue values("BO", "Bordet", 1, "address02");
insert into venue values("DD", "DD", 1, "address03");
insert into venue values("DU", "Dubliner", 2, "address04");
insert into venue values("EC", "Event Casino", 3, "address05");
insert into venue values("EX", "Excet", 3, "address06");
insert into venue values("FS", "Flying Scotsman", 2, "address07");
insert into venue values("HB", "Henriksberg", 1, "address08");
insert into venue values("HR", "Hard Rock Cafe GBG", 2, "address09");
insert into venue values("JP", "John Scott's Palace", 2, "address10");
insert into venue values("KB", "Kungsbacka", 1, "address11");
insert into venue values("LA", "Valand", 5, "address12");
insert into venue values("OP", "Ospecifierant Pass", 6, "address13");
insert into venue values("RA", "Rastavlosen", 1, "address14");
insert into venue values("RB", "Rockbaren", 2, "address15");
insert into venue values("SH", "Steakhouse Goteburg", 1, "address16");
insert into venue values("TR", "Tradgar'n Goteborg", 2, "address17");
insert into venue values("VB", "Vasa Bar", 1, "address18");
insert into venue values("VI", "Viiva", 1, "address19");

/*Insert into BlackList*/
insert into blacklist values('13881','BD');
insert into  blacklist values('17041','LA');

/*Insert data into work_history table */
insert into work_history values('17128','2018-01-01','LA','EC','RB','RA','EX','RB','DD');
insert into work_history values('17038','2018-01-01','VI','TR','BD','EC','KB','OP','KB');
insert into work_history values('17094','2018-01-01','OP','FS','JP','VB','HB','SH','BD');
insert into work_history values('817086','2018-01-01','HR','HR','VB','DU','TR','DD','EC');
insert into work_history values('17005','2018-01-01','VB','TR','DD','RB','HR','BO','DD');
insert into work_history values('13881','2018-01-01','HR','BD','LA','RA','EX','VB','BD');
insert into work_history values('17057','2018-01-01','DD','BO','VI','TR','VB','SH','RA');

/*Insert data into salary table */
insert into salary values('17084',1936);
insert into salary values('817086',14027);
insert into salary values('17134',16499);
insert into salary values('17130',69516);
insert into salary values('17136',35201);
insert into salary values('17088',38907);
insert into salary values('17041',55260);
insert into salary values('17139',63258);
insert into salary values('817042',72908);
insert into salary values('17037',6395);
insert into salary values('17057',25413);
insert into salary values('17129',43334);
insert into salary values('917063',18538);
insert into salary values('917063',17847);
insert into salary values('17146',31858);
insert into salary values('17041',78852);
insert into salary values('17121',49326);
insert into salary values('17108',13310);
insert into salary values('17121',4712);
insert into salary values('17139',55625);
insert into salary values('17038',36449);
insert into salary values('17000',25450);
insert into salary values('817086',20226);
insert into salary values('17084',44804);
insert into salary values('17146',90);
insert into salary values('817003',883);
insert into salary values('17146',60321);
insert into salary values('17014',1208);
insert into salary values('17041',8587);
insert into salary values('17005',71377);
insert into salary values('17132',4293);
insert into salary values('17126',76022);
insert into salary values('17131',57644);
insert into salary values('17145',47385);
insert into salary values('17094',52384);
insert into salary values('17000',4698);
insert into salary values('817042',76679);
insert into salary values('17126',65969);
insert into salary values('17000',37037);
insert into salary values('817086',43393);
insert into salary values('17000',64062);

/*Insert data into venue_emp_rating table */
insert into venue_emp_rating values('OP','17129',10);
insert into venue_emp_rating values('HB','817042',6);
insert into venue_emp_rating values('LA','17005',9);
insert into venue_emp_rating values('BD','13881',7);
insert into venue_emp_rating values('HR','17038',5);
insert into venue_emp_rating values('JP','17142',5);
insert into venue_emp_rating values('RB','17094',8);
insert into venue_emp_rating values('OP','17100',6);

/*Insert data into request_off table */
insert into request_off values('17130','2018-04-07',True,'17146');
insert into request_off values('17129','2018-03-09',True,'17005');
insert into request_off values('13881','2018-10-8',False,null);
insert into request_off values('917061','2018-09-11',False,null);
insert into request_off values('17094','2018-10-06',True,'17131');
insert into request_off values('17128','2018-03-09',False,null);
insert into request_off values('17134','2018-03-07',False,null);
insert into request_off values('817003','2018-04-09',True,'17035');

/* Insert data into swap_request */
/*
Example for trigger
insert into swap_request(employeeID,weekOfRequest,dayOf,employee2,dayOf2) values('17038','2018-04-15','tues','wed','17000');
*/