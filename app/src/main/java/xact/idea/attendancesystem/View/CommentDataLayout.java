package xact.idea.attendancesystem.View;



import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import java.io.File;

import xact.idea.attendancesystem.R;


public class CommentDataLayout extends RelativeLayout {
    public AppCompatImageView mImgContent = null;
    public EditText mEditContent = null;
    public RelativeLayout mRlRemove = null;
    public RelativeLayout mRlContent = null;
    private Uri mImageUri = null;
    public RelativeLayout mRoot = null;
    public File mFile = null;
    public ImageView img_close=null;

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        this.mFile = file;
    }

    public CommentDataLayout(Context context) {
        this(context, null, 0);

    }

    public CommentDataLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }
    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.item_comment_content,this);
        mRlContent = (RelativeLayout) findViewById(R.id.rl_content);
        mImgContent = (AppCompatImageView) findViewById(R.id.img_content);
        mEditContent = (EditText) findViewById(R.id.edit_content);
        mRlRemove = (RelativeLayout) findViewById(R.id.rl_btn_remove);
        mRoot = (RelativeLayout) findViewById(R.id.rlt_item_root);
        img_close=(ImageView)findViewById(R.id.image_close);
    }



    public Uri getImageUri() {
        return mImageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.mImageUri = imageUri;
    }

}
