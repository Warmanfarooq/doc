/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.graphics.Bitmap;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.Magnifier;
/*     */ import androidx.cardview.widget.CardView;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SelectionLoupe
/*     */   extends CardView
/*     */ {
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private ImageView mImageView;
/*     */   private Magnifier mMagnifier;
/*     */   private int mType;
/*     */   
/*     */   public SelectionLoupe(PDFViewCtrl pdfViewCtrl) {
/*  27 */     this(pdfViewCtrl, 1);
/*     */   }
/*     */   
/*     */   public SelectionLoupe(PDFViewCtrl pdfViewCtrl, int type) {
/*  31 */     super(pdfViewCtrl.getContext(), null);
/*  32 */     this.mPdfViewCtrl = pdfViewCtrl;
/*  33 */     this.mType = type;
/*     */     
/*  35 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  39 */     if (canUseMagnifier()) {
/*  40 */       this.mMagnifier = new Magnifier((View)this.mPdfViewCtrl);
/*     */     } else {
/*  42 */       LayoutInflater.from(getContext()).inflate(R.layout.view_selection_loupe, (ViewGroup)this);
/*  43 */       this.mImageView = (ImageView)findViewById(R.id.imageview);
/*     */       
/*  45 */       if (!Utils.isLollipop()) {
/*  46 */         setPreventCornerOverlap(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setup(Bitmap bitmap) {
/*  52 */     int cornerRadius = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.pdftron_magnifier_corner_radius);
/*  53 */     setup(bitmap, cornerRadius);
/*     */   }
/*     */   public void setup(Bitmap bitmap, float cornerRadius) {
/*  56 */     if (canUseMagnifier()) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     int elevation = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.pdftron_magnifier_elevation);
/*  61 */     setCardElevation(elevation);
/*     */     
/*  63 */     if (Utils.isLollipop()) {
/*  64 */       this.mImageView.setImageBitmap(bitmap);
/*  65 */       setRadius(cornerRadius);
/*     */     } else {
/*  67 */       RoundCornersDrawable round = new RoundCornersDrawable(bitmap, cornerRadius, 0);
/*  68 */       round.enableBorder(getContext().getResources().getColor(R.color.light_gray_border), 
/*  69 */           Utils.convDp2Pix(getContext(), 1.0F));
/*  70 */       this.mImageView.setBackground(round);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void show() {
/*  75 */     if (!canUseMagnifier() && 
/*  76 */       getParent() == null) {
/*  77 */       this.mPdfViewCtrl.addView((View)this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @TargetApi(28)
/*     */   public void show(float xPosInView, float yPosInView) {
/*  84 */     if (canUseMagnifier()) {
/*  85 */       this.mMagnifier.show(xPosInView, yPosInView);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dismiss() {
/*  90 */     if (canUseMagnifier()) {
/*  91 */       this.mMagnifier.dismiss();
/*     */     } else {
/*  93 */       this.mPdfViewCtrl.removeView((View)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLayout(boolean changed, int l, int t, int r, int b) {
/*  99 */     super.onLayout(changed, l, t, r, b);
/*     */     
/* 101 */     if (canUseMagnifier()) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     this.mImageView.layout(0, 0, r - l, b - t);
/*     */   }
/*     */   
/*     */   private boolean canUseMagnifier() {
/* 109 */     return (Utils.isPie() && this.mType == 1);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\SelectionLoupe.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */