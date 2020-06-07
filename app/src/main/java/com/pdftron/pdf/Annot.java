/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Annot
/*      */ {
/*      */   public static final int e_Text = 0;
/*      */   public static final int e_Link = 1;
/*      */   public static final int e_FreeText = 2;
/*      */   public static final int e_Line = 3;
/*      */   public static final int e_Square = 4;
/*      */   public static final int e_Circle = 5;
/*      */   public static final int e_Polygon = 6;
/*      */   public static final int e_Polyline = 7;
/*      */   public static final int e_Highlight = 8;
/*      */   public static final int e_Underline = 9;
/*      */   public static final int e_Squiggly = 10;
/*      */   public static final int e_StrikeOut = 11;
/*      */   public static final int e_Stamp = 12;
/*      */   public static final int e_Caret = 13;
/*      */   public static final int e_Ink = 14;
/*      */   public static final int e_Popup = 15;
/*      */   public static final int e_FileAttachment = 16;
/*      */   public static final int e_Sound = 17;
/*      */   public static final int e_Movie = 18;
/*      */   public static final int e_Widget = 19;
/*      */   public static final int e_Screen = 20;
/*      */   public static final int e_PrinterMark = 21;
/*      */   public static final int e_TrapNet = 22;
/*      */   public static final int e_Watermark = 23;
/*      */   public static final int e_3D = 24;
/*      */   public static final int e_Redact = 25;
/*      */   public static final int e_Projection = 26;
/*      */   public static final int e_RichMedia = 27;
/*      */   public static final int e_Unknown = 28;
/*      */   public static final int e_action_trigger_activate = 0;
/*      */   public static final int e_action_trigger_annot_enter = 1;
/*      */   public static final int e_action_trigger_annot_exit = 2;
/*      */   public static final int e_action_trigger_annot_down = 3;
/*      */   public static final int e_action_trigger_annot_up = 4;
/*      */   public static final int e_action_trigger_annot_focus = 5;
/*      */   public static final int e_action_trigger_annot_blur = 6;
/*      */   public static final int e_action_trigger_annot_page_open = 7;
/*      */   public static final int e_action_trigger_annot_page_close = 8;
/*      */   public static final int e_action_trigger_annot_page_visible = 9;
/*      */   public static final int e_action_trigger_annot_page_invisible = 10;
/*      */   public static final int e_invisible = 0;
/*      */   public static final int e_hidden = 1;
/*      */   public static final int e_print = 2;
/*      */   public static final int e_no_zoom = 3;
/*      */   public static final int e_no_rotate = 4;
/*      */   public static final int e_no_view = 5;
/*      */   public static final int e_read_only = 6;
/*      */   public static final int e_locked = 7;
/*      */   public static final int e_toggle_no_view = 8;
/*      */   public static final int e_locked_contents = 9;
/*      */   public static final int e_normal = 0;
/*      */   public static final int e_rollover = 1;
/*      */   public static final int e_down = 2;
/*      */   long a;
/*      */   private Object b;
/*      */   
/*      */   public static Annot create(Doc paramDoc, int paramInt, Rect paramRect) throws PDFNetException {
/*  123 */     return new Annot(Create(paramDoc.__GetHandle(), paramInt, paramRect.a), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Annot(Obj paramObj) {
/*  136 */     this.a = paramObj.__GetHandle();
/*  137 */     this.b = paramObj.__GetRefHandle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Annot() {
/*  145 */     this.a = 0L;
/*  146 */     this.b = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getTriggerAction(int paramInt) throws PDFNetException {
/*  192 */     return Obj.__Create(GetTriggerAction(this.a, paramInt), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  204 */     if (paramObject != null && paramObject instanceof Annot) {
/*  205 */       return (this.a == ((Annot)paramObject).a);
/*      */     }
/*  207 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  214 */     return (int)this.a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid() throws PDFNetException {
/*  227 */     return IsValid(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getSDFObj() throws PDFNetException {
/*  238 */     return Obj.__Create(this.a, this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() throws PDFNetException {
/*  249 */     return GetType(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMarkup() throws PDFNetException {
/*  260 */     return IsMarkup(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getContents() throws PDFNetException {
/*  271 */     return GetContents(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContents(String paramString) throws PDFNetException {
/*  283 */     SetContents(this.a, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rect getRect() throws PDFNetException {
/*  299 */     return new Rect(GetRect(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rect getVisibleContentBox() throws PDFNetException {
/*  314 */     return new Rect(GetVisibleContentBox(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRect(Rect paramRect) throws PDFNetException {
/*  325 */     SetRect(this.a, paramRect.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Page getPage() throws PDFNetException {
/*  341 */     return new Page(GetPage(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPage(Page paramPage) throws PDFNetException {
/*  357 */     SetPage(this.a, paramPage.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getUniqueID() throws PDFNetException {
/*  370 */     return Obj.__Create(GetUniqueID(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUniqueID(String paramString) throws PDFNetException {
/*  383 */     SetUniqueID(this.a, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate() throws PDFNetException {
/*  395 */     return new Date(GetDate(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDate(Date paramDate) throws PDFNetException {
/*  407 */     SetDate(this.a, paramDate.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDateToNow() throws PDFNetException {
/*  417 */     SetDateToNow(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFlag(int paramInt) throws PDFNetException {
/*  504 */     return GetFlag(this.a, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlag(int paramInt, boolean paramBoolean) throws PDFNetException {
/*  516 */     SetFlag(this.a, paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BorderStyle
/*      */   {
/*      */     public static final int e_solid = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int e_dashed = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int e_beveled = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int e_inset = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int e_underline = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     long a;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BorderStyle(int param1Int1, int param1Int2, int param1Int3, int param1Int4) throws PDFNetException {
/*  558 */       this.a = Annot.BorderStyleCreate(param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BorderStyle(int param1Int1, int param1Int2, int param1Int3, int param1Int4, double[] param1ArrayOfdouble) throws PDFNetException {
/*  578 */       this.a = Annot.BorderStyleCreate(param1Int1, param1Int2, param1Int3, param1Int4, param1ArrayOfdouble);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void destroy() throws PDFNetException {
/*  592 */       if (this.a != 0L) {
/*      */         
/*  594 */         Annot.BorderStyleDestroy(this.a);
/*  595 */         this.a = 0L;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void finalize() throws Throwable {
/*  605 */       destroy();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStyle() throws PDFNetException {
/*  616 */       return Annot.BSGetStyle(this.a);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setStyle(int param1Int) throws PDFNetException {
/*  627 */       Annot.BSSetStyle(this.a, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getHR() throws PDFNetException {
/*  638 */       return Annot.BSGetHR(this.a);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setHR(int param1Int) throws PDFNetException {
/*  649 */       Annot.BSSetHR(this.a, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getVR() throws PDFNetException {
/*  660 */       return Annot.BSGetVR(this.a);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setVR(int param1Int) throws PDFNetException {
/*  671 */       Annot.BSSetVR(this.a, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getWidth() throws PDFNetException {
/*  683 */       return Annot.BSGetWidth(this.a);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWidth(double param1Double) throws PDFNetException {
/*  694 */       Annot.BSSetWidth(this.a, param1Double);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] getDash() throws PDFNetException {
/*  705 */       return Annot.BSGetDash(this.a);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDash(double[] param1ArrayOfdouble) throws PDFNetException {
/*  716 */       Annot.BSSetDash(this.a, param1ArrayOfdouble);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     BorderStyle(long param1Long) {
/*  726 */       this.a = param1Long;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BorderStyle getBorderStyle() throws PDFNetException {
/*  741 */     return new BorderStyle(GetBorderStyle(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderStyle(BorderStyle paramBorderStyle) throws PDFNetException {
/*  752 */     SetBorderStyle(this.a, paramBorderStyle.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getAppearance() throws PDFNetException {
/*  786 */     return Obj.__Create(GetAppearance(this.a, 0, null), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getAppearance(int paramInt) throws PDFNetException {
/*  801 */     return Obj.__Create(GetAppearance(this.a, paramInt, null), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getAppearance(int paramInt, String paramString) throws PDFNetException {
/*  822 */     return Obj.__Create(GetAppearance(this.a, paramInt, paramString), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAppearance(Obj paramObj) throws PDFNetException {
/*  835 */     SetAppearance(this.a, paramObj.__GetHandle(), 0, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAppearance(int paramInt, String paramString) throws PDFNetException {
/*  852 */     RemoveAppearance(this.a, paramInt, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAppearance(Obj paramObj, int paramInt) throws PDFNetException {
/*  868 */     SetAppearance(this.a, paramObj.__GetHandle(), paramInt, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAppearance(Obj paramObj, int paramInt, String paramString) throws PDFNetException {
/*  887 */     SetAppearance(this.a, paramObj.__GetHandle(), paramInt, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flatten(Page paramPage) throws PDFNetException {
/*  907 */     Flatten(this.a, paramPage.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActiveAppearanceState() throws PDFNetException {
/*  921 */     return GetActiveAppearanceState(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setActiveAppearanceState(String paramString) throws PDFNetException {
/*  935 */     SetActiveAppearanceState(this.a, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorPt getColorAsRGB() throws PDFNetException {
/*  953 */     return new ColorPt(GetColorAsRGB(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorPt getColorAsCMYK() throws PDFNetException {
/*  970 */     return new ColorPt(GetColorAsCMYK(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorPt getColorAsGray() throws PDFNetException {
/*  987 */     return new ColorPt(GetColorAsGray(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColorCompNum() throws PDFNetException {
/*  999 */     return GetColorCompNum(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColor(ColorPt paramColorPt) throws PDFNetException {
/* 1010 */     setColor(paramColorPt, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/* 1022 */     SetColor(this.a, paramColorPt.a, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int GetStructParent() throws PDFNetException {
/* 1038 */     return GetStructParent(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStructParent(int paramInt) throws PDFNetException {
/* 1054 */     SetStructParent(this.a, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getOptionalContent() throws PDFNetException {
/* 1072 */     return Obj.__Create(GetOptionalContent(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionalContent(Obj paramObj) throws PDFNetException {
/* 1089 */     SetOptionalContent(this.a, paramObj.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRotation() throws PDFNetException {
/* 1106 */     return GetRotation(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotation(int paramInt) throws PDFNetException {
/* 1124 */     SetRotation(this.a, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refreshAppearance() throws PDFNetException {
/* 1148 */     RefreshAppearance(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCustomData(String paramString) throws PDFNetException {
/* 1160 */     return GetCustomData(this.a, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomData(String paramString1, String paramString2) throws PDFNetException {
/* 1172 */     SetCustomData(this.a, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteCustomData(String paramString) throws PDFNetException {
/* 1183 */     DeleteCustomData(this.a, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resize(Rect paramRect) throws PDFNetException {
/* 1194 */     Resize(this.a, paramRect.a);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Annot(long paramLong, Object paramObject) {
/* 1199 */     this.a = paramLong;
/* 1200 */     this.b = paramObject;
/*      */   }
/*      */   
/*      */   public static Annot __Create(long paramLong, Object paramObject) {
/* 1204 */     if (paramLong == 0L) {
/* 1205 */       return null;
/*      */     }
/* 1207 */     return new Annot(paramLong, paramObject);
/*      */   }
/*      */   
/*      */   public long __GetHandle() {
/* 1211 */     return this.a;
/*      */   }
/*      */   
/*      */   public Object __GetRefHandle() {
/* 1215 */     return this.b;
/*      */   }
/*      */   
/*      */   static native long GetTriggerAction(long paramLong, int paramInt);
/*      */   
/*      */   static native long Create(long paramLong1, int paramInt, long paramLong2);
/*      */   
/*      */   static native boolean IsValid(long paramLong);
/*      */   
/*      */   static native int GetType(long paramLong);
/*      */   
/*      */   static native long GetRect(long paramLong);
/*      */   
/*      */   static native long GetVisibleContentBox(long paramLong);
/*      */   
/*      */   static native boolean IsMarkup(long paramLong);
/*      */   
/*      */   static native void SetRect(long paramLong1, long paramLong2);
/*      */   
/*      */   static native String GetContents(long paramLong);
/*      */   
/*      */   static native void SetContents(long paramLong, String paramString);
/*      */   
/*      */   static native long GetPage(long paramLong);
/*      */   
/*      */   static native long SetPage(long paramLong1, long paramLong2);
/*      */   
/*      */   static native boolean GetFlag(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetFlag(long paramLong, int paramInt, boolean paramBoolean);
/*      */   
/*      */   static native long GetDate(long paramLong);
/*      */   
/*      */   static native void SetDate(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetDateToNow(long paramLong);
/*      */   
/*      */   static native long GetUniqueID(long paramLong);
/*      */   
/*      */   static native void SetUniqueID(long paramLong, String paramString);
/*      */   
/*      */   static native long GetColorAsRGB(long paramLong);
/*      */   
/*      */   static native long GetColorAsCMYK(long paramLong);
/*      */   
/*      */   static native long GetColorAsGray(long paramLong);
/*      */   
/*      */   static native int GetColorCompNum(long paramLong);
/*      */   
/*      */   static native void SetColor(long paramLong1, long paramLong2, int paramInt);
/*      */   
/*      */   static native int GetStructParent(long paramLong);
/*      */   
/*      */   static native void SetStructParent(long paramLong, int paramInt);
/*      */   
/*      */   static native long GetOptionalContent(long paramLong);
/*      */   
/*      */   static native void SetOptionalContent(long paramLong1, long paramLong2);
/*      */   
/*      */   static native int GetRotation(long paramLong);
/*      */   
/*      */   static native void SetRotation(long paramLong, int paramInt);
/*      */   
/*      */   static native void RefreshAppearance(long paramLong);
/*      */   
/*      */   static native void Resize(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long BorderStyleCreate(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */   
/*      */   static native long BorderStyleCreate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble);
/*      */   
/*      */   static native void BorderStyleDestroy(long paramLong);
/*      */   
/*      */   static native int BSGetStyle(long paramLong);
/*      */   
/*      */   static native void BSSetStyle(long paramLong, int paramInt);
/*      */   
/*      */   static native int BSGetHR(long paramLong);
/*      */   
/*      */   static native void BSSetHR(long paramLong, int paramInt);
/*      */   
/*      */   static native int BSGetVR(long paramLong);
/*      */   
/*      */   static native void BSSetVR(long paramLong, int paramInt);
/*      */   
/*      */   static native double BSGetWidth(long paramLong);
/*      */   
/*      */   static native void BSSetWidth(long paramLong, double paramDouble);
/*      */   
/*      */   static native double[] BSGetDash(long paramLong);
/*      */   
/*      */   static native void BSSetDash(long paramLong, double[] paramArrayOfdouble);
/*      */   
/*      */   static native long GetBorderStyle(long paramLong);
/*      */   
/*      */   static native void SetBorderStyle(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetAppearance(long paramLong, int paramInt, String paramString);
/*      */   
/*      */   static native void SetAppearance(long paramLong1, long paramLong2, int paramInt, String paramString);
/*      */   
/*      */   static native void RemoveAppearance(long paramLong, int paramInt, String paramString);
/*      */   
/*      */   static native void Flatten(long paramLong1, long paramLong2);
/*      */   
/*      */   static native String GetActiveAppearanceState(long paramLong);
/*      */   
/*      */   static native void SetActiveAppearanceState(long paramLong, String paramString);
/*      */   
/*      */   static native String GetCustomData(long paramLong, String paramString);
/*      */   
/*      */   static native void SetCustomData(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native void DeleteCustomData(long paramLong, String paramString);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Annot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */