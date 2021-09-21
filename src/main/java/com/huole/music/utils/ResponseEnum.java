package com.huole.music.utils;

public enum ResponseEnum {

    SUCCESS("success", "成功", 1, true),
    ERROR("error", "失败", -1, false),
    NO_PERMISSION("no_permission", "无权限", -2, false),
    MODIFY_SUCCESS("modify_success", "修改成功", 666, true),
    MODIFY_FAILED("modify_failed", "修改失败", -1, false),

    UPLOAD_SUCCESS("upload_success", "上传成功", 1, true),
    UPLOAD_FAILED("upload_failed", "上传失败", -1, false),
    UPLOAD_IMAGE_FAILED("upload_image_fail", "图片上传失败", 1000, false),
    UPLOAD_FILE_FAILED("upload_file_fail", "文件上传失败", 1001, false),
    ADD_SUCCESS("add_success", "添加成功", 1, true),
    ADD_FAILED("add_failed", "添加失败", -1, false),;

    // 成员变量
    private String name;
    private String msg;
    private Integer code;
    private Boolean success;

    // 构造方法
    private ResponseEnum(String name, String msg, Integer code, Boolean success) {
        this.name = name;
        this.msg = msg;
        this.code = code;
        this.success = success;
    }

    public String getName(){
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean isSuccess() {
        return success;
    }
}
