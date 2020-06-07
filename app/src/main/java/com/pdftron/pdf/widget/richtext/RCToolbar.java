/*     */ package com.pdftron.pdf.widget.richtext;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.FrameLayout;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.utils.ViewerUtils;
/*     */ import com.pdftron.pdf.viewmodel.RichTextEvent;
/*     */ import com.pdftron.pdf.viewmodel.RichTextViewModel;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import jp.wasabeef.richeditor.RichEditor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RCToolbar
/*     */   extends FrameLayout
/*     */ {
/*     */   private AnnotStyleDialogFragment mAnnotStyleDialog;
/*     */   private ToolManager mToolManager;
/*     */   private RichTextViewModel mRichTextViewModel;
/*  38 */   private HashMap<RichEditor.Type, View> mButtons = new HashMap<>();
/*     */   
/*     */   public RCToolbar(Context context) {
/*  41 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public RCToolbar(Context context, AttributeSet attrs) {
/*  45 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public RCToolbar(Context context, AttributeSet attrs, int defStyle) {
/*  49 */     super(context, attrs, defStyle);
/*     */     
/*  51 */     init(context);
/*     */   }
/*     */   
/*     */   private void init(Context context) {
/*  55 */     if (context instanceof FragmentActivity) {
/*  56 */       this.mRichTextViewModel = (RichTextViewModel)ViewModelProviders.of((FragmentActivity)context).get(RichTextViewModel.class);
/*     */     }
/*     */     
/*  59 */     LayoutInflater.from(context).inflate(R.layout.rc_toolbar, (ViewGroup)this, true);
/*  60 */     initViews();
/*     */   }
/*     */   
/*     */   private void initViews() {
/*  64 */     findViewById(R.id.action_undo).setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  67 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.UNDO);
/*     */           }
/*     */         });
/*     */     
/*  71 */     findViewById(R.id.action_redo).setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  74 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.REDO);
/*     */           }
/*     */         });
/*     */     
/*  78 */     final View styleView = findViewById(R.id.action_style);
/*  79 */     styleView.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  82 */             RCToolbar.this.toggleStylePicker(styleView);
/*     */           }
/*     */         });
/*     */     
/*  86 */     View view = findViewById(R.id.action_bold);
/*  87 */     tintBackground(view);
/*  88 */     this.mButtons.put(RichEditor.Type.BOLD, view);
/*  89 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  92 */             v.setSelected(!v.isSelected());
/*  93 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.BOLD);
/*     */           }
/*     */         });
/*     */     
/*  97 */     view = findViewById(R.id.action_italic);
/*  98 */     tintBackground(view);
/*  99 */     this.mButtons.put(RichEditor.Type.ITALIC, view);
/* 100 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 103 */             v.setSelected(!v.isSelected());
/* 104 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.ITALIC);
/*     */           }
/*     */         });
/*     */     
/* 108 */     view = findViewById(R.id.action_strikethrough);
/* 109 */     tintBackground(view);
/* 110 */     this.mButtons.put(RichEditor.Type.STRIKETHROUGH, view);
/* 111 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 114 */             v.setSelected(!v.isSelected());
/* 115 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.STRIKE_THROUGH);
/*     */           }
/*     */         });
/*     */     
/* 119 */     view = findViewById(R.id.action_underline);
/* 120 */     tintBackground(view);
/* 121 */     this.mButtons.put(RichEditor.Type.UNDERLINE, view);
/* 122 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 125 */             v.setSelected(!v.isSelected());
/* 126 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.UNDERLINE);
/*     */           }
/*     */         });
/*     */     
/* 130 */     findViewById(R.id.action_indent).setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 133 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.INDENT);
/*     */           }
/*     */         });
/*     */     
/* 137 */     findViewById(R.id.action_outdent).setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 140 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.OUTDENT);
/*     */           }
/*     */         });
/*     */     
/* 144 */     view = findViewById(R.id.action_align_left);
/* 145 */     tintBackground(view);
/* 146 */     this.mButtons.put(RichEditor.Type.JUSTUFYLEFT, view);
/* 147 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 150 */             v.setSelected(!v.isSelected());
/* 151 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.ALIGN_LEFT);
/*     */           }
/*     */         });
/*     */     
/* 155 */     view = findViewById(R.id.action_align_center);
/* 156 */     tintBackground(view);
/* 157 */     this.mButtons.put(RichEditor.Type.JUSTIFYCENTER, view);
/* 158 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 161 */             v.setSelected(!v.isSelected());
/* 162 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.ALIGN_CENTER);
/*     */           }
/*     */         });
/*     */     
/* 166 */     view = findViewById(R.id.action_align_right);
/* 167 */     tintBackground(view);
/* 168 */     this.mButtons.put(RichEditor.Type.JUSTIFYRIGHT, view);
/* 169 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 172 */             v.setSelected(!v.isSelected());
/* 173 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.ALIGN_RIGHT);
/*     */           }
/*     */         });
/*     */     
/* 177 */     view = findViewById(R.id.action_insert_bullets);
/* 178 */     tintBackground(view);
/* 179 */     this.mButtons.put(RichEditor.Type.UNORDEREDLIST, view);
/* 180 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 183 */             v.setSelected(!v.isSelected());
/* 184 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.BULLETS);
/*     */           }
/*     */         });
/*     */     
/* 188 */     view = findViewById(R.id.action_insert_numbers);
/* 189 */     tintBackground(view);
/* 190 */     this.mButtons.put(RichEditor.Type.ORDEREDLIST, view);
/* 191 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 194 */             v.setSelected(!v.isSelected());
/* 195 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.NUMBERS);
/*     */           }
/*     */         });
/*     */     
/* 199 */     view = findViewById(R.id.action_blockquote);
/* 200 */     view.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 203 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.BLOCK_QUOTE);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void toggleStylePicker(View which) {
/* 209 */     AnnotStyle annotStyle = getFreeTextAnnotStyle();
/* 210 */     final FragmentActivity activity = this.mToolManager.getCurrentActivity();
/* 211 */     if (null == annotStyle || null == this.mToolManager || null == activity) {
/*     */       return;
/*     */     }
/*     */     
/* 215 */     annotStyle.setTextHTMLContent("rc");
/*     */ 
/*     */ 
/*     */     
/* 219 */     final AnnotStyleDialogFragment popupWindow = (new AnnotStyleDialogFragment.Builder(annotStyle)).setAnchorView(which).setWhiteListFont(this.mToolManager.getFreeTextFonts()).build();
/*     */     
/* 221 */     if (this.mAnnotStyleDialog != null) {
/*     */       return;
/*     */     }
/*     */     
/* 225 */     this.mAnnotStyleDialog = popupWindow;
/*     */     
/* 227 */     popupWindow.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */         {
/*     */           public void onDismiss(DialogInterface dialog) {
/* 230 */             RCToolbar.this.mAnnotStyleDialog = null;
/*     */             
/* 232 */             Context context = RCToolbar.this.getContext();
/* 233 */             if (context == null) {
/*     */               return;
/*     */             }
/*     */             
/* 237 */             AnnotStyle annotStyle = popupWindow.getAnnotStyle();
/* 238 */             ToolStyleConfig.getInstance().saveAnnotStyle(context, annotStyle, "");
/* 239 */             Tool tool = (Tool)RCToolbar.this.mToolManager.getTool();
/* 240 */             if (tool != null) {
/* 241 */               tool.setupAnnotProperty(annotStyle);
/*     */             }
/*     */             
/* 244 */             RCToolbar.this.updateStyle(annotStyle);
/*     */ 
/*     */             
/* 247 */             RCToolbar.this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.SHOW_KEYBOARD);
/* 248 */             Utils.showSoftKeyboard((Context)activity, null);
/*     */           }
/*     */         });
/*     */     
/* 252 */     this.mRichTextViewModel.onEditorAction(RichTextEvent.Type.HIDE_KEYBOARD);
/* 253 */     Utils.hideSoftKeyboard((Context)activity, (View)this);
/*     */     
/* 255 */     popupWindow.show(activity.getSupportFragmentManager());
/*     */   }
/*     */   
/*     */   public void updateStyle(@Nullable AnnotStyle annotStyle) {
/* 259 */     if (annotStyle != null) {
/* 260 */       this.mRichTextViewModel.onUpdateTextStyle(annotStyle);
/*     */       
/* 262 */       SharedPreferences settings = Tool.getToolPreferences(getContext());
/* 263 */       SharedPreferences.Editor editor = settings.edit();
/* 264 */       editor.putInt(ToolStyleConfig.getInstance().getTextColorKey(2, ""), annotStyle.getTextColor());
/* 265 */       editor.putFloat(ToolStyleConfig.getInstance().getTextSizeKey(2, ""), annotStyle.getTextSize());
/* 266 */       editor.apply();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setToolManager(ToolManager toolManager) {
/* 271 */     this.mToolManager = toolManager;
/*     */   }
/*     */   
/*     */   public void deselectAll() {
/* 275 */     for (View view : this.mButtons.values()) {
/* 276 */       view.setSelected(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void tintBackground(View view) {
/* 281 */     Drawable drawable = getResources().getDrawable(R.drawable.rounded_corners);
/* 282 */     drawable.mutate();
/* 283 */     drawable.setColorFilter(getContext().getResources().getColor(R.color.controls_edit_toolbar_tool), PorterDuff.Mode.SRC_ATOP);
/* 284 */     view.setBackground((Drawable)ViewerUtils.createBackgroundSelector(drawable));
/*     */   }
/*     */   
/*     */   private AnnotStyle getFreeTextAnnotStyle() {
/* 288 */     Context context = getContext();
/* 289 */     if (context == null) {
/* 290 */       return null;
/*     */     }
/* 292 */     return ToolStyleConfig.getInstance().getCustomAnnotStyle(context, 2, "");
/*     */   }
/*     */   
/*     */   public void updateDecorationTypes(List<RichEditor.Type> types) {
/* 296 */     deselectAll();
/* 297 */     if (types != null)
/* 298 */       for (RichEditor.Type type : types) {
/* 299 */         View view = this.mButtons.get(type);
/* 300 */         if (view != null)
/* 301 */           view.setSelected(true); 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\richtext\RCToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */