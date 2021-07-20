import axios from 'axios'
import qs from 'qs'
import tool from '../common/tool'

/**
 * 发送ajax
 *
 * @param {string} api, 请求路径,
 * @param {Object} data, 请求数据
 * @param {Object} options, ajax配置
 * @return {Deferred}, dfd
 */
async function sendAjax(api, method, data, options) {
  if (!api) {
    return
  }
  // 处理配置项
  var ajaxSetting = {
    url: api,
    method: method
  }
  if (method === 'get') {
    ajaxSetting.params = data
  } else if (method === 'put') {
    ajaxSetting.data = qs.stringify(data)
  } else {
    ajaxSetting.data = JSON.stringify(data)
  }
  Object.assign(ajaxSetting, options)
  const result = await axios(ajaxSetting)
  if (result.status === 200 && result.data.code === 1000) {
    console.log("登录失效需要重新登录");
    //登录失效需要重新登录
    window.location.href = "/login";
    //window.location.href = tool.getLoginAndLoginOutUrl(0);
    return;
  }
  return result.data;
}

/**
 * GET
 *
 * @param {string} api, 请求路径,
 * @param {Object} data, 请求数据
 * @param {Object} options, 配置项
 * @return {Deferred}, dfd
 */
function get(api, data, options) {
  return sendAjax(api, 'get', data, options)
}

/**
 * put 数据格式为表单form
 * 
 * @param {string} api, 请求路径,
 * @param {Object} data, 请求数据
 * @param {Object} options, 配置项
 * @return {Deferred}, dfd
 */
function put(api, data, options) {
  if (options === undefined) options = new Object();
  options.headers = {
    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
  }
  return sendAjax(api, 'put', data, options)
}

/**
 * post 数据格式Json
 *
 * @param {string} api, 请求路径,
 * @param {Object} data, 请求数据
 * @param {Object} options, 配置项
 * @return {Deferred}, dfd
 */
function post(api, data, options) {
  if (options === undefined) options = new Object();
  options.headers = {
    'Content-Type': 'application/json; charset=UTF-8'
  }
  return sendAjax(api, 'post', data, options)
}


export default {
  get: get,
  post: post,
  put: put,
  sendAjax: sendAjax
}
