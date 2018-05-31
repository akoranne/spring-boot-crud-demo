package com.sakx.developer.demo.exception;

import com.sakx.developer.demo.utils.JsonUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorInfo {
    private String code;
    private String url;
    private String message;
    private Object payload;
    
     
  
    
    public String toJson() {
		return JsonUtils.toJson(this, this.getClass());
	}
 
}
