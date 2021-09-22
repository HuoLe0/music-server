package com.huole.music.service;


public interface ReturnService {

    public void setSuccess(boolean success);

    public void setCode(Integer code);

    public void setData(Object data);

    public void setMsg(String msg);

    public void setExt(String ext);

    public void setTimestamp(Long timestamp);

    public Object getReturnValue();
}
