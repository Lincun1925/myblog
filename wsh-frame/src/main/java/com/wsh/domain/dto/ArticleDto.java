package com.wsh.domain.dto;

import com.wsh.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto extends Article {

    private List<Long> tags;

}
