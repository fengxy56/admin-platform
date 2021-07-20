

//获取随机菜单编码
function genID(length) {
    return Number(Math.random().toString().substr(3, length || 1) + Date.now()).toString(36);
}
//获取登录登出url  type==0登录其他为登出
function getLoginAndLoginOutUrl(type) {

   // storage.storage.api.remove("verify_token");
//    console.log(storage.get("verify_token"));
//     storage.remove("verify_token");
        

    //

	//接口域名
    const domainUrl = (process.env.NODE_ENV === "production" ? window.location.origin : "http://localhost:9002");
	//前端域名
    const feUrl = (process.env.NODE_ENV === "production" ? window.location.origin : "http://localhost:8002");
	//回调地址base64编码
    const redirectUrl = `${domainUrl}/api/-api/sso/redirect/${new Buffer(feUrl).toString('base64')}`;
    let url = "";
    if (type === 0) {
        url = `${domainUrl}/api/login`;
    } else {
        url = `${domainUrl}/api/loginOut`;
    }
    console.log("url:"+url);
    debugger;
    return url;
}
//table排序根据数字不同显示不同颜色
function getNumberColor(num) {
    let params = {};
    switch (num) {
        case 1: {
            params.color = "green";
            break;
        }
        case 2: {
            params.color = "cyan";
            break;
        }
        case 3: {
            params.color = "blue";
            break;
        }
        case 4: {
            params.color = "geekblue";
            break;
        } 
        case 5: {
            params.color = "purple";
            break;
        } 
        case 6: {
            params.color = "orange";
            break;
        } 
        case 7: {
            params.color = "volcano";
            break;
        } 
        case 8: {
            params.color = "red";
            break;
        }
        case 9: {
            params.color = "#FF7744";
            params.type = "border";
            break;
        }
        case 10: {
            params.color = "#FF5511";
            params.type = "border";
            break;
        }
        case 11: {
            params.color = "#E63F00";
            params.type = "border";
            break;
        }

        default: {
            params.color = "magenta";
            break;
        }
    }
    return params;
}

export default {
    genID,
    getLoginAndLoginOutUrl,
    getNumberColor
}