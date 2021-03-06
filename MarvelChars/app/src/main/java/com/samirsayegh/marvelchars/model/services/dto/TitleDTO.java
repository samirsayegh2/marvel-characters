package com.samirsayegh.marvelchars.model.services.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.samirsayegh.marvelchars.model.services.dto.base.BaseInformationDTO;

/**
 * Created by yormirsamir.sayegh on 26/04/2017.
 */

public class TitleDTO extends BaseInformationDTO {

    @SerializedName("title")
    @Expose
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
