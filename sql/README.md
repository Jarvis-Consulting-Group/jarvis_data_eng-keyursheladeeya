# Introduction

The SQL Project allows us to familiarize ourselves with concepts surrounding relational databases. These concepts are
relational database management systems, structured query language, SQL optimization techniques, and data modeling.
We also had a variety of tasks, answering questions about the concepts relating to SQL and implementing queries on
existing data provided to us. To work on implementing queries, we had to use a variety of tools such as Git for
managing the code history of SQL files, a Docker container running our PostgreSQL database, installing pgAdmin;
an integrated development environment specifically for PostgreSQL, and lastly, we inserted the existing data file
(clubdata.sql) into the database using the command pql -h localhost -U postgres -f clubdata.sql -d postgres -x -q.

# SQL Queries

###### Table Setup (DDL)

Following the provided entity-relational data model, there are three tables we must implement. The cd.facilities tables
with a primary key of facid (facility id), the cd.members table with a primary key of memid (member id) and foreign
key of recommendedby which points back to the memid of the cd.members table, and lastly a joining table between
cd.facilities and cd.members called cd.bookings with foreign keys facid referencing facid from cd.facilities
(this is also the primary key), and memid referencing memid in cd.members.

```sql
CREATE TABLE cd.facilities (
    "facid" integer NOT NULL,
    "name" character varying(100),
    "membercost" numeric,
    "guestcost" numeric,
    "initialoutlay" numeric,
    "monthlymaintenance" numeric,
    CONSTRAINT "PK_facid" PRIMARY KEY ("facid")
);

CREATE TABLE cd.members (
    "memid" integer NOT NULL,
    "surname" character varying(200) NOT NULL,
    "firstname" character varying(200) NOT NULL,
    "address" character varying(300) NOT NULL,
    "zipcode" integer NOT NULL,
    "telephone" character varying(20) NOT NULL,
    "recommendedby" integer,
    "joindate" timestamp NOT NULL,
    CONSTRAINT "PK_memid" PRIMARY KEY ("memid"),
    CONSTRAINT "FK_recommendby" FOREIGN KEY ("recommendedby")
        REFERENCES public.members ("memid") ON DELETE SET NULL
);

CREATE TABLE cd.bookings (
    "facid" integer NOT NULL,
    "memid" integer NOT NULL,
    "starttime" timestamp NOT NULL,
    "slots" integer NOT NULL,
    CONSTRAINT "pk_bookings_facid" PRIMARY KEY ("facid"),
    CONSTRAINT "fk_facilities_facid" FOREIGN KEY ("facid")
        REFERENCES public.facilities ("facid"),
    CONSTRAINT "fk_members_memid" FOREIGN KEY ("memid")
        REFERENCES public.members ("memid")
);
```

###### Question 1: Insert a new facility into the facilities table.

- Inserted a new record into the cd.facilities table with the given values. (9, 'Spa', 20, 30, 100000, 800)

```sql
INSERT INTO cd.facilities 
VALUES (9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2: Insert the Spa facility again but automatically get the next facility ID.

- Automatically generated the facility ID using a sub-query that counts all the records. Since the IDs are
  zero-based, the count will return the subsequent number.

```sql
INSERT INTO cd.facilities 
VALUES 
  (
    (
      SELECT 
        max(facid)+ 1 
      FROM 
        cd.facilities
    ), 
    'Spa', 
    20, 
    30, 
    100000, 
    800
  );

```

###### Question 3: Update the initial outlay for the second tennis court to 10000.

- Used the UPDATE statement to change the initial outlay value for the second tennis court. The conditions for
  the WHERE clause are to specify which record was initial outlay = 80000 and pattern matching with '%Tennis Court%'.

```sql
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid = 1;
```

###### Question 4: Update the costs for the second tennis court so that it is 10% more than the first tennis court.

- Used the UPDATE statement to change the member cost and guest cost fields for the second tennis court.
  We used sub-queries to get the first tennis court's cost values and multiplied by 1.1 (to get 10% more).

```sql
UPDATE 
  cd.facilities 
SET 
  membercost = (
    SELECT 
      (membercost + membercost * 0.1) 
    from 
      cd.facilities 
    where 
      facid = 0
  ), 
  guestcost = (
    SELECT 
      (guestcost + guestcost * 0.1) 
    from 
      cd.facilities 
    where 
      facid = 0
  ) 
WHERE 
  facid = 1;

```

###### Question 5: Delete all the records within the bookings table.

- Used a DELETE statement without a WHERE clause to delete all the records.

```sql
DELETE FROM
    cd.bookings;
```

###### Question 6: Delete a member from the cd.members table.

- Used a DELETE statement to remove the record with member ID 37 by using a WHERE clause.

```sql
DELETE from 
  cd.members 
WHERE 
  memid = 37;
```

###### Question 7: Select records that charges members fees and the fee is 1/50th of the monthly maintenance cost.

- Used a SELECT statement to retrieve records where the member cost was above 0 (a fee exists), and a
  calculation where that member cost was less than the monthly maintenance divided by 50.

```sql
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  membercost > 0 
  AND membercost < monthlymaintenance / 50;
```

###### Question 8: Retrieve all facilities with the word 'Tennis' in their name.

- Used a SELECT statement with a WHERE clause which utilizes pattern matching to
  find names with the word 'Tennis' ('%Tennis%').

```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis%';
```

###### Question 9: Retrieve the records with the member IDs of 1 and 5 without using the OR operator.

- Used a SELECT statement with a WHERE clause that has an IN operator. The IN operator matches values by the list
  inside the parentheses.

```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  facid IN (1, 5);
```

###### Question 10: Get a list of records of members who joined on and after September 2012.

- Used a SELECT statement and WHERE clause where the join date was equal to or greater than September 1st, 2012.

```sql
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate >= '2012-09-01';
```

###### Question 11: Use a UNION operator to combine two statements.

- Used a UNION operator to combine two SELECT statements. The SELECT statements returned the surname of members and
  facility names from facilities. The UNION operator combines the two since both SELECT statements return one column
  of character-varying data type.

```sql
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
```

START HERE

###### Question 12: Retrieve all start times from bookings for the member 'David Farrell'

- Used a SELECT statement that JOINs the tables bookings with the table members on the member ID. A WHERE clause was
  also included returning matching rows where the member's first name is 'David' and surname is 'Farrell'.

```sql
SELECT 
  starttime 
FROM 
  cd.bookings 
  INNER JOIN cd.members ON members.memid = bookings.memid 
WHERE 
  firstname = 'David' 
  AND surname = 'Farrell';
```

###### Question 13: Retrieve records of start times for the tennis court facilities on September 21st, 2012.

- Used a SELECT statement that JOINs the bookings and facilities table with a WHERE clause for facility names like
  '%Tennis Court%' and timestamps on September 21st, 2012 (timestamp >= '2012-09-21' AND timestamp < '2012-09-22').

```sql
SELECT 
  starttime, 
  name 
FROM 
  cd.facilities 
  INNER JOIN cd.bookings ON bookings.facid = facilities.facid 
WHERE 
  starttime >= '2012-09-21' 
  AND starttime < '2012-09-22' 
  AND name LIKE 'Tennis Court%';
```

###### Question 14: Use self-join to retrieve the name of members and their recommender.

- Used a SELECT statement that JOINs to itself (self-join using aliases).
  The table then returns the member's name and the names of their recommender (recommended by ID).

```sql
SELECT 
  mem.firstname as memfname, 
  mem.surname as memsname, 
  rec.firstname as recfname, 
  rec.surname as recsname 
from 
  cd.members as mem 
  LEFT OUTER JOIN cd.members as rec ON rec.memid = mem.recommendedby 
ORDER BY 
  memsname, 
  memfname;
```

###### Question 15: Retrieve the names of all members who have recommended someone.

- Used a SELECT statement that joins the table to itself (self-join using aliases). We then return
  unique values just in case someone has recommended more than one member.

```sql
select 
  distinct m1.firstname as firstname, 
  m1.surname as surname 
from 
  cd.members as m1 
  inner join cd.members as m2 on m1.memid = m2.recommendedby 
order by 
  m1.surname, 
  m1.firstname;
```

###### Question 16: Retrieve the names of members and their recommenders without using JOIN keyword.

- Used a SELECT statement with a sub-query to retrieve the recommender's name and ordered it by the first column.

```sql
SELECT 
  DISTINCT m.firstname || ' ' || m.surname AS member, 
  (
    SELECT 
      r.firstname || ' ' || r.surname 
    FROM 
      cd.members r 
    WHERE 
      r.memid = m.recommendedby
  ) AS recommender 
FROM 
  cd.members m 
ORDER BY 
  member;
```

###### Question 17: Count the number of recommendations by member.

- Used the COUNT aggregate function to retrieve the number of times a member has recommended someone.
  The result is grouped and ordered by the column 'recommendedby'.

```sql
SELECT 
  recommendedby, 
  count(*) 
from 
  cd.members 
where 
  recommendedby is not null 
group by 
  recommendedby 
order by 
  recommendedby;
```

###### Question 18: Retrieve the SUM of slots by facility.

- Used the SUM aggregate function grouped by facility ID to retrieve the sum of slots.
  The results are then ordered by facility ID.

```sql
select 
  facid, 
  sum(slots) as "total slots" 
from 
  cd.bookings 
group by 
  facid 
order by 
  facid;
```

###### Question 19: Retrieve the SUM of slots by facility during the month of September 2012.

- Used the SUM aggregate function grouped by facility ID with a WHERE clause for records during September 2012
  to retrieve the sum of slots for that month. The result is in ascending order by the aggregate column.

```sql
select 
  facid, 
  sum(slots) as "total slots" 
from 
  cd.bookings 
where 
  starttime >= '2012-09-01' 
  and starttime < '2012-10-01' 
group by 
  facid 
order by 
  sum(slots);
```

###### Question 20: Use multiple group bys to retrieve the sum of slots for facilities during each month.

- Used the SUM aggregate function grouped by facility ID and month
  number (A digit from the timestamp is retrieved using EXTRACT() function) where the year was 2012.

```sql
select 
  facid, 
  extract(
    month 
    from 
      starttime
  ) as month, 
  sum(slots) 
from 
  cd.bookings 
where 
  extract(
    year 
    from 
      starttime
  ) = 2012 
group by 
  facid, 
  month 
order by 
  facid, 
  month;
```

###### Question 21: Find the number of members who have made at least one booking.

- Used the COUNT aggregate function with the DISTINCT keyword to retrieve the number of unique member ID values in bookings.

```sql
select 
  count(distinct memid) 
from 
  cd.bookings;
```

###### Question 22: Retrieve members who had their first booking after September 1st, 2012.

- Used the MIN aggregate function to find the first booking date of members on or after September 1st, 2012.
  Retrieved the name fields from the member's table through a join.

```sql
SELECT 
  m.surname, 
  m.firstname, 
  m.memid, 
  min(b.starttime) 
FROM 
  cd.members m 
  INNER JOIN cd.bookings b ON m.memid = b.memid 
WHERE 
  b.starttime >= '2012-09-01' 
GROUP BY 
  m.surname, 
  m.firstname, 
  m.memid 
ORDER BY 
  m.memid;
```

###### Question 23: Retrieve all member names and a column containing the total number of members.

- Used the COUNT window function with OVER, so the total number of members is printed on all rows
  as it treats all rows as one group.

```sql
SELECT 
  (
    SELECT 
      COUNT(*) 
    FROM 
      cd.members
  ), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;

```

###### Question 24: Retrieve all member names and the row number as a field.

- Used the ROW_NUMBER window function with the OVER option so the query prints the row number on all rows.

```sql
SELECT 
  row_number() OVER(
    ORDER BY 
      joindate
  ), 
  firstname, 
  surname 
FROM 
  cd.members;
```

###### Question 25: Retrieve the highest SUM of slots by facility (if there is a tie print both).

- Used a order by clause to get the descending of slots and limit the num of rows to get the 1st.

```sql
SELECT 
  facid, 
  sum(slots) AS total 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  total DESC 
LIMIT 
  1;
```

###### Question 26: Combine and format the surname and first name fields of members.

- Combined surname and first name with string concatenation using the || operator.

```sql
SELECT 
  surname || ', ' || firstname AS name 
FROM 
  cd.members;
```

###### Question 27: Retrieve all records with telephone numbers that have parentheses.

- Used pattern matching in the WHERE clause to find strings telephone numbers with parentheses '%(%'
  (values that starts with.

```sql
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone LIKE '%(%';
```

###### Question 28: Count the number of members by the first letter of their surname.

- Used a left function to retrieve the first letter of a member's surname,
  a COUNT aggregate function, and group by the first column.

```sql
SELECT 
  LEFT(surname, 1) AS letter, 
  COUNT(*) 
FROM 
  cd.members 
GROUP BY 
  letter 
ORDER BY 
  letter;
```