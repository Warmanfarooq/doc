/*      */ package com.pdftron.pdf.utils;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.res.Resources;
/*      */ import android.graphics.Bitmap;
/*      */ import android.graphics.Canvas;
/*      */ import android.graphics.RectF;
/*      */ import android.graphics.pdf.PdfDocument;
/*      */ import android.os.Bundle;
/*      */ import android.text.Html;
/*      */ import android.text.Spanned;
/*      */ import android.view.View;
/*      */ import android.widget.EditText;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.ColorSpace;
/*      */ import com.pdftron.pdf.Date;
/*      */ import com.pdftron.pdf.Element;
/*      */ import com.pdftron.pdf.ElementReader;
/*      */ import com.pdftron.pdf.ElementWriter;
/*      */ import com.pdftron.pdf.Field;
/*      */ import com.pdftron.pdf.Font;
/*      */ import com.pdftron.pdf.GState;
/*      */ import com.pdftron.pdf.PDFDoc;
/*      */ import com.pdftron.pdf.PDFDraw;
/*      */ import com.pdftron.pdf.PDFNet;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.PageIterator;
/*      */ import com.pdftron.pdf.Point;
/*      */ import com.pdftron.pdf.QuadPoint;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.annots.FreeText;
/*      */ import com.pdftron.pdf.annots.Ink;
/*      */ import com.pdftron.pdf.annots.Line;
/*      */ import com.pdftron.pdf.annots.Markup;
/*      */ import com.pdftron.pdf.annots.PolyLine;
/*      */ import com.pdftron.pdf.annots.Polygon;
/*      */ import com.pdftron.pdf.annots.Popup;
/*      */ import com.pdftron.pdf.annots.Redaction;
/*      */ import com.pdftron.pdf.annots.RubberStamp;
/*      */ import com.pdftron.pdf.annots.Square;
/*      */ import com.pdftron.pdf.annots.Text;
/*      */ import com.pdftron.pdf.annots.Widget;
/*      */ import com.pdftron.pdf.model.AnnotReviewState;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.model.FontResource;
/*      */ import com.pdftron.pdf.model.FreeTextCacheStruct;
/*      */ import com.pdftron.pdf.model.RulerItem;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.ToolManager;
/*      */ import com.pdftron.pdf.widget.AutoScrollEditText;
/*      */ import com.pdftron.pdf.widget.richtext.PTRichEditor;
/*      */ import com.pdftron.sdf.DictIterator;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import io.reactivex.Single;
/*      */ import io.reactivex.SingleEmitter;
/*      */ import io.reactivex.SingleOnSubscribe;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectOutput;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.UUID;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AnnotUtils
/*      */ {
/*   96 */   public static String KEY_InReplyTo = "IRT";
/*   97 */   public static String KEY_ReplyTo = "RT";
/*   98 */   public static String KEY_NM = "NM";
/*   99 */   public static String VALUE_Group = "Group";
/*  100 */   public static String KEY_RichContent = "RC";
/*  101 */   public static String KEY_RawRichContent = "rawRC";
/*  102 */   public static String KEY_FreeTextDate = "pdftron_freetext_date";
/*  103 */   public static String KEY_FreeTextFill = "pdftron_freetext_fill";
/*      */   
/*  105 */   public static String Key_State = "State";
/*  106 */   public static String Key_StateModel = "StateModel";
/*  107 */   public static String Key_StateModelMarked = "Marked";
/*  108 */   public static String Key_StateModelReview = "Review";
/*  109 */   public static String Key_StateAccepted = "Accepted";
/*  110 */   public static String Key_StateRejected = "Rejected";
/*  111 */   public static String Key_StateCancelled = "Cancelled";
/*  112 */   public static String Key_StateCompleted = "Completed";
/*  113 */   public static String Key_StateNone = "None";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flattenAnnot(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull Annot annot, int pageNum) throws PDFNetException {
/*  119 */     PDFDoc pdfDoc = pdfViewCtrl.getDoc();
/*      */     
/*  121 */     Page page = pdfDoc.getPage(pageNum);
/*  122 */     annot.flatten(page);
/*      */     
/*  124 */     pdfViewCtrl.update(true);
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
/*      */   public static String createScreenshot(@NonNull File tempDir, @NonNull PDFDoc pdfDoc, @NonNull Annot annot, int pageNum) {
/*  139 */     String tempFilePath = (new File(tempDir, Utils.getScreenshotFileName() + ".png")).getAbsolutePath();
/*      */ 
/*      */     
/*      */     try {
/*  143 */       Square sq = new Square(annot);
/*  144 */       Rect bbox = sq.getContentRect();
/*  145 */       Annot.BorderStyle sqBorderStyle = sq.getBorderStyle();
/*  146 */       if (sqBorderStyle != null) {
/*  147 */         double borderWidth = sqBorderStyle.getWidth();
/*  148 */         bbox.inflate(borderWidth * -1.0D);
/*      */       } 
/*  150 */       Page page = pdfDoc.getPage(pageNum);
/*  151 */       PDFDraw draw = new PDFDraw();
/*  152 */       draw.setClipRect(bbox);
/*  153 */       draw.export(page, tempFilePath);
/*  154 */     } catch (PDFNetException e) {
/*  155 */       return null;
/*      */     } 
/*  157 */     return tempFilePath;
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
/*      */   public static Single<String> createScreenshotAsync(final File tempDir, final PDFDoc pdfDoc, final Annot annot, final int annotPageNumber) {
/*  170 */     return Single.create(new SingleOnSubscribe<String>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<String> emitter) throws Exception
/*      */           {
/*  174 */             boolean shouldUnlockRead = false;
/*      */             try {
/*  176 */               pdfDoc.lockRead();
/*  177 */               shouldUnlockRead = true;
/*  178 */               String tempPng = AnnotUtils.createScreenshot(tempDir, pdfDoc, annot, annotPageNumber);
/*  179 */               if (tempPng != null) {
/*  180 */                 emitter.onSuccess(tempPng);
/*      */               } else {
/*  182 */                 emitter.tryOnError(new IllegalStateException("Screenshot creation failed"));
/*      */               } 
/*  184 */             } catch (Exception e) {
/*  185 */               emitter.tryOnError(new IllegalStateException("Screenshot creation failed"));
/*  186 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */             } finally {
/*  188 */               if (shouldUnlockRead) {
/*  189 */                 pdfDoc.unlockRead();
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public static Single<String> loadSystemFonts() {
/*  197 */     return Single.create(new SingleOnSubscribe<String>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<String> emitter) throws Exception {
/*  200 */             String fontList = PDFNet.getSystemFontList();
/*  201 */             if (!Utils.isNullOrEmpty(fontList)) {
/*  202 */               emitter.onSuccess(fontList);
/*      */             } else {
/*  204 */               emitter.tryOnError(new RuntimeException("Unable to get system fonts"));
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   public static String getCurrentTime(@NonNull String format) {
/*  212 */     SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
/*  213 */     Calendar cal = Calendar.getInstance();
/*  214 */     return dateFormat.format(cal.getTime());
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
/*      */   private static boolean refreshCustomStickyNoteAppearance(@NonNull Context context, @NonNull Annot annot) {
/*  229 */     InputStream fis = null;
/*  230 */     PDFDoc template = null;
/*  231 */     ElementReader reader = null;
/*  232 */     ElementWriter writer = null;
/*      */     
/*      */     try {
/*  235 */       Text text = new Text(annot);
/*  236 */       String iconName = text.getIconName();
/*      */ 
/*      */ 
/*      */       
/*  240 */       fis = context.getResources().openRawResource(R.raw.stickynote_icons);
/*  241 */       template = new PDFDoc(fis);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  246 */       for (int pageNum = 1, pageCount = template.getPageCount(); pageNum <= pageCount; pageNum++) {
/*  247 */         if (iconName.equalsIgnoreCase(template.getPageLabel(pageNum).getPrefix())) {
/*  248 */           Page iconPage = template.getPage(pageNum);
/*  249 */           Obj contents = iconPage.getContents();
/*  250 */           Obj importedContents = annot.getSDFObj().getDoc().importObj(contents, true);
/*  251 */           Rect bbox = iconPage.getMediaBox();
/*  252 */           importedContents.putRect("BBox", bbox.getX1(), bbox.getY1(), bbox.getX2(), bbox.getY2());
/*  253 */           importedContents.putName("Subtype", "Form");
/*  254 */           importedContents.putName("Type", "XObject");
/*  255 */           reader = new ElementReader();
/*  256 */           writer = new ElementWriter();
/*  257 */           reader.begin(importedContents);
/*  258 */           writer.begin(importedContents, true);
/*  259 */           ColorPt rgbColor = text.getColorAsRGB();
/*  260 */           double opacity = text.getOpacity();
/*  261 */           for (Element element = reader.next(); element != null; element = reader.next()) {
/*  262 */             if (element.getType() == 1 && !element.isClippingPath()) {
/*  263 */               element.getGState().setFillColorSpace(ColorSpace.createDeviceRGB());
/*  264 */               element.getGState().setFillColor(rgbColor);
/*  265 */               element.getGState().setFillOpacity(opacity);
/*  266 */               element.getGState().setStrokeOpacity(opacity);
/*  267 */               element.setPathStroke(true);
/*  268 */               element.setPathFill(true);
/*      */             } 
/*  270 */             writer.writeElement(element);
/*      */           } 
/*  272 */           reader.end();
/*  273 */           writer.end();
/*      */ 
/*      */           
/*  276 */           text.setAppearance(importedContents);
/*      */ 
/*      */           
/*  279 */           AnalyticsHandlerAdapter.getInstance().sendEvent(4, "sticky note icon: " + iconName, 101);
/*      */           
/*  281 */           return true;
/*      */         } 
/*      */       } 
/*  284 */     } catch (Exception e) {
/*  285 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  287 */       if (reader != null) {
/*      */         try {
/*  289 */           reader.destroy();
/*  290 */         } catch (Exception exception) {}
/*      */       }
/*      */ 
/*      */       
/*  294 */       if (writer != null) {
/*      */         try {
/*  296 */           writer.destroy();
/*  297 */         } catch (Exception exception) {}
/*      */       }
/*      */ 
/*      */       
/*  301 */       Utils.closeQuietly(template);
/*  302 */       Utils.closeQuietly(fis);
/*      */     } 
/*      */     
/*  305 */     return false;
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
/*      */   private static boolean refreshCustomStampAppearance(@NonNull Context context, @NonNull Annot annot) {
/*  320 */     InputStream fis = null;
/*  321 */     PDFDoc template = null;
/*      */     
/*      */     try {
/*  324 */       RubberStamp stamp = new RubberStamp(annot);
/*  325 */       String iconName = stamp.getIconName();
/*      */ 
/*      */ 
/*      */       
/*  329 */       fis = context.getResources().openRawResource(R.raw.stamps_icons);
/*  330 */       template = new PDFDoc(fis);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  335 */       for (int pageNum = 1, pageCount = template.getPageCount(); pageNum <= pageCount; pageNum++) {
/*  336 */         if (iconName.equalsIgnoreCase(template.getPageLabel(pageNum).getPrefix())) {
/*  337 */           Page iconPage = template.getPage(pageNum);
/*  338 */           Obj contents = iconPage.getContents();
/*  339 */           Obj importedContents = annot.getSDFObj().getDoc().importObj(contents, true);
/*  340 */           Rect bbox = iconPage.getMediaBox();
/*  341 */           importedContents.putRect("BBox", bbox.getX1(), bbox.getY1(), bbox.getX2(), bbox.getY2());
/*  342 */           importedContents.putName("Subtype", "Form");
/*  343 */           importedContents.putName("Type", "XObject");
/*      */ 
/*      */           
/*  346 */           Obj res = iconPage.getResourceDict();
/*  347 */           if (res != null) {
/*  348 */             Obj importedRes = annot.getSDFObj().getDoc().importObj(res, true);
/*  349 */             importedContents.put("Resources", importedRes);
/*      */           } 
/*      */ 
/*      */           
/*  353 */           stamp.setAppearance(importedContents);
/*      */ 
/*      */           
/*  356 */           AnalyticsHandlerAdapter.getInstance().sendEvent(4, "rubber stamp icon: " + iconName, 101);
/*      */           
/*  358 */           return true;
/*      */         } 
/*      */       } 
/*  361 */     } catch (Exception e) {
/*  362 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  364 */       Utils.closeQuietly(template);
/*  365 */       Utils.closeQuietly(fis);
/*      */     } 
/*      */     
/*  368 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean refreshCustomRichFreeTextAppearance(@NonNull File appearance, @NonNull Annot annot) {
/*  375 */     PDFDoc template = null;
/*      */     
/*      */     try {
/*  378 */       FreeText freeText = new FreeText(annot);
/*  379 */       template = new PDFDoc(appearance.getAbsolutePath());
/*  380 */       Page editTextPage = template.getPage(1);
/*  381 */       Obj contents = editTextPage.getContents();
/*  382 */       Obj importedContents = annot.getSDFObj().getDoc().importObj(contents, true);
/*  383 */       Rect bbox = editTextPage.getMediaBox();
/*  384 */       importedContents.putRect("BBox", bbox.getX1(), bbox.getY1(), bbox.getX2(), bbox.getY2());
/*  385 */       importedContents.putName("Subtype", "Form");
/*  386 */       importedContents.putName("Type", "XObject");
/*      */ 
/*      */       
/*  389 */       Obj res = editTextPage.getResourceDict();
/*  390 */       if (res != null) {
/*  391 */         Obj importedRes = annot.getSDFObj().getDoc().importObj(res, true);
/*  392 */         importedContents.put("Resources", importedRes);
/*      */       } 
/*      */ 
/*      */       
/*  396 */       freeText.setAppearance(importedContents);
/*  397 */     } catch (Exception e) {
/*  398 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  400 */       Utils.closeQuietly(template);
/*      */     } 
/*      */     
/*  403 */     return false;
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
/*      */   public static void refreshAnnotAppearance(@NonNull Context context, @NonNull Annot annot) throws PDFNetException {
/*  419 */     switch (annot.getType()) {
/*      */       case 14:
/*  421 */         if (!PressureInkUtils.refreshCustomInkAppearanceForExistingAnnot(annot)) {
/*  422 */           annot.refreshAppearance();
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case 0:
/*  428 */         if (!refreshCustomStickyNoteAppearance(context, annot)) {
/*  429 */           annot.refreshAppearance();
/*      */         }
/*      */         return;
/*      */       case 12:
/*  433 */         if (!refreshCustomStampAppearance(context, annot)) {
/*  434 */           annot.refreshAppearance();
/*      */         }
/*      */         return;
/*      */     } 
/*  438 */     annot.refreshAppearance();
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
/*      */   public static void applyCustomFreeTextAppearance(@NonNull PDFViewCtrl pdfViewCtrl, @NonNull AutoScrollEditText editText, @NonNull Annot annot, int pageNum) throws PDFNetException {
/*  453 */     if (Utils.isLollipop()) {
/*  454 */       editText.removeSpacingHandle();
/*  455 */       editText.clearFocus();
/*  456 */       editText.setCursorVisible(false);
/*  457 */       editText.clearComposingText();
/*  458 */       float spacing = 0.0F;
/*  459 */       if (Utils.isLollipop()) {
/*  460 */         spacing = editText.getLetterSpacing();
/*      */       }
/*  462 */       Rect bbox = editText.getBoundingRect();
/*  463 */       createCustomFreeTextAppearance((View)editText, pdfViewCtrl, annot, pageNum, bbox);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  470 */       annot.setCustomData(KEY_FreeTextFill, String.valueOf(spacing));
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
/*      */   public static void createCustomFreeTextAppearance(View view, PDFViewCtrl pdfViewCtrl, Annot annot, int pageNum, @Nullable Rect bbox) throws PDFNetException {
/*  486 */     if (null == view || null == pdfViewCtrl || null == annot) {
/*      */       return;
/*      */     }
/*      */     
/*  490 */     FreeText freeText = new FreeText(annot);
/*      */     
/*  492 */     Rect viewRect = (bbox == null) ? trimView(view) : bbox;
/*  493 */     Rect viewPageRect = getPageRectFromScreenRect(pdfViewCtrl, viewRect, pageNum);
/*  494 */     Rect textRect = freeText.getRect();
/*  495 */     textRect.normalize();
/*  496 */     textRect.setX2(textRect.getX1() + viewPageRect.getWidth());
/*  497 */     textRect.setY1(textRect.getY2() - viewPageRect.getHeight());
/*  498 */     freeText.resize(textRect);
/*      */     
/*  500 */     File ret = createPdfFromView(view, 
/*  501 */         (int)(viewRect.getWidth() + 0.5D), 
/*  502 */         (int)(viewRect.getHeight() + 0.5D), new File(view
/*  503 */           .getContext().getCacheDir(), "rc-FreeText.pdf"));
/*      */ 
/*      */     
/*  506 */     if (ret != null) {
/*  507 */       refreshCustomRichFreeTextAppearance(ret, (Annot)freeText);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createRCFreeTextAppearance(PTRichEditor richEditor, PDFViewCtrl pdfViewCtrl, Annot annot, int pageNum) throws PDFNetException {
/*      */     String editTextHtml;
/*  517 */     if (null == richEditor || null == pdfViewCtrl || null == annot) {
/*      */       return;
/*      */     }
/*      */     
/*  521 */     FreeText freeText = new FreeText(annot);
/*  522 */     createCustomFreeTextAppearance((View)richEditor, pdfViewCtrl, annot, pageNum, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     String rawHtml = richEditor.getHtml();
/*  589 */     freeText.setCustomData(KEY_RawRichContent, rawHtml);
/*      */ 
/*      */     
/*  592 */     String ZERO_WIDTH_SPACE_STR_ESCAPE = "&#8203;";
/*      */     
/*  594 */     EditText tempTextView = new EditText(richEditor.getContext());
/*  595 */     tempTextView.setText((CharSequence)Html.fromHtml(rawHtml));
/*  596 */     String plainText = tempTextView.getText().toString();
/*  597 */     StringBuilder sb = new StringBuilder();
/*  598 */     sb.append("<html><body>");
/*      */     
/*  600 */     if (Utils.isNougat()) {
/*  601 */       editTextHtml = Html.toHtml((Spanned)tempTextView.getEditableText(), 1);
/*      */     } else {
/*  603 */       editTextHtml = Html.toHtml((Spanned)tempTextView.getEditableText());
/*      */     } 
/*  605 */     sb.append(editTextHtml);
/*  606 */     sb.append("</body></html>");
/*  607 */     String htmlContent = sb.toString().replaceAll("&#8203;", "");
/*      */     
/*  609 */     freeText.getSDFObj().putString(KEY_RichContent, htmlContent);
/*  610 */     freeText.getSDFObj().putString("pdftron", "");
/*  611 */     freeText.setContents(plainText);
/*  612 */     freeText.setFontSize(richEditor.getEditorFontSize());
/*      */   }
/*      */   
/*      */   private static Rect trimView(View view) throws PDFNetException {
/*  616 */     Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
/*  617 */     Canvas canvas = new Canvas(bitmap);
/*  618 */     view.draw(canvas);
/*      */     
/*  620 */     Bitmap trimmed = createTrimmedBitmap(bitmap);
/*  621 */     return new Rect(0.0D, 0.0D, trimmed.getWidth(), trimmed.getHeight());
/*      */   }
/*      */   
/*      */   private static Bitmap createTrimmedBitmap(Bitmap bmp) {
/*  625 */     int imgHeight = bmp.getHeight();
/*  626 */     int imgWidth = bmp.getWidth();
/*  627 */     int smallX = 0, largeX = imgWidth, smallY = 0, largeY = imgHeight;
/*  628 */     int left = imgWidth, right = imgWidth, top = imgHeight, bottom = imgHeight;
/*  629 */     for (int i = 0; i < imgWidth; i++) {
/*  630 */       for (int j = 0; j < imgHeight; j++) {
/*  631 */         if (bmp.getPixel(i, j) != 0) {
/*  632 */           if (i - smallX < left) {
/*  633 */             left = i - smallX;
/*      */           }
/*  635 */           if (largeX - i < right) {
/*  636 */             right = largeX - i;
/*      */           }
/*  638 */           if (j - smallY < top) {
/*  639 */             top = j - smallY;
/*      */           }
/*  641 */           if (largeY - j < bottom) {
/*  642 */             bottom = largeY - j;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  648 */     left = 0;
/*  649 */     top = 0;
/*  650 */     bmp = Bitmap.createBitmap(bmp, left, top, imgWidth - left - right, imgHeight - top - bottom);
/*      */     
/*  652 */     return bmp;
/*      */   }
/*      */   
/*      */   private static void removeWhiteFillForElement(ElementWriter writer, ElementReader reader) throws PDFNetException {
/*      */     Element element;
/*  657 */     while ((element = reader.next()) != null) {
/*  658 */       GState gs; ColorPt fillColor; ColorSpace fillCS; ColorPt fillColorRgb; switch (element.getType()) {
/*      */         case 1:
/*  660 */           gs = element.getGState();
/*      */           
/*  662 */           fillColor = gs.getFillColor();
/*  663 */           fillCS = gs.getFillColorSpace();
/*  664 */           fillColorRgb = fillCS.convert2RGB(fillColor);
/*  665 */           if (fillColorRgb.get(0) == 1.0D && fillColorRgb.get(1) == 1.0D && fillColorRgb.get(2) == 1.0D) {
/*  666 */             element.setPathFill(false);
/*      */           }
/*  668 */           writer.writeElement(element);
/*      */           continue;
/*      */       } 
/*      */       
/*  672 */       writer.writeElement(element);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int convPixelToPoint(double pixel) {
/*  679 */     return (int)(pixel * 72.0D / 96.0D + 0.5D);
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
/*      */   @RequiresApi(api = 19)
/*      */   @Nullable
/*      */   public static File createPdfFromView(@NonNull View content, int width, int height, @NonNull File outputFile) {
/*  695 */     if (!Utils.isKitKat()) {
/*  696 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  700 */       PdfDocument document = new PdfDocument();
/*      */ 
/*      */ 
/*      */       
/*  704 */       PdfDocument.PageInfo pageInfo = (new PdfDocument.PageInfo.Builder(width, height, 1)).create();
/*      */ 
/*      */       
/*  707 */       PdfDocument.Page page = document.startPage(pageInfo);
/*      */ 
/*      */       
/*  710 */       content.draw(page.getCanvas());
/*      */ 
/*      */       
/*  713 */       document.finishPage(page);
/*      */ 
/*      */       
/*  716 */       outputFile.createNewFile();
/*  717 */       FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
/*      */       
/*  719 */       document.writeTo(fileOutputStream);
/*      */ 
/*      */       
/*  722 */       document.close();
/*      */       
/*  724 */       return outputFile;
/*  725 */     } catch (Exception ex) {
/*  726 */       ex.printStackTrace();
/*      */       
/*  728 */       return null;
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
/*      */   @Nullable
/*      */   public static double[] getStampSize(@NonNull Context context, @NonNull String stampName) {
/*  743 */     InputStream fis = null;
/*  744 */     PDFDoc template = null;
/*      */     try {
/*  746 */       fis = context.getResources().openRawResource(R.raw.stamps_icons);
/*  747 */       template = new PDFDoc(fis);
/*      */       
/*  749 */       for (int pageNum = 1, pageCount = template.getPageCount(); pageNum <= pageCount; pageNum++) {
/*  750 */         if (stampName.equalsIgnoreCase(template.getPageLabel(pageNum).getPrefix())) {
/*  751 */           Page page = template.getPage(pageNum);
/*  752 */           double[] size = new double[2];
/*  753 */           size[0] = page.getPageWidth();
/*  754 */           size[1] = page.getPageHeight();
/*  755 */           return size;
/*      */         } 
/*      */       } 
/*  758 */     } catch (Exception e) {
/*  759 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/*  761 */       Utils.closeQuietly(template);
/*  762 */       Utils.closeQuietly(fis);
/*      */     } 
/*      */     
/*  765 */     return null;
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
/*      */   public static String getAnnotTypeAsString(@NonNull Context context, int typeId) {
/*  779 */     switch (typeId) {
/*      */       case 0:
/*  781 */         return context.getResources().getString(R.string.annot_text).toLowerCase();
/*      */       case 1:
/*  783 */         return context.getResources().getString(R.string.annot_link).toLowerCase();
/*      */       case 2:
/*      */       case 1010:
/*      */       case 1011:
/*  787 */         return context.getResources().getString(R.string.annot_free_text).toLowerCase();
/*      */       case 1007:
/*  789 */         return context.getResources().getString(R.string.annot_callout).toLowerCase();
/*      */       case 3:
/*  791 */         return context.getResources().getString(R.string.annot_line).toLowerCase();
/*      */       case 4:
/*  793 */         return context.getResources().getString(R.string.annot_square).toLowerCase();
/*      */       case 5:
/*  795 */         return context.getResources().getString(R.string.annot_circle).toLowerCase();
/*      */       case 6:
/*  797 */         return context.getResources().getString(R.string.annot_polygon).toLowerCase();
/*      */       case 7:
/*  799 */         return context.getResources().getString(R.string.annot_polyline).toLowerCase();
/*      */       case 8:
/*  801 */         return context.getResources().getString(R.string.annot_highlight).toLowerCase();
/*      */       case 9:
/*  803 */         return context.getResources().getString(R.string.annot_underline).toLowerCase();
/*      */       case 10:
/*  805 */         return context.getResources().getString(R.string.annot_squiggly).toLowerCase();
/*      */       case 11:
/*  807 */         return context.getResources().getString(R.string.annot_strikeout).toLowerCase();
/*      */       case 12:
/*  809 */         return context.getResources().getString(R.string.annot_stamp).toLowerCase();
/*      */       case 13:
/*  811 */         return context.getResources().getString(R.string.annot_caret).toLowerCase();
/*      */       case 14:
/*  813 */         return context.getResources().getString(R.string.annot_ink).toLowerCase();
/*      */       case 25:
/*  815 */         return context.getResources().getString(R.string.annot_redaction).toLowerCase();
/*      */       case 1002:
/*  817 */         return context.getResources().getString(R.string.annot_signature).toLowerCase();
/*      */       case 1001:
/*  819 */         return context.getResources().getString(R.string.annot_arrow).toLowerCase();
/*      */       case 1006:
/*  821 */         return context.getResources().getString(R.string.annot_ruler).toLowerCase();
/*      */       case 1004:
/*  823 */         return context.getResources().getString(R.string.annot_free_highlight).toLowerCase();
/*      */       case 1005:
/*  825 */         return context.getResources().getString(R.string.annot_cloud).toLowerCase();
/*      */       case 16:
/*  827 */         return context.getResources().getString(R.string.annot_file_attachment).toLowerCase();
/*      */       case 17:
/*  829 */         return context.getResources().getString(R.string.annot_sound).toLowerCase();
/*      */       case 1008:
/*  831 */         return context.getResources().getString(R.string.annot_perimeter_measure).toLowerCase();
/*      */       case 1009:
/*      */       case 1012:
/*  834 */         return context.getResources().getString(R.string.annot_area_measure).toLowerCase();
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
/*  846 */     return context.getResources().getString(R.string.annot_misc).toLowerCase();
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
/*      */   public static String getAnnotTypeAsString(@NonNull Context context, @NonNull Annot annot) throws PDFNetException {
/*  862 */     int typeId = getAnnotType(annot);
/*  863 */     return getAnnotTypeAsString(context, typeId);
/*      */   }
/*      */   
/*      */   public static int getAnnotType(@NonNull Annot annot) throws PDFNetException {
/*  867 */     int typeId = annot.getType();
/*  868 */     switch (typeId) {
/*      */       case 3:
/*  870 */         if (isArrow(annot))
/*  871 */           return 1001; 
/*  872 */         if (isRuler(annot)) {
/*  873 */           return 1006;
/*      */         }
/*      */       case 7:
/*  876 */         if (isPerimeterMeasure(annot)) {
/*  877 */           return 1008;
/*      */         }
/*      */       case 6:
/*  880 */         if (isCloud(annot))
/*  881 */           return 1005; 
/*  882 */         if (isAreaMeasure(annot)) {
/*  883 */           if (isRectAreaMeasure(annot)) {
/*  884 */             return 1012;
/*      */           }
/*  886 */           return 1009;
/*      */         } 
/*      */       case 14:
/*  889 */         if (isFreeHighlighter(annot)) {
/*  890 */           return 1004;
/*      */         }
/*      */       case 2:
/*  893 */         if (isCallout(annot))
/*  894 */           return 1007; 
/*  895 */         if (isFreeTextDate(annot))
/*  896 */           return 1011; 
/*  897 */         if (isFreeTextSpacing(annot))
/*  898 */           return 1010; 
/*      */         break;
/*      */     } 
/*  901 */     return annot.getType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AnnotStyle getAnnotStyle(@NonNull Annot annot) throws PDFNetException {
/*      */     ColorPt colorPt;
/*  913 */     int compNum, annotType = annot.getType();
/*  914 */     boolean annotIsSticky = (annotType == 0);
/*  915 */     boolean annotIsFreeText = AnnotStyle.isFreeTextGroup(annotType);
/*  916 */     boolean annotIsWidget = (annotType == 19);
/*      */ 
/*      */     
/*  919 */     float thickness = (float)annot.getBorderStyle().getWidth();
/*      */     
/*  921 */     if (thickness == 0.0F) {
/*  922 */       Obj sdfObj = annot.getSDFObj().findObj("pdftron_thickness");
/*  923 */       if (sdfObj != null) {
/*  924 */         thickness = (float)sdfObj.getNumber();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  932 */     if (annotIsFreeText) {
/*  933 */       FreeText freeText = new FreeText(annot);
/*  934 */       colorPt = freeText.getLineColor();
/*  935 */       compNum = freeText.getLineColorCompNum();
/*      */     } else {
/*  937 */       colorPt = annot.getColorAsRGB();
/*  938 */       compNum = annot.getColorCompNum();
/*      */     } 
/*  940 */     int color = Utils.colorPt2color(colorPt);
/*  941 */     if (compNum == 0) {
/*  942 */       color = 0;
/*      */     }
/*      */ 
/*      */     
/*  946 */     int fillColor = 0;
/*  947 */     float opacity = 1.0F;
/*  948 */     if (annot.isMarkup()) {
/*  949 */       Markup m = new Markup(annot);
/*  950 */       opacity = (float)m.getOpacity();
/*      */       
/*  952 */       if (annotIsFreeText) {
/*  953 */         FreeText freeText = new FreeText(annot);
/*  954 */         if (freeText.getColorCompNum() == 3) {
/*  955 */           ColorPt fillColorPt = freeText.getColorAsRGB();
/*  956 */           fillColor = Utils.colorPt2color(fillColorPt);
/*      */         }
/*      */       
/*  959 */       } else if (m.getInteriorColorCompNum() == 3) {
/*  960 */         ColorPt fillColorPt = m.getInteriorColor();
/*  961 */         fillColor = Utils.colorPt2color(fillColorPt);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  967 */     String icon = "";
/*  968 */     if (annotIsSticky) {
/*  969 */       Text t = new Text(annot);
/*  970 */       icon = t.getIconName();
/*      */     } 
/*      */     
/*  973 */     if (annotType == 17) {
/*  974 */       icon = "sound";
/*      */     }
/*      */     
/*  977 */     AnnotStyle annotStyle = new AnnotStyle();
/*  978 */     annotStyle.setAnnotType(annot.getType());
/*  979 */     annotStyle.setStyle(color, fillColor, thickness, opacity);
/*      */     
/*  981 */     if (!Utils.isNullOrEmpty(icon)) {
/*  982 */       annotStyle.setIcon(icon);
/*      */     }
/*      */     
/*  985 */     if (annotType == 5 || annotType == 4 || annotType == 3 || annotType == 7 || annotType == 6 || annotType == 14 || annotType == 0 || 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  992 */       canUseBitmapAppearance(annot))
/*      */     {
/*      */       
/*  995 */       annotStyle.setHasAppearance(false);
/*      */     }
/*      */ 
/*      */     
/*  999 */     if (annotIsFreeText) {
/* 1000 */       String fontName = "";
/*      */ 
/*      */       
/* 1003 */       FreeText freeText = new FreeText(annot);
/* 1004 */       float textSize = (float)freeText.getFontSize();
/* 1005 */       int textColor = Utils.colorPt2color(freeText.getTextColor());
/* 1006 */       Obj freeTextObj = freeText.getSDFObj();
/* 1007 */       Obj drDict = freeTextObj.findObj("DR");
/* 1008 */       if (drDict != null && drDict.isDict()) {
/* 1009 */         Obj fontDict = drDict.findObj("Font");
/* 1010 */         if (fontDict != null && fontDict.isDict()) {
/* 1011 */           DictIterator fItr = fontDict.getDictIterator();
/* 1012 */           if (fItr.hasNext()) {
/* 1013 */             Font f = new Font(fItr.value());
/* 1014 */             fontName = f.getName();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1019 */       String rawHTML = freeText.getCustomData(KEY_RawRichContent);
/* 1020 */       if (!Utils.isNullOrEmpty(rawHTML))
/*      */       {
/* 1022 */         annotStyle.setTextHTMLContent(rawHTML);
/*      */       }
/*      */       
/* 1025 */       annotStyle.setFont(new FontResource(fontName));
/* 1026 */       annotStyle.setTextColor(textColor);
/* 1027 */       if (textSize == 0.0F) {
/* 1028 */         textSize = 12.0F;
/*      */       }
/* 1030 */       annotStyle.setTextSize(textSize);
/*      */     } 
/*      */     
/* 1033 */     if (annotIsWidget) {
/*      */ 
/*      */       
/* 1036 */       Widget widget = new Widget(annot);
/* 1037 */       float textSize = (float)widget.getFontSize();
/* 1038 */       int textColor = Utils.colorPt2color(widget.getTextColor());
/* 1039 */       Font font = widget.getFont();
/* 1040 */       String fontName = (font != null) ? font.getName() : "";
/*      */       
/* 1042 */       annotStyle.setFont(new FontResource(fontName));
/* 1043 */       annotStyle.setTextColor(textColor);
/* 1044 */       if (textSize == 0.0F) {
/* 1045 */         textSize = 12.0F;
/*      */       }
/* 1047 */       annotStyle.setTextSize(textSize);
/*      */     } 
/*      */     
/* 1050 */     if (annotType == 3) {
/* 1051 */       if (!isSimpleLine(annot)) {
/* 1052 */         annotStyle.setHasAppearance(true);
/*      */       }
/* 1054 */       if (isArrow(annot)) {
/* 1055 */         annotType = 1001;
/* 1056 */         annotStyle.setHasAppearance(false);
/* 1057 */       } else if (isRuler(annot)) {
/* 1058 */         annotType = 1006;
/* 1059 */         RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot(annot);
/* 1060 */         if (null != rulerItem) {
/* 1061 */           annotStyle.setRulerItem(rulerItem);
/*      */         } else {
/* 1063 */           rulerItem = RulerItem.getRulerItem(annot);
/* 1064 */           if (null != rulerItem) {
/* 1065 */             annotStyle.setRulerItem(rulerItem);
/*      */           }
/*      */         } 
/* 1068 */         annotStyle.setHasAppearance(false);
/*      */       } 
/* 1070 */       annotStyle.setAnnotType(annotType);
/*      */     } 
/* 1072 */     if (annotType == 14 && 
/* 1073 */       isFreeHighlighter(annot)) {
/* 1074 */       annotType = 1004;
/* 1075 */       annotStyle.setAnnotType(annotType);
/* 1076 */       annotStyle.setHasAppearance(false);
/*      */     } 
/*      */     
/* 1079 */     if (annotType == 7 && 
/* 1080 */       isPerimeterMeasure(annot)) {
/* 1081 */       annotType = 1008;
/* 1082 */       annotStyle.setAnnotType(annotType);
/* 1083 */       RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot(annot);
/* 1084 */       if (null != rulerItem) {
/* 1085 */         annotStyle.setRulerItem(rulerItem);
/*      */       }
/*      */     } 
/*      */     
/* 1089 */     if (annotType == 6) {
/* 1090 */       if (isCloud(annot)) {
/* 1091 */         annotType = 1005;
/* 1092 */         annotStyle.setAnnotType(annotType);
/* 1093 */         Polygon polygon = new Polygon(annot);
/* 1094 */         double intensity = polygon.getBorderEffectIntensity();
/* 1095 */         annotStyle.setBorderEffectIntensity(intensity);
/* 1096 */       } else if (isAreaMeasure(annot)) {
/* 1097 */         annotType = isRectAreaMeasure(annot) ? 1012 : 1009;
/*      */         
/* 1099 */         annotStyle.setAnnotType(annotType);
/* 1100 */         RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot(annot);
/* 1101 */         if (null != rulerItem) {
/* 1102 */           annotStyle.setRulerItem(rulerItem);
/*      */         }
/*      */       } 
/*      */     }
/* 1106 */     if (annotType == 2) {
/* 1107 */       if (isCallout(annot)) {
/* 1108 */         annotType = 1007;
/* 1109 */         annotStyle.setAnnotType(annotType);
/* 1110 */         annotStyle.setHasAppearance(true);
/* 1111 */       } else if (isFreeTextDate(annot)) {
/* 1112 */         annotType = 1011;
/* 1113 */         annotStyle.setAnnotType(annotType);
/* 1114 */         annotStyle.setDateFormat(annot.getCustomData(KEY_FreeTextDate));
/* 1115 */       } else if (isFreeTextSpacing(annot)) {
/* 1116 */         annotType = 1010;
/* 1117 */         annotStyle.setAnnotType(annotType);
/* 1118 */         String spacing = annot.getCustomData(KEY_FreeTextFill);
/*      */         try {
/* 1120 */           float spacingF = Float.parseFloat(spacing);
/* 1121 */           annotStyle.setLetterSpacing(spacingF);
/* 1122 */         } catch (Exception ex) {
/* 1123 */           annotStyle.setLetterSpacing(0.0F);
/*      */         } 
/*      */       } 
/*      */     }
/* 1127 */     if (annotType == 25) {
/* 1128 */       Redaction redaction = new Redaction(annot);
/* 1129 */       annotStyle.setOverlayText(redaction.getOverlayText());
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1134 */       Annot.BorderStyle borderStyle = annot.getBorderStyle();
/* 1135 */       if (borderStyle.getStyle() != 0) {
/* 1136 */         annotStyle.setHasAppearance(true);
/*      */       }
/* 1138 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1141 */     annotStyle.setTextContent(annot.getContents());
/*      */     
/* 1143 */     return annotStyle;
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
/*      */   private static String getAnnotTypeAsPluralString(@NonNull Context context, int typeId) {
/* 1157 */     switch (typeId) {
/*      */       case 0:
/* 1159 */         return context.getResources().getString(R.string.annot_text_plural).toLowerCase();
/*      */       case 1:
/* 1161 */         return context.getResources().getString(R.string.annot_link_plural).toLowerCase();
/*      */       case 2:
/*      */       case 1010:
/*      */       case 1011:
/* 1165 */         return context.getResources().getString(R.string.annot_free_text_plural).toLowerCase();
/*      */       case 1007:
/* 1167 */         return context.getResources().getString(R.string.annot_callout_plural).toLowerCase();
/*      */       case 3:
/* 1169 */         return context.getResources().getString(R.string.annot_line_plural).toLowerCase();
/*      */       case 4:
/* 1171 */         return context.getResources().getString(R.string.annot_square_plural).toLowerCase();
/*      */       case 5:
/* 1173 */         return context.getResources().getString(R.string.annot_circle_plural).toLowerCase();
/*      */       case 6:
/* 1175 */         return context.getResources().getString(R.string.annot_polygon_plural).toLowerCase();
/*      */       case 7:
/* 1177 */         return context.getResources().getString(R.string.annot_polyline).toLowerCase();
/*      */       case 8:
/* 1179 */         return context.getResources().getString(R.string.annot_highlight_plural).toLowerCase();
/*      */       case 9:
/* 1181 */         return context.getResources().getString(R.string.annot_underline_plural).toLowerCase();
/*      */       case 10:
/* 1183 */         return context.getResources().getString(R.string.annot_squiggly_plural).toLowerCase();
/*      */       case 11:
/* 1185 */         return context.getResources().getString(R.string.annot_strikeout_plural).toLowerCase();
/*      */       case 12:
/* 1187 */         return context.getResources().getString(R.string.annot_stamp_plural).toLowerCase();
/*      */       case 13:
/* 1189 */         return context.getResources().getString(R.string.annot_caret_plural).toLowerCase();
/*      */       case 14:
/* 1191 */         return context.getResources().getString(R.string.annot_ink_plural).toLowerCase();
/*      */       case 25:
/* 1193 */         return context.getResources().getString(R.string.annot_redaction_plural).toLowerCase();
/*      */       case 1002:
/* 1195 */         return context.getResources().getString(R.string.annot_signature_plural).toLowerCase();
/*      */       case 1001:
/* 1197 */         return context.getResources().getString(R.string.annot_arrow_plural).toLowerCase();
/*      */       case 1006:
/* 1199 */         return context.getResources().getString(R.string.annot_ruler_plural).toLowerCase();
/*      */       case 1004:
/* 1201 */         return context.getResources().getString(R.string.annot_free_highlight_plural).toLowerCase();
/*      */       case 1005:
/* 1203 */         return context.getResources().getString(R.string.annot_cloud_plural).toLowerCase();
/*      */       case 16:
/* 1205 */         return context.getResources().getString(R.string.annot_file_attachment_plural).toLowerCase();
/*      */       case 17:
/* 1207 */         return context.getResources().getString(R.string.annot_sound).toLowerCase();
/*      */       case 1008:
/* 1209 */         return context.getResources().getString(R.string.annot_perimeter_measure).toLowerCase();
/*      */       case 1009:
/*      */       case 1012:
/* 1212 */         return context.getResources().getString(R.string.annot_area_measure).toLowerCase();
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
/* 1224 */     return context.getResources().getString(R.string.annot_misc_plural).toLowerCase();
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
/*      */   public static String getAnnotTypeAsPluralString(@NonNull Context context, @NonNull Annot annot) throws PDFNetException {
/* 1240 */     int typeId = getAnnotType(annot);
/* 1241 */     return getAnnotTypeAsPluralString(context, typeId);
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
/*      */   public static String getAnnotTypeAsString(Annot annot) throws PDFNetException {
/* 1254 */     int annotType = annot.getType();
/* 1255 */     switch (annotType) {
/*      */       case 0:
/* 1257 */         return "sticky_note";
/*      */       case 1:
/* 1259 */         return "link";
/*      */       case 2:
/* 1261 */         if (isCallout(annot)) {
/* 1262 */           return "callout";
/*      */         }
/* 1264 */         return "free_text";
/*      */       case 3:
/* 1266 */         if (isArrow(annot)) {
/* 1267 */           return "arrow";
/*      */         }
/* 1269 */         if (isRuler(annot)) {
/* 1270 */           return "ruler";
/*      */         }
/* 1272 */         return "line";
/*      */       case 4:
/* 1274 */         return "square";
/*      */       case 5:
/* 1276 */         return "circle";
/*      */       case 6:
/* 1278 */         if (isCloud(annot)) {
/* 1279 */           return "cloud";
/*      */         }
/* 1281 */         return "polygon";
/*      */       case 7:
/* 1283 */         return "polyline";
/*      */       case 8:
/* 1285 */         return "highlight";
/*      */       case 9:
/* 1287 */         return "underline";
/*      */       case 10:
/* 1289 */         return "squiggly";
/*      */       case 11:
/* 1291 */         return "strikeout";
/*      */       case 12:
/* 1293 */         return "stamp";
/*      */       case 13:
/* 1295 */         return "caret";
/*      */       case 14:
/* 1297 */         if (isFreeHighlighter(annot)) {
/* 1298 */           return "free_highlighter";
/*      */         }
/* 1300 */         return "ink";
/*      */     } 
/* 1302 */     return "annotation";
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
/*      */   public static String getAnnotTypeAsPluralString(Annot annot) throws PDFNetException {
/* 1316 */     int annotType = annot.getType();
/* 1317 */     switch (annotType) {
/*      */       case 0:
/* 1319 */         return "sticky_notes";
/*      */       case 3:
/* 1321 */         if (isArrow(annot)) {
/* 1322 */           return "arrows";
/*      */         }
/* 1324 */         if (isRuler(annot)) {
/* 1325 */           return "rulers";
/*      */         }
/* 1327 */         return "lines";
/*      */       case 2:
/* 1329 */         if (isCallout(annot)) {
/* 1330 */           return "callouts";
/*      */         }
/* 1332 */         return "free_texts";
/*      */       case 1:
/* 1334 */         return "links";
/*      */       case 4:
/* 1336 */         return "squares";
/*      */       case 5:
/* 1338 */         return "circles";
/*      */       case 6:
/* 1340 */         if (isCloud(annot)) {
/* 1341 */           return "clouds";
/*      */         }
/* 1343 */         return "polygons";
/*      */       case 7:
/* 1345 */         return "polylines";
/*      */       case 8:
/* 1347 */         return "highlights";
/*      */       case 9:
/* 1349 */         return "underlines";
/*      */       case 10:
/* 1351 */         return "squiggles";
/*      */       case 11:
/* 1353 */         return "strikeouts";
/*      */       case 12:
/* 1355 */         return "stamps";
/*      */       case 13:
/* 1357 */         return "carets";
/*      */       case 14:
/* 1359 */         if (isFreeHighlighter(annot)) {
/* 1360 */           return "free_highlighters";
/*      */         }
/* 1362 */         return "inks";
/*      */     } 
/* 1364 */     return "annotations";
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
/*      */   public static boolean isSimpleLine(@NonNull Annot annot) throws PDFNetException {
/* 1380 */     Line line = new Line(annot);
/* 1381 */     return (line.isValid() && line.getEndStyle() == 9 && line.getStartStyle() == 9);
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
/*      */   public static boolean isArrow(@NonNull Annot annot) throws PDFNetException {
/* 1396 */     Line line = new Line(annot);
/* 1397 */     return (line.isValid() && line.getEndStyle() == 3);
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
/*      */   public static boolean isRuler(@NonNull Annot annot) {
/*      */     try {
/* 1411 */       String itField = MeasureUtils.getIT(annot);
/* 1412 */       return (itField != null && itField.equals(MeasureUtils.K_LineDimension));
/* 1413 */     } catch (Exception ex) {
/* 1414 */       ex.printStackTrace();
/*      */ 
/*      */       
/* 1417 */       return (RulerItem.getRulerItem(annot) != null);
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
/*      */   public static boolean isPerimeterMeasure(@NonNull Annot annot) {
/*      */     try {
/* 1431 */       String itField = MeasureUtils.getIT(annot);
/* 1432 */       return (itField != null && itField.equals(MeasureUtils.K_PolyLineDimension));
/* 1433 */     } catch (Exception ex) {
/* 1434 */       ex.printStackTrace();
/*      */       
/* 1436 */       return false;
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
/*      */   public static boolean isAreaMeasure(@NonNull Annot annot) {
/*      */     try {
/* 1450 */       String itField = MeasureUtils.getIT(annot);
/* 1451 */       return (itField != null && itField.equals(MeasureUtils.K_PolygonDimension));
/* 1452 */     } catch (Exception ex) {
/* 1453 */       ex.printStackTrace();
/*      */       
/* 1455 */       return false;
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
/*      */   public static boolean isRectAreaMeasure(@NonNull Annot annot) {
/*      */     try {
/* 1468 */       if (6 == annot.getType() && 
/* 1469 */         annot.isValid() && 
/* 1470 */         !Utils.isNullOrEmpty(annot.getCustomData(MeasureUtils.K_RECT_AREA))) {
/* 1471 */         String itField = MeasureUtils.getIT(annot);
/* 1472 */         return (itField != null && itField.equals(MeasureUtils.K_PolygonDimension));
/*      */       }
/*      */     
/* 1475 */     } catch (Exception ex) {
/* 1476 */       ex.printStackTrace();
/*      */     } 
/* 1478 */     return false;
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
/*      */   public static boolean isCloud(@NonNull Annot annot) throws PDFNetException {
/* 1493 */     Polygon polygon = new Polygon(annot);
/* 1494 */     return (polygon.isValid() && polygon.getBorderEffect() == 1);
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
/*      */   public static boolean isFreeHighlighter(@NonNull Annot annot) throws PDFNetException {
/* 1509 */     Ink ink = new Ink(annot);
/* 1510 */     return (ink.isValid() && ink.getHighlightIntent());
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
/*      */   public static boolean isCallout(@NonNull Annot annot) throws PDFNetException {
/* 1522 */     if (2 == annot.getType()) {
/* 1523 */       FreeText freeText = new FreeText(annot);
/* 1524 */       return (freeText.isValid() && freeText
/* 1525 */         .getIntentName() == 1);
/*      */     } 
/* 1527 */     return false;
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
/*      */   public static boolean isFreeTextDate(@NonNull Annot annot) throws PDFNetException {
/* 1539 */     if (2 == annot.getType()) {
/* 1540 */       FreeText freeText = new FreeText(annot);
/* 1541 */       return (freeText.isValid() && 
/* 1542 */         !Utils.isNullOrEmpty(freeText.getCustomData(KEY_FreeTextDate)));
/*      */     } 
/* 1544 */     return false;
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
/*      */   public static boolean isFreeTextSpacing(@NonNull Annot annot) throws PDFNetException {
/* 1556 */     if (2 == annot.getType()) {
/* 1557 */       FreeText freeText = new FreeText(annot);
/* 1558 */       return (freeText.isValid() && 
/* 1559 */         !Utils.isNullOrEmpty(freeText.getCustomData(KEY_FreeTextFill)));
/*      */     } 
/* 1561 */     return false;
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
/*      */   public static boolean isListBox(@NonNull Annot annot) throws PDFNetException {
/* 1573 */     if (19 == annot.getType()) {
/* 1574 */       Widget widget = new Widget(annot);
/* 1575 */       Field field = widget.getField();
/* 1576 */       if (field != null && field.isValid()) {
/* 1577 */         int field_type = field.getType();
/* 1578 */         boolean isCombo = field.getFlag(14);
/* 1579 */         return (4 == field_type && !isCombo);
/*      */       } 
/*      */     } 
/* 1582 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAnnotImageResId(int type) {
/* 1592 */     int resId = 16908292;
/*      */     
/* 1594 */     switch (type) {
/*      */       case 0:
/* 1596 */         resId = R.drawable.ic_annotation_sticky_note_black_24dp;
/*      */         break;
/*      */       case 3:
/* 1599 */         resId = R.drawable.ic_annotation_line_black_24dp;
/*      */         break;
/*      */       case 1001:
/* 1602 */         resId = R.drawable.ic_annotation_arrow_black_24dp;
/*      */         break;
/*      */       case 1006:
/* 1605 */         resId = R.drawable.ic_annotation_distance_black_24dp;
/*      */         break;
/*      */       case 7:
/* 1608 */         resId = R.drawable.ic_annotation_polyline_black_24dp;
/*      */         break;
/*      */       case 1008:
/* 1611 */         resId = R.drawable.ic_annotation_perimeter_black_24dp;
/*      */         break;
/*      */       case 4:
/* 1614 */         resId = R.drawable.ic_annotation_square_black_24dp;
/*      */         break;
/*      */       case 5:
/* 1617 */         resId = R.drawable.ic_annotation_circle_black_24dp;
/*      */         break;
/*      */       case 6:
/* 1620 */         resId = R.drawable.ic_annotation_polygon_black_24dp;
/*      */         break;
/*      */       case 1009:
/* 1623 */         resId = R.drawable.ic_annotation_poly_area_24dp;
/*      */         break;
/*      */       case 1012:
/* 1626 */         resId = R.drawable.ic_annotation_area_black_24dp;
/*      */         break;
/*      */       case 1005:
/* 1629 */         resId = R.drawable.ic_annotation_cloud_black_24dp;
/*      */         break;
/*      */       case 9:
/* 1632 */         resId = R.drawable.ic_annotation_underline_black_24dp;
/*      */         break;
/*      */       case 11:
/* 1635 */         resId = R.drawable.ic_annotation_strikeout_black_24dp;
/*      */         break;
/*      */       case 14:
/* 1638 */         resId = R.drawable.ic_annotation_freehand_black_24dp;
/*      */         break;
/*      */       case 8:
/* 1641 */         resId = R.drawable.ic_annotation_highlight_black_24dp;
/*      */         break;
/*      */       case 2:
/* 1644 */         resId = R.drawable.ic_annotation_freetext_black_24dp;
/*      */         break;
/*      */       case 1007:
/* 1647 */         resId = R.drawable.ic_annotation_callout_black_24dp;
/*      */         break;
/*      */       case 1010:
/* 1650 */         resId = R.drawable.ic_fill_and_sign_spacing_text;
/*      */         break;
/*      */       case 1011:
/* 1653 */         resId = R.drawable.ic_date_range_24px;
/*      */         break;
/*      */       case 10:
/* 1656 */         resId = R.drawable.ic_annotation_squiggly_black_24dp;
/*      */         break;
/*      */       case 12:
/* 1659 */         resId = R.drawable.ic_annotation_stamp_black_24dp;
/*      */         break;
/*      */       case 13:
/* 1662 */         resId = R.drawable.ic_annotation_caret_black_24dp;
/*      */         break;
/*      */       case 25:
/* 1665 */         resId = R.drawable.ic_annotation_redact_black_24dp;
/*      */         break;
/*      */       case 1002:
/* 1668 */         resId = R.drawable.ic_annotation_signature_black_24dp;
/*      */         break;
/*      */       case 1003:
/* 1671 */         resId = R.drawable.ic_annotation_eraser_black_24dp;
/*      */         break;
/*      */       case 1004:
/* 1674 */         resId = R.drawable.ic_annotation_free_highlight_black_24dp;
/*      */         break;
/*      */       case 17:
/* 1677 */         resId = R.drawable.ic_mic_black_24dp;
/*      */         break;
/*      */       case 16:
/* 1680 */         resId = R.drawable.ic_attach_file_black_24dp;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1686 */     return resId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAnnotColor(Annot annot) {
/*      */     int color;
/*      */     try {
/* 1698 */       int type = annot.getType();
/* 1699 */       ColorPt colorPt = annot.getColorAsRGB();
/* 1700 */       color = Utils.colorPt2color(colorPt);
/* 1701 */       if (type == 2) {
/* 1702 */         FreeText freeText = new FreeText(annot);
/* 1703 */         if (freeText.getTextColorCompNum() == 3) {
/* 1704 */           ColorPt fillColorPt = freeText.getTextColor();
/* 1705 */           color = Utils.colorPt2color(fillColorPt);
/*      */         } 
/*      */       } 
/* 1708 */       if (annot.isMarkup()) {
/*      */         
/* 1710 */         Markup m = new Markup(annot);
/* 1711 */         if (m.getInteriorColorCompNum() == 3) {
/* 1712 */           ColorPt fillColorPt = m.getInteriorColor();
/* 1713 */           int fillColor = Utils.colorPt2color(fillColorPt);
/* 1714 */           if (fillColor != 0) {
/* 1715 */             color = fillColor;
/*      */           }
/*      */         } 
/*      */       } 
/* 1719 */     } catch (Exception e) {
/* 1720 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1721 */       color = -16777216;
/*      */     } 
/* 1723 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getAnnotOpacity(Annot annot) {
/* 1733 */     float opacity = 1.0F;
/*      */     try {
/* 1735 */       if (annot.isMarkup()) {
/* 1736 */         Markup m = new Markup(annot);
/* 1737 */         opacity = (float)m.getOpacity();
/*      */       } 
/* 1739 */     } catch (Exception e) {
/* 1740 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1741 */       opacity = 1.0F;
/*      */     } 
/* 1743 */     return opacity;
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
/*      */   public static Rect computeAnnotInbox(PDFViewCtrl pdfViewCtrl, Annot annot, int pg) {
/*      */     try {
/* 1756 */       Rect r = annot.getRect();
/* 1757 */       Rect ur = new Rect();
/* 1758 */       r.normalize();
/*      */       
/* 1760 */       double[] pts = pdfViewCtrl.convPagePtToScreenPt(r.getX1(), r.getY2(), pg);
/* 1761 */       ur.setX1(pts[0]);
/* 1762 */       ur.setY1(pts[1]);
/* 1763 */       pts = pdfViewCtrl.convPagePtToScreenPt(r.getX2(), r.getY1(), pg);
/* 1764 */       ur.setX2(pts[0]);
/* 1765 */       ur.setY2(pts[1]);
/* 1766 */       return ur;
/* 1767 */     } catch (PDFNetException e) {
/* 1768 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       
/* 1770 */       return null;
/*      */     } 
/*      */   }
/*      */   public static Rect quadToRect(QuadPoint qp) throws PDFNetException {
/* 1774 */     float x1 = (float)Math.min(Math.min(Math.min(qp.p1.x, qp.p2.x), qp.p3.x), qp.p4.x);
/* 1775 */     float y1 = (float)Math.min(Math.min(Math.min(qp.p1.y, qp.p2.y), qp.p3.y), qp.p4.y);
/* 1776 */     float x2 = (float)Math.max(Math.max(Math.max(qp.p1.x, qp.p2.x), qp.p3.x), qp.p4.x);
/* 1777 */     float y2 = (float)Math.max(Math.max(Math.max(qp.p1.y, qp.p2.y), qp.p3.y), qp.p4.y);
/* 1778 */     Rect quadRect = new Rect(x1, y1, x2, y2);
/* 1779 */     quadRect.normalize();
/* 1780 */     return quadRect;
/*      */   }
/*      */   
/*      */   public static Rect getPageRectFromScreenRect(PDFViewCtrl pdfViewCtrl, Rect screenRect, int pg) throws PDFNetException {
/* 1784 */     if (null == pdfViewCtrl || null == screenRect) {
/* 1785 */       return null;
/*      */     }
/* 1787 */     screenRect.normalize();
/*      */     
/* 1789 */     double[] pts1 = pdfViewCtrl.convScreenPtToPagePt(screenRect.getX1(), screenRect.getY2(), pg);
/*      */     
/* 1791 */     double[] pts2 = pdfViewCtrl.convScreenPtToPagePt(screenRect.getX2(), screenRect.getY1(), pg);
/* 1792 */     Rect result = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/* 1793 */     result.normalize();
/* 1794 */     return result;
/*      */   }
/*      */   
/*      */   public static RectF getScreenRectFromPageRect(PDFViewCtrl pdfViewCtrl, Rect pageRect, int pg) throws PDFNetException {
/* 1798 */     if (null == pdfViewCtrl || null == pageRect) {
/* 1799 */       return null;
/*      */     }
/* 1801 */     RectF rectF = new RectF();
/* 1802 */     pageRect.normalize();
/*      */     
/* 1804 */     double[] pts = pdfViewCtrl.convPagePtToScreenPt(pageRect.getX1(), pageRect.getY2(), pg);
/* 1805 */     rectF.left = (float)pts[0];
/* 1806 */     rectF.top = (float)pts[1];
/* 1807 */     pts = pdfViewCtrl.convPagePtToScreenPt(pageRect.getX2(), pageRect.getY1(), pg);
/* 1808 */     rectF.right = (float)pts[0];
/* 1809 */     rectF.bottom = (float)pts[1];
/* 1810 */     return rectF;
/*      */   }
/*      */   
/*      */   private static JSONObject createFreeTextJson(FreeTextCacheStruct freeTextCacheStruct) {
/* 1814 */     JSONObject object = new JSONObject();
/*      */     try {
/* 1816 */       object.put("contents", freeTextCacheStruct.contents);
/* 1817 */       object.put("pageNum", freeTextCacheStruct.pageNum);
/* 1818 */       JSONObject targetPoint = new JSONObject();
/* 1819 */       targetPoint.put("x", freeTextCacheStruct.x);
/* 1820 */       targetPoint.put("y", freeTextCacheStruct.y);
/* 1821 */       object.put("targetPoint", targetPoint);
/* 1822 */     } catch (Exception e) {
/* 1823 */       e.printStackTrace();
/* 1824 */       object = new JSONObject();
/*      */     } 
/* 1826 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveFreeTextCache(FreeTextCacheStruct freeTextCacheStruct, PDFViewCtrl pdfViewCtrl) {
/* 1836 */     if (null == freeTextCacheStruct || null == pdfViewCtrl) {
/*      */       return;
/*      */     }
/* 1839 */     if (pdfViewCtrl.getToolManager() == null) {
/*      */       return;
/*      */     }
/* 1842 */     if (Utils.isNullOrEmpty(freeTextCacheStruct.contents)) {
/*      */       return;
/*      */     }
/* 1845 */     String cacheFileName = ((ToolManager)pdfViewCtrl.getToolManager()).getFreeTextCacheFileName();
/*      */     
/* 1847 */     JSONObject obj = createFreeTextJson(freeTextCacheStruct);
/* 1848 */     ObjectOutput out = null;
/*      */     try {
/* 1850 */       if (!cacheFileName.trim().isEmpty()) {
/* 1851 */         out = new ObjectOutputStream(new FileOutputStream(new File(pdfViewCtrl.getContext().getCacheDir(), "") + cacheFileName));
/* 1852 */         out.writeObject(obj.toString());
/*      */       } 
/* 1854 */     } catch (Exception e) {
/* 1855 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 1856 */       e.printStackTrace();
/*      */     } finally {
/* 1858 */       if (out != null) {
/*      */         try {
/* 1860 */           out.close();
/* 1861 */         } catch (Exception exception) {}
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
/*      */   public static int getAnnotationCountOnPage(@NonNull PDFViewCtrl pdfViewCtrl, int pageNum, @Nullable ArrayList<Integer> excludeType) throws PDFNetException {
/* 1873 */     int count = 0;
/* 1874 */     ArrayList<Annot> annotsOnPage = pdfViewCtrl.getAnnotationsOnPage(pageNum);
/* 1875 */     for (Annot annot : annotsOnPage) {
/* 1876 */       int type = annot.getType();
/* 1877 */       if (excludeType != null && 
/* 1878 */         excludeType.contains(Integer.valueOf(type))) {
/*      */         continue;
/*      */       }
/*      */       
/* 1882 */       count++;
/*      */     } 
/* 1884 */     return count;
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
/*      */   public static void safeDeleteAnnotsOnPage(PDFDoc doc, ArrayList<Integer> pages) {
/* 1896 */     if (null == doc || null == pages) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1900 */       for (Iterator<Integer> iterator = pages.iterator(); iterator.hasNext(); ) { int pageNum = ((Integer)iterator.next()).intValue();
/* 1901 */         if (pageNum > -1) {
/* 1902 */           pageNum++;
/* 1903 */           Page page = doc.getPage(pageNum);
/* 1904 */           if (page.isValid()) {
/* 1905 */             int annotationCount = page.getNumAnnots();
/* 1906 */             for (int a = annotationCount - 1; a >= 0; a--) {
/*      */               try {
/* 1908 */                 Annot annotation = page.getAnnot(a);
/* 1909 */                 if (annotation != null && annotation.isValid())
/*      */                 {
/*      */                   
/* 1912 */                   if (annotation.getType() != 1 && annotation
/* 1913 */                     .getType() != 19)
/* 1914 */                     page.annotRemove(annotation); 
/*      */                 }
/* 1916 */               } catch (PDFNetException pDFNetException) {}
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }  }
/*      */     
/* 1923 */     } catch (Exception ignored) {
/* 1924 */       ignored.printStackTrace();
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
/*      */   public static void safeDeleteAllAnnots(PDFDoc doc) {
/* 1936 */     if (null == doc) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1940 */       PageIterator pageIterator = doc.getPageIterator();
/* 1941 */       while (pageIterator.hasNext()) {
/* 1942 */         Page page = pageIterator.next();
/* 1943 */         if (page.isValid()) {
/* 1944 */           int annotationCount = page.getNumAnnots();
/* 1945 */           for (int a = annotationCount - 1; a >= 0; a--) {
/*      */             try {
/* 1947 */               Annot annotation = page.getAnnot(a);
/* 1948 */               if (annotation != null && annotation.isValid())
/*      */               {
/*      */                 
/* 1951 */                 if (annotation.getType() != 1 && annotation
/* 1952 */                   .getType() != 19)
/* 1953 */                   page.annotRemove(annotation); 
/*      */               }
/* 1955 */             } catch (PDFNetException pDFNetException) {}
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/* 1961 */     } catch (Exception ignored) {
/* 1962 */       ignored.printStackTrace();
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
/*      */   public static void setAuthor(Annot annot, String author) {
/*      */     try {
/* 1976 */       if (annot != null && annot.isMarkup()) {
/* 1977 */         Markup markup = new Markup(annot);
/* 1978 */         setAuthor(markup, author);
/*      */       } 
/* 1980 */     } catch (Exception e) {
/* 1981 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getAnnotLocalDate(@NonNull Annot annot) throws PDFNetException {
/* 1992 */     Date date = annot.getDate();
/* 1993 */     return getLocalDate(date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getLocalDate(@NonNull Date date) throws PDFNetException {
/* 2003 */     Calendar calendar = Calendar.getInstance();
/* 2004 */     int month = date.getMonth() - 1;
/* 2005 */     calendar.set(date.getYear(), month, date.getDay(), date.getHour(), date.getMinute(), date.getSecond());
/* 2006 */     int offset = TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings();
/* 2007 */     long localTime = calendar.getTimeInMillis() + offset;
/* 2008 */     return new Date(localTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getAnnotLocalCreationDate(@NonNull Annot annot) throws PDFNetException {
/* 2018 */     Date date = annot.getDate();
/* 2019 */     if (annot.isMarkup()) {
/* 2020 */       date = (new Markup(annot)).getCreationDates();
/*      */     }
/* 2022 */     Calendar calendar = Calendar.getInstance();
/* 2023 */     int month = date.getMonth() - 1;
/* 2024 */     calendar.set(date.getYear(), month, date.getDay(), date.getHour(), date.getMinute(), date.getSecond());
/* 2025 */     int offset = TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings();
/* 2026 */     long localTime = calendar.getTimeInMillis() + offset;
/* 2027 */     return new Date(localTime);
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
/*      */   public static void setAuthor(Markup markup, String author) {
/* 2039 */     if (markup == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 2043 */       markup.setTitle(author);
/* 2044 */     } catch (Exception e) {
/* 2045 */       e.printStackTrace();
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
/*      */   public static void setUniqueId(Annot annot, String id) {
/*      */     try {
/* 2059 */       if (annot != null) {
/* 2060 */         annot.setUniqueID(id);
/*      */       }
/* 2062 */     } catch (Exception e) {
/* 2063 */       e.printStackTrace();
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
/*      */   @Nullable
/*      */   public static ArrayList<Point> getPolyVertices(Annot annot) {
/*      */     try {
/* 2077 */       if (annot == null) {
/* 2078 */         return null;
/*      */       }
/* 2080 */       if (annot.getType() == 7 || annot
/* 2081 */         .getType() == 6) {
/* 2082 */         PolyLine polyLine = new PolyLine(annot);
/* 2083 */         int count = polyLine.getVertexCount();
/*      */         
/* 2085 */         ArrayList<Point> points = new ArrayList<>();
/* 2086 */         for (int i = 0; i < count; i++) {
/* 2087 */           Point pagePoint = polyLine.getVertex(i);
/* 2088 */           points.add(pagePoint);
/*      */         } 
/* 2090 */         return points;
/*      */       } 
/* 2092 */     } catch (Exception ex) {
/* 2093 */       ex.printStackTrace();
/*      */     } 
/* 2095 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMadeByPDFTron(Annot annot) throws PDFNetException {
/* 2102 */     String[] supportedTag = { "pdftron", "pdftronlink" };
/* 2103 */     if (annot != null && annot.getSDFObj() != null) {
/* 2104 */       Obj sdfObj = annot.getSDFObj();
/* 2105 */       for (String tag : supportedTag) {
/* 2106 */         Object selfMadeObj = sdfObj.findObj(tag);
/* 2107 */         if (selfMadeObj != null) {
/* 2108 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 2112 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasReplyTypeReply(Annot annot) throws PDFNetException {
/* 2122 */     if (annot == null) {
/* 2123 */       return false;
/*      */     }
/* 2125 */     Obj annotSDFObj = annot.getSDFObj();
/* 2126 */     if (annotSDFObj != null) {
/* 2127 */       Obj irt = annotSDFObj.findObj(KEY_InReplyTo);
/*      */ 
/*      */       
/* 2130 */       return (irt != null && !hasReplyTypeGroup(annot));
/*      */     } 
/* 2132 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Annot createAnnotationStateReply(@NonNull String parentAnnotId, int pageNum, @NonNull PDFViewCtrl pdfViewCtrl, @NonNull String authorId, @NonNull AnnotReviewState state) throws PDFNetException {
/* 2139 */     Resources resources = pdfViewCtrl.getContext().getResources();
/* 2140 */     String stateStr = "";
/* 2141 */     String stateStrKey = Key_StateNone;
/* 2142 */     switch (state) {
/*      */       case ACCEPTED:
/* 2144 */         stateStr = resources.getString(R.string.annot_review_state_accepted);
/* 2145 */         stateStrKey = Key_StateAccepted;
/*      */         break;
/*      */       case REJECTED:
/* 2148 */         stateStr = resources.getString(R.string.annot_review_state_rejected);
/* 2149 */         stateStrKey = Key_StateRejected;
/*      */         break;
/*      */       case CANCELLED:
/* 2152 */         stateStr = resources.getString(R.string.annot_review_state_cancelled);
/* 2153 */         stateStrKey = Key_StateCancelled;
/*      */         break;
/*      */       case COMPLETED:
/* 2156 */         stateStr = resources.getString(R.string.annot_review_state_completed);
/* 2157 */         stateStrKey = Key_StateCompleted;
/*      */         break;
/*      */       case NONE:
/* 2160 */         stateStr = resources.getString(R.string.annot_review_state_none);
/* 2161 */         stateStrKey = Key_StateNone;
/*      */         break;
/*      */     } 
/* 2164 */     String replyMessage = String.format(resources.getString(R.string.annot_review_state_set_by), new Object[] { stateStr, authorId });
/*      */     
/* 2166 */     Annot reply = createAnnotationReply(parentAnnotId, pageNum, pdfViewCtrl, authorId, replyMessage);
/*      */     
/* 2168 */     if (reply != null) {
/* 2169 */       boolean shouldUnlock = false;
/*      */       try {
/* 2171 */         pdfViewCtrl.docLock(true);
/* 2172 */         shouldUnlock = true;
/* 2173 */         reply.getSDFObj().putString(Key_State, stateStrKey);
/* 2174 */         reply.getSDFObj().putString(Key_StateModel, Key_StateModelReview);
/*      */       } finally {
/* 2176 */         if (shouldUnlock) {
/* 2177 */           pdfViewCtrl.docUnlock();
/*      */         }
/*      */       } 
/*      */     } 
/* 2181 */     return reply;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Annot createAnnotationReply(@NonNull String parentAnnotId, int pageNum, @NonNull PDFViewCtrl pdfViewCtrl, @NonNull String authorId, @NonNull String contents) throws PDFNetException {
/* 2188 */     Annot parent = ViewerUtils.getAnnotById(pdfViewCtrl, parentAnnotId, pageNum);
/* 2189 */     if (parent == null) {
/* 2190 */       return null;
/*      */     }
/* 2192 */     boolean shouldUnlock = false;
/*      */     try {
/* 2194 */       if (!parent.isValid()) {
/* 2195 */         return null;
/*      */       }
/* 2197 */       pdfViewCtrl.docLock(true);
/* 2198 */       shouldUnlock = true;
/* 2199 */       Rect rect = parent.getRect();
/* 2200 */       rect.normalize();
/* 2201 */       double left = rect.getX1();
/* 2202 */       double top = rect.getY2();
/*      */       
/* 2204 */       Point p = new Point(left, top);
/* 2205 */       Text reply = Text.create((Doc)pdfViewCtrl.getDoc(), p);
/* 2206 */       setAuthor((Markup)reply, authorId);
/* 2207 */       reply.getSDFObj().putString(KEY_InReplyTo, parentAnnotId);
/* 2208 */       Rect popupRect = new Rect();
/* 2209 */       popupRect.set(left + 20.0D, top + 20.0D, left + 90.0D, top + 90.0D);
/* 2210 */       Popup popup = Popup.create((Doc)pdfViewCtrl.getDoc(), popupRect);
/* 2211 */       popup.setParent((Annot)reply);
/* 2212 */       popup.setContents(contents);
/* 2213 */       reply.setPopup(popup);
/*      */       
/* 2215 */       Page page = pdfViewCtrl.getDoc().getPage(pageNum);
/* 2216 */       page.annotPushBack((Annot)reply);
/* 2217 */       page.annotPushBack((Annot)popup);
/*      */       
/* 2219 */       return (Annot)reply;
/*      */     } finally {
/* 2221 */       if (shouldUnlock) {
/* 2222 */         pdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Annot updateAnnotationReply(@NonNull String replyId, int pageNum, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable ToolManager toolManager, @NonNull String contents) throws PDFNetException {
/* 2231 */     Annot reply = ViewerUtils.getAnnotById(pdfViewCtrl, replyId, pageNum);
/* 2232 */     if (reply == null || !reply.isMarkup()) {
/* 2233 */       return null;
/*      */     }
/* 2235 */     boolean shouldUnlock = false;
/*      */     try {
/* 2237 */       pdfViewCtrl.docLock(true);
/* 2238 */       shouldUnlock = true;
/* 2239 */       Markup markup = new Markup(reply);
/* 2240 */       HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2241 */       annots.put(reply, Integer.valueOf(pageNum));
/* 2242 */       if (toolManager != null) {
/* 2243 */         toolManager.raiseAnnotationsPreModifyEvent(annots);
/*      */       }
/*      */       
/* 2246 */       Utils.handleEmptyPopup(pdfViewCtrl, markup);
/* 2247 */       Popup popup = markup.getPopup();
/* 2248 */       popup.setContents(contents);
/* 2249 */       setDateToNow(pdfViewCtrl, (Annot)markup);
/*      */       
/* 2251 */       if (toolManager != null) {
/* 2252 */         toolManager.raiseAnnotationsModifiedEvent(annots, new Bundle());
/*      */       }
/* 2254 */       return reply;
/*      */     } finally {
/* 2256 */       if (shouldUnlock) {
/* 2257 */         pdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void deleteAnnotationReply(@NonNull String replyId, int pageNum, @NonNull PDFViewCtrl pdfViewCtrl, @Nullable ToolManager toolManager) throws PDFNetException {
/* 2265 */     Annot reply = ViewerUtils.getAnnotById(pdfViewCtrl, replyId, pageNum);
/* 2266 */     if (reply == null) {
/*      */       return;
/*      */     }
/* 2269 */     boolean shouldUnlock = false;
/*      */     try {
/* 2271 */       pdfViewCtrl.docLock(true);
/* 2272 */       shouldUnlock = true;
/* 2273 */       HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 2274 */       annots.put(reply, Integer.valueOf(pageNum));
/* 2275 */       if (toolManager != null) {
/* 2276 */         toolManager.raiseAnnotationsPreRemoveEvent(annots);
/*      */       }
/*      */       
/* 2279 */       Page page = pdfViewCtrl.getDoc().getPage(pageNum);
/* 2280 */       page.annotRemove(reply);
/* 2281 */       if (toolManager != null) {
/* 2282 */         toolManager.raiseAnnotationsRemovedEvent(annots);
/*      */       }
/*      */     } finally {
/* 2285 */       if (shouldUnlock) {
/* 2286 */         pdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setDateToNow(PDFViewCtrl pdfViewCtrl, Annot annot) {
/* 2292 */     boolean shouldUnlock = false;
/*      */     try {
/* 2294 */       pdfViewCtrl.docLock(true);
/* 2295 */       shouldUnlock = true;
/* 2296 */       annot.setDateToNow();
/* 2297 */     } catch (Exception e) {
/* 2298 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 2300 */       if (shouldUnlock) {
/* 2301 */         pdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static String getIRTAsString(Annot annot) throws PDFNetException {
/* 2311 */     if (annot == null) {
/* 2312 */       return null;
/*      */     }
/* 2314 */     Obj annotSDFObj = annot.getSDFObj();
/* 2315 */     if (annotSDFObj != null) {
/* 2316 */       Obj irt = annotSDFObj.findObj(KEY_InReplyTo);
/* 2317 */       if (irt != null) {
/*      */         
/* 2319 */         if (irt.isString()) {
/* 2320 */           return irt.getAsPDFText();
/*      */         }
/*      */         
/* 2323 */         if (irt.isDict()) {
/* 2324 */           Obj nm = irt.findObj(KEY_NM);
/* 2325 */           if (nm != null && nm.isString()) {
/* 2326 */             return nm.getAsPDFText();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 2331 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasReplyTypeGroup(Annot annot) throws PDFNetException {
/* 2341 */     if (annot == null) {
/* 2342 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2347 */     Obj annotSDFObj = annot.getSDFObj();
/* 2348 */     if (annotSDFObj != null) {
/* 2349 */       Obj irt = annotSDFObj.findObj(KEY_InReplyTo);
/* 2350 */       Obj rt = annotSDFObj.findObj(KEY_ReplyTo);
/* 2351 */       if (irt != null && rt != null && rt.isName()) {
/* 2352 */         String rtVal = rt.getName();
/* 2353 */         return VALUE_Group.equals(rtVal);
/*      */       } 
/*      */     } 
/* 2356 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isGroupSelected(PDFViewCtrl pdfViewCtrl, ArrayList<Annot> selected, int page) throws PDFNetException {
/* 2366 */     if (null == pdfViewCtrl || null == selected) {
/* 2367 */       return false;
/*      */     }
/* 2369 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 2371 */       pdfViewCtrl.docLockRead();
/* 2372 */       shouldUnlockRead = true;
/*      */       
/* 2374 */       for (Annot annot : selected) {
/* 2375 */         ArrayList<Annot> annotsInGroup = getAnnotationsInGroup(pdfViewCtrl, annot, page);
/* 2376 */         if (null == annotsInGroup || annotsInGroup.isEmpty()) {
/* 2377 */           return false;
/*      */         }
/* 2379 */         if (selected.size() != annotsInGroup.size()) {
/* 2380 */           return false;
/*      */         }
/* 2382 */         if (!annotsInGroup.containsAll(selected)) {
/* 2383 */           return false;
/*      */         }
/*      */       } 
/*      */     } finally {
/* 2387 */       if (shouldUnlockRead) {
/* 2388 */         pdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/* 2391 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createAnnotationGroup(PDFViewCtrl pdfViewCtrl, Annot primary, ArrayList<Annot> allInGroup) throws PDFNetException {
/* 2401 */     if (null == pdfViewCtrl || null == primary || null == allInGroup) {
/*      */       return;
/*      */     }
/* 2404 */     boolean shouldUnlock = false;
/*      */     try {
/* 2406 */       pdfViewCtrl.docLock(true);
/* 2407 */       shouldUnlock = true;
/* 2408 */       for (Annot ann : allInGroup) {
/*      */         
/* 2410 */         if (ann.getUniqueID() == null) {
/* 2411 */           setUniqueId(ann, UUID.randomUUID().toString());
/*      */         }
/* 2413 */         if (ann.equals(primary)) {
/*      */           
/* 2415 */           ann.getSDFObj().erase(KEY_ReplyTo);
/* 2416 */           ann.getSDFObj().erase(KEY_InReplyTo); continue;
/*      */         } 
/* 2418 */         ann.getSDFObj().putName(KEY_ReplyTo, VALUE_Group);
/* 2419 */         ann.getSDFObj().put(KEY_InReplyTo, primary.getSDFObj());
/*      */       } 
/*      */     } finally {
/*      */       
/* 2423 */       if (shouldUnlock) {
/* 2424 */         pdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void ungroupAnnotations(PDFViewCtrl pdfViewCtrl, ArrayList<Annot> allInGroup) throws PDFNetException {
/* 2430 */     if (null == pdfViewCtrl || null == allInGroup) {
/*      */       return;
/*      */     }
/* 2433 */     boolean shouldUnlock = false;
/*      */     try {
/* 2435 */       pdfViewCtrl.docLock(true);
/* 2436 */       shouldUnlock = true;
/* 2437 */       for (Annot ann : allInGroup) {
/*      */         
/* 2439 */         ann.getSDFObj().erase(KEY_ReplyTo);
/* 2440 */         ann.getSDFObj().erase(KEY_InReplyTo);
/*      */       } 
/*      */     } finally {
/* 2443 */       if (shouldUnlock) {
/* 2444 */         pdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static Annot getPrimaryAnnotInGroup(PDFViewCtrl pdfViewCtrl, ArrayList<Annot> annots) throws PDFNetException {
/* 2451 */     if (null == pdfViewCtrl || null == annots) {
/* 2452 */       return null;
/*      */     }
/* 2454 */     for (Annot ann : annots) {
/* 2455 */       if (!hasReplyTypeGroup(ann)) {
/* 2456 */         return ann;
/*      */       }
/*      */     } 
/* 2459 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static ArrayList<Annot> getAnnotationsInGroup(PDFViewCtrl pdfViewCtrl, Annot annot, int pageNum) throws PDFNetException {
/* 2467 */     if (null == pdfViewCtrl || null == annot) {
/* 2468 */       return null;
/*      */     }
/* 2470 */     if (!annot.isValid()) {
/* 2471 */       return null;
/*      */     }
/* 2473 */     boolean shouldUnlockRead = false;
/*      */     try {
/* 2475 */       ArrayList<Annot> annotsInGroup = new ArrayList<>();
/* 2476 */       pdfViewCtrl.docLockRead();
/* 2477 */       shouldUnlockRead = true;
/*      */       
/* 2479 */       String mainAnnotId = null;
/* 2480 */       if (hasReplyTypeGroup(annot)) {
/*      */ 
/*      */         
/* 2483 */         mainAnnotId = getIRTAsString(annot);
/* 2484 */       } else if (annot.getUniqueID() != null) {
/*      */         
/* 2486 */         mainAnnotId = annot.getUniqueID().getAsPDFText();
/*      */       } 
/* 2488 */       if (Utils.isNullOrEmpty(mainAnnotId)) {
/* 2489 */         return null;
/*      */       }
/*      */       
/* 2492 */       ArrayList<Annot> annotsInPage = pdfViewCtrl.getAnnotationsOnPage(pageNum);
/* 2493 */       for (Annot ann : annotsInPage) {
/* 2494 */         if (ann != null && ann.isValid() && ann.getUniqueID() != null) {
/*      */           
/* 2496 */           String id = ann.getUniqueID().getAsPDFText();
/* 2497 */           if (id != null && id.equals(mainAnnotId))
/*      */           {
/* 2499 */             annotsInGroup.add(ann);
/*      */           }
/* 2501 */           String irt = getIRTAsString(ann);
/* 2502 */           if (hasReplyTypeGroup(ann) && irt != null && irt.equals(mainAnnotId))
/*      */           {
/* 2504 */             annotsInGroup.add(ann);
/*      */           }
/*      */         } 
/*      */       } 
/* 2508 */       return annotsInGroup;
/*      */     } finally {
/* 2510 */       if (shouldUnlockRead) {
/* 2511 */         pdfViewCtrl.docUnlockRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canUseBitmapAppearance(Annot annot) throws PDFNetException {
/* 2520 */     int annotType = annot.getType();
/* 2521 */     return (annotType == 12 || annotType == 0 || annotType == 2 || annotType == 17 || annotType == 16 || annotType == 25 || annotType == 19 || annotType == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Single<Bitmap> getAnnotationAppearanceAsync(final PDFViewCtrl pdfViewCtrl, final Annot annot) {
/* 2532 */     return Single.create(new SingleOnSubscribe<Bitmap>()
/*      */         {
/*      */           public void subscribe(SingleEmitter<Bitmap> emitter) throws Exception {
/* 2535 */             Bitmap bitmap = AnnotUtils.getAnnotationAppearance(pdfViewCtrl, annot);
/* 2536 */             if (bitmap != null) {
/* 2537 */               emitter.onSuccess(bitmap);
/*      */             } else {
/* 2539 */               emitter.tryOnError(new IllegalStateException("Invalid state when creating annotation appearance PDFViewCtrl"));
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static Bitmap getAnnotationAppearance(PDFViewCtrl pdfViewCtrl, Annot annot) {
/* 2547 */     if (null == pdfViewCtrl || null == annot) {
/* 2548 */       return null;
/*      */     }
/* 2550 */     boolean shouldUnlockRead = false;
/* 2551 */     PDFDoc doc = null;
/*      */     
/* 2553 */     try { pdfViewCtrl.docLockRead();
/* 2554 */       shouldUnlockRead = true;
/*      */       
/* 2556 */       if (!annot.isValid() || !canUseBitmapAppearance(annot)) {
/* 2557 */         return null;
/*      */       }
/*      */       
/* 2560 */       PDFDraw draw = new PDFDraw();
/* 2561 */       double dpi = pdfViewCtrl.getZoom() * 72.0D * (pdfViewCtrl.getContext().getResources().getDisplayMetrics()).density;
/* 2562 */       draw.setDPI(Math.min(dpi, 360.0D));
/* 2563 */       draw.setPageTransparent(true);
/* 2564 */       draw.setAntiAliasing(true);
/*      */       
/* 2566 */       Rect annotRect = annot.getRect();
/*      */ 
/*      */       
/* 2569 */       doc = new PDFDoc();
/* 2570 */       Rect pageRect = new Rect(0.0D, 0.0D, annotRect.getWidth(), annotRect.getHeight());
/* 2571 */       Page page = doc.pageCreate(pageRect);
/* 2572 */       doc.pagePushBack(page);
/*      */ 
/*      */       
/* 2575 */       Obj srcAnnotation = annot.getSDFObj();
/* 2576 */       Obj pEntry = srcAnnotation.findObj("P");
/*      */       
/* 2578 */       Obj[] objList = { srcAnnotation };
/*      */ 
/*      */ 
/*      */       
/* 2582 */       Obj[] exclList = null;
/* 2583 */       if (pEntry != null) {
/* 2584 */         exclList = new Obj[] { pEntry };
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2589 */         Page p = new Page(pEntry);
/* 2590 */         int pageRotation = 0;
/* 2591 */         int viewRotation = pdfViewCtrl.getPageRotation();
/* 2592 */         if (p.isValid()) {
/* 2593 */           pageRotation = p.getRotation();
/*      */         }
/* 2595 */         int annotRotation = (pageRotation + viewRotation) % 4;
/* 2596 */         if (!annot.getFlag(4)) {
/* 2597 */           page.setRotation(annotRotation);
/*      */         }
/*      */       } 
/* 2600 */       Obj[] destAnnot = doc.getSDFDoc().importObjs(objList, exclList);
/* 2601 */       if (destAnnot != null && destAnnot.length > 0) {
/* 2602 */         Annot dest = new Annot(destAnnot[0]);
/* 2603 */         dest.setRect(pageRect);
/* 2604 */         page.annotPushBack(dest);
/* 2605 */         if (!annot.getFlag(4)) {
/* 2606 */           dest.setRotation(annot.getRotation());
/*      */         }
/* 2608 */         dest.flatten(page);
/*      */         
/* 2610 */         if (annot.getFlag(3) || annot.getType() == 0) {
/*      */           
/* 2612 */           Rect visibleRect = page.getVisibleContentBox();
/*      */ 
/*      */           
/* 2615 */           double width = visibleRect.getWidth();
/* 2616 */           double height = visibleRect.getHeight();
/* 2617 */           if (width < height) {
/* 2618 */             width = height * annotRect.getWidth() / annotRect.getHeight();
/*      */             
/* 2620 */             double diff = Math.abs(visibleRect.getWidth() - width) * 0.5D;
/* 2621 */             visibleRect.setX1(visibleRect.getX1() - diff);
/* 2622 */             visibleRect.setX2(visibleRect.getX1() + width);
/*      */           } else {
/* 2624 */             height = width * annotRect.getWidth() / annotRect.getHeight();
/*      */             
/* 2626 */             double diff = Math.abs(visibleRect.getHeight() - height) * 0.5D;
/* 2627 */             visibleRect.setY1(visibleRect.getY1() - diff);
/* 2628 */             visibleRect.setY2(visibleRect.getY1() + height);
/*      */           } 
/*      */           
/* 2631 */           page.setCropBox(visibleRect);
/* 2632 */           page.setMediaBox(visibleRect);
/*      */         } 
/*      */         
/* 2635 */         return draw.getBitmap(page);
/*      */       }  }
/* 2637 */     catch (Exception exception) {  }
/* 2638 */     catch (OutOfMemoryError oom)
/* 2639 */     { Utils.manageOOM(pdfViewCtrl); }
/*      */     finally
/* 2641 */     { Utils.closeQuietly(doc);
/* 2642 */       if (shouldUnlockRead) {
/* 2643 */         pdfViewCtrl.docUnlockRead();
/*      */       } }
/*      */     
/* 2646 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void putStampDegree(Annot annot, int rotation) throws PDFNetException {
/* 2654 */     Obj stampObj = annot.getSDFObj();
/* 2655 */     stampObj.putNumber("pdftronImageStampRotationDegree", rotation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStampDegree(Annot annot) throws PDFNetException {
/* 2663 */     Obj stampObj = annot.getSDFObj();
/* 2664 */     Obj rotationObj = stampObj.findObj("pdftronImageStampRotationDegree");
/* 2665 */     int rotation = 0;
/* 2666 */     if (rotationObj != null && rotationObj.isNumber()) {
/* 2667 */       rotation = (int)rotationObj.getNumber();
/* 2668 */       return rotation;
/*      */     } 
/*      */     
/* 2671 */     int oldDegree = getStampDegreeOld(annot);
/*      */ 
/*      */     
/* 2674 */     switch (oldDegree) {
/*      */       case 90:
/* 2676 */         return 270;
/*      */       case 180:
/* 2678 */         return 180;
/*      */       case 270:
/* 2680 */         return 90;
/*      */     } 
/* 2682 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStampDegreeOld(Annot annot) throws PDFNetException {
/* 2690 */     Obj stampObj = annot.getSDFObj();
/* 2691 */     Obj rotationObj = stampObj.findObj("pdftronImageStampRotation");
/* 2692 */     int rotation = 0;
/* 2693 */     if (rotationObj != null && rotationObj.isNumber()) {
/* 2694 */       rotation = (int)rotationObj.getNumber();
/*      */     }
/* 2696 */     return rotation;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compareCreationDate(Annot thisObj, Annot thatObj) {
/* 2702 */     if (thisObj == null || thatObj == null) {
/* 2703 */       return 0;
/*      */     }
/*      */     try {
/* 2706 */       Date thisDate = getAnnotLocalCreationDate(thisObj);
/* 2707 */       Date thatDate = getAnnotLocalCreationDate(thatObj);
/* 2708 */       return thisDate.compareTo(thatDate);
/* 2709 */     } catch (PDFNetException e) {
/* 2710 */       e.printStackTrace();
/*      */       
/* 2712 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int compareDate(Annot thisObj, Annot thatObj) {
/* 2718 */     if (thisObj == null || thatObj == null) {
/* 2719 */       return 0;
/*      */     }
/*      */     try {
/* 2722 */       Date thisDate = getAnnotLocalDate(thisObj);
/* 2723 */       Date thatDate = getAnnotLocalDate(thatObj);
/* 2724 */       return thisDate.compareTo(thatDate);
/* 2725 */     } catch (PDFNetException e) {
/* 2726 */       e.printStackTrace();
/*      */       
/* 2728 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setStyle(Annot annot, boolean hasFill, int strokeColor, int fillColor, float thickness, float opacity) throws PDFNetException {
/* 2734 */     ColorPt color = Utils.color2ColorPt(strokeColor);
/* 2735 */     annot.setColor(color, 3);
/*      */     
/* 2737 */     if (hasFill && annot instanceof Markup) {
/* 2738 */       color = Utils.color2ColorPt(fillColor);
/* 2739 */       if (fillColor == 0) {
/* 2740 */         ((Markup)annot).setInteriorColor(color, 0);
/*      */       } else {
/* 2742 */         ((Markup)annot).setInteriorColor(color, 3);
/*      */       } 
/*      */     } 
/*      */     
/* 2746 */     if (annot instanceof Markup) {
/* 2747 */       ((Markup)annot).setOpacity(opacity);
/*      */     }
/*      */     
/* 2750 */     Annot.BorderStyle bs = annot.getBorderStyle();
/* 2751 */     if (hasFill && strokeColor == 0) {
/* 2752 */       bs.setWidth(0.0D);
/*      */     } else {
/* 2754 */       bs.setWidth(thickness);
/*      */     } 
/* 2756 */     annot.setBorderStyle(bs);
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
/*      */   public static void traversePages(PDFDoc doc, PageVisitor visitor) {
/* 2771 */     if (doc == null || visitor == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 2775 */       PageIterator iterator = doc.getPageIterator();
/* 2776 */       while (iterator.hasNext()) {
/* 2777 */         Page page = iterator.next();
/* 2778 */         if (page != null && page.isValid()) {
/* 2779 */           visitor.visit(page);
/*      */         }
/*      */       } 
/* 2782 */     } catch (Exception e) {
/* 2783 */       e.printStackTrace();
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
/*      */   public static void traverseAnnots(Page page, AnnotVisitor visitor) {
/* 2799 */     if (page == null || visitor == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 2803 */       int numAnnots = page.getNumAnnots();
/* 2804 */       for (int i = 0; i < numAnnots; i++) {
/* 2805 */         Annot annot = page.getAnnot(i);
/* 2806 */         if (annot != null && annot.isValid()) {
/* 2807 */           visitor.visit(annot);
/*      */         }
/*      */       } 
/* 2810 */     } catch (Exception e) {
/* 2811 */       e.printStackTrace();
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
/*      */   public static void traverseAnnots(PDFDoc doc, final AnnotVisitor visitor) {
/* 2826 */     if (doc == null || visitor == null) {
/*      */       return;
/*      */     }
/* 2829 */     traversePages(doc, new PageVisitor()
/*      */         {
/*      */           public void visit(@NonNull Page page) {
/* 2832 */             AnnotUtils.traverseAnnots(page, visitor);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public static interface AnnotVisitor extends Visitor<Annot> {
/*      */     void visit(@NonNull Annot param1Annot);
/*      */   }
/*      */   
/*      */   public static interface PageVisitor extends Visitor<Page> {
/*      */     void visit(@NonNull Page param1Page);
/*      */   }
/*      */   
/*      */   private static interface Visitor<T> {
/*      */     void visit(@NonNull T param1T);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AnnotUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */