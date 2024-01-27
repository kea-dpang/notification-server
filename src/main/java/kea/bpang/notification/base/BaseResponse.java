package kea.bpang.notification.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * API 응답의 기본 형식을 정의하는 클래스
 * 모든 API 응답은 이 클래스를 상속받아야 하며, 공통적으로 상태 코드와 메시지를 포함한다.
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    private int status;
    private String message;
}
