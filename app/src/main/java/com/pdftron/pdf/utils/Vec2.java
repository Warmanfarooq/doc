/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.graphics.PointF;
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
/*     */ public class Vec2
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   
/*     */   public Vec2() {
/*  22 */     this.x = 0.0D;
/*  23 */     this.y = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec2(double x, double y) {
/*  33 */     this.x = x;
/*  34 */     this.y = y;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  39 */     if (obj != null && obj instanceof Vec2) {
/*  40 */       Vec2 v = (Vec2)obj;
/*  41 */       if (this.x == v.x && this.y == v.y) return true; 
/*  42 */       return false;
/*     */     } 
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/*  53 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void normalize() {
/*  60 */     double len = length();
/*  61 */     this.x /= len;
/*  62 */     this.y /= len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec2 getVectorTo(Vec2 point) {
/*  72 */     Vec2 aux = new Vec2();
/*     */     
/*  74 */     aux.setX(point.x - this.x);
/*  75 */     aux.setY(point.y - this.y);
/*     */     
/*  77 */     return aux;
/*     */   }
/*     */   
/*     */   public Vec2 getVectorTo(int x, int y) {
/*  81 */     Vec2 aux = new Vec2();
/*     */     
/*  83 */     aux.setX(x - this.x);
/*  84 */     aux.setY(y - this.y);
/*     */     
/*  86 */     return aux;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double x, double y) {
/*  96 */     this.x = x;
/*  97 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(double x) {
/* 106 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(double y) {
/* 115 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void offset(double x, double y) {
/* 125 */     this.x += x;
/* 126 */     this.y += y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double x() {
/* 135 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double y() {
/* 144 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dot(Vec2 vec) {
/* 154 */     return (float)(this.x * vec.x() + this.y * vec.y());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Vec2 vec) {
/* 163 */     this.x += vec.x();
/* 164 */     this.y += vec.y();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void subtract(Vec2 vec) {
/* 173 */     this.x -= vec.x();
/* 174 */     this.y -= vec.y();
/*     */   }
/*     */   
/*     */   public static Vec2 add(Vec2 vec1, Vec2 vec2) {
/* 178 */     return new Vec2(vec1.x() + vec2.x(), vec1.y() + vec2.y());
/*     */   }
/*     */   
/*     */   public static Vec2 subtract(Vec2 vec1, Vec2 vec2) {
/* 182 */     return new Vec2(vec1.x() - vec2.x(), vec1.y() - vec2.y());
/*     */   }
/*     */   
/*     */   public static Vec2 multiply(Vec2 vec1, Vec2 vec2) {
/* 186 */     return new Vec2(vec1.x() * vec2.x(), vec1.y() * vec2.y());
/*     */   }
/*     */   
/*     */   public static Vec2 multiply(Vec2 vec1, double val) {
/* 190 */     return new Vec2(vec1.x() * val, vec1.y() * val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(float val) {
/* 199 */     this.x *= val;
/* 200 */     this.y *= val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec2 getIntValue() {
/* 209 */     Vec2 intVec = new Vec2();
/* 210 */     intVec.set((int)this.x, (int)this.y);
/* 211 */     return intVec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean roundEqual(Vec2 vec) {
/* 221 */     return (Math.round(this.x) == Math.round(vec.x()) && Math.round(this.y) == Math.round(vec.y()));
/*     */   }
/*     */   
/*     */   public Vec2 getPerp() {
/* 225 */     return new Vec2(-this.y, this.x);
/*     */   }
/*     */   
/*     */   public PointF toPointF() {
/* 229 */     return new PointF((float)this.x, (float)this.y);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\Vec2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */