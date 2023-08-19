Application Details
 - This is a Client Scheduling Application.
 - The purpose of this application is for authorized users to add and update customer records in order to create and update appointments with those customers.


Author Specifications
 - This application was programmed by Zachary Hoppock, who can be reached at zhoppc@wgu.edu or zacharyhoppock@gmail.com.
 - This is version 1.1 of the finished application, finalized on 8/10/2023.


IDE Used
 - The application was made using IntelliJ IDEA Community Edition 2021.1.3 with Oracle OpenJDK version 17.0.6 and JavaFX-SDK-17.0.8.


How To Use
 - When the application boots up, you will be faced with a login screen that you will need to input authorized credentials into.
 - There is a button on the top right that will allow to exit out of the application as long as you are logged out.
 - Once you have successfully logged in, you may or may not be alerted to an upcoming appointment you have with a customer.
 	- You should then be at the Main Menu, which allows you to navigate to the Customers screen, the Appointments screen, one of the three database reports, or to logout of the application.
 	- Keep in mind that every page going forward will have buttons on the top right to either return to the Main Menu or Logout of the application.
 		- In the Customers screen, you can view a table of customers, along with buttons to add a customer, update an existing customer, delete an existing customer, or create an appointment for an existing customer.
		- Be sure that a customer has no appointments scheduled before you delete it or you will get an alert.
 			- In the Add Customer screen, you will need to fill out all the parameters and selection boxes appropriately before saving your new customer.
 			- You can cancel this at anytime, but you will be asked if you are sure you want to leave the screen if you had already filled in anything.
			- The Update Customer screen is the same as the Add Customer screen, with the addition of already filled in parameters based on the customer you selected from the table.
			- You can cancel this at anytime, but you will be asked if you are sure you want to leave the screen if you had changed anything.
			- In the Add Appointment screen, you will need to fill out all the parameters and selection boxes appropriately before saving your new appointment for your selected customer.
			- You can cancel this at anytime, but you will be asked if you are sure you want to leave the screen if you had already filled in anything.
		- In the Appointments screen, you can view a table of appointments, along with buttons to update an appointment or delete an appointment.
		- There are radio buttons to let you view upcoming appointments a week ahead or a month ahead.
			- The Update Appointment screen is the same as the Add Appointment screen, with the addition of already filled in parameters based on the appointment you selected from the table.
			- Be sure an appointment time is not going to overlap with another appointment for the same customer or you will get an alert.
			- You can cancel this at anytime, but you will be asked if you are sure you want to leave the screen if you had changed anything.
		- The first report you can view will list all the appointments in the database, sorted by first their type and then by the month of their start time.
		- The second report you can view will list all current contacts and their associated appointments, each sorted by start time.
		- The third report you can view will list all current customers and their associated appointments, each sorted by the year of their start time.


Additional Report
 - The third report demonstrated in the application is a list of appointments that are grouped by their respective customer and are sorted by year for each customer.

Database Connection
 - For connection to the database, the application uses mysql-connector-java-8.0.25.