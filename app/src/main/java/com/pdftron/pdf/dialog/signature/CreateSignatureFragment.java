/*     */ package com.pdftron.pdf.dialog.signature;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.Rect;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.RelativeLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnCreateSignatureListener;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.StampManager;
/*     */ import com.pdftron.pdf.widget.signature.VariableWidthSignatureView;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateSignatureFragment
/*     */   extends Fragment
/*     */ {
/*     */   private static final String BUNDLE_COLOR = "bundle_color";
/*     */   private static final String BUNDLE_STROKE_WIDTH = "bundle_stroke_width";
/*     */   private static final String BUNDLE_SHOW_SIGNATURE_FROM_IMAGE = "bundle_signature_from_image";
/*     */   private static final String BUNDLE_PRESSURE_SENSITIVE = "bundle_pressure_sensitive";
/*     */   private OnCreateSignatureListener mOnCreateSignatureListener;
/*     */   private Toolbar mToolbar;
/*     */   private Button mClearButton;
/*     */   private ImageButton mStyleSignatureButton;
/*     */   private int mColor;
/*     */   private float mStrokeWidth;
/*     */   private boolean mShowSignatureFromImage;
/*     */   private boolean mIsPressureSensitive = true;
/*     */   private VariableWidthSignatureView mSignatureView;
/*     */   
/*     */   public static CreateSignatureFragment newInstance(int color, float strokeWidth, boolean showSignatureFromImage, boolean isPressureSensitive) {
/*  58 */     CreateSignatureFragment fragment = new CreateSignatureFragment();
/*  59 */     Bundle bundle = new Bundle();
/*  60 */     bundle.putInt("bundle_color", color);
/*  61 */     bundle.putFloat("bundle_stroke_width", strokeWidth);
/*  62 */     bundle.putBoolean("bundle_signature_from_image", showSignatureFromImage);
/*  63 */     bundle.putBoolean("bundle_pressure_sensitive", isPressureSensitive);
/*  64 */     fragment.setArguments(bundle);
/*  65 */     return fragment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  70 */     super.onCreate(savedInstanceState);
/*     */     
/*  72 */     Bundle arg = getArguments();
/*  73 */     if (arg != null) {
/*  74 */       this.mColor = arg.getInt("bundle_color");
/*  75 */       this.mStrokeWidth = arg.getFloat("bundle_stroke_width");
/*  76 */       this.mShowSignatureFromImage = arg.getBoolean("bundle_signature_from_image", true);
/*  77 */       this.mIsPressureSensitive = arg.getBoolean("bundle_pressure_sensitive", this.mIsPressureSensitive);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  84 */     return inflater.inflate(R.layout.tools_dialog_create_signature, container, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  89 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/*  91 */     RelativeLayout mSignatureContainer = (RelativeLayout)view.findViewById(R.id.tools_dialog_floating_sig_signature_view);
/*  92 */     this.mSignatureView = new VariableWidthSignatureView(view.getContext(), null);
/*  93 */     this.mSignatureView.setPressureSensitivity(this.mIsPressureSensitive);
/*  94 */     this.mSignatureView.setColor(this.mColor);
/*  95 */     this.mSignatureView.setStrokeWidth(this.mStrokeWidth);
/*  96 */     this.mSignatureView.addListener(new VariableWidthSignatureView.InkListener()
/*     */         {
/*     */           public void onInkStarted()
/*     */           {
/* 100 */             CreateSignatureFragment.this.setClearButtonEnabled(true);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onInkCompleted(List<double[]> mStrokeOutline) {
/* 105 */             CreateSignatureFragment.this.createSignature(CreateSignatureFragment.this.getContext(), mStrokeOutline);
/*     */           }
/*     */         });
/* 108 */     mSignatureContainer.addView((View)this.mSignatureView);
/*     */ 
/*     */     
/* 111 */     this.mClearButton = (Button)view.findViewById(R.id.tools_dialog_floating_sig_button_clear);
/* 112 */     setClearButtonEnabled(false);
/* 113 */     this.mClearButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 116 */             CreateSignatureFragment.this.mSignatureView.clear();
/* 117 */             CreateSignatureFragment.this.setClearButtonEnabled(false);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 122 */     ImageButton imageButton = (ImageButton)view.findViewById(R.id.tools_dialog_floating_sig_button_image);
/* 123 */     imageButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 126 */             if (CreateSignatureFragment.this.mOnCreateSignatureListener != null) {
/* 127 */               CreateSignatureFragment.this.mOnCreateSignatureListener.onSignatureFromImage(null, -1, null);
/*     */             }
/*     */           }
/*     */         });
/* 131 */     if (this.mShowSignatureFromImage) {
/* 132 */       imageButton.setVisibility(0);
/*     */     } else {
/* 134 */       imageButton.setVisibility(8);
/*     */     } 
/*     */ 
/*     */     
/* 138 */     this.mStyleSignatureButton = (ImageButton)view.findViewById(R.id.tools_dialog_floating_sig_button_style);
/*     */     
/* 140 */     this.mStyleSignatureButton.getDrawable().mutate().setColorFilter(this.mColor, PorterDuff.Mode.SRC_IN);
/* 141 */     this.mStyleSignatureButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v)
/*     */           {
/* 145 */             CreateSignatureFragment.this.mStyleSignatureButton.setSelected(true);
/*     */ 
/*     */             
/* 148 */             AnnotStyle annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle(v.getContext(), 1002, "");
/*     */             
/* 150 */             int[] pos = new int[2];
/* 151 */             CreateSignatureFragment.this.mStyleSignatureButton.getLocationOnScreen(pos);
/*     */             
/* 153 */             Rect anchor = new Rect(pos[0], pos[1], pos[0] + CreateSignatureFragment.this.mStyleSignatureButton.getWidth(), pos[1] + CreateSignatureFragment.this.mStyleSignatureButton.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 158 */             final AnnotStyleDialogFragment popupWindow = (new AnnotStyleDialogFragment.Builder(annotStyle)).setAnchorInScreen(anchor).setShowPressureSensitivePreview(CreateSignatureFragment.this.mIsPressureSensitive).build();
/*     */             
/*     */             try {
/* 161 */               FragmentActivity activity = CreateSignatureFragment.this.getActivity();
/* 162 */               if (activity == null) {
/* 163 */                 AnalyticsHandlerAdapter.getInstance().sendException(new Exception("SignaturePickerDialog is not attached with an Activity"));
/*     */                 return;
/*     */               } 
/* 166 */               popupWindow.show(activity.getSupportFragmentManager(), 3, 
/*     */                   
/* 168 */                   AnalyticsHandlerAdapter.getInstance().getAnnotationTool(9));
/* 169 */             } catch (Exception ex) {
/* 170 */               AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */             } 
/*     */             
/* 173 */             popupWindow.setOnAnnotStyleChangeListener(new AnnotStyle.OnAnnotStyleChangeListener()
/*     */                 {
/*     */                   public void onChangeAnnotThickness(float thickness, boolean done) {
/* 176 */                     if (done) {
/* 177 */                       CreateSignatureFragment.this.mSignatureView.setStrokeWidth(thickness);
/* 178 */                       CreateSignatureFragment.this.mStrokeWidth = thickness;
/*     */                       
/* 180 */                       CommonToast.showText(CreateSignatureFragment.this.getContext(), R.string.signature_thickness_toast, 1);
/*     */                     } 
/*     */                   }
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotTextSize(float textSize, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotTextColor(int textColor) {}
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotOpacity(float opacity, boolean done) {}
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotStrokeColor(int color) {
/* 201 */                     CreateSignatureFragment.this.mStyleSignatureButton.getDrawable().mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 202 */                     CreateSignatureFragment.this.mSignatureView.setColor(color);
/* 203 */                     CreateSignatureFragment.this.mColor = color;
/*     */                   }
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotFillColor(int color) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotIcon(String icon) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeAnnotFont(FontResource font) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeRulerProperty(RulerItem rulerItem) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeOverlayText(String overlayText) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeSnapping(boolean snap) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeRichContentEnabled(boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void onChangeDateFormat(String dateFormat) {}
/*     */                 });
/* 246 */             popupWindow.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */                 {
/*     */                   public void onDismiss(DialogInterface dialogInterface) {
/* 249 */                     if (CreateSignatureFragment.this.mOnCreateSignatureListener != null) {
/* 250 */                       CreateSignatureFragment.this.mOnCreateSignatureListener.onAnnotStyleDialogFragmentDismissed(popupWindow);
/*     */                     }
/* 252 */                     CreateSignatureFragment.this.mStyleSignatureButton.setSelected(false);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void setClearButtonEnabled(boolean enabled) {
/* 260 */     if (enabled) {
/* 261 */       this.mClearButton.setTextColor(this.mClearButton.getContext().getResources().getColor(R.color.tools_colors_white));
/*     */     } else {
/* 263 */       this.mClearButton.setTextColor(this.mClearButton.getContext().getResources().getColor(R.color.tab_unselected));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnCreateSignatureListener(OnCreateSignatureListener listener) {
/* 273 */     this.mOnCreateSignatureListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToolbar(@NonNull Toolbar toolbar) {
/* 282 */     this.mToolbar = toolbar;
/*     */   }
/*     */   
/*     */   public void resetToolbar(Context context) {
/* 286 */     if (this.mToolbar != null) {
/* 287 */       this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */           {
/*     */             public boolean onMenuItemClick(MenuItem item) {
/* 290 */               if (CreateSignatureFragment.this.mToolbar == null) {
/* 291 */                 return false;
/*     */               }
/* 293 */               if (item.getItemId() == R.id.controls_action_edit) {
/* 294 */                 CreateSignatureFragment.this.mSignatureView.finish();
/* 295 */                 return true;
/*     */               } 
/* 297 */               return false;
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private void createSignature(@Nullable Context context, @NonNull List<double[]> strokes) {
/* 304 */     if (context == null) {
/*     */       return;
/*     */     }
/* 307 */     if (strokes.isEmpty()) {
/*     */       return;
/*     */     }
/* 310 */     String signatureFilePath = StampManager.getInstance().getSignatureFilePath(context);
/* 311 */     Page page = StampManager.getInstance().createVariableThicknessSignature(signatureFilePath, this.mSignatureView
/* 312 */         .getBoundingBox(), strokes, this.mColor, this.mStrokeWidth * 2.0F);
/*     */ 
/*     */     
/* 315 */     if (this.mOnCreateSignatureListener != null)
/* 316 */       this.mOnCreateSignatureListener.onSignatureCreated((page != null) ? signatureFilePath : null); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\signature\CreateSignatureFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */