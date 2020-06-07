/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.net.Uri;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.ListAdapter;
/*     */ import android.widget.ListView;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
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
/*     */ public class PortfolioDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*     */   private ArrayList<String> mEmbeddedFiles;
/*     */   private int mFileType;
/*     */   private File mFile;
/*     */   private Uri mFileUri;
/*     */   private PDFDoc mPdfDoc;
/*     */   PortfolioDialogFragmentListener mListener;
/*     */   public static final int FILE_TYPE_FILE = 0;
/*     */   public static final int FILE_TYPE_FILE_URI = 1;
/*     */   public static final int FILE_TYPE_PDFDOC = 2;
/*     */   private static final String KEY_FILE = "key_file";
/*     */   private static final String KEY_FILE_URI = "key_file_uri";
/*     */   private static final String KEY_PASS = "key_pass";
/*     */   private static final String KEY_FILE_TYPE = "key_file_type";
/*     */   private static final String KEY_DIALOG_TITLE = "key_dialog_title";
/*     */   
/*     */   public static PortfolioDialogFragment newInstance(File file, String password, int dialogTitleRes) {
/*  64 */     PortfolioDialogFragment fragment = new PortfolioDialogFragment();
/*  65 */     Bundle bundle = new Bundle();
/*  66 */     bundle.putSerializable("key_file", file);
/*  67 */     bundle.putString("key_pass", password);
/*  68 */     bundle.putInt("key_file_type", 0);
/*  69 */     if (dialogTitleRes != 0) {
/*  70 */       bundle.putInt("key_dialog_title", dialogTitleRes);
/*     */     }
/*  72 */     fragment.setArguments(bundle);
/*     */     
/*  74 */     return fragment;
/*     */   }
/*     */   
/*     */   public static PortfolioDialogFragment newInstance(File file, String password) {
/*  78 */     return newInstance(file, password, 0);
/*     */   }
/*     */   
/*     */   public static PortfolioDialogFragment newInstance(Uri fileUri, String password, int dialogTitleRes) {
/*  82 */     PortfolioDialogFragment fragment = new PortfolioDialogFragment();
/*  83 */     Bundle bundle = new Bundle();
/*  84 */     bundle.putString("key_file_uri", fileUri.toString());
/*  85 */     bundle.putString("key_pass", password);
/*  86 */     bundle.putInt("key_file_type", 1);
/*  87 */     if (dialogTitleRes != 0) {
/*  88 */       bundle.putInt("key_dialog_title", dialogTitleRes);
/*     */     }
/*  90 */     fragment.setArguments(bundle);
/*     */     
/*  92 */     return fragment;
/*     */   }
/*     */   
/*     */   public static PortfolioDialogFragment newInstance(Uri fileUri, String password) {
/*  96 */     return newInstance(fileUri, password, 0);
/*     */   }
/*     */   
/*     */   public static PortfolioDialogFragment newInstance(int fileType, int dialogTitleRes) {
/* 100 */     PortfolioDialogFragment fragment = new PortfolioDialogFragment();
/* 101 */     Bundle bundle = new Bundle();
/* 102 */     bundle.putInt("key_file_type", fileType);
/* 103 */     if (dialogTitleRes != 0) {
/* 104 */       bundle.putInt("key_dialog_title", dialogTitleRes);
/*     */     }
/* 106 */     fragment.setArguments(bundle);
/*     */     
/* 108 */     return fragment;
/*     */   }
/*     */   
/*     */   public static PortfolioDialogFragment newInstance(int fileType) {
/* 112 */     return newInstance(fileType, 0);
/*     */   }
/*     */   
/*     */   public File getPortfolioFile() {
/* 116 */     return this.mFile;
/*     */   }
/*     */   
/*     */   public Uri getPortfolioFileUri() {
/* 120 */     return this.mFileUri;
/*     */   }
/*     */   
/*     */   public void setListener(PortfolioDialogFragmentListener listener) {
/* 124 */     this.mListener = listener;
/*     */   }
/*     */   
/*     */   public void initParams(PDFDoc pdfDoc) {
/* 128 */     this.mPdfDoc = pdfDoc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 135 */     Bundle bundle = getArguments();
/* 136 */     int dialogTitle = R.string.pdf_portfolio;
/* 137 */     if (bundle != null) {
/* 138 */       this.mFileType = bundle.getInt("key_file_type");
/* 139 */       boolean success = true;
/* 140 */       if (this.mFileType == 0) {
/* 141 */         this.mFile = (File)getArguments().getSerializable("key_file");
/* 142 */       } else if (this.mFileType == 1) {
/* 143 */         String fileUriStr = getArguments().getString("key_file_uri");
/* 144 */         if (!Utils.isNullOrEmpty(fileUriStr)) {
/*     */           try {
/* 146 */             this.mFileUri = Uri.parse(fileUriStr);
/* 147 */           } catch (Exception e) {
/* 148 */             success = false;
/*     */           } 
/*     */         } else {
/* 151 */           success = false;
/*     */         } 
/* 153 */       } else if (this.mFileType == 2 && 
/* 154 */         this.mPdfDoc == null) {
/* 155 */         success = false;
/*     */       } 
/*     */       
/* 158 */       if (!success) {
/* 159 */         dismiss();
/*     */       }
/*     */       
/* 162 */       String password = getArguments().getString("key_pass");
/* 163 */       if (this.mFileType == 0) {
/* 164 */         this.mEmbeddedFiles = Utils.getFileNamesFromPortfolio(this.mFile, password);
/* 165 */       } else if (this.mFileType == 1) {
/* 166 */         this.mEmbeddedFiles = Utils.getFileNamesFromPortfolio(getContext(), this.mFileUri, password);
/* 167 */       } else if (this.mFileType == 2) {
/* 168 */         this.mEmbeddedFiles = Utils.getFileNamesFromPortfolio(this.mPdfDoc);
/*     */       } 
/*     */       
/* 171 */       dialogTitle = getArguments().getInt("key_dialog_title", R.string.pdf_portfolio);
/*     */     } 
/*     */ 
/*     */     
/* 175 */     View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_portfolio_dialog, null);
/* 176 */     ListView fileList = (ListView)dialogView.findViewById(R.id.list);
/*     */     
/* 178 */     PortfolioEntryAdapter fileListAdapter = new PortfolioEntryAdapter((Context)getActivity(), this.mEmbeddedFiles);
/* 179 */     fileList.setAdapter((ListAdapter)fileListAdapter);
/* 180 */     fileList.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 183 */             PortfolioDialogFragment.this.mListener.onPortfolioDialogFragmentFileClicked(PortfolioDialogFragment.this.mFileType, PortfolioDialogFragment.this, PortfolioDialogFragment.this.mEmbeddedFiles.get(position));
/* 184 */             PortfolioDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 188 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 189 */     builder.setView(dialogView)
/* 190 */       .setTitle(dialogTitle)
/* 191 */       .setCancelable(true)
/* 192 */       .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
/*     */           public void onClick(DialogInterface dialog, int id) {
/* 194 */             dialog.cancel();
/*     */           }
/*     */         });
/* 197 */     return (Dialog)builder.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAttach(Context context) {
/* 202 */     super.onAttach(context);
/*     */     try {
/* 204 */       if (this.mListener == null) {
/* 205 */         this.mListener = (PortfolioDialogFragmentListener)context;
/*     */       }
/* 207 */     } catch (ClassCastException e) {
/* 208 */       throw new ClassCastException(context.toString() + " must implement " + e.getClass().toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface PortfolioDialogFragmentListener {
/*     */     void onPortfolioDialogFragmentFileClicked(int param1Int, PortfolioDialogFragment param1PortfolioDialogFragment, String param1String); }
/*     */   
/*     */   private class PortfolioEntryAdapter extends ArrayAdapter<String> {
/*     */     PortfolioEntryAdapter(Context context, List<String> entries) {
/* 217 */       super(context, 0, entries);
/* 218 */       this.mEntries = entries;
/*     */     }
/*     */     
/*     */     private List<String> mEntries;
/*     */     
/*     */     @NonNull
/*     */     public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*     */       ViewHolder holder;
/* 226 */       if (convertView == null) {
/* 227 */         convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_portfolio_list, parent, false);
/*     */         
/* 229 */         holder = new ViewHolder();
/* 230 */         holder.fileName = (TextView)convertView.findViewById(R.id.file_name);
/* 231 */         convertView.setTag(holder);
/*     */       } else {
/* 233 */         holder = (ViewHolder)convertView.getTag();
/*     */       } 
/*     */       
/* 236 */       holder.fileName.setText(this.mEntries.get(position));
/*     */       
/* 238 */       return convertView;
/*     */     }
/*     */     
/*     */     private class ViewHolder {
/*     */       protected TextView fileName;
/*     */       
/*     */       private ViewHolder() {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\PortfolioDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */