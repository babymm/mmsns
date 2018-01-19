package com.lovecws.mumu.mmsns.modular.recommend.mapreduce.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 计算模型
 * @date 2018-01-17 16:53:
 */
public class ItemModel {

    private long uid;
    private long itemid;
    private float proference;

    public long getUid() {
        return uid;
    }

    public ItemModel() {
    }

    public ItemModel(long uid, long itemid, float proference) {
        this.uid = uid;
        this.itemid = itemid;
        this.proference = proference;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public float getProference() {
        return proference;
    }

    public void setProference(float proference) {
        this.proference = proference;
    }


    private final List<ItemModel> ITEM_MODEL_LIST = new ArrayList<ItemModel>();

    public synchronized ItemModel put(ItemModel itemModel) {
        ITEM_MODEL_LIST.add(itemModel);
        return itemModel;
    }

    public synchronized void puts(List<ItemModel> itemModelList) {
        ITEM_MODEL_LIST.addAll(itemModelList);
    }

    public String value() {
        StringBuilder stringBuilder = new StringBuilder();
        ITEM_MODEL_LIST.sort(new Comparator<ItemModel>() {
            @Override
            public int compare(ItemModel o1, ItemModel o2) {
                return o1.getUid() == o2.getUid() ? 0 : o1.getUid() > o2.getUid() ? 1 : -1;
            }
        });
        for (ItemModel itemModel : ITEM_MODEL_LIST) {
            stringBuilder.append(itemModel.getUid() + "," + itemModel.getItemid() + "," + itemModel.getProference() + "\n");
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
