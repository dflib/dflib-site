CREATE TABLE "person" ("id" bigint primary key, "name" varchar(100), "salary" double);

CREATE TABLE "address" ("id" bigint primary key, "person_id" bigint not null, "line1" varchar(100), "line2" varchar(100), "city" varchar(100), "zip" varchar(100), "country" varchar(100));