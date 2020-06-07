/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import android.annotation.TargetApi;
/*      */ import android.content.Context;
/*      */ import android.os.AsyncTask;
/*      */ import android.os.Build;
/*      */ import android.os.Bundle;
/*      */ import android.os.CancellationSignal;
/*      */ import android.os.Handler;
/*      */ import android.os.Message;
/*      */ import android.os.ParcelFileDescriptor;
/*      */ import android.print.PageRange;
/*      */ import android.print.PrintAttributes;
/*      */ import android.print.PrintDocumentAdapter;
/*      */ import android.print.PrintDocumentInfo;
/*      */ import android.print.PrintJob;
/*      */ import android.print.PrintJobInfo;
/*      */ import android.print.PrintManager;
/*      */ import android.util.Log;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.ocg.Context;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import com.pdftron.sdf.ObjSet;
/*      */ import java.io.Closeable;
/*      */ import java.io.FileOutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Print
/*      */ {
/*      */   public static final int PRINT_CONTENT_DOCUMENT_BIT = 1;
/*      */   public static final int PRINT_CONTENT_ANNOTATION_BIT = 2;
/*      */   public static final int PRINT_CONTENT_SUMMARY_BIT = 4;
/*   67 */   static final ArrayList<PrintJob> a = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   private static ArrayList<PrintCallback> b = new ArrayList<>();
/*      */   
/*   94 */   private static final ReentrantLock c = new ReentrantLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addPrintListener(PrintCallback paramPrintCallback) {
/*  106 */     c.lock();
/*  107 */     if (!b.contains(paramPrintCallback)) {
/*  108 */       b.add(paramPrintCallback);
/*      */     }
/*      */ 
/*      */     
/*  112 */     c.unlock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void removePrintListener(PrintCallback paramPrintCallback) {
/*  122 */     c.lock();
/*  123 */     if (b.contains(paramPrintCallback)) {
/*  124 */       b.remove(paramPrintCallback);
/*      */     }
/*  126 */     c.unlock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearPrintListeners() {
/*  133 */     c.lock();
/*  134 */     b.clear();
/*  135 */     c.unlock();
/*      */   }
/*      */   
/*      */   static class a extends Handler {
/*      */     private a() {}
/*      */     
/*      */     public final void handleMessage(Message param1Message) {
/*  142 */       ArrayList<PrintJob> arrayList = new ArrayList();
/*  143 */       for (Iterator<PrintJob> iterator = Print.a.iterator(); iterator.hasNext(); ) {
/*      */         Iterator<PrintCallback> iterator1; PrintJob printJob;
/*  145 */         if ((printJob = iterator.next()).isCompleted()) {
/*  146 */           PrintJobInfo printJobInfo = printJob.getInfo();
/*  147 */           int j = param1Message.what;
/*  148 */           int i = printJobInfo.getCopies();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  153 */           arrayList.add(printJob);
/*  154 */           Print.a().lock();
/*  155 */           for (iterator1 = Print.b().iterator(); iterator1.hasNext();) {
/*  156 */             (printCallback = iterator1.next()).onPrintCompleted(j, i);
/*      */           }
/*  158 */           Print.a().unlock(); continue;
/*  159 */         }  if (iterator1.isFailed()) {
/*      */           
/*  161 */           arrayList.add(iterator1);
/*  162 */           Print.a().lock();
/*  163 */           for (Iterator<PrintCallback> iterator2 = Print.b().iterator(); iterator2.hasNext();) {
/*  164 */             (printCallback = iterator2.next()).onPrintFailed();
/*      */           }
/*  166 */           Print.a().unlock(); continue;
/*  167 */         }  if (iterator1.isCancelled()) {
/*      */           
/*  169 */           arrayList.add(iterator1);
/*  170 */           Print.a().lock();
/*  171 */           for (Iterator<PrintCallback> iterator2 = Print.b().iterator(); iterator2.hasNext();) {
/*  172 */             (printCallback = iterator2.next()).onPrintCancelled();
/*      */           }
/*  174 */           Print.a().unlock();
/*      */         } 
/*      */       } 
/*  177 */       Print.a.removeAll(arrayList);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   public static void startPrintJob(Context paramContext, String paramString, PDFDoc paramPDFDoc, Integer paramInteger, Boolean paramBoolean) throws PDFNetException {
/*  216 */     startPrintJob(paramContext, paramString, paramPDFDoc, paramInteger, paramBoolean, (Context)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TargetApi(19)
/*      */   public static void startPrintJob(Context paramContext, String paramString, PDFDoc paramPDFDoc, Integer paramInteger, Boolean paramBoolean, Context paramContext1) throws PDFNetException {
/*  259 */     if (Build.VERSION.SDK_INT < 19)
/*      */     {
/*  261 */       throw new PDFNetException("false", 0L, "Print.java", "startPrintJob", "Print is only available for devices running Android 4.4 (KitKat) or higher.");
/*      */     }
/*      */     
/*  264 */     a a = new a((byte)0);
/*      */     
/*      */     Obj obj;
/*      */     
/*  268 */     if ((obj = paramPDFDoc.getRoot().findObj("Collection")) != null)
/*      */     {
/*  270 */       throw new PDFNetException("false", 0L, "Print.java", "startPrintJob", "The document is a portfolio/collection and can't be printed.");
/*      */     }
/*      */     
/*  273 */     int i = paramInteger.intValue();
/*  274 */     boolean bool = paramBoolean.booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     PrintManager printManager = (PrintManager)paramContext.getSystemService("print");
/*      */     
/*  292 */     PrintDocumentAdapter printDocumentAdapter = new PrintDocumentAdapter(paramPDFDoc, i, bool, paramContext1, a)
/*      */       {
/*      */         private PrintDocumentInfo e;
/*      */ 
/*      */         
/*      */         private PDFDoc f;
/*      */         
/*  299 */         private final ReentrantLock g = new ReentrantLock();
/*  300 */         private final ReentrantLock h = new ReentrantLock();
/*      */         
/*      */         private int i;
/*      */         private int j;
/*      */         private boolean k;
/*      */         private PageRange[] l;
/*      */         
/*      */         public final void onStart() {
/*  308 */           super.onStart();
/*  309 */           this.e = null;
/*  310 */           this.f = null;
/*  311 */           this.i = 0;
/*  312 */           this.j = 0;
/*  313 */           this.k = false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final void onLayout(PrintAttributes param1PrintAttributes1, PrintAttributes param1PrintAttributes2, CancellationSignal param1CancellationSignal, LayoutResultCallback param1LayoutResultCallback, Bundle param1Bundle) {
/*  326 */           if (param1PrintAttributes2.equals(param1PrintAttributes1) && this.e != null) {
/*  327 */             param1LayoutResultCallback.onLayoutFinished(this.e, true);
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */ 
/*      */           
/*  335 */           (new AsyncTask<Void, Void, Integer>(this, param1CancellationSignal, param1LayoutResultCallback, param1PrintAttributes2) {
/*  336 */               CancelFlag a = null;
/*      */ 
/*      */ 
/*      */               
/*      */               protected final void onPreExecute() {
/*  341 */                 Print.null.a(this.b);
/*      */                 
/*  343 */                 this.c.setOnCancelListener(new CancellationSignal.OnCancelListener(this)
/*      */                     {
/*      */                       public final void onCancel()
/*      */                       {
/*  347 */                         this.a.cancel(true);
/*  348 */                         Print.null.b(this.a.b).lock();
/*  349 */                         if (this.a.a != null) {
/*  350 */                           this.a.a.set(true);
/*      */                         }
/*  352 */                         Print.null.b(this.a.b).unlock();
/*      */                       }
/*      */                     });
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               private Integer a() {
/*  361 */                 if (isCancelled()) {
/*  362 */                   return Integer.valueOf(-1);
/*      */                 }
/*      */ 
/*      */                 
/*  366 */                 boolean bool1 = false;
/*  367 */                 boolean bool2 = false;
/*  368 */                 boolean bool3 = false;
/*  369 */                 boolean bool4 = false;
/*  370 */                 PageSet pageSet = null;
/*      */ 
/*      */                 
/*  373 */                 try { this.b.a.lockRead();
/*  374 */                   int i = this.b.a.getPageCount(); }
/*  375 */                 catch (PDFNetException pDFNetException)
/*  376 */                 { this.d.onLayoutFailed("The input document is locked somewhere.");
/*  377 */                   null = Integer.valueOf(0);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  382 */                   return null; }
/*      */                 finally { try { this.b.a.unlockRead(); }
/*      */                   catch (PDFNetException pDFNetException) {} }
/*  385 */                  try { pageSet = new PageSet(1, null);
/*      */                   
/*      */                   ObjSet objSet;
/*      */                   
/*      */                   Obj obj;
/*  390 */                   (obj = (objSet = new ObjSet()).createDict()).putBool("PORTRAIT", this.e.getMediaSize().isPortrait());
/*  391 */                   obj.putNumber("PAPER_SIZE_HEIGHT", this.e.getMediaSize().getHeightMils());
/*  392 */                   obj.putNumber("PAPER_SIZE_WIDTH", this.e.getMediaSize().getWidthMils());
/*  393 */                   if ((this.b.b & 0x1) == 0 && (this.b.b & 0x4) != 0) {
/*  394 */                     obj.putNumber("PRINT_CONTENT", 3.0D);
/*  395 */                   } else if (this.b.b == 1) {
/*  396 */                     obj.putNumber("PRINT_CONTENT", 0.0D);
/*  397 */                   } else if ((this.b.b & 0x4) == 0) {
/*  398 */                     obj.putNumber("PRINT_CONTENT", 1.0D);
/*      */                   } else {
/*  400 */                     obj.putNumber("PRINT_CONTENT", 2.0D);
/*      */                   } 
/*  402 */                   obj.putBool("IS_RTL", this.b.c);
/*      */                   
/*  404 */                   if (isCancelled())
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  483 */                     return integer; }  while (!Print.null.c(this.b).tryLock()) { Thread.sleep(50L); if (isCancelled()) return integer;  }  bool3 = true; if (Print.null.d(this.b) != null) { Print.null.d(this.b).close(); Print.null.a(this.b, (PDFDoc)null); }  Print.null.a(this.b, new PDFDoc()); Print.null.d(this.b).lock(); bool1 = true; this.b.a.lockRead(); bool2 = true; Print.null.b(this.b).lock(); bool4 = true; this.a = new CancelFlag(); long l = (this.b.d != null) ? this.b.d.__GetHandle() : 0L; Print.FormatWithCancel(this.b.a.__GetHandle(), pageSet.__GetHandle(), Print.null.d(this.b).__GetHandle(), obj.__GetHandle(), this.a.__GetHandle(), l); } catch (Exception exception) { this.d.onLayoutFailed("An error occurred while trying to print the document."); Log.e("Print", "onLayoutFailed: " + exception.getMessage()); return Integer.valueOf(0); } finally { if (this.a != null) { this.a.destroy(); this.a = null; }  if (bool4)
/*      */                     try { Print.null.b(this.b).unlock(); } catch (Exception exception) {}  if (bool3)
/*      */                     try { Print.null.c(this.b).unlock(); } catch (Exception exception) {}  if (Print.null.d(this.b) != null && bool1)
/*      */                     try { Print.null.d(this.b).unlock(); } catch (Exception exception) { Print.null.a(this.b, (PDFDoc)null); this.d.onLayoutFailed("An error occurred while trying to print the document: cannot write on the output PDF file"); Log.e("Print", "onLayoutFailed: " + exception.getMessage()); return Integer.valueOf(0); }   if (bool2)
/*      */                     try { this.b.a.unlockRead(); } catch (Exception exception) { this.d.onLayoutFailed("An error occurred while trying to print the document: cannot access the input PDF file"); Log.e("Print", "onLayoutFailed: " + exception.getMessage()); return Integer.valueOf(0); }   if (pageSet != null)
/*      */                     try { pageSet.destroy(); }
/*      */                     catch (Exception exception) {}  }
/*  490 */                  if (isCancelled()) {
/*  491 */                   return Integer.valueOf(-1);
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/*  496 */                 return Integer.valueOf(1);
/*      */               }
/*  547 */             }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final void onWrite(PageRange[] param1ArrayOfPageRange, ParcelFileDescriptor param1ParcelFileDescriptor, CancellationSignal param1CancellationSignal, WriteResultCallback param1WriteResultCallback) {
/*  555 */           (new AsyncTask<Void, Void, Void>(this, param1CancellationSignal, param1ParcelFileDescriptor, param1ArrayOfPageRange, param1WriteResultCallback)
/*      */             {
/*      */               
/*      */               protected final void onPreExecute()
/*      */               {
/*  560 */                 Print.null.i(this.e);
/*      */                 
/*  562 */                 this.a.setOnCancelListener(new CancellationSignal.OnCancelListener(this)
/*      */                     {
/*      */                       public final void onCancel() {
/*  565 */                         this.a.cancel(true);
/*      */                       }
/*      */                     });
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               private Void a() {
/*  574 */                 if (isCancelled() || Print.null.d(this.e) == null) {
/*  575 */                   return null;
/*      */                 }
/*      */ 
/*      */                 
/*  579 */                 FileOutputStream fileOutputStream = null;
/*  580 */                 boolean bool = false;
/*      */                 
/*  582 */                 try { fileOutputStream = new FileOutputStream(this.b.getFileDescriptor());
/*      */ 
/*      */ 
/*      */                   
/*  586 */                   while (!Print.null.c(this.e).tryLock())
/*  587 */                   { Thread.sleep(50L);
/*  588 */                     if (isCancelled())
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  652 */                       return null; }  }  if (Print.null.d(this.e) == null) return null;  Print.null.d(this.e).lockRead(); bool = true; if (Print.a(this.c, Print.null.d(this.e).getPageCount())) { if (isCancelled()) return null;  Print.null.d(this.e).save(fileOutputStream, 32L, (ProgressMonitor)null); } else { PDFDoc pDFDoc = null; boolean bool1 = false; try { (pDFDoc = new PDFDoc()).lock(); bool1 = true; int i = Print.null.d(this.e).getPageCount(); for (byte b = 0; b < i; b++) { if (Print.b(this.c, b)) pDFDoc.pagePushBack(Print.null.d(this.e).getPage(b + 1));  if (isCancelled()) { try { pDFDoc.unlock(); } catch (Exception exception) {} return null; }  }  pDFDoc.save(fileOutputStream, 32L, (ProgressMonitor)null); try { pDFDoc.unlock(); } catch (Exception exception) {} } catch (Exception exception) { this.d.onWriteFailed("An error occurred while trying to print the document: on write failed"); Log.e("Print", "onWriteFailed: " + exception.getMessage()); return null; } finally { if (pDFDoc != null && bool1) { try { pDFDoc.unlock(); } catch (Exception exception) {} Print.a(pDFDoc); }  }  }  if (isCancelled()) return null;  this.d.onWriteFinished(this.c); } catch (Exception exception) { this.d.onWriteFailed("An error occurred while trying to print the document: on write failed"); Log.e("Print", "onWriteFailed: " + exception.getMessage()); return null; }
/*      */                 finally
/*      */                 { Print.null.c(this.e).unlock(); Print.a(fileOutputStream); if (Print.null.d(this.e) != null && bool)
/*      */                     try {
/*      */                       Print.null.d(this.e).unlockRead();
/*      */                     } catch (Exception exception) {}  }
/*  658 */                  return null;
/*      */               }
/*  686 */             }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
/*      */         }
/*      */ 
/*      */         
/*      */         public final void onFinish() {
/*  691 */           super.onFinish();
/*  692 */           if (this.i == 0 && this.j == 0 && this.f != null) {
/*  693 */             Print.a(this.f);
/*  694 */             this.f = null;
/*      */           } else {
/*  696 */             this.k = true;
/*      */           } 
/*      */           
/*  699 */           int i = 0;
/*      */           
/*  701 */           try { this.a.lockRead();
/*  702 */             i = this.a.getPageCount(); }
/*  703 */           catch (PDFNetException pDFNetException)
/*      */           
/*      */           { try {
/*  706 */               this.a.unlockRead();
/*  707 */             } catch (PDFNetException pDFNetException1) {} } finally { try { this.a.unlockRead(); } catch (PDFNetException pDFNetException) {} }
/*      */ 
/*      */ 
/*      */           
/*  711 */           byte b = 0;
/*  712 */           if (this.l != null && this.l.length > 0) {
/*  713 */             for (byte b1 = 0; b1 < i; b1++) {
/*  714 */               if (Print.b(this.l, b1)) {
/*  715 */                 b++;
/*      */               }
/*      */             } 
/*      */           }
/*      */           
/*  720 */           this.m.sendEmptyMessageDelayed(b, 200L);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */     
/*  726 */     PrintJob printJob = printManager.print(paramString, printDocumentAdapter, null);
/*  727 */     a.add(printJob);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void startPrintJob(Context paramContext, String paramString1, PDFDoc paramPDFDoc, String paramString2, Integer paramInteger, Boolean paramBoolean) throws PDFNetException {
/*  759 */     if (!paramPDFDoc.initSecurityHandler() && 
/*  760 */       !paramPDFDoc.initStdSecurityHandler(paramString2)) {
/*  761 */       throw new PDFNetException("false", 0L, "Print.java", "startPrintJob", "The document is encrypted and the password supplied is wrong.");
/*      */     }
/*      */     
/*  764 */     startPrintJob(paramContext, paramString1, paramPDFDoc, paramInteger, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean c(PageRange[] paramArrayOfPageRange, int paramInt) {
/*  780 */     int i = paramArrayOfPageRange.length;
/*  781 */     for (byte b = 0; b < i; b++) {
/*  782 */       if (paramArrayOfPageRange[b].getStart() <= paramInt && paramArrayOfPageRange[b].getEnd() >= paramInt) {
/*  783 */         return true;
/*      */       }
/*      */     } 
/*  786 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PDFDoc exportAnnotations(PDFDoc paramPDFDoc, boolean paramBoolean) throws PDFNetException {
/*  801 */     Log.d("Print", "start exportAnnotations");
/*  802 */     PDFDoc pDFDoc = null;
/*  803 */     PageSet pageSet = null;
/*  804 */     boolean bool1 = false;
/*  805 */     boolean bool2 = false;
/*      */     
/*      */     try {
/*  808 */       pageSet = new PageSet(1, paramPDFDoc.getPageCount());
/*      */       ObjSet objSet;
/*      */       Obj obj;
/*  811 */       (obj = (objSet = new ObjSet()).createDict()).putNumber("PRINT_CONTENT", 3.0D);
/*  812 */       obj.putBool("IS_RTL", paramBoolean);
/*      */ 
/*      */ 
/*      */       
/*  816 */       (pDFDoc = new PDFDoc()).lock();
/*      */       
/*  818 */       bool1 = true;
/*      */       
/*  820 */       paramPDFDoc.lockRead();
/*      */       
/*  822 */       bool2 = true;
/*      */       
/*  824 */       Format(paramPDFDoc.__GetHandle(), pageSet.__GetHandle(), pDFDoc.__GetHandle(), obj.__GetHandle());
/*      */     }
/*  826 */     catch (PDFNetException pDFNetException) {
/*      */       
/*  828 */       throw new PDFNetException("false", 0L, "Print.java", "exportAnnotations", pDFNetException.getMessage());
/*      */     } finally {
/*  830 */       if (bool2) {
/*      */         try {
/*  832 */           paramPDFDoc.unlockRead();
/*  833 */         } catch (PDFNetException pDFNetException) {}
/*      */       }
/*      */       
/*  836 */       if (pDFDoc != null && bool1) {
/*      */         try {
/*  838 */           pDFDoc.unlock();
/*  839 */         } catch (PDFNetException pDFNetException) {}
/*      */       }
/*      */       
/*  842 */       if (pageSet != null) {
/*      */         try {
/*  844 */           pageSet.destroy();
/*  845 */         } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  852 */     return pDFDoc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void Format(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static native void FormatWithCancel(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Compat
/*      */   {
/*      */     public static void Format(long param1Long1, long param1Long2, long param1Long3, long param1Long4) throws PDFNetException {
/*  919 */       Print.Format(param1Long1, param1Long2, param1Long3, param1Long4);
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface PrintCallback {
/*      */     void onPrintCompleted(int param1Int1, int param1Int2);
/*      */     
/*      */     void onPrintFailed();
/*      */     
/*      */     void onPrintCancelled();
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Print.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */