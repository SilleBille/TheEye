package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionBoundingPolyObject {

    @SerializedName("vertices")
    List<VisionVerticesObject> vertices;

    public List<VisionVerticesObject> getVertices() {
        return vertices;
    }

    public void setVertices(List<VisionVerticesObject> vertices) {
        this.vertices = vertices;
    }

}
