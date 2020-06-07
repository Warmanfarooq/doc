/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.StateListDrawable;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.PopupWindow;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
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
/*     */ public class StampStatePopup
/*     */   extends PopupWindow
/*     */ {
/*     */   public static final String STATE_SIGNATURE = "signature";
/*     */   public static final String STATE_IMAGE_STAMP = "stamp";
/*     */   public static final String STATE_RUBBER_STAMP = "rubber_stamp";
/*     */   private ToolManager mToolManager;
/*     */   private String mStampState;
/*     */   private ImageButton mSignatureButton;
/*     */   private ImageButton mImageStampButton;
/*     */   private ImageButton mRubberStampButton;
/*     */   
/*     */   public StampStatePopup(@NonNull Context context, @NonNull ToolManager toolManager, @NonNull String stampState, int backgroundColor, int iconColor) {
/*  37 */     super(context);
/*  38 */     this.mToolManager = toolManager;
/*  39 */     this.mStampState = stampState;
/*     */     
/*  41 */     View mainView = LayoutInflater.from(context).inflate(R.layout.tools_annotation_toolbar_state_stamp_popup, null);
/*  42 */     mainView.setBackgroundColor(backgroundColor);
/*  43 */     this.mSignatureButton = (ImageButton)mainView.findViewById(R.id.tools_annotation_toolbar_state_signature_button);
/*  44 */     this.mImageStampButton = (ImageButton)mainView.findViewById(R.id.tools_annotation_toolbar_state_image_stamp_button);
/*  45 */     this.mRubberStampButton = (ImageButton)mainView.findViewById(R.id.tools_annotation_toolbar_state_rubber_stamp_button);
/*     */     
/*  47 */     StateListDrawable stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_annotation_signature_black_24dp, iconColor);
/*  48 */     this.mSignatureButton.setImageDrawable((Drawable)stateListDrawable);
/*  49 */     stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_annotation_image_black_24dp, iconColor);
/*  50 */     this.mImageStampButton.setImageDrawable((Drawable)stateListDrawable);
/*  51 */     stateListDrawable = Utils.createImageDrawableSelector(context, R.drawable.ic_annotation_stamp_black_24dp, iconColor);
/*  52 */     this.mRubberStampButton.setImageDrawable((Drawable)stateListDrawable);
/*     */     
/*  54 */     this.mSignatureButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  57 */             StampStatePopup.this.mStampState = "signature";
/*  58 */             StampStatePopup.this.dismiss();
/*     */           }
/*     */         });
/*  61 */     this.mImageStampButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  64 */             StampStatePopup.this.mStampState = "stamp";
/*  65 */             StampStatePopup.this.dismiss();
/*     */           }
/*     */         });
/*  68 */     this.mRubberStampButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  71 */             StampStatePopup.this.mStampState = "rubber_stamp";
/*  72 */             StampStatePopup.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/*  76 */     updateButtonsVisibility();
/*     */     
/*  78 */     setContentView(mainView);
/*  79 */     setOutsideTouchable(true);
/*  80 */     setFocusable(false);
/*     */     
/*  82 */     setAnimationStyle(R.style.Controls_AnnotationPopupAnimation);
/*     */   }
/*     */   
/*     */   public void show(View parent, int x, int y) {
/*     */     try {
/*  87 */       showAtLocation(parent, 0, x, y);
/*  88 */     } catch (Exception ex) {
/*  89 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void showAtLocation(View parent, int gravity, int x, int y) {
/*  95 */     super.showAtLocation(parent, gravity, x, y);
/*  96 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(45);
/*     */   }
/*     */ 
/*     */   
/*     */   public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
/* 101 */     super.showAsDropDown(anchor, xoff, yoff, gravity);
/* 102 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(45);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 107 */     super.dismiss();
/* 108 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(40);
/*     */   }
/*     */   
/*     */   public String getStampState() {
/* 112 */     return this.mStampState;
/*     */   }
/*     */   
/*     */   public void updateView(String newSignatureState) {
/* 116 */     this.mStampState = newSignatureState;
/* 117 */     updateButtonsVisibility();
/*     */   }
/*     */   
/*     */   private void updateButtonsVisibility() {
/* 121 */     boolean visible = (!this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.SIGNATURE) && !"signature".equals(this.mStampState));
/* 122 */     this.mSignatureButton.setVisibility(visible ? 0 : 8);
/* 123 */     visible = (!this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.STAMPER) && !"stamp".equals(this.mStampState));
/* 124 */     this.mImageStampButton.setVisibility(visible ? 0 : 8);
/* 125 */     visible = (!this.mToolManager.isToolModeDisabled(ToolManager.ToolMode.RUBBER_STAMPER) && !"rubber_stamp".equals(this.mStampState));
/* 126 */     this.mRubberStampButton.setVisibility(visible ? 0 : 8);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\StampStatePopup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */