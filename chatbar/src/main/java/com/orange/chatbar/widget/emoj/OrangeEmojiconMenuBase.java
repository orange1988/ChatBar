package com.orange.chatbar.widget.emoj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.orange.chatbar.entity.OrangeEmoj;


public class OrangeEmojiconMenuBase extends LinearLayout{
    protected EaseEmojiconMenuListener listener;
    
    public OrangeEmojiconMenuBase(Context context) {
        super(context);
    }
    
    @SuppressLint("NewApi")
    public OrangeEmojiconMenuBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public OrangeEmojiconMenuBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    
    /**
     * 设置回调监听
     * @param listener
     */
    public void setEmojiconMenuListener(EaseEmojiconMenuListener listener){
        this.listener = listener;
    }
    
    public interface EaseEmojiconMenuListener{
        /**
         * 表情被点击
         * @param emojicon
         */
        void onExpressionClicked(OrangeEmoj emojicon);
        /**
         * 删除按钮被点击
         */
        void onDeleteImageClicked();
    }
}
