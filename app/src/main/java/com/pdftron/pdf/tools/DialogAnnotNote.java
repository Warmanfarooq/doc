/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.app.Activity;
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.graphics.Rect;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.view.GestureDetector;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.Window;
/*     */ import android.view.WindowManager;
/*     */ import android.widget.Button;
/*     */ import android.widget.EditText;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DialogAnnotNote
/*     */   extends AlertDialog
/*     */ {
/*     */   private static final int MAX_ALLOWED_SYSTEM_BAR_SIZE = 256;
/*     */   private static final int MIN_ALLOWED_SYSTEM_BAR_SIZE = 5;
/*     */   View mMainView;
/*     */   EditText mTextBox;
/*     */   Button mPositiveButton;
/*     */   private Button mNegativeButton;
/*     */   private DialogAnnotNoteListener mListener;
/*     */   GestureDetector mGestureDetector;
/*     */   boolean mInEditMode;
/*     */   private String mOriginalNote;
/*     */   protected PDFViewCtrl mPdfViewCtrl;
/*     */   protected boolean mHasPermission = true;
/*     */   
/*  68 */   public TextWatcher textWatcher = new TextWatcher()
/*     */     {
/*     */       public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/*  76 */         DialogAnnotNote.this.mPositiveButton.setEnabled(true);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void afterTextChanged(Editable s) {}
/*     */     };
/*     */ 
/*     */   
/*     */   public DialogAnnotNote(@NonNull PDFViewCtrl pdfViewCtrl, String note) {
/*  86 */     this(pdfViewCtrl, note, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialogAnnotNote(@NonNull PDFViewCtrl pdfViewCtrl, String note, boolean hasPermission) {
/*  94 */     super(pdfViewCtrl.getContext());
/*  95 */     Context context = getContext();
/*  96 */     if (note == null) {
/*  97 */       note = "";
/*     */     }
/*  99 */     this.mOriginalNote = note;
/* 100 */     this.mHasPermission = hasPermission;
/*     */     
/* 102 */     this.mInEditMode = this.mHasPermission;
/* 103 */     this.mMainView = LayoutInflater.from(context).inflate(R.layout.tools_dialog_annotation_popup_text_input, null);
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.mTextBox = (EditText)this.mMainView.findViewById(R.id.tools_dialog_annotation_popup_edittext);
/*     */ 
/*     */     
/* 110 */     this.mPositiveButton = (Button)this.mMainView.findViewById(R.id.tools_dialog_annotation_popup_button_positive);
/* 111 */     this.mPositiveButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 114 */             DialogAnnotNote.this.setButtonPressed(-1);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 119 */     this.mNegativeButton = (Button)this.mMainView.findViewById(R.id.tools_dialog_annotation_popup_button_negative);
/* 120 */     this.mNegativeButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 123 */             DialogAnnotNote.this.setButtonPressed(-2);
/*     */           }
/*     */         });
/* 126 */     if (!this.mHasPermission) {
/* 127 */       this.mNegativeButton.setVisibility(4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 132 */     if (this.mHasPermission) {
/* 133 */       Utils.showSoftKeyboard(context, (View)this.mTextBox);
/* 134 */       if (getWindow() != null) {
/* 135 */         getWindow().setSoftInputMode(16);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 140 */     setView(this.mMainView);
/* 141 */     setCanceledOnTouchOutside(false);
/*     */ 
/*     */     
/* 144 */     this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)new MyGestureDetector());
/* 145 */     this.mPdfViewCtrl = pdfViewCtrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnotNoteListener(DialogAnnotNoteListener listener) {
/* 155 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNote() {
/* 162 */     return this.mTextBox.getText().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditEnabled() {
/* 170 */     return this.mInEditMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setButtonPressed(int button) {
/* 180 */     if (this.mListener != null) {
/* 181 */       this.mListener.onAnnotButtonPressed(button);
/*     */     }
/* 183 */     dismiss();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void switchToViewMode() {
/* 192 */     this.mInEditMode = false;
/*     */ 
/*     */     
/* 195 */     this.mTextBox.setFocusable(false);
/* 196 */     this.mTextBox.setFocusableInTouchMode(false);
/* 197 */     this.mTextBox.setLongClickable(false);
/* 198 */     this.mTextBox.setCursorVisible(false);
/* 199 */     this.mTextBox.setSelection(0);
/*     */ 
/*     */     
/* 202 */     this.mPositiveButton.setText(getContext().getString(R.string.tools_misc_close));
/* 203 */     this.mNegativeButton.setText(getContext().getString(R.string.delete));
/*     */ 
/*     */     
/* 206 */     if (this.mHasPermission) {
/* 207 */       this.mTextBox.setHint(getContext().getString(R.string.tools_dialog_annotation_popup_view_mode_hint));
/*     */     } else {
/* 209 */       this.mTextBox.setHint(getContext().getString(R.string.tools_dialog_annotation_popup_readonly_hint));
/*     */     } 
/*     */ 
/*     */     
/* 213 */     this.mTextBox.setOnTouchListener(new View.OnTouchListener()
/*     */         {
/*     */           public boolean onTouch(View v, MotionEvent event) {
/* 216 */             return DialogAnnotNote.this.mGestureDetector.onTouchEvent(event);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class MyGestureDetector
/*     */     extends GestureDetector.SimpleOnGestureListener
/*     */   {
/*     */     public boolean onSingleTapUp(MotionEvent e) {
/* 229 */       if (!DialogAnnotNote.this.mInEditMode && DialogAnnotNote.this.mHasPermission) {
/* 230 */         DialogAnnotNote.this.setWrapNoteHeight();
/*     */ 
/*     */         
/* 233 */         DialogAnnotNote.this.mTextBox.setFocusableInTouchMode(true);
/* 234 */         DialogAnnotNote.this.mTextBox.setFocusable(true);
/* 235 */         DialogAnnotNote.this.mTextBox.requestFocus();
/* 236 */         DialogAnnotNote.this.mTextBox.setCursorVisible(true);
/* 237 */         DialogAnnotNote.this.mTextBox.setSelection(DialogAnnotNote.this.mTextBox.getText().length());
/* 238 */         DialogAnnotNote.this.mTextBox.setLongClickable(true);
/*     */ 
/*     */         
/* 241 */         DialogAnnotNote.this.mPositiveButton.setText(DialogAnnotNote.this.getContext().getString(R.string.tools_misc_save));
/* 242 */         DialogAnnotNote.this.mNegativeButton.setText(DialogAnnotNote.this.getContext().getString(R.string.cancel));
/*     */         
/* 244 */         DialogAnnotNote.this.mTextBox.addTextChangedListener(DialogAnnotNote.this.textWatcher);
/* 245 */         DialogAnnotNote.this.mPositiveButton.setEnabled(false);
/*     */ 
/*     */         
/* 248 */         Utils.showSoftKeyboard(DialogAnnotNote.this.getContext(), (View)DialogAnnotNote.this.mTextBox);
/*     */ 
/*     */         
/* 251 */         DialogAnnotNote.this.mTextBox.setHint(DialogAnnotNote.this.getContext().getString(R.string.tools_dialog_annotation_popup_note_hint));
/*     */ 
/*     */         
/* 254 */         DialogAnnotNote.this.mInEditMode = true;
/* 255 */         return true;
/*     */       } 
/* 257 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onDown(MotionEvent e) {
/* 262 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void setWrapNoteHeight() {
/* 267 */     Window window = getWindow();
/* 268 */     if (window != null) {
/* 269 */       WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
/* 270 */       lp.copyFrom(window.getAttributes());
/* 271 */       lp.height = -2;
/* 272 */       window.setAttributes(lp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setHeight() {
/*     */     int noteHeight;
/* 279 */     DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
/* 280 */     int screenHeight = metrics.heightPixels;
/* 281 */     int screenWidth = metrics.widthPixels;
/*     */ 
/*     */     
/* 284 */     if ((getContext().getResources().getConfiguration()).orientation == 2)
/*     */     {
/* 286 */       screenWidth = screenHeight;
/*     */     }
/*     */     
/* 289 */     Activity activity = getOwnerActivity();
/* 290 */     int statusBarHeight = 0;
/* 291 */     int navigationBarHeight = 0;
/* 292 */     if (activity == null) {
/* 293 */       int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
/* 294 */       if (resourceId > 0) {
/* 295 */         statusBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
/*     */       }
/* 297 */       resourceId = getContext().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
/* 298 */       if (resourceId > 0) {
/* 299 */         navigationBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
/*     */       }
/*     */     } else {
/* 302 */       statusBarHeight = getStatusBarHeight(activity);
/* 303 */       navigationBarHeight = getNavigationBarHeight(activity);
/*     */     } 
/*     */     
/* 306 */     int windowWidth = (int)((screenWidth - statusBarHeight - navigationBarHeight) * 0.9F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     double reduceFactor = 0.5D;
/* 314 */     if (getContext().getResources().getBoolean(R.bool.isTablet)) {
/* 315 */       noteHeight = (int)(windowWidth * reduceFactor);
/*     */     } else {
/*     */       
/* 318 */       noteHeight = windowWidth;
/*     */     } 
/* 320 */     setNoteHeight(noteHeight);
/* 321 */     increaseNoteHeight(reduceFactor, windowWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   private void increaseNoteHeight(final double factor, final int windowWidth) {
/* 326 */     this.mTextBox.post(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 330 */             int numTextLines = DialogAnnotNote.this.mTextBox.getLineCount();
/*     */ 
/*     */             
/* 333 */             int padding = DialogAnnotNote.this.mTextBox.getPaddingTop() + DialogAnnotNote.this.mTextBox.getPaddingBottom();
/* 334 */             int TextBoxSize = DialogAnnotNote.this.mTextBox.getHeight() - padding;
/* 335 */             int lineHeight = DialogAnnotNote.this.mTextBox.getLineHeight();
/* 336 */             int numLinesInBox = TextBoxSize / lineHeight;
/*     */ 
/*     */ 
/*     */             
/* 340 */             if (numTextLines > numLinesInBox) {
/* 341 */               if (DialogAnnotNote.this.getContext().getResources().getBoolean(R.bool.isTablet)) {
/* 342 */                 if (factor >= 1.0D) {
/* 343 */                   DialogAnnotNote.this.setWrapNoteHeight();
/*     */                 } else {
/* 345 */                   double newFactor = factor + 0.25D;
/* 346 */                   int noteHeight = (int)(windowWidth * newFactor);
/* 347 */                   DialogAnnotNote.this.setNoteHeight(noteHeight);
/* 348 */                   DialogAnnotNote.this.increaseNoteHeight(newFactor, windowWidth);
/*     */                 } 
/*     */               } else {
/* 351 */                 DialogAnnotNote.this.setWrapNoteHeight();
/*     */               } 
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNoteHeight(int noteHeight) {
/* 360 */     Window window = getWindow();
/* 361 */     if (window != null) {
/* 362 */       WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
/* 363 */       lp.copyFrom(window.getAttributes());
/* 364 */       lp.height = noteHeight;
/* 365 */       window.setAttributes(lp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onStart() {
/* 374 */     super.onStart();
/*     */     
/* 376 */     initTextBox(this.mOriginalNote);
/*     */     
/* 378 */     if (!this.mInEditMode) {
/* 379 */       setHeight();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initTextBox(String note) {
/* 389 */     if (!note.equals("") || !this.mHasPermission) {
/* 390 */       this.mTextBox.setText(note);
/*     */       
/* 392 */       this.mTextBox.setSelection(this.mTextBox.getText().length());
/* 393 */       switchToViewMode();
/*     */     } else {
/* 395 */       Utils.showSoftKeyboard(getContext(), (View)this.mTextBox);
/*     */     } 
/*     */ 
/*     */     
/* 399 */     if (this.mInEditMode) {
/* 400 */       this.mTextBox.addTextChangedListener(this.textWatcher);
/* 401 */       this.mPositiveButton.setEnabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"NewApi"})
/*     */   private static int getNavigationBarHeight(@NonNull Activity activity) {
/* 409 */     int height = Utils.getRealScreenHeight((Context)activity) - Utils.getScreenHeight((Context)activity);
/* 410 */     if (height > 256)
/*     */     {
/* 412 */       height = 0;
/*     */     }
/* 414 */     if (height < 5)
/*     */     {
/* 416 */       height = 0;
/*     */     }
/* 418 */     int width = Utils.getRealScreenWidth((Context)activity) - Utils.getScreenWidth((Context)activity);
/* 419 */     if (width > 256)
/*     */     {
/* 421 */       height = 0;
/*     */     }
/*     */     
/* 424 */     return height;
/*     */   }
/*     */   
/*     */   @SuppressLint({"NewApi"})
/*     */   private static int getStatusBarHeight(@NonNull Activity activity) {
/* 429 */     Rect rectangle = new Rect();
/* 430 */     activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
/*     */     
/* 432 */     if (rectangle.top > 256) {
/*     */       
/* 434 */       rectangle.top = 0;
/* 435 */     } else if (rectangle.top == 0) {
/*     */ 
/*     */       
/* 438 */       int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
/* 439 */       if (resourceId > 0) {
/* 440 */         rectangle.top = activity.getResources().getDimensionPixelSize(resourceId);
/*     */       }
/* 442 */     } else if (rectangle.top < 5 && rectangle.top > 0) {
/*     */       
/* 444 */       rectangle.top = 0;
/*     */     } 
/*     */     
/* 447 */     return rectangle.top;
/*     */   }
/*     */   
/*     */   public static interface DialogAnnotNoteListener {
/*     */     void onAnnotButtonPressed(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogAnnotNote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */