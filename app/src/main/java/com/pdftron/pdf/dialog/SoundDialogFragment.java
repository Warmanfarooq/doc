/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.media.AudioRecord;
/*     */ import android.media.AudioTrack;
/*     */ import android.os.Bundle;
/*     */ import android.os.Handler;
/*     */ import android.os.Looper;
/*     */ import android.os.Message;
/*     */ import android.os.Process;
/*     */ import android.util.Log;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.content.res.AppCompatResources;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.core.app.ActivityCompat;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.chibde.visualizer.LineBarVisualizer;
/*     */ import com.pdftron.pdf.controls.CustomSizeDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnSoundRecordedListener;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.time.DurationFormatUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoundDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */ {
/*  50 */   public static final String TAG = SoundDialogFragment.class.getName();
/*     */   
/*     */   private static final int DIALOG_MODE_SOUND_CREATE = 0;
/*     */   
/*     */   private static final int DIALOG_MODE_SOUND_PLAY = 1;
/*     */   
/*     */   private static final String BUNDLE_DIALOG_MODE = "dialog_mode";
/*     */   
/*     */   private static final String BUNDLE_TARGET_POINT_X = "target_point_x";
/*     */   
/*     */   private static final String BUNDLE_TARGET_POINT_Y = "target_point_y";
/*     */   private static final String BUNDLE_TARGET_PAGE_NUM = "target_page_num";
/*     */   private static final String BUNDLE_AUDIO_FILE_PATH = "audio_file_path";
/*     */   private static final String BUNDLE_SAMPLE_RATE = "sample_rate";
/*     */   private static final String BUNDLE_ENCODING_BIT_RATE = "encoding_bit_rate";
/*     */   private static final String BUNDLE_NUM_CHANNEL_OUT = "num_channel_out";
/*     */   private int mSampleRate;
/*     */   private int mEncodingBitRate;
/*     */   private int mNumChannelIn;
/*     */   private int mNumChannelOut;
/*     */   private boolean permissionToRecordAccepted = true;
/*  71 */   private String[] permissions = new String[] { "android.permission.RECORD_AUDIO" };
/*     */   
/*     */   private PointF mTargetPoint;
/*  74 */   private int mPageNum = -1;
/*     */   
/*     */   private OnSoundRecordedListener mOnSoundRecordedListener;
/*     */   
/*     */   private AudioRecord mRecorder;
/*     */   
/*     */   private AudioTrack mPlayer;
/*     */   
/*     */   private int mBufferSize;
/*     */   
/*     */   private boolean mStartRecording = true;
/*     */   
/*     */   private boolean mStartPlaying = true;
/*     */   
/*     */   private boolean mCleaningRecorder;
/*     */   
/*     */   private boolean mCleaningPlayer;
/*     */   private Thread mRecordingThread;
/*     */   private Thread mPlayingThread;
/*     */   private boolean mRecorded;
/*     */   private boolean mShouldContinue;
/*     */   private boolean mRecordingDisabled;
/*     */   private boolean mPlayingDisabled;
/*  97 */   private int mDialogMode = 0;
/*     */   
/*  99 */   private Handler mPlayerHandler = new Handler(Looper.getMainLooper())
/*     */     {
/*     */       public void handleMessage(Message msg) {
/* 102 */         SoundDialogFragment.this.handlePlay();
/*     */       }
/*     */     };
/*     */   
/* 106 */   private Handler mRecordVisualizerHandler = new Handler(Looper.getMainLooper())
/*     */     {
/*     */       public void handleMessage(Message msg) {
/* 109 */         if (msg != null && msg.obj != null) {
/* 110 */           byte[] data = (byte[])msg.obj;
/* 111 */           if (SoundDialogFragment.this.mVisualizer != null) {
/* 112 */             SoundDialogFragment.this.mVisualizer.setRecorder(data);
/*     */           }
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   private long mStartTime;
/* 119 */   private Handler mHandler = new Handler();
/* 120 */   private final int SEC_UPDATE = 100;
/* 121 */   private Runnable mUpdateTimeTask = new Runnable() {
/*     */       public void run() {
/* 123 */         if (SoundDialogFragment.this.mLengthLabel == null) {
/*     */           return;
/*     */         }
/* 126 */         long start = SoundDialogFragment.this.mStartTime;
/* 127 */         long millis = System.currentTimeMillis() - start;
/* 128 */         SoundDialogFragment.this.updateLengthLabel(millis);
/*     */         
/* 130 */         SoundDialogFragment.this.mHandler.postDelayed(this, 100L);
/*     */       }
/*     */     };
/*     */   
/* 134 */   private String mFilePath = null;
/*     */   
/*     */   private TextView mLeftLabel;
/*     */   
/*     */   private ImageView mLeftIcon;
/*     */   private TextView mCenterLabel;
/*     */   private ImageView mCenterIcon;
/*     */   private TextView mRightLabel;
/*     */   private ImageView mRightIcon;
/*     */   private LineBarVisualizer mVisualizer;
/*     */   private TextView mLengthLabel;
/*     */   
/*     */   public static SoundDialogFragment newInstance(@NonNull PointF targetPoint, int pageNum) {
/* 147 */     SoundDialogFragment fragment = new SoundDialogFragment();
/* 148 */     Bundle bundle = new Bundle();
/* 149 */     bundle.putFloat("target_point_x", targetPoint.x);
/* 150 */     bundle.putFloat("target_point_y", targetPoint.y);
/* 151 */     bundle.putInt("target_page_num", pageNum);
/* 152 */     bundle.putInt("dialog_mode", 0);
/* 153 */     fragment.setArguments(bundle);
/* 154 */     return fragment;
/*     */   }
/*     */   
/*     */   public static SoundDialogFragment newInstance(String filePath, int sampleRate, int encodingBitRate, int channel) {
/* 158 */     SoundDialogFragment fragment = new SoundDialogFragment();
/* 159 */     Bundle bundle = new Bundle();
/*     */     
/* 161 */     bundle.putInt("dialog_mode", 1);
/* 162 */     bundle.putString("audio_file_path", filePath);
/* 163 */     bundle.putInt("sample_rate", sampleRate);
/* 164 */     bundle.putInt("encoding_bit_rate", encodingBitRate);
/* 165 */     bundle.putInt("num_channel_out", channel);
/* 166 */     fragment.setArguments(bundle);
/* 167 */     return fragment;
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
/*     */   public void setOnSoundRecordedListener(OnSoundRecordedListener listener) {
/* 180 */     this.mOnSoundRecordedListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/* 185 */     super.onCreate(savedInstanceState);
/*     */     
/* 187 */     this.mHeight = 350;
/*     */     
/* 189 */     Bundle args = getArguments();
/* 190 */     if (args != null) {
/* 191 */       this.mDialogMode = args.getInt("dialog_mode", 0);
/* 192 */       if (this.mDialogMode == 0) {
/* 193 */         float x = args.getFloat("target_point_x");
/* 194 */         float y = args.getFloat("target_point_y");
/* 195 */         this.mTargetPoint = new PointF(x, y);
/* 196 */         this.mPageNum = args.getInt("target_page_num", -1);
/*     */       } else {
/* 198 */         this.mFilePath = args.getString("audio_file_path", null);
/* 199 */         this.mSampleRate = args.getInt("sample_rate");
/* 200 */         this.mEncodingBitRate = args.getInt("encoding_bit_rate", 3);
/* 201 */         this.mNumChannelOut = args.getInt("num_channel_out", 4);
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     FragmentActivity fragmentActivity = getActivity();
/* 206 */     if (fragmentActivity != null) {
/* 207 */       if (this.mDialogMode == 0) {
/* 208 */         this.mFilePath = fragmentActivity.getFilesDir().getAbsolutePath();
/* 209 */         this.mFilePath += "/audiorecord.out";
/*     */       } 
/*     */       
/* 212 */       ActivityCompat.requestPermissions((Activity)fragmentActivity, this.permissions, 10015);
/*     */     } 
/*     */     
/* 215 */     if (this.mDialogMode == 0) {
/* 216 */       this.mRecordingDisabled = false;
/* 217 */       this.mPlayingDisabled = true;
/*     */       
/* 219 */       this.mSampleRate = 22050;
/* 220 */       this.mEncodingBitRate = 2;
/* 221 */       this.mNumChannelIn = 16;
/* 222 */       this.mNumChannelOut = 4;
/*     */     } else {
/* 224 */       this.mRecordingDisabled = true;
/* 225 */       this.mPlayingDisabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
/* 231 */     super.onRequestPermissionsResult(requestCode, permissions, grantResults);
/*     */     
/* 233 */     switch (requestCode) {
/*     */       case 10015:
/* 235 */         this.permissionToRecordAccepted = (grantResults[0] == 0);
/*     */         break;
/*     */     } 
/* 238 */     if (this.permissionToRecordAccepted && 
/* 239 */       this.mDialogMode == 1)
/*     */     {
/* 241 */       handlePlay();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResume() {
/* 248 */     super.onResume();
/* 249 */     if (!this.permissionToRecordAccepted) {
/* 250 */       dismiss();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/* 257 */     View view = inflater.inflate(R.layout.fragment_sound_create_dialog, container);
/*     */     
/* 259 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
/* 260 */     toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
/* 261 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 264 */             SoundDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 267 */     if (this.mDialogMode == 0) {
/* 268 */       toolbar.setTitle(R.string.tools_qm_sound);
/*     */     } else {
/* 270 */       toolbar.setTitle(R.string.tools_qm_play_sound);
/*     */     } 
/*     */     
/* 273 */     this.mLengthLabel = (TextView)view.findViewById(R.id.record_length);
/* 274 */     updateLengthLabel(0L);
/*     */     
/* 276 */     this.mVisualizer = (LineBarVisualizer)view.findViewById(R.id.visualizer);
/* 277 */     this.mVisualizer.setColor(Utils.getPrimaryColor(view.getContext()));
/* 278 */     this.mVisualizer.setDensity(90.0F);
/*     */     
/* 280 */     this.mLeftIcon = (ImageView)view.findViewById(R.id.record_preview);
/* 281 */     this.mLeftLabel = (TextView)view.findViewById(R.id.record_preview_label);
/* 282 */     this.mCenterIcon = (ImageView)view.findViewById(R.id.record_icon);
/* 283 */     this.mCenterLabel = (TextView)view.findViewById(R.id.record_icon_label);
/* 284 */     this.mRightIcon = (ImageView)view.findViewById(R.id.record_done);
/* 285 */     this.mRightLabel = (TextView)view.findViewById(R.id.record_done_label);
/*     */     
/* 287 */     if (this.mDialogMode == 1) {
/* 288 */       this.mCenterIcon.setImageDrawable(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_play_arrow_black_24dp));
/* 289 */       this.mCenterLabel.setText(R.string.sound_label_preview);
/*     */     } 
/*     */     
/* 292 */     this.mLeftIcon.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 295 */             SoundDialogFragment.this.handlePlay();
/*     */           }
/*     */         });
/*     */     
/* 299 */     this.mLeftLabel.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 302 */             SoundDialogFragment.this.handlePlay();
/*     */           }
/*     */         });
/*     */     
/* 306 */     this.mCenterIcon.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 309 */             if (SoundDialogFragment.this.mDialogMode == 0) {
/* 310 */               SoundDialogFragment.this.handleRecord();
/*     */             } else {
/* 312 */               SoundDialogFragment.this.handlePlay();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 317 */     this.mCenterLabel.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 320 */             if (SoundDialogFragment.this.mDialogMode == 0) {
/* 321 */               SoundDialogFragment.this.handleRecord();
/*     */             } else {
/* 323 */               SoundDialogFragment.this.handlePlay();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 328 */     this.mRightIcon.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 331 */             SoundDialogFragment.this.handleDone();
/*     */           }
/*     */         });
/*     */     
/* 335 */     this.mRightLabel.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 338 */             SoundDialogFragment.this.handleDone();
/*     */           }
/*     */         });
/*     */     
/* 342 */     return view;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStart() {
/* 347 */     super.onStart();
/*     */     
/* 349 */     updateLayoutVisibility();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStop() {
/* 354 */     super.onStop();
/* 355 */     this.mShouldContinue = false;
/* 356 */     if (this.mRecordingThread != null) {
/* 357 */       this.mRecordingThread.interrupt();
/* 358 */       this.mRecordingThread = null;
/*     */     } 
/* 360 */     if (this.mPlayingThread != null) {
/* 361 */       this.mPlayingThread.interrupt();
/* 362 */       this.mPlayingThread = null;
/*     */     } 
/* 364 */     if (this.mRecorder != null && !this.mCleaningRecorder) {
/* 365 */       this.mCleaningRecorder = true;
/* 366 */       this.mRecorder.release();
/* 367 */       this.mRecorder = null;
/* 368 */       this.mCleaningRecorder = false;
/*     */     } 
/* 370 */     if (this.mPlayer != null && !this.mCleaningPlayer) {
/* 371 */       this.mCleaningPlayer = true;
/* 372 */       this.mPlayer.release();
/* 373 */       this.mPlayer = null;
/* 374 */       this.mCleaningPlayer = false;
/*     */     } 
/* 376 */     this.mHandler.removeCallbacksAndMessages(null);
/* 377 */     this.mPlayerHandler.removeCallbacksAndMessages(null);
/*     */   }
/*     */   
/*     */   private void updateLengthLabel(long millis) {
/* 381 */     String text = DurationFormatUtils.formatDuration(millis, "mm:ss.SSS");
/* 382 */     this.mLengthLabel.setText(text);
/*     */   }
/*     */   
/*     */   private void updateLayoutVisibility() {
/* 386 */     Context context = getContext();
/* 387 */     if (context == null) {
/*     */       return;
/*     */     }
/* 390 */     if (this.mLeftIcon == null || this.mLeftLabel == null || this.mCenterIcon == null || this.mCenterLabel == null || this.mRightIcon == null || this.mRightLabel == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 395 */     if (this.mDialogMode == 0) {
/* 396 */       if (!this.mRecorded) {
/*     */         
/* 398 */         this.mLeftIcon.setVisibility(4);
/* 399 */         this.mLeftLabel.setVisibility(4);
/* 400 */         this.mRightIcon.setVisibility(4);
/* 401 */         this.mRightLabel.setVisibility(4);
/*     */       } else {
/* 403 */         this.mLeftIcon.setVisibility(0);
/* 404 */         this.mLeftLabel.setVisibility(0);
/* 405 */         this.mRightIcon.setVisibility(0);
/* 406 */         this.mRightLabel.setVisibility(0);
/*     */         
/* 408 */         setViewEnabled(context, this.mLeftIcon, this.mLeftLabel, true);
/* 409 */         setViewEnabled(context, this.mRightIcon, this.mRightLabel, true);
/* 410 */         setViewEnabled(context, this.mCenterIcon, this.mCenterLabel, true);
/*     */         
/* 412 */         if (this.mPlayingDisabled) {
/* 413 */           setViewEnabled(context, this.mLeftIcon, this.mLeftLabel, false);
/* 414 */           setViewEnabled(context, this.mRightIcon, this.mRightLabel, false);
/*     */         } 
/* 416 */         if (this.mRecordingDisabled) {
/* 417 */           setViewEnabled(context, this.mCenterIcon, this.mCenterLabel, false);
/*     */         }
/*     */       } 
/*     */     } else {
/* 421 */       this.mLeftIcon.setVisibility(4);
/* 422 */       this.mLeftLabel.setVisibility(4);
/* 423 */       this.mRightIcon.setVisibility(4);
/* 424 */       this.mRightLabel.setVisibility(4);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setViewEnabled(@NonNull Context context, @NonNull ImageView view, @NonNull TextView label, boolean enabled) {
/* 429 */     int color = Utils.getPrimaryColor(context);
/* 430 */     if (!enabled) {
/* 431 */       color = context.getResources().getColor(R.color.gray400);
/*     */     }
/* 433 */     view.getDrawable().mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 434 */     label.setTextColor(color);
/*     */   }
/*     */   
/*     */   private void handleDone() {
/* 438 */     if (this.mFilePath != null && this.mOnSoundRecordedListener != null) {
/* 439 */       this.mOnSoundRecordedListener.onSoundRecorded(this.mTargetPoint, this.mPageNum, this.mFilePath);
/*     */     }
/* 441 */     dismiss();
/*     */   }
/*     */   
/*     */   private void handlePlay() {
/* 445 */     Context context = getContext();
/* 446 */     if (context == null) {
/*     */       return;
/*     */     }
/* 449 */     if (this.mLeftIcon == null || this.mLeftLabel == null) {
/*     */       return;
/*     */     }
/* 452 */     if (this.mPlayingDisabled) {
/*     */       return;
/*     */     }
/*     */     
/* 456 */     onPlay(this.mStartPlaying);
/* 457 */     if (this.mStartPlaying) {
/* 458 */       if (this.mDialogMode == 0) {
/* 459 */         this.mLeftIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_stop_black_24dp));
/* 460 */         this.mLeftLabel.setText(R.string.sound_label_stop);
/*     */       } else {
/* 462 */         this.mCenterIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_stop_black_24dp));
/* 463 */         this.mCenterLabel.setText(R.string.sound_label_stop);
/*     */       }
/*     */     
/* 466 */     } else if (this.mDialogMode == 0) {
/* 467 */       this.mLeftIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_play_arrow_black_24dp));
/* 468 */       this.mLeftLabel.setText(R.string.sound_label_preview);
/*     */     } else {
/* 470 */       this.mCenterIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_play_arrow_black_24dp));
/* 471 */       this.mCenterLabel.setText(R.string.sound_label_play);
/*     */     } 
/*     */     
/* 474 */     updateLayoutVisibility();
/* 475 */     this.mStartPlaying = !this.mStartPlaying;
/*     */   }
/*     */   
/*     */   private void handleRecord() {
/* 479 */     Context context = getContext();
/* 480 */     if (context == null) {
/*     */       return;
/*     */     }
/* 483 */     if (this.mCenterIcon == null || this.mCenterLabel == null) {
/*     */       return;
/*     */     }
/* 486 */     if (this.mRecordingDisabled) {
/*     */       return;
/*     */     }
/*     */     
/* 490 */     onRecord(this.mStartRecording);
/* 491 */     if (this.mStartRecording) {
/* 492 */       this.mCenterIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_mic_off_black_24dp));
/* 493 */       this.mCenterLabel.setText(R.string.sound_label_stop);
/*     */     } else {
/* 495 */       this.mCenterIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_mic_black_24dp));
/* 496 */       this.mCenterLabel.setText(R.string.sound_label_record);
/*     */     } 
/* 498 */     updateLayoutVisibility();
/* 499 */     this.mStartRecording = !this.mStartRecording;
/*     */   }
/*     */   
/*     */   private void startRecording() {
/* 503 */     this.mPlayingDisabled = true;
/* 504 */     this.mStartTime = System.currentTimeMillis();
/*     */     
/* 506 */     this.mShouldContinue = true;
/*     */ 
/*     */     
/* 509 */     this.mBufferSize = AudioRecord.getMinBufferSize(this.mSampleRate, this.mNumChannelIn, this.mEncodingBitRate);
/*     */ 
/*     */ 
/*     */     
/* 513 */     this.mRecorder = new AudioRecord(0, this.mSampleRate, this.mNumChannelIn, this.mEncodingBitRate, this.mBufferSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 519 */     recordAudio();
/*     */   }
/*     */   
/*     */   private void stopRecording() {
/* 523 */     this.mShouldContinue = false;
/*     */     
/* 525 */     this.mPlayingDisabled = false;
/* 526 */     this.mRecorded = true;
/*     */     
/* 528 */     this.mHandler.removeCallbacks(this.mUpdateTimeTask);
/*     */   }
/*     */   
/*     */   private void onRecord(boolean start) {
/* 532 */     if (start) {
/* 533 */       startRecording();
/*     */     } else {
/* 535 */       stopRecording();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startPlaying() {
/* 540 */     this.mRecordingDisabled = true;
/* 541 */     this.mStartTime = System.currentTimeMillis();
/*     */     
/* 543 */     this.mShouldContinue = true;
/*     */     
/* 545 */     this.mBufferSize = AudioTrack.getMinBufferSize(this.mSampleRate, this.mNumChannelOut, this.mEncodingBitRate);
/* 546 */     if (this.mBufferSize == -2 || this.mBufferSize == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 551 */     this.mPlayer = new AudioTrack(3, this.mSampleRate, this.mNumChannelOut, this.mEncodingBitRate, this.mBufferSize, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 559 */     if (this.mVisualizer != null) {
/* 560 */       this.mVisualizer.setPlayer(this.mPlayer.getAudioSessionId());
/*     */     }
/*     */     
/* 563 */     playAudio();
/*     */   }
/*     */   
/*     */   private void stopPlaying() {
/* 567 */     this.mShouldContinue = false;
/*     */     
/* 569 */     this.mRecordingDisabled = false;
/*     */     
/* 571 */     this.mHandler.removeCallbacks(this.mUpdateTimeTask);
/*     */   }
/*     */   
/*     */   private void onPlay(boolean start) {
/* 575 */     if (start) {
/* 576 */       startPlaying();
/*     */     } else {
/* 578 */       stopPlaying();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recordAudio() {
/* 583 */     this.mRecordingThread = new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 586 */             Process.setThreadPriority(-16);
/*     */             
/* 588 */             FileOutputStream fos = null;
/*     */             try {
/* 590 */               fos = new FileOutputStream(SoundDialogFragment.this.mFilePath);
/* 591 */             } catch (FileNotFoundException ex) {
/* 592 */               ex.printStackTrace();
/*     */             } 
/*     */             
/* 595 */             byte[] audioBuffer = new byte[SoundDialogFragment.this.mBufferSize];
/*     */             
/* 597 */             if (SoundDialogFragment.this.mRecorder == null || SoundDialogFragment.this.mRecorder.getState() != 1) {
/* 598 */               Log.e(SoundDialogFragment.TAG, "Audio Record can't initialize!");
/*     */               return;
/*     */             } 
/* 601 */             SoundDialogFragment.this.mRecorder.startRecording();
/*     */             
/* 603 */             SoundDialogFragment.this.mHandler.removeCallbacks(SoundDialogFragment.this.mUpdateTimeTask);
/* 604 */             SoundDialogFragment.this.mHandler.postDelayed(SoundDialogFragment.this.mUpdateTimeTask, 100L);
/*     */             
/* 606 */             Log.v(SoundDialogFragment.TAG, "Start recording");
/*     */             
/* 608 */             long bytesRead = 0L;
/* 609 */             if (fos != null) {
/* 610 */               while (SoundDialogFragment.this.mShouldContinue && !Thread.interrupted()) {
/* 611 */                 int numberOfByte = SoundDialogFragment.this.mRecorder.read(audioBuffer, 0, audioBuffer.length);
/* 612 */                 bytesRead += numberOfByte;
/*     */ 
/*     */                 
/* 615 */                 if (-3 != numberOfByte) {
/*     */                   try {
/* 617 */                     byte[] audioBufferReversed = SoundDialogFragment.swapByteArray(audioBuffer);
/*     */                     
/* 619 */                     byte[] eightBit = SoundDialogFragment.toEightBitArray(audioBufferReversed);
/* 620 */                     if (eightBit != null) {
/* 621 */                       Message m = new Message();
/* 622 */                       m.obj = Arrays.copyOf(eightBit, eightBit.length);
/* 623 */                       SoundDialogFragment.this.mRecordVisualizerHandler.sendMessage(m);
/*     */                     } 
/*     */                     
/* 626 */                     if (audioBufferReversed != null) {
/* 627 */                       fos.write(audioBufferReversed);
/*     */                     }
/* 629 */                   } catch (IOException e) {
/* 630 */                     e.printStackTrace();
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */               try {
/* 635 */                 fos.close();
/* 636 */               } catch (IOException e) {
/* 637 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */             
/* 641 */             if (SoundDialogFragment.this.mRecorder != null && !SoundDialogFragment.this.mCleaningRecorder) {
/* 642 */               SoundDialogFragment.this.mCleaningRecorder = true;
/* 643 */               SoundDialogFragment.this.mRecorder.stop();
/* 644 */               SoundDialogFragment.this.mRecorder.release();
/* 645 */               SoundDialogFragment.this.mRecorder = null;
/* 646 */               SoundDialogFragment.this.mCleaningRecorder = false;
/*     */             } 
/*     */             
/* 649 */             Log.v(SoundDialogFragment.TAG, String.format("Recording stopped. Samples read: %d", new Object[] { Long.valueOf(bytesRead) }));
/*     */           }
/*     */         });
/* 652 */     this.mRecordingThread.start();
/*     */   }
/*     */   
/*     */   private void playAudio() {
/* 656 */     this.mPlayingThread = new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 659 */             FileInputStream fis = null;
/* 660 */             byte[] byteData = null;
/*     */             try {
/* 662 */               fis = new FileInputStream(SoundDialogFragment.this.mFilePath);
/* 663 */               byteData = IOUtils.toByteArray(fis);
/* 664 */               byteData = SoundDialogFragment.swapByteArray(byteData);
/* 665 */             } catch (IOException e) {
/* 666 */               e.printStackTrace();
/*     */             } finally {
/* 668 */               Utils.closeQuietly(fis);
/*     */             } 
/* 670 */             if (byteData == null) {
/*     */               return;
/*     */             }
/* 673 */             if (SoundDialogFragment.this.mPlayer == null || SoundDialogFragment.this.mPlayer.getState() != 1) {
/* 674 */               Log.e(SoundDialogFragment.TAG, "Audio Track can't initialize!");
/*     */               
/*     */               return;
/*     */             } 
/* 678 */             SoundDialogFragment.this.mPlayer.play();
/*     */             
/* 680 */             SoundDialogFragment.this.mHandler.removeCallbacks(SoundDialogFragment.this.mUpdateTimeTask);
/* 681 */             SoundDialogFragment.this.mHandler.postDelayed(SoundDialogFragment.this.mUpdateTimeTask, 100L);
/*     */             
/* 683 */             Log.v(SoundDialogFragment.TAG, "Audio streaming started");
/*     */             
/* 685 */             int bytesRead = 0;
/* 686 */             int size = byteData.length;
/*     */ 
/*     */             
/* 689 */             while (bytesRead < size && SoundDialogFragment.this.mShouldContinue && !Thread.interrupted()) {
/* 690 */               int bytesPerRead = Math.min(SoundDialogFragment.this.mBufferSize, size - bytesRead);
/* 691 */               bytesRead += SoundDialogFragment.this.mPlayer.write(byteData, bytesRead, bytesPerRead);
/*     */             } 
/*     */             
/* 694 */             if (SoundDialogFragment.this.mPlayer != null && !SoundDialogFragment.this.mCleaningPlayer) {
/* 695 */               SoundDialogFragment.this.mCleaningPlayer = true;
/* 696 */               SoundDialogFragment.this.mPlayer.stop();
/* 697 */               SoundDialogFragment.this.mPlayer.release();
/* 698 */               SoundDialogFragment.this.mPlayer = null;
/* 699 */               SoundDialogFragment.this.mCleaningPlayer = false;
/*     */             } 
/*     */             
/* 702 */             if (SoundDialogFragment.this.mShouldContinue)
/*     */             {
/*     */               
/* 705 */               SoundDialogFragment.this.mPlayerHandler.sendEmptyMessage(0);
/*     */             }
/*     */             
/* 708 */             Log.v(SoundDialogFragment.TAG, "Audio streaming finished. Samples written: " + byteData.length);
/*     */           }
/*     */         });
/* 711 */     this.mPlayingThread.start();
/*     */   }
/*     */   
/*     */   private static byte[] swapByteArray(byte[] a) {
/* 715 */     if (a == null) {
/* 716 */       return null;
/*     */     }
/* 718 */     byte[] ret = new byte[a.length];
/*     */     
/* 720 */     int limit = a.length - a.length % 2;
/* 721 */     if (limit < 1) return null; 
/* 722 */     for (int i = 0; i < limit - 1; i += 2) {
/* 723 */       ret[i] = a[i + 1];
/* 724 */       ret[i + 1] = a[i];
/*     */     } 
/* 726 */     return ret;
/*     */   }
/*     */   
/*     */   private static byte[] toEightBitArray(byte[] a) {
/* 730 */     if (a == null) {
/* 731 */       return null;
/*     */     }
/* 733 */     byte[] ret = new byte[a.length / 2];
/* 734 */     int j = 0;
/* 735 */     for (int i = 0; i < a.length; i++) {
/* 736 */       if ((i & 0x1) == 0)
/*     */       {
/* 738 */         ret[j++] = a[i];
/*     */       }
/*     */     } 
/* 741 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\SoundDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */