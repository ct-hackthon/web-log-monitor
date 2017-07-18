package web.log.monitor.web.vo;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/13  *** fulongwen   *** Initial
 ***********************************************************/
public class ResponseVo {

    private int code;
    private String msg;
    private boolean isSuccess;
    private Object data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseVo ofSuccess(Object data){

        ResponseVo vo = new ResponseVo();
        vo.setCode(200);
        vo.setMsg("Success");
        vo.setSuccess(true);
        vo.setData(data);
        return vo;
    }

    public static ResponseVo ofFail(Object data){

        ResponseVo vo = new ResponseVo();
        vo.setCode(500);
        vo.setMsg("Error");
        vo.setSuccess(false);
        vo.setData(data);
        return vo;
    }
}
