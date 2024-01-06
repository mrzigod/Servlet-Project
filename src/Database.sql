Create sequence item_ids
      INCREMENT BY 1
      MINVALUE 8
      START WITH 8
;

Create table Players(
    name varchar(63)  not null unique,
    current_inventory int not null,
    max_inv_size int  not null,
    money int not null,
    permissions int not null,
    won int default 0
);

Create table Items(
    item_id int not null unique default nextval('item_ids'),
    item_name varchar(63) not null,
    item_size int not null,
    cost int not null,
    dungeon int
);

Create table Inventories(
    player_name varchar(63),
    item_id int not null
);

insert into Items values
(1,'pierwszy_przedmiot',1,1,1),
(2,'drugi_przedmiot',2,2,1),
(3,'trzeci',3,3,1),
(4,'czwarty_przedmiot',4,4,1),
(5,'piaty_przedmiot',5,5,1),
(6,'szosty',6,6,1),
(7,'siodmy',7,7,1);