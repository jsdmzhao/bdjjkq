<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp" %>
<title>jquery验证演示</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#commentForm").validate();
});
</script>

<style type="text/css">
#commentForm { width: 500px; }
#commentForm label { width: 250px; }
#commentForm label.error, #commentForm input.submit { margin-left: 253px; }
</style>

</head>
<body>
<form class="cmxform" id="commentForm" method="post" action="">
	<fieldset>
		<legend>输入个人信息</legend>
		<p>

			<label for="cname">姓名 (必填, 最少两个字节)</label>
			<input id="cname" name="name" class="required" minlength="2" />
		<p>
			<label for="cemail">E-Mail (必填)</label>
			<input id="cemail" name="email" class="required email" />
		</p>
		<p>
			<label for="curl">URL (可选)</label>

			<input id="curl" name="url" class="url" value="" />
		</p>
		<p>
			<label for="ccomment">简介 (必填)</label>
			<textarea id="ccomment" name="comment" class="required"></textarea>
		</p>
		<p>
			<input class="submit" type="submit" value="Submit"/>

		</p>
	</fieldset>
</form>

</body>
</html>