/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.Intent;
/*     */ import android.net.Uri;
/*     */ import android.util.Patterns;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.ActionParameter;
/*     */ import com.pdftron.pdf.FileSpec;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.sdf.Obj;
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
/*     */ 
/*     */ public class ActionUtils
/*     */ {
/*     */   private ActionInterceptCallback mActionCallback;
/*     */   
/*     */   private static class LazzyHolder
/*     */   {
/*  42 */     static final ActionUtils INSTANCE = new ActionUtils();
/*     */   }
/*     */   
/*     */   public static ActionUtils getInstance() {
/*  46 */     return LazzyHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionInterceptCallback(ActionInterceptCallback callback) {
/*  56 */     this.mActionCallback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionInterceptCallback getActionInterceptCallback() {
/*  65 */     return this.mActionCallback;
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
/*     */   public void executeAction(ActionParameter actionParam, final PDFViewCtrl pdfViewCtrl) {
/*     */     try {
/*  81 */       if (getActionInterceptCallback() != null && getActionInterceptCallback().onInterceptExecuteAction(actionParam, pdfViewCtrl)) {
/*     */         return;
/*     */       }
/*  84 */       Action action = actionParam.getAction();
/*  85 */       int action_type = action.getType();
/*  86 */       if (action_type == 5) {
/*  87 */         Obj o = action.getSDFObj();
/*  88 */         o = o.findObj("URI");
/*  89 */         if (o != null) {
/*  90 */           String uri = o.getAsPDFText();
/*  91 */           if (uri.startsWith("mailto:") || Patterns.EMAIL_ADDRESS.matcher(uri).matches()) {
/*     */             
/*  93 */             if (uri.startsWith("mailto:")) {
/*  94 */               uri = uri.substring(7);
/*     */             }
/*  96 */             Intent i = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", uri, null));
/*  97 */             pdfViewCtrl.getContext().startActivity(Intent.createChooser(i, pdfViewCtrl.getResources().getString(R.string.tools_misc_sendemail)));
/*  98 */           } else if (uri.startsWith("tel:") || Patterns.PHONE.matcher(uri).matches()) {
/*     */             
/* 100 */             if (uri.startsWith("tel:")) {
/* 101 */               uri = uri.substring(4);
/*     */             }
/* 103 */             Intent i = new Intent("android.intent.action.DIAL", Uri.fromParts("tel", uri, null));
/* 104 */             pdfViewCtrl.getContext().startActivity(Intent.createChooser(i, pdfViewCtrl.getResources().getString(R.string.tools_misc_dialphone)));
/*     */           } else {
/*     */             
/* 107 */             if (!uri.startsWith("https://") && !uri.startsWith("http://")) {
/* 108 */               uri = "http://" + uri;
/*     */             }
/*     */             
/* 111 */             final String finalUrl = uri;
/*     */             
/* 113 */             AlertDialog.Builder builder = new AlertDialog.Builder(pdfViewCtrl.getContext());
/* 114 */             builder.setTitle(R.string.tools_dialog_open_web_page_title)
/* 115 */               .setMessage(String.format(pdfViewCtrl.getResources().getString(R.string.tools_dialog_open_web_page_message), new Object[] { finalUrl
/* 116 */                   })).setIcon(null)
/* 117 */               .setPositiveButton(R.string.open, new DialogInterface.OnClickListener()
/*     */                 {
/*     */                   public void onClick(DialogInterface dialog, int which) {
/* 120 */                     Intent i = new Intent("android.intent.action.VIEW", Uri.parse(finalUrl));
/* 121 */                     pdfViewCtrl.getContext().startActivity(Intent.createChooser(i, pdfViewCtrl.getResources().getString(R.string.tools_misc_openwith)));
/*     */                   }
/* 124 */                 }).setNegativeButton(R.string.cancel, null);
/* 125 */             builder.create().show();
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 130 */         boolean handled = false;
/* 131 */         if (action_type == 1) {
/* 132 */           Obj o = action.getSDFObj();
/* 133 */           o = o.findObj("F");
/* 134 */           if (o != null) {
/* 135 */             FileSpec fileSpec = new FileSpec(o);
/* 136 */             if (fileSpec.isValid()) {
/* 137 */               String filePath = fileSpec.getFilePath();
/* 138 */               if (!Utils.isNullOrEmpty(filePath)) {
/* 139 */                 ToolManager toolManager = (ToolManager)pdfViewCtrl.getToolManager();
/* 140 */                 handled = toolManager.onNewFileCreated(filePath);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 145 */         if (!handled) {
/* 146 */           pdfViewCtrl.executeAction(actionParam);
/*     */         }
/*     */       } 
/* 149 */     } catch (PDFNetException e) {
/* 150 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface ActionInterceptCallback {
/*     */     boolean onInterceptExecuteAction(ActionParameter param1ActionParameter, PDFViewCtrl param1PDFViewCtrl);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ActionUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */