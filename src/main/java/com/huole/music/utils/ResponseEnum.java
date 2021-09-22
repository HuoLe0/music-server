package com.huole.music.utils;

public enum ResponseEnum {

    SUCCESS("success", "成功", 1, true),
    ERROR("error", "失败", -1, false),
    NO_PERMISSION("no_permission", "无权限", -2, false),
    MODIFY_SUCCESS("modify_success", "修改成功", 666, true),
    MODIFY_FAILED("modify_failed", "修改失败", -1, false),
    LOGIN_SUCCESS("login_success", "登录成功", 1, true),
    LOGIN_FAILED("login_failed", "登录失败", -1, false),
    COLLECT_SUCCESS("collect_success", "收藏成功", 1, true),
    COLLECT_FAILED("collect_failed", "收藏失败", -1, false),
    COLLECT_REPEATED("collect_repeated", "重复收藏", -2, false),
    COMMENT_SUCCESS("comment_success", "评论成功", 1, true),
    COMMENT_FAILED("comment_failed", "评论失败", -1, false),
    COMMENT_NULL("comment_repeated", "评论内容为空", -2, false),

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
