/*       */ package com.pdftron.pdf;
/*       */ 
/*       */ import android.annotation.SuppressLint;
/*       */ import android.annotation.TargetApi;
/*       */ import android.app.ActivityManager;
/*       */ import android.app.AlertDialog;
/*       */ import android.content.Context;
/*       */ import android.content.DialogInterface;
/*       */ import android.content.res.Configuration;
/*       */ import android.content.res.Resources;
/*       */ import android.graphics.Bitmap;
/*       */ import android.graphics.Canvas;
/*       */ import android.graphics.Color;
/*       */ import android.graphics.Matrix;
/*       */ import android.graphics.Paint;
/*       */ import android.graphics.PointF;
/*       */ import android.graphics.PorterDuff;
/*       */ import android.graphics.PorterDuffXfermode;
/*       */ import android.graphics.Rect;
/*       */ import android.graphics.RectF;
/*       */ import android.graphics.Region;
/*       */ import android.graphics.Xfermode;
/*       */ import android.graphics.drawable.Drawable;
/*       */ import android.net.Uri;
/*       */ import android.os.Build;
/*       */ import android.os.Debug;
/*       */ import android.os.Handler;
/*       */ import android.os.Message;
/*       */ import android.os.Process;
/*       */ import android.os.SystemClock;
/*       */ import android.text.method.PasswordTransformationMethod;
/*       */ import android.text.method.TransformationMethod;
/*       */ import android.util.AttributeSet;
/*       */ import android.util.DisplayMetrics;
/*       */ import android.util.Log;
/*       */ import android.util.SparseArray;
/*       */ import android.util.SparseIntArray;
/*       */ import android.view.GestureDetector;
/*       */ import android.view.InputDevice;
/*       */ import android.view.KeyEvent;
/*       */ import android.view.MotionEvent;
/*       */ import android.view.PointerIcon;
/*       */ import android.view.ScaleGestureDetector;
/*       */ import android.view.View;
/*       */ import android.view.ViewGroup;
/*       */ import android.webkit.URLUtil;
/*       */ import android.widget.Button;
/*       */ import android.widget.EditText;
/*       */ import android.widget.OverScroller;
/*       */ import android.widget.TextView;
/*       */ import android.widget.Toast;
/*       */ import com.pdftron.common.Matrix2D;
/*       */ import com.pdftron.common.PDFNetException;
/*       */ import com.pdftron.filters.Filter;
/*       */ import com.pdftron.filters.SecondaryFileFilter;
/*       */ import com.pdftron.pdf.ocg.Context;
/*       */ import com.pdftron.sdf.DictIterator;
/*       */ import com.pdftron.sdf.Obj;
/*       */ import com.pdftron.sdf.ObjSet;
/*       */ import com.pdftron.sdf.SDFDoc;
/*       */ import com.pdftron.sdf.SecurityHandler;
/*       */ import java.io.BufferedReader;
/*       */ import java.io.ByteArrayOutputStream;
/*       */ import java.io.DataInputStream;
/*       */ import java.io.File;
/*       */ import java.io.FileNotFoundException;
/*       */ import java.io.IOException;
/*       */ import java.io.InputStream;
/*       */ import java.io.InputStreamReader;
/*       */ import java.lang.ref.WeakReference;
/*       */ import java.net.HttpURLConnection;
/*       */ import java.net.URL;
/*       */ import java.text.Bidi;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Collection;
/*       */ import java.util.HashSet;
/*       */ import java.util.Iterator;
/*       */ import java.util.LinkedList;
/*       */ import java.util.List;
/*       */ import java.util.Map;
/*       */ import java.util.Queue;
/*       */ import java.util.Set;
/*       */ import java.util.Vector;
/*       */ import java.util.concurrent.CopyOnWriteArrayList;
/*       */ import java.util.concurrent.locks.Lock;
/*       */ import java.util.concurrent.locks.ReentrantLock;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class PDFViewCtrl
/*       */   extends ViewGroup
/*       */ {
/*   139 */   private static final String c = PDFViewCtrl.class.getName();
/*       */   
/*       */   static boolean a = false;
/*       */   static boolean b = false;
/*   143 */   private static boolean d = (a || b); protected PDFDoc mDoc; protected SecondaryFileFilter mDocumentConversionFilter; private ExternalAnnotManager e; private OverScroller f; private OverScroller g; private OverScroller h; private g i;
/*       */   
/*       */   static void a(int paramInt1, String paramString, int paramInt2) {
/*       */     try {
/*   147 */       PDFNetInternalTools.logMessage(paramInt1, "WRAPPER - " + Process.myTid() + " - " + paramInt2 + ": " + paramString, "PDFViewCtrl.java", paramInt2); return;
/*   148 */     } catch (Exception exception) {
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   171 */   private final Lock j = new ReentrantLock();
/*   172 */   private final Lock k = new ReentrantLock();
/*       */   private f l;
/*       */   private d m;
/*       */   private Bitmap n;
/*       */   private boolean o;
/*       */   private int p;
/*       */   private int q;
/*       */   private boolean r;
/*       */   private boolean s;
/*       */   private boolean t;
/*       */   private boolean u;
/*       */   private int v;
/*       */   private boolean w;
/*       */   private int x;
/*       */   private int y;
/*       */   private int z;
/*       */   private boolean A;
/*       */   private boolean B;
/*   190 */   private PointF C = new PointF(0.0F, 0.0F);
/*   191 */   private final Lock D = new ReentrantLock();
/*       */   
/*   193 */   private int E = 0;
/*   194 */   private int F = 0;
/*       */   
/*   196 */   private int G = 3;
/*   197 */   private int H = 3;
/*   198 */   private int I = 0;
/*       */ 
/*       */   
/*   201 */   private SparseArray<Rect> J = new SparseArray();
/*       */   
/*       */   private List<Integer> K;
/*   204 */   private static int L = 2;
/*       */   
/*   206 */   private Set<Long> M = new HashSet<>(); private boolean N; private int O; private int P; private boolean Q; private boolean R; private boolean S; private boolean T; private boolean U; private boolean V; private boolean W; private boolean aa; private boolean ab; private boolean ac; private boolean ad; private MotionEvent ae; private boolean af; private Matrix ag; private Matrix ah;
/*       */   
/*       */   static int a(boolean paramBoolean) {
/*   209 */     if (d) {
/*   210 */       int n; paramBoolean = !paramBoolean;
/*   211 */       StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/*   212 */       if (L + paramBoolean >= arrayOfStackTraceElement.length) {
/*   213 */         n = arrayOfStackTraceElement.length - 1 - L;
/*       */       }
/*   215 */       return arrayOfStackTraceElement[L + n].getLineNumber();
/*       */     } 
/*   217 */     return 0;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect ai;
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect aj;
/*       */ 
/*       */ 
/*       */   
/*       */   private RectF ak;
/*       */ 
/*       */   
/*       */   private RectF al;
/*       */ 
/*       */   
/*       */   private Paint am;
/*       */ 
/*       */   
/*       */   private Paint an;
/*       */ 
/*       */   
/*       */   private Paint ao;
/*       */ 
/*       */   
/*       */   private Paint ap;
/*       */ 
/*       */   
/*       */   private Paint aq;
/*       */ 
/*       */   
/*       */   private Paint ar;
/*       */ 
/*       */   
/*       */   private Paint as;
/*       */ 
/*       */   
/*       */   private Paint at;
/*       */ 
/*       */   
/*       */   private Paint au;
/*       */ 
/*       */   
/*       */   private Paint av;
/*       */ 
/*       */   
/*       */   private Paint aw;
/*       */ 
/*       */   
/*       */   private Paint ax;
/*       */ 
/*       */   
/*       */   private Paint ay;
/*       */ 
/*       */   
/*       */   private Paint az;
/*       */ 
/*       */   
/*       */   private f.a[] aA;
/*       */ 
/*       */   
/*   282 */   private int aB = 0;
/*   283 */   private int aC = 0;
/*       */ 
/*       */   
/*   286 */   private int aD = 0;
/*   287 */   private int aE = 0;
/*       */   
/*   289 */   private int aF = 0;
/*   290 */   private int aG = 0;
/*   291 */   private int aH = 0;
/*   292 */   private int aI = 0;
/*       */   
/*   294 */   private int aJ = 0;
/*   295 */   private int aK = 0;
/*   296 */   private int aL = 0;
/*   297 */   private int aM = 0;
/*       */ 
/*       */   
/*   300 */   private RectF aN = new RectF();
/*   301 */   private RectF aO = new RectF();
/*   302 */   private SparseArray<RectF> aP = new SparseArray();
/*   303 */   private SparseArray<RectF> aQ = new SparseArray();
/*       */   
/*       */   private int aR;
/*       */   
/*       */   private float aS;
/*       */   private float aT;
/*       */   private float aU;
/*       */   private float aV;
/*       */   private float aW;
/*       */   private float aX;
/*       */   private float aY;
/*       */   private int aZ;
/*       */   private int ba;
/*       */   private float bb;
/*       */   private float bc;
/*       */   private float bd;
/*       */   private float be;
/*       */   private float bf;
/*       */   private boolean bg;
/*       */   private double bh;
/*       */   private double bi;
/*       */   private ZoomLimitMode bj;
/*       */   private double bk;
/*       */   private double bl;
/*   327 */   private double bm = 1.0D;
/*       */   
/*       */   private double bn;
/*       */   
/*       */   private double bo;
/*       */   
/*       */   private int bp;
/*       */   
/*       */   private float bq;
/*       */   
/*       */   private float br;
/*       */   
/*       */   private boolean bs;
/*       */   
/*       */   private PointF bt;
/*       */   
/*       */   private float bu;
/*       */   
/*       */   private boolean bv;
/*       */   
/*       */   private boolean bw;
/*       */   
/*       */   private boolean bx;
/*       */   
/*       */   private int by;
/*       */   
/*       */   private boolean bz;
/*       */   
/*       */   private boolean bA;
/*       */   
/*       */   private TextSearchListener bB;
/*       */   
/*       */   private CopyOnWriteArrayList<DocumentDownloadListener> bC;
/*       */   private CopyOnWriteArrayList<UniversalDocumentConversionListener> bD;
/*       */   private boolean bE;
/*       */   private CopyOnWriteArrayList<PageChangeListener> bF;
/*       */   private int bG;
/*       */   private int bH;
/*       */   private PageChangeState bI;
/*       */   private CopyOnWriteArrayList<DocumentLoadListener> bJ;
/*       */   private boolean bK;
/*       */   private ErrorReportListener bL;
/*       */   private ArrayList<ThumbAsyncListener> bM;
/*       */   private ActionCompletedListener bN;
/*       */   private RenderingListener bO;
/*       */   private UniversalDocumentProgressIndicatorListener bP;
/*       */   private boolean bQ;
/*       */   private c bR;
/*       */   private ToolManager bS;
/*       */   private PageViewMode bT;
/*       */   private PageViewMode bU;
/*       */   private PagePresentationMode bV;
/*       */   private boolean bW;
/*       */   private boolean bX;
/*       */   private boolean bY;
/*       */   private boolean bZ;
/*       */   private boolean ca;
/*       */   private boolean cb;
/*       */   private int cc;
/*       */   private int cd;
/*       */   private j ce;
/*       */   private Object cf;
/*       */   private int cg;
/*       */   private int ch;
/*   391 */   private final Lock ci = new ReentrantLock();
/*       */ 
/*       */   
/*       */   private boolean cj;
/*       */   
/*       */   private boolean ck = true;
/*       */   
/*       */   private boolean cl = false;
/*       */   
/*   400 */   private int cm = 0;
/*   401 */   private int cn = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean co = false;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean cp = false;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   enum o
/*       */   {
/*   478 */     a(0), b(0), c(0), d(0); public static int[] a() { return (int[])e.clone(); } } private int cq = o.d; private int cr; private float cs; private float ct; private float cu; enum n { a(0), b(0), c(0), d(0); } private boolean cv = false; private SparseIntArray cw = new SparseIntArray(); private SparseIntArray cx = new SparseIntArray(); private PageViewMode cy; private boolean cz = false; private boolean cA = false; private boolean cB = true; private ArrayList<OnCanvasSizeChangeListener> cC; private u cD; private int cE = 0; private SparseArray<double[]> cF; public static final double SCROLL_ZOOM_FACTOR = 1.5D; public enum PriorEventMode { SCROLLING(1), PINCH(2), FLING(3), DOUBLE_TAP(4), PAGE_SLIDING(5), OTHER(0);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     PriorEventMode(int param1Int1) {
/*   483 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   487 */       return this.a;
/*       */     } }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum PageViewMode
/*       */   {
/*   498 */     FIT_PAGE(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   503 */     FIT_WIDTH(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   508 */     FIT_HEIGHT(2),
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   514 */     ZOOM(3),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   519 */     NOT_DEFINED(-1);
/*       */     
/*       */     private final int a;
/*   522 */     private static SparseArray<PageViewMode> b = new SparseArray(5);
/*       */     
/*       */     static
/*       */     {
/*       */       PageViewMode[] arrayOfPageViewMode;
/*       */       int i;
/*       */       byte b;
/*   529 */       for (i = (arrayOfPageViewMode = values()).length, b = 0; b < i; ) { PageViewMode pageViewMode = arrayOfPageViewMode[b];
/*   530 */         b.put(pageViewMode.a, pageViewMode);
/*       */         b++; }
/*       */        } PageViewMode(int param1Int1) {
/*       */       this.a = param1Int1;
/*       */     } public final int getValue() {
/*   535 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum PagePresentationMode
/*       */   {
/*   550 */     SINGLE(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   555 */     SINGLE_CONT(2),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   560 */     FACING(3),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   565 */     FACING_CONT(4),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   570 */     FACING_COVER(5),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   575 */     FACING_COVER_CONT(6),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   580 */     SINGLE_VERT(7),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   585 */     FACING_VERT(8),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   590 */     FACING_COVER_VERT(9);
/*       */     
/*       */     private final int a;
/*   593 */     private static SparseArray<PagePresentationMode> b = new SparseArray(7);
/*       */     
/*       */     static
/*       */     {
/*       */       PagePresentationMode[] arrayOfPagePresentationMode;
/*       */       int i;
/*       */       byte b;
/*   600 */       for (i = (arrayOfPagePresentationMode = values()).length, b = 0; b < i; ) { PagePresentationMode pagePresentationMode = arrayOfPagePresentationMode[b];
/*   601 */         b.put(pagePresentationMode.a, pagePresentationMode);
/*       */         b++; }
/*       */        } PagePresentationMode(int param1Int1) {
/*       */       this.a = param1Int1;
/*       */     } public final int getValue() {
/*   606 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum TextSearchResult
/*       */   {
/*   621 */     NOT_FOUND(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   626 */     FOUND(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   631 */     CANCELED(-1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   636 */     INVALID_INPUT(2);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     TextSearchResult(int param1Int1) {
/*   641 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   645 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum TextSelectionMode
/*       */   {
/*   657 */     STRUCTURAL(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   662 */     RECTANGULAR(1);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     TextSelectionMode(int param1Int1) {
/*   667 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   671 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum OverPrintMode
/*       */   {
/*   684 */     OFF(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   689 */     ON(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   694 */     PDFX(2);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     OverPrintMode(int param1Int1) {
/*   699 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   703 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum ZoomLimitMode
/*       */   {
/*   717 */     ABSOLUTE(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   722 */     RELATIVE(2),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   727 */     NONE(3);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     ZoomLimitMode(int param1Int1) {
/*   732 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   736 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum PageChangeState
/*       */   {
/*   747 */     BEGIN(-1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   752 */     ONGOING(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   757 */     END(1),
/*       */     
/*   759 */     SILENT(0);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     PageChangeState(int param1Int1) {
/*   764 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   768 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum DownloadState
/*       */   {
/*   779 */     PAGE(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   784 */     THUMB(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   789 */     NAMED_DESTS(2),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   794 */     OUTLINE(3),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   799 */     FINISHED(4),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   804 */     FAILED(5),
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   810 */     OPENED(6);
/*       */     
/*       */     private final int a;
/*   813 */     private static SparseArray<DownloadState> b = new SparseArray(7);
/*       */     
/*       */     static
/*       */     {
/*       */       DownloadState[] arrayOfDownloadState;
/*       */       int i;
/*       */       byte b;
/*   820 */       for (i = (arrayOfDownloadState = values()).length, b = 0; b < i; ) { DownloadState downloadState = arrayOfDownloadState[b];
/*   821 */         b.put(downloadState.a, downloadState);
/*       */         b++; }
/*       */        } DownloadState(int param1Int1) {
/*       */       this.a = param1Int1;
/*       */     } public final int getValue() {
/*   826 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public enum ConversionState
/*       */   {
/*   841 */     FINISHED(0),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   846 */     PROGRESS(1),
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   851 */     FAILED(2);
/*       */     
/*       */     private final int a;
/*       */     
/*       */     ConversionState(int param1Int1) {
/*   856 */       this.a = param1Int1;
/*       */     }
/*       */     
/*       */     public final int getValue() {
/*   860 */       return this.a;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void refreshAndUpdate(ViewChangeCollection paramViewChangeCollection) throws PDFNetException {
/*   872 */     RefreshAndUpdate(this.cY, paramViewChangeCollection.a);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   893 */   public static int DEFAULT_BG_COLOR = -1709592;
/*   894 */   public static int DEFAULT_DARK_BG_COLOR = -14606047;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cG;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cH;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cI;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cJ;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cK;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cL;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rect cM;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private RectF cN;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private RectF cO;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private RectF cP;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private float cQ;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private float cR;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private float cS;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private float cT;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private UniversalDocumentConversionListener cU;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean cV;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int[] cW;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private long cX;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private long cY;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private long cZ;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private GestureDetector da;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private ScaleGestureDetector db;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final i dc;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final m dd;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final k de;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final d df;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final b dg;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final l dh;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final h di;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final q dj;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final p dk;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final t dl;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final g dm;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final e dn;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final f do;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final s dp;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final a dq;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private final r dr;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Thread ds;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static class HTTPRequestOptions
/*       */   {
/*       */     Obj a;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     private ObjSet b;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public HTTPRequestOptions() {
/*       */       try {
/*  1453 */         this.b = new ObjSet();
/*  1454 */         this.a = this.b.createDict(); return;
/*  1455 */       } catch (PDFNetException pDFNetException) {
/*  1456 */         System.err.println("Error Occurred when creating HTTPRequestOptions.");
/*       */         return;
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void addHeader(String param1String1, String param1String2) throws PDFNetException {
/*  1469 */       this.a.putText(param1String1, param1String2);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void restrictDownloadUsage(boolean param1Boolean) throws PDFNetException {
/*  1479 */       this.a.putBool("MINIMAL_DOWNLOAD", param1Boolean);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void skipByteRangeTest(boolean param1Boolean) throws PDFNetException {
/*  1497 */       this.a.putBool("SKIP_BYTE_RANGE_TEST", param1Boolean);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void useRemoteFileSize(long param1Long) throws PDFNetException {
/*  1509 */       this.a.putNumber("FORCE_REMOTE_SIZE", param1Long);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public class Selection
/*       */   {
/*       */     private long a;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public int getPageNum() {
/*  1525 */       return PDFViewCtrl.a(this.a);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public double[] getQuads() {
/*  1542 */       return PDFViewCtrl.b(this.a);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public String getAsUnicode() {
/*  1550 */       return PDFViewCtrl.c(this.a);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public String getAsHtml() {
/*  1558 */       return PDFViewCtrl.d(this.a);
/*       */     }
/*       */ 
/*       */     
/*       */     private Selection(PDFViewCtrl this$0, long param1Long, Object param1Object) {
/*  1563 */       this.a = param1Long;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static class LinkInfo
/*       */   {
/*  1579 */     private double mX1 = 0.0D;
/*  1580 */     private double mX2 = 0.0D;
/*  1581 */     private double mY1 = 0.0D;
/*  1582 */     private double mY2 = 0.0D;
/*  1583 */     private String mUrl = "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public String getURL() {
/*  1593 */       return this.mUrl;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Rect getRect() {
/*       */       try {
/*  1601 */         return new Rect(this.mX1, this.mY1, this.mX2, this.mY2);
/*  1602 */       } catch (PDFNetException pDFNetException) {
/*  1603 */         return null;
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public PDFViewCtrl(Context paramContext, AttributeSet paramAttributeSet) {
/*  1877 */     this(paramContext, paramAttributeSet, 0);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public PDFViewCtrl(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
/*  1899 */     super(paramContext, paramAttributeSet, paramInt);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2293 */     this.cG = new Rect();
/*  2294 */     this.cH = new Rect();
/*  2295 */     this.cI = new Rect();
/*  2296 */     this.cJ = new Rect();
/*  2297 */     this.cK = new Rect();
/*  2298 */     this.cL = new Rect();
/*  2299 */     this.cM = new Rect();
/*  2300 */     this.cN = new RectF();
/*  2301 */     this.cO = new RectF();
/*  2302 */     this.cP = new RectF();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3901 */     this.cQ = 1.7014117E38F;
/*  3902 */     this.cR = -1.7014117E38F;
/*  3903 */     this.cS = 1.7014117E38F;
/*  3904 */     this.cT = -1.7014117E38F;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5433 */     this.cU = new UniversalDocumentConversionListener(this)
/*       */       {
/*       */         public final void onConversionEvent(ConversionState param1ConversionState, int param1Int) {
/*  5436 */           switch (PDFViewCtrl.null.b[param1ConversionState.ordinal()]) {
/*       */             case 1:
/*       */               return;
/*       */             case 2:
/*  5440 */               if (this.a.mDocumentConversionFilter != null) {
/*  5441 */                 this.a.mDocumentConversionFilter.close();
/*  5442 */                 this.a.mDocumentConversionFilter = null;
/*       */               } 
/*  5444 */               this.a.removeUniversalDocumentConversionListener(PDFViewCtrl.a(this.a));
/*       */               return;
/*       */             case 3:
/*  5447 */               if (this.a.mDocumentConversionFilter != null) {
/*  5448 */                 this.a.mDocumentConversionFilter.close();
/*  5449 */                 this.a.mDocumentConversionFilter = null;
/*       */               } 
/*  5451 */               this.a.removeUniversalDocumentConversionListener(PDFViewCtrl.a(this.a));
/*       */               break;
/*       */           } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*       */       };
/* 10146 */     this.cV = false;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 11895 */     this.da = new GestureDetector(getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(this)
/*       */         {
/*       */           
/*       */           public final boolean onScroll(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2)
/*       */           {
/* 11900 */             if (param1MotionEvent2.getButtonState() == 2) {
/* 11901 */               return false;
/*       */             }
/*       */             InputDevice inputDevice;
/* 11904 */             if ((inputDevice = (InputDevice)((param1MotionEvent1 != null) ? InputDevice.getDevice(param1MotionEvent1.getDeviceId()) : null)) == null || (inputDevice.getSources() & 0x2002) == 0) {
/* 11905 */               PDFViewCtrl.b(this.a, true);
/*       */             }
/* 11907 */             return this.a.onScroll(param1MotionEvent1, param1MotionEvent2, param1Float1, param1Float2);
/*       */           }
/*       */ 
/*       */           
/*       */           public final boolean onFling(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
/* 11912 */             if (param1MotionEvent2.getButtonState() == 2) {
/* 11913 */               return false;
/*       */             }
/* 11915 */             PDFViewCtrl.b(this.a, false);
/* 11916 */             PDFViewCtrl.c(this.a, true);
/* 11917 */             return this.a.onFling(param1MotionEvent1, param1MotionEvent2, param1Float1, param1Float2);
/*       */           }
/*       */ 
/*       */           
/*       */           public final boolean onDoubleTap(MotionEvent param1MotionEvent) {
/* 11922 */             if (param1MotionEvent.getButtonState() == 2) {
/* 11923 */               return false;
/*       */             }
/* 11925 */             if (PDFViewCtrl.j.a(PDFViewCtrl.d(this.a)))
/*       */             {
/* 11927 */               return true;
/*       */             }
/*       */             
/* 11930 */             return this.a.onDoubleTap(param1MotionEvent);
/*       */           }
/*       */ 
/*       */           
/*       */           public final boolean onDoubleTapEvent(MotionEvent param1MotionEvent) {
/* 11935 */             if (param1MotionEvent.getButtonState() == 2) {
/* 11936 */               return false;
/*       */             }
/* 11938 */             if (PDFViewCtrl.j.a(PDFViewCtrl.d(this.a)))
/*       */             {
/* 11940 */               return true;
/*       */             }
/*       */             
/* 11943 */             return this.a.onDoubleTapEvent(param1MotionEvent);
/*       */           }
/*       */ 
/*       */ 
/*       */           
/*       */           public final boolean onDown(MotionEvent param1MotionEvent) {
/* 11949 */             if (param1MotionEvent.getButtonState() == 2) {
/* 11950 */               return false;
/*       */             }
/* 11952 */             return this.a.onDown(param1MotionEvent);
/*       */           }
/*       */ 
/*       */           
/*       */           public final void onLongPress(MotionEvent param1MotionEvent) {
/* 11957 */             if (param1MotionEvent.getButtonState() == 2) {
/*       */               return;
/*       */             }
/*       */             
/* 11961 */             this.a.onLongPress(param1MotionEvent);
/*       */           }
/*       */ 
/*       */           
/*       */           public final void onShowPress(MotionEvent param1MotionEvent) {
/* 11966 */             if (param1MotionEvent.getButtonState() == 2) {
/*       */               return;
/*       */             }
/* 11969 */             this.a.onShowPress(param1MotionEvent);
/*       */           }
/*       */ 
/*       */           
/*       */           public final boolean onSingleTapUp(MotionEvent param1MotionEvent) {
/* 11974 */             if (param1MotionEvent.getButtonState() == 2) {
/* 11975 */               return true;
/*       */             }
/*       */             
/* 11978 */             if (PDFViewCtrl.j.a(PDFViewCtrl.d(this.a)))
/*       */             {
/* 11980 */               return true;
/*       */             }
/* 11982 */             return this.a.onSingleTapUp(param1MotionEvent);
/*       */           }
/*       */ 
/*       */           
/*       */           public final boolean onSingleTapConfirmed(MotionEvent param1MotionEvent) {
/* 11987 */             if (param1MotionEvent.getButtonState() == 2) {
/* 11988 */               return false;
/*       */             }
/* 11990 */             if (PDFViewCtrl.j.a(PDFViewCtrl.d(this.a)))
/*       */             {
/* 11992 */               return true;
/*       */             }
/*       */ 
/*       */ 
/*       */ 
/*       */             
/* 11998 */             if (!PDFViewCtrl.K(this.a)) {
/* 11999 */               return this.a.onSingleTapConfirmed(param1MotionEvent);
/*       */             }
/* 12001 */             PDFViewCtrl.d(this.a, false);
/* 12002 */             return false;
/*       */           }
/*       */         }null, true);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12013 */     this.db = new ScaleGestureDetector(getContext(), (ScaleGestureDetector.OnScaleGestureListener)new ScaleGestureDetector.SimpleOnScaleGestureListener(this)
/*       */         {
/*       */           public final boolean onScale(ScaleGestureDetector param1ScaleGestureDetector)
/*       */           {
/* 12017 */             PDFViewCtrl.e(this.a, false);
/*       */             
/* 12019 */             return this.a.onScale(param1ScaleGestureDetector);
/*       */           }
/*       */ 
/*       */           
/*       */           public final boolean onScaleBegin(ScaleGestureDetector param1ScaleGestureDetector) {
/* 12024 */             PDFViewCtrl.f(this.a, true);
/* 12025 */             PDFViewCtrl.g(this.a, true);
/* 12026 */             PDFViewCtrl.h(this.a, false);
/* 12027 */             PDFViewCtrl.i(this.a, true);
/*       */             
/* 12029 */             return this.a.onScaleBegin(param1ScaleGestureDetector);
/*       */           }
/*       */ 
/*       */           
/*       */           public final void onScaleEnd(ScaleGestureDetector param1ScaleGestureDetector) {
/* 12034 */             PDFViewCtrl.e(this.a, true);
/* 12035 */             PDFViewCtrl.i(this.a, false);
/*       */             
/* 12037 */             this.a.onScaleEnd(param1ScaleGestureDetector);
/*       */           }
/*       */         });
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12063 */     this.dc = new i(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12090 */     this.dd = new m(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12128 */     this.de = new k(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12156 */     this.df = new d(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12209 */     this.dg = new b(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12237 */     this.dh = new l(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12262 */     this.di = new h(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12285 */     this.dj = new q(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12309 */     this.dk = new p(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12333 */     this.dl = new t(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12409 */     this.dm = new g(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12530 */     this.dn = new e(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12557 */     this.do = new f(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12590 */     this.dp = new s(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12613 */     this.dq = new a(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12639 */     this.dr = new r(this);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     Context context = paramContext;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     PDFViewCtrl pDFViewCtrl;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 15610 */     (pDFViewCtrl = this).bQ = false;
/*       */     
/* 15612 */     pDFViewCtrl.s = true;
/* 15613 */     pDFViewCtrl.o = false;
/* 15614 */     pDFViewCtrl.r = false;
/* 15615 */     pDFViewCtrl.t = false;
/* 15616 */     pDFViewCtrl.u = false;
/*       */     
/* 15618 */     pDFViewCtrl.cF = new SparseArray();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 15627 */     pDFViewCtrl.N = true;
/* 15628 */     pDFViewCtrl.O = 2000;
/* 15629 */     pDFViewCtrl.P = 750;
/* 15630 */     pDFViewCtrl.Q = false;
/* 15631 */     pDFViewCtrl.R = false;
/* 15632 */     pDFViewCtrl.S = false;
/* 15633 */     pDFViewCtrl.T = false;
/* 15634 */     pDFViewCtrl.bg = false;
/* 15635 */     pDFViewCtrl.ab = false;
/* 15636 */     pDFViewCtrl.ad = false;
/* 15637 */     pDFViewCtrl.A = false;
/* 15638 */     pDFViewCtrl.v = 0;
/*       */     
/* 15640 */     pDFViewCtrl.bB = null;
/* 15641 */     pDFViewCtrl.by = 0;
/* 15642 */     pDFViewCtrl.bz = false;
/* 15643 */     pDFViewCtrl.bA = false;
/*       */     
/* 15645 */     pDFViewCtrl.bF = null;
/* 15646 */     pDFViewCtrl.bG = 1;
/* 15647 */     pDFViewCtrl.bH = 1;
/* 15648 */     pDFViewCtrl.bI = PageChangeState.END;
/*       */     
/* 15650 */     pDFViewCtrl.bC = null;
/* 15651 */     pDFViewCtrl.bO = null;
/* 15652 */     pDFViewCtrl.bP = null;
/*       */     
/* 15654 */     pDFViewCtrl.bK = false;
/*       */     
/* 15656 */     pDFViewCtrl.bL = null;
/*       */     
/* 15658 */     pDFViewCtrl.bh = 0.0D;
/* 15659 */     pDFViewCtrl.bi = 500000.0D;
/* 15660 */     pDFViewCtrl.bj = ZoomLimitMode.NONE;
/* 15661 */     pDFViewCtrl.bk = 1.0D;
/* 15662 */     pDFViewCtrl.bl = pDFViewCtrl.bk;
/* 15663 */     pDFViewCtrl.bn = 1.0D;
/* 15664 */     pDFViewCtrl.bo = pDFViewCtrl.bn;
/*       */     
/* 15666 */     pDFViewCtrl.bT = PageViewMode.FIT_PAGE;
/* 15667 */     pDFViewCtrl.bU = PageViewMode.NOT_DEFINED;
/* 15668 */     pDFViewCtrl.bV = PagePresentationMode.SINGLE;
/*       */     
/* 15670 */     pDFViewCtrl.aA = new f.a[2];
/*       */     
/* 15672 */     pDFViewCtrl.K = new ArrayList<>(10);
/*       */     
/* 15674 */     pDFViewCtrl.x = Color.argb(255, 255, 255, 255);
/* 15675 */     pDFViewCtrl.y = Color.argb(255, 255, 255, 255);
/* 15676 */     pDFViewCtrl.am = new Paint();
/* 15677 */     pDFViewCtrl.am.setColor(pDFViewCtrl.x);
/* 15678 */     pDFViewCtrl.am.setStyle(Paint.Style.FILL);
/* 15679 */     pDFViewCtrl.am.setAntiAlias(true);
/* 15680 */     pDFViewCtrl.am.setFilterBitmap(false);
/*       */     
/* 15682 */     pDFViewCtrl.an = new Paint(pDFViewCtrl.am);
/* 15683 */     pDFViewCtrl.an.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
/*       */     
/* 15685 */     pDFViewCtrl.bW = true;
/* 15686 */     pDFViewCtrl.bY = true;
/*       */     
/* 15688 */     pDFViewCtrl.z = DEFAULT_BG_COLOR;
/* 15689 */     pDFViewCtrl.setBackgroundColor(pDFViewCtrl.z);
/* 15690 */     pDFViewCtrl.ao = new Paint();
/* 15691 */     pDFViewCtrl.ao.setColor(pDFViewCtrl.z);
/* 15692 */     pDFViewCtrl.ao.setStyle(Paint.Style.FILL);
/* 15693 */     pDFViewCtrl.ao.setAntiAlias(true);
/* 15694 */     pDFViewCtrl.ao.setFilterBitmap(false);
/*       */     
/* 15696 */     if (a) {
/* 15697 */       pDFViewCtrl.ap = new Paint();
/* 15698 */       pDFViewCtrl.ap.setColor(Color.argb(50, 240, 177, 114));
/* 15699 */       pDFViewCtrl.ap.setStyle(Paint.Style.FILL);
/* 15700 */       pDFViewCtrl.ap.setAntiAlias(true);
/* 15701 */       pDFViewCtrl.ap.setFilterBitmap(false);
/*       */       
/* 15703 */       pDFViewCtrl.aq = new Paint();
/* 15704 */       pDFViewCtrl.aq.setColor(Color.argb(50, 255, 0, 0));
/* 15705 */       pDFViewCtrl.aq.setStyle(Paint.Style.STROKE);
/* 15706 */       pDFViewCtrl.aq.setAntiAlias(true);
/* 15707 */       pDFViewCtrl.aq.setFilterBitmap(false);
/*       */       
/* 15709 */       pDFViewCtrl.ar = new Paint(pDFViewCtrl.ap);
/* 15710 */       pDFViewCtrl.ar.setColor(Color.argb(50, 10, 186, 181));
/*       */       
/* 15712 */       pDFViewCtrl.as = new Paint(pDFViewCtrl.ar);
/* 15713 */       pDFViewCtrl.as.setColor(Color.argb(50, 255, 198, 123));
/*       */       
/* 15715 */       pDFViewCtrl.at = new Paint();
/* 15716 */       pDFViewCtrl.at.setColor(Color.argb(50, 255, 0, 0));
/* 15717 */       pDFViewCtrl.at.setStyle(Paint.Style.FILL);
/* 15718 */       pDFViewCtrl.at.setAntiAlias(true);
/* 15719 */       pDFViewCtrl.at.setFilterBitmap(false);
/*       */       
/* 15721 */       pDFViewCtrl.au = new Paint();
/* 15722 */       pDFViewCtrl.au.setColor(Color.argb(50, 0, 0, 255));
/* 15723 */       pDFViewCtrl.au.setStyle(Paint.Style.FILL);
/* 15724 */       pDFViewCtrl.au.setAntiAlias(true);
/* 15725 */       pDFViewCtrl.au.setFilterBitmap(false);
/*       */       
/* 15727 */       pDFViewCtrl.av = new Paint();
/* 15728 */       pDFViewCtrl.av.setColor(-16777216);
/* 15729 */       pDFViewCtrl.av.setStrokeJoin(Paint.Join.ROUND);
/* 15730 */       pDFViewCtrl.av.setStrokeCap(Paint.Cap.ROUND);
/* 15731 */       pDFViewCtrl.av.setStyle(Paint.Style.STROKE);
/* 15732 */       pDFViewCtrl.av.setTextAlign(Paint.Align.LEFT);
/* 15733 */       pDFViewCtrl.av.setTextSize(30.0F);
/*       */       
/* 15735 */       pDFViewCtrl.aw = new Paint();
/* 15736 */       pDFViewCtrl.aw.setColor(Color.argb(100, 0, 255, 0));
/* 15737 */       pDFViewCtrl.aw.setStyle(Paint.Style.FILL);
/* 15738 */       pDFViewCtrl.aw.setAntiAlias(true);
/* 15739 */       pDFViewCtrl.aw.setFilterBitmap(false);
/*       */       
/* 15741 */       pDFViewCtrl.ax = new Paint();
/* 15742 */       pDFViewCtrl.ax.setColor(Color.argb(100, 0, 255, 100));
/* 15743 */       pDFViewCtrl.ax.setStyle(Paint.Style.FILL);
/* 15744 */       pDFViewCtrl.ax.setAntiAlias(true);
/* 15745 */       pDFViewCtrl.ax.setFilterBitmap(false);
/*       */       
/* 15747 */       pDFViewCtrl.ay = new Paint();
/* 15748 */       pDFViewCtrl.ay.setColor(Color.argb(100, 100, 255, 0));
/* 15749 */       pDFViewCtrl.ay.setStyle(Paint.Style.FILL);
/* 15750 */       pDFViewCtrl.ay.setAntiAlias(true);
/* 15751 */       pDFViewCtrl.ay.setFilterBitmap(false);
/*       */       
/* 15753 */       pDFViewCtrl.az = new Paint();
/* 15754 */       pDFViewCtrl.az.setColor(Color.argb(100, 100, 255, 100));
/* 15755 */       pDFViewCtrl.az.setStyle(Paint.Style.FILL);
/* 15756 */       pDFViewCtrl.az.setAntiAlias(true);
/* 15757 */       pDFViewCtrl.az.setFilterBitmap(false);
/*       */     } 
/*       */     
/* 15760 */     pDFViewCtrl.mDoc = null;
/* 15761 */     pDFViewCtrl.l = new f();
/* 15762 */     pDFViewCtrl.m = new d();
/* 15763 */     pDFViewCtrl.f = new OverScroller(context);
/* 15764 */     pDFViewCtrl.g = new OverScroller(context);
/* 15765 */     pDFViewCtrl.h = new OverScroller(context);
/* 15766 */     pDFViewCtrl.ah = new Matrix();
/* 15767 */     pDFViewCtrl.ag = new Matrix();
/* 15768 */     pDFViewCtrl.ai = new Rect();
/* 15769 */     pDFViewCtrl.aj = new Rect();
/* 15770 */     pDFViewCtrl.ak = new RectF();
/* 15771 */     pDFViewCtrl.al = new RectF();
/*       */     
/* 15773 */     pDFViewCtrl.da.setIsLongpressEnabled(false);
/* 15774 */     pDFViewCtrl.bZ = true;
/* 15775 */     pDFViewCtrl.ac = false;
/*       */     
/* 15777 */     pDFViewCtrl.setFocusable(true);
/* 15778 */     pDFViewCtrl.setFocusableInTouchMode(true);
/* 15779 */     pDFViewCtrl.setVerticalScrollBarEnabled(true);
/* 15780 */     pDFViewCtrl.setHorizontalScrollBarEnabled(true);
/* 15781 */     pDFViewCtrl.setScrollBarStyle(50331648);
/*       */     
/* 15783 */     pDFViewCtrl.setWillNotDraw(false);
/*       */     
/* 15785 */     pDFViewCtrl.ca = true;
/* 15786 */     pDFViewCtrl.cb = true;
/* 15787 */     pDFViewCtrl.cc = context.getResources().getInteger(17694722);
/* 15788 */     pDFViewCtrl.cd = 1000;
/* 15789 */     pDFViewCtrl.ce = new j(pDFViewCtrl, pDFViewCtrl.cc, pDFViewCtrl.cd);
/*       */     
/* 15791 */     pDFViewCtrl.aR = context.getResources().getInteger(17694720);
/* 15792 */     pDFViewCtrl.i = new g(pDFViewCtrl.aR);
/*       */     
/* 15794 */     pDFViewCtrl.cf = null;
/*       */     
/* 15796 */     pDFViewCtrl.cg = 0;
/* 15797 */     pDFViewCtrl.ch = 0;
/*       */     
/* 15799 */     pDFViewCtrl.cj = false;
/*       */     
/* 15801 */     pDFViewCtrl.cD = new u(pDFViewCtrl);
/*       */     
/*       */     try {
/* 15804 */       long[] arrayOfLong = pDFViewCtrl.PDFViewCtrlCreate(new RenderCallback());
/* 15805 */       pDFViewCtrl.cY = arrayOfLong[0];
/* 15806 */       pDFViewCtrl.cZ = arrayOfLong[1];
/* 15807 */       pDFViewCtrl.cX = SetJavaScriptEventCallback(pDFViewCtrl.cY, new a(context), (Object)null);
/*       */ 
/*       */       
/* 15810 */       pDFViewCtrl.setImageSmoothing(false);
/* 15811 */       pDFViewCtrl.setCaching(false);
/* 15812 */       pDFViewCtrl.setOverprint(OverPrintMode.PDFX);
/* 15813 */       pDFViewCtrl.setPageViewMode(pDFViewCtrl.bT);
/* 15814 */       pDFViewCtrl.setPageRefViewMode(pDFViewCtrl.bT);
/* 15815 */       PagePresentationMode pagePresentationMode = pDFViewCtrl.bV;
/* 15816 */       pDFViewCtrl.bV = pDFViewCtrl.getPagePresentationMode();
/* 15817 */       pDFViewCtrl.setPagePresentationMode(pagePresentationMode);
/*       */       
/* 15819 */       long l1 = Runtime.getRuntime().maxMemory() / 1048576L;
/* 15820 */       pDFViewCtrl.setRenderedContentCacheSize((long)(l1 * 0.25D));
/* 15821 */     } catch (Exception exception) {
/*       */       return;
/*       */     } 
/*       */     
/* 15825 */     if (d)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 16221 */       if (d) {
/* 16222 */         int n = a(true);
/* 16223 */         int i1 = a(true);
/*       */         
/* 16225 */         if (n == i1) {
/* 16226 */           L = 3;
/*       */         }
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 16683 */     b.a.a().a(64);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isValid() {
/*       */     return (this.cY != 0L);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*       */     int n = this.cm;
/*       */     int i1 = this.cn;
/*       */     boolean bool = this.co;
/*       */     this.cm = 0;
/*       */     this.cn = 0;
/*       */     this.co = false;
/*       */     int i2 = paramInt3 - paramInt1;
/*       */     int i3 = paramInt4 - paramInt2;
/*       */     if (this.aB == i2 && this.aC == i3 && this.mDoc != null && !this.t && !this.bg) {
/*       */       if (this.bK) {
/*       */         this.df.removeMessages(0);
/*       */         this.df.sendEmptyMessage(0);
/*       */       } 
/*       */       return;
/*       */     } 
/*       */     this.aC = i3;
/*       */     this.aB = i2;
/*       */     if (this.mDoc != null && this.aB > 0 && this.aC > 0) {
/*       */       this.bg = false;
/*       */       try {
/*       */         OnSize(this.cY, this.aB, this.aC, this.aB, false);
/*       */       } catch (Exception exception) {}
/*       */       if (this.t) {
/*       */         this.t = false;
/*       */         setPagePresentationMode(this.bV);
/*       */         setPageViewMode(this.bT);
/*       */         scrollTo(0, 0);
/*       */         this.bk = j();
/*       */         this.bl = a(this.bk);
/*       */         this.bn = k();
/*       */         this.bo = a(this.bn);
/*       */         this.bH = getCurrentPage();
/*       */         this.bG = this.bH;
/*       */         if (this.bS != null) {
/*       */           this.bS.onSetDoc();
/*       */         }
/*       */       } 
/*       */       c();
/*       */       if (n != 0 || i1 != 0) {
/*       */         OnScroll(this.cY, n, i1, false);
/*       */       }
/*       */       scrollTo(p(), q());
/*       */       requestRendering();
/*       */     } else if (this.mDoc == null) {
/*       */       this.aD = 0;
/*       */       this.aE = 0;
/*       */       this.aF = 0;
/*       */       this.aJ = 0;
/*       */     } 
/*       */     if (paramBoolean && this.mDoc != null && this.bj == ZoomLimitMode.RELATIVE) {
/*       */       double d2, d3, d1 = getZoom();
/*       */       this.bk = j();
/*       */       this.bl = a(this.bk);
/*       */       this.bn = k();
/*       */       this.bo = a(this.bn);
/*       */       if (this.cv) {
/*       */         d2 = this.bh * this.bn;
/*       */         d3 = this.bi * this.bo;
/*       */       } else {
/*       */         d2 = this.bh * this.bk;
/*       */         d3 = this.bi * this.bl;
/*       */       } 
/*       */       if (d1 < d2) {
/*       */         if (this.cv) {
/*       */           if (this.cy == PageViewMode.FIT_PAGE || this.cy == PageViewMode.FIT_WIDTH || this.cy == PageViewMode.FIT_HEIGHT) {
/*       */             setPageViewMode(this.cy, this.aB / 2, this.aC / 2, bool);
/*       */           }
/*       */         } else {
/*       */           PageViewMode pageViewMode = this.bU;
/*       */           if (this.bU != PageViewMode.FIT_PAGE && this.bU != PageViewMode.FIT_WIDTH && this.bU != PageViewMode.FIT_HEIGHT) {
/*       */             pageViewMode = getPageRefViewMode();
/*       */           }
/*       */           setPageViewMode(pageViewMode, this.aB / 2, this.aC / 2, bool);
/*       */         } 
/*       */       }
/*       */       if (d1 > d3) {
/*       */         setZoom(this.aB / 2, this.aC / 2, d3, bool, bool);
/*       */       }
/*       */       requestRendering();
/*       */     } 
/*       */     if (this.aC > 0 && this.aB > 0 && this.mDoc != null) {
/*       */       this.df.removeMessages(0);
/*       */       this.df.sendEmptyMessage(0);
/*       */       if (this.bS != null && !this.bQ) {
/*       */         this.bQ = true;
/*       */         this.bS.onControlReady();
/*       */       } 
/*       */       this.cD.c();
/*       */     } 
/*       */     if (this.bS != null) {
/*       */       this.bS.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*       */     super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onMeasure(int paramInt1, int paramInt2) {
/*       */     int n = MeasureSpec.getSize(paramInt1);
/*       */     int i1 = MeasureSpec.getSize(paramInt2);
/*       */     setMeasuredDimension(n, i1);
/*       */     n = getChildCount();
/*       */     for (i1 = 0; i1 < n; i1++) {
/*       */       View view;
/*       */       if ((view = getChildAt(i1)).getVisibility() != 8) {
/*       */         measureChild(view, paramInt1, paramInt2);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void c() {
/*       */     this.cV = false;
/*       */     this.cw.clear();
/*       */     this.cx.clear();
/*       */     PDFViewCtrl pDFViewCtrl;
/* 16868 */     this.aD = GetTilingRegionWidth((pDFViewCtrl = this).cY);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 16873 */     this.aE = GetTilingRegionHeight((pDFViewCtrl = this).cY);
/*       */     this.l.b(getCurCanvasId());
/*       */     this.cD.c();
/*       */     if (!this.cv || !f()) {
/*       */       r();
/*       */     }
/*       */     if (this.cC != null) {
/*       */       for (Iterator<OnCanvasSizeChangeListener> iterator = this.cC.iterator(); iterator.hasNext();) {
/*       */         (onCanvasSizeChangeListener = iterator.next()).onCanvasSizeChanged();
/*       */       }
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean onGenericMotionEvent(MotionEvent paramMotionEvent) {
/*       */     this.D.lock();
/*       */     this.C.x = paramMotionEvent.getX();
/*       */     this.C.y = paramMotionEvent.getY();
/*       */     this.D.unlock();
/*       */     if (this.bS != null && this.bS.onGenericMotionEvent(paramMotionEvent)) {
/*       */       return true;
/*       */     }
/*       */     return super.onGenericMotionEvent(paramMotionEvent);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public PointF getCurrentMousePosition() {
/*       */     this.D.lock();
/*       */     PointF pointF = new PointF(this.C.x, this.C.y);
/*       */     this.D.unlock();
/*       */     return pointF;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean onTouchEvent(MotionEvent paramMotionEvent) {
/*       */     int[] arrayOfInt = { 0, 0 };
/*       */     getLocationOnScreen(arrayOfInt);
/*       */     PointF pointF = new PointF(paramMotionEvent.getRawX() - arrayOfInt[0], paramMotionEvent.getRawY() - arrayOfInt[1]);
/*       */     paramMotionEvent.setLocation(pointF.x, pointF.y);
/*       */     super.onTouchEvent(paramMotionEvent);
/*       */     if (this.mDoc == null) {
/*       */       return true;
/*       */     }
/*       */     if (!this.bZ) {
/*       */       return true;
/*       */     }
/*       */     if (this.dk.hasMessages(0)) {
/*       */       this.dk.removeMessages(0);
/*       */       this.dk.dispatchMessage(this.dk.obtainMessage(0));
/*       */     } 
/*       */     if (this.dl.hasMessages(0)) {
/*       */       this.dl.removeMessages(0);
/*       */       this.dl.dispatchMessage(this.dl.obtainMessage(0));
/*       */     } 
/*       */     if (paramMotionEvent.getToolType(0) == 2 && !this.bY) {
/*       */       if (paramMotionEvent.getAction() == 211) {
/*       */         if (this.bS != null) {
/*       */           this.bS.onDown(paramMotionEvent);
/*       */         }
/*       */       } else if (paramMotionEvent.getAction() == 213) {
/*       */         if (this.bS != null) {
/*       */           this.bS.onMove(paramMotionEvent, paramMotionEvent, -1.0F, -1.0F);
/*       */         }
/*       */       } else if (paramMotionEvent.getAction() == 212 && this.bS != null) {
/*       */         this.bS.onUp(paramMotionEvent, PriorEventMode.OTHER);
/*       */       } 
/*       */     }
/*       */     boolean bool1;
/*       */     if (!(bool1 = this.da.onTouchEvent(paramMotionEvent)) && paramMotionEvent.getAction() == 2 && this.bS != null) {
/*       */       this.bS.onMove(paramMotionEvent, paramMotionEvent, -1.0F, -1.0F);
/*       */     }
/*       */     if (paramMotionEvent.getPointerCount() > 1 && (paramMotionEvent.getAction() & 0xFF) == 5 && this.bS != null) {
/*       */       this.bS.onPointerDown(paramMotionEvent);
/*       */     }
/*       */     this.af = false;
/*       */     if (Build.VERSION.SDK_INT >= 19) {
/*       */       boolean bool = true;
/*       */       if (this.bS != null) {
/*       */         bool = !this.bS.isCreatingAnnotation() ? true : false;
/*       */       }
/*       */       this.db.setQuickScaleEnabled((this.bX && bool));
/*       */     } 
/*       */     if (paramMotionEvent.getAction() == 2 && this.bt != null) {
/*       */       if (paramMotionEvent.getY() < this.bt.y) {
/*       */         this.bv = true;
/*       */         if (a) {
/*       */           Log.d(c, "scale: ABOVE");
/*       */         }
/*       */       } else {
/*       */         this.bv = false;
/*       */         if (a) {
/*       */           Log.d(c, "scale: BELOW");
/*       */         }
/*       */       } 
/*       */     }
/*       */     if (Build.VERSION.SDK_INT >= 23) {
/*       */       this.db.setStylusScaleEnabled(this.bY);
/*       */     }
/*       */     boolean bool2 = this.db.onTouchEvent(paramMotionEvent);
/*       */     if (paramMotionEvent.getAction() == 0) {
/*       */       this.di.removeMessages(1);
/*       */       this.ae = MotionEvent.obtain(paramMotionEvent);
/*       */       this.di.sendEmptyMessageAtTime(1, paramMotionEvent.getDownTime() + 500L);
/*       */     } else if (paramMotionEvent.getAction() == 1 || bool1 || this.af) {
/*       */       this.di.removeMessages(1);
/*       */     } 
/*       */     bool1 |= bool2;
/*       */     if (paramMotionEvent.getAction() == 1) {
/*       */       this.cq = o.d;
/*       */       if (this.Q) {
/*       */         onUp(paramMotionEvent, PriorEventMode.SCROLLING);
/*       */         this.Q = false;
/*       */         this.S = false;
/*       */       } else if (this.S && this.T) {
/*       */         onUp(paramMotionEvent, PriorEventMode.PINCH);
/*       */         this.S = false;
/*       */       } else if (this.U) {
/*       */         this.U = false;
/*       */         onUp(paramMotionEvent, PriorEventMode.DOUBLE_TAP);
/*       */       } else if (this.R) {
/*       */         onUp(paramMotionEvent, PriorEventMode.FLING);
/*       */       } else {
/*       */         onUp(paramMotionEvent, PriorEventMode.OTHER);
/*       */       } 
/*       */     } 
/*       */     return bool1;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onDraw(Canvas paramCanvas) {
/*       */     // Byte code:
/*       */     //   0: aload_0
/*       */     //   1: getfield mDoc : Lcom/pdftron/pdf/PDFDoc;
/*       */     //   4: ifnonnull -> 8
/*       */     //   7: return
/*       */     //   8: iconst_0
/*       */     //   9: istore_2
/*       */     //   10: aload_0
/*       */     //   11: getfield j : Ljava/util/concurrent/locks/Lock;
/*       */     //   14: invokeinterface lock : ()V
/*       */     //   19: iconst_1
/*       */     //   20: istore_2
/*       */     //   21: aload_0
/*       */     //   22: getfield bV : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */     //   25: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */     //   28: istore_3
/*       */     //   29: aload_0
/*       */     //   30: invokevirtual getCurCanvasId : ()I
/*       */     //   33: istore #4
/*       */     //   35: aload_0
/*       */     //   36: invokevirtual getScrollX : ()I
/*       */     //   39: istore #5
/*       */     //   41: aload_0
/*       */     //   42: invokevirtual getScrollY : ()I
/*       */     //   45: istore #6
/*       */     //   47: aload_0
/*       */     //   48: invokespecial n : ()Z
/*       */     //   51: ifeq -> 113
/*       */     //   54: iload_3
/*       */     //   55: ifeq -> 87
/*       */     //   58: aload_0
/*       */     //   59: getfield cv : Z
/*       */     //   62: ifeq -> 75
/*       */     //   65: aload_0
/*       */     //   66: aload_0
/*       */     //   67: iload #4
/*       */     //   69: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */     //   72: putfield aL : I
/*       */     //   75: iload #6
/*       */     //   77: aload_0
/*       */     //   78: getfield aL : I
/*       */     //   81: isub
/*       */     //   82: istore #6
/*       */     //   84: goto -> 113
/*       */     //   87: aload_0
/*       */     //   88: getfield cv : Z
/*       */     //   91: ifeq -> 104
/*       */     //   94: aload_0
/*       */     //   95: aload_0
/*       */     //   96: iload #4
/*       */     //   98: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */     //   101: putfield aH : I
/*       */     //   104: iload #5
/*       */     //   106: aload_0
/*       */     //   107: getfield aH : I
/*       */     //   110: isub
/*       */     //   111: istore #5
/*       */     //   113: aload_0
/*       */     //   114: getfield i : Lcom/pdftron/pdf/g;
/*       */     //   117: invokevirtual b : ()I
/*       */     //   120: dup
/*       */     //   121: istore #7
/*       */     //   123: iconst_2
/*       */     //   124: if_icmpne -> 209
/*       */     //   127: aload_0
/*       */     //   128: getfield cD : Lcom/pdftron/pdf/PDFViewCtrl$u;
/*       */     //   131: invokevirtual c : ()V
/*       */     //   134: aload_0
/*       */     //   135: getfield K : Ljava/util/List;
/*       */     //   138: invokeinterface iterator : ()Ljava/util/Iterator;
/*       */     //   143: astore #8
/*       */     //   145: aload #8
/*       */     //   147: invokeinterface hasNext : ()Z
/*       */     //   152: ifeq -> 209
/*       */     //   155: aload #8
/*       */     //   157: invokeinterface next : ()Ljava/lang/Object;
/*       */     //   162: checkcast java/lang/Integer
/*       */     //   165: invokevirtual intValue : ()I
/*       */     //   168: istore #9
/*       */     //   170: aload_0
/*       */     //   171: getfield J : Landroid/util/SparseArray;
/*       */     //   174: iload #9
/*       */     //   176: aconst_null
/*       */     //   177: invokevirtual get : (ILjava/lang/Object;)Ljava/lang/Object;
/*       */     //   180: checkcast android/graphics/Rect
/*       */     //   183: dup
/*       */     //   184: astore #10
/*       */     //   186: ifnull -> 206
/*       */     //   189: invokestatic a : ()Lcom/pdftron/pdf/b;
/*       */     //   192: aload #10
/*       */     //   194: invokevirtual a : (Landroid/graphics/Rect;)V
/*       */     //   197: aload_0
/*       */     //   198: getfield J : Landroid/util/SparseArray;
/*       */     //   201: iload #9
/*       */     //   203: invokevirtual remove : (I)V
/*       */     //   206: goto -> 145
/*       */     //   209: aload_0
/*       */     //   210: invokespecial f : ()Z
/*       */     //   213: ifne -> 243
/*       */     //   216: aload_0
/*       */     //   217: getfield r : Z
/*       */     //   220: ifne -> 243
/*       */     //   223: aload_0
/*       */     //   224: getfield dg : Lcom/pdftron/pdf/PDFViewCtrl$b;
/*       */     //   227: iconst_0
/*       */     //   228: invokevirtual hasMessages : (I)Z
/*       */     //   231: ifne -> 243
/*       */     //   234: aload_0
/*       */     //   235: getfield dg : Lcom/pdftron/pdf/PDFViewCtrl$b;
/*       */     //   238: iconst_0
/*       */     //   239: invokevirtual sendEmptyMessage : (I)Z
/*       */     //   242: pop
/*       */     //   243: iconst_0
/*       */     //   244: istore #8
/*       */     //   246: aload_0
/*       */     //   247: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   250: iload #4
/*       */     //   252: invokevirtual c : (I)Lcom/pdftron/pdf/f$a;
/*       */     //   255: dup
/*       */     //   256: astore #9
/*       */     //   258: ifnonnull -> 318
/*       */     //   261: aload_0
/*       */     //   262: aload_0
/*       */     //   263: getfield bV : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */     //   266: invokevirtual isContinuousPagePresentationMode : (Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */     //   269: ifne -> 318
/*       */     //   272: aload_0
/*       */     //   273: dup
/*       */     //   274: astore #9
/*       */     //   276: invokevirtual getCurCanvasId : ()I
/*       */     //   279: istore #10
/*       */     //   281: aload #9
/*       */     //   283: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   286: iload #10
/*       */     //   288: invokevirtual c : (I)Lcom/pdftron/pdf/f$a;
/*       */     //   291: dup
/*       */     //   292: astore #11
/*       */     //   294: ifnonnull -> 307
/*       */     //   297: aload #9
/*       */     //   299: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   302: iload #10
/*       */     //   304: invokevirtual b : (I)V
/*       */     //   307: aload_0
/*       */     //   308: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   311: iload #4
/*       */     //   313: invokevirtual c : (I)Lcom/pdftron/pdf/f$a;
/*       */     //   316: astore #9
/*       */     //   318: aload #9
/*       */     //   320: ifnull -> 334
/*       */     //   323: aload_0
/*       */     //   324: getfield aA : [Lcom/pdftron/pdf/f$a;
/*       */     //   327: iconst_0
/*       */     //   328: iinc #8, 1
/*       */     //   331: aload #9
/*       */     //   333: aastore
/*       */     //   334: iconst_0
/*       */     //   335: istore #10
/*       */     //   337: iload #5
/*       */     //   339: istore #11
/*       */     //   341: iload #6
/*       */     //   343: istore #12
/*       */     //   345: aload_0
/*       */     //   346: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   349: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$j;)Z
/*       */     //   352: ifeq -> 575
/*       */     //   355: aload_0
/*       */     //   356: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   359: invokestatic b : (Lcom/pdftron/pdf/PDFViewCtrl$j;)I
/*       */     //   362: istore #13
/*       */     //   364: aload_0
/*       */     //   365: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   368: invokestatic c : (Lcom/pdftron/pdf/PDFViewCtrl$j;)Z
/*       */     //   371: ifeq -> 393
/*       */     //   374: iload_3
/*       */     //   375: ifeq -> 387
/*       */     //   378: aload_0
/*       */     //   379: invokespecial t : ()I
/*       */     //   382: istore #13
/*       */     //   384: goto -> 393
/*       */     //   387: aload_0
/*       */     //   388: invokespecial s : ()I
/*       */     //   391: istore #13
/*       */     //   393: iconst_0
/*       */     //   394: aload_0
/*       */     //   395: aload_1
/*       */     //   396: iload #13
/*       */     //   398: aload_0
/*       */     //   399: getfield r : Z
/*       */     //   402: ifne -> 409
/*       */     //   405: iconst_1
/*       */     //   406: goto -> 410
/*       */     //   409: iconst_0
/*       */     //   410: invokespecial a : (Landroid/graphics/Canvas;IZ)Z
/*       */     //   413: ior
/*       */     //   414: istore_3
/*       */     //   415: aload_0
/*       */     //   416: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   419: invokevirtual a : ()Z
/*       */     //   422: ifne -> 432
/*       */     //   425: aload_0
/*       */     //   426: aload_1
/*       */     //   427: iload #13
/*       */     //   429: invokespecial a : (Landroid/graphics/Canvas;I)V
/*       */     //   432: aload_0
/*       */     //   433: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   436: iload #13
/*       */     //   438: iload #13
/*       */     //   440: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$j;II)Z
/*       */     //   443: ifeq -> 496
/*       */     //   446: aload_0
/*       */     //   447: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   450: iload #13
/*       */     //   452: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$j;I)I
/*       */     //   455: istore #14
/*       */     //   457: iload_3
/*       */     //   458: aload_0
/*       */     //   459: aload_1
/*       */     //   460: iload #14
/*       */     //   462: aload_0
/*       */     //   463: getfield r : Z
/*       */     //   466: ifne -> 473
/*       */     //   469: iconst_1
/*       */     //   470: goto -> 474
/*       */     //   473: iconst_0
/*       */     //   474: invokespecial a : (Landroid/graphics/Canvas;IZ)Z
/*       */     //   477: ior
/*       */     //   478: istore_3
/*       */     //   479: aload_0
/*       */     //   480: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   483: invokevirtual a : ()Z
/*       */     //   486: ifne -> 496
/*       */     //   489: aload_0
/*       */     //   490: aload_1
/*       */     //   491: iload #14
/*       */     //   493: invokespecial a : (Landroid/graphics/Canvas;I)V
/*       */     //   496: aload_0
/*       */     //   497: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   500: iload #13
/*       */     //   502: iload #13
/*       */     //   504: invokestatic b : (Lcom/pdftron/pdf/PDFViewCtrl$j;II)Z
/*       */     //   507: ifeq -> 560
/*       */     //   510: aload_0
/*       */     //   511: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   514: iload #13
/*       */     //   516: invokestatic b : (Lcom/pdftron/pdf/PDFViewCtrl$j;I)I
/*       */     //   519: istore #14
/*       */     //   521: iload_3
/*       */     //   522: aload_0
/*       */     //   523: aload_1
/*       */     //   524: iload #14
/*       */     //   526: aload_0
/*       */     //   527: getfield r : Z
/*       */     //   530: ifne -> 537
/*       */     //   533: iconst_1
/*       */     //   534: goto -> 538
/*       */     //   537: iconst_0
/*       */     //   538: invokespecial a : (Landroid/graphics/Canvas;IZ)Z
/*       */     //   541: ior
/*       */     //   542: istore_3
/*       */     //   543: aload_0
/*       */     //   544: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   547: invokevirtual a : ()Z
/*       */     //   550: ifne -> 560
/*       */     //   553: aload_0
/*       */     //   554: aload_1
/*       */     //   555: iload #14
/*       */     //   557: invokespecial a : (Landroid/graphics/Canvas;I)V
/*       */     //   560: iload_3
/*       */     //   561: ifne -> 572
/*       */     //   564: aload_0
/*       */     //   565: getfield cD : Lcom/pdftron/pdf/PDFViewCtrl$u;
/*       */     //   568: iconst_0
/*       */     //   569: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$u;Z)V
/*       */     //   572: iconst_1
/*       */     //   573: istore #10
/*       */     //   575: iconst_0
/*       */     //   576: istore #13
/*       */     //   578: iconst_0
/*       */     //   579: istore_3
/*       */     //   580: aload_0
/*       */     //   581: invokespecial n : ()Z
/*       */     //   584: ifeq -> 645
/*       */     //   587: aload_0
/*       */     //   588: getfield bV : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */     //   591: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */     //   594: ifeq -> 622
/*       */     //   597: aload_0
/*       */     //   598: getfield cv : Z
/*       */     //   601: ifeq -> 614
/*       */     //   604: aload_0
/*       */     //   605: aload_0
/*       */     //   606: iload #4
/*       */     //   608: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */     //   611: putfield aL : I
/*       */     //   614: aload_0
/*       */     //   615: getfield aL : I
/*       */     //   618: istore_3
/*       */     //   619: goto -> 645
/*       */     //   622: aload_0
/*       */     //   623: getfield cv : Z
/*       */     //   626: ifeq -> 639
/*       */     //   629: aload_0
/*       */     //   630: aload_0
/*       */     //   631: iload #4
/*       */     //   633: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */     //   636: putfield aH : I
/*       */     //   639: aload_0
/*       */     //   640: getfield aH : I
/*       */     //   643: istore #13
/*       */     //   645: aload_0
/*       */     //   646: getfield r : Z
/*       */     //   649: ifeq -> 673
/*       */     //   652: aload_0
/*       */     //   653: aload_1
/*       */     //   654: iload #11
/*       */     //   656: iload #12
/*       */     //   658: iload #5
/*       */     //   660: iload #12
/*       */     //   662: iconst_0
/*       */     //   663: invokespecial a : (Landroid/graphics/Canvas;IIIIZ)V
/*       */     //   666: aload_0
/*       */     //   667: getfield J : Landroid/util/SparseArray;
/*       */     //   670: invokevirtual clear : ()V
/*       */     //   673: iload #7
/*       */     //   675: iconst_1
/*       */     //   676: if_icmpne -> 682
/*       */     //   679: iconst_0
/*       */     //   680: istore #8
/*       */     //   682: iconst_0
/*       */     //   683: istore #14
/*       */     //   685: iload #14
/*       */     //   687: iload #8
/*       */     //   689: if_icmpge -> 1387
/*       */     //   692: iconst_0
/*       */     //   693: istore #4
/*       */     //   695: aload_0
/*       */     //   696: getfield aA : [Lcom/pdftron/pdf/f$a;
/*       */     //   699: iload #14
/*       */     //   701: aaload
/*       */     //   702: astore #9
/*       */     //   704: aload_0
/*       */     //   705: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   708: invokevirtual a : ()Z
/*       */     //   711: ifne -> 1347
/*       */     //   714: iload #10
/*       */     //   716: ifne -> 1347
/*       */     //   719: aload_0
/*       */     //   720: getfield cG : Landroid/graphics/Rect;
/*       */     //   723: iload #5
/*       */     //   725: iload #6
/*       */     //   727: iload #5
/*       */     //   729: aload_0
/*       */     //   730: getfield aB : I
/*       */     //   733: iadd
/*       */     //   734: iload #6
/*       */     //   736: aload_0
/*       */     //   737: getfield aC : I
/*       */     //   740: iadd
/*       */     //   741: invokevirtual set : (IIII)V
/*       */     //   744: aload_0
/*       */     //   745: getfield r : Z
/*       */     //   748: ifeq -> 760
/*       */     //   751: iload #5
/*       */     //   753: ineg
/*       */     //   754: istore #13
/*       */     //   756: iload #6
/*       */     //   758: ineg
/*       */     //   759: istore_3
/*       */     //   760: aload_0
/*       */     //   761: aload_0
/*       */     //   762: getfield cG : Landroid/graphics/Rect;
/*       */     //   765: getfield left : I
/*       */     //   768: i2d
/*       */     //   769: aload_0
/*       */     //   770: getfield cG : Landroid/graphics/Rect;
/*       */     //   773: getfield top : I
/*       */     //   776: i2d
/*       */     //   777: aload_0
/*       */     //   778: getfield cG : Landroid/graphics/Rect;
/*       */     //   781: getfield right : I
/*       */     //   784: i2d
/*       */     //   785: aload_0
/*       */     //   786: getfield cG : Landroid/graphics/Rect;
/*       */     //   789: getfield bottom : I
/*       */     //   792: i2d
/*       */     //   793: invokespecial a : (DDDD)[D
/*       */     //   796: dup
/*       */     //   797: astore #15
/*       */     //   799: arraylength
/*       */     //   800: istore #16
/*       */     //   802: iconst_0
/*       */     //   803: istore #17
/*       */     //   805: iload #17
/*       */     //   807: iload #16
/*       */     //   809: if_icmpge -> 1344
/*       */     //   812: aload #15
/*       */     //   814: iload #17
/*       */     //   816: daload
/*       */     //   817: d2i
/*       */     //   818: istore #18
/*       */     //   820: aload_0
/*       */     //   821: getfield cH : Landroid/graphics/Rect;
/*       */     //   824: aload #15
/*       */     //   826: iload #17
/*       */     //   828: iconst_1
/*       */     //   829: iadd
/*       */     //   830: daload
/*       */     //   831: ldc2_w 0.5
/*       */     //   834: dadd
/*       */     //   835: d2i
/*       */     //   836: aload #15
/*       */     //   838: iload #17
/*       */     //   840: iconst_2
/*       */     //   841: iadd
/*       */     //   842: daload
/*       */     //   843: ldc2_w 0.5
/*       */     //   846: dadd
/*       */     //   847: d2i
/*       */     //   848: aload #15
/*       */     //   850: iload #17
/*       */     //   852: iconst_3
/*       */     //   853: iadd
/*       */     //   854: daload
/*       */     //   855: ldc2_w 0.5
/*       */     //   858: dadd
/*       */     //   859: d2i
/*       */     //   860: aload #15
/*       */     //   862: iload #17
/*       */     //   864: iconst_4
/*       */     //   865: iadd
/*       */     //   866: daload
/*       */     //   867: ldc2_w 0.5
/*       */     //   870: dadd
/*       */     //   871: d2i
/*       */     //   872: invokevirtual set : (IIII)V
/*       */     //   875: aload_0
/*       */     //   876: getfield cI : Landroid/graphics/Rect;
/*       */     //   879: aload_0
/*       */     //   880: getfield cH : Landroid/graphics/Rect;
/*       */     //   883: invokevirtual set : (Landroid/graphics/Rect;)V
/*       */     //   886: aload_0
/*       */     //   887: getfield cH : Landroid/graphics/Rect;
/*       */     //   890: aload_0
/*       */     //   891: getfield cG : Landroid/graphics/Rect;
/*       */     //   894: invokevirtual intersect : (Landroid/graphics/Rect;)Z
/*       */     //   897: dup
/*       */     //   898: istore #19
/*       */     //   900: ifeq -> 1338
/*       */     //   903: aload_0
/*       */     //   904: getfield cJ : Landroid/graphics/Rect;
/*       */     //   907: aload_0
/*       */     //   908: getfield cH : Landroid/graphics/Rect;
/*       */     //   911: getfield left : I
/*       */     //   914: aload_0
/*       */     //   915: getfield cI : Landroid/graphics/Rect;
/*       */     //   918: getfield left : I
/*       */     //   921: isub
/*       */     //   922: aload_0
/*       */     //   923: getfield cH : Landroid/graphics/Rect;
/*       */     //   926: getfield top : I
/*       */     //   929: aload_0
/*       */     //   930: getfield cI : Landroid/graphics/Rect;
/*       */     //   933: getfield top : I
/*       */     //   936: isub
/*       */     //   937: aload_0
/*       */     //   938: getfield cH : Landroid/graphics/Rect;
/*       */     //   941: getfield right : I
/*       */     //   944: aload_0
/*       */     //   945: getfield cI : Landroid/graphics/Rect;
/*       */     //   948: getfield left : I
/*       */     //   951: isub
/*       */     //   952: aload_0
/*       */     //   953: getfield cH : Landroid/graphics/Rect;
/*       */     //   956: getfield bottom : I
/*       */     //   959: aload_0
/*       */     //   960: getfield cI : Landroid/graphics/Rect;
/*       */     //   963: getfield top : I
/*       */     //   966: isub
/*       */     //   967: invokevirtual set : (IIII)V
/*       */     //   970: aload_0
/*       */     //   971: getfield cH : Landroid/graphics/Rect;
/*       */     //   974: invokevirtual width : ()I
/*       */     //   977: aload_0
/*       */     //   978: getfield cH : Landroid/graphics/Rect;
/*       */     //   981: invokevirtual height : ()I
/*       */     //   984: imul
/*       */     //   985: istore #19
/*       */     //   987: aload_0
/*       */     //   988: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   991: iload #18
/*       */     //   993: iconst_0
/*       */     //   994: invokevirtual b : (II)Ljava/util/Collection;
/*       */     //   997: astore #20
/*       */     //   999: iconst_0
/*       */     //   1000: istore #21
/*       */     //   1002: aload #20
/*       */     //   1004: invokeinterface iterator : ()Ljava/util/Iterator;
/*       */     //   1009: astore #20
/*       */     //   1011: aload #20
/*       */     //   1013: invokeinterface hasNext : ()Z
/*       */     //   1018: ifeq -> 1122
/*       */     //   1021: aload #20
/*       */     //   1023: invokeinterface next : ()Ljava/lang/Object;
/*       */     //   1028: checkcast com/pdftron/pdf/e
/*       */     //   1031: astore #22
/*       */     //   1033: aload_0
/*       */     //   1034: getfield cK : Landroid/graphics/Rect;
/*       */     //   1037: aload #22
/*       */     //   1039: getfield e : I
/*       */     //   1042: aload #22
/*       */     //   1044: getfield f : I
/*       */     //   1047: aload #22
/*       */     //   1049: getfield e : I
/*       */     //   1052: aload #22
/*       */     //   1054: getfield b : I
/*       */     //   1057: iadd
/*       */     //   1058: aload #22
/*       */     //   1060: getfield f : I
/*       */     //   1063: aload #22
/*       */     //   1065: getfield c : I
/*       */     //   1068: iadd
/*       */     //   1069: invokevirtual set : (IIII)V
/*       */     //   1072: aload_0
/*       */     //   1073: getfield cK : Landroid/graphics/Rect;
/*       */     //   1076: aload_0
/*       */     //   1077: getfield cJ : Landroid/graphics/Rect;
/*       */     //   1080: invokevirtual intersect : (Landroid/graphics/Rect;)Z
/*       */     //   1083: dup
/*       */     //   1084: istore #23
/*       */     //   1086: ifeq -> 1119
/*       */     //   1089: iload #21
/*       */     //   1091: aload_0
/*       */     //   1092: getfield cK : Landroid/graphics/Rect;
/*       */     //   1095: invokevirtual width : ()I
/*       */     //   1098: aload_0
/*       */     //   1099: getfield cK : Landroid/graphics/Rect;
/*       */     //   1102: invokevirtual height : ()I
/*       */     //   1105: imul
/*       */     //   1106: iadd
/*       */     //   1107: istore #21
/*       */     //   1109: aload_0
/*       */     //   1110: aload_1
/*       */     //   1111: aload #22
/*       */     //   1113: iload #13
/*       */     //   1115: iload_3
/*       */     //   1116: invokespecial a : (Landroid/graphics/Canvas;Lcom/pdftron/pdf/e;II)V
/*       */     //   1119: goto -> 1011
/*       */     //   1122: aload_0
/*       */     //   1123: getfield l : Lcom/pdftron/pdf/f;
/*       */     //   1126: iload #18
/*       */     //   1128: invokevirtual f : (I)Ljava/util/Collection;
/*       */     //   1131: dup
/*       */     //   1132: astore #20
/*       */     //   1134: invokeinterface iterator : ()Ljava/util/Iterator;
/*       */     //   1139: astore #22
/*       */     //   1141: aload #22
/*       */     //   1143: invokeinterface hasNext : ()Z
/*       */     //   1148: ifeq -> 1235
/*       */     //   1151: aload #22
/*       */     //   1153: invokeinterface next : ()Ljava/lang/Object;
/*       */     //   1158: checkcast com/pdftron/pdf/e
/*       */     //   1161: astore #23
/*       */     //   1163: aload_0
/*       */     //   1164: getfield cK : Landroid/graphics/Rect;
/*       */     //   1167: aload #23
/*       */     //   1169: getfield e : I
/*       */     //   1172: aload #23
/*       */     //   1174: getfield f : I
/*       */     //   1177: aload #23
/*       */     //   1179: getfield e : I
/*       */     //   1182: aload #23
/*       */     //   1184: getfield b : I
/*       */     //   1187: iadd
/*       */     //   1188: aload #23
/*       */     //   1190: getfield f : I
/*       */     //   1193: aload #23
/*       */     //   1195: getfield c : I
/*       */     //   1198: iadd
/*       */     //   1199: invokevirtual set : (IIII)V
/*       */     //   1202: aload_0
/*       */     //   1203: getfield M : Ljava/util/Set;
/*       */     //   1206: aload #23
/*       */     //   1208: getfield a : J
/*       */     //   1211: invokestatic valueOf : (J)Ljava/lang/Long;
/*       */     //   1214: invokeinterface contains : (Ljava/lang/Object;)Z
/*       */     //   1219: ifne -> 1232
/*       */     //   1222: aload_0
/*       */     //   1223: aload_1
/*       */     //   1224: aload #23
/*       */     //   1226: iload #13
/*       */     //   1228: iload_3
/*       */     //   1229: invokespecial a : (Landroid/graphics/Canvas;Lcom/pdftron/pdf/e;II)V
/*       */     //   1232: goto -> 1141
/*       */     //   1235: iload #19
/*       */     //   1237: iload #21
/*       */     //   1239: if_icmple -> 1248
/*       */     //   1242: iconst_1
/*       */     //   1243: istore #4
/*       */     //   1245: goto -> 1338
/*       */     //   1248: aload_0
/*       */     //   1249: getfield r : Z
/*       */     //   1252: ifeq -> 1338
/*       */     //   1255: invokestatic a : ()Lcom/pdftron/pdf/b;
/*       */     //   1258: invokevirtual a : ()Landroid/graphics/Rect;
/*       */     //   1261: dup
/*       */     //   1262: astore #22
/*       */     //   1264: aload_0
/*       */     //   1265: getfield cI : Landroid/graphics/Rect;
/*       */     //   1268: getfield left : I
/*       */     //   1271: aload_0
/*       */     //   1272: getfield cG : Landroid/graphics/Rect;
/*       */     //   1275: getfield left : I
/*       */     //   1278: isub
/*       */     //   1279: aload_0
/*       */     //   1280: getfield cI : Landroid/graphics/Rect;
/*       */     //   1283: getfield top : I
/*       */     //   1286: aload_0
/*       */     //   1287: getfield cG : Landroid/graphics/Rect;
/*       */     //   1290: getfield top : I
/*       */     //   1293: isub
/*       */     //   1294: aload_0
/*       */     //   1295: getfield cI : Landroid/graphics/Rect;
/*       */     //   1298: getfield right : I
/*       */     //   1301: aload_0
/*       */     //   1302: getfield cG : Landroid/graphics/Rect;
/*       */     //   1305: getfield left : I
/*       */     //   1308: isub
/*       */     //   1309: aload_0
/*       */     //   1310: getfield cI : Landroid/graphics/Rect;
/*       */     //   1313: getfield bottom : I
/*       */     //   1316: aload_0
/*       */     //   1317: getfield cG : Landroid/graphics/Rect;
/*       */     //   1320: getfield top : I
/*       */     //   1323: isub
/*       */     //   1324: invokevirtual set : (IIII)V
/*       */     //   1327: aload_0
/*       */     //   1328: getfield J : Landroid/util/SparseArray;
/*       */     //   1331: iload #18
/*       */     //   1333: aload #22
/*       */     //   1335: invokevirtual put : (ILjava/lang/Object;)V
/*       */     //   1338: iinc #17, 5
/*       */     //   1341: goto -> 805
/*       */     //   1344: goto -> 1350
/*       */     //   1347: iconst_1
/*       */     //   1348: istore #4
/*       */     //   1350: aload_0
/*       */     //   1351: getfield r : Z
/*       */     //   1354: ifne -> 1381
/*       */     //   1357: iload #4
/*       */     //   1359: ifeq -> 1381
/*       */     //   1362: iload #10
/*       */     //   1364: ifne -> 1381
/*       */     //   1367: aload_0
/*       */     //   1368: aload_1
/*       */     //   1369: iload #11
/*       */     //   1371: iload #12
/*       */     //   1373: iload #5
/*       */     //   1375: iload #12
/*       */     //   1377: iconst_1
/*       */     //   1378: invokespecial a : (Landroid/graphics/Canvas;IIIIZ)V
/*       */     //   1381: iinc #14, 1
/*       */     //   1384: goto -> 685
/*       */     //   1387: aload #9
/*       */     //   1389: ifnonnull -> 1406
/*       */     //   1392: aload_0
/*       */     //   1393: aload_1
/*       */     //   1394: iload #11
/*       */     //   1396: iload #12
/*       */     //   1398: iload #5
/*       */     //   1400: iload #12
/*       */     //   1402: iconst_0
/*       */     //   1403: invokespecial a : (Landroid/graphics/Canvas;IIIIZ)V
/*       */     //   1406: aload_0
/*       */     //   1407: getfield r : Z
/*       */     //   1410: ifne -> 2705
/*       */     //   1413: iload #7
/*       */     //   1415: iconst_1
/*       */     //   1416: if_icmpne -> 1869
/*       */     //   1419: aload_0
/*       */     //   1420: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1423: invokeinterface lock : ()V
/*       */     //   1428: aload_0
/*       */     //   1429: aload_1
/*       */     //   1430: astore #10
/*       */     //   1432: dup
/*       */     //   1433: astore #9
/*       */     //   1435: getfield am : Landroid/graphics/Paint;
/*       */     //   1438: iconst_1
/*       */     //   1439: invokevirtual setFilterBitmap : (Z)V
/*       */     //   1442: aload #9
/*       */     //   1444: getfield i : Lcom/pdftron/pdf/g;
/*       */     //   1447: invokevirtual c : ()Landroid/util/SparseArray;
/*       */     //   1450: astore #11
/*       */     //   1452: iconst_0
/*       */     //   1453: istore_3
/*       */     //   1454: iload_3
/*       */     //   1455: aload #11
/*       */     //   1457: invokevirtual size : ()I
/*       */     //   1460: if_icmpge -> 1831
/*       */     //   1463: aload #9
/*       */     //   1465: getfield i : Lcom/pdftron/pdf/g;
/*       */     //   1468: invokevirtual c : ()Landroid/util/SparseArray;
/*       */     //   1471: iload_3
/*       */     //   1472: invokevirtual keyAt : (I)I
/*       */     //   1475: istore #4
/*       */     //   1477: aload #9
/*       */     //   1479: getfield cO : Landroid/graphics/RectF;
/*       */     //   1482: aload #11
/*       */     //   1484: iload #4
/*       */     //   1486: invokevirtual get : (I)Ljava/lang/Object;
/*       */     //   1489: checkcast android/graphics/RectF
/*       */     //   1492: invokevirtual set : (Landroid/graphics/RectF;)V
/*       */     //   1495: aload #9
/*       */     //   1497: iload #4
/*       */     //   1499: aload #9
/*       */     //   1501: getfield cN : Landroid/graphics/RectF;
/*       */     //   1504: invokespecial a : (ILandroid/graphics/RectF;)Landroid/graphics/Bitmap;
/*       */     //   1507: dup
/*       */     //   1508: astore #5
/*       */     //   1510: ifnull -> 1534
/*       */     //   1513: aload #10
/*       */     //   1515: aload #5
/*       */     //   1517: aconst_null
/*       */     //   1518: aload #9
/*       */     //   1520: getfield cO : Landroid/graphics/RectF;
/*       */     //   1523: aload #9
/*       */     //   1525: getfield am : Landroid/graphics/Paint;
/*       */     //   1528: invokevirtual drawBitmap : (Landroid/graphics/Bitmap;Landroid/graphics/Rect;Landroid/graphics/RectF;Landroid/graphics/Paint;)V
/*       */     //   1531: goto -> 1576
/*       */     //   1534: aload #10
/*       */     //   1536: aload #9
/*       */     //   1538: getfield cO : Landroid/graphics/RectF;
/*       */     //   1541: getfield left : F
/*       */     //   1544: aload #9
/*       */     //   1546: getfield cO : Landroid/graphics/RectF;
/*       */     //   1549: getfield top : F
/*       */     //   1552: aload #9
/*       */     //   1554: getfield cO : Landroid/graphics/RectF;
/*       */     //   1557: getfield right : F
/*       */     //   1560: aload #9
/*       */     //   1562: getfield cO : Landroid/graphics/RectF;
/*       */     //   1565: getfield bottom : F
/*       */     //   1568: aload #9
/*       */     //   1570: getfield am : Landroid/graphics/Paint;
/*       */     //   1573: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
/*       */     //   1576: aload #9
/*       */     //   1578: getfield J : Landroid/util/SparseArray;
/*       */     //   1581: iload #4
/*       */     //   1583: invokevirtual get : (I)Ljava/lang/Object;
/*       */     //   1586: checkcast android/graphics/Rect
/*       */     //   1589: dup
/*       */     //   1590: astore #6
/*       */     //   1592: ifnull -> 1825
/*       */     //   1595: aload #9
/*       */     //   1597: getfield cP : Landroid/graphics/RectF;
/*       */     //   1600: aload #6
/*       */     //   1602: invokevirtual set : (Landroid/graphics/Rect;)V
/*       */     //   1605: aload #9
/*       */     //   1607: getfield ah : Landroid/graphics/Matrix;
/*       */     //   1610: aload #9
/*       */     //   1612: getfield cP : Landroid/graphics/RectF;
/*       */     //   1615: aload #9
/*       */     //   1617: getfield cO : Landroid/graphics/RectF;
/*       */     //   1620: getstatic android/graphics/Matrix$ScaleToFit.CENTER : Landroid/graphics/Matrix$ScaleToFit;
/*       */     //   1623: invokevirtual setRectToRect : (Landroid/graphics/RectF;Landroid/graphics/RectF;Landroid/graphics/Matrix$ScaleToFit;)Z
/*       */     //   1626: pop
/*       */     //   1627: aload #10
/*       */     //   1629: invokevirtual save : ()I
/*       */     //   1632: pop
/*       */     //   1633: aload #10
/*       */     //   1635: aload #9
/*       */     //   1637: getfield cO : Landroid/graphics/RectF;
/*       */     //   1640: getfield left : F
/*       */     //   1643: aload #9
/*       */     //   1645: getfield cO : Landroid/graphics/RectF;
/*       */     //   1648: getfield top : F
/*       */     //   1651: aload #9
/*       */     //   1653: getfield cO : Landroid/graphics/RectF;
/*       */     //   1656: getfield right : F
/*       */     //   1659: aload #9
/*       */     //   1661: getfield cO : Landroid/graphics/RectF;
/*       */     //   1664: getfield bottom : F
/*       */     //   1667: invokevirtual clipRect : (FFFF)Z
/*       */     //   1670: pop
/*       */     //   1671: aload #9
/*       */     //   1673: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1676: invokeinterface lock : ()V
/*       */     //   1681: aload #9
/*       */     //   1683: getfield n : Landroid/graphics/Bitmap;
/*       */     //   1686: ifnull -> 1709
/*       */     //   1689: aload #10
/*       */     //   1691: aload #9
/*       */     //   1693: getfield n : Landroid/graphics/Bitmap;
/*       */     //   1696: aload #9
/*       */     //   1698: getfield ah : Landroid/graphics/Matrix;
/*       */     //   1701: aload #9
/*       */     //   1703: getfield am : Landroid/graphics/Paint;
/*       */     //   1706: invokevirtual drawBitmap : (Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V
/*       */     //   1709: aload #9
/*       */     //   1711: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1714: invokeinterface unlock : ()V
/*       */     //   1719: goto -> 1737
/*       */     //   1722: astore #7
/*       */     //   1724: aload #9
/*       */     //   1726: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1729: invokeinterface unlock : ()V
/*       */     //   1734: aload #7
/*       */     //   1736: athrow
/*       */     //   1737: getstatic com/pdftron/pdf/PDFViewCtrl.a : Z
/*       */     //   1740: ifeq -> 1807
/*       */     //   1743: aload #9
/*       */     //   1745: getfield aw : Landroid/graphics/Paint;
/*       */     //   1748: astore #7
/*       */     //   1750: iload #4
/*       */     //   1752: iconst_4
/*       */     //   1753: irem
/*       */     //   1754: iconst_1
/*       */     //   1755: if_icmpne -> 1765
/*       */     //   1758: aload #9
/*       */     //   1760: getfield ax : Landroid/graphics/Paint;
/*       */     //   1763: astore #7
/*       */     //   1765: iload #4
/*       */     //   1767: iconst_4
/*       */     //   1768: irem
/*       */     //   1769: iconst_2
/*       */     //   1770: if_icmpne -> 1780
/*       */     //   1773: aload #9
/*       */     //   1775: getfield ay : Landroid/graphics/Paint;
/*       */     //   1778: astore #7
/*       */     //   1780: iload #4
/*       */     //   1782: iconst_4
/*       */     //   1783: irem
/*       */     //   1784: iconst_3
/*       */     //   1785: if_icmpne -> 1795
/*       */     //   1788: aload #9
/*       */     //   1790: getfield az : Landroid/graphics/Paint;
/*       */     //   1793: astore #7
/*       */     //   1795: aload #10
/*       */     //   1797: aload #9
/*       */     //   1799: getfield cO : Landroid/graphics/RectF;
/*       */     //   1802: aload #7
/*       */     //   1804: invokevirtual drawRect : (Landroid/graphics/RectF;Landroid/graphics/Paint;)V
/*       */     //   1807: aload #10
/*       */     //   1809: invokevirtual restore : ()V
/*       */     //   1812: goto -> 1825
/*       */     //   1815: astore #8
/*       */     //   1817: aload #10
/*       */     //   1819: invokevirtual restore : ()V
/*       */     //   1822: aload #8
/*       */     //   1824: athrow
/*       */     //   1825: iinc #3, 1
/*       */     //   1828: goto -> 1454
/*       */     //   1831: aload #9
/*       */     //   1833: getfield am : Landroid/graphics/Paint;
/*       */     //   1836: iconst_0
/*       */     //   1837: invokevirtual setFilterBitmap : (Z)V
/*       */     //   1840: aload #9
/*       */     //   1842: invokespecial h : ()V
/*       */     //   1845: aload_0
/*       */     //   1846: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1849: invokeinterface unlock : ()V
/*       */     //   1854: goto -> 2705
/*       */     //   1857: astore_1
/*       */     //   1858: aload_0
/*       */     //   1859: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1862: invokeinterface unlock : ()V
/*       */     //   1867: aload_1
/*       */     //   1868: athrow
/*       */     //   1869: aload_0
/*       */     //   1870: getfield u : Z
/*       */     //   1873: ifeq -> 1897
/*       */     //   1876: aload_0
/*       */     //   1877: iconst_0
/*       */     //   1878: putfield u : Z
/*       */     //   1881: aload_0
/*       */     //   1882: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   1885: ifnull -> 1897
/*       */     //   1888: aload_0
/*       */     //   1889: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   1892: invokeinterface onDoubleTapZoomAnimationEnd : ()V
/*       */     //   1897: aload_0
/*       */     //   1898: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   1901: invokeinterface lock : ()V
/*       */     //   1906: aload_0
/*       */     //   1907: getfield n : Landroid/graphics/Bitmap;
/*       */     //   1910: ifnull -> 2681
/*       */     //   1913: aload_0
/*       */     //   1914: getfield J : Landroid/util/SparseArray;
/*       */     //   1917: invokevirtual size : ()I
/*       */     //   1920: ifle -> 2681
/*       */     //   1923: aload_0
/*       */     //   1924: iload #10
/*       */     //   1926: aload_1
/*       */     //   1927: astore #11
/*       */     //   1929: istore #10
/*       */     //   1931: dup
/*       */     //   1932: astore #9
/*       */     //   1934: invokevirtual getScrollX : ()I
/*       */     //   1937: istore_3
/*       */     //   1938: aload #9
/*       */     //   1940: invokevirtual getScrollY : ()I
/*       */     //   1943: istore #4
/*       */     //   1945: new android/graphics/RectF
/*       */     //   1948: dup
/*       */     //   1949: iload_3
/*       */     //   1950: i2f
/*       */     //   1951: iload #4
/*       */     //   1953: i2f
/*       */     //   1954: iload_3
/*       */     //   1955: aload #9
/*       */     //   1957: getfield aB : I
/*       */     //   1960: iadd
/*       */     //   1961: i2f
/*       */     //   1962: iload #4
/*       */     //   1964: aload #9
/*       */     //   1966: getfield aC : I
/*       */     //   1969: iadd
/*       */     //   1970: i2f
/*       */     //   1971: invokespecial <init> : (FFFF)V
/*       */     //   1974: astore #5
/*       */     //   1976: iconst_0
/*       */     //   1977: istore #6
/*       */     //   1979: iconst_0
/*       */     //   1980: istore #7
/*       */     //   1982: aload #9
/*       */     //   1984: invokespecial n : ()Z
/*       */     //   1987: ifeq -> 2070
/*       */     //   1990: aload #9
/*       */     //   1992: getfield bV : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */     //   1995: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */     //   1998: ifeq -> 2016
/*       */     //   2001: aload #9
/*       */     //   2003: aload #9
/*       */     //   2005: getfield v : I
/*       */     //   2008: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */     //   2011: istore #7
/*       */     //   2013: goto -> 2028
/*       */     //   2016: aload #9
/*       */     //   2018: aload #9
/*       */     //   2020: getfield v : I
/*       */     //   2023: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */     //   2026: istore #6
/*       */     //   2028: new android/graphics/RectF
/*       */     //   2031: dup
/*       */     //   2032: iload #6
/*       */     //   2034: i2f
/*       */     //   2035: iload #7
/*       */     //   2037: i2f
/*       */     //   2038: iload #6
/*       */     //   2040: aload #9
/*       */     //   2042: getfield aD : I
/*       */     //   2045: iadd
/*       */     //   2046: i2f
/*       */     //   2047: aload #9
/*       */     //   2049: getfield aE : I
/*       */     //   2052: iload #7
/*       */     //   2054: iadd
/*       */     //   2055: i2f
/*       */     //   2056: invokespecial <init> : (FFFF)V
/*       */     //   2059: dup
/*       */     //   2060: astore #8
/*       */     //   2062: aload #5
/*       */     //   2064: invokestatic intersects : (Landroid/graphics/RectF;Landroid/graphics/RectF;)Z
/*       */     //   2067: ifeq -> 2681
/*       */     //   2070: iconst_0
/*       */     //   2071: istore #8
/*       */     //   2073: iconst_0
/*       */     //   2074: istore #12
/*       */     //   2076: iload #10
/*       */     //   2078: ifeq -> 2172
/*       */     //   2081: aload #9
/*       */     //   2083: getfield cv : Z
/*       */     //   2086: ifne -> 2122
/*       */     //   2089: aload #9
/*       */     //   2091: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   2094: invokestatic g : (Lcom/pdftron/pdf/PDFViewCtrl$j;)Z
/*       */     //   2097: ifne -> 2122
/*       */     //   2100: aload #9
/*       */     //   2102: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   2105: invokestatic h : (Lcom/pdftron/pdf/PDFViewCtrl$j;)Z
/*       */     //   2108: ifne -> 2122
/*       */     //   2111: aload #9
/*       */     //   2113: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   2116: invokestatic i : (Lcom/pdftron/pdf/PDFViewCtrl$j;)Z
/*       */     //   2119: ifeq -> 2172
/*       */     //   2122: aload #9
/*       */     //   2124: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   2127: invokestatic d : (Lcom/pdftron/pdf/PDFViewCtrl$j;)I
/*       */     //   2130: aload #9
/*       */     //   2132: getfield cv : Z
/*       */     //   2135: ifeq -> 2143
/*       */     //   2138: iload #6
/*       */     //   2140: goto -> 2144
/*       */     //   2143: iconst_0
/*       */     //   2144: isub
/*       */     //   2145: istore #8
/*       */     //   2147: aload #9
/*       */     //   2149: getfield ce : Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */     //   2152: invokestatic f : (Lcom/pdftron/pdf/PDFViewCtrl$j;)I
/*       */     //   2155: aload #9
/*       */     //   2157: getfield cv : Z
/*       */     //   2160: ifeq -> 2168
/*       */     //   2163: iload #7
/*       */     //   2165: goto -> 2169
/*       */     //   2168: iconst_0
/*       */     //   2169: isub
/*       */     //   2170: istore #12
/*       */     //   2172: iload_3
/*       */     //   2173: iload #6
/*       */     //   2175: isub
/*       */     //   2176: istore_3
/*       */     //   2177: iload #4
/*       */     //   2179: iload #7
/*       */     //   2181: isub
/*       */     //   2182: istore #4
/*       */     //   2184: aload #9
/*       */     //   2186: iload_3
/*       */     //   2187: i2d
/*       */     //   2188: iload #4
/*       */     //   2190: i2d
/*       */     //   2191: iload_3
/*       */     //   2192: aload #9
/*       */     //   2194: getfield aB : I
/*       */     //   2197: iadd
/*       */     //   2198: i2d
/*       */     //   2199: iload #4
/*       */     //   2201: aload #9
/*       */     //   2203: getfield aC : I
/*       */     //   2206: iadd
/*       */     //   2207: i2d
/*       */     //   2208: invokespecial a : (DDDD)[D
/*       */     //   2211: dup
/*       */     //   2212: astore_3
/*       */     //   2213: ifnull -> 2681
/*       */     //   2216: iconst_0
/*       */     //   2217: istore #4
/*       */     //   2219: iconst_0
/*       */     //   2220: istore #10
/*       */     //   2222: aload_3
/*       */     //   2223: arraylength
/*       */     //   2224: istore #13
/*       */     //   2226: iload #10
/*       */     //   2228: iload #13
/*       */     //   2230: if_icmpge -> 2668
/*       */     //   2233: aload_3
/*       */     //   2234: iload #10
/*       */     //   2236: daload
/*       */     //   2237: d2i
/*       */     //   2238: istore #14
/*       */     //   2240: aload #9
/*       */     //   2242: getfield J : Landroid/util/SparseArray;
/*       */     //   2245: iload #14
/*       */     //   2247: invokevirtual get : (I)Ljava/lang/Object;
/*       */     //   2250: checkcast android/graphics/Rect
/*       */     //   2253: dup
/*       */     //   2254: astore #15
/*       */     //   2256: ifnull -> 2662
/*       */     //   2259: aload #9
/*       */     //   2261: getfield ak : Landroid/graphics/RectF;
/*       */     //   2264: aload #15
/*       */     //   2266: getfield left : I
/*       */     //   2269: i2f
/*       */     //   2270: aload #15
/*       */     //   2272: getfield top : I
/*       */     //   2275: i2f
/*       */     //   2276: aload #15
/*       */     //   2278: getfield right : I
/*       */     //   2281: i2f
/*       */     //   2282: aload #15
/*       */     //   2284: getfield bottom : I
/*       */     //   2287: i2f
/*       */     //   2288: invokevirtual set : (FFFF)V
/*       */     //   2291: aload #9
/*       */     //   2293: getfield al : Landroid/graphics/RectF;
/*       */     //   2296: aload_3
/*       */     //   2297: iload #10
/*       */     //   2299: iconst_1
/*       */     //   2300: iadd
/*       */     //   2301: daload
/*       */     //   2302: d2f
/*       */     //   2303: iload #6
/*       */     //   2305: i2f
/*       */     //   2306: fadd
/*       */     //   2307: iload #8
/*       */     //   2309: i2f
/*       */     //   2310: fsub
/*       */     //   2311: aload_3
/*       */     //   2312: iload #10
/*       */     //   2314: iconst_2
/*       */     //   2315: iadd
/*       */     //   2316: daload
/*       */     //   2317: d2f
/*       */     //   2318: iload #7
/*       */     //   2320: i2f
/*       */     //   2321: fadd
/*       */     //   2322: iload #12
/*       */     //   2324: i2f
/*       */     //   2325: fsub
/*       */     //   2326: aload_3
/*       */     //   2327: iload #10
/*       */     //   2329: iconst_3
/*       */     //   2330: iadd
/*       */     //   2331: daload
/*       */     //   2332: d2f
/*       */     //   2333: iload #6
/*       */     //   2335: i2f
/*       */     //   2336: fadd
/*       */     //   2337: iload #8
/*       */     //   2339: i2f
/*       */     //   2340: fsub
/*       */     //   2341: aload_3
/*       */     //   2342: iload #10
/*       */     //   2344: iconst_4
/*       */     //   2345: iadd
/*       */     //   2346: daload
/*       */     //   2347: d2f
/*       */     //   2348: iload #7
/*       */     //   2350: i2f
/*       */     //   2351: fadd
/*       */     //   2352: iload #12
/*       */     //   2354: i2f
/*       */     //   2355: fsub
/*       */     //   2356: invokevirtual set : (FFFF)V
/*       */     //   2359: aload #9
/*       */     //   2361: getfield ah : Landroid/graphics/Matrix;
/*       */     //   2364: aload #9
/*       */     //   2366: getfield ak : Landroid/graphics/RectF;
/*       */     //   2369: aload #9
/*       */     //   2371: getfield al : Landroid/graphics/RectF;
/*       */     //   2374: getstatic android/graphics/Matrix$ScaleToFit.CENTER : Landroid/graphics/Matrix$ScaleToFit;
/*       */     //   2377: invokevirtual setRectToRect : (Landroid/graphics/RectF;Landroid/graphics/RectF;Landroid/graphics/Matrix$ScaleToFit;)Z
/*       */     //   2380: pop
/*       */     //   2381: aload #11
/*       */     //   2383: invokevirtual save : ()I
/*       */     //   2386: pop
/*       */     //   2387: aload #9
/*       */     //   2389: invokespecial n : ()Z
/*       */     //   2392: ifeq -> 2448
/*       */     //   2395: aload #9
/*       */     //   2397: getfield al : Landroid/graphics/RectF;
/*       */     //   2400: new android/graphics/RectF
/*       */     //   2403: dup
/*       */     //   2404: iload #6
/*       */     //   2406: i2f
/*       */     //   2407: iload #7
/*       */     //   2409: i2f
/*       */     //   2410: iload #6
/*       */     //   2412: aload #9
/*       */     //   2414: getfield aD : I
/*       */     //   2417: iadd
/*       */     //   2418: i2f
/*       */     //   2419: iload #7
/*       */     //   2421: aload #9
/*       */     //   2423: getfield aE : I
/*       */     //   2426: iadd
/*       */     //   2427: i2f
/*       */     //   2428: invokespecial <init> : (FFFF)V
/*       */     //   2431: invokevirtual intersect : (Landroid/graphics/RectF;)Z
/*       */     //   2434: dup
/*       */     //   2435: istore #15
/*       */     //   2437: ifne -> 2448
/*       */     //   2440: ldc 'DRAW'
/*       */     //   2442: ldc 'these should intersect'
/*       */     //   2444: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
/*       */     //   2447: pop
/*       */     //   2448: aload #11
/*       */     //   2450: aload #9
/*       */     //   2452: getfield al : Landroid/graphics/RectF;
/*       */     //   2455: getfield left : F
/*       */     //   2458: aload #9
/*       */     //   2460: getfield al : Landroid/graphics/RectF;
/*       */     //   2463: getfield top : F
/*       */     //   2466: aload #9
/*       */     //   2468: getfield al : Landroid/graphics/RectF;
/*       */     //   2471: getfield right : F
/*       */     //   2474: aload #9
/*       */     //   2476: getfield al : Landroid/graphics/RectF;
/*       */     //   2479: getfield bottom : F
/*       */     //   2482: invokevirtual clipRect : (FFFF)Z
/*       */     //   2485: pop
/*       */     //   2486: aload #9
/*       */     //   2488: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   2491: invokeinterface lock : ()V
/*       */     //   2496: aload #9
/*       */     //   2498: getfield n : Landroid/graphics/Bitmap;
/*       */     //   2501: ifnull -> 2524
/*       */     //   2504: aload #11
/*       */     //   2506: aload #9
/*       */     //   2508: getfield n : Landroid/graphics/Bitmap;
/*       */     //   2511: aload #9
/*       */     //   2513: getfield ah : Landroid/graphics/Matrix;
/*       */     //   2516: aload #9
/*       */     //   2518: getfield am : Landroid/graphics/Paint;
/*       */     //   2521: invokevirtual drawBitmap : (Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V
/*       */     //   2524: aload #9
/*       */     //   2526: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   2529: invokeinterface unlock : ()V
/*       */     //   2534: goto -> 2550
/*       */     //   2537: astore_1
/*       */     //   2538: aload #9
/*       */     //   2540: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   2543: invokeinterface unlock : ()V
/*       */     //   2548: aload_1
/*       */     //   2549: athrow
/*       */     //   2550: getstatic com/pdftron/pdf/PDFViewCtrl.a : Z
/*       */     //   2553: ifeq -> 2620
/*       */     //   2556: aload #9
/*       */     //   2558: getfield aw : Landroid/graphics/Paint;
/*       */     //   2561: astore #15
/*       */     //   2563: iload #14
/*       */     //   2565: iconst_4
/*       */     //   2566: irem
/*       */     //   2567: iconst_1
/*       */     //   2568: if_icmpne -> 2578
/*       */     //   2571: aload #9
/*       */     //   2573: getfield ax : Landroid/graphics/Paint;
/*       */     //   2576: astore #15
/*       */     //   2578: iload #14
/*       */     //   2580: iconst_4
/*       */     //   2581: irem
/*       */     //   2582: iconst_2
/*       */     //   2583: if_icmpne -> 2593
/*       */     //   2586: aload #9
/*       */     //   2588: getfield ay : Landroid/graphics/Paint;
/*       */     //   2591: astore #15
/*       */     //   2593: iload #14
/*       */     //   2595: iconst_4
/*       */     //   2596: irem
/*       */     //   2597: iconst_3
/*       */     //   2598: if_icmpne -> 2608
/*       */     //   2601: aload #9
/*       */     //   2603: getfield az : Landroid/graphics/Paint;
/*       */     //   2606: astore #15
/*       */     //   2608: aload #11
/*       */     //   2610: aload #9
/*       */     //   2612: getfield al : Landroid/graphics/RectF;
/*       */     //   2615: aload #15
/*       */     //   2617: invokevirtual drawRect : (Landroid/graphics/RectF;Landroid/graphics/Paint;)V
/*       */     //   2620: aload #11
/*       */     //   2622: invokevirtual restore : ()V
/*       */     //   2625: iload #4
/*       */     //   2627: aload #5
/*       */     //   2629: aload #9
/*       */     //   2631: getfield al : Landroid/graphics/RectF;
/*       */     //   2634: invokestatic intersects : (Landroid/graphics/RectF;Landroid/graphics/RectF;)Z
/*       */     //   2637: ior
/*       */     //   2638: istore #4
/*       */     //   2640: goto -> 2662
/*       */     //   2643: astore_1
/*       */     //   2644: aload #11
/*       */     //   2646: invokevirtual restore : ()V
/*       */     //   2649: aload #5
/*       */     //   2651: aload #9
/*       */     //   2653: getfield al : Landroid/graphics/RectF;
/*       */     //   2656: invokestatic intersects : (Landroid/graphics/RectF;Landroid/graphics/RectF;)Z
/*       */     //   2659: pop
/*       */     //   2660: aload_1
/*       */     //   2661: athrow
/*       */     //   2662: iinc #10, 5
/*       */     //   2665: goto -> 2226
/*       */     //   2668: iload #4
/*       */     //   2670: ifne -> 2681
/*       */     //   2673: aload #9
/*       */     //   2675: getfield J : Landroid/util/SparseArray;
/*       */     //   2678: invokevirtual clear : ()V
/*       */     //   2681: aload_0
/*       */     //   2682: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   2685: invokeinterface unlock : ()V
/*       */     //   2690: goto -> 2705
/*       */     //   2693: astore_1
/*       */     //   2694: aload_0
/*       */     //   2695: getfield k : Ljava/util/concurrent/locks/Lock;
/*       */     //   2698: invokeinterface unlock : ()V
/*       */     //   2703: aload_1
/*       */     //   2704: athrow
/*       */     //   2705: aload_0
/*       */     //   2706: aload_1
/*       */     //   2707: astore #10
/*       */     //   2709: dup
/*       */     //   2710: astore #9
/*       */     //   2712: getfield ca : Z
/*       */     //   2715: ifeq -> 2814
/*       */     //   2718: aload #9
/*       */     //   2720: aload #9
/*       */     //   2722: getfield bV : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */     //   2725: invokevirtual isContinuousPagePresentationMode : (Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */     //   2728: ifne -> 2814
/*       */     //   2731: iconst_0
/*       */     //   2732: istore #11
/*       */     //   2734: aload #9
/*       */     //   2736: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   2739: ifnull -> 2804
/*       */     //   2742: aload #9
/*       */     //   2744: getfield bV : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */     //   2747: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */     //   2750: ifeq -> 2780
/*       */     //   2753: aload #9
/*       */     //   2755: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   2758: aload #10
/*       */     //   2760: aload #9
/*       */     //   2762: invokevirtual getScrollX : ()I
/*       */     //   2765: aload #9
/*       */     //   2767: getfield aJ : I
/*       */     //   2770: invokeinterface onDrawEdgeEffects : (Landroid/graphics/Canvas;II)Z
/*       */     //   2775: istore #11
/*       */     //   2777: goto -> 2804
/*       */     //   2780: aload #9
/*       */     //   2782: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   2785: aload #10
/*       */     //   2787: aload #9
/*       */     //   2789: getfield aF : I
/*       */     //   2792: aload #9
/*       */     //   2794: invokevirtual getScrollY : ()I
/*       */     //   2797: invokeinterface onDrawEdgeEffects : (Landroid/graphics/Canvas;II)Z
/*       */     //   2802: istore #11
/*       */     //   2804: iload #11
/*       */     //   2806: ifeq -> 2814
/*       */     //   2809: aload #9
/*       */     //   2811: invokespecial h : ()V
/*       */     //   2814: aload_0
/*       */     //   2815: getfield j : Ljava/util/concurrent/locks/Lock;
/*       */     //   2818: invokeinterface unlock : ()V
/*       */     //   2823: iconst_0
/*       */     //   2824: istore_2
/*       */     //   2825: aload_0
/*       */     //   2826: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   2829: ifnull -> 2903
/*       */     //   2832: aload_0
/*       */     //   2833: invokespecial f : ()Z
/*       */     //   2836: ifeq -> 2846
/*       */     //   2839: aload_0
/*       */     //   2840: getfield r : Z
/*       */     //   2843: ifeq -> 2903
/*       */     //   2846: iconst_0
/*       */     //   2847: istore #14
/*       */     //   2849: aload_0
/*       */     //   2850: getfield r : Z
/*       */     //   2853: ifeq -> 2880
/*       */     //   2856: aload_1
/*       */     //   2857: invokevirtual save : ()I
/*       */     //   2860: pop
/*       */     //   2861: iconst_1
/*       */     //   2862: istore #14
/*       */     //   2864: aload_1
/*       */     //   2865: aload_0
/*       */     //   2866: invokevirtual getScrollX : ()I
/*       */     //   2869: ineg
/*       */     //   2870: i2f
/*       */     //   2871: aload_0
/*       */     //   2872: invokevirtual getScrollY : ()I
/*       */     //   2875: ineg
/*       */     //   2876: i2f
/*       */     //   2877: invokevirtual translate : (FF)V
/*       */     //   2880: aload_0
/*       */     //   2881: getfield bS : Lcom/pdftron/pdf/PDFViewCtrl$ToolManager;
/*       */     //   2884: aload_1
/*       */     //   2885: aload_0
/*       */     //   2886: getfield ag : Landroid/graphics/Matrix;
/*       */     //   2889: invokeinterface onDraw : (Landroid/graphics/Canvas;Landroid/graphics/Matrix;)V
/*       */     //   2894: iload #14
/*       */     //   2896: ifeq -> 2903
/*       */     //   2899: aload_1
/*       */     //   2900: invokevirtual restore : ()V
/*       */     //   2903: return
/*       */     //   2904: dup
/*       */     //   2905: astore_3
/*       */     //   2906: invokevirtual getMessage : ()Ljava/lang/String;
/*       */     //   2909: ifnull -> 2973
/*       */     //   2912: getstatic com/pdftron/pdf/PDFViewCtrl.b : Z
/*       */     //   2915: ifeq -> 2948
/*       */     //   2918: iconst_4
/*       */     //   2919: new java/lang/StringBuilder
/*       */     //   2922: dup
/*       */     //   2923: ldc 'OnDraw Error: '
/*       */     //   2925: invokespecial <init> : (Ljava/lang/String;)V
/*       */     //   2928: aload_3
/*       */     //   2929: invokevirtual getMessage : ()Ljava/lang/String;
/*       */     //   2932: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*       */     //   2935: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   2938: iconst_1
/*       */     //   2939: invokestatic a : (Z)I
/*       */     //   2942: invokestatic a : (ILjava/lang/String;I)V
/*       */     //   2945: goto -> 2973
/*       */     //   2948: ldc 'PDFNet'
/*       */     //   2950: new java/lang/StringBuilder
/*       */     //   2953: dup
/*       */     //   2954: ldc 'onDraw Error: '
/*       */     //   2956: invokespecial <init> : (Ljava/lang/String;)V
/*       */     //   2959: aload_3
/*       */     //   2960: invokevirtual getMessage : ()Ljava/lang/String;
/*       */     //   2963: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*       */     //   2966: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   2969: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
/*       */     //   2972: pop
/*       */     //   2973: iload_2
/*       */     //   2974: ifeq -> 3003
/*       */     //   2977: aload_0
/*       */     //   2978: getfield j : Ljava/util/concurrent/locks/Lock;
/*       */     //   2981: invokeinterface unlock : ()V
/*       */     //   2986: return
/*       */     //   2987: astore_1
/*       */     //   2988: iload_2
/*       */     //   2989: ifeq -> 3001
/*       */     //   2992: aload_0
/*       */     //   2993: getfield j : Ljava/util/concurrent/locks/Lock;
/*       */     //   2996: invokeinterface unlock : ()V
/*       */     //   3001: aload_1
/*       */     //   3002: athrow
/*       */     //   3003: return
/*       */     // Line number table:
/*       */     //   Java source line number -> byte code offset
/*       */     //   #2312	-> 0
/*       */     //   #2313	-> 7
/*       */     //   #2315	-> 8
/*       */     //   #2318	-> 10
/*       */     //   #2319	-> 19
/*       */     //   #2321	-> 21
/*       */     //   #2323	-> 29
/*       */     //   #2324	-> 35
/*       */     //   #2325	-> 41
/*       */     //   #2326	-> 47
/*       */     //   #2327	-> 54
/*       */     //   #2328	-> 58
/*       */     //   #2329	-> 65
/*       */     //   #2331	-> 75
/*       */     //   #2333	-> 87
/*       */     //   #2334	-> 94
/*       */     //   #2336	-> 104
/*       */     //   #2340	-> 113
/*       */     //   #2341	-> 121
/*       */     //   #2342	-> 127
/*       */     //   #2343	-> 134
/*       */     //   #2344	-> 170
/*       */     //   #2345	-> 184
/*       */     //   #17683	-> 189
/*       */     //   #2346	-> 192
/*       */     //   #2347	-> 197
/*       */     //   #2349	-> 206
/*       */     //   #2352	-> 209
/*       */     //   #2353	-> 223
/*       */     //   #2354	-> 234
/*       */     //   #2359	-> 243
/*       */     //   #2360	-> 246
/*       */     //   #2361	-> 256
/*       */     //   #2362	-> 261
/*       */     //   #2363	-> 272
/*       */     //   #18209	-> 274
/*       */     //   #18210	-> 281
/*       */     //   #18212	-> 292
/*       */     //   #18215	-> 297
/*       */     //   #2364	-> 307
/*       */     //   #2367	-> 318
/*       */     //   #2369	-> 323
/*       */     //   #2373	-> 334
/*       */     //   #2374	-> 337
/*       */     //   #2376	-> 341
/*       */     //   #2378	-> 345
/*       */     //   #2379	-> 355
/*       */     //   #2380	-> 364
/*       */     //   #2381	-> 374
/*       */     //   #2382	-> 378
/*       */     //   #2384	-> 387
/*       */     //   #2391	-> 393
/*       */     //   #2392	-> 415
/*       */     //   #2393	-> 425
/*       */     //   #2397	-> 432
/*       */     //   #2398	-> 446
/*       */     //   #2399	-> 457
/*       */     //   #2400	-> 479
/*       */     //   #2401	-> 489
/*       */     //   #2406	-> 496
/*       */     //   #2407	-> 510
/*       */     //   #2408	-> 521
/*       */     //   #2409	-> 543
/*       */     //   #2410	-> 553
/*       */     //   #2414	-> 560
/*       */     //   #2415	-> 564
/*       */     //   #2417	-> 572
/*       */     //   #2424	-> 575
/*       */     //   #2425	-> 578
/*       */     //   #2426	-> 580
/*       */     //   #2427	-> 587
/*       */     //   #2428	-> 597
/*       */     //   #2429	-> 604
/*       */     //   #2431	-> 614
/*       */     //   #2433	-> 622
/*       */     //   #2434	-> 629
/*       */     //   #2436	-> 639
/*       */     //   #2441	-> 645
/*       */     //   #2443	-> 652
/*       */     //   #2444	-> 666
/*       */     //   #2447	-> 673
/*       */     //   #2448	-> 679
/*       */     //   #2451	-> 682
/*       */     //   #2452	-> 692
/*       */     //   #2453	-> 695
/*       */     //   #2455	-> 704
/*       */     //   #2456	-> 719
/*       */     //   #2461	-> 744
/*       */     //   #2462	-> 751
/*       */     //   #2463	-> 756
/*       */     //   #2466	-> 760
/*       */     //   #2467	-> 797
/*       */     //   #2468	-> 802
/*       */     //   #2469	-> 812
/*       */     //   #2470	-> 820
/*       */     //   #2472	-> 875
/*       */     //   #2473	-> 886
/*       */     //   #2474	-> 898
/*       */     //   #2477	-> 903
/*       */     //   #2481	-> 970
/*       */     //   #2483	-> 987
/*       */     //   #2484	-> 999
/*       */     //   #2485	-> 1002
/*       */     //   #2486	-> 1033
/*       */     //   #2487	-> 1072
/*       */     //   #2488	-> 1084
/*       */     //   #2489	-> 1089
/*       */     //   #2492	-> 1109
/*       */     //   #2494	-> 1119
/*       */     //   #2497	-> 1122
/*       */     //   #2498	-> 1132
/*       */     //   #2499	-> 1163
/*       */     //   #2500	-> 1202
/*       */     //   #2502	-> 1222
/*       */     //   #2504	-> 1232
/*       */     //   #2507	-> 1235
/*       */     //   #2508	-> 1242
/*       */     //   #2509	-> 1248
/*       */     //   #18683	-> 1255
/*       */     //   #2510	-> 1258
/*       */     //   #2511	-> 1262
/*       */     //   #2515	-> 1327
/*       */     //   #2468	-> 1338
/*       */     //   #2518	-> 1344
/*       */     //   #2519	-> 1347
/*       */     //   #2521	-> 1350
/*       */     //   #2522	-> 1362
/*       */     //   #2523	-> 1367
/*       */     //   #2451	-> 1381
/*       */     //   #2528	-> 1387
/*       */     //   #2530	-> 1392
/*       */     //   #2532	-> 1406
/*       */     //   #2533	-> 1413
/*       */     //   #2534	-> 1419
/*       */     //   #2536	-> 1428
/*       */     //   #18993	-> 1433
/*       */     //   #18994	-> 1442
/*       */     //   #18995	-> 1452
/*       */     //   #18996	-> 1463
/*       */     //   #18997	-> 1477
/*       */     //   #18998	-> 1495
/*       */     //   #18999	-> 1508
/*       */     //   #19000	-> 1513
/*       */     //   #19002	-> 1534
/*       */     //   #19006	-> 1576
/*       */     //   #19007	-> 1590
/*       */     //   #19008	-> 1595
/*       */     //   #19009	-> 1605
/*       */     //   #19010	-> 1627
/*       */     //   #19012	-> 1633
/*       */     //   #19013	-> 1671
/*       */     //   #19015	-> 1681
/*       */     //   #19016	-> 1689
/*       */     //   #19019	-> 1709
/*       */     //   #19020	-> 1719
/*       */     //   #19019	-> 1722
/*       */     //   #19021	-> 1737
/*       */     //   #19022	-> 1743
/*       */     //   #19023	-> 1750
/*       */     //   #19024	-> 1758
/*       */     //   #19026	-> 1765
/*       */     //   #19027	-> 1773
/*       */     //   #19029	-> 1780
/*       */     //   #19030	-> 1788
/*       */     //   #19032	-> 1795
/*       */     //   #19035	-> 1807
/*       */     //   #19036	-> 1812
/*       */     //   #19035	-> 1815
/*       */     //   #18995	-> 1825
/*       */     //   #19040	-> 1831
/*       */     //   #19042	-> 1840
/*       */     //   #2538	-> 1845
/*       */     //   #2539	-> 1854
/*       */     //   #2538	-> 1857
/*       */     //   #2541	-> 1869
/*       */     //   #2542	-> 1876
/*       */     //   #2543	-> 1881
/*       */     //   #2544	-> 1888
/*       */     //   #2547	-> 1897
/*       */     //   #2549	-> 1906
/*       */     //   #2550	-> 1923
/*       */     //   #19110	-> 1932
/*       */     //   #19111	-> 1938
/*       */     //   #19113	-> 1945
/*       */     //   #19115	-> 1976
/*       */     //   #19116	-> 1979
/*       */     //   #19118	-> 1982
/*       */     //   #19119	-> 1990
/*       */     //   #19120	-> 2001
/*       */     //   #19122	-> 2016
/*       */     //   #19124	-> 2028
/*       */     //   #19125	-> 2060
/*       */     //   #19130	-> 2070
/*       */     //   #19131	-> 2073
/*       */     //   #19132	-> 2076
/*       */     //   #19133	-> 2105
/*       */     //   #19134	-> 2122
/*       */     //   #19135	-> 2147
/*       */     //   #19138	-> 2172
/*       */     //   #19139	-> 2177
/*       */     //   #19141	-> 2184
/*       */     //   #19142	-> 2212
/*       */     //   #19146	-> 2216
/*       */     //   #19147	-> 2219
/*       */     //   #19148	-> 2233
/*       */     //   #19149	-> 2240
/*       */     //   #19150	-> 2254
/*       */     //   #19155	-> 2259
/*       */     //   #19156	-> 2291
/*       */     //   #19161	-> 2359
/*       */     //   #19163	-> 2381
/*       */     //   #19165	-> 2387
/*       */     //   #19166	-> 2395
/*       */     //   #19168	-> 2435
/*       */     //   #19169	-> 2440
/*       */     //   #19172	-> 2448
/*       */     //   #19173	-> 2486
/*       */     //   #19175	-> 2496
/*       */     //   #19176	-> 2504
/*       */     //   #19179	-> 2524
/*       */     //   #19180	-> 2534
/*       */     //   #19179	-> 2537
/*       */     //   #19182	-> 2550
/*       */     //   #19183	-> 2556
/*       */     //   #19184	-> 2563
/*       */     //   #19185	-> 2571
/*       */     //   #19187	-> 2578
/*       */     //   #19188	-> 2586
/*       */     //   #19190	-> 2593
/*       */     //   #19191	-> 2601
/*       */     //   #19193	-> 2608
/*       */     //   #19196	-> 2620
/*       */     //   #19197	-> 2625
/*       */     //   #19198	-> 2640
/*       */     //   #19196	-> 2643
/*       */     //   #19197	-> 2649
/*       */     //   #19147	-> 2662
/*       */     //   #19201	-> 2668
/*       */     //   #19202	-> 2673
/*       */     //   #2553	-> 2681
/*       */     //   #2554	-> 2690
/*       */     //   #2553	-> 2693
/*       */     //   #2559	-> 2705
/*       */     //   #19975	-> 2710
/*       */     //   #19976	-> 2731
/*       */     //   #19978	-> 2734
/*       */     //   #19979	-> 2742
/*       */     //   #19980	-> 2753
/*       */     //   #19982	-> 2780
/*       */     //   #19986	-> 2804
/*       */     //   #19987	-> 2809
/*       */     //   #2565	-> 2814
/*       */     //   #2566	-> 2823
/*       */     //   #2569	-> 2825
/*       */     //   #2571	-> 2846
/*       */     //   #2572	-> 2849
/*       */     //   #2573	-> 2856
/*       */     //   #2574	-> 2861
/*       */     //   #2575	-> 2864
/*       */     //   #2578	-> 2880
/*       */     //   #2580	-> 2894
/*       */     //   #2581	-> 2899
/*       */     //   #2593	-> 2903
/*       */     //   #2584	-> 2904
/*       */     //   #2585	-> 2905
/*       */     //   #2586	-> 2912
/*       */     //   #2587	-> 2918
/*       */     //   #2589	-> 2948
/*       */     //   #2593	-> 2973
/*       */     //   #2594	-> 2977
/*       */     //   #2593	-> 2987
/*       */     //   #2594	-> 2992
/*       */     //   #2597	-> 3003
/*       */     // Exception table:
/*       */     //   from	to	target	type
/*       */     //   10	2903	2904	java/lang/Exception
/*       */     //   10	2903	2987	finally
/*       */     //   1428	1845	1857	finally
/*       */     //   1633	1807	1815	finally
/*       */     //   1681	1709	1722	finally
/*       */     //   1906	2681	2693	finally
/*       */     //   2387	2620	2643	finally
/*       */     //   2496	2524	2537	finally
/*       */     //   2904	2973	2987	finally
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(Canvas paramCanvas, e parame, int paramInt1, int paramInt2) {
/*       */     int n = this.cK.left - parame.e;
/*       */     int i1 = this.cK.top - parame.f;
/*       */     this.ai.set(n, i1, n + this.cK.width(), i1 + this.cK.height());
/*       */     n = this.cI.left + this.cK.left + paramInt1;
/*       */     i1 = this.cI.top + this.cK.top + paramInt2;
/*       */     if (parame.k == 1) {
/*       */       n += parame.g;
/*       */       i1 += parame.h;
/*       */     } 
/*       */     this.aj.set(n, i1, n + this.cK.width(), i1 + this.cK.height());
/*       */     n = 0;
/*       */     Paint paint = this.am;
/*       */     if (parame.k == 1) {
/*       */       if (parame.l) {
/*       */         paint = this.an;
/*       */       }
/*       */       if (parame.d == null) {
/*       */         double d1 = getZoom();
/*       */         n = this.cI.left + parame.e + paramInt1;
/*       */         i1 = this.cI.top + parame.f + paramInt2;
/*       */         this.aj.set(n, i1, n + parame.b, i1 + parame.c);
/*       */         paramCanvas.save();
/*       */         try {
/*       */           paramCanvas.clipRect(this.aj);
/*       */           parame.n.draw(paramCanvas, n, i1, d1, d1, d1, d1);
/*       */         } catch (Exception exception2) {
/*       */           Exception exception1;
/*       */           (exception1 = null).printStackTrace();
/*       */         } finally {
/*       */           paramCanvas.restore();
/*       */         } 
/*       */         n = 1;
/*       */       } 
/*       */     } 
/*       */     if (n == 0) {
/*       */       paramCanvas.drawBitmap(parame.d, this.ai, this.aj, paint);
/*       */     }
/*       */     if (a) {
/*       */       if (parame.k == 0) {
/*       */         String str = String.valueOf(parame.a) + "-t";
/*       */         paramCanvas.drawText(str, this.aj.left, this.aj.bottom, this.av);
/*       */         paramCanvas.drawRect(this.aj, this.ap);
/*       */         paramCanvas.drawLine(this.aj.left, this.aj.top, this.aj.right, this.aj.bottom, this.aq);
/*       */         paramCanvas.drawRect(this.aj, this.aq);
/*       */         return;
/*       */       } 
/*       */       if (parame.k == 1) {
/*       */         Paint paint1 = this.ar;
/*       */         if (n != 0) {
/*       */           paint1 = this.as;
/*       */         }
/*       */         paramCanvas.drawRect(this.aj, paint1);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean a(Canvas paramCanvas, int paramInt, boolean paramBoolean) {
/*       */     boolean bool = false;
/*       */     boolean bool1 = a(this.bV);
/*       */     try {
/*       */       byte b1 = bool1 ? 0 : getScrollOffsetForCanvasId(paramInt);
/*       */       byte b2 = bool1 ? getScrollOffsetForCanvasId(paramInt) : 0;
/*       */       double[] arrayOfDouble;
/*       */       if ((arrayOfDouble = (double[])this.cF.get(paramInt)) != null) {
/*       */         int n = arrayOfDouble.length / 5;
/*       */         int i1 = j.b(this.ce);
/*       */         int i2 = 0;
/*       */         int i3 = 0;
/*       */         if (this.cv) {
/*       */           if (bool1) {
/*       */             if (paramInt == i1) {
/*       */               i2 = j.d(this.ce);
/*       */               i3 = j.e(this.ce) + j.f(this.ce) - b2;
/*       */             } else if (paramInt < i1) {
/*       */               i3 = j.e(this.ce) + j.f(this.ce) - b2;
/*       */             } 
/*       */             if (paramInt < i1) {
/*       */               i2 = Math.max(0, b(arrayOfDouble) - this.aB);
/*       */             }
/*       */             if (paramInt != i1 && (i1 = this.cw.get(paramInt, -2147483648)) != Integer.MIN_VALUE) {
/*       */               i2 = i1;
/*       */             }
/*       */           } else {
/*       */             if (paramInt == i1) {
/*       */               i2 = j.e(this.ce) + j.d(this.ce) - b1;
/*       */               i3 = j.f(this.ce);
/*       */             } else if ((paramInt < i1 && !this.cj) || (paramInt > i1 && this.cj)) {
/*       */               i2 = j.e(this.ce) + j.d(this.ce) - b1;
/*       */             } 
/*       */             if (paramInt < i1) {
/*       */               i3 = Math.max(0, a(arrayOfDouble) - this.aC);
/*       */             }
/*       */             if (paramInt != i1 && (i1 = this.cx.get(paramInt, -2147483648)) != Integer.MIN_VALUE) {
/*       */               i3 = i1;
/*       */             }
/*       */           } 
/*       */         } else if ((j.g(this.ce) || j.h(this.ce) || j.i(this.ce)) && paramInt == i1) {
/*       */           i2 = j.d(this.ce);
/*       */           i3 = j.f(this.ce);
/*       */         } 
/*       */         for (i1 = 0; i1 < n; i1++) {
/*       */           int i4 = i1 * 5;
/*       */           int i5 = (int)arrayOfDouble[i4];
/*       */           boolean bool2 = false;
/*       */           Bitmap bitmap;
/*       */           if (paramBoolean && (bitmap = a(i5, this.cN)) != null) {
/*       */             float f1 = bitmap.getWidth();
/*       */             float f2 = bitmap.getHeight();
/*       */             float f3 = (float)arrayOfDouble[i4 + 1];
/*       */             float f4 = (float)arrayOfDouble[i4 + 2];
/*       */             float f5 = (float)arrayOfDouble[i4 + 3];
/*       */             float f6 = (float)arrayOfDouble[i4 + 4];
/*       */             float f7 = f5 - f3;
/*       */             float f8 = f6 - f4;
/*       */             paramCanvas.save();
/*       */             try {
/*       */               paramCanvas.clipRect(f3 + b1 - (this.cv ? false : i2), f4 + b2 - (this.cv ? false : i3), f5 + b1 - (this.cv ? false : i2), f6 + b2 - (this.cv ? false : i3), Region.Op.INTERSECT);
/*       */               float f9 = f1 - this.cN.left - this.cN.right;
/*       */               float f10 = f2 - this.cN.top - this.cN.bottom;
/*       */               f3 -= this.cN.left * f7 / f9;
/*       */               f5 += this.cN.right * f7 / f9;
/*       */               f4 -= this.cN.top * f8 / f10;
/*       */               f6 += this.cN.bottom * f8 / f10;
/*       */               f7 = f5 - f3;
/*       */               f8 = f6 - f4;
/*       */               f9 = Math.max(i2, f3);
/*       */               f10 = Math.max(i3, f4);
/*       */               f5 = Math.min((i2 + this.aB), f5);
/*       */               f6 = Math.min((i3 + this.aC), f6);
/*       */               float f11 = f1 * (f9 - f3) / f7;
/*       */               float f12 = f2 * (f10 - f4) / f8;
/*       */               f1 *= (f5 - f3) / f7;
/*       */               f2 *= (f6 - f4) / f8;
/*       */               f3 = f9 + b1 - (this.cv ? false : i2);
/*       */               f4 = f10 + b2 - (this.cv ? false : i3);
/*       */               f5 = f5 + b1 - (this.cv ? false : i2);
/*       */               f6 = f6 + b2 - (this.cv ? false : i3);
/*       */               this.ak.set(f11, f12, f1, f2);
/*       */               this.al.set(f3, f4, f5, f6);
/*       */               boolean bool3;
/*       */               if (bool3 = this.ah.setRectToRect(this.ak, this.al, Matrix.ScaleToFit.CENTER)) {
/*       */                 this.am.setFilterBitmap(true);
/*       */                 paramCanvas.drawBitmap(bitmap, this.ah, this.am);
/*       */                 this.am.setFilterBitmap(false);
/*       */                 if (a) {
/*       */                   paramCanvas.drawRect(this.al, this.au);
/*       */                 }
/*       */                 bool2 = true;
/*       */               } 
/*       */             } finally {
/*       */               paramCanvas.restore();
/*       */             } 
/*       */           } 
/*       */           if (!bool2) {
/*       */             float f1 = (float)arrayOfDouble[i4 + 1] + b1 - (this.cv ? false : i2);
/*       */             float f2 = (float)arrayOfDouble[i4 + 2] + b2 - (this.cv ? false : i3);
/*       */             float f3 = (float)arrayOfDouble[i4 + 3] + b1 - (this.cv ? false : i2);
/*       */             float f4 = (float)arrayOfDouble[i4 + 4] + b2 - (this.cv ? false : i3);
/*       */             paramCanvas.drawRect(f1, f2, f3, f4, this.am);
/*       */             if (i5 == this.cD.b) {
/*       */               u.a(this.cD, true);
/*       */               u.a(this.cD, f1, f2, f3, f4);
/*       */               bool = true;
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         if (!this.cv && paramInt == j.b(this.ce) && j.g(this.ce) && !j.c(this.ce)) {
/*       */           if (bool1) {
/*       */             this.cM.set(0, b2 + this.aC, this.aB, b2 + (this.aC << 1));
/*       */           } else {
/*       */             this.cM.set(b1 + this.aB, 0, b1 + (this.aB << 1), this.aC);
/*       */           } 
/*       */           paramCanvas.drawRect(this.cM, this.ao);
/*       */           if (bool1) {
/*       */             this.cM.set(0, b2 - this.aC, this.aB, b2);
/*       */           } else {
/*       */             this.cM.set(b1 - this.aB, 0, b1, this.aC);
/*       */           } 
/*       */           paramCanvas.drawRect(this.cM, this.ao);
/*       */         } 
/*       */       } 
/*       */     } catch (Exception exception) {
/*       */       Log.e("PDFNet", "drawThumbnailForCanvas error for canvas " + paramInt + ": " + exception.toString());
/*       */       bool = false;
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(Canvas paramCanvas, int paramInt) {
/*       */     try {
/*       */       int n = getScrollX();
/*       */       int i1 = getScrollY();
/*       */       this.cG.set(n, i1, n + this.aB, i1 + this.aC);
/*       */       double[] arrayOfDouble;
/*       */       if ((arrayOfDouble = (double[])this.cF.get(paramInt)) == null || arrayOfDouble.length == 0) {
/*       */         return;
/*       */       }
/*       */       i1 = arrayOfDouble.length;
/*       */       boolean bool;
/*       */       byte b1 = (bool = a(this.bV)) ? 0 : getScrollOffsetForCanvasId(paramInt);
/*       */       int i2 = getScrollY();
/*       */       byte b2 = bool ? getScrollOffsetForCanvasId(paramInt) : 0;
/*       */       int i3 = getScrollX();
/*       */       int i4 = j.b(this.ce);
/*       */       int i5 = 0;
/*       */       int i6 = 0;
/*       */       if (this.cv) {
/*       */         if (paramInt == i4) {
/*       */           if (bool) {
/*       */             i5 = j.d(this.ce);
/*       */           } else {
/*       */             i6 = j.f(this.ce);
/*       */           } 
/*       */         } else if (paramInt < i4) {
/*       */           if (bool) {
/*       */             i5 = Math.max(0, b(arrayOfDouble) - this.aB);
/*       */           } else {
/*       */             i6 = Math.max(0, a(arrayOfDouble) - this.aC);
/*       */           } 
/*       */         } 
/*       */         if (paramInt != i4) {
/*       */           i4 = this.cw.get(paramInt, -2147483648);
/*       */           int i7 = this.cx.get(paramInt, -2147483648);
/*       */           if (bool) {
/*       */             if (i4 != Integer.MIN_VALUE) {
/*       */               i5 = i4;
/*       */             }
/*       */           } else if (i7 != Integer.MIN_VALUE) {
/*       */             i6 = i7;
/*       */           } 
/*       */         } 
/*       */       } else if ((j.g(this.ce) || j.h(this.ce) || j.i(this.ce)) && paramInt == i4) {
/*       */         i5 = j.d(this.ce);
/*       */         i6 = j.f(this.ce);
/*       */       } 
/*       */       if (bool) {
/*       */         this.cL.set(i3, b2, i3 + this.aB, b2 + this.aC);
/*       */       } else {
/*       */         this.cL.set(b1, i2, b1 + this.aB, i2 + this.aC);
/*       */       } 
/*       */       for (i4 = 0; i4 < i1; i4 += 5) {
/*       */         int i7 = (int)arrayOfDouble[i4];
/*       */         this.cH.set((int)(arrayOfDouble[i4 + 1] + 0.5D) + this.cL.left - i5, (int)(arrayOfDouble[i4 + 2] + 0.5D) + this.cL.top - i6, (int)arrayOfDouble[i4 + 3] + this.cL.left - i5, (int)arrayOfDouble[i4 + 4] + this.cL.top - i6);
/*       */         this.cI.set(this.cH);
/*       */         boolean bool1;
/*       */         if (bool1 = this.cH.intersect(this.cG)) {
/*       */           this.cJ.set(this.cH.left - this.cI.left, this.cH.top - this.cI.top, this.cH.right - this.cI.left, this.cH.bottom - this.cI.top);
/*       */           Collection<e> collection;
/*       */           for (e e1 : collection = this.l.b(i7, 0)) {
/*       */             this.cK.set(e1.e, e1.f, e1.e + e1.b, e1.f + e1.c);
/*       */             boolean bool2;
/*       */             if (bool2 = this.cK.intersect(this.cJ)) {
/*       */               a(paramCanvas, e1, 0, 0);
/*       */             }
/*       */           } 
/*       */           for (e e1 : collection = this.l.b(i7, 1)) {
/*       */             this.cK.set(e1.e, e1.f, e1.e + e1.b, e1.f + e1.c);
/*       */             boolean bool2;
/*       */             if ((bool2 = this.cK.intersect(this.cJ)) && !this.M.contains(Long.valueOf(e1.a))) {
/*       */               a(paramCanvas, e1, 0, 0);
/*       */             }
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       if (!this.cv && (i5 > 0 || i6 > 0)) {
/*       */         if (bool) {
/*       */           this.cM.set(0, b2 - i6, this.aB, b2);
/*       */         } else {
/*       */           this.cM.set(b1 - i5, 0, b1, this.aC);
/*       */         } 
/*       */         paramCanvas.drawRect(this.cM, this.ao);
/*       */       } 
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       Log.e("PDFNet", "drawFullResTileForCanvas error for canvas " + paramInt + ": " + exception.toString());
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(int paramInt) {
/*       */     this.aQ.clear();
/*       */     paramInt = getScrollY() - paramInt;
/*       */     double[] arrayOfDouble;
/*       */     int n = (arrayOfDouble = a(0.0D, (paramInt - 5 * this.aC), this.aD, (paramInt + 6 * this.aC))).length / 5;
/*       */     byte b1;
/*       */     for (b1 = 0; b1 < n; b1++) {
/*       */       int i1 = b1 * 5;
/*       */       int i2 = (int)arrayOfDouble[i1];
/*       */       float f2 = (float)arrayOfDouble[i1 + 1];
/*       */       float f3 = (float)arrayOfDouble[i1 + 2];
/*       */       float f4 = (float)arrayOfDouble[i1 + 3];
/*       */       float f1 = (float)arrayOfDouble[i1 + 4];
/*       */       this.aQ.put(i2, new RectF(f2, f3, f4, f1));
/*       */     } 
/*       */     for (b1 = 0; b1 < this.aP.size(); b1++) {
/*       */       RectF rectF;
/*       */       float f1 = (rectF = (RectF)this.aP.valueAt(b1)).left + getScrollX();
/*       */       float f2 = rectF.top + getScrollY();
/*       */       rectF.set(f1, f2, rectF.width() + f1, rectF.height() + f2);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void d() {
/*       */     float f1 = (float)getZoom();
/*       */     float f2 = this.aV * f1;
/*       */     float f3 = this.aW * f1;
/*       */     PDFViewCtrl pDFViewCtrl = this;
/*       */     float[] arrayOfFloat2;
/* 20611 */     (arrayOfFloat2 = new float[2])[0] = 0.0F;
/* 20612 */     arrayOfFloat2[1] = 0.0F;
/*       */     
/* 20614 */     float f6 = pDFViewCtrl.getScrollX();
/* 20615 */     float f7 = pDFViewCtrl.getScrollY();
/*       */     
/* 20617 */     if (pDFViewCtrl.n()) {
/* 20618 */       if (a(pDFViewCtrl.bV)) {
/* 20619 */         if (pDFViewCtrl.cv) {
/* 20620 */           pDFViewCtrl.aL = pDFViewCtrl.getScrollOffsetForCanvasId(pDFViewCtrl.getCurCanvasId());
/*       */         }
/* 20622 */         f7 -= pDFViewCtrl.aL;
/*       */       } else {
/* 20624 */         if (pDFViewCtrl.cv) {
/* 20625 */           pDFViewCtrl.aH = pDFViewCtrl.getScrollOffsetForCanvasId(pDFViewCtrl.getCurCanvasId());
/*       */         }
/* 20627 */         f6 -= pDFViewCtrl.aH;
/*       */       } 
/*       */     }
/*       */     
/* 20631 */     if (f6 <= 0.0F || f7 <= 0.0F) {
/*       */       double[] arrayOfDouble;
/* 20633 */       float f9 = (float)(arrayOfDouble = pDFViewCtrl.convCanvasPtToScreenPt(0.0D, 0.0D))[0];
/* 20634 */       float f8 = (float)arrayOfDouble[1];
/*       */       
/* 20636 */       if (f6 <= 0.0F && f9 > 0.0F) {
/* 20637 */         arrayOfFloat2[0] = f9;
/*       */       }
/*       */       
/* 20640 */       if (f7 <= 0.0F && f8 > 0.0F) {
/* 20641 */         arrayOfFloat2[1] = f8;
/*       */       }
/*       */     } 
/*       */     
/* 20645 */     float[] arrayOfFloat1 = arrayOfFloat2;
/*       */     f2 += arrayOfFloat1[0];
/*       */     f3 += arrayOfFloat1[1];
/*       */     float f4 = f1;
/*       */     f1 /= this.aS;
/*       */     float f5 = f2 - f1 * this.aT;
/*       */     f6 = f3 - f1 * this.aU;
/*       */     f2 += (this.aB - this.aT) * f1;
/*       */     f1 = f3 + (this.aC - this.aU) * f1;
/*       */     f3 = 0.0F;
/*       */     f7 = 0.0F;
/*       */     if (this.ba > 1 && isContinuousPagePresentationMode(this.bV)) {
/*       */       double[] arrayOfDouble = convPagePtToCanvasPt(this.aX, this.aY, this.aZ);
/*       */       f3 = (this.aV - (float)arrayOfDouble[0]) * f4;
/*       */       f7 = (this.aW - (float)arrayOfDouble[1]) * f4;
/*       */     } 
/*       */     this.aN.set(getScrollX(), getScrollY(), (getScrollX() + this.aB), (getScrollY() + this.aC));
/*       */     this.aO.set(f5 - f3, f6 - f7, f2 - f3, f1 - f7);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
/*       */     boolean bool = false;
/*       */     if (this.bS != null) {
/*       */       bool = this.bS.onKeyUp(paramInt, paramKeyEvent);
/*       */     }
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onDoubleTap(MotionEvent paramMotionEvent) {
/*       */     boolean bool = this.bX;
/*       */     if (Build.VERSION.SDK_INT >= 19) {
/*       */       bool = this.db.isQuickScaleEnabled();
/*       */     }
/*       */     if (bool) {
/*       */       this.bt = new PointF(paramMotionEvent.getX(), paramMotionEvent.getY());
/*       */     } else {
/*       */       bool = handleDoubleTap(paramMotionEvent);
/*       */       if (this.bS != null) {
/*       */         this.bS.onDoubleTapEnd(paramMotionEvent);
/*       */       }
/*       */       return bool;
/*       */     } 
/*       */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean handleDoubleTap(MotionEvent paramMotionEvent) {
/*       */     this.U = true;
/*       */     boolean bool = false;
/*       */     if (this.bS != null) {
/*       */       bool = this.bS.onDoubleTap(paramMotionEvent);
/*       */     }
/*       */     if (!bool && this.mDoc != null) {
/*       */       cancelRendering();
/*       */       double d1 = getZoom();
/*       */       double d2 = j();
/*       */       if (Math.abs(d1 - d2) > 0.009999999776482582D) {
/*       */         a(this.bT, true);
/*       */       } else {
/*       */         d2 = b(d1 * 2.0D);
/*       */         a(paramMotionEvent.getX(), paramMotionEvent.getY(), d2);
/*       */       } 
/*       */     } 
/*       */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onDoubleTapEvent(MotionEvent paramMotionEvent) {
/*       */     boolean bool = false;
/*       */     if (this.bS != null) {
/*       */       bool = this.bS.onDoubleTapEvent(paramMotionEvent);
/*       */     }
/*       */     if (!bool) {
/*       */       boolean bool1;
/*       */       int n;
/*       */       switch (n = paramMotionEvent.getActionMasked()) {
/*       */         case 0:
/*       */           this.V = true;
/*       */           break;
/*       */         case 1:
/*       */           this.V = false;
/*       */           bool1 = this.bX;
/*       */           if (Build.VERSION.SDK_INT >= 19) {
/*       */             bool1 = this.db.isQuickScaleEnabled();
/*       */           }
/*       */           if (!this.W && bool1) {
/*       */             bool = handleDoubleTap(paramMotionEvent);
/*       */             if (this.bS != null) {
/*       */               this.bS.onDoubleTapEnd(paramMotionEvent);
/*       */             }
/*       */           } 
/*       */           break;
/*       */       } 
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onDown(MotionEvent paramMotionEvent) {
/*       */     this.cr = n.d;
/*       */     this.cu = 1.0F;
/*       */     this.ct = 0.0F;
/*       */     this.cs = 0.0F;
/*       */     this.ad = false;
/*       */     if (!this.f.isFinished()) {
/*       */       this.ad = true;
/*       */       this.f.forceFinished(true);
/*       */     } 
/*       */     if (!j.a(this.ce) && this.s && this.mDoc != null) {
/*       */       int n = getScrollX();
/*       */       int i1 = getScrollY();
/*       */       if (a(this.bV)) {
/*       */         if (this.cv) {
/*       */           this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         if (i1 >= this.aL && n()) {
/*       */           i1 -= this.aL;
/*       */         }
/*       */       } else {
/*       */         if (this.cv) {
/*       */           this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         if (n >= this.aH && n()) {
/*       */           n -= this.aH;
/*       */         }
/*       */       } 
/*       */       n -= p();
/*       */       i1 -= q();
/*       */       if (n != 0 || i1 != 0) {
/*       */         scrollBy(n, i1);
/*       */       }
/*       */     } 
/*       */     this.bs = false;
/*       */     a(paramMotionEvent.getX(), paramMotionEvent.getY());
/*       */     if (this.bS != null) {
/*       */       this.bS.onDown(paramMotionEvent);
/*       */     }
/*       */     if (this.mDoc != null && !isContinuousPagePresentationMode(this.bV)) {
/*       */       OverScroller overScroller;
/*       */       if (!(overScroller = g()).isFinished()) {
/*       */         overScroller.abortAnimation();
/*       */       }
/*       */       this.ce.a(paramMotionEvent);
/*       */     } 
/*       */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
/*       */     if (this.i.b() == 1) {
/*       */       return true;
/*       */     }
/*       */     if (j.a(this.ce) && this.mDoc != null) {
/*       */       return this.ce.a(paramFloat1, paramFloat2);
/*       */     }
/*       */     boolean bool;
/*       */     if (this.bS != null && (bool = this.bS.onUp(paramMotionEvent2, PriorEventMode.FLING))) {
/*       */       return true;
/*       */     }
/*       */     if (this.mDoc != null) {
/*       */       if (this.ca && !isContinuousPagePresentationMode(this.bV) && o()) {
/*       */         return true;
/*       */       }
/*       */       int n = this.aD - this.aB;
/*       */       int i1 = this.aE - this.aC;
/*       */       if (Math.abs(paramFloat1) < Math.abs(paramFloat2) * 1.5D) {
/*       */         paramFloat1 = 0.0F;
/*       */       }
/*       */       paramFloat1 = (float)(paramFloat1 * 0.75D);
/*       */       paramFloat2 = (float)(paramFloat2 * 0.75D);
/*       */       int i2 = getScrollX();
/*       */       int i3 = getScrollY();
/*       */       if (a(this.bV)) {
/*       */         if (this.cv) {
/*       */           this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         if (i3 >= this.aL && n()) {
/*       */           i3 -= this.aL;
/*       */         }
/*       */       } else {
/*       */         if (this.cv) {
/*       */           this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         if (i2 >= this.aH && n()) {
/*       */           i2 -= this.aH;
/*       */         }
/*       */       } 
/*       */       this.f.fling(i2, i3, -((int)paramFloat1), -((int)paramFloat2), 0, n, 0, i1);
/*       */       if (this.s) {
/*       */         n = this.f.getFinalX() - this.f.getStartX();
/*       */         i1 = this.f.getFinalY() - this.f.getStartY();
/*       */         try {
/*       */           OnScroll(this.cY, n, i1, false);
/*       */         } catch (Exception exception) {}
/*       */         requestRendering();
/*       */       } 
/*       */       invalidate();
/*       */     } 
/*       */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onFlingStop() {
/*       */     if (!j.a(this.ce)) {
/*       */       this.cD.b();
/*       */     }
/*       */     if (this.bS != null) {
/*       */       this.bS.onFlingStop();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onLongPress(MotionEvent paramMotionEvent) {
/*       */     if (j.a(this.ce) || !this.bW) {
/*       */       return;
/*       */     }
/*       */     if (this.W || this.V) {
/*       */       return;
/*       */     }
/*       */     if (this.bS != null) {
/*       */       this.bS.onLongPress(paramMotionEvent);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean e() {
/*       */     return (this.cr == n.c || this.cr == n.b);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean f() {
/*       */     return (this.o && !e());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector) {
/*       */     this.bs = false;
/*       */     if (!this.ck) {
/*       */       return false;
/*       */     }
/*       */     int n = 0;
/*       */     if (this.ca && !isContinuousPagePresentationMode(this.bV) && j.a(this.ce) && this.mDoc != null) {
/*       */       n = 1;
/*       */       if ((!this.cB || this.bS == null || !this.bS.isCreatingAnnotation()) && Math.abs(j.e(this.ce)) < j.j(this.ce)) {
/*       */         if (!j.c(this.ce)) {
/*       */           n = getPageNumberFromScreenPt(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
/*       */           b(n);
/*       */           j.a(this.ce, true);
/*       */           n = 0;
/*       */         } 
/*       */       }
/*       */     } 
/*       */     if (n != 0) {
/*       */       return false;
/*       */     }
/*       */     this.cD.d();
/*       */     boolean bool;
/*       */     if (this.bS != null && (bool = this.bS.onScaleBegin(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY()))) {
/*       */       this.ab = true;
/*       */       return true;
/*       */     } 
/*       */     if (this.mDoc != null) {
/*       */       cancelRenderingAsync();
/*       */       this.p = 1;
/*       */       this.q = getPageCount();
/*       */       int i1 = GetCurCanvasId(this.cY);
/*       */       double[] arrayOfDouble;
/*       */       if (!isContinuousPagePresentationMode(this.bV) && (arrayOfDouble = GetPageRectsOnCanvas(this.cY, i1)).length > 0) {
/*       */         this.p = (int)arrayOfDouble[0];
/*       */         this.q = (int)arrayOfDouble[arrayOfDouble.length - 5];
/*       */       } 
/*       */       this.o = true;
/*       */       b(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
/*       */       this.bu = -1.0F;
/*       */       this.aa = false;
/*       */       this.be = -1.0F;
/*       */       this.bf = -1.0F;
/*       */       this.dj.removeMessages(0);
/*       */       this.dj.sendEmptyMessageDelayed(0, 1000L);
/*       */     } 
/*       */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onScale(ScaleGestureDetector paramScaleGestureDetector) {
/*       */     if (!this.ck) {
/*       */       return true;
/*       */     }
/*       */     if (this.i.b() == 1) {
/*       */       return true;
/*       */     }
/*       */     int n = 0;
/*       */     if (!j.k(this.ce) && this.ca && !isContinuousPagePresentationMode(this.bV) && j.a(this.ce) && this.mDoc != null) {
/*       */       n = 1;
/*       */       if (Math.abs(j.e(this.ce)) < j.j(this.ce)) {
/*       */         if (!j.c(this.ce)) {
/*       */           n = getPageNumberFromScreenPt(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
/*       */           b(n);
/*       */           j.a(this.ce, true);
/*       */           n = 0;
/*       */         } 
/*       */       }
/*       */     } 
/*       */     if (n != 0) {
/*       */       return true;
/*       */     }
/*       */     if (!this.ab && this.mDoc != null) {
/*       */       this.bs = true;
/*       */       this.bc = paramScaleGestureDetector.getFocusX();
/*       */       this.bd = paramScaleGestureDetector.getFocusY();
/*       */       if (this.be < 0.0F) {
/*       */         this.be = this.bc;
/*       */       }
/*       */       if (this.bf < 0.0F) {
/*       */         this.bf = this.bd;
/*       */       }
/*       */       float f1 = -this.bc + this.be;
/*       */       float f2 = -this.bd + this.bf;
/*       */       this.be = this.bc;
/*       */       this.bf = this.bd;
/*       */       float f3 = paramScaleGestureDetector.getScaleFactor();
/*       */       if (this.bu < 0.0F) {
/*       */         this.bu = f3;
/*       */       }
/*       */       if (this.bS != null && this.bS.isCreatingAnnotation() && this.cr == n.d) {
/*       */         this.cu *= f3;
/*       */         if (this.cu > 2.0F || this.cu < 0.5F || f3 > 1.25F || f3 < 0.8F) {
/*       */           this.cr = n.a;
/*       */         } else if (paramScaleGestureDetector.getCurrentSpan() < 1.1D * paramScaleGestureDetector.getPreviousSpan() && paramScaleGestureDetector.getCurrentSpan() > 0.9090909F * paramScaleGestureDetector.getPreviousSpan()) {
/*       */           DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
/*       */           float f4 = 32.0F * displayMetrics.densityDpi / 160.0F;
/*       */           this.ct += f1;
/*       */           this.cs += f2;
/*       */           if (Math.abs(this.ct) > f4 && Math.abs(this.ct) > Math.abs(this.cs)) {
/*       */             this.cr = n.c;
/*       */           } else if (Math.abs(this.cs) > f4) {
/*       */             this.cr = n.b;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       if (e()) {
/*       */         if (this.cr != n.c) {
/*       */           f1 = 0.0F;
/*       */         }
/*       */         if (this.cr != n.b) {
/*       */           f2 = 0.0F;
/*       */         }
/*       */         scrollBy((int)f1, (int)f2);
/*       */         return true;
/*       */       } 
/*       */       scrollBy((int)f1, (int)f2);
/*       */       f1 = f3;
/*       */       if (this.V) {
/*       */         if (this.bv && this.bw) {
/*       */           f1 = this.bu;
/*       */           if (a) {
/*       */             Log.d(c, "scale: HIT MIN AND ABOVE");
/*       */           }
/*       */         } 
/*       */         if (!this.bv && this.bx) {
/*       */           f1 = this.bu;
/*       */           if (a) {
/*       */             Log.d(c, "scale: HIT MAX AND BELOW");
/*       */           }
/*       */         } 
/*       */         this.bu = f1;
/*       */       } 
/*       */       if (this.V) {
/*       */         this.aa = true;
/*       */         f1 = (float)b((this.bb * f1));
/*       */       } else {
/*       */         f1 = (float)b((this.bb * f3));
/*       */       } 
/*       */       if (Math.abs(f1 - getZoom()) >= 0.01D) {
/*       */         if (this.V) {
/*       */           setZoom(f1);
/*       */         } else {
/*       */           setZoom((int)(this.bc + 0.5D), (int)(this.bd + 0.5D), f1);
/*       */         } 
/*       */         this.dj.removeMessages(0);
/*       */         this.dj.sendEmptyMessageDelayed(0, 1000L);
/*       */         invalidate();
/*       */       } 
/*       */     } 
/*       */     return (this.bS != null && this.bS.onScale(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY()));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector) {
/*       */     this.o = false;
/*       */     if (e()) {
/*       */       return;
/*       */     }
/*       */     c();
/*       */     if (!this.ck) {
/*       */       return;
/*       */     }
/*       */     int n = 0;
/*       */     if (!j.k(this.ce) && this.ca && !isContinuousPagePresentationMode(this.bV) && j.a(this.ce) && this.mDoc != null) {
/*       */       n = 1;
/*       */       if (Math.abs(j.e(this.ce)) < j.j(this.ce)) {
/*       */         if (!j.c(this.ce)) {
/*       */           n = getPageNumberFromScreenPt(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
/*       */           b(n);
/*       */           j.a(this.ce, true);
/*       */           n = 0;
/*       */         } 
/*       */       }
/*       */     } 
/*       */     if (n != 0) {
/*       */       return;
/*       */     }
/*       */     if (!this.ab && this.mDoc != null) {
/*       */       this.bc = paramScaleGestureDetector.getFocusX();
/*       */       this.bd = paramScaleGestureDetector.getFocusY();
/*       */       float f1 = paramScaleGestureDetector.getScaleFactor();
/*       */       if (this.aa) {
/*       */         f1 = (float)b((this.bb * this.bu));
/*       */       } else {
/*       */         f1 = (float)b((this.bb * f1));
/*       */       } 
/*       */       this.dj.removeMessages(0);
/*       */       if (this.aa) {
/*       */         setZoom(f1);
/*       */       } else {
/*       */         setZoom((int)this.bc, (int)this.bd, f1);
/*       */       } 
/*       */     } 
/*       */     j.a(this.ce, false);
/*       */     this.bt = null;
/*       */     if (this.bS != null && !e()) {
/*       */       this.bS.onScaleEnd(paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
/*       */       invalidate();
/*       */     } 
/*       */     invalidate();
/*       */     this.cD.c();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onSingleTapConfirmed(MotionEvent paramMotionEvent) {
/*       */     if (!this.ad && this.bS != null) {
/*       */       this.bS.onSingleTapConfirmed(paramMotionEvent);
/*       */       this.dk.sendEmptyMessageDelayed(0, 200L);
/*       */     } 
/*       */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
/*       */     if (this.i.b() == 1) {
/*       */       return true;
/*       */     }
/*       */     if (j.k(this.ce)) {
/*       */       return true;
/*       */     }
/*       */     if (this.bS != null && this.bS.isCreatingAnnotation() && paramMotionEvent2.getPointerCount() == 2 && !this.o) {
/*       */       if (this.cr == n.d) {
/*       */         DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
/*       */         float f2 = 16.0F * displayMetrics.densityDpi / 160.0F;
/*       */         this.ct += paramFloat1;
/*       */         this.cs += paramFloat2;
/*       */         float f3 = paramMotionEvent2.getX(0) - this.cQ;
/*       */         float f1 = paramMotionEvent2.getX(1) - this.cR;
/*       */         float f4 = paramMotionEvent2.getY(0) - this.cS;
/*       */         float f5 = paramMotionEvent2.getY(1) - this.cT;
/*       */         this.cQ = paramMotionEvent2.getX(0);
/*       */         this.cR = paramMotionEvent2.getX(1);
/*       */         this.cS = paramMotionEvent2.getY(0);
/*       */         this.cT = paramMotionEvent2.getY(1);
/*       */         if ((f3 <= 0.0F && f1 >= 0.0F) || (f3 >= 0.0F && f1 <= 0.0F)) {
/*       */           this.ct = 0.0F;
/*       */         }
/*       */         if ((f4 <= 0.0F && f5 >= 0.0F) || (f4 >= 0.0F && f5 <= 0.0F)) {
/*       */           this.cs = 0.0F;
/*       */         }
/*       */         if (Math.abs(this.ct) > f2 && Math.abs(this.ct) > Math.abs(this.cs)) {
/*       */           this.cr = n.c;
/*       */           paramFloat1 = this.ct;
/*       */         } else if (Math.abs(this.cs) > f2) {
/*       */           this.cr = n.b;
/*       */           paramFloat2 = this.cs;
/*       */         } 
/*       */       } 
/*       */       if (!j.a(this.ce) && e()) {
/*       */         if (this.cr != n.c) {
/*       */           paramFloat1 = 0.0F;
/*       */         }
/*       */         if (this.cr != n.b) {
/*       */           paramFloat2 = 0.0F;
/*       */         }
/*       */         scrollBy((int)paramFloat1, (int)paramFloat2);
/*       */       } 
/*       */     } 
/*       */     boolean bool;
/*       */     if (this.bS != null && (bool = this.bS.onMove(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2))) {
/*       */       return true;
/*       */     }
/*       */     if (this.mDoc != null) {
/*       */       if (this.ca && !isContinuousPagePresentationMode(this.bV)) {
/*       */         if (!j.a(this.ce)) {
/*       */           j.c(this.ce, (j.g(this.ce) || this.cv) ? getScrollX() : 0);
/*       */           j.d(this.ce, getScrollY());
/*       */         } 
/*       */         this.ce.b(paramMotionEvent2);
/*       */         if (o()) {
/*       */           return true;
/*       */         }
/*       */       } 
/*       */       if (this.bs) {
/*       */         a(paramMotionEvent2.getX(), paramMotionEvent2.getY());
/*       */         this.bs = false;
/*       */       } 
/*       */       if (j.a(this.ce) && this.bS != null && this.bS.isCreatingAnnotation() && paramMotionEvent2.getPointerCount() == 2) {
/*       */         return true;
/*       */       }
/*       */       j.b(this.ce, false);
/*       */       float f1 = paramMotionEvent2.getX();
/*       */       float f2 = paramMotionEvent2.getY();
/*       */       double[] arrayOfDouble;
/*       */       f1 = (float)(arrayOfDouble = convPagePtToScreenPt(this.bq, this.br, this.bp))[0] - f1;
/*       */       float f3 = (float)arrayOfDouble[1] - f2;
/*       */       if (this.cp) {
/*       */         if (this.cq == o.d) {
/*       */           if (Math.abs(paramFloat2) > Math.abs(paramFloat1) * 1.4F) {
/*       */             this.cq = o.a;
/*       */           } else if (Math.abs(paramFloat1) > Math.abs(paramFloat2) * 1.4F) {
/*       */             this.cq = o.b;
/*       */           } else {
/*       */             this.cq = o.c;
/*       */           } 
/*       */         }
/*       */         switch (null.a[this.cq - 1]) {
/*       */           case 1:
/*       */             f1 = 0.0F;
/*       */             break;
/*       */           case 2:
/*       */             f3 = 0.0F;
/*       */             break;
/*       */         } 
/*       */       } 
/*       */       scrollBy((int)f1, (int)f3);
/*       */     } 
/*       */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*       */     if (this.bS != null) {
/*       */       this.bS.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void onShowPress(MotionEvent paramMotionEvent) {
/*       */     if (this.bS != null) {
/*       */       this.bS.onShowPress(paramMotionEvent);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onSingleTapUp(MotionEvent paramMotionEvent) {
/*       */     if (this.bS != null) {
/*       */       this.bS.onSingleTapUp(paramMotionEvent);
/*       */     }
/*       */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean onUp(MotionEvent paramMotionEvent, PriorEventMode paramPriorEventMode) {
/*       */     boolean bool = false;
/*       */     if (j.a(this.ce) && this.mDoc != null) {
/*       */       bool = this.ce.a(0.0F, 0.0F);
/*       */       paramPriorEventMode = PriorEventMode.PAGE_SLIDING;
/*       */     } 
/*       */     if (this.bS != null) {
/*       */       bool = this.bS.onUp(paramMotionEvent, paramPriorEventMode);
/*       */     }
/*       */     if (!bool && paramPriorEventMode == PriorEventMode.SCROLLING) {
/*       */       requestRendering();
/*       */     }
/*       */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected int computeVerticalScrollRange() {
/*       */     if (this.cv) {
/*       */       return this.aJ;
/*       */     }
/*       */     if (this.ca && !isContinuousPagePresentationMode(this.bV)) {
/*       */       if (a(this.bV)) {
/*       */         if (this.aC == this.aE || j.a(this.ce)) {
/*       */           return this.aK;
/*       */         }
/*       */       } else if (j.a(this.ce)) {
/*       */         return this.aC;
/*       */       } 
/*       */     }
/*       */     return this.aE;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected int computeHorizontalScrollRange() {
/*       */     if (this.cv) {
/*       */       return this.aF;
/*       */     }
/*       */     if (this.ca && !isContinuousPagePresentationMode(this.bV)) {
/*       */       if (this.aB == this.aD || j.a(this.ce)) {
/*       */         return this.aG;
/*       */       }
/*       */     }
/*       */     return this.aD;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void computeScroll() {
/*       */     if (this.g.computeScrollOffset() || this.h.computeScrollOffset()) {
/*       */       OverScroller overScroller;
/*       */       if ((overScroller = g()) == this.g) {
/*       */         int n = this.g.getCurrX() + this.aB / 2;
/*       */         PDFViewCtrl pDFViewCtrl;
/* 21320 */         j.e(this.ce, (pDFViewCtrl = this).f(n));
/*       */       } else if (overScroller == this.h) {
/*       */         int n = this.h.getCurrY() + this.aC / 2;
/*       */ 
/*       */ 
/*       */         
/*       */         PDFViewCtrl pDFViewCtrl;
/*       */ 
/*       */         
/* 21329 */         j.e(this.ce, (pDFViewCtrl = this).f(n));
/*       */       } 
/*       */       if (a(this.bV)) {
/*       */         if (this.aE > this.aC) {
/*       */           if (j.l(this.ce)) {
/*       */             j.e(this.ce, j.f(this.ce, j.m(this.ce)));
/*       */           } else {
/*       */             j.e(this.ce, j.g(this.ce, j.m(this.ce)));
/*       */           } 
/*       */         }
/*       */       } else if (this.aD > this.aB) {
/*       */         if (j.n(this.ce)) {
/*       */           j.e(this.ce, j.f(this.ce, j.m(this.ce)));
/*       */         } else {
/*       */           j.e(this.ce, j.g(this.ce, j.m(this.ce)));
/*       */         } 
/*       */       } 
/*       */       j.e(this.ce, Math.max(1, Math.min(j.m(this.ce), getPageCount())));
/*       */       if (j.c(this.ce)) {
/*       */         if (!j.o(this.ce) || j.m(this.ce) != j.b(this.ce)) {
/*       */           if (j.p(this.ce)) {
/*       */             SetCurrentPage(this.cY, j.m(this.ce));
/*       */             requestRendering();
/*       */           } 
/*       */         }
/*       */       }
/*       */       super.scrollTo(overScroller.getCurrX(), overScroller.getCurrY());
/*       */       h();
/*       */       return;
/*       */     } 
/*       */     if (j.q(this.ce) != -1) {
/*       */       b(j.r(this.ce));
/*       */     }
/*       */     boolean bool = true;
/*       */     if (!this.s) {
/*       */       if (this.f.computeScrollOffset()) {
/*       */         bool = false;
/*       */         int n = this.f.getCurrX() - getScrollX();
/*       */         int i1 = this.f.getCurrY() - getScrollY();
/*       */         if (n != 0 || i1 != 0) {
/*       */           scrollBy(n, i1);
/*       */         }
/*       */       } 
/*       */     } else if (this.f.computeScrollOffset()) {
/*       */       bool = false;
/*       */       int n = 0;
/*       */       int i1 = 0;
/*       */       if (n()) {
/*       */         if (a(this.bV)) {
/*       */           if (this.cv) {
/*       */             this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */           }
/*       */           i1 = this.aL;
/*       */         } else {
/*       */           if (this.cv) {
/*       */             this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */           }
/*       */           n = this.aH;
/*       */         } 
/*       */       }
/*       */       super.scrollTo(this.f.getCurrX() + n, this.f.getCurrY() + i1);
/*       */       h();
/*       */     } 
/*       */     if (this.R && bool) {
/*       */       this.R = false;
/*       */       onFlingStop();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private OverScroller g() {
/*       */     if (a(this.bV)) {
/*       */       return this.h;
/*       */     }
/*       */     return this.g;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static boolean a(PagePresentationMode paramPagePresentationMode) {
/*       */     return (paramPagePresentationMode == PagePresentationMode.SINGLE_VERT || paramPagePresentationMode == PagePresentationMode.FACING_VERT || paramPagePresentationMode == PagePresentationMode.FACING_COVER_VERT);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void b(int paramInt) {
/*       */     setCurrentPage(paramInt);
/*       */     j.e(this.ce, paramInt);
/*       */     j.h(this.ce, -1);
/*       */     j.s(this.ce);
/*       */     if (this.cv) {
/*       */       if (a(this.bV)) {
/*       */         paramInt = this.aE - this.aC;
/*       */         double[] arrayOfDouble = e(j.r(this.ce));
/*       */         int n = Math.max(0, b(arrayOfDouble) - this.aB);
/*       */         if (j.t(this.ce) > j.r(this.ce)) {
/*       */           scrollTo(n, paramInt);
/*       */         } else if (j.t(this.ce) < j.r(this.ce)) {
/*       */           scrollTo(0, 0);
/*       */         } 
/*       */         paramInt = this.cw.get(j.r(this.ce), -2147483648);
/*       */         if (j.t(this.ce) != j.r(this.ce)) {
/*       */           if (paramInt != Integer.MIN_VALUE) {
/*       */             scrollTo(paramInt, q());
/*       */           } else {
/*       */             return;
/*       */           } 
/*       */         } else if (j.u(this.ce)) {
/*       */           j.c(this.ce, false);
/*       */           if (paramInt != Integer.MIN_VALUE) {
/*       */             scrollTo(paramInt, j.v(this.ce) - getScrollOffsetForCanvasId(j.t(this.ce)));
/*       */           } else {
/*       */             paramInt = h(j.t(this.ce));
/*       */             if (j.w(this.ce) < paramInt) {
/*       */               n = 0;
/*       */             }
/*       */             scrollTo(n, j.v(this.ce) - getScrollOffsetForCanvasId(j.t(this.ce)));
/*       */             return;
/*       */           } 
/*       */         } else {
/*       */           return;
/*       */         } 
/*       */       } else {
/*       */         paramInt = this.aD - this.aB;
/*       */         double[] arrayOfDouble = e(j.r(this.ce));
/*       */         int n = Math.max(0, a(arrayOfDouble) - this.aC);
/*       */         if (this.cj) {
/*       */           if (j.t(this.ce) > j.r(this.ce)) {
/*       */             scrollTo(0, n);
/*       */           } else if (j.t(this.ce) < j.r(this.ce)) {
/*       */             scrollTo(paramInt, 0);
/*       */           } 
/*       */         } else if (j.t(this.ce) > j.r(this.ce)) {
/*       */           scrollTo(paramInt, n);
/*       */         } else if (j.t(this.ce) < j.r(this.ce)) {
/*       */           scrollTo(0, 0);
/*       */         } 
/*       */         paramInt = this.cx.get(j.r(this.ce), -2147483648);
/*       */         if (j.t(this.ce) != j.r(this.ce)) {
/*       */           if (paramInt != Integer.MIN_VALUE) {
/*       */             scrollTo(p(), paramInt);
/*       */             return;
/*       */           } 
/*       */         } else if (j.u(this.ce)) {
/*       */           j.c(this.ce, false);
/*       */           if (paramInt != Integer.MIN_VALUE) {
/*       */             scrollTo(j.x(this.ce) - getScrollOffsetForCanvasId(j.t(this.ce)), paramInt);
/*       */             return;
/*       */           } 
/*       */           paramInt = h(j.t(this.ce));
/*       */           if (j.w(this.ce) < paramInt) {
/*       */             n = 0;
/*       */           }
/*       */           scrollTo(j.x(this.ce) - getScrollOffsetForCanvasId(j.t(this.ce)), n);
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public double getZoom() {
/*       */     double d1;
/*       */     return d1 = GetZoom(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean setZoom(double paramDouble) {
/*       */     paramDouble = b(paramDouble);
/*       */     boolean bool = SetZoom(this.cY, paramDouble, false);
/*       */     c();
/*       */     scrollTo(p(), q());
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean setZoom(double paramDouble, boolean paramBoolean) {
/*       */     if (!paramBoolean) {
/*       */       return setZoom(paramDouble);
/*       */     }
/*       */     return a(this.aB / 2.0F, this.aC / 2.0F, paramDouble);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean setZoom(int paramInt1, int paramInt2, double paramDouble) {
/*       */     paramDouble = b(paramDouble);
/*       */     boolean bool = SetZoom(this.cY, paramInt1, paramInt2, paramDouble, false);
/*       */     c();
/*       */     scrollTo(p(), q());
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean setZoom(int paramInt1, int paramInt2, double paramDouble, boolean paramBoolean) {
/*       */     if (!paramBoolean) {
/*       */       return setZoom(paramInt1, paramInt2, paramDouble);
/*       */     }
/*       */     return a(paramInt1, paramInt2, paramDouble);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean setZoom(int paramInt1, int paramInt2, double paramDouble, boolean paramBoolean1, boolean paramBoolean2) {
/*       */     if (!paramBoolean2) {
/*       */       return setZoom(paramInt1, paramInt2, paramDouble, paramBoolean1);
/*       */     }
/*       */     this.i.a(true);
/*       */     if (this.cv) {
/*       */       if (a(this.bV)) {
/*       */         this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } else {
/*       */         this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } 
/*       */     }
/*       */     d(this.aL);
/*       */     c(paramInt1, paramInt2);
/*       */     b(paramInt1, paramInt2);
/*       */     boolean bool;
/*       */     if (bool = setZoom(paramInt1, paramInt2, paramDouble, paramBoolean1)) {
/*       */       m();
/*       */       a(this.aL);
/*       */       d();
/*       */       boolean bool1;
/*       */       if (!(bool1 = isContinuousPagePresentationMode(this.bV))) {
/*       */         this.aO.left += this.aH;
/*       */         this.aO.right += this.aH;
/*       */         this.aO.top += this.aL;
/*       */         this.aO.bottom += this.aL;
/*       */         for (bool1 = false; bool1 < this.aQ.size(); bool1++) {
/*       */           RectF rectF;
/*       */           rectF.left += this.aH;
/*       */           rectF.right += this.aH;
/*       */           rectF.top += this.aL;
/*       */           rectF.bottom += this.aL;
/*       */         } 
/*       */       } 
/*       */       if (this.bS != null) {
/*       */         this.bS.onDoubleTapZoomAnimationBegin();
/*       */       }
/*       */       this.i.a(this.aN, this.aO, this.aP, this.aQ);
/*       */       this.u = true;
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setZoomLimits(ZoomLimitMode paramZoomLimitMode, double paramDouble1, double paramDouble2) throws PDFNetException {
/*       */     this.bj = paramZoomLimitMode;
/*       */     this.bU = PageViewMode.NOT_DEFINED;
/*       */     if (paramDouble1 < 0.0D) {
/*       */       paramDouble1 = 0.0D;
/*       */     }
/*       */     if (paramDouble1 > paramDouble2) {
/*       */       paramDouble2 = paramDouble1;
/*       */     }
/*       */     if (this.bj == ZoomLimitMode.RELATIVE) {
/*       */       if (paramDouble1 > 1.0D) {
/*       */         paramDouble1 = 1.0D;
/*       */       }
/*       */       if (paramDouble2 < 1.0D) {
/*       */         paramDouble2 = 1.0D;
/*       */       }
/*       */     } 
/*       */     this.bh = paramDouble1;
/*       */     this.bi = paramDouble2;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMinimumRefZoomForMaximumZoomLimit(double paramDouble) {
/*       */     this.bm = paramDouble;
/*       */     this.bl = a(this.bk);
/*       */     this.bo = a(this.bo);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setRelativeZoomLimits(PageViewMode paramPageViewMode, double paramDouble1, double paramDouble2) throws PDFNetException {
/*       */     if (paramPageViewMode == PageViewMode.FIT_PAGE || paramPageViewMode == PageViewMode.FIT_WIDTH || paramPageViewMode == PageViewMode.FIT_HEIGHT) {
/*       */       this.bU = paramPageViewMode;
/*       */       this.bj = ZoomLimitMode.RELATIVE;
/*       */       if (paramDouble1 > 1.0D) {
/*       */         paramDouble1 = 1.0D;
/*       */       }
/*       */       if (paramDouble2 < 1.0D) {
/*       */         paramDouble2 = 1.0D;
/*       */       }
/*       */       this.bh = paramDouble1;
/*       */       this.bi = paramDouble2;
/*       */       this.bk = j();
/*       */       this.bl = a(this.bk);
/*       */       this.bn = k();
/*       */       this.bo = a(this.bn);
/*       */       return;
/*       */     } 
/*       */     Log.e("PDFNet", "ref_view_mode must be one of PageViewMode.FIT_PAGE, PageViewMode.FIT_WIDTH, or PageViewMode.FIT_HEIGHT");
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public double getZoomForViewMode(PageViewMode paramPageViewMode) throws PDFNetException {
/*       */     if (paramPageViewMode == PageViewMode.FIT_PAGE || paramPageViewMode == PageViewMode.FIT_WIDTH || paramPageViewMode == PageViewMode.FIT_HEIGHT) {
/*       */       return GetRefZoom(this.cY, paramPageViewMode.getValue());
/*       */     }
/*       */     throw new PDFNetException("", 0L, "PDFViewCtrl", "getZoomForViewMode", "viewMode must be one of PageViewMode.FIT_PAGE, PageViewMode.FIT_WIDTH, or PageViewMode.FIT_HEIGHT");
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean smartZoom(int paramInt1, int paramInt2) {
/*       */     cancelRendering();
/*       */     boolean bool = false;
/*       */     try {
/*       */       if (bool = SmartZoom(this.cY, paramInt1, paramInt2)) {
/*       */         c();
/*       */         scrollTo(p(), q());
/*       */       } else {
/*       */         requestRendering();
/*       */       } 
/*       */     } catch (Exception exception) {
/*       */       requestRendering();
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean smartZoom(int paramInt1, int paramInt2, boolean paramBoolean) {
/*       */     if (!paramBoolean) {
/*       */       return smartZoom(paramInt1, paramInt2);
/*       */     }
/*       */     this.i.a(true);
/*       */     if (a(this.bV)) {
/*       */       if (this.cv) {
/*       */         this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       }
/*       */     } else if (this.cv) {
/*       */       this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } 
/*       */     d(this.aL);
/*       */     c(paramInt1, paramInt2);
/*       */     b(paramInt1, paramInt2);
/*       */     this.K.clear();
/*       */     boolean bool;
/*       */     if (bool = smartZoom(paramInt1, paramInt2)) {
/*       */       m();
/*       */       a(this.aL);
/*       */       d();
/*       */       boolean bool1;
/*       */       if (!(bool1 = isContinuousPagePresentationMode(this.bV))) {
/*       */         this.aO.left += this.aH;
/*       */         this.aO.right += this.aH;
/*       */         this.aO.top += this.aL;
/*       */         this.aO.bottom += this.aL;
/*       */         for (bool1 = false; bool1 < this.aQ.size(); bool1++) {
/*       */           RectF rectF;
/*       */           rectF.left += this.aH;
/*       */           rectF.right += this.aH;
/*       */           rectF.top += this.aL;
/*       */           rectF.bottom += this.aL;
/*       */         } 
/*       */       } 
/*       */       if (this.bS != null) {
/*       */         this.bS.onDoubleTapZoomAnimationBegin();
/*       */       }
/*       */       this.i.a(this.aN, this.aO, this.aP, this.aQ);
/*       */       this.u = true;
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setLongPressEnabled(boolean paramBoolean) {
/*       */     this.bW = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean getLongPressEnabled() {
/*       */     return this.bW;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @TargetApi(19)
/*       */   public void setQuickScaleEnabled(boolean paramBoolean) {
/*       */     if (Build.VERSION.SDK_INT >= 19) {
/*       */       this.bX = paramBoolean;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @TargetApi(19)
/*       */   public boolean getQuickScaleEnabled() {
/*       */     return this.bX;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @TargetApi(23)
/*       */   public boolean isStylusScaleEnabled() {
/*       */     return this.bY;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @TargetApi(23)
/*       */   public void setStylusScaleEnabled(boolean paramBoolean) {
/*       */     this.bY = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void scrollBy(int paramInt1, int paramInt2) {
/*       */     try {
/*       */       OnScroll(this.cY, paramInt1, paramInt2, false);
/*       */     } catch (Exception exception) {}
/*       */     if (this.cv) {
/*       */       if (a(this.bV)) {
/*       */         this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } else {
/*       */         this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } 
/*       */     }
/*       */     if (n()) {
/*       */       super.scrollTo(p() + this.aH, q() + this.aL);
/*       */     } else {
/*       */       super.scrollTo(p(), q());
/*       */     } 
/*       */     if (this.f.isFinished() && !this.Q && !f()) {
/*       */       requestRendering();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void scrollTo(int paramInt1, int paramInt2) {
/*       */     try {
/*       */       OnScroll(this.cY, paramInt1 - p(), paramInt2 - q(), false);
/*       */     } catch (Exception exception) {}
/*       */     if (this.cv) {
/*       */       if (a(this.bV)) {
/*       */         this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } else {
/*       */         this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } 
/*       */     }
/*       */     if (n()) {
/*       */       super.scrollTo(p() + this.aH, q() + this.aL);
/*       */     } else {
/*       */       super.scrollTo(p(), q());
/*       */     } 
/*       */     if (!f()) {
/*       */       requestRendering();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean canScrollHorizontally(int paramInt) {
/*       */     int n;
/*       */     if ((n = computeHorizontalScrollOffset()) != 1) {
/*       */       return super.canScrollHorizontally(paramInt);
/*       */     }
/*       */     int i1;
/*       */     if ((i1 = computeHorizontalScrollRange() - computeHorizontalScrollExtent()) == 0) {
/*       */       return false;
/*       */     }
/*       */     if (paramInt < 0) {
/*       */       return (n > 1);
/*       */     }
/*       */     return (n < i1 - 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void h() {
/*       */     if (Build.VERSION.SDK_INT >= 16) {
/*       */       postInvalidateOnAnimation();
/*       */       return;
/*       */     } 
/*       */     invalidate();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean getDirectionalLockEnabled() {
/*       */     return this.cp;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setDirectionalLockEnabled(boolean paramBoolean) {
/*       */     this.cp = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMaintainZoomEnabled(boolean paramBoolean) throws PDFNetException {
/*       */     if (a(this.bV)) {
/*       */       Log.e(c, "Non-continuous vertical mode is not available in maintain zoom mode, turning off maintain zoom mode...");
/*       */       paramBoolean = false;
/*       */     } 
/*       */     this.cv = paramBoolean;
/*       */     if (this.cv) {
/*       */       SetPageRefViewMode(this.cY, PageViewMode.ZOOM.getValue());
/*       */       return;
/*       */     } 
/*       */     SetPageRefViewMode(this.cY, this.bT.getValue());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isMaintainZoomEnabled() {
/*       */     return this.cv;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setZoomingInAddingAnnotationEnabled(boolean paramBoolean) {
/*       */     this.cB = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isZoomingInAddingAnnotationEnabled() {
/*       */     return this.cB;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getCurrentPage() {
/*       */     if (this.mDoc != null) {
/*       */       if (this.ca && j.a(this.ce)) {
/*       */         return j.m(this.ce);
/*       */       }
/*       */       if (!this.f.isFinished() && this.s && (isContinuousPagePresentationMode(this.bV) || isFacingPagePresentationMode(this.bV))) {
/*       */         int n = getScrollX();
/*       */         int i1 = getScrollY();
/*       */         double[] arrayOfDouble;
/*       */         if ((arrayOfDouble = a(n, i1, (n + this.aB), (i1 + this.aC))) == null || arrayOfDouble.length == 0) {
/*       */           return GetCurrentPage(this.cY);
/*       */         }
/*       */         int i2 = arrayOfDouble.length / 5;
/*       */         double d1 = 0.0D;
/*       */         int i3 = 0;
/*       */         for (byte b1 = 0; b1 < i2; b1++) {
/*       */           int i4 = b1 * 5;
/*       */           double d2 = arrayOfDouble[i4 + 1];
/*       */           double d3 = arrayOfDouble[i4 + 2];
/*       */           double d4 = arrayOfDouble[i4 + 3];
/*       */           double d5 = arrayOfDouble[i4 + 4];
/*       */           d2 = (d2 < n) ? n : d2;
/*       */           d3 = (d3 < i1) ? i1 : d3;
/*       */           d4 = (d4 > (n + this.aB)) ? (n + this.aB) : d4;
/*       */           d5 = (d5 > (i1 + this.aC)) ? (i1 + this.aC) : d5;
/*       */           double d6;
/*       */           if ((d6 = ((d6 = (d4 - d2) * (d5 - d3)) < 0.0D) ? -d6 : d6) > d1) {
/*       */             d1 = d6;
/*       */             i3 = (int)arrayOfDouble[i4];
/*       */           } 
/*       */         } 
/*       */         if (i3 > getPageCount()) {
/*       */           i3 = getPageCount();
/*       */         }
/*       */         return i3;
/*       */       } 
/*       */       return GetCurrentPage(this.cY);
/*       */     } 
/*       */     return 0;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void findText(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt) {
/*       */     paramString = paramString;
/* 22114 */     StringBuilder stringBuilder = new StringBuilder(paramString);
/* 22115 */     if (Bidi.requiresBidi(paramString.toCharArray(), 0, paramString.length())) {
/* 22116 */       Bidi bidi = new Bidi(paramString, -2);
/* 22117 */       stringBuilder = new StringBuilder();
/* 22118 */       for (byte b1 = 0; b1 < bidi.getRunCount(); b1++) {
/* 22119 */         String str = paramString.substring(bidi.getRunStart(b1), bidi.getRunLimit(b1));
/* 22120 */         if (bidi.getRunLevel(b1) % 2 == 1) {
/* 22121 */           str = (new StringBuffer(str)).reverse().toString();
/*       */         }
/* 22123 */         stringBuilder.append(str);
/*       */       } 
/*       */     } 
/* 22126 */     paramString = stringBuilder.toString();
/*       */     cancelFindText();
/*       */     if (this.mDoc == null) {
/*       */       if (this.bB != null)
/*       */         this.bB.onTextSearchEnd(TextSearchResult.INVALID_INPUT); 
/*       */       return;
/*       */     } 
/*       */     synchronized (this) {
/*       */       this.by++;
/*       */     } 
/*       */     if (this.bB != null)
/*       */       this.bB.onTextSearchStart(); 
/*       */     this.ds = new Thread(new Runnable(this)
/*       */         {
/*       */           public final void run() { try {
/*       */               while (PDFViewCtrl.b(this.a) > 0) {
/*       */                 Thread.sleep(50L);
/*       */                 PDFViewCtrl.c(this.a).sendEmptyMessage(0);
/*       */               } 
/*       */               return;
/*       */             } catch (InterruptedException interruptedException) {
/*       */               return;
/*       */             }  }
/*       */         });
/*       */     this.ds.start();
/*       */     try {
/*       */       FindTextAsync(this.cY, paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramInt);
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       synchronized (this) {
/*       */         this.bA = paramBoolean4;
/*       */         return;
/*       */       } 
/*       */     } 
/*       */   }
/*       */   public void setInteractionEnabled(boolean paramBoolean) { this.bZ = paramBoolean; }
/*       */   private boolean a(Bitmap paramBitmap) { byte b1 = 1;
/*       */     int n = getChildCount();
/*       */     int[] arrayOfInt = null;
/*       */     if (n > 0) {
/*       */       arrayOfInt = new int[n];
/*       */       for (byte b2 = 0; b2 < n; b2++) {
/*       */         View view = getChildAt(b2);
/*       */         arrayOfInt[b2] = view.getVisibility();
/*       */         if (!(view instanceof android.widget.VideoView))
/*       */           view.setVisibility(4); 
/*       */       } 
/*       */     } 
/*       */     Drawable drawable = getBackground();
/*       */     try {
/*       */       if (drawable != null)
/*       */         setBackground(null); 
/*       */       Canvas canvas;
/*       */       (canvas = new Canvas()).setBitmap(paramBitmap);
/*       */       this.r = true;
/*       */       draw(canvas);
/*       */       this.r = false;
/*       */     } catch (Exception exception) {
/*       */       b1 = 0;
/*       */     } catch (OutOfMemoryError outOfMemoryError) {
/*       */       if (b) {
/*       */         a(4, "Taking snapshot ran out of memory", a(true));
/*       */       } else {
/*       */         Log.e("PDFNet", "Taking snapshot ran out of memory");
/*       */       } 
/*       */       this.de.a();
/*       */       b1 = 0;
/*       */     } finally {
/*       */       this.r = false;
/*       */       if (drawable != null)
/*       */         setBackground(drawable); 
/*       */       if (n > 0)
/*       */         for (b1 = 0; b1 < n; b1++) {
/*       */           View view;
/*       */           if (!(view = getChildAt(b1) instanceof android.widget.VideoView))
/*       */             view.setVisibility(arrayOfInt[b1]); 
/*       */         }  
/*       */     } 
/*       */     return b1; }
/*       */   public void setDebug(boolean paramBoolean) { a = paramBoolean;
/*       */     b = paramBoolean;
/*       */     d = (a || b); }
/*       */   public void setBuiltInPageSlidingEnabled(boolean paramBoolean) { this.cb = paramBoolean;
/*       */     this.ca = paramBoolean; }
/*       */   public void setBuiltInPageSlidingState(boolean paramBoolean) { if (this.cb)
/*       */       this.ca = paramBoolean;  }
/*       */   public void setBuiltInPageSlidingThreshSpeed(int paramInt) { this.cd = paramInt; }
/*       */   public void setBuiltInPageSlidingAnimationDuration(int paramInt) { this.cc = paramInt; }
/*       */   public void setBuiltInZoomAnimationDuration(int paramInt) { this.aR = paramInt; }
/*       */   public boolean gotoFirstPage() { return a(-2, 0); }
/*       */   public boolean gotoLastPage() { return a(2, 0); }
/*       */   public boolean canGotoPreviousPage() { return a(getCurrentPage(), false); }
/*       */   public boolean canGotoNextPage() { return a(getCurrentPage(), true); }
/*       */   public boolean gotoPreviousPage() { return a(-1, 0); }
/*       */   public void gotoPreviousPage(boolean paramBoolean) { if (paramBoolean && !(paramBoolean = isContinuousPagePresentationMode(this.bV)) && n()) {
/*       */       int n = getResources().getInteger(17694720);
/*       */       j.c(this.ce, j.a(this.ce, getCurCanvasId()), n);
/*       */       return;
/*       */     } 
/*       */     gotoPreviousPage(); }
/*       */   public boolean gotoNextPage() { return a(1, 0); }
/*       */   public void gotoNextPage(boolean paramBoolean) { if (paramBoolean && !(paramBoolean = isContinuousPagePresentationMode(this.bV)) && n()) {
/*       */       int n = getResources().getInteger(17694720);
/*       */       j.c(this.ce, j.b(this.ce, getCurCanvasId()), n);
/*       */       return;
/*       */     } 
/*       */     gotoNextPage(); }
/*       */   public boolean setCurrentPage(int paramInt) { return a(0, paramInt); }
/*       */   public boolean showRect(int paramInt, Rect paramRect) throws PDFNetException { setCurrentPage(paramInt);
/*       */     boolean bool = ShowRect(this.cY, paramInt, paramRect.a);
/*       */     c();
/*       */     scrollTo(p(), q());
/*       */     return bool; }
/*       */   public Rect jumpToAnnotWithID(String paramString) { if (this.e == null)
/*       */       return null; 
/*       */     try {
/*       */       Rect rect = this.e.jumpToAnnotWithID(paramString);
/*       */       c();
/*       */       scrollTo(p(), q());
/*       */       return rect;
/*       */     } catch (Exception exception) {
/*       */       return null;
/*       */     }  }
/*       */   public Matrix getPageSlidingCanvasTransform() { Matrix matrix;
/*       */     (matrix = new Matrix()).set(this.ag);
/*       */     return matrix; }
/*       */   public boolean isContinuousPagePresentationMode(PagePresentationMode paramPagePresentationMode) { return (paramPagePresentationMode == PagePresentationMode.SINGLE_CONT || paramPagePresentationMode == PagePresentationMode.FACING_CONT || paramPagePresentationMode == PagePresentationMode.FACING_COVER_CONT); }
/*       */   public boolean isFacingPagePresentationMode(PagePresentationMode paramPagePresentationMode) { return (paramPagePresentationMode == PagePresentationMode.FACING || paramPagePresentationMode == PagePresentationMode.FACING_CONT || paramPagePresentationMode == PagePresentationMode.FACING_COVER || paramPagePresentationMode == PagePresentationMode.FACING_COVER_CONT || paramPagePresentationMode == PagePresentationMode.FACING_VERT || paramPagePresentationMode == PagePresentationMode.FACING_COVER_VERT); }
/*       */   public PDFDoc openPDFUri(Uri paramUri, String paramString) throws PDFNetException, FileNotFoundException { return openPDFUri(paramUri, paramString, (String)null, (HTTPRequestOptions)null); }
/*       */   public PDFDoc openPDFUri(Uri paramUri, String paramString1, String paramString2, HTTPRequestOptions paramHTTPRequestOptions) throws PDFNetException, FileNotFoundException { SecondaryFileFilter secondaryFileFilter;
/*       */     if (paramUri == null)
/*       */       return null; 
/*       */     String str = paramUri.toString();
/*       */     if ("content".equals(paramUri.getScheme())) {
/*       */       paramString2 = null;
/*       */       try {
/*       */         secondaryFileFilter = new SecondaryFileFilter(getContext(), paramUri);
/*       */         PDFDoc pDFDoc1 = new PDFDoc((Filter)secondaryFileFilter);
/*       */       } finally {
/*       */         if (secondaryFileFilter != null)
/*       */           secondaryFileFilter.close(); 
/*       */       } 
/*       */       return (PDFDoc)paramUri;
/*       */     } 
/*       */     if (URLUtil.isHttpUrl(str) || URLUtil.isHttpsUrl(str)) {
/*       */       openUrlAsync(str, (String)secondaryFileFilter, paramString1, paramHTTPRequestOptions);
/*       */       return null;
/*       */     } 
/*       */     str = paramUri.getPath();
/*       */     PDFDoc pDFDoc = new PDFDoc(str);
/*       */     a(pDFDoc, paramString1);
/*       */     return pDFDoc; }
/*       */   private void a(PDFDoc paramPDFDoc, String paramString) throws PDFNetException { if (paramString == null)
/*       */       paramString = ""; 
/*       */     boolean bool;
/*       */     if (!(bool = paramPDFDoc.initStdSecurityHandler(paramString)))
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl", "openPDFUri", "Incorrect password"); 
/*       */     int n;
/*       */     if ((n = paramPDFDoc.getPageCount()) <= 0)
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl", "openPDFUri", "Trying to open PDF with no pages."); 
/*       */     setDoc(paramPDFDoc); }
/*       */   public DocumentConversion openNonPDFUri(Uri paramUri, ConversionOptions paramConversionOptions) throws PDFNetException, FileNotFoundException { DocumentConversion documentConversion;
/*       */     if ("content".equals(paramUri.getScheme())) {
/*       */       this.mDocumentConversionFilter = new SecondaryFileFilter(getContext(), paramUri);
/*       */       documentConversion = Convert.universalConversion((Filter)this.mDocumentConversionFilter, paramConversionOptions);
/*       */     } else {
/*       */       String str = documentConversion.getPath();
/*       */       File file;
/*       */       if (!(file = new File(str)).exists())
/*       */         return null; 
/*       */       documentConversion = Convert.universalConversion(str, paramConversionOptions);
/*       */     } 
/*       */     openUniversalDocument(documentConversion);
/*       */     addUniversalDocumentConversionListener(this.cU);
/*       */     return documentConversion; }
/*       */   public PDFDoc getDoc() { return this.mDoc; }
/*       */   public void setDoc(PDFDoc paramPDFDoc) throws PDFNetException { closeDoc();
/*       */     this.ci.lock();
/*       */     try {
/*       */       if (paramPDFDoc != null) {
/*       */         boolean bool = false;
/*       */         SecurityHandler securityHandler;
/*       */         if ((securityHandler = paramPDFDoc.getSecurityHandler()) != null) {
/*       */           if (securityHandler.getPermission(2)) {
/*       */             a(paramPDFDoc, false);
/*       */           } else {
/*       */             bool = true;
/*       */           } 
/*       */         } else if (!(bool = !paramPDFDoc.initSecurityHandler() ? true : false)) {
/*       */           a(paramPDFDoc, false);
/*       */         } 
/*       */         if (bool) {
/*       */           if (this.bR == null)
/*       */             this.bR = new c(this, getContext()); 
/*       */           this.bR.a(paramPDFDoc);
/*       */           this.bR.show();
/*       */         } 
/*       */       } 
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl", "setDoc", exception.getMessage());
/*       */     } finally {
/*       */       this.ci.unlock();
/*       */     }  }
/*       */   public void openUniversalDocument(DocumentConversion paramDocumentConversion) throws PDFNetException { this.cg = 0;
/*       */     this.ch = 0;
/*       */     closeDoc();
/*       */     try {
/*       */       OpenUniversalDocumentNoDoc(this.cY, paramDocumentConversion.__GetHandle());
/*       */       this.cD.a = paramDocumentConversion;
/*       */       this.bE = true;
/*       */     } catch (Exception exception) {
/*       */       if (this.bC != null)
/*       */         for (Iterator<DocumentDownloadListener> iterator = this.bC.iterator(); iterator.hasNext();)
/*       */           (documentDownloadListener = iterator.next()).onDownloadEvent(DownloadState.FAILED, 0, 0, 0, exception.getMessage());  
/*       */     } 
/*       */     requestLayout(); }
/*       */   public void openUrlAsync(String paramString1, String paramString2, String paramString3, HTTPRequestOptions paramHTTPRequestOptions) throws PDFNetException { this.cg = 0;
/*       */     this.ch = 0;
/*       */     closeDoc();
/*       */     try {
/*       */       long l1 = (paramHTTPRequestOptions == null) ? 0L : paramHTTPRequestOptions.a.__GetHandle();
/*       */       paramString2 = (paramString2 == null) ? "" : paramString2;
/*       */       paramString3 = (paramString3 == null) ? "" : paramString3;
/*       */       OpenURL(this.cY, paramString1, paramString2, paramString3, l1);
/*       */       this.bE = true;
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       if (this.bC != null)
/*       */         for (Iterator<DocumentDownloadListener> iterator = this.bC.iterator(); iterator.hasNext();)
/*       */           (documentDownloadListener = iterator.next()).onDownloadEvent(DownloadState.FAILED, 0, 0, 0, exception.getMessage());  
/*       */       return;
/*       */     }  }
/*       */   public void closeDoc() throws PDFNetException { this.ci.lock();
/*       */     try {
/*       */       if (this.mDoc != null || this.cD.a != null) {
/*       */         i();
/*       */         this.j.lock();
/*       */         try {
/*       */           this.l.b();
/*       */           this.m.c();
/*       */         } finally {
/*       */           this.j.unlock();
/*       */         } 
/*       */         CloseDoc(this.cY);
/*       */         this.cE++;
/*       */         this.cD.a();
/*       */         this.mDoc = null;
/*       */         i();
/*       */         if (this.mDoc != null) {
/*       */           this.bg = true;
/*       */           requestLayout();
/*       */         } 
/*       */       } 
/*       */       return;
/*       */     } finally {
/*       */       this.ci.unlock();
/*       */     }  }
/*       */   public void docLockRead() throws PDFNetException { if (this.cY == 0L)
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl.java", "docLockRead", "PDFViewCtrl is destroyed"); 
/*       */     DocLockRead(this.cY); }
/*       */   public boolean docTryLockRead(int paramInt) throws PDFNetException { if (this.cY == 0L)
/*       */       return false; 
/*       */     return DocTryLockRead(this.cY, paramInt); }
/*       */   public boolean docUnlockRead() { if (this.cY == 0L)
/*       */       return false; 
/*       */     try {
/*       */       DocUnlockRead(this.cY);
/*       */       return true;
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return false;
/*       */     }  }
/*       */   public void docLock(boolean paramBoolean) throws PDFNetException { if (this.cY == 0L)
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl.java", "docLock", "PDFViewCtrl is destroyed"); 
/*       */     this.B = paramBoolean;
/*       */     DocLock(this.cY, paramBoolean); }
/*       */   public boolean docTryLock(int paramInt) throws PDFNetException { if (this.cY == 0L)
/*       */       return false; 
/*       */     return DocTryLock(this.cY, paramInt); }
/*       */   public boolean docUnlock() { if (this.cY == 0L)
/*       */       return false; 
/*       */     try {
/*       */       DocUnlock(this.cY);
/*       */       if (this.B) {
/*       */         this.B = false;
/*       */         requestRendering();
/*       */       } 
/*       */       return true;
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return false;
/*       */     }  }
/*       */   public void setToolManager(ToolManager paramToolManager) { this.bS = paramToolManager; }
/*       */   public ToolManager getToolManager() { return this.bS; }
/*       */   public void addDocumentDownloadListener(DocumentDownloadListener paramDocumentDownloadListener) { if (this.bC == null)
/*       */       this.bC = new CopyOnWriteArrayList<>(); 
/*       */     if (!this.bC.contains(paramDocumentDownloadListener))
/*       */       this.bC.add(paramDocumentDownloadListener);  }
/*       */   public void removeDocumentDownloadListener(DocumentDownloadListener paramDocumentDownloadListener) { if (this.bC != null)
/*       */       this.bC.remove(paramDocumentDownloadListener);  }
/*       */   public void addOnCanvasSizeChangeListener(OnCanvasSizeChangeListener paramOnCanvasSizeChangeListener) { if (this.cC == null)
/*       */       this.cC = new ArrayList<>(); 
/*       */     if (!this.cC.contains(paramOnCanvasSizeChangeListener))
/*       */       this.cC.add(paramOnCanvasSizeChangeListener);  }
/*       */   public void removeOnCanvasSizeChangeListener(OnCanvasSizeChangeListener paramOnCanvasSizeChangeListener) { if (this.cC != null)
/*       */       this.cC.remove(paramOnCanvasSizeChangeListener);  }
/*       */   public void addUniversalDocumentConversionListener(UniversalDocumentConversionListener paramUniversalDocumentConversionListener) { if (this.bD == null)
/*       */       this.bD = new CopyOnWriteArrayList<>(); 
/*       */     if (!this.bD.contains(paramUniversalDocumentConversionListener))
/*       */       this.bD.add(paramUniversalDocumentConversionListener);  }
/*       */   public void removeUniversalDocumentConversionListener(UniversalDocumentConversionListener paramUniversalDocumentConversionListener) { if (this.bD != null)
/*       */       this.bD.remove(paramUniversalDocumentConversionListener);  }
/*       */   public void addPageChangeListener(PageChangeListener paramPageChangeListener) { if (this.bF == null)
/*       */       this.bF = new CopyOnWriteArrayList<>(); 
/*       */     this.bF.add(paramPageChangeListener); }
/*       */   public void removePageChangeListener(PageChangeListener paramPageChangeListener) { if (this.bF != null)
/*       */       this.bF.remove(paramPageChangeListener);  }
/*       */   public void setRenderingListener(RenderingListener paramRenderingListener) { this.bO = paramRenderingListener; } public void addDocumentLoadListener(DocumentLoadListener paramDocumentLoadListener) { if (this.bJ == null)
/*       */       this.bJ = new CopyOnWriteArrayList<>(); 
/*       */     if (!this.bJ.contains(paramDocumentLoadListener))
/*       */       this.bJ.add(paramDocumentLoadListener);  } public void removeDocumentLoadListener(DocumentLoadListener paramDocumentLoadListener) { if (this.bJ != null)
/*       */       this.bJ.remove(paramDocumentLoadListener);  } public void setErrorReportListener(ErrorReportListener paramErrorReportListener) {
/*       */     this.bL = paramErrorReportListener;
/*       */   } public void addThumbAsyncListener(ThumbAsyncListener paramThumbAsyncListener) {
/*       */     if (this.bM == null)
/*       */       this.bM = new ArrayList<>(); 
/*       */     if (!this.bM.contains(paramThumbAsyncListener))
/*       */       this.bM.add(paramThumbAsyncListener); 
/*       */   } public void removeThumbAsyncListener(ThumbAsyncListener paramThumbAsyncListener) {
/*       */     if (this.bM != null)
/*       */       this.bM.remove(paramThumbAsyncListener); 
/*       */   } public void setActionCompletedListener(ActionCompletedListener paramActionCompletedListener) {
/*       */     this.bN = paramActionCompletedListener;
/*       */   } public void setTextSearchListener(TextSearchListener paramTextSearchListener) {
/*       */     this.bB = paramTextSearchListener;
/*       */   } public void setUniversalDocumentProgressIndicatorListener(UniversalDocumentProgressIndicatorListener paramUniversalDocumentProgressIndicatorListener) {
/*       */     this.bP = paramUniversalDocumentProgressIndicatorListener;
/*       */   } public void postCustomEvent(Object paramObject) {
/*       */     if (this.dl.hasMessages(0))
/*       */       this.dl.removeMessages(0); 
/*       */     this.cf = paramObject;
/*       */     this.dl.sendEmptyMessage(0);
/*       */   } public void closeTool() {
/*       */     if (this.bS != null)
/*       */       this.bS.onClose(); 
/*       */   } public double[] convScreenPtToCanvasPt(double paramDouble1, double paramDouble2) {
/*       */     return ConvScreenPtToCanvasPt(this.cY, paramDouble1, paramDouble2);
/*       */   } public double[] convScreenPtToCanvasPt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     return ConvScreenPtToCanvasPt(this.cY, paramDouble1, paramDouble2, paramInt);
/*       */   } public double[] convScreenPtToPagePt(double paramDouble1, double paramDouble2) {
/*       */     return ConvScreenPtToPagePt(this.cY, paramDouble1, paramDouble2, -1);
/*       */   } public double[] convScreenPtToPagePt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     return ConvScreenPtToPagePt(this.cY, paramDouble1, paramDouble2, paramInt);
/*       */   } public double[] convCanvasPtToScreenPt(double paramDouble1, double paramDouble2) {
/*       */     double[] arrayOfDouble;
/*       */     return arrayOfDouble = ConvCanvasPtToScreenPt(this.cY, paramDouble1, paramDouble2);
/*       */   } public double[] convCanvasPtToScreenPt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     return ConvCanvasPtToScreenPt(this.cY, paramDouble1, paramDouble2, paramInt);
/*       */   } public double[] convCanvasPtToPagePt(double paramDouble1, double paramDouble2) {
/*       */     return ConvCanvasPtToPagePt(this.cY, paramDouble1, paramDouble2, -1);
/*       */   } public double[] convCanvasPtToPagePt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     return ConvCanvasPtToPagePt(this.cY, paramDouble1, paramDouble2, paramInt);
/*       */   } public double[] convPagePtToCanvasPt(double paramDouble1, double paramDouble2) {
/*       */     return ConvPagePtToCanvasPt(this.cY, paramDouble1, paramDouble2, -1);
/*       */   } public double[] convPagePtToCanvasPt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     return ConvPagePtToCanvasPt(this.cY, paramDouble1, paramDouble2, paramInt);
/*       */   } public double[] convPagePtToScreenPt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     double[] arrayOfDouble;
/*       */     return arrayOfDouble = ConvPagePtToScreenPt(this.cY, paramDouble1, paramDouble2, paramInt);
/*       */   } @Deprecated
/*       */   public double[] convPagePtToHorizonalScrollingPt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     return convPagePtToHorizontalScrollingPt(paramDouble1, paramDouble2, paramInt);
/*       */   } public double[] convPagePtToHorizontalScrollingPt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     int n = h(paramInt);
/*       */     double[] arrayOfDouble = convPagePtToScreenPt(paramDouble1, paramDouble2, paramInt);
/*       */     boolean bool;
/*       */     if (!(bool = isContinuousPagePresentationMode(this.bV))) {
/*       */       bool = false;
/*       */       if (a(this.bV)) {
/*       */         if (this.aC == this.aE || j.a(this.ce)) {
/*       */           arrayOfDouble[0] = arrayOfDouble[0] + getScrollX();
/*       */           arrayOfDouble[1] = arrayOfDouble[1] + getScrollOffsetForCanvasId(n);
/*       */           bool = true;
/*       */         } 
/*       */       } else if (this.aB == this.aD || j.a(this.ce)) {
/*       */         arrayOfDouble[0] = arrayOfDouble[0] + getScrollOffsetForCanvasId(n);
/*       */         arrayOfDouble[1] = arrayOfDouble[1] + getScrollY();
/*       */         bool = true;
/*       */       } 
/*       */       if (!bool) {
/*       */         arrayOfDouble[0] = arrayOfDouble[0] + getScrollX();
/*       */         arrayOfDouble[1] = arrayOfDouble[1] + getScrollY();
/*       */       } 
/*       */     } else {
/*       */       arrayOfDouble[0] = arrayOfDouble[0] + getScrollX();
/*       */       arrayOfDouble[1] = arrayOfDouble[1] + getScrollY();
/*       */     } 
/*       */     return arrayOfDouble;
/*       */   } public double[] convHorizontalScrollingPtToPagePt(double paramDouble1, double paramDouble2, int paramInt) {
/*       */     int n = h(paramInt);
/*       */     boolean bool;
/*       */     if (!(bool = isContinuousPagePresentationMode(this.bV))) {
/*       */       bool = false;
/*       */       if (a(this.bV)) {
/*       */         if (this.aC == this.aE || j.a(this.ce)) {
/*       */           paramDouble1 -= getScrollX();
/*       */           paramDouble2 -= getScrollOffsetForCanvasId(n);
/*       */           bool = true;
/*       */         } 
/*       */       } else if (this.aB == this.aD || j.a(this.ce)) {
/*       */         paramDouble1 -= getScrollOffsetForCanvasId(n);
/*       */         paramDouble2 -= getScrollY();
/*       */         bool = true;
/*       */       } 
/*       */       if (!bool) {
/*       */         paramDouble1 -= getScrollX();
/*       */         paramDouble2 -= getScrollY();
/*       */       } 
/*       */     } else {
/*       */       paramDouble1 -= getScrollX();
/*       */       paramDouble2 -= getScrollY();
/*       */     } 
/*       */     return convScreenPtToPagePt(paramDouble1, paramDouble2, paramInt);
/*       */   } public PointF snapToNearestInDoc(double paramDouble1, double paramDouble2) {
/*       */     double[] arrayOfDouble = SnapToNearestInDoc(this.cY, paramDouble1, paramDouble2);
/*       */     return new PointF((float)arrayOfDouble[0], (float)arrayOfDouble[1]);
/*       */   } public boolean isSlidingWhileZoomed() {
/*       */     return (j.a(this.ce) && (j.g(this.ce) || j.h(this.ce) || j.i(this.ce)));
/*       */   } public int getPageNumberFromScreenPt(double paramDouble1, double paramDouble2) {
/*       */     return GetPageNumberFromScreenPt(this.cY, paramDouble1, paramDouble2);
/*       */   } public void destroy() {
/*       */     if (this.cY == 0L)
/*       */       return; 
/* 22562 */     (null = ImageCache.a.a()).a(false);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     null.a();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 22683 */     b.a.a().b();
/*       */     null.a(true);
/*       */     if (this.bS != null) {
/*       */       this.bS.onDestroy();
/*       */     }
/*       */     this.ci.lock();
/*       */     try {
/*       */       if (this.bR != null && this.bR.isShowing()) {
/*       */         this.bR.dismiss();
/*       */       }
/*       */     } catch (Exception exception) {}
/*       */     try {
/*       */       pause();
/*       */       this.j.lock();
/*       */       try {
/*       */         this.l.b();
/*       */         this.m.c();
/*       */       } finally {
/*       */         this.j.unlock();
/*       */       } 
/*       */       CloseDoc(this.cY);
/*       */       this.mDoc = null;
/*       */       pause();
/*       */       if (this.cY != 0L) {
/*       */         try {
/*       */           Destroy(this.cY);
/*       */           this.cY = 0L;
/*       */         } catch (Exception exception) {}
/*       */       }
/*       */       l();
/*       */       if (this.cZ != 0L) {
/*       */         try {
/*       */           DestroyRenderData(this.cZ, 0L, 0L, 0L, 0L, 0L, this.cX);
/*       */           this.cZ = 0L;
/*       */         } catch (Exception exception) {}
/*       */       }
/*       */       if (this.mDocumentConversionFilter != null) {
/*       */         this.mDocumentConversionFilter.close();
/*       */         this.mDocumentConversionFilter = null;
/*       */       } 
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       if ((null = null).getMessage() != null) {
/*       */         Log.e("PDFNet", null.getMessage());
/*       */       }
/*       */       return;
/*       */     } finally {
/*       */       this.ci.unlock();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void prepareWords(int paramInt) {
/*       */     try {
/*       */       PrepareWords(this.cY, paramInt);
/*       */       return;
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean wereWordsPrepared(int paramInt) {
/*       */     try {
/*       */       return WereWordsPrepared(this.cY, paramInt);
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isThereTextInRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*       */     try {
/*       */       return IsThereTextInRect(this.cY, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void prepareAnnotsForMouse(int paramInt) {
/*       */     prepareAnnotsForMouse(paramInt, 15.0D, 7.0D);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void prepareAnnotsForMouse(int paramInt, double paramDouble1, double paramDouble2) {
/*       */     try {
/*       */       double d1 = a((float)paramDouble1);
/*       */       double d2 = a((float)paramDouble2);
/*       */       PrepareAnnotsForMouse(this.cY, paramInt, d1, d2);
/*       */       return;
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean wereAnnotsForMousePrepared(int paramInt) {
/*       */     try {
/*       */       return WereAnnotsForMousePrepared(this.cY, paramInt);
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getAnnotTypeUnder(double paramDouble1, double paramDouble2) {
/*       */     try {
/*       */       return GetAnnotTypeUnder(this.cY, paramDouble1, paramDouble2);
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */       return 28;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setTextSelectionMode(TextSelectionMode paramTextSelectionMode) {
/*       */     SetTextSelectionMode(this.cY, paramTextSelectionMode.getValue());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean select(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*       */     return Select(this.cY, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean select(double paramDouble1, double paramDouble2, int paramInt1, double paramDouble3, double paramDouble4, int paramInt2) {
/*       */     return Select(this.cY, paramDouble1, paramDouble2, paramInt1, paramDouble3, paramDouble4, paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean selectByRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*       */     return SelectByRect(this.cY, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean selectByStructWithSmartSnapping(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*       */     return SelectByStructWithSmartSnapping(this.cY, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean selectByStructWithSnapping(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2) {
/*       */     return SelectByStructWithSnapping(this.cY, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramBoolean1, paramBoolean2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean selectWithHighlights(Highlights paramHighlights) {
/*       */     return SelectByHighlights(this.cY, paramHighlights.a);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean selectAndJumpWithHighlights(Highlights paramHighlights) {
/*       */     boolean bool;
/*       */     if (bool = selectWithHighlights(paramHighlights)) {
/*       */       if (!isContinuousPagePresentationMode(this.bV)) {
/*       */         r();
/*       */       }
/*       */       scrollTo(p(), q());
/*       */       invalidate();
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean selectWithSelection(Selection paramSelection) {
/*       */     return SelectBySelection(this.cY, Selection.a(paramSelection));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void selectAll() {
/*       */     SelectAll(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean hasSelection() {
/*       */     return HasSelection(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void clearSelection() {
/*       */     ClearSelection(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Selection getSelection(int paramInt) {
/*       */     return new Selection(GetSelection(this.cY, paramInt), this, (byte)0);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getSelectionBeginPage() {
/*       */     return GetSelectionBeginPage(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getSelectionEndPage() {
/*       */     return GetSelectionEndPage(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean hasSelectionOnPage(int paramInt) {
/*       */     return HasSelectionOnPage(this.cY, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void findText(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
/*       */     findText(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, -1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void cancelFindText() {
/*       */     synchronized (this) {
/*       */       if (this.by > 0) {
/*       */         if (this.ds != null) {
/*       */           this.ds.interrupt();
/*       */         }
/*       */         this.dr.removeMessages(0);
/*       */         CancelFindText(this.cY);
/*       */         this.bz = true;
/*       */       } 
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void cancelRendering() {
/*       */     this.dc.removeMessages(0);
/*       */     if (this.cY != 0L) {
/*       */       try {
/*       */         CancelRendering(this.cY);
/*       */         return;
/*       */       } catch (Exception exception) {}
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void cancelRenderingAsync() {
/*       */     this.dc.removeMessages(0);
/*       */     if (this.cY != 0L) {
/*       */       try {
/*       */         CancelRenderingAsync(this.cY);
/*       */         return;
/*       */       } catch (Exception exception) {}
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void requestRendering() {
/*       */     if (this.cY != 0L && !this.cl) {
/*       */       try {
/*       */         RequestRender(this.cY);
/*       */         return;
/*       */       } catch (Exception exception) {
/*       */         Log.e("PDFNet", "RR1 " + exception.getMessage());
/*       */         purgeMemory();
/*       */         try {
/*       */           RequestRender(this.cY);
/*       */           return;
/*       */         } catch (Exception exception1) {
/*       */           if ((exception = null).getMessage() != null) {
/*       */             Log.e("PDFNet", "RR2 " + exception.getMessage());
/*       */           }
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isFinishedRendering() {
/*       */     try {
/*       */       return IsFinishedRendering(this.cY, false);
/*       */     } catch (Exception exception) {
/*       */       return false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isFinishedRendering(boolean paramBoolean) {
/*       */     try {
/*       */       return IsFinishedRendering(this.cY, paramBoolean);
/*       */     } catch (Exception exception) {
/*       */       return false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void waitForRendering(int paramInt) {
/*       */     long l1 = SystemClock.uptimeMillis();
/*       */     while (!isFinishedRendering(true)) {
/*       */       try {
/*       */         Thread.sleep(20L);
/*       */       } catch (InterruptedException interruptedException) {}
/*       */       if (SystemClock.uptimeMillis() - l1 >= paramInt) {
/*       */         break;
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void waitForRendering() {
/*       */     waitForRendering(1500);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isHardwareAccelerated() {
/*       */     return super.isHardwareAccelerated();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updatePageLayout() throws PDFNetException {
/*       */     UpdatePageLayout(this.cY);
/*       */     c();
/*       */     this.bg = true;
/*       */     requestLayout();
/*       */     this.j.lock();
/*       */     try {
/*       */       int[] arrayOfInt1;
/*       */       int[] arrayOfInt2;
/*       */       int i1;
/*       */       byte b1;
/*       */       for (i1 = (arrayOfInt2 = arrayOfInt1 = this.m.b()).length, b1 = 0; b1 < i1; ) {
/*       */         int i2 = arrayOfInt2[b1];
/*       */         c c1;
/*       */         (c1 = this.m.a(i2)).c = null;
/*       */         b1++;
/*       */       } 
/*       */       int n;
/*       */       if ((n = getPageBox()) != 1) {
/*       */         i1 = 0;
/*       */         try {
/*       */           docLockRead();
/*       */           i1 = 1;
/*       */           PDFDoc pDFDoc = getDoc();
/*       */           int arrayOfInt[], i2;
/*       */           byte b2;
/*       */           for (i2 = (arrayOfInt = arrayOfInt1).length, b2 = 0; b2 < i2; b2++) {
/*       */             c c1 = this.m.a(i3);
/*       */             int i3;
/*       */             Page page;
/*       */             if ((i3 = arrayOfInt[b2]) > 0 && i3 <= getPageCount() && (page = pDFDoc.getPage(i3)) != null) {
/*       */               c1.c = page.getBox(n);
/*       */             }
/*       */           } 
/*       */         } catch (PDFNetException pDFNetException2) {
/*       */           PDFNetException pDFNetException1;
/*       */           (pDFNetException1 = null).printStackTrace();
/*       */         } finally {
/*       */           if (i1 != 0) {
/*       */             docUnlockRead();
/*       */           }
/*       */         } 
/*       */       } 
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */     } finally {
/*       */       this.j.unlock();
/*       */     } 
/*       */     if (this.mDoc != null && this.bj == ZoomLimitMode.RELATIVE) {
/*       */       double d2, d3;
/*       */       boolean bool = !isFinishedRendering() ? true : false;
/*       */       double d1 = getZoom();
/*       */       cancelRendering();
/*       */       this.bk = j();
/*       */       this.bl = a(this.bk);
/*       */       this.bn = k();
/*       */       this.bo = a(this.bn);
/*       */       if (this.cv) {
/*       */         setPageViewMode(this.cy);
/*       */       }
/*       */       if (this.cv) {
/*       */         d2 = this.bh * this.bn;
/*       */         d3 = this.bi * this.bo;
/*       */       } else {
/*       */         d2 = this.bh * this.bk;
/*       */         d3 = this.bi * this.bl;
/*       */       } 
/*       */       if (d1 < d2) {
/*       */         setZoom(d2);
/*       */       }
/*       */       if (d1 > d3) {
/*       */         setZoom(d3);
/*       */       }
/*       */       if (bool) {
/*       */         requestRendering();
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void update() throws PDFNetException {
/*       */     update(false);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void update(boolean paramBoolean) throws PDFNetException {
/*       */     Update(this.cY, paramBoolean);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void update(Rect paramRect) throws PDFNetException {
/*       */     Update(this.cY, paramRect.a);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void update(Annot paramAnnot, int paramInt) throws PDFNetException {
/*       */     Update(this.cY, paramAnnot.a, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void update(Field paramField) throws PDFNetException {
/*       */     UpdateField(this.cY, paramField.a);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void resume() {
/*       */     this.cl = false;
/*       */     if (this.bK) {
/*       */       requestLayout();
/*       */     }
/*       */     if (this.cY != 0L && this.mDoc != null) {
/*       */       requestRendering();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void pause() {
/*       */     i();
/*       */     this.cl = true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void i() {
/*       */     try {
/*       */       if (this.bE) {
/*       */         if (this.bC != null) {
/*       */           for (Iterator<DocumentDownloadListener> iterator = this.bC.iterator(); iterator.hasNext();) {
/*       */             (documentDownloadListener = iterator.next()).onDownloadEvent(DownloadState.FAILED, 0, 0, 0, "cancelled");
/*       */           }
/*       */         }
/*       */         CloseDoc(this.cY);
/*       */         this.bE = false;
/*       */       } 
/*       */       cancelAllThumbRequests();
/*       */       cancelRendering();
/*       */       cancelFindText();
/*       */       closeTool();
/*       */       l();
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void onConfigurationChanged(Configuration paramConfiguration) {
/*       */     if (this.bS != null) {
/*       */       this.bS.onConfigurationChanged(paramConfiguration);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void purgeMemory() {
/*       */     this.A = true;
/*       */     ImageCache imageCache;
/* 23562 */     (imageCache = ImageCache.a.a()).a(false);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     imageCache.a();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 23683 */     b.a.a().b();
/*       */     try {
/*       */       cancelRendering();
/*       */       PurgeMemory(this.cY);
/*       */       this.k.lock();
/*       */       this.n = null;
/*       */       this.k.unlock();
/*       */       return;
/*       */     } finally {
/*       */       imageCache.a(true);
/*       */       this.A = false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void requestLayout() {
/*       */     super.requestLayout();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public long[] getAllCanvasPixelSizes() {
/*       */     return GetAllCanvasPixelSizes(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public double getCanvasWidth() {
/*       */     return GetCanvasWidth(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public double getCanvasHeight() {
/*       */     return GetCanvasHeight(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getViewCanvasWidth() {
/*       */     return this.aD;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getViewCanvasHeight() {
/*       */     return this.aE;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public PagePresentationMode getPagePresentationMode() {
/*       */     if (this.bV != null && (this.bV == PagePresentationMode.SINGLE_VERT || this.bV == PagePresentationMode.FACING_VERT || this.bV == PagePresentationMode.FACING_COVER_VERT)) {
/*       */       return this.bV;
/*       */     }
/*       */     return PagePresentationMode.valueOf(GetPagePresentationMode(this.cY));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPagePresentationMode(PagePresentationMode paramPagePresentationMode) {
/*       */     if (this.cv && a(paramPagePresentationMode)) {
/*       */       Log.e(c, "Non-continuous vertical mode is not available in maintain zoom mode, turning off maintain zoom mode...");
/*       */       try {
/*       */         setMaintainZoomEnabled(false);
/*       */       } catch (Exception exception) {}
/*       */     } 
/*       */     try {
/*       */       if (paramPagePresentationMode != this.bV) {
/*       */         if (!this.f.isFinished()) {
/*       */           this.f.forceFinished(true);
/*       */           if (this.s && this.mDoc != null) {
/*       */             int n = getScrollX();
/*       */             int i1 = getScrollY();
/*       */             if (a(paramPagePresentationMode)) {
/*       */               if (this.cv) {
/*       */                 this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */               }
/*       */               if (i1 >= this.aL && n()) {
/*       */                 i1 -= this.aL;
/*       */               }
/*       */             } else {
/*       */               if (this.cv) {
/*       */                 this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */               }
/*       */               if (n >= this.aH && n()) {
/*       */                 n -= this.aH;
/*       */               }
/*       */             } 
/*       */             int i2 = n - p();
/*       */             int i3 = i1 - q();
/*       */             if (i2 != 0 || i3 != 0) {
/*       */               scrollBy(i2, i3);
/*       */             }
/*       */           } 
/*       */         } 
/*       */         this.aF = 0;
/*       */         this.aG = 0;
/*       */         this.aH = 0;
/*       */         this.aI = 0;
/*       */         this.aJ = 0;
/*       */         this.aK = 0;
/*       */         this.aL = 0;
/*       */         this.aM = 0;
/*       */         this.bV = paramPagePresentationMode;
/* 24457 */         SetPagePresentationMode(this.cY, ((paramPagePresentationMode = this.bV) == PagePresentationMode.SINGLE_VERT) ? 
/* 24458 */             PagePresentationMode.SINGLE.getValue() : (
/* 24459 */             (paramPagePresentationMode == PagePresentationMode.FACING_VERT) ? 
/* 24460 */             PagePresentationMode.FACING.getValue() : (
/* 24461 */             (paramPagePresentationMode == PagePresentationMode.FACING_COVER_VERT) ? 
/* 24462 */             PagePresentationMode.FACING_COVER.getValue() : 
/*       */             
/* 24464 */             paramPagePresentationMode.getValue())));
/*       */         requestLayout();
/*       */         if (this.mDoc != null && this.bj == ZoomLimitMode.RELATIVE) {
/*       */           double d2, d3, d1 = getZoom();
/*       */           this.bk = j();
/*       */           this.bl = a(this.bk);
/*       */           this.bn = k();
/*       */           this.bo = a(this.bn);
/*       */           if (this.cv) {
/*       */             d2 = this.bh * this.bn;
/*       */             d3 = this.bi * this.bo;
/*       */           } else {
/*       */             d2 = this.bh * this.bk;
/*       */             d3 = this.bi * this.bl;
/*       */           } 
/*       */           if (d1 < d2) {
/*       */             setZoom(d2);
/*       */             return;
/*       */           } 
/*       */           if (d1 > d3) {
/*       */             setZoom(d3);
/*       */             return;
/*       */           } 
/*       */         } 
/*       */         c();
/*       */         scrollTo(p(), q());
/*       */       } 
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public PageViewMode getPageViewMode() {
/*       */     return PageViewMode.valueOf(GetPageViewMode(this.cY));
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(PageViewMode paramPageViewMode, boolean paramBoolean) {
/*       */     if (getPageViewMode() != paramPageViewMode || paramBoolean) {
/*       */       if (!this.f.isFinished()) {
/*       */         this.f.forceFinished(true);
/*       */         if (this.s && this.mDoc != null) {
/*       */           int n = getScrollX();
/*       */           int i1 = getScrollY();
/*       */           if (a(this.bV)) {
/*       */             if (this.cv) {
/*       */               this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */             }
/*       */             if (i1 >= this.aL && n()) {
/*       */               i1 -= this.aL;
/*       */             }
/*       */           } else {
/*       */             if (this.cv) {
/*       */               this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */             }
/*       */             if (n >= this.aH && n()) {
/*       */               n -= this.aH;
/*       */             }
/*       */           } 
/*       */           int i2 = n - p();
/*       */           int i3 = i1 - q();
/*       */           if (i2 != 0 || i3 != 0) {
/*       */             scrollBy(i2, i3);
/*       */           }
/*       */         } 
/*       */       } 
/*       */       SetPageViewMode(this.cY, paramPageViewMode.getValue());
/*       */       this.bT = paramPageViewMode;
/*       */       requestLayout();
/*       */       if (this.mDoc != null && this.bj == ZoomLimitMode.RELATIVE) {
/*       */         double d2, d3, d1 = getZoom();
/*       */         this.bk = j();
/*       */         this.bl = a(this.bk);
/*       */         if (this.cv) {
/*       */           d2 = this.bh * this.bn;
/*       */           d3 = this.bi * this.bo;
/*       */         } else {
/*       */           d2 = this.bh * this.bk;
/*       */           d3 = this.bi * this.bl;
/*       */         } 
/*       */         if (d1 < d2) {
/*       */           setZoom(d2);
/*       */           return;
/*       */         } 
/*       */         if (d1 > d3) {
/*       */           setZoom(d3);
/*       */           return;
/*       */         } 
/*       */       } 
/*       */       c();
/*       */       scrollTo(p(), q());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPageViewMode(PageViewMode paramPageViewMode) {
/*       */     try {
/*       */       a(paramPageViewMode, false);
/*       */       return;
/*       */     } catch (Exception exception) {
/*       */       return;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPageViewMode(PageViewMode paramPageViewMode, int paramInt1, int paramInt2, boolean paramBoolean) {
/*       */     if (!paramBoolean) {
/*       */       setPageViewMode(paramPageViewMode);
/*       */       return;
/*       */     } 
/*       */     this.i.a(true);
/*       */     if (this.cv) {
/*       */       if (a(this.bV)) {
/*       */         this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } else {
/*       */         this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       } 
/*       */     }
/*       */     d(this.aL);
/*       */     c(paramInt1, paramInt2);
/*       */     b(paramInt1, paramInt2);
/*       */     this.K.clear();
/*       */     setPageViewMode(paramPageViewMode);
/*       */     m();
/*       */     a(this.aL);
/*       */     d();
/*       */     boolean bool1 = isContinuousPagePresentationMode(this.bV);
/*       */     boolean bool2 = a(this.bV);
/*       */     if (!bool1) {
/*       */       if (bool2) {
/*       */         this.aO.top += getScrollY();
/*       */         this.aO.bottom += getScrollY();
/*       */       } else {
/*       */         this.aO.left += getScrollX();
/*       */         this.aO.right += getScrollX();
/*       */       } 
/*       */       for (bool1 = false; bool1 < this.aQ.size(); bool1++) {
/*       */         RectF rectF = (RectF)this.aQ.valueAt(bool1);
/*       */         if (bool2) {
/*       */           rectF.top += getScrollY();
/*       */           rectF.bottom += getScrollY();
/*       */         } else {
/*       */           rectF.left += getScrollX();
/*       */           rectF.right += getScrollX();
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     if (this.bS != null) {
/*       */       this.bS.onDoubleTapZoomAnimationBegin();
/*       */     }
/*       */     this.i.a(this.aN, this.aO, this.aP, this.aQ);
/*       */     this.u = true;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public PageViewMode getPageRefViewMode() {
/*       */     return PageViewMode.valueOf(GetPageRefViewMode(this.cY));
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPageRefViewMode(PageViewMode paramPageViewMode) throws PDFNetException {
/*       */     if (paramPageViewMode != PageViewMode.ZOOM && this.cv) {
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl", "setPageRefViewMode", "Page Ref View Mode cannot be other than PAGE_VIEW_ZOOM when maintain zoom level feature is enabled.");
/*       */     }
/*       */     SetPageRefViewMode(this.cY, paramPageViewMode.getValue());
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPreferredViewMode(PageViewMode paramPageViewMode) {
/*       */     this.cy = paramPageViewMode;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public PageViewMode getPreferredViewMode() {
/*       */     return this.cy;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setVerticalAlign(int paramInt) throws PDFNetException {
/*       */     SetVerticalAlign(this.cY, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setHorizontalAlign(int paramInt) throws PDFNetException {
/*       */     SetHorizontalAlign(this.cY, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private double j() {
/*       */     double d1;
/*       */     if (this.bU == PageViewMode.FIT_PAGE || this.bU == PageViewMode.FIT_WIDTH || this.bU == PageViewMode.FIT_HEIGHT) {
/*       */       d1 = GetRefZoom(this.cY, this.bU.getValue());
/*       */     } else {
/*       */       d1 = GetRefZoom(this.cY, getPageRefViewMode().getValue());
/*       */     } 
/*       */     return d1;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private double a(double paramDouble) {
/*       */     if (paramDouble < this.bm) {
/*       */       return this.bm;
/*       */     }
/*       */     return paramDouble;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private double k() {
/*       */     double d1 = 1.0D;
/*       */     if (this.cy == PageViewMode.FIT_PAGE || this.cy == PageViewMode.FIT_WIDTH || this.cy == PageViewMode.FIT_HEIGHT) {
/*       */       d1 = GetRefZoom(this.cY, this.cy.getValue());
/*       */     }
/*       */     return d1;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private double c(int paramInt) {
/*       */     double d1 = 1.0D;
/*       */     if (this.cy == PageViewMode.FIT_PAGE || this.cy == PageViewMode.FIT_WIDTH || this.cy == PageViewMode.FIT_HEIGHT) {
/*       */       d1 = GetRefZoomForPage(this.cY, this.cy.getValue(), paramInt);
/*       */     }
/*       */     return d1;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPageSpacingDP(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*       */     setPageSpacing((int)(a(paramInt1) + 0.5F), (int)(a(paramInt2) + 0.5F), (int)(a(paramInt3) + 0.5F), (int)(a(paramInt4) + 0.5F));
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPageSpacing(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*       */     this.G = paramInt1;
/*       */     this.H = paramInt2;
/*       */     this.I = paramInt3;
/*       */     SetPageSpacing(this.cY, paramInt1, paramInt2, paramInt3, paramInt4);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setDisplayCutout(int paramInt1, int paramInt2) {
/*       */     this.E = paramInt1;
/*       */     this.F = paramInt2;
/*       */     setPageSpacing(this.G, this.H, this.I, Math.max(paramInt1, paramInt2));
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public int getDisplayCutoutTop() {
/*       */     return this.E;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public int getDisplayCutoutBottom() {
/*       */     return this.F;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setProgressiveRendering(boolean paramBoolean) {
/*       */     this.N = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setProgressiveRendering(boolean paramBoolean, int paramInt1, int paramInt2) {
/*       */     this.N = paramBoolean;
/*       */     if (this.N) {
/*       */       this.O = (paramInt1 > 0) ? paramInt1 : this.O;
/*       */       this.P = (paramInt2 > 0) ? paramInt2 : this.P;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setupThumbnails(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, long paramLong, double paramDouble) throws PDFNetException {
/*       */     cancelRendering();
/*       */     this.m.c();
/*       */     SetupThumbnails(this.cY, paramBoolean1, paramBoolean2, paramBoolean3, paramInt, paramLong, paramDouble);
/*       */     this.dd.sendEmptyMessage(0);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void clearThumbCache() throws PDFNetException {
/*       */     ClearThumbCache(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public void setOCGContext(Context paramContext) throws PDFNetException {
/*       */     if (paramContext == null) {
/*       */       SetOCGContext(this.cY, 0L);
/*       */       return;
/*       */     } 
/*       */     SetOCGContext(this.cY, paramContext.__GetHandle());
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void updateOCGContext() throws PDFNetException {
/*       */     UpdateOCGContext(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public Context getOCGContext() throws PDFNetException {
/*       */     return Context.__Create(GetOCGContext(this.cY), this);
/*       */   }
/*       */ 
/*       */   
/*       */   public void cancelAllThumbRequests() throws PDFNetException {
/*       */     if (this.cY != 0L) {
/*       */       CancelAllThumbRequests(this.cY);
/*       */     }
/*       */   }
/*       */ 
/*       */   
/*       */   public void getThumbAsync(int paramInt) throws PDFNetException {
/*       */     GetThumbAsync(this.cY, paramInt, this.cZ);
/*       */   }
/*       */ 
/*       */   
/*       */   public boolean getProgressiveRendering() {
/*       */     return this.N;
/*       */   }
/*       */ 
/*       */   
/*       */   public void setCaching(boolean paramBoolean) throws PDFNetException {
/*       */     SetCaching(this.cY, paramBoolean);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setUrlExtraction(boolean paramBoolean) throws PDFNetException {
/*       */     SetUrlExtraction(this.cY, paramBoolean);
/*       */   }
/*       */ 
/*       */   
/*       */   public LinkInfo getLinkAt(int paramInt1, int paramInt2) throws PDFNetException {
/*       */     return GetLinkAt(this.cY, paramInt1, paramInt2);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setRenderedContentCacheSize(long paramLong) throws PDFNetException {
/*       */     SetMemInfo(this.cY, 0.0D, (paramLong << 10L << 10L));
/*       */   }
/*       */ 
/*       */   
/*       */   public void setImageSmoothing(boolean paramBoolean) throws PDFNetException {
/*       */     SetImageSmoothing(this.cY, paramBoolean);
/*       */     update();
/*       */   }
/*       */ 
/*       */   
/*       */   public boolean getRightToLeftLanguage() {
/*       */     return this.cj;
/*       */   }
/*       */ 
/*       */   
/*       */   public void setRightToLeftLanguage(boolean paramBoolean) throws PDFNetException {
/*       */     if (paramBoolean != this.cj) {
/*       */       this.cj = paramBoolean;
/*       */       SetRightToLeftLanguage(this.cY, paramBoolean);
/*       */       updatePageLayout();
/*       */       update(true);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public int getPageBox() throws PDFNetException {
/*       */     return GetPageBox(this.cY);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setPageBox(int paramInt) throws PDFNetException {
/*       */     SetPageBox(this.cY, paramInt);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setDefaultPageColor(int paramInt1, int paramInt2, int paramInt3) throws PDFNetException {
/*       */     setDefaultPageColor(Color.argb(255, paramInt1, paramInt2, paramInt3));
/*       */   }
/*       */ 
/*       */   
/*       */   public void setDefaultPageColor(int paramInt) throws PDFNetException {
/*       */     int n = Color.red(paramInt);
/*       */     int i1 = Color.green(paramInt);
/*       */     int i2 = Color.blue(paramInt);
/*       */     paramInt = Color.argb(paramInt = Color.alpha(paramInt), n, i1, i2);
/*       */     if (this.x != paramInt) {
/*       */       SetDefaultPageColor(this.cY, (byte)n, (byte)i1, (byte)i2);
/*       */       this.y = this.x = paramInt;
/*       */       this.am.setColor(this.x);
/*       */       update();
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public void setClientBackgroundColor(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws PDFNetException {
/*       */     paramInt1 = Color.argb(paramBoolean ? 0 : 255, paramInt1, paramInt2, paramInt3);
/*       */     if (this.z != paramInt1) {
/*       */       paramInt2 = Color.red(paramInt1);
/*       */       paramInt3 = Color.green(paramInt1);
/*       */       int n = Color.blue(paramInt1);
/*       */       int i1 = Color.alpha(paramInt1);
/*       */       SetBackgroundColor(this.cY, (byte)paramInt2, (byte)paramInt3, (byte)n, (byte)i1);
/*       */       this.z = paramInt1;
/*       */       setBackgroundColor(this.z);
/*       */       this.ao.setColor(this.z);
/*       */       invalidate();
/*       */       update();
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public void hideAnnotation(Annot paramAnnot) {
/*       */     if (this.cA) {
/*       */       this.M.add(Long.valueOf(paramAnnot.__GetHandle()));
/*       */       invalidate();
/*       */       return;
/*       */     } 
/*       */     HideAnnotation(this.cY, paramAnnot.a);
/*       */   }
/*       */ 
/*       */   
/*       */   public void showAnnotation(Annot paramAnnot) {
/*       */     if (this.cA) {
/*       */       this.M.remove(Long.valueOf(paramAnnot.__GetHandle()));
/*       */       invalidate();
/*       */       return;
/*       */     } 
/*       */     ShowAnnotation(this.cY, paramAnnot.a);
/*       */   }
/*       */ 
/*       */   
/*       */   public int getClientBackgroundColor() {
/*       */     return this.z;
/*       */   }
/*       */ 
/*       */   
/*       */   public void setDrawAnnotations(boolean paramBoolean) throws PDFNetException {
/*       */     SetDrawAnnotations(this.cY, paramBoolean);
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setAntiAliasing(boolean paramBoolean) throws PDFNetException {
/*       */     SetAntiAliasing(this.cY, paramBoolean);
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setHighlightFields(boolean paramBoolean) throws PDFNetException {
/*       */     SetHighlightFields(this.cY, paramBoolean);
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setFieldHighlightColor(ColorPt paramColorPt) throws PDFNetException {
/*       */     SetFieldHighlightColor(this.cY, paramColorPt.__GetHandle());
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setSignatureHighlightColor(ColorPt paramColorPt) throws PDFNetException {
/*       */     SetSignatureHighlightColor(this.cY, paramColorPt.__GetHandle());
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setRequiredFieldBorderColor(ColorPt paramColorPt) throws PDFNetException {
/*       */     SetRequiredFieldBorderColor(this.cY, paramColorPt.__GetHandle());
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setPathHinting(boolean paramBoolean) throws PDFNetException {
/*       */     SetPathHinting(this.cY, paramBoolean);
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setPageBorderVisibility(boolean paramBoolean) throws PDFNetException {
/*       */     SetPageBorderVisibility(this.cY, paramBoolean);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setPageTransparencyGrid(boolean paramBoolean) throws PDFNetException {
/*       */     SetPageTransparencyGrid(this.cY, paramBoolean);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setThinLineAdjustment(boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/*       */     SetThinLineAdjustment(this.cY, paramBoolean1, paramBoolean2);
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setOverprint(OverPrintMode paramOverPrintMode) throws PDFNetException {
/*       */     SetOverprint(this.cY, paramOverPrintMode.getValue());
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setGamma(double paramDouble) throws PDFNetException {
/*       */     SetGamma(this.cY, paramDouble);
/*       */     update(true);
/*       */   }
/*       */ 
/*       */   
/*       */   public void setNextOnLayoutAdjustments(int paramInt1, int paramInt2, boolean paramBoolean) {
/*       */     this.cm = paramInt1;
/*       */     this.cn = paramInt2;
/*       */     this.co = paramBoolean;
/*       */   }
/*       */ 
/*       */   
/*       */   public void getContentSize(int[] paramArrayOfint) {
/*       */     double[] arrayOfDouble = a(0.0D, 0.0D, this.aD, this.aE);
/*       */     int n = 0;
/*       */     int i1 = 0;
/*       */     int i2 = 0;
/*       */     int i3 = 0;
/*       */     if (arrayOfDouble.length > 0) {
/*       */       n = (int)Math.min(arrayOfDouble[1], arrayOfDouble[3]);
/*       */       i1 = (int)Math.max(arrayOfDouble[1], arrayOfDouble[3]);
/*       */       i2 = (int)Math.min(arrayOfDouble[2], arrayOfDouble[4]);
/*       */       i3 = (int)Math.max(arrayOfDouble[2], arrayOfDouble[4]);
/*       */       byte b1;
/*       */       int i4;
/*       */       for (b1 = 5, i4 = arrayOfDouble.length; b1 < i4; b1 += 5) {
/*       */         n = (int)Math.min(arrayOfDouble[b1 + 1], Math.min(arrayOfDouble[b1 + 3], n));
/*       */         i1 = (int)Math.max(arrayOfDouble[b1 + 1], Math.max(arrayOfDouble[b1 + 3], i1));
/*       */         i2 = (int)Math.min(arrayOfDouble[b1 + 2], Math.min(arrayOfDouble[b1 + 4], i2));
/*       */         i3 = (int)Math.max(arrayOfDouble[b1 + 2], Math.max(arrayOfDouble[b1 + 4], i3));
/*       */       } 
/*       */     } 
/*       */     int[] arrayOfInt = GetPageSpacing(this.cY);
/*       */     paramArrayOfint[0] = i1 - n + arrayOfInt[2] + arrayOfInt[2];
/*       */     paramArrayOfint[1] = i3 - i2 + arrayOfInt[3] + arrayOfInt[3];
/*       */   }
/*       */ 
/*       */   
/*       */   public void setDevicePixelDensity(double paramDouble1, double paramDouble2) {
/*       */     SetDevicePixelDensity(this.cY, paramDouble1, paramDouble2);
/*       */   }
/*       */ 
/*       */   
/*       */   public Rect getScreenRectForAnnot(Annot paramAnnot, int paramInt) throws PDFNetException {
/*       */     return new Rect(GetScreenRectForAnnot(this.cY, paramAnnot.a, paramInt));
/*       */   }
/*       */ 
/*       */   
/*       */   public Rect getPageRectForAnnot(Annot paramAnnot, int paramInt) throws PDFNetException {
/*       */     Rect rect = getScreenRectForAnnot(paramAnnot, paramInt);
/*       */     double arrayOfDouble2[], d1 = (arrayOfDouble2 = convScreenPtToPagePt(rect.getX1(), rect.getY1(), paramInt))[0];
/*       */     double d2 = arrayOfDouble2[1];
/*       */     double arrayOfDouble1[], d3 = (arrayOfDouble1 = convScreenPtToPagePt(rect.getX2(), rect.getY2(), paramInt))[0];
/*       */     double d4 = arrayOfDouble1[1];
/*       */     double d5 = (d1 < d3) ? d1 : d3;
/*       */     double d6 = (d1 > d3) ? d1 : d3;
/*       */     double d7 = (d2 < d4) ? d2 : d4;
/*       */     double d8 = (d2 > d4) ? d2 : d4;
/*       */     return new Rect(d5, d7, d6, d8);
/*       */   }
/*       */ 
/*       */   
/*       */   public Annot getAnnotationAt(int paramInt1, int paramInt2) {
/*       */     return getAnnotationAt(paramInt1, paramInt2, 15.0D, 7.0D);
/*       */   }
/*       */ 
/*       */   
/*       */   public Annot getAnnotationAt(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2) {
/*       */     if (this.mDoc == null) {
/*       */       return null;
/*       */     }
/*       */     double d1 = a((float)paramDouble1);
/*       */     double d2 = a((float)paramDouble2);
/*       */     try {
/*       */       return new Annot(GetAnnotationAt(this.cY, paramInt1, paramInt2, d1, d2), this);
/*       */     } catch (Exception exception) {
/*       */       return null;
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public ArrayList<Annot> getAnnotationListAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*       */     ArrayList<Annot> arrayList = new ArrayList();
/*       */     try {
/*       */       long[] arrayOfLong;
/*       */       for (paramInt2 = (arrayOfLong = arrayOfLong = GetAnnotationListAt(this.cY, paramInt1, paramInt2, paramInt3, paramInt4)).length, paramInt3 = 0; paramInt3 < paramInt2; ) {
/*       */         long l1 = arrayOfLong[paramInt3];
/*       */         arrayList.add(new Annot(l1, this));
/*       */         paramInt3++;
/*       */       } 
/*       */     } catch (Exception exception) {}
/*       */     return arrayList;
/*       */   }
/*       */ 
/*       */   
/*       */   public ArrayList<Annot> getAnnotationsOnPage(int paramInt) {
/*       */     ArrayList<Annot> arrayList = new ArrayList();
/*       */     try {
/*       */       long[] arrayOfLong;
/*       */       int n;
/*       */       byte b1;
/*       */       for (n = (arrayOfLong = arrayOfLong = GetAnnotationsOnPage(this.cY, paramInt)).length, b1 = 0; b1 < n; ) {
/*       */         long l1 = arrayOfLong[b1];
/*       */         arrayList.add(new Annot(l1, this));
/*       */         b1++;
/*       */       } 
/*       */     } catch (Exception exception) {}
/*       */     return arrayList;
/*       */   }
/*       */ 
/*       */   
/*       */   public CurvePainter getAnnotationPainter(int paramInt, long paramLong) {
/*       */     this.j.lock();
/*       */     try {
/*       */       e e1;
/*       */       if ((e1 = this.l.a(paramInt, paramLong, 1)) != null && e1.n != null) {
/*       */         return CurvePainter.create(e1.n);
/*       */       }
/*       */       return null;
/*       */     } finally {
/*       */       this.j.unlock();
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public void setColorPostProcessMode(int paramInt) throws PDFNetException {
/*       */     if (paramInt != getColorPostProcessMode()) {
/*       */       SetColorPostProcessMode(this.cY, paramInt);
/*       */       if (paramInt == 3) {
/* 25117 */         int n = Color.alpha(paramInt = this.y);
/* 25118 */         int i1 = Color.red(paramInt);
/* 25119 */         int i2 = Color.green(paramInt);
/* 25120 */         paramInt = Color.blue(paramInt);
/*       */         
/* 25122 */         this.x = Color.argb(n, 255 - i1, 255 - i2, 255 - paramInt);
/*       */         this.am.setColor(this.x);
/*       */       } else if (paramInt == 0) {
/*       */         this.x = this.y;
/*       */         this.am.setColor(this.x);
/*       */       } 
/*       */       update();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setColorPostProcessColors(int paramInt1, int paramInt2) throws PDFNetException {
/*       */     SetColorPostProcessColors(this.cY, paramInt1, paramInt2);
/*       */     this.x = paramInt1;
/*       */     this.am.setColor(this.x);
/*       */     update();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setColorPostProcessMapFile(Filter paramFilter) throws PDFNetException {
/*       */     SetColorPostProcessMapFile(this.cY, paramFilter.__GetHandle());
/*       */     this.x = -5422;
/*       */     this.am.setColor(this.x);
/*       */     update();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ColorPt getPostProcessedColor(ColorPt paramColorPt) {
/*       */     return new ColorPt(GetPostProcessedColor(this.cY, paramColorPt.__GetHandle()));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isUndoRedoEnabled() {
/*       */     return this.cz;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void enableUndoRedo() throws PDFNetException {
/*       */     EnableUndoRedo(this.cY);
/*       */     this.cz = true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String undo() throws PDFNetException {
/*       */     if (this.e != null) {
/*       */       return this.e.undo();
/*       */     }
/*       */     return Undo(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String redo() throws PDFNetException {
/*       */     if (this.e != null) {
/*       */       return this.e.redo();
/*       */     }
/*       */     return Redo(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String takeUndoSnapshot(String paramString) throws PDFNetException {
/*       */     if (this.e != null) {
/*       */       return this.e.takeSnapshot(paramString);
/*       */     }
/*       */     TakeSnapshot(this.cY, paramString);
/*       */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getNextUndoInfo() throws PDFNetException {
/*       */     if (this.e != null) {
/*       */       return this.e.getNextUndoInfo();
/*       */     }
/*       */     return GetNextUndoInfo(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getNextRedoInfo() throws PDFNetException {
/*       */     if (this.e != null) {
/*       */       return this.e.getNextRedoInfo();
/*       */     }
/*       */     return GetNextRedoInfo(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void revertAllChanges() throws PDFNetException {
/*       */     RevertAllChanges(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ExternalAnnotManager enableExternalAnnotManager(String paramString) throws PDFNetException {
/*       */     this.e = new ExternalAnnotManager(GetExternalAnnotManager(this.cY, paramString));
/*       */     return this.e;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ExternalAnnotManager getExternalAnnotManager() throws PDFNetException, NullPointerException {
/*       */     if (this.e == null) {
/*       */       throw new NullPointerException("ExternalAnnotManager is not set");
/*       */     }
/*       */     return this.e;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void enableAnnotationLayer() {
/*       */     EnableFloatingAnnotTiles(this.cY, this.cZ, 33);
/*       */     this.cA = true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isAnnotationLayerEnabled() {
/*       */     return this.cA;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getColorPostProcessMode() throws PDFNetException {
/*       */     return GetColorPostProcessMode(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean a(Obj paramObj, HashSet<Long> paramHashSet) {
/*       */     boolean bool = false;
/*       */     try {
/*       */       if (paramObj != null) {
/*       */         Long long_ = Long.valueOf(paramObj.__GetHandle());
/*       */         if (!paramHashSet.contains(long_)) {
/*       */           paramHashSet.add(long_);
/*       */           Action action;
/*       */           int n;
/*       */           bool = ((n = (action = new Action(paramObj)).getType()) == 0 || n == 9 || a(action.GetNext(), paramHashSet)) ? true : false;
/*       */         } 
/*       */       } 
/*       */     } catch (PDFNetException pDFNetException2) {
/*       */       PDFNetException pDFNetException1;
/*       */       (pDFNetException1 = null).printStackTrace();
/*       */     } 
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void executeAction(Action paramAction) throws PDFNetException {
/*       */     ActionParameter actionParameter = new ActionParameter(paramAction);
/*       */     executeAction(actionParameter);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void executeAction(ActionParameter paramActionParameter) throws PDFNetException {
/*       */     int n = getCurrentPage();
/*       */     Action action;
/*       */     Obj obj = (action = paramActionParameter.getAction()).getSDFObj();
/*       */     HashSet<Long> hashSet = new HashSet();
/*       */     double d1 = getZoom();
/*       */     ExecuteAction(this.cY, paramActionParameter.a);
/*       */     if (a(obj, hashSet)) {
/*       */       double d6;
/*       */       if (!this.cv) {
/*       */         d1 = getZoom();
/*       */       }
/*       */       int[] arrayOfInt = GetVisiblePages(this.cY);
/*       */       int i2 = -1;
/*       */       if (arrayOfInt.length > 0) {
/*       */         i2 = arrayOfInt[0];
/*       */       }
/*       */       double d2 = 0.0D;
/*       */       double d3 = 0.0D;
/*       */       if (i2 > 0) {
/*       */         double[] arrayOfDouble;
/*       */         d2 = (arrayOfDouble = convScreenPtToPagePt(0.0D, 0.0D, i2))[0];
/*       */         d3 = arrayOfDouble[1];
/*       */       } 
/*       */       int i1;
/*       */       if ((i1 = getCurrentPage()) != n) {
/*       */         setCurrentPage(i1);
/*       */       }
/*       */       PDFViewCtrl pDFViewCtrl;
/* 25698 */       if ((pDFViewCtrl = this).bj == ZoomLimitMode.RELATIVE) {
/* 25699 */         if (pDFViewCtrl.cv) {
/* 25700 */           d6 = pDFViewCtrl.bh * pDFViewCtrl.bn;
/*       */         } else {
/* 25702 */           d6 = pDFViewCtrl.bh * pDFViewCtrl.bk;
/*       */         } 
/*       */       } else {
/* 25705 */         d6 = pDFViewCtrl.bh;
/*       */       } 
/*       */       
/* 25708 */       double d4 = d6;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 25714 */       if ((pDFViewCtrl = this).bj == ZoomLimitMode.RELATIVE) {
/* 25715 */         if (pDFViewCtrl.cv) {
/* 25716 */           d6 = pDFViewCtrl.bi * pDFViewCtrl.bo;
/*       */         } else {
/* 25718 */           d6 = pDFViewCtrl.bi * pDFViewCtrl.bl;
/*       */         } 
/*       */       } else {
/* 25721 */         d6 = pDFViewCtrl.bi;
/*       */       } 
/*       */       
/* 25724 */       double d5 = d6;
/*       */       if (d1 < d4) {
/*       */         d1 = d4;
/*       */       } else if (d1 > d5) {
/*       */         d1 = d5;
/*       */       } 
/*       */       setZoom(d1);
/*       */       if (i2 > 0) {
/*       */         double[] arrayOfDouble;
/*       */         d2 = (arrayOfDouble = convPagePtToScreenPt(d2, d3, i2))[0];
/*       */         d3 = arrayOfDouble[1];
/*       */         scrollTo((int)d2 + p(), (int)d3 + q());
/*       */       } 
/*       */       this.bk = j();
/*       */       this.bl = a(this.bk);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void setViewerCache(SDFDoc paramSDFDoc, int paramInt, boolean paramBoolean) throws PDFNetException {
/*       */     SetViewerCache(paramSDFDoc.__GetHandle(), paramInt, paramBoolean);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void rotateClockwise() {
/*       */     RotateClockwise(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void rotateCounterClockwise() {
/*       */     RotateCounterClockwise(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getPageRotation() {
/*       */     return GetRotation(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getHScrollPos() {
/*       */     return isMaintainZoomEnabled() ? p() : getScrollX();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @TargetApi(14)
/*       */   public void setHScrollPos(int paramInt) {
/*       */     setScrollX(paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getVScrollPos() {
/*       */     return isMaintainZoomEnabled() ? q() : getScrollY();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @TargetApi(14)
/*       */   public void setVScrollPos(int paramInt) {
/*       */     setScrollY(paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Matrix2D getDeviceTransform() {
/*       */     return Matrix2D.__Create(GetDeviceTransform(this.cY, -1));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Matrix2D getDeviceTransform(int paramInt) {
/*       */     return Matrix2D.__Create(GetDeviceTransform(this.cY, paramInt));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setZoomEnabled(boolean paramBoolean) {
/*       */     this.ck = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean getZoomEnabled() {
/*       */     return this.ck;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void finalize() throws Throwable {
/*       */     super.finalize();
/*       */     destroy();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void l() {
/*       */     this.dd.removeCallbacksAndMessages(null);
/*       */     this.dg.removeCallbacksAndMessages(null);
/*       */     this.dj.removeCallbacksAndMessages(null);
/*       */     this.di.removeCallbacksAndMessages(null);
/*       */     this.dh.removeCallbacksAndMessages(null);
/*       */     this.dk.removeCallbacksAndMessages(null);
/*       */     this.dl.removeCallbacksAndMessages(null);
/*       */     this.dr.removeCallbacksAndMessages(null);
/*       */     this.dm.removeCallbacksAndMessages(null);
/*       */     this.dn.removeCallbacksAndMessages(null);
/*       */     this.df.removeCallbacksAndMessages(null);
/*       */     this.do.removeCallbacksAndMessages(null);
/*       */     this.dp.removeCallbacksAndMessages(null);
/*       */     this.dq.removeCallbacksAndMessages(null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(float paramFloat1, float paramFloat2) {
/*       */     this.bp = getPageNumberFromScreenPt(paramFloat1, paramFloat2);
/*       */     if (this.bp <= 0) {
/*       */       this.bp = getCurrentPage();
/*       */     }
/*       */     double[] arrayOfDouble = convScreenPtToPagePt(paramFloat1, paramFloat2, this.bp);
/*       */     this.bq = (float)arrayOfDouble[0];
/*       */     this.br = (float)arrayOfDouble[1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean a(long paramLong, int paramInt1, int paramInt2) {
/*       */     boolean bool = true;
/*       */     try {
/*       */       bool = DownloaderUpdatePage(this.cY, paramLong, paramInt1, paramInt2);
/*       */     } catch (Exception exception) {}
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean e(long paramLong) {
/*       */     try {
/*       */       return DownloaderIsCorrectDoc(this.cY, paramLong);
/*       */     } catch (Exception exception) {
/*       */       return false;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean a(int paramInt1, int paramInt2) {
/*       */     try {
/*       */       if (this.mDoc != null) {
/*       */         int n = getCurrentPage();
/*       */         boolean bool = false;
/*       */         boolean bool1 = !isContinuousPagePresentationMode(this.bV) ? true : false;
/*       */         switch (paramInt1) {
/*       */           case -2:
/*       */             bool = GotoFirstPage(this.cY);
/*       */             break;
/*       */           case -1:
/*       */             bool = GotoPreviousPage(this.cY);
/*       */             break;
/*       */           case 0:
/*       */             bool = SetCurrentPage(this.cY, paramInt2);
/*       */             break;
/*       */           case 1:
/*       */             bool = GotoNextPage(this.cY);
/*       */             break;
/*       */           case 2:
/*       */             bool = GotoLastPage(this.cY);
/*       */             break;
/*       */         } 
/*       */         if (bool) {
/*       */           if (!this.f.isFinished()) {
/*       */             this.f.forceFinished(true);
/*       */           }
/*       */           if (bool1) {
/*       */             PDFViewCtrl pDFViewCtrl;
/* 26868 */             this.aD = GetTilingRegionWidth((pDFViewCtrl = this).cY);
/*       */ 
/*       */ 
/*       */ 
/*       */             
/* 26873 */             this.aE = GetTilingRegionHeight((pDFViewCtrl = this).cY);
/*       */             if (!this.r) {
/*       */               if (n != paramInt2 && !this.cv) {
/*       */                 if (!j.o(this.ce) || (getPagePresentationMode() != PagePresentationMode.FACING_COVER && getPagePresentationMode() != PagePresentationMode.FACING && getPagePresentationMode() != PagePresentationMode.FACING_COVER_VERT && getPagePresentationMode() != PagePresentationMode.FACING_VERT) || Math.abs(n - paramInt2) != 1) {
/*       */                   this.bk = j();
/*       */                   this.bl = a(this.bk);
/*       */                 } 
/*       */               }
/*       */               this.bI = PageChangeState.END;
/*       */               this.dg.removeMessages(0);
/*       */               this.dg.sendEmptyMessage(0);
/*       */               j.d(this.ce, false);
/*       */               int i1 = getCurrentPage();
/*       */               r();
/*       */               if (this.bS != null) {
/*       */                 this.bS.onPageTurning(j.y(this.ce), i1);
/*       */               }
/*       */             } 
/*       */           } 
/*       */           paramInt1 = h(n);
/*       */           n = h(paramInt2);
/*       */           if (this.cv && paramInt1 != n) {
/*       */             this.cw.put(paramInt1, p());
/*       */             this.cx.put(paramInt1, q());
/*       */           } 
/*       */           if (!this.r) {
/*       */             int i1 = this.cw.get(n, -2147483648);
/*       */             int i2 = this.cx.get(n, -2147483648);
/*       */             if (bool1 && this.cv && n != paramInt1 && i1 != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
/*       */               scrollTo(i1, i2);
/*       */             } else {
/*       */               scrollTo(p(), q());
/*       */             } 
/*       */             invalidate();
/*       */           } 
/*       */           if (this.cv) {
/*       */             this.bn = Math.min(this.bn, c(paramInt2));
/*       */             this.bo = Math.max(this.bo, c(paramInt2));
/*       */             this.bo = a(this.bo);
/*       */           } else {
/*       */             this.bk = j();
/*       */             this.bl = a(this.bk);
/*       */           } 
/*       */         } 
/*       */         return bool;
/*       */       } 
/*       */     } catch (Exception exception2) {
/*       */       Exception exception1;
/*       */       (exception1 = null).printStackTrace();
/*       */     } 
/*       */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void b(float paramFloat1, float paramFloat2) {
/*       */     this.bb = (float)getZoom();
/*       */     this.bc = paramFloat1;
/*       */     this.bd = paramFloat2;
/*       */     if (!this.w && this.n != null && this.J.size() > 0) {
/*       */       return;
/*       */     }
/*       */     PDFViewCtrl pDFViewCtrl = this;
/*       */     try {
/* 26987 */       pDFViewCtrl.k.lock();
/*       */       try {
/* 26989 */         if (pDFViewCtrl.n == null || pDFViewCtrl.n.getWidth() != pDFViewCtrl.aB || pDFViewCtrl.n.getHeight() != pDFViewCtrl.aC) {
/*       */           ImageCache imageCache;
/* 26991 */           (imageCache = 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/* 27562 */             ImageCache.a.a()).a(pDFViewCtrl.n);
/*       */           Bitmap bitmap;
/*       */           if ((bitmap = imageCache.a(pDFViewCtrl.aB, pDFViewCtrl.aC)) == null) {
/*       */             bitmap = Bitmap.createBitmap(pDFViewCtrl.aB, pDFViewCtrl.aC, Bitmap.Config.ARGB_8888);
/*       */           }
/*       */           pDFViewCtrl.n = bitmap;
/*       */         } 
/*       */         boolean bool;
/*       */         if (bool = pDFViewCtrl.a(pDFViewCtrl.n)) {
/*       */           pDFViewCtrl.w = false;
/*       */           pDFViewCtrl.v = pDFViewCtrl.getCurCanvasId();
/*       */           if (pDFViewCtrl.J.size() == 0) {
/*       */             Log.e("DRAW", "Error, we got a snapshot, but rects are empty");
/*       */           }
/*       */         } else {
/*       */           Log.e("DRAW", "Error, couldn't take a snapshot");
/*       */           pDFViewCtrl.de.a();
/*       */         } 
/*       */       } finally {
/*       */         pDFViewCtrl.k.unlock();
/*       */       } 
/*       */     } catch (Exception exception) {
/*       */       return;
/*       */     } catch (OutOfMemoryError outOfMemoryError) {
/*       */       if (b) {
/*       */         a(4, "No snapshot due to out of memory", a(true));
/*       */       } else {
/*       */         Log.e("PDFNet", "No snapshot due to out of memory");
/*       */       } 
/*       */       pDFViewCtrl.de.a();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void m() {
/*       */     double[] arrayOfDouble;
/*       */     float f1 = (f1 = (float)(arrayOfDouble = convPagePtToScreenPt(this.aX, this.aY, this.aZ))[1]) - this.aU;
/*       */     scrollBy(0, (int)f1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void d(int paramInt) {
/*       */     this.aP.clear();
/*       */     paramInt = getScrollY() - paramInt;
/*       */     double[] arrayOfDouble;
/*       */     int n = (arrayOfDouble = a(0.0D, (paramInt - 5 * this.aC), this.aD, (paramInt + 6 * this.aC))).length / 5;
/*       */     int i1 = 0;
/*       */     int i2 = 0;
/*       */     if (a(this.bV)) {
/*       */       if (this.cv) {
/*       */         this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       }
/*       */       if (n()) {
/*       */         i2 = this.aL;
/*       */       }
/*       */     } else {
/*       */       if (this.cv) {
/*       */         this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */       }
/*       */       if (n()) {
/*       */         i1 = this.aH;
/*       */       }
/*       */     } 
/*       */     for (byte b1 = 0; b1 < n; b1++) {
/*       */       int i3 = b1 * 5;
/*       */       int i4 = (int)arrayOfDouble[i3];
/*       */       float f2 = (float)arrayOfDouble[i3 + 1];
/*       */       float f3 = (float)arrayOfDouble[i3 + 2];
/*       */       float f4 = (float)arrayOfDouble[i3 + 3];
/*       */       float f1 = (float)arrayOfDouble[i3 + 4];
/*       */       this.aP.put(i4, new RectF(f2 - getScrollX() + i1, f3 - getScrollY() + i2, f4 - getScrollX() + i1, f1 - getScrollY() + i2));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void c(float paramFloat1, float paramFloat2) {
/*       */     this.cD.d();
/*       */     this.aS = (float)getZoom();
/*       */     this.aT = paramFloat1;
/*       */     this.aU = paramFloat2;
/*       */     this.ba = getCurrentPage();
/*       */     double[] arrayOfDouble = convScreenPtToCanvasPt(this.aT, this.aU);
/*       */     this.aV = (float)arrayOfDouble[0];
/*       */     this.aW = (float)arrayOfDouble[1];
/*       */     this.aZ = getPageNumberFromScreenPt(this.aT, this.aU);
/*       */     arrayOfDouble = convScreenPtToPagePt(this.aT, this.aU, this.aZ);
/*       */     this.aX = (float)arrayOfDouble[0];
/*       */     this.aY = (float)arrayOfDouble[1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean a(float paramFloat1, float paramFloat2, double paramDouble) {
/*       */     b(paramFloat1, paramFloat2);
/*       */     this.K.clear();
/*       */     paramDouble = b(paramDouble);
/*       */     boolean bool = SetZoom(this.cY, (int)this.bc, (int)this.bd, paramDouble, false);
/*       */     c();
/*       */     scrollTo(p(), q());
/*       */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private double b(double paramDouble) {
/*       */     double d1;
/*       */     double d2;
/*       */     if (this.bj == ZoomLimitMode.NONE) {
/*       */       return paramDouble;
/*       */     }
/*       */     if (this.bj == ZoomLimitMode.RELATIVE) {
/*       */       if (this.cv) {
/*       */         d1 = this.bh * this.bn;
/*       */         d2 = this.bi * this.bo;
/*       */       } else {
/*       */         d1 = this.bh * this.bk;
/*       */         d2 = this.bi * this.bl;
/*       */       } 
/*       */     } else {
/*       */       d1 = this.bh;
/*       */       d2 = this.bi;
/*       */     } 
/*       */     if (d1 >= 0.0D && paramDouble < d1) {
/*       */       paramDouble = d1;
/*       */       if (a) {
/*       */         Log.d(c, "scale: hit MIN_ZOOM");
/*       */       }
/*       */       this.bw = true;
/*       */     } else {
/*       */       this.bw = false;
/*       */     } 
/*       */     if (d2 >= 0.0D && paramDouble > d2) {
/*       */       paramDouble = d2;
/*       */       if (a) {
/*       */         Log.d(c, "scale: hit MAX_ZOOM");
/*       */       }
/*       */       this.bx = true;
/*       */     } else {
/*       */       this.bx = false;
/*       */     } 
/*       */     return paramDouble;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean n() {
/*       */     boolean bool;
/*       */     if (bool = isContinuousPagePresentationMode(this.bV)) {
/*       */       return false;
/*       */     }
/*       */     if (a(this.bV)) {
/*       */       return (j.a(this.ce) || this.cv || this.aC == this.aE);
/*       */     }
/*       */     return (j.a(this.ce) || this.cv || this.aB == this.aD);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean o() {
/*       */     boolean bool;
/*       */     if (bool = isContinuousPagePresentationMode(this.bV)) {
/*       */       return false;
/*       */     }
/*       */     return ((this.aB == this.aD && this.aC == this.aE) || j.a(this.ce));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean a(int paramInt, boolean paramBoolean) {
/*       */     boolean bool;
/*       */     if (bool = isContinuousPagePresentationMode(this.bV)) {
/*       */       return false;
/*       */     }
/*       */     if (paramBoolean) {
/*       */       return (paramInt < getPageCount());
/*       */     }
/*       */     return (paramInt > 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private float a(float paramFloat) {
/*       */     float f1 = (getContext().getResources().getDisplayMetrics()).density;
/*       */     return paramFloat * f1;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getPageCount() {
/*       */     return GetPagesCount(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int[] getVisiblePages() {
/*       */     return GetVisiblePages(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int[] getVisiblePagesInTransition() {
/*       */     if (j.a(this.ce)) {
/*       */       int i1 = (this.aB == this.aD || this.cv) ? getScrollX() : this.aI;
/*       */       int i3 = (this.aC == this.aE || this.cv) ? getScrollY() : this.aM;
/*       */       i3 = i3;
/*       */       PDFViewCtrl pDFViewCtrl2 = pDFViewCtrl1;
/*       */       PDFViewCtrl pDFViewCtrl1;
/* 28329 */       int n = a(this.bV) ? (pDFViewCtrl1 = this).f(i3) : (
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 29320 */         pDFViewCtrl1 = this).f(pDFViewCtrl2);
/*       */       int i2 = j.a(this.ce, n);
/*       */       int i4 = j.b(this.ce, n);
/*       */       ArrayList<Integer> arrayList = new ArrayList();
/*       */       for (Integer integer : g(i2)) {
/*       */         if (!arrayList.contains(integer)) {
/*       */           arrayList.add(integer);
/*       */         }
/*       */       } 
/*       */       for (Integer integer : g(n)) {
/*       */         if (!arrayList.contains(integer)) {
/*       */           arrayList.add(integer);
/*       */         }
/*       */       } 
/*       */       for (Integer integer : g(i4)) {
/*       */         if (!arrayList.contains(integer)) {
/*       */           arrayList.add(integer);
/*       */         }
/*       */       } 
/*       */       int[] arrayOfInt = new int[arrayList.size()];
/*       */       byte b1 = 0;
/*       */       for (Integer integer : arrayList) {
/*       */         arrayOfInt[b1] = integer.intValue();
/*       */         b1++;
/*       */       } 
/*       */       return arrayOfInt;
/*       */     } 
/*       */     return GetVisiblePages(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*       */     double[] arrayOfDouble;
/*       */     if ((arrayOfDouble = a(paramInt3, paramInt4, (paramInt3 + this.aB), (paramInt4 + this.aC))) == null) {
/*       */       return;
/*       */     }
/*       */     paramInt4 = arrayOfDouble.length / 5;
/*       */     int n = 0;
/*       */     int i1 = 0;
/*       */     if (n()) {
/*       */       if (a(this.bV)) {
/*       */         if (this.cv) {
/*       */           this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         i1 = this.aL;
/*       */       } else {
/*       */         if (this.cv) {
/*       */           this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         n = this.aH;
/*       */       } 
/*       */     }
/*       */     boolean bool = false;
/*       */     for (byte b1 = 0; b1 < paramInt4; b1++) {
/*       */       int i2 = b1 * 5;
/*       */       int i3 = (int)arrayOfDouble[i2];
/*       */       boolean bool1 = false;
/*       */       RectF rectF = new RectF();
/*       */       Bitmap bitmap;
/*       */       if (paramBoolean && (bitmap = a(i3, rectF)) != null) {
/*       */         float f1 = bitmap.getWidth();
/*       */         float f2 = bitmap.getHeight();
/*       */         float f3 = (float)arrayOfDouble[i2 + 1];
/*       */         float f4 = (float)arrayOfDouble[i2 + 2];
/*       */         float f5 = (float)arrayOfDouble[i2 + 3];
/*       */         float f6 = (float)arrayOfDouble[i2 + 4];
/*       */         float f7 = f5 - f3;
/*       */         float f8 = f6 - f4;
/*       */         paramCanvas.save();
/*       */         try {
/*       */           paramCanvas.clipRect(f3 + n, f4 + i1, f5 + n, f6 + i1, Region.Op.INTERSECT);
/*       */           float f10 = f1 + rectF.left + rectF.right;
/*       */           float f11 = f2 + rectF.top + rectF.bottom;
/*       */           f3 -= rectF.left * f7 / f10;
/*       */           f5 += rectF.right * f7 / f10;
/*       */           f4 -= rectF.top * f8 / f11;
/*       */           f6 += rectF.bottom * f8 / f11;
/*       */           f7 = f5 - f3;
/*       */           f8 = f6 - f4;
/*       */           float f9 = Math.max(paramInt1, f3);
/*       */           f10 = Math.max(paramInt2, f4);
/*       */           f5 = Math.min((paramInt1 + this.aB), f5);
/*       */           f6 = Math.min((paramInt2 + this.aC), f6);
/*       */           f11 = f1 * (f9 - f3) / f7;
/*       */           float f12 = f2 * (f10 - f4) / f8;
/*       */           f1 *= (f5 - f3) / f7;
/*       */           f2 *= (f6 - f4) / f8;
/*       */           this.ak.set(f11, f12, f1, f2);
/*       */           this.al.set(f9 + n, f10 + i1, f5 + n, f6 + i1);
/*       */           this.ah.setRectToRect(this.ak, this.al, Matrix.ScaleToFit.CENTER);
/*       */           this.am.setFilterBitmap(true);
/*       */           paramCanvas.drawBitmap(bitmap, this.ah, this.am);
/*       */           this.am.setFilterBitmap(false);
/*       */           if (a) {
/*       */             paramCanvas.drawRect(this.al, this.at);
/*       */           }
/*       */           bool1 = true;
/*       */         } finally {
/*       */           paramCanvas.restore();
/*       */         } 
/*       */       } 
/*       */       if (!bool1) {
/*       */         float f2 = (float)arrayOfDouble[i2 + 1] + n;
/*       */         float f1 = (float)arrayOfDouble[i2 + 2] + i1;
/*       */         float f3 = (float)arrayOfDouble[i2 + 3] + n;
/*       */         float f4 = (float)arrayOfDouble[i2 + 4] + i1;
/*       */         paramCanvas.drawRect(f2, f1, f3, f4, this.am);
/*       */         if (i3 == this.cD.b) {
/*       */           u.a(this.cD, true);
/*       */           u.a(this.cD, f2, f1, f3, f4);
/*       */           bool = true;
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     if (!bool) {
/*       */       u.a(this.cD, false);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void a(PDFDoc paramPDFDoc, boolean paramBoolean) throws PDFNetException {
/*       */     cancelRendering();
/*       */     this.j.lock();
/*       */     try {
/*       */       this.l.b();
/*       */       this.m.c();
/*       */     } finally {
/*       */       this.j.unlock();
/*       */     } 
/*       */     if (!paramBoolean) {
/*       */       try {
/*       */         SetDoc(this.cY, paramPDFDoc.__GetHandle());
/*       */         this.mDoc = paramPDFDoc;
/*       */       } catch (Exception exception) {
/*       */         this.mDoc = null;
/*       */         invalidate();
/*       */         throw new PDFNetException("", 0L, "PDFViewCtrl.java", "setDocHelper", exception.getMessage());
/*       */       } 
/*       */     } else {
/*       */       this.mDoc = (PDFDoc)exception;
/*       */     } 
/*       */     if (this.mDoc == null) {
/*       */       throw new PDFNetException("", 0L, "PDFViewCtrl.java", "setDocHelper", "PDFDoc is null");
/*       */     }
/*       */     closeTool();
/*       */     if (this.bS != null) {
/*       */       this.bQ = true;
/*       */       this.bS.onControlReady();
/*       */     } 
/*       */     this.bK = true;
/*       */     this.t = true;
/*       */     this.bg = true;
/*       */     requestLayout();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private double[] a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*       */     return GetPageRects(this.cY, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private double[] e(int paramInt) {
/*       */     return GetPageRectsOnCanvas(this.cY, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getCurCanvasId() {
/*       */     return GetCurCanvasId(this.cY);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int p() {
/*       */     return (int)(GetHScrollPos(this.cY) + 0.5D);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int q() {
/*       */     return (int)(GetVScrollPos(this.cY) + 0.5D);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Bitmap a(int paramInt, RectF paramRectF) {
/*       */     c c1;
/*       */     if ((c1 = this.m.a(paramInt)) != null) {
/*       */       return a(c1.a, paramInt, c1.b, c1.c, paramRectF);
/*       */     }
/*       */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Bitmap a(Bitmap paramBitmap, int paramInt, Rect paramRect1, Rect paramRect2, RectF paramRectF) {
/*       */     if (paramBitmap == null) {
/*       */       return null;
/*       */     }
/*       */     int n = getPageRotation();
/*       */     int i1 = 0;
/*       */     int i2 = 0;
/*       */     int i3 = paramBitmap.getWidth();
/*       */     int i4 = paramBitmap.getHeight();
/*       */     float f1 = 0.0F;
/*       */     try {
/*       */       int i5;
/*       */       if ((i5 = GetPageBox(this.cY)) != 1 && paramRect1 != null && paramRect2 != null) {
/*       */         if (paramRect1.getHeight() > 0.0D && paramRect2.getWidth() > 0.0D) {
/*       */           double d5, d1 = (paramRect2.getX1() - paramRect1.getX1()) / paramRect1.getWidth();
/*       */           double d2 = (paramRect1.getX2() - paramRect2.getX2()) / paramRect1.getWidth();
/*       */           double d3 = (paramRect1.getY2() - paramRect2.getY2()) / paramRect1.getHeight();
/*       */           double d4 = (paramRect2.getY1() - paramRect1.getY1()) / paramRect1.getHeight();
/*       */           int i6;
/*       */           switch (i6 = this.mDoc.getPage(paramInt).getRotation()) {
/*       */             case 1:
/*       */               d5 = d1;
/*       */               d1 = d4;
/*       */               d4 = d2;
/*       */               d2 = d3;
/*       */               d3 = d5;
/*       */               break;
/*       */             case 2:
/*       */               d5 = d1;
/*       */               d1 = d2;
/*       */               d2 = d5;
/*       */               d5 = d4;
/*       */               d4 = d3;
/*       */               d3 = d5;
/*       */               break;
/*       */             case 3:
/*       */               d5 = d1;
/*       */               d1 = d3;
/*       */               d3 = d2;
/*       */               d2 = d4;
/*       */               d4 = d5;
/*       */               break;
/*       */           } 
/*       */           double d6 = d1 * i3;
/*       */           double d7 = (1.0D - d2) * i3;
/*       */           double d8 = d3 * i4;
/*       */           double d9 = (1.0D - d4) * i4;
/*       */           i1 = (int)d6;
/*       */           i6 = (int)Math.ceil(d7);
/*       */           i2 = (int)d8;
/*       */           int i7 = (int)Math.ceil(d9);
/*       */           paramRectF.set((float)(d6 - i1), (float)(d8 - i2), (float)(i6 - d7), (float)(i7 - d9));
/*       */           i3 = i6 - i1;
/*       */           i4 = i7 - i2;
/*       */         } 
/*       */       } else {
/*       */         paramRectF.set(0.0F, 0.0F, 0.0F, 0.0F);
/*       */       } 
/*       */     } catch (Exception exception) {
/*       */       paramRectF.set(0.0F, 0.0F, 0.0F, 0.0F);
/*       */     } 
/*       */     switch (n) {
/*       */       case 1:
/*       */         f1 = paramRectF.left;
/*       */         paramRectF.left = paramRectF.bottom;
/*       */         paramRectF.bottom = paramRectF.right;
/*       */         paramRectF.right = paramRectF.top;
/*       */         paramRectF.top = f1;
/*       */         f1 = 90.0F;
/*       */         break;
/*       */       case 2:
/*       */         f1 = paramRectF.left;
/*       */         paramRectF.left = paramRectF.right;
/*       */         paramRectF.right = f1;
/*       */         f1 = paramRectF.top;
/*       */         paramRectF.top = paramRectF.bottom;
/*       */         paramRectF.bottom = f1;
/*       */         f1 = 180.0F;
/*       */         break;
/*       */       case 3:
/*       */         f1 = paramRectF.left;
/*       */         paramRectF.left = paramRectF.top;
/*       */         paramRectF.top = paramRectF.right;
/*       */         paramRectF.right = paramRectF.bottom;
/*       */         paramRectF.bottom = f1;
/*       */         f1 = 270.0F;
/*       */         break;
/*       */     } 
/*       */     try {
/*       */       Matrix matrix;
/*       */       (matrix = new Matrix()).postRotate(f1);
/*       */       return Bitmap.createBitmap(paramBitmap, i1, i2, i3, i4, matrix, true);
/*       */     } catch (Exception exception) {
/*       */       return paramBitmap;
/*       */     } catch (OutOfMemoryError outOfMemoryError) {
/*       */       if (b) {
/*       */         a(4, "Rotate thumbnail for page " + paramInt + " ran out of memory", a(true));
/*       */       } else {
/*       */         Log.e("PDFNet", "Rotate thumbnail for page " + paramInt + " ran out of memory");
/*       */       } 
/*       */       this.de.a();
/*       */       return paramBitmap;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void r() {
/*       */     boolean bool = a(this.bV);
/*       */     PagePresentationMode pagePresentationMode = getPagePresentationMode();
/*       */     int n = getPageCount();
/*       */     if (this.cv) {
/*       */       if (!f()) {
/*       */         n++;
/*       */         byte b1 = 0;
/*       */         if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */           n = !i(n) ? (n + 1) : (n + 2);
/*       */         } else if (pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) {
/*       */           n = i(n) ? (n + 1) : (n + 2);
/*       */           b1 = -1;
/*       */         } 
/*       */         if (bool) {
/*       */           this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */           this.aJ = getScrollOffsetForCanvasId(n);
/*       */           return;
/*       */         } 
/*       */         this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         this.aF = !this.cj ? getScrollOffsetForCanvasId(n) : getScrollOffsetForCanvasId(b1);
/*       */       } 
/*       */       return;
/*       */     } 
/*       */     if (bool) {
/*       */       if (this.aC != this.aE) {
/*       */         this.aJ = 0;
/*       */         this.aL = 0;
/*       */         return;
/*       */       } 
/*       */     } else if (this.aB != this.aD) {
/*       */       this.aF = 0;
/*       */       this.aH = 0;
/*       */       return;
/*       */     } 
/*       */     if (pagePresentationMode == PagePresentationMode.SINGLE) {
/*       */       this.aF = (int)((this.aB * n) + j.z(this.ce) * (n - 1));
/*       */       this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } else if (pagePresentationMode == PagePresentationMode.FACING) {
/*       */       this.aF = (int)((this.aB * (int)Math.ceil(n / 2.0D)) + j.z(this.ce) * (int)(Math.ceil(n / 2.0D) - 1.0D));
/*       */       this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } else if (pagePresentationMode == PagePresentationMode.FACING_COVER) {
/*       */       this.aF = (int)((this.aB * (int)Math.ceil((n + 1) / 2.0D)) + j.z(this.ce) * (int)(Math.ceil((n + 1) / 2.0D) - 1.0D));
/*       */       this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } else if (pagePresentationMode == PagePresentationMode.SINGLE_VERT) {
/*       */       this.aJ = (int)((this.aC * n) + j.z(this.ce) * (n - 1));
/*       */       this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } else if (pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */       this.aJ = (int)((this.aC * (int)Math.ceil(n / 2.0D)) + j.z(this.ce) * (int)(Math.ceil(n / 2.0D) - 1.0D));
/*       */       this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } else if (pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) {
/*       */       this.aJ = (int)((this.aC * (int)Math.ceil((n + 1) / 2.0D)) + j.z(this.ce) * (int)(Math.ceil((n + 1) / 2.0D) - 1.0D));
/*       */       this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */     } 
/*       */     if (bool) {
/*       */       this.aK = this.aJ;
/*       */       this.aM = this.aL;
/*       */       return;
/*       */     } 
/*       */     this.aG = this.aF;
/*       */     this.aI = this.aH;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int a(double[] paramArrayOfdouble) {
/*       */     if (paramArrayOfdouble == null || paramArrayOfdouble.length < 5) {
/*       */       return 0;
/*       */     }
/*       */     int n = (int)paramArrayOfdouble[4];
/*       */     if (paramArrayOfdouble.length > 5 && ((!this.cj && paramArrayOfdouble[3] < paramArrayOfdouble[8]) || (this.cj && paramArrayOfdouble[3] > paramArrayOfdouble[8]))) {
/*       */       n = (int)paramArrayOfdouble[9];
/*       */     }
/*       */     return n;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static int b(double[] paramArrayOfdouble) {
/*       */     if (paramArrayOfdouble == null || paramArrayOfdouble.length < 5) {
/*       */       return 0;
/*       */     }
/*       */     int n = (int)paramArrayOfdouble[3];
/*       */     if (paramArrayOfdouble.length > 5 && paramArrayOfdouble[2] < paramArrayOfdouble[7]) {
/*       */       n = (int)paramArrayOfdouble[8];
/*       */     }
/*       */     return n;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean turnPageInNonContinuousMode(int paramInt, boolean paramBoolean) {
/*       */     int n;
/*       */     if (isContinuousPagePresentationMode(this.bV)) {
/*       */       return false;
/*       */     }
/*       */     if (a(this.bV)) {
/*       */       if (this.aC == this.aE) {
/*       */         if (paramBoolean) {
/*       */           if (paramInt < getPageCount()) {
/*       */             gotoNextPage();
/*       */             return true;
/*       */           } 
/*       */         } else if (paramInt > 1) {
/*       */           gotoPreviousPage();
/*       */           return true;
/*       */         } 
/*       */         return false;
/*       */       } 
/*       */       if (paramInt > 0) {
/*       */         if (this.cv) {
/*       */           this.aL = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         int i1 = getScrollY() - this.aL;
/*       */         if (paramBoolean) {
/*       */           n = getHeight();
/*       */           if ((i1 + n) >= this.aE - 1.0F) {
/*       */             if (paramInt < getPageCount()) {
/*       */               j.c(this.ce.a(), j.b(this.ce, getCurCanvasId()), 0);
/*       */             }
/*       */             return true;
/*       */           } 
/*       */         } else if (i1 <= 1.0F) {
/*       */           if (paramInt > 1) {
/*       */             this.ce.a();
/*       */             j.c(this.ce.a(), j.a(this.ce, getCurCanvasId()), 0);
/*       */           } 
/*       */           return true;
/*       */         } 
/*       */       } 
/*       */     } else {
/*       */       if (this.aB == this.aD) {
/*       */         if (n != 0) {
/*       */           if (paramInt < getPageCount()) {
/*       */             gotoNextPage();
/*       */             return true;
/*       */           } 
/*       */         } else if (paramInt > 1) {
/*       */           gotoPreviousPage();
/*       */           return true;
/*       */         } 
/*       */         return false;
/*       */       } 
/*       */       if (paramInt > 0) {
/*       */         if (this.cv) {
/*       */           this.aH = getScrollOffsetForCanvasId(getCurCanvasId());
/*       */         }
/*       */         int i1 = getScrollX() - this.aH;
/*       */         if (n != 0) {
/*       */           n = getWidth();
/*       */           if ((i1 + n) >= this.aD - 1.0F) {
/*       */             if (paramInt < getPageCount()) {
/*       */               if (!this.cj) {
/*       */                 j.c(this.ce.a(), j.b(this.ce, getCurCanvasId()), 0);
/*       */               } else {
/*       */                 j.c(this.ce.a(), j.a(this.ce, getCurCanvasId()), 0);
/*       */               } 
/*       */             }
/*       */             return true;
/*       */           } 
/*       */         } else if (i1 <= 1.0F) {
/*       */           if (paramInt > 1) {
/*       */             this.ce.a();
/*       */             if (!this.cj) {
/*       */               j.c(this.ce.a(), j.a(this.ce, getCurCanvasId()), 0);
/*       */             } else {
/*       */               j.c(this.ce.a(), j.b(this.ce, getCurCanvasId()), 0);
/*       */             } 
/*       */           } 
/*       */           return true;
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getScrollOffsetForCanvasId(int paramInt) {
/*       */     int n;
/*       */     PagePresentationMode pagePresentationMode;
/*       */     boolean bool;
/*       */     boolean bool1 = (!(bool = a(pagePresentationMode = getPagePresentationMode())) && this.cj) ? true : false;
/*       */     int i1 = getPageCount();
/*       */     if (this.cv) {
/*       */       int i3 = i1;
/*       */       if (((pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) && i(i3)) || ((pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) && !i(i3))) {
/*       */         i3++;
/*       */       }
/*       */       byte b1 = (pagePresentationMode != PagePresentationMode.SINGLE && pagePresentationMode != PagePresentationMode.SINGLE_VERT) ? 2 : 1;
/*       */       if (!f() && !this.cV) {
/*       */         n = (int)j.z(this.ce);
/*       */         long[] arrayOfLong;
/*       */         if ((arrayOfLong = getAllCanvasPixelSizes()) == null || arrayOfLong.length == 0) {
/*       */           return 0;
/*       */         }
/*       */         this.cW = new int[i1 + 2];
/*       */         this.cW[0] = 0;
/*       */         i1 = (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) ? 2 : 1;
/*       */         int i4 = !bool1 ? 1 : (arrayOfLong.length - 2);
/*       */         byte b2 = !bool1 ? 3 : -3;
/*       */         int i5 = 0;
/*       */         for (; i1 <= i3; i1 += b1, i4 += b2) {
/*       */           this.cW[i1] = i5 = (int)arrayOfLong[i4] + n + i5;
/*       */         }
/*       */         this.cV = true;
/*       */       } 
/*       */       if (this.cW == null || this.cW.length == 0) {
/*       */         return 0;
/*       */       }
/*       */       if (bool1) {
/*       */         if (pagePresentationMode == PagePresentationMode.FACING_COVER) {
/*       */           i3--;
/*       */         }
/*       */         return this.cW[Math.min(this.cW.length - 1, Math.max(0, i3 - paramInt))];
/*       */       } 
/*       */       return this.cW[Math.min(this.cW.length - 1, Math.max(0, paramInt - b1))];
/*       */     } 
/*       */     if (bool1) {
/*       */       byte b1 = 1;
/*       */       int i3 = i1;
/*       */       if (getPagePresentationMode() == PagePresentationMode.FACING) {
/*       */         i3 = i(i1) ? i1 : (i1 + 1);
/*       */         b1 = 2;
/*       */       } else if (getPagePresentationMode() == PagePresentationMode.FACING_COVER) {
/*       */         i3 = i(i1) ? (i1 + 1) : i1;
/*       */       } 
/*       */       paramInt = i3 - paramInt + b1;
/*       */     } 
/*       */     int i2 = 0;
/*       */     if (pagePresentationMode == PagePresentationMode.SINGLE || pagePresentationMode == PagePresentationMode.SINGLE_VERT) {
/*       */       i2 = (int)((((n != 0) ? this.aC : this.aB) * (paramInt - 1)) + j.z(this.ce) * (paramInt - 1));
/*       */     } else if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */       i2 = (int)((((n != 0) ? this.aC : this.aB) * (paramInt / 2 - 1)) + j.z(this.ce) * (paramInt / 2 - 1));
/*       */     } else if (pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) {
/*       */       i2 = (int)((((n != 0) ? this.aC : this.aB) * ((paramInt + 1) / 2 - 1)) + j.z(this.ce) * ((paramInt + 1) / 2 - 1));
/*       */     } 
/*       */     return i2;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int s() {
/*       */     int n = (this.aB == this.aD || this.cv) ? getScrollX() : this.aI;
/*       */     int i1 = n + this.aB / 2;
/*       */     PDFViewCtrl pDFViewCtrl;
/* 30320 */     return (pDFViewCtrl = this).f(i1);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private int t() {
/*       */     int n = (this.aC == this.aE || this.cv) ? getScrollY() : this.aM;
/*       */     int i1 = n + this.aC / 2;
/*       */     PDFViewCtrl pDFViewCtrl;
/* 30329 */     return (pDFViewCtrl = this).f(i1);
/*       */   }
/*       */   
/*       */   private int f(int paramInt) {
/*       */     int n, i1;
/*       */     PagePresentationMode pagePresentationMode;
/*       */     int i2;
/*       */     boolean bool = (!(i2 = a(pagePresentationMode = getPagePresentationMode())) && this.cj) ? true : false;
/*       */     int i3 = getPageCount();
/*       */     if (this.cv) {
/*       */       byte b1 = 1, b2 = 1;
/*       */       i2 = 1;
/*       */       int i5 = i3;
/*       */       if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */         b1 = 2;
/*       */         b2 = 2;
/*       */         i5 = (i3 + 1) / 2;
/*       */         if (i(i3))
/*       */           i3++; 
/*       */       } else if (pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) {
/*       */         b1 = 2;
/*       */         b2 = 1;
/*       */         i5 = (i3 + 2) / 2;
/*       */         if (i(i3))
/*       */           i3++; 
/*       */       } 
/*       */       while (i2 != i5) {
/*       */         int i6 = (i2 + i5) / 2;
/*       */         int i8 = !bool ? (b1 * i6 + b2) : (i3 - b1 * i6 + b2 + 1);
/*       */         if (getScrollOffsetForCanvasId(i8) <= paramInt) {
/*       */           i1 = i6 + 1;
/*       */           continue;
/*       */         } 
/*       */         int i7 = i6;
/*       */       } 
/*       */       n = b1 * i1 - (b1 - 1) * (2 - b2);
/*       */       if (bool)
/*       */         n = i3 - n + 1; 
/*       */       return n;
/*       */     } 
/*       */     float f1 = paramInt + 0.5F;
/*       */     int i4 = getCurCanvasId();
/*       */     if (n == PagePresentationMode.SINGLE || n == PagePresentationMode.SINGLE_VERT) {
/*       */       i4 = (int)(f1 / ((i1 != 0) ? this.aC : (this.aB + j.z(this.ce)))) + 1;
/*       */       i4 = Math.max(1, Math.min(i4, getPageCount()));
/*       */     } else if (n == PagePresentationMode.FACING || n == PagePresentationMode.FACING_VERT) {
/*       */       int i5 = i(getPageCount()) ? getPageCount() : (getPageCount() + 1);
/*       */       i4 = (int)(f1 / ((i1 != 0) ? this.aC : (this.aB + j.z(this.ce)))) + 1 << 1;
/*       */       i4 = Math.max(2, Math.min(i4, i5));
/*       */     } else if (n == PagePresentationMode.FACING_COVER || n == PagePresentationMode.FACING_COVER_VERT) {
/*       */       int i5 = i(getPageCount()) ? (getPageCount() + 1) : getPageCount();
/*       */       i4 = ((int)(f1 / ((i1 != 0) ? this.aC : (this.aB + j.z(this.ce)))) + 1 << 1) - 1;
/*       */       i4 = Math.max(1, Math.min(i4, i5));
/*       */     } 
/*       */     if (bool) {
/*       */       i1 = 1;
/*       */       int i5 = getPageCount();
/*       */       if (getPagePresentationMode() == PagePresentationMode.FACING) {
/*       */         i5 = i(i5) ? i5 : (i5 + 1);
/*       */         i1 = 2;
/*       */       } else if (getPagePresentationMode() == PagePresentationMode.FACING_COVER) {
/*       */         i5 = i(i5) ? (i5 + 1) : i5;
/*       */       } 
/*       */       i4 = i5 - i4 + i1;
/*       */     } 
/*       */     return i4;
/*       */   }
/*       */   
/*       */   public int getScrollXOffsetInTools(int paramInt) {
/*       */     int n = 0;
/*       */     if (this.cv && j.a(this.ce)) {
/*       */       int i1 = j.b(this.ce);
/*       */       paramInt = h(paramInt);
/*       */       if (i1 != paramInt)
/*       */         n = j.d(this.ce) - getScrollOffsetForCanvasId(i1); 
/*       */     } 
/*       */     return n;
/*       */   }
/*       */   
/*       */   public int getScrollYOffsetInTools(int paramInt) {
/*       */     int n = 0;
/*       */     if (this.cv && j.a(this.ce)) {
/*       */       int i1 = j.b(this.ce);
/*       */       if ((paramInt = h(paramInt)) == i1) {
/*       */         n = j.f(this.ce);
/*       */       } else {
/*       */         double[] arrayOfDouble;
/*       */         if (paramInt < i1 && (arrayOfDouble = (double[])this.cF.get(paramInt)) != null)
/*       */           n = Math.max(0, a(arrayOfDouble) - this.aC); 
/*       */       } 
/*       */       int i2;
/*       */       if (paramInt != i1 && (i2 = this.cx.get(paramInt, -2147483648)) != Integer.MIN_VALUE)
/*       */         n = i2; 
/*       */     } 
/*       */     return n;
/*       */   }
/*       */   
/*       */   public boolean isCurrentSlidingCanvas(int paramInt) {
/*       */     int n = j.b(this.ce);
/*       */     paramInt = h(paramInt);
/*       */     return (n == paramInt);
/*       */   }
/*       */   
/*       */   public int getSlidingScrollX() {
/*       */     int n = 0;
/*       */     if (j.a(this.ce))
/*       */       n = j.d(this.ce); 
/*       */     return n;
/*       */   }
/*       */   
/*       */   public int getSlidingScrollY() {
/*       */     int n = 0;
/*       */     if (j.a(this.ce))
/*       */       n = j.f(this.ce); 
/*       */     return n;
/*       */   }
/*       */   
/*       */   private ArrayList<Integer> g(int paramInt) {
/*       */     ArrayList<Integer> arrayList = new ArrayList();
/*       */     if (getPagePresentationMode() == PagePresentationMode.SINGLE || getPagePresentationMode() == PagePresentationMode.SINGLE_VERT) {
/*       */       paramInt = Math.max(1, Math.min(paramInt, getPageCount()));
/*       */       arrayList.add(Integer.valueOf(paramInt));
/*       */     } else {
/*       */       int n = paramInt;
/*       */       n = Math.max(1, Math.min(n, getPageCount()));
/*       */       arrayList.add(Integer.valueOf(n));
/*       */       paramInt--;
/*       */       if ((getPagePresentationMode() == PagePresentationMode.FACING || getPagePresentationMode() == PagePresentationMode.FACING_COVER || getPagePresentationMode() == PagePresentationMode.FACING_VERT || getPagePresentationMode() == PagePresentationMode.FACING_COVER_VERT) && (paramInt = Math.max(1, Math.min(paramInt, getPageCount()))) != n)
/*       */         arrayList.add(0, Integer.valueOf(paramInt)); 
/*       */     } 
/*       */     return arrayList;
/*       */   }
/*       */   
/*       */   private int h(int paramInt) {
/*       */     PagePresentationMode pagePresentationMode = getPagePresentationMode();
/*       */     int n = paramInt;
/*       */     if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */       if (!i(paramInt))
/*       */         n = paramInt + 1; 
/*       */     } else if ((pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) && i(paramInt)) {
/*       */       n = paramInt + 1;
/*       */     } 
/*       */     return n;
/*       */   }
/*       */   
/*       */   private static boolean i(int paramInt) {
/*       */     return ((paramInt & 0x1) == 0);
/*       */   }
/*       */   
/*       */   static class u {
/*       */     static class a {
/*       */       long a;
/*       */       int b;
/*       */       int c;
/*       */       
/*       */       a(long param2Long, int param2Int1, int param2Int2, String param2String) {
/*       */         this.a = param2Long;
/*       */         this.b = param2Int1;
/*       */         this.c = param2Int2;
/*       */       }
/*       */     }
/*       */     DocumentConversion a = null;
/*       */     int b = -1;
/*       */     private final WeakReference<PDFViewCtrl> c;
/*       */     private Queue<a> d;
/*       */     private Handler e = new Handler();
/*       */     private long f = 0L;
/*       */     private boolean g = false;
/*       */     private Rect h;
/*       */     private boolean i = false;
/*       */     private boolean j = false;
/*       */     
/*       */     u(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.d = new LinkedList<>();
/*       */       this.c = new WeakReference<>(param1PDFViewCtrl);
/*       */       try {
/*       */         this.h = new Rect();
/*       */         return;
/*       */       } catch (Exception exception) {
/*       */         return;
/*       */       } 
/*       */     }
/*       */     
/*       */     final void a() {
/*       */       d();
/*       */       this.b = -1;
/*       */       this.i = false;
/*       */       this.a = null;
/*       */       this.d.clear();
/*       */       this.f = 0L;
/*       */       this.j = false;
/*       */       this.e.removeCallbacksAndMessages(null);
/*       */     }
/*       */     
/*       */     final void a(long param1Long, int param1Int1, int param1Int2, String param1String) {
/*       */       this.d.add(new a(param1Long, param1Int1, param1Int2, param1String));
/*       */     }
/*       */     
/*       */     final void b() {
/*       */       if (this.d.size() > 0) {
/*       */         PDFViewCtrl pDFViewCtrl;
/*       */         if ((pDFViewCtrl = this.c.get()) == null)
/*       */           return; 
/*       */         byte b = 0;
/*       */         boolean bool = false;
/*       */         boolean bool1 = false;
/*       */         try {
/*       */           if (bool1 = pDFViewCtrl.docTryLockRead(50))
/*       */             while (this.d.size() > 0) {
/*       */               if (b >= 100 && (PDFViewCtrl.f(pDFViewCtrl)).a == null) {
/*       */                 bool = true;
/*       */                 break;
/*       */               } 
/*       */               a a = this.d.remove();
/*       */               PDFViewCtrl.a(pDFViewCtrl, a.a, a.b, a.c);
/*       */               b++;
/*       */             }  
/*       */         } catch (PDFNetException pDFNetException) {
/*       */         
/*       */         } finally {
/*       */           if (bool1)
/*       */             pDFViewCtrl.docUnlockRead(); 
/*       */         } 
/*       */         if (bool) {
/*       */           u u1;
/*       */           (u1 = this).e.postDelayed(new Runnable(u1) {
/*       */                 public final void run() {
/*       */                   PDFViewCtrl pDFViewCtrl;
/*       */                   if ((pDFViewCtrl = PDFViewCtrl.u.a(this.a).get()) == null)
/*       */                     return; 
/*       */                   if (!PDFViewCtrl.j.a(PDFViewCtrl.d(pDFViewCtrl)) && !PDFViewCtrl.e(pDFViewCtrl))
/*       */                     this.a.b(); 
/*       */                 }
/*       */               },  50L);
/*       */         } 
/*       */         int i = pDFViewCtrl.getPageCount();
/*       */         PDFViewCtrl.a(pDFViewCtrl, i);
/*       */         if (PDFViewCtrl.g(pDFViewCtrl) != null)
/*       */           for (Iterator<UniversalDocumentConversionListener> iterator = PDFViewCtrl.g(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */             (universalDocumentConversionListener = iterator.next()).onConversionEvent(ConversionState.PROGRESS, i);
/*       */         a(false);
/*       */         this.b = i + 1;
/*       */         PDFViewCtrl.h(pDFViewCtrl);
/*       */         if (this.d.size() == 0)
/*       */           e(); 
/*       */         if (!PDFViewCtrl.i(pDFViewCtrl).hasMessages(0))
/*       */           PDFViewCtrl.i(pDFViewCtrl).sendEmptyMessageDelayed(0, 200L); 
/*       */       } 
/*       */     }
/*       */     
/*       */     private void e() {
/*       */       if (this.f != 0L) {
/*       */         PDFViewCtrl pDFViewCtrl;
/*       */         if ((pDFViewCtrl = this.c.get()) == null)
/*       */           return; 
/*       */         PDFViewCtrl.a(pDFViewCtrl, this.f);
/*       */         PDFViewCtrl.f(pDFViewCtrl).d();
/*       */         this.f = 0L;
/*       */         a(false);
/*       */         if ((PDFViewCtrl.f(pDFViewCtrl)).a != null && PDFViewCtrl.g(pDFViewCtrl) != null)
/*       */           for (Iterator<UniversalDocumentConversionListener> iterator = PDFViewCtrl.g(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */             (universalDocumentConversionListener = iterator.next()).onConversionEvent(ConversionState.FINISHED, 0);
/*       */       } 
/*       */     }
/*       */     
/*       */     final void c() {
/*       */       d();
/*       */       try {
/*       */         PDFViewCtrl pDFViewCtrl;
/*       */         if ((pDFViewCtrl = this.c.get()) == null)
/*       */           return; 
/*       */         if (f() && !this.g) {
/*       */           PDFViewCtrl.j(pDFViewCtrl).onAddProgressIndicator();
/*       */           this.g = true;
/*       */           PDFViewCtrl.j(pDFViewCtrl).onProgressIndicatorPageVisibilityChanged(false);
/*       */           this.i = false;
/*       */         } 
/*       */         return;
/*       */       } catch (PDFNetException pDFNetException) {
/*       */         return;
/*       */       } 
/*       */     }
/*       */     
/*       */     final void d() {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.c.get()) == null)
/*       */         return; 
/*       */       if (this.g && PDFViewCtrl.j(pDFViewCtrl) != null) {
/*       */         PDFViewCtrl.j(pDFViewCtrl).onRemoveProgressIndicator();
/*       */         this.g = false;
/*       */         this.i = false;
/*       */       } 
/*       */     }
/*       */     
/*       */     final void a(boolean param1Boolean) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.c.get()) == null)
/*       */         return; 
/*       */       try {
/*       */         if (f() && param1Boolean && !this.j) {
/*       */           PDFViewCtrl.j(pDFViewCtrl).onShowContentPendingIndicator();
/*       */           this.j = true;
/*       */         } else {
/*       */           if (!param1Boolean && this.j && PDFViewCtrl.j(pDFViewCtrl) != null) {
/*       */             PDFViewCtrl.j(pDFViewCtrl).onRemoveContentPendingIndicator();
/*       */             this.j = false;
/*       */           } 
/*       */           return;
/*       */         } 
/*       */       } catch (PDFNetException pDFNetException) {}
/*       */     }
/*       */     
/*       */     private boolean f() throws PDFNetException {
/*       */       PDFViewCtrl pDFViewCtrl = this.c.get();
/*       */       if (this.a != null && this.a.getConversionStatus() == 1 && pDFViewCtrl != null && PDFViewCtrl.j(pDFViewCtrl) != null)
/*       */         return true; 
/*       */       return false;
/*       */     }
/*       */   }
/*       */   
/*       */   public static class PrivateDownloader {
/*       */     protected static void partDownloadRequested(long param1Long1, long param1Long2) {
/*       */       if (param1Long1 == 0L || param1Long2 == 0L)
/*       */         return; 
/*       */       Boolean bool = Boolean.valueOf(false);
/*       */       String str = "";
/*       */       null = Obj.__Create(param1Long1, null);
/*       */       Obj obj = Obj.__Create(param1Long2, null);
/*       */       HttpURLConnection httpURLConnection = null;
/*       */       try {
/*       */         Obj obj2;
/*       */         if ((obj2 = null.findObj("url")) == null)
/*       */           try {
/*       */             return;
/*       */           } catch (Exception exception2) {
/*       */             return;
/*       */           }  
/*       */         String str2 = obj2.getAsPDFText();
/*       */         String str1 = (obj2 = null.findObj("method")).getAsPDFText();
/*       */         URL uRL;
/*       */         httpURLConnection = (HttpURLConnection)(uRL = new URL(str2)).openConnection();
/*       */         if (str1.equalsIgnoreCase("head")) {
/*       */           httpURLConnection.setRequestMethod("HEAD");
/*       */         } else if (str1.equalsIgnoreCase("get")) {
/*       */           httpURLConnection.setRequestMethod("GET");
/*       */           bool = Boolean.valueOf(true);
/*       */         } else {
/*       */           obj.putString("message", "Unsupported HTTP Method called");
/*       */           if (httpURLConnection != null)
/*       */             httpURLConnection.disconnect(); 
/*       */           try {
/*       */             return;
/*       */           } catch (Exception exception) {
/*       */             return;
/*       */           } 
/*       */         } 
/*       */         Obj obj1;
/*       */         if ((obj1 = null.findObj("headers")) != null) {
/*       */           DictIterator dictIterator = obj1.getDictIterator();
/*       */           while (dictIterator.hasNext()) {
/*       */             String str3 = dictIterator.key().getName();
/*       */             String str4 = dictIterator.value().getAsPDFText();
/*       */             httpURLConnection.setRequestProperty(str3, str4);
/*       */             dictIterator.next();
/*       */           } 
/*       */         } 
/*       */         int i = httpURLConnection.getResponseCode();
/*       */         obj.putNumber("status", i);
/*       */         obj1 = obj.putDict("headers");
/*       */         Map<String, List<String>> map;
/*       */         for (Iterator<Map.Entry> iterator = (map = httpURLConnection.getHeaderFields()).entrySet().iterator(); iterator.hasNext(); ) {
/*       */           Map.Entry<String, ?> entry;
/*       */           String str3 = (entry = iterator.next()).getKey();
/*       */           String str4 = httpURLConnection.getHeaderField(str3);
/*       */           obj1.putString(str3, str4);
/*       */         } 
/*       */         if (bool.booleanValue() && (i == 200 || i == 206)) {
/*       */           byte[] arrayOfByte;
/*       */           int j;
/*       */           if ((j = httpURLConnection.getContentLength()) > 0) {
/*       */             arrayOfByte = new byte[j];
/*       */             DataInputStream dataInputStream;
/*       */             (dataInputStream = new DataInputStream(httpURLConnection.getInputStream())).readFully(arrayOfByte);
/*       */           } else {
/*       */             arrayOfByte = PDFViewCtrl.a(httpURLConnection.getInputStream());
/*       */           } 
/*       */           if (arrayOfByte != null) {
/*       */             obj.putString("response_body", arrayOfByte);
/*       */             obj.putNumber("response_length", arrayOfByte.length);
/*       */           } 
/*       */         } 
/*       */       } catch (IOException iOException) {
/*       */         if (httpURLConnection != null) {
/*       */           str = PDFViewCtrl.b(httpURLConnection.getErrorStream());
/*       */           Log.e("PDFNet", str);
/*       */         } 
/*       */       } catch (Exception exception2) {
/*       */         Exception exception1;
/*       */         if ((str = (exception1 = null).getMessage()) != null)
/*       */           Log.e("PDFNet", str); 
/*       */       } finally {
/*       */         if (httpURLConnection != null)
/*       */           httpURLConnection.disconnect(); 
/*       */         try {
/*       */           if (str != null && str.length() > 0) {
/*       */             obj.putNumber("status", 400.0D);
/*       */             obj.putString("message", str);
/*       */           } 
/*       */         } catch (Exception exception) {
/*       */           if ((str = (obj = null).getMessage()) != null)
/*       */             Log.e("PDFNet", obj.getMessage() + "\n" + str); 
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   private static String c(InputStream paramInputStream) {
/*       */     if (paramInputStream == null)
/*       */       return ""; 
/*       */     BufferedReader bufferedReader = null;
/*       */     null = new StringBuilder();
/*       */     try {
/*       */       bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
/*       */       String str;
/*       */       while ((str = bufferedReader.readLine()) != null)
/*       */         null.append(str); 
/*       */     } catch (IOException iOException2) {
/*       */       IOException iOException1;
/*       */       (iOException1 = null).printStackTrace();
/*       */     } finally {
/*       */       if (bufferedReader != null)
/*       */         try {
/*       */           bufferedReader.close();
/*       */         } catch (IOException iOException) {
/*       */           (bufferedReader = null).printStackTrace();
/*       */         }  
/*       */       try {
/*       */         paramInputStream.close();
/*       */       } catch (IOException iOException) {
/*       */         (bufferedReader = null).printStackTrace();
/*       */       } 
/*       */     } 
/*       */     return SYNTHETIC_LOCAL_VARIABLE_2.toString();
/*       */   }
/*       */   
/*       */   class RenderCallback {
/*       */     private static final int TILE_TYPE_FINAL_RENDER = 0;
/*       */     private static final int TILE_TYPE_PROGRESSIVE_RENDER = 1;
/*       */     private static final int TILE_TYPE_OFFSCREEN_RENDER = 2;
/*       */     private static final int TILE_TYPE_LOW_RES_PREVIEW = 3;
/*       */     private static final int ANNOT_OPERATION_NORMAL_ANNOT_BITMAP = 1;
/*       */     private static final int ANNOT_OPERATION_MULT_BLEND_ANNOT_BITMAP = 2;
/*       */     private static final int ANNOT_OPERATION_NORMAL_ANNOT_VECTOR = 3;
/*       */     private static final int ANNOT_OPERATION_MULT_BLEND_ANNOT_VECTOR = 4;
/*       */     private static final int ANNOT_OPERATION_RESIZE_ANNOT_BITMAP = 128;
/*       */     private static final int ANNOT_OPERATION_REMOVE_ANNOT_BITMAP = 129;
/*       */     private static final int ANNOT_OPERATION_CLEAR_ALL_ANNOT_BITMAPS = 130;
/*       */     private static final int ANNOT_OPERATION_ANNOT_RENDERS_COMPLETE = 131;
/*       */     private static final int ANNOT_OPERATION_REINDEX_ANNOT_BITMAP = 132;
/*       */     
/*       */     @SuppressLint({"DefaultLocale"})
/*       */     private void AnnotBitmapProc(PDFViewCtrl param1PDFViewCtrl, int param1Int1, int[] param1ArrayOfint, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, long param1Long1, long param1Long2, long param1Long3, int param1Int7, int param1Int8, int param1Int9, int param1Int10) {
/*       */       if (PDFViewCtrl.a) {
/*       */         Log.d("annotlayer", "operation: " + param1Int1);
/*       */         Log.d("annotlayer", String.format("w: %d, h: %d, pg: %d", new Object[] { Integer.valueOf(param1Int2), Integer.valueOf(param1Int3), Integer.valueOf(param1Int5) }));
/*       */         Log.d("annotlayer", String.format("annot_index %d, key %d, x: %d, y: %d, xOff: %d, yOff: %d", new Object[] { Integer.valueOf(param1Int6), Long.valueOf(param1Long1), Long.valueOf(param1Long2), Long.valueOf(param1Long3), Integer.valueOf(param1Int7), Integer.valueOf(param1Int8) }));
/*       */       } 
/*       */       if (param1Int1 == 128) {
/*       */         if (!PDFViewCtrl.m(PDFViewCtrl.this).a(param1Int10))
/*       */           return; 
/*       */         PDFViewCtrl.n(PDFViewCtrl.this).lock();
/*       */         try {
/*       */           PDFViewCtrl.m(PDFViewCtrl.this).a(param1Long1, param1Int5, param1Int7, param1Int8, (int)param1Long2, (int)param1Long3, param1Int2, param1Int3);
/*       */           return;
/*       */         } catch (Exception exception) {
/*       */           if (PDFViewCtrl.a)
/*       */             exception.printStackTrace(); 
/*       */           return;
/*       */         } finally {
/*       */           PDFViewCtrl.n(PDFViewCtrl.this).unlock();
/*       */         } 
/*       */       } 
/*       */       if (param1Int1 == 132) {
/*       */         if (!PDFViewCtrl.m(PDFViewCtrl.this).a(param1Int10))
/*       */           return; 
/*       */         PDFViewCtrl.n(PDFViewCtrl.this).lock();
/*       */         try {
/*       */           PDFViewCtrl.m(PDFViewCtrl.this).a(param1Long1, param1Int5, param1Int2);
/*       */           return;
/*       */         } catch (Exception exception) {
/*       */           if (PDFViewCtrl.a)
/*       */             exception.printStackTrace(); 
/*       */           return;
/*       */         } finally {
/*       */           PDFViewCtrl.n(PDFViewCtrl.this).unlock();
/*       */         } 
/*       */       } 
/*       */       if (param1Int1 == 129) {
/*       */         removeTileProc(-1, param1Long1, param1Int5, param1Int10, 1);
/*       */         return;
/*       */       } 
/*       */       if (param1Int1 == 130) {
/*       */         removeTileProc(-1, -1L, -1, param1Int10, 1);
/*       */         return;
/*       */       } 
/*       */       if (param1Int1 == 131) {
/*       */         PDFViewCtrl.i(PDFViewCtrl.this).sendEmptyMessage(0);
/*       */         PDFViewCtrl.this.postInvalidate();
/*       */         return;
/*       */       } 
/*       */       if (PDFViewCtrl.o(PDFViewCtrl.this)) {
/*       */         if (PDFViewCtrl.b) {
/*       */           PDFViewCtrl.a(4, String.format("Ignore annotation %d due to out of memory", new Object[] { Integer.valueOf(param1Int6) }), PDFViewCtrl.a(false));
/*       */           return;
/*       */         } 
/*       */         Log.e("PDFNet", String.format("Ignore annotation %d due to out of memory", new Object[] { Integer.valueOf(param1Int6) }));
/*       */         return;
/*       */       } 
/*       */       if (PDFViewCtrl.p(PDFViewCtrl.this) && param1Int5 >= PDFViewCtrl.q(PDFViewCtrl.this) && param1Int5 <= PDFViewCtrl.r(PDFViewCtrl.this))
/*       */         return; 
/*       */       if (param1ArrayOfint == null || param1ArrayOfint.length == 0) {
/*       */         if (PDFViewCtrl.b) {
/*       */           PDFViewCtrl.a(4, String.format("Null buffer, will invoke purge memory", new Object[] { Integer.valueOf(param1Int6) }), PDFViewCtrl.a(false));
/*       */         } else {
/*       */           Log.e("PDFNet", "not enough memory to allocate the bitmap");
/*       */         } 
/*       */         PDFViewCtrl.s(PDFViewCtrl.this).a();
/*       */         return;
/*       */       } 
/*       */       param1PDFViewCtrl = null;
/*       */       CurvePainter curvePainter = null;
/*       */       if (param1Int1 == 1 || param1Int1 == 2) {
/*       */         if ((null = ImageCache.a.a().a(param1Int2, param1Int3)) == null) {
/*       */           try {
/*       */             null = Bitmap.createBitmap(param1ArrayOfint, param1Int2, param1Int3, Bitmap.Config.ARGB_8888);
/*       */           } catch (OutOfMemoryError outOfMemoryError) {
/*       */             if (PDFViewCtrl.b) {
/*       */               PDFViewCtrl.a(4, "Can't create bitmap due to out of memory:" + param1Int6, PDFViewCtrl.a(false));
/*       */             } else {
/*       */               Log.e("PDFNet", "Can't create bitmap due to out of memory:" + param1Int6);
/*       */             } 
/*       */             PDFViewCtrl.s(PDFViewCtrl.this).a();
/*       */             return;
/*       */           } 
/*       */         } else {
/*       */           null.setPixels(param1ArrayOfint, 0, param1Int2, 0, 0, param1Int2, param1Int3);
/*       */         } 
/*       */         (curvePainter = new CurvePainter(null)).setRect(new Rect((int)(param1Int7 + 0.5D), (int)(param1Int8 + 0.5D), (int)((param1Int7 + param1Int2) + 0.5D), (int)((param1Int8 + param1Int3) + 0.5D)));
/*       */       } else if (param1Int1 == 3 || param1Int1 == 4) {
/*       */         (curvePainter = new CurvePainter(param1ArrayOfint)).setMultBlend((param1Int1 == 4));
/*       */         curvePainter.setRect(new Rect((int)param1Long2, (int)param1Long3, (int)(param1Long2 + param1Int7), (int)(param1Long3 + param1Int8)));
/*       */       } 
/*       */       PDFViewCtrl.n(PDFViewCtrl.this).lock();
/*       */       if (PDFViewCtrl.a)
/*       */         Log.i("annotlayer", String.format("Got tile: page %d - index %d - sequence %d", new Object[] { Integer.valueOf(param1Int5), Integer.valueOf(param1Int6), Integer.valueOf(param1Int10) })); 
/*       */       try {
/*       */         Log.d("annotlayer", "addTile core w: " + param1Int2 + " h:" + param1Int3);
/*       */         param1Int1 = (param1Int1 == 2 || param1Int1 == 4) ? 1 : 0;
/*       */         PDFViewCtrl.m(PDFViewCtrl.this).a(param1Long1, param1Int5, param1Int7, param1Int8, (int)param1Long2, (int)param1Long3, param1Int2, param1Int3, null, curvePainter, param1Int10, 1, param1Int1, param1Int6);
/*       */       } catch (Exception exception) {
/*       */         if (PDFViewCtrl.a)
/*       */           exception.printStackTrace(); 
/*       */       } catch (OutOfMemoryError outOfMemoryError) {
/*       */         if (PDFViewCtrl.b) {
/*       */           PDFViewCtrl.a(4, "Will invoke purge memory when batch of tiles is done", PDFViewCtrl.a(false));
/*       */         } else {
/*       */           Log.e("PDFNet", "Will invoke purge memory when batch of tiles is done");
/*       */         } 
/*       */         PDFViewCtrl.s(PDFViewCtrl.this).a();
/*       */       } finally {
/*       */         PDFViewCtrl.n(PDFViewCtrl.this).unlock();
/*       */       } 
/*       */       if (PDFViewCtrl.t(PDFViewCtrl.this) != null)
/*       */         PDFViewCtrl.t(PDFViewCtrl.this).onAnnotPainterUpdated(param1Int5, param1Long1, CurvePainter.create(curvePainter)); 
/*       */     }
/*       */     
/*       */     @SuppressLint({"DefaultLocale"})
/*       */     private void DeluxeCreateTileProc(PDFViewCtrl param1PDFViewCtrl, int[] param1ArrayOfint, int param1Int1, int param1Int2, int param1Int3, int param1Int4, long param1Long1, long param1Long2, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10, int param1Int11) {
/*       */       if (PDFViewCtrl.o(PDFViewCtrl.this)) {
/*       */         if (PDFViewCtrl.b) {
/*       */           PDFViewCtrl.a(4, String.format("Ignore Tile %d due to out of memory", new Object[] { Integer.valueOf(param1Int5) }), PDFViewCtrl.a(false));
/*       */           return;
/*       */         } 
/*       */         Log.e("PDFNet", String.format("Ignore Tile %d due to out of memory", new Object[] { Integer.valueOf(param1Int5) }));
/*       */         return;
/*       */       } 
/*       */       if (PDFViewCtrl.p(PDFViewCtrl.this) && param1Int10 != 3 && param1Int4 >= PDFViewCtrl.q(PDFViewCtrl.this) && param1Int4 <= PDFViewCtrl.r(PDFViewCtrl.this))
/*       */         return; 
/*       */       if (param1ArrayOfint == null || param1ArrayOfint.length == 0) {
/*       */         if (PDFViewCtrl.b) {
/*       */           PDFViewCtrl.a(4, String.format("Null buffer, will invoke purge memory", new Object[] { Integer.valueOf(param1Int5) }), PDFViewCtrl.a(false));
/*       */         } else {
/*       */           Log.e("PDFNet", "not enough memory to allocate the bitmap");
/*       */         } 
/*       */         PDFViewCtrl.s(PDFViewCtrl.this).a();
/*       */         return;
/*       */       } 
/*       */       if ((null = ImageCache.a.a().a(param1Int1, param1Int2)) == null) {
/*       */         try {
/*       */           null = Bitmap.createBitmap(param1ArrayOfint, param1Int1, param1Int2, Bitmap.Config.ARGB_8888);
/*       */         } catch (OutOfMemoryError outOfMemoryError) {
/*       */           if (PDFViewCtrl.b) {
/*       */             PDFViewCtrl.a(4, "Can't create bitmap due to out of memory:" + param1Int5, PDFViewCtrl.a(false));
/*       */           } else {
/*       */             Log.e("PDFNet", "Can't create bitmap due to out of memory:" + param1Int5);
/*       */           } 
/*       */           PDFViewCtrl.s(PDFViewCtrl.this).a();
/*       */           return;
/*       */         } 
/*       */       } else {
/*       */         null.setPixels(param1ArrayOfint, 0, param1Int1, 0, 0, param1Int1, param1Int2);
/*       */       } 
/*       */       param1ArrayOfint = null;
/*       */       Rect rect = null;
/*       */       if (param1Int10 == 3) {
/*       */         boolean bool = false;
/*       */         try {
/*       */           Rect rect1 = new Rect();
/*       */           rect = new Rect();
/*       */           PDFViewCtrl.this.docLockRead();
/*       */           bool = true;
/*       */           Page page;
/*       */           rect1 = (page = PDFViewCtrl.this.getDoc().getPage(param1Int4)).getCropBox();
/*       */           rect = page.getBox(PDFViewCtrl.this.getPageBox());
/*       */         } catch (Exception exception) {
/*       */         
/*       */         } finally {
/*       */           if (bool)
/*       */             PDFViewCtrl.this.docUnlockRead(); 
/*       */         } 
/*       */       } 
/*       */       PDFViewCtrl.n(PDFViewCtrl.this).lock();
/*       */       if (PDFViewCtrl.a)
/*       */         Log.i("TILES", String.format("Got tile: page %d - id %d - sequence %d", new Object[] { Integer.valueOf(param1Int4), Integer.valueOf(param1Int5), Integer.valueOf(param1Int11) })); 
/*       */       if (!(PDFViewCtrl.m(PDFViewCtrl.this)).b) {
/*       */         (PDFViewCtrl.m(PDFViewCtrl.this)).b = true;
/*       */         (PDFViewCtrl.m(PDFViewCtrl.this)).a = param1Int11 - 1;
/*       */       } 
/*       */       try {
/*       */         f f;
/*       */         int i;
/*       */         int j;
/*       */         if (param1Int10 != 3) {
/*       */           f.a a;
/*       */           if ((a = PDFViewCtrl.m(PDFViewCtrl.this).c(param1Int8)) == null)
/*       */             PDFViewCtrl.m(PDFViewCtrl.this).b(param1Int8); 
/*       */           int m = param1Int11;
/*       */           Bitmap bitmap = null;
/*       */           int k = param1Int2;
/*       */           j = param1Int1;
/*       */           param1Int2 = param1Int7;
/*       */           param1Int1 = param1Int6;
/*       */           long l3 = param1Long2, l2 = param1Long1;
/*       */           i = param1Int4;
/*       */           long l1 = param1Int5;
/*       */           (f = PDFViewCtrl.m(PDFViewCtrl.this)).a(l1, i, l2, l3, param1Int1, param1Int2, j, k, bitmap, null, m, 0, false, -1);
/*       */         } else {
/*       */           PDFViewCtrl.u(PDFViewCtrl.this).a(param1Int4, (Bitmap)f, i, j);
/*       */           if (PDFViewCtrl.a)
/*       */             Log.d(PDFViewCtrl.a(), "received thumbnails for page: " + param1Int4); 
/*       */         } 
/*       */       } catch (Exception exception) {
/*       */         if (PDFViewCtrl.a)
/*       */           exception.printStackTrace(); 
/*       */       } catch (OutOfMemoryError outOfMemoryError) {
/*       */         if (PDFViewCtrl.b) {
/*       */           PDFViewCtrl.a(4, "Will invoke purge memory when batch of tiles is done", PDFViewCtrl.a(false));
/*       */         } else {
/*       */           Log.e("PDFNet", "Will invoke purge memory when batch of tiles is done");
/*       */         } 
/*       */         PDFViewCtrl.s(PDFViewCtrl.this).a();
/*       */       } finally {
/*       */         PDFViewCtrl.n(PDFViewCtrl.this).unlock();
/*       */       } 
/*       */       if (param1Int10 == 3) {
/*       */         PDFViewCtrl.this.postInvalidate();
/*       */       } else if (param1Int9 == 0) {
/*       */         PDFViewCtrl.v(PDFViewCtrl.this).lock();
/*       */         try {
/*       */           PDFViewCtrl.a(PDFViewCtrl.this, true);
/*       */           Rect rect1;
/*       */           if ((rect1 = (Rect)PDFViewCtrl.w(PDFViewCtrl.this).get(param1Int4)) != null)
/*       */             if (PDFViewCtrl.x(PDFViewCtrl.this) != null && !PDFViewCtrl.x(PDFViewCtrl.this).a()) {
/*       */               PDFViewCtrl.y(PDFViewCtrl.this).add(Integer.valueOf(param1Int4));
/*       */             } else {
/*       */               b.a.a().a(rect1);
/*       */               PDFViewCtrl.w(PDFViewCtrl.this).remove(param1Int4);
/*       */               if (PDFViewCtrl.w(PDFViewCtrl.this).size() == 0)
/*       */                 PDFViewCtrl.y(PDFViewCtrl.this).clear(); 
/*       */             }  
/*       */         } finally {
/*       */           PDFViewCtrl.v(PDFViewCtrl.this).unlock();
/*       */         } 
/*       */         if (param1Int10 == 0)
/*       */           PDFViewCtrl.i(PDFViewCtrl.this).sendEmptyMessage(1); 
/*       */         PDFViewCtrl.this.postInvalidate();
/*       */       } 
/*       */       if (PDFViewCtrl.b || PDFViewCtrl.a) {
/*       */         ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
/*       */         ActivityManager activityManager;
/*       */         if ((activityManager = (ActivityManager)PDFViewCtrl.this.getContext().getSystemService("activity")) != null) {
/*       */           activityManager.getMemoryInfo(memoryInfo);
/*       */           if (PDFViewCtrl.b)
/*       */             PDFViewCtrl.a(1, "available memory size: " + (memoryInfo.availMem / 1048576L) + "MB, " + "native heap allocated size: " + (Debug.getNativeHeapAllocatedSize() / 1048576L) + "MB", PDFViewCtrl.a(false)); 
/*       */         } 
/*       */         if (PDFViewCtrl.a) {
/*       */           Log.v("MemorySize", "available memory size: " + (memoryInfo.availMem / 1048576L) + "MB");
/*       */           Log.v("MemorySize", "native heap allocated size: " + (Debug.getNativeHeapAllocatedSize() / 1048576L) + "MB");
/*       */         } 
/*       */       } 
/*       */     }
/*       */     
/*       */     private void CreateTileProc(PDFViewCtrl param1PDFViewCtrl, int[] param1ArrayOfint, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, long param1Long, boolean param1Boolean1, boolean param1Boolean2, int param1Int6, boolean param1Boolean3, int param1Int7, int param1Int8, int param1Int9, int param1Int10, int param1Int11, int param1Int12) {}
/*       */     
/*       */     private void RemoveTileProc(int param1Int1, long param1Long, int param1Int2, int param1Int3) {
/*       */       removeTileProc(param1Int1, param1Long, param1Int2, param1Int3, 0);
/*       */     }
/*       */     
/*       */     private void removeTileProc(int param1Int1, long param1Long, int param1Int2, int param1Int3, int param1Int4) {
/*       */       PDFViewCtrl.n(PDFViewCtrl.this).lock();
/*       */       boolean bool = true;
/*       */       if (param1Int4 == 0) {
/*       */         (PDFViewCtrl.m(PDFViewCtrl.this)).b = true;
/*       */         (PDFViewCtrl.m(PDFViewCtrl.this)).a = param1Int3;
/*       */       } else if (param1Int4 == 1 && !PDFViewCtrl.m(PDFViewCtrl.this).a(param1Int3)) {
/*       */         return;
/*       */       } 
/*       */       try {
/*       */         return;
/*       */       } catch (Exception exception) {
/*       */         Log.e("TILES", "Error in remove tile proc!!!! " + exception.toString());
/*       */       } finally {
/*       */         if (bool)
/*       */           PDFViewCtrl.n(PDFViewCtrl.this).unlock(); 
/*       */       } 
/*       */     }
/*       */     
/*       */     private long GetAvailableTileBytes() {
/*       */       return PDFViewCtrl.z(PDFViewCtrl.this);
/*       */     }
/*       */     
/*       */     private void RenderBegin(PDFViewCtrl param1PDFViewCtrl, boolean param1Boolean) {
/*       */       PDFViewCtrl.A(PDFViewCtrl.this).removeMessages(0);
/*       */       PDFViewCtrl.A(PDFViewCtrl.this).sendEmptyMessageDelayed(0, PDFViewCtrl.B(PDFViewCtrl.this));
/*       */       if (param1Boolean && PDFViewCtrl.C(PDFViewCtrl.this) != null)
/*       */         PDFViewCtrl.D(PDFViewCtrl.this).sendEmptyMessage(0); 
/*       */     }
/*       */     
/*       */     private void RenderEnd(boolean param1Boolean) {
/*       */       PDFViewCtrl.A(PDFViewCtrl.this).removeMessages(0);
/*       */       if (param1Boolean && PDFViewCtrl.C(PDFViewCtrl.this) != null)
/*       */         PDFViewCtrl.D(PDFViewCtrl.this).sendEmptyMessage(1); 
/*       */     }
/*       */     
/*       */     private void FindTextProcCallback(boolean param1Boolean, long param1Long) {
/*       */       Thread.yield();
/*       */       Message message;
/*       */       (message = new Message()).setTarget(PDFViewCtrl.E(PDFViewCtrl.this));
/*       */       Selection selection = new Selection(param1Long, PDFViewCtrl.this, (byte)0);
/*       */       Vector<Boolean> vector;
/*       */       (vector = new Vector<>()).add(Boolean.valueOf(param1Boolean));
/*       */       vector.add(selection);
/*       */       message.obj = vector;
/*       */       message.sendToTarget();
/*       */     }
/*       */     
/*       */     private void PartDownloadedProcCallback(int param1Int1, long param1Long, int param1Int2, int param1Int3, String param1String, PDFViewCtrl param1PDFViewCtrl) {
/*       */       Thread.yield();
/*       */       Message message;
/*       */       (message = new Message()).setTarget(PDFViewCtrl.F(PDFViewCtrl.this));
/*       */       Vector<Integer> vector;
/*       */       (vector = new Vector<>()).add(Integer.valueOf(param1Int1));
/*       */       vector.add(Long.valueOf(param1Long));
/*       */       vector.add(Integer.valueOf(param1Int2));
/*       */       vector.add(Integer.valueOf(param1Int3));
/*       */       vector.add(param1String);
/*       */       vector.add(Integer.valueOf(PDFViewCtrl.G(PDFViewCtrl.this)));
/*       */       message.obj = vector;
/*       */       message.sendToTarget();
/*       */     }
/*       */     
/*       */     private void ErrorReportProcCallback(String param1String) {
/*       */       Thread.yield();
/*       */       Message message;
/*       */       (message = new Message()).setTarget(PDFViewCtrl.H(PDFViewCtrl.this));
/*       */       Vector<String> vector;
/*       */       (vector = new Vector<>()).add(param1String);
/*       */       message.obj = vector;
/*       */       message.sendToTarget();
/*       */     }
/*       */     
/*       */     private void ThumbAsyncHandlerProc(int param1Int1, boolean param1Boolean, int[] param1ArrayOfint, int param1Int2, int param1Int3) {
/*       */       Thread.yield();
/*       */       Message message;
/*       */       (message = new Message()).setTarget(PDFViewCtrl.I(PDFViewCtrl.this));
/*       */       if (param1Boolean) {
/*       */         Vector<Integer> vector;
/*       */         (vector = new Vector<>()).add(Integer.valueOf(param1Int1));
/*       */         vector.add(param1ArrayOfint);
/*       */         vector.add(Integer.valueOf(param1Int2));
/*       */         vector.add(Integer.valueOf(param1Int3));
/*       */         message.obj = vector;
/*       */         message.sendToTarget();
/*       */       } 
/*       */     }
/*       */     
/*       */     private void OnRequestRenderInWorkerThreadCallback() {}
/*       */     
/*       */     private void DoActionCompletedCallback(PDFViewCtrl param1PDFViewCtrl, long param1Long) {
/*       */       Thread.yield();
/*       */       Action action = new Action(param1Long, null);
/*       */       Message message;
/*       */       (message = new Message()).setTarget(PDFViewCtrl.J(PDFViewCtrl.this));
/*       */       Vector<Action> vector;
/*       */       (vector = new Vector<>()).add(action);
/*       */       message.obj = vector;
/*       */       message.sendToTarget();
/*       */     }
/*       */   }
/*       */   
/*       */   static class i extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     i(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null) {
/*       */         PDFViewCtrl.L(pDFViewCtrl);
/*       */         sendEmptyMessageDelayed(0, PDFViewCtrl.M(pDFViewCtrl));
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class m extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     m(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null) {
/*       */         pDFViewCtrl.requestRendering();
/*       */         if (param1Message.what == 1 && PDFViewCtrl.t(pDFViewCtrl) != null)
/*       */           PDFViewCtrl.t(pDFViewCtrl).onRenderingFinished(); 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class k extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     k(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void a() {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null) {
/*       */         PDFViewCtrl.j(pDFViewCtrl, true);
/*       */         if (!hasMessages(0))
/*       */           sendEmptyMessage(0); 
/*       */       } 
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.N(pDFViewCtrl) != 0L) {
/*       */         pDFViewCtrl.purgeMemory();
/*       */         try {
/*       */           pDFViewCtrl.update();
/*       */           return;
/*       */         } catch (PDFNetException pDFNetException) {}
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class d extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     d(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null) {
/*       */         if (PDFViewCtrl.O(pDFViewCtrl) != null && PDFViewCtrl.P(pDFViewCtrl)) {
/*       */           PDFViewCtrl.k(pDFViewCtrl, false);
/*       */           for (Iterator<DocumentLoadListener> iterator = PDFViewCtrl.O(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */             (documentLoadListener = iterator.next()).onDocumentLoaded(); 
/*       */         } 
/*       */         PDFViewCtrl.f(pDFViewCtrl).c();
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class b extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     b(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.Q(pDFViewCtrl) != null) {
/*       */         PDFViewCtrl.c(pDFViewCtrl, pDFViewCtrl.getCurrentPage());
/*       */         if (PDFViewCtrl.R(pDFViewCtrl) != PDFViewCtrl.S(pDFViewCtrl) || PDFViewCtrl.T(pDFViewCtrl) == PageChangeState.BEGIN || PDFViewCtrl.T(pDFViewCtrl) == PageChangeState.END) {
/*       */           Iterator<PageChangeListener> iterator;
/*       */           if (PDFViewCtrl.R(pDFViewCtrl) != PDFViewCtrl.S(pDFViewCtrl) && PDFViewCtrl.T(pDFViewCtrl) == PageChangeState.SILENT)
/*       */             PDFViewCtrl.a(pDFViewCtrl, PageChangeState.END);
/*       */           int i = PDFViewCtrl.R(pDFViewCtrl);
/*       */           PDFViewCtrl.d(pDFViewCtrl, PDFViewCtrl.S(pDFViewCtrl));
/*       */           PageChangeState pageChangeState;
/*       */           if ((pageChangeState = PDFViewCtrl.T(pDFViewCtrl)) == PageChangeState.BEGIN) {
/*       */             for (iterator = PDFViewCtrl.Q(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */               (pageChangeListener = iterator.next()).onPageChange(i, i, PageChangeState.BEGIN);
/*       */             PDFViewCtrl.a(pDFViewCtrl, PageChangeState.ONGOING);
/*       */             return;
/*       */           } 
/*       */           if (iterator == PageChangeState.ONGOING) {
/*       */             for (iterator = PDFViewCtrl.Q(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */               (pageChangeListener = iterator.next()).onPageChange(i, PDFViewCtrl.S(pDFViewCtrl), PageChangeState.ONGOING);
/*       */             return;
/*       */           } 
/*       */           if (iterator == PageChangeState.END) {
/*       */             PDFViewCtrl.a(pDFViewCtrl, PageChangeState.SILENT);
/*       */             for (iterator = PDFViewCtrl.Q(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */               (pageChangeListener = iterator.next()).onPageChange(PDFViewCtrl.S(pDFViewCtrl), PDFViewCtrl.S(pDFViewCtrl), PageChangeState.END);
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class l extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     l(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.C(pDFViewCtrl) != null) {
/*       */         if (param1Message.what == 0) {
/*       */           PDFViewCtrl.C(pDFViewCtrl).onRenderingStarted();
/*       */           return;
/*       */         } 
/*       */         PDFViewCtrl.C(pDFViewCtrl).onRenderingFinished();
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class h extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     h(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null) {
/*       */         pDFViewCtrl.onLongPress(PDFViewCtrl.U(pDFViewCtrl));
/*       */         if (Build.VERSION.SDK_INT >= 18)
/*       */           PDFViewCtrl.d(pDFViewCtrl, true); 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class q extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     q(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null)
/*       */         pDFViewCtrl.requestRendering(); 
/*       */     }
/*       */   }
/*       */   
/*       */   static class p extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     p(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.t(pDFViewCtrl) != null)
/*       */         PDFViewCtrl.t(pDFViewCtrl).onPostSingleTapConfirmed(); 
/*       */     }
/*       */   }
/*       */   
/*       */   static class t extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     t(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.t(pDFViewCtrl) != null)
/*       */         PDFViewCtrl.t(pDFViewCtrl).onCustomEvent(PDFViewCtrl.V(pDFViewCtrl)); 
/*       */     }
/*       */   }
/*       */   
/*       */   static class g extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     g(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && param1Message.obj instanceof Vector) {
/*       */         Vector<Boolean> vector;
/*       */         boolean bool = ((Boolean)(vector = (Vector<Boolean>)param1Message.obj).elementAt(0)).booleanValue();
/*       */         null = (Selection)vector.elementAt(1);
/*       */         synchronized ((PDFViewCtrl)this.a.get()) {
/*       */           PDFViewCtrl.W(pDFViewCtrl);
/*       */         } 
/*       */         if (PDFViewCtrl.X(pDFViewCtrl) != null)
/*       */           PDFViewCtrl.X(pDFViewCtrl).interrupt(); 
/*       */         PDFViewCtrl.c(pDFViewCtrl).removeMessages(0);
/*       */         if (bool) {
/*       */           pDFViewCtrl.selectWithSelection((Selection)vector);
/*       */           if (!pDFViewCtrl.isContinuousPagePresentationMode(PDFViewCtrl.Y(pDFViewCtrl)))
/*       */             PDFViewCtrl.Z(pDFViewCtrl); 
/*       */           pDFViewCtrl.scrollTo(PDFViewCtrl.aa(pDFViewCtrl), PDFViewCtrl.ab(pDFViewCtrl));
/*       */           pDFViewCtrl.invalidate();
/*       */           if (PDFViewCtrl.ac(pDFViewCtrl) != null) {
/*       */             PDFViewCtrl.ac(pDFViewCtrl).onTextSearchEnd(TextSearchResult.FOUND);
/*       */             return;
/*       */           } 
/*       */         } else {
/*       */           boolean bool2 = false;
/*       */           boolean bool1 = false;
/*       */           synchronized (this) {
/*       */             if (PDFViewCtrl.ad(pDFViewCtrl)) {
/*       */               PDFViewCtrl.l(pDFViewCtrl, false);
/*       */               bool2 = true;
/*       */             } 
/*       */             if (PDFViewCtrl.ae(pDFViewCtrl)) {
/*       */               PDFViewCtrl.m(pDFViewCtrl, false);
/*       */               bool1 = true;
/*       */             } 
/*       */           } 
/*       */           if (PDFViewCtrl.ac(pDFViewCtrl) != null) {
/*       */             if (bool2) {
/*       */               PDFViewCtrl.ac(pDFViewCtrl).onTextSearchEnd(TextSearchResult.CANCELED);
/*       */               return;
/*       */             } 
/*       */             if (bool1) {
/*       */               PDFViewCtrl.ac(pDFViewCtrl).onTextSearchEnd(TextSearchResult.INVALID_INPUT);
/*       */               return;
/*       */             } 
/*       */             PDFViewCtrl.ac(pDFViewCtrl).onTextSearchEnd(TextSearchResult.NOT_FOUND);
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class e extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     e(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && param1Message.obj instanceof Vector) {
/*       */         Vector<Integer> vector;
/*       */         DownloadState downloadState = DownloadState.valueOf(((Integer)(vector = (Vector<Integer>)param1Message.obj).elementAt(0)).intValue());
/*       */         Long long_ = (Long)vector.elementAt(1);
/*       */         Integer integer2 = vector.elementAt(2);
/*       */         Integer integer3 = vector.elementAt(3);
/*       */         String str = (String)vector.elementAt(4);
/*       */         Integer integer1;
/*       */         if ((integer1 = vector.elementAt(5)).intValue() != PDFViewCtrl.G(pDFViewCtrl)) {
/*       */           Log.i("UNIVERSAL", "Got Event for conversion but sequence number doesn't match");
/*       */           return;
/*       */         } 
/*       */         if (downloadState != DownloadState.FAILED && !PDFViewCtrl.b(pDFViewCtrl, long_.longValue()))
/*       */           return; 
/*       */         if (downloadState == DownloadState.PAGE) {
/*       */           PDFViewCtrl.f(pDFViewCtrl).a(long_.longValue(), integer2.intValue(), integer3.intValue(), str);
/*       */           if (!PDFViewCtrl.j.a(PDFViewCtrl.d(pDFViewCtrl)) && !PDFViewCtrl.e(pDFViewCtrl))
/*       */             PDFViewCtrl.f(pDFViewCtrl).b(); 
/*       */           if ((PDFViewCtrl.f(pDFViewCtrl)).a == null) {
/*       */             PDFViewCtrl.af(pDFViewCtrl);
/*       */             if (PDFViewCtrl.ag(pDFViewCtrl) > PDFViewCtrl.ah(pDFViewCtrl))
/*       */               PDFViewCtrl.e(pDFViewCtrl, PDFViewCtrl.ah(pDFViewCtrl)); 
/*       */           } 
/*       */         } else if (downloadState == DownloadState.THUMB) {
/*       */           PDFViewCtrl.b(pDFViewCtrl, long_.longValue(), integer2.intValue(), integer3.intValue());
/*       */         } else if (downloadState == DownloadState.OUTLINE) {
/*       */           PDFViewCtrl.c(pDFViewCtrl, long_.longValue());
/*       */         } else if (downloadState == DownloadState.FINISHED) {
/*       */           PDFViewCtrl.u.a(PDFViewCtrl.f(pDFViewCtrl), long_.longValue());
/*       */           if (!PDFViewCtrl.j.a(PDFViewCtrl.d(pDFViewCtrl)) && !PDFViewCtrl.e(pDFViewCtrl))
/*       */             PDFViewCtrl.u.b(PDFViewCtrl.f(pDFViewCtrl)); 
/*       */         } else {
/*       */           UniversalDocumentConversionListener universalDocumentConversionListener;
/*       */           if (downloadState == DownloadState.FAILED) {
/*       */             if ((PDFViewCtrl.f(pDFViewCtrl)).a != null) {
/*       */               PDFViewCtrl.f(pDFViewCtrl).d();
/*       */               if (PDFViewCtrl.g(pDFViewCtrl) != null)
/*       */                 for (Iterator<UniversalDocumentConversionListener> iterator = PDFViewCtrl.g(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */                   (universalDocumentConversionListener = iterator.next()).onConversionEvent(ConversionState.FAILED, 0);
/*       */             } 
/*       */           } else if (downloadState == DownloadState.OPENED) {
/*       */             PDFViewCtrl.d(pDFViewCtrl, universalDocumentConversionListener.longValue());
/*       */             try {
/*       */               PDFViewCtrl.e(pDFViewCtrl, universalDocumentConversionListener.longValue());
/*       */             } catch (PDFNetException pDFNetException) {
/*       */               (integer1 = null).printStackTrace();
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         if (downloadState == DownloadState.FINISHED || downloadState == DownloadState.FAILED)
/*       */           PDFViewCtrl.n(pDFViewCtrl, false); 
/*       */         if ((PDFViewCtrl.f(pDFViewCtrl)).a == null) {
/*       */           if (downloadState == DownloadState.PAGE || downloadState == DownloadState.FINISHED || downloadState == DownloadState.NAMED_DESTS || downloadState == DownloadState.THUMB || downloadState == DownloadState.OUTLINE || downloadState == DownloadState.FAILED) {
/*       */             if (PDFViewCtrl.ai(pDFViewCtrl) != null)
/*       */               for (Iterator<DocumentDownloadListener> iterator = PDFViewCtrl.ai(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */                 (documentDownloadListener = iterator.next()).onDownloadEvent(downloadState, integer2.intValue(), PDFViewCtrl.ag(pDFViewCtrl), PDFViewCtrl.ah(pDFViewCtrl), str);  
/*       */             if (PDFViewCtrl.t(pDFViewCtrl) != null)
/*       */               PDFViewCtrl.t(pDFViewCtrl).onDocumentDownloadEvent(downloadState, integer2.intValue(), PDFViewCtrl.ag(pDFViewCtrl), PDFViewCtrl.ah(pDFViewCtrl), str); 
/*       */           } 
/*       */           if (!PDFViewCtrl.i(pDFViewCtrl).hasMessages(0))
/*       */             PDFViewCtrl.i(pDFViewCtrl).sendEmptyMessageDelayed(0, 200L); 
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class f extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     f(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.aj(pDFViewCtrl) != null && param1Message.obj instanceof Vector) {
/*       */         Vector<String> vector;
/*       */         String str = (vector = (Vector<String>)param1Message.obj).elementAt(0);
/*       */         PDFViewCtrl.aj(pDFViewCtrl).onErrorReportEvent(str);
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class s extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     s(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.ak(pDFViewCtrl) != null && param1Message.obj instanceof Vector) {
/*       */         Vector<Integer> vector;
/*       */         int j = ((Integer)(vector = (Vector<Integer>)param1Message.obj).elementAt(0)).intValue();
/*       */         int[] arrayOfInt = (int[])vector.elementAt(1);
/*       */         int k = ((Integer)vector.elementAt(2)).intValue();
/*       */         int i = ((Integer)vector.elementAt(3)).intValue();
/*       */         for (Iterator<ThumbAsyncListener> iterator = PDFViewCtrl.ak(pDFViewCtrl).iterator(); iterator.hasNext();)
/*       */           (thumbAsyncListener = iterator.next()).onThumbReceived(j, arrayOfInt, k, i); 
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class a extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     a(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.al(pDFViewCtrl) != null && param1Message.obj instanceof Vector) {
/*       */         Vector<Action> vector;
/*       */         Action action = (vector = (Vector<Action>)param1Message.obj).elementAt(0);
/*       */         PDFViewCtrl.al(pDFViewCtrl).onActionCompleted(action);
/*       */       } 
/*       */     }
/*       */   }
/*       */   
/*       */   static class r extends Handler {
/*       */     private final WeakReference<PDFViewCtrl> a;
/*       */     
/*       */     r(PDFViewCtrl param1PDFViewCtrl) {
/*       */       this.a = new WeakReference<>(param1PDFViewCtrl);
/*       */     }
/*       */     
/*       */     public final void handleMessage(Message param1Message) {
/*       */       PDFViewCtrl pDFViewCtrl;
/*       */       int i;
/*       */       if ((pDFViewCtrl = this.a.get()) != null && PDFViewCtrl.ac(pDFViewCtrl) != null && (i = (int)(PDFViewCtrl.am(pDFViewCtrl) * 100.0F)) >= 0)
/*       */         PDFViewCtrl.ac(pDFViewCtrl).onTextSearchProgress(i); 
/*       */     }
/*       */   }
/*       */   
/*       */   class c extends AlertDialog {
/*       */     private EditText b;
/*       */     private PDFDoc c;
/*       */     private int d;
/*       */     
/*       */     c(PDFViewCtrl this$0, Context param1Context) {
/*       */       super(param1Context);
/*       */       this.d = 0;
/*       */       this.c = null;
/*       */       setTitle("Password");
/*       */       this.b = new EditText(param1Context);
/*       */       this.b.setLayoutParams(new LayoutParams(-1, -1));
/*       */       this.b.setSingleLine();
/*       */       this.b.setTransformationMethod((TransformationMethod)new PasswordTransformationMethod());
/*       */       this.b.setOnEditorActionListener(new TextView.OnEditorActionListener(this, this$0) {
/*       */             public final boolean onEditorAction(TextView param2TextView, int param2Int, KeyEvent param2KeyEvent) {
/*       */               if (param2Int == 0 || param2Int == 6 || (param2KeyEvent != null && param2KeyEvent.getKeyCode() == 66)) {
/*       */                 PDFViewCtrl.c.a(this.a);
/*       */                 return true;
/*       */               } 
/*       */               return false;
/*       */             }
/*       */           });
/*       */       setOnShowListener(new OnShowListener(this, this$0) {
/*       */             public final void onShow(DialogInterface param2DialogInterface) {
/*       */               Button button;
/*       */               if ((button = (Button)((PDFViewCtrl.an(this.a.a) != null) ? PDFViewCtrl.an(this.a.a).getButton(-1) : null)) != null)
/*       */                 button.setOnClickListener(new View.OnClickListener(this) {
/*       */                       public final void onClick(View param3View) {
/*       */                         PDFViewCtrl.c.a(this.a.a);
/*       */                       }
/*       */                     }); 
/*       */             }
/*       */           });
/*       */       setView((View)this.b, 8, 8, 8, 8);
/*       */       setButton(-1, "OK", new OnClickListener(this, this$0) {
/*       */             public final void onClick(DialogInterface param2DialogInterface, int param2Int) {}
/*       */           });
/*       */       setButton(-2, "Cancel", new OnClickListener(this, this$0) {
/*       */             public final void onClick(DialogInterface param2DialogInterface, int param2Int) {}
/*       */           });
/*       */     }
/*       */     
/*       */     public final void a(PDFDoc param1PDFDoc) {
/*       */       this.c = param1PDFDoc;
/*       */       this.d = 0;
/*       */     }
/*       */     
/*       */     public final void show() {
/*       */       this.b.setText("");
/*       */       super.show();
/*       */     }
/*       */   }
/*       */   
/*       */   class j {
/*       */     private boolean a;
/*       */     private boolean b;
/*       */     private boolean c;
/*       */     private boolean d;
/*       */     private boolean e;
/*       */     private boolean f;
/*       */     private float g;
/*       */     private float h;
/*       */     private float i;
/*       */     private float j;
/*       */     private float k;
/*       */     private final float l;
/*       */     private final int m;
/*       */     private final float n;
/*       */     private int o;
/*       */     private int p;
/*       */     private int q;
/*       */     private int r;
/*       */     private int s;
/*       */     private int t;
/*       */     private int u = -1;
/*       */     private int v;
/*       */     private boolean w = false;
/*       */     private boolean x = false;
/*       */     private int y;
/*       */     private int z;
/*       */     private boolean A;
/*       */     private boolean B;
/*       */     private boolean C;
/*       */     private boolean D;
/*       */     private boolean E;
/*       */     private boolean F;
/*       */     private boolean G;
/*       */     private int H;
/*       */     private int I;
/*       */     private int J;
/*       */     private int K;
/*       */     private int L;
/*       */     
/*       */     j(PDFViewCtrl this$0, int param1Int1, int param1Int2) {
/*       */       this.b = false;
/*       */       this.d = false;
/*       */       this.c = false;
/*       */       this.e = false;
/*       */       this.f = false;
/*       */       this.E = false;
/*       */       this.F = false;
/*       */       this.k = PDFViewCtrl.a(this$0, 10.0F);
/*       */       this.l = PDFViewCtrl.a(this$0, param1Int2);
/*       */       this.m = param1Int1;
/*       */       this.n = PDFViewCtrl.a(this$0, 30.0F);
/*       */       this.B = false;
/*       */       this.C = false;
/*       */       this.D = false;
/*       */       this.a = false;
/*       */     }
/*       */     
/*       */     final j a() {
/*       */       this.o = this.M.getCurrentPage();
/*       */       this.s = this.o;
/*       */       this.t = this.o;
/*       */       this.u = -1;
/*       */       this.q = this.M.getPageCount();
/*       */       boolean bool = this.b;
/*       */       if (!this.b) {
/*       */         this.A = false;
/*       */         this.z = this.M.getCurCanvasId();
/*       */         this.y = this.M.getCurrentPage();
/*       */       } 
/*       */       this.r = this.q;
/*       */       PagePresentationMode pagePresentationMode;
/*       */       if ((pagePresentationMode = this.M.getPagePresentationMode()) == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */         this.r = PDFViewCtrl.f(this.M, this.q) ? this.q : (this.q + 1);
/*       */       } else if (pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) {
/*       */         this.r = PDFViewCtrl.f(this.M, this.q) ? (this.q + 1) : this.q;
/*       */       } 
/*       */       this.E = false;
/*       */       this.F = true;
/*       */       this.d = false;
/*       */       this.G = false;
/*       */       this.a = false;
/*       */       PDFViewCtrl.ao(this.M);
/*       */       this.C = false;
/*       */       this.D = false;
/*       */       if (PDFViewCtrl.a(this.M, pagePresentationMode)) {
/*       */         if (PDFViewCtrl.ap(this.M) == PDFViewCtrl.l(this.M)) {
/*       */           this.B = false;
/*       */           if (PDFViewCtrl.aq(this.M) != PDFViewCtrl.k(this.M))
/*       */             this.D = true; 
/*       */         } else {
/*       */           this.B = true;
/*       */         } 
/*       */       } else if (PDFViewCtrl.aq(this.M) == PDFViewCtrl.k(this.M)) {
/*       */         this.B = false;
/*       */         if (PDFViewCtrl.ap(this.M) != PDFViewCtrl.l(this.M))
/*       */           this.C = true; 
/*       */       } else {
/*       */         this.B = true;
/*       */       } 
/*       */       if (this.c) {
/*       */         this.e = true;
/*       */         if (PDFViewCtrl.a(this.M, pagePresentationMode)) {
/*       */           this.p = PDFViewCtrl.ar(this.M);
/*       */         } else {
/*       */           this.p = PDFViewCtrl.as(this.M);
/*       */         } 
/*       */       } else {
/*       */         this.p = this.M.getCurCanvasId();
/*       */       } 
/*       */       if (!bool)
/*       */         if (PDFViewCtrl.a(this.M, pagePresentationMode)) {
/*       */           this.H = this.M.getScrollX();
/*       */           this.I = (this.B || PDFViewCtrl.at(this.M)) ? 0 : this.M.getScrollY();
/*       */         } else {
/*       */           this.H = (this.B || PDFViewCtrl.at(this.M)) ? this.M.getScrollX() : 0;
/*       */           this.I = this.M.getScrollY();
/*       */         }  
/*       */       this.v = 0;
/*       */       return this;
/*       */     }
/*       */     
/*       */     final void a(MotionEvent param1MotionEvent) {
/*       */       this.g = param1MotionEvent.getX();
/*       */       this.h = param1MotionEvent.getY();
/*       */       this.i = param1MotionEvent.getX();
/*       */       this.j = param1MotionEvent.getY();
/*       */       a();
/*       */     }
/*       */     
/*       */     final boolean b(MotionEvent param1MotionEvent) {
/*       */       if (!this.F || this.q == 0)
/*       */         return false; 
/*       */       float f2 = param1MotionEvent.getX();
/*       */       float f1 = param1MotionEvent.getY();
/*       */       int m = this.M.getScrollX();
/*       */       int n = this.M.getScrollY();
/*       */       float f3 = this.i - f2;
/*       */       float f4 = this.j - f1;
/*       */       float f5 = f2 - this.g;
/*       */       float f6 = f1 - this.h;
/*       */       this.i = f2;
/*       */       this.j = f1;
/*       */       int i = 0;
/*       */       if (PDFViewCtrl.at(this.M)) {
/*       */         PDFViewCtrl.Z(this.M);
/*       */         if (PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M))) {
/*       */           PDFViewCtrl.g(this.M, this.M.getScrollOffsetForCanvasId(this.M.getCurCanvasId()));
/*       */           PDFViewCtrl.h(this.M, PDFViewCtrl.au(this.M));
/*       */         } else {
/*       */           PDFViewCtrl.i(this.M, this.M.getScrollOffsetForCanvasId(this.M.getCurCanvasId()));
/*       */           PDFViewCtrl.j(this.M, PDFViewCtrl.av(this.M));
/*       */         } 
/*       */       } 
/*       */       int k = (this.B && !PDFViewCtrl.at(this.M)) ? m : (m - PDFViewCtrl.aw(this.M));
/*       */       int i1 = (this.B || PDFViewCtrl.at(this.M)) ? (PDFViewCtrl.aw(this.M) + Math.abs(this.v)) : this.M.getScrollX();
/*       */       int i2 = (this.B && !PDFViewCtrl.at(this.M)) ? n : (n - PDFViewCtrl.ax(this.M));
/*       */       int i3 = (this.B || PDFViewCtrl.at(this.M)) ? (PDFViewCtrl.ax(this.M) + Math.abs(this.v)) : this.M.getScrollY();
/*       */       if (!this.b)
/*       */         if (!PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M)) && Math.abs(f5) > Math.abs(f6)) {
/*       */           if (f5 >= 3.0F) {
/*       */             if (k <= 1.0F) {
/*       */               if (m != i1)
/*       */                 if (!PDFViewCtrl.at(this.M) && !this.a) {
/*       */                   this.a = true;
/*       */                   PDFViewCtrl.a(this.M, i1, 0);
/*       */                 }  
/*       */               this.b = true;
/*       */             } 
/*       */           } else if (f5 <= -3.0F) {
/*       */             if ((k + PDFViewCtrl.aq(this.M)) >= this.M.getViewCanvasWidth() - 1.0F) {
/*       */               if (m != i1)
/*       */                 if (!PDFViewCtrl.at(this.M) && !this.a) {
/*       */                   this.a = true;
/*       */                   PDFViewCtrl.a(this.M, i1, 0);
/*       */                 }  
/*       */               this.b = true;
/*       */             } 
/*       */           } 
/*       */         } else if (PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M)) && Math.abs(f6) > Math.abs(f5)) {
/*       */           if (f6 >= 3.0F) {
/*       */             if (i2 <= 1.0F) {
/*       */               if (n != i3)
/*       */                 if (!PDFViewCtrl.at(this.M) && !this.a) {
/*       */                   this.a = true;
/*       */                   PDFViewCtrl.a(this.M, 0, i3);
/*       */                 }  
/*       */               this.b = true;
/*       */             } 
/*       */           } else if (f6 <= -3.0F) {
/*       */             if ((i2 + PDFViewCtrl.ap(this.M)) >= this.M.getViewCanvasHeight() - 1.0F) {
/*       */               if (n != i3)
/*       */                 if (!PDFViewCtrl.at(this.M) && !this.a) {
/*       */                   this.a = true;
/*       */                   PDFViewCtrl.a(this.M, 0, i3);
/*       */                 }  
/*       */               this.b = true;
/*       */             } 
/*       */           } 
/*       */         } else if (this.b) {
/*       */           return true;
/*       */         }  
/*       */       if (this.b) {
/*       */         c(this.p);
/*       */         if (b(this.o, this.p))
/*       */           c(d(this.p)); 
/*       */         if (c(this.o, this.p))
/*       */           c(e(this.p)); 
/*       */         if (PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M))) {
/*       */           if (f4 < 0.0F) {
/*       */             this.x = false;
/*       */             k = 0;
/*       */             if (i3 > 0) {
/*       */               k = Math.max(-i3, (int)f4);
/*       */               this.v += k;
/*       */             } else if ((m = (this.o == 1 || this.o == 2) ? 1 : 0) != 0 && PDFViewCtrl.t(this.M) != null) {
/*       */               PDFViewCtrl.t(this.M).onPullEdgeEffects(-1, Math.abs(f4) / PDFViewCtrl.ap(this.M));
/*       */             } 
/*       */             m = this.M.getScrollY();
/*       */             PDFViewCtrl.ay(this.M).startScroll(0, m, 0, k, 0);
/*       */             i = 1;
/*       */           } else if (f4 > 0.0F) {
/*       */             this.x = true;
/*       */             if (PDFViewCtrl.at(this.M)) {
/*       */               k = PDFViewCtrl.az(this.M) - PDFViewCtrl.ap(this.M) - (int)(PDFViewCtrl.d(this.M)).k - this.M.getScrollY();
/*       */             } else {
/*       */               k = (m = this.B ? PDFViewCtrl.aA(this.M) : PDFViewCtrl.az(this.M)) - i3 - PDFViewCtrl.ap(this.M);
/*       */             } 
/*       */             m = 0;
/*       */             if (k > 0) {
/*       */               m = Math.min(k, (int)f4);
/*       */               this.v += m;
/*       */             } else if ((i = (this.o == this.q) ? 1 : 0) && PDFViewCtrl.t(this.M) != null) {
/*       */               PDFViewCtrl.t(this.M).onPullEdgeEffects(1, Math.abs(f4) / PDFViewCtrl.ap(this.M));
/*       */               PDFViewCtrl.f(this.M).a(true);
/*       */             } 
/*       */             i = this.M.getScrollY();
/*       */             PDFViewCtrl.ay(this.M).startScroll(0, i, 0, m, 0);
/*       */             i = 1;
/*       */           } 
/*       */           if (!this.A) {
/*       */             k = this.M.getScrollOffsetForCanvasId(this.z);
/*       */             m = this.M.getScrollY();
/*       */             if (Math.abs(k - m) > PDFViewCtrl.ap(this.M))
/*       */               this.A = true; 
/*       */           } 
/*       */         } else {
/*       */           if (f3 < 0.0F) {
/*       */             this.w = false;
/*       */             k = 0;
/*       */             if (i1 > 0) {
/*       */               k = Math.max(-i1, (int)f3);
/*       */               this.v += k;
/*       */             } else if ((m = !PDFViewCtrl.aB(this.M) ? ((this.o == 1 || this.o == 2) ? 1 : 0) : ((this.o == this.q) ? 1 : 0)) != 0 && PDFViewCtrl.t(this.M) != null) {
/*       */               PDFViewCtrl.t(this.M).onPullEdgeEffects(-1, Math.abs(f3) / PDFViewCtrl.aq(this.M));
/*       */               if (PDFViewCtrl.aB(this.M))
/*       */                 PDFViewCtrl.f(this.M).a(true); 
/*       */             } 
/*       */             m = this.M.getScrollX();
/*       */             PDFViewCtrl.aC(this.M).startScroll(m, 0, k, 0, 0);
/*       */             i = 1;
/*       */           } else if (f3 > 0.0F) {
/*       */             this.w = true;
/*       */             if (PDFViewCtrl.at(this.M)) {
/*       */               k = PDFViewCtrl.aD(this.M) - PDFViewCtrl.aq(this.M) - (int)(PDFViewCtrl.d(this.M)).k - this.M.getScrollX();
/*       */             } else {
/*       */               k = (m = this.B ? PDFViewCtrl.aE(this.M) : PDFViewCtrl.aD(this.M)) - i1 - PDFViewCtrl.aq(this.M);
/*       */             } 
/*       */             m = 0;
/*       */             if (k > 0) {
/*       */               m = Math.min(k, (int)f3);
/*       */               this.v += m;
/*       */             } else if ((i = !PDFViewCtrl.aB(this.M) ? ((this.o == this.q) ? 1 : 0) : ((this.o == 1 || this.o == 2) ? 1 : 0)) != 0 && PDFViewCtrl.t(this.M) != null) {
/*       */               PDFViewCtrl.t(this.M).onPullEdgeEffects(1, Math.abs(f3) / PDFViewCtrl.aq(this.M));
/*       */               if (!PDFViewCtrl.aB(this.M))
/*       */                 PDFViewCtrl.f(this.M).a(true); 
/*       */             } 
/*       */             i = this.M.getScrollX();
/*       */             PDFViewCtrl.aC(this.M).startScroll(i, 0, m, 0, 0);
/*       */             i = 1;
/*       */           } 
/*       */           if (!this.A) {
/*       */             k = this.M.getScrollOffsetForCanvasId(this.z);
/*       */             m = this.M.getScrollX();
/*       */             if (Math.abs(k - m) > PDFViewCtrl.aq(this.M))
/*       */               this.A = true; 
/*       */           } 
/*       */         } 
/*       */         if (!this.f) {
/*       */           PDFViewCtrl.a(this.M, PageChangeState.BEGIN);
/*       */           PDFViewCtrl.aF(this.M).removeMessages(0);
/*       */           PDFViewCtrl.aF(this.M).sendEmptyMessage(0);
/*       */           this.f = true;
/*       */         } 
/*       */         if (this.t <= 0)
/*       */           this.t = 1; 
/*       */         if (this.t > this.q)
/*       */           this.t = this.q; 
/*       */       } 
/*       */       if (i != 0)
/*       */         this.M.invalidate(); 
/*       */       return i;
/*       */     }
/*       */     
/*       */     final boolean a(float param1Float1, float param1Float2) {
/*       */       // Byte code:
/*       */       //   0: aload_0
/*       */       //   1: getfield F : Z
/*       */       //   4: ifeq -> 885
/*       */       //   7: aload_0
/*       */       //   8: getfield E : Z
/*       */       //   11: ifne -> 885
/*       */       //   14: aload_0
/*       */       //   15: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   18: invokestatic ao : (Lcom/pdftron/pdf/PDFViewCtrl;)V
/*       */       //   21: iconst_0
/*       */       //   22: istore_3
/*       */       //   23: aload_0
/*       */       //   24: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   27: aload_0
/*       */       //   28: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   31: invokestatic Y : (Lcom/pdftron/pdf/PDFViewCtrl;)Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   34: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl;Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */       //   37: ifeq -> 134
/*       */       //   40: fload_2
/*       */       //   41: invokestatic abs : (F)F
/*       */       //   44: aload_0
/*       */       //   45: getfield l : F
/*       */       //   48: fcmpl
/*       */       //   49: ifle -> 311
/*       */       //   52: fload_2
/*       */       //   53: fconst_0
/*       */       //   54: fcmpg
/*       */       //   55: ifge -> 96
/*       */       //   58: aload_0
/*       */       //   59: iconst_1
/*       */       //   60: putfield x : Z
/*       */       //   63: aload_0
/*       */       //   64: invokespecial c : ()Z
/*       */       //   67: ifeq -> 311
/*       */       //   70: aload_0
/*       */       //   71: aload_0
/*       */       //   72: invokespecial e : ()I
/*       */       //   75: putfield u : I
/*       */       //   78: aload_0
/*       */       //   79: aload_0
/*       */       //   80: getfield u : I
/*       */       //   83: istore_2
/*       */       //   84: dup
/*       */       //   85: astore_1
/*       */       //   86: iload_2
/*       */       //   87: iconst_m1
/*       */       //   88: invokespecial a : (II)V
/*       */       //   91: iconst_1
/*       */       //   92: istore_3
/*       */       //   93: goto -> 311
/*       */       //   96: aload_0
/*       */       //   97: iconst_0
/*       */       //   98: putfield x : Z
/*       */       //   101: aload_0
/*       */       //   102: invokespecial b : ()Z
/*       */       //   105: ifeq -> 311
/*       */       //   108: aload_0
/*       */       //   109: aload_0
/*       */       //   110: invokespecial d : ()I
/*       */       //   113: putfield u : I
/*       */       //   116: aload_0
/*       */       //   117: aload_0
/*       */       //   118: getfield u : I
/*       */       //   121: istore_2
/*       */       //   122: dup
/*       */       //   123: astore_1
/*       */       //   124: iload_2
/*       */       //   125: iconst_m1
/*       */       //   126: invokespecial a : (II)V
/*       */       //   129: iconst_1
/*       */       //   130: istore_3
/*       */       //   131: goto -> 311
/*       */       //   134: fload_1
/*       */       //   135: invokestatic abs : (F)F
/*       */       //   138: aload_0
/*       */       //   139: getfield l : F
/*       */       //   142: fcmpl
/*       */       //   143: ifle -> 311
/*       */       //   146: fload_1
/*       */       //   147: fconst_0
/*       */       //   148: fcmpg
/*       */       //   149: ifge -> 233
/*       */       //   152: aload_0
/*       */       //   153: iconst_1
/*       */       //   154: putfield w : Z
/*       */       //   157: aload_0
/*       */       //   158: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   161: invokestatic aB : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   164: ifne -> 200
/*       */       //   167: aload_0
/*       */       //   168: invokespecial c : ()Z
/*       */       //   171: ifeq -> 311
/*       */       //   174: aload_0
/*       */       //   175: aload_0
/*       */       //   176: invokespecial e : ()I
/*       */       //   179: putfield u : I
/*       */       //   182: aload_0
/*       */       //   183: aload_0
/*       */       //   184: getfield u : I
/*       */       //   187: istore_2
/*       */       //   188: dup
/*       */       //   189: astore_1
/*       */       //   190: iload_2
/*       */       //   191: iconst_m1
/*       */       //   192: invokespecial a : (II)V
/*       */       //   195: iconst_1
/*       */       //   196: istore_3
/*       */       //   197: goto -> 311
/*       */       //   200: aload_0
/*       */       //   201: invokespecial b : ()Z
/*       */       //   204: ifeq -> 311
/*       */       //   207: aload_0
/*       */       //   208: aload_0
/*       */       //   209: invokespecial d : ()I
/*       */       //   212: putfield u : I
/*       */       //   215: aload_0
/*       */       //   216: aload_0
/*       */       //   217: getfield u : I
/*       */       //   220: istore_2
/*       */       //   221: dup
/*       */       //   222: astore_1
/*       */       //   223: iload_2
/*       */       //   224: iconst_m1
/*       */       //   225: invokespecial a : (II)V
/*       */       //   228: iconst_1
/*       */       //   229: istore_3
/*       */       //   230: goto -> 311
/*       */       //   233: aload_0
/*       */       //   234: iconst_0
/*       */       //   235: putfield w : Z
/*       */       //   238: aload_0
/*       */       //   239: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   242: invokestatic aB : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   245: ifne -> 281
/*       */       //   248: aload_0
/*       */       //   249: invokespecial b : ()Z
/*       */       //   252: ifeq -> 311
/*       */       //   255: aload_0
/*       */       //   256: aload_0
/*       */       //   257: invokespecial d : ()I
/*       */       //   260: putfield u : I
/*       */       //   263: aload_0
/*       */       //   264: aload_0
/*       */       //   265: getfield u : I
/*       */       //   268: istore_2
/*       */       //   269: dup
/*       */       //   270: astore_1
/*       */       //   271: iload_2
/*       */       //   272: iconst_m1
/*       */       //   273: invokespecial a : (II)V
/*       */       //   276: iconst_1
/*       */       //   277: istore_3
/*       */       //   278: goto -> 311
/*       */       //   281: aload_0
/*       */       //   282: invokespecial c : ()Z
/*       */       //   285: ifeq -> 311
/*       */       //   288: aload_0
/*       */       //   289: aload_0
/*       */       //   290: invokespecial e : ()I
/*       */       //   293: putfield u : I
/*       */       //   296: aload_0
/*       */       //   297: aload_0
/*       */       //   298: getfield u : I
/*       */       //   301: istore_2
/*       */       //   302: dup
/*       */       //   303: astore_1
/*       */       //   304: iload_2
/*       */       //   305: iconst_m1
/*       */       //   306: invokespecial a : (II)V
/*       */       //   309: iconst_1
/*       */       //   310: istore_3
/*       */       //   311: iload_3
/*       */       //   312: ifne -> 878
/*       */       //   315: aload_0
/*       */       //   316: iconst_1
/*       */       //   317: putfield G : Z
/*       */       //   320: aload_0
/*       */       //   321: aload_0
/*       */       //   322: dup
/*       */       //   323: astore_1
/*       */       //   324: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   327: invokevirtual getWidth : ()I
/*       */       //   330: istore_2
/*       */       //   331: aload_1
/*       */       //   332: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   335: invokevirtual getHeight : ()I
/*       */       //   338: istore_3
/*       */       //   339: aload_1
/*       */       //   340: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   343: invokevirtual getScrollX : ()I
/*       */       //   346: istore #4
/*       */       //   348: aload_1
/*       */       //   349: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   352: invokevirtual getScrollY : ()I
/*       */       //   355: istore #5
/*       */       //   357: aload_1
/*       */       //   358: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   361: aload_1
/*       */       //   362: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   365: invokestatic Y : (Lcom/pdftron/pdf/PDFViewCtrl;)Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   368: invokestatic a : (Lcom/pdftron/pdf/PDFViewCtrl;Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;)Z
/*       */       //   371: dup
/*       */       //   372: istore #6
/*       */       //   374: ifeq -> 415
/*       */       //   377: aload_1
/*       */       //   378: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   381: invokestatic ay : (Lcom/pdftron/pdf/PDFViewCtrl;)Landroid/widget/OverScroller;
/*       */       //   384: invokevirtual computeScrollOffset : ()Z
/*       */       //   387: ifeq -> 403
/*       */       //   390: aload_1
/*       */       //   391: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   394: invokestatic ay : (Lcom/pdftron/pdf/PDFViewCtrl;)Landroid/widget/OverScroller;
/*       */       //   397: invokevirtual getCurrY : ()I
/*       */       //   400: goto -> 410
/*       */       //   403: aload_1
/*       */       //   404: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   407: invokevirtual getScrollY : ()I
/*       */       //   410: istore #5
/*       */       //   412: goto -> 450
/*       */       //   415: aload_1
/*       */       //   416: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   419: invokestatic aC : (Lcom/pdftron/pdf/PDFViewCtrl;)Landroid/widget/OverScroller;
/*       */       //   422: invokevirtual computeScrollOffset : ()Z
/*       */       //   425: ifeq -> 441
/*       */       //   428: aload_1
/*       */       //   429: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   432: invokestatic aC : (Lcom/pdftron/pdf/PDFViewCtrl;)Landroid/widget/OverScroller;
/*       */       //   435: invokevirtual getCurrX : ()I
/*       */       //   438: goto -> 448
/*       */       //   441: aload_1
/*       */       //   442: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   445: invokevirtual getScrollX : ()I
/*       */       //   448: istore #4
/*       */       //   450: aload_1
/*       */       //   451: getfield p : I
/*       */       //   454: istore #7
/*       */       //   456: iconst_0
/*       */       //   457: istore #8
/*       */       //   459: iconst_0
/*       */       //   460: istore #9
/*       */       //   462: iload #6
/*       */       //   464: ifeq -> 484
/*       */       //   467: iload #5
/*       */       //   469: aload_1
/*       */       //   470: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   473: iload #7
/*       */       //   475: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */       //   478: isub
/*       */       //   479: istore #9
/*       */       //   481: goto -> 498
/*       */       //   484: iload #4
/*       */       //   486: aload_1
/*       */       //   487: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   490: iload #7
/*       */       //   492: invokevirtual getScrollOffsetForCanvasId : (I)I
/*       */       //   495: isub
/*       */       //   496: istore #8
/*       */       //   498: iconst_1
/*       */       //   499: istore #4
/*       */       //   501: aload_1
/*       */       //   502: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   505: invokevirtual getPagePresentationMode : ()Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   508: dup
/*       */       //   509: astore #5
/*       */       //   511: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.FACING : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   514: if_acmpeq -> 525
/*       */       //   517: aload #5
/*       */       //   519: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.FACING_VERT : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   522: if_acmpne -> 528
/*       */       //   525: iconst_2
/*       */       //   526: istore #4
/*       */       //   528: iload #6
/*       */       //   530: ifeq -> 550
/*       */       //   533: aload_1
/*       */       //   534: getfield p : I
/*       */       //   537: iload #4
/*       */       //   539: if_icmple -> 546
/*       */       //   542: iconst_1
/*       */       //   543: goto -> 593
/*       */       //   546: iconst_0
/*       */       //   547: goto -> 593
/*       */       //   550: aload_1
/*       */       //   551: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   554: invokestatic aB : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   557: ifne -> 577
/*       */       //   560: aload_1
/*       */       //   561: getfield p : I
/*       */       //   564: iload #4
/*       */       //   566: if_icmple -> 573
/*       */       //   569: iconst_1
/*       */       //   570: goto -> 593
/*       */       //   573: iconst_0
/*       */       //   574: goto -> 593
/*       */       //   577: aload_1
/*       */       //   578: getfield p : I
/*       */       //   581: aload_1
/*       */       //   582: getfield r : I
/*       */       //   585: if_icmpge -> 592
/*       */       //   588: iconst_1
/*       */       //   589: goto -> 593
/*       */       //   592: iconst_0
/*       */       //   593: istore #5
/*       */       //   595: iload #6
/*       */       //   597: ifeq -> 619
/*       */       //   600: aload_1
/*       */       //   601: getfield p : I
/*       */       //   604: aload_1
/*       */       //   605: getfield q : I
/*       */       //   608: if_icmpge -> 615
/*       */       //   611: iconst_1
/*       */       //   612: goto -> 662
/*       */       //   615: iconst_0
/*       */       //   616: goto -> 662
/*       */       //   619: aload_1
/*       */       //   620: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   623: invokestatic aB : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   626: ifne -> 648
/*       */       //   629: aload_1
/*       */       //   630: getfield p : I
/*       */       //   633: aload_1
/*       */       //   634: getfield q : I
/*       */       //   637: if_icmpge -> 644
/*       */       //   640: iconst_1
/*       */       //   641: goto -> 662
/*       */       //   644: iconst_0
/*       */       //   645: goto -> 662
/*       */       //   648: aload_1
/*       */       //   649: getfield p : I
/*       */       //   652: iload #4
/*       */       //   654: if_icmple -> 661
/*       */       //   657: iconst_1
/*       */       //   658: goto -> 662
/*       */       //   661: iconst_0
/*       */       //   662: istore #4
/*       */       //   664: iload #6
/*       */       //   666: ifeq -> 749
/*       */       //   669: iload #9
/*       */       //   671: ifge -> 690
/*       */       //   674: iload #5
/*       */       //   676: ifeq -> 690
/*       */       //   679: iload #9
/*       */       //   681: invokestatic abs : (I)I
/*       */       //   684: iload_3
/*       */       //   685: iconst_2
/*       */       //   686: idiv
/*       */       //   687: if_icmpgt -> 860
/*       */       //   690: iload #9
/*       */       //   692: ifle -> 868
/*       */       //   695: iload #4
/*       */       //   697: ifeq -> 868
/*       */       //   700: aload_1
/*       */       //   701: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   704: invokestatic at : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   707: ifeq -> 730
/*       */       //   710: aload_1
/*       */       //   711: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   714: invokevirtual getScrollY : ()I
/*       */       //   717: aload_1
/*       */       //   718: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   721: invokestatic d : (Lcom/pdftron/pdf/PDFViewCtrl;)Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */       //   724: getfield I : I
/*       */       //   727: isub
/*       */       //   728: istore #9
/*       */       //   730: iload #9
/*       */       //   732: iload_3
/*       */       //   733: iconst_2
/*       */       //   734: idiv
/*       */       //   735: if_icmple -> 868
/*       */       //   738: aload_1
/*       */       //   739: iload #7
/*       */       //   741: invokespecial e : (I)I
/*       */       //   744: istore #7
/*       */       //   746: goto -> 868
/*       */       //   749: iload #8
/*       */       //   751: ifge -> 791
/*       */       //   754: iload #5
/*       */       //   756: ifeq -> 791
/*       */       //   759: iload #8
/*       */       //   761: invokestatic abs : (I)I
/*       */       //   764: iload_2
/*       */       //   765: iconst_2
/*       */       //   766: idiv
/*       */       //   767: if_icmple -> 791
/*       */       //   770: aload_1
/*       */       //   771: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   774: invokestatic aB : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   777: ifeq -> 860
/*       */       //   780: aload_1
/*       */       //   781: iload #7
/*       */       //   783: invokespecial e : (I)I
/*       */       //   786: istore #7
/*       */       //   788: goto -> 868
/*       */       //   791: iload #8
/*       */       //   793: ifle -> 868
/*       */       //   796: iload #4
/*       */       //   798: ifeq -> 868
/*       */       //   801: aload_1
/*       */       //   802: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   805: invokestatic at : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   808: ifeq -> 831
/*       */       //   811: aload_1
/*       */       //   812: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   815: invokevirtual getScrollX : ()I
/*       */       //   818: aload_1
/*       */       //   819: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   822: invokestatic d : (Lcom/pdftron/pdf/PDFViewCtrl;)Lcom/pdftron/pdf/PDFViewCtrl$j;
/*       */       //   825: getfield H : I
/*       */       //   828: isub
/*       */       //   829: istore #8
/*       */       //   831: iload #8
/*       */       //   833: iload_2
/*       */       //   834: iconst_2
/*       */       //   835: idiv
/*       */       //   836: if_icmple -> 868
/*       */       //   839: aload_1
/*       */       //   840: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   843: invokestatic aB : (Lcom/pdftron/pdf/PDFViewCtrl;)Z
/*       */       //   846: ifne -> 860
/*       */       //   849: aload_1
/*       */       //   850: iload #7
/*       */       //   852: invokespecial e : (I)I
/*       */       //   855: istore #7
/*       */       //   857: goto -> 868
/*       */       //   860: aload_1
/*       */       //   861: iload #7
/*       */       //   863: invokespecial d : (I)I
/*       */       //   866: istore #7
/*       */       //   868: iload #7
/*       */       //   870: istore_2
/*       */       //   871: dup
/*       */       //   872: astore_1
/*       */       //   873: iload_2
/*       */       //   874: iconst_m1
/*       */       //   875: invokespecial a : (II)V
/*       */       //   878: aload_0
/*       */       //   879: iconst_1
/*       */       //   880: putfield E : Z
/*       */       //   883: iconst_1
/*       */       //   884: ireturn
/*       */       //   885: iconst_0
/*       */       //   886: ireturn
/*       */       // Line number table:
/*       */       //   Java source line number -> byte code offset
/*       */       //   #13170	-> 0
/*       */       //   #13171	-> 14
/*       */       //   #13173	-> 21
/*       */       //   #13175	-> 23
/*       */       //   #13176	-> 40
/*       */       //   #13177	-> 52
/*       */       //   #13179	-> 58
/*       */       //   #13180	-> 63
/*       */       //   #13181	-> 70
/*       */       //   #13182	-> 78
/*       */       //   #14331	-> 85
/*       */       //   #13183	-> 91
/*       */       //   #13187	-> 96
/*       */       //   #13188	-> 101
/*       */       //   #13189	-> 108
/*       */       //   #13190	-> 116
/*       */       //   #15331	-> 123
/*       */       //   #13191	-> 129
/*       */       //   #13196	-> 134
/*       */       //   #13197	-> 146
/*       */       //   #13199	-> 152
/*       */       //   #13200	-> 157
/*       */       //   #13201	-> 167
/*       */       //   #13202	-> 174
/*       */       //   #13203	-> 182
/*       */       //   #16331	-> 189
/*       */       //   #13204	-> 195
/*       */       //   #13207	-> 200
/*       */       //   #13208	-> 207
/*       */       //   #13209	-> 215
/*       */       //   #17331	-> 222
/*       */       //   #13210	-> 228
/*       */       //   #13215	-> 233
/*       */       //   #13216	-> 238
/*       */       //   #13217	-> 248
/*       */       //   #13218	-> 255
/*       */       //   #13219	-> 263
/*       */       //   #18331	-> 270
/*       */       //   #13220	-> 276
/*       */       //   #13223	-> 281
/*       */       //   #13224	-> 288
/*       */       //   #13225	-> 296
/*       */       //   #19331	-> 303
/*       */       //   #13226	-> 309
/*       */       //   #13233	-> 311
/*       */       //   #13234	-> 315
/*       */       //   #13235	-> 320
/*       */       //   #19448	-> 323
/*       */       //   #19449	-> 331
/*       */       //   #19451	-> 339
/*       */       //   #19452	-> 348
/*       */       //   #19453	-> 357
/*       */       //   #19454	-> 372
/*       */       //   #19455	-> 377
/*       */       //   #19457	-> 415
/*       */       //   #19459	-> 450
/*       */       //   #19460	-> 456
/*       */       //   #19461	-> 459
/*       */       //   #19462	-> 462
/*       */       //   #19463	-> 467
/*       */       //   #19465	-> 484
/*       */       //   #19468	-> 498
/*       */       //   #19470	-> 501
/*       */       //   #19471	-> 509
/*       */       //   #19472	-> 525
/*       */       //   #19475	-> 528
/*       */       //   #19476	-> 595
/*       */       //   #19478	-> 664
/*       */       //   #19479	-> 669
/*       */       //   #19482	-> 690
/*       */       //   #19483	-> 700
/*       */       //   #19484	-> 710
/*       */       //   #19486	-> 730
/*       */       //   #19488	-> 738
/*       */       //   #19492	-> 749
/*       */       //   #19494	-> 770
/*       */       //   #19497	-> 780
/*       */       //   #19499	-> 791
/*       */       //   #19500	-> 801
/*       */       //   #19501	-> 811
/*       */       //   #19503	-> 831
/*       */       //   #19505	-> 839
/*       */       //   #19506	-> 849
/*       */       //   #19508	-> 860
/*       */       //   #19514	-> 868
/*       */       //   #13235	-> 870
/*       */       //   #20331	-> 872
/*       */       //   #13238	-> 878
/*       */       //   #13239	-> 883
/*       */       //   #13241	-> 885
/*       */     }
/*       */     
/*       */     private int a(int param1Int) {
/*       */       int i = param1Int;
/*       */       try {
/*       */         double[] arrayOfDouble;
/*       */         if ((arrayOfDouble = PDFViewCtrl.b(this.M, param1Int)) != null) {
/*       */           int k = arrayOfDouble.length / 5;
/*       */           for (byte b = 0; b < k; b++) {
/*       */             int m = b * 5;
/*       */             if ((m = (int)arrayOfDouble[m]) < i)
/*       */               i = m; 
/*       */           } 
/*       */         } 
/*       */       } catch (Exception exception) {}
/*       */       return i;
/*       */     }
/*       */     
/*       */     private int b(int param1Int) {
/*       */       int i = param1Int;
/*       */       try {
/*       */         double[] arrayOfDouble;
/*       */         if ((arrayOfDouble = PDFViewCtrl.b(this.M, param1Int)) != null) {
/*       */           int k = arrayOfDouble.length / 5;
/*       */           for (byte b = 0; b < k; b++) {
/*       */             int m = b * 5;
/*       */             if ((m = (int)arrayOfDouble[m]) > i)
/*       */               i = m; 
/*       */           } 
/*       */         } 
/*       */       } catch (Exception exception) {}
/*       */       return i;
/*       */     }
/*       */     
/*       */     private void c(int param1Int) {
/*       */       // Byte code:
/*       */       //   0: aload_0
/*       */       //   1: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   4: getfield mDoc : Lcom/pdftron/pdf/PDFDoc;
/*       */       //   7: ifnull -> 22
/*       */       //   10: iload_1
/*       */       //   11: ifle -> 22
/*       */       //   14: iload_1
/*       */       //   15: aload_0
/*       */       //   16: getfield r : I
/*       */       //   19: if_icmple -> 23
/*       */       //   22: return
/*       */       //   23: aload_0
/*       */       //   24: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   27: invokestatic aG : (Lcom/pdftron/pdf/PDFViewCtrl;)Landroid/util/SparseArray;
/*       */       //   30: iload_1
/*       */       //   31: invokevirtual get : (I)Ljava/lang/Object;
/*       */       //   34: ifnull -> 38
/*       */       //   37: return
/*       */       //   38: aload_0
/*       */       //   39: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   42: iload_1
/*       */       //   43: invokestatic b : (Lcom/pdftron/pdf/PDFViewCtrl;I)[D
/*       */       //   46: dup
/*       */       //   47: astore_2
/*       */       //   48: ifnull -> 206
/*       */       //   51: aload_2
/*       */       //   52: arraylength
/*       */       //   53: iconst_5
/*       */       //   54: idiv
/*       */       //   55: istore_3
/*       */       //   56: iconst_1
/*       */       //   57: istore #4
/*       */       //   59: iconst_0
/*       */       //   60: istore #5
/*       */       //   62: iload #5
/*       */       //   64: iload_3
/*       */       //   65: if_icmpge -> 189
/*       */       //   68: iload #5
/*       */       //   70: iconst_5
/*       */       //   71: imul
/*       */       //   72: istore #6
/*       */       //   74: aload_2
/*       */       //   75: iload #6
/*       */       //   77: daload
/*       */       //   78: d2i
/*       */       //   79: istore #6
/*       */       //   81: aload_0
/*       */       //   82: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   85: invokevirtual getPagePresentationMode : ()Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   88: dup
/*       */       //   89: astore #7
/*       */       //   91: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.SINGLE : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   94: if_acmpeq -> 105
/*       */       //   97: aload #7
/*       */       //   99: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.SINGLE_VERT : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   102: if_acmpne -> 114
/*       */       //   105: iload #6
/*       */       //   107: iload_1
/*       */       //   108: if_icmpeq -> 183
/*       */       //   111: goto -> 180
/*       */       //   114: aload #7
/*       */       //   116: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.FACING : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   119: if_acmpeq -> 130
/*       */       //   122: aload #7
/*       */       //   124: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.FACING_VERT : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   127: if_acmpne -> 150
/*       */       //   130: iload #6
/*       */       //   132: iload_1
/*       */       //   133: if_icmpgt -> 144
/*       */       //   136: iload #6
/*       */       //   138: iload_1
/*       */       //   139: iconst_1
/*       */       //   140: isub
/*       */       //   141: if_icmpge -> 183
/*       */       //   144: iconst_0
/*       */       //   145: istore #4
/*       */       //   147: goto -> 183
/*       */       //   150: aload #7
/*       */       //   152: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.FACING_COVER : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   155: if_acmpeq -> 166
/*       */       //   158: aload #7
/*       */       //   160: getstatic com/pdftron/pdf/PDFViewCtrl$PagePresentationMode.FACING_COVER_VERT : Lcom/pdftron/pdf/PDFViewCtrl$PagePresentationMode;
/*       */       //   163: if_acmpne -> 183
/*       */       //   166: iload #6
/*       */       //   168: iload_1
/*       */       //   169: if_icmpgt -> 180
/*       */       //   172: iload #6
/*       */       //   174: iload_1
/*       */       //   175: iconst_1
/*       */       //   176: isub
/*       */       //   177: if_icmpge -> 183
/*       */       //   180: iconst_0
/*       */       //   181: istore #4
/*       */       //   183: iinc #5, 1
/*       */       //   186: goto -> 62
/*       */       //   189: iload #4
/*       */       //   191: ifeq -> 206
/*       */       //   194: aload_0
/*       */       //   195: getfield M : Lcom/pdftron/pdf/PDFViewCtrl;
/*       */       //   198: invokestatic aG : (Lcom/pdftron/pdf/PDFViewCtrl;)Landroid/util/SparseArray;
/*       */       //   201: iload_1
/*       */       //   202: aload_2
/*       */       //   203: invokevirtual put : (ILjava/lang/Object;)V
/*       */       //   206: return
/*       */       //   207: pop
/*       */       //   208: return
/*       */       // Line number table:
/*       */       //   Java source line number -> byte code offset
/*       */       //   #13287	-> 0
/*       */       //   #13288	-> 22
/*       */       //   #13290	-> 23
/*       */       //   #13292	-> 37
/*       */       //   #13296	-> 38
/*       */       //   #13297	-> 47
/*       */       //   #13298	-> 51
/*       */       //   #13300	-> 56
/*       */       //   #13302	-> 59
/*       */       //   #13303	-> 68
/*       */       //   #13304	-> 74
/*       */       //   #13307	-> 81
/*       */       //   #13308	-> 89
/*       */       //   #13309	-> 105
/*       */       //   #13310	-> 111
/*       */       //   #13312	-> 114
/*       */       //   #13313	-> 130
/*       */       //   #13314	-> 144
/*       */       //   #13316	-> 150
/*       */       //   #13317	-> 166
/*       */       //   #13318	-> 180
/*       */       //   #13302	-> 183
/*       */       //   #13322	-> 189
/*       */       //   #13323	-> 194
/*       */       //   #13327	-> 206
/*       */       //   #13326	-> 207
/*       */       //   #13328	-> 208
/*       */       // Exception table:
/*       */       //   from	to	target	type
/*       */       //   38	206	207	java/lang/Exception
/*       */     }
/*       */     
/*       */     private void a(int param1Int1, int param1Int2) {
/*       */       this.b = true;
/*       */       int i = this.M.getCurCanvasId();
/*       */       if (PDFViewCtrl.at(this.M) && i != param1Int1) {
/*       */         PDFViewCtrl.aH(this.M).put(i, PDFViewCtrl.aa(this.M));
/*       */         PDFViewCtrl.aI(this.M).put(i, PDFViewCtrl.ab(this.M));
/*       */       } 
/*       */       this.u = Math.max(1, Math.min(param1Int1, this.r));
/*       */       this.s = Math.max(1, Math.min(param1Int1, this.q));
/*       */       if (this.u == this.z)
/*       */         this.s = this.y; 
/*       */       param1Int1 = this.M.getScrollX();
/*       */       int k = this.M.getScrollY();
/*       */       if (PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M))) {
/*       */         k = PDFViewCtrl.ay(this.M).computeScrollOffset() ? PDFViewCtrl.ay(this.M).getCurrY() : this.M.getScrollY();
/*       */         this.K = this.M.getScrollOffsetForCanvasId(this.u);
/*       */       } else {
/*       */         param1Int1 = PDFViewCtrl.aC(this.M).computeScrollOffset() ? PDFViewCtrl.aC(this.M).getCurrX() : this.M.getScrollX();
/*       */         this.J = this.M.getScrollOffsetForCanvasId(this.u);
/*       */       } 
/*       */       if (!this.c)
/*       */         this.L = i; 
/*       */       if (PDFViewCtrl.at(this.M))
/*       */         if (this.p == this.u) {
/*       */           PagePresentationMode pagePresentationMode;
/*       */           int m = ((pagePresentationMode = this.M.getPagePresentationMode()) == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_VERT || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) ? 2 : 1;
/*       */           if (PDFViewCtrl.a(this.M, pagePresentationMode)) {
/*       */             int n = this.p + m;
/*       */             n = this.M.getScrollOffsetForCanvasId(n);
/*       */             m = Math.abs(k - this.M.getScrollOffsetForCanvasId(this.p));
/*       */             int i1 = Math.abs(k + PDFViewCtrl.ap(this.M) - n);
/*       */             if (m > i1)
/*       */               this.K = n - PDFViewCtrl.ap(this.M) - (int)(PDFViewCtrl.d(this.M)).k; 
/*       */           } else {
/*       */             int n = this.p + (!PDFViewCtrl.aB(this.M) ? m : -m);
/*       */             n = this.M.getScrollOffsetForCanvasId(n);
/*       */             m = Math.abs(param1Int1 - this.M.getScrollOffsetForCanvasId(this.p));
/*       */             int i1 = Math.abs(param1Int1 + PDFViewCtrl.aq(this.M) - n);
/*       */             if (m > i1)
/*       */               this.J = n - PDFViewCtrl.aq(this.M) - (int)(PDFViewCtrl.d(this.M)).k; 
/*       */           } 
/*       */         } else if (PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M))) {
/*       */           if (this.p > this.u)
/*       */             this.K = this.M.getScrollOffsetForCanvasId(this.p) - PDFViewCtrl.ap(this.M) - (int)(PDFViewCtrl.d(this.M)).k; 
/*       */         } else if ((this.p > this.u && !PDFViewCtrl.aB(this.M)) || (this.p < this.u && PDFViewCtrl.aB(this.M))) {
/*       */           this.J = this.M.getScrollOffsetForCanvasId(this.p) - PDFViewCtrl.aq(this.M) - (int)(PDFViewCtrl.d(this.M)).k;
/*       */         }  
/*       */       if (PDFViewCtrl.a(this.M, PDFViewCtrl.Y(this.M))) {
/*       */         i = this.K - k;
/*       */         if (param1Int2 < 0)
/*       */           param1Int2 = Math.min(1000, (int)(Math.abs(i) / this.M.getHeight() * this.m)); 
/*       */         PDFViewCtrl.ay(this.M).startScroll(0, k, 0, i, param1Int2);
/*       */       } else {
/*       */         i = this.J - param1Int1;
/*       */         if (param1Int2 < 0)
/*       */           param1Int2 = Math.min(1000, (int)(Math.abs(i) / this.M.getWidth() * this.m)); 
/*       */         PDFViewCtrl.aC(this.M).startScroll(param1Int1, 0, i, 0, param1Int2);
/*       */       } 
/*       */       this.c = true;
/*       */       c(this.u);
/*       */       if (b(this.u, this.u))
/*       */         c(d(this.u)); 
/*       */       if (c(this.u, this.u))
/*       */         c(e(this.u)); 
/*       */       if (this.p != this.u) {
/*       */         c(this.u);
/*       */         if (b(this.p, this.p))
/*       */           c(d(this.p)); 
/*       */         if (c(this.p, this.p))
/*       */           c(e(this.p)); 
/*       */       } 
/*       */       this.M.invalidate();
/*       */     }
/*       */     
/*       */     private boolean b(int param1Int1, int param1Int2) {
/*       */       if (this.q <= 1)
/*       */         return false; 
/*       */       PagePresentationMode pagePresentationMode;
/*       */       if ((pagePresentationMode = this.M.getPagePresentationMode()) == PagePresentationMode.SINGLE || pagePresentationMode == PagePresentationMode.SINGLE_VERT)
/*       */         return (param1Int1 > 1); 
/*       */       if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT)
/*       */         return (this.q > 2 && param1Int2 > 2); 
/*       */       if (pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT)
/*       */         return (param1Int2 >= 2); 
/*       */       return false;
/*       */     }
/*       */     
/*       */     private boolean b() {
/*       */       return b(this.o, this.p);
/*       */     }
/*       */     
/*       */     private boolean c(int param1Int1, int param1Int2) {
/*       */       if (this.q <= 1)
/*       */         return false; 
/*       */       PagePresentationMode pagePresentationMode;
/*       */       if ((pagePresentationMode = this.M.getPagePresentationMode()) == PagePresentationMode.SINGLE || pagePresentationMode == PagePresentationMode.SINGLE_VERT)
/*       */         return (param1Int1 < this.q); 
/*       */       if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT)
/*       */         return (this.q > 2 && param1Int2 < this.q); 
/*       */       if (pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT)
/*       */         return (param1Int2 < this.q); 
/*       */       return false;
/*       */     }
/*       */     
/*       */     private boolean c() {
/*       */       return c(this.o, this.p);
/*       */     }
/*       */     
/*       */     private int d(int param1Int) {
/*       */       PagePresentationMode pagePresentationMode = this.M.getPagePresentationMode();
/*       */       int i = param1Int;
/*       */       if (pagePresentationMode == PagePresentationMode.SINGLE || pagePresentationMode == PagePresentationMode.SINGLE_VERT) {
/*       */         if (param1Int > 1)
/*       */           i = param1Int - 1; 
/*       */       } else if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */         if (param1Int > 3)
/*       */           i = param1Int - 2; 
/*       */       } else if ((pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) && param1Int > 2) {
/*       */         i = param1Int - 2;
/*       */       } 
/*       */       if (i < 0)
/*       */         i = param1Int; 
/*       */       return i;
/*       */     }
/*       */     
/*       */     private int d() {
/*       */       return d(this.p);
/*       */     }
/*       */     
/*       */     private int e(int param1Int) {
/*       */       PagePresentationMode pagePresentationMode = this.M.getPagePresentationMode();
/*       */       int i = this.M.getPageCount();
/*       */       int k = param1Int;
/*       */       if (pagePresentationMode == PagePresentationMode.SINGLE || pagePresentationMode == PagePresentationMode.SINGLE_VERT) {
/*       */         if (param1Int < i)
/*       */           k = param1Int + 1; 
/*       */       } else {
/*       */         if (pagePresentationMode == PagePresentationMode.FACING || pagePresentationMode == PagePresentationMode.FACING_VERT) {
/*       */           if (param1Int < i && (k = param1Int + 2) > i) {
/*       */             if (!PDFViewCtrl.f(this.M, this.q)) {
/*       */               k = i + 1;
/*       */               return k;
/*       */             } 
/*       */           } else {
/*       */             return k;
/*       */           } 
/*       */         } else if ((pagePresentationMode == PagePresentationMode.FACING_COVER || pagePresentationMode == PagePresentationMode.FACING_COVER_VERT) && param1Int < i && (k = param1Int + 2) > i) {
/*       */           if (PDFViewCtrl.f(this.M, this.q)) {
/*       */             k = i + 1;
/*       */             return k;
/*       */           } 
/*       */         } else {
/*       */           return k;
/*       */         } 
/*       */         k = i;
/*       */       } 
/*       */       return k;
/*       */     }
/*       */     
/*       */     private int e() {
/*       */       return e(this.p);
/*       */     }
/*       */   }
/*       */   
/*       */   public void docLockRead(LockRunnable paramLockRunnable) throws Exception {
/*       */     boolean bool = false;
/*       */     try {
/*       */       docLockRead();
/*       */       bool = true;
/*       */       paramLockRunnable.run();
/*       */       return;
/*       */     } finally {
/*       */       if (bool)
/*       */         docUnlockRead(); 
/*       */     } 
/*       */   }
/*       */   
/*       */   public void docLock(boolean paramBoolean, LockRunnable paramLockRunnable) throws Exception {
/*       */     boolean bool = false;
/*       */     try {
/*       */       docLock(paramBoolean);
/*       */       bool = true;
/*       */       paramLockRunnable.run();
/*       */       return;
/*       */     } finally {
/*       */       if (bool)
/*       */         docUnlock(); 
/*       */     } 
/*       */   }
/*       */   
/*       */   private long b() {
/*       */     long l4 = Runtime.getRuntime().maxMemory();
/*       */     if (b)
/*       */       a(1, "getAllowedMaxHeapSize: " + l4, a(true)); 
/*       */     long l1 = l4;
/*       */     l4 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
/*       */     if (b)
/*       */       a(1, "getAllocatedHeapSize: " + l4, a(true)); 
/*       */     long l2 = l4;
/*       */     long l3;
/*       */     if ((l3 = l1 - l2) < 10485760L)
/*       */       l3 = 0L; 
/*       */     if (b)
/*       */       a(1, "getAvailableHeapSize: " + l3, a(true)); 
/*       */     return l3;
/*       */   }
/*       */   
/*       */   public void purgeMemoryDueToOOM() {
/*       */     ImageCache imageCache;
/*       */     (imageCache = ImageCache.a.a()).a(false);
/*       */     imageCache.a();
/*       */     imageCache.a(true);
/*       */     b.a.a().b();
/*       */     this.de.a();
/*       */   }
/*       */   
/*       */   private static native long SetJavaScriptEventCallback(long paramLong, JavaScriptEventProc paramJavaScriptEventProc, Object paramObject);
/*       */   
/*       */   private static native void RefreshAndUpdate(long paramLong1, long paramLong2);
/*       */   
/*       */   private native long[] PDFViewCtrlCreate(RenderCallback paramRenderCallback);
/*       */   
/*       */   private static native void Destroy(long paramLong);
/*       */   
/*       */   private static native void DestroyRenderData(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7);
/*       */   
/*       */   private static native void PurgeMemory(long paramLong);
/*       */   
/*       */   private static native void SetMemInfo(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native void SetDoc(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void CloseDoc(long paramLong);
/*       */   
/*       */   private static native long GetExternalAnnotManager(long paramLong, String paramString);
/*       */   
/*       */   private static native void EnableFloatingAnnotTiles(long paramLong1, long paramLong2, int paramInt);
/*       */   
/*       */   private static native void EnableUndoRedo(long paramLong);
/*       */   
/*       */   private static native String Undo(long paramLong);
/*       */   
/*       */   private static native String Redo(long paramLong);
/*       */   
/*       */   private static native void TakeSnapshot(long paramLong, String paramString);
/*       */   
/*       */   private static native String GetNextUndoInfo(long paramLong);
/*       */   
/*       */   private static native String GetNextRedoInfo(long paramLong);
/*       */   
/*       */   private static native void RevertAllChanges(long paramLong);
/*       */   
/*       */   private static native void DocLock(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void DocUnlock(long paramLong);
/*       */   
/*       */   private static native boolean DocTryLock(long paramLong, int paramInt);
/*       */   
/*       */   private static native boolean DocTryLockRead(long paramLong, int paramInt);
/*       */   
/*       */   private static native void DocLockRead(long paramLong);
/*       */   
/*       */   private static native void DocUnlockRead(long paramLong);
/*       */   
/*       */   private static native void RequestRender(long paramLong);
/*       */   
/*       */   private static native void SetCaching(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetImageSmoothing(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetRightToLeftLanguage(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetPageBox(long paramLong, int paramInt);
/*       */   
/*       */   private static native void SetOverprint(long paramLong, int paramInt);
/*       */   
/*       */   private static native void SetGamma(long paramLong, double paramDouble);
/*       */   
/*       */   private static native void HideAnnotation(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void ShowAnnotation(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void SetDrawAnnotations(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetHighlightFields(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetFieldHighlightColor(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void SetSignatureHighlightColor(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void SetRequiredFieldBorderColor(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void SetAntiAliasing(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetThinLineAdjustment(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*       */   
/*       */   private static native void SetPathHinting(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetDefaultPageColor(long paramLong, byte paramByte1, byte paramByte2, byte paramByte3);
/*       */   
/*       */   private static native void SetBackgroundColor(long paramLong, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);
/*       */   
/*       */   private static native void SetVerticalAlign(long paramLong, int paramInt);
/*       */   
/*       */   private static native void SetHorizontalAlign(long paramLong, int paramInt);
/*       */   
/*       */   private static native void SetPageBorderVisibility(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetPageTransparencyGrid(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void SetUrlExtraction(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native LinkInfo GetLinkAt(long paramLong, int paramInt1, int paramInt2);
/*       */   
/*       */   private static native void OnSize(long paramLong, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/*       */   
/*       */   private static native void OnScroll(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);
/*       */   
/*       */   private static native double GetZoom(long paramLong);
/*       */   
/*       */   private static native boolean SetZoom(long paramLong, double paramDouble, boolean paramBoolean);
/*       */   
/*       */   private static native boolean SetZoom(long paramLong, int paramInt1, int paramInt2, double paramDouble, boolean paramBoolean);
/*       */   
/*       */   private static native boolean SmartZoom(long paramLong, int paramInt1, int paramInt2);
/*       */   
/*       */   private static native void CancelRendering(long paramLong);
/*       */   
/*       */   private static native void CancelRenderingAsync(long paramLong);
/*       */   
/*       */   private static native void DoProgressiveRender(long paramLong);
/*       */   
/*       */   private static native boolean IsFinishedRendering(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void CancelAllThumbRequests(long paramLong);
/*       */   
/*       */   private static native void GetThumbAsync(long paramLong1, long paramLong2, long paramLong3);
/*       */   
/*       */   private static native void SetupThumbnails(long paramLong1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, long paramLong2, double paramDouble);
/*       */   
/*       */   private static native void ClearThumbCache(long paramLong);
/*       */   
/*       */   private static native void SetPageViewMode(long paramLong, int paramInt);
/*       */   
/*       */   private static native int GetPageViewMode(long paramLong);
/*       */   
/*       */   private static native void SetPagePresentationMode(long paramLong, int paramInt);
/*       */   
/*       */   private static native int GetPagePresentationMode(long paramLong);
/*       */   
/*       */   private static native void SetPageRefViewMode(long paramLong, int paramInt);
/*       */   
/*       */   private static native int GetPageRefViewMode(long paramLong);
/*       */   
/*       */   private static native double GetRefZoom(long paramLong, int paramInt);
/*       */   
/*       */   private static native double GetRefZoomForPage(long paramLong, int paramInt1, int paramInt2);
/*       */   
/*       */   private static native void SetColorPostProcessMode(long paramLong, int paramInt);
/*       */   
/*       */   private static native void SetColorPostProcessColors(long paramLong, int paramInt1, int paramInt2);
/*       */   
/*       */   private static native void SetColorPostProcessMapFile(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native int GetColorPostProcessMode(long paramLong);
/*       */   
/*       */   private static native long GetPostProcessedColor(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native int GetPageBox(long paramLong);
/*       */   
/*       */   private static native int GetPagesCount(long paramLong);
/*       */   
/*       */   private static native int[] GetVisiblePages(long paramLong);
/*       */   
/*       */   private static native int GetCurrentPage(long paramLong);
/*       */   
/*       */   private static native boolean GotoFirstPage(long paramLong);
/*       */   
/*       */   private static native boolean GotoLastPage(long paramLong);
/*       */   
/*       */   private static native boolean GotoNextPage(long paramLong);
/*       */   
/*       */   private static native boolean GotoPreviousPage(long paramLong);
/*       */   
/*       */   private static native boolean SetCurrentPage(long paramLong, int paramInt);
/*       */   
/*       */   private static native boolean ShowRect(long paramLong1, int paramInt, long paramLong2);
/*       */   
/*       */   private static native long[] GetAllCanvasPixelSizes(long paramLong);
/*       */   
/*       */   private static native double GetCanvasWidth(long paramLong);
/*       */   
/*       */   private static native double GetCanvasHeight(long paramLong);
/*       */   
/*       */   private static native int GetTilingRegionWidth(long paramLong);
/*       */   
/*       */   private static native int GetTilingRegionHeight(long paramLong);
/*       */   
/*       */   private static native double GetHScrollPos(long paramLong);
/*       */   
/*       */   private static native double GetVScrollPos(long paramLong);
/*       */   
/*       */   private static native void RotateClockwise(long paramLong);
/*       */   
/*       */   private static native void RotateCounterClockwise(long paramLong);
/*       */   
/*       */   private static native int GetRotation(long paramLong);
/*       */   
/*       */   private static native void SetPageSpacing(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*       */   
/*       */   private static native int[] GetPageSpacing(long paramLong);
/*       */   
/*       */   private static native int GetCurCanvasId(long paramLong);
/*       */   
/*       */   private static native double[] GetPageRects(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*       */   
/*       */   private static native double[] GetPageRectsOnCanvas(long paramLong, int paramInt);
/*       */   
/*       */   private static native double[] ConvScreenPtToPagePt(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*       */   
/*       */   private static native double[] ConvScreenPtToCanvasPt(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native double[] ConvScreenPtToCanvasPt(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*       */   
/*       */   private static native double[] ConvCanvasPtToScreenPt(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native double[] ConvCanvasPtToScreenPt(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*       */   
/*       */   private static native double[] ConvCanvasPtToPagePt(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*       */   
/*       */   private static native double[] ConvPagePtToCanvasPt(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*       */   
/*       */   private static native double[] ConvPagePtToScreenPt(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*       */   
/*       */   private static native double[] SnapToNearestInDoc(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native long GetDeviceTransform(long paramLong, int paramInt);
/*       */   
/*       */   private static native int GetPageNumberFromScreenPt(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native void SetDevicePixelDensity(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native long GetScreenRectForAnnot(long paramLong1, long paramLong2, int paramInt);
/*       */   
/*       */   private static native long GetAnnotationAt(long paramLong, int paramInt1, int paramInt2, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native long[] GetAnnotationListAt(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*       */   
/*       */   private static native long[] GetAnnotationsOnPage(long paramLong, int paramInt);
/*       */   
/*       */   private static native void UpdatePageLayout(long paramLong);
/*       */   
/*       */   private static native void Update(long paramLong, boolean paramBoolean);
/*       */   
/*       */   private static native void Update(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void Update(long paramLong1, long paramLong2, int paramInt);
/*       */   
/*       */   private static native void UpdateField(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void ExecuteAction(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void PrepareWords(long paramLong, int paramInt);
/*       */   
/*       */   private static native boolean WereWordsPrepared(long paramLong, int paramInt);
/*       */   
/*       */   private static native boolean IsThereTextInRect(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*       */   
/*       */   private static native void PrepareAnnotsForMouse(long paramLong, int paramInt, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native boolean WereAnnotsForMousePrepared(long paramLong, int paramInt);
/*       */   
/*       */   private static native int GetAnnotTypeUnder(long paramLong, double paramDouble1, double paramDouble2);
/*       */   
/*       */   private static native void SetTextSelectionMode(long paramLong, int paramInt);
/*       */   
/*       */   private static native boolean Select(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*       */   
/*       */   private static native boolean Select(long paramLong, double paramDouble1, double paramDouble2, int paramInt1, double paramDouble3, double paramDouble4, int paramInt2);
/*       */   
/*       */   private static native boolean SelectByRect(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*       */   
/*       */   private static native boolean SelectByStructWithSmartSnapping(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*       */   
/*       */   private static native boolean SelectByStructWithSnapping(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2);
/*       */   
/*       */   private static native boolean SelectByHighlights(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void SelectAll(long paramLong);
/*       */   
/*       */   private static native boolean SelectBySelection(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void FindTextAsync(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt);
/*       */   
/*       */   private static native void CancelFindText(long paramLong);
/*       */   
/*       */   private static native double GetFindTextProgress(long paramLong);
/*       */   
/*       */   private static native boolean HasSelection(long paramLong);
/*       */   
/*       */   private static native void ClearSelection(long paramLong);
/*       */   
/*       */   private static native long GetSelection(long paramLong, int paramInt);
/*       */   
/*       */   private static native int GetSelectionBeginPage(long paramLong);
/*       */   
/*       */   private static native int GetSelectionEndPage(long paramLong);
/*       */   
/*       */   private static native boolean HasSelectionOnPage(long paramLong, int paramInt);
/*       */   
/*       */   private static native int SelectionGetPageNum(long paramLong);
/*       */   
/*       */   private static native double[] SelectionGetQuads(long paramLong);
/*       */   
/*       */   private static native String SelectionGetAsUnicode(long paramLong);
/*       */   
/*       */   private static native String SelectionGetAsHtml(long paramLong);
/*       */   
/*       */   private static native boolean DownloaderUpdatePage(long paramLong1, long paramLong2, int paramInt1, int paramInt2);
/*       */   
/*       */   private static native void DownloaderUpdateOutline(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void DownloaderUpdateThumb(long paramLong1, long paramLong2, int paramInt1, int paramInt2);
/*       */   
/*       */   private static native void DownloaderFinishedDownload(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void DownloaderOpened(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native boolean DownloaderIsCorrectDoc(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void OpenURL(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2);
/*       */   
/*       */   private static native void OpenUniversalDocumentNoDoc(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void SetViewerCache(long paramLong, int paramInt, boolean paramBoolean);
/*       */   
/*       */   private static native long GetOCGContext(long paramLong);
/*       */   
/*       */   private static native void SetOCGContext(long paramLong1, long paramLong2);
/*       */   
/*       */   private static native void UpdateOCGContext(long paramLong);
/*       */   
/*       */   public static interface LockRunnable {
/*       */     void run() throws Exception;
/*       */   }
/*       */   
/*       */   public static interface ToolManager {
/*       */     void onControlReady();
/*       */     
/*       */     void onClose();
/*       */     
/*       */     void onCustomEvent(Object param1Object);
/*       */     
/*       */     boolean onKeyUp(int param1Int, KeyEvent param1KeyEvent);
/*       */     
/*       */     boolean onDoubleTap(MotionEvent param1MotionEvent);
/*       */     
/*       */     boolean onDoubleTapEvent(MotionEvent param1MotionEvent);
/*       */     
/*       */     void onDoubleTapEnd(MotionEvent param1MotionEvent);
/*       */     
/*       */     boolean onDown(MotionEvent param1MotionEvent);
/*       */     
/*       */     boolean onPointerDown(MotionEvent param1MotionEvent);
/*       */     
/*       */     void onDocumentDownloadEvent(DownloadState param1DownloadState, int param1Int1, int param1Int2, int param1Int3, String param1String);
/*       */     
/*       */     void onDraw(Canvas param1Canvas, Matrix param1Matrix);
/*       */     
/*       */     boolean onFlingStop();
/*       */     
/*       */     void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*       */     
/*       */     boolean onLongPress(MotionEvent param1MotionEvent);
/*       */     
/*       */     boolean onMove(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2);
/*       */     
/*       */     void onScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*       */     
/*       */     void onPageTurning(int param1Int1, int param1Int2);
/*       */     
/*       */     boolean onScale(float param1Float1, float param1Float2);
/*       */     
/*       */     boolean onScaleBegin(float param1Float1, float param1Float2);
/*       */     
/*       */     boolean onScaleEnd(float param1Float1, float param1Float2);
/*       */     
/*       */     void onSetDoc();
/*       */     
/*       */     boolean onShowPress(MotionEvent param1MotionEvent);
/*       */     
/*       */     boolean onSingleTapConfirmed(MotionEvent param1MotionEvent);
/*       */     
/*       */     void onPostSingleTapConfirmed();
/*       */     
/*       */     boolean onSingleTapUp(MotionEvent param1MotionEvent);
/*       */     
/*       */     boolean onUp(MotionEvent param1MotionEvent, PriorEventMode param1PriorEventMode);
/*       */     
/*       */     boolean onGenericMotionEvent(MotionEvent param1MotionEvent);
/*       */     
/*       */     void onChangePointerIcon(PointerIcon param1PointerIcon);
/*       */     
/*       */     void onConfigurationChanged(Configuration param1Configuration);
/*       */     
/*       */     boolean onDrawEdgeEffects(Canvas param1Canvas, int param1Int1, int param1Int2);
/*       */     
/*       */     void onReleaseEdgeEffects();
/*       */     
/*       */     void onPullEdgeEffects(int param1Int, float param1Float);
/*       */     
/*       */     void onDoubleTapZoomAnimationBegin();
/*       */     
/*       */     void onDoubleTapZoomAnimationEnd();
/*       */     
/*       */     void onRenderingFinished();
/*       */     
/*       */     boolean isCreatingAnnotation();
/*       */     
/*       */     void onDestroy();
/*       */     
/*       */     void onAnnotPainterUpdated(int param1Int, long param1Long, CurvePainter param1CurvePainter);
/*       */   }
/*       */   
/*       */   public static interface UniversalDocumentProgressIndicatorListener {
/*       */     void onAddProgressIndicator();
/*       */     
/*       */     void onPositionProgressIndicatorPage(Rect param1Rect);
/*       */     
/*       */     void onProgressIndicatorPageVisibilityChanged(boolean param1Boolean);
/*       */     
/*       */     void onRemoveProgressIndicator();
/*       */     
/*       */     void onShowContentPendingIndicator();
/*       */     
/*       */     void onRemoveContentPendingIndicator();
/*       */   }
/*       */   
/*       */   public static interface DocumentLoadListener {
/*       */     void onDocumentLoaded();
/*       */   }
/*       */   
/*       */   public static interface TextSearchListener {
/*       */     void onTextSearchStart();
/*       */     
/*       */     void onTextSearchProgress(int param1Int);
/*       */     
/*       */     void onTextSearchEnd(TextSearchResult param1TextSearchResult);
/*       */   }
/*       */   
/*       */   public static interface RenderingListener {
/*       */     void onRenderingStarted();
/*       */     
/*       */     void onRenderingFinished();
/*       */   }
/*       */   
/*       */   public static interface OnCanvasSizeChangeListener {
/*       */     void onCanvasSizeChanged();
/*       */   }
/*       */   
/*       */   public static interface PageChangeListener {
/*       */     void onPageChange(int param1Int1, int param1Int2, PageChangeState param1PageChangeState);
/*       */   }
/*       */   
/*       */   public static interface ActionCompletedListener {
/*       */     void onActionCompleted(Action param1Action);
/*       */   }
/*       */   
/*       */   public static interface ThumbAsyncListener {
/*       */     void onThumbReceived(int param1Int1, int[] param1ArrayOfint, int param1Int2, int param1Int3);
/*       */   }
/*       */   
/*       */   public static interface ErrorReportListener {
/*       */     void onErrorReportEvent(String param1String);
/*       */   }
/*       */   
/*       */   public static interface UniversalDocumentConversionListener {
/*       */     void onConversionEvent(ConversionState param1ConversionState, int param1Int);
/*       */   }
/*       */   
/*       */   public static interface DocumentDownloadListener {
/*       */     void onDownloadEvent(DownloadState param1DownloadState, int param1Int1, int param1Int2, int param1Int3, String param1String);
/*       */   }
/*       */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFViewCtrl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */