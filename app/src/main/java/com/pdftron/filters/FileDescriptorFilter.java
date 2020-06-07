/*     */ package com.pdftron.filters;
/*     */ 
/*     */ import android.os.ParcelFileDescriptor;
/*     */ import android.os.Process;
/*     */ import android.util.Log;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileDescriptorFilter
/*     */   extends CustomFilter
/*     */ {
/*     */   protected FileChannel mFileChannel;
/*     */   protected boolean mIsInputChannel;
/*     */   protected long mPosition;
/*     */   protected ParcelFileDescriptor mPfd;
/*     */   protected int mMySequenceNumber;
/*     */   protected FileDescriptorFilterManager mFilterManager;
/*     */   protected int mMode;
/*     */   protected FileLock mLock;
/*     */   public static final String TAG = "SaveFilter";
/*     */   
/*     */   public FileDescriptorFilter(int paramInt, ParcelFileDescriptor paramParcelFileDescriptor) throws PDFNetException {
/*  34 */     super(paramInt, paramParcelFileDescriptor);
/*     */     
/*  36 */     this.mPfd = paramParcelFileDescriptor;
/*  37 */     this.mMode = paramInt;
/*  38 */     FileInputStream fileInputStream = new FileInputStream(paramParcelFileDescriptor.getFileDescriptor());
/*  39 */     this.mFileChannel = fileInputStream.getChannel();
/*  40 */     this.mLock = null;
/*  41 */     this.mIsInputChannel = true;
/*     */     
/*  43 */     this.mFilterManager = new FileDescriptorFilterManager();
/*  44 */     this.mMySequenceNumber = this.mFilterManager.getNewSequenceNumber();
/*     */     
/*  46 */     Log.d("SaveFilter", this.mMySequenceNumber + ": create FileDescriptorFilter in Input mode, actual mode: " + a(paramInt));
/*  47 */     if (!this.mFileChannel.isOpen()) {
/*  48 */       Log.d("SaveFilter", this.mMySequenceNumber + ": create FileDescriptorFilter file channel closed!!!");
/*     */     }
/*     */     
/*  51 */     if (paramInt == 0) {
/*  52 */       this.mFilterManager.addReadFilter(this); return;
/*  53 */     }  if (paramInt == 1) {
/*  54 */       this.mFilterManager.addReadWriteFilter(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public FileDescriptorFilter(int paramInt, FileDescriptorFilter paramFileDescriptorFilter) throws PDFNetException {
/*  60 */     super(paramInt, paramFileDescriptorFilter.mPfd);
/*     */     
/*  62 */     this.mPfd = paramFileDescriptorFilter.mPfd;
/*  63 */     this.mMode = paramInt;
/*  64 */     this.mFileChannel = paramFileDescriptorFilter.mFileChannel;
/*  65 */     this.mIsInputChannel = paramFileDescriptorFilter.mIsInputChannel;
/*  66 */     this.mFilterManager = paramFileDescriptorFilter.mFilterManager;
/*  67 */     this.mMySequenceNumber = this.mFilterManager.getNewSequenceNumber();
/*  68 */     this.mLock = paramFileDescriptorFilter.mLock;
/*     */     
/*  70 */     if (!this.mIsInputChannel) {
/*     */       try {
/*  72 */         Log.d("SaveFilter", this.mMySequenceNumber + ": FileDescriptorFilter copy READ mode close output");
/*  73 */         this.mFileChannel.close();
/*  74 */         FileInputStream fileInputStream = new FileInputStream(this.mPfd.getFileDescriptor());
/*  75 */         this.mFileChannel = fileInputStream.getChannel();
/*  76 */         this.mLock = null;
/*  77 */         this.mIsInputChannel = true;
/*  78 */       } catch (IOException iOException) {
/*  79 */         (paramFileDescriptorFilter = null).printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  83 */     Log.d("SaveFilter", this.mMySequenceNumber + ": copy FileDescriptorFilter in Input mode, actual mode: " + a(paramInt));
/*  84 */     if (!this.mFileChannel.isOpen()) {
/*  85 */       Log.e("SaveFilter", this.mMySequenceNumber + ": copy FileDescriptorFilter file channel closed!!!");
/*     */     }
/*     */     
/*  88 */     if (paramInt == 0) {
/*  89 */       this.mFilterManager.addReadFilter(this); return;
/*  90 */     }  if (paramInt == 1) {
/*  91 */       this.mFilterManager.addReadWriteFilter(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String a(int paramInt) {
/*  96 */     if (paramInt == 0) {
/*  97 */       return "READ";
/*     */     }
/*  99 */     return "WRITE";
/*     */   }
/*     */ 
/*     */   
/*     */   protected FileDescriptorFilter(long paramLong, FileDescriptorFilter paramFileDescriptorFilter) {
/* 104 */     super(paramLong, (Filter)null);
/*     */     
/* 106 */     this.mPfd = paramFileDescriptorFilter.mPfd;
/* 107 */     this.mMode = paramFileDescriptorFilter.mMode;
/* 108 */     this.mFileChannel = paramFileDescriptorFilter.mFileChannel;
/* 109 */     this.mIsInputChannel = paramFileDescriptorFilter.mIsInputChannel;
/* 110 */     this.mFilterManager = paramFileDescriptorFilter.mFilterManager;
/* 111 */     this.mMySequenceNumber = paramFileDescriptorFilter.mMySequenceNumber;
/* 112 */     this.mLock = paramFileDescriptorFilter.mLock;
/*     */   }
/*     */   
/*     */   public static FileDescriptorFilter __Create(long paramLong, FileDescriptorFilter paramFileDescriptorFilter) {
/* 116 */     return new FileDescriptorFilter(paramLong, paramFileDescriptorFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws PDFNetException {}
/*     */ 
/*     */   
/*     */   public int getThreadId() {
/* 125 */     return Process.getThreadPriority(Process.myTid());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long onRead(byte[] paramArrayOfbyte, Object paramObject) {
/*     */     try {
/* 132 */       ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/* 133 */       if (!this.mIsInputChannel) {
/*     */         
/*     */         try {
/* 136 */           this.mFileChannel.close();
/* 137 */           this.mLock = null;
/* 138 */           paramObject = new FileInputStream(this.mPfd.getFileDescriptor());
/* 139 */           this.mFileChannel = paramObject.getChannel();
/* 140 */           this.mIsInputChannel = true;
/* 141 */         } catch (IOException iOException) {
/* 142 */           (paramObject = null).printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/*     */       try {
/* 147 */         this.mFileChannel.position(this.mPosition);
/* 148 */         int i = this.mFileChannel.read(byteBuffer);
/* 149 */         this.mPosition = this.mFileChannel.position();
/*     */         
/* 151 */         this.mFileChannel.position(0L);
/* 152 */         return i;
/* 153 */       } catch (IOException iOException) {
/* 154 */         (paramObject = null).printStackTrace();
/*     */       } 
/* 156 */     } catch (Exception exception) {
/* 157 */       (paramArrayOfbyte = null).printStackTrace();
/*     */     } 
/*     */     
/* 160 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long onSeek(long paramLong, int paramInt, Object paramObject) {
/* 166 */     byte b = 0;
/*     */     
/*     */     try {
/* 169 */       if (paramInt == 0) {
/* 170 */         this.mPosition = (paramLong >= 0L) ? paramLong : 0L;
/* 171 */       } else if (paramInt == 1) {
/* 172 */         this.mPosition = paramLong + this.mPosition;
/* 173 */       } else if (paramInt == 2) {
/* 174 */         this.mPosition = this.mFileChannel.size() + paramLong;
/*     */       } 
/*     */       
/* 177 */       this.mFileChannel.position(this.mPosition);
/*     */       
/* 179 */       this.mFileChannel.position(0L);
/*     */     }
/* 181 */     catch (Exception exception) {
/* 182 */       b = -1;
/* 183 */       Log.e("SaveFilter", this.mMySequenceNumber + ": save FileDescriptorFilter onSeek ERROR: " + getThreadId() + "| isInputFilter:" + ((this.mMode == 0) ? 1 : 0));
/* 184 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 187 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public long onTell(Object paramObject) {
/* 192 */     return this.mPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long onTruncate(long paramLong, Object paramObject) {
/* 199 */     switchToWriteMode();
/*     */     
/* 201 */     if (this.mLock != null && !this.mIsInputChannel) {
/*     */       try {
/* 203 */         if (this.mPosition > paramLong) {
/* 204 */           this.mPosition = paramLong;
/*     */         }
/* 206 */         this.mFileChannel.truncate(paramLong);
/* 207 */         this.mFileChannel.position(0L);
/* 208 */         return this.mFileChannel.size();
/* 209 */       } catch (IOException iOException2) {
/* 210 */         IOException iOException1; (iOException1 = null).printStackTrace();
/*     */       } 
/*     */     }
/* 213 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long onFlush(Object paramObject) {
/* 218 */     long l = -1L;
/* 219 */     if (this.mLock != null) {
/* 220 */       Log.d("SaveFilter", this.mMySequenceNumber + ":" + getThreadId() + ": FileDescriptorFilter onFlush position: " + this.mPosition + " | mIsInputChannel: " + this.mIsInputChannel);
/*     */       try {
/* 222 */         this.mFileChannel.truncate(this.mPosition);
/* 223 */         l = 0L;
/* 224 */       } catch (IOException iOException) {
/* 225 */         Log.e("SaveFilter", iOException.getMessage());
/*     */       } finally {
/* 227 */         this.mFilterManager.releaseLock();
/* 228 */         Log.d("SaveFilter", this.mMySequenceNumber + ":" + getThreadId() + ": onFlush releaseLock");
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long onWrite(byte[] paramArrayOfbyte, Object paramObject) {
/* 238 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/*     */     
/* 240 */     switchToWriteMode();
/*     */     
/* 242 */     if (this.mLock != null && !this.mIsInputChannel) {
/*     */       try {
/* 244 */         this.mFileChannel.position(this.mPosition);
/* 245 */         int i = this.mFileChannel.write(byteBuffer);
/* 246 */         this.mPosition = this.mFileChannel.position();
/*     */         
/* 248 */         return i;
/* 249 */       } catch (IOException iOException) {
/* 250 */         (byteBuffer = null).printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 254 */     return 0L;
/*     */   }
/*     */   
/*     */   public boolean switchToWriteMode() {
/* 258 */     if (this.mIsInputChannel) {
/*     */       
/*     */       try {
/* 261 */         this.mFileChannel.close();
/* 262 */         FileOutputStream fileOutputStream = new FileOutputStream(this.mPfd.getFileDescriptor());
/* 263 */         this.mFileChannel = fileOutputStream.getChannel();
/*     */         try {
/* 265 */           this.mLock = this.mFileChannel.lock();
/* 266 */         } catch (IOException iOException) {
/* 267 */           this.mLock = null;
/* 268 */           iOException.printStackTrace();
/*     */         } 
/* 270 */         if (!this.mFilterManager.acquireLock()) {
/* 271 */           this.mLock = null;
/*     */         }
/*     */         
/* 274 */         this.mIsInputChannel = false;
/* 275 */       } catch (IOException iOException2) {
/* 276 */         IOException iOException1; (iOException1 = null).printStackTrace();
/*     */       } 
/*     */     }
/* 279 */     return !this.mIsInputChannel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long onCreateInputIterator(Object paramObject) {
/* 285 */     Log.d("SaveFilter", this.mMySequenceNumber + ":" + getThreadId() + ": FileDescriptorFilter onCreateInputIterator position: " + this.mPosition);
/*     */     try {
/* 287 */       paramObject = new FileDescriptorFilter(0, this);
/*     */ 
/*     */       
/* 290 */       this.callback_data = ((FileDescriptorFilter)paramObject).callback_data;
/* 291 */       this.mPfd = ((FileDescriptorFilter)paramObject).mPfd;
/* 292 */       this.mMode = ((FileDescriptorFilter)paramObject).mMode;
/* 293 */       this.mFileChannel = ((FileDescriptorFilter)paramObject).mFileChannel;
/* 294 */       this.mIsInputChannel = ((FileDescriptorFilter)paramObject).mIsInputChannel;
/* 295 */       this.mFilterManager = ((FileDescriptorFilter)paramObject).mFilterManager;
/* 296 */       this.mMySequenceNumber = ((FileDescriptorFilter)paramObject).mMySequenceNumber;
/* 297 */       this.mLock = ((FileDescriptorFilter)paramObject).mLock;
/* 298 */       return paramObject.__GetHandle();
/* 299 */     } catch (Exception exception) {
/* 300 */       (paramObject = null).printStackTrace();
/*     */ 
/*     */       
/* 303 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDestroy(Object paramObject) {
/*     */     try {
/* 311 */       if (this.mMode == 0) {
/* 312 */         this.mFilterManager.removeReadFilter(this);
/*     */       } else {
/* 314 */         this.mFilterManager.removeReadWriteFilter(this);
/*     */       } 
/* 316 */       this.impl = 0L;
/* 317 */       this.callback_data = 0L; return;
/* 318 */     } catch (Exception exception) {
/* 319 */       (paramObject = null).printStackTrace();
/*     */       return;
/*     */     } 
/*     */   }
/*     */   public FileDescriptorFilter createOutputIterator() {
/*     */     try {
/* 325 */       if (this.mFileChannel == null) {
/* 326 */         Log.e("SaveFilter", this.mMySequenceNumber + ":" + getThreadId() + ": FileDescriptorFilter createOutputIterator: FileChannel IS NULL!!!");
/*     */       }
/* 328 */       if (this.mFileChannel != null && !this.mFileChannel.isOpen()) {
/* 329 */         Log.e("SaveFilter", this.mMySequenceNumber + ":" + getThreadId() + ": FileDescriptorFilter createOutputIterator: FileChannel IS CLOSED!!!");
/*     */       }
/*     */       
/*     */       FileDescriptorFilter fileDescriptorFilter;
/*     */       
/* 334 */       (fileDescriptorFilter = new FileDescriptorFilter(1, this.mPfd)).seek(0L, 2);
/* 335 */       Log.d("SaveFilter", this.mMySequenceNumber + ": FileDescriptorFilter createOutputIterator: " + fileDescriptorFilter.mMySequenceNumber + " | position: " + fileDescriptorFilter.mPosition);
/* 336 */       return fileDescriptorFilter;
/* 337 */     } catch (Exception exception2) {
/* 338 */       Exception exception1; (exception1 = null).printStackTrace();
/*     */       
/* 340 */       return null;
/*     */     } 
/*     */   }
/*     */   public boolean canWriteOnInputFilter() {
/* 344 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 349 */     this.mFilterManager.cleanup();
/*     */     try {
/* 351 */       this.mPfd.close();
/* 352 */       Log.d("SaveFilter", this.mMySequenceNumber + ": FileDescriptorFilter close ParcelFileDescriptor"); return;
/* 353 */     } catch (Exception exception2) {
/* 354 */       Exception exception1; (exception1 = null).printStackTrace();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\FileDescriptorFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */