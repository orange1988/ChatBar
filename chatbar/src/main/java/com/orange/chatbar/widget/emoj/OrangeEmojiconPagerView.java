package com.orange.chatbar.widget.emoj;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.orange.chatbar.R;
import com.orange.chatbar.adapter.OrangeEmojGridAdapter;
import com.orange.chatbar.adapter.OrangeiconPagerAdapter;
import com.orange.chatbar.entity.OrangeEmoj;
import com.orange.chatbar.entity.OrangeEmojGroupEntity;
import com.orange.chatbar.utils.SmileUtils;

import java.util.ArrayList;
import java.util.List;

public class OrangeEmojiconPagerView extends ViewPager{

    private Context context;
    private List<OrangeEmojGroupEntity> groupEntities;
    private List<OrangeEmoj> totalEmojiconList = new ArrayList<OrangeEmoj>();
    
    private PagerAdapter pagerAdapter;
    
    private int emojiconRows = 3;
    private int emojiconColumns = 7;
    
    private int bigEmojiconRows = 2;
    private int bigEmojiconColumns = 4;
    
    private int firstGroupPageSize;
    
    private int maxPageCount;
    private int previousPagerPosition;
	private EaseEmojiconPagerViewListener pagerViewListener;
    private List<View> viewpages; 

    public OrangeEmojiconPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public OrangeEmojiconPagerView(Context context) {
        this(context, null);
    }
    
    
    public void init(List<OrangeEmojGroupEntity> emojiconGroupList, int emijiconColumns, int bigEmojiconColumns){
        if(emojiconGroupList == null){
            throw new RuntimeException("emojiconGroupList is null");
        }
        
        this.groupEntities = emojiconGroupList;
        this.emojiconColumns = emijiconColumns;
        this.bigEmojiconColumns = bigEmojiconColumns;
        
        viewpages = new ArrayList<View>();
        for(int i = 0; i < groupEntities.size(); i++){
            OrangeEmojGroupEntity group = groupEntities.get(i);
            List<OrangeEmoj> groupEmojicons = group.getEmojiconList();
            totalEmojiconList.addAll(groupEmojicons);
            List<View> gridViews = getGroupGridViews(group);
            if(i == 0){
                firstGroupPageSize = gridViews.size();
            }
            maxPageCount = Math.max(gridViews.size(), maxPageCount);
            viewpages.addAll(gridViews);
        }
        
        pagerAdapter = new OrangeiconPagerAdapter(viewpages);
        setAdapter(pagerAdapter);
        setOnPageChangeListener(new EmojiPagerChangeListener());
        
        if(pagerViewListener != null){
            pagerViewListener.onPagerViewInited(maxPageCount, firstGroupPageSize);
        }
    }
    
    public void setPagerViewListener(EaseEmojiconPagerViewListener pagerViewListener){
    	this.pagerViewListener = pagerViewListener;
    }
    
    
    /**
     * 设置当前表情组位置
     * @param position
     */
    public void setGroupPostion(int position){
    	if (getAdapter() != null && position >= 0 && position < groupEntities.size()) {
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += getPageSize(groupEntities.get(i));
            }
            setCurrentItem(count);
        }
    }
    
    /**
     * 获取表情组的gridview list
     * @param groupEntity
     * @return
     */
    public List<View> getGroupGridViews(OrangeEmojGroupEntity groupEntity){
        List<OrangeEmoj> emojiconList = groupEntity.getEmojiconList();
        int itemSize = emojiconColumns * emojiconRows -1;
        int totalSize = emojiconList.size();
        OrangeEmoj.Type emojiType = groupEntity.getType();
        if(emojiType == OrangeEmoj.Type.BIG_EXPRESSION){
            itemSize = bigEmojiconColumns * bigEmojiconRows;
        }
        int pageSize = totalSize % itemSize == 0 ? totalSize/itemSize : totalSize/itemSize + 1;   
        List<View> views = new ArrayList<View>();
        for(int i = 0; i < pageSize; i++){
            View view = View.inflate(context, R.layout.ease_expression_gridview, null);
            GridView gv = (GridView) view.findViewById(R.id.gridview);
            if(emojiType == OrangeEmoj.Type.BIG_EXPRESSION){
                gv.setNumColumns(bigEmojiconColumns);
            }else{
                gv.setNumColumns(emojiconColumns);
            }
            List<OrangeEmoj> list = new ArrayList<OrangeEmoj>();
            if(i != pageSize -1){
                list.addAll(emojiconList.subList(i * itemSize, (i+1) * itemSize));
            }else{
                list.addAll(emojiconList.subList(i * itemSize, totalSize));
            }
            if(emojiType != OrangeEmoj.Type.BIG_EXPRESSION){
                OrangeEmoj deleteIcon = new OrangeEmoj();
                deleteIcon.setEmojiText(SmileUtils.DELETE_KEY);
                list.add(deleteIcon);
            }
            final OrangeEmojGridAdapter gridAdapter = new OrangeEmojGridAdapter(context, 1, list, emojiType);
            gv.setAdapter(gridAdapter);
            gv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OrangeEmoj emojicon = gridAdapter.getItem(position);
                    if(pagerViewListener != null){
                        String emojiText = emojicon.getEmojiText();
                        if(emojiText != null && emojiText.equals(SmileUtils.DELETE_KEY)){
                            pagerViewListener.onDeleteImageClicked();
                        }else{
                            pagerViewListener.onExpressionClicked(emojicon);
                        }
                        
                    }
                    
                }
            });
            
            views.add(view);
        }
        return views;
    }
    

    /**
     * 添加表情组
     * @param groupEntity
     */
    public void addEmojiconGroup(OrangeEmojGroupEntity groupEntity, boolean notifyDataChange) {
        int pageSize = getPageSize(groupEntity);
        if(pageSize > maxPageCount){
            maxPageCount = pageSize;
            if(pagerViewListener != null && pagerAdapter != null){
                pagerViewListener.onGroupMaxPageSizeChanged(maxPageCount);
            }
        }
        viewpages.addAll(getGroupGridViews(groupEntity));
        if(pagerAdapter != null && notifyDataChange){
            pagerAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 移除表情组
     * @param position
     */
    public void removeEmojiconGroup(int position){
        if(position > groupEntities.size() - 1){
            return;
        }
        if(pagerAdapter != null){
            pagerAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 获取pager数量
     * @return
     */
    private int getPageSize(OrangeEmojGroupEntity groupEntity) {
        List<OrangeEmoj> emojiconList = groupEntity.getEmojiconList();
        int itemSize = emojiconColumns * emojiconRows -1;
        int totalSize = emojiconList.size();
        OrangeEmoj.Type emojiType = groupEntity.getType();
        if(emojiType == OrangeEmoj.Type.BIG_EXPRESSION){
            itemSize = bigEmojiconColumns * bigEmojiconRows;
        }
        int pageSize = totalSize % itemSize == 0 ? totalSize/itemSize : totalSize/itemSize + 1;   
        return pageSize;
    }
    
    private class EmojiPagerChangeListener implements OnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
        	int endSize = 0;
        	int groupPosition = 0;
            for(OrangeEmojGroupEntity groupEntity : groupEntities){
            	int groupPageSize = getPageSize(groupEntity);
            	//选中的position在当前遍历的group里
            	if(endSize + groupPageSize > position){
            		//前面的group切换过来的
            		if(previousPagerPosition - endSize < 0){
            			if(pagerViewListener != null){
            				pagerViewListener.onGroupPositionChanged(groupPosition, groupPageSize);
            				pagerViewListener.onGroupPagePostionChangedTo(0);
            			}
            			break;
            		}
            		//后面的group切换过来的
            		if(previousPagerPosition - endSize >= groupPageSize){
            			if(pagerViewListener != null){
            				pagerViewListener.onGroupPositionChanged(groupPosition, groupPageSize);
            				pagerViewListener.onGroupPagePostionChangedTo(position - endSize);
            			}
            			break;
            		}
            		
            		//当前group的pager切换
            		if(pagerViewListener != null){
            			pagerViewListener.onGroupInnerPagePostionChanged(previousPagerPosition-endSize, position-endSize);
            		}
            		break;
            		
            	}
            	groupPosition++;
            	endSize += groupPageSize;
            }
            
            previousPagerPosition = position;
        }
        
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }
    
    
    
    public interface EaseEmojiconPagerViewListener{
        /**
         * pagerview初始化完毕
         * @param groupMaxPageSize 最大表情组的page大小
         * @param firstGroupPageSize 第一组的page大小
         */
        void onPagerViewInited(int groupMaxPageSize, int firstGroupPageSize);
        
    	/**
    	 * 表情组位置变动(从一组表情组移动另一组)
    	 * @param groupPosition 表情组位置
    	 * @param pagerSizeOfGroup 表情组里的pager的size
    	 */
    	void onGroupPositionChanged(int groupPosition, int pagerSizeOfGroup);
    	/**
    	 * 表情组内的page位置变动
    	 * @param oldPosition
    	 * @param newPosition
    	 */
    	void onGroupInnerPagePostionChanged(int oldPosition, int newPosition);
    	
    	/**
    	 * 从别的表情组切过来的page位置变动
    	 * @param position
    	 */
    	void onGroupPagePostionChangedTo(int position);
    	
    	/**
    	 * 表情组最大pager数变化
    	 * @param maxCount
    	 */
    	void onGroupMaxPageSizeChanged(int maxCount);
    	
    	void onDeleteImageClicked();
    	void onExpressionClicked(OrangeEmoj emojicon);
    	
    }

}
