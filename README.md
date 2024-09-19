I want to build a web application using spring boot for backend, angular for front end, and mysql for database. I would like to deploy it on my registered domain using aws. 

user logic:

As a user, I would like to be able to login with my own username and password(i will most likely preset these depending if I want to write create account logic). I would like to be able to enter daily inbound inspections including car mark and number, inspection date, checkbox on if it was repaired or not, checkbox on if it was bad ordered or not. If the car is bad ordered, I would like to populate a field with a description on why it was bad ordered and add it on to a bad order list for tracking purposes. I would like to be able to view my entered inbound inspections in table view, that is responsive with pagination, with sort and filter functions for user experience, update and delete options for user entries. I would then like to be able to view my entered bad orders in table view, that is responsive with pagination, with sort and filter functions, and update and delete options for entries.

user query logic:

Additionally, as a user, I would like to be able to query a timeframe (to/from date) and get a total number of cars inspected, total number of cars repaired(on inspection), total number of cars bad ordered. I would like to be able to search by car number to retrieve the most recent inspection date(s), search by date to retrieve the car mark, car numbers inspected on that day. As for bad orders, I would like to search by car number, repair date to retrieve the car mark, car number, bad order reason, bad order date, and repair date. I would like to be able to export the table of bad ordered railcars into excel for easy printing and viewing, showing columns: car mark, car number, date bad ordered, bad ordered reason. 

-----------------------------------------------------------------------------------------------------------------------------------------------


Project structure

View:

1. Welcome/login page for layered security, enter username and password (make sure no sql injection possible)
2. Home page with snapshot view of tables and metrics, possibly a quick form entry spot for user experience.
3. Form entry for inbound railcar inspections with car mark, car number, repaired on inbound (y/n), date, bad order (y/n), bad order reason; when user selects bad order, it will put it in the bad order table, but also keep it in the inspection table for metrics tracking. will have visual indicators to show that car is bad ordered (ie, red highlight or styling to make it obvious)
4. table view for cars inspected, allowing the user to filter by date, sort by fields, update and delete entry etc. Query search and possible export to excel function? Query is more important on inbound table.
5. Bad order table view showing all railcars that have been selected for bad order allowing user to filter/sort by fields, update and delete entry. Query and export functions with a nice table format? More important for bad order table to maintain bad order tracking.



----------------------------------------------------------------------------------------------------------------------------------------------------

Business logic brainstorm:

//best practice to handle relationship between inbound and bad order(should there be anything shared with carMark and carNumber? 1 to 1 relation?)

Use inboundId as the Foreign Key
I can set up a relationship where the BadOrderedRailcar table references the InboundRailcar table using the inboundId:

Workflow:

When isBadOrdered is set to true in an InboundRailcar, you create a corresponding entry in the BadOrderedRailcar table with the same inboundId.
I can use a trigger or application logic to automatically insert data into BadOrderedRailcar when isBadOrdered becomes true.




//best practice to handle repairedDate, update and delete logic

When BadOrderedRailcar object has a repairedDate, it will be removed from the active bad order list by checking if isActive is false. If isActive is true, it will be displayed on the bad order table, if isActive is false, it will be archived for query.


There will be delete methods for the InboundRailcar table and BadOrderedRailcar table. When an inbound railcar is deleted, it will be deleted from the database in InboundRailcar and BadOrderedRailcar. When a bad ordered railcar is deleted, it will be deleted from the database in InboundRailcar and BadOrderedRailcar

There will be update methods for the InboundRailcar table and BadOrderedRailcar table. When either an inbound railcar or bad ordered railcar is updated, it will be updated from the database in InboundRailcar and BadOrderedRailcar

--------------------------------------------------------------------------------------------------------------------------------------------------


