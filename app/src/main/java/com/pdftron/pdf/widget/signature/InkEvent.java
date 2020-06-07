/*    */ package com.pdftron.pdf.widget.signature;
/*    */ 
/*    */ class InkEvent {
/*    */   public final InkEventType eventType;
/*    */   public final float x;
/*    */   public final float y;
/*    */   final float pressure;
/*    */   
/*    */   InkEvent(InkEventType eventType, float x, float y, float pressure) {
/* 10 */     this.eventType = eventType;
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.pressure = pressure;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\signature\InkEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */