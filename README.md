# Procedural JDBC
This is a Java library to *easily* execute mySQL server procedures with JDBC. All that is needed is the procedure name, and parameter list (if any).

Contains three main functionalities to execute procedures, get return values, and get result sets.

This implementation is intended for stored procedures.
The function for getting values from back end executes a procedure with INOUT variables--look at example mySQL code on the project to see the format.
