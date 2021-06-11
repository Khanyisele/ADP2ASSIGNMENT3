package com.mycompany.assignment;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Khanyisele Peyi 218329091
 */
public class ReadFile {


 private ObjectInputStream input;

 ArrayList<Customer> CustArray = new ArrayList<>();
 ArrayList<Supplier> SuppArray = new ArrayList<>();
 int CustomersCanRent;
 int CustomersCannotRent;
 int age[];


 public void openFile() {
 try {
 input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
 System.out.println("open for reading");
 } catch (IOException ioe) {
 System.out.println("error reading file" + ioe.getMessage());
 }
 }

 
 public void readFile() {

 try {


 while (true) {
 temp = (Stakeholder) input.readObject();

 if (temp instanceof Customer) {
 CustArray.add((Customer) temp); 

 } else if (temp instanceof Supplier) {
 SuppArray.add((Supplier) temp); 

 }

 }
 } catch (EOFException ex) {
 System.out.println("EOF reached"+ ex.getMessage());
 } catch (ClassNotFoundException cnfe) {
 System.out.println("class not found"+ cnfe.getMessage());
 } catch (FileNotFoundException fnfe) {
 System.out.println("file not found"+ fnfe.getMessage());
 } catch (IOException ioe) {
 System.out.println("IO exception"+ ioe.getMessage());
 }

 }

 //prints all customers to console
 public void printCustomer() {
 System.out.println("Customers");
 for (int i = 0; i < CustArray.size(); i++) {
 System.out.println(CustArray.get(i));

 }
 }


 public void printSupplier() {
 System.out.println("Suppliers");
 for (int i = 0; i < SuppArray.size(); i++) {
 System.out.println(SuppArray.get(i));
 }
 }

 
 public void closeFile() {
 try {
 input.close();
 } catch (IOException ioe) {
 System.out.println("error closing ser file" + ioe.getMessage());
 }
 }

 //age calculation
 public void age() {
 age = new int[CustArray.size()];
 for (int i = 0; i < CustArray.size(); i++) {
 LocalDate lDate = LocalDate.parse(CustArray.get(i).getDateOfBirth());
 int year = lDate.getYear();
 age[i] = 2021 - year;
 }
 }


 public void canRent() {
 for (int i = 0; i < CustArray.size(); i++) {
 if (CustArray.get(i).getCanRent() == true) {
 CustomersCanRent++;
 } else if (CustArray.get(i).getCanRent() == false) {
 CustomersCannotRent++;
 }
 }
 }

 
 public void sort() {
 CustArray.sort(Comparator.comparing(Stakeholder::getStHolderId));
 }
 
 
 public void formatDOB() {
 try{
 DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 

 for (int i = 0; i < CustArray.size(); i++) {
 String DOB = CustArray.get(i).getDateOfBirth();
 Date formattedDate = format1.parse(DOB);
 DateFormat format2 = new SimpleDateFormat("dd MMM yyyy"); 
 CustArray.get(i).setDateOfBirth(format2.format(formattedDate));
 }
 }
 catch(ParseException pe){
 System.out.println("parse exception" + pe.getMessage());
 }
 }


 public void sortSupplier() {
 SuppArray.sort(Comparator.comparing(Supplier::getName));
 }


 public void CreateCustomerFile(){
 try {
 FileWriter fw = new FileWriter("Customer.txt"); 
 BufferedWriter bw = new BufferedWriter(fw); 
 bw.write("==================== CUSTOMERS ====================");
 bw.newLine();
 bw.write("ID\tName\tSurname\t\tDate of birth\tAge");
 bw.newLine();
 bw.write("===================================================");
 bw.newLine();
 for (int i = 0; i < CustArray.size(); i++) {

 if (CustArray.get(i).getSurName().length() < 8) {
 bw.write(CustArray.get(i).getStHolderId() + "\t" + CustArray.get(i).getFirstName() + "\t" + CustArray.get(i).getSurName() + "\t\t" + CustArray.get(i).getDateOfBirth() + "\t" + age[i] + "\t");
 bw.newLine();
 } else {
 bw.write(CustArray.get(i).getStHolderId() + "\t" + CustArray.get(i).getFirstName() + "\t" + CustArray.get(i).getSurName() + "\t" + CustArray.get(i).getDateOfBirth() + "\t" + age[i] + "\t");
 bw.newLine();
 }
 }

 bw.newLine();
 bw.write("Number of customers that can rent:");
 bw.write(String.valueOf("\t" + CustomersCanRent));
 bw.newLine();
 bw.write("Number of customers who cannot rent: ");
 bw.write(String.valueOf("\t" + CustomersCannotRent));
 } catch (EOFException ex) {
 System.out.println("EOF reached" + ex.getMessage());
 } catch (FileNotFoundException fnfe) {
 System.out.println("file not found" + fnfe.getMessage());
 } catch (IOException ioe) {
 System.out.println("IO exception" + ioe.getMessage());
 }
 }

 public void CreateSupplierFile() {
 try {
 
 FileWriter fw = new FileWriter("Supplier.txt");
 BufferedWriter bw = new BufferedWriter(fw);
 bw.write("=========================== Supplier ============================");
 bw.newLine();
 bw.write("ID\tName\t\t\tProd Type\tDescription");
 bw.newLine();
 bw.write("=================================================================");
 for (int i = 0; i < SuppArray.size(); i++) {
 if (SuppArray.get(i).getName().length() > 12) {
 bw.newLine();
 bw.write(SuppArray.get(i).getStHolderId() + "\t" + SuppArray.get(i).getName() + "\t" + SuppArray.get(i).getProductType() + "\t\t" + SuppArray.get(i).getProductDescription() + "\t");

 } else {
 bw.newLine();
 bw.write(SuppArray.get(i).getStHolderId() + "\t" + SuppArray.get(i).getName() + "\t\t" + SuppArray.get(i).getProductType() + "\t\t" + SuppArray.get(i).getProductDescription() + "\t");

 }
 }

 bw.close();
 } catch (EOFException ex) {
 System.out.println("EOF reached" + ex.getMessage());
 } catch (FileNotFoundException fnfe) {
 System.out.println("file not found" + fnfe.getMessage());
 } catch (IOException ioe) {
 System.out.println("IO exception" + ioe.getMessage());
 }

 }

 public static void main(String[] args) {
 //calling all functions
 ReadFile obj1 = new ReadFile();

 obj1.openFile();
 obj1.readFile();
 obj1.sort();
 obj1.age();
 obj1.formatDOB();
 obj1.sortSupplier();

 obj1.printCustomer();
 obj1.printSupplier();
 obj1.canRent();

 obj1.closeFile();
 obj1.CreateCustomerFile();
 obj1.CreateSupplierFile();

 }
}
