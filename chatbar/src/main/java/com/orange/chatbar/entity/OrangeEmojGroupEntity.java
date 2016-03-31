package com.orange.chatbar.entity;


import java.util.List;

/**
 * 一组表情所对应的实体类
 */
public class OrangeEmojGroupEntity {
    /**
     * 表情数据
     */
    private List<OrangeEmoj> emojiconList;
    /**
     * 图片
     */
    private int icon;
    /**
     * 组名
     */
    private String name;
    /**
     * 表情类型
     */
    private OrangeEmoj.Type type;

    public OrangeEmojGroupEntity() {
    }

    public OrangeEmojGroupEntity(int icon, List<OrangeEmoj> emojiconList) {
        this.icon = icon;
        this.emojiconList = emojiconList;
        type = OrangeEmoj.Type.NORMAL;
    }

    public OrangeEmojGroupEntity(int icon, List<OrangeEmoj> emojiconList, OrangeEmoj.Type type) {
        this.icon = icon;
        this.emojiconList = emojiconList;
        this.type = type;
    }

    public List<OrangeEmoj> getEmojiconList() {
        return emojiconList;
    }

    public void setEmojiconList(List<OrangeEmoj> emojiconList) {
        this.emojiconList = emojiconList;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrangeEmoj.Type getType() {
        return type;
    }

    public void setType(OrangeEmoj.Type type) {
        this.type = type;
    }


}
