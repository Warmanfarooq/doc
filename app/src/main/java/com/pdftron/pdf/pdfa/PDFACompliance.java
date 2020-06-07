/*     */ package com.pdftron.pdf.pdfa;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ 
/*     */ public class PDFACompliance
/*     */ {
/*     */   public static final int e_Level1A = 1;
/*     */   public static final int e_Level1B = 2;
/*     */   public static final int e_Level2A = 3;
/*     */   public static final int e_Level2B = 4;
/*     */   public static final int e_Level2U = 5;
/*     */   public static final int e_Level3A = 6;
/*     */   public static final int e_Level3B = 7;
/*     */   public static final int e_Level3U = 8;
/*     */   public static final int e_PDFA0_1_0 = 10;
/*     */   public static final int e_PDFA0_1_1 = 11;
/*     */   public static final int e_PDFA0_1_2 = 12;
/*     */   public static final int e_PDFA0_1_3 = 13;
/*     */   public static final int e_PDFA0_1_4 = 14;
/*     */   public static final int e_PDFA0_1_5 = 15;
/*     */   public static final int e_PDFA1_2_1 = 121;
/*     */   public static final int e_PDFA1_2_2 = 122;
/*     */   public static final int e_PDFA1_3_1 = 131;
/*     */   public static final int e_PDFA1_3_2 = 132;
/*     */   public static final int e_PDFA1_3_3 = 133;
/*     */   public static final int e_PDFA1_3_4 = 134;
/*     */   public static final int e_PDFA1_4_1 = 141;
/*     */   public static final int e_PDFA1_4_2 = 142;
/*     */   public static final int e_PDFA1_6_1 = 161;
/*     */   public static final int e_PDFA1_7_1 = 171;
/*     */   public static final int e_PDFA1_7_2 = 172;
/*     */   public static final int e_PDFA1_7_3 = 173;
/*     */   public static final int e_PDFA1_7_4 = 174;
/*     */   public static final int e_PDFA1_8_1 = 181;
/*     */   public static final int e_PDFA1_8_2 = 182;
/*     */   public static final int e_PDFA1_8_3 = 183;
/*     */   public static final int e_PDFA1_8_4 = 184;
/*     */   public static final int e_PDFA1_8_5 = 185;
/*     */   public static final int e_PDFA1_8_6 = 186;
/*     */   public static final int e_PDFA1_8_7 = 187;
/*     */   public static final int e_PDFA1_10_1 = 1101;
/*     */   public static final int e_PDFA1_11_1 = 1111;
/*     */   public static final int e_PDFA1_11_2 = 1112;
/*     */   public static final int e_PDFA1_12_1 = 1121;
/*     */   public static final int e_PDFA1_12_2 = 1122;
/*     */   public static final int e_PDFA1_12_3 = 1123;
/*     */   public static final int e_PDFA1_12_4 = 1124;
/*     */   public static final int e_PDFA1_12_5 = 1125;
/*     */   public static final int e_PDFA1_12_6 = 1126;
/*     */   public static final int e_PDFA1_13_1 = 1131;
/*     */   public static final int e_PDFA2_2_1 = 221;
/*     */   public static final int e_PDFA2_3_2 = 232;
/*     */   public static final int e_PDFA2_3_3 = 233;
/*     */   public static final int e_PDFA2_3_3_1 = 2331;
/*     */   public static final int e_PDFA2_3_3_2 = 2332;
/*     */   public static final int e_PDFA2_3_4_1 = 2341;
/*     */   public static final int e_PDFA2_4_1 = 241;
/*     */   public static final int e_PDFA2_4_2 = 242;
/*     */   public static final int e_PDFA2_4_3 = 243;
/*     */   public static final int e_PDFA2_4_4 = 244;
/*     */   public static final int e_PDFA2_5_1 = 251;
/*     */   public static final int e_PDFA2_5_2 = 252;
/*     */   public static final int e_PDFA2_6_1 = 261;
/*     */   public static final int e_PDFA2_7_1 = 271;
/*     */   public static final int e_PDFA2_8_1 = 281;
/*     */   public static final int e_PDFA2_9_1 = 291;
/*     */   public static final int e_PDFA2_10_1 = 2101;
/*     */   public static final int e_PDFA3_2_1 = 321;
/*     */   public static final int e_PDFA3_3_1 = 331;
/*     */   public static final int e_PDFA3_3_2 = 332;
/*     */   public static final int e_PDFA3_3_3_1 = 3331;
/*     */   public static final int e_PDFA3_3_3_2 = 3332;
/*     */   public static final int e_PDFA3_4_1 = 341;
/*     */   public static final int e_PDFA3_5_1 = 351;
/*     */   public static final int e_PDFA3_5_2 = 352;
/*     */   public static final int e_PDFA3_5_3 = 353;
/*     */   public static final int e_PDFA3_5_4 = 354;
/*     */   public static final int e_PDFA3_5_5 = 355;
/*     */   public static final int e_PDFA3_5_6 = 356;
/*     */   public static final int e_PDFA3_6_1 = 361;
/*     */   public static final int e_PDFA3_7_1 = 371;
/*     */   public static final int e_PDFA3_7_2 = 372;
/*     */   public static final int e_PDFA3_7_3 = 373;
/*     */   public static final int e_PDFA4_1 = 41;
/*     */   public static final int e_PDFA4_2 = 42;
/*     */   public static final int e_PDFA4_3 = 43;
/*     */   public static final int e_PDFA4_4 = 44;
/*     */   public static final int e_PDFA4_5 = 45;
/*     */   public static final int e_PDFA4_6 = 46;
/*     */   public static final int e_PDFA5_2_1 = 521;
/*     */   public static final int e_PDFA5_2_2 = 522;
/*     */   public static final int e_PDFA5_2_3 = 523;
/*     */   public static final int e_PDFA5_2_4 = 524;
/*     */   public static final int e_PDFA5_2_5 = 525;
/*     */   public static final int e_PDFA5_2_6 = 526;
/*     */   public static final int e_PDFA5_2_7 = 527;
/*     */   public static final int e_PDFA5_2_8 = 528;
/*     */   public static final int e_PDFA5_2_9 = 529;
/*     */   public static final int e_PDFA5_2_10 = 5210;
/*     */   public static final int e_PDFA5_2_11 = 5211;
/*     */   public static final int e_PDFA5_3_1 = 531;
/*     */   public static final int e_PDFA5_3_2_1 = 5321;
/*     */   public static final int e_PDFA5_3_2_2 = 5322;
/*     */   public static final int e_PDFA5_3_2_3 = 5323;
/*     */   public static final int e_PDFA5_3_2_4 = 5324;
/*     */   public static final int e_PDFA5_3_2_5 = 5325;
/*     */   public static final int e_PDFA5_3_3_1 = 5331;
/*     */   public static final int e_PDFA5_3_3_2 = 5332;
/*     */   public static final int e_PDFA5_3_3_3 = 5333;
/*     */   public static final int e_PDFA5_3_3_4 = 5334;
/*     */   public static final int e_PDFA5_3_4_1 = 5341;
/*     */   public static final int e_PDFA5_3_4_2 = 5342;
/*     */   public static final int e_PDFA5_3_4_3 = 5343;
/*     */   public static final int e_PDFA6_1_1 = 611;
/*     */   public static final int e_PDFA6_1_2 = 612;
/*     */   public static final int e_PDFA6_2_1 = 621;
/*     */   public static final int e_PDFA6_2_2 = 622;
/*     */   public static final int e_PDFA6_2_3 = 623;
/*     */   public static final int e_PDFA7_2_1 = 721;
/*     */   public static final int e_PDFA7_2_2 = 722;
/*     */   public static final int e_PDFA7_2_3 = 723;
/*     */   public static final int e_PDFA7_2_4 = 724;
/*     */   public static final int e_PDFA7_2_5 = 725;
/*     */   public static final int e_PDFA7_3_1 = 731;
/*     */   public static final int e_PDFA7_3_2 = 732;
/*     */   public static final int e_PDFA7_3_3 = 733;
/*     */   public static final int e_PDFA7_3_4 = 734;
/*     */   public static final int e_PDFA7_3_5 = 735;
/*     */   public static final int e_PDFA7_3_6 = 736;
/*     */   public static final int e_PDFA7_3_7 = 737;
/*     */   public static final int e_PDFA7_3_8 = 738;
/*     */   public static final int e_PDFA7_3_9 = 739;
/*     */   public static final int e_PDFA7_5_1 = 751;
/*     */   public static final int e_PDFA7_8_1 = 781;
/*     */   public static final int e_PDFA7_8_2 = 782;
/*     */   public static final int e_PDFA7_8_3 = 783;
/*     */   public static final int e_PDFA7_8_4 = 784;
/*     */   public static final int e_PDFA7_8_5 = 785;
/*     */   public static final int e_PDFA7_8_6 = 786;
/*     */   public static final int e_PDFA7_8_7 = 787;
/*     */   public static final int e_PDFA7_8_8 = 788;
/*     */   public static final int e_PDFA7_8_9 = 789;
/*     */   public static final int e_PDFA7_8_10 = 7810;
/*     */   public static final int e_PDFA7_8_11 = 7811;
/*     */   public static final int e_PDFA7_8_12 = 7812;
/*     */   public static final int e_PDFA7_8_13 = 7813;
/*     */   public static final int e_PDFA7_8_14 = 7814;
/*     */   public static final int e_PDFA7_8_15 = 7815;
/*     */   public static final int e_PDFA7_8_16 = 7816;
/*     */   public static final int e_PDFA7_8_17 = 7817;
/*     */   public static final int e_PDFA7_8_18 = 7818;
/*     */   public static final int e_PDFA7_8_19 = 7819;
/*     */   public static final int e_PDFA7_8_20 = 7820;
/*     */   public static final int e_PDFA7_8_21 = 7821;
/*     */   public static final int e_PDFA7_8_22 = 7822;
/*     */   public static final int e_PDFA7_8_23 = 7823;
/*     */   public static final int e_PDFA7_8_24 = 7824;
/*     */   public static final int e_PDFA7_8_25 = 7825;
/*     */   public static final int e_PDFA7_8_26 = 7826;
/*     */   public static final int e_PDFA7_8_27 = 7827;
/*     */   public static final int e_PDFA7_8_28 = 7828;
/*     */   public static final int e_PDFA7_8_29 = 7829;
/*     */   public static final int e_PDFA7_8_30 = 7830;
/*     */   public static final int e_PDFA7_8_31 = 7831;
/*     */   public static final int e_PDFA7_11_1 = 7111;
/*     */   public static final int e_PDFA7_11_2 = 7112;
/*     */   public static final int e_PDFA7_11_3 = 7113;
/*     */   public static final int e_PDFA7_11_4 = 7114;
/*     */   public static final int e_PDFA7_11_5 = 7115;
/*     */   public static final int e_PDFA9_1 = 91;
/*     */   public static final int e_PDFA9_2 = 92;
/*     */   public static final int e_PDFA9_3 = 93;
/*     */   public static final int e_PDFA9_4 = 94;
/*     */   public static final int e_PDFA3_8_1 = 381;
/*     */   public static final int e_PDFA8_2_2 = 822;
/*     */   public static final int e_PDFA8_3_3_1 = 8331;
/*     */   public static final int e_PDFA8_3_3_2 = 8332;
/*     */   public static final int e_PDFA8_3_4_1 = 8341;
/*     */   public static final int e_PDFA1_2_3 = 123;
/*     */   public static final int e_PDFA1_10_2 = 1102;
/*     */   public static final int e_PDFA1_10_3 = 1103;
/*     */   public static final int e_PDFA1_12_10 = 11210;
/*     */   public static final int e_PDFA1_13_5 = 1135;
/*     */   public static final int e_PDFA2_3_10 = 2310;
/*     */   public static final int e_PDFA2_4_2_10 = 24220;
/*     */   public static final int e_PDFA2_4_2_11 = 24221;
/*     */   public static final int e_PDFA2_4_2_12 = 24222;
/*     */   public static final int e_PDFA2_4_2_13 = 24223;
/*     */   public static final int e_PDFA2_5_10 = 2510;
/*     */   public static final int e_PDFA2_5_11 = 2511;
/*     */   public static final int e_PDFA2_5_12 = 2512;
/*     */   public static final int e_PDFA2_8_3_1 = 2831;
/*     */   public static final int e_PDFA2_8_3_2 = 2832;
/*     */   public static final int e_PDFA2_8_3_3 = 2833;
/*     */   public static final int e_PDFA2_8_3_4 = 2834;
/*     */   public static final int e_PDFA2_8_3_5 = 2835;
/*     */   public static final int e_PDFA2_10_20 = 21020;
/*     */   public static final int e_PDFA2_10_21 = 21021;
/*     */   public static final int e_PDFA11_0_0 = 11000;
/*     */   public static final int e_PDFA6_10_0 = 6100;
/*     */   public static final int e_PDFA6_10_1 = 6101;
/*     */   public static final int e_PDFA6_2_11_5 = 62115;
/*     */   public static final int e_PDFA6_2_11_6 = 62116;
/*     */   public static final int e_PDFA6_2_11_7 = 62117;
/*     */   public static final int e_PDFA6_2_11_8 = 62118;
/*     */   public static final int e_PDFA6_9_1 = 69001;
/*     */   public static final int e_PDFA6_9_3 = 69003;
/*     */   public static final int e_PDFA8_1 = 81;
/*     */   public static final int e_PDFA_3E1 = 1;
/*     */   public static final int e_PDFA_3E1_1 = 101;
/*     */   public static final int e_PDFA_3E2 = 2;
/*     */   public static final int e_PDFA_3E3 = 3;
/*     */   private long a;
/*     */   
/*     */   public PDFACompliance(boolean paramBoolean1, String paramString1, String paramString2, int paramInt1, int[] paramArrayOfint, int paramInt2, boolean paramBoolean2) throws PDFNetException {
/*     */     int i;
/* 607 */     if (paramArrayOfint == null) {
/*     */       
/* 609 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 613 */       i = paramArrayOfint.length;
/*     */     } 
/* 615 */     this.a = PDFAComplianceCreate(paramBoolean1, paramString1, paramString2, paramInt1, paramArrayOfint, i, paramInt2, paramBoolean2);
/*     */   }
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
/*     */   public PDFACompliance(boolean paramBoolean, String paramString1, String paramString2, int paramInt1, int[] paramArrayOfint, int paramInt2) throws PDFNetException {
/*     */     int i;
/* 635 */     if (paramArrayOfint == null) {
/*     */       
/* 637 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 641 */       i = paramArrayOfint.length;
/*     */     } 
/* 643 */     this.a = PDFAComplianceCreate(paramBoolean, paramString1, paramString2, paramInt1, paramArrayOfint, i, paramInt2);
/*     */   }
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
/*     */   public PDFACompliance(boolean paramBoolean, String paramString1, String paramString2, int[] paramArrayOfint) throws PDFNetException {
/*     */     int i;
/* 662 */     if (paramArrayOfint == null) {
/*     */       
/* 664 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 668 */       i = paramArrayOfint.length;
/*     */     } 
/* 670 */     this.a = PDFAComplianceCreate(paramBoolean, paramString1, paramString2, paramArrayOfint, i);
/*     */   }
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
/*     */   public PDFACompliance(boolean paramBoolean1, byte[] paramArrayOfbyte, String paramString, int paramInt1, int[] paramArrayOfint, int paramInt2, boolean paramBoolean2) throws PDFNetException {
/*     */     int i, j;
/* 693 */     if (paramArrayOfint == null) {
/*     */       
/* 695 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 699 */       i = paramArrayOfint.length;
/*     */     } 
/* 701 */     if (paramArrayOfbyte == null) {
/*     */       
/* 703 */       j = 0;
/*     */     }
/*     */     else {
/*     */       
/* 707 */       j = paramArrayOfbyte.length;
/*     */     } 
/* 709 */     this.a = PDFAComplianceCreate(paramBoolean1, paramArrayOfbyte, j, paramString, paramInt1, paramArrayOfint, i, paramInt2, paramBoolean2);
/*     */   }
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
/*     */   public PDFACompliance(boolean paramBoolean, byte[] paramArrayOfbyte, String paramString, int paramInt1, int[] paramArrayOfint, int paramInt2) throws PDFNetException {
/*     */     int i, j;
/* 731 */     if (paramArrayOfint == null) {
/*     */       
/* 733 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 737 */       i = paramArrayOfint.length;
/*     */     } 
/* 739 */     if (paramArrayOfbyte == null) {
/*     */       
/* 741 */       j = 0;
/*     */     }
/*     */     else {
/*     */       
/* 745 */       j = paramArrayOfbyte.length;
/*     */     } 
/* 747 */     this.a = PDFAComplianceCreate(paramBoolean, paramArrayOfbyte, j, paramString, paramInt1, paramArrayOfint, i, paramInt2);
/*     */   }
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
/*     */   public PDFACompliance(boolean paramBoolean, byte[] paramArrayOfbyte, String paramString, int[] paramArrayOfint) throws PDFNetException {
/*     */     int i, j;
/* 768 */     if (paramArrayOfint == null) {
/*     */       
/* 770 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 774 */       i = paramArrayOfint.length;
/*     */     } 
/* 776 */     if (paramArrayOfbyte == null) {
/*     */       
/* 778 */       j = 0;
/*     */     }
/*     */     else {
/*     */       
/* 782 */       j = paramArrayOfbyte.length;
/*     */     } 
/* 784 */     this.a = PDFAComplianceCreate(paramBoolean, paramArrayOfbyte, j, paramString, paramArrayOfint, i);
/*     */   }
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
/*     */   public void saveAs(String paramString, boolean paramBoolean) throws PDFNetException {
/* 800 */     Save(this.a, paramString, paramBoolean);
/*     */   }
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
/*     */   public byte[] saveAs(boolean paramBoolean) throws PDFNetException {
/* 816 */     return Save(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getErrorCount() throws PDFNetException {
/* 827 */     return GetErrorCount(this.a);
/*     */   }
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
/*     */   public int getError(int paramInt) throws PDFNetException {
/* 840 */     return GetError(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRefObjCount(int paramInt) throws PDFNetException {
/* 852 */     return GetRefObjCount(this.a, paramInt);
/*     */   }
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
/*     */   public int getRefObj(int paramInt1, int paramInt2) throws PDFNetException {
/* 867 */     return GetRefObj(this.a, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPDFAErrorMessage(int paramInt) throws PDFNetException {
/* 879 */     return GetPDFAErrorMessage(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 891 */     if (this.a != 0L) {
/*     */       
/* 893 */       Destroy(this.a);
/* 894 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   static native long PDFAComplianceCreate(boolean paramBoolean1, String paramString1, String paramString2, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3, boolean paramBoolean2);
/*     */   
/*     */   static native long PDFAComplianceCreate(boolean paramBoolean, String paramString1, String paramString2, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3);
/*     */   
/*     */   static native long PDFAComplianceCreate(boolean paramBoolean, String paramString1, String paramString2, int[] paramArrayOfint, int paramInt);
/*     */   
/*     */   static native long PDFAComplianceCreate(boolean paramBoolean1, byte[] paramArrayOfbyte, int paramInt1, String paramString, int paramInt2, int[] paramArrayOfint, int paramInt3, int paramInt4, boolean paramBoolean2);
/*     */   
/*     */   static native long PDFAComplianceCreate(boolean paramBoolean, byte[] paramArrayOfbyte, int paramInt1, String paramString, int paramInt2, int[] paramArrayOfint, int paramInt3, int paramInt4);
/*     */   
/*     */   static native long PDFAComplianceCreate(boolean paramBoolean, byte[] paramArrayOfbyte, int paramInt1, String paramString, int[] paramArrayOfint, int paramInt2);
/*     */   
/*     */   static native void Save(long paramLong, String paramString, boolean paramBoolean);
/*     */   
/*     */   static native byte[] Save(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native int GetErrorCount(long paramLong);
/*     */   
/*     */   static native int GetError(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetRefObjCount(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetRefObj(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native String GetPDFAErrorMessage(int paramInt);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\pdfa\PDFACompliance.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */