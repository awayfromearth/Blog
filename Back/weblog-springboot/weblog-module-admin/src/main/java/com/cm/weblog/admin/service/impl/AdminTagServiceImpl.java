package com.cm.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.weblog.admin.model.vo.tag.AddTagReqVO;
import com.cm.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.cm.weblog.admin.model.vo.tag.FindTagPageListRspVO;
import com.cm.weblog.admin.service.AdminTagService;
import com.cm.weblog.common.domain.dos.TagDO;
import com.cm.weblog.common.domain.mapper.TagMapper;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.utils.PageResponse;
import com.cm.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签服务实现类
 */
@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
    @Resource
    private TagMapper tagMapper;

    @Override
    @Transactional
    public Response<?> addTags(AddTagReqVO addTagReqVO) {
        // 1、VO 转 DO
        List<TagDO> tagDOs = addTagReqVO.getTags()
                .stream().map(tagName -> TagDO.builder()
                        .name(tagName.trim())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        // 2、批量插入数据
        try {
            saveBatch(tagDOs);
        } catch (Exception e) {
            log.warn("该标签已存在", e);
        }

        return Response.success();
    }

    @Override
    public PageResponse<List<FindTagPageListRspVO>> findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {
        // 1、获取分页查询条件
        Long current = findTagPageListReqVO.getCurrent();
        Long size = findTagPageListReqVO.getSize();
        String name = findTagPageListReqVO.getName();
        LocalDateTime startDate = findTagPageListReqVO.getStartDate();
        LocalDateTime endDate = findTagPageListReqVO.getEndDate();

        // 2、执行查询操作
        Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);
        List<TagDO> tagDOs = page.getRecords();

        // 3、DO 转 VO
        List<FindTagPageListRspVO> vos = Collections.emptyList();

        if (!CollectionUtils.isEmpty(tagDOs)) {
            vos = tagDOs.stream()
                    .map(tagDO -> FindTagPageListRspVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .createTime(tagDO.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(page, vos);
    }

    @Override
    public Response<?> deleteTag(Long id) {
        // 0 表示数据库中没有修改了的记录亦即没有匹配的标签；1表示数据库中修改了1条记录
        int count = tagMapper.deleteById(id);

        return count > 0 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }
}
