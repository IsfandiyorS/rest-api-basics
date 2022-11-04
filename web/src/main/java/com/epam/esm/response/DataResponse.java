package com.epam.esm.response;

/**
 * Class {@code DataResponse} uses for cover all return responses as a one object
 * in {@link com.epam.esm.controller.GiftCertificateController} and
 * {@link com.epam.esm.controller.TagController} controllers
 *
 * @author Sultonov Isfandiyor
 * @since 1.0
 */
public class DataResponse<T> {
    private T data;
    private Boolean success;

    public DataResponse(Boolean success) {
        this.success = success;
    }
    public DataResponse(T data) {
        this.data = data;
        this.success = true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
