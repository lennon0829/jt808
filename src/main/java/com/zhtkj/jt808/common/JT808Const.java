package com.zhtkj.jt808.common;

/**
 * ClassName: JT808Const 
 * @Description: 消息id、分隔符、抓拍图片保存路径等等配置
 */
public class JT808Const {

	//抓拍图片保存路径
    public static final String IMAGE_SAVE_PATH = "D:\\catimg\\";
    
	//消息分隔符
	public static final int MSG_DELIMITER = 0x7e;
	
	//任务类
    public static final int TASK_HEAD_ID = 0x0F01; //任务类       

    public static final int TASK_BODY_ID_LOGIN = 0x0101; //任务类 - 登录ID

    public static final int TASK_BODY_ID_GPS = 0x0102; //任务类 - GPSID  
    
    public static final int TASK_BODY_ID_EVENT = 0x0103; //任务类 - 事件ID
    
    public static final int TASK_BODY_ID_SELFCHECK = 0x0104; //任务类 - 终端自检ID
    
    public static final int TASK_BODY_ID_VERSION = 0x0180; //任务类 - 终端程序版本信息上报ID
    
    public static final int TASK_BODY_ID_CONFIG = 0x0181; //任务类 - 网关下发终端配置文件信息ID
    
    //命令类
    public static final int ACTION_HEAD_ID = 0x0F02; //命令类

    public static final int ACTION_BODY_ID_LOCKCAR = 0x0201; //命令类 - 锁车命令ID    

    public static final int ACTION_BODY_ID_LIMITSPEED = 0x0202; //命令类 - 限速命令ID  

    public static final int ACTION_BODY_ID_LIMITUP = 0x0203; //命令类 - 限举命令ID     
 
    public static final int ACTION_BODY_ID_IMGACT = 0x0204; //命令类 - 抓拍命令ID     

    public static final int ACTION_BODY_ID_PASSWORD = 0x0205; //命令类 - 密码命令ID

    public static final int ACTION_BODY_ID_CONTROL = 0x0206; //命令类 - 管控命令ID
    
    public static final int ACTION_BODY_ID_LOCKCARCOMPANY = 0x0207; //命令类 - 运输公司锁车命令ID
    
    //参数类
    public static final int PARAM_HEAD_ID = 0x0F03; //参数类 
 
    public static final int PARAM_BODY_ID_LINE = 0x0301; //参数类 - 路线ID     

    public static final int PARAM_BODY_ID_GONG = 0x0302; //参数类 - 工地ID    

    public static final int PARAM_BODY_ID_XIAO = 0x0303; //参数类 - 消纳场ID    

    public static final int PARAM_BODY_ID_LIMSPCIRCLE = 0x0304; //参数类 - 限速圈ID      

    public static final int PARAM_BODY_ID_PARKING = 0x0305; //参数类 - 停车场ID    

    public static final int PARAM_BODY_ID_BAN = 0x0306; //参数类 - 禁区ID    

    public static final int PARAM_BODY_ID_WORKPASSPORT = 0x0307; //参数类 - 核准证ID     

    public static final int PARAM_BODY_ID_INFO = 0x0308; //参数类 - 提示信息ID     

    public static final int PARAM_BODY_ID_FINGER = 0x0309; //参数类 - 指纹ID     

    public static final int PARAM_BODY_ID_REDLIGHT = 0x030A; //参数类 - 红灯电子围栏ID   
    
    public static final int PARAM_BODY_ID_DEVICECONFIG = 0x030B; //参数类 - 终端地址配置 
    
    public static final int PARAM_BODY_ID_LOCKCAREXT = 0x030C; //参数类 - 扩展锁车命令信息定义
    
    public static final int PARAM_BODY_ID_NOTIFY = 0x030D; //参数类 - 资讯提示信息定义
    
    public static final int PARAM_BODY_ID_CONTROLSWITCH = 0x0380; //参数类 - 管控处理开关
    
    public static final int PARAM_BODY_ID_THRESHOLDVALUE = 0x0381; //参数类 - 业务阙值定义
}
