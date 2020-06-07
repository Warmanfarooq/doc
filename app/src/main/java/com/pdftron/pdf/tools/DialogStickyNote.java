/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.LayerDrawable;
/*     */ import android.util.Log;
/*     */ import android.view.GestureDetector;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.ImageView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.core.content.ContextCompat;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
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
/*     */ public class DialogStickyNote
/*     */   extends DialogAnnotNote
/*     */ {
/*     */   private ImageButton mStyleButton;
/*     */   private boolean mIconStyleChanged;
/*     */   private boolean mExistingNote;
/*     */   private int mIconColor;
/*     */   private float mIconOpacity;
/*     */   private String mIconType;
/*     */   private static final int TEXT_VIEW = 0;
/*     */   private AnnotStyleDialogFragment mAnnotStyleDialog;
/*     */   private AnnotStyle.OnAnnotStyleChangeListener mAnnotStyleChangeListener;
/*     */   
/*     */   public DialogStickyNote(@NonNull PDFViewCtrl pdfViewCtrl, String note, boolean existingNote, String iconType, int iconColor, float iconOpacity) {
/*  57 */     this(pdfViewCtrl, note, existingNote, iconType, iconColor, iconOpacity, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialogStickyNote(@NonNull PDFViewCtrl pdfViewCtrl, String note, boolean existingNote, String iconType, int iconColor, float iconOpacity, boolean hasPermission) {
/*  64 */     super(pdfViewCtrl, note, hasPermission); boolean showStyleIcon; this.mIconStyleChanged = false;
/*  65 */     FragmentActivity fragmentActivity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/*  66 */     if (fragmentActivity != null) {
/*  67 */       setOwnerActivity((Activity)fragmentActivity);
/*     */     } else {
/*  69 */       Log.e(DialogStickyNote.class.getName(), "ToolManager is not attached to with an Activity");
/*     */     } 
/*  71 */     this.mExistingNote = existingNote;
/*  72 */     this.mIconColor = iconColor;
/*  73 */     this.mIconType = iconType;
/*  74 */     this.mIconOpacity = iconOpacity;
/*     */     
/*  76 */     this.mGestureDetector = new GestureDetector(getContext(), (GestureDetector.OnGestureListener)new MyGestureDetector()
/*     */         {
/*     */           public boolean onSingleTapUp(MotionEvent e) {
/*  79 */             boolean superReturnValue = super.onSingleTapUp(e);
/*     */ 
/*     */ 
/*     */             
/*  83 */             if (DialogStickyNote.this.mIconStyleChanged) {
/*  84 */               DialogStickyNote.this.mPositiveButton.setEnabled(true);
/*     */             }
/*     */             
/*  87 */             return superReturnValue;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  92 */     this.mStyleButton = (ImageButton)this.mMainView.findViewById(R.id.tools_dialog_annotation_popup_button_style);
/*  93 */     this.mStyleButton.setImageDrawable((Drawable)createCurrentIcon());
/*  94 */     this.mStyleButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
/*  95 */     if (this.mHasPermission) {
/*  96 */       this.mStyleButton.setOnClickListener(new View.OnClickListener()
/*     */           {
/*     */             public void onClick(View view) {
/*  99 */               FragmentActivity activity = null;
/* 100 */               if (DialogStickyNote.this.getContext() instanceof FragmentActivity) {
/* 101 */                 activity = (FragmentActivity)DialogStickyNote.this.getContext();
/* 102 */               } else if (DialogStickyNote.this.mPdfViewCtrl != null) {
/* 103 */                 ToolManager toolManager = (ToolManager)DialogStickyNote.this.mPdfViewCtrl.getToolManager();
/* 104 */                 activity = toolManager.getCurrentActivity();
/*     */               } 
/* 106 */               if (activity == null) {
/* 107 */                 AnalyticsHandlerAdapter.getInstance().sendException(new Exception("DialogStickyNote is not attached with an Activity"));
/*     */                 return;
/*     */               } 
/* 110 */               if (DialogStickyNote.this.mAnnotStyleDialog == null) {
/*     */                 return;
/*     */               }
/* 113 */               DialogStickyNote.this.mAnnotStyleDialog.show(activity.getSupportFragmentManager(), 4, 
/*     */                   
/* 115 */                   AnalyticsHandlerAdapter.getInstance().getAnnotationTool(7));
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 120 */       addStyleView();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     TypedArray typedArray = getContext().obtainStyledAttributes(null, R.styleable.DialogStickyNote, 0, R.style.DialogStickyNoteStyle);
/*     */     try {
/* 127 */       showStyleIcon = typedArray.getBoolean(R.styleable.DialogStickyNote_showStyleIcon, true);
/*     */     } finally {
/* 129 */       typedArray.recycle();
/*     */     } 
/* 131 */     this.mStyleButton.setVisibility(showStyleIcon ? 0 : 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initTextBox(String note) {
/* 139 */     if (this.mExistingNote && !note.equals("")) {
/* 140 */       switchToViewMode();
/* 141 */       this.mTextBox.requestFocus();
/*     */     } else {
/* 143 */       Utils.showSoftKeyboard(getContext(), (View)this.mTextBox);
/*     */     } 
/*     */     
/* 146 */     if (!note.equals("")) {
/* 147 */       this.mTextBox.setText(note);
/*     */       
/* 149 */       this.mTextBox.setSelection(this.mTextBox.getText().length());
/*     */     } 
/*     */ 
/*     */     
/* 153 */     int noteBackgroundColor = getBackgroundColor();
/* 154 */     this.mTextBox.setBackgroundColor(noteBackgroundColor);
/*     */   }
/*     */   
/*     */   private int getBackgroundColor() {
/* 158 */     float[] hsv = new float[3];
/* 159 */     Color.colorToHSV(this.mIconColor, hsv);
/*     */     
/* 161 */     float hue = hsv[0];
/* 162 */     float saturation = hsv[1];
/* 163 */     float brightness = hsv[2];
/*     */ 
/*     */     
/* 166 */     int lightColor = ContextCompat.getColor(getContext(), R.color.tools_dialog_sticky_note_textbox_background);
/* 167 */     if ((saturation == 1.0F && brightness == 0.0F) || saturation == 0.0F || Utils.isDeviceNightMode(getContext())) {
/* 168 */       return lightColor;
/*     */     }
/*     */ 
/*     */     
/* 172 */     if (brightness > 0.8F) {
/* 173 */       saturation = 0.1F;
/* 174 */     } else if (brightness > 0.6D) {
/* 175 */       saturation = 0.12F;
/* 176 */     } else if (brightness > 0.4D) {
/* 177 */       saturation = 0.14F;
/* 178 */     } else if (brightness > 0.2D) {
/* 179 */       saturation = 0.16F;
/*     */     } else {
/* 181 */       saturation = 0.18F;
/*     */     } 
/* 183 */     brightness = 0.97F;
/*     */     
/* 185 */     float[] lightColorHSV = { hue, saturation, brightness };
/* 186 */     lightColor = Color.HSVToColor(lightColorHSV);
/*     */     
/* 188 */     return lightColor;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addStyleView() {
/* 193 */     AnnotStyle annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle(getContext(), 0, "");
/* 194 */     annotStyle.setIcon(this.mIconType);
/* 195 */     annotStyle.setStyle(this.mIconColor, 0, 0.0F, this.mIconOpacity);
/* 196 */     this.mAnnotStyleDialog = (new AnnotStyleDialogFragment.Builder(annotStyle)).build();
/* 197 */     this.mAnnotStyleDialog.setOnAnnotStyleChangeListener(new AnnotStyle.OnAnnotStyleChangeListener()
/*     */         {
/*     */           public void onChangeAnnotThickness(float thickness, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotTextSize(float textSize, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotTextColor(int textColor) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotOpacity(float opacity, boolean done) {
/* 215 */             DialogStickyNote.this.mIconOpacity = opacity;
/* 216 */             DialogStickyNote.this.mStyleButton.setAlpha(opacity);
/* 217 */             if (DialogStickyNote.this.mAnnotStyleChangeListener != null) {
/* 218 */               DialogStickyNote.this.mAnnotStyleChangeListener.onChangeAnnotOpacity(opacity, done);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onChangeAnnotStrokeColor(int color) {
/* 224 */             DialogStickyNote.this.mIconColor = color;
/* 225 */             DialogStickyNote.this.mStyleButton.setImageDrawable((Drawable)DialogStickyNote.this.createCurrentIcon());
/* 226 */             DialogStickyNote.this.mTextBox.setBackgroundColor(DialogStickyNote.this.getBackgroundColor());
/* 227 */             if (DialogStickyNote.this.mAnnotStyleChangeListener != null) {
/* 228 */               DialogStickyNote.this.mAnnotStyleChangeListener.onChangeAnnotStrokeColor(color);
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotFillColor(int color) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotIcon(String icon) {
/* 239 */             DialogStickyNote.this.mIconType = icon;
/* 240 */             DialogStickyNote.this.mStyleButton.setImageDrawable((Drawable)DialogStickyNote.this.createCurrentIcon());
/* 241 */             if (DialogStickyNote.this.mAnnotStyleChangeListener != null) {
/* 242 */               DialogStickyNote.this.mAnnotStyleChangeListener.onChangeAnnotIcon(icon);
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotFont(FontResource font) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeRulerProperty(RulerItem rulerItem) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeOverlayText(String overlayText) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeSnapping(boolean snap) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeRichContentEnabled(boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeDateFormat(String dateFormat) {}
/*     */         });
/* 276 */     this.mAnnotStyleDialog.setOnDismissListener(new OnDismissListener()
/*     */         {
/*     */           public void onDismiss(DialogInterface dialogInterface)
/*     */           {
/* 280 */             DialogStickyNote.this.mIconType = DialogStickyNote.this.mAnnotStyleDialog.getAnnotStyle().getIcon();
/* 281 */             DialogStickyNote.this.mIconColor = DialogStickyNote.this.mAnnotStyleDialog.getAnnotStyle().getColor();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private LayerDrawable createCurrentIcon() {
/* 288 */     return (LayerDrawable)AnnotStyle.getIconDrawable(getContext(), this.mIconType.toLowerCase(), this.mIconColor, this.mIconOpacity);
/*     */   }
/*     */   
/*     */   public void setAnnotAppearanceChangeListener(AnnotStyle.OnAnnotStyleChangeListener listener) {
/* 292 */     this.mAnnotStyleChangeListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareDismiss() {
/* 303 */     if (this.mAnnotStyleDialog != null) {
/* 304 */       ToolStyleConfig.getInstance().saveAnnotStyle(getContext(), this.mAnnotStyleDialog.getAnnotStyle(), "");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExistingNote() {
/* 312 */     return this.mExistingNote;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogStickyNote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */