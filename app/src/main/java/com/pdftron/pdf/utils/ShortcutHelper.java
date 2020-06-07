/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.view.KeyEvent;
/*     */ import android.view.MotionEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShortcutHelper
/*     */ {
/*     */   private static boolean sEnabled = false;
/*     */   
/*     */   public static void enable(boolean enabled) {
/*  26 */     sEnabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled() {
/*  33 */     return sEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHighlightAnnot(int keyCode, KeyEvent event) {
/*  43 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 36);
/*     */     
/*  45 */     if (b) {
/*  46 */       sendEvent(1);
/*     */     }
/*  48 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUnderlineAnnot(int keyCode, KeyEvent event) {
/*  58 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 49);
/*     */     
/*  60 */     if (b) {
/*  61 */       sendEvent(2);
/*     */     }
/*  63 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStrikethroughAnnot(int keyCode, KeyEvent event) {
/*  73 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && (keyCode == 39 || keyCode == 52));
/*     */     
/*  75 */     if (b) {
/*  76 */       sendEvent(3);
/*     */     }
/*  78 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSquigglyAnnot(int keyCode, KeyEvent event) {
/*  88 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 35);
/*     */     
/*  90 */     if (b) {
/*  91 */       sendEvent(4);
/*     */     }
/*  93 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTextboxAnnot(int keyCode, KeyEvent event) {
/* 103 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 48);
/*     */     
/* 105 */     if (b) {
/* 106 */       sendEvent(5);
/*     */     }
/* 108 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCommentAnnot(int keyCode, KeyEvent event) {
/* 118 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && (keyCode == 31 || keyCode == 42));
/*     */     
/* 120 */     if (b) {
/* 121 */       sendEvent(6);
/*     */     }
/* 123 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRectangleAnnot(int keyCode, KeyEvent event) {
/* 133 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 46);
/*     */     
/* 135 */     if (b) {
/* 136 */       sendEvent(7);
/*     */     }
/* 138 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOvalAnnot(int keyCode, KeyEvent event) {
/* 148 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 43);
/*     */     
/* 150 */     if (b) {
/* 151 */       sendEvent(8);
/*     */     }
/* 153 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDrawAnnot(int keyCode, KeyEvent event) {
/* 163 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && (keyCode == 32 || keyCode == 44 || keyCode == 34));
/*     */     
/* 165 */     if (b) {
/* 166 */       sendEvent(9);
/*     */     }
/* 168 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEraserAnnot(int keyCode, KeyEvent event) {
/* 178 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 33);
/*     */     
/* 180 */     if (b) {
/* 181 */       sendEvent(10);
/*     */     }
/* 183 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLineAnnot(int keyCode, KeyEvent event) {
/* 193 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 40);
/*     */     
/* 195 */     if (b) {
/* 196 */       sendEvent(11);
/*     */     }
/* 198 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isArrowAnnot(int keyCode, KeyEvent event) {
/* 208 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 29);
/*     */     
/* 210 */     if (b) {
/* 211 */       sendEvent(12);
/*     */     }
/* 213 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSignatureAnnot(int keyCode, KeyEvent event) {
/* 223 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 47);
/*     */     
/* 225 */     if (b) {
/* 226 */       sendEvent(13);
/*     */     }
/* 228 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isImageAnnot(int keyCode, KeyEvent event) {
/* 238 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 37);
/*     */     
/* 240 */     if (b) {
/* 241 */       sendEvent(14);
/*     */     }
/* 243 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHyperlinkAnnot(int keyCode, KeyEvent event) {
/* 253 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 39);
/*     */     
/* 255 */     if (b) {
/* 256 */       sendEvent(15);
/*     */     }
/* 258 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDeleteAnnot(int keyCode, KeyEvent event) {
/* 268 */     boolean b = ((sEnabled && keyCode == 112) || keyCode == 67);
/* 269 */     if (b) {
/* 270 */       sendEvent(16);
/*     */     }
/* 272 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGotoNextDoc(int keyCode, KeyEvent event) {
/* 282 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 61);
/*     */     
/* 284 */     if (b) {
/* 285 */       sendEvent(17);
/*     */     }
/* 287 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGotoPreviousDoc(int keyCode, KeyEvent event) {
/* 297 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && event.isShiftPressed() && keyCode == 61);
/*     */     
/* 299 */     if (b) {
/* 300 */       sendEvent(18);
/*     */     }
/* 302 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFind(int keyCode, KeyEvent event) {
/* 312 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 34);
/*     */     
/* 314 */     if (b) {
/* 315 */       sendEvent(19);
/*     */     }
/* 317 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGotoNextSearch(int keyCode, KeyEvent event) {
/* 327 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 66);
/*     */     
/* 329 */     if (b) {
/* 330 */       sendEvent(20);
/*     */     }
/* 332 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGotoPreviousSearch(int keyCode, KeyEvent event) {
/* 342 */     boolean b = (sEnabled && !event.isCtrlPressed() && !event.isAltPressed() && event.isShiftPressed() && keyCode == 66);
/*     */     
/* 344 */     if (b) {
/* 345 */       sendEvent(21);
/*     */     }
/* 347 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUndo(int keyCode, KeyEvent event) {
/* 357 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 54);
/*     */     
/* 359 */     if (b) {
/* 360 */       sendEvent(22);
/*     */     }
/* 362 */     return b;
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
/*     */   public static boolean isRedo(int keyCode, KeyEvent event) {
/* 374 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && ((!event.isShiftPressed() && keyCode == 53) || (event.isShiftPressed() && keyCode == 54)));
/* 375 */     if (b) {
/* 376 */       sendEvent(23);
/*     */     }
/* 378 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCopy(int keyCode, KeyEvent event) {
/* 388 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 31);
/*     */     
/* 390 */     if (b) {
/* 391 */       sendEvent(24);
/*     */     }
/* 393 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCut(int keyCode, KeyEvent event) {
/* 403 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 52);
/*     */     
/* 405 */     if (b) {
/* 406 */       sendEvent(25);
/*     */     }
/* 408 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPaste(int keyCode, KeyEvent event) {
/* 418 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 50);
/*     */     
/* 420 */     if (b) {
/* 421 */       sendEvent(26);
/*     */     }
/* 423 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPrint(int keyCode, KeyEvent event) {
/* 433 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 44);
/*     */     
/* 435 */     if (b) {
/* 436 */       sendEvent(27);
/*     */     }
/* 438 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAddBookmark(int keyCode, KeyEvent event) {
/* 448 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 32);
/*     */     
/* 450 */     if (b) {
/* 451 */       sendEvent(28);
/*     */     }
/* 453 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScrollToLeft(int keyCode, KeyEvent event) {
/* 463 */     return (sEnabled && keyCode == 21);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScrollToRight(int keyCode, KeyEvent event) {
/* 473 */     return (sEnabled && keyCode == 22);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScrollToUp(int keyCode, KeyEvent event) {
/* 483 */     return (sEnabled && keyCode == 19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScrollToDown(int keyCode, KeyEvent event) {
/* 493 */     return (sEnabled && keyCode == 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPageUp(int keyCode, KeyEvent event) {
/* 503 */     boolean b = (sEnabled && keyCode == 92);
/* 504 */     if (b) {
/* 505 */       sendEvent(29);
/*     */     }
/* 507 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPageDown(int keyCode, KeyEvent event) {
/* 517 */     boolean b = (sEnabled && (keyCode == 93 || keyCode == 62));
/* 518 */     if (b) {
/* 519 */       sendEvent(30);
/*     */     }
/* 521 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGotoFirstPage(int keyCode, KeyEvent event) {
/* 531 */     boolean b = (sEnabled && keyCode == 122);
/* 532 */     if (b) {
/* 533 */       sendEvent(31);
/*     */     }
/* 535 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGotoLastPage(int keyCode, KeyEvent event) {
/* 545 */     boolean b = (sEnabled && keyCode == 123);
/* 546 */     if (b) {
/* 547 */       sendEvent(32);
/*     */     }
/* 549 */     return b;
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
/*     */   public static boolean isJumpPageBack(int keyCode, KeyEvent event) {
/* 561 */     boolean b = ((sEnabled && !event.isShiftPressed() && event.isCtrlPressed() && !event.isAltPressed() && keyCode == 71) || (!event.isCtrlPressed() && event.isAltPressed() && keyCode == 21));
/* 562 */     if (b) {
/* 563 */       sendEvent(33);
/*     */     }
/* 565 */     return b;
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
/*     */   public static boolean isJumpPageForward(int keyCode, KeyEvent event) {
/* 577 */     boolean b = ((sEnabled && !event.isShiftPressed() && event.isCtrlPressed() && !event.isAltPressed() && keyCode == 72) || (!event.isCtrlPressed() && event.isAltPressed() && keyCode == 22));
/* 578 */     if (b) {
/* 579 */       sendEvent(34);
/*     */     }
/* 581 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStartEdit(int keyCode, KeyEvent event) {
/* 591 */     boolean b = (sEnabled && keyCode == 66);
/* 592 */     if (b) {
/* 593 */       sendEvent(35);
/*     */     }
/* 595 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSwitchForm(int keyCode, KeyEvent event) {
/* 605 */     boolean b = (sEnabled && keyCode == 61);
/* 606 */     if (b) {
/* 607 */       sendEvent(36);
/*     */     }
/* 609 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRotateClockwise(int keyCode, KeyEvent event) {
/* 619 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && event.isShiftPressed() && (keyCode == 81 || keyCode == 70 || keyCode == 157));
/*     */     
/* 621 */     if (b) {
/* 622 */       sendEvent(37);
/*     */     }
/* 624 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRotateCounterClockwise(int keyCode, KeyEvent event) {
/* 634 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && event.isShiftPressed() && (keyCode == 69 || keyCode == 156));
/*     */     
/* 636 */     if (b) {
/* 637 */       sendEvent(38);
/*     */     }
/* 639 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isZoomIn(int keyCode, KeyEvent event) {
/* 649 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && (keyCode == 81 || keyCode == 70 || keyCode == 157));
/*     */     
/* 651 */     if (b) {
/* 652 */       sendEvent(39);
/*     */     }
/* 654 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isZoomIn(MotionEvent event) {
/* 663 */     return (sEnabled && (event
/* 664 */       .getSource() & 0x2) != 0 && (event
/* 665 */       .getMetaState() & 0x1000) != 0 && event
/* 666 */       .getAxisValue(9) > 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isZoomOut(int keyCode, KeyEvent event) {
/* 677 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && (keyCode == 69 || keyCode == 156));
/*     */     
/* 679 */     if (b) {
/* 680 */       sendEvent(40);
/*     */     }
/* 682 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isZoomOut(MotionEvent event) {
/* 691 */     return (sEnabled && (event
/* 692 */       .getSource() & 0x2) != 0 && (event
/* 693 */       .getMetaState() & 0x1000) != 0 && event
/* 694 */       .getAxisValue(9) < 0.0F);
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
/*     */   public static boolean isZoomInOut(MotionEvent event) {
/* 706 */     return (sEnabled && event
/* 707 */       .getToolType(0) == 3 && (event
/* 708 */       .getMetaState() & 0x1000) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isResetZoom(int keyCode, KeyEvent event) {
/* 718 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 7);
/*     */     
/* 720 */     if (b) {
/* 721 */       sendEvent(41);
/*     */     }
/* 723 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLongPress(MotionEvent event) {
/* 732 */     return (sEnabled && event
/* 733 */       .getButtonState() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isScroll(MotionEvent event) {
/* 742 */     return (sEnabled && (event
/* 743 */       .getSource() & 0x2) != 0 && (event
/* 744 */       .getMetaState() & 0x1000) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTextSelect(MotionEvent event) {
/* 753 */     return (sEnabled && event
/* 754 */       .getPointerCount() == 1 && event.getToolType(0) == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOpenDrawer(int keyCode, KeyEvent event) {
/* 764 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 43);
/*     */     
/* 766 */     if (b) {
/* 767 */       sendEvent(42);
/*     */     }
/* 769 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCommitText(int keyCode, KeyEvent event) {
/* 780 */     boolean b = (sEnabled && (keyCode == 111 || (event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 66)));
/* 781 */     if (b) {
/* 782 */       sendEvent(43);
/*     */     }
/* 784 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCommitDraw(int keyCode, KeyEvent event) {
/* 794 */     boolean b = (sEnabled && (keyCode == 111 || keyCode == 66));
/* 795 */     if (b) {
/* 796 */       sendEvent(44);
/*     */     }
/* 798 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSwitchInk(int keyCode, KeyEvent event) {
/* 808 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && (keyCode == 8 || keyCode == 9 || keyCode == 10 || keyCode == 11 || keyCode == 12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 814 */     if (b) {
/* 815 */       sendEvent(45);
/*     */     }
/* 817 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEraseInk(int keyCode, KeyEvent event) {
/* 827 */     boolean b = (sEnabled && (keyCode == 7 || keyCode == 33));
/* 828 */     if (b) {
/* 829 */       sendEvent(46);
/*     */     }
/* 831 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCancelTool(int keyCode, KeyEvent event) {
/* 841 */     boolean b = (sEnabled && keyCode == 111);
/* 842 */     if (b) {
/* 843 */       sendEvent(47);
/*     */     }
/* 845 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCloseMenu(int keyCode, KeyEvent event) {
/* 855 */     boolean b = (sEnabled && keyCode == 111);
/* 856 */     if (b) {
/* 857 */       sendEvent(48);
/*     */     }
/* 859 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCloseTab(int keyCode, KeyEvent event) {
/* 869 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && !event.isShiftPressed() && keyCode == 51);
/*     */     
/* 871 */     if (b) {
/* 872 */       sendEvent(49);
/*     */     }
/* 874 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCloseApp(int keyCode, KeyEvent event) {
/* 884 */     boolean b = (sEnabled && event.isCtrlPressed() && !event.isAltPressed() && event.isShiftPressed() && keyCode == 51);
/*     */     
/* 886 */     if (b) {
/* 887 */       sendEvent(50);
/*     */     }
/* 889 */     return b;
/*     */   }
/*     */   
/*     */   private static void sendEvent(int shortcutId) {
/* 893 */     AnalyticsHandlerAdapter.getInstance().sendEvent(36, AnalyticsParam.shortcutParam(shortcutId));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ShortcutHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */