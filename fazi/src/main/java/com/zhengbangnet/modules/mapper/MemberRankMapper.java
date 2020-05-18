package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.MemberRank;
import org.springframework.stereotype.Repository;

/**
 * 
 * MemberRankMapper数据库操作接口类
 * 
 **/

@Repository("memberRankMapper")
public interface MemberRankMapper extends BaseMapper<MemberRank,Long>{


    MemberRank getDefault();

    MemberRank getByPoint(Integer point);
}