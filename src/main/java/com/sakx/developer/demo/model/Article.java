package com.sakx.developer.demo.model;


import com.sakx.developer.demo.utils.JsonUtils;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

// @Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Article implements Serializable{

	private static final long serialVersionUID = -3970822026879214094L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", updatable = false, nullable = false)
	int articleId;

	String title;

	String category;

	public String toJson() {
		return JsonUtils.toJson(this, this.getClass());
	}
}