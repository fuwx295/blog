package com.greywind.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.greywind.blog.dao.mapper.TagMapper;
import com.greywind.blog.dao.pojo.Tag;
import com.greywind.blog.service.TagService;
import com.greywind.blog.vo.Result;
import com.greywind.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //Mybatisplus 无法进行多表查询
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1.标签所拥有的文章数量最多的标签
         * 2.查询 根据tag_id分组 计数 ，从大到小 排列 取前limit个
         */
        List<Long> tagIds=tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        //需求的是tagId和tagName
        //select * from tag where id in(1,2,3,4)
        List<Tag> tagList=tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    /**
     * 查询所有文章标签
     *
     * @return
     */
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);

        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());

        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag=tagMapper.selectById(id);

        return Result.success(copy(tag));
    }
}
