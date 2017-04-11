<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>大数据查询系统</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css" />
<!-- 可选的Bootstrap主题文件（一般不用引入）
<link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap-theme.min.css" />
 -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="http://apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<style>
.main {
	margin: 0px auto;
	padding: 0px;
	width: 100%;
	font-size: 1.0rem;
}

.center {
	margin-top: 8rem;
	text-align: center;
}

.form-group {
	width: 80%;
	align: center;
	margin-left: 10%;
	margin-right: 10%;
}
</style>
</head>

<body>
	<div class="main">
		<div class="bg-primary" style="padding:1rem;text-align:center">
			<h2>数据查询系统</h2>
		</div>
		<div class="panel panel-primary"
			style="padding:0.3rem;margin-top:0.2rem">
			<form action="search.do" method="post" id="query"
				enctype="application/x-www-form-urlencoded" name="query"
				class="form-inline" role="form" onsubmit="return false">
				<div class="form-group">
					<label>QQ/邮箱/名字/网名/手机/ID：</label> <input type="text"
						class="form-control" id="key" name="keyworld" value=""
						placeholder="请输入关键字" style="width:80%" /> <input name=""
						type="submit" class="btn btn-primary" value="查询" onclick="search()"/>
				</div>
			</form>
		</div>
		<div style="align:center">
			<div class="form-group" id="result">
			</div>
		</div>
		</div>
</body>
<script>
	function search() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#query").serialize(),
			url : 'search.esp',
			timeout : 60000,
			success : function(json) {
				if (json.code == 0) {
					var result="";
					$.each(json.datas, function (n, value) {
		                result+=value+"<br>";
		            });
					$("#result").html(result);
				}
				alert(json.msg);
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}
</script>
</html>