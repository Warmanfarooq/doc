/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Field
/*     */   extends h
/*     */ {
/*     */   public static final int e_button = 0;
/*     */   public static final int e_check = 1;
/*     */   public static final int e_radio = 2;
/*     */   public static final int e_text = 3;
/*     */   public static final int e_choice = 4;
/*     */   public static final int e_signature = 5;
/*     */   public static final int e_null = 6;
/*     */   public static final int e_action_trigger_keystroke = 13;
/*     */   public static final int e_action_trigger_format = 14;
/*     */   public static final int e_action_trigger_validate = 15;
/*     */   public static final int e_action_trigger_calculate = 16;
/*     */   public static final int e_read_only = 0;
/*     */   public static final int e_required = 1;
/*     */   public static final int e_no_export = 2;
/*     */   public static final int e_pushbutton_flag = 3;
/*     */   public static final int e_radio_flag = 4;
/*     */   public static final int e_toggle_to_off = 5;
/*     */   public static final int e_radios_in_unison = 6;
/*     */   public static final int e_multiline = 7;
/*     */   public static final int e_password = 8;
/*     */   public static final int e_file_select = 9;
/*     */   public static final int e_no_spellcheck = 10;
/*     */   public static final int e_no_scroll = 11;
/*     */   public static final int e_comb = 12;
/*     */   public static final int e_rich_text = 13;
/*     */   public static final int e_combo = 14;
/*     */   public static final int e_edit = 15;
/*     */   public static final int e_sort = 16;
/*     */   public static final int e_multiselect = 17;
/*     */   public static final int e_commit_on_sel_change = 18;
/*     */   public static final int e_left_justified = 0;
/*     */   public static final int e_centered = 1;
/*     */   public static final int e_right_justified = 2;
/*     */   long a;
/*     */   private Object d;
/*     */   
/*     */   public Field(Obj paramObj) throws PDFNetException {
/*  69 */     this.a = FieldCreate(paramObj.__GetHandle());
/*  70 */     this.d = paramObj.__GetRefHandle();
/*  71 */     clearList();
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
/*     */   public void destroy() throws PDFNetException {
/*  84 */     if (this.a != 0L && !(this.d instanceof FieldIterator)) {
/*     */       
/*  86 */       Destroy(this.a);
/*  87 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  93 */     destroy();
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
/*     */   public boolean isValid() throws PDFNetException {
/* 106 */     return IsValid(this.a);
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
/*     */   public Obj getTriggerAction(int paramInt) throws PDFNetException {
/* 160 */     return Obj.__Create(GetTriggerAction(this.a, paramInt), this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() throws PDFNetException {
/* 171 */     return GetType(this.a);
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
/*     */   public Obj getValue() throws PDFNetException {
/* 185 */     return Obj.__Create(GetValue(this.a), this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueAsString() throws PDFNetException {
/* 196 */     return GetValueAsString(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getValueAsBool() throws PDFNetException {
/* 207 */     return GetValueAsBool(this.a);
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
/*     */   public ViewChangeCollection setValue(Obj paramObj) throws PDFNetException {
/* 238 */     return new ViewChangeCollection(SetValue(this.a, paramObj.__GetHandle()));
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
/*     */   public ViewChangeCollection setValue(String paramString) throws PDFNetException {
/* 250 */     return new ViewChangeCollection(SetValue(this.a, paramString));
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
/*     */   public ViewChangeCollection setValue(boolean paramBoolean) throws PDFNetException {
/* 262 */     return new ViewChangeCollection(SetValue(this.a, paramBoolean));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshAppearance() throws PDFNetException {
/* 283 */     RefreshAppearance(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eraseAppearance() throws PDFNetException {
/* 293 */     EraseAppearance(this.a);
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
/*     */   public Obj getDefaultValue() throws PDFNetException {
/* 307 */     return Obj.__Create(GetDefaultValue(this.a), this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GState getDefaultAppearance() throws PDFNetException {
/* 315 */     return new GState(GetDefaultAppearance(this.a), this.d, null);
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
/*     */   public String getDefaultValueAsString() throws PDFNetException {
/* 327 */     return GetDefaultValueAsString(this.a);
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
/*     */   public String getName() throws PDFNetException {
/* 339 */     return GetName(this.a);
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
/*     */   public String getPartialName() throws PDFNetException {
/* 351 */     return GetPartialName(this.a);
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
/*     */   public void rename(String paramString) throws PDFNetException {
/* 363 */     Rename(this.a, paramString);
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
/*     */   public boolean isAnnot() throws PDFNetException {
/* 376 */     return IsAnnot(this.a);
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
/*     */   public boolean getFlag(int paramInt) throws PDFNetException {
/* 528 */     return GetFlag(this.a, paramInt);
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
/*     */   
/*     */   public void setFlag(int paramInt, boolean paramBoolean) throws PDFNetException {
/* 545 */     SetFlag(this.a, paramInt, paramBoolean);
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
/*     */   public int getJustification() throws PDFNetException {
/* 572 */     return GetJustification(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJustification(int paramInt) throws PDFNetException {
/* 583 */     SetJustification(this.a, paramInt);
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
/*     */   public void setMaxLen(int paramInt) throws PDFNetException {
/* 596 */     SetMaxLen(this.a, paramInt);
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
/*     */   public int getMaxLen() throws PDFNetException {
/* 610 */     return GetMaxLen(this.a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flatten(Page paramPage) throws PDFNetException {
/* 632 */     Flatten(this.a, paramPage.a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj findInheritedAttribute(String paramString) throws PDFNetException {
/* 657 */     return Obj.__Create(FindInheritedAttribute(this.a, paramString), this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rect getUpdateRect() {
/* 665 */     return new Rect(GetUpdateRect(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() throws PDFNetException {
/* 676 */     return Obj.__Create(GetSDFObj(this.a), this.d);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj useSignatureHandler(long paramLong) throws PDFNetException {
/*     */     long l;
/* 696 */     return Obj.__Create(l = UseSignatureHandler(this.a, paramLong), this.d);
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
/*     */   public int getOptCount() throws PDFNetException {
/* 708 */     return GetOptCount(this.a);
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
/*     */   public String getOpt(int paramInt) throws PDFNetException {
/* 722 */     return GetOpt(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLockedByDigitalSignature() throws PDFNetException {
/* 733 */     return IsLockedByDigitalSignature(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   Field(long paramLong, Object paramObject) throws PDFNetException {
/* 738 */     this.a = paramLong;
/* 739 */     this.d = paramObject;
/* 740 */     clearList();
/*     */   }
/*     */   
/*     */   public static Field __Create(long paramLong, Object paramObject) throws PDFNetException {
/* 744 */     if (paramLong == 0L) {
/* 745 */       return null;
/*     */     }
/* 747 */     return new Field(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 751 */     return this.a;
/*     */   }
/*     */   
/*     */   public Object __GetRefHandle() {
/* 755 */     return this.d;
/*     */   }
/*     */   
/*     */   static native long GetTriggerAction(long paramLong, int paramInt);
/*     */   
/*     */   static native long FieldCreate(long paramLong);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native long GetValue(long paramLong);
/*     */   
/*     */   static native String GetValueAsString(long paramLong);
/*     */   
/*     */   static native boolean GetValueAsBool(long paramLong);
/*     */   
/*     */   static native long SetValue(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long SetValue(long paramLong, String paramString);
/*     */   
/*     */   static native long SetValue(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void RefreshAppearance(long paramLong);
/*     */   
/*     */   static native void EraseAppearance(long paramLong);
/*     */   
/*     */   static native long GetDefaultValue(long paramLong);
/*     */   
/*     */   static native String GetDefaultValueAsString(long paramLong);
/*     */   
/*     */   static native long GetDefaultAppearance(long paramLong);
/*     */   
/*     */   static native String GetName(long paramLong);
/*     */   
/*     */   static native String GetPartialName(long paramLong);
/*     */   
/*     */   static native void Rename(long paramLong, String paramString);
/*     */   
/*     */   static native boolean IsAnnot(long paramLong);
/*     */   
/*     */   static native boolean GetFlag(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetFlag(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   static native int GetJustification(long paramLong);
/*     */   
/*     */   static native void SetJustification(long paramLong, int paramInt);
/*     */   
/*     */   static native void Flatten(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long FindInheritedAttribute(long paramLong, String paramString);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */   
/*     */   static native void SetMaxLen(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetMaxLen(long paramLong);
/*     */   
/*     */   static native long GetUpdateRect(long paramLong);
/*     */   
/*     */   static native long UseSignatureHandler(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetOptCount(long paramLong);
/*     */   
/*     */   static native String GetOpt(long paramLong, int paramInt);
/*     */   
/*     */   static native boolean IsLockedByDigitalSignature(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Field.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */