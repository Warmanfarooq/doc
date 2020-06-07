/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.animation.Animator;
/*     */ import android.animation.TimeInterpolator;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.animation.AccelerateInterpolator;
/*     */ import android.view.animation.DecelerateInterpolator;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.SelectionLoupe;
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
/*     */ public abstract class BaseTool
/*     */   extends Tool
/*     */ {
/*     */   public static final int LOUPE_TYPE_TEXT = 1;
/*     */   public static final int LOUPE_TYPE_MEASURE = 2;
/*     */   public static final int LOUPE_RADIUS = 60;
/*     */   public static final int LOUPE_SIZE = 120;
/*     */   protected final float mTSWidgetRadius;
/*     */   private int mLoupeWidth;
/*     */   private int mLoupeHeight;
/*     */   private int mLoupeRadius;
/*     */   private int mLoupeSize;
/*     */   protected boolean mLoupeEnabled;
/*     */   protected SelectionLoupe mSelectionLoupe;
/*     */   protected RectF mLoupeBBox;
/*     */   protected Canvas mCanvas;
/*     */   protected Bitmap mBitmap;
/*     */   protected boolean mDrawingLoupe;
/*     */   protected RectF mSrcRectF;
/*     */   protected RectF mDesRectF;
/*     */   protected Matrix mMatrix;
/*     */   protected PointF mPressedPoint;
/*     */   
/*     */   public BaseTool(@NonNull PDFViewCtrl ctrl) {
/*  53 */     super(ctrl);
/*     */     
/*  55 */     this.mTSWidgetRadius = convDp2Pix(12.0F);
/*     */     
/*  57 */     this.mLoupeWidth = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.pdftron_magnifier_width);
/*  58 */     this.mLoupeHeight = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.pdftron_magnifier_height);
/*     */     
/*  60 */     this.mLoupeBBox = new RectF();
/*     */     
/*  62 */     this.mSrcRectF = new RectF();
/*  63 */     this.mDesRectF = new RectF();
/*  64 */     this.mMatrix = new Matrix();
/*  65 */     this.mPressedPoint = new PointF();
/*     */     
/*  67 */     this.mSelectionLoupe = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getSelectionLoupe(getLoupeType());
/*  68 */     this.mBitmap = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getSelectionLoupeBitmap(getLoupeType());
/*  69 */     this.mCanvas = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getSelectionLoupeCanvas(getLoupeType());
/*     */     
/*  71 */     this.mLoupeSize = (int)convDp2Pix(120.0F);
/*  72 */     this.mLoupeRadius = (int)convDp2Pix(60.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*  77 */     boolean result = super.onDown(e);
/*     */     
/*  79 */     float x = e.getX() + this.mPdfViewCtrl.getScrollX();
/*  80 */     float y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*  81 */     this.mPressedPoint.x = x;
/*  82 */     this.mPressedPoint.y = y;
/*     */     
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  89 */     boolean result = super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/*  91 */     float sx = this.mPdfViewCtrl.getScrollX();
/*  92 */     float sy = this.mPdfViewCtrl.getScrollY();
/*  93 */     this.mPressedPoint.x = e2.getX() + sx;
/*  94 */     this.mPressedPoint.y = e2.getY() + sy;
/*     */     
/*  96 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 101 */     super.onClose();
/* 102 */     this.mSelectionLoupe.dismiss();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void unsetAnnot() {
/* 107 */     super.unsetAnnot();
/*     */     
/* 109 */     this.mSelectionLoupe.dismiss();
/*     */   }
/*     */   
/*     */   public boolean isDrawingLoupe() {
/* 113 */     return this.mDrawingLoupe;
/*     */   }
/*     */   
/*     */   protected void setLoupeInfo(float touch_x, float touch_y) {
/* 117 */     if (!this.mLoupeEnabled) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     if (Utils.isPie() && 1 == getLoupeType()) {
/*     */       try {
/* 123 */         this.mSelectionLoupe.show(touch_x, touch_y - this.mTSWidgetRadius);
/* 124 */       } catch (Exception ex) {
/* 125 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 130 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 131 */     float sy = this.mPdfViewCtrl.getScrollY();
/*     */     
/* 133 */     float left = touch_x + sx - this.mLoupeWidth / 2.0F;
/* 134 */     float right = left + this.mLoupeWidth;
/* 135 */     float top = touch_y + sy - this.mLoupeHeight * 1.6F;
/* 136 */     float bottom = top + this.mLoupeHeight;
/*     */     
/* 138 */     if (2 == getLoupeType()) {
/* 139 */       left = touch_x + sx - this.mLoupeSize / 2.0F;
/* 140 */       right = left + this.mLoupeSize;
/* 141 */       top = touch_y + sy - this.mLoupeSize * 1.2F;
/* 142 */       bottom = top + this.mLoupeSize;
/*     */     } 
/*     */     
/* 145 */     this.mLoupeBBox.set(left, top, right, bottom);
/*     */     
/* 147 */     float centerX = this.mLoupeBBox.centerX();
/* 148 */     float centerY = this.mLoupeBBox.centerY();
/*     */     
/* 150 */     if (this.mSelectionLoupe != null) {
/* 151 */       if (2 == getLoupeType()) {
/* 152 */         this.mSelectionLoupe.layout((int)(centerX - this.mLoupeRadius), (int)(centerY - this.mLoupeRadius), (int)(centerX + this.mLoupeRadius), (int)(centerY + this.mLoupeRadius));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 158 */         this.mSelectionLoupe.layout((int)(centerX - (this.mLoupeWidth / 2)), (int)(centerY - (this.mLoupeHeight / 2)), (int)(centerX + (this.mLoupeWidth / 2)), (int)(centerY + (this.mLoupeHeight / 2)));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void animateLoupe(boolean show) {
/* 169 */     if (!this.mLoupeEnabled) {
/*     */       return;
/*     */     }
/* 172 */     if (null == this.mSelectionLoupe) {
/*     */       return;
/*     */     }
/* 175 */     if (Utils.isPie() && 1 == getLoupeType()) {
/* 176 */       if (!show) {
/* 177 */         this.mSelectionLoupe.dismiss();
/*     */       }
/*     */       return;
/*     */     } 
/* 181 */     if (2 == getLoupeType()) {
/* 182 */       this.mSelectionLoupe.setPivotX(this.mLoupeRadius);
/* 183 */       this.mSelectionLoupe.setPivotY((this.mLoupeRadius * 2));
/*     */     } else {
/* 185 */       this.mSelectionLoupe.setPivotX(this.mLoupeWidth / 2.0F);
/* 186 */       this.mSelectionLoupe.setPivotY(this.mLoupeHeight);
/*     */     } 
/* 188 */     if (show) {
/* 189 */       this.mSelectionLoupe.show();
/* 190 */       this.mSelectionLoupe.animate()
/* 191 */         .scaleX(1.0F)
/* 192 */         .scaleY(1.0F)
/* 193 */         .alpha(1.0F)
/* 194 */         .setDuration(100L)
/* 195 */         .setInterpolator((TimeInterpolator)new DecelerateInterpolator())
/* 196 */         .setListener(new Animator.AnimatorListener()
/*     */           {
/*     */             public void onAnimationStart(Animator animation) {
/* 199 */               BaseTool.this.mSelectionLoupe.setScaleX(0.0F);
/* 200 */               BaseTool.this.mSelectionLoupe.setScaleY(0.0F);
/* 201 */               BaseTool.this.mSelectionLoupe.setAlpha(0.0F);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationEnd(Animator animation) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationCancel(Animator animation) {
/* 211 */               BaseTool.this.mSelectionLoupe.dismiss();
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationRepeat(Animator animation) {}
/*     */           });
/*     */     } else {
/* 220 */       this.mSelectionLoupe.animate()
/* 221 */         .scaleX(0.0F)
/* 222 */         .scaleY(0.0F)
/* 223 */         .alpha(0.0F)
/* 224 */         .setDuration(150L)
/* 225 */         .setInterpolator((TimeInterpolator)new AccelerateInterpolator())
/* 226 */         .setListener(new Animator.AnimatorListener()
/*     */           {
/*     */             public void onAnimationStart(Animator animation) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void onAnimationEnd(Animator animation) {
/* 234 */               BaseTool.this.mSelectionLoupe.dismiss();
/*     */             }
/*     */ 
/*     */             
/*     */             public void onAnimationCancel(Animator animation) {
/* 239 */               BaseTool.this.mSelectionLoupe.dismiss();
/*     */             }
/*     */ 
/*     */             
/*     */             public void onAnimationRepeat(Animator animation) {}
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean canDrawLoupe();
/*     */ 
/*     */   
/*     */   protected abstract int getLoupeType();
/*     */   
/*     */   protected Matrix getMatrix() {
/* 255 */     float left = this.mPressedPoint.x - this.mLoupeBBox.width() / 2.0F / 1.25F;
/* 256 */     float top = this.mPressedPoint.y - this.mLoupeBBox.height() / 2.0F / 1.25F;
/* 257 */     float right = this.mPressedPoint.x + this.mLoupeBBox.width() / 2.0F / 1.25F;
/* 258 */     float bottom = this.mPressedPoint.y + this.mLoupeBBox.height() / 2.0F / 1.25F;
/*     */     
/* 260 */     if (2 == getLoupeType()) {
/* 261 */       left = this.mPressedPoint.x - this.mLoupeBBox.width() / 4.0F;
/* 262 */       top = this.mPressedPoint.y - this.mLoupeBBox.height() / 4.0F;
/* 263 */       right = left + this.mLoupeBBox.width() / 2.0F;
/* 264 */       bottom = top + this.mLoupeBBox.height() / 2.0F;
/*     */     } 
/*     */     
/* 267 */     this.mSrcRectF.set(left, top, right, bottom);
/* 268 */     this.mDesRectF.set(0.0F, 0.0F, this.mBitmap.getWidth(), this.mBitmap.getHeight());
/* 269 */     this.mMatrix.setRectToRect(this.mSrcRectF, this.mDesRectF, Matrix.ScaleToFit.CENTER);
/*     */     
/* 271 */     return this.mMatrix;
/*     */   }
/*     */   
/*     */   protected void drawLoupe() {
/* 275 */     if (!this.mLoupeEnabled) {
/*     */       return;
/*     */     }
/* 278 */     if (this.mDrawingLoupe) {
/*     */       return;
/*     */     }
/*     */     
/* 282 */     if (null == this.mSelectionLoupe) {
/*     */       return;
/*     */     }
/*     */     
/* 286 */     if (Utils.isPie() && 1 == getLoupeType()) {
/*     */       return;
/*     */     }
/*     */     
/* 290 */     if (canDrawLoupe()) {
/* 291 */       this.mDrawingLoupe = true;
/*     */       
/* 293 */       this.mSelectionLoupe.setAlpha(0.0F);
/* 294 */       if (this.mAnnotView != null) {
/* 295 */         this.mAnnotView.setSelectionHandleVisible(false);
/*     */       }
/* 297 */       this.mCanvas.save();
/* 298 */       this.mCanvas.setMatrix(getMatrix());
/* 299 */       this.mPdfViewCtrl.draw(this.mCanvas);
/* 300 */       this.mCanvas.restore();
/* 301 */       this.mSelectionLoupe.setAlpha(1.0F);
/* 302 */       if (this.mAnnotView != null) {
/* 303 */         this.mAnnotView.setSelectionHandleVisible(true);
/*     */       }
/*     */       
/* 306 */       this.mDrawingLoupe = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 312 */     if (!this.mDrawingLoupe) {
/* 313 */       super.onDraw(canvas, tfm);
/*     */     }
/*     */     
/* 316 */     drawLoupe();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\BaseTool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */