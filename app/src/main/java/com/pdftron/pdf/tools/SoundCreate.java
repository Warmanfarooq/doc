/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.PointF;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.filters.MappedFile;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Sound;
/*     */ import com.pdftron.pdf.dialog.SoundDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnSoundRecordedListener;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class SoundCreate
/*     */   extends SimpleTapShapeCreate
/*     */ {
/*     */   public static final String SOUND_ICON = "sound";
/*     */   public static final int SAMPLE_RATE = 22050;
/*     */   private static final int BITS_PER_SAMPLE = 16;
/*     */   private static final int NUM_CHANNELS = 1;
/*     */   private String mOutputFilePath;
/*     */   
/*     */   public SoundCreate(@NonNull PDFViewCtrl ctrl) {
/*  38 */     super(ctrl);
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  43 */     return ToolManager.ToolMode.SOUND_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  48 */     return 17;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnnotation() {
/*  54 */     if (this.mPt2 == null) {
/*  55 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("target point is not specified."));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  60 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     setNextToolModeHelper();
/*  65 */     setCurrentDefaultToolModeHelper(getToolMode());
/*     */     
/*  67 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*  68 */     FragmentActivity activity = toolManager.getCurrentActivity();
/*  69 */     if (activity == null) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     SoundDialogFragment fragment = SoundDialogFragment.newInstance(this.mPt2, this.mDownPageNum);
/*  74 */     fragment.setStyle(0, R.style.CustomAppTheme);
/*  75 */     fragment.show(activity.getSupportFragmentManager(), SoundDialogFragment.TAG);
/*  76 */     fragment.setOnSoundRecordedListener(new OnSoundRecordedListener()
/*     */         {
/*     */           public void onSoundRecorded(PointF targetPoint, int pageNum, String outputFile) {
/*  79 */             SoundCreate.this.createSound(targetPoint, pageNum, outputFile);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void createSound(PointF targetPoint, int pageNum, String outputPath) {
/*  85 */     this.mOutputFilePath = outputPath;
/*     */     
/*  87 */     createAnnotation(targetPoint, pageNum);
/*     */     
/*  89 */     if (outputPath != null) {
/*  90 */       File file = new File(outputPath);
/*  91 */       if (file.exists() && file.isFile())
/*     */       {
/*  93 */         file.delete();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 100 */     if (Utils.isNullOrEmpty(this.mOutputFilePath)) {
/* 101 */       return null;
/*     */     }
/* 103 */     MappedFile mappedFile = new MappedFile(this.mOutputFilePath);
/* 104 */     Sound sound = Sound.createWithData((Doc)this.mPdfViewCtrl.getDoc(), bbox, (Filter)mappedFile, 16, 22050, 1);
/*     */     
/* 106 */     sound.setIcon(0);
/* 107 */     sound.getSoundStream().putName("E", "Signed");
/*     */     
/* 109 */     sound.refreshAppearance();
/*     */     
/* 111 */     return (Annot)sound;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\SoundCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */