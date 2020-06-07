/*     */ package android.view;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Rect;
/*     */ import android.util.AttributeSet;
/*     */ import android.widget.LinearLayout;
/*     */ import androidx.annotation.AttrRes;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DispatchFairInsetsLinearLayout
/*     */   extends LinearLayout
/*     */ {
/*     */   private boolean mConsumeInsets = true;
/*  23 */   private Rect mTempInsets = null;
/*     */   
/*     */   public DispatchFairInsetsLinearLayout(Context context) {
/*  26 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public DispatchFairInsetsLinearLayout(Context context, AttributeSet attrs) {
/*  30 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public DispatchFairInsetsLinearLayout(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
/*  34 */     super(context, attrs, defStyleAttr);
/*     */     
/*  36 */     TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DispatchFairInsetsLinearLayout, defStyleAttr, R.style.DispatchFairInsetsLinearLayout);
/*     */     
/*     */     try {
/*  39 */       this.mConsumeInsets = a.getBoolean(R.styleable.DispatchFairInsetsLinearLayout_consumeInsets, true);
/*     */     } finally {
/*  41 */       a.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fitSystemWindows(Rect insets) {
/*  52 */     if (Utils.isLollipop()) {
/*  53 */       return super.fitSystemWindows(insets);
/*     */     }
/*  55 */     if (this.mTempInsets == null) {
/*  56 */       this.mTempInsets = new Rect();
/*     */     }
/*  58 */     int childCount = getChildCount();
/*  59 */     for (int i = 0; i < childCount; i++) {
/*  60 */       View child = getChildAt(i);
/*     */ 
/*     */       
/*  63 */       this.mTempInsets.set(insets);
/*  64 */       child.fitSystemWindows(this.mTempInsets);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  69 */     return this.mConsumeInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(21)
/*     */   public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
/*  81 */     int childCount = getChildCount();
/*  82 */     for (int i = 0; i < childCount; i++) {
/*  83 */       View child = getChildAt(i);
/*  84 */       if (child != null) {
/*  85 */         child.dispatchApplyWindowInsets(insets);
/*     */       }
/*     */     } 
/*  88 */     return this.mConsumeInsets ? insets.consumeSystemWindowInsets() : insets;
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
/*     */   public void setConsumeInsets(boolean consumeInsets) {
/* 100 */     if (this.mConsumeInsets != consumeInsets) {
/* 101 */       this.mConsumeInsets = consumeInsets;
/*     */ 
/*     */       
/* 104 */       ViewCompat.requestApplyInsets((View)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getConsumeInsets() {
/* 113 */     return this.mConsumeInsets;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\android\view\DispatchFairInsetsLinearLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */