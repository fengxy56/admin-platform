import request from '../common/request'
export default   {
	//【用户管理】查询登录用户
	login: function(data) {
		return request.post(`/api/login`, data)
	},
	loginOut: function(data) {
		return request.get(`/api/loginOut`, data)
	},
	//【用户管理】查询登录用户
	getUserInfo: function(data) {
		return request.get(`/api/user/getUserInfo`, data)
	},
	//【用户管理】查询用户左侧授权菜单--首页显示
	getUserLoginTreeMenu: function(data) {
		return request.get(`/api/user/getUserLoginTreeMenu`, data)
	},
	//【菜单管理】查询菜单组件-末级菜单
	getXMenuLink: function(data) {
		return request.get(`/api/sysMenu/getHkMenuLink`, data)
	},
	//【菜单管理】获取菜单列表
	getXMenuList: function(data) {
		return request.get(`/api/sysMenu/getHkMenuList`, data)
	},
	//【菜单管理】获取菜单树状结构
	getTreeXMenuList: function(data) {
		return request.get(`/api/sysMenu/getTreeXMenuList`, data)
	},
	//【菜单管理】保存菜单
	saveOrUpdateHkMenu: function(data) {
		return request.post(`/api/sysMenu/saveOrUpdateHkMenu`, data)
	},
	//【菜单管理】删除菜单
	deleteXMenuById: function(data) {
		return request.get(`/api/sysMenu/deleteHkMenuById`, data)
	},
	//【组织用户】查询组织数列表
	getSysOrgTreeList: function(data) {
		return request.get(`/api/sysOrg/getSysOrgTreeList`, data)
	},
	//【组织用户】保存组织
	saveSysOrg: function(data) {
		return request.post(`/api/sysOrg/saveSysOrg`, data)
	},
	//【组织用户】修改组织
	updateSysOrg: function(data) {
		return request.post(`/api/sysOrg/updateSysOrg`, data)
	},
	//【组织用户】移动组织
	moveSysOrgSort: function(data) {
		return request.post(`/api/sysOrg/moveSysOrgSort`, data)
	},
	
	//【组织用户】查询用户列表
	getSysUserList: function(data) {
		return request.post(`/api/sysUser/getSysUserList`, data)
	},
	//【组织用户】保存用户
	saveSysUser: function(data) {
		return request.post(`/api/sysUser/saveSysUser`, data)
	},
	//【组织用户】修改用户
	updateSysUser: function(data) {
		return request.post(`/api/sysUser/updateSysUser`, data)
	},
	//【组织用户】移动用户
	moveSysUserSort: function(data) {
		return request.post(`/api/sysUser/moveSysUserSort`, data)
	},
	
	
	//【权限管理】查询用户角色列表
	getUvERoleUserList: function(data) {
		return request.post(`/api/sysAuthSysMenu/getUvERoleUserList`, data)
	},

	//【权限管理】查询系统权限授权菜单树--授权使用
	getTreeSysAuthMenuList: function(data) {
		return request.post(`/api/sysAuthSysMenu/getTreeSysAuthMenuList`, data)
	},
	//【权限管理】保存修改系统权限--保存修改角色权限
	saveOrUpdateSysAuthMenu: function(data) {
		return request.post(`/api/sysAuthSysMenu/saveOrUpdateSysAuthMenu`, data)
	},
	
	//【权限管理】获取组织信息
	getHxOrgTreeList: function(data) {
		return request.post(`/api/hxOrg/getHxOrgTreeList`, data)
	},




	//【接口管理】【系统信息】查询账户授权菜单树--授权使用
	getSysInfoList: function(data) {
		return request.get(`/api/sysInfo/getSysInfoList`, data)
	},
	//【接口管理】【系统信息】保存账户授权
	saveOrUpdateSysInfo: function(data) {
		return request.post(`/api/sysInfo/saveOrUpdateSysInfo`, data)
	},


	//【短信管理】【短信系统】查询账户授权菜单树--授权使用
	getSmsSysInfoList: function(data) {
		return request.get(`/api/sms/getSmsSysInfoList`, data)
	},
	//【短信管理】【短信系统】保存账户授权
	saveOrUpdateSmsSysInfo: function(data) {
		return request.post(`/api/sms/saveOrUpdateSmsSysInfo`, data)
	},





	//【字典管理】查询字典树状结构数据
	getDictList: function(data) {
		return request.get(`/api/sysDict/getTreeDict`, data)
	},
	//【字典管理】保存/修改字典数据
	saveOrUpdateDictInfo: function(data) {
		return request.post(`/api/sysDict/saveOrUpdateDictInfo`, data)
	},
	//【字典管理】 获取字典集合
	getDictListByParentId: function(data) {
		return request.get(`/api/sysDict/getDictListByParentId`, data)
	},


	//【项目列表】左侧组织树
	projectList: function(data) {
		return request.get(`/api/sysDict/getProjectList`, data)
	},



	
	
	//【鸿信-鸿信签到】查询鸿信签到信息
	getHxAttendanceSetList: function(data) {
		return request.post(`/api/hxAttendanceSet/getHxAttendanceSetList`, data)
	},
	//【鸿信-鸿信签到】根据主键id查询鸿信签到信息
	getHxAttendanceSetById: function(data) {
		return request.get(`/api/hxAttendanceSet/getHxAttendanceSetById`, data)
	},
	//【鸿信-鸿信签到】修改鸿信签到信息
	updateHxAttendanceSetById: function(data) {
		return request.post(`/api/hxAttendanceSet/updateHxAttendanceSetById`, data)
	},



	
	//【鸿信-轨迹签到】查询轨迹信息
	getHxSignTrackConfigureList: function(data) {
		return request.post(`/api/hxSignTrackConfigure/getHxSignTrackConfigureList`, data)
	},

	//【鸿信-轨迹签到】保存轨迹信息
	saveOrUpdateHxSignTrackConfigure: function(data) {
		return request.post(`/api/hxSignTrackConfigure/saveOrUpdateHxSignTrackConfigure`, data)
	},

	//【鸿信-轨迹签到】保存轨迹权限
	saveBathHxSignTrackBathAuth: function(data) {
		return request.post(`/api/hxSignTrackAuth/saveBathHxSignTrackBathAuth`, data)
	},
	// 【鸿信-轻应用授权】获取轻应用授权列表
	getHxSignTrackAuthList: function(data) {
		return request.post(`/api/hxSignTrackAuth/getHxSignTrackAuthList`, data)
	},

	// 【鸿信-轻应用授权】获取轻应用授权列表
	deleteHxSignTrackAuthList: function(data) {
		return request.post(`/api/hxSignTrackAuth/deleteHxSignTrackAuthList`, data)
	},
	





	//【鸿信-监理轨迹签到记录】查询签到记录
	getHxSignTrackRecordList: function(data) {
		return request.post(`/api/hxSignTrackRecord/getHxSignTrackRecordList`, data)
	},
	//【鸿信-监理轨迹签到记录】查询签到汇总
	getHxSignTrackRecordSummary: function(data) {
		return request.post(`/api/hxSignTrackRecord/getHxSignTrackRecordSummary`, data)
	},

	//【鸿信-监理轨迹签到记录】查询签到汇总导出
	exportExcel: function(data) {
		return request.post(`/api/hxSignTrackRecord/exportExcel`, data)
	},


	//【鸿信-人员信息】获取人员信息
	getTBdPersonList: function(data) {
		return request.post(`/api/tbdPerson/getTBdPersonList`, data)
	},
	//【鸿信-同事圈】获取同事圈信息
	getWorkoList: function(data) {
		return request.post(`/api/worko/getWorkoList`, data)
	},
	//【鸿信-同事圈】修改同事圈信息
	updateWorko: function(data) {
		return request.post(`/api/worko/updateWorko`, data)
	},
	//【鸿信-同事圈】获取同事圈-用户信息
	getWorkoUsersList: function(data) {
		return request.post(`/api/users/getUsersList`, data)
	},
	//【鸿信-同事圈】获取同事圈-oa审批禁用用户信息
	saveOrUpdateOaApprovalUsers: function(data) {
		return request.post(`/api/users/saveOrUpdateOaApprovalUsers`, data)
	},
	

	//【鸿信-人员信息】修人员时间
	updateTBdPersonLastUpdateTime: function(data) {
		return request.post(`/api/tbdPerson/updateTBdPersonLastUpdateTime`, data)
	},

	//【鸿信-鸿信待办】获取待办信息
	getHxUserUndoStatusList: function(data) {
		return request.post(`/api/hxUserUndoStatus/getHxUserUndoStatusList`, data)
	},
	//【鸿信-鸿信待办】修改待办信息
	updateHxUserUndoStatus: function(data) {
		return request.post(`/api/hxUserUndoStatus/updateHxUserUndoStatus`, data)
	},
	//【鸿信-鸿信批量处理待办】修改待办信息
	batchUpdateHxUserUndoStatus: function(data) {
		return request.post(`/api/hxUserUndoStatus/batchUpdateHxUserUndoStatus`, data)
	},

	//【鸿信-移动审批】获取待办信息
	getHongkunPushinfoList: function(data) {
		return request.post(`/api/hongkunPushinfo/getHongkunPushinfoList`, data)
	},
	//【鸿信-移动审批】修改待办信息
	updateHongkunPushinfo: function(data) {
		return request.post(`/api/hongkunPushinfo/updateHongkunPushinfo`, data)
	},
	//【鸿信-移动审批】修改待办信息
	batchUpdateHongkunPushinfo: function(data) {
		return request.post(`/api/hongkunPushinfo/batchUpdateHongkunPushinfo`, data)
	},
	//【鸿信-移动审批】获取移动审批-应用西悉尼
	getHongkunOutsupportinfoList: function(data) {
		return request.post(`/api/hongkunPushinfo/getHongkunOutsupportinfoList`, data)
	},

	//【鸿信-首页轮播】获取首页轮播图
	getHxPollImageList: function(data) {
		return request.get(`/api/hxPollImage/getHxPollImageList`, data)
	},	
	//【同事圈上传】上传文件
	uploadTsq: "/api/fileUpload/uploadTsq.do",
	//【鸿信-首页轮播】获取首页轮播图
	saveOrUpdateHxPollImage: function(data) {
		return request.post(`/api/hxPollImage/saveOrUpdateHxPollImage`, data)
	},	

	//【鸿信-轻应用列表】查询列表
	getHxCustAppInfoList: function(data) {
		return request.post(`/api/hxCustAppInfo/getHxCustAppInfoList`, data)
	},
	//【鸿信-轻应用列表】保存基本信息
	saveHxCustAppInfo: function(data) {
		return request.post(`/api/hxCustAppInfo/saveHxCustAppInfo`, data)
	},	
	//【鸿信-轻应用列表】修改基本信息
	updateHxCustAppInfo: function(data) {
		return request.post(`/api/hxCustAppInfo/updateHxCustAppInfo`, data)
	},

	// 【鸿信-轻应用菜单】获取轻应用菜单列表
	getHxCustAppMenuList: function(data) {
		return request.post(`/api/hxCustAppInfo/getHxCustAppMenuList`, data)
	},
	// 【鸿信-轻应用菜单】保存或者修改
	saveOrUpdateHxCustAppMenu: function(data) {
		return request.post(`/api/hxCustAppInfo/saveOrUpdateHxCustAppMenu`, data)
	},


	// 【鸿信-轻应用菜单】获取轻应用菜单列表
	getUserRoleAppMenuVOList: function(data) {
		return request.get(`/api/hxAppAuth/getUserRoleAppMenuVOList`, data)
	},
	// 【鸿信-轻应用菜单】保存或者修改
	saveUserRoleHxAppMenuAuth: function(data) {
		return request.post(`/api/hxAppAuth/saveUserRoleHxAppMenuAuth`, data)
	},

	

	//【鸿信-轻应用授权】获取用户角色授权
	getHxUserRoleAppTree: function(data) {
		return request.get(`/api/hxAppAuth/getHxUserRoleAppTree`, data)
  	},

	// 【鸿信-轻应用授权】保存用户角色授权
	saveHxUserRoleAppAuth: function(data) {
		return request.post(`/api/hxAppAuth/saveHxUserRoleAppAuth`, data)
	},
	// 【鸿信-轻应用授权】批量保存用户角色授权
	saveBathHxUserRoleAppAuth: function(data) {
		return request.post(`/api/hxAppAuth/saveBathHxUserRoleAppAuth`, data)
	},
	// 【鸿信-轻应用授权】获取轻应用授权列表
	getAppUserRoleAuthList: function(data) {
		return request.post(`/api/hxAppAuth/getAppUserRoleAuthList`, data)
	},


	


	// 【数据统计-智慧工地】获取用户点击日志
	getLogHxSiteList: function(data) {
		return request.post(`/api/logHxSite/getLogHxSiteList`, data)
	},
	// 【数据统计-工作更新】获取用户点击日志
	getHxWorkReportDailySummaryCountList: function(data) {
		return request.post(`/api/hxWorkReport/getHxWorkReportDailySummaryCountList`, data)
	},


	// 【二维码】获取维码列表
	getHxCodeInfoList: function(data) {
		return request.post(`/api/hxCode/getHxCodeInfoList`, data)
	},
	// 【二维码】保存或者修改二维码
	saveOrUpdateHxCodeInfo: function(data) {
		return request.post(`/api/hxCode/saveOrUpdateHxCodeInfo`, data)
	},



}
