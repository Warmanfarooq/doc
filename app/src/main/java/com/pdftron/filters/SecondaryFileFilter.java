/*      */ package com.pdftron.filters;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.net.Uri;
/*      */ import android.os.ParcelFileDescriptor;
/*      */ import android.util.Log;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.FileChannel;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SecondaryFileFilter
/*      */   extends CustomFilter
/*      */ {
/*   20 */   private static final String a = SecondaryFileFilter.class.getName();
/*      */   
/*      */   private Context b;
/*      */   
/*      */   private Uri c;
/*      */   
/*      */   private ParcelFileDescriptor d;
/*      */   private ParcelFileDescriptor e;
/*      */   private ParcelFileDescriptor.AutoCloseInputStream f;
/*      */   private ParcelFileDescriptor.AutoCloseOutputStream g;
/*      */   private long h;
/*      */   private boolean i = false;
/*      */   private int j;
/*      */   
/*      */   public SecondaryFileFilter(Context paramContext, Uri paramUri) throws PDFNetException, FileNotFoundException {
/*   35 */     this(paramContext, paramUri, 0);
/*      */   }
/*      */   
/*      */   public SecondaryFileFilter(Context paramContext, Uri paramUri, int paramInt) throws PDFNetException, FileNotFoundException {
/*   39 */     super(paramInt, paramUri);
/*      */     
/*   41 */     this.j = FileDescriptorFilterManager.a.a().b();
/*      */ 
/*      */ 
/*      */     
/*   45 */     this.b = paramContext;
/*   46 */     this.c = paramUri;
/*   47 */     this.d = paramContext.getContentResolver().openFileDescriptor(paramUri, "r");
/*   48 */     this.f = new ParcelFileDescriptor.AutoCloseInputStream(this.d);
/*   49 */     if (paramInt != 0) {
/*   50 */       this.e = paramContext.getContentResolver().openFileDescriptor(paramUri, "rw");
/*   51 */       this.g = new ParcelFileDescriptor.AutoCloseOutputStream(this.e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public SecondaryFileFilter(int paramInt, SecondaryFileFilter paramSecondaryFileFilter) throws PDFNetException, IOException {
/*   56 */     super(paramInt, paramSecondaryFileFilter.c);
/*   57 */     this.j = FileDescriptorFilterManager.a.a().b();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   62 */     this.attached = paramSecondaryFileFilter;
/*      */     
/*   64 */     a(paramSecondaryFileFilter);
/*      */   }
/*      */   
/*      */   protected SecondaryFileFilter(long paramLong, SecondaryFileFilter paramSecondaryFileFilter) throws IOException {
/*   68 */     super(paramLong, paramSecondaryFileFilter);
/*   69 */     this.j = FileDescriptorFilterManager.a.a().b();
/*      */ 
/*      */ 
/*      */     
/*   73 */     a(paramSecondaryFileFilter);
/*      */   }
/*      */   
/*      */   private void a(SecondaryFileFilter paramSecondaryFileFilter) throws IOException {
/*   77 */     this.b = paramSecondaryFileFilter.b;
/*   78 */     this.c = paramSecondaryFileFilter.c;
/*   79 */     this.d = paramSecondaryFileFilter.d;
/*   80 */     this.f = paramSecondaryFileFilter.f;
/*   81 */     if (paramSecondaryFileFilter.e != null) {
/*   82 */       this.e = paramSecondaryFileFilter.e;
/*   83 */       this.g = paramSecondaryFileFilter.g;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static SecondaryFileFilter __Create(long paramLong, SecondaryFileFilter paramSecondaryFileFilter) throws IOException {
/*   88 */     return new SecondaryFileFilter(paramLong, paramSecondaryFileFilter);
/*      */   }
/*      */ 
/*      */   
/*      */   public long size() throws PDFNetException {
/*      */     try {
/*   94 */       return this.f.getChannel().size();
/*   95 */     } catch (Exception exception2) {
/*   96 */       Exception exception1; (exception1 = null).printStackTrace();
/*   97 */       return super.size();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long onRead(byte[] paramArrayOfbyte, Object paramObject) {
/*      */     try {
/*      */       FileChannel fileChannel;
/* 1103 */       if (!(fileChannel = ((SecondaryFileFilter)(paramObject = this)).f.getChannel()).isOpen()) {
/* 1104 */         ((SecondaryFileFilter)paramObject).d = ((SecondaryFileFilter)paramObject).b.getContentResolver().openFileDescriptor(((SecondaryFileFilter)paramObject).c, "r");
/* 1105 */         ((SecondaryFileFilter)paramObject).f = new ParcelFileDescriptor.AutoCloseInputStream(((SecondaryFileFilter)paramObject).d);
/*      */       } 
/*      */       (paramObject = this.f.getChannel()).position(this.h);
/*      */       ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/*      */       int i = paramObject.read(byteBuffer);
/*      */       this.h = paramObject.position();
/*      */       return i;
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "onRead exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */       return -1L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public long onSeek(long paramLong, int paramInt, Object paramObject) {
/*      */     byte b = 0;
/*      */     try {
/*      */       if (paramInt == 0) {
/*      */         this.h = (paramLong >= 0L) ? paramLong : 0L;
/*      */       } else if (paramInt == 1) {
/*      */         this.h = paramLong + this.h;
/*      */       } else if (paramInt == 2) {
/*      */         this.h = size() + paramLong;
/*      */       } 
/*      */     } catch (Exception exception2) {
/*      */       Exception exception1;
/*      */       (exception1 = null).printStackTrace();
/*      */       Log.e(a, "onSeek exception for filter #: " + getSequenceNumber());
/*      */       b = -1;
/*      */     } 
/*      */     return b;
/*      */   }
/*      */   
/*      */   public long onTell(Object paramObject) {
/*      */     return this.h;
/*      */   }
/*      */   
/*      */   public long onFlush(Object paramObject) {
/*      */     if (this.g == null)
/*      */       return 0L; 
/*      */     paramObject = this.g.getChannel();
/*      */     try {
/*      */       paramObject.truncate(this.h);
/*      */       return paramObject.size();
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "onFlush exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */       return -1L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public long onWrite(byte[] paramArrayOfbyte, Object paramObject) {
/*      */     if (this.g == null)
/*      */       return 0L; 
/*      */     try {
/*      */       ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/*      */       (paramObject = this.g.getChannel()).position(this.h);
/*      */       int i = paramObject.write(byteBuffer);
/*      */       this.h = paramObject.position();
/*      */       return i;
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "onWrite exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */       return -1L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public long onTruncate(long paramLong, Object paramObject) {
/*      */     if (this.g == null)
/*      */       return 0L; 
/*      */     paramObject = this.g.getChannel();
/*      */     try {
/*      */       paramObject.truncate(paramLong);
/*      */       return paramObject.size();
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "onTruncate exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */       return -1L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public long onCreateInputIterator(Object paramObject) {
/*      */     try {
/*      */       return (paramObject = new SecondaryFileFilter(this.b, this.c, 0)).__GetHandle();
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "onCreateInputIterator exception for filter #: " + this.j);
/*      */       exception.printStackTrace();
/*      */       return 0L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SecondaryFileFilter createOutputIterator() {
/*      */     try {
/*      */       return new SecondaryFileFilter(this.b, this.c, 1);
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "createOutputIterator exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onDestroy(Object paramObject) {
/*      */     if (this.attached != null)
/*      */       return; 
/*      */     close();
/*      */     this.impl = 0L;
/*      */     this.callback_data = 0L;
/*      */   }
/*      */   
/*      */   public void close() {
/*      */     if (this.i)
/*      */       return; 
/*      */     cleanup();
/*      */     this.i = true;
/*      */   }
/*      */   
/*      */   public void cleanup() {
/*      */     try {
/*      */       if (this.g != null) {
/*      */         FileChannel fileChannel;
/*      */         (fileChannel = this.g.getChannel()).close();
/*      */         this.g.close();
/*      */       } 
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "close exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */     } 
/*      */     try {
/*      */       this.f.close();
/*      */       return;
/*      */     } catch (Exception exception) {
/*      */       Log.e(a, "close exception for filter #: " + getSequenceNumber());
/*      */       exception.printStackTrace();
/*      */       return;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void force(boolean paramBoolean) throws IOException {
/*      */     if (this.e != null && this.g != null) {
/*      */       this.g.getChannel().force(paramBoolean);
/*      */       this.e.getFileDescriptor().sync();
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getSequenceNumber() {
/*      */     return "[" + this.j + "]";
/*      */   }
/*      */   
/*      */   public int getRawSequenceNumber() {
/*      */     return this.j;
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\SecondaryFileFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */