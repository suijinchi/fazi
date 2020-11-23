<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<link href="${respath}/admin/css/page.css?ver=${ver}" rel="stylesheet" type="text/css" />
<c:if test="${page!=null&&fn:length(page.content)>0 }">
	
	<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
	<c:set var="seg" value="5" />
	<c:set var="startSeg" value="${page.pageNo-(seg-1)/2 }" />
	<c:set var="endSeg" value="${page.pageNo + (seg-1)/2}" />
	<c:if test="${startSeg<1}">
		<c:set var="startSeg" value="1" />
	</c:if>
	<c:if test="${endSeg>page.totalPages}">
		<c:set var="endSeg" value="${page.totalPages }" />
	</c:if>
	<c:set var="previousPageNo" value="${page.pageNo-1}" />
	<c:set var="nextPageNo" value="${page.pageNo+1}" />
	
	<div id="page" class="page">
		
		<c:if test="${page.pageNo>1}">
			<a href="javascript:void(0);" onclick="$.pageSkip(${previousPageNo},this);">上一页</a>
		</c:if>
		<c:if test="${page.pageNo!=1&&page.totalPages>1 }">
			<a href="javascript:void(0);" onclick="$.pageSkip(1,this);">首页</a>
		</c:if>
		
		<c:forEach begin="${startSeg}" end="${endSeg }" var="pn" varStatus="status">
			
			<c:if test="${status.index==0&&pn!=1 }">
				<span>...</span>
			</c:if>
			
			<c:if test="${pn!=page.pageNo}">
				<a href="javascript:void(0);" onclick="$.pageSkip(${pn},this);">${pn}</a>
			</c:if>
			<c:if test="${pn==page.pageNo}">
				<a class="cur">${pn}</a>
			</c:if>
			
			<c:if test="${endSeg<page.totalPages&&pn==endSeg}">
				<span>...</span>
			</c:if>
		</c:forEach>
		
		<c:if test="${page.pageNo<page.totalPages }">
			<a href="javascript:void(0);" onclick="$.pageSkip(${nextPageNo},this);">下一页</a>
		</c:if>
		
		<c:if test="${page.pageNo!=page.totalPages&&page.totalPages>1}">
			<a href="javascript:void(0);" onclick="$.pageSkip(${page.totalPages},this);">尾页</a>
		</c:if>
		
		<span>共${page.totalPages}页</span>
		<span>跳转到</span>
		<input id="pageNo" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text" class="pn" name="pageNo" value="${page.pageNo}"/>
		<span>页</span>
		<input type="submit" class="sure" value="确定"/>
		
	</div>
	
	<script>
		$(function(){
			// 页码跳转
			$.pageSkip = function(pageNo,dom) {
				$(dom).closest("form").find("input[name='pageNo']").val(pageNo);
				$(dom).closest("form").submit();
				return false;
			}
		});
	</script>
</c:if>


<c:if test="${page==null||fn:length(page.content)==0 }">
	<div style="padding: 10px;text-align: center;">
		暂无记录
	</div>
</c:if>

