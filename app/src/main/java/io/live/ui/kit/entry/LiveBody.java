package io.live.ui.kit.entry;

import androidx.annotation.Keep;

import org.json.JSONObject;

/**
 * by JFZ
 * 2024/4/11
 * desc：
 **/
@Keep
public abstract class LiveBody {

    private int extType;

    private String ext;

    public int getExtType() {
        return extType;
    }

    public void setExtType(int extType) {
        this.extType = extType;
    }

    public String getExt() {
        return compatString(ext);
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public final LiveBody parseJsonToBody(JSONObject obj) {
        if (obj != null) {
            if (obj.has("extType")) {
                setExtType(obj.optInt("extType"));
            }
            if (obj.has("ext")) {
                setExt(obj.optString("ext"));
            }
        }
        return parseBody(obj);
    }

    public abstract LiveBody parseBody(JSONObject obj);

    public final String compatString(String value) {
        return compatString(value, "");
    }

    public final String compatString(String value, String defVal) {
        if (value == null) {
            if (defVal != null) {
                return defVal;
            }
            return "";
        }
        return value;
    }
}
