package com.github.jiahaowen.spring.assistant.component.migration.util.constans;

/**
 * 系统通用常量
 *
 * @author jiahaowen
 * @version $Id: CommonConstants.java, v 0.1 2014-11-13 上午10:03:33 jiahaowen Exp $
 */
public class ArcoreCommonConstants {

    /** 系统用户ID(-1) */
    public static final String SYSTEM_USER_ID = "-1";

    /** 下划线分隔符("_") */
    public static final String COMPART_UNDERLINE = "_";

    /** 逗号分隔符(",") */
    public static final String COMPART_COMMA = ",";

    /** 竖线分隔符("|") */
    public static String COMPART_VERTICAL_LINE = "|";

    /** 井号分隔符("#") */
    public static String COMPART_HASHTAG = "#";

    /** 合约业务编码，业务事件流水号前4位。即场景码，错误码的6-9位。 */
    public static final String ARRANGEMENT_BIZ_CODE = "1210";

    /** 合约单据号，生成合约号时用 */
    public static final String ARRANGEMENT_RECEIPT_CODE = "0000";

    /** 数据版本，业务订单号规范版本，只有在业务订单规范升级时才会产生新值，全局管控 */
    public static final String DATA_VERSION = "1";

    /** 系统版本 */
    public static final String SYSTEM_VERSION = "1";

    /** 事件码 */
    public static final String EVENT_NO = "event_no";

    /** 事件子任务编号 */
    public static final String SUB_EVENT_ID = "sub_event_id";

    /** 租户ID */
    public static final String TNT_INST_ID = "tnt_inst_id";

    /** 产品码 */
    public static final String PROD_CODE = "prod_code";

    /** 合约事件编码长度 */
    public static final int EVENT_CODE_LENGTH = 8;

    /** 合约编号业务扩展位 */
    public static final String BIZ_EXT_CODE = "0000";

    /** 合约号 */
    public static final String ARRANGEMENT_NO = "arrangementNo";

    /** 合约版本号 */
    public static final String ARRANGEMENT_VERSION = "arrangementVersion";

    /** 合约号长度 */
    public static final int ARRANGEMENT_NO_LENGTH = 28;

    /** 业务事件流水号长度 */
    public static final int ARRANGEMENT_BIZ_EVENT_NO_LENGTH = 32;

    /** 参与者id中客户分表位的倒数起始位 */
    public static final int INVOVLED_PARTY_ID_START = 3;

    /** 参与者id中客户分表位的倒数结束位 */
    public static final int INVOVLED_PARTY_ID_END = 2;

    /** 虚拟卡号 */
    public static final String VIRTUAL_CARD_NO = "virtualCard";

    /** 账号 */
    public static final String ACCOUNT_NO = "accountNo";

    /** 锁ID */
    public static final String LOCK_ID = "lockId";

    /** 客户类型key */
    public static final String INVOVLED_PARTY_TYPE_STR = "InvolvedPartyType";

    /** 任务信息 */
    public static final String TASK_TASK_INFO = "task_info";

    /** 策略信息 */
    public static final String TASK_STRATEGY_INFO = "strategy_info";

    /** 数量 */
    public static final String COUNT = "count";

    /** 事件处理成功结果—T */
    public static final String EVENT_SUCCESS_RESULT = "T";

    /** 合约影响解约信息 */
    public static final String AR_AFFECT_INVALID_INFO = "arAffectInvalidInfo";

    /** 合约生效/失效日期 */
    public static final String AR_DATE = "ar_date";

    /** 合约模板产品完整数据 */
    public static final String AR_TPL_PD_DATA = "AR_TPL_PD_DATA";

    /** 签约产品完整数据 */
    public static final String SIGN_PD_DATA = "SIGN_PD_DATA";

    /** future默认超时时间 */
    public static final long FUTURE_DEFAULT_TIME_OUT = 6000;

    /** 是 */
    public static final String TRUE = "01";

    /** 否 */
    public static final String FALSE = "02";

    /** 合约锁默认超时时间，单位（秒） */
    public static final long AR_LOCK_TIME_OUT_SECENDS = 60 * 3;

    /** 网商tnt_inst_id */
    public static final String MYBKC1CN = "MYBKC1CN";

    /** 批处理线程池容量 */
    public static final int BATCH_THREAD_POOL_CAPACITY = 50;

    /** 开户产品条件组(核算开户条件组) */
    public static final String OPEN_ACCOUNT_GROUP_CODE = "99991223";

    /** 续签合约主合约 */
    public static final String MAIN_RENEWAL_AR = "01";

    /** 续签合约子合约 */
    public static final String REL_RENEWAL_AR = "02";

    /** 唯一ID */
    public static final String UNIQUE_ID = "uniqueId";

    /** 芝麻信用tnt_inst_id */
    public static final String SSCMW1CN = "SSCMW1CN";

    /** 芝麻信用分支机构id */
    public static final String Z79 = "Z79";

    /** 生效续签合约的子合约 */
    public static final String NEED_VALID_RENEWAL_AR = "01";

    /** 续签子合约key */
    public static final String RENEWAL_SUB_AR = "RENEWAL_SUB_AR";

    /** 单独生效主合约 */
    public static final String IS_VALID_MAIN = "01";

    /** 后台异步任务 */
    public static final String ASY_TASK = "01";

    /** 前台接口 */
    public static final String FACADE = "02";

    /** 操作入口-调整为合约级的协商等级 */
    public static final String ADJUST_2_AR_AGREE_LEVEL = "01";

    /** 预校验结果 */
    public static final String PRE_CHECK_INFO = "PRE_CHECK_INFO";

    /** 偏移至天 */
    public static final String SHIFT_DAY = "01";

    /** 偏移至秒 */
    public static final String SHIFT_SECOND = "02";

    /** 消息DTO属性key:action */
    public static final String DTO_PTY_KEY_ACTION = "action";

    /** 消息DTO属性key:prodType */
    public static final String DTO_PTY_KEY_PRODTYPE = "prodType";

    /** 消息DTO属性key:instId */
    public static final String DTO_PTY_KEY_INSTID = "instId";

    /** 金融云租户id */
    public static final String FNCCW6CN = "FNCCW6CN";

    /** 缓存对象版本 */
    public static final int CACHE_VERSION = 1;

    /** 缓存分隔符 */
    public static final String CACHE_SPLITER = "_";
}
