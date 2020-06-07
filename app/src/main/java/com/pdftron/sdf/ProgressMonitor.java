package com.pdftron.sdf;

interface ProgressMonitor {
  void setRange(int paramInt1, int paramInt2);
  
  int getRangeStart();
  
  int getRangeFinish();
  
  int getPos();
  
  int setPos(int paramInt);
  
  int offsetPos(int paramInt);
  
  int setStep(int paramInt);
  
  int stepIt();
}


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\ProgressMonitor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */