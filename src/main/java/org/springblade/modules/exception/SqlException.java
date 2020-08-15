package org.springblade.modules.exception;

import lombok.Data;

/**
 * sql异常
 * @author SventySeven
 * @since 2020-08-15
 */
public class SqlException extends RuntimeException {
	private Integer code;

	public SqlException(String message) {
		super(message);
	}

	public SqlException(String message, Integer code) {
		super(message);
		this.code = code;
	}

}
