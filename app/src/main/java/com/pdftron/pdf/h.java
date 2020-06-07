/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class h
/*    */   implements __Delete
/*    */ {
/*    */   protected void finalize() throws Throwable {
/* 19 */     if (this.c != 0L) {
/*    */ 
/*    */ 
/*    */       
/* 23 */       synchronized (b) {
/*    */         
/* 25 */         Object object = b.get(this.a);
/*    */       } 
/* 27 */       if (SYNTHETIC_LOCAL_VARIABLE_1 != null)
/*    */       {
/* 29 */         synchronized (SYNTHETIC_LOCAL_VARIABLE_1) {
/*    */           
/* 31 */           ((LinkedList<h>)SYNTHETIC_LOCAL_VARIABLE_1).add(this);
/*    */           return;
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void clearList() throws PDFNetException {
/* 46 */     this.a = Thread.currentThread();
/* 47 */     synchronized (b) {
/*    */       
/* 49 */       Object object = b.get(this.a);
/*    */     } 
/* 51 */     if (SYNTHETIC_LOCAL_VARIABLE_1 != null) {
/*    */       
/* 53 */       synchronized (SYNTHETIC_LOCAL_VARIABLE_1)
/*    */       {
/* 55 */         LinkedList<__Delete> linkedList = (LinkedList)SYNTHETIC_LOCAL_VARIABLE_1;
/* 56 */         while (!linkedList.isEmpty())
/*    */         {
/* 58 */           ((__Delete)linkedList.removeFirst()).destroy();
/*    */         }
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 64 */       null = new LinkedList();
/* 65 */       synchronized (b) {
/*    */         
/* 67 */         b.put(this.a, null);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   static HashMap b = new HashMap<>();
/*    */   private Thread a;
/*    */   long c;
/*    */   
/*    */   public abstract void destroy() throws PDFNetException;
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\h.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */