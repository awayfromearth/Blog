package com.cm.weblog.admin.service.impl;

import com.cm.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.cm.weblog.admin.service.AdminArticleService;
import com.cm.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.cm.weblog.common.domain.dos.ArticleContentDO;
import com.cm.weblog.common.domain.dos.ArticleDO;
import com.cm.weblog.common.domain.dos.CategoryDO;
import com.cm.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.cm.weblog.common.domain.mapper.ArticleContentMapper;
import com.cm.weblog.common.domain.mapper.ArticleMapper;
import com.cm.weblog.common.domain.mapper.CategoryMapper;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    private Long articleId;

    @Override
    @Transactional(rollbackFor = Exception.class) // 1、开启事务回滚
    public Response<?> publishArticle(PublishArticleReqVO publishArticleReqVO) {
        /*
        * 2、VO 转 ArticleDO 并保存
        * */
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);
        // 插入后拿到 ID
        Long articleId = articleDO.getId();

        /*
        * 3、VO 转 ArticleContentDO 并保存
        * */
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);

        /*
        * 4、校验文章分类并处理
        * */
        Long categoryId = publishArticleReqVO.getCategoryId();
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在，category: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 手动抛出一个运行时异常
        // int i = 10 / 0;

        /*
        * 5、保存文章标签
        * */
        List<String> publishTags = publishArticleReqVO.getTags();
        insetTags(publishTags);

        return Response.success();
    }

    /**
     * 保存标签
     * @param tags 标签集合
     */
    private void insetTags(List<String> tags) {
        // TODO
    }
}
