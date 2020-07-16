/**
 * http://
 */

var xhr=null;
var arr=new Array();

function startRequest(){
	if(window.XMLHttpRequest){
		xhr=new XMLHttpRequest;
	}else{
		xhr=new ActiveXObject("Microsoft.XMLHTTP");
	}
}

function toServer(root){
	var departmentName=document.getElementById("sawonForm").departmentName.value;
	arr.push(departmentName);
	alert(arr.join("\n"));
	
	if(departmentName != "부서선택하세요"){
		startRequest();
		url=root+"/sawon/listOk.do";
		
		xhr.open("POST", url, true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send("departmentName=" + departmentName);
		//xhr.onreadystatechange=resultProcess;
		
	}
	
	function resultProcess(){
		
	}
}