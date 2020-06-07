/*     */ package com.rarepebble.colorpicker;
/*     */ 
/*     */ import android.graphics.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ObservableColor
/*     */ {
/*  31 */   private final float[] hsv = new float[] { 0.0F, 0.0F, 0.0F };
/*     */   private int alpha;
/*  33 */   private final List<Observer> observers = new ArrayList<>();
/*     */   
/*     */   ObservableColor(int color) {
/*  36 */     Color.colorToHSV(color, this.hsv);
/*  37 */     this.alpha = Color.alpha(color);
/*     */   }
/*     */   
/*     */   public void getHsv(float[] hsvOut) {
/*  41 */     hsvOut[0] = this.hsv[0];
/*  42 */     hsvOut[1] = this.hsv[1];
/*  43 */     hsvOut[2] = this.hsv[2];
/*     */   }
/*     */   
/*     */   public int getColor() {
/*  47 */     return Color.HSVToColor(this.alpha, this.hsv);
/*     */   }
/*     */   
/*     */   public float getHue() {
/*  51 */     return this.hsv[0];
/*     */   }
/*     */   
/*     */   public float getSat() {
/*  55 */     return this.hsv[1];
/*     */   }
/*     */   
/*     */   public float getValue() {
/*  59 */     return this.hsv[2];
/*     */   }
/*     */   
/*     */   public int getAlpha() {
/*  63 */     return this.alpha;
/*     */   }
/*     */   
/*     */   public float getLightness() {
/*  67 */     return getLightnessWithValue(this.hsv[2]);
/*     */   }
/*     */   
/*     */   public float getLightnessWithValue(float value) {
/*  71 */     float[] hsV = { this.hsv[0], this.hsv[1], value };
/*  72 */     int color = Color.HSVToColor(hsV);
/*  73 */     return (Color.red(color) * 0.2126F + Color.green(color) * 0.7152F + Color.blue(color) * 0.0722F) / 255.0F;
/*     */   }
/*     */   
/*     */   public void addObserver(Observer observer) {
/*  77 */     this.observers.add(observer);
/*     */   }
/*     */   
/*     */   public void updateHueSat(float hue, float sat, Observer sender) {
/*  81 */     this.hsv[0] = hue;
/*  82 */     this.hsv[1] = sat;
/*  83 */     notifyOtherObservers(sender);
/*     */   }
/*     */   
/*     */   public void updateValue(float value, Observer sender) {
/*  87 */     this.hsv[2] = value;
/*  88 */     notifyOtherObservers(sender);
/*     */   }
/*     */   
/*     */   public void updateAlpha(int alpha, Observer sender) {
/*  92 */     this.alpha = alpha;
/*  93 */     notifyOtherObservers(sender);
/*     */   }
/*     */   
/*     */   public void updateColor(int color, Observer sender) {
/*  97 */     Color.colorToHSV(color, this.hsv);
/*  98 */     this.alpha = Color.alpha(color);
/*  99 */     notifyOtherObservers(sender);
/*     */   }
/*     */   
/*     */   private void notifyOtherObservers(Observer sender) {
/* 103 */     for (Observer observer : this.observers) {
/* 104 */       if (observer != sender)
/* 105 */         observer.updateColor(this); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Observer {
/*     */     void updateColor(ObservableColor param1ObservableColor);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\ObservableColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */