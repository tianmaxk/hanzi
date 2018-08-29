<!DOCTYPE html>
<!-- 导入自定义ftl -->
<#import "page.ftl" as cast/>
<html>
<head>
	<style>
	th,td {
		word-wrap:break-word;
		/*word-break:break-all;*/
		max-width:400px;
	}
	</style>
</head>
	<body>
		<div>表名：${tablename}</div><br/>
		<div>关键字搜索：<input type="text" id="keyword" style="width:200px;" />&nbsp;&nbsp;<input type="button" value="搜索" onclick="find();" /></div>
		<table border=1>
			<tr>
				<#list columns as col>
				<th>${col}</th>
				</#list>
			</tr>
			<#list contents as x>
			<tr>
				<#list columns as col>
				<td>${x[col]!""}</td>
				</#list>
			</tr>
			</#list>
		</table>
		<!-- 使用该标签 -->
		<@cast.page pageNo=pageno totalPage=totalpage showPages=9 callFunName="goto"/>
<script>
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return decodeURIComponent(r[2]); return null;
}

window.onload = function(){
	document.getElementById("keyword").value = GetQueryString("keyword");
}

function goto(page){
	var url = "tablepage?pageno="+page;
	var full = GetQueryString("full");
	if(full=='y'){
		url += "&full=y";
	}
	window.location.href = url;
}

function find(){
	var keyword = document.getElementById("keyword").value;
	var url = "tablepage?type=search&keyword="+keyword;
	var full = GetQueryString("full");
	if(full=='y'){
		url += "&full=y";
	}
	window.location.href = url;
}
</script>
	</body>
</html>