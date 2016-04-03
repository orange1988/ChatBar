package com.orange.chatbar.entity;


import java.util.List;

/**
 * 一组表情所对应的实体类
 */
public class OrangeEmojiGroupEntity {
    /**
     * 表情数据
     */
    private List<OrangeEmoji> emojiconList;
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
    private OrangeEmoji.Type type;

    public OrangeEmojiGroupEntity() {
    }

    public OrangeEmojiGroupEntity(int icon, List<OrangeEmoji> emojiconList) {
        this.icon = icon;
        this.emojiconList = emojiconList;
        type = OrangeEmoji.Type.NORMAL;
    }

    public OrangeEmojiGroupEntity(int icon, List<OrangeEmoji> emojiconList, OrangeEmoji.Type type) {
        this.icon = icon;
        this.emojiconList = emojiconList;
        this.type = type;
    }

    public List<OrangeEmoji> getEmojiconList() {
        return emojiconList;
    }

    public void setEmojiconList(List<OrangeEmoji> emojiconList) {
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

    public OrangeEmoji.Type getType() {
        return type;
    }

    public void setType(OrangeEmoji.Type type) {
        this.type = type;
    }


}
