package com.example.recepiefinder.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root {
    @SerializedName("hits")
    private List<Hit> hits;

    public Root() {
    }

    public Root(List<Hit> hits) {
        this.hits = hits;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
