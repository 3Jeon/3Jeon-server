package umc.Jeon.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedList;

import static umc.Jeon.config.exception.BaseResponseStatus.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedList message;

    // validation 실패
    public BaseResponse(LinkedList errorList){
        this.isSuccess = false;
        this.code = 2090;
        this.message =  errorList;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        LinkedList list = new LinkedList();
        list.add(status.getMessage());
        this.isSuccess = status.isSuccess();
        this.message = list;
        this.code = status.getCode();
    }

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        LinkedList list = new LinkedList();
        list.add(SUCCESS.getMessage());
        this.isSuccess = SUCCESS.isSuccess();
        this.message = list;
        this.code = SUCCESS.getCode();
        this.result = result;
    }
}
