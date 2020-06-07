/*     */ package android.view;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Rect;
/*     */ import android.util.AttributeSet;
/*     */ import android.widget.RelativeLayout;
/*     */ import androidx.core.view.OnApplyWindowInsetsListener;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import androidx.core.view.WindowInsetsCompat;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DispatchFairInsetsRelativeLayout
/*     */   extends RelativeLayout
/*     */ {
/*     */   private static final boolean CONSUME_INSETS = true;
/*     */   private boolean mConsumeInsets = true;
/*  38 */   private Rect mTempInsets = null;
/*     */   
/*  40 */   private OnApplyWindowInsetsListener mListener = null;
/*     */   
/*     */   public DispatchFairInsetsRelativeLayout(Context context) {
/*  43 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public DispatchFairInsetsRelativeLayout(Context context, AttributeSet attrs) {
/*  47 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public DispatchFairInsetsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
/*  51 */     super(context, attrs, defStyleAttr);
/*     */     
/*  53 */     TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DispatchFairInsetsRelativeLayout, defStyleAttr, R.style.DispatchFairInsetsRelativeLayout);
/*     */ 
/*     */     
/*     */     try {
/*  57 */       this.mConsumeInsets = a.getBoolean(R.styleable.DispatchFairInsetsFrameLayout_consumeInsets, true);
/*  58 */     } catch (Exception ignored) {
/*  59 */       this.mConsumeInsets = true;
/*     */     } finally {
/*  61 */       a.recycle();
/*     */     } 
/*     */ 
/*     */     
/*  65 */     ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener()
/*     */         {
/*     */           public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
/*  68 */             return insets;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fitSystemWindows(Rect insets) {
/*  80 */     if (Utils.isLollipop()) {
/*  81 */       return super.fitSystemWindows(insets);
/*     */     }
/*  83 */     if (this.mTempInsets == null) {
/*  84 */       this.mTempInsets = new Rect();
/*     */     }
/*  86 */     int childCount = getChildCount();
/*  87 */     for (int i = 0; i < childCount; i++) {
/*  88 */       View child = getChildAt(i);
/*     */ 
/*     */       
/*  91 */       this.mTempInsets.set(insets);
/*  92 */       child.fitSystemWindows(this.mTempInsets);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  97 */     return this.mConsumeInsets;
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
/*     */ 
/*     */   
/*     */   @TargetApi(21)
/*     */   public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
/* 114 */     insets = applyWindowInsets(insets);
/* 115 */     int childCount = getChildCount();
/* 116 */     for (int i = 0; i < childCount; i++) {
/* 117 */       View child = getChildAt(i);
/* 118 */       if (child != null) {
/* 119 */         child.dispatchApplyWindowInsets(insets);
/*     */       }
/*     */     } 
/* 122 */     return this.mConsumeInsets ? insets.consumeSystemWindowInsets() : insets;
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
/*     */   private WindowInsets applyWindowInsets(WindowInsets insets) {
/* 134 */     if (this.mListener != null) {
/* 135 */       return this.mListener.onApplyWindowInsets((View)this, insets);
/*     */     }
/* 137 */     return onApplyWindowInsets(insets);
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
/*     */   @TargetApi(21)
/*     */   public void setOnApplyWindowInsetsListener(OnApplyWindowInsetsListener listener) {
/* 151 */     this.mListener = listener;
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
/* 163 */     if (this.mConsumeInsets != consumeInsets) {
/* 164 */       this.mConsumeInsets = consumeInsets;
/*     */ 
/*     */       
/* 167 */       ViewCompat.requestApplyInsets((View)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getConsumeInsets() {
/* 176 */     return this.mConsumeInsets;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\android\view\DispatchFairInsetsRelativeLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */