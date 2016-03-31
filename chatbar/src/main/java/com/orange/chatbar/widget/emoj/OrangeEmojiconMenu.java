package com.orange.chatbar.widget.emoj;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.orange.chatbar.R;
import com.orange.chatbar.entity.OrangeEmoj;
import com.orange.chatbar.entity.OrangeEmojGroupEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 表情图片控件
 */
public class OrangeEmojiconMenu extends OrangeEmojiconMenuBase {
	
	private int emojiconColumns;
	private int bigEmojiconColumns;
	private final int defaultBigColumns = 4;
	private final int defaultColumns = 7;
    private OrangeEmojiconScrollTabBar tabBar;
    private OrangeEmojiconIndicatorView indicatorView;
    private OrangeEmojiconPagerView pagerView;
    
    private List<OrangeEmojGroupEntity> emojiconGroupList = new ArrayList<OrangeEmojGroupEntity>();
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public OrangeEmojiconMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public OrangeEmojiconMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public OrangeEmojiconMenu(Context context) {
		super(context);
		init(context, null);
	}
	
	private void init(Context context, AttributeSet attrs){
		LayoutInflater.from(context).inflate(R.layout.ease_widget_emojicon, this);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OrangeEmojiconMenu);
		emojiconColumns = ta.getInt(R.styleable.OrangeEmojiconMenu_emojiconColumns, defaultColumns);
		bigEmojiconColumns = ta.getInt(R.styleable.OrangeEmojiconMenu_bigEmojiconRows, defaultBigColumns);
		ta.recycle();
		
		pagerView = (OrangeEmojiconPagerView) findViewById(R.id.pager_view);
		indicatorView = (OrangeEmojiconIndicatorView) findViewById(R.id.indicator_view);
		tabBar = (OrangeEmojiconScrollTabBar) findViewById(R.id.tab_bar);
		
	}
	
	public void init(List<OrangeEmojGroupEntity> groupEntities){
	    if(groupEntities == null || groupEntities.size() == 0){
	        return;
	    }
	    for(OrangeEmojGroupEntity groupEntity : groupEntities){
	        emojiconGroupList.add(groupEntity);
	        tabBar.addTab(groupEntity.getIcon());
	    }
	    
	    pagerView.setPagerViewListener(new EmojiconPagerViewListener());
        pagerView.init(emojiconGroupList, emojiconColumns,bigEmojiconColumns);
        
        tabBar.setTabBarItemClickListener(new OrangeEmojiconScrollTabBar.EaseScrollTabBarItemClickListener() {
            
            @Override
            public void onItemClick(int position) {
                pagerView.setGroupPostion(position);
            }
        });
	    
	}
	
	
	/**
     * 添加表情组
     * @param groupEntity
     */
    public void addEmojiconGroup(OrangeEmojGroupEntity groupEntity){
        emojiconGroupList.add(groupEntity);
        pagerView.addEmojiconGroup(groupEntity, true);
        tabBar.addTab(groupEntity.getIcon());
    }
    
    /**
     * 添加一系列表情组
     * @param groupEntitieList
     */
    public void addEmojiconGroup(List<OrangeEmojGroupEntity> groupEntitieList){
        for(int i= 0; i < groupEntitieList.size(); i++){
            OrangeEmojGroupEntity groupEntity = groupEntitieList.get(i);
            emojiconGroupList.add(groupEntity);
            pagerView.addEmojiconGroup(groupEntity, i == groupEntitieList.size()-1 ? true : false);
            tabBar.addTab(groupEntity.getIcon());
        }
        
    }
    
    /**
     * 移除表情组
     * @param position
     */
    public void removeEmojiconGroup(int position){
        emojiconGroupList.remove(position);
        pagerView.removeEmojiconGroup(position);
        tabBar.removeTab(position);
    }
    
    public void setTabBarVisibility(boolean isVisible){
        if(!isVisible){
            tabBar.setVisibility(View.GONE);
        }else{
            tabBar.setVisibility(View.VISIBLE);
        }
    }
	
	
	private class EmojiconPagerViewListener implements OrangeEmojiconPagerView.EaseEmojiconPagerViewListener {

        @Override
        public void onPagerViewInited(int groupMaxPageSize, int firstGroupPageSize) {
            indicatorView.init(groupMaxPageSize);
            indicatorView.updateIndicator(firstGroupPageSize);
            tabBar.selectedTo(0);
        }

        @Override
        public void onGroupPositionChanged(int groupPosition, int pagerSizeOfGroup) {
            indicatorView.updateIndicator(pagerSizeOfGroup);
            tabBar.selectedTo(groupPosition);
        }

        @Override
        public void onGroupInnerPagePostionChanged(int oldPosition, int newPosition) {
            indicatorView.selectTo(oldPosition, newPosition);
        }

        @Override
        public void onGroupPagePostionChangedTo(int position) {
            indicatorView.selectTo(position);
        }

        @Override
        public void onGroupMaxPageSizeChanged(int maxCount) {
            indicatorView.updateIndicator(maxCount);
        }

        @Override
        public void onDeleteImageClicked() {
            if(listener != null){
                listener.onDeleteImageClicked();
            }
        }

        @Override
        public void onExpressionClicked(OrangeEmoj emojicon) {
            if(listener != null){
                listener.onExpressionClicked(emojicon);
            }
        }
	    
	}
	
}
