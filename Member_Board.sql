create table Member_Board(
board_num number not null,
board_id varchar2(50),
board_subject varchar2(100),
board_content varchar2(2000),
board_file varchar2(100),
board_re_ref number,
board_re_lev number,
board_re_seq number,
board_count number,
board_date date,
constraint pk_Member_Board primary key(board_num));

create sequence board_num;

alter table Member_Board
add constraint pk_board_id foreign key(board_id)
references jsp_member(id);

ALTER TABLE MEMBER_BOARD
ADD(BOARD_PARENT NUMBER(10));