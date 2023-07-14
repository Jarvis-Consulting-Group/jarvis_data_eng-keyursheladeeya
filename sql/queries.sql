/*Modifying data*/
--1. Insert some data into a table
INSERT INTO cd.facilities VALUES (9, 'Spa', 20, 30, 100000, 800);

--2. Insert calculated data into a table
INSERT INTO cd.facilities VALUES ((SELECT max(facid)+1 FROM cd.facilities),'Spa', 20, 30, 100000, 800);

--3. Update some existing data
UPDATE cd.facilities SET initialoutlay = 10000 WHERE facid = 1;

--4. Update a row based on the contents of another row
UPDATE cd.facilities
SET membercost = (SELECT (membercost+membercost*0.1) from cd.facilities where facid=0),
guestcost = (SELECT (guestcost+guestcost*0.1) from cd.facilities where facid=0)
WHERE facid = 1;

--5. Delete all bookings
DELETE from cd.bookings;

--6. Delete a member from the cd.members table
DELETE from cd.members WHERE memid=37;

/*Basics*/

--1. Control which rows are retrieved - part 2
SELECT facid, name, membercost, monthlymaintenance FROM cd.facilities WHERE membercost > 0 AND membercost<monthlymaintenance/50;

--2. Basic string searches
SELECT * FROM cd.facilities WHERE name LIKE '%Tennis%';

--3. Matching against multiple possible values
SELECT * FROM cd.facilities WHERE facid IN (1,5);

--4. Working with dates
SELECT memid, surname, firstname, joindate FROM cd.members WHERE joindate >= '2012-09-01';

--5. Combining results from multiple queries
SELECT surname FROM cd.members UNION SELECT name FROM cd.facilities;


/* Joins */

--1. Retrieve the start times of members' bookings
SELECT starttime FROM cd.bookings INNER JOIN cd.members ON members.memid = bookings.memid WHERE firstname = 'David' AND surname = 'Farrell';

--2. Work out the start times of bookings for tennis courts
SELECT starttime, name FROM cd.facilities INNER JOIN cd.bookings ON bookings.facid = facilities.facid WHERE starttime >= '2012-09-21' AND starttime < '2012-09-22' AND name LIKE 'Tennis Court%';

--3. Produce a list of all members, along with their recommender
SELECT mem.firstname as memfname, mem.surname as memsname, rec.firstname as recfname, rec.surname as recsname from cd.members as mem LEFT OUTER JOIN cd.members as rec
ON rec.memid = mem.recommendedby ORDER BY memsname, memfname;

--4. Produce a list of all members who have recommended another member
select distinct m1.firstname as firstname, m1.surname as surname
from cd.members as m1
inner join cd.members as m2
on m1.memid = m2.recommendedby
order by m1.surname, m1.firstname;

--5. Produce a list of all members, along with their recommender, using no joins.
SELECT DISTINCT m.firstname || ' ' || m.surname AS member,
       (SELECT r.firstname || ' ' || r.surname
        FROM cd.members r
        WHERE r.memid = m.recommendedby) AS recommender
FROM cd.members m
ORDER BY member;

/* Aggregation */
--1.
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

--2.
select
  facid,
  sum(slots) as "total slots"
from
  cd.bookings
group by
  facid
order by
  facid;

--3.
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

--4.
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

--5.
select
  count(distinct memid)
from
  cd.bookings;

--6.
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

--7.
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

--8.
SELECT
  row_number() OVER(
    ORDER BY
      joindate
  ),
  firstname,
  surname
FROM
  cd.members;

--9.
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

/*String*/
--1.
SELECT
  surname || ', ' || firstname AS name
FROM
  cd.members;

--2.
SELECT
  memid,
  telephone
FROM
  cd.members
WHERE
  telephone LIKE '%(%';

--3.
SELECT
  LEFT(surname, 1) AS letter,
  COUNT(*)
FROM
  cd.members
GROUP BY
  letter
ORDER BY
  letter;
