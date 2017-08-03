package test.bwie.com.mychatprogectitem.emojicon;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconIndicatorView;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconPagerView.EaseEmojiconPagerViewListener;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconScrollTabBar;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconScrollTabBar.EaseScrollTabBarItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Emojicon menu
 */
public class EaseEmojiconMenu extends EaseEmojiconMenuBase {
	
	private int emojiconColumns;
	private int bigEmojiconColumns;
    private EaseEmojiconScrollTabBar tabBar;
    private EaseEmojiconIndicatorView indicatorView;
    private EaseEmojiconPagerView pagerView;
    
    private List<EaseEmojiconGroupEntity> emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public EaseEmojiconMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public EaseEmojiconMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public EaseEmojiconMenu(Context context) {
		super(context);
		init(context, null);
	}
	
	private void init(Context context, AttributeSet attrs){
		LayoutInflater.from(context).inflate(com.hyphenate.easeui.R.layout.ease_widget_emojicon, this);
		TypedArray ta = context.obtainStyledAttributes(attrs, com.hyphenate.easeui.R.styleable.EaseEmojiconMenu);
        int defaultColumns = 7;
        emojiconColumns = ta.getInt(com.hyphenate.easeui.R.styleable.EaseEmojiconMenu_emojiconColumns, defaultColumns);
        int defaultBigColumns = 4;
        bigEmojiconColumns = ta.getInt(com.hyphenate.easeui.R.styleable.EaseEmojiconMenu_bigEmojiconRows, defaultBigColumns);
		ta.recycle();
		
		pagerView = (EaseEmojiconPagerView) findViewById(com.hyphenate.easeui.R.id.pager_view);
		indicatorView = (EaseEmojiconIndicatorView) findViewById(com.hyphenate.easeui.R.id.indicator_view);
		tabBar = (EaseEmojiconScrollTabBar) findViewById(com.hyphenate.easeui.R.id.tab_bar);
		
	}
	
	public void init(List<EaseEmojiconGroupEntity> groupEntities){
	    if(groupEntities == null || groupEntities.size() == 0){
	        return;
	    }
	    for(EaseEmojiconGroupEntity groupEntity : groupEntities){
	        emojiconGroupList.add(groupEntity);
	        tabBar.addTab(groupEntity.getIcon());
	    }
	    
	    pagerView.setPagerViewListener(new EmojiconPagerViewListener());
        pagerView.init(emojiconGroupList, emojiconColumns,bigEmojiconColumns);
        
        tabBar.setTabBarItemClickListener(new EaseScrollTabBarItemClickListener() {
            
            @Override
            public void onItemClick(int position) {
                pagerView.setGroupPostion(position);
            }
        });
	    
	}
	
	
	/**
     * add emojicon group
     * @param groupEntity
     */
    public void addEmojiconGroup(EaseEmojiconGroupEntity groupEntity){
        emojiconGroupList.add(groupEntity);
        pagerView.addEmojiconGroup(groupEntity, true);
        tabBar.addTab(groupEntity.getIcon());
    }
    
    /**
     * add emojicon group list
     * @param groupEntitieList
     */
    public void addEmojiconGroup(List<EaseEmojiconGroupEntity> groupEntitieList){
        for(int i= 0; i < groupEntitieList.size(); i++){
            EaseEmojiconGroupEntity groupEntity = groupEntitieList.get(i);
            emojiconGroupList.add(groupEntity);
            pagerView.addEmojiconGroup(groupEntity, i == groupEntitieList.size()-1 ? true : false);
            tabBar.addTab(groupEntity.getIcon());
        }
        
    }
    
    /**
     * remove emojicon group
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
	
	
	private class EmojiconPagerViewListener implements EaseEmojiconPagerViewListener {

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
        public void onExpressionClicked(EaseEmojicon emojicon) {
            if(listener != null){
                listener.onExpressionClicked(emojicon);
            }
        }
	    
	}
	
}