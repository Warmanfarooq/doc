/*     */ package com.pdftron.pdf.widget.signature;
/*     */ 
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.StrokeOutlineBuilder;
/*     */ import com.pdftron.pdf.utils.PathPool;
/*     */ import com.pdftron.pdf.utils.PointFPool;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import io.reactivex.Observable;
/*     */ import io.reactivex.android.schedulers.AndroidSchedulers;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import io.reactivex.disposables.Disposable;
/*     */ import io.reactivex.functions.Action;
/*     */ import io.reactivex.functions.Consumer;
/*     */ import io.reactivex.functions.Function;
/*     */ import io.reactivex.functions.Predicate;
/*     */ import io.reactivex.schedulers.Schedulers;
/*     */ import io.reactivex.subjects.PublishSubject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PointProcessor
/*     */ {
/*     */   @Nullable
/*     */   protected StrokeOutlineBuilder mCurrentOutlineBuilder;
/*  41 */   protected ArrayList<PointF> mCurrentCanvasStroke = new ArrayList<>();
/*  42 */   private List<StrokeOutlineResult> mAllStrokeOutlines = new ArrayList<>();
/*     */   
/*     */   private Observable<InkDrawInfo> mTouchEventToOutlinePathObservable;
/*  45 */   private PublishSubject<InkEvent> mTouchEventSubject = PublishSubject.create();
/*  46 */   private final CompositeDisposable mDisposable = new CompositeDisposable();
/*     */ 
/*     */   
/*     */   private final DrawCallback mDrawCallback;
/*     */   
/*     */   private final Bitmap mBitmap;
/*     */   
/*     */   private final Canvas mCanvas;
/*     */   
/*     */   private final Paint mPaint;
/*     */   
/*     */   private double mStrokeWidth;
/*     */   
/*     */   private boolean mIsPressureSensitive = true;
/*     */ 
/*     */   
/*     */   private PointProcessor(int width, int height, @ColorInt int strokeColor, double strokeWidth, @NonNull DrawCallback drawCallback) {
/*  63 */     this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
/*  64 */     this.mCanvas = new Canvas(this.mBitmap);
/*  65 */     this.mStrokeWidth = strokeWidth;
/*     */ 
/*     */     
/*  68 */     this.mPaint = new Paint();
/*  69 */     this.mPaint.setStrokeCap(Paint.Cap.ROUND);
/*  70 */     this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
/*  71 */     this.mPaint.setStrokeWidth(0.0F);
/*  72 */     this.mPaint.setAntiAlias(true);
/*  73 */     this.mPaint.setColor(strokeColor);
/*     */     
/*  75 */     this.mDrawCallback = drawCallback;
/*     */     
/*  77 */     this.mTouchEventToOutlinePathObservable = outlineArrayToOutlineSegmentPath(touchEventToOutlineSegmentArray()).cache();
/*  78 */     this.mDisposable.add(subscribeToCanvasDrawer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointProcessor(int width, int height, @ColorInt int strokeColor, double strokeWidth, double opacity, @NonNull DrawCallback drawCallback) {
/*  89 */     this(width, height, strokeColor, strokeWidth, drawCallback);
/*  90 */     this.mPaint.setAlpha((int)(255.0D * opacity));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Disposable subscribeToCanvasDrawer() {
/* 104 */     return this.mTouchEventToOutlinePathObservable.observeOn(AndroidSchedulers.mainThread()).doOnComplete(new Action() { public void run() throws Exception { Utils.throwIfNotOnMainThread(); PointProcessor.this.mDrawCallback.onComplete(PointProcessor.this.mAllStrokeOutlines); } }).observeOn(Schedulers.computation()).subscribe(new Consumer<InkDrawInfo>()
/*     */         {
/*     */           public void accept(InkDrawInfo drawInfo) throws Exception
/*     */           {
/* 108 */             Utils.throwIfOnMainThread();
/*     */             
/* 110 */             PointProcessor.this.mCanvas.drawPath(drawInfo.path, PointProcessor.this.mPaint);
/* 111 */             PointProcessor.this.mDrawCallback.onDrawInfoReceived(drawInfo, PointProcessor.this.mBitmap);
/*     */           }
/*     */         }new Consumer<Throwable>()
/*     */         {
/*     */           public void accept(Throwable throwable) throws Exception
/*     */           {
/* 117 */             throw new RuntimeException(throwable);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDown(float x, float y, float pressure) {
/* 124 */     this.mTouchEventSubject.onNext(new InkEvent(InkEventType.ON_TOUCH_DOWN, x, y, this.mIsPressureSensitive ? pressure : 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMove(float x, float y, float pressure) {
/* 135 */     this.mTouchEventSubject.onNext(new InkEvent(InkEventType.ON_TOUCH_MOVE, x, y, this.mIsPressureSensitive ? pressure : 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUp(float x, float y, float pressure) {
/* 146 */     this.mTouchEventSubject.onNext(new InkEvent(InkEventType.ON_TOUCH_UP, x, y, this.mIsPressureSensitive ? pressure : 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 157 */     this.mDisposable.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setColorWithRedraw(@ColorInt int color) {
/* 167 */     this.mPaint.setColor(color);
/* 168 */     onRedrawIfInitialized();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setStrokeWidth(float strokeWidth) {
/* 180 */     this.mStrokeWidth = strokeWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() {
/* 187 */     this.mTouchEventSubject.onComplete();
/*     */   }
/*     */   
/*     */   private void onRedrawIfInitialized() {
/* 191 */     if (this.mBitmap != null) {
/*     */       
/* 193 */       this.mDisposable.clear();
/* 194 */       this.mBitmap.eraseColor(0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       this.mDisposable.add(subscribeToCanvasDrawer());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   private Observable<double[]> touchEventToOutlineSegmentArray() {
/* 207 */     return this.mTouchEventSubject.serialize().observeOn(Schedulers.computation()).map(new Function<InkEvent, double[]>()
/*     */         {
/*     */           public double[] apply(InkEvent inkEvent) throws Exception {
/* 210 */             Utils.throwIfOnMainThread();
/*     */ 
/*     */             
/* 213 */             InkEventType eventType = inkEvent.eventType;
/* 214 */             float x = inkEvent.x;
/* 215 */             float y = inkEvent.y;
/* 216 */             float pressure = inkEvent.pressure;
/*     */             
/* 218 */             return PointProcessor.this.handleTouches(eventType, x, y, pressure);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] handleTouches(InkEventType event, float x, float y, float pressure) {
/* 226 */     switch (event) {
/*     */       case ON_TOUCH_DOWN:
/* 228 */         return handleOnDown(x, y, pressure);
/*     */       case ON_TOUCH_MOVE:
/* 230 */         return handleOnMove(x, y, pressure);
/*     */ 
/*     */       
/*     */       case ON_TOUCH_UP:
/* 234 */         return handleOnUp(pressure);
/*     */     } 
/* 236 */     throw new RuntimeException("Missing check for event type");
/*     */   }
/*     */   
/*     */   protected double[] handleOnDown(float x, float y, float pressure) {
/* 240 */     this.mCurrentOutlineBuilder = new StrokeOutlineBuilder(this.mStrokeWidth);
/* 241 */     this.mCurrentCanvasStroke = new ArrayList<>();
/* 242 */     this.mCurrentOutlineBuilder.addPoint(x, y, pressure);
/* 243 */     this.mCurrentCanvasStroke.add(PointFPool.getInstance().obtain(x, y));
/* 244 */     return this.mCurrentOutlineBuilder.getOutline();
/*     */   }
/*     */   
/*     */   protected double[] handleOnMove(float x, float y, float pressure) {
/* 248 */     this.mCurrentOutlineBuilder.addPoint(x, y, pressure);
/* 249 */     this.mCurrentCanvasStroke.add(PointFPool.getInstance().obtain(x, y));
/* 250 */     return this.mCurrentOutlineBuilder.getOutline();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double[] handleOnUp(float pressure) {
/* 258 */     StrokeOutlineResult strokeOutlineResult = new StrokeOutlineResult(this.mCurrentCanvasStroke, this.mCurrentOutlineBuilder.getOutline());
/* 259 */     this.mAllStrokeOutlines.add(strokeOutlineResult);
/*     */     
/* 261 */     return this.mCurrentOutlineBuilder.getOutline();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   private Observable<InkDrawInfo> outlineArrayToOutlineSegmentPath(Observable<double[]> observable) {
/* 274 */     return observable.filter(new Predicate<double[]>() { public boolean test(double[] outline) throws Exception { return (outline.length > 8); } }).map(new Function<double[], InkDrawInfo>()
/*     */         {
/*     */           public InkDrawInfo apply(double[] outline) throws Exception {
/* 277 */             Utils.throwIfOnMainThread();
/* 278 */             Path pathf = PathPool.getInstance().obtain();
/* 279 */             pathf.setFillType(Path.FillType.WINDING);
/*     */             
/* 281 */             double left = outline[0];
/* 282 */             double top = outline[1];
/* 283 */             double right = outline[0];
/* 284 */             double bottom = outline[1];
/*     */             
/* 286 */             pathf.moveTo((float)outline[0], (float)outline[1]);
/* 287 */             for (int i = 2, cnt = outline.length; i < cnt; i += 6) {
/*     */ 
/*     */ 
/*     */               
/* 291 */               for (int k = 0; k <= 5; k += 2) {
/* 292 */                 double x = outline[i + k];
/* 293 */                 double y = outline[i + k + 1];
/* 294 */                 left = Math.min(x, left);
/* 295 */                 top = Math.min(y, top);
/* 296 */                 right = Math.max(x, right);
/* 297 */                 bottom = Math.max(y, bottom);
/*     */               } 
/*     */               
/* 300 */               pathf.cubicTo((float)outline[i], (float)outline[i + 1], (float)outline[i + 2], (float)outline[i + 3], (float)outline[i + 4], (float)outline[i + 5]);
/*     */             } 
/*     */             
/* 303 */             int fudge = 2;
/* 304 */             return new InkDrawInfo((int)left - fudge, (int)right + fudge, (int)top - fudge, (int)bottom + fudge, pathf, PointProcessor.this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 310 */                 .mPaint);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static interface DrawCallback {
/*     */     void onDrawInfoReceived(@NonNull InkDrawInfo param1InkDrawInfo, @NonNull Bitmap param1Bitmap);
/*     */     
/*     */     void onComplete(List<StrokeOutlineResult> param1List);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\signature\PointProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */