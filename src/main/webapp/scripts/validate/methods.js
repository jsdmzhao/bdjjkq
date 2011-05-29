$.validator.addMethod("userName", function(value, element) {
	var param={"user.loginId": $('#username').val()};
	var res;
	$.ajax({
		url: URL_PREFIX + '/reg/isValidUsername.htm',
		type: 'post',
		async : false,
		dataType: 'text',
		data: param,
		success: function(rst, textStatus){
			res = rst;
		}
	});
	return this.optional(element) || res != "exist";
},"用户名称已存在");
 
//验证用户名格式
$.validator.addMethod("userNameFmt", function(value, element) {
	var userNameRegExp = /([a-z])([a-z0-9_]){5,15}/;
	var formatPass = value.replace(userNameRegExp,"").length==0;
	return this.optional(element) || formatPass;
},"须以字母开头，至少6位");

//验证用户邮箱
$.validator.addMethod("regEmail", function(value, element) {
	var param={"user.email": $('#email').val()};
	var res;
	$.ajax({
		url: URL_PREFIX + '/reg/isValidEmail.htm',
		type: 'post',
		async : false,
		dataType: 'text',
		data: param,
		success: function(rst, textStatus){
			res = rst;
		}
	});
	return this.optional(element) || res != "exist";
},"邮箱地址已存在");