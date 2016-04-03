package com.orange.chatbar.widget.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.orange.chatbar.entity.OrangeEmoji;


public class OrangeEmojiMenuBase extends LinearLayout{
    protected EaseEmojiconMenuListener listener;
    
    public OrangeEmojiMenuBase(Context context) {
        super(context);
    }
    
    @SuppressLint("NewApi")
    public OrangeEmojiMenuBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public OrangeEmojiMenuBase(Context context, AttributeSet attrs) {
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
        void onExpressionClicked(OrangeEmoji emojicon);
        /**
         * 删除按钮被点击
         */
        void onDeleteImageClicked();
    }
}
