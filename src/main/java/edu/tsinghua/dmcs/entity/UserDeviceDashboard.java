package edu.tsinghua.dmcs.entity;

public class UserDeviceDashboard {
    private Long id;

    private Long deviceId;

    private Long userId;

    private Integer x_position;

    private Integer widget;

    private Integer type;

    private Long paramId;

    private Integer y_position;

    private Integer width;

    private Integer heighth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getX_position() {
        return x_position;
    }

    public void setX_position(Integer x_position) {
        this.x_position = x_position;
    }

    public Integer getWidget() {
        return widget;
    }

    public void setWidget(Integer widget) {
        this.widget = widget;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public Integer getY_position() {
        return y_position;
    }

    public void setY_position(Integer y_position) {
        this.y_position = y_position;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeighth() {
        return heighth;
    }

    public void setHeighth(Integer heighth) {
        this.heighth = heighth;
    }
}