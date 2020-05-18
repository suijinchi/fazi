package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.MemberRank;

public interface MemberRankService extends BaseService<MemberRank,Long> {


    MemberRank getDefault();
}