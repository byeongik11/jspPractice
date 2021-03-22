CREATE TABLE GUESTBOOK(
	guestbook_no NUMBER(15) NOT NULL,
	guestbook_id VARCHAR2(15) NOT NULL,
	guestbook_password VARCHAR(15) NOT NULL,
	guestbook_content VARCHAR(1000),
	guestbook_group NUMBER(15),
	guestbook_parent NUMBER(15),
	guestbook_date DATE,
	CONSTRAINT PK_GUESTBOOK PRIMARY KEY(guestbook_no));

	
CREATE SEQUENCE guestbook_no_seq;
