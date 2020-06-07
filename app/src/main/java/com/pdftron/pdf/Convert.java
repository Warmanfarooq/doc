/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import com.pdftron.sdf.ObjSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Convert
/*      */ {
/*      */   public static final int e_very_strict = 0;
/*      */   public static final int e_strict = 1;
/*      */   public static final int e_default = 2;
/*      */   public static final int e_keep_most = 3;
/*      */   public static final int e_keep_all = 4;
/*      */   public static final int e_off = 0;
/*      */   public static final int e_simple = 1;
/*      */   public static final int e_fast = 2;
/*      */   public static final int e_high_quality = 3;
/*      */   
/*      */   public static class XPSOutputCommonOptions
/*      */   {
/*      */     public static final int e_op_off = 0;
/*      */     public static final int e_op_on = 1;
/*      */     public static final int e_op_pdfx_on = 2;
/*      */     Obj a;
/*      */     private ObjSet b;
/*      */     
/*      */     public XPSOutputCommonOptions() {
/*      */       try {
/*  110 */         this.b = new ObjSet();
/*  111 */         this.a = this.b.createDict();
/*      */         return;
/*  113 */       } catch (PDFNetException pDFNetException) {
/*      */         
/*  115 */         System.err.println("Error Occurred when creating XPSOutputCommonOptions.");
/*      */         return;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPrintMode(boolean param1Boolean) throws PDFNetException {
/*  125 */       this.a.putBool("PRINTMODE", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDPI(int param1Int) throws PDFNetException {
/*  135 */       this.a.putNumber("DPI", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setRenderPages(boolean param1Boolean) throws PDFNetException {
/*  145 */       this.a.putBool("RENDER", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setThickenLines(boolean param1Boolean) throws PDFNetException {
/*  155 */       this.a.putBool("THICKENLINES", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void generateURLLinks(boolean param1Boolean) throws PDFNetException {
/*  166 */       this.a.putBool("URL_LINKS", param1Boolean);
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
/*      */     public void setOverprint(int param1Int) throws PDFNetException {
/*  186 */       switch (param1Int) {
/*      */         
/*      */         case 0:
/*  189 */           this.a.putName("OVERPRINT_MODE", "OFF");
/*      */           return;
/*      */         case 1:
/*  192 */           this.a.putName("OVERPRINT_MODE", "ON");
/*      */           return;
/*      */         case 2:
/*  195 */           this.a.putName("OVERPRINT_MODE", "PDFX");
/*      */           break;
/*      */       } 
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
/*      */ 
/*      */   
/*      */   public static class XPSOutputOptions
/*      */     extends XPSOutputCommonOptions
/*      */   {
/*      */     public void setOpenXps(boolean param1Boolean) throws PDFNetException {
/*  217 */       this.a.putBool("OPENXPS", param1Boolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XODOutputOptions
/*      */     extends XPSOutputCommonOptions
/*      */   {
/*      */     public static final int e_internal_xfdf = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int e_external_xfdf = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int e_flatten = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setOutputThumbnails(boolean param1Boolean) throws PDFNetException {
/*  245 */       this.a.putBool("NOTHUMBS", !param1Boolean);
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
/*      */     public void setThumbnailSize(int param1Int) throws PDFNetException {
/*  258 */       setThumbnailSize(param1Int, param1Int);
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
/*      */     public void setThumbnailSize(int param1Int1, int param1Int2) throws PDFNetException {
/*  272 */       this.a.putNumber("THUMB_SIZE", param1Int1);
/*  273 */       this.a.putNumber("LARGE_THUMB_SIZE", param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setElementLimit(int param1Int) throws PDFNetException {
/*  284 */       this.a.putNumber("ELEMENTLIMIT", param1Int);
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
/*      */     public void setOpacityMaskWorkaround(boolean param1Boolean) throws PDFNetException {
/*  296 */       this.a.putBool("MASKRENDER", param1Boolean);
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
/*      */     public void setMaximumImagePixels(int param1Int) throws PDFNetException {
/*  312 */       this.a.putNumber("MAX_IMAGE_PIXELS", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setFlattenThreshold(int param1Int) throws PDFNetException {
/*  323 */       Convert.a(this.a, param1Int);
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
/*      */     public void setFlattenContent(int param1Int) throws PDFNetException {
/*  335 */       Convert.b(this.a, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreferJPG(boolean param1Boolean) throws PDFNetException {
/*  346 */       this.a.putBool("PREFER_JPEG", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setJPGQuality(int param1Int) throws PDFNetException {
/*  356 */       this.a.putNumber("JPEG_QUALITY", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSilverlightTextWorkaround(boolean param1Boolean) throws PDFNetException {
/*  367 */       this.a.putBool("REMOVE_ROTATED_TEXT", param1Boolean);
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
/*      */     public void setAnnotationOutput(int param1Int) throws PDFNetException {
/*  382 */       switch (param1Int) {
/*      */         
/*      */         case 0:
/*  385 */           this.a.putName("ANNOTATION_OUTPUT", "INTERNAL");
/*      */           return;
/*      */         case 1:
/*  388 */           this.a.putName("ANNOTATION_OUTPUT", "EXTERNAL");
/*      */           return;
/*      */         case 2:
/*  391 */           this.a.putName("ANNOTATION_OUTPUT", "FLATTEN");
/*      */           break;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExternalParts(boolean param1Boolean) throws PDFNetException {
/*  404 */       this.a.putBool("EXTERNAL_PARTS", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEncryptPassword(String param1String) throws PDFNetException {
/*  414 */       this.a.putName("ENCRYPT_PASSWORD", param1String);
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
/*      */     public void UseSilverlightFlashCompatible(boolean param1Boolean) throws PDFNetException {
/*  426 */       this.a.putBool("COMPATIBLE_XOD", param1Boolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HTMLOutputOptions
/*      */   {
/*      */     Obj a;
/*      */ 
/*      */     
/*      */     private ObjSet b;
/*      */ 
/*      */     
/*      */     public HTMLOutputOptions() {
/*      */       try {
/*  442 */         this.b = new ObjSet();
/*  443 */         this.a = this.b.createDict();
/*      */         return;
/*  445 */       } catch (PDFNetException pDFNetException) {
/*      */         
/*  447 */         System.err.println("Error Occurred when creating HTMLOutputOptions.");
/*      */         return;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreferJPG(boolean param1Boolean) throws PDFNetException {
/*  458 */       this.a.putBool("PREFER_JPEG", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setJPGQuality(int param1Int) throws PDFNetException {
/*  468 */       this.a.putNumber("JPEG_QUALITY", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDPI(int param1Int) throws PDFNetException {
/*  479 */       this.a.putNumber("DPI", param1Int);
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
/*      */     public void setMaximumImagePixels(int param1Int) throws PDFNetException {
/*  495 */       this.a.putNumber("MAX_IMAGE_PIXELS", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setReflow(boolean param1Boolean) throws PDFNetException {
/*  505 */       this.a.putBool("REFLOW", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setScale(double param1Double) throws PDFNetException {
/*  515 */       this.a.putNumber("SCALE", param1Double);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExternalLinks(boolean param1Boolean) throws PDFNetException {
/*  525 */       this.a.putBool("EXTERNAL_LINKS", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setInternalLinks(boolean param1Boolean) throws PDFNetException {
/*  535 */       this.a.putBool("INTERNAL_LINKS", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSimplifyText(boolean param1Boolean) throws PDFNetException {
/*  545 */       this.a.putBool("SIMPLIFY_TEXT", param1Boolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EPUBOutputOptions
/*      */   {
/*      */     Obj a;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ObjSet b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EPUBOutputOptions() {
/*      */       try {
/*  574 */         this.b = new ObjSet();
/*  575 */         this.a = this.b.createDict();
/*      */         return;
/*  577 */       } catch (PDFNetException pDFNetException) {
/*      */         
/*  579 */         System.err.println("Error Occurred when creating EPUBOutputOptions.");
/*      */         return;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setExpanded(boolean param1Boolean) throws PDFNetException {
/*  590 */       this.a.putBool("EPUB_EXPANDED", param1Boolean);
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
/*      */     public void setReuseCover(boolean param1Boolean) throws PDFNetException {
/*  602 */       this.a.putBool("EPUB_REUSE_COVER", param1Boolean);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TiffOutputOptions
/*      */   {
/*      */     public static final int e_media = 0;
/*      */     public static final int e_crop = 1;
/*      */     public static final int e_bleed = 2;
/*      */     public static final int e_trim = 3;
/*      */     public static final int e_art = 4;
/*      */     public static final int e_op_off = 0;
/*      */     public static final int e_op_on = 1;
/*      */     public static final int e_op_pdfx_on = 2;
/*      */     Obj a;
/*      */     private ObjSet b;
/*      */     
/*      */     public TiffOutputOptions() {
/*      */       try {
/*  621 */         this.b = new ObjSet();
/*  622 */         this.a = this.b.createDict();
/*      */         return;
/*  624 */       } catch (PDFNetException pDFNetException) {
/*      */         
/*  626 */         System.err.println("Error Occurred when creating TiffOutputOptions.");
/*      */         return;
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBox(int param1Int) throws PDFNetException {
/*  685 */       switch (param1Int) {
/*      */         
/*      */         case 0:
/*  688 */           this.a.putName("BOX", "media");
/*      */           return;
/*      */         case 1:
/*  691 */           this.a.putName("BOX", "crop");
/*      */           return;
/*      */         case 2:
/*  694 */           this.a.putName("BOX", "bleed");
/*      */           return;
/*      */         case 3:
/*  697 */           this.a.putName("BOX", "trim");
/*      */           return;
/*      */         case 4:
/*  700 */           this.a.putName("BOX", "art");
/*      */           break;
/*      */       } 
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
/*      */     public void setRotate(int param1Int) throws PDFNetException {
/*  716 */       switch (param1Int) {
/*      */         
/*      */         case 0:
/*  719 */           this.a.putName("ROTATE", "0");
/*      */           return;
/*      */         case 90:
/*  722 */           this.a.putName("ROTATE", "90");
/*      */           return;
/*      */         case 180:
/*  725 */           this.a.putName("ROTATE", "180");
/*      */           return;
/*      */         case 270:
/*  728 */           this.a.putName("ROTATE", "270");
/*      */           break;
/*      */       } 
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
/*      */     public void setRotate(double param1Double1, double param1Double2, double param1Double3, double param1Double4) throws PDFNetException {
/*  746 */       this.a.putNumber("CLIP_X1", param1Double1);
/*  747 */       this.a.putNumber("CLIP_Y1", param1Double2);
/*  748 */       this.a.putNumber("CLIP_X2", param1Double3);
/*  749 */       this.a.putNumber("CLIP_Y2", param1Double4);
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
/*      */     public void setPages(String param1String) throws PDFNetException {
/*  761 */       this.a.putName("PAGES", param1String);
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
/*      */     public void setOverprint(int param1Int) throws PDFNetException {
/*  779 */       switch (param1Int) {
/*      */         
/*      */         case 0:
/*  782 */           this.a.putName("OVERPRINT", "off");
/*      */           return;
/*      */         case 1:
/*  785 */           this.a.putName("OVERPRINT", "on");
/*      */           return;
/*      */         case 2:
/*  788 */           this.a.putName("OVERPRINT", "pdfx");
/*      */           break;
/*      */       } 
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
/*      */     public void setCMYK(boolean param1Boolean) throws PDFNetException {
/*  803 */       this.a.putBool("CMYK", param1Boolean);
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
/*      */     public void setDither(boolean param1Boolean) throws PDFNetException {
/*  817 */       this.a.putBool("DITHER", param1Boolean);
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
/*      */     public void setGray(boolean param1Boolean) throws PDFNetException {
/*  831 */       this.a.putBool("GRAY", param1Boolean);
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
/*      */     public void setMono(boolean param1Boolean) throws PDFNetException {
/*  848 */       this.a.putBool("MONO", param1Boolean);
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
/*      */     public void setAnnots(boolean param1Boolean) throws PDFNetException {
/*  862 */       this.a.putBool("ANNOTS", param1Boolean);
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
/*      */     public void setSmooth(boolean param1Boolean) throws PDFNetException {
/*  874 */       this.a.putBool("SMOOTH", param1Boolean);
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
/*      */     public void setPrintmode(boolean param1Boolean) throws PDFNetException {
/*  890 */       this.a.putBool("PRINTMODE", param1Boolean);
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
/*      */     public void setTransparentPage(boolean param1Boolean) throws PDFNetException {
/*  909 */       this.a.putBool("TRANSPARENT_PAGE", param1Boolean);
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
/*      */     public void setPalettized(boolean param1Boolean) throws PDFNetException {
/*  922 */       this.a.putBool("PALETTIZED", param1Boolean);
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
/*      */     public void setDPI(double param1Double) throws PDFNetException {
/*  936 */       this.a.putNumber("DPI", param1Double);
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
/*      */     public void setGamma(double param1Double) throws PDFNetException {
/*  955 */       this.a.putNumber("GAMMA", param1Double);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setHRes(int param1Int) throws PDFNetException {
/*  966 */       this.a.putNumber("HRES", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setVRes(int param1Int) throws PDFNetException {
/*  977 */       this.a.putNumber("VRES", param1Int);
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
/*      */ 
/*      */   
/*      */   public static void fromXps(Doc paramDoc, String paramString) throws PDFNetException {
/*  994 */     FromXps(paramDoc.__GetHandle(), paramString);
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
/*      */   public static void fromXps(Doc paramDoc, byte[] paramArrayOfbyte) throws PDFNetException {
/* 1008 */     FromXpsBuf(paramDoc.__GetHandle(), paramArrayOfbyte);
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
/*      */   public static void fromEmf(Doc paramDoc, String paramString) throws PDFNetException {
/* 1023 */     FromEmf(paramDoc.__GetHandle(), paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void fromText(Doc paramDoc, String paramString, Obj paramObj) throws PDFNetException {
/* 1028 */     FromText(paramDoc.__GetHandle(), paramString, paramObj.__GetHandle());
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
/*      */   public static void toEmf(Doc paramDoc, String paramString) throws PDFNetException {
/* 1042 */     DocToEmf(paramDoc.__GetHandle(), paramString);
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
/*      */   public static void toEmf(Page paramPage, String paramString) throws PDFNetException {
/* 1056 */     PageToEmf(paramPage.a, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SVGOutputOptions
/*      */   {
/*      */     public static final int e_op_off = 0;
/*      */     
/*      */     public static final int e_op_on = 1;
/*      */     public static final int e_op_pdfx_on = 2;
/*      */     Obj a;
/*      */     private ObjSet b;
/*      */     
/*      */     public SVGOutputOptions() {
/*      */       try {
/* 1071 */         this.b = new ObjSet();
/* 1072 */         this.a = this.b.createDict();
/*      */         return;
/* 1074 */       } catch (PDFNetException pDFNetException) {
/*      */         
/* 1076 */         System.err.println("Error occurred when creating SVGOutputOptions.");
/*      */         return;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEmbedImages(boolean param1Boolean) throws PDFNetException {
/* 1087 */       this.a.putBool("EMBEDIMAGES", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setNoFonts(boolean param1Boolean) throws PDFNetException {
/* 1097 */       this.a.putBool("NOFONTS", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSvgFonts(boolean param1Boolean) throws PDFNetException {
/* 1108 */       this.a.putBool("SVGFONTS", param1Boolean);
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
/*      */     public void setEmbedFonts(boolean param1Boolean) throws PDFNetException {
/* 1120 */       this.a.putBool("EMBEDFONTS", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setNoUnicode(boolean param1Boolean) throws PDFNetException {
/* 1130 */       this.a.putBool("NOUNICODE", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setIndividualCharPlacement(boolean param1Boolean) throws PDFNetException {
/* 1140 */       this.a.putBool("INDIVIDUALCHARPLACEMENT", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setRemoveCharPlacement(boolean param1Boolean) throws PDFNetException {
/* 1150 */       this.a.putBool("REMOVECHARPLACEMENT", param1Boolean);
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
/*      */     public void setFlattenContent(int param1Int) throws PDFNetException {
/* 1162 */       Convert.b(this.a, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setFlattenThreshold(int param1Int) throws PDFNetException {
/* 1173 */       Convert.a(this.a, param1Int);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCompress(boolean param1Boolean) throws PDFNetException {
/* 1210 */       this.a.putBool("SVGZ", param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setOutputThumbnails(boolean param1Boolean) throws PDFNetException {
/* 1220 */       this.a.putBool("NOTHUMBS", !param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setThumbnailSize(int param1Int) throws PDFNetException {
/* 1231 */       this.a.putNumber("THUMB_SIZE", param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCreateXmlWrapper(boolean param1Boolean) throws PDFNetException {
/* 1241 */       this.a.putBool("NOXMLDOC", !param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDtd(boolean param1Boolean) throws PDFNetException {
/* 1251 */       this.a.putBool("OMITDTD", !param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAnnots(boolean param1Boolean) throws PDFNetException {
/* 1261 */       this.a.putBool("NOANNOTS", !param1Boolean);
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
/*      */     public void setOverprint(int param1Int) throws PDFNetException {
/* 1280 */       switch (param1Int) {
/*      */         
/*      */         case 0:
/* 1283 */           this.a.putName("OVERPRINT_MODE", "OFF");
/*      */           return;
/*      */         case 1:
/* 1286 */           this.a.putName("OVERPRINT_MODE", "ON");
/*      */           return;
/*      */         case 2:
/* 1289 */           this.a.putName("OVERPRINT_MODE", "PDFX");
/*      */           break;
/*      */       } 
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
/*      */   
/*      */   public static void toSvg(Doc paramDoc, String paramString) throws PDFNetException {
/* 1307 */     DocToSvg(paramDoc.__GetHandle(), paramString);
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
/*      */   public static void toSvg(Doc paramDoc, String paramString, SVGOutputOptions paramSVGOutputOptions) throws PDFNetException {
/* 1320 */     DocToSvgWithOptions(paramDoc.__GetHandle(), paramString, paramSVGOutputOptions.a.__GetHandle());
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
/*      */   public static void toSvg(Page paramPage, String paramString) throws PDFNetException {
/* 1332 */     PageToSvg(paramPage.a, paramString);
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
/*      */   public static void toSvg(Page paramPage, String paramString, SVGOutputOptions paramSVGOutputOptions) throws PDFNetException {
/* 1345 */     PageToSvgWithOptions(paramPage.a, paramString, paramSVGOutputOptions.a.__GetHandle());
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
/*      */   public static void toXps(Doc paramDoc, String paramString) throws PDFNetException {
/* 1357 */     DocToXps(paramDoc.__GetHandle(), paramString, 0L);
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
/*      */   public static void toXps(Doc paramDoc, String paramString, XPSOutputOptions paramXPSOutputOptions) throws PDFNetException {
/* 1371 */     DocToXps(paramDoc.__GetHandle(), paramString, paramXPSOutputOptions.a.__GetHandle());
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
/*      */   public static void toXps(String paramString1, String paramString2) throws PDFNetException {
/* 1393 */     FileToXps(paramString1, paramString2, 0L);
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
/*      */   public static void toXps(String paramString1, String paramString2, XPSOutputOptions paramXPSOutputOptions) throws PDFNetException {
/* 1417 */     FileToXps(paramString1, paramString2, paramXPSOutputOptions.a.__GetHandle());
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
/*      */   public static void toXod(String paramString1, String paramString2) throws PDFNetException {
/* 1439 */     FileToXod(paramString1, paramString2, 0L);
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
/*      */   public static void toXod(String paramString1, String paramString2, XODOutputOptions paramXODOutputOptions) throws PDFNetException {
/* 1463 */     FileToXod(paramString1, paramString2, paramXODOutputOptions.a.__GetHandle());
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
/*      */   public static void toXod(Doc paramDoc, String paramString) throws PDFNetException {
/* 1475 */     DocToXod(paramDoc.__GetHandle(), paramString, 0L);
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
/*      */   public static void toXod(Doc paramDoc, String paramString, XODOutputOptions paramXODOutputOptions) throws PDFNetException {
/* 1489 */     DocToXod(paramDoc.__GetHandle(), paramString, paramXODOutputOptions.a.__GetHandle());
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
/*      */   public static Filter toXod(Doc paramDoc) throws PDFNetException {
/* 1502 */     return Filter.__Create(DocToXodStream(paramDoc.__GetHandle(), 0L), null);
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
/*      */   public static Filter toXod(Doc paramDoc, XODOutputOptions paramXODOutputOptions) throws PDFNetException {
/* 1516 */     return Filter.__Create(DocToXodStream(paramDoc.__GetHandle(), paramXODOutputOptions.a.__GetHandle()), null);
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
/*      */   public static Filter toXod(String paramString) throws PDFNetException {
/* 1539 */     return Filter.__Create(FileToXodStream(paramString, 0L), null);
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
/*      */   public static Filter toXod(String paramString, XODOutputOptions paramXODOutputOptions) throws PDFNetException {
/* 1563 */     return Filter.__Create(FileToXodStream(paramString, paramXODOutputOptions.a.__GetHandle()), null);
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
/*      */   public static void toHtml(String paramString1, String paramString2) throws PDFNetException {
/* 1582 */     FileToHtml(paramString1, paramString2, 0L);
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
/*      */   public static void toHtml(String paramString1, String paramString2, HTMLOutputOptions paramHTMLOutputOptions) throws PDFNetException {
/* 1605 */     FileToHtml(paramString1, paramString2, paramHTMLOutputOptions.a.__GetHandle());
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
/*      */   public static void toHtml(Doc paramDoc, String paramString) throws PDFNetException {
/* 1619 */     DocToHtml(paramDoc.__GetHandle(), paramString, 0L);
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
/*      */   public static void toHtml(Doc paramDoc, String paramString, HTMLOutputOptions paramHTMLOutputOptions) throws PDFNetException {
/* 1637 */     DocToHtml(paramDoc.__GetHandle(), paramString, paramHTMLOutputOptions.a.__GetHandle());
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
/*      */   public static void toEpub(String paramString1, String paramString2) throws PDFNetException {
/* 1657 */     FileToEpub(paramString1, paramString2, 0L, 0L);
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
/*      */   public static void toEpub(String paramString1, String paramString2, HTMLOutputOptions paramHTMLOutputOptions) throws PDFNetException {
/* 1681 */     FileToEpub(paramString1, paramString2, paramHTMLOutputOptions.a.__GetHandle(), 0L);
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
/*      */   public static void toEpub(String paramString1, String paramString2, EPUBOutputOptions paramEPUBOutputOptions) throws PDFNetException {
/* 1705 */     FileToEpub(paramString1, paramString2, 0L, paramEPUBOutputOptions.a.__GetHandle());
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
/*      */   public static void toEpub(String paramString1, String paramString2, HTMLOutputOptions paramHTMLOutputOptions, EPUBOutputOptions paramEPUBOutputOptions) throws PDFNetException {
/* 1733 */     FileToEpub(paramString1, paramString2, paramHTMLOutputOptions.a.__GetHandle(), paramEPUBOutputOptions.a.__GetHandle());
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
/*      */   public static void toEpub(Doc paramDoc, String paramString) throws PDFNetException {
/* 1747 */     DocToEpub(paramDoc.__GetHandle(), paramString, 0L, 0L);
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
/*      */   public static void toEpub(Doc paramDoc, String paramString, HTMLOutputOptions paramHTMLOutputOptions) throws PDFNetException {
/* 1765 */     DocToEpub(paramDoc.__GetHandle(), paramString, paramHTMLOutputOptions.a.__GetHandle(), 0L);
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
/*      */   public static void toEpub(Doc paramDoc, String paramString, EPUBOutputOptions paramEPUBOutputOptions) throws PDFNetException {
/* 1783 */     DocToEpub(paramDoc.__GetHandle(), paramString, 0L, paramEPUBOutputOptions.a.__GetHandle());
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
/*      */   public static void toEpub(Doc paramDoc, String paramString, HTMLOutputOptions paramHTMLOutputOptions, EPUBOutputOptions paramEPUBOutputOptions) throws PDFNetException {
/* 1805 */     DocToEpub(paramDoc.__GetHandle(), paramString, paramHTMLOutputOptions.a.__GetHandle(), paramEPUBOutputOptions.a.__GetHandle());
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
/*      */   public static void toTiff(String paramString1, String paramString2) throws PDFNetException {
/* 1820 */     FileToTiff(paramString1, paramString2, 0L);
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
/*      */   public static void toTiff(String paramString1, String paramString2, TiffOutputOptions paramTiffOutputOptions) throws PDFNetException {
/* 1839 */     FileToTiff(paramString1, paramString2, paramTiffOutputOptions.a.__GetHandle());
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
/*      */   public static void toTiff(Doc paramDoc, String paramString) throws PDFNetException {
/* 1853 */     DocToTiff(paramDoc.__GetHandle(), paramString, 0L);
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
/*      */   public static void toTiff(Doc paramDoc, String paramString, TiffOutputOptions paramTiffOutputOptions) throws PDFNetException {
/* 1871 */     DocToTiff(paramDoc.__GetHandle(), paramString, paramTiffOutputOptions.a.__GetHandle());
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
/*      */   public static DocumentConversion universalConversion(String paramString, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 1900 */     long l = 0L;
/* 1901 */     if (paramConversionOptions != null)
/*      */     {
/* 1903 */       l = paramConversionOptions.a();
/*      */     }
/* 1905 */     return new DocumentConversion(UniversalConversion(paramString, l));
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
/*      */   public static DocumentConversion universalConversion(Filter paramFilter, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 1933 */     long l = 0L;
/* 1934 */     if (paramConversionOptions != null)
/*      */     {
/* 1936 */       l = paramConversionOptions.a();
/*      */     }
/* 1938 */     return new DocumentConversion(UniversalConversionWithFilter(paramFilter.__GetHandle(), l));
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
/*      */   public static DocumentConversion appendUniversalConversion(DocumentConversion paramDocumentConversion, String paramString, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 1968 */     long l = 0L;
/* 1969 */     if (paramConversionOptions != null)
/*      */     {
/* 1971 */       l = paramConversionOptions.a();
/*      */     }
/* 1973 */     return new DocumentConversion(AppendUniversalConversion(paramDocumentConversion.__GetHandle(), paramString, l));
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
/*      */   public static DocumentConversion appendUniversalConversion(DocumentConversion paramDocumentConversion, Filter paramFilter, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2005 */     long l = 0L;
/* 2006 */     if (paramConversionOptions != null)
/*      */     {
/* 2008 */       l = paramConversionOptions.a();
/*      */     }
/* 2010 */     return new DocumentConversion(AppendUniversalConversionWithFilter(paramDocumentConversion.__GetHandle(), paramFilter.__GetHandle(), l));
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
/*      */   public static void wordToPdf(Doc paramDoc, String paramString, WordToPDFOptions paramWordToPDFOptions) throws PDFNetException {
/* 2035 */     long l = 0L;
/* 2036 */     if (paramWordToPDFOptions != null)
/*      */     {
/* 2038 */       l = paramWordToPDFOptions.a();
/*      */     }
/* 2040 */     WordToPdf(paramDoc.__GetHandle(), paramString, l);
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
/*      */   public static DocumentConversion wordToPdfConversion(Doc paramDoc, String paramString, WordToPDFOptions paramWordToPDFOptions) throws PDFNetException {
/* 2073 */     long l = 0L;
/* 2074 */     if (paramWordToPDFOptions != null)
/*      */     {
/* 2076 */       l = paramWordToPDFOptions.a();
/*      */     }
/* 2078 */     return new DocumentConversion(WordToPdfConversion(paramDoc.__GetHandle(), paramString, l));
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
/*      */   public static void wordToPdf(Doc paramDoc, Filter paramFilter, WordToPDFOptions paramWordToPDFOptions) throws PDFNetException {
/* 2103 */     long l = 0L;
/* 2104 */     if (paramWordToPDFOptions != null)
/*      */     {
/* 2106 */       l = paramWordToPDFOptions.a();
/*      */     }
/* 2108 */     WordToPdfWithFilter(paramDoc.__GetHandle(), paramFilter.__GetHandle(), l);
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
/*      */   public static DocumentConversion wordToPdfConversion(Doc paramDoc, Filter paramFilter, WordToPDFOptions paramWordToPDFOptions) throws PDFNetException {
/* 2141 */     long l = 0L;
/* 2142 */     if (paramWordToPDFOptions != null)
/*      */     {
/* 2144 */       l = paramWordToPDFOptions.a();
/*      */     }
/* 2146 */     return new DocumentConversion(WordToPdfConversionWithFilter(paramDoc.__GetHandle(), paramFilter.__GetHandle(), l));
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
/*      */   public static void officeToPdf(Doc paramDoc, String paramString, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2171 */     long l = 0L;
/* 2172 */     if (paramConversionOptions != null)
/*      */     {
/* 2174 */       l = paramConversionOptions.a();
/*      */     }
/* 2176 */     OfficeToPdf(paramDoc.__GetHandle(), paramString, l);
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
/*      */   public static DocumentConversion streamingPdfConversion(String paramString, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2205 */     long l = 0L;
/* 2206 */     if (paramConversionOptions != null)
/*      */     {
/* 2208 */       l = paramConversionOptions.a();
/*      */     }
/* 2210 */     return new DocumentConversion(UniversalConversion(paramString, l));
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
/*      */   public static void officeToPdf(Doc paramDoc, Filter paramFilter, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2235 */     long l = 0L;
/* 2236 */     if (paramConversionOptions != null)
/*      */     {
/* 2238 */       l = paramConversionOptions.a();
/*      */     }
/* 2240 */     OfficeToPdfWithFilter(paramDoc.__GetHandle(), paramFilter.__GetHandle(), l);
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
/*      */   public static DocumentConversion streamingPdfConversion(Filter paramFilter, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2269 */     long l = 0L;
/* 2270 */     if (paramConversionOptions != null)
/*      */     {
/* 2272 */       l = paramConversionOptions.a();
/*      */     }
/* 2274 */     return new DocumentConversion(UniversalConversionWithFilter(paramFilter.__GetHandle(), l));
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
/*      */   public static DocumentConversion streamingPdfConversion(Doc paramDoc, String paramString, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2305 */     long l = 0L;
/* 2306 */     if (paramConversionOptions != null)
/*      */     {
/* 2308 */       l = paramConversionOptions.a();
/*      */     }
/* 2310 */     return new DocumentConversion(UniversalConversionWithPdf(paramDoc.__GetHandle(), paramString, l));
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
/*      */   public static DocumentConversion streamingPdfConversion(Doc paramDoc, Filter paramFilter, ConversionOptions paramConversionOptions) throws PDFNetException {
/* 2342 */     long l = 0L;
/* 2343 */     if (paramConversionOptions != null)
/*      */     {
/* 2345 */       l = paramConversionOptions.a();
/*      */     }
/* 2347 */     return new DocumentConversion(UniversalConversionWithPdfAndFilter(paramDoc.__GetHandle(), paramFilter.__GetHandle(), l));
/*      */   }
/*      */ 
/*      */   
/*      */   public static BlackBoxContext createBlackBoxContext(String paramString) throws PDFNetException {
/* 2352 */     return new BlackBoxContext(CreateBlackBoxContext(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BlackBoxContext createBlackBoxContextConvert(String paramString1, String paramString2) throws PDFNetException {
/* 2358 */     return new BlackBoxContext(CreateBlackBoxContextConvert(paramString1, paramString2));
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
/*      */   public static void toPdf(Doc paramDoc, String paramString) throws PDFNetException {
/* 2382 */     FileToPdf(paramDoc.__GetHandle(), paramString);
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
/*      */   public static void fromCAD(Doc paramDoc, String paramString, CADConvertOptions paramCADConvertOptions) throws PDFNetException {
/* 2399 */     long l = 0L;
/* 2400 */     if (paramCADConvertOptions != null)
/*      */     {
/* 2402 */       l = paramCADConvertOptions.a();
/*      */     }
/* 2404 */     FromCAD(paramDoc.__GetHandle(), paramString, l);
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
/*      */   public static void fromTiff(Doc paramDoc, Filter paramFilter) throws PDFNetException {
/* 2416 */     FromTiff(paramDoc.__GetHandle(), paramFilter.__GetHandle());
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
/*      */   public static boolean requiresPrinter(String paramString) throws PDFNetException {
/* 2433 */     return RequiresPrinter(paramString);
/*      */   }
/*      */   
/*      */   static native void FromXps(long paramLong, String paramString);
/*      */   
/*      */   static native void FromXpsBuf(long paramLong, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native void FromEmf(long paramLong, String paramString);
/*      */   
/*      */   static native void FromText(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void DocToEmf(long paramLong, String paramString);
/*      */   
/*      */   static native void PageToEmf(long paramLong, String paramString);
/*      */   
/*      */   static native void DocToSvg(long paramLong, String paramString);
/*      */   
/*      */   static native void DocToSvgWithOptions(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void PageToSvg(long paramLong, String paramString);
/*      */   
/*      */   static native void PageToSvgWithOptions(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void DocToXps(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void FileToXps(String paramString1, String paramString2, long paramLong);
/*      */   
/*      */   static native void FileToXod(String paramString1, String paramString2, long paramLong);
/*      */   
/*      */   static native void DocToXod(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long FileToXodStream(String paramString, long paramLong);
/*      */   
/*      */   static native long DocToXodStream(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void FileToHtml(String paramString1, String paramString2, long paramLong);
/*      */   
/*      */   static native void DocToHtml(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void FileToEpub(String paramString1, String paramString2, long paramLong1, long paramLong2);
/*      */   
/*      */   static native void DocToEpub(long paramLong1, String paramString, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void FileToTiff(String paramString1, String paramString2, long paramLong);
/*      */   
/*      */   static native void DocToTiff(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long UniversalConversion(String paramString, long paramLong);
/*      */   
/*      */   static native long UniversalConversionWithFilter(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long UniversalConversionWithPdf(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long UniversalConversionWithPdfAndFilter(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long AppendUniversalConversion(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long AppendUniversalConversionWithFilter(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void OfficeToPdf(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void WordToPdf(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long WordToPdfConversion(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void OfficeToPdfWithFilter(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void WordToPdfWithFilter(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long WordToPdfConversionWithFilter(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long CreateBlackBoxContext(String paramString);
/*      */   
/*      */   static native long CreateBlackBoxContextConvert(String paramString1, String paramString2);
/*      */   
/*      */   static native void FileToPdf(long paramLong, String paramString);
/*      */   
/*      */   static native void FromCAD(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void FromTiff(long paramLong1, long paramLong2);
/*      */   
/*      */   static native boolean RequiresPrinter(String paramString);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Convert.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */