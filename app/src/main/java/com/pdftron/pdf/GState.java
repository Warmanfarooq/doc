/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import com.pdftron.common.Matrix2D;
/*      */ import com.pdftron.common.PDFNetException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GState
/*      */ {
/*      */   public static final int e_transform = 0;
/*      */   public static final int e_rendering_intent = 1;
/*      */   public static final int e_stroke_cs = 2;
/*      */   public static final int e_stroke_color = 3;
/*      */   public static final int e_fill_cs = 4;
/*      */   public static final int e_fill_color = 5;
/*      */   public static final int e_line_width = 6;
/*      */   public static final int e_line_cap = 7;
/*      */   public static final int e_line_join = 8;
/*      */   public static final int e_flatness = 9;
/*      */   public static final int e_miter_limit = 10;
/*      */   public static final int e_dash_pattern = 11;
/*      */   public static final int e_char_spacing = 12;
/*      */   public static final int e_word_spacing = 13;
/*      */   public static final int e_horizontal_scale = 14;
/*      */   public static final int e_leading = 15;
/*      */   public static final int e_font = 16;
/*      */   public static final int e_font_size = 17;
/*      */   public static final int e_text_render_mode = 18;
/*      */   public static final int e_text_rise = 19;
/*      */   public static final int e_text_knockout = 20;
/*      */   public static final int e_text_pos_offset = 21;
/*      */   public static final int e_blend_mode = 22;
/*      */   public static final int e_opacity_fill = 23;
/*      */   public static final int e_opacity_stroke = 24;
/*      */   public static final int e_alpha_is_shape = 25;
/*      */   public static final int e_soft_mask = 26;
/*      */   public static final int e_smoothnes = 27;
/*      */   public static final int e_auto_stoke_adjust = 28;
/*      */   public static final int e_stroke_overprint = 29;
/*      */   public static final int e_fill_overprint = 30;
/*      */   public static final int e_overprint_mode = 31;
/*      */   public static final int e_transfer_funct = 32;
/*      */   public static final int e_BG_funct = 33;
/*      */   public static final int e_UCR_funct = 34;
/*      */   public static final int e_halftone = 35;
/*      */   public static final int e_null = 36;
/*      */   public static final int e_butt_cap = 0;
/*      */   public static final int e_round_cap = 1;
/*      */   public static final int e_square_cap = 2;
/*      */   public static final int e_miter_join = 0;
/*      */   public static final int e_round_join = 1;
/*      */   public static final int e_bevel_join = 2;
/*      */   public static final int e_fill_text = 0;
/*      */   public static final int e_stroke_text = 1;
/*      */   public static final int e_fill_stroke_text = 2;
/*      */   public static final int e_invisible_text = 3;
/*      */   public static final int e_fill_clip_text = 4;
/*      */   public static final int e_stroke_clip_text = 5;
/*      */   public static final int e_fill_stroke_clip_text = 6;
/*      */   public static final int e_clip_text = 7;
/*      */   public static final int e_absolute_colorimetric = 0;
/*      */   public static final int e_relative_colorimetric = 1;
/*      */   public static final int e_saturation = 2;
/*      */   public static final int e_perceptual = 3;
/*      */   public static final int e_bl_compatible = 0;
/*      */   public static final int e_bl_normal = 1;
/*      */   public static final int e_bl_multiply = 2;
/*      */   public static final int e_bl_screen = 3;
/*      */   public static final int e_bl_difference = 4;
/*      */   public static final int e_bl_darken = 5;
/*      */   public static final int e_bl_lighten = 6;
/*      */   public static final int e_bl_color_dodge = 7;
/*      */   public static final int e_bl_color_burn = 8;
/*      */   public static final int e_bl_exclusion = 9;
/*      */   public static final int e_bl_hard_light = 10;
/*      */   public static final int e_bl_overlay = 11;
/*      */   public static final int e_bl_soft_light = 12;
/*      */   public static final int e_bl_luminosity = 13;
/*      */   public static final int e_bl_hue = 14;
/*      */   public static final int e_bl_saturation = 15;
/*      */   public static final int e_bl_color = 16;
/*      */   long a;
/*      */   private Object b;
/*      */   private Object c;
/*      */   
/*      */   public Matrix2D getTransform() throws PDFNetException {
/*  149 */     return Matrix2D.__Create(GetTransform(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorSpace getStrokeColorSpace() throws PDFNetException {
/*  160 */     return ColorSpace.__Create(GetStrokeColorSpace(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorSpace getFillColorSpace() throws PDFNetException {
/*  171 */     return ColorSpace.__Create(GetFillColorSpace(this.a), this.b);
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
/*      */   public ColorPt getStrokeColor() throws PDFNetException {
/*  183 */     return new ColorPt(GetStrokeColor(this.a));
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
/*      */   public PatternColor GetStrokePattern() throws PDFNetException {
/*  195 */     return PatternColor.a(GetStrokePattern(this.a), this.c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorPt getFillColor() throws PDFNetException {
/*  206 */     return new ColorPt(GetFillColor(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PatternColor getFillPattern() throws PDFNetException {
/*  217 */     return PatternColor.a(GetFillPattern(this.a), this.c);
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
/*      */   public double getFlatness() throws PDFNetException {
/*  235 */     return GetFlatness(this.a);
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
/*      */   public int getLineCap() throws PDFNetException {
/*  259 */     return GetLineCap(this.a);
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
/*      */   public int getLineJoin() throws PDFNetException {
/*  283 */     return GetLineJoin(this.a);
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
/*      */   public double getLineWidth() throws PDFNetException {
/*  297 */     return GetLineWidth(this.a);
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
/*      */   public double getMiterLimit() throws PDFNetException {
/*  312 */     return GetMiterLimit(this.a);
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
/*      */   public double[] getDashes() throws PDFNetException {
/*  329 */     return GetDashes(this.a);
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
/*      */   public double getPhase() throws PDFNetException {
/*  341 */     return GetPhase(this.a);
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
/*      */   public double getCharSpacing() throws PDFNetException {
/*  357 */     return GetCharSpacing(this.a);
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
/*      */   public double getWordSpacing() throws PDFNetException {
/*  371 */     return GetWordSpacing(this.a);
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
/*      */   public double getHorizontalScale() throws PDFNetException {
/*  388 */     return GetHorizontalScale(this.a);
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
/*      */   public double getLeading() throws PDFNetException {
/*  403 */     return GetLeading(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() throws PDFNetException {
/*  414 */     return Font.__Create(GetFont(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getFontSize() throws PDFNetException {
/*  425 */     return GetFontSize(this.a);
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
/*      */   public int getTextRenderMode() throws PDFNetException {
/*  466 */     return GetTextRenderMode(this.a);
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
/*      */   public double getTextRise() throws PDFNetException {
/*  481 */     return GetTextRise(this.a);
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
/*      */   public boolean isTextKnockout() throws PDFNetException {
/*  494 */     return IsTextKnockout(this.a);
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
/*      */   public int getRenderingIntent() throws PDFNetException {
/*  518 */     return GetRenderingIntent(this.a);
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
/*      */   public int getBlendMode() throws PDFNetException {
/*  598 */     return GetBlendMode(this.a);
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
/*      */   public double getFillOpacity() throws PDFNetException {
/*  611 */     return GetFillOpacity(this.a);
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
/*      */   public double getStrokeOpacity() throws PDFNetException {
/*  624 */     return GetStrokeOpacity(this.a);
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
/*      */   public boolean getAISFlag() throws PDFNetException {
/*  637 */     return GetAISFlag(this.a);
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
/*      */   public Obj getSoftMask() throws PDFNetException {
/*  649 */     return Obj.__Create(GetSoftMask(this.a), this.c);
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
/*      */   public Matrix2D getSoftMaskTransform() throws PDFNetException {
/*  663 */     return Matrix2D.__Create(GetSoftMaskTransform(this.a));
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
/*      */   public boolean getStrokeOverprint() throws PDFNetException {
/*  675 */     return GetStrokeOverprint(this.a);
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
/*      */   public boolean getFillOverprint() throws PDFNetException {
/*  687 */     return GetFillOverprint(this.a);
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
/*      */   public int getOverprintMode() throws PDFNetException {
/*  699 */     return GetOverprintMode(this.a);
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
/*      */   public boolean getAutoStrokeAdjust() throws PDFNetException {
/*  711 */     return GetAutoStrokeAdjust(this.a);
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
/*      */   public double getSmoothnessTolerance() throws PDFNetException {
/*  725 */     return GetSmoothnessTolerance(this.a);
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
/*      */   public Obj getTransferFunct() throws PDFNetException {
/*  739 */     return Obj.__Create(GetTransferFunct(this.a), this.c);
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
/*      */   public Obj getBlackGenFunct() throws PDFNetException {
/*  752 */     return Obj.__Create(GetBlackGenFunct(this.a), this.c);
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
/*      */   public Obj getUCRFunct() throws PDFNetException {
/*  765 */     return Obj.__Create(GetUCRFunct(this.a), this.c);
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
/*      */   public Obj getHalftone() throws PDFNetException {
/*  779 */     return Obj.__Create(GetHalftone(this.a), this.c);
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
/*      */   public void setTransform(Matrix2D paramMatrix2D) throws PDFNetException {
/*  795 */     SetTransform(this.a, paramMatrix2D.__GetHandle());
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
/*      */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/*  820 */     SetTransform(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void concat(Matrix2D paramMatrix2D) throws PDFNetException {
/*  831 */     Concat(this.a, paramMatrix2D.__GetHandle());
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
/*      */   public void concat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/*  847 */     Concat(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrokeColorSpace(ColorSpace paramColorSpace) throws PDFNetException {
/*  858 */     SetStrokeColorSpace(this.a, paramColorSpace.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFillColorSpace(ColorSpace paramColorSpace) throws PDFNetException {
/*  869 */     SetFillColorSpace(this.a, paramColorSpace.a);
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
/*      */   public void setStrokeColor(ColorPt paramColorPt) throws PDFNetException {
/*  882 */     SetStrokeColorPt(this.a, paramColorPt.a);
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
/*      */   public void setStrokeColor(PatternColor paramPatternColor) throws PDFNetException {
/*  896 */     SetStrokeColor(this.a, paramPatternColor.a);
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
/*      */   public void setStrokeColor(PatternColor paramPatternColor, ColorPt paramColorPt) throws PDFNetException {
/*  910 */     SetStrokeColor(this.a, paramPatternColor.a, paramColorPt.a);
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
/*      */   public void setFillColor(ColorPt paramColorPt) throws PDFNetException {
/*  923 */     SetFillColorPt(this.a, paramColorPt.a);
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
/*      */   public void setFillColor(PatternColor paramPatternColor) throws PDFNetException {
/*  936 */     SetFillColor(this.a, paramPatternColor.a);
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
/*      */   public void setFillColor(PatternColor paramPatternColor, ColorPt paramColorPt) throws PDFNetException {
/*  950 */     SetFillColor(this.a, paramPatternColor.a, paramColorPt.a);
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
/*      */   public void setFlatness(double paramDouble) throws PDFNetException {
/*  966 */     SetFlatness(this.a, paramDouble);
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
/*      */   public void setLineCap(int paramInt) throws PDFNetException {
/*  979 */     SetLineCap(this.a, paramInt);
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
/*      */   public void setLineJoin(int paramInt) throws PDFNetException {
/*  993 */     SetLineJoin(this.a, paramInt);
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
/*      */   public void setLineWidth(double paramDouble) throws PDFNetException {
/* 1006 */     SetLineWidth(this.a, paramDouble);
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
/*      */   public void setMiterLimit(double paramDouble) throws PDFNetException {
/* 1019 */     SetMiterLimit(this.a, paramDouble);
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
/*      */   public void setDashPattern(double[] paramArrayOfdouble, double paramDouble) throws PDFNetException {
/* 1036 */     SetDashPattern(this.a, paramArrayOfdouble, paramDouble);
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
/*      */   public void setCharSpacing(double paramDouble) throws PDFNetException {
/* 1050 */     SetCharSpacing(this.a, paramDouble);
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
/*      */   public void setWordSpacing(double paramDouble) throws PDFNetException {
/* 1063 */     SetWordSpacing(this.a, paramDouble);
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
/*      */   public void setHorizontalScale(double paramDouble) throws PDFNetException {
/* 1079 */     SetHorizontalScale(this.a, paramDouble);
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
/*      */   public void setLeading(double paramDouble) throws PDFNetException {
/* 1094 */     SetLeading(this.a, paramDouble);
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
/*      */   public void setFont(Font paramFont, double paramDouble) throws PDFNetException {
/* 1106 */     SetFont(this.a, paramFont.a, paramDouble);
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
/*      */   public void setTextRenderMode(int paramInt) throws PDFNetException {
/* 1120 */     SetTextRenderMode(this.a, paramInt);
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
/*      */   public void setTextRise(double paramDouble) throws PDFNetException {
/* 1134 */     SetTextRise(this.a, paramDouble);
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
/*      */   public void setTextKnockout(boolean paramBoolean) throws PDFNetException {
/* 1146 */     SetTextKnockout(this.a, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderingIntent(int paramInt) throws PDFNetException {
/* 1157 */     SetRenderingIntent(this.a, paramInt);
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
/*      */   public void setBlendMode(int paramInt) throws PDFNetException {
/* 1175 */     SetBlendMode(this.a, paramInt);
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
/*      */   public void setFillOpacity(double paramDouble) throws PDFNetException {
/* 1187 */     SetFillOpacity(this.a, paramDouble);
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
/*      */   public void setStrokeOpacity(double paramDouble) throws PDFNetException {
/* 1199 */     SetStrokeOpacity(this.a, paramDouble);
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
/*      */   public void setAISFlag(boolean paramBoolean) throws PDFNetException {
/* 1213 */     SetAISFlag(this.a, paramBoolean);
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
/*      */   public void setSoftMask(Obj paramObj) throws PDFNetException {
/* 1225 */     SetSoftMask(this.a, paramObj.__GetHandle());
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
/*      */   public void setStrokeOverprint(boolean paramBoolean) throws PDFNetException {
/* 1238 */     SetStrokeOverprint(this.a, paramBoolean);
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
/*      */   public void setFillOverprint(boolean paramBoolean) throws PDFNetException {
/* 1250 */     SetFillOverprint(this.a, paramBoolean);
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
/*      */   public void setOverprintMode(int paramInt) throws PDFNetException {
/* 1262 */     SetOverprintMode(this.a, paramInt);
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
/*      */   public void setAutoStrokeAdjust(boolean paramBoolean) throws PDFNetException {
/* 1274 */     SetAutoStrokeAdjust(this.a, paramBoolean);
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
/*      */   public void setSmoothnessTolerance(double paramDouble) throws PDFNetException {
/* 1286 */     SetSmoothnessTolerance(this.a, paramDouble);
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
/*      */   public void setBlackGenFunct(Obj paramObj) throws PDFNetException {
/* 1299 */     SetBlackGenFunct(this.a, paramObj.__GetHandle());
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
/*      */   public void setUCRFunct(Obj paramObj) throws PDFNetException {
/* 1312 */     SetUCRFunct(this.a, paramObj.__GetHandle());
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
/*      */   public void setTransferFunct(Obj paramObj) throws PDFNetException {
/* 1326 */     SetTransferFunct(this.a, paramObj.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHalftone(Obj paramObj) throws PDFNetException {
/* 1337 */     SetHalftone(this.a, paramObj.__GetHandle());
/*      */   }
/*      */ 
/*      */   
/*      */   GState(long paramLong, Object paramObject1, Object paramObject2) {
/* 1342 */     this.a = paramLong;
/* 1343 */     this.b = paramObject1;
/* 1344 */     this.c = paramObject2;
/*      */   }
/*      */   
/*      */   static native long GetTransform(long paramLong);
/*      */   
/*      */   static native long GetStrokeColorSpace(long paramLong);
/*      */   
/*      */   static native long GetFillColorSpace(long paramLong);
/*      */   
/*      */   static native long GetStrokeColor(long paramLong);
/*      */   
/*      */   static native long GetStrokePattern(long paramLong);
/*      */   
/*      */   static native long GetFillColor(long paramLong);
/*      */   
/*      */   static native long GetFillPattern(long paramLong);
/*      */   
/*      */   static native double GetFlatness(long paramLong);
/*      */   
/*      */   static native int GetLineCap(long paramLong);
/*      */   
/*      */   static native int GetLineJoin(long paramLong);
/*      */   
/*      */   static native double GetLineWidth(long paramLong);
/*      */   
/*      */   static native double GetMiterLimit(long paramLong);
/*      */   
/*      */   static native double[] GetDashes(long paramLong);
/*      */   
/*      */   static native double GetPhase(long paramLong);
/*      */   
/*      */   static native double GetCharSpacing(long paramLong);
/*      */   
/*      */   static native double GetWordSpacing(long paramLong);
/*      */   
/*      */   static native double GetHorizontalScale(long paramLong);
/*      */   
/*      */   static native double GetLeading(long paramLong);
/*      */   
/*      */   static native long GetFont(long paramLong);
/*      */   
/*      */   static native double GetFontSize(long paramLong);
/*      */   
/*      */   static native int GetTextRenderMode(long paramLong);
/*      */   
/*      */   static native double GetTextRise(long paramLong);
/*      */   
/*      */   static native boolean IsTextKnockout(long paramLong);
/*      */   
/*      */   static native int GetRenderingIntent(long paramLong);
/*      */   
/*      */   static native int GetBlendMode(long paramLong);
/*      */   
/*      */   static native double GetFillOpacity(long paramLong);
/*      */   
/*      */   static native double GetStrokeOpacity(long paramLong);
/*      */   
/*      */   static native boolean GetAISFlag(long paramLong);
/*      */   
/*      */   static native long GetSoftMask(long paramLong);
/*      */   
/*      */   static native long GetSoftMaskTransform(long paramLong);
/*      */   
/*      */   static native boolean GetStrokeOverprint(long paramLong);
/*      */   
/*      */   static native boolean GetFillOverprint(long paramLong);
/*      */   
/*      */   static native int GetOverprintMode(long paramLong);
/*      */   
/*      */   static native boolean GetAutoStrokeAdjust(long paramLong);
/*      */   
/*      */   static native double GetSmoothnessTolerance(long paramLong);
/*      */   
/*      */   static native long GetTransferFunct(long paramLong);
/*      */   
/*      */   static native long GetBlackGenFunct(long paramLong);
/*      */   
/*      */   static native long GetUCRFunct(long paramLong);
/*      */   
/*      */   static native long GetHalftone(long paramLong);
/*      */   
/*      */   static native void SetTransform(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetTransform(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*      */   
/*      */   static native void Concat(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void Concat(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*      */   
/*      */   static native void SetStrokeColorSpace(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetFillColorSpace(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetStrokeColorPt(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetStrokeColor(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetStrokeColor(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void SetFillColorPt(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetFillColor(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetFillColor(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void SetFlatness(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetLineCap(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetLineJoin(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetLineWidth(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetMiterLimit(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetDashPattern(long paramLong, double[] paramArrayOfdouble, double paramDouble);
/*      */   
/*      */   static native void SetCharSpacing(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetWordSpacing(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetHorizontalScale(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetLeading(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetFont(long paramLong1, long paramLong2, double paramDouble);
/*      */   
/*      */   static native void SetTextRenderMode(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetTextRise(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetTextKnockout(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native void SetRenderingIntent(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetBlendMode(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetFillOpacity(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetStrokeOpacity(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetAISFlag(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native void SetSoftMask(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetStrokeOverprint(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native void SetFillOverprint(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native void SetOverprintMode(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetAutoStrokeAdjust(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native void SetSmoothnessTolerance(long paramLong, double paramDouble);
/*      */   
/*      */   static native void SetBlackGenFunct(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetUCRFunct(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetTransferFunct(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void SetHalftone(long paramLong1, long paramLong2);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\GState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */