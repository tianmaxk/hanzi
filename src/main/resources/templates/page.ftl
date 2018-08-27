<#-- 参数说明：pageNo当前的页码，totalPage总页数， showPages显示的页码个数，callFunName回调方法名（需在js中自己定义）-->
<#macro page pageNo totalPage showPages callFunName>
	<style>
	/*分页 begin*/
.page_list{width:75%;margin:5% auto;line-height:46px;}
.page_list a{ text-decoration: none;font-size:75%;color:#666; display: block;float: left;width:46px;height:46px;line-height:46px;
    margin-right: 10px;text-align: center;background-color: #fff;border:1px solid #dedede;}
.page_list a.top_page,.page_list a.end_page{width:62px;}
.page_list a.page_prev,.page_list a.page_next{width:76px;}
.page_list span.page_ellipsis{display: block;float: left;width:46px;height:46px;margin-right: 10px;background-position:5px -88px;border:none;}
.page_list a:hover ,.page_list a.current{background-color: #dedede;color:#333;}
/*分页 end*/
	</style>
	<div class="page_list clearfix">
		<#if pageNo!=1>
        <a href="javascript:${callFunName+'('+1+')'};" class="top_page">首页</a>
        <a href="javascript:${callFunName+'('+(pageNo-1)?c+')'};" class="page_prev">上一页</a>
        </#if>
        <#if pageNo-showPages/2 gt 0>
        	<#assign start = pageNo-(showPages-1)/2/>
        	<#if showPages gt totalPage>
        		<#assign start = 1/>
        	</#if>
        <#else>
        	<#assign start = 1/>
        </#if>
        <#if totalPage gt showPages>
        	<#assign end = (start+showPages-1)/>
        	<#if end gt totalPage>
        		<#assign start = totalPage-showPages+1/>
        		<#assign end = totalPage/>
        	</#if>
        <#else>
        	<#assign end = totalPage/>
        </#if>
        <#assign pages=start..end/>
        <#list pages as page>
        	<#if page==pageNo>
        		<a href="javascript:${callFunName+'('+page?c+')'};" class="current">${page?c}</a>
        	<#else>
        		<a href="javascript:${callFunName+'('+page?c+')'};">${page?c}</a>
        	</#if>
        </#list>
		<#if pageNo!=totalPage>
        	<a href="javascript:${callFunName+'('+(pageNo+1)?c+')'};" class="page_next">下一页</a>
        	<a href="javascript:${callFunName+'('+totalPage?c+')'};" class="end_page">尾页</a>
        </#if>
        &nbsp;共${totalcount?c}条记录，第${pageNo?c}页/共${totalPage?c}页
    </div>
</#macro>
