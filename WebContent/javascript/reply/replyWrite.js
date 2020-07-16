/**
 * 
 */

var arr=new Array();
function writeToServer(root){
	var writeReply=document.getElementById("writeReply").value;
	arr.push(root + "," + writeReply);
	
	var url=root + "/reply/replyWrite.do";
	var params="writeReply="+writeReply;
	
	sendRequest("POST", url, params, writeFromServer);
	
	alert(arr.join("\n"));
}

function writeFromServer(){
	if(xhr.readyState==4 && xhr.status==200){
		arr.push("result: " + xhr.responseText);
		
		alert(arr.join("\n")); //넘어온것 확인
		
		//이제 뿌려야함
		
		
	}
}