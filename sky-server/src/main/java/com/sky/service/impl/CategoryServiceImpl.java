package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.inter.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类服务实现类
 * 提供分类相关的业务逻辑实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

        /**
     * 新增分类
     * @param categoryDTO 分类数据传输对象
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.insert(category);
    }

        /**
     * 分类分页查询
     * @param categoryPageQueryDTO 分类分页查询条件
     * @return 分页结果
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

        /**
     * 根据ID删除分类
     * @param id 分类ID
     * TODO:需要注意分类是否管理菜品和套餐，否则要报异常
     */
    @Override
    public void deleteById(Long id) {
        categoryMapper.delete(id);
    }

        /**
     * 修改分类信息
     * @param categoryDTO 分类数据传输对象
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.update(category);
    }

        /**
     * 启用或禁用分类
     * @param status 状态：1启用 0禁用
     * @param id 分类ID
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        categoryMapper.update(Category.builder()
                                    .id(id)
                                    .status(status)
                                    .updateTime(LocalDateTime.now())
                                    .updateUser(BaseContext.getCurrentId()).build());
    }

        /**
     * 根据类型查询分类列表
     * @param type 分类类型
     * @return 分类列表
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
