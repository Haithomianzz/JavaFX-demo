package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
    private int day;
    private int month;
    private int year;

    public void setDay(int day) {
        this.day = day;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setYear(int year) {
        this.year = year;
    }

    private LocalDate date;

     // Constructors
     public Date() {
         this.date = LocalDate.now();
     }
     public Date(LocalDate date) {
         this.date = date;
     }
     public Date(String dateString) {
         this.setDate(dateString);
     }

     // Get date
     public LocalDate getDate() {
         return date;
     }
     // Set date from a string
     public boolean setDate(String dateString) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         try {
             this.date = LocalDate.parse(dateString, formatter);
             return true;
         } catch (DateTimeParseException e) {
             System.out.println("Invalid date format. Please use yyyy-MM-dd");
             return false;
         }
     }

     // Check if the date is valid
     public boolean isValidDate() {
         return this.date != null;
     }

     @Override
     public String toString() {
         return date != null ? date.toString() : "Invalid Date";
     }
}
