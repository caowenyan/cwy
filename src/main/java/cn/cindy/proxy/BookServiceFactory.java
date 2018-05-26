package cn.cindy.proxy;

public class BookServiceFactory {
 private static BookServiceBean service = new BookServiceBean();  
 private BookServiceFactory() {  
 }  
 public static BookServiceBean getInstance() {  
  return service;  
 }  
}  