package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionRequest {

    @SerializedName("image")
    private VisionImageObject image;
    @SerializedName("features")
    private List<VisionFeatureObject> features;

    public VisionImageObject getImage() {
        return image;
    }

    public void setImage(VisionImageObject image) {
        this.image = image;
    }

    public List<VisionFeatureObject> getFeatures() {
        return features;
    }

    public void setFeatures(List<VisionFeatureObject> features) {
        this.features = features;
    }

}
