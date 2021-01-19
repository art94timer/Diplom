package com.art.dip.utility.exception;

import java.io.Serial;

public class NotFoundEntityException extends RuntimeException{


	public NotFoundEntityException(String msg) {
		super(msg);
	}
}
