INSERT INTO customer VALUES (1,admin, 35d0239415e2371ee283a773f215c036, Salt);
INSERT INTO customer VALUES (2,user1, b614d9bc7599d324e730dafbee318881, Salt1);
INSERT INTO customer VALUES (3,jdoe, 20718de57850c927f5127635a0a142bc, Salt2);
INSERT INTO customer VALUES (4,jrow, 713c448ae8ff09d84d4037ca5d4934d9, Salt3);
INSERT INTO customer VALUES (5,xxx, 50f1fa3d094cd33999010d52bcf348ee, Salt4);
INSERT INTO customer VALUES (6,null, 35d0239415e2371ee283a773f215c036, Salt);


INSERT INTO role VALUES (1, READ,admin);
INSERT INTO role VALUES (2, WRITE,admin);
INSERT INTO role VALUES (3, EXECUTE,admin);

INSERT INTO role VALUES (4, READ,user1);
INSERT INTO role VALUES (5, EXECUTE,user1);
INSERT INTO role VALUES (6, WRITE,user1);

INSERT INTO role VALUES (7, READ,jdoe);
INSERT INTO role VALUES (8, WRITE,jdoe);
INSERT INTO role VALUES (9, EXECUTE,jdoe);

INSERT INTO role VALUES (10, EXECUTE,jrow);

INSERT INTO role VALUES (11, READ,null);


INSERT INTO resource VALUES (1, A, null, null, null, 1);
INSERT INTO resource VALUES (2, A, null, null, null, 2);
INSERT INTO resource VALUES (3, A, null, null, null, 3);
INSERT INTO resource VALUES (4, B, null, null, null, 1);
INSERT INTO resource VALUES (5, B, null, null, null, 2);
INSERT INTO resource VALUES (6, B, null, null, null, 3);
INSERT INTO resource VALUES (7, C, null, null, null, 1);
INSERT INTO resource VALUES (8, C, null, null, null, 2);
INSERT INTO resource VALUES (9, C, null, null, null, 3);

INSERT INTO resource VALUES (10, A, null, null, null, 4);
INSERT INTO resource VALUES (11, A.B, null, null, null, 5);
INSERT INTO resource VALUES (12, XY.UV.ABCDEFGHIJ, null, null, null, 6);
INSERT INTO resource VALUES (13, A, null, null, null, 7);
INSERT INTO resource VALUES (14, A.B, null, null, null, 8);
INSERT INTO resource VALUES (15, A.B.C, null, null, null, 10);
INSERT INTO resource VALUES (16, A.BC, null, null, null, 9);
INSERT INTO resource VALUES (17, A.B, null, null, null, 11);
