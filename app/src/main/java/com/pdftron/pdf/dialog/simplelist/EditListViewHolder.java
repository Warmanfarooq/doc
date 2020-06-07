/*    */ package com.pdftron.pdf.dialog.simplelist;
/*    */ 
/*    */ import android.view.View;
/*    */ import android.widget.EditText;
/*    */ import android.widget.ImageButton;
/*    */ import android.widget.TextView;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ public class EditListViewHolder
/*    */   extends RecyclerView.ViewHolder
/*    */ {
/*    */   public final TextView textView;
/*    */   public final EditText editText;
/*    */   public final ImageButton contextButton;
/*    */   public final ImageButton confirmButton;
/*    */   
/*    */   public EditListViewHolder(@NonNull View itemView) {
/* 20 */     super(itemView);
/*    */     
/* 22 */     this.textView = (TextView)itemView.findViewById(R.id.textview);
/* 23 */     this.editText = (EditText)itemView.findViewById(R.id.edittext);
/* 24 */     this.contextButton = (ImageButton)itemView.findViewById(R.id.context_button);
/* 25 */     this.confirmButton = (ImageButton)itemView.findViewById(R.id.confirm_button);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\simplelist\EditListViewHolder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */