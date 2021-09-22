package com.huole.music.service.Impl;

import com.huole.music.model.ResultModel;
import com.huole.music.service.ReturnService;
import org.springframework.stereotype.Service;


@Service
public class ReturnServiceImpl implements ReturnService {

    private ResultModel resultModel = new ResultModel();

    @Override
    public void setSuccess(boolean success) {
        resultModel.setSuccess(success);
    }

    @Override
    public void setCode(Integer code) {
        resultModel.setCode(code);
    }

    @Override
    public void setData(Object data) {
        resultModel.setData(data);
    }

    @Override
    public void setMsg(String msg) {
        resultModel.setMsg(msg);
    }

    @Override
    public void setExt(String ext) {
        resultModel.setExt(ext);
    }

    @Override
    public void setTimestamp(Long timestamp) {
        resultModel.setTimestamp(timestamp);
    }

    @Override
    public Object getReturnValue() {
        return resultModel;
    }
}
