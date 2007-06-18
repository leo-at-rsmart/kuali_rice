CREATE TABLE EN_RULE_ATTRIB_VLD_VAL_T (
	RULE_ATTRIB_VLD_VAL_ID NUMBER(19) NOT NULL,
	RULE_ATTRIB_VLD_VAL_NM VARCHAR2(2000) NOT NULL,
	RULE_ATTRIB_VLD_VAL_LBL_TXT VARCHAR2(2000) NOT NULL,
	RULE_ATTRIB_ID NUMBER(19) NOT NULL,
	RULE_ATTRIB_VLD_VAL_CUR_IND NUMBER(1) DEFAULT 0,
    RULE_ATTRIB_VLD_VAL_VER_NBR NUMBER(8) DEFAULT 0,
	DB_LOCK_VER_NBR	NUMBER(8) DEFAULT 0,
	CONSTRAINT EN_RULE_ATTRIB_VLD_VAL_PK PRIMARY KEY (RULE_ATTRIB_VLD_VAL_ID) USING INDEX
)
/