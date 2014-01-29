CrestronSQLite - Android Application

This application allows a user to enter information about a System into a SQLite Database.
Once the application is launched the user can input data about their system into the appropriate text field. Once the data is correctly entered, the user can press the Save button to store the System data into the SQLite Database. If all elements of the system are valid the System name is displayed on the bottom of the screen in a list of all Systems in the database. If the user selects one of the Systems in the list, its information and attributes are displayed into the text fields. The System can then be updated by changing any of the desired text fields. The user can then select the Clear button to clear the text entry fields to enter a new System. Each system is displayed in order on the bottom of the application. The data is stored in the app even if the application is closed. If incorrect data is entered into any of the text fields, a dialog box will appear telling the user what their error is. It is important to make sure that all entries are correct and follow the following form:

System Name: String (unique)

Host Name: String

IPID: hex value in the range of 0x03 - 0xff (03 - 255)

CIP Port: Integer in the range of 1 - 65535

Web Port: Integer

Use SSL: Boolean

User Name: String

Password: String


