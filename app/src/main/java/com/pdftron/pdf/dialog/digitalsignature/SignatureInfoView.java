/*    */ package com.pdftron.pdf.dialog.digitalsignature;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.res.TypedArray;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.ViewGroup;
/*    */ import android.widget.FrameLayout;
/*    */ import android.widget.TextView;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SignatureInfoView
/*    */   extends FrameLayout
/*    */ {
/*    */   private TextView mLabel;
/*    */   private TextView mDetails;
/*    */   
/*    */   public SignatureInfoView(Context context) {
/* 23 */     this(context, null);
/*    */   }
/*    */   
/*    */   public SignatureInfoView(Context context, @Nullable AttributeSet attrs) {
/* 27 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public SignatureInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/* 31 */     super(context, attrs, defStyleAttr);
/* 32 */     init(context, attrs, defStyleAttr);
/*    */   }
/*    */   
/*    */   private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/* 36 */     LayoutInflater.from(context).inflate(R.layout.view_signature_info, (ViewGroup)this, true);
/* 37 */     this.mLabel = (TextView)findViewById(R.id.tools_dialog_signatureinfo_label);
/* 38 */     this.mDetails = (TextView)findViewById(R.id.tools_dialog_signatureinfo_details);
/*    */     
/* 40 */     TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SignatureInfoView, defStyleAttr, R.style.SignatureInfoViewDefault);
/*    */     
/*    */     try {
/* 43 */       String label = a.getString(R.styleable.SignatureInfoView_info_label);
/* 44 */       this.mLabel.setText(label);
/*    */     } finally {
/* 46 */       a.recycle();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLabel(@Nullable String label) {
/* 56 */     this.mLabel.setText(label);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDetails(@Nullable String details) {
/* 65 */     this.mDetails.setText(details);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\digitalsignature\SignatureInfoView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */